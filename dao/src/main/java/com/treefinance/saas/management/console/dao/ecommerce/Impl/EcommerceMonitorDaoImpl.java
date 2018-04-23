package com.treefinance.saas.management.console.dao.ecommerce.Impl;

import com.alibaba.dubbo.rpc.RpcException;
import com.treefinance.saas.management.console.common.utils.DataConverterUtils;
import com.treefinance.saas.management.console.dao.ecommerce.EcommerceMonitorDao;
import com.treefinance.saas.management.console.dao.entity.AppBizLicense;
import com.treefinance.saas.management.console.dao.entity.AppBizLicenseCriteria;
import com.treefinance.saas.management.console.dao.entity.MerchantBase;
import com.treefinance.saas.management.console.dao.entity.MerchantBaseCriteria;
import com.treefinance.saas.management.console.dao.mapper.AppBizLicenseMapper;
import com.treefinance.saas.management.console.dao.mapper.MerchantBaseMapper;
import com.treefinance.saas.merchant.center.facade.request.console.QueryAppBizLicenseByBizTypeRequest;
import com.treefinance.saas.merchant.center.facade.request.console.QueryMerchantByBizTypeRequest;
import com.treefinance.saas.merchant.center.facade.request.grapserver.QueryMerchantByAppIdRequest;
import com.treefinance.saas.merchant.center.facade.result.console.AppBizLicenseResult;
import com.treefinance.saas.merchant.center.facade.result.console.MerchantBaseResult;
import com.treefinance.saas.merchant.center.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.center.facade.service.AppBizLicenseFacade;
import com.treefinance.saas.merchant.center.facade.service.MerchantBaseInfoFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.swing.plaf.basic.BasicIconFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:guoguoyun
 * @date:Created in 2018/1/16下午8:33
 */
@Repository
public class EcommerceMonitorDaoImpl implements EcommerceMonitorDao {

    private final static Logger logger = LoggerFactory.getLogger(EcommerceMonitorDaoImpl.class);


    @Autowired
    AppBizLicenseFacade appBizLicenseFacade;

    @Autowired
    MerchantBaseInfoFacade merchantBaseInfoFacade;


    @Override
    public List<MerchantBase> queryAllEcommerceListByBizeType(Integer bizType) {

        QueryMerchantByBizTypeRequest request = new QueryMerchantByBizTypeRequest();
        request.setBizType(bizType);


        MerchantResult<List<MerchantBaseResult>> rpcResult;

        try {
            logger.info("请求商户中心，request：{}",request);
            rpcResult = merchantBaseInfoFacade.queryMerchantByBizType(request);
        }catch (RpcException e){
            logger.error("根据业务类型获取列表数据失败：{}",e.getMessage());
            return new ArrayList<>();
        }

        if(rpcResult.isSuccess()){
            List<MerchantBaseResult> list = new ArrayList<>();
            return DataConverterUtils.convert(list,MerchantBase.class);
        }
        logger.error("请求商户中心，根据业务类型获取商户列表失败，{}",rpcResult.getRetMsg());
        return new ArrayList<>();
    }
}
