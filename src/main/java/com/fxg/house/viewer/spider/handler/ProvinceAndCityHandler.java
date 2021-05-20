package com.fxg.house.viewer.spider.handler;

import com.fxg.house.viewer.entity.City;
import com.fxg.house.viewer.entity.Province;
import com.fxg.house.viewer.mapper.CityMapper;
import com.fxg.house.viewer.mapper.ProvinceMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 解析出省、市信息存入mysql
 */
@Component
public class ProvinceAndCityHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	private static String cityListUrl = "https://www.lianjia.com/city/";

	@Autowired
	private CityMapper mapper;
	@Autowired
	private ProvinceMapper provinceMapper;

	@Transactional
	public Map<String, Integer> parse() throws Exception {
		Document document = Jsoup.connect(cityListUrl).get();
		Elements elements = document.select("ul.city_list_ul").select("div.city_province");
		Iterator<Element> iterator = elements.iterator();
		List<Province> provinceList =  new ArrayList();
		List<City> cityList =  new ArrayList();

		int i = 1;
		int j = 1;
		while (iterator.hasNext()) {
			log.info("+++++++++++开始解析第{}个省", i);
			Element provinceDiv = iterator.next();
			String provinceName = provinceDiv.select("div.city_list_tit").text().trim();
			Province province = new Province();
			province.setName(provinceName);
			log.info("+++++++++++解析成功，省名：{}", provinceName);
			provinceList.add(province);
			i++;
			Elements cityAList = provinceDiv.select("a");
			Iterator<Element> cityIt = cityAList.iterator();
			while (cityIt.hasNext()) {
				log.info("开始解析第{}个市", j);
				Element a = cityIt.next();
				String href = a.attr("href").trim();
				String cityName = a.text().trim();
				City city = new City();
				city.setProvinceName(provinceName);
				city.setName(cityName);
				city.setCode(parseCityCode(href));
				cityList.add(city);
				log.info("解析成功，市名：{}", cityName);
				j++;
			}
		}

		int cityNum = mapper.insertBatch(cityList);
		int provinceNum = provinceMapper.insertBatch(provinceList);
		HashMap<String, Integer> map = new HashMap<>();
		map.put("cityNum", cityNum);
		map.put("provinceNum", provinceNum);
		return map;
	}


	private String parseCityCode(String str) {
		// "https://bh.lianjia.com/"
		String result = null;
		try {
			String s = str.replace("https://", "");
			int indexOf = s.indexOf(".");
			result = s.substring(0, indexOf);
		} catch (Exception e) {
			log.error("解析cityCode失败,str:{},error:{}", str, e.getMessage());
		}
		return result;
	}
}
