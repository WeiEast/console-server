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

package com.treefinance.saas.management.console.common.result;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * @author Jerry
 * @since 17:56 25/04/2017
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -42294283374489841L;

    private T data;
    private StateCode stateCode;
    private String statusText;
    private Long timestamp = System.currentTimeMillis();

    public Result() {
    }

    @Deprecated
    public Result(T data) {
        this.data = data;
    }

    @Deprecated
    public Result(T data, String statusText) {
        this.data = data;
        this.statusText = statusText;
    }

    //是否处理成功, 意味着没有业务失败和系统失败
    public boolean isSuccess() {
        return stateCode == null ? false : stateCode.getCode() >= 0;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public StateCode getStateCode() {
        return stateCode;
    }

    public void setStateCode(StateCode stateCode) {
        this.stateCode = stateCode;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("data", data)
                .add("stateCode", stateCode)
                .add("statusText", statusText)
                .add("timestamp", timestamp)
                .toString();
    }
}
