/*
 * Copyright © 2015 - 2017 杭州大树网络技术有限公司. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.treefinance.saas.console.share.cache;

import com.treefinance.b2b.saas.context.conf.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisServiceImpl implements RedisService {

    private static final int REDIS_KEY_TIMEOUT = PropertiesConfiguration.getInstance().getInt("platform.redisKey.timeout", 600);
    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void enqueue(String key, String value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public String dequeue(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    @Override
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean setExpiredValueQuietly(String key, final String value) {
        return setValueQuietly(key, value, REDIS_KEY_TIMEOUT);
    }

    @Override
    public boolean setValueQuietly(String key, String value) {
        try {
            setValue(key, value);
            return true;
        } catch (Exception e) {
            logger.error("Error setting value into redis!", e);
        }
        return false;
    }

    @Override
    public boolean setValueQuietly(String key, String value, long ttlSeconds) {
        return setValueQuietly(key, value, ttlSeconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean setValueQuietly(String key, String value, long timeout, TimeUnit unit) {
        try {
            setValue(key, value, timeout, unit);
            return true;
        } catch (Exception e) {
            logger.error("Error setting value into redis!", e);
        }
        return false;
    }

    @Override
    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void setValue(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    @Override
    public boolean deleteQuietly(String key) {
        try {
            delete(key);
            return true;
        } catch (Exception e) {
            logger.error("Error deleting redis key!", e);
        }
        return true;
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public Long[] increaseRequestLimitCounter(String ip, long[] times, TimeUnit[] timeUnits) {
        final int length = times.length;
        Long[] ret = new Long[length];
        for (int i = 0; i < length; i++) {
            final TimeUnit timeUnit = timeUnits[i];
            final long time = times[i];
            final byte[] key = RedisKey.getLimitCounterKey(ip, time, timeUnit).getBytes();
            ret[i] = redisTemplate.execute((RedisCallback<Long>)connection -> {

                final Long count = connection.incr(key);
                connection.expire(key, timeUnit.toSeconds(time));
                return count;
            });
        }
        return ret;
    }
}
