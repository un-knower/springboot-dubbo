package com.cmsz.cn.service.impl;

//import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.annotation.Service;
import com.cmsz.cn.annotation.KafkaAnnotation;
import com.cmsz.cn.bean.KafkaBean;
import com.cmsz.cn.service.IKafkaService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reger.dubbo.annotation.Inject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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

    @Override
    public Boolean sendMessage(KafkaBean kafkaBean) {
        boolean isSuccess=false;
        if(StringUtils.isEmpty(kafkaBean.getTopic())){
            logger.error("消息队列无Topic,请核实消息体：{}",gson.toJson(kafkaBean));
        }else{
            if(StringUtils.isEmpty(kafkaBean.getKey())){
                kafkaProducer.send(new ProducerRecord<String, String>("lile",kafkaBean.getValue()));
                isSuccess=true;
            }else{
                kafkaProducer.send(new ProducerRecord<String, String>("lile",kafkaBean.getKey(),kafkaBean.getValue()));
                isSuccess=true;
            }
        }
        return isSuccess;
    }

    @Override
    public void sendMessage(List<KafkaBean> kafkaBeanList) {
         if(!CollectionUtils.isEmpty(kafkaBeanList)){
             for(KafkaBean kafkaBean:kafkaBeanList){
                 sendMessage(kafkaBean);
             }
         }
    }

    @Override
    public void sendMessage(String value) {
        logger.info("发送消息队列。。。。");
        KafkaBean kafkaBean=gson.fromJson(value,KafkaBean.class);
        System.out.println(kafkaBean.getKey());
        if(StringUtils.isEmpty(kafkaBean.getKey())){
            kafkaProducer.send(new ProducerRecord<String, String>("hytc",kafkaBean.getValue()));
        }else{
            kafkaProducer.send(new ProducerRecord<String, String>("hytc",kafkaBean.getKey(),kafkaBean.getValue()));
        }
    }

}
