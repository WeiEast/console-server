package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.management.console.biz.service.RedisDataService;
import com.treefinance.saas.management.console.common.domain.request.RedisRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * @return
     */
    @RequestMapping(value = "/getKeys", produces = "application/json", method = RequestMethod.POST)
    public Object getKeys(@RequestBody RedisRequest request) {
        Object data = redisDataService.queryKeyList(request.getKey(), request.getPageNumber(), request.getPageSize());
        return Results.newSuccessResult(data);
    }

    /**
     * 获取redis数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", produces = "application/json", method = RequestMethod.POST)
    public Object getData(@RequestBody RedisRequest request) {
        Object data = null;
        String key = request.getKey();
        String type = request.getType();
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
