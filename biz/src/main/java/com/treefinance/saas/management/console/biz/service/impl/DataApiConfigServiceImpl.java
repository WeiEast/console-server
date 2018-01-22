package com.treefinance.saas.management.console.biz.service.impl;

import com.treefinance.saas.dataservice.dataserver.dataapiconfig.facade.DataApiConfigFacade;
import com.treefinance.saas.management.console.biz.service.DataApiConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DataApiConfigServiceImpl implements DataApiConfigService{

    @Autowired
    private DataApiConfigFacade dataApiConfigFacade;

    @Override
    public Map<String, String> getDsApiConfigType() {
        return dataApiConfigFacade.getDataApiConfigType();
    }
}
