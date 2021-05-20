package com.fxg.house.viewer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 区县初始化记录
 */
@TableName(value = "county_init_record")
public class CountyInitRecord implements Serializable {

	private static final long serialVersionUID = -3442801817260149742L;

	private Integer id;

	private Integer countyId;

	private String countyCode;

	private String countyName;

	private Integer cityId;

	private String cityCode;

	private String cityName;

	private Integer provinceId;

	private String provinceName;

	private Boolean isPressing;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime communityLastUpdateTime;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dealHistoryLastUpdateTime;

	private Integer communityNum;

	private Integer dealNum;

	private Integer houseNum;

	private LocalDateTime createTime;

	private LocalDateTime lastTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCountyId() {
		return countyId;
	}

	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Boolean getIsPressing() {
		return isPressing;
	}

	public void setIsPressing(Boolean pressing) {
		isPressing = pressing;
	}

	public LocalDateTime getCommunityLastUpdateTime() {
		return communityLastUpdateTime;
	}

	public void setCommunityLastUpdateTime(LocalDateTime communityLastUpdateTime) {
		this.communityLastUpdateTime = communityLastUpdateTime;
	}

	public LocalDateTime getDealHistoryLastUpdateTime() {
		return dealHistoryLastUpdateTime;
	}

	public void setDealHistoryLastUpdateTime(LocalDateTime dealHistoryLastUpdateTime) {
		this.dealHistoryLastUpdateTime = dealHistoryLastUpdateTime;
	}

	public Integer getCommunityNum() {
		return communityNum;
	}

	public void setCommunityNum(Integer communityNum) {
		this.communityNum = communityNum;
	}

	public Integer getDealNum() {
		return dealNum;
	}

	public void setDealNum(Integer dealNum) {
		this.dealNum = dealNum;
	}

	public Integer getHouseNum() {
		return houseNum;
	}

	public void setHouseNum(Integer houseNum) {
		this.houseNum = houseNum;
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
}