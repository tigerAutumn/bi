package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsProductInvestViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsProductInvestViewExample() {
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

        public Criteria andTodayInvest7IsNull() {
            addCriterion("today_invest_7 is null");
            return (Criteria) this;
        }

        public Criteria andTodayInvest7IsNotNull() {
            addCriterion("today_invest_7 is not null");
            return (Criteria) this;
        }

        public Criteria andTodayInvest7EqualTo(Double value) {
            addCriterion("today_invest_7 =", value, "todayInvest7");
            return (Criteria) this;
        }

        public Criteria andTodayInvest7NotEqualTo(Double value) {
            addCriterion("today_invest_7 <>", value, "todayInvest7");
            return (Criteria) this;
        }

        public Criteria andTodayInvest7GreaterThan(Double value) {
            addCriterion("today_invest_7 >", value, "todayInvest7");
            return (Criteria) this;
        }

        public Criteria andTodayInvest7GreaterThanOrEqualTo(Double value) {
            addCriterion("today_invest_7 >=", value, "todayInvest7");
            return (Criteria) this;
        }

        public Criteria andTodayInvest7LessThan(Double value) {
            addCriterion("today_invest_7 <", value, "todayInvest7");
            return (Criteria) this;
        }

        public Criteria andTodayInvest7LessThanOrEqualTo(Double value) {
            addCriterion("today_invest_7 <=", value, "todayInvest7");
            return (Criteria) this;
        }

        public Criteria andTodayInvest7In(List<Double> values) {
            addCriterion("today_invest_7 in", values, "todayInvest7");
            return (Criteria) this;
        }

        public Criteria andTodayInvest7NotIn(List<Double> values) {
            addCriterion("today_invest_7 not in", values, "todayInvest7");
            return (Criteria) this;
        }

        public Criteria andTodayInvest7Between(Double value1, Double value2) {
            addCriterion("today_invest_7 between", value1, value2, "todayInvest7");
            return (Criteria) this;
        }

        public Criteria andTodayInvest7NotBetween(Double value1, Double value2) {
            addCriterion("today_invest_7 not between", value1, value2, "todayInvest7");
            return (Criteria) this;
        }

        public Criteria andTotalInvest7IsNull() {
            addCriterion("total_invest_7 is null");
            return (Criteria) this;
        }

        public Criteria andTotalInvest7IsNotNull() {
            addCriterion("total_invest_7 is not null");
            return (Criteria) this;
        }

        public Criteria andTotalInvest7EqualTo(Double value) {
            addCriterion("total_invest_7 =", value, "totalInvest7");
            return (Criteria) this;
        }

        public Criteria andTotalInvest7NotEqualTo(Double value) {
            addCriterion("total_invest_7 <>", value, "totalInvest7");
            return (Criteria) this;
        }

        public Criteria andTotalInvest7GreaterThan(Double value) {
            addCriterion("total_invest_7 >", value, "totalInvest7");
            return (Criteria) this;
        }

        public Criteria andTotalInvest7GreaterThanOrEqualTo(Double value) {
            addCriterion("total_invest_7 >=", value, "totalInvest7");
            return (Criteria) this;
        }

        public Criteria andTotalInvest7LessThan(Double value) {
            addCriterion("total_invest_7 <", value, "totalInvest7");
            return (Criteria) this;
        }

        public Criteria andTotalInvest7LessThanOrEqualTo(Double value) {
            addCriterion("total_invest_7 <=", value, "totalInvest7");
            return (Criteria) this;
        }

        public Criteria andTotalInvest7In(List<Double> values) {
            addCriterion("total_invest_7 in", values, "totalInvest7");
            return (Criteria) this;
        }

        public Criteria andTotalInvest7NotIn(List<Double> values) {
            addCriterion("total_invest_7 not in", values, "totalInvest7");
            return (Criteria) this;
        }

        public Criteria andTotalInvest7Between(Double value1, Double value2) {
            addCriterion("total_invest_7 between", value1, value2, "totalInvest7");
            return (Criteria) this;
        }

        public Criteria andTotalInvest7NotBetween(Double value1, Double value2) {
            addCriterion("total_invest_7 not between", value1, value2, "totalInvest7");
            return (Criteria) this;
        }

        public Criteria andTodayInvest30IsNull() {
            addCriterion("today_invest_30 is null");
            return (Criteria) this;
        }

        public Criteria andTodayInvest30IsNotNull() {
            addCriterion("today_invest_30 is not null");
            return (Criteria) this;
        }

        public Criteria andTodayInvest30EqualTo(Double value) {
            addCriterion("today_invest_30 =", value, "todayInvest30");
            return (Criteria) this;
        }

        public Criteria andTodayInvest30NotEqualTo(Double value) {
            addCriterion("today_invest_30 <>", value, "todayInvest30");
            return (Criteria) this;
        }

        public Criteria andTodayInvest30GreaterThan(Double value) {
            addCriterion("today_invest_30 >", value, "todayInvest30");
            return (Criteria) this;
        }

        public Criteria andTodayInvest30GreaterThanOrEqualTo(Double value) {
            addCriterion("today_invest_30 >=", value, "todayInvest30");
            return (Criteria) this;
        }

        public Criteria andTodayInvest30LessThan(Double value) {
            addCriterion("today_invest_30 <", value, "todayInvest30");
            return (Criteria) this;
        }

        public Criteria andTodayInvest30LessThanOrEqualTo(Double value) {
            addCriterion("today_invest_30 <=", value, "todayInvest30");
            return (Criteria) this;
        }

        public Criteria andTodayInvest30In(List<Double> values) {
            addCriterion("today_invest_30 in", values, "todayInvest30");
            return (Criteria) this;
        }

        public Criteria andTodayInvest30NotIn(List<Double> values) {
            addCriterion("today_invest_30 not in", values, "todayInvest30");
            return (Criteria) this;
        }

        public Criteria andTodayInvest30Between(Double value1, Double value2) {
            addCriterion("today_invest_30 between", value1, value2, "todayInvest30");
            return (Criteria) this;
        }

        public Criteria andTodayInvest30NotBetween(Double value1, Double value2) {
            addCriterion("today_invest_30 not between", value1, value2, "todayInvest30");
            return (Criteria) this;
        }

        public Criteria andTotalInvest30IsNull() {
            addCriterion("total_invest_30 is null");
            return (Criteria) this;
        }

        public Criteria andTotalInvest30IsNotNull() {
            addCriterion("total_invest_30 is not null");
            return (Criteria) this;
        }

        public Criteria andTotalInvest30EqualTo(Double value) {
            addCriterion("total_invest_30 =", value, "totalInvest30");
            return (Criteria) this;
        }

        public Criteria andTotalInvest30NotEqualTo(Double value) {
            addCriterion("total_invest_30 <>", value, "totalInvest30");
            return (Criteria) this;
        }

        public Criteria andTotalInvest30GreaterThan(Double value) {
            addCriterion("total_invest_30 >", value, "totalInvest30");
            return (Criteria) this;
        }

        public Criteria andTotalInvest30GreaterThanOrEqualTo(Double value) {
            addCriterion("total_invest_30 >=", value, "totalInvest30");
            return (Criteria) this;
        }

        public Criteria andTotalInvest30LessThan(Double value) {
            addCriterion("total_invest_30 <", value, "totalInvest30");
            return (Criteria) this;
        }

        public Criteria andTotalInvest30LessThanOrEqualTo(Double value) {
            addCriterion("total_invest_30 <=", value, "totalInvest30");
            return (Criteria) this;
        }

        public Criteria andTotalInvest30In(List<Double> values) {
            addCriterion("total_invest_30 in", values, "totalInvest30");
            return (Criteria) this;
        }

        public Criteria andTotalInvest30NotIn(List<Double> values) {
            addCriterion("total_invest_30 not in", values, "totalInvest30");
            return (Criteria) this;
        }

        public Criteria andTotalInvest30Between(Double value1, Double value2) {
            addCriterion("total_invest_30 between", value1, value2, "totalInvest30");
            return (Criteria) this;
        }

        public Criteria andTotalInvest30NotBetween(Double value1, Double value2) {
            addCriterion("total_invest_30 not between", value1, value2, "totalInvest30");
            return (Criteria) this;
        }

        public Criteria andTodayInvest90IsNull() {
            addCriterion("today_invest_90 is null");
            return (Criteria) this;
        }

        public Criteria andTodayInvest90IsNotNull() {
            addCriterion("today_invest_90 is not null");
            return (Criteria) this;
        }

        public Criteria andTodayInvest90EqualTo(Double value) {
            addCriterion("today_invest_90 =", value, "todayInvest90");
            return (Criteria) this;
        }

        public Criteria andTodayInvest90NotEqualTo(Double value) {
            addCriterion("today_invest_90 <>", value, "todayInvest90");
            return (Criteria) this;
        }

        public Criteria andTodayInvest90GreaterThan(Double value) {
            addCriterion("today_invest_90 >", value, "todayInvest90");
            return (Criteria) this;
        }

        public Criteria andTodayInvest90GreaterThanOrEqualTo(Double value) {
            addCriterion("today_invest_90 >=", value, "todayInvest90");
            return (Criteria) this;
        }

        public Criteria andTodayInvest90LessThan(Double value) {
            addCriterion("today_invest_90 <", value, "todayInvest90");
            return (Criteria) this;
        }

        public Criteria andTodayInvest90LessThanOrEqualTo(Double value) {
            addCriterion("today_invest_90 <=", value, "todayInvest90");
            return (Criteria) this;
        }

        public Criteria andTodayInvest90In(List<Double> values) {
            addCriterion("today_invest_90 in", values, "todayInvest90");
            return (Criteria) this;
        }

        public Criteria andTodayInvest90NotIn(List<Double> values) {
            addCriterion("today_invest_90 not in", values, "todayInvest90");
            return (Criteria) this;
        }

        public Criteria andTodayInvest90Between(Double value1, Double value2) {
            addCriterion("today_invest_90 between", value1, value2, "todayInvest90");
            return (Criteria) this;
        }

        public Criteria andTodayInvest90NotBetween(Double value1, Double value2) {
            addCriterion("today_invest_90 not between", value1, value2, "todayInvest90");
            return (Criteria) this;
        }

        public Criteria andTotalInvest90IsNull() {
            addCriterion("total_invest_90 is null");
            return (Criteria) this;
        }

        public Criteria andTotalInvest90IsNotNull() {
            addCriterion("total_invest_90 is not null");
            return (Criteria) this;
        }

        public Criteria andTotalInvest90EqualTo(Double value) {
            addCriterion("total_invest_90 =", value, "totalInvest90");
            return (Criteria) this;
        }

        public Criteria andTotalInvest90NotEqualTo(Double value) {
            addCriterion("total_invest_90 <>", value, "totalInvest90");
            return (Criteria) this;
        }

        public Criteria andTotalInvest90GreaterThan(Double value) {
            addCriterion("total_invest_90 >", value, "totalInvest90");
            return (Criteria) this;
        }

        public Criteria andTotalInvest90GreaterThanOrEqualTo(Double value) {
            addCriterion("total_invest_90 >=", value, "totalInvest90");
            return (Criteria) this;
        }

        public Criteria andTotalInvest90LessThan(Double value) {
            addCriterion("total_invest_90 <", value, "totalInvest90");
            return (Criteria) this;
        }

        public Criteria andTotalInvest90LessThanOrEqualTo(Double value) {
            addCriterion("total_invest_90 <=", value, "totalInvest90");
            return (Criteria) this;
        }

        public Criteria andTotalInvest90In(List<Double> values) {
            addCriterion("total_invest_90 in", values, "totalInvest90");
            return (Criteria) this;
        }

        public Criteria andTotalInvest90NotIn(List<Double> values) {
            addCriterion("total_invest_90 not in", values, "totalInvest90");
            return (Criteria) this;
        }

        public Criteria andTotalInvest90Between(Double value1, Double value2) {
            addCriterion("total_invest_90 between", value1, value2, "totalInvest90");
            return (Criteria) this;
        }

        public Criteria andTotalInvest90NotBetween(Double value1, Double value2) {
            addCriterion("total_invest_90 not between", value1, value2, "totalInvest90");
            return (Criteria) this;
        }

        public Criteria andTodayInvest180IsNull() {
            addCriterion("today_invest_180 is null");
            return (Criteria) this;
        }

        public Criteria andTodayInvest180IsNotNull() {
            addCriterion("today_invest_180 is not null");
            return (Criteria) this;
        }

        public Criteria andTodayInvest180EqualTo(Double value) {
            addCriterion("today_invest_180 =", value, "todayInvest180");
            return (Criteria) this;
        }

        public Criteria andTodayInvest180NotEqualTo(Double value) {
            addCriterion("today_invest_180 <>", value, "todayInvest180");
            return (Criteria) this;
        }

        public Criteria andTodayInvest180GreaterThan(Double value) {
            addCriterion("today_invest_180 >", value, "todayInvest180");
            return (Criteria) this;
        }

        public Criteria andTodayInvest180GreaterThanOrEqualTo(Double value) {
            addCriterion("today_invest_180 >=", value, "todayInvest180");
            return (Criteria) this;
        }

        public Criteria andTodayInvest180LessThan(Double value) {
            addCriterion("today_invest_180 <", value, "todayInvest180");
            return (Criteria) this;
        }

        public Criteria andTodayInvest180LessThanOrEqualTo(Double value) {
            addCriterion("today_invest_180 <=", value, "todayInvest180");
            return (Criteria) this;
        }

        public Criteria andTodayInvest180In(List<Double> values) {
            addCriterion("today_invest_180 in", values, "todayInvest180");
            return (Criteria) this;
        }

        public Criteria andTodayInvest180NotIn(List<Double> values) {
            addCriterion("today_invest_180 not in", values, "todayInvest180");
            return (Criteria) this;
        }

        public Criteria andTodayInvest180Between(Double value1, Double value2) {
            addCriterion("today_invest_180 between", value1, value2, "todayInvest180");
            return (Criteria) this;
        }

        public Criteria andTodayInvest180NotBetween(Double value1, Double value2) {
            addCriterion("today_invest_180 not between", value1, value2, "todayInvest180");
            return (Criteria) this;
        }

        public Criteria andTotalInvest180IsNull() {
            addCriterion("total_invest_180 is null");
            return (Criteria) this;
        }

        public Criteria andTotalInvest180IsNotNull() {
            addCriterion("total_invest_180 is not null");
            return (Criteria) this;
        }

        public Criteria andTotalInvest180EqualTo(Double value) {
            addCriterion("total_invest_180 =", value, "totalInvest180");
            return (Criteria) this;
        }

        public Criteria andTotalInvest180NotEqualTo(Double value) {
            addCriterion("total_invest_180 <>", value, "totalInvest180");
            return (Criteria) this;
        }

        public Criteria andTotalInvest180GreaterThan(Double value) {
            addCriterion("total_invest_180 >", value, "totalInvest180");
            return (Criteria) this;
        }

        public Criteria andTotalInvest180GreaterThanOrEqualTo(Double value) {
            addCriterion("total_invest_180 >=", value, "totalInvest180");
            return (Criteria) this;
        }

        public Criteria andTotalInvest180LessThan(Double value) {
            addCriterion("total_invest_180 <", value, "totalInvest180");
            return (Criteria) this;
        }

        public Criteria andTotalInvest180LessThanOrEqualTo(Double value) {
            addCriterion("total_invest_180 <=", value, "totalInvest180");
            return (Criteria) this;
        }

        public Criteria andTotalInvest180In(List<Double> values) {
            addCriterion("total_invest_180 in", values, "totalInvest180");
            return (Criteria) this;
        }

        public Criteria andTotalInvest180NotIn(List<Double> values) {
            addCriterion("total_invest_180 not in", values, "totalInvest180");
            return (Criteria) this;
        }

        public Criteria andTotalInvest180Between(Double value1, Double value2) {
            addCriterion("total_invest_180 between", value1, value2, "totalInvest180");
            return (Criteria) this;
        }

        public Criteria andTotalInvest180NotBetween(Double value1, Double value2) {
            addCriterion("total_invest_180 not between", value1, value2, "totalInvest180");
            return (Criteria) this;
        }

        public Criteria andTodayInvest270IsNull() {
            addCriterion("today_invest_270 is null");
            return (Criteria) this;
        }

        public Criteria andTodayInvest270IsNotNull() {
            addCriterion("today_invest_270 is not null");
            return (Criteria) this;
        }

        public Criteria andTodayInvest270EqualTo(Double value) {
            addCriterion("today_invest_270 =", value, "todayInvest270");
            return (Criteria) this;
        }

        public Criteria andTodayInvest270NotEqualTo(Double value) {
            addCriterion("today_invest_270 <>", value, "todayInvest270");
            return (Criteria) this;
        }

        public Criteria andTodayInvest270GreaterThan(Double value) {
            addCriterion("today_invest_270 >", value, "todayInvest270");
            return (Criteria) this;
        }

        public Criteria andTodayInvest270GreaterThanOrEqualTo(Double value) {
            addCriterion("today_invest_270 >=", value, "todayInvest270");
            return (Criteria) this;
        }

        public Criteria andTodayInvest270LessThan(Double value) {
            addCriterion("today_invest_270 <", value, "todayInvest270");
            return (Criteria) this;
        }

        public Criteria andTodayInvest270LessThanOrEqualTo(Double value) {
            addCriterion("today_invest_270 <=", value, "todayInvest270");
            return (Criteria) this;
        }

        public Criteria andTodayInvest270In(List<Double> values) {
            addCriterion("today_invest_270 in", values, "todayInvest270");
            return (Criteria) this;
        }

        public Criteria andTodayInvest270NotIn(List<Double> values) {
            addCriterion("today_invest_270 not in", values, "todayInvest270");
            return (Criteria) this;
        }

        public Criteria andTodayInvest270Between(Double value1, Double value2) {
            addCriterion("today_invest_270 between", value1, value2, "todayInvest270");
            return (Criteria) this;
        }

        public Criteria andTodayInvest270NotBetween(Double value1, Double value2) {
            addCriterion("today_invest_270 not between", value1, value2, "todayInvest270");
            return (Criteria) this;
        }

        public Criteria andTotalInvest270IsNull() {
            addCriterion("total_invest_270 is null");
            return (Criteria) this;
        }

        public Criteria andTotalInvest270IsNotNull() {
            addCriterion("total_invest_270 is not null");
            return (Criteria) this;
        }

        public Criteria andTotalInvest270EqualTo(Double value) {
            addCriterion("total_invest_270 =", value, "totalInvest270");
            return (Criteria) this;
        }

        public Criteria andTotalInvest270NotEqualTo(Double value) {
            addCriterion("total_invest_270 <>", value, "totalInvest270");
            return (Criteria) this;
        }

        public Criteria andTotalInvest270GreaterThan(Double value) {
            addCriterion("total_invest_270 >", value, "totalInvest270");
            return (Criteria) this;
        }

        public Criteria andTotalInvest270GreaterThanOrEqualTo(Double value) {
            addCriterion("total_invest_270 >=", value, "totalInvest270");
            return (Criteria) this;
        }

        public Criteria andTotalInvest270LessThan(Double value) {
            addCriterion("total_invest_270 <", value, "totalInvest270");
            return (Criteria) this;
        }

        public Criteria andTotalInvest270LessThanOrEqualTo(Double value) {
            addCriterion("total_invest_270 <=", value, "totalInvest270");
            return (Criteria) this;
        }

        public Criteria andTotalInvest270In(List<Double> values) {
            addCriterion("total_invest_270 in", values, "totalInvest270");
            return (Criteria) this;
        }

        public Criteria andTotalInvest270NotIn(List<Double> values) {
            addCriterion("total_invest_270 not in", values, "totalInvest270");
            return (Criteria) this;
        }

        public Criteria andTotalInvest270Between(Double value1, Double value2) {
            addCriterion("total_invest_270 between", value1, value2, "totalInvest270");
            return (Criteria) this;
        }

        public Criteria andTotalInvest270NotBetween(Double value1, Double value2) {
            addCriterion("total_invest_270 not between", value1, value2, "totalInvest270");
            return (Criteria) this;
        }

        public Criteria andTodayInvest365IsNull() {
            addCriterion("today_invest_365 is null");
            return (Criteria) this;
        }

        public Criteria andTodayInvest365IsNotNull() {
            addCriterion("today_invest_365 is not null");
            return (Criteria) this;
        }

        public Criteria andTodayInvest365EqualTo(Double value) {
            addCriterion("today_invest_365 =", value, "todayInvest365");
            return (Criteria) this;
        }

        public Criteria andTodayInvest365NotEqualTo(Double value) {
            addCriterion("today_invest_365 <>", value, "todayInvest365");
            return (Criteria) this;
        }

        public Criteria andTodayInvest365GreaterThan(Double value) {
            addCriterion("today_invest_365 >", value, "todayInvest365");
            return (Criteria) this;
        }

        public Criteria andTodayInvest365GreaterThanOrEqualTo(Double value) {
            addCriterion("today_invest_365 >=", value, "todayInvest365");
            return (Criteria) this;
        }

        public Criteria andTodayInvest365LessThan(Double value) {
            addCriterion("today_invest_365 <", value, "todayInvest365");
            return (Criteria) this;
        }

        public Criteria andTodayInvest365LessThanOrEqualTo(Double value) {
            addCriterion("today_invest_365 <=", value, "todayInvest365");
            return (Criteria) this;
        }

        public Criteria andTodayInvest365In(List<Double> values) {
            addCriterion("today_invest_365 in", values, "todayInvest365");
            return (Criteria) this;
        }

        public Criteria andTodayInvest365NotIn(List<Double> values) {
            addCriterion("today_invest_365 not in", values, "todayInvest365");
            return (Criteria) this;
        }

        public Criteria andTodayInvest365Between(Double value1, Double value2) {
            addCriterion("today_invest_365 between", value1, value2, "todayInvest365");
            return (Criteria) this;
        }

        public Criteria andTodayInvest365NotBetween(Double value1, Double value2) {
            addCriterion("today_invest_365 not between", value1, value2, "todayInvest365");
            return (Criteria) this;
        }

        public Criteria andTotalInvest365IsNull() {
            addCriterion("total_invest_365 is null");
            return (Criteria) this;
        }

        public Criteria andTotalInvest365IsNotNull() {
            addCriterion("total_invest_365 is not null");
            return (Criteria) this;
        }

        public Criteria andTotalInvest365EqualTo(Double value) {
            addCriterion("total_invest_365 =", value, "totalInvest365");
            return (Criteria) this;
        }

        public Criteria andTotalInvest365NotEqualTo(Double value) {
            addCriterion("total_invest_365 <>", value, "totalInvest365");
            return (Criteria) this;
        }

        public Criteria andTotalInvest365GreaterThan(Double value) {
            addCriterion("total_invest_365 >", value, "totalInvest365");
            return (Criteria) this;
        }

        public Criteria andTotalInvest365GreaterThanOrEqualTo(Double value) {
            addCriterion("total_invest_365 >=", value, "totalInvest365");
            return (Criteria) this;
        }

        public Criteria andTotalInvest365LessThan(Double value) {
            addCriterion("total_invest_365 <", value, "totalInvest365");
            return (Criteria) this;
        }

        public Criteria andTotalInvest365LessThanOrEqualTo(Double value) {
            addCriterion("total_invest_365 <=", value, "totalInvest365");
            return (Criteria) this;
        }

        public Criteria andTotalInvest365In(List<Double> values) {
            addCriterion("total_invest_365 in", values, "totalInvest365");
            return (Criteria) this;
        }

        public Criteria andTotalInvest365NotIn(List<Double> values) {
            addCriterion("total_invest_365 not in", values, "totalInvest365");
            return (Criteria) this;
        }

        public Criteria andTotalInvest365Between(Double value1, Double value2) {
            addCriterion("total_invest_365 between", value1, value2, "totalInvest365");
            return (Criteria) this;
        }

        public Criteria andTotalInvest365NotBetween(Double value1, Double value2) {
            addCriterion("total_invest_365 not between", value1, value2, "totalInvest365");
            return (Criteria) this;
        }

        public Criteria andDateIsNull() {
            addCriterion("date is null");
            return (Criteria) this;
        }

        public Criteria andDateIsNotNull() {
            addCriterion("date is not null");
            return (Criteria) this;
        }

        public Criteria andDateEqualTo(Date value) {
            addCriterion("date =", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotEqualTo(Date value) {
            addCriterion("date <>", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThan(Date value) {
            addCriterion("date >", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThanOrEqualTo(Date value) {
            addCriterion("date >=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThan(Date value) {
            addCriterion("date <", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThanOrEqualTo(Date value) {
            addCriterion("date <=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateIn(List<Date> values) {
            addCriterion("date in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotIn(List<Date> values) {
            addCriterion("date not in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateBetween(Date value1, Date value2) {
            addCriterion("date between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotBetween(Date value1, Date value2) {
            addCriterion("date not between", value1, value2, "date");
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