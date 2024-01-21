package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsDafyFinanceChangeRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsDafyFinanceChangeRecordExample() {
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerNameIsNull() {
            addCriterion("old_customer_manager_name is null");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerNameIsNotNull() {
            addCriterion("old_customer_manager_name is not null");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerNameEqualTo(String value) {
            addCriterion("old_customer_manager_name =", value, "oldCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerNameNotEqualTo(String value) {
            addCriterion("old_customer_manager_name <>", value, "oldCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerNameGreaterThan(String value) {
            addCriterion("old_customer_manager_name >", value, "oldCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerNameGreaterThanOrEqualTo(String value) {
            addCriterion("old_customer_manager_name >=", value, "oldCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerNameLessThan(String value) {
            addCriterion("old_customer_manager_name <", value, "oldCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerNameLessThanOrEqualTo(String value) {
            addCriterion("old_customer_manager_name <=", value, "oldCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerNameLike(String value) {
            addCriterion("old_customer_manager_name like", value, "oldCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerNameNotLike(String value) {
            addCriterion("old_customer_manager_name not like", value, "oldCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerNameIn(List<String> values) {
            addCriterion("old_customer_manager_name in", values, "oldCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerNameNotIn(List<String> values) {
            addCriterion("old_customer_manager_name not in", values, "oldCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerNameBetween(String value1, String value2) {
            addCriterion("old_customer_manager_name between", value1, value2, "oldCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerNameNotBetween(String value1, String value2) {
            addCriterion("old_customer_manager_name not between", value1, value2, "oldCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerNameIsNull() {
            addCriterion("new_customer_manager_name is null");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerNameIsNotNull() {
            addCriterion("new_customer_manager_name is not null");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerNameEqualTo(String value) {
            addCriterion("new_customer_manager_name =", value, "newCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerNameNotEqualTo(String value) {
            addCriterion("new_customer_manager_name <>", value, "newCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerNameGreaterThan(String value) {
            addCriterion("new_customer_manager_name >", value, "newCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerNameGreaterThanOrEqualTo(String value) {
            addCriterion("new_customer_manager_name >=", value, "newCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerNameLessThan(String value) {
            addCriterion("new_customer_manager_name <", value, "newCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerNameLessThanOrEqualTo(String value) {
            addCriterion("new_customer_manager_name <=", value, "newCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerNameLike(String value) {
            addCriterion("new_customer_manager_name like", value, "newCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerNameNotLike(String value) {
            addCriterion("new_customer_manager_name not like", value, "newCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerNameIn(List<String> values) {
            addCriterion("new_customer_manager_name in", values, "newCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerNameNotIn(List<String> values) {
            addCriterion("new_customer_manager_name not in", values, "newCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerNameBetween(String value1, String value2) {
            addCriterion("new_customer_manager_name between", value1, value2, "newCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerNameNotBetween(String value1, String value2) {
            addCriterion("new_customer_manager_name not between", value1, value2, "newCustomerManagerName");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerDafyIdIsNull() {
            addCriterion("old_customer_manager_dafy_id is null");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerDafyIdIsNotNull() {
            addCriterion("old_customer_manager_dafy_id is not null");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerDafyIdEqualTo(Integer value) {
            addCriterion("old_customer_manager_dafy_id =", value, "oldCustomerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerDafyIdNotEqualTo(Integer value) {
            addCriterion("old_customer_manager_dafy_id <>", value, "oldCustomerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerDafyIdGreaterThan(Integer value) {
            addCriterion("old_customer_manager_dafy_id >", value, "oldCustomerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerDafyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("old_customer_manager_dafy_id >=", value, "oldCustomerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerDafyIdLessThan(Integer value) {
            addCriterion("old_customer_manager_dafy_id <", value, "oldCustomerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerDafyIdLessThanOrEqualTo(Integer value) {
            addCriterion("old_customer_manager_dafy_id <=", value, "oldCustomerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerDafyIdIn(List<Integer> values) {
            addCriterion("old_customer_manager_dafy_id in", values, "oldCustomerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerDafyIdNotIn(List<Integer> values) {
            addCriterion("old_customer_manager_dafy_id not in", values, "oldCustomerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerDafyIdBetween(Integer value1, Integer value2) {
            addCriterion("old_customer_manager_dafy_id between", value1, value2, "oldCustomerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andOldCustomerManagerDafyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("old_customer_manager_dafy_id not between", value1, value2, "oldCustomerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerDafyIdIsNull() {
            addCriterion("new_customer_manager_dafy_id is null");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerDafyIdIsNotNull() {
            addCriterion("new_customer_manager_dafy_id is not null");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerDafyIdEqualTo(Integer value) {
            addCriterion("new_customer_manager_dafy_id =", value, "newCustomerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerDafyIdNotEqualTo(Integer value) {
            addCriterion("new_customer_manager_dafy_id <>", value, "newCustomerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerDafyIdGreaterThan(Integer value) {
            addCriterion("new_customer_manager_dafy_id >", value, "newCustomerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerDafyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("new_customer_manager_dafy_id >=", value, "newCustomerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerDafyIdLessThan(Integer value) {
            addCriterion("new_customer_manager_dafy_id <", value, "newCustomerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerDafyIdLessThanOrEqualTo(Integer value) {
            addCriterion("new_customer_manager_dafy_id <=", value, "newCustomerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerDafyIdIn(List<Integer> values) {
            addCriterion("new_customer_manager_dafy_id in", values, "newCustomerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerDafyIdNotIn(List<Integer> values) {
            addCriterion("new_customer_manager_dafy_id not in", values, "newCustomerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerDafyIdBetween(Integer value1, Integer value2) {
            addCriterion("new_customer_manager_dafy_id between", value1, value2, "newCustomerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andNewCustomerManagerDafyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("new_customer_manager_dafy_id not between", value1, value2, "newCustomerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptIdIsNull() {
            addCriterion("old_dafy_dept_id is null");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptIdIsNotNull() {
            addCriterion("old_dafy_dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptIdEqualTo(Integer value) {
            addCriterion("old_dafy_dept_id =", value, "oldDafyDeptId");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptIdNotEqualTo(Integer value) {
            addCriterion("old_dafy_dept_id <>", value, "oldDafyDeptId");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptIdGreaterThan(Integer value) {
            addCriterion("old_dafy_dept_id >", value, "oldDafyDeptId");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("old_dafy_dept_id >=", value, "oldDafyDeptId");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptIdLessThan(Integer value) {
            addCriterion("old_dafy_dept_id <", value, "oldDafyDeptId");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptIdLessThanOrEqualTo(Integer value) {
            addCriterion("old_dafy_dept_id <=", value, "oldDafyDeptId");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptIdIn(List<Integer> values) {
            addCriterion("old_dafy_dept_id in", values, "oldDafyDeptId");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptIdNotIn(List<Integer> values) {
            addCriterion("old_dafy_dept_id not in", values, "oldDafyDeptId");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptIdBetween(Integer value1, Integer value2) {
            addCriterion("old_dafy_dept_id between", value1, value2, "oldDafyDeptId");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptIdNotBetween(Integer value1, Integer value2) {
            addCriterion("old_dafy_dept_id not between", value1, value2, "oldDafyDeptId");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptIdIsNull() {
            addCriterion("new_dafy_dept_id is null");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptIdIsNotNull() {
            addCriterion("new_dafy_dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptIdEqualTo(Integer value) {
            addCriterion("new_dafy_dept_id =", value, "newDafyDeptId");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptIdNotEqualTo(Integer value) {
            addCriterion("new_dafy_dept_id <>", value, "newDafyDeptId");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptIdGreaterThan(Integer value) {
            addCriterion("new_dafy_dept_id >", value, "newDafyDeptId");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("new_dafy_dept_id >=", value, "newDafyDeptId");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptIdLessThan(Integer value) {
            addCriterion("new_dafy_dept_id <", value, "newDafyDeptId");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptIdLessThanOrEqualTo(Integer value) {
            addCriterion("new_dafy_dept_id <=", value, "newDafyDeptId");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptIdIn(List<Integer> values) {
            addCriterion("new_dafy_dept_id in", values, "newDafyDeptId");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptIdNotIn(List<Integer> values) {
            addCriterion("new_dafy_dept_id not in", values, "newDafyDeptId");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptIdBetween(Integer value1, Integer value2) {
            addCriterion("new_dafy_dept_id between", value1, value2, "newDafyDeptId");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptIdNotBetween(Integer value1, Integer value2) {
            addCriterion("new_dafy_dept_id not between", value1, value2, "newDafyDeptId");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptCodeIsNull() {
            addCriterion("old_dafy_dept_code is null");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptCodeIsNotNull() {
            addCriterion("old_dafy_dept_code is not null");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptCodeEqualTo(String value) {
            addCriterion("old_dafy_dept_code =", value, "oldDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptCodeNotEqualTo(String value) {
            addCriterion("old_dafy_dept_code <>", value, "oldDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptCodeGreaterThan(String value) {
            addCriterion("old_dafy_dept_code >", value, "oldDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptCodeGreaterThanOrEqualTo(String value) {
            addCriterion("old_dafy_dept_code >=", value, "oldDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptCodeLessThan(String value) {
            addCriterion("old_dafy_dept_code <", value, "oldDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptCodeLessThanOrEqualTo(String value) {
            addCriterion("old_dafy_dept_code <=", value, "oldDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptCodeLike(String value) {
            addCriterion("old_dafy_dept_code like", value, "oldDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptCodeNotLike(String value) {
            addCriterion("old_dafy_dept_code not like", value, "oldDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptCodeIn(List<String> values) {
            addCriterion("old_dafy_dept_code in", values, "oldDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptCodeNotIn(List<String> values) {
            addCriterion("old_dafy_dept_code not in", values, "oldDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptCodeBetween(String value1, String value2) {
            addCriterion("old_dafy_dept_code between", value1, value2, "oldDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptCodeNotBetween(String value1, String value2) {
            addCriterion("old_dafy_dept_code not between", value1, value2, "oldDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptCodeIsNull() {
            addCriterion("new_dafy_dept_code is null");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptCodeIsNotNull() {
            addCriterion("new_dafy_dept_code is not null");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptCodeEqualTo(String value) {
            addCriterion("new_dafy_dept_code =", value, "newDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptCodeNotEqualTo(String value) {
            addCriterion("new_dafy_dept_code <>", value, "newDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptCodeGreaterThan(String value) {
            addCriterion("new_dafy_dept_code >", value, "newDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptCodeGreaterThanOrEqualTo(String value) {
            addCriterion("new_dafy_dept_code >=", value, "newDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptCodeLessThan(String value) {
            addCriterion("new_dafy_dept_code <", value, "newDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptCodeLessThanOrEqualTo(String value) {
            addCriterion("new_dafy_dept_code <=", value, "newDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptCodeLike(String value) {
            addCriterion("new_dafy_dept_code like", value, "newDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptCodeNotLike(String value) {
            addCriterion("new_dafy_dept_code not like", value, "newDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptCodeIn(List<String> values) {
            addCriterion("new_dafy_dept_code in", values, "newDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptCodeNotIn(List<String> values) {
            addCriterion("new_dafy_dept_code not in", values, "newDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptCodeBetween(String value1, String value2) {
            addCriterion("new_dafy_dept_code between", value1, value2, "newDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptCodeNotBetween(String value1, String value2) {
            addCriterion("new_dafy_dept_code not between", value1, value2, "newDafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptNameIsNull() {
            addCriterion("old_dafy_dept_name is null");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptNameIsNotNull() {
            addCriterion("old_dafy_dept_name is not null");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptNameEqualTo(String value) {
            addCriterion("old_dafy_dept_name =", value, "oldDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptNameNotEqualTo(String value) {
            addCriterion("old_dafy_dept_name <>", value, "oldDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptNameGreaterThan(String value) {
            addCriterion("old_dafy_dept_name >", value, "oldDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptNameGreaterThanOrEqualTo(String value) {
            addCriterion("old_dafy_dept_name >=", value, "oldDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptNameLessThan(String value) {
            addCriterion("old_dafy_dept_name <", value, "oldDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptNameLessThanOrEqualTo(String value) {
            addCriterion("old_dafy_dept_name <=", value, "oldDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptNameLike(String value) {
            addCriterion("old_dafy_dept_name like", value, "oldDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptNameNotLike(String value) {
            addCriterion("old_dafy_dept_name not like", value, "oldDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptNameIn(List<String> values) {
            addCriterion("old_dafy_dept_name in", values, "oldDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptNameNotIn(List<String> values) {
            addCriterion("old_dafy_dept_name not in", values, "oldDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptNameBetween(String value1, String value2) {
            addCriterion("old_dafy_dept_name between", value1, value2, "oldDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andOldDafyDeptNameNotBetween(String value1, String value2) {
            addCriterion("old_dafy_dept_name not between", value1, value2, "oldDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptNameIsNull() {
            addCriterion("new_dafy_dept_name is null");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptNameIsNotNull() {
            addCriterion("new_dafy_dept_name is not null");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptNameEqualTo(String value) {
            addCriterion("new_dafy_dept_name =", value, "newDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptNameNotEqualTo(String value) {
            addCriterion("new_dafy_dept_name <>", value, "newDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptNameGreaterThan(String value) {
            addCriterion("new_dafy_dept_name >", value, "newDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptNameGreaterThanOrEqualTo(String value) {
            addCriterion("new_dafy_dept_name >=", value, "newDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptNameLessThan(String value) {
            addCriterion("new_dafy_dept_name <", value, "newDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptNameLessThanOrEqualTo(String value) {
            addCriterion("new_dafy_dept_name <=", value, "newDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptNameLike(String value) {
            addCriterion("new_dafy_dept_name like", value, "newDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptNameNotLike(String value) {
            addCriterion("new_dafy_dept_name not like", value, "newDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptNameIn(List<String> values) {
            addCriterion("new_dafy_dept_name in", values, "newDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptNameNotIn(List<String> values) {
            addCriterion("new_dafy_dept_name not in", values, "newDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptNameBetween(String value1, String value2) {
            addCriterion("new_dafy_dept_name between", value1, value2, "newDafyDeptName");
            return (Criteria) this;
        }

        public Criteria andNewDafyDeptNameNotBetween(String value1, String value2) {
            addCriterion("new_dafy_dept_name not between", value1, value2, "newDafyDeptName");
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

        public Criteria andOpUserIdIsNull() {
            addCriterion("op_user_id is null");
            return (Criteria) this;
        }

        public Criteria andOpUserIdIsNotNull() {
            addCriterion("op_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andOpUserIdEqualTo(Integer value) {
            addCriterion("op_user_id =", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdNotEqualTo(Integer value) {
            addCriterion("op_user_id <>", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdGreaterThan(Integer value) {
            addCriterion("op_user_id >", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("op_user_id >=", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdLessThan(Integer value) {
            addCriterion("op_user_id <", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("op_user_id <=", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdIn(List<Integer> values) {
            addCriterion("op_user_id in", values, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdNotIn(List<Integer> values) {
            addCriterion("op_user_id not in", values, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdBetween(Integer value1, Integer value2) {
            addCriterion("op_user_id between", value1, value2, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("op_user_id not between", value1, value2, "opUserId");
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