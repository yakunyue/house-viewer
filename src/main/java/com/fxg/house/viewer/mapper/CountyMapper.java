package com.fxg.house.viewer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxg.house.viewer.entity.County;

import java.util.List;

/**
 * <p>
 * 区县 Mapper 接口
 * </p>
 *
 * @author yueyakun
 * @since 2020-05-18
 */
public interface CountyMapper extends BaseMapper<County> {

	int insertBatch(List<County> list);

	List<County> queryInitCountyList(String cityCode);
}
