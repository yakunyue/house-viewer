package com.fxg.house.viewer.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxg.house.viewer.dto.ZZCountyStatDTO;
import com.fxg.house.viewer.entity.CityHouseDealStat;
import com.fxg.house.viewer.mapper.CityHouseDealStatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 城市房产成交统计表 service
 * </p>
 *
 * @author yueyakun
 */
@Service
public class CityHouseDealStatService extends ServiceImpl<CityHouseDealStatMapper, CityHouseDealStat> {

	@Autowired
	private CityHouseDealStatMapper mapper;

	public List<ZZCountyStatDTO> queryZZCountyStatDTOList(String code) {
		switch (code) {
			case "DEAL_COUNT":
				return mapper.zzCountyStatDealCount();
			case "DEAL_AVG_PRICE":
				return mapper.zzCountyStatDealAvgPrice();
			case "SECOND_COUNT":
				return mapper.zzCountyStatSecondCount();
			case "SECOND_AVG_PRICE":
				return mapper.zzCountyStatSecondAvgPrice();
			default:
				throw new IllegalArgumentException("不支持的数据纬度");
		}
	}


	public List<CityHouseDealStat> cityLastMonthDeal(String cityCode) {

		CityHouseDealStat one = mapper.getCityLastMonth(cityCode);
		List<CityHouseDealStat> result =  mapper.cityLastMonthDeal(cityCode,one.getDealYear(),one.getDealMonth());
		return result;
	}
}
