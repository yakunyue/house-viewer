package com.fxg.house.viewer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxg.house.viewer.entity.WatchCommunity;

import java.util.List;

/**
 * <p>
 * 小区关注记录表 Mapper 接口
 * </p>
 *
 * @author yueyakun
 * @since 2020-05-18
 */
public interface WatchCommunityMapper extends BaseMapper<WatchCommunity> {

	int insertBatch(List<WatchCommunity> list);

}
