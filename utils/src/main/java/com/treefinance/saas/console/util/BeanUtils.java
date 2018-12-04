package com.treefinance.saas.console.util;

import com.treefinance.toolkit.exception.UnexpectedException;
import com.treefinance.toolkit.util.Assert;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cglib.beans.BeanCopier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jerry
 * @date 2018/11/26 17:03
 */
public final class BeanUtils {

    private static final Map<String, BeanCopier> COPIER_MAP = new ConcurrentHashMap<>();

    private BeanUtils() {}

    /**
     * 基于CGLIB的bean properties 的拷贝, the given <code>source</code> can be null.
     *
     * @param source the bean to copy from, may be null
     * @param target the bean to copy to
     * @see #copyProperties(Object, Object)
     */
    public static void copy(@Nullable Object source, @Nonnull Object target) {
        if (source == null) {
            return;
        }

        copyProperties(source, target);
    }

    /**
     * 基于CGLIB的bean properties 的拷贝，性能要远优于{@link org.springframework.beans.BeanUtils#copyProperties}
     *
     * @param source the bean to copy from
     * @param target the bean to copy to
     */
    public static void copyProperties(@Nonnull Object source, @Nonnull Object target) {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        String key = String.format("%s:%s", source.getClass().getName(), target.getClass().getName());
        BeanCopier beanCopier = COPIER_MAP.computeIfAbsent(key, s -> BeanCopier.create(source.getClass(), target.getClass(), false));

        beanCopier.copy(source, target, null);
    }

    /**
     * convert the bean <code>source</code> to a new instance of <code>clazz</code>
     * <p>
     * new a instance of <code>clazz<code/> and copy properties from the <code>source</code> bean.
     * </p>
     * 
     * @param source the bean to convert from,may be null
     * @param clazz the instance type to new
     * @return a instance of <code>clazz</code>, or <code>null</code> if the given <code>source</code> is null.
     */
    @Nullable
    public static <S, T> T convert(@Nullable S source, @Nonnull Class<T> clazz) {
        if (source == null) {
            return null;
        }

        return convertStrict(source, clazz);
    }

    /**
     * convert the bean <code>source</code> to a new instance of <code>clazz</code>
     * <p>
     * new a instance of <code>clazz<code/> and copy properties from the <code>source</code> bean.
     * </p>
     *
     * @param source the bean to convert from, must not be null
     * @param clazz the instance type to new
     * @return a instance of <code>clazz</code>
     */
    @Nonnull
    public static <S, T> T convertStrict(@Nonnull S source, @Nonnull Class<T> clazz) {
        Assert.notNull(clazz, "Target class must not be null");

        T target;
        try {
            target = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new UnexpectedException("Can not new instance of class[" + clazz + "]", e);
        }

        copyProperties(source, target);

        return target;
    }

    /**
     * convert a list of bean <code>source</code> to instances of <code>clazz</code>
     * <p>
     * iterate to new instances of <code>clazz<code/> and copy properties from the <code>source</code> bean.
     * </p>
     *
     * @param source the bean to convert from
     * @param clazz the instance type to new
     * @return instances of <code>clazz</code>
     */
    public static <S, T> List<T> convert(@Nullable Collection<S> source, @Nonnull Class<T> clazz) {
        Assert.notNull(clazz, "Target class must not be null");

        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        }

        List<T> result = new ArrayList<>(source.size());

        try {
            for (S obj : source) {
                if (obj == null) {
                    continue;
                }
                T target = clazz.newInstance();

                copyProperties(obj, target);

                result.add(target);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new UnexpectedException("Can not new instance of class[" + clazz + "]", e);
        }

        return result;
    }

    /**
     * transform elements into a {@code List} whose elements are the result of applying the provided mapping functions
     * to the input elements.
     *
     * @param list the list to transform
     * @param mapper a mapping function to transform elements
     * @return a transformed {@code List}
     */
    public static <S, T> List<T> transform(@Nullable List<S> list, @Nonnull Function<? super S, ? extends T> mapper) {
        return transform(list, mapper, false);
    }

    /**
     * transform elements into a {@code List} whose elements are the result of applying the provided mapping functions
     * to the input elements.
     *
     * @param list the list to transform
     * @param mapper a mapping function to transform elements
     * @param distinct if the elements is distinct
     * @return a transformed {@code List}
     */
    public static <S, T> List<T> transform(@Nullable List<S> list, @Nonnull Function<? super S, ? extends T> mapper, boolean distinct) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        Stream<? extends T> stream = list.stream().map(mapper);

        if (distinct) {
            stream = stream.distinct();
        }

        return stream.collect(Collectors.toList());
    }

    /**
     * transform elements into a {@code Map} whose keys and values are the result of applying the provided mapping
     * functions to the input elements.
     *
     * @param list the list to transform
     * @param keyMapper a mapping function to produce keys
     * @param valueMapper a mapping function to produce values
     * @return a transformed {@code Map}
     */
    public static <S, K, V> Map<K, V> transformToMap(@Nullable List<S> list, @Nonnull Function<? super S, ? extends K> keyMapper,
        @Nonnull Function<? super S, ? extends V> valueMapper) {
        return transformToMap(list, false, keyMapper, valueMapper);
    }

    /**
     * transform elements into a {@code Map} whose keys and values are the result of applying the provided mapping
     * functions to the input elements.
     *
     * @param list the list to transform
     * @param distinct if the elements is distinct
     * @param keyMapper a mapping function to produce keys
     * @param valueMapper a mapping function to produce values
     * @return a transformed {@code Map}
     */
    public static <S, K, V> Map<K, V> transformToMap(@Nullable List<S> list, boolean distinct, @Nonnull Function<? super S, ? extends K> keyMapper,
        @Nonnull Function<? super S, ? extends V> valueMapper) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }

        Stream<? extends S> stream = list.stream();

        if (distinct) {
            stream = stream.distinct();
        }

        return stream.collect(Collectors.toMap(keyMapper, valueMapper));
    }

    /**
     * transform elements into a {@code Map} whose keys and values are the result of applying the provided mapping
     * functions to the input elements.
     *
     * @param list the list to transform
     * @param mapper a mapping function to transform elements
     * @param distinct if the elements is distinct
     * @param keyMapper a mapping function to produce keys
     * @param valueMapper a mapping function to produce values
     * @return a transformed {@code Map}
     */
    public static <S, T, K, V> Map<K, V> transformToMap(@Nullable List<S> list, @Nonnull Function<? super S, ? extends T> mapper, boolean distinct,
        @Nonnull Function<? super T, ? extends K> keyMapper, @Nonnull Function<? super T, ? extends V> valueMapper) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }

        Stream<? extends T> stream = list.stream().map(mapper);

        if (distinct) {
            stream = stream.distinct();
        }

        return stream.collect(Collectors.toMap(keyMapper, valueMapper));
    }
}
