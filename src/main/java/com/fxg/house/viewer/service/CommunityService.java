package com.fxg.house.viewer.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxg.house.viewer.entity.Community;
import com.fxg.house.viewer.entity.Street;
import com.fxg.house.viewer.mapper.CommunityMapper;
import com.fxg.house.viewer.mapper.StreetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 小区表 服务实现类
 * </p>
 *
 * @author yueyakun
 * @since 2020-05-18
 */
@Service
public class CommunityService extends ServiceImpl<CommunityMapper, Community> {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private CommunityMapper communityMapper;
	@Autowired
	private StreetMapper streetMapper;

	/**
	 * 按名称模糊查询小区列表
	 *
	 * @param cityCode
	 * @param countyCode
	 * @param streetCode
	 * @param communityName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Page<Community> fuzzyPage(String cityCode, String countyCode, String streetCode, String communityName, Integer pageIndex,
			Integer pageSize) {
		LambdaQueryWrapper<Community> queryWrapper = assembleQueryWrapper(cityCode, countyCode, streetCode, communityName);
		Page<Community> page = new Page<>(pageIndex, pageSize);
		Page<Community> result = this.page(page, queryWrapper);
		List<Community> records = result.getRecords();
		Set<String> streetCodes = records.stream()
										 .map(Community::getStreetCode)
										 .collect(Collectors.toSet());
		if (!CollectionUtils.isEmpty(streetCodes)) {
			List<Street> streets = streetMapper.queryByCodes(new ArrayList<>(streetCodes));
			records.forEach(r -> streets.forEach(s -> {
				if (r.getStreetCode()
					 .equals(s.getCode())) {
					r.setStreet(s);
				}
			}));
		}
		return result;
	}

	public List<Community> fuzzyList(String cityCode, String countyCode, String streetCode, String communityName) {
		LambdaQueryWrapper<Community> queryWrapper = assembleQueryWrapper(cityCode, countyCode, streetCode, communityName);
		queryWrapper.last("limit 10");
		List<Community> result = this.list(queryWrapper);
		return result;
	}

	private LambdaQueryWrapper<Community> assembleQueryWrapper(String cityCode, String countyCode, String streetCode,
			String communityName) {
		LambdaQueryWrapper<Community> queryWrapper = Wrappers.lambdaQuery();
		if (!StringUtils.isEmpty(cityCode)) {
			queryWrapper.eq(Community::getCityCode, cityCode);
		}
		if (!StringUtils.isEmpty(countyCode)) {
			queryWrapper.eq(Community::getCountyCode, countyCode);
		}
		if (!StringUtils.isEmpty(streetCode)) {
			queryWrapper.eq(Community::getStreetCode, streetCode);
		}
		if (!StringUtils.isEmpty(communityName)) {
			queryWrapper.like(Community::getName, communityName);
		}
		return queryWrapper;
	}

	/**
	 * 更新小区的更新日和更新时
	 *
	 * @param updateDay
	 * @param updateHour
	 * @param id
	 */
	public void updateDayAndHour(int updateDay, int updateHour, Integer id) {
		communityMapper.updateDayAndHour(updateDay, updateHour, id);
	}

	/**
	 * 按更新日和更新时查询小区列表
	 *
	 * @param hour
	 * @param day
	 * @param nextDay
	 * @return
	 */
	public List<Community> queryThisHourCommunity(int hour, int day, int nextDay) {
		return communityMapper.queryThisHourCommunity(hour, day, nextDay);
	}
}
