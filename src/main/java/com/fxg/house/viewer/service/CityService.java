package com.fxg.house.viewer.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxg.house.viewer.entity.City;
import com.fxg.house.viewer.mapper.CityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 市 表 service
 * </p>
 *
 * @author yueyakun
 */
@Service
public class CityService extends ServiceImpl<CityMapper, City> {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private CityMapper mapper;

	public List<City> list(Integer provinceId) {
		LambdaQueryWrapper<City> queryWrapper = Wrappers.lambdaQuery();
		if (Objects.nonNull(provinceId) && provinceId > 0)
			queryWrapper.eq(City::getProvinceId, provinceId);
		return this.list(queryWrapper);
	}

	public List<City> queryInitCityList(String name) {

		return mapper.queryInitCityList(name);
	}
}
