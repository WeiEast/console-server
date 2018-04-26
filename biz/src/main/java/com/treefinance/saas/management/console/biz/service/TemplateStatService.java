package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.request.TemplateStatRequest;
import com.treefinance.saas.management.console.common.domain.vo.TemplateStatVO;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.monitor.facade.domain.base.PageRequest;

import java.util.Map;


/**
 * @author:guoguoyun
 * @date:Created in 2018/4/26上午9:48
 */
public interface TemplateStatService {
    Result<Map<String,Object>> queryTemplateStat(TemplateStatRequest templateStatRequest);

    Result<Boolean> addOrUpdateStatTemplate(TemplateStatVO templateStatVO);
}
