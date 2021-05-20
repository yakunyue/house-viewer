package com.fxg.house.viewer.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxg.house.viewer.entity.County;
import com.fxg.house.viewer.entity.CountyInitRecord;
import com.fxg.house.viewer.mapper.CountyInitRecordMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * <p>
 * 区县初始化记录 表 service
 * </p>
 *
 * @author yueyakun
 */

@Service
public class CountyInitRecordService extends ServiceImpl<CountyInitRecordMapper, CountyInitRecord> {

	@Autowired
	private CountyInitRecordMapper mapper;
	@Autowired
	private CountyService countyService;

	public boolean add(CountyInitRecord data) {
		LambdaQueryWrapper<CountyInitRecord> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CountyInitRecord::getCountyId,data.getCountyId());
		CountyInitRecord one = this.getOne(queryWrapper,false);
		if (Objects.nonNull(one)) {
			return true;
		}
		County county = countyService.getById(data.getCountyId());
		BeanUtils.copyProperties(county, data, "id");
		data.setCountyId(county.getId());
		data.setCountyCode(county.getCode());
		data.setCountyName(county.getName());
//		data.setPhase(CountyInitPhase.NOT_START);控制初始化进度的字段，现在不用了
		return super.save(data);
	}

	public Page<CountyInitRecord> queryInitPage(Integer pageIndex, Integer pageSize, String cityName) {

		Page<CountyInitRecord> page = new Page<>(pageIndex, pageSize);
		LambdaQueryWrapper<CountyInitRecord> queryWrapper = new LambdaQueryWrapper<>();
		if (!StringUtils.isEmpty(cityName)) {
			queryWrapper.like(CountyInitRecord::getCityName, cityName);
		}
		queryWrapper.orderByAsc(CountyInitRecord::getCityCode,CountyInitRecord::getCountyCode);
		return this.page(page, queryWrapper);
	}

	public Integer updateIsPressing(boolean b, Integer id) {
		return mapper.updateIsPressing(b,id);
	}
}
