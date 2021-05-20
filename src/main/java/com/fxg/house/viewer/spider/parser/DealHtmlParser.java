package com.fxg.house.viewer.spider.parser;

import com.fxg.house.viewer.entity.CommunityHistoryDeal;
import com.fxg.house.viewer.utils.StringHandler;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Component
public class DealHtmlParser implements AbstractHtmlParser<CommunityHistoryDeal> {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public CommunityHistoryDeal pars(String url) {
		CommunityHistoryDeal deal = new CommunityHistoryDeal();
		try {

			Document document = getDocument(url);
			String dealPriceText = document.select("div.price")
										   .select("i")
										   .text()
										   .trim();
			String listPriceText = "";
			String dealCycleText = "";
			LocalDate listDate = null;
			Element msgElement = document.select("div.msg")
										 .first();
			if (Objects.nonNull(msgElement)) {
				Elements spans = msgElement.select("span");
				if (spans.size() >= 2) {
					String text = StringHandler.matchNumber(spans.get(0).text()).trim();
					if (!StringUtils.isEmpty(text.trim())) {
						listPriceText = text;
					}
					dealCycleText = StringHandler.matchNumber(spans.get(1).text()).trim();
				}
			}
			Iterator<Element> transItems = document.select("div.transaction")
												   .select("li")
												   .iterator();
			while (transItems.hasNext()) {
				Element el = transItems.next();
				String text = el.text();
				if (text.contains("挂牌时间")) {
					listDate = StringHandler.dateFormatter(text.replace("挂牌时间", "").trim());
				}
			}

			deal.setDealPrice(dealPriceDetail(dealPriceText));
			deal.setDealCycle(dealCycleDetail(dealCycleText));
			deal.setListDate(listDate);
			//没有挂牌价就那成交价当挂牌价
			if (StringUtils.isEmpty(listPriceText)) {
				deal.setListPrice(dealPriceDetail(dealPriceText));
			} else {
				deal.setListPrice(listPriceDetail(listPriceText));
			}

		} catch (Exception e) {

		}
		return deal;
	}

	private Document getDocument(String url) throws IOException {

		Document document = null;
		try {
			document = Jsoup.connect(url)
							.userAgent("Mozilla")
							.get();
		} catch (HttpStatusException exception) {
			document = Jsoup.connect(url.replace("lianjia", "ke"))
							.userAgent("Mozilla")
							.get();
		}
		return document;
	}

	private BigDecimal listPriceDetail(String str) {
		// 挂牌175万
		str = StringHandler.matchNumber(str);
		BigDecimal result = null;
		try {
			result = new BigDecimal(str).setScale(2, RoundingMode.HALF_UP);
		} catch (Exception e) {
			log.warn("解析成交挂牌价失败，str：{}", str);
		}
		return result;
	}

	private Integer dealCycleDetail(String str) {
		// 成交周期126天
		str = StringHandler.matchNumber(str);
		Integer result = null;
		try {
			result = Integer.parseInt(str);
		} catch (Exception e) {
			log.warn("解析成交周期失败，str：{}", str);
		}
		return result;
	}

	private BigDecimal dealPriceDetail(String str) {
		// 可能是区间
		BigDecimal result = null;
		try {
			if (str.contains("-")) {
				String[] split = str.split("-");
				result = new BigDecimal(split[1].trim());
			} else {
				result = new BigDecimal(str);
			}
		} catch (Exception e) {
			log.warn("解析成交价失败，str：{}", str);
		}
		return result;
	}


