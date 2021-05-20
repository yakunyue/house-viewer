package com.fxg.house.viewer.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 小区关注记录
 */
@TableName(value = "watch_community")
public class WatchCommunity implements Serializable {

	private static final long serialVersionUID = 7789076597038580376L;

	private Integer id;

	private Integer communityId;

	private String lianjiaCommunityId;

	private Integer userId;

	private String cityCode;

	private String countyCode;

	private String streetCode;

	private LocalDateTime createTime;

	private LocalDateTime lastTime;

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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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
}
