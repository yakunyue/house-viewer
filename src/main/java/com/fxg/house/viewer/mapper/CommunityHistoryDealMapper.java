package com.fxg.house.viewer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxg.house.viewer.dto.HistoryDealPositionDTO;
import com.fxg.house.viewer.dto.PriceChangeDTO;
import com.fxg.house.viewer.entity.CommunityHistoryDeal;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 成交记录表 Mapper 接口
 * </p>
 *
 * @author yueyakun
 * @since 2020-05-18
 */
public interface CommunityHistoryDealMapper extends BaseMapper<CommunityHistoryDeal> {

	int insertBatch(List<CommunityHistoryDeal> list);

	List<CommunityHistoryDeal> queryAlreadyExist(List<String> newIds);

	List<PriceChangeDTO> queryPriceChange(@Param("cityCode") String cityCode, @Param("countyCode") String countyCode,
			@Param("streetCode") String streetCode, @Param("communityCode") String communityCode, @Param("fromDate") LocalDate fromDate);

	int updateListPrice(CommunityHistoryDeal deal);

	int updateDealCycle();

	List<HistoryDealPositionDTO> queryDealPosition(@Param("cityCode") String cityCode, @Param("fromDate") LocalDate fromDate,
			@Param("toDate") LocalDate toDate);
}
