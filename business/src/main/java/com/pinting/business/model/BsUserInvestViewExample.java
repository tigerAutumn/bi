package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsUserInvestViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsUserInvestViewExample() {
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

        public Criteria andTodayUserNumIsNull() {
            addCriterion("today_user_num is null");
            return (Criteria) this;
        }

        public Criteria andTodayUserNumIsNotNull() {
            addCriterion("today_user_num is not null");
            return (Criteria) this;
        }

        public Criteria andTodayUserNumEqualTo(Integer value) {
            addCriterion("today_user_num =", value, "todayUserNum");
            return (Criteria) this;
        }

        public Criteria andTodayUserNumNotEqualTo(Integer value) {
            addCriterion("today_user_num <>", value, "todayUserNum");
            return (Criteria) this;
        }

        public Criteria andTodayUserNumGreaterThan(Integer value) {
            addCriterion("today_user_num >", value, "todayUserNum");
            return (Criteria) this;
        }

        public Criteria andTodayUserNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("today_user_num >=", value, "todayUserNum");
            return (Criteria) this;
        }

        public Criteria andTodayUserNumLessThan(Integer value) {
            addCriterion("today_user_num <", value, "todayUserNum");
            return (Criteria) this;
        }

        public Criteria andTodayUserNumLessThanOrEqualTo(Integer value) {
            addCriterion("today_user_num <=", value, "todayUserNum");
            return (Criteria) this;
        }

        public Criteria andTodayUserNumIn(List<Integer> values) {
            addCriterion("today_user_num in", values, "todayUserNum");
            return (Criteria) this;
        }

        public Criteria andTodayUserNumNotIn(List<Integer> values) {
            addCriterion("today_user_num not in", values, "todayUserNum");
            return (Criteria) this;
        }

        public Criteria andTodayUserNumBetween(Integer value1, Integer value2) {
            addCriterion("today_user_num between", value1, value2, "todayUserNum");
            return (Criteria) this;
        }

        public Criteria andTodayUserNumNotBetween(Integer value1, Integer value2) {
            addCriterion("today_user_num not between", value1, value2, "todayUserNum");
            return (Criteria) this;
        }

        public Criteria andTodayInvestNumIsNull() {
            addCriterion("today_invest_num is null");
            return (Criteria) this;
        }

        public Criteria andTodayInvestNumIsNotNull() {
            addCriterion("today_invest_num is not null");
            return (Criteria) this;
        }

        public Criteria andTodayInvestNumEqualTo(Integer value) {
            addCriterion("today_invest_num =", value, "todayInvestNum");
            return (Criteria) this;
        }

        public Criteria andTodayInvestNumNotEqualTo(Integer value) {
            addCriterion("today_invest_num <>", value, "todayInvestNum");
            return (Criteria) this;
        }

        public Criteria andTodayInvestNumGreaterThan(Integer value) {
            addCriterion("today_invest_num >", value, "todayInvestNum");
            return (Criteria) this;
        }

        public Criteria andTodayInvestNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("today_invest_num >=", value, "todayInvestNum");
            return (Criteria) this;
        }

        public Criteria andTodayInvestNumLessThan(Integer value) {
            addCriterion("today_invest_num <", value, "todayInvestNum");
            return (Criteria) this;
        }

        public Criteria andTodayInvestNumLessThanOrEqualTo(Integer value) {
            addCriterion("today_invest_num <=", value, "todayInvestNum");
            return (Criteria) this;
        }

        public Criteria andTodayInvestNumIn(List<Integer> values) {
            addCriterion("today_invest_num in", values, "todayInvestNum");
            return (Criteria) this;
        }

        public Criteria andTodayInvestNumNotIn(List<Integer> values) {
            addCriterion("today_invest_num not in", values, "todayInvestNum");
            return (Criteria) this;
        }

        public Criteria andTodayInvestNumBetween(Integer value1, Integer value2) {
            addCriterion("today_invest_num between", value1, value2, "todayInvestNum");
            return (Criteria) this;
        }

        public Criteria andTodayInvestNumNotBetween(Integer value1, Integer value2) {
            addCriterion("today_invest_num not between", value1, value2, "todayInvestNum");
            return (Criteria) this;
        }

        public Criteria andTodayInvestAmountIsNull() {
            addCriterion("today_invest_amount is null");
            return (Criteria) this;
        }

        public Criteria andTodayInvestAmountIsNotNull() {
            addCriterion("today_invest_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTodayInvestAmountEqualTo(Double value) {
            addCriterion("today_invest_amount =", value, "todayInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTodayInvestAmountNotEqualTo(Double value) {
            addCriterion("today_invest_amount <>", value, "todayInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTodayInvestAmountGreaterThan(Double value) {
            addCriterion("today_invest_amount >", value, "todayInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTodayInvestAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("today_invest_amount >=", value, "todayInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTodayInvestAmountLessThan(Double value) {
            addCriterion("today_invest_amount <", value, "todayInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTodayInvestAmountLessThanOrEqualTo(Double value) {
            addCriterion("today_invest_amount <=", value, "todayInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTodayInvestAmountIn(List<Double> values) {
            addCriterion("today_invest_amount in", values, "todayInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTodayInvestAmountNotIn(List<Double> values) {
            addCriterion("today_invest_amount not in", values, "todayInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTodayInvestAmountBetween(Double value1, Double value2) {
            addCriterion("today_invest_amount between", value1, value2, "todayInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTodayInvestAmountNotBetween(Double value1, Double value2) {
            addCriterion("today_invest_amount not between", value1, value2, "todayInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTodayAnnualAmountIsNull() {
            addCriterion("today_annual_amount is null");
            return (Criteria) this;
        }

        public Criteria andTodayAnnualAmountIsNotNull() {
            addCriterion("today_annual_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTodayAnnualAmountEqualTo(Double value) {
            addCriterion("today_annual_amount =", value, "todayAnnualAmount");
            return (Criteria) this;
        }

        public Criteria andTodayAnnualAmountNotEqualTo(Double value) {
            addCriterion("today_annual_amount <>", value, "todayAnnualAmount");
            return (Criteria) this;
        }

        public Criteria andTodayAnnualAmountGreaterThan(Double value) {
            addCriterion("today_annual_amount >", value, "todayAnnualAmount");
            return (Criteria) this;
        }

        public Criteria andTodayAnnualAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("today_annual_amount >=", value, "todayAnnualAmount");
            return (Criteria) this;
        }

        public Criteria andTodayAnnualAmountLessThan(Double value) {
            addCriterion("today_annual_amount <", value, "todayAnnualAmount");
            return (Criteria) this;
        }

        public Criteria andTodayAnnualAmountLessThanOrEqualTo(Double value) {
            addCriterion("today_annual_amount <=", value, "todayAnnualAmount");
            return (Criteria) this;
        }

        public Criteria andTodayAnnualAmountIn(List<Double> values) {
            addCriterion("today_annual_amount in", values, "todayAnnualAmount");
            return (Criteria) this;
        }

        public Criteria andTodayAnnualAmountNotIn(List<Double> values) {
            addCriterion("today_annual_amount not in", values, "todayAnnualAmount");
            return (Criteria) this;
        }

        public Criteria andTodayAnnualAmountBetween(Double value1, Double value2) {
            addCriterion("today_annual_amount between", value1, value2, "todayAnnualAmount");
            return (Criteria) this;
        }

        public Criteria andTodayAnnualAmountNotBetween(Double value1, Double value2) {
            addCriterion("today_annual_amount not between", value1, value2, "todayAnnualAmount");
            return (Criteria) this;
        }

        public Criteria andTotalUserNumIsNull() {
            addCriterion("total_user_num is null");
            return (Criteria) this;
        }

        public Criteria andTotalUserNumIsNotNull() {
            addCriterion("total_user_num is not null");
            return (Criteria) this;
        }

        public Criteria andTotalUserNumEqualTo(Integer value) {
            addCriterion("total_user_num =", value, "totalUserNum");
            return (Criteria) this;
        }

        public Criteria andTotalUserNumNotEqualTo(Integer value) {
            addCriterion("total_user_num <>", value, "totalUserNum");
            return (Criteria) this;
        }

        public Criteria andTotalUserNumGreaterThan(Integer value) {
            addCriterion("total_user_num >", value, "totalUserNum");
            return (Criteria) this;
        }

        public Criteria andTotalUserNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_user_num >=", value, "totalUserNum");
            return (Criteria) this;
        }

        public Criteria andTotalUserNumLessThan(Integer value) {
            addCriterion("total_user_num <", value, "totalUserNum");
            return (Criteria) this;
        }

        public Criteria andTotalUserNumLessThanOrEqualTo(Integer value) {
            addCriterion("total_user_num <=", value, "totalUserNum");
            return (Criteria) this;
        }

        public Criteria andTotalUserNumIn(List<Integer> values) {
            addCriterion("total_user_num in", values, "totalUserNum");
            return (Criteria) this;
        }

        public Criteria andTotalUserNumNotIn(List<Integer> values) {
            addCriterion("total_user_num not in", values, "totalUserNum");
            return (Criteria) this;
        }

        public Criteria andTotalUserNumBetween(Integer value1, Integer value2) {
            addCriterion("total_user_num between", value1, value2, "totalUserNum");
            return (Criteria) this;
        }

        public Criteria andTotalUserNumNotBetween(Integer value1, Integer value2) {
            addCriterion("total_user_num not between", value1, value2, "totalUserNum");
            return (Criteria) this;
        }

        public Criteria andTotalInvestNumIsNull() {
            addCriterion("total_invest_num is null");
            return (Criteria) this;
        }

        public Criteria andTotalInvestNumIsNotNull() {
            addCriterion("total_invest_num is not null");
            return (Criteria) this;
        }

        public Criteria andTotalInvestNumEqualTo(Integer value) {
            addCriterion("total_invest_num =", value, "totalInvestNum");
            return (Criteria) this;
        }

        public Criteria andTotalInvestNumNotEqualTo(Integer value) {
            addCriterion("total_invest_num <>", value, "totalInvestNum");
            return (Criteria) this;
        }

        public Criteria andTotalInvestNumGreaterThan(Integer value) {
            addCriterion("total_invest_num >", value, "totalInvestNum");
            return (Criteria) this;
        }

        public Criteria andTotalInvestNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_invest_num >=", value, "totalInvestNum");
            return (Criteria) this;
        }

        public Criteria andTotalInvestNumLessThan(Integer value) {
            addCriterion("total_invest_num <", value, "totalInvestNum");
            return (Criteria) this;
        }

        public Criteria andTotalInvestNumLessThanOrEqualTo(Integer value) {
            addCriterion("total_invest_num <=", value, "totalInvestNum");
            return (Criteria) this;
        }

        public Criteria andTotalInvestNumIn(List<Integer> values) {
            addCriterion("total_invest_num in", values, "totalInvestNum");
            return (Criteria) this;
        }

        public Criteria andTotalInvestNumNotIn(List<Integer> values) {
            addCriterion("total_invest_num not in", values, "totalInvestNum");
            return (Criteria) this;
        }

        public Criteria andTotalInvestNumBetween(Integer value1, Integer value2) {
            addCriterion("total_invest_num between", value1, value2, "totalInvestNum");
            return (Criteria) this;
        }

        public Criteria andTotalInvestNumNotBetween(Integer value1, Integer value2) {
            addCriterion("total_invest_num not between", value1, value2, "totalInvestNum");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountIsNull() {
            addCriterion("total_invest_amount is null");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountIsNotNull() {
            addCriterion("total_invest_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountEqualTo(Double value) {
            addCriterion("total_invest_amount =", value, "totalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountNotEqualTo(Double value) {
            addCriterion("total_invest_amount <>", value, "totalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountGreaterThan(Double value) {
            addCriterion("total_invest_amount >", value, "totalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("total_invest_amount >=", value, "totalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountLessThan(Double value) {
            addCriterion("total_invest_amount <", value, "totalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountLessThanOrEqualTo(Double value) {
            addCriterion("total_invest_amount <=", value, "totalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountIn(List<Double> values) {
            addCriterion("total_invest_amount in", values, "totalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountNotIn(List<Double> values) {
            addCriterion("total_invest_amount not in", values, "totalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountBetween(Double value1, Double value2) {
            addCriterion("total_invest_amount between", value1, value2, "totalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountNotBetween(Double value1, Double value2) {
            addCriterion("total_invest_amount not between", value1, value2, "totalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAnnualAmountIsNull() {
            addCriterion("total_annual_amount is null");
            return (Criteria) this;
        }

        public Criteria andTotalAnnualAmountIsNotNull() {
            addCriterion("total_annual_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTotalAnnualAmountEqualTo(Double value) {
            addCriterion("total_annual_amount =", value, "totalAnnualAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAnnualAmountNotEqualTo(Double value) {
            addCriterion("total_annual_amount <>", value, "totalAnnualAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAnnualAmountGreaterThan(Double value) {
            addCriterion("total_annual_amount >", value, "totalAnnualAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAnnualAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("total_annual_amount >=", value, "totalAnnualAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAnnualAmountLessThan(Double value) {
            addCriterion("total_annual_amount <", value, "totalAnnualAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAnnualAmountLessThanOrEqualTo(Double value) {
            addCriterion("total_annual_amount <=", value, "totalAnnualAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAnnualAmountIn(List<Double> values) {
            addCriterion("total_annual_amount in", values, "totalAnnualAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAnnualAmountNotIn(List<Double> values) {
            addCriterion("total_annual_amount not in", values, "totalAnnualAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAnnualAmountBetween(Double value1, Double value2) {
            addCriterion("total_annual_amount between", value1, value2, "totalAnnualAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAnnualAmountNotBetween(Double value1, Double value2) {
            addCriterion("total_annual_amount not between", value1, value2, "totalAnnualAmount");
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