package com.cmsz.cn.controller;

import com.alibaba.fastjson.JSON;
import com.cmsz.cn.bean.Order;
import com.cmsz.cn.service.IService;
import com.cmsz.cn.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
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

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "consumerService")
    private IService service;

    @RequestMapping(value = "/consumer" ,produces = "text/plain;charset=UTF-8")
    public String buildAlipayTrade(@RequestBody Order order){
        boolean isPreTrade=service.sendTradeMessage(order);
        if(isPreTrade){
            order.setRemake("订单下单成功");
        }else{
            order.setRemake("订单下单失败,请重试");
        }
        logger.debug(JSON.toJSONString(order));
        return JSON.toJSONString(order);
    }

}
