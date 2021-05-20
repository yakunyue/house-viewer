package com.fxg.house.viewer.spider.handler;

import com.fxg.house.viewer.entity.County;
import com.fxg.house.viewer.mapper.CountyMapper;
import com.fxg.house.viewer.spider.parser.CountyHtmlParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 解析出区县信息存入mysql
 */
public class CountyHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass()
													 .getName());

	private static String baseUrl = "https://%s.ke.com/ershoufang/";

	@Autowired
	private CountyMapper mapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private CountyHtmlParser countyHtmlParser;

	/**
	 * 解析出区县信息存入mysql
	 * @return 处理成功的city数
	 */
	@Transactional
	public Integer parseCounty() {

		SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select * from city where second_hand = 1");

		int i = 1;
		while (rowSet.next()) {
			int cityId = rowSet.getInt("id");
			String cityName = rowSet.getString("name");
			String cityCode = rowSet.getString("code");
			int provinceId = rowSet.getInt("province_id");
			String provinceName = rowSet.getString("province_name");
			String cityUrl = String.format(baseUrl, cityCode);
			log.info("开始解析第{}个city，cityName：{}，url：{}", i, cityName, cityUrl);
			List<County> countyList = countyHtmlParser.parsList(cityUrl);
			countyList.forEach(county -> {
				county.setCityId(cityId);
				county.setCityCode(cityCode);
				county.setCityName(cityName);
				county.setProvinceId(provinceId);
				county.setProvinceName(provinceName);
			});
			log.info("开始保存第{}个city下的county，cityName：{}，url：{}", i, cityName, cityUrl);
			mapper.insertBatch(countyList);
			log.info("成功保存第{}个city下的county，cityName：{}，countySize：{}", i, cityName, countyList.size());
			i++;
		}
		return i;
	}
}
