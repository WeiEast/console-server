package com.treefinance.saas.console.biz.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis数据服务
 * Created by yh-treefinance on 2017/11/15.
 */
public interface RedisDataService {

    /**
     * 分页查询redis-key列表
     *
     * @param keys       key关键字
     * @param pageNumber 页码
     * @param pageSize   页面大小
     * @return
     */
    List<Map<String, String>> queryKeyList(String keys, Integer pageNumber, Integer pageSize);

    /**
     * 获取redis set
     *
     * @param key
     * @return
     */
    Set<String> getRedisSet(String key);

    /**
     * 获取redis zset
     *
     * @param key
     * @return
     */
    Set<String> getRedisZset(String key);

    /**
     * 获取redis数据
     *
     * @param key
     * @return
     */
    List<String> getRedisList(String key);

    /**
     * 获取redis map数据
     *
     * @param key
     * @return
     */
    Map<Object, Object> getRedisMap(String key);

    /**
     * 获取redis value
     *
     * @param key
     * @return
     */
    Object getRedisObject(String key);
}
