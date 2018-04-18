package com.treefinance.saas.management.console.common.domain;

import com.treefinance.saas.knife.common.StateCode;

/**
 * Buddha Bless , No Bug !
 *
 * @author haojiahong
 * @date 2018/4/16
 */
public interface ConsoleStateCode {

    StateCode REPEAT_REQUEST_ERROR = new StateCode(-2001, "请求频繁");
    StateCode DOWNLOAD_ERROR = new StateCode(-2002, "下载失败");
}
