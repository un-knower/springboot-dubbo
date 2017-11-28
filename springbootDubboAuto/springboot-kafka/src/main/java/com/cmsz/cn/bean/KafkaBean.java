package com.cmsz.cn.bean;

import com.cmsz.cn.annotation.KafkaAnnotation;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

/**
 * Created by le on 2017/11/24.
 */
@Data
public class KafkaBean  implements Serializable {

    private String key;

    private String value;

    private long offset;

    private String status;

    private Integer retryNum=0;

    private String topic;

}
