package com.treefinance.saas.management.console.web.controller;

import com.google.common.collect.Maps;
import com.treefinance.saas.knife.request.PageRequest;
import com.treefinance.saas.knife.result.Results;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Buddha Bless , No Bug !
 *
 * @author haojiahong
 * @date 2018/4/12
 */
@RestController
@RequestMapping("/saas/console/test")
public class TestResultController {

    private static Map<String, String> dataMap = Maps.newHashMap();

    static {
        dataMap.put("value", "这是数据体");
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public Object success() {
        return Results.newSuccessResult(dataMap);
    }

    @RequestMapping(value = "/success/text", method = RequestMethod.GET)
    public Object successText() {
        return Results.newSuccessResult(dataMap, "自定义statusText");
    }


    @RequestMapping(value = "/success/page", method = RequestMethod.GET)
    public Object successPage() {
        PageRequest request = new PageRequest();
        return Results.newPageResult(request, 100, dataMap);
    }

    @RequestMapping(value = "/success/page/text", method = RequestMethod.GET)
    public Object successPageText() {
        PageRequest request = new PageRequest();
        return Results.newPageResult(request, 100, dataMap, "自定义statusText");
    }


}
