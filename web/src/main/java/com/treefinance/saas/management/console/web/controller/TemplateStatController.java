package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.TemplateStatService;
import com.treefinance.saas.management.console.common.domain.request.TemplateStatRequest;
import com.treefinance.saas.management.console.common.domain.vo.TemplateStatVO;
import com.treefinance.saas.management.console.common.result.Result;
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
public class TemplateStatController {

    private static  final Logger logger = LoggerFactory.getLogger(TemplateStatController.class);


    @Autowired
    TemplateStatService templateStatService;


    /**
     * 统计模板列表查询
     */
    @RequestMapping(value="query",method = RequestMethod.POST)
    public Result<Map<String,Object>>  queryTemplateStat(@RequestBody TemplateStatRequest templateStatRequest) {
        return templateStatService.queryTemplateStat(templateStatRequest);
    }

    /**
     * 新增或更新统计模板数据
     */
    @RequestMapping(value="addorupdate",method = RequestMethod.POST)
    public Result<Boolean>  queryTemplateStat(@RequestBody TemplateStatVO templateStatVO) {
        return templateStatService.addOrUpdateStatTemplate(templateStatVO);
    }





}
