package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LnLoanRelationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LnLoanRelationExample() {
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

        public Criteria andBsUserIdIsNull() {
            addCriterion("bs_user_id is null");
            return (Criteria) this;
        }

        public Criteria andBsUserIdIsNotNull() {
            addCriterion("bs_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andBsUserIdEqualTo(Integer value) {
            addCriterion("bs_user_id =", value, "bsUserId");
            return (Criteria) this;
        }

        public Criteria andBsUserIdNotEqualTo(Integer value) {
            addCriterion("bs_user_id <>", value, "bsUserId");
            return (Criteria) this;
        }

        public Criteria andBsUserIdGreaterThan(Integer value) {
            addCriterion("bs_user_id >", value, "bsUserId");
            return (Criteria) this;
        }

        public Criteria andBsUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("bs_user_id >=", value, "bsUserId");
            return (Criteria) this;
        }

        public Criteria andBsUserIdLessThan(Integer value) {
            addCriterion("bs_user_id <", value, "bsUserId");
            return (Criteria) this;
        }

        public Criteria andBsUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("bs_user_id <=", value, "bsUserId");
            return (Criteria) this;
        }

        public Criteria andBsUserIdIn(List<Integer> values) {
            addCriterion("bs_user_id in", values, "bsUserId");
            return (Criteria) this;
        }

        public Criteria andBsUserIdNotIn(List<Integer> values) {
            addCriterion("bs_user_id not in", values, "bsUserId");
            return (Criteria) this;
        }

        public Criteria andBsUserIdBetween(Integer value1, Integer value2) {
            addCriterion("bs_user_id between", value1, value2, "bsUserId");
            return (Criteria) this;
        }

        public Criteria andBsUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("bs_user_id not between", value1, value2, "bsUserId");
            return (Criteria) this;
        }

        public Criteria andBsSubAccountIdIsNull() {
            addCriterion("bs_sub_account_id is null");
            return (Criteria) this;
        }

        public Criteria andBsSubAccountIdIsNotNull() {
            addCriterion("bs_sub_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andBsSubAccountIdEqualTo(Integer value) {
            addCriterion("bs_sub_account_id =", value, "bsSubAccountId");
            return (Criteria) this;
        }

        public Criteria andBsSubAccountIdNotEqualTo(Integer value) {
            addCriterion("bs_sub_account_id <>", value, "bsSubAccountId");
            return (Criteria) this;
        }

        public Criteria andBsSubAccountIdGreaterThan(Integer value) {
            addCriterion("bs_sub_account_id >", value, "bsSubAccountId");
            return (Criteria) this;
        }

        public Criteria andBsSubAccountIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("bs_sub_account_id >=", value, "bsSubAccountId");
            return (Criteria) this;
        }

        public Criteria andBsSubAccountIdLessThan(Integer value) {
            addCriterion("bs_sub_account_id <", value, "bsSubAccountId");
            return (Criteria) this;
        }

        public Criteria andBsSubAccountIdLessThanOrEqualTo(Integer value) {
            addCriterion("bs_sub_account_id <=", value, "bsSubAccountId");
            return (Criteria) this;
        }

        public Criteria andBsSubAccountIdIn(List<Integer> values) {
            addCriterion("bs_sub_account_id in", values, "bsSubAccountId");
            return (Criteria) this;
        }

        public Criteria andBsSubAccountIdNotIn(List<Integer> values) {
            addCriterion("bs_sub_account_id not in", values, "bsSubAccountId");
            return (Criteria) this;
        }

        public Criteria andBsSubAccountIdBetween(Integer value1, Integer value2) {
            addCriterion("bs_sub_account_id between", value1, value2, "bsSubAccountId");
            return (Criteria) this;
        }

        public Criteria andBsSubAccountIdNotBetween(Integer value1, Integer value2) {
            addCriterion("bs_sub_account_id not between", value1, value2, "bsSubAccountId");
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

        public Criteria andLnSubAccountIdIsNull() {
            addCriterion("ln_sub_account_id is null");
            return (Criteria) this;
        }

        public Criteria andLnSubAccountIdIsNotNull() {
            addCriterion("ln_sub_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andLnSubAccountIdEqualTo(Integer value) {
            addCriterion("ln_sub_account_id =", value, "lnSubAccountId");
            return (Criteria) this;
        }

        public Criteria andLnSubAccountIdNotEqualTo(Integer value) {
            addCriterion("ln_sub_account_id <>", value, "lnSubAccountId");
            return (Criteria) this;
        }

        public Criteria andLnSubAccountIdGreaterThan(Integer value) {
            addCriterion("ln_sub_account_id >", value, "lnSubAccountId");
            return (Criteria) this;
        }

        public Criteria andLnSubAccountIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ln_sub_account_id >=", value, "lnSubAccountId");
            return (Criteria) this;
        }

        public Criteria andLnSubAccountIdLessThan(Integer value) {
            addCriterion("ln_sub_account_id <", value, "lnSubAccountId");
            return (Criteria) this;
        }

        public Criteria andLnSubAccountIdLessThanOrEqualTo(Integer value) {
            addCriterion("ln_sub_account_id <=", value, "lnSubAccountId");
            return (Criteria) this;
        }

        public Criteria andLnSubAccountIdIn(List<Integer> values) {
            addCriterion("ln_sub_account_id in", values, "lnSubAccountId");
            return (Criteria) this;
        }

        public Criteria andLnSubAccountIdNotIn(List<Integer> values) {
            addCriterion("ln_sub_account_id not in", values, "lnSubAccountId");
            return (Criteria) this;
        }

        public Criteria andLnSubAccountIdBetween(Integer value1, Integer value2) {
            addCriterion("ln_sub_account_id between", value1, value2, "lnSubAccountId");
            return (Criteria) this;
        }

        public Criteria andLnSubAccountIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ln_sub_account_id not between", value1, value2, "lnSubAccountId");
            return (Criteria) this;
        }

        public Criteria andInitAmountIsNull() {
            addCriterion("init_amount is null");
            return (Criteria) this;
        }

        public Criteria andInitAmountIsNotNull() {
            addCriterion("init_amount is not null");
            return (Criteria) this;
        }

        public Criteria andInitAmountEqualTo(Double value) {
            addCriterion("init_amount =", value, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountNotEqualTo(Double value) {
            addCriterion("init_amount <>", value, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountGreaterThan(Double value) {
            addCriterion("init_amount >", value, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("init_amount >=", value, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountLessThan(Double value) {
            addCriterion("init_amount <", value, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountLessThanOrEqualTo(Double value) {
            addCriterion("init_amount <=", value, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountIn(List<Double> values) {
            addCriterion("init_amount in", values, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountNotIn(List<Double> values) {
            addCriterion("init_amount not in", values, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountBetween(Double value1, Double value2) {
            addCriterion("init_amount between", value1, value2, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountNotBetween(Double value1, Double value2) {
            addCriterion("init_amount not between", value1, value2, "initAmount");
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

        public Criteria andTotalAmountIsNull() {
            addCriterion("total_amount is null");
            return (Criteria) this;
        }

        public Criteria andTotalAmountIsNotNull() {
            addCriterion("total_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTotalAmountEqualTo(Double value) {
            addCriterion("total_amount =", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountNotEqualTo(Double value) {
            addCriterion("total_amount <>", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountGreaterThan(Double value) {
            addCriterion("total_amount >", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("total_amount >=", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountLessThan(Double value) {
            addCriterion("total_amount <", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountLessThanOrEqualTo(Double value) {
            addCriterion("total_amount <=", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountIn(List<Double> values) {
            addCriterion("total_amount in", values, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountNotIn(List<Double> values) {
            addCriterion("total_amount not in", values, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountBetween(Double value1, Double value2) {
            addCriterion("total_amount between", value1, value2, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountNotBetween(Double value1, Double value2) {
            addCriterion("total_amount not between", value1, value2, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andLeftAmountIsNull() {
            addCriterion("left_amount is null");
            return (Criteria) this;
        }

        public Criteria andLeftAmountIsNotNull() {
            addCriterion("left_amount is not null");
            return (Criteria) this;
        }

        public Criteria andLeftAmountEqualTo(Double value) {
            addCriterion("left_amount =", value, "leftAmount");
            return (Criteria) this;
        }

        public Criteria andLeftAmountNotEqualTo(Double value) {
            addCriterion("left_amount <>", value, "leftAmount");
            return (Criteria) this;
        }

        public Criteria andLeftAmountGreaterThan(Double value) {
            addCriterion("left_amount >", value, "leftAmount");
            return (Criteria) this;
        }

        public Criteria andLeftAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("left_amount >=", value, "leftAmount");
            return (Criteria) this;
        }

        public Criteria andLeftAmountLessThan(Double value) {
            addCriterion("left_amount <", value, "leftAmount");
            return (Criteria) this;
        }

        public Criteria andLeftAmountLessThanOrEqualTo(Double value) {
            addCriterion("left_amount <=", value, "leftAmount");
            return (Criteria) this;
        }

        public Criteria andLeftAmountIn(List<Double> values) {
            addCriterion("left_amount in", values, "leftAmount");
            return (Criteria) this;
        }

        public Criteria andLeftAmountNotIn(List<Double> values) {
            addCriterion("left_amount not in", values, "leftAmount");
            return (Criteria) this;
        }

        public Criteria andLeftAmountBetween(Double value1, Double value2) {
            addCriterion("left_amount between", value1, value2, "leftAmount");
            return (Criteria) this;
        }

        public Criteria andLeftAmountNotBetween(Double value1, Double value2) {
            addCriterion("left_amount not between", value1, value2, "leftAmount");
            return (Criteria) this;
        }

        public Criteria andFirstTermIsNull() {
            addCriterion("first_term is null");
            return (Criteria) this;
        }

        public Criteria andFirstTermIsNotNull() {
            addCriterion("first_term is not null");
            return (Criteria) this;
        }

        public Criteria andFirstTermEqualTo(Integer value) {
            addCriterion("first_term =", value, "firstTerm");
            return (Criteria) this;
        }

        public Criteria andFirstTermNotEqualTo(Integer value) {
            addCriterion("first_term <>", value, "firstTerm");
            return (Criteria) this;
        }

        public Criteria andFirstTermGreaterThan(Integer value) {
            addCriterion("first_term >", value, "firstTerm");
            return (Criteria) this;
        }

        public Criteria andFirstTermGreaterThanOrEqualTo(Integer value) {
            addCriterion("first_term >=", value, "firstTerm");
            return (Criteria) this;
        }

        public Criteria andFirstTermLessThan(Integer value) {
            addCriterion("first_term <", value, "firstTerm");
            return (Criteria) this;
        }

        public Criteria andFirstTermLessThanOrEqualTo(Integer value) {
            addCriterion("first_term <=", value, "firstTerm");
            return (Criteria) this;
        }

        public Criteria andFirstTermIn(List<Integer> values) {
            addCriterion("first_term in", values, "firstTerm");
            return (Criteria) this;
        }

        public Criteria andFirstTermNotIn(List<Integer> values) {
            addCriterion("first_term not in", values, "firstTerm");
            return (Criteria) this;
        }

        public Criteria andFirstTermBetween(Integer value1, Integer value2) {
            addCriterion("first_term between", value1, value2, "firstTerm");
            return (Criteria) this;
        }

        public Criteria andFirstTermNotBetween(Integer value1, Integer value2) {
            addCriterion("first_term not between", value1, value2, "firstTerm");
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

        public Criteria andTransMarkIsNull() {
            addCriterion("trans_mark is null");
            return (Criteria) this;
        }

        public Criteria andTransMarkIsNotNull() {
            addCriterion("trans_mark is not null");
            return (Criteria) this;
        }

        public Criteria andTransMarkEqualTo(String value) {
            addCriterion("trans_mark =", value, "transMark");
            return (Criteria) this;
        }

        public Criteria andTransMarkNotEqualTo(String value) {
            addCriterion("trans_mark <>", value, "transMark");
            return (Criteria) this;
        }

        public Criteria andTransMarkGreaterThan(String value) {
            addCriterion("trans_mark >", value, "transMark");
            return (Criteria) this;
        }

        public Criteria andTransMarkGreaterThanOrEqualTo(String value) {
            addCriterion("trans_mark >=", value, "transMark");
            return (Criteria) this;
        }

        public Criteria andTransMarkLessThan(String value) {
            addCriterion("trans_mark <", value, "transMark");
            return (Criteria) this;
        }

        public Criteria andTransMarkLessThanOrEqualTo(String value) {
            addCriterion("trans_mark <=", value, "transMark");
            return (Criteria) this;
        }

        public Criteria andTransMarkLike(String value) {
            addCriterion("trans_mark like", value, "transMark");
            return (Criteria) this;
        }

        public Criteria andTransMarkNotLike(String value) {
            addCriterion("trans_mark not like", value, "transMark");
            return (Criteria) this;
        }

        public Criteria andTransMarkIn(List<String> values) {
            addCriterion("trans_mark in", values, "transMark");
            return (Criteria) this;
        }

        public Criteria andTransMarkNotIn(List<String> values) {
            addCriterion("trans_mark not in", values, "transMark");
            return (Criteria) this;
        }

        public Criteria andTransMarkBetween(String value1, String value2) {
            addCriterion("trans_mark between", value1, value2, "transMark");
            return (Criteria) this;
        }

        public Criteria andTransMarkNotBetween(String value1, String value2) {
            addCriterion("trans_mark not between", value1, value2, "transMark");
            return (Criteria) this;
        }

        public Criteria andBidStatusIsNull() {
            addCriterion("bid_status is null");
            return (Criteria) this;
        }

        public Criteria andBidStatusIsNotNull() {
            addCriterion("bid_status is not null");
            return (Criteria) this;
        }

        public Criteria andBidStatusEqualTo(String value) {
            addCriterion("bid_status =", value, "bidStatus");
            return (Criteria) this;
        }

        public Criteria andBidStatusNotEqualTo(String value) {
            addCriterion("bid_status <>", value, "bidStatus");
            return (Criteria) this;
        }

        public Criteria andBidStatusGreaterThan(String value) {
            addCriterion("bid_status >", value, "bidStatus");
            return (Criteria) this;
        }

        public Criteria andBidStatusGreaterThanOrEqualTo(String value) {
            addCriterion("bid_status >=", value, "bidStatus");
            return (Criteria) this;
        }

        public Criteria andBidStatusLessThan(String value) {
            addCriterion("bid_status <", value, "bidStatus");
            return (Criteria) this;
        }

        public Criteria andBidStatusLessThanOrEqualTo(String value) {
            addCriterion("bid_status <=", value, "bidStatus");
            return (Criteria) this;
        }

        public Criteria andBidStatusLike(String value) {
            addCriterion("bid_status like", value, "bidStatus");
            return (Criteria) this;
        }

        public Criteria andBidStatusNotLike(String value) {
            addCriterion("bid_status not like", value, "bidStatus");
            return (Criteria) this;
        }

        public Criteria andBidStatusIn(List<String> values) {
            addCriterion("bid_status in", values, "bidStatus");
            return (Criteria) this;
        }

        public Criteria andBidStatusNotIn(List<String> values) {
            addCriterion("bid_status not in", values, "bidStatus");
            return (Criteria) this;
        }

        public Criteria andBidStatusBetween(String value1, String value2) {
            addCriterion("bid_status between", value1, value2, "bidStatus");
            return (Criteria) this;
        }

        public Criteria andBidStatusNotBetween(String value1, String value2) {
            addCriterion("bid_status not between", value1, value2, "bidStatus");
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