package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BsDepositionReturnExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsDepositionReturnExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andPrincipalIsNull() {
            addCriterion("principal is null");
            return (Criteria) this;
        }

        public Criteria andPrincipalIsNotNull() {
            addCriterion("principal is not null");
            return (Criteria) this;
        }

        public Criteria andPrincipalEqualTo(Double value) {
            addCriterion("principal =", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalNotEqualTo(Double value) {
            addCriterion("principal <>", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalGreaterThan(Double value) {
            addCriterion("principal >", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalGreaterThanOrEqualTo(Double value) {
            addCriterion("principal >=", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalLessThan(Double value) {
            addCriterion("principal <", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalLessThanOrEqualTo(Double value) {
            addCriterion("principal <=", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalIn(List<Double> values) {
            addCriterion("principal in", values, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalNotIn(List<Double> values) {
            addCriterion("principal not in", values, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalBetween(Double value1, Double value2) {
            addCriterion("principal between", value1, value2, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalNotBetween(Double value1, Double value2) {
            addCriterion("principal not between", value1, value2, "principal");
            return (Criteria) this;
        }

        public Criteria andInterestIsNull() {
            addCriterion("interest is null");
            return (Criteria) this;
        }

        public Criteria andInterestIsNotNull() {
            addCriterion("interest is not null");
            return (Criteria) this;
        }

        public Criteria andInterestEqualTo(Double value) {
            addCriterion("interest =", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotEqualTo(Double value) {
            addCriterion("interest <>", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestGreaterThan(Double value) {
            addCriterion("interest >", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestGreaterThanOrEqualTo(Double value) {
            addCriterion("interest >=", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestLessThan(Double value) {
            addCriterion("interest <", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestLessThanOrEqualTo(Double value) {
            addCriterion("interest <=", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestIn(List<Double> values) {
            addCriterion("interest in", values, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotIn(List<Double> values) {
            addCriterion("interest not in", values, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestBetween(Double value1, Double value2) {
            addCriterion("interest between", value1, value2, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotBetween(Double value1, Double value2) {
            addCriterion("interest not between", value1, value2, "interest");
            return (Criteria) this;
        }

        public Criteria andPenaltyIsNull() {
            addCriterion("penalty is null");
            return (Criteria) this;
        }

        public Criteria andPenaltyIsNotNull() {
            addCriterion("penalty is not null");
            return (Criteria) this;
        }

        public Criteria andPenaltyEqualTo(Double value) {
            addCriterion("penalty =", value, "penalty");
            return (Criteria) this;
        }

        public Criteria andPenaltyNotEqualTo(Double value) {
            addCriterion("penalty <>", value, "penalty");
            return (Criteria) this;
        }

        public Criteria andPenaltyGreaterThan(Double value) {
            addCriterion("penalty >", value, "penalty");
            return (Criteria) this;
        }

        public Criteria andPenaltyGreaterThanOrEqualTo(Double value) {
            addCriterion("penalty >=", value, "penalty");
            return (Criteria) this;
        }

        public Criteria andPenaltyLessThan(Double value) {
            addCriterion("penalty <", value, "penalty");
            return (Criteria) this;
        }

        public Criteria andPenaltyLessThanOrEqualTo(Double value) {
            addCriterion("penalty <=", value, "penalty");
            return (Criteria) this;
        }

        public Criteria andPenaltyIn(List<Double> values) {
            addCriterion("penalty in", values, "penalty");
            return (Criteria) this;
        }

        public Criteria andPenaltyNotIn(List<Double> values) {
            addCriterion("penalty not in", values, "penalty");
            return (Criteria) this;
        }

        public Criteria andPenaltyBetween(Double value1, Double value2) {
            addCriterion("penalty between", value1, value2, "penalty");
            return (Criteria) this;
        }

        public Criteria andPenaltyNotBetween(Double value1, Double value2) {
            addCriterion("penalty not between", value1, value2, "penalty");
            return (Criteria) this;
        }

        public Criteria andBonusIsNull() {
            addCriterion("bonus is null");
            return (Criteria) this;
        }

        public Criteria andBonusIsNotNull() {
            addCriterion("bonus is not null");
            return (Criteria) this;
        }

        public Criteria andBonusEqualTo(Double value) {
            addCriterion("bonus =", value, "bonus");
            return (Criteria) this;
        }

        public Criteria andBonusNotEqualTo(Double value) {
            addCriterion("bonus <>", value, "bonus");
            return (Criteria) this;
        }

        public Criteria andBonusGreaterThan(Double value) {
            addCriterion("bonus >", value, "bonus");
            return (Criteria) this;
        }

        public Criteria andBonusGreaterThanOrEqualTo(Double value) {
            addCriterion("bonus >=", value, "bonus");
            return (Criteria) this;
        }

        public Criteria andBonusLessThan(Double value) {
            addCriterion("bonus <", value, "bonus");
            return (Criteria) this;
        }

        public Criteria andBonusLessThanOrEqualTo(Double value) {
            addCriterion("bonus <=", value, "bonus");
            return (Criteria) this;
        }

        public Criteria andBonusIn(List<Double> values) {
            addCriterion("bonus in", values, "bonus");
            return (Criteria) this;
        }

        public Criteria andBonusNotIn(List<Double> values) {
            addCriterion("bonus not in", values, "bonus");
            return (Criteria) this;
        }

        public Criteria andBonusBetween(Double value1, Double value2) {
            addCriterion("bonus between", value1, value2, "bonus");
            return (Criteria) this;
        }

        public Criteria andBonusNotBetween(Double value1, Double value2) {
            addCriterion("bonus not between", value1, value2, "bonus");
            return (Criteria) this;
        }

        public Criteria andOverflowInterestIsNull() {
            addCriterion("overflow_interest is null");
            return (Criteria) this;
        }

        public Criteria andOverflowInterestIsNotNull() {
            addCriterion("overflow_interest is not null");
            return (Criteria) this;
        }

        public Criteria andOverflowInterestEqualTo(Double value) {
            addCriterion("overflow_interest =", value, "overflowInterest");
            return (Criteria) this;
        }

        public Criteria andOverflowInterestNotEqualTo(Double value) {
            addCriterion("overflow_interest <>", value, "overflowInterest");
            return (Criteria) this;
        }

        public Criteria andOverflowInterestGreaterThan(Double value) {
            addCriterion("overflow_interest >", value, "overflowInterest");
            return (Criteria) this;
        }

        public Criteria andOverflowInterestGreaterThanOrEqualTo(Double value) {
            addCriterion("overflow_interest >=", value, "overflowInterest");
            return (Criteria) this;
        }

        public Criteria andOverflowInterestLessThan(Double value) {
            addCriterion("overflow_interest <", value, "overflowInterest");
            return (Criteria) this;
        }

        public Criteria andOverflowInterestLessThanOrEqualTo(Double value) {
            addCriterion("overflow_interest <=", value, "overflowInterest");
            return (Criteria) this;
        }

        public Criteria andOverflowInterestIn(List<Double> values) {
            addCriterion("overflow_interest in", values, "overflowInterest");
            return (Criteria) this;
        }

        public Criteria andOverflowInterestNotIn(List<Double> values) {
            addCriterion("overflow_interest not in", values, "overflowInterest");
            return (Criteria) this;
        }

        public Criteria andOverflowInterestBetween(Double value1, Double value2) {
            addCriterion("overflow_interest between", value1, value2, "overflowInterest");
            return (Criteria) this;
        }

        public Criteria andOverflowInterestNotBetween(Double value1, Double value2) {
            addCriterion("overflow_interest not between", value1, value2, "overflowInterest");
            return (Criteria) this;
        }

        public Criteria andExpectDateIsNull() {
            addCriterion("expect_date is null");
            return (Criteria) this;
        }

        public Criteria andExpectDateIsNotNull() {
            addCriterion("expect_date is not null");
            return (Criteria) this;
        }

        public Criteria andExpectDateEqualTo(Date value) {
            addCriterionForJDBCDate("expect_date =", value, "expectDate");
            return (Criteria) this;
        }

        public Criteria andExpectDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("expect_date <>", value, "expectDate");
            return (Criteria) this;
        }

        public Criteria andExpectDateGreaterThan(Date value) {
            addCriterionForJDBCDate("expect_date >", value, "expectDate");
            return (Criteria) this;
        }

        public Criteria andExpectDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("expect_date >=", value, "expectDate");
            return (Criteria) this;
        }

        public Criteria andExpectDateLessThan(Date value) {
            addCriterionForJDBCDate("expect_date <", value, "expectDate");
            return (Criteria) this;
        }

        public Criteria andExpectDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("expect_date <=", value, "expectDate");
            return (Criteria) this;
        }

        public Criteria andExpectDateIn(List<Date> values) {
            addCriterionForJDBCDate("expect_date in", values, "expectDate");
            return (Criteria) this;
        }

        public Criteria andExpectDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("expect_date not in", values, "expectDate");
            return (Criteria) this;
        }

        public Criteria andExpectDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("expect_date between", value1, value2, "expectDate");
            return (Criteria) this;
        }

        public Criteria andExpectDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("expect_date not between", value1, value2, "expectDate");
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

        public Criteria andReturnOrderNoIsNull() {
            addCriterion("return_order_no is null");
            return (Criteria) this;
        }

        public Criteria andReturnOrderNoIsNotNull() {
            addCriterion("return_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andReturnOrderNoEqualTo(String value) {
            addCriterion("return_order_no =", value, "returnOrderNo");
            return (Criteria) this;
        }

        public Criteria andReturnOrderNoNotEqualTo(String value) {
            addCriterion("return_order_no <>", value, "returnOrderNo");
            return (Criteria) this;
        }

        public Criteria andReturnOrderNoGreaterThan(String value) {
            addCriterion("return_order_no >", value, "returnOrderNo");
            return (Criteria) this;
        }

        public Criteria andReturnOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("return_order_no >=", value, "returnOrderNo");
            return (Criteria) this;
        }

        public Criteria andReturnOrderNoLessThan(String value) {
            addCriterion("return_order_no <", value, "returnOrderNo");
            return (Criteria) this;
        }

        public Criteria andReturnOrderNoLessThanOrEqualTo(String value) {
            addCriterion("return_order_no <=", value, "returnOrderNo");
            return (Criteria) this;
        }

        public Criteria andReturnOrderNoLike(String value) {
            addCriterion("return_order_no like", value, "returnOrderNo");
            return (Criteria) this;
        }

        public Criteria andReturnOrderNoNotLike(String value) {
            addCriterion("return_order_no not like", value, "returnOrderNo");
            return (Criteria) this;
        }

        public Criteria andReturnOrderNoIn(List<String> values) {
            addCriterion("return_order_no in", values, "returnOrderNo");
            return (Criteria) this;
        }

        public Criteria andReturnOrderNoNotIn(List<String> values) {
            addCriterion("return_order_no not in", values, "returnOrderNo");
            return (Criteria) this;
        }

        public Criteria andReturnOrderNoBetween(String value1, String value2) {
            addCriterion("return_order_no between", value1, value2, "returnOrderNo");
            return (Criteria) this;
        }

        public Criteria andReturnOrderNoNotBetween(String value1, String value2) {
            addCriterion("return_order_no not between", value1, value2, "returnOrderNo");
            return (Criteria) this;
        }

        public Criteria andReturnAmountIsNull() {
            addCriterion("return_amount is null");
            return (Criteria) this;
        }

        public Criteria andReturnAmountIsNotNull() {
            addCriterion("return_amount is not null");
            return (Criteria) this;
        }

        public Criteria andReturnAmountEqualTo(Double value) {
            addCriterion("return_amount =", value, "returnAmount");
            return (Criteria) this;
        }

        public Criteria andReturnAmountNotEqualTo(Double value) {
            addCriterion("return_amount <>", value, "returnAmount");
            return (Criteria) this;
        }

        public Criteria andReturnAmountGreaterThan(Double value) {
            addCriterion("return_amount >", value, "returnAmount");
            return (Criteria) this;
        }

        public Criteria andReturnAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("return_amount >=", value, "returnAmount");
            return (Criteria) this;
        }

        public Criteria andReturnAmountLessThan(Double value) {
            addCriterion("return_amount <", value, "returnAmount");
            return (Criteria) this;
        }

        public Criteria andReturnAmountLessThanOrEqualTo(Double value) {
            addCriterion("return_amount <=", value, "returnAmount");
            return (Criteria) this;
        }

        public Criteria andReturnAmountIn(List<Double> values) {
            addCriterion("return_amount in", values, "returnAmount");
            return (Criteria) this;
        }

        public Criteria andReturnAmountNotIn(List<Double> values) {
            addCriterion("return_amount not in", values, "returnAmount");
            return (Criteria) this;
        }

        public Criteria andReturnAmountBetween(Double value1, Double value2) {
            addCriterion("return_amount between", value1, value2, "returnAmount");
            return (Criteria) this;
        }

        public Criteria andReturnAmountNotBetween(Double value1, Double value2) {
            addCriterion("return_amount not between", value1, value2, "returnAmount");
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