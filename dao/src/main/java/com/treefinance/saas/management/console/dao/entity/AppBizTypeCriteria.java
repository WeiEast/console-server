package com.treefinance.saas.management.console.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppBizTypeCriteria {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
     */
    protected int limit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
     */
    protected int offset;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
     */
    public AppBizTypeCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
     */
    public int getLimit() {
        return limit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
     */
    public int getOffset() {
        return offset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
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
     * This method corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
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

        public Criteria andBizCodeIsNull() {
            addCriterion("BizCode is null");
            return (Criteria) this;
        }

        public Criteria andBizCodeIsNotNull() {
            addCriterion("BizCode is not null");
            return (Criteria) this;
        }

        public Criteria andBizCodeEqualTo(String value) {
            addCriterion("BizCode =", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeNotEqualTo(String value) {
            addCriterion("BizCode <>", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeGreaterThan(String value) {
            addCriterion("BizCode >", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BizCode >=", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeLessThan(String value) {
            addCriterion("BizCode <", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeLessThanOrEqualTo(String value) {
            addCriterion("BizCode <=", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeLike(String value) {
            addCriterion("BizCode like", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeNotLike(String value) {
            addCriterion("BizCode not like", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeIn(List<String> values) {
            addCriterion("BizCode in", values, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeNotIn(List<String> values) {
            addCriterion("BizCode not in", values, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeBetween(String value1, String value2) {
            addCriterion("BizCode between", value1, value2, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeNotBetween(String value1, String value2) {
            addCriterion("BizCode not between", value1, value2, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizNameIsNull() {
            addCriterion("BizName is null");
            return (Criteria) this;
        }

        public Criteria andBizNameIsNotNull() {
            addCriterion("BizName is not null");
            return (Criteria) this;
        }

        public Criteria andBizNameEqualTo(String value) {
            addCriterion("BizName =", value, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameNotEqualTo(String value) {
            addCriterion("BizName <>", value, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameGreaterThan(String value) {
            addCriterion("BizName >", value, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameGreaterThanOrEqualTo(String value) {
            addCriterion("BizName >=", value, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameLessThan(String value) {
            addCriterion("BizName <", value, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameLessThanOrEqualTo(String value) {
            addCriterion("BizName <=", value, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameLike(String value) {
            addCriterion("BizName like", value, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameNotLike(String value) {
            addCriterion("BizName not like", value, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameIn(List<String> values) {
            addCriterion("BizName in", values, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameNotIn(List<String> values) {
            addCriterion("BizName not in", values, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameBetween(String value1, String value2) {
            addCriterion("BizName between", value1, value2, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameNotBetween(String value1, String value2) {
            addCriterion("BizName not between", value1, value2, "bizName");
            return (Criteria) this;
        }

        public Criteria andTimeoutIsNull() {
            addCriterion("timeout is null");
            return (Criteria) this;
        }

        public Criteria andTimeoutIsNotNull() {
            addCriterion("timeout is not null");
            return (Criteria) this;
        }

        public Criteria andTimeoutEqualTo(Integer value) {
            addCriterion("timeout =", value, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutNotEqualTo(Integer value) {
            addCriterion("timeout <>", value, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutGreaterThan(Integer value) {
            addCriterion("timeout >", value, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutGreaterThanOrEqualTo(Integer value) {
            addCriterion("timeout >=", value, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutLessThan(Integer value) {
            addCriterion("timeout <", value, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutLessThanOrEqualTo(Integer value) {
            addCriterion("timeout <=", value, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutIn(List<Integer> values) {
            addCriterion("timeout in", values, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutNotIn(List<Integer> values) {
            addCriterion("timeout not in", values, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutBetween(Integer value1, Integer value2) {
            addCriterion("timeout between", value1, value2, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutNotBetween(Integer value1, Integer value2) {
            addCriterion("timeout not between", value1, value2, "timeout");
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
     * This class corresponds to the database table app_biz_type
     *
     * @mbg.generated do_not_delete_during_merge Wed Dec 27 10:53:52 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table app_biz_type
     *
     * @mbg.generated Wed Dec 27 10:53:52 CST 2017
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