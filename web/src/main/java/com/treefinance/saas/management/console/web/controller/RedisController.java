package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.RedisDataService;
import com.treefinance.saas.management.console.common.result.CommonStateCode;
import com.treefinance.saas.management.console.common.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yh-treefinance on 2017/11/15.
 */
@RestController
@RequestMapping("/saas/console/redis")
public class RedisController {
    @Autowired
    private RedisDataService redisDataService;

    /**
     * 获取redis key
     *
     * @param keys
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping("/getKeys")
    public Object getKeys(String keys, Integer pageNumber, Integer pageSize) {
        Object data = redisDataService.queryKeyList(keys, pageNumber, pageSize);
        return Results.newSuccessResult(data);
    }

    /**
     * 获取redis数据
     *
     * @param key
     * @param type
     * @return
     */
    @RequestMapping("/getData")
    public Object getData(String key, String type) {
        Object data = null;
        if (DataType.NONE.code().equals(type)) {
            return Results.newFailedResult(CommonStateCode.NO_RELATED_DATA);
        } else if (DataType.LIST.code().equals(type)) {
            data = redisDataService.getRedisList(key);
        } else if (DataType.SET.code().equals(type)) {
            data = redisDataService.getRedisSet(key);
        } else if (DataType.ZSET.code().equals(type)) {
            data = redisDataService.getRedisZset(key);
        } else if (DataType.STRING.code().equals(type)) {
            data = redisDataService.getRedisObject(key);
        } else if (DataType.HASH.code().equals(type)) {
            data = redisDataService.getRedisMap(key);
        }
        return Results.newSuccessResult(data);
    }
}
