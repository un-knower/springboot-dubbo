package com.cmsz.cn.service;

import com.cmsz.cn.bean.KafkaBean;

import java.util.List;

/**
 * Created by le on 2017/11/24.
 */
public interface IKafkaService {

    void sendMessage(KafkaBean kafkaBean);

    void sendMessage(List<KafkaBean> kafkaBeanList);

}
