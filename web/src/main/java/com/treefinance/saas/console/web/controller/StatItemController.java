package com.treefinance.saas.console.web.controller;

import com.treefinance.saas.console.biz.service.StatItemService;
import com.treefinance.saas.console.common.domain.vo.StatItemVO;
import com.treefinance.saas.knife.result.SaasResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author:guoguoyun
 * @date:Created in 2018/4/27上午10:57
 */
@RestController
@RequestMapping("/saas/console/monitor/stat/dataitem/")
public class StatItemController {

    @Autowired
    private StatItemService statItemService;


    @RequestMapping(value = "query", method = {RequestMethod.POST, RequestMethod.GET})
    public SaasResult<List<StatItemVO>> queryByTemplateId(@RequestBody StatItemVO statItemVO) {
        return statItemService.queryByTemplateId(statItemVO.getTemplateId());
    }


    @RequestMapping(value = "save", method = RequestMethod.POST)
    public SaasResult<Long> save(@RequestBody StatItemVO statItemVO) {
        return statItemService.saveStatItem(statItemVO);
    }
}
