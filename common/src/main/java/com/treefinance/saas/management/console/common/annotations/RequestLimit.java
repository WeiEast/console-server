package com.treefinance.saas.management.console.common.annotations;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;
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
