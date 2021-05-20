package com.fxg.house.viewer.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fxg.house.viewer.api.HttpResult;
import com.fxg.house.viewer.api.HttpStatus;
import com.fxg.house.viewer.entity.Community;
import com.fxg.house.viewer.service.CommunityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 小区相关查询接口
 */

@RestController
@RequestMapping("/community")
public class CommunityController {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private CommunityService communityService;


	/**
	 * 查询小区列表
	 */
	@GetMapping("/fuzzy/page")
	public HttpResult<Page<Community>> queryFuzzyPage(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize, @RequestParam(required = false) String cityCode,
			@RequestParam(required = false) String countyCode, @RequestParam(required = false) String streetCode,
			@RequestParam(required = false) String communityName) {
		Page<Community> result = communityService.fuzzyPage(cityCode, countyCode, streetCode, communityName, pageIndex, pageSize);
		return new HttpResult<>(HttpStatus.OK, "ok", result);
	}

	/**
	 * 查询小区列表
	 */
	@GetMapping("/fuzzy/list")
	public HttpResult<List<Community>> queryFuzzyList(@RequestParam(required = false) String cityCode,
			@RequestParam(required = false) String countyCode, @RequestParam(required = false) String streetCode,
			@RequestParam(required = false) String communityName) {
		List<Community> result = communityService.fuzzyList(cityCode, countyCode, streetCode, communityName);
		return new HttpResult<>(HttpStatus.OK, "ok", result);
	}

	//	/**
	//	 * 更新高德位置信息
	//	 */
	//	@PostMapping(value = "/updateLocation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	//	public HttpResult<Integer> updateLocation() {
	//		Integer result = communityService.updateLocation();
	//		return new HttpResult<>(HttpStatus.OK, "ok", result);
	//	}
	//
	//	/**
	//	 * 更新百度位置信息
	//	 */
	//	@PostMapping(value = "/upToBaiduLocation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	//	public HttpResult<Integer> upToBaiduLocation() {
	//		Integer result = communityService.upToBaiduLocation();
	//		return new HttpResult<>(HttpStatus.OK, "ok", result);
	//	}
}
