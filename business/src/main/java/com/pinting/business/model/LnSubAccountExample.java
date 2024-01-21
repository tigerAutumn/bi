package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class LnSubAccountExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LnSubAccountExample() {
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

        public Criteria andAccountIdIsNull() {
            addCriterion("account_id is null");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNotNull() {
            addCriterion("account_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccountIdEqualTo(Integer value) {
            addCriterion("account_id =", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotEqualTo(Integer value) {
            addCriterion("account_id <>", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThan(Integer value) {
            addCriterion("account_id >", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("account_id >=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThan(Integer value) {
            addCriterion("account_id <", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThanOrEqualTo(Integer value) {
            addCriterion("account_id <=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdIn(List<Integer> values) {
            addCriterion("account_id in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotIn(List<Integer> values) {
            addCriterion("account_id not in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdBetween(Integer value1, Integer value2) {
            addCriterion("account_id between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotBetween(Integer value1, Integer value2) {
            addCriterion("account_id not between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andAccountTypeIsNull() {
            addCriterion("account_type is null");
            return (Criteria) this;
        }

        public Criteria andAccountTypeIsNotNull() {
            addCriterion("account_type is not null");
            return (Criteria) this;
        }

        public Criteria andAccountTypeEqualTo(String value) {
            addCriterion("account_type =", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeNotEqualTo(String value) {
            addCriterion("account_type <>", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeGreaterThan(String value) {
            addCriterion("account_type >", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeGreaterThanOrEqualTo(String value) {
            addCriterion("account_type >=", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeLessThan(String value) {
            addCriterion("account_type <", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeLessThanOrEqualTo(String value) {
            addCriterion("account_type <=", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeLike(String value) {
            addCriterion("account_type like", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeNotLike(String value) {
            addCriterion("account_type not like", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeIn(List<String> values) {
            addCriterion("account_type in", values, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeNotIn(List<String> values) {
            addCriterion("account_type not in", values, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeBetween(String value1, String value2) {
            addCriterion("account_type between", value1, value2, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeNotBetween(String value1, String value2) {
            addCriterion("account_type not between", value1, value2, "accountType");
            return (Criteria) this;
        }

        public Criteria andProductCodeIsNull() {
            addCriterion("product_code is null");
            return (Criteria) this;
        }

        public Criteria andProductCodeIsNotNull() {
            addCriterion("product_code is not null");
            return (Criteria) this;
        }

        public Criteria andProductCodeEqualTo(String value) {
            addCriterion("product_code =", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotEqualTo(String value) {
            addCriterion("product_code <>", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeGreaterThan(String value) {
            addCriterion("product_code >", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeGreaterThanOrEqualTo(String value) {
            addCriterion("product_code >=", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLessThan(String value) {
            addCriterion("product_code <", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLessThanOrEqualTo(String value) {
            addCriterion("product_code <=", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLike(String value) {
            addCriterion("product_code like", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotLike(String value) {
            addCriterion("product_code not like", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeIn(List<String> values) {
            addCriterion("product_code in", values, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotIn(List<String> values) {
            addCriterion("product_code not in", values, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeBetween(String value1, String value2) {
            addCriterion("product_code between", value1, value2, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotBetween(String value1, String value2) {
            addCriterion("product_code not between", value1, value2, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNull() {
            addCriterion("product_name is null");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNotNull() {
            addCriterion("product_name is not null");
            return (Criteria) this;
        }

        public Criteria andProductNameEqualTo(String value) {
            addCriterion("product_name =", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotEqualTo(String value) {
            addCriterion("product_name <>", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThan(String value) {
            addCriterion("product_name >", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThanOrEqualTo(String value) {
            addCriterion("product_name >=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThan(String value) {
            addCriterion("product_name <", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThanOrEqualTo(String value) {
            addCriterion("product_name <=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLike(String value) {
            addCriterion("product_name like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotLike(String value) {
            addCriterion("product_name not like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameIn(List<String> values) {
            addCriterion("product_name in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotIn(List<String> values) {
            addCriterion("product_name not in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameBetween(String value1, String value2) {
            addCriterion("product_name between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotBetween(String value1, String value2) {
            addCriterion("product_name not between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andProductRateIsNull() {
            addCriterion("product_rate is null");
            return (Criteria) this;
        }

        public Criteria andProductRateIsNotNull() {
            addCriterion("product_rate is not null");
            return (Criteria) this;
        }

        public Criteria andProductRateEqualTo(Double value) {
            addCriterion("product_rate =", value, "productRate");
            return (Criteria) this;
        }

        public Criteria andProductRateNotEqualTo(Double value) {
            addCriterion("product_rate <>", value, "productRate");
            return (Criteria) this;
        }

        public Criteria andProductRateGreaterThan(Double value) {
            addCriterion("product_rate >", value, "productRate");
            return (Criteria) this;
        }

        public Criteria andProductRateGreaterThanOrEqualTo(Double value) {
            addCriterion("product_rate >=", value, "productRate");
            return (Criteria) this;
        }

        public Criteria andProductRateLessThan(Double value) {
            addCriterion("product_rate <", value, "productRate");
            return (Criteria) this;
        }

        public Criteria andProductRateLessThanOrEqualTo(Double value) {
            addCriterion("product_rate <=", value, "productRate");
            return (Criteria) this;
        }

        public Criteria andProductRateIn(List<Double> values) {
            addCriterion("product_rate in", values, "productRate");
            return (Criteria) this;
        }

        public Criteria andProductRateNotIn(List<Double> values) {
            addCriterion("product_rate not in", values, "productRate");
            return (Criteria) this;
        }

        public Criteria andProductRateBetween(Double value1, Double value2) {
            addCriterion("product_rate between", value1, value2, "productRate");
            return (Criteria) this;
        }

        public Criteria andProductRateNotBetween(Double value1, Double value2) {
            addCriterion("product_rate not between", value1, value2, "productRate");
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

        public Criteria andCheckStatusIsNull() {
            addCriterion("check_status is null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNotNull() {
            addCriterion("check_status is not null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusEqualTo(String value) {
            addCriterion("check_status =", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotEqualTo(String value) {
            addCriterion("check_status <>", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThan(String value) {
            addCriterion("check_status >", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThanOrEqualTo(String value) {
            addCriterion("check_status >=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThan(String value) {
            addCriterion("check_status <", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThanOrEqualTo(String value) {
            addCriterion("check_status <=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLike(String value) {
            addCriterion("check_status like", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotLike(String value) {
            addCriterion("check_status not like", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIn(List<String> values) {
            addCriterion("check_status in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotIn(List<String> values) {
            addCriterion("check_status not in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusBetween(String value1, String value2) {
            addCriterion("check_status between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotBetween(String value1, String value2) {
            addCriterion("check_status not between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andInterestBeginDateIsNull() {
            addCriterion("interest_begin_date is null");
            return (Criteria) this;
        }

        public Criteria andInterestBeginDateIsNotNull() {
            addCriterion("interest_begin_date is not null");
            return (Criteria) this;
        }

        public Criteria andInterestBeginDateEqualTo(Date value) {
            addCriterionForJDBCDate("interest_begin_date =", value, "interestBeginDate");
            return (Criteria) this;
        }

        public Criteria andInterestBeginDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("interest_begin_date <>", value, "interestBeginDate");
            return (Criteria) this;
        }

        public Criteria andInterestBeginDateGreaterThan(Date value) {
            addCriterionForJDBCDate("interest_begin_date >", value, "interestBeginDate");
            return (Criteria) this;
        }

        public Criteria andInterestBeginDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("interest_begin_date >=", value, "interestBeginDate");
            return (Criteria) this;
        }

        public Criteria andInterestBeginDateLessThan(Date value) {
            addCriterionForJDBCDate("interest_begin_date <", value, "interestBeginDate");
            return (Criteria) this;
        }

        public Criteria andInterestBeginDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("interest_begin_date <=", value, "interestBeginDate");
            return (Criteria) this;
        }

        public Criteria andInterestBeginDateIn(List<Date> values) {
            addCriterionForJDBCDate("interest_begin_date in", values, "interestBeginDate");
            return (Criteria) this;
        }

        public Criteria andInterestBeginDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("interest_begin_date not in", values, "interestBeginDate");
            return (Criteria) this;
        }

        public Criteria andInterestBeginDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("interest_begin_date between", value1, value2, "interestBeginDate");
            return (Criteria) this;
        }

        public Criteria andInterestBeginDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("interest_begin_date not between", value1, value2, "interestBeginDate");
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

        public Criteria andLastCalInterestDateIsNull() {
            addCriterion("last_cal__interest_date is null");
            return (Criteria) this;
        }

        public Criteria andLastCalInterestDateIsNotNull() {
            addCriterion("last_cal__interest_date is not null");
            return (Criteria) this;
        }

        public Criteria andLastCalInterestDateEqualTo(Date value) {
            addCriterionForJDBCDate("last_cal__interest_date =", value, "lastCalInterestDate");
            return (Criteria) this;
        }

        public Criteria andLastCalInterestDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("last_cal__interest_date <>", value, "lastCalInterestDate");
            return (Criteria) this;
        }

        public Criteria andLastCalInterestDateGreaterThan(Date value) {
            addCriterionForJDBCDate("last_cal__interest_date >", value, "lastCalInterestDate");
            return (Criteria) this;
        }

        public Criteria andLastCalInterestDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("last_cal__interest_date >=", value, "lastCalInterestDate");
            return (Criteria) this;
        }

        public Criteria andLastCalInterestDateLessThan(Date value) {
            addCriterionForJDBCDate("last_cal__interest_date <", value, "lastCalInterestDate");
            return (Criteria) this;
        }

        public Criteria andLastCalInterestDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("last_cal__interest_date <=", value, "lastCalInterestDate");
            return (Criteria) this;
        }

        public Criteria andLastCalInterestDateIn(List<Date> values) {
            addCriterionForJDBCDate("last_cal__interest_date in", values, "lastCalInterestDate");
            return (Criteria) this;
        }

        public Criteria andLastCalInterestDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("last_cal__interest_date not in", values, "lastCalInterestDate");
            return (Criteria) this;
        }

        public Criteria andLastCalInterestDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("last_cal__interest_date between", value1, value2, "lastCalInterestDate");
            return (Criteria) this;
        }

        public Criteria andLastCalInterestDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("last_cal__interest_date not between", value1, value2, "lastCalInterestDate");
            return (Criteria) this;
        }

        public Criteria andLastFinishInterestDateIsNull() {
            addCriterion("last_finish_interest_date is null");
            return (Criteria) this;
        }

        public Criteria andLastFinishInterestDateIsNotNull() {
            addCriterion("last_finish_interest_date is not null");
            return (Criteria) this;
        }

        public Criteria andLastFinishInterestDateEqualTo(Date value) {
            addCriterionForJDBCDate("last_finish_interest_date =", value, "lastFinishInterestDate");
            return (Criteria) this;
        }

        public Criteria andLastFinishInterestDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("last_finish_interest_date <>", value, "lastFinishInterestDate");
            return (Criteria) this;
        }

        public Criteria andLastFinishInterestDateGreaterThan(Date value) {
            addCriterionForJDBCDate("last_finish_interest_date >", value, "lastFinishInterestDate");
            return (Criteria) this;
        }

        public Criteria andLastFinishInterestDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("last_finish_interest_date >=", value, "lastFinishInterestDate");
            return (Criteria) this;
        }

        public Criteria andLastFinishInterestDateLessThan(Date value) {
            addCriterionForJDBCDate("last_finish_interest_date <", value, "lastFinishInterestDate");
            return (Criteria) this;
        }

        public Criteria andLastFinishInterestDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("last_finish_interest_date <=", value, "lastFinishInterestDate");
            return (Criteria) this;
        }

        public Criteria andLastFinishInterestDateIn(List<Date> values) {
            addCriterionForJDBCDate("last_finish_interest_date in", values, "lastFinishInterestDate");
            return (Criteria) this;
        }

        public Criteria andLastFinishInterestDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("last_finish_interest_date not in", values, "lastFinishInterestDate");
            return (Criteria) this;
        }

        public Criteria andLastFinishInterestDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("last_finish_interest_date between", value1, value2, "lastFinishInterestDate");
            return (Criteria) this;
        }

        public Criteria andLastFinishInterestDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("last_finish_interest_date not between", value1, value2, "lastFinishInterestDate");
            return (Criteria) this;
        }

        public Criteria andAccumulationInerestIsNull() {
            addCriterion("accumulation_inerest is null");
            return (Criteria) this;
        }

        public Criteria andAccumulationInerestIsNotNull() {
            addCriterion("accumulation_inerest is not null");
            return (Criteria) this;
        }

        public Criteria andAccumulationInerestEqualTo(Double value) {
            addCriterion("accumulation_inerest =", value, "accumulationInerest");
            return (Criteria) this;
        }

        public Criteria andAccumulationInerestNotEqualTo(Double value) {
            addCriterion("accumulation_inerest <>", value, "accumulationInerest");
            return (Criteria) this;
        }

        public Criteria andAccumulationInerestGreaterThan(Double value) {
            addCriterion("accumulation_inerest >", value, "accumulationInerest");
            return (Criteria) this;
        }

        public Criteria andAccumulationInerestGreaterThanOrEqualTo(Double value) {
            addCriterion("accumulation_inerest >=", value, "accumulationInerest");
            return (Criteria) this;
        }

        public Criteria andAccumulationInerestLessThan(Double value) {
            addCriterion("accumulation_inerest <", value, "accumulationInerest");
            return (Criteria) this;
        }

        public Criteria andAccumulationInerestLessThanOrEqualTo(Double value) {
            addCriterion("accumulation_inerest <=", value, "accumulationInerest");
            return (Criteria) this;
        }

        public Criteria andAccumulationInerestIn(List<Double> values) {
            addCriterion("accumulation_inerest in", values, "accumulationInerest");
            return (Criteria) this;
        }

        public Criteria andAccumulationInerestNotIn(List<Double> values) {
            addCriterion("accumulation_inerest not in", values, "accumulationInerest");
            return (Criteria) this;
        }

        public Criteria andAccumulationInerestBetween(Double value1, Double value2) {
            addCriterion("accumulation_inerest between", value1, value2, "accumulationInerest");
            return (Criteria) this;
        }

        public Criteria andAccumulationInerestNotBetween(Double value1, Double value2) {
            addCriterion("accumulation_inerest not between", value1, value2, "accumulationInerest");
            return (Criteria) this;
        }

        public Criteria andOpenTimeIsNull() {
            addCriterion("open_time is null");
            return (Criteria) this;
        }

        public Criteria andOpenTimeIsNotNull() {
            addCriterion("open_time is not null");
            return (Criteria) this;
        }

        public Criteria andOpenTimeEqualTo(Date value) {
            addCriterion("open_time =", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeNotEqualTo(Date value) {
            addCriterion("open_time <>", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeGreaterThan(Date value) {
            addCriterion("open_time >", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("open_time >=", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeLessThan(Date value) {
            addCriterion("open_time <", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeLessThanOrEqualTo(Date value) {
            addCriterion("open_time <=", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeIn(List<Date> values) {
            addCriterion("open_time in", values, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeNotIn(List<Date> values) {
            addCriterion("open_time not in", values, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeBetween(Date value1, Date value2) {
            addCriterion("open_time between", value1, value2, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeNotBetween(Date value1, Date value2) {
            addCriterion("open_time not between", value1, value2, "openTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeIsNull() {
            addCriterion("close_time is null");
            return (Criteria) this;
        }

        public Criteria andCloseTimeIsNotNull() {
            addCriterion("close_time is not null");
            return (Criteria) this;
        }

        public Criteria andCloseTimeEqualTo(Date value) {
            addCriterion("close_time =", value, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeNotEqualTo(Date value) {
            addCriterion("close_time <>", value, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeGreaterThan(Date value) {
            addCriterion("close_time >", value, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("close_time >=", value, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeLessThan(Date value) {
            addCriterion("close_time <", value, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeLessThanOrEqualTo(Date value) {
            addCriterion("close_time <=", value, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeIn(List<Date> values) {
            addCriterion("close_time in", values, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeNotIn(List<Date> values) {
            addCriterion("close_time not in", values, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeBetween(Date value1, Date value2) {
            addCriterion("close_time between", value1, value2, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeNotBetween(Date value1, Date value2) {
            addCriterion("close_time not between", value1, value2, "closeTime");
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