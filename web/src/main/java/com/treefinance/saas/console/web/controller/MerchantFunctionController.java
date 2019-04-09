package com.treefinance.saas.console.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.treefinance.saas.console.biz.service.MerchantFunctionService;
import com.treefinance.saas.console.common.domain.vo.MerchantFunctionVO;
import com.treefinance.saas.console.exception.BizException;
import com.treefinance.saas.knife.request.PageRequest;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.merchant.facade.request.console.MerchantFunctionRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 张琰佳
 * @since 2:20 PM 2019/3/5
 */
@RestController
@RequestMapping("/saas/console/merchant/function")
public class MerchantFunctionController {
    private static final Logger logger = LoggerFactory.getLogger(MerchantFunctionController.class);

    @Autowired
    private MerchantFunctionService merchantFunctionService;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public SaasResult<Integer> insert(MerchantFunctionRequest request) {
        if (request == null || StringUtils.isBlank(request.getAppId()) || request.getSync() == null || StringUtils.isBlank(request.getSyncUrl())) {
            throw new BizException("请求参数不能为空");
        }
        logger.info("增加埋点功能参数request={}", JSONObject.toJSONString(request));
        MerchantFunctionVO result = merchantFunctionService.getMerchantFunctionByAppId(request);
        if (result != null) {
            throw new BizException("此商户已经存在");
        }
        return merchantFunctionService.insert(request);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public SaasResult<Integer> update(MerchantFunctionRequest request) {
        if (request == null || StringUtils.isBlank(request.getAppId()) || request.getSync() == null || StringUtils.isBlank(request.getSyncUrl())) {
            throw new BizException("请求参数不能为空");
        }
        logger.info("更新埋点功能参数request={}", JSONObject.toJSONString(request));
        return merchantFunctionService.update(request);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public SaasResult<Map<String, Object>> queryMerchantFunctionList(PageRequest request) {
        if (request == null) {
            throw new BizException("请求参数不能为空");
        }
        logger.info("获取埋点商户类表参数request={}", JSONObject.toJSONString(request));
        return merchantFunctionService.queryMerchantFunctionList(request);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public SaasResult<Integer> delete(MerchantFunctionRequest request) {
        if (request == null || request.getId() == null) {
            throw new BizException("请求参数不能为空");
        }
        logger.info("获取埋点商户类表参数request={}", JSONObject.toJSONString(request));
        return merchantFunctionService.delete(request);
    }

}
