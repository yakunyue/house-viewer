package com.fxg.house.viewer.dto;

import java.io.Serializable;

/**
 * 返回价格变动的beal
 */
public class PriceChangeDTO implements Serializable {

	private static final long serialVersionUID = 3337672443055698036L;

	private String cityName;

	//	private String cityCode;

	private String countyName;

	//	private String countyCode;

	private String streetName;

	//  private String streetCode;

	private Integer dealYear;

	private Integer dealMonth;

	private Integer dealNum;

	private Integer avgArea;

	private Integer listPriceAvg;

	private Integer dealPriceAvg;

	private String yearMonth;

	public String getYearMonth() {
		return dealYear + "-" + dealMonth;
	}


	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public Integer getDealYear() {
		return dealYear;
	}

	public void setDealYear(Integer dealYear) {
		this.dealYear = dealYear;
	}

	public Integer getDealMonth() {
		return dealMonth;
	}

	public void setDealMonth(Integer dealMonth) {
		this.dealMonth = dealMonth;
	}

	public Integer getDealNum() {
		return dealNum;
	}

	public void setDealNum(Integer dealNum) {
		this.dealNum = dealNum;
	}

	public Integer getAvgArea() {
		return avgArea;
	}

	public void setAvgArea(Integer avgArea) {
		this.avgArea = avgArea;
	}

	public Integer getListPriceAvg() {
		return listPriceAvg;
	}

	public void setListPriceAvg(Integer listPriceAvg) {
		this.listPriceAvg = listPriceAvg;
	}

	public Integer getDealPriceAvg() {
		return dealPriceAvg;
	}

	public void setDealPriceAvg(Integer dealPriceAvg) {
		this.dealPriceAvg = dealPriceAvg;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
}