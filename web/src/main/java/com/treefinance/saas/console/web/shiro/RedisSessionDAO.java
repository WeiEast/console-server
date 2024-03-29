/**
 * Copyright © 2017 Treefinance All Rights Reserved
 */
package com.treefinance.saas.console.web.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenjh on 2017/6/12.
 * <p>
 * redis实现共享session
 */
public class RedisSessionDAO extends EnterpriseCacheSessionDAO {
    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);

    /**
     * session 在redis过期时间是30天
     */
    private static int expireTime = 30 * 24 * 60 * 60;

    private static String prefix = "console-shiro-session:";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 创建session，保存到redis
     *
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        logger.debug("创建session:{}", session.getId());
        redisTemplate.opsForValue().set(prefix + sessionId.toString(), session);
        return sessionId;
    }

    /**
     * 获取session
     *
     * @param sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.debug("获取session:{}", sessionId);
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = super.doReadSession(sessionId);
        if (session == null) {
            session = (Session) redisTemplate.opsForValue().get(prefix + sessionId.toString());
        }
        return session;
    }

    /**
     * 更新session的最后一次访问时间
     *
     * @param session
     */
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        logger.debug("获取session:{}", session.getId());
        String key = prefix + session.getId().toString();
        redisTemplate.opsForValue().set(key, session);
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 删除session
     *
     * @param session
     */
    @Override
    protected void doDelete(Session session) {
        logger.debug("删除session:{}", session.getId());
        super.doDelete(session);
        redisTemplate.delete(prefix + session.getId().toString());
    }
}
