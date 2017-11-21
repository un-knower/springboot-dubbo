package com.cmsz.cn.service;

import com.alibaba.dubbo.config.spring.ServiceBean;
import com.cmsz.cn.config.DubboBaseConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by le on 2017/11/21.
 */
@Configuration
public class Export extends DubboBaseConfig {
    @Bean
    public ServiceBean<Person> personProvider(Person person) {
        ServiceBean<Person> serviceBean = new ServiceBean<Person>();
        serviceBean.setProxy("javassist");
        serviceBean.setVersion("1.0.0");
        serviceBean.setInterface(Person.class.getName());
        serviceBean.setRef(person);
        serviceBean.setTimeout(5000);
        serviceBean.setRetries(3);
        return serviceBean;
    }
}
