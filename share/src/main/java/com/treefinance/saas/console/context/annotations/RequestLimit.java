package com.treefinance.saas.console.context.annotations;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Created by haojiahong on 2017/6/29.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface RequestLimit {

    int[] counts() default {Integer.MAX_VALUE};

    long[] times() default {1};

    TimeUnit[] timeUnits() default {TimeUnit.DAYS};

}
