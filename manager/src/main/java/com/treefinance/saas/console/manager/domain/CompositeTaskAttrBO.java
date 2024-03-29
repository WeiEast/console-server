/*
 * Copyright © 2015 - 2017 杭州大树网络技术有限公司. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.treefinance.saas.console.manager.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jerry
 * @date 2018/12/16 23:06
 */
@Getter
@Setter
@ToString
public class CompositeTaskAttrBO implements Serializable {

    private Long id;

    private String uniqueId;

    private String appId;

    private String accountNo;

    private String webSite;

    private Byte bizType;

    private Byte status;

    private String stepCode;

    private Date createTime;

    private Date lastUpdateTime;
    /**
     * attribute_name
     */
    private String attrName;
    /**
     * attribute_value
     */
    private String attrValue;
}
