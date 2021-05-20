package com.fxg.house.viewer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxg.house.viewer.entity.CountyInitRecord;

import java.util.List;

/**
 * <p>
 * 区县初始化记录 Mapper 接口
 * </p>
 *
 * @author yueyakun
 * @since 2020-05-18
 */
public interface CountyInitRecordMapper extends BaseMapper<CountyInitRecord> {



	int updateIsPressing(Boolean isPressing, Integer id);

	int updateCommunityNumAndLastUpdateTime(String CountyCode);

	int updateDealNumAndLastUpdateTime(String CountyCode);

	int updateHouseNum(Integer houseNum, Integer id);

	List<CountyInitRecord> queryCountyInitRecodeByCityCode(String cityCode);
}
