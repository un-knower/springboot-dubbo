package com.cmsz.cn.bean;

import com.cmsz.cn.annotation.KafkaAnnotation;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Created by le on 2017/11/24.
 */
@Data
@KafkaAnnotation(topic = "aliay")
public class KafkaBean {

    private String key;

    @NonNull
    private String value;

    private long offset;

    @NonNull
    private String status;

    private Integer retryNum=0;

}
