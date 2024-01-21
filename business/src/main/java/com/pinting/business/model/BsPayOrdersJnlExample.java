package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsPayOrdersJnlExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsPayOrdersJnlExample() {
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

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(Integer value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(Integer value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(Integer value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(Integer value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(Integer value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<Integer> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<Integer> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(Integer value1, Integer value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andTransCodeIsNull() {
            addCriterion("trans_code is null");
            return (Criteria) this;
        }

        public Criteria andTransCodeIsNotNull() {
            addCriterion("trans_code is not null");
            return (Criteria) this;
        }

        public Criteria andTransCodeEqualTo(String value) {
            addCriterion("trans_code =", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeNotEqualTo(String value) {
            addCriterion("trans_code <>", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeGreaterThan(String value) {
            addCriterion("trans_code >", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeGreaterThanOrEqualTo(String value) {
            addCriterion("trans_code >=", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeLessThan(String value) {
            addCriterion("trans_code <", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeLessThanOrEqualTo(String value) {
            addCriterion("trans_code <=", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeLike(String value) {
            addCriterion("trans_code like", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeNotLike(String value) {
            addCriterion("trans_code not like", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeIn(List<String> values) {
            addCriterion("trans_code in", values, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeNotIn(List<String> values) {
            addCriterion("trans_code not in", values, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeBetween(String value1, String value2) {
            addCriterion("trans_code between", value1, value2, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeNotBetween(String value1, String value2) {
            addCriterion("trans_code not between", value1, value2, "transCode");
            return (Criteria) this;
        }

        public Criteria andChannelTransTypeIsNull() {
            addCriterion("channel_trans_type is null");
            return (Criteria) this;
        }

        public Criteria andChannelTransTypeIsNotNull() {
            addCriterion("channel_trans_type is not null");
            return (Criteria) this;
        }

        public Criteria andChannelTransTypeEqualTo(String value) {
            addCriterion("channel_trans_type =", value, "channelTransType");
            return (Criteria) this;
        }

        public Criteria andChannelTransTypeNotEqualTo(String value) {
            addCriterion("channel_trans_type <>", value, "channelTransType");
            return (Criteria) this;
        }

        public Criteria andChannelTransTypeGreaterThan(String value) {
            addCriterion("channel_trans_type >", value, "channelTransType");
            return (Criteria) this;
        }

        public Criteria andChannelTransTypeGreaterThanOrEqualTo(String value) {
            addCriterion("channel_trans_type >=", value, "channelTransType");
            return (Criteria) this;
        }

        public Criteria andChannelTransTypeLessThan(String value) {
            addCriterion("channel_trans_type <", value, "channelTransType");
            return (Criteria) this;
        }

        public Criteria andChannelTransTypeLessThanOrEqualTo(String value) {
            addCriterion("channel_trans_type <=", value, "channelTransType");
            return (Criteria) this;
        }

        public Criteria andChannelTransTypeLike(String value) {
            addCriterion("channel_trans_type like", value, "channelTransType");
            return (Criteria) this;
        }

        public Criteria andChannelTransTypeNotLike(String value) {
            addCriterion("channel_trans_type not like", value, "channelTransType");
            return (Criteria) this;
        }

        public Criteria andChannelTransTypeIn(List<String> values) {
            addCriterion("channel_trans_type in", values, "channelTransType");
            return (Criteria) this;
        }

        public Criteria andChannelTransTypeNotIn(List<String> values) {
            addCriterion("channel_trans_type not in", values, "channelTransType");
            return (Criteria) this;
        }

        public Criteria andChannelTransTypeBetween(String value1, String value2) {
            addCriterion("channel_trans_type between", value1, value2, "channelTransType");
            return (Criteria) this;
        }

        public Criteria andChannelTransTypeNotBetween(String value1, String value2) {
            addCriterion("channel_trans_type not between", value1, value2, "channelTransType");
            return (Criteria) this;
        }

        public Criteria andTransAmountIsNull() {
            addCriterion("trans_amount is null");
            return (Criteria) this;
        }

        public Criteria andTransAmountIsNotNull() {
            addCriterion("trans_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTransAmountEqualTo(Double value) {
            addCriterion("trans_amount =", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountNotEqualTo(Double value) {
            addCriterion("trans_amount <>", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountGreaterThan(Double value) {
            addCriterion("trans_amount >", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("trans_amount >=", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountLessThan(Double value) {
            addCriterion("trans_amount <", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountLessThanOrEqualTo(Double value) {
            addCriterion("trans_amount <=", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountIn(List<Double> values) {
            addCriterion("trans_amount in", values, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountNotIn(List<Double> values) {
            addCriterion("trans_amount not in", values, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountBetween(Double value1, Double value2) {
            addCriterion("trans_amount between", value1, value2, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountNotBetween(Double value1, Double value2) {
            addCriterion("trans_amount not between", value1, value2, "transAmount");
            return (Criteria) this;
        }

        public Criteria andSysTimeIsNull() {
            addCriterion("sys_time is null");
            return (Criteria) this;
        }

        public Criteria andSysTimeIsNotNull() {
            addCriterion("sys_time is not null");
            return (Criteria) this;
        }

        public Criteria andSysTimeEqualTo(Date value) {
            addCriterion("sys_time =", value, "sysTime");
            return (Criteria) this;
        }

        public Criteria andSysTimeNotEqualTo(Date value) {
            addCriterion("sys_time <>", value, "sysTime");
            return (Criteria) this;
        }

        public Criteria andSysTimeGreaterThan(Date value) {
            addCriterion("sys_time >", value, "sysTime");
            return (Criteria) this;
        }

        public Criteria andSysTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sys_time >=", value, "sysTime");
            return (Criteria) this;
        }

        public Criteria andSysTimeLessThan(Date value) {
            addCriterion("sys_time <", value, "sysTime");
            return (Criteria) this;
        }

        public Criteria andSysTimeLessThanOrEqualTo(Date value) {
            addCriterion("sys_time <=", value, "sysTime");
            return (Criteria) this;
        }

        public Criteria andSysTimeIn(List<Date> values) {
            addCriterion("sys_time in", values, "sysTime");
            return (Criteria) this;
        }

        public Criteria andSysTimeNotIn(List<Date> values) {
            addCriterion("sys_time not in", values, "sysTime");
            return (Criteria) this;
        }

        public Criteria andSysTimeBetween(Date value1, Date value2) {
            addCriterion("sys_time between", value1, value2, "sysTime");
            return (Criteria) this;
        }

        public Criteria andSysTimeNotBetween(Date value1, Date value2) {
            addCriterion("sys_time not between", value1, value2, "sysTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeIsNull() {
            addCriterion("channel_time is null");
            return (Criteria) this;
        }

        public Criteria andChannelTimeIsNotNull() {
            addCriterion("channel_time is not null");
            return (Criteria) this;
        }

        public Criteria andChannelTimeEqualTo(Date value) {
            addCriterion("channel_time =", value, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeNotEqualTo(Date value) {
            addCriterion("channel_time <>", value, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeGreaterThan(Date value) {
            addCriterion("channel_time >", value, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("channel_time >=", value, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeLessThan(Date value) {
            addCriterion("channel_time <", value, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeLessThanOrEqualTo(Date value) {
            addCriterion("channel_time <=", value, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeIn(List<Date> values) {
            addCriterion("channel_time in", values, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeNotIn(List<Date> values) {
            addCriterion("channel_time not in", values, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeBetween(Date value1, Date value2) {
            addCriterion("channel_time between", value1, value2, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeNotBetween(Date value1, Date value2) {
            addCriterion("channel_time not between", value1, value2, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoIsNull() {
            addCriterion("channel_jnl_no is null");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoIsNotNull() {
            addCriterion("channel_jnl_no is not null");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoEqualTo(String value) {
            addCriterion("channel_jnl_no =", value, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoNotEqualTo(String value) {
            addCriterion("channel_jnl_no <>", value, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoGreaterThan(String value) {
            addCriterion("channel_jnl_no >", value, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoGreaterThanOrEqualTo(String value) {
            addCriterion("channel_jnl_no >=", value, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoLessThan(String value) {
            addCriterion("channel_jnl_no <", value, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoLessThanOrEqualTo(String value) {
            addCriterion("channel_jnl_no <=", value, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoLike(String value) {
            addCriterion("channel_jnl_no like", value, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoNotLike(String value) {
            addCriterion("channel_jnl_no not like", value, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoIn(List<String> values) {
            addCriterion("channel_jnl_no in", values, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoNotIn(List<String> values) {
            addCriterion("channel_jnl_no not in", values, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoBetween(String value1, String value2) {
            addCriterion("channel_jnl_no between", value1, value2, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoNotBetween(String value1, String value2) {
            addCriterion("channel_jnl_no not between", value1, value2, "channelJnlNo");
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

        public Criteria andSubAccountIdIsNull() {
            addCriterion("sub_account_id is null");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdIsNotNull() {
            addCriterion("sub_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdEqualTo(Integer value) {
            addCriterion("sub_account_id =", value, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdNotEqualTo(Integer value) {
            addCriterion("sub_account_id <>", value, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdGreaterThan(Integer value) {
            addCriterion("sub_account_id >", value, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("sub_account_id >=", value, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdLessThan(Integer value) {
            addCriterion("sub_account_id <", value, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdLessThanOrEqualTo(Integer value) {
            addCriterion("sub_account_id <=", value, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdIn(List<Integer> values) {
            addCriterion("sub_account_id in", values, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdNotIn(List<Integer> values) {
            addCriterion("sub_account_id not in", values, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdBetween(Integer value1, Integer value2) {
            addCriterion("sub_account_id between", value1, value2, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdNotBetween(Integer value1, Integer value2) {
            addCriterion("sub_account_id not between", value1, value2, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeIsNull() {
            addCriterion("sub_account_code is null");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeIsNotNull() {
            addCriterion("sub_account_code is not null");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeEqualTo(String value) {
            addCriterion("sub_account_code =", value, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeNotEqualTo(String value) {
            addCriterion("sub_account_code <>", value, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeGreaterThan(String value) {
            addCriterion("sub_account_code >", value, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeGreaterThanOrEqualTo(String value) {
            addCriterion("sub_account_code >=", value, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeLessThan(String value) {
            addCriterion("sub_account_code <", value, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeLessThanOrEqualTo(String value) {
            addCriterion("sub_account_code <=", value, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeLike(String value) {
            addCriterion("sub_account_code like", value, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeNotLike(String value) {
            addCriterion("sub_account_code not like", value, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeIn(List<String> values) {
            addCriterion("sub_account_code in", values, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeNotIn(List<String> values) {
            addCriterion("sub_account_code not in", values, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeBetween(String value1, String value2) {
            addCriterion("sub_account_code between", value1, value2, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeNotBetween(String value1, String value2) {
            addCriterion("sub_account_code not between", value1, value2, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeIsNull() {
            addCriterion("return_code is null");
            return (Criteria) this;
        }

        public Criteria andReturnCodeIsNotNull() {
            addCriterion("return_code is not null");
            return (Criteria) this;
        }

        public Criteria andReturnCodeEqualTo(String value) {
            addCriterion("return_code =", value, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeNotEqualTo(String value) {
            addCriterion("return_code <>", value, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeGreaterThan(String value) {
            addCriterion("return_code >", value, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeGreaterThanOrEqualTo(String value) {
            addCriterion("return_code >=", value, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeLessThan(String value) {
            addCriterion("return_code <", value, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeLessThanOrEqualTo(String value) {
            addCriterion("return_code <=", value, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeLike(String value) {
            addCriterion("return_code like", value, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeNotLike(String value) {
            addCriterion("return_code not like", value, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeIn(List<String> values) {
            addCriterion("return_code in", values, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeNotIn(List<String> values) {
            addCriterion("return_code not in", values, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeBetween(String value1, String value2) {
            addCriterion("return_code between", value1, value2, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeNotBetween(String value1, String value2) {
            addCriterion("return_code not between", value1, value2, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnMsgIsNull() {
            addCriterion("return_msg is null");
            return (Criteria) this;
        }

        public Criteria andReturnMsgIsNotNull() {
            addCriterion("return_msg is not null");
            return (Criteria) this;
        }

        public Criteria andReturnMsgEqualTo(String value) {
            addCriterion("return_msg =", value, "returnMsg");
            return (Criteria) this;
        }

        public Criteria andReturnMsgNotEqualTo(String value) {
            addCriterion("return_msg <>", value, "returnMsg");
            return (Criteria) this;
        }

        public Criteria andReturnMsgGreaterThan(String value) {
            addCriterion("return_msg >", value, "returnMsg");
            return (Criteria) this;
        }

        public Criteria andReturnMsgGreaterThanOrEqualTo(String value) {
            addCriterion("return_msg >=", value, "returnMsg");
            return (Criteria) this;
        }

        public Criteria andReturnMsgLessThan(String value) {
            addCriterion("return_msg <", value, "returnMsg");
            return (Criteria) this;
        }

        public Criteria andReturnMsgLessThanOrEqualTo(String value) {
            addCriterion("return_msg <=", value, "returnMsg");
            return (Criteria) this;
        }

        public Criteria andReturnMsgLike(String value) {
            addCriterion("return_msg like", value, "returnMsg");
            return (Criteria) this;
        }

        public Criteria andReturnMsgNotLike(String value) {
            addCriterion("return_msg not like", value, "returnMsg");
            return (Criteria) this;
        }

        public Criteria andReturnMsgIn(List<String> values) {
            addCriterion("return_msg in", values, "returnMsg");
            return (Criteria) this;
        }

        public Criteria andReturnMsgNotIn(List<String> values) {
            addCriterion("return_msg not in", values, "returnMsg");
            return (Criteria) this;
        }

        public Criteria andReturnMsgBetween(String value1, String value2) {
            addCriterion("return_msg between", value1, value2, "returnMsg");
            return (Criteria) this;
        }

        public Criteria andReturnMsgNotBetween(String value1, String value2) {
            addCriterion("return_msg not between", value1, value2, "returnMsg");
            return (Criteria) this;
        }

        public Criteria andNoteIsNull() {
            addCriterion("note is null");
            return (Criteria) this;
        }

        public Criteria andNoteIsNotNull() {
            addCriterion("note is not null");
            return (Criteria) this;
        }

        public Criteria andNoteEqualTo(String value) {
            addCriterion("note =", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotEqualTo(String value) {
            addCriterion("note <>", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThan(String value) {
            addCriterion("note >", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThanOrEqualTo(String value) {
            addCriterion("note >=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThan(String value) {
            addCriterion("note <", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThanOrEqualTo(String value) {
            addCriterion("note <=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLike(String value) {
            addCriterion("note like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotLike(String value) {
            addCriterion("note not like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteIn(List<String> values) {
            addCriterion("note in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotIn(List<String> values) {
            addCriterion("note not in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteBetween(String value1, String value2) {
            addCriterion("note between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotBetween(String value1, String value2) {
            addCriterion("note not between", value1, value2, "note");
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