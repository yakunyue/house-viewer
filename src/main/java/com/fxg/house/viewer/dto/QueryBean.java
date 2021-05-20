package com.fxg.house.viewer.dto;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author yxb
 * @since 2020/5/13
 */
public class QueryBean implements Serializable {

	private static final long serialVersionUID = 13594566542235292L;

	private Integer pageIndex = 1;

	private Integer pageSize = 10;

	private String cityCode;

	private String countyCode;

	private String streetCode;

	private String communityCode;

	private LocalDate fromDate;

	private LocalDate toDate;

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
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

	public String getCommunityCode() {
		return communityCode;
	}

	public void setCommunityCode(String communityCode) {
		this.communityCode = communityCode;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		QueryBean queryBean = (QueryBean) o;
		return Objects.equals(pageIndex, queryBean.pageIndex) && Objects.equals(pageSize, queryBean.pageSize) && Objects
				.equals(cityCode, queryBean.cityCode) && Objects.equals(countyCode, queryBean.countyCode)
				&& Objects.equals(streetCode, queryBean.streetCode) && Objects.equals(communityCode,
				queryBean.communityCode) && Objects.equals(fromDate, queryBean.fromDate) && Objects.equals(toDate,
				queryBean.toDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(pageIndex, pageSize, cityCode, countyCode, streetCode, communityCode, fromDate, toDate);
	}
}
