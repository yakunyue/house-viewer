package com.fxg.house.viewer.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxg.house.viewer.entity.Province;
import com.fxg.house.viewer.mapper.ProvinceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 省 表 service
 * </p>
 *
 * @author yueyakun
 */
@Service
public class ProvinceService extends ServiceImpl<ProvinceMapper, Province> {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ProvinceMapper mapper;

}
