package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsRedPacketCheckExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsRedPacketCheckExample() {
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

        public Criteria andApplyNoIsNull() {
            addCriterion("apply_no is null");
            return (Criteria) this;
        }

        public Criteria andApplyNoIsNotNull() {
            addCriterion("apply_no is not null");
            return (Criteria) this;
        }

        public Criteria andApplyNoEqualTo(String value) {
            addCriterion("apply_no =", value, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoNotEqualTo(String value) {
            addCriterion("apply_no <>", value, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoGreaterThan(String value) {
            addCriterion("apply_no >", value, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoGreaterThanOrEqualTo(String value) {
            addCriterion("apply_no >=", value, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoLessThan(String value) {
            addCriterion("apply_no <", value, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoLessThanOrEqualTo(String value) {
            addCriterion("apply_no <=", value, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoLike(String value) {
            addCriterion("apply_no like", value, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoNotLike(String value) {
            addCriterion("apply_no not like", value, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoIn(List<String> values) {
            addCriterion("apply_no in", values, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoNotIn(List<String> values) {
            addCriterion("apply_no not in", values, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoBetween(String value1, String value2) {
            addCriterion("apply_no between", value1, value2, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoNotBetween(String value1, String value2) {
            addCriterion("apply_no not between", value1, value2, "applyNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoIsNull() {
            addCriterion("serial_no is null");
            return (Criteria) this;
        }

        public Criteria andSerialNoIsNotNull() {
            addCriterion("serial_no is not null");
            return (Criteria) this;
        }

        public Criteria andSerialNoEqualTo(String value) {
            addCriterion("serial_no =", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotEqualTo(String value) {
            addCriterion("serial_no <>", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThan(String value) {
            addCriterion("serial_no >", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThanOrEqualTo(String value) {
            addCriterion("serial_no >=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThan(String value) {
            addCriterion("serial_no <", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThanOrEqualTo(String value) {
            addCriterion("serial_no <=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLike(String value) {
            addCriterion("serial_no like", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotLike(String value) {
            addCriterion("serial_no not like", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoIn(List<String> values) {
            addCriterion("serial_no in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotIn(List<String> values) {
            addCriterion("serial_no not in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoBetween(String value1, String value2) {
            addCriterion("serial_no between", value1, value2, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotBetween(String value1, String value2) {
            addCriterion("serial_no not between", value1, value2, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNameIsNull() {
            addCriterion("serial_name is null");
            return (Criteria) this;
        }

        public Criteria andSerialNameIsNotNull() {
            addCriterion("serial_name is not null");
            return (Criteria) this;
        }

        public Criteria andSerialNameEqualTo(String value) {
            addCriterion("serial_name =", value, "serialName");
            return (Criteria) this;
        }

        public Criteria andSerialNameNotEqualTo(String value) {
            addCriterion("serial_name <>", value, "serialName");
            return (Criteria) this;
        }

        public Criteria andSerialNameGreaterThan(String value) {
            addCriterion("serial_name >", value, "serialName");
            return (Criteria) this;
        }

        public Criteria andSerialNameGreaterThanOrEqualTo(String value) {
            addCriterion("serial_name >=", value, "serialName");
            return (Criteria) this;
        }

        public Criteria andSerialNameLessThan(String value) {
            addCriterion("serial_name <", value, "serialName");
            return (Criteria) this;
        }

        public Criteria andSerialNameLessThanOrEqualTo(String value) {
            addCriterion("serial_name <=", value, "serialName");
            return (Criteria) this;
        }

        public Criteria andSerialNameLike(String value) {
            addCriterion("serial_name like", value, "serialName");
            return (Criteria) this;
        }

        public Criteria andSerialNameNotLike(String value) {
            addCriterion("serial_name not like", value, "serialName");
            return (Criteria) this;
        }

        public Criteria andSerialNameIn(List<String> values) {
            addCriterion("serial_name in", values, "serialName");
            return (Criteria) this;
        }

        public Criteria andSerialNameNotIn(List<String> values) {
            addCriterion("serial_name not in", values, "serialName");
            return (Criteria) this;
        }

        public Criteria andSerialNameBetween(String value1, String value2) {
            addCriterion("serial_name between", value1, value2, "serialName");
            return (Criteria) this;
        }

        public Criteria andSerialNameNotBetween(String value1, String value2) {
            addCriterion("serial_name not between", value1, value2, "serialName");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeIsNull() {
            addCriterion("policy_type is null");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeIsNotNull() {
            addCriterion("policy_type is not null");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeEqualTo(String value) {
            addCriterion("policy_type =", value, "policyType");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeNotEqualTo(String value) {
            addCriterion("policy_type <>", value, "policyType");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeGreaterThan(String value) {
            addCriterion("policy_type >", value, "policyType");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeGreaterThanOrEqualTo(String value) {
            addCriterion("policy_type >=", value, "policyType");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeLessThan(String value) {
            addCriterion("policy_type <", value, "policyType");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeLessThanOrEqualTo(String value) {
            addCriterion("policy_type <=", value, "policyType");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeLike(String value) {
            addCriterion("policy_type like", value, "policyType");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeNotLike(String value) {
            addCriterion("policy_type not like", value, "policyType");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeIn(List<String> values) {
            addCriterion("policy_type in", values, "policyType");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeNotIn(List<String> values) {
            addCriterion("policy_type not in", values, "policyType");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeBetween(String value1, String value2) {
            addCriterion("policy_type between", value1, value2, "policyType");
            return (Criteria) this;
        }

        public Criteria andPolicyTypeNotBetween(String value1, String value2) {
            addCriterion("policy_type not between", value1, value2, "policyType");
            return (Criteria) this;
        }

        public Criteria andDistributeTypeIsNull() {
            addCriterion("distribute_type is null");
            return (Criteria) this;
        }

        public Criteria andDistributeTypeIsNotNull() {
            addCriterion("distribute_type is not null");
            return (Criteria) this;
        }

        public Criteria andDistributeTypeEqualTo(String value) {
            addCriterion("distribute_type =", value, "distributeType");
            return (Criteria) this;
        }

        public Criteria andDistributeTypeNotEqualTo(String value) {
            addCriterion("distribute_type <>", value, "distributeType");
            return (Criteria) this;
        }

        public Criteria andDistributeTypeGreaterThan(String value) {
            addCriterion("distribute_type >", value, "distributeType");
            return (Criteria) this;
        }

        public Criteria andDistributeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("distribute_type >=", value, "distributeType");
            return (Criteria) this;
        }

        public Criteria andDistributeTypeLessThan(String value) {
            addCriterion("distribute_type <", value, "distributeType");
            return (Criteria) this;
        }

        public Criteria andDistributeTypeLessThanOrEqualTo(String value) {
            addCriterion("distribute_type <=", value, "distributeType");
            return (Criteria) this;
        }

        public Criteria andDistributeTypeLike(String value) {
            addCriterion("distribute_type like", value, "distributeType");
            return (Criteria) this;
        }

        public Criteria andDistributeTypeNotLike(String value) {
            addCriterion("distribute_type not like", value, "distributeType");
            return (Criteria) this;
        }

        public Criteria andDistributeTypeIn(List<String> values) {
            addCriterion("distribute_type in", values, "distributeType");
            return (Criteria) this;
        }

        public Criteria andDistributeTypeNotIn(List<String> values) {
            addCriterion("distribute_type not in", values, "distributeType");
            return (Criteria) this;
        }

        public Criteria andDistributeTypeBetween(String value1, String value2) {
            addCriterion("distribute_type between", value1, value2, "distributeType");
            return (Criteria) this;
        }

        public Criteria andDistributeTypeNotBetween(String value1, String value2) {
            addCriterion("distribute_type not between", value1, value2, "distributeType");
            return (Criteria) this;
        }

        public Criteria andTotalIsNull() {
            addCriterion("total is null");
            return (Criteria) this;
        }

        public Criteria andTotalIsNotNull() {
            addCriterion("total is not null");
            return (Criteria) this;
        }

        public Criteria andTotalEqualTo(Integer value) {
            addCriterion("total =", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotEqualTo(Integer value) {
            addCriterion("total <>", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThan(Integer value) {
            addCriterion("total >", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThanOrEqualTo(Integer value) {
            addCriterion("total >=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThan(Integer value) {
            addCriterion("total <", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThanOrEqualTo(Integer value) {
            addCriterion("total <=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalIn(List<Integer> values) {
            addCriterion("total in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotIn(List<Integer> values) {
            addCriterion("total not in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalBetween(Integer value1, Integer value2) {
            addCriterion("total between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotBetween(Integer value1, Integer value2) {
            addCriterion("total not between", value1, value2, "total");
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

        public Criteria andUseConditionIsNull() {
            addCriterion("use_condition is null");
            return (Criteria) this;
        }

        public Criteria andUseConditionIsNotNull() {
            addCriterion("use_condition is not null");
            return (Criteria) this;
        }

        public Criteria andUseConditionEqualTo(String value) {
            addCriterion("use_condition =", value, "useCondition");
            return (Criteria) this;
        }

        public Criteria andUseConditionNotEqualTo(String value) {
            addCriterion("use_condition <>", value, "useCondition");
            return (Criteria) this;
        }

        public Criteria andUseConditionGreaterThan(String value) {
            addCriterion("use_condition >", value, "useCondition");
            return (Criteria) this;
        }

        public Criteria andUseConditionGreaterThanOrEqualTo(String value) {
            addCriterion("use_condition >=", value, "useCondition");
            return (Criteria) this;
        }

        public Criteria andUseConditionLessThan(String value) {
            addCriterion("use_condition <", value, "useCondition");
            return (Criteria) this;
        }

        public Criteria andUseConditionLessThanOrEqualTo(String value) {
            addCriterion("use_condition <=", value, "useCondition");
            return (Criteria) this;
        }

        public Criteria andUseConditionLike(String value) {
            addCriterion("use_condition like", value, "useCondition");
            return (Criteria) this;
        }

        public Criteria andUseConditionNotLike(String value) {
            addCriterion("use_condition not like", value, "useCondition");
            return (Criteria) this;
        }

        public Criteria andUseConditionIn(List<String> values) {
            addCriterion("use_condition in", values, "useCondition");
            return (Criteria) this;
        }

        public Criteria andUseConditionNotIn(List<String> values) {
            addCriterion("use_condition not in", values, "useCondition");
            return (Criteria) this;
        }

        public Criteria andUseConditionBetween(String value1, String value2) {
            addCriterion("use_condition between", value1, value2, "useCondition");
            return (Criteria) this;
        }

        public Criteria andUseConditionNotBetween(String value1, String value2) {
            addCriterion("use_condition not between", value1, value2, "useCondition");
            return (Criteria) this;
        }

        public Criteria andFullIsNull() {
            addCriterion("full is null");
            return (Criteria) this;
        }

        public Criteria andFullIsNotNull() {
            addCriterion("full is not null");
            return (Criteria) this;
        }

        public Criteria andFullEqualTo(Double value) {
            addCriterion("full =", value, "full");
            return (Criteria) this;
        }

        public Criteria andFullNotEqualTo(Double value) {
            addCriterion("full <>", value, "full");
            return (Criteria) this;
        }

        public Criteria andFullGreaterThan(Double value) {
            addCriterion("full >", value, "full");
            return (Criteria) this;
        }

        public Criteria andFullGreaterThanOrEqualTo(Double value) {
            addCriterion("full >=", value, "full");
            return (Criteria) this;
        }

        public Criteria andFullLessThan(Double value) {
            addCriterion("full <", value, "full");
            return (Criteria) this;
        }

        public Criteria andFullLessThanOrEqualTo(Double value) {
            addCriterion("full <=", value, "full");
            return (Criteria) this;
        }

        public Criteria andFullIn(List<Double> values) {
            addCriterion("full in", values, "full");
            return (Criteria) this;
        }

        public Criteria andFullNotIn(List<Double> values) {
            addCriterion("full not in", values, "full");
            return (Criteria) this;
        }

        public Criteria andFullBetween(Double value1, Double value2) {
            addCriterion("full between", value1, value2, "full");
            return (Criteria) this;
        }

        public Criteria andFullNotBetween(Double value1, Double value2) {
            addCriterion("full not between", value1, value2, "full");
            return (Criteria) this;
        }

        public Criteria andSubtractIsNull() {
            addCriterion("subtract is null");
            return (Criteria) this;
        }

        public Criteria andSubtractIsNotNull() {
            addCriterion("subtract is not null");
            return (Criteria) this;
        }

        public Criteria andSubtractEqualTo(Double value) {
            addCriterion("subtract =", value, "subtract");
            return (Criteria) this;
        }

        public Criteria andSubtractNotEqualTo(Double value) {
            addCriterion("subtract <>", value, "subtract");
            return (Criteria) this;
        }

        public Criteria andSubtractGreaterThan(Double value) {
            addCriterion("subtract >", value, "subtract");
            return (Criteria) this;
        }

        public Criteria andSubtractGreaterThanOrEqualTo(Double value) {
            addCriterion("subtract >=", value, "subtract");
            return (Criteria) this;
        }

        public Criteria andSubtractLessThan(Double value) {
            addCriterion("subtract <", value, "subtract");
            return (Criteria) this;
        }

        public Criteria andSubtractLessThanOrEqualTo(Double value) {
            addCriterion("subtract <=", value, "subtract");
            return (Criteria) this;
        }

        public Criteria andSubtractIn(List<Double> values) {
            addCriterion("subtract in", values, "subtract");
            return (Criteria) this;
        }

        public Criteria andSubtractNotIn(List<Double> values) {
            addCriterion("subtract not in", values, "subtract");
            return (Criteria) this;
        }

        public Criteria andSubtractBetween(Double value1, Double value2) {
            addCriterion("subtract between", value1, value2, "subtract");
            return (Criteria) this;
        }

        public Criteria andSubtractNotBetween(Double value1, Double value2) {
            addCriterion("subtract not between", value1, value2, "subtract");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartIsNull() {
            addCriterion("use_time_start is null");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartIsNotNull() {
            addCriterion("use_time_start is not null");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartEqualTo(Date value) {
            addCriterion("use_time_start =", value, "useTimeStart");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartNotEqualTo(Date value) {
            addCriterion("use_time_start <>", value, "useTimeStart");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartGreaterThan(Date value) {
            addCriterion("use_time_start >", value, "useTimeStart");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartGreaterThanOrEqualTo(Date value) {
            addCriterion("use_time_start >=", value, "useTimeStart");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartLessThan(Date value) {
            addCriterion("use_time_start <", value, "useTimeStart");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartLessThanOrEqualTo(Date value) {
            addCriterion("use_time_start <=", value, "useTimeStart");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartIn(List<Date> values) {
            addCriterion("use_time_start in", values, "useTimeStart");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartNotIn(List<Date> values) {
            addCriterion("use_time_start not in", values, "useTimeStart");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartBetween(Date value1, Date value2) {
            addCriterion("use_time_start between", value1, value2, "useTimeStart");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartNotBetween(Date value1, Date value2) {
            addCriterion("use_time_start not between", value1, value2, "useTimeStart");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndIsNull() {
            addCriterion("use_time_end is null");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndIsNotNull() {
            addCriterion("use_time_end is not null");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndEqualTo(Date value) {
            addCriterion("use_time_end =", value, "useTimeEnd");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndNotEqualTo(Date value) {
            addCriterion("use_time_end <>", value, "useTimeEnd");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndGreaterThan(Date value) {
            addCriterion("use_time_end >", value, "useTimeEnd");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndGreaterThanOrEqualTo(Date value) {
            addCriterion("use_time_end >=", value, "useTimeEnd");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndLessThan(Date value) {
            addCriterion("use_time_end <", value, "useTimeEnd");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndLessThanOrEqualTo(Date value) {
            addCriterion("use_time_end <=", value, "useTimeEnd");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndIn(List<Date> values) {
            addCriterion("use_time_end in", values, "useTimeEnd");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndNotIn(List<Date> values) {
            addCriterion("use_time_end not in", values, "useTimeEnd");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndBetween(Date value1, Date value2) {
            addCriterion("use_time_end between", value1, value2, "useTimeEnd");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndNotBetween(Date value1, Date value2) {
            addCriterion("use_time_end not between", value1, value2, "useTimeEnd");
            return (Criteria) this;
        }

        public Criteria andApplicantIsNull() {
            addCriterion("applicant is null");
            return (Criteria) this;
        }

        public Criteria andApplicantIsNotNull() {
            addCriterion("applicant is not null");
            return (Criteria) this;
        }

        public Criteria andApplicantEqualTo(Integer value) {
            addCriterion("applicant =", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantNotEqualTo(Integer value) {
            addCriterion("applicant <>", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantGreaterThan(Integer value) {
            addCriterion("applicant >", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantGreaterThanOrEqualTo(Integer value) {
            addCriterion("applicant >=", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantLessThan(Integer value) {
            addCriterion("applicant <", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantLessThanOrEqualTo(Integer value) {
            addCriterion("applicant <=", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantIn(List<Integer> values) {
            addCriterion("applicant in", values, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantNotIn(List<Integer> values) {
            addCriterion("applicant not in", values, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantBetween(Integer value1, Integer value2) {
            addCriterion("applicant between", value1, value2, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantNotBetween(Integer value1, Integer value2) {
            addCriterion("applicant not between", value1, value2, "applicant");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNull() {
            addCriterion("check_status is null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNotNull() {
            addCriterion("check_status is not null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusEqualTo(String value) {
            addCriterion("check_status =", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotEqualTo(String value) {
            addCriterion("check_status <>", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThan(String value) {
            addCriterion("check_status >", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThanOrEqualTo(String value) {
            addCriterion("check_status >=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThan(String value) {
            addCriterion("check_status <", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThanOrEqualTo(String value) {
            addCriterion("check_status <=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLike(String value) {
            addCriterion("check_status like", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotLike(String value) {
            addCriterion("check_status not like", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIn(List<String> values) {
            addCriterion("check_status in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotIn(List<String> values) {
            addCriterion("check_status not in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusBetween(String value1, String value2) {
            addCriterion("check_status between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotBetween(String value1, String value2) {
            addCriterion("check_status not between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusIsNull() {
            addCriterion("msg_status is null");
            return (Criteria) this;
        }

        public Criteria andMsgStatusIsNotNull() {
            addCriterion("msg_status is not null");
            return (Criteria) this;
        }

        public Criteria andMsgStatusEqualTo(String value) {
            addCriterion("msg_status =", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusNotEqualTo(String value) {
            addCriterion("msg_status <>", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusGreaterThan(String value) {
            addCriterion("msg_status >", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusGreaterThanOrEqualTo(String value) {
            addCriterion("msg_status >=", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusLessThan(String value) {
            addCriterion("msg_status <", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusLessThanOrEqualTo(String value) {
            addCriterion("msg_status <=", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusLike(String value) {
            addCriterion("msg_status like", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusNotLike(String value) {
            addCriterion("msg_status not like", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusIn(List<String> values) {
            addCriterion("msg_status in", values, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusNotIn(List<String> values) {
            addCriterion("msg_status not in", values, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusBetween(String value1, String value2) {
            addCriterion("msg_status between", value1, value2, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusNotBetween(String value1, String value2) {
            addCriterion("msg_status not between", value1, value2, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andCheckerIsNull() {
            addCriterion("checker is null");
            return (Criteria) this;
        }

        public Criteria andCheckerIsNotNull() {
            addCriterion("checker is not null");
            return (Criteria) this;
        }

        public Criteria andCheckerEqualTo(Integer value) {
            addCriterion("checker =", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerNotEqualTo(Integer value) {
            addCriterion("checker <>", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerGreaterThan(Integer value) {
            addCriterion("checker >", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerGreaterThanOrEqualTo(Integer value) {
            addCriterion("checker >=", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerLessThan(Integer value) {
            addCriterion("checker <", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerLessThanOrEqualTo(Integer value) {
            addCriterion("checker <=", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerIn(List<Integer> values) {
            addCriterion("checker in", values, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerNotIn(List<Integer> values) {
            addCriterion("checker not in", values, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerBetween(Integer value1, Integer value2) {
            addCriterion("checker between", value1, value2, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerNotBetween(Integer value1, Integer value2) {
            addCriterion("checker not between", value1, value2, "checker");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelIsNull() {
            addCriterion("notify_channel is null");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelIsNotNull() {
            addCriterion("notify_channel is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelEqualTo(String value) {
            addCriterion("notify_channel =", value, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelNotEqualTo(String value) {
            addCriterion("notify_channel <>", value, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelGreaterThan(String value) {
            addCriterion("notify_channel >", value, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelGreaterThanOrEqualTo(String value) {
            addCriterion("notify_channel >=", value, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelLessThan(String value) {
            addCriterion("notify_channel <", value, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelLessThanOrEqualTo(String value) {
            addCriterion("notify_channel <=", value, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelLike(String value) {
            addCriterion("notify_channel like", value, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelNotLike(String value) {
            addCriterion("notify_channel not like", value, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelIn(List<String> values) {
            addCriterion("notify_channel in", values, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelNotIn(List<String> values) {
            addCriterion("notify_channel not in", values, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelBetween(String value1, String value2) {
            addCriterion("notify_channel between", value1, value2, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelNotBetween(String value1, String value2) {
            addCriterion("notify_channel not between", value1, value2, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andTermLimitIsNull() {
            addCriterion("term_limit is null");
            return (Criteria) this;
        }

        public Criteria andTermLimitIsNotNull() {
            addCriterion("term_limit is not null");
            return (Criteria) this;
        }

        public Criteria andTermLimitEqualTo(String value) {
            addCriterion("term_limit =", value, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitNotEqualTo(String value) {
            addCriterion("term_limit <>", value, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitGreaterThan(String value) {
            addCriterion("term_limit >", value, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitGreaterThanOrEqualTo(String value) {
            addCriterion("term_limit >=", value, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitLessThan(String value) {
            addCriterion("term_limit <", value, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitLessThanOrEqualTo(String value) {
            addCriterion("term_limit <=", value, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitLike(String value) {
            addCriterion("term_limit like", value, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitNotLike(String value) {
            addCriterion("term_limit not like", value, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitIn(List<String> values) {
            addCriterion("term_limit in", values, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitNotIn(List<String> values) {
            addCriterion("term_limit not in", values, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitBetween(String value1, String value2) {
            addCriterion("term_limit between", value1, value2, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitNotBetween(String value1, String value2) {
            addCriterion("term_limit not between", value1, value2, "termLimit");
            return (Criteria) this;
        }

        public Criteria andManualConditionsIsNull() {
            addCriterion("manual_conditions is null");
            return (Criteria) this;
        }

        public Criteria andManualConditionsIsNotNull() {
            addCriterion("manual_conditions is not null");
            return (Criteria) this;
        }

        public Criteria andManualConditionsEqualTo(String value) {
            addCriterion("manual_conditions =", value, "manualConditions");
            return (Criteria) this;
        }

        public Criteria andManualConditionsNotEqualTo(String value) {
            addCriterion("manual_conditions <>", value, "manualConditions");
            return (Criteria) this;
        }

        public Criteria andManualConditionsGreaterThan(String value) {
            addCriterion("manual_conditions >", value, "manualConditions");
            return (Criteria) this;
        }

        public Criteria andManualConditionsGreaterThanOrEqualTo(String value) {
            addCriterion("manual_conditions >=", value, "manualConditions");
            return (Criteria) this;
        }

        public Criteria andManualConditionsLessThan(String value) {
            addCriterion("manual_conditions <", value, "manualConditions");
            return (Criteria) this;
        }

        public Criteria andManualConditionsLessThanOrEqualTo(String value) {
            addCriterion("manual_conditions <=", value, "manualConditions");
            return (Criteria) this;
        }

        public Criteria andManualConditionsLike(String value) {
            addCriterion("manual_conditions like", value, "manualConditions");
            return (Criteria) this;
        }

        public Criteria andManualConditionsNotLike(String value) {
            addCriterion("manual_conditions not like", value, "manualConditions");
            return (Criteria) this;
        }

        public Criteria andManualConditionsIn(List<String> values) {
            addCriterion("manual_conditions in", values, "manualConditions");
            return (Criteria) this;
        }

        public Criteria andManualConditionsNotIn(List<String> values) {
            addCriterion("manual_conditions not in", values, "manualConditions");
            return (Criteria) this;
        }

        public Criteria andManualConditionsBetween(String value1, String value2) {
            addCriterion("manual_conditions between", value1, value2, "manualConditions");
            return (Criteria) this;
        }

        public Criteria andManualConditionsNotBetween(String value1, String value2) {
            addCriterion("manual_conditions not between", value1, value2, "manualConditions");
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