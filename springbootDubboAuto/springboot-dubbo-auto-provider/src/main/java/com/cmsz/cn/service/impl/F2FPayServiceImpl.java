package com.cmsz.cn.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.cmsz.cn.bean.Order;
import com.cmsz.cn.service.IF2FPayService;
import com.cmsz.cn.utils.ZxingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * Created by le on 2017/11/16.
 */
@Service(version = "1.0.0",group = "HYTC",interfaceName = "f2FPayService",registry = "HYTC",loadbalance = "roundrobin",retries =2,actives = 5,executes = 50)
public class F2FPayServiceImpl implements IF2FPayService {

    private static final Logger logger = LoggerFactory.getLogger(F2FPayServiceImpl.class);

    @Resource(name = "alipayClient")
    private AlipayClient alipayClient;

    @Override
    public Order preTradePay(Order order) {
        boolean tradeFlag=false;
        if(StringUtils.isEmpty(order.getOut_trade_no())){
            throw new NullPointerException("交易订单号为空");
        }
        if(StringUtils.isEmpty(order.getTotal_amount())){
            throw new NullPointerException("总金额为空");
        }
        if(StringUtils.isEmpty(order.getSubject())){
            throw new NullPointerException("订单主题为空");
        }
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        String orderJson= JSON.toJSONString(order);
        request.setBizContent(orderJson);
        AlipayTradePrecreateResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            logger.error(e.toString());
        }
        if(null==response){
            throw new NullPointerException("系统异常，预下单状态未知");
        }else{
            if(response.isSuccess()){
                System.out.println("支付宝预下单成功");
                System.out.println(response.getBody());
                System.out.println(response.getOutTradeNo());
                System.out.println(order.getOut_trade_no());
                // 需要修改为运行机器上的路径,生成二维码
                String filePath = String.format("F:\\alipay\\qr-%s.png", response.getOutTradeNo());
                logger.info("filePath:" + filePath);
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);
                order.setTradeFlag("true");
            }else{
                logger.error("支付宝预下单失败!!!");
            }
        }
        return order;
    }

    @Override
    public void tradePay() throws AlipayApiException {
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();
        model.setOutBizNo("20171117134602");
        model.setAmount("10");
//        model.setPayeeAccount("jjkwqj3494@sandbox.com");
        model.setPayeeAccount("majcyn8347@sandbox.com");
        model.setPayeeType("ALIPAY_LOGONID");
        request.setBizModel(model);
        AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
        if (null == response) {
            throw new NullPointerException("系统服务器异常");
        } else {
            if (response.isSuccess()) {
                System.out.println("调用成功");
                System.out.println(response.getBody());
            } else {
                System.out.println("调用失败");
            }
        }
    }

    @Override
    public void queryTrade(Order order) throws AlipayApiException {
        if(StringUtils.isEmpty(order.getOut_trade_no())){
            throw new NullPointerException("交易订单号为空");
        }
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent(JSON.toJSONString(order));
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            System.out.println(response.getBody());
        } else {
            System.out.println(response.getBody());
            System.out.println("调用失败");
            System.out.println("该订单还未生效");
        }
    }

    @Override
    public void refund(Order order) throws AlipayApiException {
        if(StringUtils.isEmpty(order.getOut_trade_no())){
            throw new NullPointerException("交易订单号为空");
        }
        if(StringUtils.isEmpty(order.getRefund_amount())){
            throw new NullPointerException("退款金额为空");
        }
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent(JSON.toJSONString(order));
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());
    }

    @Override
    public void cancel(Order order) throws AlipayApiException {
        if(StringUtils.isEmpty(order.getOut_trade_no())){
            throw new NullPointerException("交易订单号为空");
        }
        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
        request.setBizContent(JSON.toJSONString(order));
        AlipayTradeCancelResponse response = alipayClient.execute(request);
        if (null == response) {
            throw new NullPointerException("系统服务器异常");
        } else {
            if (response.isSuccess()) {
                System.out.println("取消成功");
                System.out.println(response.getBody());
            } else {
                System.out.println("取消成功");
            }
        }
    }

    @Override
    public void downloadurl() throws AlipayApiException {
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        request.setBizContent("{\"bill_type\":\"trade\",\"bill_date\":\"2016-10\"}");
        AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
        System.out.println(response.getBillDownloadUrl());
    }
}
