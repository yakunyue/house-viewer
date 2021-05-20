package com.fxg.house.viewer.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxg.house.viewer.entity.WatchCommunity;
import com.fxg.house.viewer.mapper.WatchCommunityMapper;
import com.fxg.house.viewer.spider.handler.CommunityHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 小区关注记录表 service
 * </p>
 *
 * @author yueyakun
 */
@Service
public class WatchCommunityService extends ServiceImpl<WatchCommunityMapper, WatchCommunity> {


	@Autowired
	private WatchCommunityMapper mapper;
	@Autowired
	private CommunityHandler communityHandler;


	/**
	 * 关注小区：
	 * 1、入库
	 * 2、更新小区详情
	 * 3、初始化小区房源
	 *
	 * @param entity
	 * @return
	 */
	public WatchCommunity add(WatchCommunity entity) {
		// 查看小区已有关注记录，有关注记录就任务已经初始化过了，有该用户的关注记录就不保存了
		List<WatchCommunity> watchList = this.queryWatchList(entity.getLianjiaCommunityId());
		for (WatchCommunity e : watchList) {
			if (entity.getUserId().equals(e.getUserId())) {
				return e;
			}
		}
		// 入库
		this.save(entity);

		if (watchList.size() > 0) {
			return entity;
		}
		// 更新小区详细信息
		communityHandler.updateOneCommunityDetailAsync(entity.getCityCode(), entity.getLianjiaCommunityId());
//		// 初始化小区在售房源
//		Future<List<House>> future = houseService.parseOneCommunityHouseMessageAsync(entity.getCityCode(),
//				entity.getCountyCode(), entity.getStreetCode(), entity.getLianjiaCommunityId());
//		// 更新房源详情
//		try {
//			List<House> houses = future.get();
//			houses.forEach(
//					house -> houseService.updateOneHouseDetailAsync(house.getCityCode(), house.getLianjiaHouseId()));
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			e.printStackTrace();
//		}
		return entity;
	}

	public List<WatchCommunity> queryWatchList(String lianjiaId) {
		LambdaQueryWrapper<WatchCommunity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(WatchCommunity::getLianjiaCommunityId, lianjiaId);
		return this.list(queryWrapper);
	}
}
