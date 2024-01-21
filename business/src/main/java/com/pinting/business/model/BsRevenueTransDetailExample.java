package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsRevenueTransDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsRevenueTransDetailExample() {
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

        public Criteria andPartnerCodeIsNull() {
            addCriterion("partner_code is null");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeIsNotNull() {
            addCriterion("partner_code is not null");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeEqualTo(String value) {
            addCriterion("partner_code =", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeNotEqualTo(String value) {
            addCriterion("partner_code <>", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeGreaterThan(String value) {
            addCriterion("partner_code >", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeGreaterThanOrEqualTo(String value) {
            addCriterion("partner_code >=", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeLessThan(String value) {
            addCriterion("partner_code <", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeLessThanOrEqualTo(String value) {
            addCriterion("partner_code <=", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeLike(String value) {
            addCriterion("partner_code like", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeNotLike(String value) {
            addCriterion("partner_code not like", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeIn(List<String> values) {
            addCriterion("partner_code in", values, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeNotIn(List<String> values) {
            addCriterion("partner_code not in", values, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeBetween(String value1, String value2) {
            addCriterion("partner_code between", value1, value2, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeNotBetween(String value1, String value2) {
            addCriterion("partner_code not between", value1, value2, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andTransTypeIsNull() {
            addCriterion("trans_type is null");
            return (Criteria) this;
        }

        public Criteria andTransTypeIsNotNull() {
            addCriterion("trans_type is not null");
            return (Criteria) this;
        }

        public Criteria andTransTypeEqualTo(String value) {
            addCriterion("trans_type =", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotEqualTo(String value) {
            addCriterion("trans_type <>", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeGreaterThan(String value) {
            addCriterion("trans_type >", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeGreaterThanOrEqualTo(String value) {
            addCriterion("trans_type >=", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeLessThan(String value) {
            addCriterion("trans_type <", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeLessThanOrEqualTo(String value) {
            addCriterion("trans_type <=", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeLike(String value) {
            addCriterion("trans_type like", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotLike(String value) {
            addCriterion("trans_type not like", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeIn(List<String> values) {
            addCriterion("trans_type in", values, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotIn(List<String> values) {
            addCriterion("trans_type not in", values, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeBetween(String value1, String value2) {
            addCriterion("trans_type between", value1, value2, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotBetween(String value1, String value2) {
            addCriterion("trans_type not between", value1, value2, "transType");
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

        public Criteria andRepaySerialIsNull() {
            addCriterion("repay_serial is null");
            return (Criteria) this;
        }

        public Criteria andRepaySerialIsNotNull() {
            addCriterion("repay_serial is not null");
            return (Criteria) this;
        }

        public Criteria andRepaySerialEqualTo(Integer value) {
            addCriterion("repay_serial =", value, "repaySerial");
            return (Criteria) this;
        }

        public Criteria andRepaySerialNotEqualTo(Integer value) {
            addCriterion("repay_serial <>", value, "repaySerial");
            return (Criteria) this;
        }

        public Criteria andRepaySerialGreaterThan(Integer value) {
            addCriterion("repay_serial >", value, "repaySerial");
            return (Criteria) this;
        }

        public Criteria andRepaySerialGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_serial >=", value, "repaySerial");
            return (Criteria) this;
        }

        public Criteria andRepaySerialLessThan(Integer value) {
            addCriterion("repay_serial <", value, "repaySerial");
            return (Criteria) this;
        }

        public Criteria andRepaySerialLessThanOrEqualTo(Integer value) {
            addCriterion("repay_serial <=", value, "repaySerial");
            return (Criteria) this;
        }

        public Criteria andRepaySerialIn(List<Integer> values) {
            addCriterion("repay_serial in", values, "repaySerial");
            return (Criteria) this;
        }

        public Criteria andRepaySerialNotIn(List<Integer> values) {
            addCriterion("repay_serial not in", values, "repaySerial");
            return (Criteria) this;
        }

        public Criteria andRepaySerialBetween(Integer value1, Integer value2) {
            addCriterion("repay_serial between", value1, value2, "repaySerial");
            return (Criteria) this;
        }

        public Criteria andRepaySerialNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_serial not between", value1, value2, "repaySerial");
            return (Criteria) this;
        }

        public Criteria andRevenueTransIdIsNull() {
            addCriterion("revenue_trans_id is null");
            return (Criteria) this;
        }

        public Criteria andRevenueTransIdIsNotNull() {
            addCriterion("revenue_trans_id is not null");
            return (Criteria) this;
        }

        public Criteria andRevenueTransIdEqualTo(Integer value) {
            addCriterion("revenue_trans_id =", value, "revenueTransId");
            return (Criteria) this;
        }

        public Criteria andRevenueTransIdNotEqualTo(Integer value) {
            addCriterion("revenue_trans_id <>", value, "revenueTransId");
            return (Criteria) this;
        }

        public Criteria andRevenueTransIdGreaterThan(Integer value) {
            addCriterion("revenue_trans_id >", value, "revenueTransId");
            return (Criteria) this;
        }

        public Criteria andRevenueTransIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("revenue_trans_id >=", value, "revenueTransId");
            return (Criteria) this;
        }

        public Criteria andRevenueTransIdLessThan(Integer value) {
            addCriterion("revenue_trans_id <", value, "revenueTransId");
            return (Criteria) this;
        }

        public Criteria andRevenueTransIdLessThanOrEqualTo(Integer value) {
            addCriterion("revenue_trans_id <=", value, "revenueTransId");
            return (Criteria) this;
        }

        public Criteria andRevenueTransIdIn(List<Integer> values) {
            addCriterion("revenue_trans_id in", values, "revenueTransId");
            return (Criteria) this;
        }

        public Criteria andRevenueTransIdNotIn(List<Integer> values) {
            addCriterion("revenue_trans_id not in", values, "revenueTransId");
            return (Criteria) this;
        }

        public Criteria andRevenueTransIdBetween(Integer value1, Integer value2) {
            addCriterion("revenue_trans_id between", value1, value2, "revenueTransId");
            return (Criteria) this;
        }

        public Criteria andRevenueTransIdNotBetween(Integer value1, Integer value2) {
            addCriterion("revenue_trans_id not between", value1, value2, "revenueTransId");
            return (Criteria) this;
        }

        public Criteria andRepayScheduleIdIsNull() {
            addCriterion("repay_schedule_id is null");
            return (Criteria) this;
        }

        public Criteria andRepayScheduleIdIsNotNull() {
            addCriterion("repay_schedule_id is not null");
            return (Criteria) this;
        }

        public Criteria andRepayScheduleIdEqualTo(Integer value) {
            addCriterion("repay_schedule_id =", value, "repayScheduleId");
            return (Criteria) this;
        }

        public Criteria andRepayScheduleIdNotEqualTo(Integer value) {
            addCriterion("repay_schedule_id <>", value, "repayScheduleId");
            return (Criteria) this;
        }

        public Criteria andRepayScheduleIdGreaterThan(Integer value) {
            addCriterion("repay_schedule_id >", value, "repayScheduleId");
            return (Criteria) this;
        }

        public Criteria andRepayScheduleIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_schedule_id >=", value, "repayScheduleId");
            return (Criteria) this;
        }

        public Criteria andRepayScheduleIdLessThan(Integer value) {
            addCriterion("repay_schedule_id <", value, "repayScheduleId");
            return (Criteria) this;
        }

        public Criteria andRepayScheduleIdLessThanOrEqualTo(Integer value) {
            addCriterion("repay_schedule_id <=", value, "repayScheduleId");
            return (Criteria) this;
        }

        public Criteria andRepayScheduleIdIn(List<Integer> values) {
            addCriterion("repay_schedule_id in", values, "repayScheduleId");
            return (Criteria) this;
        }

        public Criteria andRepayScheduleIdNotIn(List<Integer> values) {
            addCriterion("repay_schedule_id not in", values, "repayScheduleId");
            return (Criteria) this;
        }

        public Criteria andRepayScheduleIdBetween(Integer value1, Integer value2) {
            addCriterion("repay_schedule_id between", value1, value2, "repayScheduleId");
            return (Criteria) this;
        }

        public Criteria andRepayScheduleIdNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_schedule_id not between", value1, value2, "repayScheduleId");
            return (Criteria) this;
        }

        public Criteria andRepayIdIsNull() {
            addCriterion("repay_id is null");
            return (Criteria) this;
        }

        public Criteria andRepayIdIsNotNull() {
            addCriterion("repay_id is not null");
            return (Criteria) this;
        }

        public Criteria andRepayIdEqualTo(Integer value) {
            addCriterion("repay_id =", value, "repayId");
            return (Criteria) this;
        }

        public Criteria andRepayIdNotEqualTo(Integer value) {
            addCriterion("repay_id <>", value, "repayId");
            return (Criteria) this;
        }

        public Criteria andRepayIdGreaterThan(Integer value) {
            addCriterion("repay_id >", value, "repayId");
            return (Criteria) this;
        }

        public Criteria andRepayIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_id >=", value, "repayId");
            return (Criteria) this;
        }

        public Criteria andRepayIdLessThan(Integer value) {
            addCriterion("repay_id <", value, "repayId");
            return (Criteria) this;
        }

        public Criteria andRepayIdLessThanOrEqualTo(Integer value) {
            addCriterion("repay_id <=", value, "repayId");
            return (Criteria) this;
        }

        public Criteria andRepayIdIn(List<Integer> values) {
            addCriterion("repay_id in", values, "repayId");
            return (Criteria) this;
        }

        public Criteria andRepayIdNotIn(List<Integer> values) {
            addCriterion("repay_id not in", values, "repayId");
            return (Criteria) this;
        }

        public Criteria andRepayIdBetween(Integer value1, Integer value2) {
            addCriterion("repay_id between", value1, value2, "repayId");
            return (Criteria) this;
        }

        public Criteria andRepayIdNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_id not between", value1, value2, "repayId");
            return (Criteria) this;
        }

        public Criteria andRepayAmountIsNull() {
            addCriterion("repay_amount is null");
            return (Criteria) this;
        }

        public Criteria andRepayAmountIsNotNull() {
            addCriterion("repay_amount is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAmountEqualTo(Double value) {
            addCriterion("repay_amount =", value, "repayAmount");
            return (Criteria) this;
        }

        public Criteria andRepayAmountNotEqualTo(Double value) {
            addCriterion("repay_amount <>", value, "repayAmount");
            return (Criteria) this;
        }

        public Criteria andRepayAmountGreaterThan(Double value) {
            addCriterion("repay_amount >", value, "repayAmount");
            return (Criteria) this;
        }

        public Criteria andRepayAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("repay_amount >=", value, "repayAmount");
            return (Criteria) this;
        }

        public Criteria andRepayAmountLessThan(Double value) {
            addCriterion("repay_amount <", value, "repayAmount");
            return (Criteria) this;
        }

        public Criteria andRepayAmountLessThanOrEqualTo(Double value) {
            addCriterion("repay_amount <=", value, "repayAmount");
            return (Criteria) this;
        }

        public Criteria andRepayAmountIn(List<Double> values) {
            addCriterion("repay_amount in", values, "repayAmount");
            return (Criteria) this;
        }

        public Criteria andRepayAmountNotIn(List<Double> values) {
            addCriterion("repay_amount not in", values, "repayAmount");
            return (Criteria) this;
        }

        public Criteria andRepayAmountBetween(Double value1, Double value2) {
            addCriterion("repay_amount between", value1, value2, "repayAmount");
            return (Criteria) this;
        }

        public Criteria andRepayAmountNotBetween(Double value1, Double value2) {
            addCriterion("repay_amount not between", value1, value2, "repayAmount");
            return (Criteria) this;
        }

        public Criteria andDepositIsNull() {
            addCriterion("deposit is null");
            return (Criteria) this;
        }

        public Criteria andDepositIsNotNull() {
            addCriterion("deposit is not null");
            return (Criteria) this;
        }

        public Criteria andDepositEqualTo(Double value) {
            addCriterion("deposit =", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositNotEqualTo(Double value) {
            addCriterion("deposit <>", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositGreaterThan(Double value) {
            addCriterion("deposit >", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositGreaterThanOrEqualTo(Double value) {
            addCriterion("deposit >=", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositLessThan(Double value) {
            addCriterion("deposit <", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositLessThanOrEqualTo(Double value) {
            addCriterion("deposit <=", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositIn(List<Double> values) {
            addCriterion("deposit in", values, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositNotIn(List<Double> values) {
            addCriterion("deposit not in", values, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositBetween(Double value1, Double value2) {
            addCriterion("deposit between", value1, value2, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositNotBetween(Double value1, Double value2) {
            addCriterion("deposit not between", value1, value2, "deposit");
            return (Criteria) this;
        }

        public Criteria andBgwServiceFeeIsNull() {
            addCriterion("bgw_service_fee is null");
            return (Criteria) this;
        }

        public Criteria andBgwServiceFeeIsNotNull() {
            addCriterion("bgw_service_fee is not null");
            return (Criteria) this;
        }

        public Criteria andBgwServiceFeeEqualTo(Double value) {
            addCriterion("bgw_service_fee =", value, "bgwServiceFee");
            return (Criteria) this;
        }

        public Criteria andBgwServiceFeeNotEqualTo(Double value) {
            addCriterion("bgw_service_fee <>", value, "bgwServiceFee");
            return (Criteria) this;
        }

        public Criteria andBgwServiceFeeGreaterThan(Double value) {
            addCriterion("bgw_service_fee >", value, "bgwServiceFee");
            return (Criteria) this;
        }

        public Criteria andBgwServiceFeeGreaterThanOrEqualTo(Double value) {
            addCriterion("bgw_service_fee >=", value, "bgwServiceFee");
            return (Criteria) this;
        }

        public Criteria andBgwServiceFeeLessThan(Double value) {
            addCriterion("bgw_service_fee <", value, "bgwServiceFee");
            return (Criteria) this;
        }

        public Criteria andBgwServiceFeeLessThanOrEqualTo(Double value) {
            addCriterion("bgw_service_fee <=", value, "bgwServiceFee");
            return (Criteria) this;
        }

        public Criteria andBgwServiceFeeIn(List<Double> values) {
            addCriterion("bgw_service_fee in", values, "bgwServiceFee");
            return (Criteria) this;
        }

        public Criteria andBgwServiceFeeNotIn(List<Double> values) {
            addCriterion("bgw_service_fee not in", values, "bgwServiceFee");
            return (Criteria) this;
        }

        public Criteria andBgwServiceFeeBetween(Double value1, Double value2) {
            addCriterion("bgw_service_fee between", value1, value2, "bgwServiceFee");
            return (Criteria) this;
        }

        public Criteria andBgwServiceFeeNotBetween(Double value1, Double value2) {
            addCriterion("bgw_service_fee not between", value1, value2, "bgwServiceFee");
            return (Criteria) this;
        }

        public Criteria andCommissionFeeIsNull() {
            addCriterion("commission_fee is null");
            return (Criteria) this;
        }

        public Criteria andCommissionFeeIsNotNull() {
            addCriterion("commission_fee is not null");
            return (Criteria) this;
        }

        public Criteria andCommissionFeeEqualTo(Double value) {
            addCriterion("commission_fee =", value, "commissionFee");
            return (Criteria) this;
        }

        public Criteria andCommissionFeeNotEqualTo(Double value) {
            addCriterion("commission_fee <>", value, "commissionFee");
            return (Criteria) this;
        }

        public Criteria andCommissionFeeGreaterThan(Double value) {
            addCriterion("commission_fee >", value, "commissionFee");
            return (Criteria) this;
        }

        public Criteria andCommissionFeeGreaterThanOrEqualTo(Double value) {
            addCriterion("commission_fee >=", value, "commissionFee");
            return (Criteria) this;
        }

        public Criteria andCommissionFeeLessThan(Double value) {
            addCriterion("commission_fee <", value, "commissionFee");
            return (Criteria) this;
        }

        public Criteria andCommissionFeeLessThanOrEqualTo(Double value) {
            addCriterion("commission_fee <=", value, "commissionFee");
            return (Criteria) this;
        }

        public Criteria andCommissionFeeIn(List<Double> values) {
            addCriterion("commission_fee in", values, "commissionFee");
            return (Criteria) this;
        }

        public Criteria andCommissionFeeNotIn(List<Double> values) {
            addCriterion("commission_fee not in", values, "commissionFee");
            return (Criteria) this;
        }

        public Criteria andCommissionFeeBetween(Double value1, Double value2) {
            addCriterion("commission_fee between", value1, value2, "commissionFee");
            return (Criteria) this;
        }

        public Criteria andCommissionFeeNotBetween(Double value1, Double value2) {
            addCriterion("commission_fee not between", value1, value2, "commissionFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeIsNull() {
            addCriterion("other_fee is null");
            return (Criteria) this;
        }

        public Criteria andOtherFeeIsNotNull() {
            addCriterion("other_fee is not null");
            return (Criteria) this;
        }

        public Criteria andOtherFeeEqualTo(Double value) {
            addCriterion("other_fee =", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeNotEqualTo(Double value) {
            addCriterion("other_fee <>", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeGreaterThan(Double value) {
            addCriterion("other_fee >", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeGreaterThanOrEqualTo(Double value) {
            addCriterion("other_fee >=", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeLessThan(Double value) {
            addCriterion("other_fee <", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeLessThanOrEqualTo(Double value) {
            addCriterion("other_fee <=", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeIn(List<Double> values) {
            addCriterion("other_fee in", values, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeNotIn(List<Double> values) {
            addCriterion("other_fee not in", values, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeBetween(Double value1, Double value2) {
            addCriterion("other_fee between", value1, value2, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeNotBetween(Double value1, Double value2) {
            addCriterion("other_fee not between", value1, value2, "otherFee");
            return (Criteria) this;
        }

        public Criteria andRevenueAmountIsNull() {
            addCriterion("revenue_amount is null");
            return (Criteria) this;
        }

        public Criteria andRevenueAmountIsNotNull() {
            addCriterion("revenue_amount is not null");
            return (Criteria) this;
        }

        public Criteria andRevenueAmountEqualTo(Double value) {
            addCriterion("revenue_amount =", value, "revenueAmount");
            return (Criteria) this;
        }

        public Criteria andRevenueAmountNotEqualTo(Double value) {
            addCriterion("revenue_amount <>", value, "revenueAmount");
            return (Criteria) this;
        }

        public Criteria andRevenueAmountGreaterThan(Double value) {
            addCriterion("revenue_amount >", value, "revenueAmount");
            return (Criteria) this;
        }

        public Criteria andRevenueAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("revenue_amount >=", value, "revenueAmount");
            return (Criteria) this;
        }

        public Criteria andRevenueAmountLessThan(Double value) {
            addCriterion("revenue_amount <", value, "revenueAmount");
            return (Criteria) this;
        }

        public Criteria andRevenueAmountLessThanOrEqualTo(Double value) {
            addCriterion("revenue_amount <=", value, "revenueAmount");
            return (Criteria) this;
        }

        public Criteria andRevenueAmountIn(List<Double> values) {
            addCriterion("revenue_amount in", values, "revenueAmount");
            return (Criteria) this;
        }

        public Criteria andRevenueAmountNotIn(List<Double> values) {
            addCriterion("revenue_amount not in", values, "revenueAmount");
            return (Criteria) this;
        }

        public Criteria andRevenueAmountBetween(Double value1, Double value2) {
            addCriterion("revenue_amount between", value1, value2, "revenueAmount");
            return (Criteria) this;
        }

        public Criteria andRevenueAmountNotBetween(Double value1, Double value2) {
            addCriterion("revenue_amount not between", value1, value2, "revenueAmount");
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