package com.cmsz.cn.bean;

import lombok.Data;

/**
 * Created by le on 2017/11/17.
 */
@Data
public class UserAuth {

    /*用户访问令牌*/
    private String app_auth_token;
    /*
    * 用户授权code*/
    private String app_auth_code;

    /*用户授权登录地址*/
    private String auth_address;

    /*获取用户令牌的方式*/
    private String grant_type;

    /*用户授权码*/
    private String code;


}
