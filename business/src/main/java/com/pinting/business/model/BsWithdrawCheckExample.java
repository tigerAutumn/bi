package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsWithdrawCheckExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsWithdrawCheckExample() {
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

        public Criteria andTerminalTypeIsNull() {
            addCriterion("terminal_type is null");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeIsNotNull() {
            addCriterion("terminal_type is not null");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeEqualTo(Integer value) {
            addCriterion("terminal_type =", value, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeNotEqualTo(Integer value) {
            addCriterion("terminal_type <>", value, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeGreaterThan(Integer value) {
            addCriterion("terminal_type >", value, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("terminal_type >=", value, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeLessThan(Integer value) {
            addCriterion("terminal_type <", value, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeLessThanOrEqualTo(Integer value) {
            addCriterion("terminal_type <=", value, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeIn(List<Integer> values) {
            addCriterion("terminal_type in", values, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeNotIn(List<Integer> values) {
            addCriterion("terminal_type not in", values, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeBetween(Integer value1, Integer value2) {
            addCriterion("terminal_type between", value1, value2, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTerminalTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("terminal_type not between", value1, value2, "terminalType");
            return (Criteria) this;
        }

        public Criteria andTransDetailIdIsNull() {
            addCriterion("trans_detail_id is null");
            return (Criteria) this;
        }

        public Criteria andTransDetailIdIsNotNull() {
            addCriterion("trans_detail_id is not null");
            return (Criteria) this;
        }

        public Criteria andTransDetailIdEqualTo(Integer value) {
            addCriterion("trans_detail_id =", value, "transDetailId");
            return (Criteria) this;
        }

        public Criteria andTransDetailIdNotEqualTo(Integer value) {
            addCriterion("trans_detail_id <>", value, "transDetailId");
            return (Criteria) this;
        }

        public Criteria andTransDetailIdGreaterThan(Integer value) {
            addCriterion("trans_detail_id >", value, "transDetailId");
            return (Criteria) this;
        }

        public Criteria andTransDetailIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("trans_detail_id >=", value, "transDetailId");
            return (Criteria) this;
        }

        public Criteria andTransDetailIdLessThan(Integer value) {
            addCriterion("trans_detail_id <", value, "transDetailId");
            return (Criteria) this;
        }

        public Criteria andTransDetailIdLessThanOrEqualTo(Integer value) {
            addCriterion("trans_detail_id <=", value, "transDetailId");
            return (Criteria) this;
        }

        public Criteria andTransDetailIdIn(List<Integer> values) {
            addCriterion("trans_detail_id in", values, "transDetailId");
            return (Criteria) this;
        }

        public Criteria andTransDetailIdNotIn(List<Integer> values) {
            addCriterion("trans_detail_id not in", values, "transDetailId");
            return (Criteria) this;
        }

        public Criteria andTransDetailIdBetween(Integer value1, Integer value2) {
            addCriterion("trans_detail_id between", value1, value2, "transDetailId");
            return (Criteria) this;
        }

        public Criteria andTransDetailIdNotBetween(Integer value1, Integer value2) {
            addCriterion("trans_detail_id not between", value1, value2, "transDetailId");
            return (Criteria) this;
        }

        public Criteria andSubTransDetailIdIsNull() {
            addCriterion("sub_trans_detail_id is null");
            return (Criteria) this;
        }

        public Criteria andSubTransDetailIdIsNotNull() {
            addCriterion("sub_trans_detail_id is not null");
            return (Criteria) this;
        }

        public Criteria andSubTransDetailIdEqualTo(Integer value) {
            addCriterion("sub_trans_detail_id =", value, "subTransDetailId");
            return (Criteria) this;
        }

        public Criteria andSubTransDetailIdNotEqualTo(Integer value) {
            addCriterion("sub_trans_detail_id <>", value, "subTransDetailId");
            return (Criteria) this;
        }

        public Criteria andSubTransDetailIdGreaterThan(Integer value) {
            addCriterion("sub_trans_detail_id >", value, "subTransDetailId");
            return (Criteria) this;
        }

        public Criteria andSubTransDetailIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("sub_trans_detail_id >=", value, "subTransDetailId");
            return (Criteria) this;
        }

        public Criteria andSubTransDetailIdLessThan(Integer value) {
            addCriterion("sub_trans_detail_id <", value, "subTransDetailId");
            return (Criteria) this;
        }

        public Criteria andSubTransDetailIdLessThanOrEqualTo(Integer value) {
            addCriterion("sub_trans_detail_id <=", value, "subTransDetailId");
            return (Criteria) this;
        }

        public Criteria andSubTransDetailIdIn(List<Integer> values) {
            addCriterion("sub_trans_detail_id in", values, "subTransDetailId");
            return (Criteria) this;
        }

        public Criteria andSubTransDetailIdNotIn(List<Integer> values) {
            addCriterion("sub_trans_detail_id not in", values, "subTransDetailId");
            return (Criteria) this;
        }

        public Criteria andSubTransDetailIdBetween(Integer value1, Integer value2) {
            addCriterion("sub_trans_detail_id between", value1, value2, "subTransDetailId");
            return (Criteria) this;
        }

        public Criteria andSubTransDetailIdNotBetween(Integer value1, Integer value2) {
            addCriterion("sub_trans_detail_id not between", value1, value2, "subTransDetailId");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(Double value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(Double value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(Double value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(Double value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(Double value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<Double> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<Double> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(Double value1, Double value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(Double value1, Double value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andClearingMarkIsNull() {
            addCriterion("clearing_mark is null");
            return (Criteria) this;
        }

        public Criteria andClearingMarkIsNotNull() {
            addCriterion("clearing_mark is not null");
            return (Criteria) this;
        }

        public Criteria andClearingMarkEqualTo(String value) {
            addCriterion("clearing_mark =", value, "clearingMark");
            return (Criteria) this;
        }

        public Criteria andClearingMarkNotEqualTo(String value) {
            addCriterion("clearing_mark <>", value, "clearingMark");
            return (Criteria) this;
        }

        public Criteria andClearingMarkGreaterThan(String value) {
            addCriterion("clearing_mark >", value, "clearingMark");
            return (Criteria) this;
        }

        public Criteria andClearingMarkGreaterThanOrEqualTo(String value) {
            addCriterion("clearing_mark >=", value, "clearingMark");
            return (Criteria) this;
        }

        public Criteria andClearingMarkLessThan(String value) {
            addCriterion("clearing_mark <", value, "clearingMark");
            return (Criteria) this;
        }

        public Criteria andClearingMarkLessThanOrEqualTo(String value) {
            addCriterion("clearing_mark <=", value, "clearingMark");
            return (Criteria) this;
        }

        public Criteria andClearingMarkLike(String value) {
            addCriterion("clearing_mark like", value, "clearingMark");
            return (Criteria) this;
        }

        public Criteria andClearingMarkNotLike(String value) {
            addCriterion("clearing_mark not like", value, "clearingMark");
            return (Criteria) this;
        }

        public Criteria andClearingMarkIn(List<String> values) {
            addCriterion("clearing_mark in", values, "clearingMark");
            return (Criteria) this;
        }

        public Criteria andClearingMarkNotIn(List<String> values) {
            addCriterion("clearing_mark not in", values, "clearingMark");
            return (Criteria) this;
        }

        public Criteria andClearingMarkBetween(String value1, String value2) {
            addCriterion("clearing_mark between", value1, value2, "clearingMark");
            return (Criteria) this;
        }

        public Criteria andClearingMarkNotBetween(String value1, String value2) {
            addCriterion("clearing_mark not between", value1, value2, "clearingMark");
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

        public Criteria andCheckTimeIsNull() {
            addCriterion("check_time is null");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIsNotNull() {
            addCriterion("check_time is not null");
            return (Criteria) this;
        }

        public Criteria andCheckTimeEqualTo(Date value) {
            addCriterion("check_time =", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotEqualTo(Date value) {
            addCriterion("check_time <>", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeGreaterThan(Date value) {
            addCriterion("check_time >", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("check_time >=", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeLessThan(Date value) {
            addCriterion("check_time <", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeLessThanOrEqualTo(Date value) {
            addCriterion("check_time <=", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIn(List<Date> values) {
            addCriterion("check_time in", values, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotIn(List<Date> values) {
            addCriterion("check_time not in", values, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeBetween(Date value1, Date value2) {
            addCriterion("check_time between", value1, value2, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotBetween(Date value1, Date value2) {
            addCriterion("check_time not between", value1, value2, "checkTime");
            return (Criteria) this;
        }

        public Criteria andExecutionTimeIsNull() {
            addCriterion("execution_time is null");
            return (Criteria) this;
        }

        public Criteria andExecutionTimeIsNotNull() {
            addCriterion("execution_time is not null");
            return (Criteria) this;
        }

        public Criteria andExecutionTimeEqualTo(Date value) {
            addCriterion("execution_time =", value, "executionTime");
            return (Criteria) this;
        }

        public Criteria andExecutionTimeNotEqualTo(Date value) {
            addCriterion("execution_time <>", value, "executionTime");
            return (Criteria) this;
        }

        public Criteria andExecutionTimeGreaterThan(Date value) {
            addCriterion("execution_time >", value, "executionTime");
            return (Criteria) this;
        }

        public Criteria andExecutionTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("execution_time >=", value, "executionTime");
            return (Criteria) this;
        }

        public Criteria andExecutionTimeLessThan(Date value) {
            addCriterion("execution_time <", value, "executionTime");
            return (Criteria) this;
        }

        public Criteria andExecutionTimeLessThanOrEqualTo(Date value) {
            addCriterion("execution_time <=", value, "executionTime");
            return (Criteria) this;
        }

        public Criteria andExecutionTimeIn(List<Date> values) {
            addCriterion("execution_time in", values, "executionTime");
            return (Criteria) this;
        }

        public Criteria andExecutionTimeNotIn(List<Date> values) {
            addCriterion("execution_time not in", values, "executionTime");
            return (Criteria) this;
        }

        public Criteria andExecutionTimeBetween(Date value1, Date value2) {
            addCriterion("execution_time between", value1, value2, "executionTime");
            return (Criteria) this;
        }

        public Criteria andExecutionTimeNotBetween(Date value1, Date value2) {
            addCriterion("execution_time not between", value1, value2, "executionTime");
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

        public Criteria andMUserIdIsNull() {
            addCriterion("m_user_id is null");
            return (Criteria) this;
        }

        public Criteria andMUserIdIsNotNull() {
            addCriterion("m_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andMUserIdEqualTo(Integer value) {
            addCriterion("m_user_id =", value, "mUserId");
            return (Criteria) this;
        }

        public Criteria andMUserIdNotEqualTo(Integer value) {
            addCriterion("m_user_id <>", value, "mUserId");
            return (Criteria) this;
        }

        public Criteria andMUserIdGreaterThan(Integer value) {
            addCriterion("m_user_id >", value, "mUserId");
            return (Criteria) this;
        }

        public Criteria andMUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("m_user_id >=", value, "mUserId");
            return (Criteria) this;
        }

        public Criteria andMUserIdLessThan(Integer value) {
            addCriterion("m_user_id <", value, "mUserId");
            return (Criteria) this;
        }

        public Criteria andMUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("m_user_id <=", value, "mUserId");
            return (Criteria) this;
        }

        public Criteria andMUserIdIn(List<Integer> values) {
            addCriterion("m_user_id in", values, "mUserId");
            return (Criteria) this;
        }

        public Criteria andMUserIdNotIn(List<Integer> values) {
            addCriterion("m_user_id not in", values, "mUserId");
            return (Criteria) this;
        }

        public Criteria andMUserIdBetween(Integer value1, Integer value2) {
            addCriterion("m_user_id between", value1, value2, "mUserId");
            return (Criteria) this;
        }

        public Criteria andMUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("m_user_id not between", value1, value2, "mUserId");
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