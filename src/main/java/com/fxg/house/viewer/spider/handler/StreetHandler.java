package com.fxg.house.viewer.spider.handler;

import com.fxg.house.viewer.entity.Street;
import com.fxg.house.viewer.mapper.StreetMapper;
import com.fxg.house.viewer.spider.parser.StreetHtmlParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 解析出区县信息存入mysql
 */
public class StreetHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	private static String baseUrl = "https://%s.lianjia.com/xiaoqu/%s/";

	@Autowired
	private StreetMapper streetMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private StreetHtmlParser streetHtmlParser;

	/**
	 * 解析出区县信息存入mysql
	 *
	 * @return 处理成功的county数
	 */
	@Transactional
	public Integer parseStreetByCounty() {

		SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select * from county");

		int i = 1;
		while (rowSet.next()) {
			int countyId = rowSet.getInt("id");
			String countyName = rowSet.getString("name");
			String countyCode = rowSet.getString("code");
			int cityId = rowSet.getInt("city_id");
			String cityName = rowSet.getString("city_name");
			String cityCode = rowSet.getString("city_code");
			int provinceId = rowSet.getInt("province_id");
			String provinceName = rowSet.getString("province_name");

			String countyUrl = String.format(baseUrl, cityCode, countyCode);

			log.info("开始解析第{}个county，countyName：{}，url：{}", i, countyName, countyUrl);
			List<Street> streetList = streetHtmlParser.parsList(countyUrl);
			streetList.forEach(street -> {
				street.setCityId(cityId);
				street.setCityCode(cityCode);
				street.setCityName(cityName);
				street.setProvinceId(provinceId);
				street.setProvinceName(provinceName);
				street.setCountyId(countyId);
				street.setCountyCode(countyCode);
				street.setCountyName(countyName);
			});

			if (streetList.size() > 0) {
				List<Street> oldList = streetMapper.getStreetList(cityCode, countyCode);
				List<String> oldCodes = oldList.stream()
											   .map(Street::getCode)
											   .collect(Collectors.toList());
				List<Street> needAdd = streetList.stream()
												 .filter(s -> !oldCodes.contains(s.getCode()))
												 .collect(Collectors.toList());
				if (needAdd.size() > 0) {
					log.info("开始保存第{}个county下的street，countyName：{}，url：{}", i, countyName, countyUrl);
					streetMapper.insertBatch(needAdd);
					log.info("成功保存第{}个county下的street，countyName：{}，streetSize：{}", i, cityName, streetList.size());
				} else {
					log.info("此区县无新增街区，county：{}", countyName);
				}
			} else {
				log.info("此区县未解析出街区，county：{}", countyName);
			}
			i++;
		}
		return i;
	}

	/**
	 * 标记出重复的街区
	 *
	 * 缘由：上面的parseStreetByCounty方法解析出的street有重复的，链家的具体逻辑不清楚，好在不多，标记出来手动清除
	 */
	public void upStreet() {
		String msg = "城市码：{}，街区码：{}  ";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT a.name,a.code,a.county_name,a.city_code,a.num,a.city_name "
															   + "FROM (SELECT *,COUNT(0) num FROM street GROUP BY CODE,city_code ) a WHERE a.num >1");
		while (rowSet.next()) {
			String streetCode = rowSet.getString("code");
			String cityCode = rowSet.getString("city_code");
			log.info(msg + "开始处理", cityCode, streetCode);
			String rightCountyName = "";
			try {
				Document document = Jsoup.connect(String.format(baseUrl, cityCode, streetCode)).get();
				rightCountyName = document.select("div[data-role]")
										  .first()
										  .child(0)
										  .select("a[href].selected")
										  .first()
										  .text()
										  .trim();
				log.info(msg + "正确所属区县名：{}", cityCode, streetCode, rightCountyName);
			} catch (Exception e) {
				log.info(msg + "发生错误", cityCode, streetCode);
				e.printStackTrace();
			}

			List<Street> list = streetMapper.getStreetListByCode(cityCode, streetCode);
			log.info(msg + "重复街区数：{}", cityCode, streetCode, list.size());
			log.info(msg + "开始标记重复街区", cityCode, streetCode);
			int i = 0;
			for (Street street : list) {
				if (!rightCountyName.equals(street.getCountyName())) {
					streetMapper.updateDay(street.getUpdateDay() + 10, street.getId());
					i++;
				}
			}
			log.info(msg + "标记完成，标记数：{}", cityCode, streetCode, i);
			if (list.size() != i + 1) {
				log.error(msg + "有问题！！！", cityCode, streetCode);
			}
		}
	}
}
