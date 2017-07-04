package com.treefinance.saas.management.console.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MerchantUserCriteria {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    protected int limit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    protected int offset;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    public MerchantUserCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    public int getLimit() {
        return limit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    public int getOffset() {
        return offset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
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
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
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

        public Criteria andMerchantIdIsNull() {
            addCriterion("MerchantId is null");
            return (Criteria) this;
        }

        public Criteria andMerchantIdIsNotNull() {
            addCriterion("MerchantId is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantIdEqualTo(Long value) {
            addCriterion("MerchantId =", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdNotEqualTo(Long value) {
            addCriterion("MerchantId <>", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdGreaterThan(Long value) {
            addCriterion("MerchantId >", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdGreaterThanOrEqualTo(Long value) {
            addCriterion("MerchantId >=", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdLessThan(Long value) {
            addCriterion("MerchantId <", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdLessThanOrEqualTo(Long value) {
            addCriterion("MerchantId <=", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdIn(List<Long> values) {
            addCriterion("MerchantId in", values, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdNotIn(List<Long> values) {
            addCriterion("MerchantId not in", values, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdBetween(Long value1, Long value2) {
            addCriterion("MerchantId between", value1, value2, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdNotBetween(Long value1, Long value2) {
            addCriterion("MerchantId not between", value1, value2, "merchantId");
            return (Criteria) this;
        }

        public Criteria andLoginNameIsNull() {
            addCriterion("LoginName is null");
            return (Criteria) this;
        }

        public Criteria andLoginNameIsNotNull() {
            addCriterion("LoginName is not null");
            return (Criteria) this;
        }

        public Criteria andLoginNameEqualTo(String value) {
            addCriterion("LoginName =", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotEqualTo(String value) {
            addCriterion("LoginName <>", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameGreaterThan(String value) {
            addCriterion("LoginName >", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameGreaterThanOrEqualTo(String value) {
            addCriterion("LoginName >=", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameLessThan(String value) {
            addCriterion("LoginName <", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameLessThanOrEqualTo(String value) {
            addCriterion("LoginName <=", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameLike(String value) {
            addCriterion("LoginName like", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotLike(String value) {
            addCriterion("LoginName not like", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameIn(List<String> values) {
            addCriterion("LoginName in", values, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotIn(List<String> values) {
            addCriterion("LoginName not in", values, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameBetween(String value1, String value2) {
            addCriterion("LoginName between", value1, value2, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotBetween(String value1, String value2) {
            addCriterion("LoginName not between", value1, value2, "loginName");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("Password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("Password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("Password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("Password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("Password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("Password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("Password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("Password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("Password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("Password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("Password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("Password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("Password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("Password not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andIsActiveIsNull() {
            addCriterion("IsActive is null");
            return (Criteria) this;
        }

        public Criteria andIsActiveIsNotNull() {
            addCriterion("IsActive is not null");
            return (Criteria) this;
        }

        public Criteria andIsActiveEqualTo(Boolean value) {
            addCriterion("IsActive =", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveNotEqualTo(Boolean value) {
            addCriterion("IsActive <>", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveGreaterThan(Boolean value) {
            addCriterion("IsActive >", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveGreaterThanOrEqualTo(Boolean value) {
            addCriterion("IsActive >=", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveLessThan(Boolean value) {
            addCriterion("IsActive <", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveLessThanOrEqualTo(Boolean value) {
            addCriterion("IsActive <=", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveIn(List<Boolean> values) {
            addCriterion("IsActive in", values, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveNotIn(List<Boolean> values) {
            addCriterion("IsActive not in", values, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveBetween(Boolean value1, Boolean value2) {
            addCriterion("IsActive between", value1, value2, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveNotBetween(Boolean value1, Boolean value2) {
            addCriterion("IsActive not between", value1, value2, "isActive");
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
     * This class corresponds to the database table merchant_user
     *
     * @mbg.generated do_not_delete_during_merge Tue Jul 04 10:52:24 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table merchant_user
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
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