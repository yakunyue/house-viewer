package com.fxg.house.viewer.enums;

public enum Gender implements BaseEnum {

	MALE(0, 1, "MALE", "男"),
	WOMAN(0, 2, "WOMAN", "女"),
	SECRET(0, 3, "SECRET", "保密");

	private Integer id;
	private Integer value;
	private String code;
	private String name;

	Gender(Integer id, Integer value, String code, String name) {
		this.id = id;
		this.value = value;
		this.code = code;
		this.name = name;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Integer getValue() {
		return this.value;
	}

	public String getCode() {
		return this.code;
	}
}
