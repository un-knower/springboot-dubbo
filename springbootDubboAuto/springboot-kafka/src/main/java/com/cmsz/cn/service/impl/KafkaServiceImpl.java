package com.cmsz.cn.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.cmsz.cn.annotation.KafkaAnnotation;
import com.cmsz.cn.bean.KafkaBean;
import com.cmsz.cn.bean.Order;
import com.cmsz.cn.service.IF2FPayService;
import com.cmsz.cn.service.IKafkaService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by le on 2017/11/24.
 */
@Service(version = "1.0.0",group = "HYTC",interfaceName = "kafkaService",registry = "HYTC",loadbalance = "roundrobin",retries =2,actives = 5,executes = 50)
public class KafkaServiceImpl implements IKafkaService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaServiceImpl.class);

    final GsonBuilder gsonBuilder = new GsonBuilder();
    final Gson gson = gsonBuilder.create();

    @Resource(name = "kafkaProducer")
    private KafkaProducer<String,String> kafkaProducer;

    @Resource(name = "kafkaConsumer")
    private KafkaConsumer<String,String> kafkaConsumer;

    @Reference(version = "1.0.0",group = "HYTC",interfaceName = "f2FPayService",check = false)
    private IF2FPayService f2FPayService;

    @Override
    public void sendMessage(KafkaBean kafkaBean) {
        if(kafkaBean.getClass().isAnnotationPresent(KafkaAnnotation.class)){
            KafkaAnnotation kafkaAnnotation=kafkaBean.getClass().getAnnotation(KafkaAnnotation.class);
            String topic=kafkaAnnotation.topic();
            if(StringUtils.isEmpty(kafkaBean.getKey())){
                kafkaProducer.send(new ProducerRecord<String, String>(topic,kafkaBean.getValue()));
            }else{
                kafkaProducer.send(new ProducerRecord<String, String>(topic,kafkaBean.getKey(),kafkaBean.getValue()));
            }
        }
    }

    @Override
    public void sendMessage(List<KafkaBean> kafkaBeanList) {
         if(!CollectionUtils.isEmpty(kafkaBeanList)){
             for(KafkaBean kafkaBean:kafkaBeanList){
                 sendMessage(kafkaBean);
             }
         }
    }

}
