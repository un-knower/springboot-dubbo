package com.cmsz.cn.service;

//import com.alibaba.dubbo.config.annotation.Reference;
//import com.cmsz.cn.bean.Order;
import com.alibaba.dubbo.config.annotation.Reference;
import com.cmsz.cn.bean.Order;
import com.cmsz.cn.service.impl.KafkaServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by le on 2017/11/24.
 */
@Component
public class KafkaMessageService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaServiceImpl.class);

    final GsonBuilder gsonBuilder = new GsonBuilder();
    final Gson gson = gsonBuilder.create();

    @Resource(name = "kafkaConsumer")
    private KafkaConsumer<String,String> kafkaConsumer;

    @Reference(version = "1.0.0",group = "HYTC",interfaceName = "f2FPayService",check = false)
    private IF2FPayService f2FPayService;

    public void consumerOrderMessage(){
        /*开始构建消费者*/
        kafkaConsumer.subscribe(Arrays.asList("lile"));
        System.out.println("开始接受消息");
        while (true){
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for(ConsumerRecord<String, String> record : records){
                logger.info("===========收到订单消息体=============");
                logger.info("topic:【{}】==offset:【{}】==key:【{}】==value:【{}】",record.topic(),record.offset(),record.key(),record.value());
                /*调用订单服务*/
                Order order= gson.fromJson(record.value(),Order.class);
                if(null!=order){
                    Order complementOrder=f2FPayService.preTradePay(order);
                    if("true".equals(complementOrder.getTradeFlag())){
                        logger.info("订单号{}下单成功",order.getOut_trade_no());
                        kafkaConsumer.commitAsync();
                    }else{
                    /*缓存到redis内*/
                        logger.info("订单号{}下单失败,请检查。订单详情：{}",order.getOut_trade_no(),gson.toJson(order));
                    }
                }else{
                    logger.error("订单号类型错误,请检查。。。。");
                }
            }
        }
    }
}
