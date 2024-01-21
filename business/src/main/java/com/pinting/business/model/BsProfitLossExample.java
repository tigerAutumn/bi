package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BsProfitLossExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsProfitLossExample() {
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

        public Criteria andClearDateIsNull() {
            addCriterion("clear_date is null");
            return (Criteria) this;
        }

        public Criteria andClearDateIsNotNull() {
            addCriterion("clear_date is not null");
            return (Criteria) this;
        }

        public Criteria andClearDateEqualTo(Date value) {
            addCriterionForJDBCDate("clear_date =", value, "clearDate");
            return (Criteria) this;
        }

        public Criteria andClearDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("clear_date <>", value, "clearDate");
            return (Criteria) this;
        }

        public Criteria andClearDateGreaterThan(Date value) {
            addCriterionForJDBCDate("clear_date >", value, "clearDate");
            return (Criteria) this;
        }

        public Criteria andClearDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("clear_date >=", value, "clearDate");
            return (Criteria) this;
        }

        public Criteria andClearDateLessThan(Date value) {
            addCriterionForJDBCDate("clear_date <", value, "clearDate");
            return (Criteria) this;
        }

        public Criteria andClearDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("clear_date <=", value, "clearDate");
            return (Criteria) this;
        }

        public Criteria andClearDateIn(List<Date> values) {
            addCriterionForJDBCDate("clear_date in", values, "clearDate");
            return (Criteria) this;
        }

        public Criteria andClearDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("clear_date not in", values, "clearDate");
            return (Criteria) this;
        }

        public Criteria andClearDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("clear_date between", value1, value2, "clearDate");
            return (Criteria) this;
        }

        public Criteria andClearDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("clear_date not between", value1, value2, "clearDate");
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

        public Criteria andSysInterestIsNull() {
            addCriterion("sys_interest is null");
            return (Criteria) this;
        }

        public Criteria andSysInterestIsNotNull() {
            addCriterion("sys_interest is not null");
            return (Criteria) this;
        }

        public Criteria andSysInterestEqualTo(Double value) {
            addCriterion("sys_interest =", value, "sysInterest");
            return (Criteria) this;
        }

        public Criteria andSysInterestNotEqualTo(Double value) {
            addCriterion("sys_interest <>", value, "sysInterest");
            return (Criteria) this;
        }

        public Criteria andSysInterestGreaterThan(Double value) {
            addCriterion("sys_interest >", value, "sysInterest");
            return (Criteria) this;
        }

        public Criteria andSysInterestGreaterThanOrEqualTo(Double value) {
            addCriterion("sys_interest >=", value, "sysInterest");
            return (Criteria) this;
        }

        public Criteria andSysInterestLessThan(Double value) {
            addCriterion("sys_interest <", value, "sysInterest");
            return (Criteria) this;
        }

        public Criteria andSysInterestLessThanOrEqualTo(Double value) {
            addCriterion("sys_interest <=", value, "sysInterest");
            return (Criteria) this;
        }

        public Criteria andSysInterestIn(List<Double> values) {
            addCriterion("sys_interest in", values, "sysInterest");
            return (Criteria) this;
        }

        public Criteria andSysInterestNotIn(List<Double> values) {
            addCriterion("sys_interest not in", values, "sysInterest");
            return (Criteria) this;
        }

        public Criteria andSysInterestBetween(Double value1, Double value2) {
            addCriterion("sys_interest between", value1, value2, "sysInterest");
            return (Criteria) this;
        }

        public Criteria andSysInterestNotBetween(Double value1, Double value2) {
            addCriterion("sys_interest not between", value1, value2, "sysInterest");
            return (Criteria) this;
        }

        public Criteria andPintingProfitIsNull() {
            addCriterion("pinting_profit is null");
            return (Criteria) this;
        }

        public Criteria andPintingProfitIsNotNull() {
            addCriterion("pinting_profit is not null");
            return (Criteria) this;
        }

        public Criteria andPintingProfitEqualTo(Double value) {
            addCriterion("pinting_profit =", value, "pintingProfit");
            return (Criteria) this;
        }

        public Criteria andPintingProfitNotEqualTo(Double value) {
            addCriterion("pinting_profit <>", value, "pintingProfit");
            return (Criteria) this;
        }

        public Criteria andPintingProfitGreaterThan(Double value) {
            addCriterion("pinting_profit >", value, "pintingProfit");
            return (Criteria) this;
        }

        public Criteria andPintingProfitGreaterThanOrEqualTo(Double value) {
            addCriterion("pinting_profit >=", value, "pintingProfit");
            return (Criteria) this;
        }

        public Criteria andPintingProfitLessThan(Double value) {
            addCriterion("pinting_profit <", value, "pintingProfit");
            return (Criteria) this;
        }

        public Criteria andPintingProfitLessThanOrEqualTo(Double value) {
            addCriterion("pinting_profit <=", value, "pintingProfit");
            return (Criteria) this;
        }

        public Criteria andPintingProfitIn(List<Double> values) {
            addCriterion("pinting_profit in", values, "pintingProfit");
            return (Criteria) this;
        }

        public Criteria andPintingProfitNotIn(List<Double> values) {
            addCriterion("pinting_profit not in", values, "pintingProfit");
            return (Criteria) this;
        }

        public Criteria andPintingProfitBetween(Double value1, Double value2) {
            addCriterion("pinting_profit between", value1, value2, "pintingProfit");
            return (Criteria) this;
        }

        public Criteria andPintingProfitNotBetween(Double value1, Double value2) {
            addCriterion("pinting_profit not between", value1, value2, "pintingProfit");
            return (Criteria) this;
        }

        public Criteria andShouldBonusIsNull() {
            addCriterion("should_bonus is null");
            return (Criteria) this;
        }

        public Criteria andShouldBonusIsNotNull() {
            addCriterion("should_bonus is not null");
            return (Criteria) this;
        }

        public Criteria andShouldBonusEqualTo(Double value) {
            addCriterion("should_bonus =", value, "shouldBonus");
            return (Criteria) this;
        }

        public Criteria andShouldBonusNotEqualTo(Double value) {
            addCriterion("should_bonus <>", value, "shouldBonus");
            return (Criteria) this;
        }

        public Criteria andShouldBonusGreaterThan(Double value) {
            addCriterion("should_bonus >", value, "shouldBonus");
            return (Criteria) this;
        }

        public Criteria andShouldBonusGreaterThanOrEqualTo(Double value) {
            addCriterion("should_bonus >=", value, "shouldBonus");
            return (Criteria) this;
        }

        public Criteria andShouldBonusLessThan(Double value) {
            addCriterion("should_bonus <", value, "shouldBonus");
            return (Criteria) this;
        }

        public Criteria andShouldBonusLessThanOrEqualTo(Double value) {
            addCriterion("should_bonus <=", value, "shouldBonus");
            return (Criteria) this;
        }

        public Criteria andShouldBonusIn(List<Double> values) {
            addCriterion("should_bonus in", values, "shouldBonus");
            return (Criteria) this;
        }

        public Criteria andShouldBonusNotIn(List<Double> values) {
            addCriterion("should_bonus not in", values, "shouldBonus");
            return (Criteria) this;
        }

        public Criteria andShouldBonusBetween(Double value1, Double value2) {
            addCriterion("should_bonus between", value1, value2, "shouldBonus");
            return (Criteria) this;
        }

        public Criteria andShouldBonusNotBetween(Double value1, Double value2) {
            addCriterion("should_bonus not between", value1, value2, "shouldBonus");
            return (Criteria) this;
        }

        public Criteria andSurplusBonusIsNull() {
            addCriterion("surplus_bonus is null");
            return (Criteria) this;
        }

        public Criteria andSurplusBonusIsNotNull() {
            addCriterion("surplus_bonus is not null");
            return (Criteria) this;
        }

        public Criteria andSurplusBonusEqualTo(Double value) {
            addCriterion("surplus_bonus =", value, "surplusBonus");
            return (Criteria) this;
        }

        public Criteria andSurplusBonusNotEqualTo(Double value) {
            addCriterion("surplus_bonus <>", value, "surplusBonus");
            return (Criteria) this;
        }

        public Criteria andSurplusBonusGreaterThan(Double value) {
            addCriterion("surplus_bonus >", value, "surplusBonus");
            return (Criteria) this;
        }

        public Criteria andSurplusBonusGreaterThanOrEqualTo(Double value) {
            addCriterion("surplus_bonus >=", value, "surplusBonus");
            return (Criteria) this;
        }

        public Criteria andSurplusBonusLessThan(Double value) {
            addCriterion("surplus_bonus <", value, "surplusBonus");
            return (Criteria) this;
        }

        public Criteria andSurplusBonusLessThanOrEqualTo(Double value) {
            addCriterion("surplus_bonus <=", value, "surplusBonus");
            return (Criteria) this;
        }

        public Criteria andSurplusBonusIn(List<Double> values) {
            addCriterion("surplus_bonus in", values, "surplusBonus");
            return (Criteria) this;
        }

        public Criteria andSurplusBonusNotIn(List<Double> values) {
            addCriterion("surplus_bonus not in", values, "surplusBonus");
            return (Criteria) this;
        }

        public Criteria andSurplusBonusBetween(Double value1, Double value2) {
            addCriterion("surplus_bonus between", value1, value2, "surplusBonus");
            return (Criteria) this;
        }

        public Criteria andSurplusBonusNotBetween(Double value1, Double value2) {
            addCriterion("surplus_bonus not between", value1, value2, "surplusBonus");
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

        public Criteria andDafy2PercentIsNull() {
            addCriterion("dafy_2_percent is null");
            return (Criteria) this;
        }

        public Criteria andDafy2PercentIsNotNull() {
            addCriterion("dafy_2_percent is not null");
            return (Criteria) this;
        }

        public Criteria andDafy2PercentEqualTo(Double value) {
            addCriterion("dafy_2_percent =", value, "dafy2Percent");
            return (Criteria) this;
        }

        public Criteria andDafy2PercentNotEqualTo(Double value) {
            addCriterion("dafy_2_percent <>", value, "dafy2Percent");
            return (Criteria) this;
        }

        public Criteria andDafy2PercentGreaterThan(Double value) {
            addCriterion("dafy_2_percent >", value, "dafy2Percent");
            return (Criteria) this;
        }

        public Criteria andDafy2PercentGreaterThanOrEqualTo(Double value) {
            addCriterion("dafy_2_percent >=", value, "dafy2Percent");
            return (Criteria) this;
        }

        public Criteria andDafy2PercentLessThan(Double value) {
            addCriterion("dafy_2_percent <", value, "dafy2Percent");
            return (Criteria) this;
        }

        public Criteria andDafy2PercentLessThanOrEqualTo(Double value) {
            addCriterion("dafy_2_percent <=", value, "dafy2Percent");
            return (Criteria) this;
        }

        public Criteria andDafy2PercentIn(List<Double> values) {
            addCriterion("dafy_2_percent in", values, "dafy2Percent");
            return (Criteria) this;
        }

        public Criteria andDafy2PercentNotIn(List<Double> values) {
            addCriterion("dafy_2_percent not in", values, "dafy2Percent");
            return (Criteria) this;
        }

        public Criteria andDafy2PercentBetween(Double value1, Double value2) {
            addCriterion("dafy_2_percent between", value1, value2, "dafy2Percent");
            return (Criteria) this;
        }

        public Criteria andDafy2PercentNotBetween(Double value1, Double value2) {
            addCriterion("dafy_2_percent not between", value1, value2, "dafy2Percent");
            return (Criteria) this;
        }

        public Criteria andDafyShouldProfitIsNull() {
            addCriterion("dafy_should_profit is null");
            return (Criteria) this;
        }

        public Criteria andDafyShouldProfitIsNotNull() {
            addCriterion("dafy_should_profit is not null");
            return (Criteria) this;
        }

        public Criteria andDafyShouldProfitEqualTo(Double value) {
            addCriterion("dafy_should_profit =", value, "dafyShouldProfit");
            return (Criteria) this;
        }

        public Criteria andDafyShouldProfitNotEqualTo(Double value) {
            addCriterion("dafy_should_profit <>", value, "dafyShouldProfit");
            return (Criteria) this;
        }

        public Criteria andDafyShouldProfitGreaterThan(Double value) {
            addCriterion("dafy_should_profit >", value, "dafyShouldProfit");
            return (Criteria) this;
        }

        public Criteria andDafyShouldProfitGreaterThanOrEqualTo(Double value) {
            addCriterion("dafy_should_profit >=", value, "dafyShouldProfit");
            return (Criteria) this;
        }

        public Criteria andDafyShouldProfitLessThan(Double value) {
            addCriterion("dafy_should_profit <", value, "dafyShouldProfit");
            return (Criteria) this;
        }

        public Criteria andDafyShouldProfitLessThanOrEqualTo(Double value) {
            addCriterion("dafy_should_profit <=", value, "dafyShouldProfit");
            return (Criteria) this;
        }

        public Criteria andDafyShouldProfitIn(List<Double> values) {
            addCriterion("dafy_should_profit in", values, "dafyShouldProfit");
            return (Criteria) this;
        }

        public Criteria andDafyShouldProfitNotIn(List<Double> values) {
            addCriterion("dafy_should_profit not in", values, "dafyShouldProfit");
            return (Criteria) this;
        }

        public Criteria andDafyShouldProfitBetween(Double value1, Double value2) {
            addCriterion("dafy_should_profit between", value1, value2, "dafyShouldProfit");
            return (Criteria) this;
        }

        public Criteria andDafyShouldProfitNotBetween(Double value1, Double value2) {
            addCriterion("dafy_should_profit not between", value1, value2, "dafyShouldProfit");
            return (Criteria) this;
        }

        public Criteria andDafySendProfitIsNull() {
            addCriterion("dafy_send_profit is null");
            return (Criteria) this;
        }

        public Criteria andDafySendProfitIsNotNull() {
            addCriterion("dafy_send_profit is not null");
            return (Criteria) this;
        }

        public Criteria andDafySendProfitEqualTo(Double value) {
            addCriterion("dafy_send_profit =", value, "dafySendProfit");
            return (Criteria) this;
        }

        public Criteria andDafySendProfitNotEqualTo(Double value) {
            addCriterion("dafy_send_profit <>", value, "dafySendProfit");
            return (Criteria) this;
        }

        public Criteria andDafySendProfitGreaterThan(Double value) {
            addCriterion("dafy_send_profit >", value, "dafySendProfit");
            return (Criteria) this;
        }

        public Criteria andDafySendProfitGreaterThanOrEqualTo(Double value) {
            addCriterion("dafy_send_profit >=", value, "dafySendProfit");
            return (Criteria) this;
        }

        public Criteria andDafySendProfitLessThan(Double value) {
            addCriterion("dafy_send_profit <", value, "dafySendProfit");
            return (Criteria) this;
        }

        public Criteria andDafySendProfitLessThanOrEqualTo(Double value) {
            addCriterion("dafy_send_profit <=", value, "dafySendProfit");
            return (Criteria) this;
        }

        public Criteria andDafySendProfitIn(List<Double> values) {
            addCriterion("dafy_send_profit in", values, "dafySendProfit");
            return (Criteria) this;
        }

        public Criteria andDafySendProfitNotIn(List<Double> values) {
            addCriterion("dafy_send_profit not in", values, "dafySendProfit");
            return (Criteria) this;
        }

        public Criteria andDafySendProfitBetween(Double value1, Double value2) {
            addCriterion("dafy_send_profit between", value1, value2, "dafySendProfit");
            return (Criteria) this;
        }

        public Criteria andDafySendProfitNotBetween(Double value1, Double value2) {
            addCriterion("dafy_send_profit not between", value1, value2, "dafySendProfit");
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