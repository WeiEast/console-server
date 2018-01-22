package com.treefinance.saas.management.console.biz.service.impl;

import com.treefinance.saas.dataservice.dataserver.tpapiconfig.facade.TpApiConfigFacade;
import com.treefinance.saas.management.console.biz.service.TpApiConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TpApiConfigServiceImpl implements TpApiConfigService{

    @Autowired
    private TpApiConfigFacade tpApiConfigFacade;

    @Override
    public Map<String, String> getTpApiConfigType() {
        return tpApiConfigFacade.getTpApiConfigType();
    }
}
