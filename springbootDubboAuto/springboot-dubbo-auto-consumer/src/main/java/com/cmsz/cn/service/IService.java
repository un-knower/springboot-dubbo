package com.cmsz.cn.service;

import java.util.concurrent.ExecutionException;

/**
 * Created by le on 2017/11/23.
 */
public interface IService {
    String talk() throws ExecutionException, InterruptedException;
}
