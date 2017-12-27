package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.vo.AppBizTypeVO;
import com.treefinance.saas.management.console.dao.entity.AppBizType;

import java.util.List;
import java.util.Map;

/**
 * Created by haojiahong on 2017/7/4.
 */
public interface AppBizTypeService {

    List<AppBizTypeVO> getBizList();

    Map<Byte, String> getBizTypeNameMap();

    AppBizType getBizTypeByBizCodeIgnoreCase(String bizCode);

    List<AppBizTypeVO> getBizListByAppId(String appId);

    List<AppBizTypeVO> getTaskBizTypeList();

    List<AppBizTypeVO> getAccessTaskBizTypeList();

    List<AppBizTypeVO> getAccessTaskDetailBizTypeList();


}
