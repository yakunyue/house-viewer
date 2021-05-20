package com.fxg.house.viewer.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 成交记录返回结果封装
 */
public class HistoryDealPositionDTO implements Serializable {


	private static final long serialVersionUID = -6141233552343913633L;

	private Integer id;

	private BigDecimal dealUnitPrice;

	private BigDecimal listPrice;

	private BigDecimal dealPrice;

	private BigDecimal longitude;

	private BigDecimal latitude;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getDealUnitPrice() {
		return dealUnitPrice;
	}

	public void setDealUnitPrice(BigDecimal dealUnitPrice) {
		this.dealUnitPrice = dealUnitPrice;
	}

	public BigDecimal getListPrice() {
		return listPrice;
	}

	public void setListPrice(BigDecimal listPrice) {
		this.listPrice = listPrice;
	}

	public BigDecimal getDealPrice() {
		return dealPrice;
	}

	public void setDealPrice(BigDecimal dealPrice) {
		this.dealPrice = dealPrice;
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
}
