package com.cmsz.cn.bean;

import lombok.Data;

/**
 * Created by le on 2017/11/16.
 */
@Data
public class Order {
    /*订单id*/
    private String out_trade_no;
    /*总金额*/
    private String total_amount;

    private String productCode="QUICK_MSECURITY_PAY";

    private String body;

    private String subject;
    /*退款金额*/
    private String refund_amount;

    private String tradeFlag="false";

}
