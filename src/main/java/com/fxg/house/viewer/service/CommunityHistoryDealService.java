package com.fxg.house.viewer.service;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.fxg.house.viewer.dto.HistoryDealPositionDTO;
import com.fxg.house.viewer.dto.PriceChangeDTO;
import com.fxg.house.viewer.dto.QueryBean;
import com.fxg.house.viewer.mapper.CommunityHistoryDealMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 成交历史表 service
 * </p>
 * <p>
 * 成交记录更新逻辑：成交信息应该不会变，每次增量更新即可，以什么为标准呢，以时间为标准？不好。
 * 由于小区的成交列表页面是以时间倒排的，所以每次更新按页解析，发现本页有重复数据，就代表后面的肯定都是重的？
 * 也不一定。。。，万一有补录的。。。
 * 但是每次都全量解析，也不好
 * 那就加个判断，如果本页有有重复数据，且该重复数据是60天前的，就结束处理。
 *
 * @author yueyakun
 */
@Service
public class CommunityHistoryDealService {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	private static String baseUrl = "https://%s.ke.com/chengjiao/pg%sc%s/";

	LoadingCache<QueryBean, List<PriceChangeDTO>> PRICE_CHANGE_CACHE;

	LoadingCache<QueryBean, List<HistoryDealPositionDTO>> DEAL_POSITION_CACHE;

	@Autowired
	private CommunityHistoryDealMapper mapper;


	@PostConstruct
	private void init() {
		PRICE_CHANGE_CACHE = Caffeine.newBuilder()
				// 数量上限
				.maximumSize(200)
				// 过期机制
				.expireAfterWrite(30, TimeUnit.MINUTES)
				// 剔除监听
				.removalListener( (key, value, cause) -> log.info(
						"key:" + key + ", value:" + value + ", 删除原因:" + cause.toString()))
				.build(key -> this.queryPriceChange(key.getCityCode(), key.getCountyCode(), key.getStreetCode(), key.getCommunityCode(), key.getFromDate()));

		DEAL_POSITION_CACHE = Caffeine.newBuilder()
				// 数量上限
				.maximumSize(200)
				// 过期机制
				.expireAfterWrite(10, TimeUnit.MINUTES)
				// 弱引用key
				// .weakKeys()
				// 弱引用value
				// .weakValues()
				// 剔除监听
				.removalListener( (key, value, cause) -> log.info(
						"key:" + key + ", value:" + value + ", 删除原因:" + cause.toString()))
				.build(key -> this.queryDealPosition(key.getCityCode(), key.getFromDate(), key.getToDate()));
	}

	public List<PriceChangeDTO> queryPriceChangeUseCache(String cityCode, String countyCode, String streetCode, String communityCode, LocalDate fromDate) {
		QueryBean queryBean = new QueryBean();
		queryBean.setCityCode(cityCode);
		queryBean.setCountyCode(countyCode);
		queryBean.setStreetCode(streetCode);
		queryBean.setCommunityCode(communityCode);
		queryBean.setFromDate(fromDate);
		return PRICE_CHANGE_CACHE.get(queryBean);
	}

	public List<HistoryDealPositionDTO> queryDealPositionUseCache(String cityCode, LocalDate fromDate, LocalDate toDate) {
		QueryBean queryBean = new QueryBean();
		queryBean.setCityCode(cityCode);
		queryBean.setFromDate(fromDate);
		queryBean.setToDate(toDate);
		return DEAL_POSITION_CACHE.get(queryBean);
	}

	public List<PriceChangeDTO> queryPriceChange(String cityCode, String countyCode, String streetCode, String communityCode, LocalDate fromDate) {
		return mapper.queryPriceChange(cityCode, countyCode, streetCode, communityCode, fromDate);
	}

	public List<HistoryDealPositionDTO> queryDealPosition(String cityCode, LocalDate fromDate, LocalDate toDate) {
		return mapper.queryDealPosition(cityCode, fromDate,toDate);
	}
}
