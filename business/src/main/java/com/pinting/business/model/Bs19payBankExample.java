package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bs19payBankExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public Bs19payBankExample() {
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

        public Criteria andBankIdIsNull() {
            addCriterion("bank_id is null");
            return (Criteria) this;
        }

        public Criteria andBankIdIsNotNull() {
            addCriterion("bank_id is not null");
            return (Criteria) this;
        }

        public Criteria andBankIdEqualTo(Integer value) {
            addCriterion("bank_id =", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotEqualTo(Integer value) {
            addCriterion("bank_id <>", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdGreaterThan(Integer value) {
            addCriterion("bank_id >", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("bank_id >=", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLessThan(Integer value) {
            addCriterion("bank_id <", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLessThanOrEqualTo(Integer value) {
            addCriterion("bank_id <=", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdIn(List<Integer> values) {
            addCriterion("bank_id in", values, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotIn(List<Integer> values) {
            addCriterion("bank_id not in", values, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdBetween(Integer value1, Integer value2) {
            addCriterion("bank_id between", value1, value2, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotBetween(Integer value1, Integer value2) {
            addCriterion("bank_id not between", value1, value2, "bankId");
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

        public Criteria andChannelPriorityIsNull() {
            addCriterion("channel_priority is null");
            return (Criteria) this;
        }

        public Criteria andChannelPriorityIsNotNull() {
            addCriterion("channel_priority is not null");
            return (Criteria) this;
        }

        public Criteria andChannelPriorityEqualTo(Integer value) {
            addCriterion("channel_priority =", value, "channelPriority");
            return (Criteria) this;
        }

        public Criteria andChannelPriorityNotEqualTo(Integer value) {
            addCriterion("channel_priority <>", value, "channelPriority");
            return (Criteria) this;
        }

        public Criteria andChannelPriorityGreaterThan(Integer value) {
            addCriterion("channel_priority >", value, "channelPriority");
            return (Criteria) this;
        }

        public Criteria andChannelPriorityGreaterThanOrEqualTo(Integer value) {
            addCriterion("channel_priority >=", value, "channelPriority");
            return (Criteria) this;
        }

        public Criteria andChannelPriorityLessThan(Integer value) {
            addCriterion("channel_priority <", value, "channelPriority");
            return (Criteria) this;
        }

        public Criteria andChannelPriorityLessThanOrEqualTo(Integer value) {
            addCriterion("channel_priority <=", value, "channelPriority");
            return (Criteria) this;
        }

        public Criteria andChannelPriorityIn(List<Integer> values) {
            addCriterion("channel_priority in", values, "channelPriority");
            return (Criteria) this;
        }

        public Criteria andChannelPriorityNotIn(List<Integer> values) {
            addCriterion("channel_priority not in", values, "channelPriority");
            return (Criteria) this;
        }

        public Criteria andChannelPriorityBetween(Integer value1, Integer value2) {
            addCriterion("channel_priority between", value1, value2, "channelPriority");
            return (Criteria) this;
        }

        public Criteria andChannelPriorityNotBetween(Integer value1, Integer value2) {
            addCriterion("channel_priority not between", value1, value2, "channelPriority");
            return (Criteria) this;
        }

        public Criteria andIsMainIsNull() {
            addCriterion("is_main is null");
            return (Criteria) this;
        }

        public Criteria andIsMainIsNotNull() {
            addCriterion("is_main is not null");
            return (Criteria) this;
        }

        public Criteria andIsMainEqualTo(Integer value) {
            addCriterion("is_main =", value, "isMain");
            return (Criteria) this;
        }

        public Criteria andIsMainNotEqualTo(Integer value) {
            addCriterion("is_main <>", value, "isMain");
            return (Criteria) this;
        }

        public Criteria andIsMainGreaterThan(Integer value) {
            addCriterion("is_main >", value, "isMain");
            return (Criteria) this;
        }

        public Criteria andIsMainGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_main >=", value, "isMain");
            return (Criteria) this;
        }

        public Criteria andIsMainLessThan(Integer value) {
            addCriterion("is_main <", value, "isMain");
            return (Criteria) this;
        }

        public Criteria andIsMainLessThanOrEqualTo(Integer value) {
            addCriterion("is_main <=", value, "isMain");
            return (Criteria) this;
        }

        public Criteria andIsMainIn(List<Integer> values) {
            addCriterion("is_main in", values, "isMain");
            return (Criteria) this;
        }

        public Criteria andIsMainNotIn(List<Integer> values) {
            addCriterion("is_main not in", values, "isMain");
            return (Criteria) this;
        }

        public Criteria andIsMainBetween(Integer value1, Integer value2) {
            addCriterion("is_main between", value1, value2, "isMain");
            return (Criteria) this;
        }

        public Criteria andIsMainNotBetween(Integer value1, Integer value2) {
            addCriterion("is_main not between", value1, value2, "isMain");
            return (Criteria) this;
        }

        public Criteria andPay19BankCodeIsNull() {
            addCriterion("pay19_bank_code is null");
            return (Criteria) this;
        }

        public Criteria andPay19BankCodeIsNotNull() {
            addCriterion("pay19_bank_code is not null");
            return (Criteria) this;
        }

        public Criteria andPay19BankCodeEqualTo(String value) {
            addCriterion("pay19_bank_code =", value, "pay19BankCode");
            return (Criteria) this;
        }

        public Criteria andPay19BankCodeNotEqualTo(String value) {
            addCriterion("pay19_bank_code <>", value, "pay19BankCode");
            return (Criteria) this;
        }

        public Criteria andPay19BankCodeGreaterThan(String value) {
            addCriterion("pay19_bank_code >", value, "pay19BankCode");
            return (Criteria) this;
        }

        public Criteria andPay19BankCodeGreaterThanOrEqualTo(String value) {
            addCriterion("pay19_bank_code >=", value, "pay19BankCode");
            return (Criteria) this;
        }

        public Criteria andPay19BankCodeLessThan(String value) {
            addCriterion("pay19_bank_code <", value, "pay19BankCode");
            return (Criteria) this;
        }

        public Criteria andPay19BankCodeLessThanOrEqualTo(String value) {
            addCriterion("pay19_bank_code <=", value, "pay19BankCode");
            return (Criteria) this;
        }

        public Criteria andPay19BankCodeLike(String value) {
            addCriterion("pay19_bank_code like", value, "pay19BankCode");
            return (Criteria) this;
        }

        public Criteria andPay19BankCodeNotLike(String value) {
            addCriterion("pay19_bank_code not like", value, "pay19BankCode");
            return (Criteria) this;
        }

        public Criteria andPay19BankCodeIn(List<String> values) {
            addCriterion("pay19_bank_code in", values, "pay19BankCode");
            return (Criteria) this;
        }

        public Criteria andPay19BankCodeNotIn(List<String> values) {
            addCriterion("pay19_bank_code not in", values, "pay19BankCode");
            return (Criteria) this;
        }

        public Criteria andPay19BankCodeBetween(String value1, String value2) {
            addCriterion("pay19_bank_code between", value1, value2, "pay19BankCode");
            return (Criteria) this;
        }

        public Criteria andPay19BankCodeNotBetween(String value1, String value2) {
            addCriterion("pay19_bank_code not between", value1, value2, "pay19BankCode");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNull() {
            addCriterion("pay_type is null");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNotNull() {
            addCriterion("pay_type is not null");
            return (Criteria) this;
        }

        public Criteria andPayTypeEqualTo(Integer value) {
            addCriterion("pay_type =", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotEqualTo(Integer value) {
            addCriterion("pay_type <>", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThan(Integer value) {
            addCriterion("pay_type >", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_type >=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThan(Integer value) {
            addCriterion("pay_type <", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThanOrEqualTo(Integer value) {
            addCriterion("pay_type <=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeIn(List<Integer> values) {
            addCriterion("pay_type in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotIn(List<Integer> values) {
            addCriterion("pay_type not in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeBetween(Integer value1, Integer value2) {
            addCriterion("pay_type between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_type not between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andOneTopIsNull() {
            addCriterion("one_top is null");
            return (Criteria) this;
        }

        public Criteria andOneTopIsNotNull() {
            addCriterion("one_top is not null");
            return (Criteria) this;
        }

        public Criteria andOneTopEqualTo(Double value) {
            addCriterion("one_top =", value, "oneTop");
            return (Criteria) this;
        }

        public Criteria andOneTopNotEqualTo(Double value) {
            addCriterion("one_top <>", value, "oneTop");
            return (Criteria) this;
        }

        public Criteria andOneTopGreaterThan(Double value) {
            addCriterion("one_top >", value, "oneTop");
            return (Criteria) this;
        }

        public Criteria andOneTopGreaterThanOrEqualTo(Double value) {
            addCriterion("one_top >=", value, "oneTop");
            return (Criteria) this;
        }

        public Criteria andOneTopLessThan(Double value) {
            addCriterion("one_top <", value, "oneTop");
            return (Criteria) this;
        }

        public Criteria andOneTopLessThanOrEqualTo(Double value) {
            addCriterion("one_top <=", value, "oneTop");
            return (Criteria) this;
        }

        public Criteria andOneTopIn(List<Double> values) {
            addCriterion("one_top in", values, "oneTop");
            return (Criteria) this;
        }

        public Criteria andOneTopNotIn(List<Double> values) {
            addCriterion("one_top not in", values, "oneTop");
            return (Criteria) this;
        }

        public Criteria andOneTopBetween(Double value1, Double value2) {
            addCriterion("one_top between", value1, value2, "oneTop");
            return (Criteria) this;
        }

        public Criteria andOneTopNotBetween(Double value1, Double value2) {
            addCriterion("one_top not between", value1, value2, "oneTop");
            return (Criteria) this;
        }

        public Criteria andDayTopIsNull() {
            addCriterion("day_top is null");
            return (Criteria) this;
        }

        public Criteria andDayTopIsNotNull() {
            addCriterion("day_top is not null");
            return (Criteria) this;
        }

        public Criteria andDayTopEqualTo(Double value) {
            addCriterion("day_top =", value, "dayTop");
            return (Criteria) this;
        }

        public Criteria andDayTopNotEqualTo(Double value) {
            addCriterion("day_top <>", value, "dayTop");
            return (Criteria) this;
        }

        public Criteria andDayTopGreaterThan(Double value) {
            addCriterion("day_top >", value, "dayTop");
            return (Criteria) this;
        }

        public Criteria andDayTopGreaterThanOrEqualTo(Double value) {
            addCriterion("day_top >=", value, "dayTop");
            return (Criteria) this;
        }

        public Criteria andDayTopLessThan(Double value) {
            addCriterion("day_top <", value, "dayTop");
            return (Criteria) this;
        }

        public Criteria andDayTopLessThanOrEqualTo(Double value) {
            addCriterion("day_top <=", value, "dayTop");
            return (Criteria) this;
        }

        public Criteria andDayTopIn(List<Double> values) {
            addCriterion("day_top in", values, "dayTop");
            return (Criteria) this;
        }

        public Criteria andDayTopNotIn(List<Double> values) {
            addCriterion("day_top not in", values, "dayTop");
            return (Criteria) this;
        }

        public Criteria andDayTopBetween(Double value1, Double value2) {
            addCriterion("day_top between", value1, value2, "dayTop");
            return (Criteria) this;
        }

        public Criteria andDayTopNotBetween(Double value1, Double value2) {
            addCriterion("day_top not between", value1, value2, "dayTop");
            return (Criteria) this;
        }

        public Criteria andMonthTopIsNull() {
            addCriterion("month_top is null");
            return (Criteria) this;
        }

        public Criteria andMonthTopIsNotNull() {
            addCriterion("month_top is not null");
            return (Criteria) this;
        }

        public Criteria andMonthTopEqualTo(Double value) {
            addCriterion("month_top =", value, "monthTop");
            return (Criteria) this;
        }

        public Criteria andMonthTopNotEqualTo(Double value) {
            addCriterion("month_top <>", value, "monthTop");
            return (Criteria) this;
        }

        public Criteria andMonthTopGreaterThan(Double value) {
            addCriterion("month_top >", value, "monthTop");
            return (Criteria) this;
        }

        public Criteria andMonthTopGreaterThanOrEqualTo(Double value) {
            addCriterion("month_top >=", value, "monthTop");
            return (Criteria) this;
        }

        public Criteria andMonthTopLessThan(Double value) {
            addCriterion("month_top <", value, "monthTop");
            return (Criteria) this;
        }

        public Criteria andMonthTopLessThanOrEqualTo(Double value) {
            addCriterion("month_top <=", value, "monthTop");
            return (Criteria) this;
        }

        public Criteria andMonthTopIn(List<Double> values) {
            addCriterion("month_top in", values, "monthTop");
            return (Criteria) this;
        }

        public Criteria andMonthTopNotIn(List<Double> values) {
            addCriterion("month_top not in", values, "monthTop");
            return (Criteria) this;
        }

        public Criteria andMonthTopBetween(Double value1, Double value2) {
            addCriterion("month_top between", value1, value2, "monthTop");
            return (Criteria) this;
        }

        public Criteria andMonthTopNotBetween(Double value1, Double value2) {
            addCriterion("month_top not between", value1, value2, "monthTop");
            return (Criteria) this;
        }

        public Criteria andForbiddenStartIsNull() {
            addCriterion("forbidden_start is null");
            return (Criteria) this;
        }

        public Criteria andForbiddenStartIsNotNull() {
            addCriterion("forbidden_start is not null");
            return (Criteria) this;
        }

        public Criteria andForbiddenStartEqualTo(Date value) {
            addCriterion("forbidden_start =", value, "forbiddenStart");
            return (Criteria) this;
        }

        public Criteria andForbiddenStartNotEqualTo(Date value) {
            addCriterion("forbidden_start <>", value, "forbiddenStart");
            return (Criteria) this;
        }

        public Criteria andForbiddenStartGreaterThan(Date value) {
            addCriterion("forbidden_start >", value, "forbiddenStart");
            return (Criteria) this;
        }

        public Criteria andForbiddenStartGreaterThanOrEqualTo(Date value) {
            addCriterion("forbidden_start >=", value, "forbiddenStart");
            return (Criteria) this;
        }

        public Criteria andForbiddenStartLessThan(Date value) {
            addCriterion("forbidden_start <", value, "forbiddenStart");
            return (Criteria) this;
        }

        public Criteria andForbiddenStartLessThanOrEqualTo(Date value) {
            addCriterion("forbidden_start <=", value, "forbiddenStart");
            return (Criteria) this;
        }

        public Criteria andForbiddenStartIn(List<Date> values) {
            addCriterion("forbidden_start in", values, "forbiddenStart");
            return (Criteria) this;
        }

        public Criteria andForbiddenStartNotIn(List<Date> values) {
            addCriterion("forbidden_start not in", values, "forbiddenStart");
            return (Criteria) this;
        }

        public Criteria andForbiddenStartBetween(Date value1, Date value2) {
            addCriterion("forbidden_start between", value1, value2, "forbiddenStart");
            return (Criteria) this;
        }

        public Criteria andForbiddenStartNotBetween(Date value1, Date value2) {
            addCriterion("forbidden_start not between", value1, value2, "forbiddenStart");
            return (Criteria) this;
        }

        public Criteria andForbiddenEndIsNull() {
            addCriterion("forbidden_end is null");
            return (Criteria) this;
        }

        public Criteria andForbiddenEndIsNotNull() {
            addCriterion("forbidden_end is not null");
            return (Criteria) this;
        }

        public Criteria andForbiddenEndEqualTo(Date value) {
            addCriterion("forbidden_end =", value, "forbiddenEnd");
            return (Criteria) this;
        }

        public Criteria andForbiddenEndNotEqualTo(Date value) {
            addCriterion("forbidden_end <>", value, "forbiddenEnd");
            return (Criteria) this;
        }

        public Criteria andForbiddenEndGreaterThan(Date value) {
            addCriterion("forbidden_end >", value, "forbiddenEnd");
            return (Criteria) this;
        }

        public Criteria andForbiddenEndGreaterThanOrEqualTo(Date value) {
            addCriterion("forbidden_end >=", value, "forbiddenEnd");
            return (Criteria) this;
        }

        public Criteria andForbiddenEndLessThan(Date value) {
            addCriterion("forbidden_end <", value, "forbiddenEnd");
            return (Criteria) this;
        }

        public Criteria andForbiddenEndLessThanOrEqualTo(Date value) {
            addCriterion("forbidden_end <=", value, "forbiddenEnd");
            return (Criteria) this;
        }

        public Criteria andForbiddenEndIn(List<Date> values) {
            addCriterion("forbidden_end in", values, "forbiddenEnd");
            return (Criteria) this;
        }

        public Criteria andForbiddenEndNotIn(List<Date> values) {
            addCriterion("forbidden_end not in", values, "forbiddenEnd");
            return (Criteria) this;
        }

        public Criteria andForbiddenEndBetween(Date value1, Date value2) {
            addCriterion("forbidden_end between", value1, value2, "forbiddenEnd");
            return (Criteria) this;
        }

        public Criteria andForbiddenEndNotBetween(Date value1, Date value2) {
            addCriterion("forbidden_end not between", value1, value2, "forbiddenEnd");
            return (Criteria) this;
        }

        public Criteria andIsAvailableIsNull() {
            addCriterion("is_available is null");
            return (Criteria) this;
        }

        public Criteria andIsAvailableIsNotNull() {
            addCriterion("is_available is not null");
            return (Criteria) this;
        }

        public Criteria andIsAvailableEqualTo(Integer value) {
            addCriterion("is_available =", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableNotEqualTo(Integer value) {
            addCriterion("is_available <>", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableGreaterThan(Integer value) {
            addCriterion("is_available >", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_available >=", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableLessThan(Integer value) {
            addCriterion("is_available <", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableLessThanOrEqualTo(Integer value) {
            addCriterion("is_available <=", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableIn(List<Integer> values) {
            addCriterion("is_available in", values, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableNotIn(List<Integer> values) {
            addCriterion("is_available not in", values, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableBetween(Integer value1, Integer value2) {
            addCriterion("is_available between", value1, value2, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableNotBetween(Integer value1, Integer value2) {
            addCriterion("is_available not between", value1, value2, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andNoticeIsNull() {
            addCriterion("notice is null");
            return (Criteria) this;
        }

        public Criteria andNoticeIsNotNull() {
            addCriterion("notice is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeEqualTo(String value) {
            addCriterion("notice =", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeNotEqualTo(String value) {
            addCriterion("notice <>", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeGreaterThan(String value) {
            addCriterion("notice >", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeGreaterThanOrEqualTo(String value) {
            addCriterion("notice >=", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeLessThan(String value) {
            addCriterion("notice <", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeLessThanOrEqualTo(String value) {
            addCriterion("notice <=", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeLike(String value) {
            addCriterion("notice like", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeNotLike(String value) {
            addCriterion("notice not like", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeIn(List<String> values) {
            addCriterion("notice in", values, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeNotIn(List<String> values) {
            addCriterion("notice not in", values, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeBetween(String value1, String value2) {
            addCriterion("notice between", value1, value2, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeNotBetween(String value1, String value2) {
            addCriterion("notice not between", value1, value2, "notice");
            return (Criteria) this;
        }

        public Criteria andDailyNoticeIsNull() {
            addCriterion("daily_notice is null");
            return (Criteria) this;
        }

        public Criteria andDailyNoticeIsNotNull() {
            addCriterion("daily_notice is not null");
            return (Criteria) this;
        }

        public Criteria andDailyNoticeEqualTo(String value) {
            addCriterion("daily_notice =", value, "dailyNotice");
            return (Criteria) this;
        }

        public Criteria andDailyNoticeNotEqualTo(String value) {
            addCriterion("daily_notice <>", value, "dailyNotice");
            return (Criteria) this;
        }

        public Criteria andDailyNoticeGreaterThan(String value) {
            addCriterion("daily_notice >", value, "dailyNotice");
            return (Criteria) this;
        }

        public Criteria andDailyNoticeGreaterThanOrEqualTo(String value) {
            addCriterion("daily_notice >=", value, "dailyNotice");
            return (Criteria) this;
        }

        public Criteria andDailyNoticeLessThan(String value) {
            addCriterion("daily_notice <", value, "dailyNotice");
            return (Criteria) this;
        }

        public Criteria andDailyNoticeLessThanOrEqualTo(String value) {
            addCriterion("daily_notice <=", value, "dailyNotice");
            return (Criteria) this;
        }

        public Criteria andDailyNoticeLike(String value) {
            addCriterion("daily_notice like", value, "dailyNotice");
            return (Criteria) this;
        }

        public Criteria andDailyNoticeNotLike(String value) {
            addCriterion("daily_notice not like", value, "dailyNotice");
            return (Criteria) this;
        }

        public Criteria andDailyNoticeIn(List<String> values) {
            addCriterion("daily_notice in", values, "dailyNotice");
            return (Criteria) this;
        }

        public Criteria andDailyNoticeNotIn(List<String> values) {
            addCriterion("daily_notice not in", values, "dailyNotice");
            return (Criteria) this;
        }

        public Criteria andDailyNoticeBetween(String value1, String value2) {
            addCriterion("daily_notice between", value1, value2, "dailyNotice");
            return (Criteria) this;
        }

        public Criteria andDailyNoticeNotBetween(String value1, String value2) {
            addCriterion("daily_notice not between", value1, value2, "dailyNotice");
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