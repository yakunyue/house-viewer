package com.fxg.house.viewer.spider.parser;

import com.fxg.house.viewer.entity.Community;
import com.fxg.house.viewer.utils.StringHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class CommunityHtmlParser implements AbstractHtmlParser<Community> {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public Community pars(String url) throws IOException {
		Document document = Jsoup.connect(url).get();
		Elements items = document.select("div.xiaoquInfoItem");
		Iterator<Element> iterator = items.iterator();
		Community community = new Community();
		while (iterator.hasNext()) {
			Element el = iterator.next();
			Elements spans = el.select("span");
			switch (spans.first().text()) {
				case "建筑类型":
					community.setBuildingType(spans.last().text());
					break;
				case "物业费用":
					community.setPropertyFee(spans.last().text());
					break;
				case "物业公司":
					community.setPropertyCompany(spans.last().text());
					break;
				case "开发商":
					community.setDevelopCompany(spans.last().text());
					break;
				case "楼栋总数":
					community.setBuildingNum(buildingNum(spans.last().text()));
					break;
				case "房屋总数":
					community.setHouseNum(houseNum(spans.last().text()));
					break;
			}
		}
		return community;
	}

	private Integer buildingNum(String str) {
		//99栋
		str = StringHandler.matchNumber(str);
		Integer result = null;
		try {
			result = Integer.parseInt(str);
		} catch (Exception e) {
			logger.warn("解析楼栋总数失败");
		}
		return result;
	}

	private Integer houseNum(String str) {
		//99套
		str = StringHandler.matchNumber(str);
		Integer result = null;
		try {
			result = Integer.parseInt(str);
		} catch (Exception e) {
			logger.warn("解析房屋总数失败");
		}
		return result;
	}

	@Override
	public List<Community> parsList(String url) throws IOException {
		Document document = Jsoup.connect(url).get();
		Elements items = document.select("ul.listContent").select("li");
		Iterator<Element> iterator = items.iterator();
		List<Community> list = new ArrayList();
		while (iterator.hasNext()) {
			Element el = iterator.next();
			Community community = new Community();

			community.setName(el.select("div.title").text());
			community.setOnRentNum(onRentNum(el.select("div.houseInfo")
											   .select("a")
											   .last()
											   .text()));
			//					.completionYear(completionYear(el.select("div.positionInfo").text()))建成年份解析失败率高，不再解析
			//					.referencePrice(referencePrice(el.select("div.xiaoquListItemPrice").select("span").text()))参考价格解析失败率高，不再解析
			community.setOnSaleNum(Integer.parseInt(el.select("div.xiaoquListItemSellCount")
													  .select("span")
													  .text()));
			community.setLianjiaId(el.attr("data-id"));

			community.setUpdateDay((int) (Math.random() * 28) + 1);//这两个字段本来是分配定时任务执行时间的，现在不用了
			community.setUpdateHour((int) (Math.random() * 13) + 8);//这两个字段本来是分配定时任务执行时间的，现在不用了
			list.add(community);
		}
		return list;
	}

	private Integer onRentNum(String str) {
		//125套正在出租
		str = StringHandler.matchNumber(str);
		Integer result = null;
		try {
			result = Integer.parseInt(str);
		} catch (Exception e) {
			logger.warn("解析正在出租数失败");
		}
		return result;
	}

	private Integer completionYear(String str) {
		//1999年建成
		str = StringHandler.matchNumber(str);
		Integer result = null;
		try {
			result = Integer.parseInt(str);
		} catch (Exception e) {
			logger.warn("解析建成年份失败");
		}
		return result;
	}

	private BigDecimal referencePrice(String str) {
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
}
