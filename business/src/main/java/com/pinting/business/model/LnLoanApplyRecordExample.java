package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LnLoanApplyRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LnLoanApplyRecordExample() {
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

        public Criteria andPartnerUserIdIsNull() {
            addCriterion("partner_user_id is null");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdIsNotNull() {
            addCriterion("partner_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdEqualTo(String value) {
            addCriterion("partner_user_id =", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdNotEqualTo(String value) {
            addCriterion("partner_user_id <>", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdGreaterThan(String value) {
            addCriterion("partner_user_id >", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("partner_user_id >=", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdLessThan(String value) {
            addCriterion("partner_user_id <", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdLessThanOrEqualTo(String value) {
            addCriterion("partner_user_id <=", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdLike(String value) {
            addCriterion("partner_user_id like", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdNotLike(String value) {
            addCriterion("partner_user_id not like", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdIn(List<String> values) {
            addCriterion("partner_user_id in", values, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdNotIn(List<String> values) {
            addCriterion("partner_user_id not in", values, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdBetween(String value1, String value2) {
            addCriterion("partner_user_id between", value1, value2, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdNotBetween(String value1, String value2) {
            addCriterion("partner_user_id not between", value1, value2, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdIsNull() {
            addCriterion("partner_loan_id is null");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdIsNotNull() {
            addCriterion("partner_loan_id is not null");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdEqualTo(String value) {
            addCriterion("partner_loan_id =", value, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdNotEqualTo(String value) {
            addCriterion("partner_loan_id <>", value, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdGreaterThan(String value) {
            addCriterion("partner_loan_id >", value, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdGreaterThanOrEqualTo(String value) {
            addCriterion("partner_loan_id >=", value, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdLessThan(String value) {
            addCriterion("partner_loan_id <", value, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdLessThanOrEqualTo(String value) {
            addCriterion("partner_loan_id <=", value, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdLike(String value) {
            addCriterion("partner_loan_id like", value, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdNotLike(String value) {
            addCriterion("partner_loan_id not like", value, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdIn(List<String> values) {
            addCriterion("partner_loan_id in", values, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdNotIn(List<String> values) {
            addCriterion("partner_loan_id not in", values, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdBetween(String value1, String value2) {
            addCriterion("partner_loan_id between", value1, value2, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdNotBetween(String value1, String value2) {
            addCriterion("partner_loan_id not between", value1, value2, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andApplyAmountIsNull() {
            addCriterion("apply_amount is null");
            return (Criteria) this;
        }

        public Criteria andApplyAmountIsNotNull() {
            addCriterion("apply_amount is not null");
            return (Criteria) this;
        }

        public Criteria andApplyAmountEqualTo(Double value) {
            addCriterion("apply_amount =", value, "applyAmount");
            return (Criteria) this;
        }

        public Criteria andApplyAmountNotEqualTo(Double value) {
            addCriterion("apply_amount <>", value, "applyAmount");
            return (Criteria) this;
        }

        public Criteria andApplyAmountGreaterThan(Double value) {
            addCriterion("apply_amount >", value, "applyAmount");
            return (Criteria) this;
        }

        public Criteria andApplyAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("apply_amount >=", value, "applyAmount");
            return (Criteria) this;
        }

        public Criteria andApplyAmountLessThan(Double value) {
            addCriterion("apply_amount <", value, "applyAmount");
            return (Criteria) this;
        }

        public Criteria andApplyAmountLessThanOrEqualTo(Double value) {
            addCriterion("apply_amount <=", value, "applyAmount");
            return (Criteria) this;
        }

        public Criteria andApplyAmountIn(List<Double> values) {
            addCriterion("apply_amount in", values, "applyAmount");
            return (Criteria) this;
        }

        public Criteria andApplyAmountNotIn(List<Double> values) {
            addCriterion("apply_amount not in", values, "applyAmount");
            return (Criteria) this;
        }

        public Criteria andApplyAmountBetween(Double value1, Double value2) {
            addCriterion("apply_amount between", value1, value2, "applyAmount");
            return (Criteria) this;
        }

        public Criteria andApplyAmountNotBetween(Double value1, Double value2) {
            addCriterion("apply_amount not between", value1, value2, "applyAmount");
            return (Criteria) this;
        }

        public Criteria andHeadFeeIsNull() {
            addCriterion("head_fee is null");
            return (Criteria) this;
        }

        public Criteria andHeadFeeIsNotNull() {
            addCriterion("head_fee is not null");
            return (Criteria) this;
        }

        public Criteria andHeadFeeEqualTo(Double value) {
            addCriterion("head_fee =", value, "headFee");
            return (Criteria) this;
        }

        public Criteria andHeadFeeNotEqualTo(Double value) {
            addCriterion("head_fee <>", value, "headFee");
            return (Criteria) this;
        }

        public Criteria andHeadFeeGreaterThan(Double value) {
            addCriterion("head_fee >", value, "headFee");
            return (Criteria) this;
        }

        public Criteria andHeadFeeGreaterThanOrEqualTo(Double value) {
            addCriterion("head_fee >=", value, "headFee");
            return (Criteria) this;
        }

        public Criteria andHeadFeeLessThan(Double value) {
            addCriterion("head_fee <", value, "headFee");
            return (Criteria) this;
        }

        public Criteria andHeadFeeLessThanOrEqualTo(Double value) {
            addCriterion("head_fee <=", value, "headFee");
            return (Criteria) this;
        }

        public Criteria andHeadFeeIn(List<Double> values) {
            addCriterion("head_fee in", values, "headFee");
            return (Criteria) this;
        }

        public Criteria andHeadFeeNotIn(List<Double> values) {
            addCriterion("head_fee not in", values, "headFee");
            return (Criteria) this;
        }

        public Criteria andHeadFeeBetween(Double value1, Double value2) {
            addCriterion("head_fee between", value1, value2, "headFee");
            return (Criteria) this;
        }

        public Criteria andHeadFeeNotBetween(Double value1, Double value2) {
            addCriterion("head_fee not between", value1, value2, "headFee");
            return (Criteria) this;
        }

        public Criteria andAgreementRateIsNull() {
            addCriterion("agreement_rate is null");
            return (Criteria) this;
        }

        public Criteria andAgreementRateIsNotNull() {
            addCriterion("agreement_rate is not null");
            return (Criteria) this;
        }

        public Criteria andAgreementRateEqualTo(Double value) {
            addCriterion("agreement_rate =", value, "agreementRate");
            return (Criteria) this;
        }

        public Criteria andAgreementRateNotEqualTo(Double value) {
            addCriterion("agreement_rate <>", value, "agreementRate");
            return (Criteria) this;
        }

        public Criteria andAgreementRateGreaterThan(Double value) {
            addCriterion("agreement_rate >", value, "agreementRate");
            return (Criteria) this;
        }

        public Criteria andAgreementRateGreaterThanOrEqualTo(Double value) {
            addCriterion("agreement_rate >=", value, "agreementRate");
            return (Criteria) this;
        }

        public Criteria andAgreementRateLessThan(Double value) {
            addCriterion("agreement_rate <", value, "agreementRate");
            return (Criteria) this;
        }

        public Criteria andAgreementRateLessThanOrEqualTo(Double value) {
            addCriterion("agreement_rate <=", value, "agreementRate");
            return (Criteria) this;
        }

        public Criteria andAgreementRateIn(List<Double> values) {
            addCriterion("agreement_rate in", values, "agreementRate");
            return (Criteria) this;
        }

        public Criteria andAgreementRateNotIn(List<Double> values) {
            addCriterion("agreement_rate not in", values, "agreementRate");
            return (Criteria) this;
        }

        public Criteria andAgreementRateBetween(Double value1, Double value2) {
            addCriterion("agreement_rate between", value1, value2, "agreementRate");
            return (Criteria) this;
        }

        public Criteria andAgreementRateNotBetween(Double value1, Double value2) {
            addCriterion("agreement_rate not between", value1, value2, "agreementRate");
            return (Criteria) this;
        }

        public Criteria andLoanServiceRateIsNull() {
            addCriterion("loan_service_rate is null");
            return (Criteria) this;
        }

        public Criteria andLoanServiceRateIsNotNull() {
            addCriterion("loan_service_rate is not null");
            return (Criteria) this;
        }

        public Criteria andLoanServiceRateEqualTo(Double value) {
            addCriterion("loan_service_rate =", value, "loanServiceRate");
            return (Criteria) this;
        }

        public Criteria andLoanServiceRateNotEqualTo(Double value) {
            addCriterion("loan_service_rate <>", value, "loanServiceRate");
            return (Criteria) this;
        }

        public Criteria andLoanServiceRateGreaterThan(Double value) {
            addCriterion("loan_service_rate >", value, "loanServiceRate");
            return (Criteria) this;
        }

        public Criteria andLoanServiceRateGreaterThanOrEqualTo(Double value) {
            addCriterion("loan_service_rate >=", value, "loanServiceRate");
            return (Criteria) this;
        }

        public Criteria andLoanServiceRateLessThan(Double value) {
            addCriterion("loan_service_rate <", value, "loanServiceRate");
            return (Criteria) this;
        }

        public Criteria andLoanServiceRateLessThanOrEqualTo(Double value) {
            addCriterion("loan_service_rate <=", value, "loanServiceRate");
            return (Criteria) this;
        }

        public Criteria andLoanServiceRateIn(List<Double> values) {
            addCriterion("loan_service_rate in", values, "loanServiceRate");
            return (Criteria) this;
        }

        public Criteria andLoanServiceRateNotIn(List<Double> values) {
            addCriterion("loan_service_rate not in", values, "loanServiceRate");
            return (Criteria) this;
        }

        public Criteria andLoanServiceRateBetween(Double value1, Double value2) {
            addCriterion("loan_service_rate between", value1, value2, "loanServiceRate");
            return (Criteria) this;
        }

        public Criteria andLoanServiceRateNotBetween(Double value1, Double value2) {
            addCriterion("loan_service_rate not between", value1, value2, "loanServiceRate");
            return (Criteria) this;
        }

        public Criteria andBgwSettleRateIsNull() {
            addCriterion("bgw_settle_rate is null");
            return (Criteria) this;
        }

        public Criteria andBgwSettleRateIsNotNull() {
            addCriterion("bgw_settle_rate is not null");
            return (Criteria) this;
        }

        public Criteria andBgwSettleRateEqualTo(Double value) {
            addCriterion("bgw_settle_rate =", value, "bgwSettleRate");
            return (Criteria) this;
        }

        public Criteria andBgwSettleRateNotEqualTo(Double value) {
            addCriterion("bgw_settle_rate <>", value, "bgwSettleRate");
            return (Criteria) this;
        }

        public Criteria andBgwSettleRateGreaterThan(Double value) {
            addCriterion("bgw_settle_rate >", value, "bgwSettleRate");
            return (Criteria) this;
        }

        public Criteria andBgwSettleRateGreaterThanOrEqualTo(Double value) {
            addCriterion("bgw_settle_rate >=", value, "bgwSettleRate");
            return (Criteria) this;
        }

        public Criteria andBgwSettleRateLessThan(Double value) {
            addCriterion("bgw_settle_rate <", value, "bgwSettleRate");
            return (Criteria) this;
        }

        public Criteria andBgwSettleRateLessThanOrEqualTo(Double value) {
            addCriterion("bgw_settle_rate <=", value, "bgwSettleRate");
            return (Criteria) this;
        }

        public Criteria andBgwSettleRateIn(List<Double> values) {
            addCriterion("bgw_settle_rate in", values, "bgwSettleRate");
            return (Criteria) this;
        }

        public Criteria andBgwSettleRateNotIn(List<Double> values) {
            addCriterion("bgw_settle_rate not in", values, "bgwSettleRate");
            return (Criteria) this;
        }

        public Criteria andBgwSettleRateBetween(Double value1, Double value2) {
            addCriterion("bgw_settle_rate between", value1, value2, "bgwSettleRate");
            return (Criteria) this;
        }

        public Criteria andBgwSettleRateNotBetween(Double value1, Double value2) {
            addCriterion("bgw_settle_rate not between", value1, value2, "bgwSettleRate");
            return (Criteria) this;
        }

        public Criteria andPeriodIsNull() {
            addCriterion("period is null");
            return (Criteria) this;
        }

        public Criteria andPeriodIsNotNull() {
            addCriterion("period is not null");
            return (Criteria) this;
        }

        public Criteria andPeriodEqualTo(Integer value) {
            addCriterion("period =", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotEqualTo(Integer value) {
            addCriterion("period <>", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodGreaterThan(Integer value) {
            addCriterion("period >", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("period >=", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodLessThan(Integer value) {
            addCriterion("period <", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("period <=", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodIn(List<Integer> values) {
            addCriterion("period in", values, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotIn(List<Integer> values) {
            addCriterion("period not in", values, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodBetween(Integer value1, Integer value2) {
            addCriterion("period between", value1, value2, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("period not between", value1, value2, "period");
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

        public Criteria andPartnerBusinessFlagIsNull() {
            addCriterion("partner_business_flag is null");
            return (Criteria) this;
        }

        public Criteria andPartnerBusinessFlagIsNotNull() {
            addCriterion("partner_business_flag is not null");
            return (Criteria) this;
        }

        public Criteria andPartnerBusinessFlagEqualTo(String value) {
            addCriterion("partner_business_flag =", value, "partnerBusinessFlag");
            return (Criteria) this;
        }

        public Criteria andPartnerBusinessFlagNotEqualTo(String value) {
            addCriterion("partner_business_flag <>", value, "partnerBusinessFlag");
            return (Criteria) this;
        }

        public Criteria andPartnerBusinessFlagGreaterThan(String value) {
            addCriterion("partner_business_flag >", value, "partnerBusinessFlag");
            return (Criteria) this;
        }

        public Criteria andPartnerBusinessFlagGreaterThanOrEqualTo(String value) {
            addCriterion("partner_business_flag >=", value, "partnerBusinessFlag");
            return (Criteria) this;
        }

        public Criteria andPartnerBusinessFlagLessThan(String value) {
            addCriterion("partner_business_flag <", value, "partnerBusinessFlag");
            return (Criteria) this;
        }

        public Criteria andPartnerBusinessFlagLessThanOrEqualTo(String value) {
            addCriterion("partner_business_flag <=", value, "partnerBusinessFlag");
            return (Criteria) this;
        }

        public Criteria andPartnerBusinessFlagLike(String value) {
            addCriterion("partner_business_flag like", value, "partnerBusinessFlag");
            return (Criteria) this;
        }

        public Criteria andPartnerBusinessFlagNotLike(String value) {
            addCriterion("partner_business_flag not like", value, "partnerBusinessFlag");
            return (Criteria) this;
        }

        public Criteria andPartnerBusinessFlagIn(List<String> values) {
            addCriterion("partner_business_flag in", values, "partnerBusinessFlag");
            return (Criteria) this;
        }

        public Criteria andPartnerBusinessFlagNotIn(List<String> values) {
            addCriterion("partner_business_flag not in", values, "partnerBusinessFlag");
            return (Criteria) this;
        }

        public Criteria andPartnerBusinessFlagBetween(String value1, String value2) {
            addCriterion("partner_business_flag between", value1, value2, "partnerBusinessFlag");
            return (Criteria) this;
        }

        public Criteria andPartnerBusinessFlagNotBetween(String value1, String value2) {
            addCriterion("partner_business_flag not between", value1, value2, "partnerBusinessFlag");
            return (Criteria) this;
        }

        public Criteria andSubjectNameIsNull() {
            addCriterion("subject_name is null");
            return (Criteria) this;
        }

        public Criteria andSubjectNameIsNotNull() {
            addCriterion("subject_name is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectNameEqualTo(String value) {
            addCriterion("subject_name =", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameNotEqualTo(String value) {
            addCriterion("subject_name <>", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameGreaterThan(String value) {
            addCriterion("subject_name >", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameGreaterThanOrEqualTo(String value) {
            addCriterion("subject_name >=", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameLessThan(String value) {
            addCriterion("subject_name <", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameLessThanOrEqualTo(String value) {
            addCriterion("subject_name <=", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameLike(String value) {
            addCriterion("subject_name like", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameNotLike(String value) {
            addCriterion("subject_name not like", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameIn(List<String> values) {
            addCriterion("subject_name in", values, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameNotIn(List<String> values) {
            addCriterion("subject_name not in", values, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameBetween(String value1, String value2) {
            addCriterion("subject_name between", value1, value2, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameNotBetween(String value1, String value2) {
            addCriterion("subject_name not between", value1, value2, "subjectName");
            return (Criteria) this;
        }

        public Criteria andPurposeIsNull() {
            addCriterion("purpose is null");
            return (Criteria) this;
        }

        public Criteria andPurposeIsNotNull() {
            addCriterion("purpose is not null");
            return (Criteria) this;
        }

        public Criteria andPurposeEqualTo(String value) {
            addCriterion("purpose =", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeNotEqualTo(String value) {
            addCriterion("purpose <>", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeGreaterThan(String value) {
            addCriterion("purpose >", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeGreaterThanOrEqualTo(String value) {
            addCriterion("purpose >=", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeLessThan(String value) {
            addCriterion("purpose <", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeLessThanOrEqualTo(String value) {
            addCriterion("purpose <=", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeLike(String value) {
            addCriterion("purpose like", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeNotLike(String value) {
            addCriterion("purpose not like", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeIn(List<String> values) {
            addCriterion("purpose in", values, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeNotIn(List<String> values) {
            addCriterion("purpose not in", values, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeBetween(String value1, String value2) {
            addCriterion("purpose between", value1, value2, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeNotBetween(String value1, String value2) {
            addCriterion("purpose not between", value1, value2, "purpose");
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
            addCriterion("apply_time =", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotEqualTo(Date value) {
            addCriterion("apply_time <>", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThan(Date value) {
            addCriterion("apply_time >", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("apply_time >=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThan(Date value) {
            addCriterion("apply_time <", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThanOrEqualTo(Date value) {
            addCriterion("apply_time <=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIn(List<Date> values) {
            addCriterion("apply_time in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotIn(List<Date> values) {
            addCriterion("apply_time not in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeBetween(Date value1, Date value2) {
            addCriterion("apply_time between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotBetween(Date value1, Date value2) {
            addCriterion("apply_time not between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andCreditAmountIsNull() {
            addCriterion("credit_amount is null");
            return (Criteria) this;
        }

        public Criteria andCreditAmountIsNotNull() {
            addCriterion("credit_amount is not null");
            return (Criteria) this;
        }

        public Criteria andCreditAmountEqualTo(Double value) {
            addCriterion("credit_amount =", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountNotEqualTo(Double value) {
            addCriterion("credit_amount <>", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountGreaterThan(Double value) {
            addCriterion("credit_amount >", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("credit_amount >=", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountLessThan(Double value) {
            addCriterion("credit_amount <", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountLessThanOrEqualTo(Double value) {
            addCriterion("credit_amount <=", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountIn(List<Double> values) {
            addCriterion("credit_amount in", values, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountNotIn(List<Double> values) {
            addCriterion("credit_amount not in", values, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountBetween(Double value1, Double value2) {
            addCriterion("credit_amount between", value1, value2, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountNotBetween(Double value1, Double value2) {
            addCriterion("credit_amount not between", value1, value2, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andLoanedAmountIsNull() {
            addCriterion("loaned_amount is null");
            return (Criteria) this;
        }

        public Criteria andLoanedAmountIsNotNull() {
            addCriterion("loaned_amount is not null");
            return (Criteria) this;
        }

        public Criteria andLoanedAmountEqualTo(Double value) {
            addCriterion("loaned_amount =", value, "loanedAmount");
            return (Criteria) this;
        }

        public Criteria andLoanedAmountNotEqualTo(Double value) {
            addCriterion("loaned_amount <>", value, "loanedAmount");
            return (Criteria) this;
        }

        public Criteria andLoanedAmountGreaterThan(Double value) {
            addCriterion("loaned_amount >", value, "loanedAmount");
            return (Criteria) this;
        }

        public Criteria andLoanedAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("loaned_amount >=", value, "loanedAmount");
            return (Criteria) this;
        }

        public Criteria andLoanedAmountLessThan(Double value) {
            addCriterion("loaned_amount <", value, "loanedAmount");
            return (Criteria) this;
        }

        public Criteria andLoanedAmountLessThanOrEqualTo(Double value) {
            addCriterion("loaned_amount <=", value, "loanedAmount");
            return (Criteria) this;
        }

        public Criteria andLoanedAmountIn(List<Double> values) {
            addCriterion("loaned_amount in", values, "loanedAmount");
            return (Criteria) this;
        }

        public Criteria andLoanedAmountNotIn(List<Double> values) {
            addCriterion("loaned_amount not in", values, "loanedAmount");
            return (Criteria) this;
        }

        public Criteria andLoanedAmountBetween(Double value1, Double value2) {
            addCriterion("loaned_amount between", value1, value2, "loanedAmount");
            return (Criteria) this;
        }

        public Criteria andLoanedAmountNotBetween(Double value1, Double value2) {
            addCriterion("loaned_amount not between", value1, value2, "loanedAmount");
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

        public Criteria andCreditLevelIsNull() {
            addCriterion("credit_level is null");
            return (Criteria) this;
        }

        public Criteria andCreditLevelIsNotNull() {
            addCriterion("credit_level is not null");
            return (Criteria) this;
        }

        public Criteria andCreditLevelEqualTo(String value) {
            addCriterion("credit_level =", value, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelNotEqualTo(String value) {
            addCriterion("credit_level <>", value, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelGreaterThan(String value) {
            addCriterion("credit_level >", value, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelGreaterThanOrEqualTo(String value) {
            addCriterion("credit_level >=", value, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelLessThan(String value) {
            addCriterion("credit_level <", value, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelLessThanOrEqualTo(String value) {
            addCriterion("credit_level <=", value, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelLike(String value) {
            addCriterion("credit_level like", value, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelNotLike(String value) {
            addCriterion("credit_level not like", value, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelIn(List<String> values) {
            addCriterion("credit_level in", values, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelNotIn(List<String> values) {
            addCriterion("credit_level not in", values, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelBetween(String value1, String value2) {
            addCriterion("credit_level between", value1, value2, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelNotBetween(String value1, String value2) {
            addCriterion("credit_level not between", value1, value2, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditScoreIsNull() {
            addCriterion("credit_score is null");
            return (Criteria) this;
        }

        public Criteria andCreditScoreIsNotNull() {
            addCriterion("credit_score is not null");
            return (Criteria) this;
        }

        public Criteria andCreditScoreEqualTo(Integer value) {
            addCriterion("credit_score =", value, "creditScore");
            return (Criteria) this;
        }

        public Criteria andCreditScoreNotEqualTo(Integer value) {
            addCriterion("credit_score <>", value, "creditScore");
            return (Criteria) this;
        }

        public Criteria andCreditScoreGreaterThan(Integer value) {
            addCriterion("credit_score >", value, "creditScore");
            return (Criteria) this;
        }

        public Criteria andCreditScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_score >=", value, "creditScore");
            return (Criteria) this;
        }

        public Criteria andCreditScoreLessThan(Integer value) {
            addCriterion("credit_score <", value, "creditScore");
            return (Criteria) this;
        }

        public Criteria andCreditScoreLessThanOrEqualTo(Integer value) {
            addCriterion("credit_score <=", value, "creditScore");
            return (Criteria) this;
        }

        public Criteria andCreditScoreIn(List<Integer> values) {
            addCriterion("credit_score in", values, "creditScore");
            return (Criteria) this;
        }

        public Criteria andCreditScoreNotIn(List<Integer> values) {
            addCriterion("credit_score not in", values, "creditScore");
            return (Criteria) this;
        }

        public Criteria andCreditScoreBetween(Integer value1, Integer value2) {
            addCriterion("credit_score between", value1, value2, "creditScore");
            return (Criteria) this;
        }

        public Criteria andCreditScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_score not between", value1, value2, "creditScore");
            return (Criteria) this;
        }

        public Criteria andLoanTimesIsNull() {
            addCriterion("loan_times is null");
            return (Criteria) this;
        }

        public Criteria andLoanTimesIsNotNull() {
            addCriterion("loan_times is not null");
            return (Criteria) this;
        }

        public Criteria andLoanTimesEqualTo(Integer value) {
            addCriterion("loan_times =", value, "loanTimes");
            return (Criteria) this;
        }

        public Criteria andLoanTimesNotEqualTo(Integer value) {
            addCriterion("loan_times <>", value, "loanTimes");
            return (Criteria) this;
        }

        public Criteria andLoanTimesGreaterThan(Integer value) {
            addCriterion("loan_times >", value, "loanTimes");
            return (Criteria) this;
        }

        public Criteria andLoanTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("loan_times >=", value, "loanTimes");
            return (Criteria) this;
        }

        public Criteria andLoanTimesLessThan(Integer value) {
            addCriterion("loan_times <", value, "loanTimes");
            return (Criteria) this;
        }

        public Criteria andLoanTimesLessThanOrEqualTo(Integer value) {
            addCriterion("loan_times <=", value, "loanTimes");
            return (Criteria) this;
        }

        public Criteria andLoanTimesIn(List<Integer> values) {
            addCriterion("loan_times in", values, "loanTimes");
            return (Criteria) this;
        }

        public Criteria andLoanTimesNotIn(List<Integer> values) {
            addCriterion("loan_times not in", values, "loanTimes");
            return (Criteria) this;
        }

        public Criteria andLoanTimesBetween(Integer value1, Integer value2) {
            addCriterion("loan_times between", value1, value2, "loanTimes");
            return (Criteria) this;
        }

        public Criteria andLoanTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("loan_times not between", value1, value2, "loanTimes");
            return (Criteria) this;
        }

        public Criteria andBreakTimesIsNull() {
            addCriterion("break_times is null");
            return (Criteria) this;
        }

        public Criteria andBreakTimesIsNotNull() {
            addCriterion("break_times is not null");
            return (Criteria) this;
        }

        public Criteria andBreakTimesEqualTo(Integer value) {
            addCriterion("break_times =", value, "breakTimes");
            return (Criteria) this;
        }

        public Criteria andBreakTimesNotEqualTo(Integer value) {
            addCriterion("break_times <>", value, "breakTimes");
            return (Criteria) this;
        }

        public Criteria andBreakTimesGreaterThan(Integer value) {
            addCriterion("break_times >", value, "breakTimes");
            return (Criteria) this;
        }

        public Criteria andBreakTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("break_times >=", value, "breakTimes");
            return (Criteria) this;
        }

        public Criteria andBreakTimesLessThan(Integer value) {
            addCriterion("break_times <", value, "breakTimes");
            return (Criteria) this;
        }

        public Criteria andBreakTimesLessThanOrEqualTo(Integer value) {
            addCriterion("break_times <=", value, "breakTimes");
            return (Criteria) this;
        }

        public Criteria andBreakTimesIn(List<Integer> values) {
            addCriterion("break_times in", values, "breakTimes");
            return (Criteria) this;
        }

        public Criteria andBreakTimesNotIn(List<Integer> values) {
            addCriterion("break_times not in", values, "breakTimes");
            return (Criteria) this;
        }

        public Criteria andBreakTimesBetween(Integer value1, Integer value2) {
            addCriterion("break_times between", value1, value2, "breakTimes");
            return (Criteria) this;
        }

        public Criteria andBreakTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("break_times not between", value1, value2, "breakTimes");
            return (Criteria) this;
        }

        public Criteria andBreakMaxDaysIsNull() {
            addCriterion("break_max_days is null");
            return (Criteria) this;
        }

        public Criteria andBreakMaxDaysIsNotNull() {
            addCriterion("break_max_days is not null");
            return (Criteria) this;
        }

        public Criteria andBreakMaxDaysEqualTo(Integer value) {
            addCriterion("break_max_days =", value, "breakMaxDays");
            return (Criteria) this;
        }

        public Criteria andBreakMaxDaysNotEqualTo(Integer value) {
            addCriterion("break_max_days <>", value, "breakMaxDays");
            return (Criteria) this;
        }

        public Criteria andBreakMaxDaysGreaterThan(Integer value) {
            addCriterion("break_max_days >", value, "breakMaxDays");
            return (Criteria) this;
        }

        public Criteria andBreakMaxDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("break_max_days >=", value, "breakMaxDays");
            return (Criteria) this;
        }

        public Criteria andBreakMaxDaysLessThan(Integer value) {
            addCriterion("break_max_days <", value, "breakMaxDays");
            return (Criteria) this;
        }

        public Criteria andBreakMaxDaysLessThanOrEqualTo(Integer value) {
            addCriterion("break_max_days <=", value, "breakMaxDays");
            return (Criteria) this;
        }

        public Criteria andBreakMaxDaysIn(List<Integer> values) {
            addCriterion("break_max_days in", values, "breakMaxDays");
            return (Criteria) this;
        }

        public Criteria andBreakMaxDaysNotIn(List<Integer> values) {
            addCriterion("break_max_days not in", values, "breakMaxDays");
            return (Criteria) this;
        }

        public Criteria andBreakMaxDaysBetween(Integer value1, Integer value2) {
            addCriterion("break_max_days between", value1, value2, "breakMaxDays");
            return (Criteria) this;
        }

        public Criteria andBreakMaxDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("break_max_days not between", value1, value2, "breakMaxDays");
            return (Criteria) this;
        }

        public Criteria andInterestTimeIsNull() {
            addCriterion("interest_time is null");
            return (Criteria) this;
        }

        public Criteria andInterestTimeIsNotNull() {
            addCriterion("interest_time is not null");
            return (Criteria) this;
        }

        public Criteria andInterestTimeEqualTo(Date value) {
            addCriterion("interest_time =", value, "interestTime");
            return (Criteria) this;
        }

        public Criteria andInterestTimeNotEqualTo(Date value) {
            addCriterion("interest_time <>", value, "interestTime");
            return (Criteria) this;
        }

        public Criteria andInterestTimeGreaterThan(Date value) {
            addCriterion("interest_time >", value, "interestTime");
            return (Criteria) this;
        }

        public Criteria andInterestTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("interest_time >=", value, "interestTime");
            return (Criteria) this;
        }

        public Criteria andInterestTimeLessThan(Date value) {
            addCriterion("interest_time <", value, "interestTime");
            return (Criteria) this;
        }

        public Criteria andInterestTimeLessThanOrEqualTo(Date value) {
            addCriterion("interest_time <=", value, "interestTime");
            return (Criteria) this;
        }

        public Criteria andInterestTimeIn(List<Date> values) {
            addCriterion("interest_time in", values, "interestTime");
            return (Criteria) this;
        }

        public Criteria andInterestTimeNotIn(List<Date> values) {
            addCriterion("interest_time not in", values, "interestTime");
            return (Criteria) this;
        }

        public Criteria andInterestTimeBetween(Date value1, Date value2) {
            addCriterion("interest_time between", value1, value2, "interestTime");
            return (Criteria) this;
        }

        public Criteria andInterestTimeNotBetween(Date value1, Date value2) {
            addCriterion("interest_time not between", value1, value2, "interestTime");
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