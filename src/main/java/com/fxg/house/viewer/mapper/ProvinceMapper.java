package com.fxg.house.viewer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxg.house.viewer.entity.Province;

import java.util.List;

/**
 * <p>
 * 省 Mapper 接口
 * </p>
 *
 * @author yueyakun
 * @since 2020-05-18
 */
public interface ProvinceMapper extends BaseMapper<Province> {

    int insertBatch(List<Province> list);

}
