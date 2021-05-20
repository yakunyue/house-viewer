package com.fxg.house.viewer.spider.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fxg.house.viewer.entity.Community;
import com.fxg.house.viewer.entity.Street;
import com.fxg.house.viewer.mapper.CommunityMapper;
import com.fxg.house.viewer.mapper.StreetMapper;
import com.fxg.house.viewer.spider.parser.CommunityHtmlParser;
import com.fxg.house.viewer.utils.StringHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * 解析小区信息的处理器
 */
@Service
public class CommunityHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private static String xiaoquListUrl = "https://%s.ke.com/xiaoqu/%s/pg%s/";

	private static String xiaoquDetailUrl = "https://%s.ke.com/xiaoqu/%s/";

	@Autowired
	private CommunityMapper communityMapper;
	@Autowired
	private StreetMapper streetMapper;
	@Autowired
	private CommunityHtmlParser communityHtmlParser;
	@Autowired
	private ObjectMapper objectMapper;


	/**
	 * 解析并保存一个区县的小区基本信息
	 */
	public int parseOneCountyCommunityMessage(String cityCode, String countyCode) {
		Integer totalNum = 0;
		List<Street> streetList = streetMapper.getStreetList(cityCode, countyCode);
		for (int i = 1; i <= streetList.size(); i++) {
			Integer count = this.parseOneStreetCommunityMessage(cityCode, countyCode, streetList.get(i - 1)
																								.getCode());
			totalNum += count;
		}
		logger.info("cityCode:{},countyCode:{},本次解析出小区数：{},", cityCode, countyCode, totalNum);
		return totalNum;
	}

	/**
	 * 解析并保存一个街区的小区基本信息
	 */
	public int parseOneStreetCommunityMessage(String cityCode, String countyCode, String streetCode) {
		Integer totalNum = 0;
		int totalPageNum = this.getTotalPageNum(cityCode, streetCode);
		logger.info("开始解析一个街区的小区--cityCode:{},countyCode:{},streetCode:{},小区页数：{}，", cityCode, countyCode, streetCode, totalPageNum);
		if (totalPageNum >= 100) {
			logger.error("开始解析一个街区的小区列表貌似出错，小区页数：{}，放弃处理", totalPageNum);
			return totalNum;
		}
		for (int i = 1; i <= totalPageNum; i++) {
			logger.info("街区码：{}，开始解析第{}页", streetCode, i);
			Integer count = this.parseOnePageCommunityMessage(cityCode, countyCode, streetCode, i);
			totalNum += count;
		}
		logger.info("解析一个街区的小区j结束--cityCode:{},countyCode:{},streetCode:{},本次解析出小区数：{},", cityCode, countyCode, streetCode, totalNum);
		return totalNum;
	}


	/**
	 * 解析一页的小区基本信息
	 */
	public int parseOnePageCommunityMessage(String cityCode, String countyCode, String streetCode, int i) {
		int totalInsertNum = 0;
		try {
			logger.info("开始解析第{}页", i);
			List<Community> list = communityHtmlParser.parsList(String.format(xiaoquListUrl, cityCode, streetCode, i));
			list.forEach(c -> {
				c.setCityCode(cityCode);
				c.setCountyCode(countyCode);
				c.setStreetCode(streetCode);
			});
			logger.info("本页共解析出{}条数据", list.size());
			List<String> newIds = list.stream()
									  .map(Community::getLianjiaId)
									  .collect(Collectors.toList());
			List<Community> oldCommunities = communityMapper.queryAlreadyExist(newIds);
			//挑出需要新增的
			List<String> oldIds = oldCommunities.stream()
												.map(Community::getLianjiaId)
												.collect(Collectors.toList());
			List<Community> needAdd = list.stream()
										  .filter(o -> !oldIds.contains(o.getLianjiaId()))
										  .collect(Collectors.toList());
			if (needAdd.size() > 0) {
				logger.info("本页新解析出{}条数据", needAdd.size());
				int count = communityMapper.insertBatch(needAdd);
				totalInsertNum += count;
			} else {
				logger.info("本页无需新增数据");
			}

			//剩下的更新
			list.removeAll(needAdd);
			list.removeAll(oldCommunities);
			if (list.size() > 0) {
				logger.info("本页需更新{}条数据", list.size());
				for (Community o : list) {
					communityMapper.updateCommunity(o);
				}
			} else {
				logger.info("本页无需更新数据");
			}

		} catch (Exception e) {
			logger.error("cityCode:{},streetCode:{},pageNo{},解析一页街区内小区时发生错误，error：{}", cityCode, streetCode, i, e.getMessage());
			e.printStackTrace();
		}
		return totalInsertNum;
	}

	/**
	 * 获取一个街区小区总页数
	 */
	public int getTotalPageNum(String cityCode, String streetCode) {
		int totalPage = 0;
		try {
			Document pageDoc = Jsoup.connect(String.format(xiaoquListUrl, cityCode, streetCode, 1))
									.get();
			Elements pageEl = pageDoc.select("div[page-data].page-box");
			String pageJson = pageEl.attr("page-data");
			totalPage = objectMapper.readTree(pageJson).get("totalPage").asInt(0);
		} catch (Exception e) {
			logger.error("城市码：{}，街区码：{}，解析此街区内小区总页数失败", cityCode, streetCode);
			e.printStackTrace();
		}
		return totalPage;
	}


	public BigDecimal referencePrice(String str) {
		//12345/平
		str = StringHandler.matchNumber(str);
		BigDecimal result = null;
		try {
			result = new BigDecimal(str).setScale(2, RoundingMode.HALF_UP);
		} catch (Exception e) {
			logger.warn("解析参考价格失败");
		}
		return result;
	}

	/**
	 * 异步方法--更新一个小区的基本信息
	 */
	@Async
	public Future<Integer> updateOneCommunityDetailAsync(String cityCode, String lianjiaId) {

		int count = 0;
		try {
			count = updateOneCommunityDetail(cityCode, lianjiaId);
		} catch (Exception e) {
			logger.error("更新小区详细信息失败，小区码：{}，error：{}", lianjiaId, e.getMessage());
			e.printStackTrace();
		}
		return new AsyncResult(count);
	}

	/**
	 * 更新一个小区的基本信息
	 */
	public Integer updateOneCommunityDetail(String cityCode, String lianjiaId) throws Exception {
		Community community = this.getCommunityDetail(cityCode, lianjiaId);
		if (StringUtils.isEmpty(community.getBuildingType()) && StringUtils.isEmpty(community.getPropertyCompany()) && StringUtils.isEmpty(
				community.getDevelopCompany()) && Objects.isNull(community.getBuildingNum()) && Objects.isNull(community.getHouseNum())) {
			return 0;
		}
		int count = communityMapper.updateDetailByLianjiaId(community);
		return count;
	}

	/**
	 * 解析出一个小区的基本信息
	 */
	private Community getCommunityDetail(String cityCode, String lianjiaId) throws IOException {
		Community community = communityHtmlParser.pars(String.format(xiaoquDetailUrl, cityCode, lianjiaId));
		community.setLianjiaId(lianjiaId);
		return community;
	}
}
