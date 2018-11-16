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

package com.treefinance.saas.management.console.common.cache;

import java.util.concurrent.TimeUnit;

public interface RedisService {

    void enqueue(String key, String value);

    String dequeue(String key);

    String getValue(String key);

    boolean setExpiredValueQuietly(String key, String value);

    boolean setValueQuietly(String key, String value);

    boolean setValueQuietly(String key, String value, long ttlSeconds);

    boolean setValueQuietly(String key, String value, long timeout, TimeUnit unit);

    void setValue(String key, String value);

    void setValue(String key, String value, long timeout, TimeUnit unit);

    boolean deleteQuietly(String key);

    void delete(String key);

    Long[] increaseRequestLimitCounter(String ip, long[] times, TimeUnit[] timeUnits);

}
