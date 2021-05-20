package com.fxg.house.viewer.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fxg.house.viewer.api.HttpResult;
import com.fxg.house.viewer.api.HttpStatus;
import com.fxg.house.viewer.entity.CountyInitRecord;
import com.fxg.house.viewer.entity.WatchCommunity;
import com.fxg.house.viewer.service.CountyInitRecordService;
import com.fxg.house.viewer.service.WatchCommunityService;
import com.fxg.house.viewer.spider.handler.InitCountyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 需要admin或者user权限的接口统一放到这个controller里
 */

@RestController
@RequestMapping("/auth")
public class AuthHouseController {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private CountyInitRecordService countyInitRecordService;
	@Autowired
	private InitCountyHandler initCountyHandler;
	@Autowired
	private WatchCommunityService watchCommunityService;

	/**
	 * 查询初始化记录
	 */
	@GetMapping("/user/queryInitPage")
	public HttpResult<Page<CountyInitRecord>> queryInitPage(
			@RequestParam(defaultValue = "1") Integer pageIndex,
			@RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(required = false) String cityName) {
		Page<CountyInitRecord> result = countyInitRecordService.queryInitPage(pageIndex, pageSize, cityName);
		return new HttpResult<>(HttpStatus.OK, "ok", result);
	}

	/**
	 * 新增待初始化区县
	 */
	@PostMapping(value = "/admin/addInitRecord", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public HttpResult<Boolean> addInitRecord(@RequestBody CountyInitRecord data) {
		Boolean result = countyInitRecordService.add(data);
		return new HttpResult<>(HttpStatus.OK, "ok", result);
	}

	/**
	 * 更新区县内小区
	 */
	@PostMapping(value = "/admin/updateCommunity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public HttpResult<Void> updateCommunity(@RequestParam Integer id) {
		initCountyHandler.initOrUpdateCountyCommunity(id);
		return new HttpResult<>(HttpStatus.OK, "ok");
	}

	/**
	 * 更新区县内成交记录
	 */
	@PostMapping(value = "/admin/updateDealHistory", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public HttpResult<Void> updateDealHistory(@RequestParam Integer id) {
		initCountyHandler.initOrUpdateCountyDealHistory(id);
		return new HttpResult<>(HttpStatus.OK, "ok");
	}

	private CountyInitRecord getAndCheckRecord(@RequestParam Integer id) {
		CountyInitRecord record = initCountyHandler.getById(id);
		Assert.isTrue(!record.getIsPressing(), "该区县正在处理中，请稍后重试");
		Objects.requireNonNull(record, "未查询到区县记录");
		return record;
	}

	/**
	 * 关注小区
	 */
	@PostMapping(value = "/user/watchCommunity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public HttpResult<WatchCommunity> watchCommunity(@RequestBody WatchCommunity data) {
		WatchCommunity add = watchCommunityService.add(data);
		return new HttpResult<>(HttpStatus.OK, "ok", add);
	}

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public HttpResult<String> login(
			@RequestParam(defaultValue = "1") Integer pageIndex,
			@RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(required = false) String cityName) {
		return new HttpResult<>(HttpStatus.OK, "ok", "result");
	}
}
