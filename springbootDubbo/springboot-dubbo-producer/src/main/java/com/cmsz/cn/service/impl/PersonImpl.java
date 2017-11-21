package com.cmsz.cn.service.impl;

import com.cmsz.cn.service.Person;
import org.springframework.stereotype.Service;

/**
 * Created by le on 2017/11/21.
 */
@Service("person")
public class PersonImpl implements Person {
    @Override
    public String talk() {
        return "Provider personImpl method";
    }
}
