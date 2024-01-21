package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysDailyCheckGachaExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysDailyCheckGachaExample() {
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

        public Criteria andCheckDateIsNull() {
            addCriterion("check_date is null");
            return (Criteria) this;
        }

        public Criteria andCheckDateIsNotNull() {
            addCriterion("check_date is not null");
            return (Criteria) this;
        }

        public Criteria andCheckDateEqualTo(Date value) {
            addCriterion("check_date =", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotEqualTo(Date value) {
            addCriterion("check_date <>", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateGreaterThan(Date value) {
            addCriterion("check_date >", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateGreaterThanOrEqualTo(Date value) {
            addCriterion("check_date >=", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateLessThan(Date value) {
            addCriterion("check_date <", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateLessThanOrEqualTo(Date value) {
            addCriterion("check_date <=", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateIn(List<Date> values) {
            addCriterion("check_date in", values, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotIn(List<Date> values) {
            addCriterion("check_date not in", values, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateBetween(Date value1, Date value2) {
            addCriterion("check_date between", value1, value2, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotBetween(Date value1, Date value2) {
            addCriterion("check_date not between", value1, value2, "checkDate");
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

        public Criteria andBalanceWithdrawTotalAmtIsNull() {
            addCriterion("balance_withdraw_total_amt is null");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalAmtIsNotNull() {
            addCriterion("balance_withdraw_total_amt is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalAmtEqualTo(Double value) {
            addCriterion("balance_withdraw_total_amt =", value, "balanceWithdrawTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalAmtNotEqualTo(Double value) {
            addCriterion("balance_withdraw_total_amt <>", value, "balanceWithdrawTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalAmtGreaterThan(Double value) {
            addCriterion("balance_withdraw_total_amt >", value, "balanceWithdrawTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("balance_withdraw_total_amt >=", value, "balanceWithdrawTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalAmtLessThan(Double value) {
            addCriterion("balance_withdraw_total_amt <", value, "balanceWithdrawTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalAmtLessThanOrEqualTo(Double value) {
            addCriterion("balance_withdraw_total_amt <=", value, "balanceWithdrawTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalAmtIn(List<Double> values) {
            addCriterion("balance_withdraw_total_amt in", values, "balanceWithdrawTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalAmtNotIn(List<Double> values) {
            addCriterion("balance_withdraw_total_amt not in", values, "balanceWithdrawTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalAmtBetween(Double value1, Double value2) {
            addCriterion("balance_withdraw_total_amt between", value1, value2, "balanceWithdrawTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalAmtNotBetween(Double value1, Double value2) {
            addCriterion("balance_withdraw_total_amt not between", value1, value2, "balanceWithdrawTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccAmtIsNull() {
            addCriterion("balance_withdraw_succ_amt is null");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccAmtIsNotNull() {
            addCriterion("balance_withdraw_succ_amt is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccAmtEqualTo(Double value) {
            addCriterion("balance_withdraw_succ_amt =", value, "balanceWithdrawSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccAmtNotEqualTo(Double value) {
            addCriterion("balance_withdraw_succ_amt <>", value, "balanceWithdrawSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccAmtGreaterThan(Double value) {
            addCriterion("balance_withdraw_succ_amt >", value, "balanceWithdrawSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("balance_withdraw_succ_amt >=", value, "balanceWithdrawSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccAmtLessThan(Double value) {
            addCriterion("balance_withdraw_succ_amt <", value, "balanceWithdrawSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccAmtLessThanOrEqualTo(Double value) {
            addCriterion("balance_withdraw_succ_amt <=", value, "balanceWithdrawSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccAmtIn(List<Double> values) {
            addCriterion("balance_withdraw_succ_amt in", values, "balanceWithdrawSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccAmtNotIn(List<Double> values) {
            addCriterion("balance_withdraw_succ_amt not in", values, "balanceWithdrawSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccAmtBetween(Double value1, Double value2) {
            addCriterion("balance_withdraw_succ_amt between", value1, value2, "balanceWithdrawSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccAmtNotBetween(Double value1, Double value2) {
            addCriterion("balance_withdraw_succ_amt not between", value1, value2, "balanceWithdrawSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailAmtIsNull() {
            addCriterion("balance_withdraw_fail_amt is null");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailAmtIsNotNull() {
            addCriterion("balance_withdraw_fail_amt is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailAmtEqualTo(Double value) {
            addCriterion("balance_withdraw_fail_amt =", value, "balanceWithdrawFailAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailAmtNotEqualTo(Double value) {
            addCriterion("balance_withdraw_fail_amt <>", value, "balanceWithdrawFailAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailAmtGreaterThan(Double value) {
            addCriterion("balance_withdraw_fail_amt >", value, "balanceWithdrawFailAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("balance_withdraw_fail_amt >=", value, "balanceWithdrawFailAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailAmtLessThan(Double value) {
            addCriterion("balance_withdraw_fail_amt <", value, "balanceWithdrawFailAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailAmtLessThanOrEqualTo(Double value) {
            addCriterion("balance_withdraw_fail_amt <=", value, "balanceWithdrawFailAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailAmtIn(List<Double> values) {
            addCriterion("balance_withdraw_fail_amt in", values, "balanceWithdrawFailAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailAmtNotIn(List<Double> values) {
            addCriterion("balance_withdraw_fail_amt not in", values, "balanceWithdrawFailAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailAmtBetween(Double value1, Double value2) {
            addCriterion("balance_withdraw_fail_amt between", value1, value2, "balanceWithdrawFailAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailAmtNotBetween(Double value1, Double value2) {
            addCriterion("balance_withdraw_fail_amt not between", value1, value2, "balanceWithdrawFailAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessAmtIsNull() {
            addCriterion("balance_withdraw_process_amt is null");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessAmtIsNotNull() {
            addCriterion("balance_withdraw_process_amt is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessAmtEqualTo(Double value) {
            addCriterion("balance_withdraw_process_amt =", value, "balanceWithdrawProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessAmtNotEqualTo(Double value) {
            addCriterion("balance_withdraw_process_amt <>", value, "balanceWithdrawProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessAmtGreaterThan(Double value) {
            addCriterion("balance_withdraw_process_amt >", value, "balanceWithdrawProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("balance_withdraw_process_amt >=", value, "balanceWithdrawProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessAmtLessThan(Double value) {
            addCriterion("balance_withdraw_process_amt <", value, "balanceWithdrawProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessAmtLessThanOrEqualTo(Double value) {
            addCriterion("balance_withdraw_process_amt <=", value, "balanceWithdrawProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessAmtIn(List<Double> values) {
            addCriterion("balance_withdraw_process_amt in", values, "balanceWithdrawProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessAmtNotIn(List<Double> values) {
            addCriterion("balance_withdraw_process_amt not in", values, "balanceWithdrawProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessAmtBetween(Double value1, Double value2) {
            addCriterion("balance_withdraw_process_amt between", value1, value2, "balanceWithdrawProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessAmtNotBetween(Double value1, Double value2) {
            addCriterion("balance_withdraw_process_amt not between", value1, value2, "balanceWithdrawProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalCountIsNull() {
            addCriterion("balance_withdraw_total_count is null");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalCountIsNotNull() {
            addCriterion("balance_withdraw_total_count is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalCountEqualTo(Integer value) {
            addCriterion("balance_withdraw_total_count =", value, "balanceWithdrawTotalCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalCountNotEqualTo(Integer value) {
            addCriterion("balance_withdraw_total_count <>", value, "balanceWithdrawTotalCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalCountGreaterThan(Integer value) {
            addCriterion("balance_withdraw_total_count >", value, "balanceWithdrawTotalCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("balance_withdraw_total_count >=", value, "balanceWithdrawTotalCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalCountLessThan(Integer value) {
            addCriterion("balance_withdraw_total_count <", value, "balanceWithdrawTotalCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalCountLessThanOrEqualTo(Integer value) {
            addCriterion("balance_withdraw_total_count <=", value, "balanceWithdrawTotalCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalCountIn(List<Integer> values) {
            addCriterion("balance_withdraw_total_count in", values, "balanceWithdrawTotalCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalCountNotIn(List<Integer> values) {
            addCriterion("balance_withdraw_total_count not in", values, "balanceWithdrawTotalCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalCountBetween(Integer value1, Integer value2) {
            addCriterion("balance_withdraw_total_count between", value1, value2, "balanceWithdrawTotalCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawTotalCountNotBetween(Integer value1, Integer value2) {
            addCriterion("balance_withdraw_total_count not between", value1, value2, "balanceWithdrawTotalCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccCountIsNull() {
            addCriterion("balance_withdraw_succ_count is null");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccCountIsNotNull() {
            addCriterion("balance_withdraw_succ_count is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccCountEqualTo(Integer value) {
            addCriterion("balance_withdraw_succ_count =", value, "balanceWithdrawSuccCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccCountNotEqualTo(Integer value) {
            addCriterion("balance_withdraw_succ_count <>", value, "balanceWithdrawSuccCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccCountGreaterThan(Integer value) {
            addCriterion("balance_withdraw_succ_count >", value, "balanceWithdrawSuccCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("balance_withdraw_succ_count >=", value, "balanceWithdrawSuccCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccCountLessThan(Integer value) {
            addCriterion("balance_withdraw_succ_count <", value, "balanceWithdrawSuccCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccCountLessThanOrEqualTo(Integer value) {
            addCriterion("balance_withdraw_succ_count <=", value, "balanceWithdrawSuccCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccCountIn(List<Integer> values) {
            addCriterion("balance_withdraw_succ_count in", values, "balanceWithdrawSuccCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccCountNotIn(List<Integer> values) {
            addCriterion("balance_withdraw_succ_count not in", values, "balanceWithdrawSuccCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccCountBetween(Integer value1, Integer value2) {
            addCriterion("balance_withdraw_succ_count between", value1, value2, "balanceWithdrawSuccCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawSuccCountNotBetween(Integer value1, Integer value2) {
            addCriterion("balance_withdraw_succ_count not between", value1, value2, "balanceWithdrawSuccCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailCountIsNull() {
            addCriterion("balance_withdraw_fail_count is null");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailCountIsNotNull() {
            addCriterion("balance_withdraw_fail_count is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailCountEqualTo(Integer value) {
            addCriterion("balance_withdraw_fail_count =", value, "balanceWithdrawFailCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailCountNotEqualTo(Integer value) {
            addCriterion("balance_withdraw_fail_count <>", value, "balanceWithdrawFailCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailCountGreaterThan(Integer value) {
            addCriterion("balance_withdraw_fail_count >", value, "balanceWithdrawFailCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("balance_withdraw_fail_count >=", value, "balanceWithdrawFailCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailCountLessThan(Integer value) {
            addCriterion("balance_withdraw_fail_count <", value, "balanceWithdrawFailCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailCountLessThanOrEqualTo(Integer value) {
            addCriterion("balance_withdraw_fail_count <=", value, "balanceWithdrawFailCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailCountIn(List<Integer> values) {
            addCriterion("balance_withdraw_fail_count in", values, "balanceWithdrawFailCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailCountNotIn(List<Integer> values) {
            addCriterion("balance_withdraw_fail_count not in", values, "balanceWithdrawFailCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailCountBetween(Integer value1, Integer value2) {
            addCriterion("balance_withdraw_fail_count between", value1, value2, "balanceWithdrawFailCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawFailCountNotBetween(Integer value1, Integer value2) {
            addCriterion("balance_withdraw_fail_count not between", value1, value2, "balanceWithdrawFailCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessCountIsNull() {
            addCriterion("balance_withdraw_process_count is null");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessCountIsNotNull() {
            addCriterion("balance_withdraw_process_count is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessCountEqualTo(Integer value) {
            addCriterion("balance_withdraw_process_count =", value, "balanceWithdrawProcessCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessCountNotEqualTo(Integer value) {
            addCriterion("balance_withdraw_process_count <>", value, "balanceWithdrawProcessCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessCountGreaterThan(Integer value) {
            addCriterion("balance_withdraw_process_count >", value, "balanceWithdrawProcessCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("balance_withdraw_process_count >=", value, "balanceWithdrawProcessCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessCountLessThan(Integer value) {
            addCriterion("balance_withdraw_process_count <", value, "balanceWithdrawProcessCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessCountLessThanOrEqualTo(Integer value) {
            addCriterion("balance_withdraw_process_count <=", value, "balanceWithdrawProcessCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessCountIn(List<Integer> values) {
            addCriterion("balance_withdraw_process_count in", values, "balanceWithdrawProcessCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessCountNotIn(List<Integer> values) {
            addCriterion("balance_withdraw_process_count not in", values, "balanceWithdrawProcessCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessCountBetween(Integer value1, Integer value2) {
            addCriterion("balance_withdraw_process_count between", value1, value2, "balanceWithdrawProcessCount");
            return (Criteria) this;
        }

        public Criteria andBalanceWithdrawProcessCountNotBetween(Integer value1, Integer value2) {
            addCriterion("balance_withdraw_process_count not between", value1, value2, "balanceWithdrawProcessCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalAmtIsNull() {
            addCriterion("bonus_withdraw_total_amt is null");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalAmtIsNotNull() {
            addCriterion("bonus_withdraw_total_amt is not null");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalAmtEqualTo(Double value) {
            addCriterion("bonus_withdraw_total_amt =", value, "bonusWithdrawTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalAmtNotEqualTo(Double value) {
            addCriterion("bonus_withdraw_total_amt <>", value, "bonusWithdrawTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalAmtGreaterThan(Double value) {
            addCriterion("bonus_withdraw_total_amt >", value, "bonusWithdrawTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("bonus_withdraw_total_amt >=", value, "bonusWithdrawTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalAmtLessThan(Double value) {
            addCriterion("bonus_withdraw_total_amt <", value, "bonusWithdrawTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalAmtLessThanOrEqualTo(Double value) {
            addCriterion("bonus_withdraw_total_amt <=", value, "bonusWithdrawTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalAmtIn(List<Double> values) {
            addCriterion("bonus_withdraw_total_amt in", values, "bonusWithdrawTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalAmtNotIn(List<Double> values) {
            addCriterion("bonus_withdraw_total_amt not in", values, "bonusWithdrawTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalAmtBetween(Double value1, Double value2) {
            addCriterion("bonus_withdraw_total_amt between", value1, value2, "bonusWithdrawTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalAmtNotBetween(Double value1, Double value2) {
            addCriterion("bonus_withdraw_total_amt not between", value1, value2, "bonusWithdrawTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccAmtIsNull() {
            addCriterion("bonus_withdraw_succ_amt is null");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccAmtIsNotNull() {
            addCriterion("bonus_withdraw_succ_amt is not null");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccAmtEqualTo(Double value) {
            addCriterion("bonus_withdraw_succ_amt =", value, "bonusWithdrawSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccAmtNotEqualTo(Double value) {
            addCriterion("bonus_withdraw_succ_amt <>", value, "bonusWithdrawSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccAmtGreaterThan(Double value) {
            addCriterion("bonus_withdraw_succ_amt >", value, "bonusWithdrawSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("bonus_withdraw_succ_amt >=", value, "bonusWithdrawSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccAmtLessThan(Double value) {
            addCriterion("bonus_withdraw_succ_amt <", value, "bonusWithdrawSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccAmtLessThanOrEqualTo(Double value) {
            addCriterion("bonus_withdraw_succ_amt <=", value, "bonusWithdrawSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccAmtIn(List<Double> values) {
            addCriterion("bonus_withdraw_succ_amt in", values, "bonusWithdrawSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccAmtNotIn(List<Double> values) {
            addCriterion("bonus_withdraw_succ_amt not in", values, "bonusWithdrawSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccAmtBetween(Double value1, Double value2) {
            addCriterion("bonus_withdraw_succ_amt between", value1, value2, "bonusWithdrawSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccAmtNotBetween(Double value1, Double value2) {
            addCriterion("bonus_withdraw_succ_amt not between", value1, value2, "bonusWithdrawSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailAmtIsNull() {
            addCriterion("bonus_withdraw_fail_amt is null");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailAmtIsNotNull() {
            addCriterion("bonus_withdraw_fail_amt is not null");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailAmtEqualTo(Double value) {
            addCriterion("bonus_withdraw_fail_amt =", value, "bonusWithdrawFailAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailAmtNotEqualTo(Double value) {
            addCriterion("bonus_withdraw_fail_amt <>", value, "bonusWithdrawFailAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailAmtGreaterThan(Double value) {
            addCriterion("bonus_withdraw_fail_amt >", value, "bonusWithdrawFailAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("bonus_withdraw_fail_amt >=", value, "bonusWithdrawFailAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailAmtLessThan(Double value) {
            addCriterion("bonus_withdraw_fail_amt <", value, "bonusWithdrawFailAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailAmtLessThanOrEqualTo(Double value) {
            addCriterion("bonus_withdraw_fail_amt <=", value, "bonusWithdrawFailAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailAmtIn(List<Double> values) {
            addCriterion("bonus_withdraw_fail_amt in", values, "bonusWithdrawFailAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailAmtNotIn(List<Double> values) {
            addCriterion("bonus_withdraw_fail_amt not in", values, "bonusWithdrawFailAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailAmtBetween(Double value1, Double value2) {
            addCriterion("bonus_withdraw_fail_amt between", value1, value2, "bonusWithdrawFailAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailAmtNotBetween(Double value1, Double value2) {
            addCriterion("bonus_withdraw_fail_amt not between", value1, value2, "bonusWithdrawFailAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessAmtIsNull() {
            addCriterion("bonus_withdraw_process_amt is null");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessAmtIsNotNull() {
            addCriterion("bonus_withdraw_process_amt is not null");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessAmtEqualTo(Double value) {
            addCriterion("bonus_withdraw_process_amt =", value, "bonusWithdrawProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessAmtNotEqualTo(Double value) {
            addCriterion("bonus_withdraw_process_amt <>", value, "bonusWithdrawProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessAmtGreaterThan(Double value) {
            addCriterion("bonus_withdraw_process_amt >", value, "bonusWithdrawProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("bonus_withdraw_process_amt >=", value, "bonusWithdrawProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessAmtLessThan(Double value) {
            addCriterion("bonus_withdraw_process_amt <", value, "bonusWithdrawProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessAmtLessThanOrEqualTo(Double value) {
            addCriterion("bonus_withdraw_process_amt <=", value, "bonusWithdrawProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessAmtIn(List<Double> values) {
            addCriterion("bonus_withdraw_process_amt in", values, "bonusWithdrawProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessAmtNotIn(List<Double> values) {
            addCriterion("bonus_withdraw_process_amt not in", values, "bonusWithdrawProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessAmtBetween(Double value1, Double value2) {
            addCriterion("bonus_withdraw_process_amt between", value1, value2, "bonusWithdrawProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessAmtNotBetween(Double value1, Double value2) {
            addCriterion("bonus_withdraw_process_amt not between", value1, value2, "bonusWithdrawProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalCountIsNull() {
            addCriterion("bonus_withdraw_total_count is null");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalCountIsNotNull() {
            addCriterion("bonus_withdraw_total_count is not null");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalCountEqualTo(Integer value) {
            addCriterion("bonus_withdraw_total_count =", value, "bonusWithdrawTotalCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalCountNotEqualTo(Integer value) {
            addCriterion("bonus_withdraw_total_count <>", value, "bonusWithdrawTotalCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalCountGreaterThan(Integer value) {
            addCriterion("bonus_withdraw_total_count >", value, "bonusWithdrawTotalCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("bonus_withdraw_total_count >=", value, "bonusWithdrawTotalCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalCountLessThan(Integer value) {
            addCriterion("bonus_withdraw_total_count <", value, "bonusWithdrawTotalCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalCountLessThanOrEqualTo(Integer value) {
            addCriterion("bonus_withdraw_total_count <=", value, "bonusWithdrawTotalCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalCountIn(List<Integer> values) {
            addCriterion("bonus_withdraw_total_count in", values, "bonusWithdrawTotalCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalCountNotIn(List<Integer> values) {
            addCriterion("bonus_withdraw_total_count not in", values, "bonusWithdrawTotalCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalCountBetween(Integer value1, Integer value2) {
            addCriterion("bonus_withdraw_total_count between", value1, value2, "bonusWithdrawTotalCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawTotalCountNotBetween(Integer value1, Integer value2) {
            addCriterion("bonus_withdraw_total_count not between", value1, value2, "bonusWithdrawTotalCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccCountIsNull() {
            addCriterion("bonus_withdraw_succ_count is null");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccCountIsNotNull() {
            addCriterion("bonus_withdraw_succ_count is not null");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccCountEqualTo(Integer value) {
            addCriterion("bonus_withdraw_succ_count =", value, "bonusWithdrawSuccCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccCountNotEqualTo(Integer value) {
            addCriterion("bonus_withdraw_succ_count <>", value, "bonusWithdrawSuccCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccCountGreaterThan(Integer value) {
            addCriterion("bonus_withdraw_succ_count >", value, "bonusWithdrawSuccCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("bonus_withdraw_succ_count >=", value, "bonusWithdrawSuccCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccCountLessThan(Integer value) {
            addCriterion("bonus_withdraw_succ_count <", value, "bonusWithdrawSuccCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccCountLessThanOrEqualTo(Integer value) {
            addCriterion("bonus_withdraw_succ_count <=", value, "bonusWithdrawSuccCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccCountIn(List<Integer> values) {
            addCriterion("bonus_withdraw_succ_count in", values, "bonusWithdrawSuccCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccCountNotIn(List<Integer> values) {
            addCriterion("bonus_withdraw_succ_count not in", values, "bonusWithdrawSuccCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccCountBetween(Integer value1, Integer value2) {
            addCriterion("bonus_withdraw_succ_count between", value1, value2, "bonusWithdrawSuccCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawSuccCountNotBetween(Integer value1, Integer value2) {
            addCriterion("bonus_withdraw_succ_count not between", value1, value2, "bonusWithdrawSuccCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailCountIsNull() {
            addCriterion("bonus_withdraw_fail_count is null");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailCountIsNotNull() {
            addCriterion("bonus_withdraw_fail_count is not null");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailCountEqualTo(Integer value) {
            addCriterion("bonus_withdraw_fail_count =", value, "bonusWithdrawFailCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailCountNotEqualTo(Integer value) {
            addCriterion("bonus_withdraw_fail_count <>", value, "bonusWithdrawFailCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailCountGreaterThan(Integer value) {
            addCriterion("bonus_withdraw_fail_count >", value, "bonusWithdrawFailCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("bonus_withdraw_fail_count >=", value, "bonusWithdrawFailCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailCountLessThan(Integer value) {
            addCriterion("bonus_withdraw_fail_count <", value, "bonusWithdrawFailCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailCountLessThanOrEqualTo(Integer value) {
            addCriterion("bonus_withdraw_fail_count <=", value, "bonusWithdrawFailCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailCountIn(List<Integer> values) {
            addCriterion("bonus_withdraw_fail_count in", values, "bonusWithdrawFailCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailCountNotIn(List<Integer> values) {
            addCriterion("bonus_withdraw_fail_count not in", values, "bonusWithdrawFailCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailCountBetween(Integer value1, Integer value2) {
            addCriterion("bonus_withdraw_fail_count between", value1, value2, "bonusWithdrawFailCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawFailCountNotBetween(Integer value1, Integer value2) {
            addCriterion("bonus_withdraw_fail_count not between", value1, value2, "bonusWithdrawFailCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessCountIsNull() {
            addCriterion("bonus_withdraw_process_count is null");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessCountIsNotNull() {
            addCriterion("bonus_withdraw_process_count is not null");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessCountEqualTo(Integer value) {
            addCriterion("bonus_withdraw_process_count =", value, "bonusWithdrawProcessCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessCountNotEqualTo(Integer value) {
            addCriterion("bonus_withdraw_process_count <>", value, "bonusWithdrawProcessCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessCountGreaterThan(Integer value) {
            addCriterion("bonus_withdraw_process_count >", value, "bonusWithdrawProcessCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("bonus_withdraw_process_count >=", value, "bonusWithdrawProcessCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessCountLessThan(Integer value) {
            addCriterion("bonus_withdraw_process_count <", value, "bonusWithdrawProcessCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessCountLessThanOrEqualTo(Integer value) {
            addCriterion("bonus_withdraw_process_count <=", value, "bonusWithdrawProcessCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessCountIn(List<Integer> values) {
            addCriterion("bonus_withdraw_process_count in", values, "bonusWithdrawProcessCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessCountNotIn(List<Integer> values) {
            addCriterion("bonus_withdraw_process_count not in", values, "bonusWithdrawProcessCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessCountBetween(Integer value1, Integer value2) {
            addCriterion("bonus_withdraw_process_count between", value1, value2, "bonusWithdrawProcessCount");
            return (Criteria) this;
        }

        public Criteria andBonusWithdrawProcessCountNotBetween(Integer value1, Integer value2) {
            addCriterion("bonus_withdraw_process_count not between", value1, value2, "bonusWithdrawProcessCount");
            return (Criteria) this;
        }

        public Criteria andSysRevenueAmtIsNull() {
            addCriterion("sys_revenue_amt is null");
            return (Criteria) this;
        }

        public Criteria andSysRevenueAmtIsNotNull() {
            addCriterion("sys_revenue_amt is not null");
            return (Criteria) this;
        }

        public Criteria andSysRevenueAmtEqualTo(Double value) {
            addCriterion("sys_revenue_amt =", value, "sysRevenueAmt");
            return (Criteria) this;
        }

        public Criteria andSysRevenueAmtNotEqualTo(Double value) {
            addCriterion("sys_revenue_amt <>", value, "sysRevenueAmt");
            return (Criteria) this;
        }

        public Criteria andSysRevenueAmtGreaterThan(Double value) {
            addCriterion("sys_revenue_amt >", value, "sysRevenueAmt");
            return (Criteria) this;
        }

        public Criteria andSysRevenueAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("sys_revenue_amt >=", value, "sysRevenueAmt");
            return (Criteria) this;
        }

        public Criteria andSysRevenueAmtLessThan(Double value) {
            addCriterion("sys_revenue_amt <", value, "sysRevenueAmt");
            return (Criteria) this;
        }

        public Criteria andSysRevenueAmtLessThanOrEqualTo(Double value) {
            addCriterion("sys_revenue_amt <=", value, "sysRevenueAmt");
            return (Criteria) this;
        }

        public Criteria andSysRevenueAmtIn(List<Double> values) {
            addCriterion("sys_revenue_amt in", values, "sysRevenueAmt");
            return (Criteria) this;
        }

        public Criteria andSysRevenueAmtNotIn(List<Double> values) {
            addCriterion("sys_revenue_amt not in", values, "sysRevenueAmt");
            return (Criteria) this;
        }

        public Criteria andSysRevenueAmtBetween(Double value1, Double value2) {
            addCriterion("sys_revenue_amt between", value1, value2, "sysRevenueAmt");
            return (Criteria) this;
        }

        public Criteria andSysRevenueAmtNotBetween(Double value1, Double value2) {
            addCriterion("sys_revenue_amt not between", value1, value2, "sysRevenueAmt");
            return (Criteria) this;
        }

        public Criteria andSysRepeatRevenueAmtIsNull() {
            addCriterion("sys_repeat_revenue_amt is null");
            return (Criteria) this;
        }

        public Criteria andSysRepeatRevenueAmtIsNotNull() {
            addCriterion("sys_repeat_revenue_amt is not null");
            return (Criteria) this;
        }

        public Criteria andSysRepeatRevenueAmtEqualTo(Double value) {
            addCriterion("sys_repeat_revenue_amt =", value, "sysRepeatRevenueAmt");
            return (Criteria) this;
        }

        public Criteria andSysRepeatRevenueAmtNotEqualTo(Double value) {
            addCriterion("sys_repeat_revenue_amt <>", value, "sysRepeatRevenueAmt");
            return (Criteria) this;
        }

        public Criteria andSysRepeatRevenueAmtGreaterThan(Double value) {
            addCriterion("sys_repeat_revenue_amt >", value, "sysRepeatRevenueAmt");
            return (Criteria) this;
        }

        public Criteria andSysRepeatRevenueAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("sys_repeat_revenue_amt >=", value, "sysRepeatRevenueAmt");
            return (Criteria) this;
        }

        public Criteria andSysRepeatRevenueAmtLessThan(Double value) {
            addCriterion("sys_repeat_revenue_amt <", value, "sysRepeatRevenueAmt");
            return (Criteria) this;
        }

        public Criteria andSysRepeatRevenueAmtLessThanOrEqualTo(Double value) {
            addCriterion("sys_repeat_revenue_amt <=", value, "sysRepeatRevenueAmt");
            return (Criteria) this;
        }

        public Criteria andSysRepeatRevenueAmtIn(List<Double> values) {
            addCriterion("sys_repeat_revenue_amt in", values, "sysRepeatRevenueAmt");
            return (Criteria) this;
        }

        public Criteria andSysRepeatRevenueAmtNotIn(List<Double> values) {
            addCriterion("sys_repeat_revenue_amt not in", values, "sysRepeatRevenueAmt");
            return (Criteria) this;
        }

        public Criteria andSysRepeatRevenueAmtBetween(Double value1, Double value2) {
            addCriterion("sys_repeat_revenue_amt between", value1, value2, "sysRepeatRevenueAmt");
            return (Criteria) this;
        }

        public Criteria andSysRepeatRevenueAmtNotBetween(Double value1, Double value2) {
            addCriterion("sys_repeat_revenue_amt not between", value1, value2, "sysRepeatRevenueAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalAmtIsNull() {
            addCriterion("baofoo_2_hfbank_total_amt is null");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalAmtIsNotNull() {
            addCriterion("baofoo_2_hfbank_total_amt is not null");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalAmtEqualTo(Double value) {
            addCriterion("baofoo_2_hfbank_total_amt =", value, "baofoo2HfbankTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalAmtNotEqualTo(Double value) {
            addCriterion("baofoo_2_hfbank_total_amt <>", value, "baofoo2HfbankTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalAmtGreaterThan(Double value) {
            addCriterion("baofoo_2_hfbank_total_amt >", value, "baofoo2HfbankTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("baofoo_2_hfbank_total_amt >=", value, "baofoo2HfbankTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalAmtLessThan(Double value) {
            addCriterion("baofoo_2_hfbank_total_amt <", value, "baofoo2HfbankTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalAmtLessThanOrEqualTo(Double value) {
            addCriterion("baofoo_2_hfbank_total_amt <=", value, "baofoo2HfbankTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalAmtIn(List<Double> values) {
            addCriterion("baofoo_2_hfbank_total_amt in", values, "baofoo2HfbankTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalAmtNotIn(List<Double> values) {
            addCriterion("baofoo_2_hfbank_total_amt not in", values, "baofoo2HfbankTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalAmtBetween(Double value1, Double value2) {
            addCriterion("baofoo_2_hfbank_total_amt between", value1, value2, "baofoo2HfbankTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalAmtNotBetween(Double value1, Double value2) {
            addCriterion("baofoo_2_hfbank_total_amt not between", value1, value2, "baofoo2HfbankTotalAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccAmtIsNull() {
            addCriterion("baofoo_2_hfbank_succ_amt is null");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccAmtIsNotNull() {
            addCriterion("baofoo_2_hfbank_succ_amt is not null");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccAmtEqualTo(Double value) {
            addCriterion("baofoo_2_hfbank_succ_amt =", value, "baofoo2HfbankSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccAmtNotEqualTo(Double value) {
            addCriterion("baofoo_2_hfbank_succ_amt <>", value, "baofoo2HfbankSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccAmtGreaterThan(Double value) {
            addCriterion("baofoo_2_hfbank_succ_amt >", value, "baofoo2HfbankSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("baofoo_2_hfbank_succ_amt >=", value, "baofoo2HfbankSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccAmtLessThan(Double value) {
            addCriterion("baofoo_2_hfbank_succ_amt <", value, "baofoo2HfbankSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccAmtLessThanOrEqualTo(Double value) {
            addCriterion("baofoo_2_hfbank_succ_amt <=", value, "baofoo2HfbankSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccAmtIn(List<Double> values) {
            addCriterion("baofoo_2_hfbank_succ_amt in", values, "baofoo2HfbankSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccAmtNotIn(List<Double> values) {
            addCriterion("baofoo_2_hfbank_succ_amt not in", values, "baofoo2HfbankSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccAmtBetween(Double value1, Double value2) {
            addCriterion("baofoo_2_hfbank_succ_amt between", value1, value2, "baofoo2HfbankSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccAmtNotBetween(Double value1, Double value2) {
            addCriterion("baofoo_2_hfbank_succ_amt not between", value1, value2, "baofoo2HfbankSuccAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailAmtIsNull() {
            addCriterion("baofoo_2_hfbank_fail_amt is null");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailAmtIsNotNull() {
            addCriterion("baofoo_2_hfbank_fail_amt is not null");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailAmtEqualTo(Double value) {
            addCriterion("baofoo_2_hfbank_fail_amt =", value, "baofoo2HfbankFailAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailAmtNotEqualTo(Double value) {
            addCriterion("baofoo_2_hfbank_fail_amt <>", value, "baofoo2HfbankFailAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailAmtGreaterThan(Double value) {
            addCriterion("baofoo_2_hfbank_fail_amt >", value, "baofoo2HfbankFailAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("baofoo_2_hfbank_fail_amt >=", value, "baofoo2HfbankFailAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailAmtLessThan(Double value) {
            addCriterion("baofoo_2_hfbank_fail_amt <", value, "baofoo2HfbankFailAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailAmtLessThanOrEqualTo(Double value) {
            addCriterion("baofoo_2_hfbank_fail_amt <=", value, "baofoo2HfbankFailAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailAmtIn(List<Double> values) {
            addCriterion("baofoo_2_hfbank_fail_amt in", values, "baofoo2HfbankFailAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailAmtNotIn(List<Double> values) {
            addCriterion("baofoo_2_hfbank_fail_amt not in", values, "baofoo2HfbankFailAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailAmtBetween(Double value1, Double value2) {
            addCriterion("baofoo_2_hfbank_fail_amt between", value1, value2, "baofoo2HfbankFailAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailAmtNotBetween(Double value1, Double value2) {
            addCriterion("baofoo_2_hfbank_fail_amt not between", value1, value2, "baofoo2HfbankFailAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessAmtIsNull() {
            addCriterion("baofoo_2_hfbank_process_amt is null");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessAmtIsNotNull() {
            addCriterion("baofoo_2_hfbank_process_amt is not null");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessAmtEqualTo(Double value) {
            addCriterion("baofoo_2_hfbank_process_amt =", value, "baofoo2HfbankProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessAmtNotEqualTo(Double value) {
            addCriterion("baofoo_2_hfbank_process_amt <>", value, "baofoo2HfbankProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessAmtGreaterThan(Double value) {
            addCriterion("baofoo_2_hfbank_process_amt >", value, "baofoo2HfbankProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("baofoo_2_hfbank_process_amt >=", value, "baofoo2HfbankProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessAmtLessThan(Double value) {
            addCriterion("baofoo_2_hfbank_process_amt <", value, "baofoo2HfbankProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessAmtLessThanOrEqualTo(Double value) {
            addCriterion("baofoo_2_hfbank_process_amt <=", value, "baofoo2HfbankProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessAmtIn(List<Double> values) {
            addCriterion("baofoo_2_hfbank_process_amt in", values, "baofoo2HfbankProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessAmtNotIn(List<Double> values) {
            addCriterion("baofoo_2_hfbank_process_amt not in", values, "baofoo2HfbankProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessAmtBetween(Double value1, Double value2) {
            addCriterion("baofoo_2_hfbank_process_amt between", value1, value2, "baofoo2HfbankProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessAmtNotBetween(Double value1, Double value2) {
            addCriterion("baofoo_2_hfbank_process_amt not between", value1, value2, "baofoo2HfbankProcessAmt");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalCountIsNull() {
            addCriterion("baofoo_2_hfbank_total_count is null");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalCountIsNotNull() {
            addCriterion("baofoo_2_hfbank_total_count is not null");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalCountEqualTo(Integer value) {
            addCriterion("baofoo_2_hfbank_total_count =", value, "baofoo2HfbankTotalCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalCountNotEqualTo(Integer value) {
            addCriterion("baofoo_2_hfbank_total_count <>", value, "baofoo2HfbankTotalCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalCountGreaterThan(Integer value) {
            addCriterion("baofoo_2_hfbank_total_count >", value, "baofoo2HfbankTotalCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("baofoo_2_hfbank_total_count >=", value, "baofoo2HfbankTotalCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalCountLessThan(Integer value) {
            addCriterion("baofoo_2_hfbank_total_count <", value, "baofoo2HfbankTotalCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalCountLessThanOrEqualTo(Integer value) {
            addCriterion("baofoo_2_hfbank_total_count <=", value, "baofoo2HfbankTotalCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalCountIn(List<Integer> values) {
            addCriterion("baofoo_2_hfbank_total_count in", values, "baofoo2HfbankTotalCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalCountNotIn(List<Integer> values) {
            addCriterion("baofoo_2_hfbank_total_count not in", values, "baofoo2HfbankTotalCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalCountBetween(Integer value1, Integer value2) {
            addCriterion("baofoo_2_hfbank_total_count between", value1, value2, "baofoo2HfbankTotalCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankTotalCountNotBetween(Integer value1, Integer value2) {
            addCriterion("baofoo_2_hfbank_total_count not between", value1, value2, "baofoo2HfbankTotalCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccCountIsNull() {
            addCriterion("baofoo_2_hfbank_succ_count is null");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccCountIsNotNull() {
            addCriterion("baofoo_2_hfbank_succ_count is not null");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccCountEqualTo(Integer value) {
            addCriterion("baofoo_2_hfbank_succ_count =", value, "baofoo2HfbankSuccCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccCountNotEqualTo(Integer value) {
            addCriterion("baofoo_2_hfbank_succ_count <>", value, "baofoo2HfbankSuccCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccCountGreaterThan(Integer value) {
            addCriterion("baofoo_2_hfbank_succ_count >", value, "baofoo2HfbankSuccCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("baofoo_2_hfbank_succ_count >=", value, "baofoo2HfbankSuccCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccCountLessThan(Integer value) {
            addCriterion("baofoo_2_hfbank_succ_count <", value, "baofoo2HfbankSuccCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccCountLessThanOrEqualTo(Integer value) {
            addCriterion("baofoo_2_hfbank_succ_count <=", value, "baofoo2HfbankSuccCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccCountIn(List<Integer> values) {
            addCriterion("baofoo_2_hfbank_succ_count in", values, "baofoo2HfbankSuccCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccCountNotIn(List<Integer> values) {
            addCriterion("baofoo_2_hfbank_succ_count not in", values, "baofoo2HfbankSuccCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccCountBetween(Integer value1, Integer value2) {
            addCriterion("baofoo_2_hfbank_succ_count between", value1, value2, "baofoo2HfbankSuccCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankSuccCountNotBetween(Integer value1, Integer value2) {
            addCriterion("baofoo_2_hfbank_succ_count not between", value1, value2, "baofoo2HfbankSuccCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailCountIsNull() {
            addCriterion("baofoo_2_hfbank_fail_count is null");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailCountIsNotNull() {
            addCriterion("baofoo_2_hfbank_fail_count is not null");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailCountEqualTo(Integer value) {
            addCriterion("baofoo_2_hfbank_fail_count =", value, "baofoo2HfbankFailCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailCountNotEqualTo(Integer value) {
            addCriterion("baofoo_2_hfbank_fail_count <>", value, "baofoo2HfbankFailCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailCountGreaterThan(Integer value) {
            addCriterion("baofoo_2_hfbank_fail_count >", value, "baofoo2HfbankFailCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("baofoo_2_hfbank_fail_count >=", value, "baofoo2HfbankFailCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailCountLessThan(Integer value) {
            addCriterion("baofoo_2_hfbank_fail_count <", value, "baofoo2HfbankFailCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailCountLessThanOrEqualTo(Integer value) {
            addCriterion("baofoo_2_hfbank_fail_count <=", value, "baofoo2HfbankFailCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailCountIn(List<Integer> values) {
            addCriterion("baofoo_2_hfbank_fail_count in", values, "baofoo2HfbankFailCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailCountNotIn(List<Integer> values) {
            addCriterion("baofoo_2_hfbank_fail_count not in", values, "baofoo2HfbankFailCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailCountBetween(Integer value1, Integer value2) {
            addCriterion("baofoo_2_hfbank_fail_count between", value1, value2, "baofoo2HfbankFailCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankFailCountNotBetween(Integer value1, Integer value2) {
            addCriterion("baofoo_2_hfbank_fail_count not between", value1, value2, "baofoo2HfbankFailCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessCountIsNull() {
            addCriterion("baofoo_2_hfbank_process_count is null");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessCountIsNotNull() {
            addCriterion("baofoo_2_hfbank_process_count is not null");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessCountEqualTo(Integer value) {
            addCriterion("baofoo_2_hfbank_process_count =", value, "baofoo2HfbankProcessCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessCountNotEqualTo(Integer value) {
            addCriterion("baofoo_2_hfbank_process_count <>", value, "baofoo2HfbankProcessCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessCountGreaterThan(Integer value) {
            addCriterion("baofoo_2_hfbank_process_count >", value, "baofoo2HfbankProcessCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("baofoo_2_hfbank_process_count >=", value, "baofoo2HfbankProcessCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessCountLessThan(Integer value) {
            addCriterion("baofoo_2_hfbank_process_count <", value, "baofoo2HfbankProcessCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessCountLessThanOrEqualTo(Integer value) {
            addCriterion("baofoo_2_hfbank_process_count <=", value, "baofoo2HfbankProcessCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessCountIn(List<Integer> values) {
            addCriterion("baofoo_2_hfbank_process_count in", values, "baofoo2HfbankProcessCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessCountNotIn(List<Integer> values) {
            addCriterion("baofoo_2_hfbank_process_count not in", values, "baofoo2HfbankProcessCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessCountBetween(Integer value1, Integer value2) {
            addCriterion("baofoo_2_hfbank_process_count between", value1, value2, "baofoo2HfbankProcessCount");
            return (Criteria) this;
        }

        public Criteria andBaofoo2HfbankProcessCountNotBetween(Integer value1, Integer value2) {
            addCriterion("baofoo_2_hfbank_process_count not between", value1, value2, "baofoo2HfbankProcessCount");
            return (Criteria) this;
        }

        public Criteria andPreDepositionBackAmtIsNull() {
            addCriterion("pre_deposition_back_amt is null");
            return (Criteria) this;
        }

        public Criteria andPreDepositionBackAmtIsNotNull() {
            addCriterion("pre_deposition_back_amt is not null");
            return (Criteria) this;
        }

        public Criteria andPreDepositionBackAmtEqualTo(Double value) {
            addCriterion("pre_deposition_back_amt =", value, "preDepositionBackAmt");
            return (Criteria) this;
        }

        public Criteria andPreDepositionBackAmtNotEqualTo(Double value) {
            addCriterion("pre_deposition_back_amt <>", value, "preDepositionBackAmt");
            return (Criteria) this;
        }

        public Criteria andPreDepositionBackAmtGreaterThan(Double value) {
            addCriterion("pre_deposition_back_amt >", value, "preDepositionBackAmt");
            return (Criteria) this;
        }

        public Criteria andPreDepositionBackAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("pre_deposition_back_amt >=", value, "preDepositionBackAmt");
            return (Criteria) this;
        }

        public Criteria andPreDepositionBackAmtLessThan(Double value) {
            addCriterion("pre_deposition_back_amt <", value, "preDepositionBackAmt");
            return (Criteria) this;
        }

        public Criteria andPreDepositionBackAmtLessThanOrEqualTo(Double value) {
            addCriterion("pre_deposition_back_amt <=", value, "preDepositionBackAmt");
            return (Criteria) this;
        }

        public Criteria andPreDepositionBackAmtIn(List<Double> values) {
            addCriterion("pre_deposition_back_amt in", values, "preDepositionBackAmt");
            return (Criteria) this;
        }

        public Criteria andPreDepositionBackAmtNotIn(List<Double> values) {
            addCriterion("pre_deposition_back_amt not in", values, "preDepositionBackAmt");
            return (Criteria) this;
        }

        public Criteria andPreDepositionBackAmtBetween(Double value1, Double value2) {
            addCriterion("pre_deposition_back_amt between", value1, value2, "preDepositionBackAmt");
            return (Criteria) this;
        }

        public Criteria andPreDepositionBackAmtNotBetween(Double value1, Double value2) {
            addCriterion("pre_deposition_back_amt not between", value1, value2, "preDepositionBackAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalAmtIsNull() {
            addCriterion("deposition_repay_total_amt is null");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalAmtIsNotNull() {
            addCriterion("deposition_repay_total_amt is not null");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalAmtEqualTo(Double value) {
            addCriterion("deposition_repay_total_amt =", value, "depositionRepayTotalAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalAmtNotEqualTo(Double value) {
            addCriterion("deposition_repay_total_amt <>", value, "depositionRepayTotalAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalAmtGreaterThan(Double value) {
            addCriterion("deposition_repay_total_amt >", value, "depositionRepayTotalAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("deposition_repay_total_amt >=", value, "depositionRepayTotalAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalAmtLessThan(Double value) {
            addCriterion("deposition_repay_total_amt <", value, "depositionRepayTotalAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalAmtLessThanOrEqualTo(Double value) {
            addCriterion("deposition_repay_total_amt <=", value, "depositionRepayTotalAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalAmtIn(List<Double> values) {
            addCriterion("deposition_repay_total_amt in", values, "depositionRepayTotalAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalAmtNotIn(List<Double> values) {
            addCriterion("deposition_repay_total_amt not in", values, "depositionRepayTotalAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalAmtBetween(Double value1, Double value2) {
            addCriterion("deposition_repay_total_amt between", value1, value2, "depositionRepayTotalAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalAmtNotBetween(Double value1, Double value2) {
            addCriterion("deposition_repay_total_amt not between", value1, value2, "depositionRepayTotalAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccAmtIsNull() {
            addCriterion("deposition_repay_succ_amt is null");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccAmtIsNotNull() {
            addCriterion("deposition_repay_succ_amt is not null");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccAmtEqualTo(Double value) {
            addCriterion("deposition_repay_succ_amt =", value, "depositionRepaySuccAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccAmtNotEqualTo(Double value) {
            addCriterion("deposition_repay_succ_amt <>", value, "depositionRepaySuccAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccAmtGreaterThan(Double value) {
            addCriterion("deposition_repay_succ_amt >", value, "depositionRepaySuccAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("deposition_repay_succ_amt >=", value, "depositionRepaySuccAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccAmtLessThan(Double value) {
            addCriterion("deposition_repay_succ_amt <", value, "depositionRepaySuccAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccAmtLessThanOrEqualTo(Double value) {
            addCriterion("deposition_repay_succ_amt <=", value, "depositionRepaySuccAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccAmtIn(List<Double> values) {
            addCriterion("deposition_repay_succ_amt in", values, "depositionRepaySuccAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccAmtNotIn(List<Double> values) {
            addCriterion("deposition_repay_succ_amt not in", values, "depositionRepaySuccAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccAmtBetween(Double value1, Double value2) {
            addCriterion("deposition_repay_succ_amt between", value1, value2, "depositionRepaySuccAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccAmtNotBetween(Double value1, Double value2) {
            addCriterion("deposition_repay_succ_amt not between", value1, value2, "depositionRepaySuccAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailAmtIsNull() {
            addCriterion("deposition_repay_fail_amt is null");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailAmtIsNotNull() {
            addCriterion("deposition_repay_fail_amt is not null");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailAmtEqualTo(Double value) {
            addCriterion("deposition_repay_fail_amt =", value, "depositionRepayFailAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailAmtNotEqualTo(Double value) {
            addCriterion("deposition_repay_fail_amt <>", value, "depositionRepayFailAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailAmtGreaterThan(Double value) {
            addCriterion("deposition_repay_fail_amt >", value, "depositionRepayFailAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("deposition_repay_fail_amt >=", value, "depositionRepayFailAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailAmtLessThan(Double value) {
            addCriterion("deposition_repay_fail_amt <", value, "depositionRepayFailAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailAmtLessThanOrEqualTo(Double value) {
            addCriterion("deposition_repay_fail_amt <=", value, "depositionRepayFailAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailAmtIn(List<Double> values) {
            addCriterion("deposition_repay_fail_amt in", values, "depositionRepayFailAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailAmtNotIn(List<Double> values) {
            addCriterion("deposition_repay_fail_amt not in", values, "depositionRepayFailAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailAmtBetween(Double value1, Double value2) {
            addCriterion("deposition_repay_fail_amt between", value1, value2, "depositionRepayFailAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailAmtNotBetween(Double value1, Double value2) {
            addCriterion("deposition_repay_fail_amt not between", value1, value2, "depositionRepayFailAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessAmtIsNull() {
            addCriterion("deposition_repay_process_amt is null");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessAmtIsNotNull() {
            addCriterion("deposition_repay_process_amt is not null");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessAmtEqualTo(Double value) {
            addCriterion("deposition_repay_process_amt =", value, "depositionRepayProcessAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessAmtNotEqualTo(Double value) {
            addCriterion("deposition_repay_process_amt <>", value, "depositionRepayProcessAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessAmtGreaterThan(Double value) {
            addCriterion("deposition_repay_process_amt >", value, "depositionRepayProcessAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("deposition_repay_process_amt >=", value, "depositionRepayProcessAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessAmtLessThan(Double value) {
            addCriterion("deposition_repay_process_amt <", value, "depositionRepayProcessAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessAmtLessThanOrEqualTo(Double value) {
            addCriterion("deposition_repay_process_amt <=", value, "depositionRepayProcessAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessAmtIn(List<Double> values) {
            addCriterion("deposition_repay_process_amt in", values, "depositionRepayProcessAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessAmtNotIn(List<Double> values) {
            addCriterion("deposition_repay_process_amt not in", values, "depositionRepayProcessAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessAmtBetween(Double value1, Double value2) {
            addCriterion("deposition_repay_process_amt between", value1, value2, "depositionRepayProcessAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessAmtNotBetween(Double value1, Double value2) {
            addCriterion("deposition_repay_process_amt not between", value1, value2, "depositionRepayProcessAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalCountIsNull() {
            addCriterion("deposition_repay_total_count is null");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalCountIsNotNull() {
            addCriterion("deposition_repay_total_count is not null");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalCountEqualTo(Integer value) {
            addCriterion("deposition_repay_total_count =", value, "depositionRepayTotalCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalCountNotEqualTo(Integer value) {
            addCriterion("deposition_repay_total_count <>", value, "depositionRepayTotalCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalCountGreaterThan(Integer value) {
            addCriterion("deposition_repay_total_count >", value, "depositionRepayTotalCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("deposition_repay_total_count >=", value, "depositionRepayTotalCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalCountLessThan(Integer value) {
            addCriterion("deposition_repay_total_count <", value, "depositionRepayTotalCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalCountLessThanOrEqualTo(Integer value) {
            addCriterion("deposition_repay_total_count <=", value, "depositionRepayTotalCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalCountIn(List<Integer> values) {
            addCriterion("deposition_repay_total_count in", values, "depositionRepayTotalCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalCountNotIn(List<Integer> values) {
            addCriterion("deposition_repay_total_count not in", values, "depositionRepayTotalCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalCountBetween(Integer value1, Integer value2) {
            addCriterion("deposition_repay_total_count between", value1, value2, "depositionRepayTotalCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayTotalCountNotBetween(Integer value1, Integer value2) {
            addCriterion("deposition_repay_total_count not between", value1, value2, "depositionRepayTotalCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccCountIsNull() {
            addCriterion("deposition_repay_succ_count is null");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccCountIsNotNull() {
            addCriterion("deposition_repay_succ_count is not null");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccCountEqualTo(Integer value) {
            addCriterion("deposition_repay_succ_count =", value, "depositionRepaySuccCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccCountNotEqualTo(Integer value) {
            addCriterion("deposition_repay_succ_count <>", value, "depositionRepaySuccCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccCountGreaterThan(Integer value) {
            addCriterion("deposition_repay_succ_count >", value, "depositionRepaySuccCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("deposition_repay_succ_count >=", value, "depositionRepaySuccCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccCountLessThan(Integer value) {
            addCriterion("deposition_repay_succ_count <", value, "depositionRepaySuccCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccCountLessThanOrEqualTo(Integer value) {
            addCriterion("deposition_repay_succ_count <=", value, "depositionRepaySuccCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccCountIn(List<Integer> values) {
            addCriterion("deposition_repay_succ_count in", values, "depositionRepaySuccCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccCountNotIn(List<Integer> values) {
            addCriterion("deposition_repay_succ_count not in", values, "depositionRepaySuccCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccCountBetween(Integer value1, Integer value2) {
            addCriterion("deposition_repay_succ_count between", value1, value2, "depositionRepaySuccCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepaySuccCountNotBetween(Integer value1, Integer value2) {
            addCriterion("deposition_repay_succ_count not between", value1, value2, "depositionRepaySuccCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailCountIsNull() {
            addCriterion("deposition_repay_fail_count is null");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailCountIsNotNull() {
            addCriterion("deposition_repay_fail_count is not null");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailCountEqualTo(Integer value) {
            addCriterion("deposition_repay_fail_count =", value, "depositionRepayFailCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailCountNotEqualTo(Integer value) {
            addCriterion("deposition_repay_fail_count <>", value, "depositionRepayFailCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailCountGreaterThan(Integer value) {
            addCriterion("deposition_repay_fail_count >", value, "depositionRepayFailCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("deposition_repay_fail_count >=", value, "depositionRepayFailCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailCountLessThan(Integer value) {
            addCriterion("deposition_repay_fail_count <", value, "depositionRepayFailCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailCountLessThanOrEqualTo(Integer value) {
            addCriterion("deposition_repay_fail_count <=", value, "depositionRepayFailCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailCountIn(List<Integer> values) {
            addCriterion("deposition_repay_fail_count in", values, "depositionRepayFailCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailCountNotIn(List<Integer> values) {
            addCriterion("deposition_repay_fail_count not in", values, "depositionRepayFailCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailCountBetween(Integer value1, Integer value2) {
            addCriterion("deposition_repay_fail_count between", value1, value2, "depositionRepayFailCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayFailCountNotBetween(Integer value1, Integer value2) {
            addCriterion("deposition_repay_fail_count not between", value1, value2, "depositionRepayFailCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessCountIsNull() {
            addCriterion("deposition_repay_process_count is null");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessCountIsNotNull() {
            addCriterion("deposition_repay_process_count is not null");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessCountEqualTo(Integer value) {
            addCriterion("deposition_repay_process_count =", value, "depositionRepayProcessCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessCountNotEqualTo(Integer value) {
            addCriterion("deposition_repay_process_count <>", value, "depositionRepayProcessCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessCountGreaterThan(Integer value) {
            addCriterion("deposition_repay_process_count >", value, "depositionRepayProcessCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("deposition_repay_process_count >=", value, "depositionRepayProcessCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessCountLessThan(Integer value) {
            addCriterion("deposition_repay_process_count <", value, "depositionRepayProcessCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessCountLessThanOrEqualTo(Integer value) {
            addCriterion("deposition_repay_process_count <=", value, "depositionRepayProcessCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessCountIn(List<Integer> values) {
            addCriterion("deposition_repay_process_count in", values, "depositionRepayProcessCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessCountNotIn(List<Integer> values) {
            addCriterion("deposition_repay_process_count not in", values, "depositionRepayProcessCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessCountBetween(Integer value1, Integer value2) {
            addCriterion("deposition_repay_process_count between", value1, value2, "depositionRepayProcessCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayProcessCountNotBetween(Integer value1, Integer value2) {
            addCriterion("deposition_repay_process_count not between", value1, value2, "depositionRepayProcessCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatAmtIsNull() {
            addCriterion("deposition_repay_repeat_amt is null");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatAmtIsNotNull() {
            addCriterion("deposition_repay_repeat_amt is not null");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatAmtEqualTo(Double value) {
            addCriterion("deposition_repay_repeat_amt =", value, "depositionRepayRepeatAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatAmtNotEqualTo(Double value) {
            addCriterion("deposition_repay_repeat_amt <>", value, "depositionRepayRepeatAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatAmtGreaterThan(Double value) {
            addCriterion("deposition_repay_repeat_amt >", value, "depositionRepayRepeatAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("deposition_repay_repeat_amt >=", value, "depositionRepayRepeatAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatAmtLessThan(Double value) {
            addCriterion("deposition_repay_repeat_amt <", value, "depositionRepayRepeatAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatAmtLessThanOrEqualTo(Double value) {
            addCriterion("deposition_repay_repeat_amt <=", value, "depositionRepayRepeatAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatAmtIn(List<Double> values) {
            addCriterion("deposition_repay_repeat_amt in", values, "depositionRepayRepeatAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatAmtNotIn(List<Double> values) {
            addCriterion("deposition_repay_repeat_amt not in", values, "depositionRepayRepeatAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatAmtBetween(Double value1, Double value2) {
            addCriterion("deposition_repay_repeat_amt between", value1, value2, "depositionRepayRepeatAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatAmtNotBetween(Double value1, Double value2) {
            addCriterion("deposition_repay_repeat_amt not between", value1, value2, "depositionRepayRepeatAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatCountIsNull() {
            addCriterion("deposition_repay_repeat_count is null");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatCountIsNotNull() {
            addCriterion("deposition_repay_repeat_count is not null");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatCountEqualTo(Integer value) {
            addCriterion("deposition_repay_repeat_count =", value, "depositionRepayRepeatCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatCountNotEqualTo(Integer value) {
            addCriterion("deposition_repay_repeat_count <>", value, "depositionRepayRepeatCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatCountGreaterThan(Integer value) {
            addCriterion("deposition_repay_repeat_count >", value, "depositionRepayRepeatCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("deposition_repay_repeat_count >=", value, "depositionRepayRepeatCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatCountLessThan(Integer value) {
            addCriterion("deposition_repay_repeat_count <", value, "depositionRepayRepeatCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatCountLessThanOrEqualTo(Integer value) {
            addCriterion("deposition_repay_repeat_count <=", value, "depositionRepayRepeatCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatCountIn(List<Integer> values) {
            addCriterion("deposition_repay_repeat_count in", values, "depositionRepayRepeatCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatCountNotIn(List<Integer> values) {
            addCriterion("deposition_repay_repeat_count not in", values, "depositionRepayRepeatCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatCountBetween(Integer value1, Integer value2) {
            addCriterion("deposition_repay_repeat_count between", value1, value2, "depositionRepayRepeatCount");
            return (Criteria) this;
        }

        public Criteria andDepositionRepayRepeatCountNotBetween(Integer value1, Integer value2) {
            addCriterion("deposition_repay_repeat_count not between", value1, value2, "depositionRepayRepeatCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateAmtIsNull() {
            addCriterion("deposition_compensate_amt is null");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateAmtIsNotNull() {
            addCriterion("deposition_compensate_amt is not null");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateAmtEqualTo(Double value) {
            addCriterion("deposition_compensate_amt =", value, "depositionCompensateAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateAmtNotEqualTo(Double value) {
            addCriterion("deposition_compensate_amt <>", value, "depositionCompensateAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateAmtGreaterThan(Double value) {
            addCriterion("deposition_compensate_amt >", value, "depositionCompensateAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("deposition_compensate_amt >=", value, "depositionCompensateAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateAmtLessThan(Double value) {
            addCriterion("deposition_compensate_amt <", value, "depositionCompensateAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateAmtLessThanOrEqualTo(Double value) {
            addCriterion("deposition_compensate_amt <=", value, "depositionCompensateAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateAmtIn(List<Double> values) {
            addCriterion("deposition_compensate_amt in", values, "depositionCompensateAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateAmtNotIn(List<Double> values) {
            addCriterion("deposition_compensate_amt not in", values, "depositionCompensateAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateAmtBetween(Double value1, Double value2) {
            addCriterion("deposition_compensate_amt between", value1, value2, "depositionCompensateAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateAmtNotBetween(Double value1, Double value2) {
            addCriterion("deposition_compensate_amt not between", value1, value2, "depositionCompensateAmt");
            return (Criteria) this;
        }

        public Criteria andDepostionCompensateSuccAmtIsNull() {
            addCriterion("depostion_compensate_succ_amt is null");
            return (Criteria) this;
        }

        public Criteria andDepostionCompensateSuccAmtIsNotNull() {
            addCriterion("depostion_compensate_succ_amt is not null");
            return (Criteria) this;
        }

        public Criteria andDepostionCompensateSuccAmtEqualTo(Double value) {
            addCriterion("depostion_compensate_succ_amt =", value, "depostionCompensateSuccAmt");
            return (Criteria) this;
        }

        public Criteria andDepostionCompensateSuccAmtNotEqualTo(Double value) {
            addCriterion("depostion_compensate_succ_amt <>", value, "depostionCompensateSuccAmt");
            return (Criteria) this;
        }

        public Criteria andDepostionCompensateSuccAmtGreaterThan(Double value) {
            addCriterion("depostion_compensate_succ_amt >", value, "depostionCompensateSuccAmt");
            return (Criteria) this;
        }

        public Criteria andDepostionCompensateSuccAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("depostion_compensate_succ_amt >=", value, "depostionCompensateSuccAmt");
            return (Criteria) this;
        }

        public Criteria andDepostionCompensateSuccAmtLessThan(Double value) {
            addCriterion("depostion_compensate_succ_amt <", value, "depostionCompensateSuccAmt");
            return (Criteria) this;
        }

        public Criteria andDepostionCompensateSuccAmtLessThanOrEqualTo(Double value) {
            addCriterion("depostion_compensate_succ_amt <=", value, "depostionCompensateSuccAmt");
            return (Criteria) this;
        }

        public Criteria andDepostionCompensateSuccAmtIn(List<Double> values) {
            addCriterion("depostion_compensate_succ_amt in", values, "depostionCompensateSuccAmt");
            return (Criteria) this;
        }

        public Criteria andDepostionCompensateSuccAmtNotIn(List<Double> values) {
            addCriterion("depostion_compensate_succ_amt not in", values, "depostionCompensateSuccAmt");
            return (Criteria) this;
        }

        public Criteria andDepostionCompensateSuccAmtBetween(Double value1, Double value2) {
            addCriterion("depostion_compensate_succ_amt between", value1, value2, "depostionCompensateSuccAmt");
            return (Criteria) this;
        }

        public Criteria andDepostionCompensateSuccAmtNotBetween(Double value1, Double value2) {
            addCriterion("depostion_compensate_succ_amt not between", value1, value2, "depostionCompensateSuccAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatAmtIsNull() {
            addCriterion("deposition_compensate_repeat_amt is null");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatAmtIsNotNull() {
            addCriterion("deposition_compensate_repeat_amt is not null");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatAmtEqualTo(Double value) {
            addCriterion("deposition_compensate_repeat_amt =", value, "depositionCompensateRepeatAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatAmtNotEqualTo(Double value) {
            addCriterion("deposition_compensate_repeat_amt <>", value, "depositionCompensateRepeatAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatAmtGreaterThan(Double value) {
            addCriterion("deposition_compensate_repeat_amt >", value, "depositionCompensateRepeatAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("deposition_compensate_repeat_amt >=", value, "depositionCompensateRepeatAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatAmtLessThan(Double value) {
            addCriterion("deposition_compensate_repeat_amt <", value, "depositionCompensateRepeatAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatAmtLessThanOrEqualTo(Double value) {
            addCriterion("deposition_compensate_repeat_amt <=", value, "depositionCompensateRepeatAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatAmtIn(List<Double> values) {
            addCriterion("deposition_compensate_repeat_amt in", values, "depositionCompensateRepeatAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatAmtNotIn(List<Double> values) {
            addCriterion("deposition_compensate_repeat_amt not in", values, "depositionCompensateRepeatAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatAmtBetween(Double value1, Double value2) {
            addCriterion("deposition_compensate_repeat_amt between", value1, value2, "depositionCompensateRepeatAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatAmtNotBetween(Double value1, Double value2) {
            addCriterion("deposition_compensate_repeat_amt not between", value1, value2, "depositionCompensateRepeatAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailAmtIsNull() {
            addCriterion("deposition_compensate_fail_amt is null");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailAmtIsNotNull() {
            addCriterion("deposition_compensate_fail_amt is not null");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailAmtEqualTo(Double value) {
            addCriterion("deposition_compensate_fail_amt =", value, "depositionCompensateFailAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailAmtNotEqualTo(Double value) {
            addCriterion("deposition_compensate_fail_amt <>", value, "depositionCompensateFailAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailAmtGreaterThan(Double value) {
            addCriterion("deposition_compensate_fail_amt >", value, "depositionCompensateFailAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailAmtGreaterThanOrEqualTo(Double value) {
            addCriterion("deposition_compensate_fail_amt >=", value, "depositionCompensateFailAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailAmtLessThan(Double value) {
            addCriterion("deposition_compensate_fail_amt <", value, "depositionCompensateFailAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailAmtLessThanOrEqualTo(Double value) {
            addCriterion("deposition_compensate_fail_amt <=", value, "depositionCompensateFailAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailAmtIn(List<Double> values) {
            addCriterion("deposition_compensate_fail_amt in", values, "depositionCompensateFailAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailAmtNotIn(List<Double> values) {
            addCriterion("deposition_compensate_fail_amt not in", values, "depositionCompensateFailAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailAmtBetween(Double value1, Double value2) {
            addCriterion("deposition_compensate_fail_amt between", value1, value2, "depositionCompensateFailAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailAmtNotBetween(Double value1, Double value2) {
            addCriterion("deposition_compensate_fail_amt not between", value1, value2, "depositionCompensateFailAmt");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateCountIsNull() {
            addCriterion("deposition_compensate_count is null");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateCountIsNotNull() {
            addCriterion("deposition_compensate_count is not null");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateCountEqualTo(Integer value) {
            addCriterion("deposition_compensate_count =", value, "depositionCompensateCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateCountNotEqualTo(Integer value) {
            addCriterion("deposition_compensate_count <>", value, "depositionCompensateCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateCountGreaterThan(Integer value) {
            addCriterion("deposition_compensate_count >", value, "depositionCompensateCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("deposition_compensate_count >=", value, "depositionCompensateCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateCountLessThan(Integer value) {
            addCriterion("deposition_compensate_count <", value, "depositionCompensateCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateCountLessThanOrEqualTo(Integer value) {
            addCriterion("deposition_compensate_count <=", value, "depositionCompensateCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateCountIn(List<Integer> values) {
            addCriterion("deposition_compensate_count in", values, "depositionCompensateCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateCountNotIn(List<Integer> values) {
            addCriterion("deposition_compensate_count not in", values, "depositionCompensateCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateCountBetween(Integer value1, Integer value2) {
            addCriterion("deposition_compensate_count between", value1, value2, "depositionCompensateCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateCountNotBetween(Integer value1, Integer value2) {
            addCriterion("deposition_compensate_count not between", value1, value2, "depositionCompensateCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateSuccCountIsNull() {
            addCriterion("deposition_compensate_succ_count is null");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateSuccCountIsNotNull() {
            addCriterion("deposition_compensate_succ_count is not null");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateSuccCountEqualTo(Integer value) {
            addCriterion("deposition_compensate_succ_count =", value, "depositionCompensateSuccCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateSuccCountNotEqualTo(Integer value) {
            addCriterion("deposition_compensate_succ_count <>", value, "depositionCompensateSuccCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateSuccCountGreaterThan(Integer value) {
            addCriterion("deposition_compensate_succ_count >", value, "depositionCompensateSuccCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateSuccCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("deposition_compensate_succ_count >=", value, "depositionCompensateSuccCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateSuccCountLessThan(Integer value) {
            addCriterion("deposition_compensate_succ_count <", value, "depositionCompensateSuccCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateSuccCountLessThanOrEqualTo(Integer value) {
            addCriterion("deposition_compensate_succ_count <=", value, "depositionCompensateSuccCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateSuccCountIn(List<Integer> values) {
            addCriterion("deposition_compensate_succ_count in", values, "depositionCompensateSuccCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateSuccCountNotIn(List<Integer> values) {
            addCriterion("deposition_compensate_succ_count not in", values, "depositionCompensateSuccCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateSuccCountBetween(Integer value1, Integer value2) {
            addCriterion("deposition_compensate_succ_count between", value1, value2, "depositionCompensateSuccCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateSuccCountNotBetween(Integer value1, Integer value2) {
            addCriterion("deposition_compensate_succ_count not between", value1, value2, "depositionCompensateSuccCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatCountIsNull() {
            addCriterion("deposition_compensate_repeat_count is null");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatCountIsNotNull() {
            addCriterion("deposition_compensate_repeat_count is not null");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatCountEqualTo(Integer value) {
            addCriterion("deposition_compensate_repeat_count =", value, "depositionCompensateRepeatCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatCountNotEqualTo(Integer value) {
            addCriterion("deposition_compensate_repeat_count <>", value, "depositionCompensateRepeatCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatCountGreaterThan(Integer value) {
            addCriterion("deposition_compensate_repeat_count >", value, "depositionCompensateRepeatCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("deposition_compensate_repeat_count >=", value, "depositionCompensateRepeatCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatCountLessThan(Integer value) {
            addCriterion("deposition_compensate_repeat_count <", value, "depositionCompensateRepeatCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatCountLessThanOrEqualTo(Integer value) {
            addCriterion("deposition_compensate_repeat_count <=", value, "depositionCompensateRepeatCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatCountIn(List<Integer> values) {
            addCriterion("deposition_compensate_repeat_count in", values, "depositionCompensateRepeatCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatCountNotIn(List<Integer> values) {
            addCriterion("deposition_compensate_repeat_count not in", values, "depositionCompensateRepeatCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatCountBetween(Integer value1, Integer value2) {
            addCriterion("deposition_compensate_repeat_count between", value1, value2, "depositionCompensateRepeatCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateRepeatCountNotBetween(Integer value1, Integer value2) {
            addCriterion("deposition_compensate_repeat_count not between", value1, value2, "depositionCompensateRepeatCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailCountIsNull() {
            addCriterion("deposition_compensate_fail_count is null");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailCountIsNotNull() {
            addCriterion("deposition_compensate_fail_count is not null");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailCountEqualTo(Integer value) {
            addCriterion("deposition_compensate_fail_count =", value, "depositionCompensateFailCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailCountNotEqualTo(Integer value) {
            addCriterion("deposition_compensate_fail_count <>", value, "depositionCompensateFailCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailCountGreaterThan(Integer value) {
            addCriterion("deposition_compensate_fail_count >", value, "depositionCompensateFailCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("deposition_compensate_fail_count >=", value, "depositionCompensateFailCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailCountLessThan(Integer value) {
            addCriterion("deposition_compensate_fail_count <", value, "depositionCompensateFailCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailCountLessThanOrEqualTo(Integer value) {
            addCriterion("deposition_compensate_fail_count <=", value, "depositionCompensateFailCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailCountIn(List<Integer> values) {
            addCriterion("deposition_compensate_fail_count in", values, "depositionCompensateFailCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailCountNotIn(List<Integer> values) {
            addCriterion("deposition_compensate_fail_count not in", values, "depositionCompensateFailCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailCountBetween(Integer value1, Integer value2) {
            addCriterion("deposition_compensate_fail_count between", value1, value2, "depositionCompensateFailCount");
            return (Criteria) this;
        }

        public Criteria andDepositionCompensateFailCountNotBetween(Integer value1, Integer value2) {
            addCriterion("deposition_compensate_fail_count not between", value1, value2, "depositionCompensateFailCount");
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