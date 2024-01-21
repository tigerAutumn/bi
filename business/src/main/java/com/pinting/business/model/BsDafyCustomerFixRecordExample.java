package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsDafyCustomerFixRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsDafyCustomerFixRecordExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

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
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andOpUserIdIsNull() {
            addCriterion("op_user_id is null");
            return (Criteria) this;
        }

        public Criteria andOpUserIdIsNotNull() {
            addCriterion("op_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andOpUserIdEqualTo(Integer value) {
            addCriterion("op_user_id =", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdNotEqualTo(Integer value) {
            addCriterion("op_user_id <>", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdGreaterThan(Integer value) {
            addCriterion("op_user_id >", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("op_user_id >=", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdLessThan(Integer value) {
            addCriterion("op_user_id <", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("op_user_id <=", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdIn(List<Integer> values) {
            addCriterion("op_user_id in", values, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdNotIn(List<Integer> values) {
            addCriterion("op_user_id not in", values, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdBetween(Integer value1, Integer value2) {
            addCriterion("op_user_id between", value1, value2, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("op_user_id not between", value1, value2, "opUserId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andBeforeDafyCustomerManagerIdIsNull() {
            addCriterion("before_dafy_customer_manager_id is null");
            return (Criteria) this;
        }

        public Criteria andBeforeDafyCustomerManagerIdIsNotNull() {
            addCriterion("before_dafy_customer_manager_id is not null");
            return (Criteria) this;
        }

        public Criteria andBeforeDafyCustomerManagerIdEqualTo(Integer value) {
            addCriterion("before_dafy_customer_manager_id =", value, "beforeDafyCustomerManagerId");
            return (Criteria) this;
        }

        public Criteria andBeforeDafyCustomerManagerIdNotEqualTo(Integer value) {
            addCriterion("before_dafy_customer_manager_id <>", value, "beforeDafyCustomerManagerId");
            return (Criteria) this;
        }

        public Criteria andBeforeDafyCustomerManagerIdGreaterThan(Integer value) {
            addCriterion("before_dafy_customer_manager_id >", value, "beforeDafyCustomerManagerId");
            return (Criteria) this;
        }

        public Criteria andBeforeDafyCustomerManagerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("before_dafy_customer_manager_id >=", value, "beforeDafyCustomerManagerId");
            return (Criteria) this;
        }

        public Criteria andBeforeDafyCustomerManagerIdLessThan(Integer value) {
            addCriterion("before_dafy_customer_manager_id <", value, "beforeDafyCustomerManagerId");
            return (Criteria) this;
        }

        public Criteria andBeforeDafyCustomerManagerIdLessThanOrEqualTo(Integer value) {
            addCriterion("before_dafy_customer_manager_id <=", value, "beforeDafyCustomerManagerId");
            return (Criteria) this;
        }

        public Criteria andBeforeDafyCustomerManagerIdIn(List<Integer> values) {
            addCriterion("before_dafy_customer_manager_id in", values, "beforeDafyCustomerManagerId");
            return (Criteria) this;
        }

        public Criteria andBeforeDafyCustomerManagerIdNotIn(List<Integer> values) {
            addCriterion("before_dafy_customer_manager_id not in", values, "beforeDafyCustomerManagerId");
            return (Criteria) this;
        }

        public Criteria andBeforeDafyCustomerManagerIdBetween(Integer value1, Integer value2) {
            addCriterion("before_dafy_customer_manager_id between", value1, value2, "beforeDafyCustomerManagerId");
            return (Criteria) this;
        }

        public Criteria andBeforeDafyCustomerManagerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("before_dafy_customer_manager_id not between", value1, value2, "beforeDafyCustomerManagerId");
            return (Criteria) this;
        }

        public Criteria andAfterDafyCustomerManagerIdIsNull() {
            addCriterion("after_dafy_customer_manager_id is null");
            return (Criteria) this;
        }

        public Criteria andAfterDafyCustomerManagerIdIsNotNull() {
            addCriterion("after_dafy_customer_manager_id is not null");
            return (Criteria) this;
        }

        public Criteria andAfterDafyCustomerManagerIdEqualTo(Integer value) {
            addCriterion("after_dafy_customer_manager_id =", value, "afterDafyCustomerManagerId");
            return (Criteria) this;
        }

        public Criteria andAfterDafyCustomerManagerIdNotEqualTo(Integer value) {
            addCriterion("after_dafy_customer_manager_id <>", value, "afterDafyCustomerManagerId");
            return (Criteria) this;
        }

        public Criteria andAfterDafyCustomerManagerIdGreaterThan(Integer value) {
            addCriterion("after_dafy_customer_manager_id >", value, "afterDafyCustomerManagerId");
            return (Criteria) this;
        }

        public Criteria andAfterDafyCustomerManagerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("after_dafy_customer_manager_id >=", value, "afterDafyCustomerManagerId");
            return (Criteria) this;
        }

        public Criteria andAfterDafyCustomerManagerIdLessThan(Integer value) {
            addCriterion("after_dafy_customer_manager_id <", value, "afterDafyCustomerManagerId");
            return (Criteria) this;
        }

        public Criteria andAfterDafyCustomerManagerIdLessThanOrEqualTo(Integer value) {
            addCriterion("after_dafy_customer_manager_id <=", value, "afterDafyCustomerManagerId");
            return (Criteria) this;
        }

        public Criteria andAfterDafyCustomerManagerIdIn(List<Integer> values) {
            addCriterion("after_dafy_customer_manager_id in", values, "afterDafyCustomerManagerId");
            return (Criteria) this;
        }

        public Criteria andAfterDafyCustomerManagerIdNotIn(List<Integer> values) {
            addCriterion("after_dafy_customer_manager_id not in", values, "afterDafyCustomerManagerId");
            return (Criteria) this;
        }

        public Criteria andAfterDafyCustomerManagerIdBetween(Integer value1, Integer value2) {
            addCriterion("after_dafy_customer_manager_id between", value1, value2, "afterDafyCustomerManagerId");
            return (Criteria) this;
        }

        public Criteria andAfterDafyCustomerManagerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("after_dafy_customer_manager_id not between", value1, value2, "afterDafyCustomerManagerId");
            return (Criteria) this;
        }

        public Criteria andBeforeSubAccountIdIsNull() {
            addCriterion("before_sub_account_id is null");
            return (Criteria) this;
        }

        public Criteria andBeforeSubAccountIdIsNotNull() {
            addCriterion("before_sub_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andBeforeSubAccountIdEqualTo(String value) {
            addCriterion("before_sub_account_id =", value, "beforeSubAccountId");
            return (Criteria) this;
        }

        public Criteria andBeforeSubAccountIdNotEqualTo(String value) {
            addCriterion("before_sub_account_id <>", value, "beforeSubAccountId");
            return (Criteria) this;
        }

        public Criteria andBeforeSubAccountIdGreaterThan(String value) {
            addCriterion("before_sub_account_id >", value, "beforeSubAccountId");
            return (Criteria) this;
        }

        public Criteria andBeforeSubAccountIdGreaterThanOrEqualTo(String value) {
            addCriterion("before_sub_account_id >=", value, "beforeSubAccountId");
            return (Criteria) this;
        }

        public Criteria andBeforeSubAccountIdLessThan(String value) {
            addCriterion("before_sub_account_id <", value, "beforeSubAccountId");
            return (Criteria) this;
        }

        public Criteria andBeforeSubAccountIdLessThanOrEqualTo(String value) {
            addCriterion("before_sub_account_id <=", value, "beforeSubAccountId");
            return (Criteria) this;
        }

        public Criteria andBeforeSubAccountIdLike(String value) {
            addCriterion("before_sub_account_id like", value, "beforeSubAccountId");
            return (Criteria) this;
        }

        public Criteria andBeforeSubAccountIdNotLike(String value) {
            addCriterion("before_sub_account_id not like", value, "beforeSubAccountId");
            return (Criteria) this;
        }

        public Criteria andBeforeSubAccountIdIn(List<String> values) {
            addCriterion("before_sub_account_id in", values, "beforeSubAccountId");
            return (Criteria) this;
        }

        public Criteria andBeforeSubAccountIdNotIn(List<String> values) {
            addCriterion("before_sub_account_id not in", values, "beforeSubAccountId");
            return (Criteria) this;
        }

        public Criteria andBeforeSubAccountIdBetween(String value1, String value2) {
            addCriterion("before_sub_account_id between", value1, value2, "beforeSubAccountId");
            return (Criteria) this;
        }

        public Criteria andBeforeSubAccountIdNotBetween(String value1, String value2) {
            addCriterion("before_sub_account_id not between", value1, value2, "beforeSubAccountId");
            return (Criteria) this;
        }

        public Criteria andAfterSubAccountIdIsNull() {
            addCriterion("after_sub_account_id is null");
            return (Criteria) this;
        }

        public Criteria andAfterSubAccountIdIsNotNull() {
            addCriterion("after_sub_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andAfterSubAccountIdEqualTo(String value) {
            addCriterion("after_sub_account_id =", value, "afterSubAccountId");
            return (Criteria) this;
        }

        public Criteria andAfterSubAccountIdNotEqualTo(String value) {
            addCriterion("after_sub_account_id <>", value, "afterSubAccountId");
            return (Criteria) this;
        }

        public Criteria andAfterSubAccountIdGreaterThan(String value) {
            addCriterion("after_sub_account_id >", value, "afterSubAccountId");
            return (Criteria) this;
        }

        public Criteria andAfterSubAccountIdGreaterThanOrEqualTo(String value) {
            addCriterion("after_sub_account_id >=", value, "afterSubAccountId");
            return (Criteria) this;
        }

        public Criteria andAfterSubAccountIdLessThan(String value) {
            addCriterion("after_sub_account_id <", value, "afterSubAccountId");
            return (Criteria) this;
        }

        public Criteria andAfterSubAccountIdLessThanOrEqualTo(String value) {
            addCriterion("after_sub_account_id <=", value, "afterSubAccountId");
            return (Criteria) this;
        }

        public Criteria andAfterSubAccountIdLike(String value) {
            addCriterion("after_sub_account_id like", value, "afterSubAccountId");
            return (Criteria) this;
        }

        public Criteria andAfterSubAccountIdNotLike(String value) {
            addCriterion("after_sub_account_id not like", value, "afterSubAccountId");
            return (Criteria) this;
        }

        public Criteria andAfterSubAccountIdIn(List<String> values) {
            addCriterion("after_sub_account_id in", values, "afterSubAccountId");
            return (Criteria) this;
        }

        public Criteria andAfterSubAccountIdNotIn(List<String> values) {
            addCriterion("after_sub_account_id not in", values, "afterSubAccountId");
            return (Criteria) this;
        }

        public Criteria andAfterSubAccountIdBetween(String value1, String value2) {
            addCriterion("after_sub_account_id between", value1, value2, "afterSubAccountId");
            return (Criteria) this;
        }

        public Criteria andAfterSubAccountIdNotBetween(String value1, String value2) {
            addCriterion("after_sub_account_id not between", value1, value2, "afterSubAccountId");
            return (Criteria) this;
        }

        public Criteria andSplitTimeIsNull() {
            addCriterion("split_time is null");
            return (Criteria) this;
        }

        public Criteria andSplitTimeIsNotNull() {
            addCriterion("split_time is not null");
            return (Criteria) this;
        }

        public Criteria andSplitTimeEqualTo(Date value) {
            addCriterion("split_time =", value, "splitTime");
            return (Criteria) this;
        }

        public Criteria andSplitTimeNotEqualTo(Date value) {
            addCriterion("split_time <>", value, "splitTime");
            return (Criteria) this;
        }

        public Criteria andSplitTimeGreaterThan(Date value) {
            addCriterion("split_time >", value, "splitTime");
            return (Criteria) this;
        }

        public Criteria andSplitTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("split_time >=", value, "splitTime");
            return (Criteria) this;
        }

        public Criteria andSplitTimeLessThan(Date value) {
            addCriterion("split_time <", value, "splitTime");
            return (Criteria) this;
        }

        public Criteria andSplitTimeLessThanOrEqualTo(Date value) {
            addCriterion("split_time <=", value, "splitTime");
            return (Criteria) this;
        }

        public Criteria andSplitTimeIn(List<Date> values) {
            addCriterion("split_time in", values, "splitTime");
            return (Criteria) this;
        }

        public Criteria andSplitTimeNotIn(List<Date> values) {
            addCriterion("split_time not in", values, "splitTime");
            return (Criteria) this;
        }

        public Criteria andSplitTimeBetween(Date value1, Date value2) {
            addCriterion("split_time between", value1, value2, "splitTime");
            return (Criteria) this;
        }

        public Criteria andSplitTimeNotBetween(Date value1, Date value2) {
            addCriterion("split_time not between", value1, value2, "splitTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeIsNull() {
            addCriterion("op_time is null");
            return (Criteria) this;
        }

        public Criteria andOpTimeIsNotNull() {
            addCriterion("op_time is not null");
            return (Criteria) this;
        }

        public Criteria andOpTimeEqualTo(Date value) {
            addCriterion("op_time =", value, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeNotEqualTo(Date value) {
            addCriterion("op_time <>", value, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeGreaterThan(Date value) {
            addCriterion("op_time >", value, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("op_time >=", value, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeLessThan(Date value) {
            addCriterion("op_time <", value, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeLessThanOrEqualTo(Date value) {
            addCriterion("op_time <=", value, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeIn(List<Date> values) {
            addCriterion("op_time in", values, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeNotIn(List<Date> values) {
            addCriterion("op_time not in", values, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeBetween(Date value1, Date value2) {
            addCriterion("op_time between", value1, value2, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeNotBetween(Date value1, Date value2) {
            addCriterion("op_time not between", value1, value2, "opTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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