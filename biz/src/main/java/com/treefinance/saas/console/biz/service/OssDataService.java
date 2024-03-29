package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.common.domain.request.OssDataRequest;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by haojiahong on 2017/11/21.
 */
public interface OssDataService {

    /**
     * 查询oss回调数据列表
     *
     * @param request
     * @return
     */
    Object getOssCallbackDataList(OssDataRequest request);

    Object downloadOssData(Long id, HttpServletRequest request, HttpServletResponse response);

    Object downloadOssDataCheck(Long id);
}