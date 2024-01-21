package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LnRepayExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LnRepayExample() {
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

        public Criteria andLnUserIdIsNull() {
            addCriterion("ln_user_id is null");
            return (Criteria) this;
        }

        public Criteria andLnUserIdIsNotNull() {
            addCriterion("ln_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andLnUserIdEqualTo(Integer value) {
            addCriterion("ln_user_id =", value, "lnUserId");
            return (Criteria) this;
        }

        public Criteria andLnUserIdNotEqualTo(Integer value) {
            addCriterion("ln_user_id <>", value, "lnUserId");
            return (Criteria) this;
        }

        public Criteria andLnUserIdGreaterThan(Integer value) {
            addCriterion("ln_user_id >", value, "lnUserId");
            return (Criteria) this;
        }

        public Criteria andLnUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ln_user_id >=", value, "lnUserId");
            return (Criteria) this;
        }

        public Criteria andLnUserIdLessThan(Integer value) {
            addCriterion("ln_user_id <", value, "lnUserId");
            return (Criteria) this;
        }

        public Criteria andLnUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("ln_user_id <=", value, "lnUserId");
            return (Criteria) this;
        }

        public Criteria andLnUserIdIn(List<Integer> values) {
            addCriterion("ln_user_id in", values, "lnUserId");
            return (Criteria) this;
        }

        public Criteria andLnUserIdNotIn(List<Integer> values) {
            addCriterion("ln_user_id not in", values, "lnUserId");
            return (Criteria) this;
        }

        public Criteria andLnUserIdBetween(Integer value1, Integer value2) {
            addCriterion("ln_user_id between", value1, value2, "lnUserId");
            return (Criteria) this;
        }

        public Criteria andLnUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ln_user_id not between", value1, value2, "lnUserId");
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

        public Criteria andRepayPlanIdIsNull() {
            addCriterion("repay_plan_id is null");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdIsNotNull() {
            addCriterion("repay_plan_id is not null");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdEqualTo(Integer value) {
            addCriterion("repay_plan_id =", value, "repayPlanId");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdNotEqualTo(Integer value) {
            addCriterion("repay_plan_id <>", value, "repayPlanId");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdGreaterThan(Integer value) {
            addCriterion("repay_plan_id >", value, "repayPlanId");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_plan_id >=", value, "repayPlanId");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdLessThan(Integer value) {
            addCriterion("repay_plan_id <", value, "repayPlanId");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdLessThanOrEqualTo(Integer value) {
            addCriterion("repay_plan_id <=", value, "repayPlanId");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdIn(List<Integer> values) {
            addCriterion("repay_plan_id in", values, "repayPlanId");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdNotIn(List<Integer> values) {
            addCriterion("repay_plan_id not in", values, "repayPlanId");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdBetween(Integer value1, Integer value2) {
            addCriterion("repay_plan_id between", value1, value2, "repayPlanId");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_plan_id not between", value1, value2, "repayPlanId");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoIsNull() {
            addCriterion("partner_order_no is null");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoIsNotNull() {
            addCriterion("partner_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoEqualTo(String value) {
            addCriterion("partner_order_no =", value, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoNotEqualTo(String value) {
            addCriterion("partner_order_no <>", value, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoGreaterThan(String value) {
            addCriterion("partner_order_no >", value, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("partner_order_no >=", value, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoLessThan(String value) {
            addCriterion("partner_order_no <", value, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoLessThanOrEqualTo(String value) {
            addCriterion("partner_order_no <=", value, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoLike(String value) {
            addCriterion("partner_order_no like", value, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoNotLike(String value) {
            addCriterion("partner_order_no not like", value, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoIn(List<String> values) {
            addCriterion("partner_order_no in", values, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoNotIn(List<String> values) {
            addCriterion("partner_order_no not in", values, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoBetween(String value1, String value2) {
            addCriterion("partner_order_no between", value1, value2, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoNotBetween(String value1, String value2) {
            addCriterion("partner_order_no not between", value1, value2, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoIsNull() {
            addCriterion("bgw_order_no is null");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoIsNotNull() {
            addCriterion("bgw_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoEqualTo(String value) {
            addCriterion("bgw_order_no =", value, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoNotEqualTo(String value) {
            addCriterion("bgw_order_no <>", value, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoGreaterThan(String value) {
            addCriterion("bgw_order_no >", value, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("bgw_order_no >=", value, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoLessThan(String value) {
            addCriterion("bgw_order_no <", value, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoLessThanOrEqualTo(String value) {
            addCriterion("bgw_order_no <=", value, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoLike(String value) {
            addCriterion("bgw_order_no like", value, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoNotLike(String value) {
            addCriterion("bgw_order_no not like", value, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoIn(List<String> values) {
            addCriterion("bgw_order_no in", values, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoNotIn(List<String> values) {
            addCriterion("bgw_order_no not in", values, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoBetween(String value1, String value2) {
            addCriterion("bgw_order_no between", value1, value2, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoNotBetween(String value1, String value2) {
            addCriterion("bgw_order_no not between", value1, value2, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdIsNull() {
            addCriterion("bgw_bind_id is null");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdIsNotNull() {
            addCriterion("bgw_bind_id is not null");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdEqualTo(String value) {
            addCriterion("bgw_bind_id =", value, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdNotEqualTo(String value) {
            addCriterion("bgw_bind_id <>", value, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdGreaterThan(String value) {
            addCriterion("bgw_bind_id >", value, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdGreaterThanOrEqualTo(String value) {
            addCriterion("bgw_bind_id >=", value, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdLessThan(String value) {
            addCriterion("bgw_bind_id <", value, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdLessThanOrEqualTo(String value) {
            addCriterion("bgw_bind_id <=", value, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdLike(String value) {
            addCriterion("bgw_bind_id like", value, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdNotLike(String value) {
            addCriterion("bgw_bind_id not like", value, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdIn(List<String> values) {
            addCriterion("bgw_bind_id in", values, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdNotIn(List<String> values) {
            addCriterion("bgw_bind_id not in", values, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdBetween(String value1, String value2) {
            addCriterion("bgw_bind_id between", value1, value2, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdNotBetween(String value1, String value2) {
            addCriterion("bgw_bind_id not between", value1, value2, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoIsNull() {
            addCriterion("pay_order_no is null");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoIsNotNull() {
            addCriterion("pay_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoEqualTo(String value) {
            addCriterion("pay_order_no =", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoNotEqualTo(String value) {
            addCriterion("pay_order_no <>", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoGreaterThan(String value) {
            addCriterion("pay_order_no >", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("pay_order_no >=", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoLessThan(String value) {
            addCriterion("pay_order_no <", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoLessThanOrEqualTo(String value) {
            addCriterion("pay_order_no <=", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoLike(String value) {
            addCriterion("pay_order_no like", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoNotLike(String value) {
            addCriterion("pay_order_no not like", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoIn(List<String> values) {
            addCriterion("pay_order_no in", values, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoNotIn(List<String> values) {
            addCriterion("pay_order_no not in", values, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoBetween(String value1, String value2) {
            addCriterion("pay_order_no between", value1, value2, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoNotBetween(String value1, String value2) {
            addCriterion("pay_order_no not between", value1, value2, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andDoneTimeIsNull() {
            addCriterion("done_time is null");
            return (Criteria) this;
        }

        public Criteria andDoneTimeIsNotNull() {
            addCriterion("done_time is not null");
            return (Criteria) this;
        }

        public Criteria andDoneTimeEqualTo(Date value) {
            addCriterion("done_time =", value, "doneTime");
            return (Criteria) this;
        }

        public Criteria andDoneTimeNotEqualTo(Date value) {
            addCriterion("done_time <>", value, "doneTime");
            return (Criteria) this;
        }

        public Criteria andDoneTimeGreaterThan(Date value) {
            addCriterion("done_time >", value, "doneTime");
            return (Criteria) this;
        }

        public Criteria andDoneTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("done_time >=", value, "doneTime");
            return (Criteria) this;
        }

        public Criteria andDoneTimeLessThan(Date value) {
            addCriterion("done_time <", value, "doneTime");
            return (Criteria) this;
        }

        public Criteria andDoneTimeLessThanOrEqualTo(Date value) {
            addCriterion("done_time <=", value, "doneTime");
            return (Criteria) this;
        }

        public Criteria andDoneTimeIn(List<Date> values) {
            addCriterion("done_time in", values, "doneTime");
            return (Criteria) this;
        }

        public Criteria andDoneTimeNotIn(List<Date> values) {
            addCriterion("done_time not in", values, "doneTime");
            return (Criteria) this;
        }

        public Criteria andDoneTimeBetween(Date value1, Date value2) {
            addCriterion("done_time between", value1, value2, "doneTime");
            return (Criteria) this;
        }

        public Criteria andDoneTimeNotBetween(Date value1, Date value2) {
            addCriterion("done_time not between", value1, value2, "doneTime");
            return (Criteria) this;
        }

        public Criteria andDoneTotalIsNull() {
            addCriterion("done_total is null");
            return (Criteria) this;
        }

        public Criteria andDoneTotalIsNotNull() {
            addCriterion("done_total is not null");
            return (Criteria) this;
        }

        public Criteria andDoneTotalEqualTo(Double value) {
            addCriterion("done_total =", value, "doneTotal");
            return (Criteria) this;
        }

        public Criteria andDoneTotalNotEqualTo(Double value) {
            addCriterion("done_total <>", value, "doneTotal");
            return (Criteria) this;
        }

        public Criteria andDoneTotalGreaterThan(Double value) {
            addCriterion("done_total >", value, "doneTotal");
            return (Criteria) this;
        }

        public Criteria andDoneTotalGreaterThanOrEqualTo(Double value) {
            addCriterion("done_total >=", value, "doneTotal");
            return (Criteria) this;
        }

        public Criteria andDoneTotalLessThan(Double value) {
            addCriterion("done_total <", value, "doneTotal");
            return (Criteria) this;
        }

        public Criteria andDoneTotalLessThanOrEqualTo(Double value) {
            addCriterion("done_total <=", value, "doneTotal");
            return (Criteria) this;
        }

        public Criteria andDoneTotalIn(List<Double> values) {
            addCriterion("done_total in", values, "doneTotal");
            return (Criteria) this;
        }

        public Criteria andDoneTotalNotIn(List<Double> values) {
            addCriterion("done_total not in", values, "doneTotal");
            return (Criteria) this;
        }

        public Criteria andDoneTotalBetween(Double value1, Double value2) {
            addCriterion("done_total between", value1, value2, "doneTotal");
            return (Criteria) this;
        }

        public Criteria andDoneTotalNotBetween(Double value1, Double value2) {
            addCriterion("done_total not between", value1, value2, "doneTotal");
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

        public Criteria andInformStatusIsNull() {
            addCriterion("inform_status is null");
            return (Criteria) this;
        }

        public Criteria andInformStatusIsNotNull() {
            addCriterion("inform_status is not null");
            return (Criteria) this;
        }

        public Criteria andInformStatusEqualTo(String value) {
            addCriterion("inform_status =", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusNotEqualTo(String value) {
            addCriterion("inform_status <>", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusGreaterThan(String value) {
            addCriterion("inform_status >", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusGreaterThanOrEqualTo(String value) {
            addCriterion("inform_status >=", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusLessThan(String value) {
            addCriterion("inform_status <", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusLessThanOrEqualTo(String value) {
            addCriterion("inform_status <=", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusLike(String value) {
            addCriterion("inform_status like", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusNotLike(String value) {
            addCriterion("inform_status not like", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusIn(List<String> values) {
            addCriterion("inform_status in", values, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusNotIn(List<String> values) {
            addCriterion("inform_status not in", values, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusBetween(String value1, String value2) {
            addCriterion("inform_status between", value1, value2, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusNotBetween(String value1, String value2) {
            addCriterion("inform_status not between", value1, value2, "informStatus");
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
        
        
        public Criteria andApplyTimeIsNull() {
            addCriterion("apply_time is null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIsNotNull() {
            addCriterion("apply_time is not null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeEqualTo(Date value) {
            addCriterion("apply_time =", value, "ApplyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotEqualTo(Date value) {
            addCriterion("apply_time <>", value, "ApplyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThan(Date value) {
            addCriterion("apply_time >", value, "ApplyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("apply_time >=", value, "ApplyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThan(Date value) {
            addCriterion("apply_time <", value, "ApplyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThanOrEqualTo(Date value) {
            addCriterion("apply_time <=", value, "ApplyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIn(List<Date> values) {
            addCriterion("apply_time in", values, "ApplyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotIn(List<Date> values) {
            addCriterion("apply_time not in", values, "ApplyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeBetween(Date value1, Date value2) {
            addCriterion("apply_time between", value1, value2, "ApplyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotBetween(Date value1, Date value2) {
            addCriterion("apply_time not between", value1, value2, "ApplyTime");
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