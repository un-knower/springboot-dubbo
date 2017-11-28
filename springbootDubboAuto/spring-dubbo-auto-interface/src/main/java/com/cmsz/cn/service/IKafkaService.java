package com.cmsz.cn.service;

import com.cmsz.cn.bean.KafkaBean;

import java.util.List;

/**
 * Created by le on 2017/11/24.
 */
public interface IKafkaService {

    boolean sendMessage(KafkaBean kafkaBean);

    void sendMessage(String value);

    void sendMessage(List<KafkaBean> kafkaBeanList);

}
