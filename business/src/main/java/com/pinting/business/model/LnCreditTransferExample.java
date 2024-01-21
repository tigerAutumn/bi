package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LnCreditTransferExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LnCreditTransferExample() {
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

        public Criteria andOutLoanRelationIdIsNull() {
            addCriterion("out_loan_relation_id is null");
            return (Criteria) this;
        }

        public Criteria andOutLoanRelationIdIsNotNull() {
            addCriterion("out_loan_relation_id is not null");
            return (Criteria) this;
        }

        public Criteria andOutLoanRelationIdEqualTo(Integer value) {
            addCriterion("out_loan_relation_id =", value, "outLoanRelationId");
            return (Criteria) this;
        }

        public Criteria andOutLoanRelationIdNotEqualTo(Integer value) {
            addCriterion("out_loan_relation_id <>", value, "outLoanRelationId");
            return (Criteria) this;
        }

        public Criteria andOutLoanRelationIdGreaterThan(Integer value) {
            addCriterion("out_loan_relation_id >", value, "outLoanRelationId");
            return (Criteria) this;
        }

        public Criteria andOutLoanRelationIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_loan_relation_id >=", value, "outLoanRelationId");
            return (Criteria) this;
        }

        public Criteria andOutLoanRelationIdLessThan(Integer value) {
            addCriterion("out_loan_relation_id <", value, "outLoanRelationId");
            return (Criteria) this;
        }

        public Criteria andOutLoanRelationIdLessThanOrEqualTo(Integer value) {
            addCriterion("out_loan_relation_id <=", value, "outLoanRelationId");
            return (Criteria) this;
        }

        public Criteria andOutLoanRelationIdIn(List<Integer> values) {
            addCriterion("out_loan_relation_id in", values, "outLoanRelationId");
            return (Criteria) this;
        }

        public Criteria andOutLoanRelationIdNotIn(List<Integer> values) {
            addCriterion("out_loan_relation_id not in", values, "outLoanRelationId");
            return (Criteria) this;
        }

        public Criteria andOutLoanRelationIdBetween(Integer value1, Integer value2) {
            addCriterion("out_loan_relation_id between", value1, value2, "outLoanRelationId");
            return (Criteria) this;
        }

        public Criteria andOutLoanRelationIdNotBetween(Integer value1, Integer value2) {
            addCriterion("out_loan_relation_id not between", value1, value2, "outLoanRelationId");
            return (Criteria) this;
        }

        public Criteria andInLoanRelationIdIsNull() {
            addCriterion("in_loan_relation_id is null");
            return (Criteria) this;
        }

        public Criteria andInLoanRelationIdIsNotNull() {
            addCriterion("in_loan_relation_id is not null");
            return (Criteria) this;
        }

        public Criteria andInLoanRelationIdEqualTo(Integer value) {
            addCriterion("in_loan_relation_id =", value, "inLoanRelationId");
            return (Criteria) this;
        }

        public Criteria andInLoanRelationIdNotEqualTo(Integer value) {
            addCriterion("in_loan_relation_id <>", value, "inLoanRelationId");
            return (Criteria) this;
        }

        public Criteria andInLoanRelationIdGreaterThan(Integer value) {
            addCriterion("in_loan_relation_id >", value, "inLoanRelationId");
            return (Criteria) this;
        }

        public Criteria andInLoanRelationIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("in_loan_relation_id >=", value, "inLoanRelationId");
            return (Criteria) this;
        }

        public Criteria andInLoanRelationIdLessThan(Integer value) {
            addCriterion("in_loan_relation_id <", value, "inLoanRelationId");
            return (Criteria) this;
        }

        public Criteria andInLoanRelationIdLessThanOrEqualTo(Integer value) {
            addCriterion("in_loan_relation_id <=", value, "inLoanRelationId");
            return (Criteria) this;
        }

        public Criteria andInLoanRelationIdIn(List<Integer> values) {
            addCriterion("in_loan_relation_id in", values, "inLoanRelationId");
            return (Criteria) this;
        }

        public Criteria andInLoanRelationIdNotIn(List<Integer> values) {
            addCriterion("in_loan_relation_id not in", values, "inLoanRelationId");
            return (Criteria) this;
        }

        public Criteria andInLoanRelationIdBetween(Integer value1, Integer value2) {
            addCriterion("in_loan_relation_id between", value1, value2, "inLoanRelationId");
            return (Criteria) this;
        }

        public Criteria andInLoanRelationIdNotBetween(Integer value1, Integer value2) {
            addCriterion("in_loan_relation_id not between", value1, value2, "inLoanRelationId");
            return (Criteria) this;
        }

        public Criteria andOutUserIdIsNull() {
            addCriterion("out_user_id is null");
            return (Criteria) this;
        }

        public Criteria andOutUserIdIsNotNull() {
            addCriterion("out_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andOutUserIdEqualTo(Integer value) {
            addCriterion("out_user_id =", value, "outUserId");
            return (Criteria) this;
        }

        public Criteria andOutUserIdNotEqualTo(Integer value) {
            addCriterion("out_user_id <>", value, "outUserId");
            return (Criteria) this;
        }

        public Criteria andOutUserIdGreaterThan(Integer value) {
            addCriterion("out_user_id >", value, "outUserId");
            return (Criteria) this;
        }

        public Criteria andOutUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_user_id >=", value, "outUserId");
            return (Criteria) this;
        }

        public Criteria andOutUserIdLessThan(Integer value) {
            addCriterion("out_user_id <", value, "outUserId");
            return (Criteria) this;
        }

        public Criteria andOutUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("out_user_id <=", value, "outUserId");
            return (Criteria) this;
        }

        public Criteria andOutUserIdIn(List<Integer> values) {
            addCriterion("out_user_id in", values, "outUserId");
            return (Criteria) this;
        }

        public Criteria andOutUserIdNotIn(List<Integer> values) {
            addCriterion("out_user_id not in", values, "outUserId");
            return (Criteria) this;
        }

        public Criteria andOutUserIdBetween(Integer value1, Integer value2) {
            addCriterion("out_user_id between", value1, value2, "outUserId");
            return (Criteria) this;
        }

        public Criteria andOutUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("out_user_id not between", value1, value2, "outUserId");
            return (Criteria) this;
        }

        public Criteria andInUserIdIsNull() {
            addCriterion("in_user_id is null");
            return (Criteria) this;
        }

        public Criteria andInUserIdIsNotNull() {
            addCriterion("in_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andInUserIdEqualTo(Integer value) {
            addCriterion("in_user_id =", value, "inUserId");
            return (Criteria) this;
        }

        public Criteria andInUserIdNotEqualTo(Integer value) {
            addCriterion("in_user_id <>", value, "inUserId");
            return (Criteria) this;
        }

        public Criteria andInUserIdGreaterThan(Integer value) {
            addCriterion("in_user_id >", value, "inUserId");
            return (Criteria) this;
        }

        public Criteria andInUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("in_user_id >=", value, "inUserId");
            return (Criteria) this;
        }

        public Criteria andInUserIdLessThan(Integer value) {
            addCriterion("in_user_id <", value, "inUserId");
            return (Criteria) this;
        }

        public Criteria andInUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("in_user_id <=", value, "inUserId");
            return (Criteria) this;
        }

        public Criteria andInUserIdIn(List<Integer> values) {
            addCriterion("in_user_id in", values, "inUserId");
            return (Criteria) this;
        }

        public Criteria andInUserIdNotIn(List<Integer> values) {
            addCriterion("in_user_id not in", values, "inUserId");
            return (Criteria) this;
        }

        public Criteria andInUserIdBetween(Integer value1, Integer value2) {
            addCriterion("in_user_id between", value1, value2, "inUserId");
            return (Criteria) this;
        }

        public Criteria andInUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("in_user_id not between", value1, value2, "inUserId");
            return (Criteria) this;
        }

        public Criteria andOutSubAccountIdIsNull() {
            addCriterion("out_sub_account_id is null");
            return (Criteria) this;
        }

        public Criteria andOutSubAccountIdIsNotNull() {
            addCriterion("out_sub_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andOutSubAccountIdEqualTo(Integer value) {
            addCriterion("out_sub_account_id =", value, "outSubAccountId");
            return (Criteria) this;
        }

        public Criteria andOutSubAccountIdNotEqualTo(Integer value) {
            addCriterion("out_sub_account_id <>", value, "outSubAccountId");
            return (Criteria) this;
        }

        public Criteria andOutSubAccountIdGreaterThan(Integer value) {
            addCriterion("out_sub_account_id >", value, "outSubAccountId");
            return (Criteria) this;
        }

        public Criteria andOutSubAccountIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_sub_account_id >=", value, "outSubAccountId");
            return (Criteria) this;
        }

        public Criteria andOutSubAccountIdLessThan(Integer value) {
            addCriterion("out_sub_account_id <", value, "outSubAccountId");
            return (Criteria) this;
        }

        public Criteria andOutSubAccountIdLessThanOrEqualTo(Integer value) {
            addCriterion("out_sub_account_id <=", value, "outSubAccountId");
            return (Criteria) this;
        }

        public Criteria andOutSubAccountIdIn(List<Integer> values) {
            addCriterion("out_sub_account_id in", values, "outSubAccountId");
            return (Criteria) this;
        }

        public Criteria andOutSubAccountIdNotIn(List<Integer> values) {
            addCriterion("out_sub_account_id not in", values, "outSubAccountId");
            return (Criteria) this;
        }

        public Criteria andOutSubAccountIdBetween(Integer value1, Integer value2) {
            addCriterion("out_sub_account_id between", value1, value2, "outSubAccountId");
            return (Criteria) this;
        }

        public Criteria andOutSubAccountIdNotBetween(Integer value1, Integer value2) {
            addCriterion("out_sub_account_id not between", value1, value2, "outSubAccountId");
            return (Criteria) this;
        }

        public Criteria andInSubAccountIdIsNull() {
            addCriterion("in_sub_account_id is null");
            return (Criteria) this;
        }

        public Criteria andInSubAccountIdIsNotNull() {
            addCriterion("in_sub_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andInSubAccountIdEqualTo(Integer value) {
            addCriterion("in_sub_account_id =", value, "inSubAccountId");
            return (Criteria) this;
        }

        public Criteria andInSubAccountIdNotEqualTo(Integer value) {
            addCriterion("in_sub_account_id <>", value, "inSubAccountId");
            return (Criteria) this;
        }

        public Criteria andInSubAccountIdGreaterThan(Integer value) {
            addCriterion("in_sub_account_id >", value, "inSubAccountId");
            return (Criteria) this;
        }

        public Criteria andInSubAccountIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("in_sub_account_id >=", value, "inSubAccountId");
            return (Criteria) this;
        }

        public Criteria andInSubAccountIdLessThan(Integer value) {
            addCriterion("in_sub_account_id <", value, "inSubAccountId");
            return (Criteria) this;
        }

        public Criteria andInSubAccountIdLessThanOrEqualTo(Integer value) {
            addCriterion("in_sub_account_id <=", value, "inSubAccountId");
            return (Criteria) this;
        }

        public Criteria andInSubAccountIdIn(List<Integer> values) {
            addCriterion("in_sub_account_id in", values, "inSubAccountId");
            return (Criteria) this;
        }

        public Criteria andInSubAccountIdNotIn(List<Integer> values) {
            addCriterion("in_sub_account_id not in", values, "inSubAccountId");
            return (Criteria) this;
        }

        public Criteria andInSubAccountIdBetween(Integer value1, Integer value2) {
            addCriterion("in_sub_account_id between", value1, value2, "inSubAccountId");
            return (Criteria) this;
        }

        public Criteria andInSubAccountIdNotBetween(Integer value1, Integer value2) {
            addCriterion("in_sub_account_id not between", value1, value2, "inSubAccountId");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(Double value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(Double value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(Double value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(Double value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(Double value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<Double> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<Double> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(Double value1, Double value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(Double value1, Double value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andInAmountIsNull() {
            addCriterion("in_amount is null");
            return (Criteria) this;
        }

        public Criteria andInAmountIsNotNull() {
            addCriterion("in_amount is not null");
            return (Criteria) this;
        }

        public Criteria andInAmountEqualTo(Double value) {
            addCriterion("in_amount =", value, "inAmount");
            return (Criteria) this;
        }

        public Criteria andInAmountNotEqualTo(Double value) {
            addCriterion("in_amount <>", value, "inAmount");
            return (Criteria) this;
        }

        public Criteria andInAmountGreaterThan(Double value) {
            addCriterion("in_amount >", value, "inAmount");
            return (Criteria) this;
        }

        public Criteria andInAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("in_amount >=", value, "inAmount");
            return (Criteria) this;
        }

        public Criteria andInAmountLessThan(Double value) {
            addCriterion("in_amount <", value, "inAmount");
            return (Criteria) this;
        }

        public Criteria andInAmountLessThanOrEqualTo(Double value) {
            addCriterion("in_amount <=", value, "inAmount");
            return (Criteria) this;
        }

        public Criteria andInAmountIn(List<Double> values) {
            addCriterion("in_amount in", values, "inAmount");
            return (Criteria) this;
        }

        public Criteria andInAmountNotIn(List<Double> values) {
            addCriterion("in_amount not in", values, "inAmount");
            return (Criteria) this;
        }

        public Criteria andInAmountBetween(Double value1, Double value2) {
            addCriterion("in_amount between", value1, value2, "inAmount");
            return (Criteria) this;
        }

        public Criteria andInAmountNotBetween(Double value1, Double value2) {
            addCriterion("in_amount not between", value1, value2, "inAmount");
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


        public Criteria andDiscountAmountIsNull() {
            addCriterion("discount_amount is null");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountIsNotNull() {
            addCriterion("discount_amount is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountEqualTo(Double value) {
            addCriterion("discount_amount =", value, "discountAmount");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountNotEqualTo(Double value) {
            addCriterion("discount_amount <>", value, "discountAmount");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountGreaterThan(Double value) {
            addCriterion("discount_amount >", value, "discountAmount");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("discount_amount >=", value, "discountAmount");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountLessThan(Double value) {
            addCriterion("discount_amount <", value, "discountAmount");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountLessThanOrEqualTo(Double value) {
            addCriterion("discount_amount <=", value, "discountAmount");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountIn(List<Double> values) {
            addCriterion("discount_amount in", values, "discountAmount");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountNotIn(List<Double> values) {
            addCriterion("discount_amount not in", values, "discountAmount");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountBetween(Double value1, Double value2) {
            addCriterion("discount_amount between", value1, value2, "discountAmount");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountNotBetween(Double value1, Double value2) {
            addCriterion("discount_amount not between", value1, value2, "discountAmount");
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