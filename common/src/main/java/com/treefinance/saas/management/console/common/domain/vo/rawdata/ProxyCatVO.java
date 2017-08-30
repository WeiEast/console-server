package com.treefinance.saas.management.console.common.domain.vo.rawdata;

import java.io.Serializable;
import java.util.List;

/**
 * Created by haojiahong on 2017/8/30.
 */
public class ProxyCatVO implements Serializable {

    private static final long serialVersionUID = -3360788891303074528L;

    private ProxyRuleVO rule;
    private List<ProxyDetailsVO> proxies;

    public ProxyRuleVO getRule() {
        return rule;
    }

    public void setRule(ProxyRuleVO rule) {
        this.rule = rule;
    }

    public List<ProxyDetailsVO> getProxies() {
        return proxies;
    }

    public void setProxies(List<ProxyDetailsVO> proxies) {
        this.proxies = proxies;
    }
}
