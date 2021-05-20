package com.fxg.house.viewer.spider.handler;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxg.house.viewer.entity.CountyInitRecord;
import com.fxg.house.viewer.mapper.CountyInitRecordMapper;
import com.fxg.house.viewer.service.CountyInitRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 提供了增量更新小区和成交记录的入口
 * </p>
 *
 * @author yueyakun
 */
@Component
public class InitCountyHandler extends ServiceImpl<CountyInitRecordMapper, CountyInitRecord> {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	private static String LOG_MESSAGE = "初始化或更新区县数据,cityName:{},countyName{}--";

	@Autowired
	private CountyInitRecordMapper mapper;
	@Autowired
	private CommunityHandler communityHandler;
	@Autowired
	private DealHandler dealHandler;
	@Autowired
	private CountyInitRecordService initRecordService;

	/**
	 * 初始化或更新整个市的小区和成交记录
	 */
	public void initOrUpdateCityCommunityAndDealHistory(String cityCode) {
		List<CountyInitRecord> records = mapper.queryCountyInitRecodeByCityCode(cityCode);
		records.forEach(record->{
			this.initOrUpdateCountyCommunity(record.getId());
			this.initOrUpdateCountyDealHistory(record.getId());
		});
	}

	/**
	 * 初始化或更新整个市的小区
	 */
	public void initOrUpdateCityCommunity(String cityCode) {
		List<CountyInitRecord> records = mapper.queryCountyInitRecodeByCityCode(cityCode);
		records.forEach(record->this.initOrUpdateCountyCommunity(record.getId()));
	}

	/**
	 * 初始化或更新整个市的成交记录
	 */
	public void initOrUpdateCityDealHistory(String cityCode) {
		List<CountyInitRecord> records = mapper.queryCountyInitRecodeByCityCode(cityCode);
		records.forEach(record->this.initOrUpdateCountyDealHistory(record.getId()));
	}

	/**
	 * 初始化/更新区线小区
	 */
	@Async
	public void initOrUpdateCountyCommunity(Integer id) {
		try {
			CountyInitRecord record = initRecordService.getById(id);
			Objects.requireNonNull(record, "不存在的初始化记录");
			if (record.getIsPressing()) {
				log.warn("该区县正在处理中，放弃处理，区县：{}", record.getCountyName());
				return;
			}
			mapper.updateIsPressing(true, id);
			log.info(LOG_MESSAGE + "解析并保存小区信息开始", record.getCityName(), record.getCountyName());
			// 开始处理解析小区
			String cityCode = record.getCityCode();
			communityHandler.parseOneCountyCommunityMessage(cityCode, record.getCountyCode());
			// 更新最后更新时间和小区数
			mapper.updateCommunityNumAndLastUpdateTime(record.getCountyCode());
			log.info(LOG_MESSAGE + "解析并保存小区信息结束", record.getCityName(), record.getCountyName());
		} catch (Exception e) {
			log.error("解析并保存小区信息报错，error：{}", e.getMessage());
			e.printStackTrace();
		} finally {
			mapper.updateIsPressing(false, id);
		}
	}

	/**
	 * 初始化/更新区线小区成交记录
	 */
	@Async
	public void initOrUpdateCountyDealHistory(Integer id) {
		try {
			CountyInitRecord record = initRecordService.getById(id);
			Objects.requireNonNull(record, "不存在的初始化记录");
			if (record.getIsPressing()) {
				log.warn("该区县正在处理中，放弃更新交易记录的处理，区县：{}", record.getCountyName());
				return;
			}
			mapper.updateIsPressing(true, id);
			log.info(LOG_MESSAGE + "初始化成交记录开始", record.getCityName(), record.getCountyName());
			String cityCode = record.getCityCode();
			dealHandler.incrementParseDealByCounty(cityCode, record.getCountyCode());
			dealHandler.updateListPrice();
			mapper.updateDealNumAndLastUpdateTime(record.getCountyCode());
			log.info(LOG_MESSAGE + "初始化成交记录结束", record.getCityName(), record.getCountyName());
		} catch (Exception e) {
			log.error("解析并保存小区成交信息报错，error：{}", e.getMessage());
			e.printStackTrace();
		} finally {
			mapper.updateIsPressing(false, id);
		}
	}
}
