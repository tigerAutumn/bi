package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsProductExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsProductExample() {
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andActivityTypeIsNull() {
            addCriterion("activity_type is null");
            return (Criteria) this;
        }

        public Criteria andActivityTypeIsNotNull() {
            addCriterion("activity_type is not null");
            return (Criteria) this;
        }

        public Criteria andActivityTypeEqualTo(String value) {
            addCriterion("activity_type =", value, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeNotEqualTo(String value) {
            addCriterion("activity_type <>", value, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeGreaterThan(String value) {
            addCriterion("activity_type >", value, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeGreaterThanOrEqualTo(String value) {
            addCriterion("activity_type >=", value, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeLessThan(String value) {
            addCriterion("activity_type <", value, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeLessThanOrEqualTo(String value) {
            addCriterion("activity_type <=", value, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeLike(String value) {
            addCriterion("activity_type like", value, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeNotLike(String value) {
            addCriterion("activity_type not like", value, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeIn(List<String> values) {
            addCriterion("activity_type in", values, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeNotIn(List<String> values) {
            addCriterion("activity_type not in", values, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeBetween(String value1, String value2) {
            addCriterion("activity_type between", value1, value2, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeNotBetween(String value1, String value2) {
            addCriterion("activity_type not between", value1, value2, "activityType");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andInterestTypeIsNull() {
            addCriterion("interest_type is null");
            return (Criteria) this;
        }

        public Criteria andInterestTypeIsNotNull() {
            addCriterion("interest_type is not null");
            return (Criteria) this;
        }

        public Criteria andInterestTypeEqualTo(Integer value) {
            addCriterion("interest_type =", value, "interestType");
            return (Criteria) this;
        }

        public Criteria andInterestTypeNotEqualTo(Integer value) {
            addCriterion("interest_type <>", value, "interestType");
            return (Criteria) this;
        }

        public Criteria andInterestTypeGreaterThan(Integer value) {
            addCriterion("interest_type >", value, "interestType");
            return (Criteria) this;
        }

        public Criteria andInterestTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("interest_type >=", value, "interestType");
            return (Criteria) this;
        }

        public Criteria andInterestTypeLessThan(Integer value) {
            addCriterion("interest_type <", value, "interestType");
            return (Criteria) this;
        }

        public Criteria andInterestTypeLessThanOrEqualTo(Integer value) {
            addCriterion("interest_type <=", value, "interestType");
            return (Criteria) this;
        }

        public Criteria andInterestTypeIn(List<Integer> values) {
            addCriterion("interest_type in", values, "interestType");
            return (Criteria) this;
        }

        public Criteria andInterestTypeNotIn(List<Integer> values) {
            addCriterion("interest_type not in", values, "interestType");
            return (Criteria) this;
        }

        public Criteria andInterestTypeBetween(Integer value1, Integer value2) {
            addCriterion("interest_type between", value1, value2, "interestType");
            return (Criteria) this;
        }

        public Criteria andInterestTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("interest_type not between", value1, value2, "interestType");
            return (Criteria) this;
        }

        public Criteria andBaseRateIsNull() {
            addCriterion("base_rate is null");
            return (Criteria) this;
        }

        public Criteria andBaseRateIsNotNull() {
            addCriterion("base_rate is not null");
            return (Criteria) this;
        }

        public Criteria andBaseRateEqualTo(Double value) {
            addCriterion("base_rate =", value, "baseRate");
            return (Criteria) this;
        }

        public Criteria andBaseRateNotEqualTo(Double value) {
            addCriterion("base_rate <>", value, "baseRate");
            return (Criteria) this;
        }

        public Criteria andBaseRateGreaterThan(Double value) {
            addCriterion("base_rate >", value, "baseRate");
            return (Criteria) this;
        }

        public Criteria andBaseRateGreaterThanOrEqualTo(Double value) {
            addCriterion("base_rate >=", value, "baseRate");
            return (Criteria) this;
        }

        public Criteria andBaseRateLessThan(Double value) {
            addCriterion("base_rate <", value, "baseRate");
            return (Criteria) this;
        }

        public Criteria andBaseRateLessThanOrEqualTo(Double value) {
            addCriterion("base_rate <=", value, "baseRate");
            return (Criteria) this;
        }

        public Criteria andBaseRateIn(List<Double> values) {
            addCriterion("base_rate in", values, "baseRate");
            return (Criteria) this;
        }

        public Criteria andBaseRateNotIn(List<Double> values) {
            addCriterion("base_rate not in", values, "baseRate");
            return (Criteria) this;
        }

        public Criteria andBaseRateBetween(Double value1, Double value2) {
            addCriterion("base_rate between", value1, value2, "baseRate");
            return (Criteria) this;
        }

        public Criteria andBaseRateNotBetween(Double value1, Double value2) {
            addCriterion("base_rate not between", value1, value2, "baseRate");
            return (Criteria) this;
        }

        public Criteria andMaxRateIsNull() {
            addCriterion("max_rate is null");
            return (Criteria) this;
        }

        public Criteria andMaxRateIsNotNull() {
            addCriterion("max_rate is not null");
            return (Criteria) this;
        }

        public Criteria andMaxRateEqualTo(Double value) {
            addCriterion("max_rate =", value, "maxRate");
            return (Criteria) this;
        }

        public Criteria andMaxRateNotEqualTo(Double value) {
            addCriterion("max_rate <>", value, "maxRate");
            return (Criteria) this;
        }

        public Criteria andMaxRateGreaterThan(Double value) {
            addCriterion("max_rate >", value, "maxRate");
            return (Criteria) this;
        }

        public Criteria andMaxRateGreaterThanOrEqualTo(Double value) {
            addCriterion("max_rate >=", value, "maxRate");
            return (Criteria) this;
        }

        public Criteria andMaxRateLessThan(Double value) {
            addCriterion("max_rate <", value, "maxRate");
            return (Criteria) this;
        }

        public Criteria andMaxRateLessThanOrEqualTo(Double value) {
            addCriterion("max_rate <=", value, "maxRate");
            return (Criteria) this;
        }

        public Criteria andMaxRateIn(List<Double> values) {
            addCriterion("max_rate in", values, "maxRate");
            return (Criteria) this;
        }

        public Criteria andMaxRateNotIn(List<Double> values) {
            addCriterion("max_rate not in", values, "maxRate");
            return (Criteria) this;
        }

        public Criteria andMaxRateBetween(Double value1, Double value2) {
            addCriterion("max_rate between", value1, value2, "maxRate");
            return (Criteria) this;
        }

        public Criteria andMaxRateNotBetween(Double value1, Double value2) {
            addCriterion("max_rate not between", value1, value2, "maxRate");
            return (Criteria) this;
        }

        public Criteria andTermIsNull() {
            addCriterion("term is null");
            return (Criteria) this;
        }

        public Criteria andTermIsNotNull() {
            addCriterion("term is not null");
            return (Criteria) this;
        }

        public Criteria andTermEqualTo(Integer value) {
            addCriterion("term =", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermNotEqualTo(Integer value) {
            addCriterion("term <>", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermGreaterThan(Integer value) {
            addCriterion("term >", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermGreaterThanOrEqualTo(Integer value) {
            addCriterion("term >=", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermLessThan(Integer value) {
            addCriterion("term <", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermLessThanOrEqualTo(Integer value) {
            addCriterion("term <=", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermIn(List<Integer> values) {
            addCriterion("term in", values, "term");
            return (Criteria) this;
        }

        public Criteria andTermNotIn(List<Integer> values) {
            addCriterion("term not in", values, "term");
            return (Criteria) this;
        }

        public Criteria andTermBetween(Integer value1, Integer value2) {
            addCriterion("term between", value1, value2, "term");
            return (Criteria) this;
        }

        public Criteria andTermNotBetween(Integer value1, Integer value2) {
            addCriterion("term not between", value1, value2, "term");
            return (Criteria) this;
        }

        public Criteria andMaxTotalAmountIsNull() {
            addCriterion("max_total_amount is null");
            return (Criteria) this;
        }

        public Criteria andMaxTotalAmountIsNotNull() {
            addCriterion("max_total_amount is not null");
            return (Criteria) this;
        }

        public Criteria andMaxTotalAmountEqualTo(Double value) {
            addCriterion("max_total_amount =", value, "maxTotalAmount");
            return (Criteria) this;
        }

        public Criteria andMaxTotalAmountNotEqualTo(Double value) {
            addCriterion("max_total_amount <>", value, "maxTotalAmount");
            return (Criteria) this;
        }

        public Criteria andMaxTotalAmountGreaterThan(Double value) {
            addCriterion("max_total_amount >", value, "maxTotalAmount");
            return (Criteria) this;
        }

        public Criteria andMaxTotalAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("max_total_amount >=", value, "maxTotalAmount");
            return (Criteria) this;
        }

        public Criteria andMaxTotalAmountLessThan(Double value) {
            addCriterion("max_total_amount <", value, "maxTotalAmount");
            return (Criteria) this;
        }

        public Criteria andMaxTotalAmountLessThanOrEqualTo(Double value) {
            addCriterion("max_total_amount <=", value, "maxTotalAmount");
            return (Criteria) this;
        }

        public Criteria andMaxTotalAmountIn(List<Double> values) {
            addCriterion("max_total_amount in", values, "maxTotalAmount");
            return (Criteria) this;
        }

        public Criteria andMaxTotalAmountNotIn(List<Double> values) {
            addCriterion("max_total_amount not in", values, "maxTotalAmount");
            return (Criteria) this;
        }

        public Criteria andMaxTotalAmountBetween(Double value1, Double value2) {
            addCriterion("max_total_amount between", value1, value2, "maxTotalAmount");
            return (Criteria) this;
        }

        public Criteria andMaxTotalAmountNotBetween(Double value1, Double value2) {
            addCriterion("max_total_amount not between", value1, value2, "maxTotalAmount");
            return (Criteria) this;
        }

        public Criteria andMinInvestAmountIsNull() {
            addCriterion("min_invest_amount is null");
            return (Criteria) this;
        }

        public Criteria andMinInvestAmountIsNotNull() {
            addCriterion("min_invest_amount is not null");
            return (Criteria) this;
        }

        public Criteria andMinInvestAmountEqualTo(Double value) {
            addCriterion("min_invest_amount =", value, "minInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMinInvestAmountNotEqualTo(Double value) {
            addCriterion("min_invest_amount <>", value, "minInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMinInvestAmountGreaterThan(Double value) {
            addCriterion("min_invest_amount >", value, "minInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMinInvestAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("min_invest_amount >=", value, "minInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMinInvestAmountLessThan(Double value) {
            addCriterion("min_invest_amount <", value, "minInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMinInvestAmountLessThanOrEqualTo(Double value) {
            addCriterion("min_invest_amount <=", value, "minInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMinInvestAmountIn(List<Double> values) {
            addCriterion("min_invest_amount in", values, "minInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMinInvestAmountNotIn(List<Double> values) {
            addCriterion("min_invest_amount not in", values, "minInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMinInvestAmountBetween(Double value1, Double value2) {
            addCriterion("min_invest_amount between", value1, value2, "minInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMinInvestAmountNotBetween(Double value1, Double value2) {
            addCriterion("min_invest_amount not between", value1, value2, "minInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMaxSingleInvestAmountIsNull() {
            addCriterion("max_single_invest_amount is null");
            return (Criteria) this;
        }

        public Criteria andMaxSingleInvestAmountIsNotNull() {
            addCriterion("max_single_invest_amount is not null");
            return (Criteria) this;
        }

        public Criteria andMaxSingleInvestAmountEqualTo(Double value) {
            addCriterion("max_single_invest_amount =", value, "maxSingleInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMaxSingleInvestAmountNotEqualTo(Double value) {
            addCriterion("max_single_invest_amount <>", value, "maxSingleInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMaxSingleInvestAmountGreaterThan(Double value) {
            addCriterion("max_single_invest_amount >", value, "maxSingleInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMaxSingleInvestAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("max_single_invest_amount >=", value, "maxSingleInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMaxSingleInvestAmountLessThan(Double value) {
            addCriterion("max_single_invest_amount <", value, "maxSingleInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMaxSingleInvestAmountLessThanOrEqualTo(Double value) {
            addCriterion("max_single_invest_amount <=", value, "maxSingleInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMaxSingleInvestAmountIn(List<Double> values) {
            addCriterion("max_single_invest_amount in", values, "maxSingleInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMaxSingleInvestAmountNotIn(List<Double> values) {
            addCriterion("max_single_invest_amount not in", values, "maxSingleInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMaxSingleInvestAmountBetween(Double value1, Double value2) {
            addCriterion("max_single_invest_amount between", value1, value2, "maxSingleInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMaxSingleInvestAmountNotBetween(Double value1, Double value2) {
            addCriterion("max_single_invest_amount not between", value1, value2, "maxSingleInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMaxInvestAmountIsNull() {
            addCriterion("max_invest_amount is null");
            return (Criteria) this;
        }

        public Criteria andMaxInvestAmountIsNotNull() {
            addCriterion("max_invest_amount is not null");
            return (Criteria) this;
        }

        public Criteria andMaxInvestAmountEqualTo(Double value) {
            addCriterion("max_invest_amount =", value, "maxInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMaxInvestAmountNotEqualTo(Double value) {
            addCriterion("max_invest_amount <>", value, "maxInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMaxInvestAmountGreaterThan(Double value) {
            addCriterion("max_invest_amount >", value, "maxInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMaxInvestAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("max_invest_amount >=", value, "maxInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMaxInvestAmountLessThan(Double value) {
            addCriterion("max_invest_amount <", value, "maxInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMaxInvestAmountLessThanOrEqualTo(Double value) {
            addCriterion("max_invest_amount <=", value, "maxInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMaxInvestAmountIn(List<Double> values) {
            addCriterion("max_invest_amount in", values, "maxInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMaxInvestAmountNotIn(List<Double> values) {
            addCriterion("max_invest_amount not in", values, "maxInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMaxInvestAmountBetween(Double value1, Double value2) {
            addCriterion("max_invest_amount between", value1, value2, "maxInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMaxInvestAmountNotBetween(Double value1, Double value2) {
            addCriterion("max_invest_amount not between", value1, value2, "maxInvestAmount");
            return (Criteria) this;
        }

        public Criteria andMaxInvestTimesIsNull() {
            addCriterion("max_invest_times is null");
            return (Criteria) this;
        }

        public Criteria andMaxInvestTimesIsNotNull() {
            addCriterion("max_invest_times is not null");
            return (Criteria) this;
        }

        public Criteria andMaxInvestTimesEqualTo(Integer value) {
            addCriterion("max_invest_times =", value, "maxInvestTimes");
            return (Criteria) this;
        }

        public Criteria andMaxInvestTimesNotEqualTo(Integer value) {
            addCriterion("max_invest_times <>", value, "maxInvestTimes");
            return (Criteria) this;
        }

        public Criteria andMaxInvestTimesGreaterThan(Integer value) {
            addCriterion("max_invest_times >", value, "maxInvestTimes");
            return (Criteria) this;
        }

        public Criteria andMaxInvestTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_invest_times >=", value, "maxInvestTimes");
            return (Criteria) this;
        }

        public Criteria andMaxInvestTimesLessThan(Integer value) {
            addCriterion("max_invest_times <", value, "maxInvestTimes");
            return (Criteria) this;
        }

        public Criteria andMaxInvestTimesLessThanOrEqualTo(Integer value) {
            addCriterion("max_invest_times <=", value, "maxInvestTimes");
            return (Criteria) this;
        }

        public Criteria andMaxInvestTimesIn(List<Integer> values) {
            addCriterion("max_invest_times in", values, "maxInvestTimes");
            return (Criteria) this;
        }

        public Criteria andMaxInvestTimesNotIn(List<Integer> values) {
            addCriterion("max_invest_times not in", values, "maxInvestTimes");
            return (Criteria) this;
        }

        public Criteria andMaxInvestTimesBetween(Integer value1, Integer value2) {
            addCriterion("max_invest_times between", value1, value2, "maxInvestTimes");
            return (Criteria) this;
        }

        public Criteria andMaxInvestTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("max_invest_times not between", value1, value2, "maxInvestTimes");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andCurrTotalAmountIsNull() {
            addCriterion("curr_total_amount is null");
            return (Criteria) this;
        }

        public Criteria andCurrTotalAmountIsNotNull() {
            addCriterion("curr_total_amount is not null");
            return (Criteria) this;
        }

        public Criteria andCurrTotalAmountEqualTo(Double value) {
            addCriterion("curr_total_amount =", value, "currTotalAmount");
            return (Criteria) this;
        }

        public Criteria andCurrTotalAmountNotEqualTo(Double value) {
            addCriterion("curr_total_amount <>", value, "currTotalAmount");
            return (Criteria) this;
        }

        public Criteria andCurrTotalAmountGreaterThan(Double value) {
            addCriterion("curr_total_amount >", value, "currTotalAmount");
            return (Criteria) this;
        }

        public Criteria andCurrTotalAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("curr_total_amount >=", value, "currTotalAmount");
            return (Criteria) this;
        }

        public Criteria andCurrTotalAmountLessThan(Double value) {
            addCriterion("curr_total_amount <", value, "currTotalAmount");
            return (Criteria) this;
        }

        public Criteria andCurrTotalAmountLessThanOrEqualTo(Double value) {
            addCriterion("curr_total_amount <=", value, "currTotalAmount");
            return (Criteria) this;
        }

        public Criteria andCurrTotalAmountIn(List<Double> values) {
            addCriterion("curr_total_amount in", values, "currTotalAmount");
            return (Criteria) this;
        }

        public Criteria andCurrTotalAmountNotIn(List<Double> values) {
            addCriterion("curr_total_amount not in", values, "currTotalAmount");
            return (Criteria) this;
        }

        public Criteria andCurrTotalAmountBetween(Double value1, Double value2) {
            addCriterion("curr_total_amount between", value1, value2, "currTotalAmount");
            return (Criteria) this;
        }

        public Criteria andCurrTotalAmountNotBetween(Double value1, Double value2) {
            addCriterion("curr_total_amount not between", value1, value2, "currTotalAmount");
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

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
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

        public Criteria andInvestNumIsNull() {
            addCriterion("invest_num is null");
            return (Criteria) this;
        }

        public Criteria andInvestNumIsNotNull() {
            addCriterion("invest_num is not null");
            return (Criteria) this;
        }

        public Criteria andInvestNumEqualTo(Integer value) {
            addCriterion("invest_num =", value, "investNum");
            return (Criteria) this;
        }

        public Criteria andInvestNumNotEqualTo(Integer value) {
            addCriterion("invest_num <>", value, "investNum");
            return (Criteria) this;
        }

        public Criteria andInvestNumGreaterThan(Integer value) {
            addCriterion("invest_num >", value, "investNum");
            return (Criteria) this;
        }

        public Criteria andInvestNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_num >=", value, "investNum");
            return (Criteria) this;
        }

        public Criteria andInvestNumLessThan(Integer value) {
            addCriterion("invest_num <", value, "investNum");
            return (Criteria) this;
        }

        public Criteria andInvestNumLessThanOrEqualTo(Integer value) {
            addCriterion("invest_num <=", value, "investNum");
            return (Criteria) this;
        }

        public Criteria andInvestNumIn(List<Integer> values) {
            addCriterion("invest_num in", values, "investNum");
            return (Criteria) this;
        }

        public Criteria andInvestNumNotIn(List<Integer> values) {
            addCriterion("invest_num not in", values, "investNum");
            return (Criteria) this;
        }

        public Criteria andInvestNumBetween(Integer value1, Integer value2) {
            addCriterion("invest_num between", value1, value2, "investNum");
            return (Criteria) this;
        }

        public Criteria andInvestNumNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_num not between", value1, value2, "investNum");
            return (Criteria) this;
        }

        public Criteria andSysReturnRateIsNull() {
            addCriterion("sys_return_rate is null");
            return (Criteria) this;
        }

        public Criteria andSysReturnRateIsNotNull() {
            addCriterion("sys_return_rate is not null");
            return (Criteria) this;
        }

        public Criteria andSysReturnRateEqualTo(Double value) {
            addCriterion("sys_return_rate =", value, "sysReturnRate");
            return (Criteria) this;
        }

        public Criteria andSysReturnRateNotEqualTo(Double value) {
            addCriterion("sys_return_rate <>", value, "sysReturnRate");
            return (Criteria) this;
        }

        public Criteria andSysReturnRateGreaterThan(Double value) {
            addCriterion("sys_return_rate >", value, "sysReturnRate");
            return (Criteria) this;
        }

        public Criteria andSysReturnRateGreaterThanOrEqualTo(Double value) {
            addCriterion("sys_return_rate >=", value, "sysReturnRate");
            return (Criteria) this;
        }

        public Criteria andSysReturnRateLessThan(Double value) {
            addCriterion("sys_return_rate <", value, "sysReturnRate");
            return (Criteria) this;
        }

        public Criteria andSysReturnRateLessThanOrEqualTo(Double value) {
            addCriterion("sys_return_rate <=", value, "sysReturnRate");
            return (Criteria) this;
        }

        public Criteria andSysReturnRateIn(List<Double> values) {
            addCriterion("sys_return_rate in", values, "sysReturnRate");
            return (Criteria) this;
        }

        public Criteria andSysReturnRateNotIn(List<Double> values) {
            addCriterion("sys_return_rate not in", values, "sysReturnRate");
            return (Criteria) this;
        }

        public Criteria andSysReturnRateBetween(Double value1, Double value2) {
            addCriterion("sys_return_rate between", value1, value2, "sysReturnRate");
            return (Criteria) this;
        }

        public Criteria andSysReturnRateNotBetween(Double value1, Double value2) {
            addCriterion("sys_return_rate not between", value1, value2, "sysReturnRate");
            return (Criteria) this;
        }

        public Criteria andSerialIdIsNull() {
            addCriterion("serial_id is null");
            return (Criteria) this;
        }

        public Criteria andSerialIdIsNotNull() {
            addCriterion("serial_id is not null");
            return (Criteria) this;
        }

        public Criteria andSerialIdEqualTo(Integer value) {
            addCriterion("serial_id =", value, "serialId");
            return (Criteria) this;
        }

        public Criteria andSerialIdNotEqualTo(Integer value) {
            addCriterion("serial_id <>", value, "serialId");
            return (Criteria) this;
        }

        public Criteria andSerialIdGreaterThan(Integer value) {
            addCriterion("serial_id >", value, "serialId");
            return (Criteria) this;
        }

        public Criteria andSerialIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("serial_id >=", value, "serialId");
            return (Criteria) this;
        }

        public Criteria andSerialIdLessThan(Integer value) {
            addCriterion("serial_id <", value, "serialId");
            return (Criteria) this;
        }

        public Criteria andSerialIdLessThanOrEqualTo(Integer value) {
            addCriterion("serial_id <=", value, "serialId");
            return (Criteria) this;
        }

        public Criteria andSerialIdIn(List<Integer> values) {
            addCriterion("serial_id in", values, "serialId");
            return (Criteria) this;
        }

        public Criteria andSerialIdNotIn(List<Integer> values) {
            addCriterion("serial_id not in", values, "serialId");
            return (Criteria) this;
        }

        public Criteria andSerialIdBetween(Integer value1, Integer value2) {
            addCriterion("serial_id between", value1, value2, "serialId");
            return (Criteria) this;
        }

        public Criteria andSerialIdNotBetween(Integer value1, Integer value2) {
            addCriterion("serial_id not between", value1, value2, "serialId");
            return (Criteria) this;
        }

        public Criteria andSerialOrderIsNull() {
            addCriterion("serial_order is null");
            return (Criteria) this;
        }

        public Criteria andSerialOrderIsNotNull() {
            addCriterion("serial_order is not null");
            return (Criteria) this;
        }

        public Criteria andSerialOrderEqualTo(Integer value) {
            addCriterion("serial_order =", value, "serialOrder");
            return (Criteria) this;
        }

        public Criteria andSerialOrderNotEqualTo(Integer value) {
            addCriterion("serial_order <>", value, "serialOrder");
            return (Criteria) this;
        }

        public Criteria andSerialOrderGreaterThan(Integer value) {
            addCriterion("serial_order >", value, "serialOrder");
            return (Criteria) this;
        }

        public Criteria andSerialOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("serial_order >=", value, "serialOrder");
            return (Criteria) this;
        }

        public Criteria andSerialOrderLessThan(Integer value) {
            addCriterion("serial_order <", value, "serialOrder");
            return (Criteria) this;
        }

        public Criteria andSerialOrderLessThanOrEqualTo(Integer value) {
            addCriterion("serial_order <=", value, "serialOrder");
            return (Criteria) this;
        }

        public Criteria andSerialOrderIn(List<Integer> values) {
            addCriterion("serial_order in", values, "serialOrder");
            return (Criteria) this;
        }

        public Criteria andSerialOrderNotIn(List<Integer> values) {
            addCriterion("serial_order not in", values, "serialOrder");
            return (Criteria) this;
        }

        public Criteria andSerialOrderBetween(Integer value1, Integer value2) {
            addCriterion("serial_order between", value1, value2, "serialOrder");
            return (Criteria) this;
        }

        public Criteria andSerialOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("serial_order not between", value1, value2, "serialOrder");
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

        public Criteria andPropertyTypeIsNull() {
            addCriterion("property_type is null");
            return (Criteria) this;
        }

        public Criteria andPropertyTypeIsNotNull() {
            addCriterion("property_type is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyTypeEqualTo(String value) {
            addCriterion("property_type =", value, "propertyType");
            return (Criteria) this;
        }

        public Criteria andPropertyTypeNotEqualTo(String value) {
            addCriterion("property_type <>", value, "propertyType");
            return (Criteria) this;
        }

        public Criteria andPropertyTypeGreaterThan(String value) {
            addCriterion("property_type >", value, "propertyType");
            return (Criteria) this;
        }

        public Criteria andPropertyTypeGreaterThanOrEqualTo(String value) {
            addCriterion("property_type >=", value, "propertyType");
            return (Criteria) this;
        }

        public Criteria andPropertyTypeLessThan(String value) {
            addCriterion("property_type <", value, "propertyType");
            return (Criteria) this;
        }

        public Criteria andPropertyTypeLessThanOrEqualTo(String value) {
            addCriterion("property_type <=", value, "propertyType");
            return (Criteria) this;
        }

        public Criteria andPropertyTypeLike(String value) {
            addCriterion("property_type like", value, "propertyType");
            return (Criteria) this;
        }

        public Criteria andPropertyTypeNotLike(String value) {
            addCriterion("property_type not like", value, "propertyType");
            return (Criteria) this;
        }

        public Criteria andPropertyTypeIn(List<String> values) {
            addCriterion("property_type in", values, "propertyType");
            return (Criteria) this;
        }

        public Criteria andPropertyTypeNotIn(List<String> values) {
            addCriterion("property_type not in", values, "propertyType");
            return (Criteria) this;
        }

        public Criteria andPropertyTypeBetween(String value1, String value2) {
            addCriterion("property_type between", value1, value2, "propertyType");
            return (Criteria) this;
        }

        public Criteria andPropertyTypeNotBetween(String value1, String value2) {
            addCriterion("property_type not between", value1, value2, "propertyType");
            return (Criteria) this;
        }

        public Criteria andBeginInterestDaysIsNull() {
            addCriterion("begin_interest_days is null");
            return (Criteria) this;
        }

        public Criteria andBeginInterestDaysIsNotNull() {
            addCriterion("begin_interest_days is not null");
            return (Criteria) this;
        }

        public Criteria andBeginInterestDaysEqualTo(String value) {
            addCriterion("begin_interest_days =", value, "beginInterestDays");
            return (Criteria) this;
        }

        public Criteria andBeginInterestDaysNotEqualTo(String value) {
            addCriterion("begin_interest_days <>", value, "beginInterestDays");
            return (Criteria) this;
        }

        public Criteria andBeginInterestDaysGreaterThan(String value) {
            addCriterion("begin_interest_days >", value, "beginInterestDays");
            return (Criteria) this;
        }

        public Criteria andBeginInterestDaysGreaterThanOrEqualTo(String value) {
            addCriterion("begin_interest_days >=", value, "beginInterestDays");
            return (Criteria) this;
        }

        public Criteria andBeginInterestDaysLessThan(String value) {
            addCriterion("begin_interest_days <", value, "beginInterestDays");
            return (Criteria) this;
        }

        public Criteria andBeginInterestDaysLessThanOrEqualTo(String value) {
            addCriterion("begin_interest_days <=", value, "beginInterestDays");
            return (Criteria) this;
        }

        public Criteria andBeginInterestDaysLike(String value) {
            addCriterion("begin_interest_days like", value, "beginInterestDays");
            return (Criteria) this;
        }

        public Criteria andBeginInterestDaysNotLike(String value) {
            addCriterion("begin_interest_days not like", value, "beginInterestDays");
            return (Criteria) this;
        }

        public Criteria andBeginInterestDaysIn(List<String> values) {
            addCriterion("begin_interest_days in", values, "beginInterestDays");
            return (Criteria) this;
        }

        public Criteria andBeginInterestDaysNotIn(List<String> values) {
            addCriterion("begin_interest_days not in", values, "beginInterestDays");
            return (Criteria) this;
        }

        public Criteria andBeginInterestDaysBetween(String value1, String value2) {
            addCriterion("begin_interest_days between", value1, value2, "beginInterestDays");
            return (Criteria) this;
        }

        public Criteria andBeginInterestDaysNotBetween(String value1, String value2) {
            addCriterion("begin_interest_days not between", value1, value2, "beginInterestDays");
            return (Criteria) this;
        }

        public Criteria andReturnTypeIsNull() {
            addCriterion("return_type is null");
            return (Criteria) this;
        }

        public Criteria andReturnTypeIsNotNull() {
            addCriterion("return_type is not null");
            return (Criteria) this;
        }

        public Criteria andReturnTypeEqualTo(String value) {
            addCriterion("return_type =", value, "returnType");
            return (Criteria) this;
        }

        public Criteria andReturnTypeNotEqualTo(String value) {
            addCriterion("return_type <>", value, "returnType");
            return (Criteria) this;
        }

        public Criteria andReturnTypeGreaterThan(String value) {
            addCriterion("return_type >", value, "returnType");
            return (Criteria) this;
        }

        public Criteria andReturnTypeGreaterThanOrEqualTo(String value) {
            addCriterion("return_type >=", value, "returnType");
            return (Criteria) this;
        }

        public Criteria andReturnTypeLessThan(String value) {
            addCriterion("return_type <", value, "returnType");
            return (Criteria) this;
        }

        public Criteria andReturnTypeLessThanOrEqualTo(String value) {
            addCriterion("return_type <=", value, "returnType");
            return (Criteria) this;
        }

        public Criteria andReturnTypeLike(String value) {
            addCriterion("return_type like", value, "returnType");
            return (Criteria) this;
        }

        public Criteria andReturnTypeNotLike(String value) {
            addCriterion("return_type not like", value, "returnType");
            return (Criteria) this;
        }

        public Criteria andReturnTypeIn(List<String> values) {
            addCriterion("return_type in", values, "returnType");
            return (Criteria) this;
        }

        public Criteria andReturnTypeNotIn(List<String> values) {
            addCriterion("return_type not in", values, "returnType");
            return (Criteria) this;
        }

        public Criteria andReturnTypeBetween(String value1, String value2) {
            addCriterion("return_type between", value1, value2, "returnType");
            return (Criteria) this;
        }

        public Criteria andReturnTypeNotBetween(String value1, String value2) {
            addCriterion("return_type not between", value1, value2, "returnType");
            return (Criteria) this;
        }

        public Criteria andInterestDealIsNull() {
            addCriterion("interest_deal is null");
            return (Criteria) this;
        }

        public Criteria andInterestDealIsNotNull() {
            addCriterion("interest_deal is not null");
            return (Criteria) this;
        }

        public Criteria andInterestDealEqualTo(String value) {
            addCriterion("interest_deal =", value, "interestDeal");
            return (Criteria) this;
        }

        public Criteria andInterestDealNotEqualTo(String value) {
            addCriterion("interest_deal <>", value, "interestDeal");
            return (Criteria) this;
        }

        public Criteria andInterestDealGreaterThan(String value) {
            addCriterion("interest_deal >", value, "interestDeal");
            return (Criteria) this;
        }

        public Criteria andInterestDealGreaterThanOrEqualTo(String value) {
            addCriterion("interest_deal >=", value, "interestDeal");
            return (Criteria) this;
        }

        public Criteria andInterestDealLessThan(String value) {
            addCriterion("interest_deal <", value, "interestDeal");
            return (Criteria) this;
        }

        public Criteria andInterestDealLessThanOrEqualTo(String value) {
            addCriterion("interest_deal <=", value, "interestDeal");
            return (Criteria) this;
        }

        public Criteria andInterestDealLike(String value) {
            addCriterion("interest_deal like", value, "interestDeal");
            return (Criteria) this;
        }

        public Criteria andInterestDealNotLike(String value) {
            addCriterion("interest_deal not like", value, "interestDeal");
            return (Criteria) this;
        }

        public Criteria andInterestDealIn(List<String> values) {
            addCriterion("interest_deal in", values, "interestDeal");
            return (Criteria) this;
        }

        public Criteria andInterestDealNotIn(List<String> values) {
            addCriterion("interest_deal not in", values, "interestDeal");
            return (Criteria) this;
        }

        public Criteria andInterestDealBetween(String value1, String value2) {
            addCriterion("interest_deal between", value1, value2, "interestDeal");
            return (Criteria) this;
        }

        public Criteria andInterestDealNotBetween(String value1, String value2) {
            addCriterion("interest_deal not between", value1, value2, "interestDeal");
            return (Criteria) this;
        }

        public Criteria andIsSupportTransferIsNull() {
            addCriterion("is_support_transfer is null");
            return (Criteria) this;
        }

        public Criteria andIsSupportTransferIsNotNull() {
            addCriterion("is_support_transfer is not null");
            return (Criteria) this;
        }

        public Criteria andIsSupportTransferEqualTo(String value) {
            addCriterion("is_support_transfer =", value, "isSupportTransfer");
            return (Criteria) this;
        }

        public Criteria andIsSupportTransferNotEqualTo(String value) {
            addCriterion("is_support_transfer <>", value, "isSupportTransfer");
            return (Criteria) this;
        }

        public Criteria andIsSupportTransferGreaterThan(String value) {
            addCriterion("is_support_transfer >", value, "isSupportTransfer");
            return (Criteria) this;
        }

        public Criteria andIsSupportTransferGreaterThanOrEqualTo(String value) {
            addCriterion("is_support_transfer >=", value, "isSupportTransfer");
            return (Criteria) this;
        }

        public Criteria andIsSupportTransferLessThan(String value) {
            addCriterion("is_support_transfer <", value, "isSupportTransfer");
            return (Criteria) this;
        }

        public Criteria andIsSupportTransferLessThanOrEqualTo(String value) {
            addCriterion("is_support_transfer <=", value, "isSupportTransfer");
            return (Criteria) this;
        }

        public Criteria andIsSupportTransferLike(String value) {
            addCriterion("is_support_transfer like", value, "isSupportTransfer");
            return (Criteria) this;
        }

        public Criteria andIsSupportTransferNotLike(String value) {
            addCriterion("is_support_transfer not like", value, "isSupportTransfer");
            return (Criteria) this;
        }

        public Criteria andIsSupportTransferIn(List<String> values) {
            addCriterion("is_support_transfer in", values, "isSupportTransfer");
            return (Criteria) this;
        }

        public Criteria andIsSupportTransferNotIn(List<String> values) {
            addCriterion("is_support_transfer not in", values, "isSupportTransfer");
            return (Criteria) this;
        }

        public Criteria andIsSupportTransferBetween(String value1, String value2) {
            addCriterion("is_support_transfer between", value1, value2, "isSupportTransfer");
            return (Criteria) this;
        }

        public Criteria andIsSupportTransferNotBetween(String value1, String value2) {
            addCriterion("is_support_transfer not between", value1, value2, "isSupportTransfer");
            return (Criteria) this;
        }

        public Criteria andManageFeeIsNull() {
            addCriterion("manage_fee is null");
            return (Criteria) this;
        }

        public Criteria andManageFeeIsNotNull() {
            addCriterion("manage_fee is not null");
            return (Criteria) this;
        }

        public Criteria andManageFeeEqualTo(Double value) {
            addCriterion("manage_fee =", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeNotEqualTo(Double value) {
            addCriterion("manage_fee <>", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeGreaterThan(Double value) {
            addCriterion("manage_fee >", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeGreaterThanOrEqualTo(Double value) {
            addCriterion("manage_fee >=", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeLessThan(Double value) {
            addCriterion("manage_fee <", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeLessThanOrEqualTo(Double value) {
            addCriterion("manage_fee <=", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeIn(List<Double> values) {
            addCriterion("manage_fee in", values, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeNotIn(List<Double> values) {
            addCriterion("manage_fee not in", values, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeBetween(Double value1, Double value2) {
            addCriterion("manage_fee between", value1, value2, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeNotBetween(Double value1, Double value2) {
            addCriterion("manage_fee not between", value1, value2, "manageFee");
            return (Criteria) this;
        }

        public Criteria andShowTerminalIsNull() {
            addCriterion("show_terminal is null");
            return (Criteria) this;
        }

        public Criteria andShowTerminalIsNotNull() {
            addCriterion("show_terminal is not null");
            return (Criteria) this;
        }

        public Criteria andShowTerminalEqualTo(String value) {
            addCriterion("show_terminal =", value, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalNotEqualTo(String value) {
            addCriterion("show_terminal <>", value, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalGreaterThan(String value) {
            addCriterion("show_terminal >", value, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalGreaterThanOrEqualTo(String value) {
            addCriterion("show_terminal >=", value, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalLessThan(String value) {
            addCriterion("show_terminal <", value, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalLessThanOrEqualTo(String value) {
            addCriterion("show_terminal <=", value, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalLike(String value) {
            addCriterion("show_terminal like", value, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalNotLike(String value) {
            addCriterion("show_terminal not like", value, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalIn(List<String> values) {
            addCriterion("show_terminal in", values, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalNotIn(List<String> values) {
            addCriterion("show_terminal not in", values, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalBetween(String value1, String value2) {
            addCriterion("show_terminal between", value1, value2, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalNotBetween(String value1, String value2) {
            addCriterion("show_terminal not between", value1, value2, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andPropertyIdIsNull() {
            addCriterion("property_id is null");
            return (Criteria) this;
        }

        public Criteria andPropertyIdIsNotNull() {
            addCriterion("property_id is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyIdEqualTo(Integer value) {
            addCriterion("property_id =", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotEqualTo(Integer value) {
            addCriterion("property_id <>", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdGreaterThan(Integer value) {
            addCriterion("property_id >", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("property_id >=", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdLessThan(Integer value) {
            addCriterion("property_id <", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdLessThanOrEqualTo(Integer value) {
            addCriterion("property_id <=", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdIn(List<Integer> values) {
            addCriterion("property_id in", values, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotIn(List<Integer> values) {
            addCriterion("property_id not in", values, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdBetween(Integer value1, Integer value2) {
            addCriterion("property_id between", value1, value2, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("property_id not between", value1, value2, "propertyId");
            return (Criteria) this;
        }

        public Criteria andIsSuggestIsNull() {
            addCriterion("is_suggest is null");
            return (Criteria) this;
        }

        public Criteria andIsSuggestIsNotNull() {
            addCriterion("is_suggest is not null");
            return (Criteria) this;
        }

        public Criteria andIsSuggestEqualTo(String value) {
            addCriterion("is_suggest =", value, "isSuggest");
            return (Criteria) this;
        }

        public Criteria andIsSuggestNotEqualTo(String value) {
            addCriterion("is_suggest <>", value, "isSuggest");
            return (Criteria) this;
        }

        public Criteria andIsSuggestGreaterThan(String value) {
            addCriterion("is_suggest >", value, "isSuggest");
            return (Criteria) this;
        }

        public Criteria andIsSuggestGreaterThanOrEqualTo(String value) {
            addCriterion("is_suggest >=", value, "isSuggest");
            return (Criteria) this;
        }

        public Criteria andIsSuggestLessThan(String value) {
            addCriterion("is_suggest <", value, "isSuggest");
            return (Criteria) this;
        }

        public Criteria andIsSuggestLessThanOrEqualTo(String value) {
            addCriterion("is_suggest <=", value, "isSuggest");
            return (Criteria) this;
        }

        public Criteria andIsSuggestLike(String value) {
            addCriterion("is_suggest like", value, "isSuggest");
            return (Criteria) this;
        }

        public Criteria andIsSuggestNotLike(String value) {
            addCriterion("is_suggest not like", value, "isSuggest");
            return (Criteria) this;
        }

        public Criteria andIsSuggestIn(List<String> values) {
            addCriterion("is_suggest in", values, "isSuggest");
            return (Criteria) this;
        }

        public Criteria andIsSuggestNotIn(List<String> values) {
            addCriterion("is_suggest not in", values, "isSuggest");
            return (Criteria) this;
        }

        public Criteria andIsSuggestBetween(String value1, String value2) {
            addCriterion("is_suggest between", value1, value2, "isSuggest");
            return (Criteria) this;
        }

        public Criteria andIsSuggestNotBetween(String value1, String value2) {
            addCriterion("is_suggest not between", value1, value2, "isSuggest");
            return (Criteria) this;
        }

        public Criteria andTerminatorIsNull() {
            addCriterion("terminator is null");
            return (Criteria) this;
        }

        public Criteria andTerminatorIsNotNull() {
            addCriterion("terminator is not null");
            return (Criteria) this;
        }

        public Criteria andTerminatorEqualTo(Integer value) {
            addCriterion("terminator =", value, "terminator");
            return (Criteria) this;
        }

        public Criteria andTerminatorNotEqualTo(Integer value) {
            addCriterion("terminator <>", value, "terminator");
            return (Criteria) this;
        }

        public Criteria andTerminatorGreaterThan(Integer value) {
            addCriterion("terminator >", value, "terminator");
            return (Criteria) this;
        }

        public Criteria andTerminatorGreaterThanOrEqualTo(Integer value) {
            addCriterion("terminator >=", value, "terminator");
            return (Criteria) this;
        }

        public Criteria andTerminatorLessThan(Integer value) {
            addCriterion("terminator <", value, "terminator");
            return (Criteria) this;
        }

        public Criteria andTerminatorLessThanOrEqualTo(Integer value) {
            addCriterion("terminator <=", value, "terminator");
            return (Criteria) this;
        }

        public Criteria andTerminatorIn(List<Integer> values) {
            addCriterion("terminator in", values, "terminator");
            return (Criteria) this;
        }

        public Criteria andTerminatorNotIn(List<Integer> values) {
            addCriterion("terminator not in", values, "terminator");
            return (Criteria) this;
        }

        public Criteria andTerminatorBetween(Integer value1, Integer value2) {
            addCriterion("terminator between", value1, value2, "terminator");
            return (Criteria) this;
        }

        public Criteria andTerminatorNotBetween(Integer value1, Integer value2) {
            addCriterion("terminator not between", value1, value2, "terminator");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNull() {
            addCriterion("finish_time is null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNotNull() {
            addCriterion("finish_time is not null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeEqualTo(Date value) {
            addCriterion("finish_time =", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotEqualTo(Date value) {
            addCriterion("finish_time <>", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThan(Date value) {
            addCriterion("finish_time >", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("finish_time >=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThan(Date value) {
            addCriterion("finish_time <", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThanOrEqualTo(Date value) {
            addCriterion("finish_time <=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIn(List<Date> values) {
            addCriterion("finish_time in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotIn(List<Date> values) {
            addCriterion("finish_time not in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeBetween(Date value1, Date value2) {
            addCriterion("finish_time between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotBetween(Date value1, Date value2) {
            addCriterion("finish_time not between", value1, value2, "finishTime");
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

        public Criteria andDistributorIsNull() {
            addCriterion("distributor is null");
            return (Criteria) this;
        }

        public Criteria andDistributorIsNotNull() {
            addCriterion("distributor is not null");
            return (Criteria) this;
        }

        public Criteria andDistributorEqualTo(Integer value) {
            addCriterion("distributor =", value, "distributor");
            return (Criteria) this;
        }

        public Criteria andDistributorNotEqualTo(Integer value) {
            addCriterion("distributor <>", value, "distributor");
            return (Criteria) this;
        }

        public Criteria andDistributorGreaterThan(Integer value) {
            addCriterion("distributor >", value, "distributor");
            return (Criteria) this;
        }

        public Criteria andDistributorGreaterThanOrEqualTo(Integer value) {
            addCriterion("distributor >=", value, "distributor");
            return (Criteria) this;
        }

        public Criteria andDistributorLessThan(Integer value) {
            addCriterion("distributor <", value, "distributor");
            return (Criteria) this;
        }

        public Criteria andDistributorLessThanOrEqualTo(Integer value) {
            addCriterion("distributor <=", value, "distributor");
            return (Criteria) this;
        }

        public Criteria andDistributorIn(List<Integer> values) {
            addCriterion("distributor in", values, "distributor");
            return (Criteria) this;
        }

        public Criteria andDistributorNotIn(List<Integer> values) {
            addCriterion("distributor not in", values, "distributor");
            return (Criteria) this;
        }

        public Criteria andDistributorBetween(Integer value1, Integer value2) {
            addCriterion("distributor between", value1, value2, "distributor");
            return (Criteria) this;
        }

        public Criteria andDistributorNotBetween(Integer value1, Integer value2) {
            addCriterion("distributor not between", value1, value2, "distributor");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeIsNull() {
            addCriterion("distribute_time is null");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeIsNotNull() {
            addCriterion("distribute_time is not null");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeEqualTo(Date value) {
            addCriterion("distribute_time =", value, "distributeTime");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeNotEqualTo(Date value) {
            addCriterion("distribute_time <>", value, "distributeTime");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeGreaterThan(Date value) {
            addCriterion("distribute_time >", value, "distributeTime");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("distribute_time >=", value, "distributeTime");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeLessThan(Date value) {
            addCriterion("distribute_time <", value, "distributeTime");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeLessThanOrEqualTo(Date value) {
            addCriterion("distribute_time <=", value, "distributeTime");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeIn(List<Date> values) {
            addCriterion("distribute_time in", values, "distributeTime");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeNotIn(List<Date> values) {
            addCriterion("distribute_time not in", values, "distributeTime");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeBetween(Date value1, Date value2) {
            addCriterion("distribute_time between", value1, value2, "distributeTime");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeNotBetween(Date value1, Date value2) {
            addCriterion("distribute_time not between", value1, value2, "distributeTime");
            return (Criteria) this;
        }

        public Criteria andIsSupportRedPacketIsNull() {
            addCriterion("is_support_red_packet is null");
            return (Criteria) this;
        }

        public Criteria andIsSupportRedPacketIsNotNull() {
            addCriterion("is_support_red_packet is not null");
            return (Criteria) this;
        }

        public Criteria andIsSupportRedPacketEqualTo(String value) {
            addCriterion("is_support_red_packet =", value, "isSupportRedPacket");
            return (Criteria) this;
        }

        public Criteria andIsSupportRedPacketNotEqualTo(String value) {
            addCriterion("is_support_red_packet <>", value, "isSupportRedPacket");
            return (Criteria) this;
        }

        public Criteria andIsSupportRedPacketGreaterThan(String value) {
            addCriterion("is_support_red_packet >", value, "isSupportRedPacket");
            return (Criteria) this;
        }

        public Criteria andIsSupportRedPacketGreaterThanOrEqualTo(String value) {
            addCriterion("is_support_red_packet >=", value, "isSupportRedPacket");
            return (Criteria) this;
        }

        public Criteria andIsSupportRedPacketLessThan(String value) {
            addCriterion("is_support_red_packet <", value, "isSupportRedPacket");
            return (Criteria) this;
        }

        public Criteria andIsSupportRedPacketLessThanOrEqualTo(String value) {
            addCriterion("is_support_red_packet <=", value, "isSupportRedPacket");
            return (Criteria) this;
        }

        public Criteria andIsSupportRedPacketLike(String value) {
            addCriterion("is_support_red_packet like", value, "isSupportRedPacket");
            return (Criteria) this;
        }

        public Criteria andIsSupportRedPacketNotLike(String value) {
            addCriterion("is_support_red_packet not like", value, "isSupportRedPacket");
            return (Criteria) this;
        }

        public Criteria andIsSupportRedPacketIn(List<String> values) {
            addCriterion("is_support_red_packet in", values, "isSupportRedPacket");
            return (Criteria) this;
        }

        public Criteria andIsSupportRedPacketNotIn(List<String> values) {
            addCriterion("is_support_red_packet not in", values, "isSupportRedPacket");
            return (Criteria) this;
        }

        public Criteria andIsSupportRedPacketBetween(String value1, String value2) {
            addCriterion("is_support_red_packet between", value1, value2, "isSupportRedPacket");
            return (Criteria) this;
        }

        public Criteria andIsSupportRedPacketNotBetween(String value1, String value2) {
            addCriterion("is_support_red_packet not between", value1, value2, "isSupportRedPacket");
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

        public Criteria andIsSupportIncrInterestIsNull() {
            addCriterion("is_support_incr_interest is null");
            return (Criteria) this;
        }

        public Criteria andIsSupportIncrInterestIsNotNull() {
            addCriterion("is_support_incr_interest is not null");
            return (Criteria) this;
        }

        public Criteria andIsSupportIncrInterestEqualTo(String value) {
            addCriterion("is_support_incr_interest =", value, "isSupportIncrInterest");
            return (Criteria) this;
        }

        public Criteria andIsSupportIncrInterestNotEqualTo(String value) {
            addCriterion("is_support_incr_interest <>", value, "isSupportIncrInterest");
            return (Criteria) this;
        }

        public Criteria andIsSupportIncrInterestGreaterThan(String value) {
            addCriterion("is_support_incr_interest >", value, "isSupportIncrInterest");
            return (Criteria) this;
        }

        public Criteria andIsSupportIncrInterestGreaterThanOrEqualTo(String value) {
            addCriterion("is_support_incr_interest >=", value, "isSupportIncrInterest");
            return (Criteria) this;
        }

        public Criteria andIsSupportIncrInterestLessThan(String value) {
            addCriterion("is_support_incr_interest <", value, "isSupportIncrInterest");
            return (Criteria) this;
        }

        public Criteria andIsSupportIncrInterestLessThanOrEqualTo(String value) {
            addCriterion("is_support_incr_interest <=", value, "isSupportIncrInterest");
            return (Criteria) this;
        }

        public Criteria andIsSupportIncrInterestLike(String value) {
            addCriterion("is_support_incr_interest like", value, "isSupportIncrInterest");
            return (Criteria) this;
        }

        public Criteria andIsSupportIncrInterestNotLike(String value) {
            addCriterion("is_support_incr_interest not like", value, "isSupportIncrInterest");
            return (Criteria) this;
        }

        public Criteria andIsSupportIncrInterestIn(List<String> values) {
            addCriterion("is_support_incr_interest in", values, "isSupportIncrInterest");
            return (Criteria) this;
        }

        public Criteria andIsSupportIncrInterestNotIn(List<String> values) {
            addCriterion("is_support_incr_interest not in", values, "isSupportIncrInterest");
            return (Criteria) this;
        }

        public Criteria andIsSupportIncrInterestBetween(String value1, String value2) {
            addCriterion("is_support_incr_interest between", value1, value2, "isSupportIncrInterest");
            return (Criteria) this;
        }

        public Criteria andIsSupportIncrInterestNotBetween(String value1, String value2) {
            addCriterion("is_support_incr_interest not between", value1, value2, "isSupportIncrInterest");
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