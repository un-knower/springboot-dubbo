package com.cmsz.cn.service;

import com.alipay.api.AlipayApiException;
import com.cmsz.cn.bean.Order;

/**
 * Created by le on 2017/11/16.
 */
/*面对面支付*/
public interface IF2FPayService {

    /*创建二维码交易单*/
    public Order preTradePay(Order order);

    /*实时交易*/
    void tradePay() throws AlipayApiException;

    /*根据交易号查询账单*/
    void queryTrade(Order order) throws AlipayApiException;

    /*账单退款*/
    void refund(Order order) throws AlipayApiException;

    /*取消账单*/
    void cancel(Order order) throws AlipayApiException;

    /*下载账单*/
    void downloadurl() throws AlipayApiException;

}
