package com.treefinance.saas.console.biz.service.impl;

import com.google.common.collect.Lists;
import com.treefinance.saas.console.biz.service.BasicDataService;
import com.treefinance.saas.console.common.domain.vo.BasicDataHistoryVO;
import com.treefinance.saas.console.common.domain.vo.BasicDataVO;
import com.treefinance.saas.console.share.adapter.AbstractServiceAdapter;
import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.request.PageRequest;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.monitor.facade.domain.base.BaseRequest;
import com.treefinance.saas.monitor.facade.domain.request.BasicDataRequest;
import com.treefinance.saas.monitor.facade.domain.request.autostat.BasicDataHistoryRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.BasicDataRO;
import com.treefinance.saas.monitor.facade.domain.ro.autostat.BasicDataHistoryRO;
import com.treefinance.saas.monitor.facade.service.autostat.BasicDataFacade;
import com.treefinance.saas.monitor.facade.service.autostat.BasicDataHistoryFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author guoguoyun
 * @date 2018/4/23下午5:12
 */
@Service
public class BasicDataServiceImpl extends AbstractServiceAdapter implements BasicDataService {
    private static final Logger logger = LoggerFactory.getLogger(BasicDataService.class);

    @Autowired
    private BasicDataFacade basicDataFacade;

    @Autowired
    private BasicDataHistoryFacade basicDataHistoryFacade;

    @Override
    public SaasResult<Map<String, Object>> queryAllBasicData(PageRequest pageRequest) {
        com.treefinance.saas.monitor.facade.domain.base.PageRequest _pageRequest = new com.treefinance.saas.monitor.facade.domain.base.PageRequest();
        this.copyProperties(pageRequest, _pageRequest);
        MonitorResult<List<BasicDataRO>> monitorResult = basicDataFacade.queryAllBasicData(_pageRequest);
        if (monitorResult.getData() == null) {
            logger.info("查询基础数据列表为空,{}", monitorResult.getErrorMsg());
            return Results.newFailedResult(CommonStateCode.NO_RELATED_DATA);

        }
        List<BasicDataVO> basicDataVOList = this.convertList(monitorResult.getData(), BasicDataVO.class);

        return Results.newPageResult(pageRequest, monitorResult.getTotalCount(), basicDataVOList);

    }

    @Override
    public SaasResult<Boolean> addBasciData(BasicDataVO basicDataVO) {

        BasicDataRequest basicDataRequest = new BasicDataRequest();
        this.copyProperties(basicDataVO, basicDataRequest);
        MonitorResult<Boolean> monitorResult = basicDataFacade.addBasicData(basicDataRequest);
        if (!monitorResult.getData()) {
            logger.error("插入基础数据错误");
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }
        return Results.newSuccessResult(monitorResult.getData());

    }

    @Override
    public SaasResult<Boolean> updateBasciData(BasicDataVO basicDataVO) {
        BasicDataRequest basicDataRequest = new BasicDataRequest();
        this.copyProperties(basicDataVO, basicDataRequest);
        MonitorResult<Boolean> monitorResult = basicDataFacade.updateBasicData(basicDataRequest);
        if (!monitorResult.getData()) {
            logger.error("更新基础数据失败");
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }
        return Results.newSuccessResult(monitorResult.getData());

    }

    @Override
    public SaasResult<List<String>> querydataName(BaseRequest baseRequest) {

        MonitorResult<List<String>> monitorResult = basicDataFacade.queryAllDataName(baseRequest);
        if (CollectionUtils.isEmpty(monitorResult.getData())) {
            logger.error("查找数据名字为空");
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }

        logger.info("查询到的数据名字为{}", monitorResult.getData().toString());
        return Results.newSuccessResult(monitorResult.getData());
    }

    @Override
    public SaasResult<String> getdataNameById(BasicDataVO basicDataVO) {
        if (basicDataVO.getId() == null) {
            logger.info("查询基础数据  传入参数ID为空");
            throw new IllegalArgumentException("请求参数不能为空！");

        }
        BasicDataRequest basicDataRequest = new BasicDataRequest();
        basicDataRequest.setId(basicDataVO.getId());
        MonitorResult<BasicDataRO> monitorResult = basicDataFacade.getBasicDataById(basicDataRequest);
        if (StringUtils.isEmpty(monitorResult.getData())) {
            logger.error("查找数据名字为空");
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }

        logger.info("查询到的数据名字为{}", monitorResult.getData().getDataName());
        return Results.newSuccessResult(monitorResult.getData().getDataName());

    }

    /**
     * 查询历史
     *
     * @param request
     * @return
     */
    @Override
    public SaasResult<Map<String, Object>> queryHistory(BasicDataHistoryRequest request) {
        MonitorResult<List<BasicDataHistoryRO>> result = basicDataHistoryFacade.queryList(request);
        List<BasicDataHistoryVO> list = Lists.newArrayList();
        if (result != null && !CollectionUtils.isEmpty(result.getData())) {
            list = this.convertList(result.getData(), BasicDataHistoryVO.class);
        }
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNumber(request.getPageNumber());
        pageRequest.setPageSize(request.getPageSize());
        return Results.newPageResult(pageRequest, result.getTotalCount(), list);
    }
}
