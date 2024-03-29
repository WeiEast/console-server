package com.treefinance.saas.console.web.advice;

import com.treefinance.saas.console.context.annotations.RequestLimit;
import com.treefinance.saas.console.exception.RequestLimitException;
import com.treefinance.saas.console.share.cache.RedisService;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by haojiahong on 2017/6/29.
 */
@Aspect
public class RequestLimitContract {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisService redisService;

    @Pointcut("execution(* com.treefinance.saas.console.web.controller..*Controller.*(..))")
    public void controllerMethodPointcut() {
    }

    @Before("controllerMethodPointcut()&&@annotation(limit)")
    public void requestLimit(final JoinPoint joinPoint, RequestLimit limit) throws RequestLimitException {

        Object[] args = joinPoint.getArgs();
        ShiroHttpSession session = null;
        for (Object arg : args) {
            if (arg instanceof ShiroHttpSession) {
                session = (ShiroHttpSession) arg;
                break;
            }
        }

        if (session == null) {
            throw new RequestLimitException("请求频繁，请稍后重试");
        }

        String ip = session.getSession().getHost();
        final Long[] limits = redisService.increaseRequestLimitCounter(ip,
                limit.times(), limit.timeUnits());
        final int[] counts = limit.counts();
        for (int i = 0; i < limits.length; i++) {
            if (limits[i] > counts[i]) {
                logger.error("ip={}请求频繁", ip);
                throw new RequestLimitException("请求频繁，请稍后重试");
            }
        }
    }


}
