package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.vo.AppBizTypeVO;

import java.util.List;

/**
 * Created by haojiahong on 2017/7/4.
 */
public interface AppBizTypeService {

    List<AppBizTypeVO> getBizList();

    List<AppBizTypeVO> getBizListByAppId(String appId);

    List<AppBizTypeVO> getTaskBizTypeList();

    List<AppBizTypeVO> getAccessTaskBizTypeList();

    List<AppBizTypeVO> getAccessTaskDetailBizTypeList();


}
