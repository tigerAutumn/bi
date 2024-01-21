package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsFinanceDafyRelationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsFinanceDafyRelationExample() {
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

        public Criteria andCustomerManagerDafyIdIsNull() {
            addCriterion("customer_manager_dafy_id is null");
            return (Criteria) this;
        }

        public Criteria andCustomerManagerDafyIdIsNotNull() {
            addCriterion("customer_manager_dafy_id is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerManagerDafyIdEqualTo(Integer value) {
            addCriterion("customer_manager_dafy_id =", value, "customerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andCustomerManagerDafyIdNotEqualTo(Integer value) {
            addCriterion("customer_manager_dafy_id <>", value, "customerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andCustomerManagerDafyIdGreaterThan(Integer value) {
            addCriterion("customer_manager_dafy_id >", value, "customerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andCustomerManagerDafyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("customer_manager_dafy_id >=", value, "customerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andCustomerManagerDafyIdLessThan(Integer value) {
            addCriterion("customer_manager_dafy_id <", value, "customerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andCustomerManagerDafyIdLessThanOrEqualTo(Integer value) {
            addCriterion("customer_manager_dafy_id <=", value, "customerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andCustomerManagerDafyIdIn(List<Integer> values) {
            addCriterion("customer_manager_dafy_id in", values, "customerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andCustomerManagerDafyIdNotIn(List<Integer> values) {
            addCriterion("customer_manager_dafy_id not in", values, "customerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andCustomerManagerDafyIdBetween(Integer value1, Integer value2) {
            addCriterion("customer_manager_dafy_id between", value1, value2, "customerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andCustomerManagerDafyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("customer_manager_dafy_id not between", value1, value2, "customerManagerDafyId");
            return (Criteria) this;
        }

        public Criteria andDafyDeptIdIsNull() {
            addCriterion("dafy_dept_id is null");
            return (Criteria) this;
        }

        public Criteria andDafyDeptIdIsNotNull() {
            addCriterion("dafy_dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andDafyDeptIdEqualTo(Integer value) {
            addCriterion("dafy_dept_id =", value, "dafyDeptId");
            return (Criteria) this;
        }

        public Criteria andDafyDeptIdNotEqualTo(Integer value) {
            addCriterion("dafy_dept_id <>", value, "dafyDeptId");
            return (Criteria) this;
        }

        public Criteria andDafyDeptIdGreaterThan(Integer value) {
            addCriterion("dafy_dept_id >", value, "dafyDeptId");
            return (Criteria) this;
        }

        public Criteria andDafyDeptIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dafy_dept_id >=", value, "dafyDeptId");
            return (Criteria) this;
        }

        public Criteria andDafyDeptIdLessThan(Integer value) {
            addCriterion("dafy_dept_id <", value, "dafyDeptId");
            return (Criteria) this;
        }

        public Criteria andDafyDeptIdLessThanOrEqualTo(Integer value) {
            addCriterion("dafy_dept_id <=", value, "dafyDeptId");
            return (Criteria) this;
        }

        public Criteria andDafyDeptIdIn(List<Integer> values) {
            addCriterion("dafy_dept_id in", values, "dafyDeptId");
            return (Criteria) this;
        }

        public Criteria andDafyDeptIdNotIn(List<Integer> values) {
            addCriterion("dafy_dept_id not in", values, "dafyDeptId");
            return (Criteria) this;
        }

        public Criteria andDafyDeptIdBetween(Integer value1, Integer value2) {
            addCriterion("dafy_dept_id between", value1, value2, "dafyDeptId");
            return (Criteria) this;
        }

        public Criteria andDafyDeptIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dafy_dept_id not between", value1, value2, "dafyDeptId");
            return (Criteria) this;
        }

        public Criteria andDafyDeptCodeIsNull() {
            addCriterion("dafy_dept_code is null");
            return (Criteria) this;
        }

        public Criteria andDafyDeptCodeIsNotNull() {
            addCriterion("dafy_dept_code is not null");
            return (Criteria) this;
        }

        public Criteria andDafyDeptCodeEqualTo(String value) {
            addCriterion("dafy_dept_code =", value, "dafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andDafyDeptCodeNotEqualTo(String value) {
            addCriterion("dafy_dept_code <>", value, "dafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andDafyDeptCodeGreaterThan(String value) {
            addCriterion("dafy_dept_code >", value, "dafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andDafyDeptCodeGreaterThanOrEqualTo(String value) {
            addCriterion("dafy_dept_code >=", value, "dafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andDafyDeptCodeLessThan(String value) {
            addCriterion("dafy_dept_code <", value, "dafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andDafyDeptCodeLessThanOrEqualTo(String value) {
            addCriterion("dafy_dept_code <=", value, "dafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andDafyDeptCodeLike(String value) {
            addCriterion("dafy_dept_code like", value, "dafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andDafyDeptCodeNotLike(String value) {
            addCriterion("dafy_dept_code not like", value, "dafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andDafyDeptCodeIn(List<String> values) {
            addCriterion("dafy_dept_code in", values, "dafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andDafyDeptCodeNotIn(List<String> values) {
            addCriterion("dafy_dept_code not in", values, "dafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andDafyDeptCodeBetween(String value1, String value2) {
            addCriterion("dafy_dept_code between", value1, value2, "dafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andDafyDeptCodeNotBetween(String value1, String value2) {
            addCriterion("dafy_dept_code not between", value1, value2, "dafyDeptCode");
            return (Criteria) this;
        }

        public Criteria andDafyDeptNameIsNull() {
            addCriterion("dafy_dept_name is null");
            return (Criteria) this;
        }

        public Criteria andDafyDeptNameIsNotNull() {
            addCriterion("dafy_dept_name is not null");
            return (Criteria) this;
        }

        public Criteria andDafyDeptNameEqualTo(String value) {
            addCriterion("dafy_dept_name =", value, "dafyDeptName");
            return (Criteria) this;
        }

        public Criteria andDafyDeptNameNotEqualTo(String value) {
            addCriterion("dafy_dept_name <>", value, "dafyDeptName");
            return (Criteria) this;
        }

        public Criteria andDafyDeptNameGreaterThan(String value) {
            addCriterion("dafy_dept_name >", value, "dafyDeptName");
            return (Criteria) this;
        }

        public Criteria andDafyDeptNameGreaterThanOrEqualTo(String value) {
            addCriterion("dafy_dept_name >=", value, "dafyDeptName");
            return (Criteria) this;
        }

        public Criteria andDafyDeptNameLessThan(String value) {
            addCriterion("dafy_dept_name <", value, "dafyDeptName");
            return (Criteria) this;
        }

        public Criteria andDafyDeptNameLessThanOrEqualTo(String value) {
            addCriterion("dafy_dept_name <=", value, "dafyDeptName");
            return (Criteria) this;
        }

        public Criteria andDafyDeptNameLike(String value) {
            addCriterion("dafy_dept_name like", value, "dafyDeptName");
            return (Criteria) this;
        }

        public Criteria andDafyDeptNameNotLike(String value) {
            addCriterion("dafy_dept_name not like", value, "dafyDeptName");
            return (Criteria) this;
        }

        public Criteria andDafyDeptNameIn(List<String> values) {
            addCriterion("dafy_dept_name in", values, "dafyDeptName");
            return (Criteria) this;
        }

        public Criteria andDafyDeptNameNotIn(List<String> values) {
            addCriterion("dafy_dept_name not in", values, "dafyDeptName");
            return (Criteria) this;
        }

        public Criteria andDafyDeptNameBetween(String value1, String value2) {
            addCriterion("dafy_dept_name between", value1, value2, "dafyDeptName");
            return (Criteria) this;
        }

        public Criteria andDafyDeptNameNotBetween(String value1, String value2) {
            addCriterion("dafy_dept_name not between", value1, value2, "dafyDeptName");
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