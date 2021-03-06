package com.cmsz.cn.annotation;

import lombok.NonNull;
import lombok.experimental.NonFinal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by le on 2017/11/24.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface KafkaAnnotation {
    @NonNull
    String topic();
}
