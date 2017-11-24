package com.cmsz.cn.kafka;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.cmsz.cn.annotation.KafkaAnnotation;
import com.cmsz.cn.bean.KafkaBean;
import com.cmsz.cn.bean.Order;
import com.cmsz.cn.service.IF2FPayService;
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
@Component
@Service(version = "1.0.0",group = "HYTC",interfaceName = "kafkaService",registry = "HYTC",loadbalance = "roundrobin",retries =2,actives = 5,executes = 50)
public class KafkaServiceImpl implements IKafkaService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaServiceImpl.class);

    final GsonBuilder gsonBuilder = new GsonBuilder();
    final Gson gson = gsonBuilder.create();

    @Resource(name = "kafkaProducer")
    private KafkaProducer<String,String> kafkaProducer;

    @Resource(name = "kafkaConsumer")
    private KafkaConsumer<String,String> kafkaConsumer;

    @Reference(version = "1.0.0",group = "HYTC",interfaceName = "f2FPayService")
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

    @PostConstruct
    public void consumerOrderMessage(){
        kafkaConsumer.subscribe(Arrays.asList("alipay"));
        while (true){
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for(ConsumerRecord<String, String> record : records){
                logger.debug("===========收到订单消息体=============");
                logger.debug("topic:【{}】==offset:【{}】==key:【{}】==value:【{}】",record.topic(),record.offset(),record.key(),record.value());
                /*调用订单服务*/
                Order order= gson.fromJson(record.value(),Order.class);
                Order complementOrder=f2FPayService.preTradePay(order);
                if("true".equals(complementOrder.getTradeFlag())){
                    logger.debug("订单号{}下单成功",order.getOut_trade_no());
                    kafkaConsumer.commitAsync();
                }else{
                    /*缓存到redis内*/
                    logger.error("订单号{}下单失败,请检查。订单详情：{}",order.getOut_trade_no(),gson.toJson(order));
                }
            }
        }
    }



}
