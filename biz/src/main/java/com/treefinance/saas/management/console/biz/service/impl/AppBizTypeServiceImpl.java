package com.treefinance.saas.management.console.biz.service.impl;

import com.google.common.collect.Lists;
import com.treefinance.saas.management.console.biz.service.AppBizTypeService;
import com.treefinance.saas.management.console.common.domain.vo.AppBizTypeVO;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.dao.entity.AppBizType;
import com.treefinance.saas.management.console.dao.mapper.AppBizTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by haojiahong on 2017/7/4.
 */
@Service
public class AppBizTypeServiceImpl implements AppBizTypeService {

    @Autowired
    private AppBizTypeMapper appBizTypeMapper;

    @Override
    public List<AppBizTypeVO> getBizList() {
        List<AppBizTypeVO> appBizTypeVOList = Lists.newArrayList();
        List<AppBizType> appBizTypeList = appBizTypeMapper.selectByExample(null);
        if (CollectionUtils.isEmpty(appBizTypeList)) {
            return appBizTypeVOList;
        }
        appBizTypeVOList = BeanUtils.convertList(appBizTypeList, AppBizTypeVO.class);
        return appBizTypeVOList;
    }
}
