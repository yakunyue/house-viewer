package com.fxg.house.viewer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxg.house.viewer.entity.City;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 市 Mapper 接口
 * </p>
 *
 * @author yueyakun
 * @since 2020-05-18
 */
public interface CityMapper extends BaseMapper<City> {

	int insertBatch(List<City> list);

	List<City> queryInitCityList(@Param("name") String name);
}
