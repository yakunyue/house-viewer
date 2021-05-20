package com.fxg.house.viewer.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class StringHandler {

	static Pattern NUMBER_PATTERN = Pattern.compile("[\\d.]");

	static Pattern NOT_NUMBER_PATTERN = Pattern.compile("[^\\d.]");

	static DateTimeFormatter DATE_FORMATTER_HYPHEN = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	static DateTimeFormatter DATE_FORMATTER_DOT = DateTimeFormatter.ofPattern("yyyy.MM.dd");


	public static String matchNumber(String str) {
		//1999年建成
		return NOT_NUMBER_PATTERN.matcher(str)
				.replaceAll("");
	}

	public static String matchString(String str) {
		//1999年建成
		return NUMBER_PATTERN.matcher(str)
				.replaceAll("");
	}

	public static LocalDate dateFormatter(String str) {
		//2020.01.21或者2020.02
		LocalDate result = null;
		try {
			if (str.contains("-")) {
				if (str.length() == 7) {
					result = LocalDate.parse(str + "-15", DATE_FORMATTER_HYPHEN);
				} else {
					result = LocalDate.parse(str, DATE_FORMATTER_HYPHEN);
				}
			} else if (str.contains(".")) {
				if (str.length() == 7) {
					result = LocalDate.parse(str + ".15", DATE_FORMATTER_DOT);
				} else {
					result = LocalDate.parse(str, DATE_FORMATTER_DOT);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		BigDecimal b = new BigDecimal("0.00098").multiply(BigDecimal.valueOf(100));
		System.out.println(b);
		System.out.println(b.setScale(3, RoundingMode.HALF_UP));
		System.out.println(matchNumber("板塔结合 2500.33和"));
		System.out.println(matchNumber("https://bj.lianjia.com/xiaoqu/1110366393266085/"));
		System.out.println(matchString("199.9年建成"));
		System.out.println(dateFormatter("2020.01"));
		System.out.println(BigDecimal.ZERO.equals(new BigDecimal("0.00").setScale(0, RoundingMode.HALF_UP)));

	}
}
