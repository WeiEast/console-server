package com.treefinance.saas.console.share.manager.dubbo;

import com.treefinance.commonservice.uid.UidService;
import com.treefinance.saas.console.context.exception.RpcServiceException;
import com.treefinance.saas.console.share.manager.GuidManager;
import com.treefinance.toolkit.util.Preconditions;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jerry
 * @date 2018/11/25 21:56
 */
@Service
public class UidServiceAdapter implements GuidManager {

    private final UidService uidService;

    @Autowired
    public UidServiceAdapter(UidService uidService) {
        this.uidService = uidService;
    }

    @Override
    public long generateUniqueId() {
        return uidService.getId();
    }

    @Override
    public long[] generateUniqueIds(int number) {
        Preconditions.isTrue(number> 0, "Incorrect input number!");

        long[] ids = uidService.getIds(number);

        if (ArrayUtils.isEmpty(ids) || ids.length != number) {
            throw new RpcServiceException("Unexpected guid-generating result!");
        }

        return ids;
    }
}
