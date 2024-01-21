package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsEntrustLoanViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsEntrustLoanViewExample() {
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

        public Criteria andPropertySymbolIsNull() {
            addCriterion("property_symbol is null");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolIsNotNull() {
            addCriterion("property_symbol is not null");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolEqualTo(String value) {
            addCriterion("property_symbol =", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolNotEqualTo(String value) {
            addCriterion("property_symbol <>", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolGreaterThan(String value) {
            addCriterion("property_symbol >", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolGreaterThanOrEqualTo(String value) {
            addCriterion("property_symbol >=", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolLessThan(String value) {
            addCriterion("property_symbol <", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolLessThanOrEqualTo(String value) {
            addCriterion("property_symbol <=", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolLike(String value) {
            addCriterion("property_symbol like", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolNotLike(String value) {
            addCriterion("property_symbol not like", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolIn(List<String> values) {
            addCriterion("property_symbol in", values, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolNotIn(List<String> values) {
            addCriterion("property_symbol not in", values, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolBetween(String value1, String value2) {
            addCriterion("property_symbol between", value1, value2, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolNotBetween(String value1, String value2) {
            addCriterion("property_symbol not between", value1, value2, "propertySymbol");
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

        public Criteria andTodayLoan7IsNull() {
            addCriterion("today_loan_7 is null");
            return (Criteria) this;
        }

        public Criteria andTodayLoan7IsNotNull() {
            addCriterion("today_loan_7 is not null");
            return (Criteria) this;
        }

        public Criteria andTodayLoan7EqualTo(Double value) {
            addCriterion("today_loan_7 =", value, "todayLoan7");
            return (Criteria) this;
        }

        public Criteria andTodayLoan7NotEqualTo(Double value) {
            addCriterion("today_loan_7 <>", value, "todayLoan7");
            return (Criteria) this;
        }

        public Criteria andTodayLoan7GreaterThan(Double value) {
            addCriterion("today_loan_7 >", value, "todayLoan7");
            return (Criteria) this;
        }

        public Criteria andTodayLoan7GreaterThanOrEqualTo(Double value) {
            addCriterion("today_loan_7 >=", value, "todayLoan7");
            return (Criteria) this;
        }

        public Criteria andTodayLoan7LessThan(Double value) {
            addCriterion("today_loan_7 <", value, "todayLoan7");
            return (Criteria) this;
        }

        public Criteria andTodayLoan7LessThanOrEqualTo(Double value) {
            addCriterion("today_loan_7 <=", value, "todayLoan7");
            return (Criteria) this;
        }

        public Criteria andTodayLoan7In(List<Double> values) {
            addCriterion("today_loan_7 in", values, "todayLoan7");
            return (Criteria) this;
        }

        public Criteria andTodayLoan7NotIn(List<Double> values) {
            addCriterion("today_loan_7 not in", values, "todayLoan7");
            return (Criteria) this;
        }

        public Criteria andTodayLoan7Between(Double value1, Double value2) {
            addCriterion("today_loan_7 between", value1, value2, "todayLoan7");
            return (Criteria) this;
        }

        public Criteria andTodayLoan7NotBetween(Double value1, Double value2) {
            addCriterion("today_loan_7 not between", value1, value2, "todayLoan7");
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

        public Criteria andTodayLoan30IsNull() {
            addCriterion("today_loan_30 is null");
            return (Criteria) this;
        }

        public Criteria andTodayLoan30IsNotNull() {
            addCriterion("today_loan_30 is not null");
            return (Criteria) this;
        }

        public Criteria andTodayLoan30EqualTo(Double value) {
            addCriterion("today_loan_30 =", value, "todayLoan30");
            return (Criteria) this;
        }

        public Criteria andTodayLoan30NotEqualTo(Double value) {
            addCriterion("today_loan_30 <>", value, "todayLoan30");
            return (Criteria) this;
        }

        public Criteria andTodayLoan30GreaterThan(Double value) {
            addCriterion("today_loan_30 >", value, "todayLoan30");
            return (Criteria) this;
        }

        public Criteria andTodayLoan30GreaterThanOrEqualTo(Double value) {
            addCriterion("today_loan_30 >=", value, "todayLoan30");
            return (Criteria) this;
        }

        public Criteria andTodayLoan30LessThan(Double value) {
            addCriterion("today_loan_30 <", value, "todayLoan30");
            return (Criteria) this;
        }

        public Criteria andTodayLoan30LessThanOrEqualTo(Double value) {
            addCriterion("today_loan_30 <=", value, "todayLoan30");
            return (Criteria) this;
        }

        public Criteria andTodayLoan30In(List<Double> values) {
            addCriterion("today_loan_30 in", values, "todayLoan30");
            return (Criteria) this;
        }

        public Criteria andTodayLoan30NotIn(List<Double> values) {
            addCriterion("today_loan_30 not in", values, "todayLoan30");
            return (Criteria) this;
        }

        public Criteria andTodayLoan30Between(Double value1, Double value2) {
            addCriterion("today_loan_30 between", value1, value2, "todayLoan30");
            return (Criteria) this;
        }

        public Criteria andTodayLoan30NotBetween(Double value1, Double value2) {
            addCriterion("today_loan_30 not between", value1, value2, "todayLoan30");
            return (Criteria) this;
        }

        public Criteria andTodayInvest60IsNull() {
            addCriterion("today_invest_60 is null");
            return (Criteria) this;
        }

        public Criteria andTodayInvest60IsNotNull() {
            addCriterion("today_invest_60 is not null");
            return (Criteria) this;
        }

        public Criteria andTodayInvest60EqualTo(Double value) {
            addCriterion("today_invest_60 =", value, "todayInvest60");
            return (Criteria) this;
        }

        public Criteria andTodayInvest60NotEqualTo(Double value) {
            addCriterion("today_invest_60 <>", value, "todayInvest60");
            return (Criteria) this;
        }

        public Criteria andTodayInvest60GreaterThan(Double value) {
            addCriterion("today_invest_60 >", value, "todayInvest60");
            return (Criteria) this;
        }

        public Criteria andTodayInvest60GreaterThanOrEqualTo(Double value) {
            addCriterion("today_invest_60 >=", value, "todayInvest60");
            return (Criteria) this;
        }

        public Criteria andTodayInvest60LessThan(Double value) {
            addCriterion("today_invest_60 <", value, "todayInvest60");
            return (Criteria) this;
        }

        public Criteria andTodayInvest60LessThanOrEqualTo(Double value) {
            addCriterion("today_invest_60 <=", value, "todayInvest60");
            return (Criteria) this;
        }

        public Criteria andTodayInvest60In(List<Double> values) {
            addCriterion("today_invest_60 in", values, "todayInvest60");
            return (Criteria) this;
        }

        public Criteria andTodayInvest60NotIn(List<Double> values) {
            addCriterion("today_invest_60 not in", values, "todayInvest60");
            return (Criteria) this;
        }

        public Criteria andTodayInvest60Between(Double value1, Double value2) {
            addCriterion("today_invest_60 between", value1, value2, "todayInvest60");
            return (Criteria) this;
        }

        public Criteria andTodayInvest60NotBetween(Double value1, Double value2) {
            addCriterion("today_invest_60 not between", value1, value2, "todayInvest60");
            return (Criteria) this;
        }

        public Criteria andTodayLoan60IsNull() {
            addCriterion("today_loan_60 is null");
            return (Criteria) this;
        }

        public Criteria andTodayLoan60IsNotNull() {
            addCriterion("today_loan_60 is not null");
            return (Criteria) this;
        }

        public Criteria andTodayLoan60EqualTo(Double value) {
            addCriterion("today_loan_60 =", value, "todayLoan60");
            return (Criteria) this;
        }

        public Criteria andTodayLoan60NotEqualTo(Double value) {
            addCriterion("today_loan_60 <>", value, "todayLoan60");
            return (Criteria) this;
        }

        public Criteria andTodayLoan60GreaterThan(Double value) {
            addCriterion("today_loan_60 >", value, "todayLoan60");
            return (Criteria) this;
        }

        public Criteria andTodayLoan60GreaterThanOrEqualTo(Double value) {
            addCriterion("today_loan_60 >=", value, "todayLoan60");
            return (Criteria) this;
        }

        public Criteria andTodayLoan60LessThan(Double value) {
            addCriterion("today_loan_60 <", value, "todayLoan60");
            return (Criteria) this;
        }

        public Criteria andTodayLoan60LessThanOrEqualTo(Double value) {
            addCriterion("today_loan_60 <=", value, "todayLoan60");
            return (Criteria) this;
        }

        public Criteria andTodayLoan60In(List<Double> values) {
            addCriterion("today_loan_60 in", values, "todayLoan60");
            return (Criteria) this;
        }

        public Criteria andTodayLoan60NotIn(List<Double> values) {
            addCriterion("today_loan_60 not in", values, "todayLoan60");
            return (Criteria) this;
        }

        public Criteria andTodayLoan60Between(Double value1, Double value2) {
            addCriterion("today_loan_60 between", value1, value2, "todayLoan60");
            return (Criteria) this;
        }

        public Criteria andTodayLoan60NotBetween(Double value1, Double value2) {
            addCriterion("today_loan_60 not between", value1, value2, "todayLoan60");
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

        public Criteria andTodayLoan90IsNull() {
            addCriterion("today_loan_90 is null");
            return (Criteria) this;
        }

        public Criteria andTodayLoan90IsNotNull() {
            addCriterion("today_loan_90 is not null");
            return (Criteria) this;
        }

        public Criteria andTodayLoan90EqualTo(Double value) {
            addCriterion("today_loan_90 =", value, "todayLoan90");
            return (Criteria) this;
        }

        public Criteria andTodayLoan90NotEqualTo(Double value) {
            addCriterion("today_loan_90 <>", value, "todayLoan90");
            return (Criteria) this;
        }

        public Criteria andTodayLoan90GreaterThan(Double value) {
            addCriterion("today_loan_90 >", value, "todayLoan90");
            return (Criteria) this;
        }

        public Criteria andTodayLoan90GreaterThanOrEqualTo(Double value) {
            addCriterion("today_loan_90 >=", value, "todayLoan90");
            return (Criteria) this;
        }

        public Criteria andTodayLoan90LessThan(Double value) {
            addCriterion("today_loan_90 <", value, "todayLoan90");
            return (Criteria) this;
        }

        public Criteria andTodayLoan90LessThanOrEqualTo(Double value) {
            addCriterion("today_loan_90 <=", value, "todayLoan90");
            return (Criteria) this;
        }

        public Criteria andTodayLoan90In(List<Double> values) {
            addCriterion("today_loan_90 in", values, "todayLoan90");
            return (Criteria) this;
        }

        public Criteria andTodayLoan90NotIn(List<Double> values) {
            addCriterion("today_loan_90 not in", values, "todayLoan90");
            return (Criteria) this;
        }

        public Criteria andTodayLoan90Between(Double value1, Double value2) {
            addCriterion("today_loan_90 between", value1, value2, "todayLoan90");
            return (Criteria) this;
        }

        public Criteria andTodayLoan90NotBetween(Double value1, Double value2) {
            addCriterion("today_loan_90 not between", value1, value2, "todayLoan90");
            return (Criteria) this;
        }

        public Criteria andTodayInvest120IsNull() {
            addCriterion("today_invest_120 is null");
            return (Criteria) this;
        }

        public Criteria andTodayInvest120IsNotNull() {
            addCriterion("today_invest_120 is not null");
            return (Criteria) this;
        }

        public Criteria andTodayInvest120EqualTo(Double value) {
            addCriterion("today_invest_120 =", value, "todayInvest120");
            return (Criteria) this;
        }

        public Criteria andTodayInvest120NotEqualTo(Double value) {
            addCriterion("today_invest_120 <>", value, "todayInvest120");
            return (Criteria) this;
        }

        public Criteria andTodayInvest120GreaterThan(Double value) {
            addCriterion("today_invest_120 >", value, "todayInvest120");
            return (Criteria) this;
        }

        public Criteria andTodayInvest120GreaterThanOrEqualTo(Double value) {
            addCriterion("today_invest_120 >=", value, "todayInvest120");
            return (Criteria) this;
        }

        public Criteria andTodayInvest120LessThan(Double value) {
            addCriterion("today_invest_120 <", value, "todayInvest120");
            return (Criteria) this;
        }

        public Criteria andTodayInvest120LessThanOrEqualTo(Double value) {
            addCriterion("today_invest_120 <=", value, "todayInvest120");
            return (Criteria) this;
        }

        public Criteria andTodayInvest120In(List<Double> values) {
            addCriterion("today_invest_120 in", values, "todayInvest120");
            return (Criteria) this;
        }

        public Criteria andTodayInvest120NotIn(List<Double> values) {
            addCriterion("today_invest_120 not in", values, "todayInvest120");
            return (Criteria) this;
        }

        public Criteria andTodayInvest120Between(Double value1, Double value2) {
            addCriterion("today_invest_120 between", value1, value2, "todayInvest120");
            return (Criteria) this;
        }

        public Criteria andTodayInvest120NotBetween(Double value1, Double value2) {
            addCriterion("today_invest_120 not between", value1, value2, "todayInvest120");
            return (Criteria) this;
        }

        public Criteria andTodayLoan120IsNull() {
            addCriterion("today_loan_120 is null");
            return (Criteria) this;
        }

        public Criteria andTodayLoan120IsNotNull() {
            addCriterion("today_loan_120 is not null");
            return (Criteria) this;
        }

        public Criteria andTodayLoan120EqualTo(Double value) {
            addCriterion("today_loan_120 =", value, "todayLoan120");
            return (Criteria) this;
        }

        public Criteria andTodayLoan120NotEqualTo(Double value) {
            addCriterion("today_loan_120 <>", value, "todayLoan120");
            return (Criteria) this;
        }

        public Criteria andTodayLoan120GreaterThan(Double value) {
            addCriterion("today_loan_120 >", value, "todayLoan120");
            return (Criteria) this;
        }

        public Criteria andTodayLoan120GreaterThanOrEqualTo(Double value) {
            addCriterion("today_loan_120 >=", value, "todayLoan120");
            return (Criteria) this;
        }

        public Criteria andTodayLoan120LessThan(Double value) {
            addCriterion("today_loan_120 <", value, "todayLoan120");
            return (Criteria) this;
        }

        public Criteria andTodayLoan120LessThanOrEqualTo(Double value) {
            addCriterion("today_loan_120 <=", value, "todayLoan120");
            return (Criteria) this;
        }

        public Criteria andTodayLoan120In(List<Double> values) {
            addCriterion("today_loan_120 in", values, "todayLoan120");
            return (Criteria) this;
        }

        public Criteria andTodayLoan120NotIn(List<Double> values) {
            addCriterion("today_loan_120 not in", values, "todayLoan120");
            return (Criteria) this;
        }

        public Criteria andTodayLoan120Between(Double value1, Double value2) {
            addCriterion("today_loan_120 between", value1, value2, "todayLoan120");
            return (Criteria) this;
        }

        public Criteria andTodayLoan120NotBetween(Double value1, Double value2) {
            addCriterion("today_loan_120 not between", value1, value2, "todayLoan120");
            return (Criteria) this;
        }

        public Criteria andTodayInvest150IsNull() {
            addCriterion("today_invest_150 is null");
            return (Criteria) this;
        }

        public Criteria andTodayInvest150IsNotNull() {
            addCriterion("today_invest_150 is not null");
            return (Criteria) this;
        }

        public Criteria andTodayInvest150EqualTo(Double value) {
            addCriterion("today_invest_150 =", value, "todayInvest150");
            return (Criteria) this;
        }

        public Criteria andTodayInvest150NotEqualTo(Double value) {
            addCriterion("today_invest_150 <>", value, "todayInvest150");
            return (Criteria) this;
        }

        public Criteria andTodayInvest150GreaterThan(Double value) {
            addCriterion("today_invest_150 >", value, "todayInvest150");
            return (Criteria) this;
        }

        public Criteria andTodayInvest150GreaterThanOrEqualTo(Double value) {
            addCriterion("today_invest_150 >=", value, "todayInvest150");
            return (Criteria) this;
        }

        public Criteria andTodayInvest150LessThan(Double value) {
            addCriterion("today_invest_150 <", value, "todayInvest150");
            return (Criteria) this;
        }

        public Criteria andTodayInvest150LessThanOrEqualTo(Double value) {
            addCriterion("today_invest_150 <=", value, "todayInvest150");
            return (Criteria) this;
        }

        public Criteria andTodayInvest150In(List<Double> values) {
            addCriterion("today_invest_150 in", values, "todayInvest150");
            return (Criteria) this;
        }

        public Criteria andTodayInvest150NotIn(List<Double> values) {
            addCriterion("today_invest_150 not in", values, "todayInvest150");
            return (Criteria) this;
        }

        public Criteria andTodayInvest150Between(Double value1, Double value2) {
            addCriterion("today_invest_150 between", value1, value2, "todayInvest150");
            return (Criteria) this;
        }

        public Criteria andTodayInvest150NotBetween(Double value1, Double value2) {
            addCriterion("today_invest_150 not between", value1, value2, "todayInvest150");
            return (Criteria) this;
        }

        public Criteria andTodayLoan150IsNull() {
            addCriterion("today_loan_150 is null");
            return (Criteria) this;
        }

        public Criteria andTodayLoan150IsNotNull() {
            addCriterion("today_loan_150 is not null");
            return (Criteria) this;
        }

        public Criteria andTodayLoan150EqualTo(Double value) {
            addCriterion("today_loan_150 =", value, "todayLoan150");
            return (Criteria) this;
        }

        public Criteria andTodayLoan150NotEqualTo(Double value) {
            addCriterion("today_loan_150 <>", value, "todayLoan150");
            return (Criteria) this;
        }

        public Criteria andTodayLoan150GreaterThan(Double value) {
            addCriterion("today_loan_150 >", value, "todayLoan150");
            return (Criteria) this;
        }

        public Criteria andTodayLoan150GreaterThanOrEqualTo(Double value) {
            addCriterion("today_loan_150 >=", value, "todayLoan150");
            return (Criteria) this;
        }

        public Criteria andTodayLoan150LessThan(Double value) {
            addCriterion("today_loan_150 <", value, "todayLoan150");
            return (Criteria) this;
        }

        public Criteria andTodayLoan150LessThanOrEqualTo(Double value) {
            addCriterion("today_loan_150 <=", value, "todayLoan150");
            return (Criteria) this;
        }

        public Criteria andTodayLoan150In(List<Double> values) {
            addCriterion("today_loan_150 in", values, "todayLoan150");
            return (Criteria) this;
        }

        public Criteria andTodayLoan150NotIn(List<Double> values) {
            addCriterion("today_loan_150 not in", values, "todayLoan150");
            return (Criteria) this;
        }

        public Criteria andTodayLoan150Between(Double value1, Double value2) {
            addCriterion("today_loan_150 between", value1, value2, "todayLoan150");
            return (Criteria) this;
        }

        public Criteria andTodayLoan150NotBetween(Double value1, Double value2) {
            addCriterion("today_loan_150 not between", value1, value2, "todayLoan150");
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

        public Criteria andTodayLoan180IsNull() {
            addCriterion("today_loan_180 is null");
            return (Criteria) this;
        }

        public Criteria andTodayLoan180IsNotNull() {
            addCriterion("today_loan_180 is not null");
            return (Criteria) this;
        }

        public Criteria andTodayLoan180EqualTo(Double value) {
            addCriterion("today_loan_180 =", value, "todayLoan180");
            return (Criteria) this;
        }

        public Criteria andTodayLoan180NotEqualTo(Double value) {
            addCriterion("today_loan_180 <>", value, "todayLoan180");
            return (Criteria) this;
        }

        public Criteria andTodayLoan180GreaterThan(Double value) {
            addCriterion("today_loan_180 >", value, "todayLoan180");
            return (Criteria) this;
        }

        public Criteria andTodayLoan180GreaterThanOrEqualTo(Double value) {
            addCriterion("today_loan_180 >=", value, "todayLoan180");
            return (Criteria) this;
        }

        public Criteria andTodayLoan180LessThan(Double value) {
            addCriterion("today_loan_180 <", value, "todayLoan180");
            return (Criteria) this;
        }

        public Criteria andTodayLoan180LessThanOrEqualTo(Double value) {
            addCriterion("today_loan_180 <=", value, "todayLoan180");
            return (Criteria) this;
        }

        public Criteria andTodayLoan180In(List<Double> values) {
            addCriterion("today_loan_180 in", values, "todayLoan180");
            return (Criteria) this;
        }

        public Criteria andTodayLoan180NotIn(List<Double> values) {
            addCriterion("today_loan_180 not in", values, "todayLoan180");
            return (Criteria) this;
        }

        public Criteria andTodayLoan180Between(Double value1, Double value2) {
            addCriterion("today_loan_180 between", value1, value2, "todayLoan180");
            return (Criteria) this;
        }

        public Criteria andTodayLoan180NotBetween(Double value1, Double value2) {
            addCriterion("today_loan_180 not between", value1, value2, "todayLoan180");
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

        public Criteria andTodayLoan270IsNull() {
            addCriterion("today_loan_270 is null");
            return (Criteria) this;
        }

        public Criteria andTodayLoan270IsNotNull() {
            addCriterion("today_loan_270 is not null");
            return (Criteria) this;
        }

        public Criteria andTodayLoan270EqualTo(Double value) {
            addCriterion("today_loan_270 =", value, "todayLoan270");
            return (Criteria) this;
        }

        public Criteria andTodayLoan270NotEqualTo(Double value) {
            addCriterion("today_loan_270 <>", value, "todayLoan270");
            return (Criteria) this;
        }

        public Criteria andTodayLoan270GreaterThan(Double value) {
            addCriterion("today_loan_270 >", value, "todayLoan270");
            return (Criteria) this;
        }

        public Criteria andTodayLoan270GreaterThanOrEqualTo(Double value) {
            addCriterion("today_loan_270 >=", value, "todayLoan270");
            return (Criteria) this;
        }

        public Criteria andTodayLoan270LessThan(Double value) {
            addCriterion("today_loan_270 <", value, "todayLoan270");
            return (Criteria) this;
        }

        public Criteria andTodayLoan270LessThanOrEqualTo(Double value) {
            addCriterion("today_loan_270 <=", value, "todayLoan270");
            return (Criteria) this;
        }

        public Criteria andTodayLoan270In(List<Double> values) {
            addCriterion("today_loan_270 in", values, "todayLoan270");
            return (Criteria) this;
        }

        public Criteria andTodayLoan270NotIn(List<Double> values) {
            addCriterion("today_loan_270 not in", values, "todayLoan270");
            return (Criteria) this;
        }

        public Criteria andTodayLoan270Between(Double value1, Double value2) {
            addCriterion("today_loan_270 between", value1, value2, "todayLoan270");
            return (Criteria) this;
        }

        public Criteria andTodayLoan270NotBetween(Double value1, Double value2) {
            addCriterion("today_loan_270 not between", value1, value2, "todayLoan270");
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

        public Criteria andTodayLoan365IsNull() {
            addCriterion("today_loan_365 is null");
            return (Criteria) this;
        }

        public Criteria andTodayLoan365IsNotNull() {
            addCriterion("today_loan_365 is not null");
            return (Criteria) this;
        }

        public Criteria andTodayLoan365EqualTo(Double value) {
            addCriterion("today_loan_365 =", value, "todayLoan365");
            return (Criteria) this;
        }

        public Criteria andTodayLoan365NotEqualTo(Double value) {
            addCriterion("today_loan_365 <>", value, "todayLoan365");
            return (Criteria) this;
        }

        public Criteria andTodayLoan365GreaterThan(Double value) {
            addCriterion("today_loan_365 >", value, "todayLoan365");
            return (Criteria) this;
        }

        public Criteria andTodayLoan365GreaterThanOrEqualTo(Double value) {
            addCriterion("today_loan_365 >=", value, "todayLoan365");
            return (Criteria) this;
        }

        public Criteria andTodayLoan365LessThan(Double value) {
            addCriterion("today_loan_365 <", value, "todayLoan365");
            return (Criteria) this;
        }

        public Criteria andTodayLoan365LessThanOrEqualTo(Double value) {
            addCriterion("today_loan_365 <=", value, "todayLoan365");
            return (Criteria) this;
        }

        public Criteria andTodayLoan365In(List<Double> values) {
            addCriterion("today_loan_365 in", values, "todayLoan365");
            return (Criteria) this;
        }

        public Criteria andTodayLoan365NotIn(List<Double> values) {
            addCriterion("today_loan_365 not in", values, "todayLoan365");
            return (Criteria) this;
        }

        public Criteria andTodayLoan365Between(Double value1, Double value2) {
            addCriterion("today_loan_365 between", value1, value2, "todayLoan365");
            return (Criteria) this;
        }

        public Criteria andTodayLoan365NotBetween(Double value1, Double value2) {
            addCriterion("today_loan_365 not between", value1, value2, "todayLoan365");
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