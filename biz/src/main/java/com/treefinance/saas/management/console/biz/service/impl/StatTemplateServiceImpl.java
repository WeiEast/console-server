package com.treefinance.saas.management.console.biz.service.impl;

import com.google.common.collect.Lists;
import com.treefinance.saas.management.console.biz.service.StatTemplateService;
import com.treefinance.saas.management.console.common.domain.request.StatTemplateRequest;
import com.treefinance.saas.management.console.common.domain.vo.StatTemplateVO;
import com.treefinance.saas.management.console.common.domain.vo.TemplateStatVO;
import com.treefinance.saas.management.console.common.result.CommonStateCode;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.StatTemplateRO;
import com.treefinance.saas.monitor.facade.service.autostat.StatTemplateFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * @author:guoguoyun
 * @date:Created in 2018/4/26上午9:48
 */
@Service
public class StatTemplateServiceImpl implements StatTemplateService {


    private static final Logger logger = LoggerFactory.getLogger(StatTemplateService.class);

    @Autowired
    private StatTemplateFacade statTemplateFacade;

    @Override
    public Result<Map<String, Object>> queryStatTemplate(StatTemplateRequest templateStatRequest) {
        logger.info("统计模板列表查询，传入请求信息为{}", templateStatRequest.toString());
        com.treefinance.saas.monitor.facade.domain.request.StatTemplateRequest templateStatRequests = new com.treefinance.saas.monitor.facade.domain.request.StatTemplateRequest();
        BeanUtils.copyProperties(templateStatRequest, templateStatRequests);
        MonitorResult<List<StatTemplateRO>> monitorResult = statTemplateFacade.queryStatTemplate(templateStatRequests);

        List<StatTemplateVO> statTemplateVOList = Lists.newArrayList();
        if (monitorResult.getData() == null) {
            return Results.newSuccessPageResult(templateStatRequest, monitorResult.getTotalCount(), statTemplateVOList);
        }

        statTemplateVOList = BeanUtils.convertList(monitorResult.getData(), StatTemplateVO.class);
        return Results.newSuccessPageResult(templateStatRequest, monitorResult.getTotalCount(), statTemplateVOList);


    }

    @Override
    public Result<Boolean> addOrUpdateStatTemplate(TemplateStatVO templateStatVO) {

        if (templateStatVO.getStatus() == null || templateStatVO.getBasicDataFilter() == null || templateStatVO.getTemplateName() == null || templateStatVO.getBasicDataId() == null || templateStatVO.getEffectiveTime() == null || templateStatVO.getExpressionType() == null || templateStatVO.getFlushDataCron() == null ||
                templateStatVO.getStatCron() == null || templateStatVO.getTemplateCode() == null) {
            logger.error("操作统计模板数据，数据参数不能为空", templateStatVO.toString());
            throw new IllegalArgumentException("请求参数不能为空！");
        }
        com.treefinance.saas.monitor.facade.domain.request.StatTemplateRequest templateStatRequest = new com.treefinance.saas.monitor.facade.domain.request.StatTemplateRequest();
        BeanUtils.copyProperties(templateStatVO, templateStatRequest);
        MonitorResult<Boolean> monitorResult;
        if (templateStatVO.getId() == null) {
            logger.info("插入模板数据，传入的信息为{}", templateStatVO.toString());
            monitorResult = statTemplateFacade.addStatTemplate(templateStatRequest);
        } else {
            logger.info("更新模板数据，传入的信息为{}", templateStatVO.toString());
            monitorResult = statTemplateFacade.updateStatTemplate(templateStatRequest);
        }
        if (monitorResult.getData() == false) {
            logger.error("模板数据操作错误");
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }
        return Results.newSuccessResult(monitorResult.getData());
    }


}
