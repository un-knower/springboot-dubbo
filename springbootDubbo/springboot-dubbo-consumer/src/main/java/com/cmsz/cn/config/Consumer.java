package com.cmsz.cn.config;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.cmsz.cn.config.DubboBaseConfig;
import com.cmsz.cn.service.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by le on 2017/11/21.
 */
@Configuration
public class Consumer extends DubboBaseConfig {

    @Bean
    public ReferenceBean<Person> personConsumer() {
        ReferenceBean<Person> ref = new ReferenceBean<Person>();
        ref.setVersion("1.0.0");
        ref.setInterface(Person.class.getName());
        ref.setTimeout(5000);
        ref.setRetries(3);
        ref.setCheck(false);
        return ref;
    }

    @Bean(name = "personService")
    public Person getPersonImpl(){
        return  personConsumer().get();
    }

}
