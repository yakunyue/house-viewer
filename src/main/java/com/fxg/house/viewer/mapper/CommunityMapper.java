package com.fxg.house.viewer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxg.house.viewer.entity.Community;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 小区表 Mapper 接口
 * </p>
 *
 * @author yueyakun
 * @since 2020-05-18
 */
public interface CommunityMapper extends BaseMapper<Community> {

	int insertBatch(List<Community> list);

	List<Community> queryAlreadyExist(List<String> newIds);

	int updateDetailByLianjiaId(Community community);

	List<String> queryLianjiaIdList(@Param("cityCode") String cityCode,@Param("streetCode")  String streetCode);

	int updateCommunity(Community o);

	int updateReferencePrice(BigDecimal referencePrice, String lianjiaId);

	void updateDayAndHour(int updateDay, int updateHour, Integer id);

	List<Community> queryThisHourCommunity(int hour, int day, int nextDay);

	int updateLocation(Integer id, BigDecimal longitude, BigDecimal latitude,String address);

	int updateCityName();

	int upToBaiduLocation(Integer id, BigDecimal longitude2, BigDecimal latitude2);
}
