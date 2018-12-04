package com.treefinance.saas.console.manager;

import com.treefinance.saas.console.manager.domain.BizTypeBO;
import com.treefinance.saas.console.manager.domain.IdentifiedBizTypeBO;

import javax.annotation.Nonnull;

import java.util.List;
import java.util.Map;

/**
 * @author Jerry
 * @date 2018/11/27 10:07
 */
public interface BizTypeManager {

    List<BizTypeBO> listBizTypes();

    List<Byte> listBizTypeValues();

    List<IdentifiedBizTypeBO> listIdentifiedBizTypes();

    List<BizTypeBO> listBizTypesInValues(@Nonnull List<Byte> bizTypeList);


    Map<Byte, String> getBizTypeNameMapping();
}
