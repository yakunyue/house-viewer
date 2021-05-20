package com.fxg.house.viewer.spider.parser;

import com.fxg.house.viewer.entity.County;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class CountyHtmlParser implements AbstractHtmlParser<County> {

	private Logger log = LoggerFactory.getLogger(this.getClass()
														 .getName());

	@Override
	public County pars(String url) {
		throw new RuntimeException("pars方法未实现");
	}


	@Override
	public List<County> parsList(String url) {

		List<County> countyList = new ArrayList();
		Document document = null;
		try {
			document = Jsoup.connect(url)
					.get();
			Elements elements = document.select("div[data-role]")
					.first()
					.select("a[href]");
			Iterator<Element> iterator = elements.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				Element aTag = iterator.next();
				String href = aTag.attr("href");
				String code = this.parseCounty(href);
				String countyName = aTag.text()
						.trim();
				County county = new County();
				county.setName(countyName);
				county.setCode(code);
				countyList.add(county);
				log.info("第{}个区县，county:{}", i, county);
				i++;
			}
		} catch (IOException e) {
			// 部分地区没有链家二手房，所以页面会找不到，
			log.error("解析 county 页面发生错误，url：{},error:{}", url, e.getMessage());
		}
		return countyList;
	}

	private String parseCounty(String str) {
		// /ershoufang/daguanqu/
		String result = null;
		try {
			String s1 = str.replace("ershoufang", "");
			result = s1.replace("/", "");
		} catch (Exception e) {
			log.error("解析区县拼音失败,str:{},error:{}", str, e.getMessage());
		}
		return result;
	}
}
