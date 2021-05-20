package com.fxg.house.viewer.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 街区
 */
@TableName(value = "street")
public class Street implements Serializable {

	private static final long serialVersionUID = 3981001666214743613L;

	private Integer id;

	private String name;

	private String code;

	private Integer countyId;

	private String countyName;

	private String countyCode;

	private Integer cityId;

	private String cityCode;

	private String cityName;

	private Integer provinceId;

	private String provinceName;

	private Integer updateDay;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getCountyId() {
		return countyId;
	}

	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
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

	public Integer getUpdateDay() {
		return updateDay;
	}

	public void setUpdateDay(Integer updateDay) {
		this.updateDay = updateDay;
	}
}