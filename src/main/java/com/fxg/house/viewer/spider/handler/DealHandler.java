package com.fxg.house.viewer.spider.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fxg.house.viewer.entity.CommunityHistoryDeal;
import com.fxg.house.viewer.entity.Street;
import com.fxg.house.viewer.mapper.CommunityHistoryDealMapper;
import com.fxg.house.viewer.mapper.CommunityMapper;
import com.fxg.house.viewer.service.StreetService;
import com.fxg.house.viewer.spider.parser.DealHtmlParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 解析出小区成交信息存入mysql
 */
@Component
public class DealHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass()
													 .getName());

	private static String baseUrl = "https://%s.ke.com/chengjiao/pg%sc%s/";
	private static String detailBaseUrl = "https://%s.lianjia.com/chengjiao/%s.html";

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private DealHtmlParser dealHtmlParser;
	@Autowired
	private CommunityHistoryDealMapper dealMapper;
	@Autowired
	private CommunityMapper communityMapper;
	@Autowired
	private StreetService streetService;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int incrementParseDealByCounty(String cityCode, String countyCode) {
		int totalNum = 0;
		List<Street> streetList = streetService.getStreetList(cityCode, countyCode);
		for (Street street : streetList) {
			List<String> lianjiaIdList = communityMapper.queryLianjiaIdList(cityCode, street.getCode());
			for (String lianjiaId : lianjiaIdList) {
				int count = this.incrementParseDealByCommunity(cityCode, countyCode, street.getCode(), lianjiaId);
				totalNum += count;
			}
		}
		return totalNum;
	}

	/**
	 * 解析出小区成交信息存入mysql
	 * @return 处理成功的city数
	 */
	@Transactional
	public Integer incrementParseDealByCommunity(String cityCode, String countyCode, String streetCode, String lianjiaId) {
		int totalInsertNum = 0;
		try {
			int totalPageNum = this.getTotalPageNum(cityCode, lianjiaId);
			if (totalPageNum>=100){
				log.error("开始解析一个小区的成交列表貌似出错，页数：{}，放弃处理",totalPageNum);
				return totalInsertNum;
			}
			log.info("小区码：{}，该小区成交记录总页数：{}", lianjiaId, totalPageNum);
			// 遍历页码，解析出成交列表
			for (int j = 1; j <= totalPageNum; j++) {
				log.info("开始增量解析第{}页", j);
				List<CommunityHistoryDeal> list = dealHtmlParser.parsList(String.format(baseUrl, cityCode, j, lianjiaId));
				log.info("本页共解析出{}条数据", list.size());
				if (list.size() == 0) {
					continue;
				}
				list.forEach(deal->{
					deal.setLianjiaCommunityId(lianjiaId);
					deal.setCityCode(cityCode);
					deal.setCountyCode(countyCode);
					deal.setStreetCode(streetCode);
				});
				List<String> newIds = list.stream().map(CommunityHistoryDeal::getLianjiaHouseId).collect(Collectors.toList());
				List<CommunityHistoryDeal> olddeals = dealMapper.queryAlreadyExist(newIds);
				// 过滤出需要的新增
				List<String> oldIds = olddeals.stream().map(CommunityHistoryDeal::getLianjiaHouseId).collect(Collectors.toList());
				List<CommunityHistoryDeal> needAdd = list.stream().filter(o -> !oldIds.contains(o.getLianjiaHouseId())).collect(Collectors.toList());
				if (needAdd.size() > 0) {
					log.info("本页共{}条新增数据", needAdd.size());
					int count = dealMapper.insertBatch(needAdd);
					totalInsertNum += count;
				} else {
					log.info("本页无需新增数据");
				}
				//本页如果有重复数据且该数据成交日期为60天前就break
				if (olddeals.size() > 0 && olddeals.get(0).getDealDate().isBefore(LocalDate.now().minusDays(60))) {
					log.info("出现60天前数据，结束该小区处理");
					break;
				}
			}
		} catch (Exception e) {
			log.error("解析一个小区成交记录报错，区县码:{},小区码：{}", countyCode, lianjiaId);
			e.printStackTrace();
		}
		return totalInsertNum;
	}

	private int getTotalPageNum(String cityCode, String lianjianId) {
		int totalPage = 0;
		try {
			Document pageDoc = Jsoup.connect(String.format(baseUrl, cityCode, 1, lianjianId)).get();
			Elements el = pageDoc.select("div[page-data].page-box");
			if (el.isEmpty()) {
				log.warn("小区码：{}，解析该小区成交记录总页数失败", lianjianId);
			} else {
				String pageJson = el.attr("page-data");
				log.info("小区码：{}，解析该小区成交记录总页数信息为{}", lianjianId, pageJson);
				totalPage = objectMapper.readTree(pageJson).get("totalPage").asInt(0);
				if (totalPage == 100) {
					totalPage = 0;
					log.warn("解析到的该小区成交记录页数异常，等于100，不再处理该小区，页面地址：{}", String.format(baseUrl, cityCode, 1, lianjianId));
				}
			}
		} catch (Exception e) {
			log.error("解析小区成交记录总页数失败，城市码：{}，小区码：{}", cityCode, lianjianId);
			e.printStackTrace();
		}
		return totalPage;
	}


	/**
	 * 从详情页获取deal信息，更新进mysql
	 */
	public Integer updateListPrice() {
		int totalNum = 0;

		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(
				"SELECT city_code,lianjia_house_id FROM community_history_deal WHERE  list_price IS NULL OR deal_cycle IS NULL  LIMIT 10000");

		while (rowSet.next()) {
			String houseId = rowSet.getString("lianjia_house_id");
			String cityCode = rowSet.getString("city_code");
			try {
				//速度太快会被封
				Thread.sleep((long) (Math.random() * 300));

				CommunityHistoryDeal deal = dealHtmlParser.pars(String.format(detailBaseUrl, cityCode, houseId));
				if (Objects.isNull(deal)){

				}
				deal.setLianjiaHouseId(houseId);
				dealMapper.updateListPrice(deal);
				totalNum ++;
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		int num = dealMapper.updateDealCycle();
		log.info("本次更新{}个成交周期", num);
		return totalNum;
	}


}
