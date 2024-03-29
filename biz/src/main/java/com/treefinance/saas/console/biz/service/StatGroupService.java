package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.common.domain.vo.StatGroupVO;
import com.treefinance.saas.knife.result.SaasResult;

import java.util.List;
import java.util.Set;

/**
 * @author:guoguoyun
 * @date:Created in 2018/4/27上午11:08
 */
public interface StatGroupService {
    SaasResult<List<StatGroupVO>> queryStatGroup(StatGroupVO statGroupVO);

    SaasResult<Boolean> addOrUpdateStatGroup(StatGroupVO statGroupVO);

    SaasResult<Set<Integer>> queryAllgroupIndex();
}
