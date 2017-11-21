package com.cmsz.cn.service.impl;

import com.cmsz.cn.service.IUserService;
import com.cmsz.cn.service.Person;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by le on 2017/11/21.
 */
@Service(value = "userService")
public class UserServiceImpl implements IUserService {

    @Resource(name = "personService")
    private Person person;

    @Override
    public String speak() {
        String value=person.talk();
        return value+"-----lile";
    }
}
