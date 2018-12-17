package com.treefinance.saas.console.manager.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author Jerry
 * @date 2018/12/17 09:58
 */
@Getter
@Setter
@ToString
public class CompositeTaskAttrPagingQuery extends BasePagingQuery {
    /**
     * 商户ID
     */
    private String appId;
    /**
     * 业务类型
     */
    private List<Byte> bizTypes;
    /**
     * 网站标识
     */
    private String website;
    /**
     * 环境标识
     */
    private Byte saasEnv;
    /**
     * 状态
     */
    private Byte status;
    /**
     * 起始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 属性名
     */
    private String attrName;
    /**
     * 属性值
     */
    private String attrValue;
    /**
     * 排序方式
     */
    private String order;
}
