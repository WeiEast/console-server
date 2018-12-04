package com.treefinance.saas.console.web.controller.common;

import com.google.common.collect.Lists;
import com.treefinance.saas.console.common.domain.vo.common.SaasEnvVO;
import com.treefinance.saas.knife.result.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Buddha Bless , No Bug !
 *
 * @author haojiahong
 * @date 2018/4/8
 */
@RestController
@RequestMapping("/saas/console/common/drop/list")
public class DropListController {

    public static final Logger logger = LoggerFactory.getLogger(DropListController.class);

    /**
     * saas环境下拉列表
     *
     * @return
     */
    @RequestMapping(value = "/saas/env", method = RequestMethod.GET)
    public Object getSaasEvnDropList() {
        List<SaasEnvVO> list = Lists.newArrayList();
        SaasEnvVO allEnv = new SaasEnvVO();
        allEnv.setId(0);
        allEnv.setName("所有环境");

        SaasEnvVO productEnv = new SaasEnvVO();
        productEnv.setId(1);
        productEnv.setName("生产环境");

        SaasEnvVO preProductEnv = new SaasEnvVO();
        preProductEnv.setId(2);
        preProductEnv.setName("预发布环境");

        list.add(allEnv);
        list.add(productEnv);
        list.add(preProductEnv);
        return Results.newSuccessResult(list);
    }
}