	@Override
	public List<CommunityHistoryDeal> parsList(String url) {
		List<CommunityHistoryDeal> list = new ArrayList();
		try {
			Document document = Jsoup.connect(url)
									 .get();
			Elements items = document.select("ul.listContent")
									 .select("li");
			Iterator<Element> iterator = items.iterator();
			while (iterator.hasNext()) {
				Element el = iterator.next();
				CommunityHistoryDeal deal = new CommunityHistoryDeal();
				deal.setLianjiaHouseId(lianjiaHouseId(el.select("a[href].img")
														.attr("href")));

				// 英国宫一期(固安) 3室2厅 109.67平米
				String title = el.select("div.title")
								 .text();
				BigDecimal area = parseArea(title);
				if (Objects.isNull(area) || BigDecimal.ZERO.equals(area.setScale(0, RoundingMode.HALF_UP))) {
					//面积没有就不处理了
					log.warn("解析到一个没有面积的成交记录，放弃处理，连接地址：{}", url);
					continue;
				}
				// 南 北 | 毛坯
				String houseInfo = el.select("div.address")
									 .select("div.houseInfo")
									 .text();
				// 中楼层(共13层) 2013年建板楼
				Elements positionInfo = el.select("div.flood")
										  .select("div.positionInfo");
				// 2020.01.21
				LocalDate dealDate = StringHandler.dateFormatter(el.select("div.address")
																   .select("div.dealDate")
																   .text());
				deal.setModel(parseModel(title));
				deal.setArea(area.setScale(2, RoundingMode.HALF_UP));
				deal.setFaceTo(parseAddress(houseInfo, true));
				deal.setFitmentSituation(parseAddress(houseInfo, false));
				deal.setDealDate(dealDate);
				deal.setDealMonth(dealDate.getMonthValue());
				deal.setDealYear(dealDate.getYear());
				deal.setFloorType(floorType(positionInfo.text()));
				deal.setTotalFloorNum(totalFloorNum(positionInfo.text()));
				//						.completionYear(completionYear(positionInfo.text()))建成年份失败概率过高，不再解析
				deal.setBuildingType(buildingType(positionInfo.text()));

				deal.setFiveYears(el.select("div.dealHouseInfo")
									.text());
				//总价和单价可能是区间，特殊判断下
				String totalPriceText = el.select("div.totalPrice")
										  .select("span")
										  .text()
										  .trim();
				String unitPriceText = el.select("div.unitPrice")
										 .select("span")
										 .text()
										 .trim();
				deal.setDealPrice(dealPrice(totalPriceText).setScale(2, RoundingMode.HALF_UP));
				deal.setDealUnitPrice(dealUnitPrice(unitPriceText).setScale(2, RoundingMode.HALF_UP));

				//成交周期可能没有，特殊判断下
				Element dealCycleSpan = el.select("span.dealCycleTxt")
										  .first();
				if (Objects.nonNull(dealCycleSpan)) {
					Elements children = dealCycleSpan.children();
					//如果有两个，第一个是挂牌价，第二个是成交周期
					if (children.size() == 2) {
						deal.setListPrice(listPrice(children.first()
															.text()));
						deal.setDealCycle(dealCycle(children.last()
															.text()));
					} else if (children.size() == 1) {
						//如果有一个，要判断下
						String text = children.first()
											  .text();
						if (text.contains("挂牌")) {
							deal.setListPrice(listPrice(text));
						} else if (text.contains("周期")) {
							deal.setDealCycle(dealCycle(text));
						}
					} else {
						//没有的话打个warning，等定时任务处理
						log.warn("成交记录的挂牌价和成交周期有问题，链接地址：{}", url);
					}
				}
				list.add(deal);
			}
		} catch (Exception e) {
			log.error("解析一页成交记录失败，error:{}，链接地址：{}", e.getMessage(), url);
			e.printStackTrace();
		}
		return list;
	}

	private String lianjiaHouseId(String str) {
		// https://lf.lianjia.com/chengjiao/101105772131.html
		int startIndex = str.lastIndexOf("/");
		int endIndex = str.lastIndexOf(".");
		String result = null;
		try {
			result = str.substring(startIndex + 1, endIndex);
		} catch (Exception e) {
			log.warn("解析房源编号失败");
		}
		return result;
	}

