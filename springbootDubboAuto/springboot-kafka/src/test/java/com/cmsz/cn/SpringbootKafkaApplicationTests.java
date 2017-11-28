package com.cmsz.cn;

import com.cmsz.cn.bean.KafkaBean;
import com.cmsz.cn.service.IKafkaService;
import com.cmsz.cn.service.KafkaMessageService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Properties;

public class SpringbootKafkaApplicationTests {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "hadoop2:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        Consumer<String,String> consumer = new KafkaConsumer<String,String>(props);
        consumer.subscribe(Arrays.asList("lile"));
        while(true){
            ConsumerRecords<String,String> records = consumer.poll(1000);
            for(ConsumerRecord<String,String> record : records){
                System.out.println("offset=" + record.offset() + ",--key=" + record.key() + ",--value=" + record.value());
            }
        }
    }
}
