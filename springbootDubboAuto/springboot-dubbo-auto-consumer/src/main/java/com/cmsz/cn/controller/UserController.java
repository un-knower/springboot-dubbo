package com.cmsz.cn.controller;

import com.cmsz.cn.service.IService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * Created by le on 2017/11/21.
 */
@RestController
public class UserController {

    @Resource(name = "consumerService")
    private IService service;

    @RequestMapping(value = "/consumer" ,produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String refer() throws ExecutionException, InterruptedException {
        return service.talk();
    }
}