	private String parseModel(String str) {
		// 英国宫一期(固安) 3室2厅 109.67平米
		String[] split = str.split(" ");
		String result = null;
		try {
			result = split[1].trim();
		} catch (Exception e) {
			log.warn("解析房屋格局失败，str:{},error:{}", str, e.getMessage());
		}
		return result;
	}

	private BigDecimal parseArea(String str) {
		// 英国宫一期(固安) 3室2厅 109.67平米
		String[] split = str.split(" ");
		BigDecimal result = null;
		try {
			result = new BigDecimal(split[2].replace("平米", "")
											.trim());
		} catch (Exception e) {
			log.warn("解析房屋面积失败,str:{},error:{}", str, e.getMessage());
		}
		return result;
	}

	private String parseAddress(String str, Boolean parseFace) {
		// 南 北 | 毛坯
		String[] split = str.split("\\|");
		String result = null;
		try {
			if (parseFace) {
				result = split[0].trim();
			} else {
				result = split[1].trim();
			}
		} catch (Exception e) {
			log.warn("解析{}失败", parseFace ? "房屋朝向" : "装修情况");
		}
		return result;
	}

	private String floorType(String str) {
		//中楼层(共13层) 2013年建板楼
		int index = str.indexOf("(");
		String result = null;
		try {
			result = str.substring(0, index);
		} catch (Exception e) {
			log.warn("解析楼层类型失败，str：{}", str);
		}
		return result;
	}

	private Integer totalFloorNum(String str) {
		//中楼层(共13层) 2013年建板楼
		String[] split = str.split(" ");
		Integer result = null;
		try {
			result = Integer.parseInt(StringHandler.matchNumber(split[0]));
		} catch (Exception e) {
			log.warn("解析总楼层数失败，str：{}", str);
		}
		return result;
	}

	private Integer completionYear(String str) {
		//中楼层(共13层) 2013年建板楼
		String[] split = str.split(" ");
		Integer result = null;
		try {
			result = Integer.parseInt(StringHandler.matchNumber(split[1]));
		} catch (Exception e) {
			log.warn("解析建成年份失败，str：{}", str);
		}
		return result;
	}

	private String buildingType(String str) {
		//中楼层(共13层) 2013年建板楼
		//顶层(共3层) 板塔结合
		int index = str.indexOf("建");
		if (index < 0) {
			index = str.indexOf(" ");
		}
		String result = null;
		try {
			result = str.substring(index + 1);
		} catch (Exception e) {
			log.warn("解析建筑类型失败，str：{}", str);
		}
		return result;
	}

	private BigDecimal listPrice(String str) {
		// 挂牌175万
		str = StringHandler.matchNumber(str);
		BigDecimal result = null;
		try {
			result = new BigDecimal(str).setScale(2, RoundingMode.HALF_UP);
		} catch (Exception e) {
			log.warn("解析成交挂牌价失败，str：{}", str);
		}
		return result;
	}

	private Integer dealCycle(String str) {
		// 成交周期126天
		str = StringHandler.matchNumber(str);
		Integer result = null;
		try {
			result = Integer.parseInt(str);
		} catch (Exception e) {
			log.warn("解析成交周期失败，str：{}", str);
		}
		return result;
	}

	private BigDecimal dealPrice(String str) {
		// 可能是区间
		BigDecimal result = null;
		try {
			if (str.contains("-")) {
				String[] split = str.split("-");
				result = new BigDecimal(split[1].trim());
			} else {
				result = new BigDecimal(str);
			}
		} catch (Exception e) {
			log.warn("解析成交价失败，str：{}", str);
		}
		return result;
	}

	private BigDecimal dealUnitPrice(String str) {
		//
		BigDecimal result = null;
		try {
			if (str.contains("-")) {
				String[] split = str.split("-");
				result = new BigDecimal(split[1].trim());
			} else {
				result = new BigDecimal(str);
			}
		} catch (Exception e) {
			log.warn("解析成交单价失败，str：{}", str);
		}
		return result;
	}
}
