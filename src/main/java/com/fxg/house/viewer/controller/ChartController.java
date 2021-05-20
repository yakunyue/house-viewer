package com.fxg.house.viewer.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fxg.house.viewer.api.HttpResult;
import com.fxg.house.viewer.api.HttpStatus;
import com.fxg.house.viewer.dto.HistoryDealPositionDTO;
import com.fxg.house.viewer.dto.PriceChangeDTO;
import com.fxg.house.viewer.dto.ZZCountyStatDTO;
import com.fxg.house.viewer.entity.CityHouseDealStat;
import com.fxg.house.viewer.service.CityHouseDealStatService;
import com.fxg.house.viewer.service.CommunityHistoryDealService;
import com.fxg.house.viewer.spider.handler.InitCountyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * 需要admin或者user权限的接口统一放到这个controller里
 */

@RestController
@RequestMapping("/chart")
public class ChartController {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private CommunityHistoryDealService communityHistoryDealService;
	@Autowired
	private InitCountyHandler initCountyHandler;

	/**
	 * 查询价格曲线数据
	 */
	@GetMapping("/priceChange")
	public HttpResult<List<PriceChangeDTO>> priceChange(@RequestParam(defaultValue = "bj") String cityCode,
			@RequestParam(required = false) String countyCode, @RequestParam(required = false) String streetCode,
			@RequestParam(required = false) String communityCode,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate) {
		if (Objects.nonNull(fromDate)) {
			fromDate = LocalDate.of(fromDate.getYear(), fromDate.getMonth(), 1);
		}
		List<PriceChangeDTO> result = communityHistoryDealService.queryPriceChangeUseCache(cityCode, countyCode,
				streetCode, communityCode, fromDate);
		return new HttpResult<>(HttpStatus.OK, "ok", result);
	}

	/**
	 * 查询成交记录位置信息
	 */
	@GetMapping("/dealPosition")
	public HttpResult<List<HistoryDealPositionDTO>> dealPosition(@RequestParam(defaultValue = "zz") String cityCode,
			@RequestParam(required = false) LocalDate fromDate, @RequestParam(required = false) LocalDate toDate) {
		if (Objects.isNull(fromDate)) {
			fromDate = LocalDate.now().minusDays(60);//默认查询60天成交
		}
		if (Objects.isNull(toDate)) {
			toDate = LocalDate.now();//默认查询60天成交
		}
		List<HistoryDealPositionDTO> result = communityHistoryDealService.queryDealPositionUseCache(cityCode, fromDate,
				toDate);
		return new HttpResult<>(HttpStatus.OK, "ok", result);
	}

	@Autowired
	private CityHouseDealStatService cityHouseDealStatService;

	/**
	 * 郑州房源成交统计
	 */
	@GetMapping("/zzHouseDealStat")
	public HttpResult<List<CityHouseDealStat>> zzHouseDealStat(@RequestParam String countyCode) {
		LambdaQueryWrapper<CityHouseDealStat> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CityHouseDealStat::getCountyCode, countyCode);
		List<CityHouseDealStat> result = cityHouseDealStatService.list(queryWrapper);
		return new HttpResult<>(HttpStatus.OK, "ok", result);
	}

	/**
	 * 郑州房源成交记录统计数据各区县对比
	 */
	@GetMapping("/zzCountyStat")
	public HttpResult<List<ZZCountyStatDTO>> zzCountyStat(@RequestParam String code) {
		List<ZZCountyStatDTO> result = cityHouseDealStatService.queryZZCountyStatDTOList(code);
		return new HttpResult<>(HttpStatus.OK, "ok", result);
	}

	/**
	 * 上月成交信息查询
	 */
	@GetMapping("/cityLastMonthDeal")
	public HttpResult<List<CityHouseDealStat>> cityLastMonthDeal(@RequestParam String cityCode) {

		List<CityHouseDealStat> list = cityHouseDealStatService.cityLastMonthDeal(cityCode);
		return new HttpResult<>(HttpStatus.OK, "ok", list);
	}
}
