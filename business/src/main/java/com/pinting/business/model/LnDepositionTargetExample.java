package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class LnDepositionTargetExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LnDepositionTargetExample() {
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

        public Criteria andProdNameIsNull() {
            addCriterion("prod_name is null");
            return (Criteria) this;
        }

        public Criteria andProdNameIsNotNull() {
            addCriterion("prod_name is not null");
            return (Criteria) this;
        }

        public Criteria andProdNameEqualTo(String value) {
            addCriterion("prod_name =", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameNotEqualTo(String value) {
            addCriterion("prod_name <>", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameGreaterThan(String value) {
            addCriterion("prod_name >", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameGreaterThanOrEqualTo(String value) {
            addCriterion("prod_name >=", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameLessThan(String value) {
            addCriterion("prod_name <", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameLessThanOrEqualTo(String value) {
            addCriterion("prod_name <=", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameLike(String value) {
            addCriterion("prod_name like", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameNotLike(String value) {
            addCriterion("prod_name not like", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameIn(List<String> values) {
            addCriterion("prod_name in", values, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameNotIn(List<String> values) {
            addCriterion("prod_name not in", values, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameBetween(String value1, String value2) {
            addCriterion("prod_name between", value1, value2, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameNotBetween(String value1, String value2) {
            addCriterion("prod_name not between", value1, value2, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdTypeIsNull() {
            addCriterion("prod_type is null");
            return (Criteria) this;
        }

        public Criteria andProdTypeIsNotNull() {
            addCriterion("prod_type is not null");
            return (Criteria) this;
        }

        public Criteria andProdTypeEqualTo(String value) {
            addCriterion("prod_type =", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeNotEqualTo(String value) {
            addCriterion("prod_type <>", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeGreaterThan(String value) {
            addCriterion("prod_type >", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeGreaterThanOrEqualTo(String value) {
            addCriterion("prod_type >=", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeLessThan(String value) {
            addCriterion("prod_type <", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeLessThanOrEqualTo(String value) {
            addCriterion("prod_type <=", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeLike(String value) {
            addCriterion("prod_type like", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeNotLike(String value) {
            addCriterion("prod_type not like", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeIn(List<String> values) {
            addCriterion("prod_type in", values, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeNotIn(List<String> values) {
            addCriterion("prod_type not in", values, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeBetween(String value1, String value2) {
            addCriterion("prod_type between", value1, value2, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeNotBetween(String value1, String value2) {
            addCriterion("prod_type not between", value1, value2, "prodType");
            return (Criteria) this;
        }

        public Criteria andTotalLimitIsNull() {
            addCriterion("total_limit is null");
            return (Criteria) this;
        }

        public Criteria andTotalLimitIsNotNull() {
            addCriterion("total_limit is not null");
            return (Criteria) this;
        }

        public Criteria andTotalLimitEqualTo(Double value) {
            addCriterion("total_limit =", value, "totalLimit");
            return (Criteria) this;
        }

        public Criteria andTotalLimitNotEqualTo(Double value) {
            addCriterion("total_limit <>", value, "totalLimit");
            return (Criteria) this;
        }

        public Criteria andTotalLimitGreaterThan(Double value) {
            addCriterion("total_limit >", value, "totalLimit");
            return (Criteria) this;
        }

        public Criteria andTotalLimitGreaterThanOrEqualTo(Double value) {
            addCriterion("total_limit >=", value, "totalLimit");
            return (Criteria) this;
        }

        public Criteria andTotalLimitLessThan(Double value) {
            addCriterion("total_limit <", value, "totalLimit");
            return (Criteria) this;
        }

        public Criteria andTotalLimitLessThanOrEqualTo(Double value) {
            addCriterion("total_limit <=", value, "totalLimit");
            return (Criteria) this;
        }

        public Criteria andTotalLimitIn(List<Double> values) {
            addCriterion("total_limit in", values, "totalLimit");
            return (Criteria) this;
        }

        public Criteria andTotalLimitNotIn(List<Double> values) {
            addCriterion("total_limit not in", values, "totalLimit");
            return (Criteria) this;
        }

        public Criteria andTotalLimitBetween(Double value1, Double value2) {
            addCriterion("total_limit between", value1, value2, "totalLimit");
            return (Criteria) this;
        }

        public Criteria andTotalLimitNotBetween(Double value1, Double value2) {
            addCriterion("total_limit not between", value1, value2, "totalLimit");
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

        public Criteria andInterestTypeEqualTo(String value) {
            addCriterion("interest_type =", value, "interestType");
            return (Criteria) this;
        }

        public Criteria andInterestTypeNotEqualTo(String value) {
            addCriterion("interest_type <>", value, "interestType");
            return (Criteria) this;
        }

        public Criteria andInterestTypeGreaterThan(String value) {
            addCriterion("interest_type >", value, "interestType");
            return (Criteria) this;
        }

        public Criteria andInterestTypeGreaterThanOrEqualTo(String value) {
            addCriterion("interest_type >=", value, "interestType");
            return (Criteria) this;
        }

        public Criteria andInterestTypeLessThan(String value) {
            addCriterion("interest_type <", value, "interestType");
            return (Criteria) this;
        }

        public Criteria andInterestTypeLessThanOrEqualTo(String value) {
            addCriterion("interest_type <=", value, "interestType");
            return (Criteria) this;
        }

        public Criteria andInterestTypeLike(String value) {
            addCriterion("interest_type like", value, "interestType");
            return (Criteria) this;
        }

        public Criteria andInterestTypeNotLike(String value) {
            addCriterion("interest_type not like", value, "interestType");
            return (Criteria) this;
        }

        public Criteria andInterestTypeIn(List<String> values) {
            addCriterion("interest_type in", values, "interestType");
            return (Criteria) this;
        }

        public Criteria andInterestTypeNotIn(List<String> values) {
            addCriterion("interest_type not in", values, "interestType");
            return (Criteria) this;
        }

        public Criteria andInterestTypeBetween(String value1, String value2) {
            addCriterion("interest_type between", value1, value2, "interestType");
            return (Criteria) this;
        }

        public Criteria andInterestTypeNotBetween(String value1, String value2) {
            addCriterion("interest_type not between", value1, value2, "interestType");
            return (Criteria) this;
        }

        public Criteria andSetupTypeIsNull() {
            addCriterion("setup_type is null");
            return (Criteria) this;
        }

        public Criteria andSetupTypeIsNotNull() {
            addCriterion("setup_type is not null");
            return (Criteria) this;
        }

        public Criteria andSetupTypeEqualTo(String value) {
            addCriterion("setup_type =", value, "setupType");
            return (Criteria) this;
        }

        public Criteria andSetupTypeNotEqualTo(String value) {
            addCriterion("setup_type <>", value, "setupType");
            return (Criteria) this;
        }

        public Criteria andSetupTypeGreaterThan(String value) {
            addCriterion("setup_type >", value, "setupType");
            return (Criteria) this;
        }

        public Criteria andSetupTypeGreaterThanOrEqualTo(String value) {
            addCriterion("setup_type >=", value, "setupType");
            return (Criteria) this;
        }

        public Criteria andSetupTypeLessThan(String value) {
            addCriterion("setup_type <", value, "setupType");
            return (Criteria) this;
        }

        public Criteria andSetupTypeLessThanOrEqualTo(String value) {
            addCriterion("setup_type <=", value, "setupType");
            return (Criteria) this;
        }

        public Criteria andSetupTypeLike(String value) {
            addCriterion("setup_type like", value, "setupType");
            return (Criteria) this;
        }

        public Criteria andSetupTypeNotLike(String value) {
            addCriterion("setup_type not like", value, "setupType");
            return (Criteria) this;
        }

        public Criteria andSetupTypeIn(List<String> values) {
            addCriterion("setup_type in", values, "setupType");
            return (Criteria) this;
        }

        public Criteria andSetupTypeNotIn(List<String> values) {
            addCriterion("setup_type not in", values, "setupType");
            return (Criteria) this;
        }

        public Criteria andSetupTypeBetween(String value1, String value2) {
            addCriterion("setup_type between", value1, value2, "setupType");
            return (Criteria) this;
        }

        public Criteria andSetupTypeNotBetween(String value1, String value2) {
            addCriterion("setup_type not between", value1, value2, "setupType");
            return (Criteria) this;
        }

        public Criteria andSellDateIsNull() {
            addCriterion("sell_date is null");
            return (Criteria) this;
        }

        public Criteria andSellDateIsNotNull() {
            addCriterion("sell_date is not null");
            return (Criteria) this;
        }

        public Criteria andSellDateEqualTo(Date value) {
            addCriterionForJDBCDate("sell_date =", value, "sellDate");
            return (Criteria) this;
        }

        public Criteria andSellDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("sell_date <>", value, "sellDate");
            return (Criteria) this;
        }

        public Criteria andSellDateGreaterThan(Date value) {
            addCriterionForJDBCDate("sell_date >", value, "sellDate");
            return (Criteria) this;
        }

        public Criteria andSellDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("sell_date >=", value, "sellDate");
            return (Criteria) this;
        }

        public Criteria andSellDateLessThan(Date value) {
            addCriterionForJDBCDate("sell_date <", value, "sellDate");
            return (Criteria) this;
        }

        public Criteria andSellDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("sell_date <=", value, "sellDate");
            return (Criteria) this;
        }

        public Criteria andSellDateIn(List<Date> values) {
            addCriterionForJDBCDate("sell_date in", values, "sellDate");
            return (Criteria) this;
        }

        public Criteria andSellDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("sell_date not in", values, "sellDate");
            return (Criteria) this;
        }

        public Criteria andSellDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("sell_date between", value1, value2, "sellDate");
            return (Criteria) this;
        }

        public Criteria andSellDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("sell_date not between", value1, value2, "sellDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateIsNull() {
            addCriterion("begin_date is null");
            return (Criteria) this;
        }

        public Criteria andBeginDateIsNotNull() {
            addCriterion("begin_date is not null");
            return (Criteria) this;
        }

        public Criteria andBeginDateEqualTo(Date value) {
            addCriterionForJDBCDate("begin_date =", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("begin_date <>", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateGreaterThan(Date value) {
            addCriterionForJDBCDate("begin_date >", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("begin_date >=", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateLessThan(Date value) {
            addCriterionForJDBCDate("begin_date <", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("begin_date <=", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateIn(List<Date> values) {
            addCriterionForJDBCDate("begin_date in", values, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("begin_date not in", values, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("begin_date between", value1, value2, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("begin_date not between", value1, value2, "beginDate");
            return (Criteria) this;
        }

        public Criteria andExpireDateIsNull() {
            addCriterion("expire_date is null");
            return (Criteria) this;
        }

        public Criteria andExpireDateIsNotNull() {
            addCriterion("expire_date is not null");
            return (Criteria) this;
        }

        public Criteria andExpireDateEqualTo(Date value) {
            addCriterionForJDBCDate("expire_date =", value, "expireDate");
            return (Criteria) this;
        }

        public Criteria andExpireDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("expire_date <>", value, "expireDate");
            return (Criteria) this;
        }

        public Criteria andExpireDateGreaterThan(Date value) {
            addCriterionForJDBCDate("expire_date >", value, "expireDate");
            return (Criteria) this;
        }

        public Criteria andExpireDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("expire_date >=", value, "expireDate");
            return (Criteria) this;
        }

        public Criteria andExpireDateLessThan(Date value) {
            addCriterionForJDBCDate("expire_date <", value, "expireDate");
            return (Criteria) this;
        }

        public Criteria andExpireDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("expire_date <=", value, "expireDate");
            return (Criteria) this;
        }

        public Criteria andExpireDateIn(List<Date> values) {
            addCriterionForJDBCDate("expire_date in", values, "expireDate");
            return (Criteria) this;
        }

        public Criteria andExpireDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("expire_date not in", values, "expireDate");
            return (Criteria) this;
        }

        public Criteria andExpireDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("expire_date between", value1, value2, "expireDate");
            return (Criteria) this;
        }

        public Criteria andExpireDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("expire_date not between", value1, value2, "expireDate");
            return (Criteria) this;
        }

        public Criteria andCycleIsNull() {
            addCriterion("cycle is null");
            return (Criteria) this;
        }

        public Criteria andCycleIsNotNull() {
            addCriterion("cycle is not null");
            return (Criteria) this;
        }

        public Criteria andCycleEqualTo(Integer value) {
            addCriterion("cycle =", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleNotEqualTo(Integer value) {
            addCriterion("cycle <>", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleGreaterThan(Integer value) {
            addCriterion("cycle >", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleGreaterThanOrEqualTo(Integer value) {
            addCriterion("cycle >=", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleLessThan(Integer value) {
            addCriterion("cycle <", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleLessThanOrEqualTo(Integer value) {
            addCriterion("cycle <=", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleIn(List<Integer> values) {
            addCriterion("cycle in", values, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleNotIn(List<Integer> values) {
            addCriterion("cycle not in", values, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleBetween(Integer value1, Integer value2) {
            addCriterion("cycle between", value1, value2, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleNotBetween(Integer value1, Integer value2) {
            addCriterion("cycle not between", value1, value2, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleUnitIsNull() {
            addCriterion("cycle_unit is null");
            return (Criteria) this;
        }

        public Criteria andCycleUnitIsNotNull() {
            addCriterion("cycle_unit is not null");
            return (Criteria) this;
        }

        public Criteria andCycleUnitEqualTo(String value) {
            addCriterion("cycle_unit =", value, "cycleUnit");
            return (Criteria) this;
        }

        public Criteria andCycleUnitNotEqualTo(String value) {
            addCriterion("cycle_unit <>", value, "cycleUnit");
            return (Criteria) this;
        }

        public Criteria andCycleUnitGreaterThan(String value) {
            addCriterion("cycle_unit >", value, "cycleUnit");
            return (Criteria) this;
        }

        public Criteria andCycleUnitGreaterThanOrEqualTo(String value) {
            addCriterion("cycle_unit >=", value, "cycleUnit");
            return (Criteria) this;
        }

        public Criteria andCycleUnitLessThan(String value) {
            addCriterion("cycle_unit <", value, "cycleUnit");
            return (Criteria) this;
        }

        public Criteria andCycleUnitLessThanOrEqualTo(String value) {
            addCriterion("cycle_unit <=", value, "cycleUnit");
            return (Criteria) this;
        }

        public Criteria andCycleUnitLike(String value) {
            addCriterion("cycle_unit like", value, "cycleUnit");
            return (Criteria) this;
        }

        public Criteria andCycleUnitNotLike(String value) {
            addCriterion("cycle_unit not like", value, "cycleUnit");
            return (Criteria) this;
        }

        public Criteria andCycleUnitIn(List<String> values) {
            addCriterion("cycle_unit in", values, "cycleUnit");
            return (Criteria) this;
        }

        public Criteria andCycleUnitNotIn(List<String> values) {
            addCriterion("cycle_unit not in", values, "cycleUnit");
            return (Criteria) this;
        }

        public Criteria andCycleUnitBetween(String value1, String value2) {
            addCriterion("cycle_unit between", value1, value2, "cycleUnit");
            return (Criteria) this;
        }

        public Criteria andCycleUnitNotBetween(String value1, String value2) {
            addCriterion("cycle_unit not between", value1, value2, "cycleUnit");
            return (Criteria) this;
        }

        public Criteria andIstYearIsNull() {
            addCriterion("ist_year is null");
            return (Criteria) this;
        }

        public Criteria andIstYearIsNotNull() {
            addCriterion("ist_year is not null");
            return (Criteria) this;
        }

        public Criteria andIstYearEqualTo(Double value) {
            addCriterion("ist_year =", value, "istYear");
            return (Criteria) this;
        }

        public Criteria andIstYearNotEqualTo(Double value) {
            addCriterion("ist_year <>", value, "istYear");
            return (Criteria) this;
        }

        public Criteria andIstYearGreaterThan(Double value) {
            addCriterion("ist_year >", value, "istYear");
            return (Criteria) this;
        }

        public Criteria andIstYearGreaterThanOrEqualTo(Double value) {
            addCriterion("ist_year >=", value, "istYear");
            return (Criteria) this;
        }

        public Criteria andIstYearLessThan(Double value) {
            addCriterion("ist_year <", value, "istYear");
            return (Criteria) this;
        }

        public Criteria andIstYearLessThanOrEqualTo(Double value) {
            addCriterion("ist_year <=", value, "istYear");
            return (Criteria) this;
        }

        public Criteria andIstYearIn(List<Double> values) {
            addCriterion("ist_year in", values, "istYear");
            return (Criteria) this;
        }

        public Criteria andIstYearNotIn(List<Double> values) {
            addCriterion("ist_year not in", values, "istYear");
            return (Criteria) this;
        }

        public Criteria andIstYearBetween(Double value1, Double value2) {
            addCriterion("ist_year between", value1, value2, "istYear");
            return (Criteria) this;
        }

        public Criteria andIstYearNotBetween(Double value1, Double value2) {
            addCriterion("ist_year not between", value1, value2, "istYear");
            return (Criteria) this;
        }

        public Criteria andRepayTypeIsNull() {
            addCriterion("repay_type is null");
            return (Criteria) this;
        }

        public Criteria andRepayTypeIsNotNull() {
            addCriterion("repay_type is not null");
            return (Criteria) this;
        }

        public Criteria andRepayTypeEqualTo(String value) {
            addCriterion("repay_type =", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeNotEqualTo(String value) {
            addCriterion("repay_type <>", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeGreaterThan(String value) {
            addCriterion("repay_type >", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeGreaterThanOrEqualTo(String value) {
            addCriterion("repay_type >=", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeLessThan(String value) {
            addCriterion("repay_type <", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeLessThanOrEqualTo(String value) {
            addCriterion("repay_type <=", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeLike(String value) {
            addCriterion("repay_type like", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeNotLike(String value) {
            addCriterion("repay_type not like", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeIn(List<String> values) {
            addCriterion("repay_type in", values, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeNotIn(List<String> values) {
            addCriterion("repay_type not in", values, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeBetween(String value1, String value2) {
            addCriterion("repay_type between", value1, value2, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeNotBetween(String value1, String value2) {
            addCriterion("repay_type not between", value1, value2, "repayType");
            return (Criteria) this;
        }

        public Criteria andLoanIdIsNull() {
            addCriterion("loan_id is null");
            return (Criteria) this;
        }

        public Criteria andLoanIdIsNotNull() {
            addCriterion("loan_id is not null");
            return (Criteria) this;
        }

        public Criteria andLoanIdEqualTo(Integer value) {
            addCriterion("loan_id =", value, "loanId");
            return (Criteria) this;
        }

        public Criteria andLoanIdNotEqualTo(Integer value) {
            addCriterion("loan_id <>", value, "loanId");
            return (Criteria) this;
        }

        public Criteria andLoanIdGreaterThan(Integer value) {
            addCriterion("loan_id >", value, "loanId");
            return (Criteria) this;
        }

        public Criteria andLoanIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("loan_id >=", value, "loanId");
            return (Criteria) this;
        }

        public Criteria andLoanIdLessThan(Integer value) {
            addCriterion("loan_id <", value, "loanId");
            return (Criteria) this;
        }

        public Criteria andLoanIdLessThanOrEqualTo(Integer value) {
            addCriterion("loan_id <=", value, "loanId");
            return (Criteria) this;
        }

        public Criteria andLoanIdIn(List<Integer> values) {
            addCriterion("loan_id in", values, "loanId");
            return (Criteria) this;
        }

        public Criteria andLoanIdNotIn(List<Integer> values) {
            addCriterion("loan_id not in", values, "loanId");
            return (Criteria) this;
        }

        public Criteria andLoanIdBetween(Integer value1, Integer value2) {
            addCriterion("loan_id between", value1, value2, "loanId");
            return (Criteria) this;
        }

        public Criteria andLoanIdNotBetween(Integer value1, Integer value2) {
            addCriterion("loan_id not between", value1, value2, "loanId");
            return (Criteria) this;
        }

        public Criteria andChargeOffAutoIsNull() {
            addCriterion("charge_off_auto is null");
            return (Criteria) this;
        }

        public Criteria andChargeOffAutoIsNotNull() {
            addCriterion("charge_off_auto is not null");
            return (Criteria) this;
        }

        public Criteria andChargeOffAutoEqualTo(String value) {
            addCriterion("charge_off_auto =", value, "chargeOffAuto");
            return (Criteria) this;
        }

        public Criteria andChargeOffAutoNotEqualTo(String value) {
            addCriterion("charge_off_auto <>", value, "chargeOffAuto");
            return (Criteria) this;
        }

        public Criteria andChargeOffAutoGreaterThan(String value) {
            addCriterion("charge_off_auto >", value, "chargeOffAuto");
            return (Criteria) this;
        }

        public Criteria andChargeOffAutoGreaterThanOrEqualTo(String value) {
            addCriterion("charge_off_auto >=", value, "chargeOffAuto");
            return (Criteria) this;
        }

        public Criteria andChargeOffAutoLessThan(String value) {
            addCriterion("charge_off_auto <", value, "chargeOffAuto");
            return (Criteria) this;
        }

        public Criteria andChargeOffAutoLessThanOrEqualTo(String value) {
            addCriterion("charge_off_auto <=", value, "chargeOffAuto");
            return (Criteria) this;
        }

        public Criteria andChargeOffAutoLike(String value) {
            addCriterion("charge_off_auto like", value, "chargeOffAuto");
            return (Criteria) this;
        }

        public Criteria andChargeOffAutoNotLike(String value) {
            addCriterion("charge_off_auto not like", value, "chargeOffAuto");
            return (Criteria) this;
        }

        public Criteria andChargeOffAutoIn(List<String> values) {
            addCriterion("charge_off_auto in", values, "chargeOffAuto");
            return (Criteria) this;
        }

        public Criteria andChargeOffAutoNotIn(List<String> values) {
            addCriterion("charge_off_auto not in", values, "chargeOffAuto");
            return (Criteria) this;
        }

        public Criteria andChargeOffAutoBetween(String value1, String value2) {
            addCriterion("charge_off_auto between", value1, value2, "chargeOffAuto");
            return (Criteria) this;
        }

        public Criteria andChargeOffAutoNotBetween(String value1, String value2) {
            addCriterion("charge_off_auto not between", value1, value2, "chargeOffAuto");
            return (Criteria) this;
        }

        public Criteria andOverLimitIsNull() {
            addCriterion("over_limit is null");
            return (Criteria) this;
        }

        public Criteria andOverLimitIsNotNull() {
            addCriterion("over_limit is not null");
            return (Criteria) this;
        }

        public Criteria andOverLimitEqualTo(String value) {
            addCriterion("over_limit =", value, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitNotEqualTo(String value) {
            addCriterion("over_limit <>", value, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitGreaterThan(String value) {
            addCriterion("over_limit >", value, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitGreaterThanOrEqualTo(String value) {
            addCriterion("over_limit >=", value, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitLessThan(String value) {
            addCriterion("over_limit <", value, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitLessThanOrEqualTo(String value) {
            addCriterion("over_limit <=", value, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitLike(String value) {
            addCriterion("over_limit like", value, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitNotLike(String value) {
            addCriterion("over_limit not like", value, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitIn(List<String> values) {
            addCriterion("over_limit in", values, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitNotIn(List<String> values) {
            addCriterion("over_limit not in", values, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitBetween(String value1, String value2) {
            addCriterion("over_limit between", value1, value2, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitNotBetween(String value1, String value2) {
            addCriterion("over_limit not between", value1, value2, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverTotalLimitIsNull() {
            addCriterion("over_total_limit is null");
            return (Criteria) this;
        }

        public Criteria andOverTotalLimitIsNotNull() {
            addCriterion("over_total_limit is not null");
            return (Criteria) this;
        }

        public Criteria andOverTotalLimitEqualTo(Double value) {
            addCriterion("over_total_limit =", value, "overTotalLimit");
            return (Criteria) this;
        }

        public Criteria andOverTotalLimitNotEqualTo(Double value) {
            addCriterion("over_total_limit <>", value, "overTotalLimit");
            return (Criteria) this;
        }

        public Criteria andOverTotalLimitGreaterThan(Double value) {
            addCriterion("over_total_limit >", value, "overTotalLimit");
            return (Criteria) this;
        }

        public Criteria andOverTotalLimitGreaterThanOrEqualTo(Double value) {
            addCriterion("over_total_limit >=", value, "overTotalLimit");
            return (Criteria) this;
        }

        public Criteria andOverTotalLimitLessThan(Double value) {
            addCriterion("over_total_limit <", value, "overTotalLimit");
            return (Criteria) this;
        }

        public Criteria andOverTotalLimitLessThanOrEqualTo(Double value) {
            addCriterion("over_total_limit <=", value, "overTotalLimit");
            return (Criteria) this;
        }

        public Criteria andOverTotalLimitIn(List<Double> values) {
            addCriterion("over_total_limit in", values, "overTotalLimit");
            return (Criteria) this;
        }

        public Criteria andOverTotalLimitNotIn(List<Double> values) {
            addCriterion("over_total_limit not in", values, "overTotalLimit");
            return (Criteria) this;
        }

        public Criteria andOverTotalLimitBetween(Double value1, Double value2) {
            addCriterion("over_total_limit between", value1, value2, "overTotalLimit");
            return (Criteria) this;
        }

        public Criteria andOverTotalLimitNotBetween(Double value1, Double value2) {
            addCriterion("over_total_limit not between", value1, value2, "overTotalLimit");
            return (Criteria) this;
        }

        public Criteria andRiskLvlIsNull() {
            addCriterion("risk_lvl is null");
            return (Criteria) this;
        }

        public Criteria andRiskLvlIsNotNull() {
            addCriterion("risk_lvl is not null");
            return (Criteria) this;
        }

        public Criteria andRiskLvlEqualTo(String value) {
            addCriterion("risk_lvl =", value, "riskLvl");
            return (Criteria) this;
        }

        public Criteria andRiskLvlNotEqualTo(String value) {
            addCriterion("risk_lvl <>", value, "riskLvl");
            return (Criteria) this;
        }

        public Criteria andRiskLvlGreaterThan(String value) {
            addCriterion("risk_lvl >", value, "riskLvl");
            return (Criteria) this;
        }

        public Criteria andRiskLvlGreaterThanOrEqualTo(String value) {
            addCriterion("risk_lvl >=", value, "riskLvl");
            return (Criteria) this;
        }

        public Criteria andRiskLvlLessThan(String value) {
            addCriterion("risk_lvl <", value, "riskLvl");
            return (Criteria) this;
        }

        public Criteria andRiskLvlLessThanOrEqualTo(String value) {
            addCriterion("risk_lvl <=", value, "riskLvl");
            return (Criteria) this;
        }

        public Criteria andRiskLvlLike(String value) {
            addCriterion("risk_lvl like", value, "riskLvl");
            return (Criteria) this;
        }

        public Criteria andRiskLvlNotLike(String value) {
            addCriterion("risk_lvl not like", value, "riskLvl");
            return (Criteria) this;
        }

        public Criteria andRiskLvlIn(List<String> values) {
            addCriterion("risk_lvl in", values, "riskLvl");
            return (Criteria) this;
        }

        public Criteria andRiskLvlNotIn(List<String> values) {
            addCriterion("risk_lvl not in", values, "riskLvl");
            return (Criteria) this;
        }

        public Criteria andRiskLvlBetween(String value1, String value2) {
            addCriterion("risk_lvl between", value1, value2, "riskLvl");
            return (Criteria) this;
        }

        public Criteria andRiskLvlNotBetween(String value1, String value2) {
            addCriterion("risk_lvl not between", value1, value2, "riskLvl");
            return (Criteria) this;
        }

        public Criteria andBuyerNumLimitIsNull() {
            addCriterion("buyer_num_limit is null");
            return (Criteria) this;
        }

        public Criteria andBuyerNumLimitIsNotNull() {
            addCriterion("buyer_num_limit is not null");
            return (Criteria) this;
        }

        public Criteria andBuyerNumLimitEqualTo(Integer value) {
            addCriterion("buyer_num_limit =", value, "buyerNumLimit");
            return (Criteria) this;
        }

        public Criteria andBuyerNumLimitNotEqualTo(Integer value) {
            addCriterion("buyer_num_limit <>", value, "buyerNumLimit");
            return (Criteria) this;
        }

        public Criteria andBuyerNumLimitGreaterThan(Integer value) {
            addCriterion("buyer_num_limit >", value, "buyerNumLimit");
            return (Criteria) this;
        }

        public Criteria andBuyerNumLimitGreaterThanOrEqualTo(Integer value) {
            addCriterion("buyer_num_limit >=", value, "buyerNumLimit");
            return (Criteria) this;
        }

        public Criteria andBuyerNumLimitLessThan(Integer value) {
            addCriterion("buyer_num_limit <", value, "buyerNumLimit");
            return (Criteria) this;
        }

        public Criteria andBuyerNumLimitLessThanOrEqualTo(Integer value) {
            addCriterion("buyer_num_limit <=", value, "buyerNumLimit");
            return (Criteria) this;
        }

        public Criteria andBuyerNumLimitIn(List<Integer> values) {
            addCriterion("buyer_num_limit in", values, "buyerNumLimit");
            return (Criteria) this;
        }

        public Criteria andBuyerNumLimitNotIn(List<Integer> values) {
            addCriterion("buyer_num_limit not in", values, "buyerNumLimit");
            return (Criteria) this;
        }

        public Criteria andBuyerNumLimitBetween(Integer value1, Integer value2) {
            addCriterion("buyer_num_limit between", value1, value2, "buyerNumLimit");
            return (Criteria) this;
        }

        public Criteria andBuyerNumLimitNotBetween(Integer value1, Integer value2) {
            addCriterion("buyer_num_limit not between", value1, value2, "buyerNumLimit");
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

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
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