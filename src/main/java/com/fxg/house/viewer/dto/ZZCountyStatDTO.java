package com.fxg.house.viewer.dto;

import java.io.Serializable;

/**
 * 郑州各区县对比数据封装
 */
public class ZZCountyStatDTO implements Serializable {

	private static final long serialVersionUID = 3337672443055678036L;

	private Integer dealYear;

	private Integer dealMonth;

	private Object eq;

	private Object gc;

	private Object hj;

	private Object js;

	private Object zy;

	private Object zd;

	private Object gx;

	private Object jk;

	private String yearMonth;

	public String getYearMonth() {
		return dealYear + "-" + dealMonth;
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

	public Object getEq() {
		return eq;
	}

	public void setEq(Object eq) {
		this.eq = eq;
	}

	public Object getGc() {
		return gc;
	}

	public void setGc(Object gc) {
		this.gc = gc;
	}

	public Object getHj() {
		return hj;
	}

	public void setHj(Object hj) {
		this.hj = hj;
	}

	public Object getJs() {
		return js;
	}

	public void setJs(Object js) {
		this.js = js;
	}

	public Object getZy() {
		return zy;
	}

	public void setZy(Object zy) {
		this.zy = zy;
	}

	public Object getZd() {
		return zd;
	}

	public void setZd(Object zd) {
		this.zd = zd;
	}

	public Object getGx() {
		return gx;
	}

	public void setGx(Object gx) {
		this.gx = gx;
	}

	public Object getJk() {
		return jk;
	}

	public void setJk(Object jk) {
		this.jk = jk;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
}