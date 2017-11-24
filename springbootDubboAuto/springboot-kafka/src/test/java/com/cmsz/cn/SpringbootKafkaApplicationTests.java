package com.cmsz.cn;

import com.cmsz.cn.bean.KafkaBean;
import com.cmsz.cn.service.IKafkaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootKafkaApplicationTests {

    @Autowired
	private IKafkaService kafkaService;

	@Test
	public void contextLoads() {
		kafkaService.sendMessage(new KafkaBean("ssf","success"));
		System.out.println("完成");
	}

}
