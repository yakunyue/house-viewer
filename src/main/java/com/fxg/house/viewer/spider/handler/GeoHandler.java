package com.fxg.house.viewer.spider.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fxg.house.viewer.mapper.CommunityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;

/**
 * 处理地里位置信息
 */
@Component
public class GeoHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Value("${fxg.config.aMap.key}")
	private String aMapKey;//高德地图 key
	@Value("${fxg.config.bMap.key}")
	private String bMapKey;//百度地图 key

	@Autowired
	private CommunityMapper communityMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 调用高德api获取小区经纬度
	 *
	 * @return
	 */
	public Integer updateLocation() {

		log.info("更新小区城市名、区县名、街区名-开始");
		int upCount = communityMapper.updateCityName();
		log.info("更新小区城市名、区县名、街区名-结束，共更新{}个小区", upCount);
		RestTemplate restTemplate = new RestTemplate();
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(
				"select id,name,city_name,county_name from community where address is null limit 10000");
		int totalNum = 0;
		while (rowSet.next()) {
			Integer id = rowSet.getInt("id");
			String name = rowSet.getString("name");
			String cityName = rowSet.getString("city_name");

			MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
			queryParams.set("key", aMapKey);
			queryParams.set("address", cityName + name);
			queryParams.set("city", cityName);
			URI uri = UriComponentsBuilder.fromHttpUrl("https://restapi.amap.com/v3/geocode/geo")
										  .queryParams(queryParams)
										  .build()
										  .toUri();
			try {
				JsonNode jsonNode = restTemplate.postForObject(uri, null, JsonNode.class);
				JsonNode geocodes = jsonNode.get("geocodes");
				JsonNode geo = geocodes.get(0);
				String location = geo.asText("location");
				String address = geo.asText("formatted_address");
				String level = geo.asText("level");
				if (!"兴趣点".equals(level)) {
					log.error("定位失败，level:{},id:{},name:{}", level, id, name);
					continue;
				}
				String[] split = location.split(",");
				int count = communityMapper.updateLocation(id, new BigDecimal(split[0]).setScale(6, RoundingMode.HALF_UP),
														   new BigDecimal(split[1]).setScale(6, RoundingMode.HALF_UP), address);
				log.info("更新位置信息成功，{}，{}", cityName, name);
				totalNum += count;
			} catch (Exception e) {
				log.error("更新高德位置信息失败，id:{},name:{}", id, name);
				e.printStackTrace();
			}
		}
		return totalNum;
	}

	/**
	 * 将高德经纬度转换成百度经纬度
	 *
	 * @return
	 */
	public Integer upToBaiduLocation() {
		RestTemplate restTemplate = new RestTemplate();
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(
				"select id,longitude,latitude from community where longitude2 is null and longitude is not null limit 10000");
		int totalNum = 0;
		while (rowSet.next()) {
			Integer id = rowSet.getInt("id");
			String longitude = rowSet.getString("longitude");
			String latitude = rowSet.getString("latitude");

			MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
			queryParams.set("ak", bMapKey);
			queryParams.set("from", "3");
			queryParams.set("to", "5");
			queryParams.set("coords", longitude+","+latitude);
			URI uri = UriComponentsBuilder.fromHttpUrl("http://api.map.baidu.com/geoconv/v1/")
										  .queryParams(queryParams)
										  .build()
										  .toUri();
			try {

				JsonNode jsonNode = restTemplate.postForObject(uri, null, JsonNode.class);
				JsonNode results = jsonNode.get("result");
				JsonNode result = results.get(0);
				String longitude2 = result.asText("x");
				String latitude2 = result.asText("y");

				int count = communityMapper.upToBaiduLocation(id, new BigDecimal(longitude2), new BigDecimal(latitude2));
				log.info("更新位置信息成功，id:{},longitude:{},latitude:{},longitude2:{},latitude2{}", id, longitude, latitude, longitude2,
						 latitude2);
				totalNum += count;
			} catch (Exception e) {
				log.error("更新百度位置信息失败，id:{}", id);
				e.printStackTrace();
			}
		}
		return totalNum;
	}
}
