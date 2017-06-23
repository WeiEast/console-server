/**
 * Copyright © 2017 Treefinance All Rights Reserved
 */
package com.treefinance.saas.management.console.biz.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * Created by chenjh on 2017/6/12.
 * <p>
 * 实现shiro的CacheManager
 */
public class RedisCacheManager implements CacheManager {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new ShiroCache<>(name, redisTemplate);
    }
}
