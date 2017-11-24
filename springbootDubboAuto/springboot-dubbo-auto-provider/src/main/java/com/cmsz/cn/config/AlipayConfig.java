package com.cmsz.cn.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.*;

/**
 * Created by le on 2017/11/16.
 */
@Configuration
public class AlipayConfig {

     @Value("${alipayServerUrl}")
     private String alipayServerUrl;

     @Value("${appid}")
     private String appid;

     @Value("${signType}")
     private String signType;

     @Value("${alipay.public.key}")
     private String alipayPublicKey;

     @Bean(name = "alipayClient")
     public AlipayClient alipayClient() throws IOException {
         File file = ResourceUtils.getFile("classpath:privateKey.txt");
         BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
         String privateKey=br.readLine();
         AlipayClient alipayClient = new DefaultAlipayClient(alipayServerUrl, appid, privateKey, "json", "GBK", alipayPublicKey, signType);
         return alipayClient;
     }

}
