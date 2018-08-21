package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.QuestionnaireStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:guoguoyun
 * @date:Created in 2018/8/21下午2:02
 */
@RestController
public class QuestionnaireStatController {

    @Autowired
    private QuestionnaireStatService questionnaireStatService;
}
