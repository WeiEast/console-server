package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.knife.request.PageRequest;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.AppCallbackConfigService;
import com.treefinance.saas.management.console.common.domain.vo.AppBizTypeVO;
import com.treefinance.saas.management.console.common.domain.vo.AppCallbackConfigVO;
import com.treefinance.saas.management.console.common.domain.vo.AppCallbackDataTypeVO;
import com.treefinance.saas.management.console.common.domain.vo.AppCallbackUrlVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by haojiahong on 2017/7/21.
 */
@RestController
@RequestMapping("/saas/console/callback")
public class AppCallbackConfigController {

    @Autowired
    private AppCallbackConfigService appCallbackConfigService;

    @RequestMapping(value = "list", method = RequestMethod.GET, produces = "application/json")
    public SaasResult<Map<String, Object>> getAppCallbackConfigList(PageRequest request) {
        SaasResult<Map<String, Object>> result = appCallbackConfigService.getList(request);
        return result;
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET, produces = "application/json")
    public SaasResult<AppCallbackConfigVO> getConfigById(@PathVariable Integer id) {
        AppCallbackConfigVO appCallbackConfigVO = appCallbackConfigService.getAppCallbackConfigById(id);
        return Results.newSuccessResult(appCallbackConfigVO);

    }

    @RequestMapping(value = "add", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public SaasResult<Integer> addConfig(@RequestBody AppCallbackConfigVO appCallbackConfigVO) {
        Integer configId = appCallbackConfigService.add(appCallbackConfigVO);
        return Results.newSuccessResult(configId);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public SaasResult<Boolean> updateConfig(@RequestBody AppCallbackConfigVO appCallbackConfigVO) {
        appCallbackConfigService.update(appCallbackConfigVO);
        return Results.newSuccessResult(Boolean.TRUE);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET, produces = "application/json")
    public SaasResult<Boolean> deleteConfigById(@PathVariable Integer id) {
        appCallbackConfigService.deleteAppCallbackConfigById(id);
        return Results.newSuccessResult(true);

    }

    @RequestMapping(value = "biz/list", produces = "application/json", method = RequestMethod.GET)
    public SaasResult<List<AppBizTypeVO>> getCallbackBizList() {
        List<AppBizTypeVO> result = appCallbackConfigService.getCallbackBizList();
        return Results.newSuccessResult(result);
    }

    @RequestMapping(value = "datatype/list", produces = "application/json", method = RequestMethod.GET)
    public SaasResult<List<AppCallbackDataTypeVO>> getDataTypeList() {
        List<AppCallbackDataTypeVO> result = appCallbackConfigService.getCallbackDataTypeList();
        return Results.newSuccessResult(result);
    }


    @RequestMapping(value = "url/validate", produces = "application/json", method = RequestMethod.POST)
    public SaasResult<Boolean> testUrl(@RequestBody AppCallbackUrlVO vo) {
        Boolean result = appCallbackConfigService.testUrl(vo.getUrl());
        return Results.newSuccessResult(result);
    }

}
