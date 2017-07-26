package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.AppCallbackConfigService;
import com.treefinance.saas.management.console.common.domain.vo.AppBizTypeVO;
import com.treefinance.saas.management.console.common.domain.vo.AppCallbackConfigVO;
import com.treefinance.saas.management.console.common.result.PageRequest;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result<Map<String, Object>> getAppCallbackConfigList(PageRequest request) {
        Result<Map<String, Object>> result = appCallbackConfigService.getList(request);
        return result;
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET, produces = "application/json")
    public Result<AppCallbackConfigVO> getConfigById(@PathVariable Integer id) {
        AppCallbackConfigVO appCallbackConfigVO = appCallbackConfigService.getAppCallbackConfigById(id);
        return Results.newSuccessResult(appCallbackConfigVO);

    }

    @RequestMapping(value = "add", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public Result<Integer> addConfig(@RequestBody AppCallbackConfigVO appCallbackConfigVO) {
        Integer configId = appCallbackConfigService.add(appCallbackConfigVO);
        return Results.newSuccessResult(configId);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public Result<Boolean> updateConfig(@RequestBody AppCallbackConfigVO appCallbackConfigVO) {
        appCallbackConfigService.update(appCallbackConfigVO);
        return Results.newSuccessResult(Boolean.TRUE);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET, produces = "application/json")
    public Result<Boolean> deleteConfigById(@PathVariable Integer id) {
        appCallbackConfigService.deleteAppCallbackConfigById(id);
        return Results.newSuccessResult(true);

    }

    @RequestMapping(value = "biz/list", produces = "application/json", method = RequestMethod.GET)
    public Result<List<AppBizTypeVO>> getCallbackBizList() {
        List<AppBizTypeVO> result = appCallbackConfigService.getCallbackBizList();
        return Results.newSuccessResult(result);
    }


}
