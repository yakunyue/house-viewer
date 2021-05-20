package com.fxg.house.viewer.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 省
 */
@TableName(value = "province")
public class Province implements Serializable {

	private static final long serialVersionUID = -3702802042252495465L;

	private Integer id;

	private String name;

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
}
