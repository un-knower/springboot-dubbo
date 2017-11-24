package com.cmsz.cn.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmsz.cn.bean.City;
import com.cmsz.cn.service.ICityService;
import org.springframework.stereotype.Component;

/**
 * Created by le on 2017/11/23.
 */
@Service(version = "1.0.0",group = "HYTC",registry = "HYTC",loadbalance = "roundrobin",retries =2,actives = 5,executes = 50)
public class CityServiceImpl implements ICityService {

    @Override
    public City findCityByName(String cityName) {
        System.out.println("调用服务端");
        City city=new City();
        city.setId(1L);
        city.setProvinceId(2L);
        city.setCityName(cityName);
        city.setDescription("是我的故乡");
        return city;
    }

}
