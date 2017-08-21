package com.treefinance.saas.management.console.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskCriteria {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    protected int limit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    protected int offset;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    public TaskCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    public int getLimit() {
        return limit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    public int getOffset() {
        return offset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("Id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("Id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("Id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUniqueIdIsNull() {
            addCriterion("UniqueId is null");
            return (Criteria) this;
        }

        public Criteria andUniqueIdIsNotNull() {
            addCriterion("UniqueId is not null");
            return (Criteria) this;
        }

        public Criteria andUniqueIdEqualTo(String value) {
            addCriterion("UniqueId =", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdNotEqualTo(String value) {
            addCriterion("UniqueId <>", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdGreaterThan(String value) {
            addCriterion("UniqueId >", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdGreaterThanOrEqualTo(String value) {
            addCriterion("UniqueId >=", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdLessThan(String value) {
            addCriterion("UniqueId <", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdLessThanOrEqualTo(String value) {
            addCriterion("UniqueId <=", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdLike(String value) {
            addCriterion("UniqueId like", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdNotLike(String value) {
            addCriterion("UniqueId not like", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdIn(List<String> values) {
            addCriterion("UniqueId in", values, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdNotIn(List<String> values) {
            addCriterion("UniqueId not in", values, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdBetween(String value1, String value2) {
            addCriterion("UniqueId between", value1, value2, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdNotBetween(String value1, String value2) {
            addCriterion("UniqueId not between", value1, value2, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNull() {
            addCriterion("AppId is null");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNotNull() {
            addCriterion("AppId is not null");
            return (Criteria) this;
        }

        public Criteria andAppIdEqualTo(String value) {
            addCriterion("AppId =", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotEqualTo(String value) {
            addCriterion("AppId <>", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThan(String value) {
            addCriterion("AppId >", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThanOrEqualTo(String value) {
            addCriterion("AppId >=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThan(String value) {
            addCriterion("AppId <", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThanOrEqualTo(String value) {
            addCriterion("AppId <=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLike(String value) {
            addCriterion("AppId like", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotLike(String value) {
            addCriterion("AppId not like", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdIn(List<String> values) {
            addCriterion("AppId in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotIn(List<String> values) {
            addCriterion("AppId not in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdBetween(String value1, String value2) {
            addCriterion("AppId between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotBetween(String value1, String value2) {
            addCriterion("AppId not between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAccountNoIsNull() {
            addCriterion("AccountNo is null");
            return (Criteria) this;
        }

        public Criteria andAccountNoIsNotNull() {
            addCriterion("AccountNo is not null");
            return (Criteria) this;
        }

        public Criteria andAccountNoEqualTo(String value) {
            addCriterion("AccountNo =", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoNotEqualTo(String value) {
            addCriterion("AccountNo <>", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoGreaterThan(String value) {
            addCriterion("AccountNo >", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoGreaterThanOrEqualTo(String value) {
            addCriterion("AccountNo >=", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoLessThan(String value) {
            addCriterion("AccountNo <", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoLessThanOrEqualTo(String value) {
            addCriterion("AccountNo <=", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoLike(String value) {
            addCriterion("AccountNo like", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoNotLike(String value) {
            addCriterion("AccountNo not like", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoIn(List<String> values) {
            addCriterion("AccountNo in", values, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoNotIn(List<String> values) {
            addCriterion("AccountNo not in", values, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoBetween(String value1, String value2) {
            addCriterion("AccountNo between", value1, value2, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoNotBetween(String value1, String value2) {
            addCriterion("AccountNo not between", value1, value2, "accountNo");
            return (Criteria) this;
        }

        public Criteria andWebSiteIsNull() {
            addCriterion("WebSite is null");
            return (Criteria) this;
        }

        public Criteria andWebSiteIsNotNull() {
            addCriterion("WebSite is not null");
            return (Criteria) this;
        }

        public Criteria andWebSiteEqualTo(String value) {
            addCriterion("WebSite =", value, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteNotEqualTo(String value) {
            addCriterion("WebSite <>", value, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteGreaterThan(String value) {
            addCriterion("WebSite >", value, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteGreaterThanOrEqualTo(String value) {
            addCriterion("WebSite >=", value, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteLessThan(String value) {
            addCriterion("WebSite <", value, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteLessThanOrEqualTo(String value) {
            addCriterion("WebSite <=", value, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteLike(String value) {
            addCriterion("WebSite like", value, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteNotLike(String value) {
            addCriterion("WebSite not like", value, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteIn(List<String> values) {
            addCriterion("WebSite in", values, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteNotIn(List<String> values) {
            addCriterion("WebSite not in", values, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteBetween(String value1, String value2) {
            addCriterion("WebSite between", value1, value2, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteNotBetween(String value1, String value2) {
            addCriterion("WebSite not between", value1, value2, "webSite");
            return (Criteria) this;
        }

        public Criteria andBizTypeIsNull() {
            addCriterion("BizType is null");
            return (Criteria) this;
        }

        public Criteria andBizTypeIsNotNull() {
            addCriterion("BizType is not null");
            return (Criteria) this;
        }

        public Criteria andBizTypeEqualTo(Byte value) {
            addCriterion("BizType =", value, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeNotEqualTo(Byte value) {
            addCriterion("BizType <>", value, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeGreaterThan(Byte value) {
            addCriterion("BizType >", value, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("BizType >=", value, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeLessThan(Byte value) {
            addCriterion("BizType <", value, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeLessThanOrEqualTo(Byte value) {
            addCriterion("BizType <=", value, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeIn(List<Byte> values) {
            addCriterion("BizType in", values, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeNotIn(List<Byte> values) {
            addCriterion("BizType not in", values, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeBetween(Byte value1, Byte value2) {
            addCriterion("BizType between", value1, value2, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("BizType not between", value1, value2, "bizType");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("Status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("Status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("Status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("Status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("Status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("Status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("Status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("Status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("Status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("Status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("Status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("Status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andErrorCodeIsNull() {
            addCriterion("ErrorCode is null");
            return (Criteria) this;
        }

        public Criteria andErrorCodeIsNotNull() {
            addCriterion("ErrorCode is not null");
            return (Criteria) this;
        }

        public Criteria andErrorCodeEqualTo(String value) {
            addCriterion("ErrorCode =", value, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeNotEqualTo(String value) {
            addCriterion("ErrorCode <>", value, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeGreaterThan(String value) {
            addCriterion("ErrorCode >", value, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ErrorCode >=", value, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeLessThan(String value) {
            addCriterion("ErrorCode <", value, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeLessThanOrEqualTo(String value) {
            addCriterion("ErrorCode <=", value, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeLike(String value) {
            addCriterion("ErrorCode like", value, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeNotLike(String value) {
            addCriterion("ErrorCode not like", value, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeIn(List<String> values) {
            addCriterion("ErrorCode in", values, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeNotIn(List<String> values) {
            addCriterion("ErrorCode not in", values, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeBetween(String value1, String value2) {
            addCriterion("ErrorCode between", value1, value2, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeNotBetween(String value1, String value2) {
            addCriterion("ErrorCode not between", value1, value2, "errorCode");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CreateTime is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CreateTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("CreateTime =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("CreateTime <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("CreateTime >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CreateTime >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("CreateTime <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("CreateTime <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("CreateTime in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("CreateTime not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("CreateTime between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("CreateTime not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIsNull() {
            addCriterion("LastUpdateTime is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIsNotNull() {
            addCriterion("LastUpdateTime is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeEqualTo(Date value) {
            addCriterion("LastUpdateTime =", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotEqualTo(Date value) {
            addCriterion("LastUpdateTime <>", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeGreaterThan(Date value) {
            addCriterion("LastUpdateTime >", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("LastUpdateTime >=", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeLessThan(Date value) {
            addCriterion("LastUpdateTime <", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("LastUpdateTime <=", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIn(List<Date> values) {
            addCriterion("LastUpdateTime in", values, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotIn(List<Date> values) {
            addCriterion("LastUpdateTime not in", values, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("LastUpdateTime between", value1, value2, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("LastUpdateTime not between", value1, value2, "lastUpdateTime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table task
     *
     * @mbg.generated do_not_delete_during_merge Mon Aug 21 19:08:12 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table task
     *
     * @mbg.generated Mon Aug 21 19:08:12 CST 2017
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}