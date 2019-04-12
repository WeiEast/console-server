package com.treefinance.saas.console.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.treefinance.saas.console.biz.service.MerchantFunctionService;
import com.treefinance.saas.console.common.domain.vo.MerchantFunctionVO;
import com.treefinance.saas.console.exception.BizException;
import com.treefinance.saas.knife.request.PageRequest;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.merchant.facade.request.console.MerchantFunctionRequest;
import com.treefinance.saas.merchant.facade.result.console.MerchantFunctionResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/insert", method = RequestMethod.POST, produces = "application/json")
    public SaasResult<Integer> insert(@RequestBody MerchantFunctionRequest request) {
        logger.info("增加埋点功能参数request={}", JSONObject.toJSONString(request));
        if (request == null || StringUtils.isBlank(request.getAppId()) || request.getSync() == null || StringUtils.isBlank(request.getSyncUrl())) {
            throw new BizException("请求参数不能为空");
        }
        MerchantFunctionVO result = merchantFunctionService.getMerchantFunctionByAppId(request);
        if (result != null) {
            throw new BizException("此商户已经存在");
        }
        return merchantFunctionService.insert(request);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    public SaasResult<Integer> update(@RequestBody MerchantFunctionRequest request) {
        logger.info("更新埋点功能参数request={}", JSONObject.toJSONString(request));
        if (request == null || StringUtils.isBlank(request.getAppId()) || request.getSync() == null || StringUtils.isBlank(request.getSyncUrl())) {
            throw new BizException("请求参数不能为空");
        }
        return merchantFunctionService.update(request);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json")
    public SaasResult<Map<String, Object>> queryMerchantFunctionList(@RequestBody PageRequest request) {
        logger.info("获取埋点商户列表参数request={}", JSONObject.toJSONString(request));
        if (request == null) {
            throw new BizException("请求参数不能为空");
        }
        return merchantFunctionService.queryMerchantFunctionList(request);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public SaasResult<Integer> delete(@PathVariable Long id) {
        logger.info("删除埋点商户参数id={}", id);
        if (id == null) {
            throw new BizException("请求参数不能为空");
        }
        return merchantFunctionService.delete(id);
    }

    @RequestMapping(value = "/searchById/{id}", method = RequestMethod.GET)
    public SaasResult<MerchantFunctionResult> searchByAppId(@PathVariable Long id) {
        logger.info("获取埋点商户参数id={}", id);
        if (id == null) {
            throw new BizException("请求参数不能为空");
        }
        return merchantFunctionService.searchByAppId(id);
    }

}
