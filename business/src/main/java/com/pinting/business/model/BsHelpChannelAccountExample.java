package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BsHelpChannelAccountExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsHelpChannelAccountExample() {
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

        public Criteria andChannelIsNull() {
            addCriterion("channel is null");
            return (Criteria) this;
        }

        public Criteria andChannelIsNotNull() {
            addCriterion("channel is not null");
            return (Criteria) this;
        }

        public Criteria andChannelEqualTo(String value) {
            addCriterion("channel =", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotEqualTo(String value) {
            addCriterion("channel <>", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThan(String value) {
            addCriterion("channel >", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThanOrEqualTo(String value) {
            addCriterion("channel >=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThan(String value) {
            addCriterion("channel <", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThanOrEqualTo(String value) {
            addCriterion("channel <=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLike(String value) {
            addCriterion("channel like", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotLike(String value) {
            addCriterion("channel not like", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelIn(List<String> values) {
            addCriterion("channel in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotIn(List<String> values) {
            addCriterion("channel not in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelBetween(String value1, String value2) {
            addCriterion("channel between", value1, value2, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotBetween(String value1, String value2) {
            addCriterion("channel not between", value1, value2, "channel");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNull() {
            addCriterion("balance is null");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNotNull() {
            addCriterion("balance is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceEqualTo(Double value) {
            addCriterion("balance =", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotEqualTo(Double value) {
            addCriterion("balance <>", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThan(Double value) {
            addCriterion("balance >", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThanOrEqualTo(Double value) {
            addCriterion("balance >=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThan(Double value) {
            addCriterion("balance <", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThanOrEqualTo(Double value) {
            addCriterion("balance <=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceIn(List<Double> values) {
            addCriterion("balance in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotIn(List<Double> values) {
            addCriterion("balance not in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceBetween(Double value1, Double value2) {
            addCriterion("balance between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotBetween(Double value1, Double value2) {
            addCriterion("balance not between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceIsNull() {
            addCriterion("available_balance is null");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceIsNotNull() {
            addCriterion("available_balance is not null");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceEqualTo(Double value) {
            addCriterion("available_balance =", value, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceNotEqualTo(Double value) {
            addCriterion("available_balance <>", value, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceGreaterThan(Double value) {
            addCriterion("available_balance >", value, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceGreaterThanOrEqualTo(Double value) {
            addCriterion("available_balance >=", value, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceLessThan(Double value) {
            addCriterion("available_balance <", value, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceLessThanOrEqualTo(Double value) {
            addCriterion("available_balance <=", value, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceIn(List<Double> values) {
            addCriterion("available_balance in", values, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceNotIn(List<Double> values) {
            addCriterion("available_balance not in", values, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceBetween(Double value1, Double value2) {
            addCriterion("available_balance between", value1, value2, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceNotBetween(Double value1, Double value2) {
            addCriterion("available_balance not between", value1, value2, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawIsNull() {
            addCriterion("can_withdraw is null");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawIsNotNull() {
            addCriterion("can_withdraw is not null");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawEqualTo(Double value) {
            addCriterion("can_withdraw =", value, "canWithdraw");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawNotEqualTo(Double value) {
            addCriterion("can_withdraw <>", value, "canWithdraw");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawGreaterThan(Double value) {
            addCriterion("can_withdraw >", value, "canWithdraw");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawGreaterThanOrEqualTo(Double value) {
            addCriterion("can_withdraw >=", value, "canWithdraw");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawLessThan(Double value) {
            addCriterion("can_withdraw <", value, "canWithdraw");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawLessThanOrEqualTo(Double value) {
            addCriterion("can_withdraw <=", value, "canWithdraw");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawIn(List<Double> values) {
            addCriterion("can_withdraw in", values, "canWithdraw");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawNotIn(List<Double> values) {
            addCriterion("can_withdraw not in", values, "canWithdraw");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawBetween(Double value1, Double value2) {
            addCriterion("can_withdraw between", value1, value2, "canWithdraw");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawNotBetween(Double value1, Double value2) {
            addCriterion("can_withdraw not between", value1, value2, "canWithdraw");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceIsNull() {
            addCriterion("freeze_balance is null");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceIsNotNull() {
            addCriterion("freeze_balance is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceEqualTo(Double value) {
            addCriterion("freeze_balance =", value, "freezeBalance");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceNotEqualTo(Double value) {
            addCriterion("freeze_balance <>", value, "freezeBalance");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceGreaterThan(Double value) {
            addCriterion("freeze_balance >", value, "freezeBalance");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceGreaterThanOrEqualTo(Double value) {
            addCriterion("freeze_balance >=", value, "freezeBalance");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceLessThan(Double value) {
            addCriterion("freeze_balance <", value, "freezeBalance");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceLessThanOrEqualTo(Double value) {
            addCriterion("freeze_balance <=", value, "freezeBalance");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceIn(List<Double> values) {
            addCriterion("freeze_balance in", values, "freezeBalance");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceNotIn(List<Double> values) {
            addCriterion("freeze_balance not in", values, "freezeBalance");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceBetween(Double value1, Double value2) {
            addCriterion("freeze_balance between", value1, value2, "freezeBalance");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceNotBetween(Double value1, Double value2) {
            addCriterion("freeze_balance not between", value1, value2, "freezeBalance");
            return (Criteria) this;
        }

        public Criteria andLastTransDateIsNull() {
            addCriterion("last_trans_date is null");
            return (Criteria) this;
        }

        public Criteria andLastTransDateIsNotNull() {
            addCriterion("last_trans_date is not null");
            return (Criteria) this;
        }

        public Criteria andLastTransDateEqualTo(Date value) {
            addCriterionForJDBCDate("last_trans_date =", value, "lastTransDate");
            return (Criteria) this;
        }

        public Criteria andLastTransDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("last_trans_date <>", value, "lastTransDate");
            return (Criteria) this;
        }

        public Criteria andLastTransDateGreaterThan(Date value) {
            addCriterionForJDBCDate("last_trans_date >", value, "lastTransDate");
            return (Criteria) this;
        }

        public Criteria andLastTransDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("last_trans_date >=", value, "lastTransDate");
            return (Criteria) this;
        }

        public Criteria andLastTransDateLessThan(Date value) {
            addCriterionForJDBCDate("last_trans_date <", value, "lastTransDate");
            return (Criteria) this;
        }

        public Criteria andLastTransDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("last_trans_date <=", value, "lastTransDate");
            return (Criteria) this;
        }

        public Criteria andLastTransDateIn(List<Date> values) {
            addCriterionForJDBCDate("last_trans_date in", values, "lastTransDate");
            return (Criteria) this;
        }

        public Criteria andLastTransDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("last_trans_date not in", values, "lastTransDate");
            return (Criteria) this;
        }

        public Criteria andLastTransDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("last_trans_date between", value1, value2, "lastTransDate");
            return (Criteria) this;
        }

        public Criteria andLastTransDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("last_trans_date not between", value1, value2, "lastTransDate");
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

        public Criteria andOpenBalanceIsNull() {
            addCriterion("open_balance is null");
            return (Criteria) this;
        }

        public Criteria andOpenBalanceIsNotNull() {
            addCriterion("open_balance is not null");
            return (Criteria) this;
        }

        public Criteria andOpenBalanceEqualTo(Double value) {
            addCriterion("open_balance =", value, "openBalance");
            return (Criteria) this;
        }

        public Criteria andOpenBalanceNotEqualTo(Double value) {
            addCriterion("open_balance <>", value, "openBalance");
            return (Criteria) this;
        }

        public Criteria andOpenBalanceGreaterThan(Double value) {
            addCriterion("open_balance >", value, "openBalance");
            return (Criteria) this;
        }

        public Criteria andOpenBalanceGreaterThanOrEqualTo(Double value) {
            addCriterion("open_balance >=", value, "openBalance");
            return (Criteria) this;
        }

        public Criteria andOpenBalanceLessThan(Double value) {
            addCriterion("open_balance <", value, "openBalance");
            return (Criteria) this;
        }

        public Criteria andOpenBalanceLessThanOrEqualTo(Double value) {
            addCriterion("open_balance <=", value, "openBalance");
            return (Criteria) this;
        }

        public Criteria andOpenBalanceIn(List<Double> values) {
            addCriterion("open_balance in", values, "openBalance");
            return (Criteria) this;
        }

        public Criteria andOpenBalanceNotIn(List<Double> values) {
            addCriterion("open_balance not in", values, "openBalance");
            return (Criteria) this;
        }

        public Criteria andOpenBalanceBetween(Double value1, Double value2) {
            addCriterion("open_balance between", value1, value2, "openBalance");
            return (Criteria) this;
        }

        public Criteria andOpenBalanceNotBetween(Double value1, Double value2) {
            addCriterion("open_balance not between", value1, value2, "openBalance");
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