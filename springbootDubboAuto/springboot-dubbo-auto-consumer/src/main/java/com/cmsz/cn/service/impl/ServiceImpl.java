package com.cmsz.cn.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.cmsz.cn.bean.City;
import com.cmsz.cn.bean.KafkaBean;
import com.cmsz.cn.bean.Order;
import com.cmsz.cn.service.IKafkaService;
import com.cmsz.cn.service.ICityService;
import com.cmsz.cn.service.IService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by le on 2017/11/23.
 */
@Service(value = "consumerService")
public class ServiceImpl implements IService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceImpl.class);

    final GsonBuilder gsonBuilder = new GsonBuilder();
    final Gson gson = gsonBuilder.create();

   @Reference(group = "HYTC",version = "1.0.0")
    private ICityService cityService;

    @Reference(group = "HYTC",version = "1.0.0",interfaceName = "kafkaService")
    private IKafkaService kafkaService;

    @Override
    public String talk() throws ExecutionException, InterruptedException {
        City city=cityService.findCityByName("lile");
        System.out.println("==============");
        System.out.println(city);
        System.out.println("==============");
        if(null==city){
            /*从容器中获取，适用于异步调用*/
            Future<City> pFuture = RpcContext.getContext().getFuture();
            city=pFuture.get();
        }
        return city.toString();
    }

    @Override
    public void sendTradeMessage(Order order) {
       order.setOut_trade_no("HYTC_"+String.valueOf(System.currentTimeMillis()));
       String orderDetail=gson.toJson(order);
        KafkaBean kafkaBean=new KafkaBean(orderDetail,"unhander");
        kafkaBean.setKey(order.getOut_trade_no());
        kafkaService.sendMessage(kafkaBean);
    }
}
