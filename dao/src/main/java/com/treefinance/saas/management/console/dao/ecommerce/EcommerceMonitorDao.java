package com.treefinance.saas.management.console.dao.ecommerce;

import com.treefinance.saas.management.console.dao.entity.MerchantBase;

import java.util.List;

/**
 * @author:guoguoyun
 * @date:Created in 2018/1/16下午8:31
 */
public interface EcommerceMonitorDao {
    List<MerchantBase> queryAllEcommerceListByBizeType(Integer bizType);
}
