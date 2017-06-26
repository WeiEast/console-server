package com.treefinance.saas.management.console.common.result;

import com.google.common.collect.Maps;

import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 放回result的工具类
 * Created by haojiahong on 2017/6/26.
 */
public abstract class Results {

    /**
     * 分页成功结果
     */
    public static <T> Result<Map<String, Object>> newSuccessPageResult(PageRequest request, long totalCount, T data) {
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("totalCount", totalCount);
        dataMap.put("request", request);
        dataMap.put("data", data);
        return newSuccessResult(dataMap, CommonStateCode.SUCCESS.getDesc());
    }

    /**
     * 成功结果
     */
    public static <T> Result<T> newSuccessResult(T data) {
        return newSuccessResult(data, CommonStateCode.SUCCESS.getDesc());
    }

    /**
     * 成功结果
     */
    public static <T> Result<T> newSuccessResult(T data, String statusText) {
        return newResult(data, CommonStateCode.SUCCESS, statusText);
    }

    /**
     * 没有数据回传的失败结果
     */
    public static <T> Result<T> newFailedResult(StateCode failedCode) {
        return newFailedResult(null, failedCode);
    }

    /**
     * 没有数据回传的失败结果
     */
    public static <T> Result<T> newFailedResult(StateCode failedCode, String statusText) {
        return newFailedResult(null, failedCode, statusText);
    }

    /**
     * 有数据回传的失败结果
     */
    public static <T> Result<T> newFailedResult(T data, StateCode failedCode) {
        return newFailedResult(data, failedCode, failedCode.getDesc());
    }

    /**
     * 有数据回传的失败结果
     */
    public static <T> Result<T> newFailedResult(T data, StateCode failedCode, String statusText) {
        checkNotNull(failedCode);
        checkArgument(failedCode.getCode() < 0,
                "invalid code, for failed result, code must be negative integers");

        return newResult(data, failedCode, statusText);
    }

    /**
     * 仅返回状态码
     */
    public static <T> Result<T> newResult(StateCode code) {
        return newResult(null, code, code.getDesc());
    }

    /**
     * 有数据回传的结果
     */
    public static <T> Result<T> newResult(T data, StateCode failedCode, String statusText) {
        Result<T> result = new Result<T>();
        result.setData(data);
        result.setStateCode(failedCode);
        result.setStatusText(statusText);
        return result;
    }

}
