package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.vo.StatGroupVO;
import com.treefinance.saas.management.console.common.result.Result;

import java.util.List;
import java.util.Set;

/**
 * @author:guoguoyun
 * @date:Created in 2018/4/27上午11:08
 */
public interface StatGroupService {
    Result<List<StatGroupVO>> queryStatGroup(StatGroupVO statGroupVO);

    Result<Boolean> addOrUpdateStatGroup(StatGroupVO statGroupVO);

    Result<Set<Integer>> queryAllgroupIndex();
}
