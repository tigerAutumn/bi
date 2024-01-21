package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsServiceFeeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsServiceFeeExample() {
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

        public Criteria andFeeTypeIsNull() {
            addCriterion("fee_type is null");
            return (Criteria) this;
        }

        public Criteria andFeeTypeIsNotNull() {
            addCriterion("fee_type is not null");
            return (Criteria) this;
        }

        public Criteria andFeeTypeEqualTo(String value) {
            addCriterion("fee_type =", value, "feeType");
            return (Criteria) this;
        }

        public Criteria andFeeTypeNotEqualTo(String value) {
            addCriterion("fee_type <>", value, "feeType");
            return (Criteria) this;
        }

        public Criteria andFeeTypeGreaterThan(String value) {
            addCriterion("fee_type >", value, "feeType");
            return (Criteria) this;
        }

        public Criteria andFeeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("fee_type >=", value, "feeType");
            return (Criteria) this;
        }

        public Criteria andFeeTypeLessThan(String value) {
            addCriterion("fee_type <", value, "feeType");
            return (Criteria) this;
        }

        public Criteria andFeeTypeLessThanOrEqualTo(String value) {
            addCriterion("fee_type <=", value, "feeType");
            return (Criteria) this;
        }

        public Criteria andFeeTypeLike(String value) {
            addCriterion("fee_type like", value, "feeType");
            return (Criteria) this;
        }

        public Criteria andFeeTypeNotLike(String value) {
            addCriterion("fee_type not like", value, "feeType");
            return (Criteria) this;
        }

        public Criteria andFeeTypeIn(List<String> values) {
            addCriterion("fee_type in", values, "feeType");
            return (Criteria) this;
        }

        public Criteria andFeeTypeNotIn(List<String> values) {
            addCriterion("fee_type not in", values, "feeType");
            return (Criteria) this;
        }

        public Criteria andFeeTypeBetween(String value1, String value2) {
            addCriterion("fee_type between", value1, value2, "feeType");
            return (Criteria) this;
        }

        public Criteria andFeeTypeNotBetween(String value1, String value2) {
            addCriterion("fee_type not between", value1, value2, "feeType");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdIsNull() {
            addCriterion("sub_account_id is null");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdIsNotNull() {
            addCriterion("sub_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdEqualTo(Integer value) {
            addCriterion("sub_account_id =", value, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdNotEqualTo(Integer value) {
            addCriterion("sub_account_id <>", value, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdGreaterThan(Integer value) {
            addCriterion("sub_account_id >", value, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("sub_account_id >=", value, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdLessThan(Integer value) {
            addCriterion("sub_account_id <", value, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdLessThanOrEqualTo(Integer value) {
            addCriterion("sub_account_id <=", value, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdIn(List<Integer> values) {
            addCriterion("sub_account_id in", values, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdNotIn(List<Integer> values) {
            addCriterion("sub_account_id not in", values, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdBetween(Integer value1, Integer value2) {
            addCriterion("sub_account_id between", value1, value2, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdNotBetween(Integer value1, Integer value2) {
            addCriterion("sub_account_id not between", value1, value2, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andTransAmountIsNull() {
            addCriterion("trans_amount is null");
            return (Criteria) this;
        }

        public Criteria andTransAmountIsNotNull() {
            addCriterion("trans_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTransAmountEqualTo(Double value) {
            addCriterion("trans_amount =", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountNotEqualTo(Double value) {
            addCriterion("trans_amount <>", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountGreaterThan(Double value) {
            addCriterion("trans_amount >", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("trans_amount >=", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountLessThan(Double value) {
            addCriterion("trans_amount <", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountLessThanOrEqualTo(Double value) {
            addCriterion("trans_amount <=", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountIn(List<Double> values) {
            addCriterion("trans_amount in", values, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountNotIn(List<Double> values) {
            addCriterion("trans_amount not in", values, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountBetween(Double value1, Double value2) {
            addCriterion("trans_amount between", value1, value2, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountNotBetween(Double value1, Double value2) {
            addCriterion("trans_amount not between", value1, value2, "transAmount");
            return (Criteria) this;
        }

        public Criteria andPlanFeeIsNull() {
            addCriterion("plan_fee is null");
            return (Criteria) this;
        }

        public Criteria andPlanFeeIsNotNull() {
            addCriterion("plan_fee is not null");
            return (Criteria) this;
        }

        public Criteria andPlanFeeEqualTo(Double value) {
            addCriterion("plan_fee =", value, "planFee");
            return (Criteria) this;
        }

        public Criteria andPlanFeeNotEqualTo(Double value) {
            addCriterion("plan_fee <>", value, "planFee");
            return (Criteria) this;
        }

        public Criteria andPlanFeeGreaterThan(Double value) {
            addCriterion("plan_fee >", value, "planFee");
            return (Criteria) this;
        }

        public Criteria andPlanFeeGreaterThanOrEqualTo(Double value) {
            addCriterion("plan_fee >=", value, "planFee");
            return (Criteria) this;
        }

        public Criteria andPlanFeeLessThan(Double value) {
            addCriterion("plan_fee <", value, "planFee");
            return (Criteria) this;
        }

        public Criteria andPlanFeeLessThanOrEqualTo(Double value) {
            addCriterion("plan_fee <=", value, "planFee");
            return (Criteria) this;
        }

        public Criteria andPlanFeeIn(List<Double> values) {
            addCriterion("plan_fee in", values, "planFee");
            return (Criteria) this;
        }

        public Criteria andPlanFeeNotIn(List<Double> values) {
            addCriterion("plan_fee not in", values, "planFee");
            return (Criteria) this;
        }

        public Criteria andPlanFeeBetween(Double value1, Double value2) {
            addCriterion("plan_fee between", value1, value2, "planFee");
            return (Criteria) this;
        }

        public Criteria andPlanFeeNotBetween(Double value1, Double value2) {
            addCriterion("plan_fee not between", value1, value2, "planFee");
            return (Criteria) this;
        }

        public Criteria andDoneFeeIsNull() {
            addCriterion("done_fee is null");
            return (Criteria) this;
        }

        public Criteria andDoneFeeIsNotNull() {
            addCriterion("done_fee is not null");
            return (Criteria) this;
        }

        public Criteria andDoneFeeEqualTo(Double value) {
            addCriterion("done_fee =", value, "doneFee");
            return (Criteria) this;
        }

        public Criteria andDoneFeeNotEqualTo(Double value) {
            addCriterion("done_fee <>", value, "doneFee");
            return (Criteria) this;
        }

        public Criteria andDoneFeeGreaterThan(Double value) {
            addCriterion("done_fee >", value, "doneFee");
            return (Criteria) this;
        }

        public Criteria andDoneFeeGreaterThanOrEqualTo(Double value) {
            addCriterion("done_fee >=", value, "doneFee");
            return (Criteria) this;
        }

        public Criteria andDoneFeeLessThan(Double value) {
            addCriterion("done_fee <", value, "doneFee");
            return (Criteria) this;
        }

        public Criteria andDoneFeeLessThanOrEqualTo(Double value) {
            addCriterion("done_fee <=", value, "doneFee");
            return (Criteria) this;
        }

        public Criteria andDoneFeeIn(List<Double> values) {
            addCriterion("done_fee in", values, "doneFee");
            return (Criteria) this;
        }

        public Criteria andDoneFeeNotIn(List<Double> values) {
            addCriterion("done_fee not in", values, "doneFee");
            return (Criteria) this;
        }

        public Criteria andDoneFeeBetween(Double value1, Double value2) {
            addCriterion("done_fee between", value1, value2, "doneFee");
            return (Criteria) this;
        }

        public Criteria andDoneFeeNotBetween(Double value1, Double value2) {
            addCriterion("done_fee not between", value1, value2, "doneFee");
            return (Criteria) this;
        }

        public Criteria andPaymentPlatformFeeIsNull() {
            addCriterion("payment_platform_fee is null");
            return (Criteria) this;
        }

        public Criteria andPaymentPlatformFeeIsNotNull() {
            addCriterion("payment_platform_fee is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentPlatformFeeEqualTo(Double value) {
            addCriterion("payment_platform_fee =", value, "paymentPlatformFee");
            return (Criteria) this;
        }

        public Criteria andPaymentPlatformFeeNotEqualTo(Double value) {
            addCriterion("payment_platform_fee <>", value, "paymentPlatformFee");
            return (Criteria) this;
        }

        public Criteria andPaymentPlatformFeeGreaterThan(Double value) {
            addCriterion("payment_platform_fee >", value, "paymentPlatformFee");
            return (Criteria) this;
        }

        public Criteria andPaymentPlatformFeeGreaterThanOrEqualTo(Double value) {
            addCriterion("payment_platform_fee >=", value, "paymentPlatformFee");
            return (Criteria) this;
        }

        public Criteria andPaymentPlatformFeeLessThan(Double value) {
            addCriterion("payment_platform_fee <", value, "paymentPlatformFee");
            return (Criteria) this;
        }

        public Criteria andPaymentPlatformFeeLessThanOrEqualTo(Double value) {
            addCriterion("payment_platform_fee <=", value, "paymentPlatformFee");
            return (Criteria) this;
        }

        public Criteria andPaymentPlatformFeeIn(List<Double> values) {
            addCriterion("payment_platform_fee in", values, "paymentPlatformFee");
            return (Criteria) this;
        }

        public Criteria andPaymentPlatformFeeNotIn(List<Double> values) {
            addCriterion("payment_platform_fee not in", values, "paymentPlatformFee");
            return (Criteria) this;
        }

        public Criteria andPaymentPlatformFeeBetween(Double value1, Double value2) {
            addCriterion("payment_platform_fee between", value1, value2, "paymentPlatformFee");
            return (Criteria) this;
        }

        public Criteria andPaymentPlatformFeeNotBetween(Double value1, Double value2) {
            addCriterion("payment_platform_fee not between", value1, value2, "paymentPlatformFee");
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