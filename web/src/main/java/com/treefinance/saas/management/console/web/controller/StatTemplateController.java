package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.StatTemplateService;
import com.treefinance.saas.management.console.common.domain.request.StatTemplateRequest;
import com.treefinance.saas.management.console.common.domain.request.TestExpressionRequest;
import com.treefinance.saas.management.console.common.domain.request.TestRequest;
import com.treefinance.saas.management.console.common.domain.vo.TemplateStatVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author:guoguoyun
 * @date:Created in 2018/4/26上午9:44
 */
@RestController
@RequestMapping("/saas/console/monitor/stat/template/")
public class StatTemplateController {

    @Autowired
    private StatTemplateService templateStatService;


    /**
     * 统计模板列表查询
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public SaasResult<Map<String, Object>> queryTemplateStat(@RequestBody StatTemplateRequest templateStatRequest) {
        return templateStatService.queryStatTemplate(templateStatRequest);
    }

    /**
     * 新增或更新统计模板数据
     */
    @RequestMapping(value = "addorupdate", method = RequestMethod.POST)
    public SaasResult<Boolean> queryTemplateStat(@RequestBody TemplateStatVO templateStatVO) {
        return templateStatService.addOrUpdateStatTemplate(templateStatVO);
    }


    /**
     * testExpression
     */
    @RequestMapping(value = "testExpression", method = RequestMethod.POST)
    public Object testExpression(@RequestBody TestExpressionRequest request) {
        return templateStatService.testExpression(request);
    }

    /**
     * testExpression
     */
    @RequestMapping(value = "test", method = RequestMethod.POST)
    public Object test(@RequestBody TestRequest request) {
        return templateStatService.test(request);
    }

}
