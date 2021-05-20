package com.fxg.house.viewer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxg.house.viewer.entity.Street;

import java.util.List;

/**
 * <p>
 * 街区 Mapper 接口
 * </p>
 *
 * @author yueyakun
 * @since 2020-05-18
 */
public interface StreetMapper extends BaseMapper<Street> {

    int insertBatch(List<Street> list);

	List<Street> getStreetList(String cityCode, String countyCode);

	int updateDay(int updateDay, Integer id);

	List<Street> queryThisDayStreet(int updateDay, int nextUpdateDay);

	List<Street> getStreetListByCode(String cityCode, String streetCode);

	List<Street> queryByCodes(List<String> list);
}
