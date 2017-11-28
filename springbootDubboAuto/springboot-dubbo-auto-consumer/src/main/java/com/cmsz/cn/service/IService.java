package com.cmsz.cn.service;

import com.cmsz.cn.bean.Order;

import java.util.concurrent.ExecutionException;

/**
 * Created by le on 2017/11/23.
 */
public interface IService {
    String talk() throws ExecutionException, InterruptedException;

    boolean sendTradeMessage(Order order);

}
