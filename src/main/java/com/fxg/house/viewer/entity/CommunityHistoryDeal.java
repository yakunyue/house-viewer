package com.fxg.house.viewer.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 成交记录
 */
@TableName(value = "community_history_deal")
public class CommunityHistoryDeal implements Serializable {

	private static final long serialVersionUID = 7913366921972143958L;

	private Integer id;

	private Integer communityId;

	private String lianjiaCommunityId;

	private String lianjiaHouseId;

	private String faceTo;

	private String model;

	private BigDecimal area;

	private String fitmentSituation;

	private String floorType;

	private Integer totalFloorNum;

	private Integer completionYear;

	private String buildingType;

	private BigDecimal dealUnitPrice;

	private String fiveYears;

	private BigDecimal listPrice;

	private LocalDate dealDate;

	private Integer dealMonth;

	private Integer dealYear;

	private Integer dealCycle;

	private BigDecimal dealPrice;

	private String cityCode;

	private String countyCode;

	private String streetCode;

	private LocalDateTime createTime;

	private LocalDateTime lastTime;

	private LocalDate listDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public String getLianjiaCommunityId() {
		return lianjiaCommunityId;
	}

	public void setLianjiaCommunityId(String lianjiaCommunityId) {
		this.lianjiaCommunityId = lianjiaCommunityId;
	}

	public String getLianjiaHouseId() {
		return lianjiaHouseId;
	}

	public void setLianjiaHouseId(String lianjiaHouseId) {
		this.lianjiaHouseId = lianjiaHouseId;
	}

	public String getFaceTo() {
		return faceTo;
	}

	public void setFaceTo(String faceTo) {
		this.faceTo = faceTo;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public String getFitmentSituation() {
		return fitmentSituation;
	}

	public void setFitmentSituation(String fitmentSituation) {
		this.fitmentSituation = fitmentSituation;
	}

	public String getFloorType() {
		return floorType;
	}

	public void setFloorType(String floorType) {
		this.floorType = floorType;
	}

	public Integer getTotalFloorNum() {
		return totalFloorNum;
	}

	public void setTotalFloorNum(Integer totalFloorNum) {
		this.totalFloorNum = totalFloorNum;
	}

	public Integer getCompletionYear() {
		return completionYear;
	}

	public void setCompletionYear(Integer completionYear) {
		this.completionYear = completionYear;
	}

	public String getBuildingType() {
		return buildingType;
	}

	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}

	public BigDecimal getDealUnitPrice() {
		return dealUnitPrice;
	}

	public void setDealUnitPrice(BigDecimal dealUnitPrice) {
		this.dealUnitPrice = dealUnitPrice;
	}

	public String getFiveYears() {
		return fiveYears;
	}

	public void setFiveYears(String fiveYears) {
		this.fiveYears = fiveYears;
	}

	public BigDecimal getListPrice() {
		return listPrice;
	}

	public void setListPrice(BigDecimal listPrice) {
		this.listPrice = listPrice;
	}

	public LocalDate getDealDate() {
		return dealDate;
	}

	public void setDealDate(LocalDate dealDate) {
		this.dealDate = dealDate;
	}

	public Integer getDealMonth() {
		return dealMonth;
	}

	public void setDealMonth(Integer dealMonth) {
		this.dealMonth = dealMonth;
	}

	public Integer getDealYear() {
		return dealYear;
	}

	public void setDealYear(Integer dealYear) {
		this.dealYear = dealYear;
	}

	public Integer getDealCycle() {
		return dealCycle;
	}

	public void setDealCycle(Integer dealCycle) {
		this.dealCycle = dealCycle;
	}

	public BigDecimal getDealPrice() {
		return dealPrice;
	}

	public void setDealPrice(BigDecimal dealPrice) {
		this.dealPrice = dealPrice;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getStreetCode() {
		return streetCode;
	}

	public void setStreetCode(String streetCode) {
		this.streetCode = streetCode;
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

	public LocalDate getListDate() {
		return listDate;
	}

	public void setListDate(LocalDate listDate) {
		this.listDate = listDate;
	}
}
