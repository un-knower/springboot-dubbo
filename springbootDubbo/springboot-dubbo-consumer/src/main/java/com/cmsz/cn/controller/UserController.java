package com.cmsz.cn.controller;

import com.cmsz.cn.config.Consumer;
import com.cmsz.cn.service.IUserService;
import com.cmsz.cn.service.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by le on 2017/11/21.
 */
@RestController
public class UserController {

    @Resource(name = "userService")
    private IUserService userService;

    @RequestMapping("/consumer")
    @ResponseBody
    public String refer(){
        return userService.speak();
    }

    @RequestMapping(value = "/",produces = "text/plain;charset=UTF-8")
    String index(){
        return "Hello Spring Boot!";
    }
}
