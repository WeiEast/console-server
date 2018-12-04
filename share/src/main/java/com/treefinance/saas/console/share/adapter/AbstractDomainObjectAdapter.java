package com.treefinance.saas.console.share.adapter;

import com.treefinance.saas.console.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Jerry
 * @date 2018/11/27 13:21
 */
public abstract class AbstractDomainObjectAdapter {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Nullable
    protected <S, T> T convert(@Nullable S bean, @Nonnull Class<T> clazz) {
        return BeanUtils.convert(bean, clazz);
    }

    @Nonnull
    protected <S, T> T convertStrict(@Nonnull S bean, @Nonnull Class<T> clazz) {
        return BeanUtils.convertStrict(bean, clazz);
    }

    protected <S, T> List<T> convert(@Nullable Collection<S> collection, @Nonnull Class<T> clazz) {
        return BeanUtils.convert(collection, clazz);
    }

    protected <S, T> List<T> convertList(@Nullable List<S> list, @Nonnull Class<T> clazz) {
        return BeanUtils.convert(list, clazz);
    }

    protected <S, T> void copy(@Nullable S source, @Nonnull T target) {
        BeanUtils.copy(source, target);
    }

    protected <S, T> void copyProperties(@Nonnull S source, @Nonnull T target) {
        BeanUtils.copyProperties(source, target);
    }

    protected <S, T> List<T> transform(@Nullable List<S> list, @Nonnull Function<? super S, ? extends T> mapper) {
        return BeanUtils.transform(list, mapper);
    }

    protected <S, T> List<T> transform(@Nullable List<S> list, @Nonnull Function<? super S, ? extends T> mapper, boolean distinct) {
        return BeanUtils.transform(list, mapper, distinct);
    }

    protected <S, K, V> Map<K, V> transformToMap(@Nullable List<S> list, @Nonnull Function<? super S, ? extends K> keyMapper,
        @Nonnull Function<? super S, ? extends V> valueMapper) {
        return BeanUtils.transformToMap(list, keyMapper, valueMapper);
    }

    protected <S, K, V> Map<K, V> transformToMap(@Nullable List<S> list, boolean distinct, @Nonnull Function<? super S, ? extends K> keyMapper,
        @Nonnull Function<? super S, ? extends V> valueMapper) {
        return BeanUtils.transformToMap(list, distinct, keyMapper, valueMapper);
    }

    protected <S, T, K, V> Map<K, V> transformToMap(@Nullable List<S> list, @Nonnull Function<? super S, ? extends T> mapper, boolean distinct,
        @Nonnull Function<? super T, ? extends K> keyMapper, @Nonnull Function<? super T, ? extends V> valueMapper) {
        return BeanUtils.transformToMap(list, mapper, distinct, keyMapper, valueMapper);
    }
}
