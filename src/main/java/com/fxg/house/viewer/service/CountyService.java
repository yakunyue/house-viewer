package com.fxg.house.viewer.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxg.house.viewer.entity.County;
import com.fxg.house.viewer.mapper.CountyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 区县 表 service
 * </p>
 *
 * @author yueyakun
 */
@Service
public class CountyService extends ServiceImpl<CountyMapper, County> {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private static String baseUrl = "https://%s.ke.com/ershoufang/";

    @Autowired
    private CountyMapper mapper;

    public List<County> selectCountByCityId(Integer cityId) {
        LambdaQueryWrapper<County> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(cityId) && cityId > 0)
            queryWrapper.eq(County::getCityId, cityId);
        return this.list(queryWrapper);
    }

	public List<County> queryInitCountyList(String cityCode) {
		return mapper.queryInitCountyList(cityCode);
	}
}
