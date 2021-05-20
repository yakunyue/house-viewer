package com.fxg.house.viewer.controller;

import com.fxg.house.viewer.api.HttpResult;
import com.fxg.house.viewer.api.HttpStatus;
import com.fxg.house.viewer.entity.City;
import com.fxg.house.viewer.entity.County;
import com.fxg.house.viewer.entity.Province;
import com.fxg.house.viewer.entity.Street;
import com.fxg.house.viewer.service.CityService;
import com.fxg.house.viewer.service.CountyService;
import com.fxg.house.viewer.service.ProvinceService;
import com.fxg.house.viewer.service.StreetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 需要admin或者user权限的接口统一放到这个controller里
 */

@RestController
@RequestMapping("/county")
public class CountyController {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private CountyService countyService;
	@Autowired
	private CityService cityService;
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private StreetService streetService;


	/**
	 * 查询省列表
	 */
	@GetMapping("/queryProvinceList")
	public HttpResult<List<Province>> queryProvinceList() {
		List<Province> result = provinceService.list();
		return new HttpResult<>(HttpStatus.OK, "ok", result);
	}

	/**
	 * 查询市列表
	 */
	@GetMapping("/queryCityList")
	public HttpResult<List<City>> queryCityList(@RequestParam(required = false) Integer provinceId) {
		List<City> result = cityService.list(provinceId);
		return new HttpResult<>(HttpStatus.OK, "ok", result);
	}

	/**
	 * 查询区县列表
	 */
	@GetMapping("/queryCountyList")
	public HttpResult<List<County>> queryCountyList(@RequestParam(required = false) Integer cityId) {
		List<County> result = countyService.selectCountByCityId(cityId);
		return new HttpResult<>(HttpStatus.OK, "ok", result);
	}

	/**
	 * 查询有初始化记录的city列表
	 */
	@GetMapping("/queryInitCityList")
	public HttpResult<List<City>> queryInitCityList(@RequestParam(required = false) String cityName) {
		List<City> result = cityService.queryInitCityList(cityName);
		return new HttpResult<>(HttpStatus.OK, "ok", result);
	}

	/**
	 * 查询有初始化记录的county列表
	 */
	@GetMapping("/queryInitCountyList")
	public HttpResult<List<County>> queryInitCountyList(@RequestParam(required = false) String cityCode) {
		List<County> result = countyService.queryInitCountyList(cityCode);
		return new HttpResult<>(HttpStatus.OK, "ok", result);
	}

	/**
	 * 查询有初始化记录的street列表
	 */
	@GetMapping("/queryInitStreetList")
	public HttpResult<List<Street>> queryInitStreetList(@RequestParam String countyCode) {
		List<Street> result = streetService.queryByCountyId(countyCode);
		return new HttpResult<>(HttpStatus.OK, "ok", result);
	}

//	/**
//	 * 标记重复的街区
//	 */
//	@GetMapping("/upstreet")
//	public HttpResult<Void> upstreet() {
//		streetService.upStreet();
//		return new HttpResult<>(HttpStatus.OK, "ok");
//	}
}
