package com.treefinance.saas.management.console.web.controller.rawdata;

import com.google.common.collect.Maps;
import com.treefinance.saas.management.console.biz.service.rawdata.ProxyProviderService;
import com.treefinance.saas.management.console.common.domain.vo.rawdata.ProxyCatVO;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

/**
 * Created by haojiahong on 2017/8/30.
 */
@RestController
@RequestMapping("/saas/console/rawdata/proxy")
public class ProxyProviderController {

    @Autowired
    private ProxyProviderService proxyProviderService;

    @RequestMapping(value = "/user/list", method = {RequestMethod.GET}, produces = "application/json")
    public Result<List<String>> queryUserList() {
        return Results.newSuccessResult(proxyProviderService.queryUserList());
    }

    @RequestMapping(value = "/cat", method = {RequestMethod.GET}, produces = "application/json")
    public Result<ProxyCatVO> queryAll(String user) {
        return Results.newSuccessResult(proxyProviderService.queryProxyCat(user));
    }

    @GetMapping("forward")
    ModelAndView forward() {
        StringBuilder url = new StringBuilder("/saas/console/rawdata/proxy/hello");
        url.append("?").append("username=hao").append("&message=msg");
        ModelAndView mv = new ModelAndView("forward:" + url.toString());
//        mv.addObject("username", "hao");
//        mv.addObject("message", "message");
        return mv;
    }


    @GetMapping("redirectView")
    RedirectView redirectView() {
        return new RedirectView("/saas/console/rawdata/proxy/hello");
    }


    @GetMapping("hello")
    public Map<String, String> hello(String username, String message) {
        Map<String, String> map = Maps.newHashMap();
        map.put("username", username);
        map.put("message", message);
        return map;
    }

}
