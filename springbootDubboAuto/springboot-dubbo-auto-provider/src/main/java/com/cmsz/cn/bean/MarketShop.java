package com.cmsz.cn.bean;

import lombok.Data;

/**
 * Created by le on 2017/11/22.
 */
@Data
public class MarketShop {

    /*门店唯一标识*/
    private String shop_id;
    /*门店外部标识*/
    private String store_id;

    private String category_id;
    /*品牌名称*/
    private String brand_name;

    private String request_id;

    private String business_time;

    private String biz_version="2.0";

    private String licence;

    private String licence_code;

    private String licence_name;

    /*操作人角色 默认商户操作:MERCHANT；服务商操作：PROVIDER；*/
    private String op_role;

    /*门店数据查询类型  BRAND_RELATION :品牌商关联店铺;MALL_SELF ：MALL自己的门店 MALL_RELATION：MALL关联下的门店 MERCHANT_SELF:商户自己的门店 KB_PROMOTER：口碑客推广者*/
    private String query_type;

    private String main_shop_name;

    private String province_code;

    private String city_code;

    private String district_code;

    private String address;

    private String longitude;

    private String latitude;

    private String contact_number;

    private String main_image="1T8Pp00AT7eo9NoAJkMR3AAAACMAAQEC";

    private String isv_uid="2088001969784501";
}
