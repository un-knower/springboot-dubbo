package com.cmsz.cn.bean;

import com.cmsz.cn.annotation.KafkaAnnotation;
import lombok.Data;
import lombok.NonNull;

/**
 * Created by le on 2017/11/24.
 */
@Data
@KafkaAnnotation(topic = "hytc")
public class KafkaBean {

    private String key;

    @NonNull
    private String value;

    private long offset;

    @NonNull
    private String status;

    private Integer retryNum=0;

}
