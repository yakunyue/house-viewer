package com.fxg.house.viewer.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxg.house.viewer.entity.Street;
import com.fxg.house.viewer.mapper.StreetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 街区 表 service
 * </p>
 *
 * @author yueyakun
 */
@Service
public class StreetService extends ServiceImpl<StreetMapper, Street> {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private StreetMapper mapper;

	public void updateDay(int updateDay, Integer id) {
		mapper.updateDay(updateDay, id);
	}

	public List<Street> queryThisDayStreet(int updateDay, int nextUpdateDay) {
		return mapper.queryThisDayStreet(updateDay, nextUpdateDay);
	}

	public List<Street> getStreetList(String cityCode, String countyCode) {
		return mapper.getStreetList(cityCode, countyCode);
	}

	public List<Street> queryByCountyId(String countyCode) {
		LambdaQueryWrapper<Street> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(Street::getCountyCode, countyCode);
		queryWrapper.orderByAsc(Street::getCode);
		return this.list(queryWrapper);
	}
}
