package com.treefinance.saas.management.console.biz.service.impl;

import com.treefinance.saas.management.console.biz.service.BasicDataService;
import com.treefinance.saas.management.console.common.domain.vo.BasicDataVO;
import com.treefinance.saas.management.console.common.result.CommonStateCode;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.monitor.facade.domain.base.BaseRequest;
import com.treefinance.saas.monitor.facade.domain.base.PageRequest;
import com.treefinance.saas.monitor.facade.domain.request.BasicDataRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.BasicDataRO;
import com.treefinance.saas.monitor.facade.service.BasicDataFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author:guoguoyun
 * @date:Created in 2018/4/23下午5:12
 */
@Service
public class BasicDataServiceImpl implements BasicDataService {
    private static final Logger logger = LoggerFactory.getLogger(BasicDataService.class);

    @Autowired
    BasicDataFacade basicDataFacade;


    @Override
    public Result<Map<String, Object>> queryAllBasicData(PageRequest pageRequest) {


        MonitorResult<List<BasicDataRO>> monitorResult = basicDataFacade.queryAllBasicData(pageRequest);
        if (monitorResult.getData() == null) {
            logger.info("查询基础数据列表为空", monitorResult.getErrorMsg());
            return Results.newFailedResult(CommonStateCode.NO_RELATED_DATA);

        }
        List<BasicDataVO>  basicDataVOList = BeanUtils.convertList(monitorResult.getData(),BasicDataVO.class);
        com.treefinance.saas.management.console.common.result.PageRequest pageRequests = new com.treefinance.saas.management.console.common.result.PageRequest();
        BeanUtils.copyProperties(pageRequest, pageRequests);

        return Results.newSuccessPageResult(pageRequests, monitorResult.getTotalCount(), basicDataVOList);


    }


    @Override
    public Result<Boolean> addBasciData(BasicDataVO basicDataVO) {

        BasicDataRequest basicDataRequest = new BasicDataRequest();
        BeanUtils.copyProperties(basicDataVO, basicDataRequest);
        MonitorResult<Boolean> monitorResult = basicDataFacade.addBasicData(basicDataRequest);
        if (monitorResult.getData() == false) {
            logger.error("插入基础数据错误");
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }
        return Results.newSuccessResult(monitorResult.getData());

    }

    @Override
    public Result<Boolean> updateBasciData(BasicDataVO basicDataVO) {
        BasicDataRequest basicDataRequest = new BasicDataRequest();
        BeanUtils.copyProperties(basicDataVO, basicDataRequest);
        MonitorResult<Boolean> monitorResult = basicDataFacade.updateBasicData(basicDataRequest);
        if (monitorResult.getData() == false) {
            logger.error("更新基础数据失败");
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }
        return Results.newSuccessResult(monitorResult.getData());


    }

    @Override
    public Result<List<String>> querydataName(BaseRequest baseRequest) {

        MonitorResult<List<String>> monitorResult = basicDataFacade.queryAllDataName(baseRequest);
        if (CollectionUtils.isEmpty(monitorResult.getData())) {
            logger.error("查找数据名字为空");
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }

        logger.info("查询到的数据名字为{}", monitorResult.getData().toString());
        return Results.newSuccessResult(monitorResult.getData());
    }


    @Override
    public Result<String> getdataNameById(BasicDataVO basicDataVO) {
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
}
