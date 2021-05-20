package com.fxg.house.viewer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 小区
 */
@TableName(value = "community")
public class Community implements Serializable {

	private static final long serialVersionUID = 6451606003535617099L;

	private Integer id;

	private String name;

	private Integer ninetyDaysDealNum;

	private Integer dealNum;

	private Integer onRentNum;

	private Integer onSaleNum;

	private Integer completionYear;

	private BigDecimal referencePrice;

	private String lianjiaId;

	private Integer pageNo;

	private String buildingType;

	private String propertyFee;

	private String propertyCompany;

	private String developCompany;

	private Integer buildingNum;

	private Integer houseNum;

	private String cityCode;

	private String countyCode;

	private String streetCode;

	private LocalDateTime createTime;

	private LocalDateTime lastTime;

	private Integer updateDay;

	private Integer updateHour;

	private Boolean isWatched;

	private BigDecimal longitude;

	private BigDecimal latitude;

	private String address;

	private BigDecimal longitude2;

	private BigDecimal latitude2;

	@TableField(exist = false)
	private Street street;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNinetyDaysDealNum() {
		return ninetyDaysDealNum;
	}

	public void setNinetyDaysDealNum(Integer ninetyDaysDealNum) {
		this.ninetyDaysDealNum = ninetyDaysDealNum;
	}

	public Integer getDealNum() {
		return dealNum;
	}

	public void setDealNum(Integer dealNum) {
		this.dealNum = dealNum;
	}

	public Integer getOnRentNum() {
		return onRentNum;
	}

	public void setOnRentNum(Integer onRentNum) {
		this.onRentNum = onRentNum;
	}

	public Integer getOnSaleNum() {
		return onSaleNum;
	}

	public void setOnSaleNum(Integer onSaleNum) {
		this.onSaleNum = onSaleNum;
	}

	public Integer getCompletionYear() {
		return completionYear;
	}

	public void setCompletionYear(Integer completionYear) {
		this.completionYear = completionYear;
	}

	public BigDecimal getReferencePrice() {
		return referencePrice;
	}

	public void setReferencePrice(BigDecimal referencePrice) {
		this.referencePrice = referencePrice;
	}

	public String getLianjiaId() {
		return lianjiaId;
	}

	public void setLianjiaId(String lianjiaId) {
		this.lianjiaId = lianjiaId;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public String getBuildingType() {
		return buildingType;
	}

	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}

	public String getPropertyFee() {
		return propertyFee;
	}

	public void setPropertyFee(String propertyFee) {
		this.propertyFee = propertyFee;
	}

	public String getPropertyCompany() {
		return propertyCompany;
	}

	public void setPropertyCompany(String propertyCompany) {
		this.propertyCompany = propertyCompany;
	}

	public String getDevelopCompany() {
		return developCompany;
	}

	public void setDevelopCompany(String developCompany) {
		this.developCompany = developCompany;
	}

	public Integer getBuildingNum() {
		return buildingNum;
	}

	public void setBuildingNum(Integer buildingNum) {
		this.buildingNum = buildingNum;
	}

	public Integer getHouseNum() {
		return houseNum;
	}

	public void setHouseNum(Integer houseNum) {
		this.houseNum = houseNum;
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

	public Integer getUpdateDay() {
		return updateDay;
	}

	public void setUpdateDay(Integer updateDay) {
		this.updateDay = updateDay;
	}

	public Integer getUpdateHour() {
		return updateHour;
	}

	public void setUpdateHour(Integer updateHour) {
		this.updateHour = updateHour;
	}

	public Boolean getWatched() {
		return isWatched;
	}

	public void setWatched(Boolean watched) {
		isWatched = watched;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getLongitude2() {
		return longitude2;
	}

	public void setLongitude2(BigDecimal longitude2) {
		this.longitude2 = longitude2;
	}

	public BigDecimal getLatitude2() {
		return latitude2;
	}

	public void setLatitude2(BigDecimal latitude2) {
		this.latitude2 = latitude2;
	}

	public Street getStreet() {
		return street;
	}

	public void setStreet(Street street) {
		this.street = street;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Community community = (Community) o;
		return Objects.equals(name, community.name) && Objects.equals(ninetyDaysDealNum, community.ninetyDaysDealNum) && Objects.equals(
				onRentNum, community.onRentNum) && Objects.equals(onSaleNum, community.onSaleNum) && Objects.equals(completionYear,
																													community.completionYear)
				&& Objects.equals(referencePrice, community.referencePrice) && Objects.equals(lianjiaId, community.lianjiaId)
				&& Objects.equals(buildingType, community.buildingType) && Objects.equals(propertyFee, community.propertyFee)
				&& Objects.equals(propertyCompany, community.propertyCompany) && Objects.equals(developCompany, community.developCompany)
				&& Objects.equals(buildingNum, community.buildingNum) && Objects.equals(houseNum, community.houseNum) && Objects.equals(
				cityCode, community.cityCode) && Objects.equals(countyCode, community.countyCode) && Objects.equals(streetCode,
																													community.streetCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, ninetyDaysDealNum, onRentNum, onSaleNum, completionYear, referencePrice, lianjiaId, buildingType,
							propertyFee, propertyCompany, developCompany, buildingNum, houseNum, cityCode, countyCode, streetCode);
	}
}
