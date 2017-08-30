package com.treefinance.saas.management.console.biz.service.rawdata.impl;

import com.google.common.collect.Lists;
import com.treefinance.proxy.api.ProxyProvider;
import com.treefinance.proxy.domain.ProxyCat;
import com.treefinance.proxy.domain.ProxyDetails;
import com.treefinance.proxy.domain.ProxyRule;
import com.treefinance.proxy.domain.ProxyStatus;
import com.treefinance.saas.management.console.biz.service.rawdata.ProxyProviderService;
import com.treefinance.saas.management.console.common.domain.vo.rawdata.ProxyCatVO;
import com.treefinance.saas.management.console.common.domain.vo.rawdata.ProxyDetailsVO;
import com.treefinance.saas.management.console.common.domain.vo.rawdata.ProxyRuleVO;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by haojiahong on 2017/8/30.
 */
@Service
public class ProxyProviderServiceImpl implements ProxyProviderService {

    @Autowired
    private ProxyProvider proxyProvider;

    @Override
    public List<String> queryUserList() {
        List<String> userList = proxyProvider.getUserList();
        return userList;
    }

    @Override
    public ProxyCatVO queryProxyCat(String user) {
        ProxyCat cat = proxyProvider.cat(user);
        ProxyCatVO catVO = new ProxyCatVO();

        ProxyRule rule = cat.getRule();
        ProxyRuleVO ruleVO = new ProxyRuleVO();
        BeanUtils.convert(rule, ruleVO);
        catVO.setRule(ruleVO);

        List<ProxyDetails> detailsList = cat.getProxies();
        List<ProxyDetailsVO> detailsVOList = Lists.newArrayList();
        for (ProxyDetails details : detailsList) {
            ProxyDetailsVO detailsVO = new ProxyDetailsVO();
            BeanUtils.convert(details, detailsVO);
            if (ProxyStatus.SERVICING.equals(details.getStatus())) {
                detailsVO.setStatus("使用中");
            }
            if (ProxyStatus.FINALIZING.equals(details.getStatus())) {
                detailsVO.setStatus("释放中");
            }
            detailsVOList.add(detailsVO);
        }
        catVO.setProxies(detailsVOList);
        return catVO;
    }
}
