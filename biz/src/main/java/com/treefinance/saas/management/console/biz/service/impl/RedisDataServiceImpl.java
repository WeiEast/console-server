package com.treefinance.saas.management.console.biz.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.treefinance.saas.management.console.biz.service.RedisDataService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yh-treefinance on 2017/11/15.
 */
@Service
public class RedisDataServiceImpl implements RedisDataService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<Map<String, String>> queryKeyList(String keys, Integer pageNumber, Integer pageSize) {
        List<String> keyList = Lists.newArrayList();
        // 1.key 查询
        try {
            if (StringUtils.isEmpty(keys)) {
                keys = "*";
            }
            if (!keys.contains("*")) {
                keys = keys.concat("*");
            }
            if (pageNumber == null || pageNumber < 0) {
                pageNumber = 0;
            }
            if (pageSize == null || pageSize < 0) {
                pageSize = 100;
            }
            ScanOptions options = ScanOptions.scanOptions().match(keys).count(pageSize).build();
            RedisConnectionFactory factory = stringRedisTemplate.getConnectionFactory();
            RedisConnection rc = factory.getConnection();
            Cursor<byte[]> cursor = rc.scan(options);

            int tmpIndex = 0;
            int startIndex = pageNumber * pageSize;
            int end = (pageNumber + 1) * pageSize;
            while (cursor.hasNext()) {
                if (tmpIndex >= startIndex && tmpIndex < end) {
                    keyList.add(new String(cursor.next()));
                    tmpIndex++;
                    continue;
                }
                // 获取到满足条件的数据后,就可以退出了
                if (tmpIndex >= end) {
                    break;
                }
                tmpIndex++;
                cursor.next();
            }
            RedisConnectionUtils.releaseConnection(rc, factory);
        } catch (Exception e) {
            logger.error("scan redis key exception:", e);
        }
        // 2.填充类型
        List<Map<String, String>> resultList = Lists.newArrayList();
        keyList.forEach(key -> {
            Map<String, String> keyMap = Maps.newHashMap();
            keyMap.put("key", key);
            DataType dataType = stringRedisTemplate.type(key);
            if (dataType != null) {
                keyMap.put("type", dataType.code());
                resultList.add(keyMap);
            }
        });
        return resultList;
    }


    @Override
    public Set<String> getRedisSet(String key) {
        return stringRedisTemplate.boundSetOps(key).members();
    }

    @Override
    public Set<String> getRedisZset(String key) {
        return stringRedisTemplate.boundZSetOps(key).range(0, -1);
    }

    @Override
    public List<String> getRedisList(String key) {
        return stringRedisTemplate.boundListOps(key).range(0, -1);
    }

    @Override
    public Object getRedisObject(String key) {
        return stringRedisTemplate.boundValueOps(key).get();
    }

    @Override
    public Map<Object, Object> getRedisMap(String key) {
        return stringRedisTemplate.boundHashOps(key).entries();
    }
}
