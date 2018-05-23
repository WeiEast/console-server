package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.request.StatTemplateRequest;
import com.treefinance.saas.management.console.common.domain.request.TestExpressionRequest;
import com.treefinance.saas.management.console.common.domain.request.TestRequest;
import com.treefinance.saas.management.console.common.domain.vo.TemplateStatVO;
import com.treefinance.saas.management.console.common.result.Result;

import java.util.Map;


/**
 * @author:guoguoyun
 * @date:Created in 2018/4/26上午9:48
 */
public interface StatTemplateService {
    /**
     * 模板查询
     *
     * @param templateStatRequest
     * @return
     */
    Result<Map<String, Object>> queryStatTemplate(StatTemplateRequest templateStatRequest);

    /**
     * 添加或修改模板
     *
     * @param templateStatVO
     * @return
     */
    Result<Boolean> addOrUpdateStatTemplate(TemplateStatVO templateStatVO);

    /**
     * 模板测试接口
     *
     * @param request
     * @return
     */
    Result<String> testExpression(TestExpressionRequest request);

    /**
     *
     * 模板测试接口
     *
     * @param request
     * @return
     */
    Result<Object> test(TestRequest request);
}
