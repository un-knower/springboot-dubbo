package com.cmsz.cn.service;

import com.cmsz.cn.bean.City;

/**
 * 城市业务 Dubbo 服务层
 *
 * Created by bysocket on 28/02/2017.
 */
public interface ICityService {

    /**
     * 根据城市名称，查询城市信息
     * @param cityName
     */
    City findCityByName(String cityName);
}
