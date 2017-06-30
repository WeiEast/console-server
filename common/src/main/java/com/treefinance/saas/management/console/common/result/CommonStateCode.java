package com.treefinance.saas.management.console.common.result;

/**
 * 公共状态码
 * Created by haojiahong on 2017/6/26.
 */
public interface CommonStateCode {
    //基础状态码
    StateCode SUCCESS = new StateCode(0, "成功");
    StateCode FAILURE = new StateCode(-100, "系统异常");
    StateCode PARAMETER_LACK = new StateCode(-101, "请求参数缺失");
    StateCode ILLEGAL_PARAMETER = new StateCode(-102, "无效的参数");
    StateCode ILLEGAL_TOKEN = new StateCode(-103, "非法的token");
    StateCode NO_RELATED_DATA = new StateCode(-104, "无相关数据");
    StateCode DB_ERROR = new StateCode(-105, "数据库异常");

    //业务状态码
    StateCode LOGIN_VERIFY_ERROR = new StateCode(-1001, "登陆验证失败");
    StateCode NOT_LOGGED_IN = new StateCode(-1002, "用户未登陆");
    StateCode NO_PERMISSION = new StateCode(-1003, "用户无权限");
    StateCode ACCOUNT_DOES_NOT_EXIST = new StateCode(-1004, "账号不存在");
    StateCode ACCOUNT_NOT_ACTIVATED = new StateCode(-1005, "账号未激活");
    StateCode REPEAT_REQUEST_ERROR = new StateCode(-1006, "频繁请求,稍后重试");
}
