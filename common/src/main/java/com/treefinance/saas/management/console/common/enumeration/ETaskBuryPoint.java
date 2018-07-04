package com.treefinance.saas.management.console.common.enumeration;

import org.apache.commons.lang3.StringUtils;

/**
 * @author:guoguoyun
 * @date:Created in 2018/6/27上午11:33
 */
public enum ETaskBuryPoint {
    E_COMMERCE_SDK_ZERO(100001,"用户选择电商，进入电商列表页"),


    E_COMMERCE_SDK_FIRST(100101,"电商列表页，点击淘宝或支付宝"),
    E_COMMERCE_SDK_SECOND(100102,"电商列表页，点击淘宝或支付宝"),
    E_COMMERCE_SDK_THIRD(100103,"电商列表页，点击返回"),
    E_COMMERCE_SDK_FOURTH(100301,"授权书页，点击返回"),
    E_COMMERCE_SDK_FIFTH(100401,"第三方登录页面，点击返回"),


    E_COMMERCE_H5_FIRST(100801,"H5电商认证页，点击重新获取二维码"),
    E_COMMERCE_H5_SECOND(100802,"H5电商认证页，点击获取用户协议"),
    E_COMMERCE_H5_THIRD(100803,"H5电商认证页，点击一键认证"),

    EMAIL_SDK_ZERO(200001,"用户选择邮箱，进入邮箱列表页"),
    EMAIL_SDK_FIRST(200101,"邮箱列表页，点击返回"),
    EMAIL_SDK_SECOND(200102,"邮箱列表页，点击QQ邮箱"),
    EMAIL_SDK_THIRD(200103,"邮箱列表页，点击163邮箱"),
    EMAIL_SDK_FOURTH(200104,"邮箱列表页，点击126邮箱"),
    EMAIL_SDK_FIFTH(200105,"邮箱列表页，点击新浪邮箱"),
    EMAIL_SDK_SIXTH(200106,"邮箱列表页，点击139邮箱"),
    EMAIL_SDK_SEVENTH(200107,"邮箱列表页，点击勾选用户协议"),
    EMAIL_SDK_EIGHTH(200108,"邮箱列表页，点击取消勾选用户歇息"),
    EMAIL_SDK_NINTH(200109,"邮箱列表页，点击查看用户协议"),
    EMAIL_SDK_TENTH(200110,"邮箱列表页，点击企业邮箱"),
    EMAIL_SDK_ELEVENTH(200301,"授权书页面，点击返回"),
    EMAIL_SDK_TWELFTH(200401,"第三方登录页面,点击返回"),



    EMAIL_H5_ZERO(200801,"H5邮箱认证页，点击二维码登录"),
    EMAIL_H5_FIRST(200802,"H5邮箱认证页，点击账号密码登录"),
    EMAIL_H5_SECOND(200803,"H5邮箱认证页，点击用户协议"),
    EMAIL_H5_THIRD(200804,"H5邮箱认证页，点击提交"),
    EMAIL_H5_FOURTH(200805,"H5邮箱认证页，点击重新获取二维码"),
    EMAIL_H5_FIFTH(200806,"H5邮箱认证页，点击忘记密码"),
    EMAIL_H5_SIXTH(200807,"H5邮箱认证页，点击一键认证"),
    EMAIL_H5_SEVENTH(200808,"H5邮箱认证页，二维码认证弹窗点击第一步"),
    EMAIL_H5_EIGHTH(200809,"H5邮箱认证页，二维码认证弹窗点击第二步"),
    EMAIL_H5_NINTH(200810,"H5邮箱认证页，二维码认证弹窗点击 已截图 下一步"),
    EMAIL_H5_TENTH(200811,"H5邮箱认证页，二维码认证弹窗点击 打开QQ扫一扫"),



    OPERATOR_ZERO(300001,"用户选择运营商，进入手机号及运营商页"),
    OPERATOR_FIRST(300501,"确认手机号及运营商页，点击运营商信息修改"),
    OPERATOR_SECOND(300502,"确认手机号及运营商页，点击确认"),
    OPERATOR_THIRD(300503,"确认手机号及运营商页，点击阅读个人信息查询授权书"),
    OPERATOR_FOURTH(300504,"确认手机号及运营商页，点击返回"),
    OPERATOR_FIFTH(300505,"确认手机号及运营商页，运营商正在维护"),
    OPERATOR_SIXTH(300506,"确认手机号及运营商页，点击勾选用户协议"),
    OPERATOR_SEVENTH(300507,"确认手机号及运营商页，点击取消勾选用户协议"),
    OPERATOR_EIGHTH(300601,"选择运营商弹窗，点击确认"),
    OPERATOR_NINTH(300602,"选择运营商弹窗，点击取消"),
    OPERATOR_TENTH(300301,"授权书页面，点击返回"),
    OPERATOR_ELEVENTH(300701,"登录页，点击下一步"),
    OPERATOR_TWELFTH(300702,"登录页，点击忘记密码"),
    OPERATOR_FOURTEENTH(300703,"登录页，点击返回");

    private Integer code;
    private String text;

    ETaskBuryPoint(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public static Integer getCode(String text) {
        if (StringUtils.isNotEmpty(text)) {
            for (ETaskBuryPoint item : ETaskBuryPoint.values()) {
                if (text.equals(item.getText())) {
                    return item.getCode();
                }
            }
        }
        return -1;
    }

    public static String getText(Integer code) {
        for (ETaskBuryPoint item : ETaskBuryPoint.values()) {
            if (code.equals(item.getCode())) {
                return item.getText();
            }
        }
        return "";
    }

}
