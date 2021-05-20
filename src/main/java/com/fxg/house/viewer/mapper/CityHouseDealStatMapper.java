package com.fxg.house.viewer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxg.house.viewer.dto.ZZCountyStatDTO;
import com.fxg.house.viewer.entity.CityHouseDealStat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 城市房产成交统计 Mapper 接口
 * </p>
 *
 * @author yueyakun
 * @since 2020-05-18
 */
public interface CityHouseDealStatMapper extends BaseMapper<CityHouseDealStat> {

	List<ZZCountyStatDTO> zzCountyStatDealCount();

	List<ZZCountyStatDTO> zzCountyStatSecondCount();

	List<ZZCountyStatDTO> zzCountyStatDealAvgPrice();

	List<ZZCountyStatDTO> zzCountyStatSecondAvgPrice();

	CityHouseDealStat getCityLastMonth(@Param("cityCode") String cityCode);

	List<CityHouseDealStat> cityLastMonthDeal(@Param("cityCode") String cityCode, @Param("dealYear") Integer dealYear, @Param("dealMonth") Integer dealMonth);
}
