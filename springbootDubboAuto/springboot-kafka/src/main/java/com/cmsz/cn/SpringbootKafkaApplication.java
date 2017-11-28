package com.cmsz.cn;

import com.cmsz.cn.service.KafkaMessageService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.Properties;

@SpringBootApplication
public class SpringbootKafkaApplication {

	public static void main(String[] args) {

		ApplicationContext ac=SpringApplication.run(SpringbootKafkaApplication.class, args);
	    ac.getBean(KafkaMessageService.class).consumerOrderMessage();
	}
}
