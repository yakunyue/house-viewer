package com.fxg.house.viewer.spider.parser;

import com.fxg.house.viewer.entity.Street;
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
public class StreetHtmlParser implements AbstractHtmlParser<Street> {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public Street pars(String url) {
		throw new RuntimeException("pars方法未实现");
	}


	@Override
	public List<Street> parsList(String url) {
		List<Street> streetList =  new ArrayList();
		Document document;
		try {
			document = Jsoup.connect(url).get();
			Elements elements = document.select("div[data-role]")
										.first()
										.child(1)
										.select("a[href]");
			Iterator<Element> iterator = elements.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				Element aTag = iterator.next();
				String href = aTag.attr("href");
				String code = this.parseStreet(href);
				String name = aTag.text().trim();

				Street street = new Street();
				street.setName(name);
				street.setCode(code);
				street.setUpdateDay((int) (Math.random() * 7) + 1);//这个字段分配定时任务用的，现在没用了
				streetList.add(street);
				log.info("第{}个街区，street:{}", i, street);
				i++;
			}
		} catch (IOException e) {
			log.error("解析 street 页面发生错误，url：{},error:{}", url, e.getMessage());
			e.printStackTrace();
		}
		return streetList;
	}

	private String parseStreet(String str) {
		// /xiaoqu/taoranting1/
		String result = null;
		try {
			String s1 = str.replace("xiaoqu", "");
			result = s1.replace("/", "");
		} catch (Exception e) {
			log.error("解析街区拼音失败,str:{},error:{}", str, e.getMessage());
		}
		return result;
	}
}
