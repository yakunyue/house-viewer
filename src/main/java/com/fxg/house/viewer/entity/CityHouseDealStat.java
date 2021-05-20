package com.fxg.house.viewer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 城市房产成交统计
 */
@TableName(value = "city_house_deal_stat")
public class CityHouseDealStat implements Serializable {

	private static final long serialVersionUID = 7913366921072143958L;

	private Integer id;

	private Integer dealYear;

	private Integer dealMonth;

	private String cityName;

	private String cityCode;

	private String countyName;

	private String countyCode;


	private Integer dealCount;
	private BigDecimal dealArea;
	private BigDecimal dealAvgPrice;


	private Integer secondCount;
	private BigDecimal secondArea;
	private BigDecimal secondAvgPrice;


	private LocalDateTime createTime;

	private LocalDateTime lastTime;

	@TableField(exist = false)
	private String yearMonth;

	public String getYearMonth() {
		String dealMonthStr = this.dealMonth.toString();

		return this.dealYear + "-" + (dealMonthStr.length() == 2 ? dealMonthStr : "0" + dealMonthStr);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public Integer getDealCount() {
		return dealCount;
	}

	public void setDealCount(Integer dealCount) {
		this.dealCount = dealCount;
	}

	public BigDecimal getDealArea() {
		return dealArea;
	}

	public void setDealArea(BigDecimal dealArea) {
		this.dealArea = dealArea;
	}

	public BigDecimal getDealAvgPrice() {
		return dealAvgPrice;
	}

	public void setDealAvgPrice(BigDecimal dealAvgPrice) {
		this.dealAvgPrice = dealAvgPrice;
	}

	public Integer getSecondCount() {
		return secondCount;
	}

	public void setSecondCount(Integer secondCount) {
		this.secondCount = secondCount;
	}

	public BigDecimal getSecondArea() {
		return secondArea;
	}

	public void setSecondArea(BigDecimal secondArea) {
		this.secondArea = secondArea;
	}

	public BigDecimal getSecondAvgPrice() {
		return secondAvgPrice;
	}

	public void setSecondAvgPrice(BigDecimal secondAvgPrice) {
		this.secondAvgPrice = secondAvgPrice;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getLastTime() {
		return lastTime;
	}

	public void setLastTime(LocalDateTime lastTime) {
		this.lastTime = lastTime;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
}
