package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsRepayJnlExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsRepayJnlExample() {
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

        public Criteria andCustomerIdIsNull() {
            addCriterion("customer_id is null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNotNull() {
            addCriterion("customer_id is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdEqualTo(String value) {
            addCriterion("customer_id =", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotEqualTo(String value) {
            addCriterion("customer_id <>", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThan(String value) {
            addCriterion("customer_id >", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThanOrEqualTo(String value) {
            addCriterion("customer_id >=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThan(String value) {
            addCriterion("customer_id <", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThanOrEqualTo(String value) {
            addCriterion("customer_id <=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLike(String value) {
            addCriterion("customer_id like", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotLike(String value) {
            addCriterion("customer_id not like", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIn(List<String> values) {
            addCriterion("customer_id in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotIn(List<String> values) {
            addCriterion("customer_id not in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdBetween(String value1, String value2) {
            addCriterion("customer_id between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotBetween(String value1, String value2) {
            addCriterion("customer_id not between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andBorrowNoIsNull() {
            addCriterion("borrow_no is null");
            return (Criteria) this;
        }

        public Criteria andBorrowNoIsNotNull() {
            addCriterion("borrow_no is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowNoEqualTo(String value) {
            addCriterion("borrow_no =", value, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoNotEqualTo(String value) {
            addCriterion("borrow_no <>", value, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoGreaterThan(String value) {
            addCriterion("borrow_no >", value, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_no >=", value, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoLessThan(String value) {
            addCriterion("borrow_no <", value, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoLessThanOrEqualTo(String value) {
            addCriterion("borrow_no <=", value, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoLike(String value) {
            addCriterion("borrow_no like", value, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoNotLike(String value) {
            addCriterion("borrow_no not like", value, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoIn(List<String> values) {
            addCriterion("borrow_no in", values, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoNotIn(List<String> values) {
            addCriterion("borrow_no not in", values, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoBetween(String value1, String value2) {
            addCriterion("borrow_no between", value1, value2, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoNotBetween(String value1, String value2) {
            addCriterion("borrow_no not between", value1, value2, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andRepayerNameIsNull() {
            addCriterion("repayer_name is null");
            return (Criteria) this;
        }

        public Criteria andRepayerNameIsNotNull() {
            addCriterion("repayer_name is not null");
            return (Criteria) this;
        }

        public Criteria andRepayerNameEqualTo(String value) {
            addCriterion("repayer_name =", value, "repayerName");
            return (Criteria) this;
        }

        public Criteria andRepayerNameNotEqualTo(String value) {
            addCriterion("repayer_name <>", value, "repayerName");
            return (Criteria) this;
        }

        public Criteria andRepayerNameGreaterThan(String value) {
            addCriterion("repayer_name >", value, "repayerName");
            return (Criteria) this;
        }

        public Criteria andRepayerNameGreaterThanOrEqualTo(String value) {
            addCriterion("repayer_name >=", value, "repayerName");
            return (Criteria) this;
        }

        public Criteria andRepayerNameLessThan(String value) {
            addCriterion("repayer_name <", value, "repayerName");
            return (Criteria) this;
        }

        public Criteria andRepayerNameLessThanOrEqualTo(String value) {
            addCriterion("repayer_name <=", value, "repayerName");
            return (Criteria) this;
        }

        public Criteria andRepayerNameLike(String value) {
            addCriterion("repayer_name like", value, "repayerName");
            return (Criteria) this;
        }

        public Criteria andRepayerNameNotLike(String value) {
            addCriterion("repayer_name not like", value, "repayerName");
            return (Criteria) this;
        }

        public Criteria andRepayerNameIn(List<String> values) {
            addCriterion("repayer_name in", values, "repayerName");
            return (Criteria) this;
        }

        public Criteria andRepayerNameNotIn(List<String> values) {
            addCriterion("repayer_name not in", values, "repayerName");
            return (Criteria) this;
        }

        public Criteria andRepayerNameBetween(String value1, String value2) {
            addCriterion("repayer_name between", value1, value2, "repayerName");
            return (Criteria) this;
        }

        public Criteria andRepayerNameNotBetween(String value1, String value2) {
            addCriterion("repayer_name not between", value1, value2, "repayerName");
            return (Criteria) this;
        }

        public Criteria andRepayerIdCardIsNull() {
            addCriterion("repayer_id_card is null");
            return (Criteria) this;
        }

        public Criteria andRepayerIdCardIsNotNull() {
            addCriterion("repayer_id_card is not null");
            return (Criteria) this;
        }

        public Criteria andRepayerIdCardEqualTo(String value) {
            addCriterion("repayer_id_card =", value, "repayerIdCard");
            return (Criteria) this;
        }

        public Criteria andRepayerIdCardNotEqualTo(String value) {
            addCriterion("repayer_id_card <>", value, "repayerIdCard");
            return (Criteria) this;
        }

        public Criteria andRepayerIdCardGreaterThan(String value) {
            addCriterion("repayer_id_card >", value, "repayerIdCard");
            return (Criteria) this;
        }

        public Criteria andRepayerIdCardGreaterThanOrEqualTo(String value) {
            addCriterion("repayer_id_card >=", value, "repayerIdCard");
            return (Criteria) this;
        }

        public Criteria andRepayerIdCardLessThan(String value) {
            addCriterion("repayer_id_card <", value, "repayerIdCard");
            return (Criteria) this;
        }

        public Criteria andRepayerIdCardLessThanOrEqualTo(String value) {
            addCriterion("repayer_id_card <=", value, "repayerIdCard");
            return (Criteria) this;
        }

        public Criteria andRepayerIdCardLike(String value) {
            addCriterion("repayer_id_card like", value, "repayerIdCard");
            return (Criteria) this;
        }

        public Criteria andRepayerIdCardNotLike(String value) {
            addCriterion("repayer_id_card not like", value, "repayerIdCard");
            return (Criteria) this;
        }

        public Criteria andRepayerIdCardIn(List<String> values) {
            addCriterion("repayer_id_card in", values, "repayerIdCard");
            return (Criteria) this;
        }

        public Criteria andRepayerIdCardNotIn(List<String> values) {
            addCriterion("repayer_id_card not in", values, "repayerIdCard");
            return (Criteria) this;
        }

        public Criteria andRepayerIdCardBetween(String value1, String value2) {
            addCriterion("repayer_id_card between", value1, value2, "repayerIdCard");
            return (Criteria) this;
        }

        public Criteria andRepayerIdCardNotBetween(String value1, String value2) {
            addCriterion("repayer_id_card not between", value1, value2, "repayerIdCard");
            return (Criteria) this;
        }

        public Criteria andRepayTimeIsNull() {
            addCriterion("repay_time is null");
            return (Criteria) this;
        }

        public Criteria andRepayTimeIsNotNull() {
            addCriterion("repay_time is not null");
            return (Criteria) this;
        }

        public Criteria andRepayTimeEqualTo(Date value) {
            addCriterion("repay_time =", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeNotEqualTo(Date value) {
            addCriterion("repay_time <>", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeGreaterThan(Date value) {
            addCriterion("repay_time >", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("repay_time >=", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeLessThan(Date value) {
            addCriterion("repay_time <", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeLessThanOrEqualTo(Date value) {
            addCriterion("repay_time <=", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeIn(List<Date> values) {
            addCriterion("repay_time in", values, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeNotIn(List<Date> values) {
            addCriterion("repay_time not in", values, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeBetween(Date value1, Date value2) {
            addCriterion("repay_time between", value1, value2, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeNotBetween(Date value1, Date value2) {
            addCriterion("repay_time not between", value1, value2, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayPrincipalIsNull() {
            addCriterion("repay_principal is null");
            return (Criteria) this;
        }

        public Criteria andRepayPrincipalIsNotNull() {
            addCriterion("repay_principal is not null");
            return (Criteria) this;
        }

        public Criteria andRepayPrincipalEqualTo(Double value) {
            addCriterion("repay_principal =", value, "repayPrincipal");
            return (Criteria) this;
        }

        public Criteria andRepayPrincipalNotEqualTo(Double value) {
            addCriterion("repay_principal <>", value, "repayPrincipal");
            return (Criteria) this;
        }

        public Criteria andRepayPrincipalGreaterThan(Double value) {
            addCriterion("repay_principal >", value, "repayPrincipal");
            return (Criteria) this;
        }

        public Criteria andRepayPrincipalGreaterThanOrEqualTo(Double value) {
            addCriterion("repay_principal >=", value, "repayPrincipal");
            return (Criteria) this;
        }

        public Criteria andRepayPrincipalLessThan(Double value) {
            addCriterion("repay_principal <", value, "repayPrincipal");
            return (Criteria) this;
        }

        public Criteria andRepayPrincipalLessThanOrEqualTo(Double value) {
            addCriterion("repay_principal <=", value, "repayPrincipal");
            return (Criteria) this;
        }

        public Criteria andRepayPrincipalIn(List<Double> values) {
            addCriterion("repay_principal in", values, "repayPrincipal");
            return (Criteria) this;
        }

        public Criteria andRepayPrincipalNotIn(List<Double> values) {
            addCriterion("repay_principal not in", values, "repayPrincipal");
            return (Criteria) this;
        }

        public Criteria andRepayPrincipalBetween(Double value1, Double value2) {
            addCriterion("repay_principal between", value1, value2, "repayPrincipal");
            return (Criteria) this;
        }

        public Criteria andRepayPrincipalNotBetween(Double value1, Double value2) {
            addCriterion("repay_principal not between", value1, value2, "repayPrincipal");
            return (Criteria) this;
        }

        public Criteria andRepayTransNoIsNull() {
            addCriterion("repay_trans_no is null");
            return (Criteria) this;
        }

        public Criteria andRepayTransNoIsNotNull() {
            addCriterion("repay_trans_no is not null");
            return (Criteria) this;
        }

        public Criteria andRepayTransNoEqualTo(String value) {
            addCriterion("repay_trans_no =", value, "repayTransNo");
            return (Criteria) this;
        }

        public Criteria andRepayTransNoNotEqualTo(String value) {
            addCriterion("repay_trans_no <>", value, "repayTransNo");
            return (Criteria) this;
        }

        public Criteria andRepayTransNoGreaterThan(String value) {
            addCriterion("repay_trans_no >", value, "repayTransNo");
            return (Criteria) this;
        }

        public Criteria andRepayTransNoGreaterThanOrEqualTo(String value) {
            addCriterion("repay_trans_no >=", value, "repayTransNo");
            return (Criteria) this;
        }

        public Criteria andRepayTransNoLessThan(String value) {
            addCriterion("repay_trans_no <", value, "repayTransNo");
            return (Criteria) this;
        }

        public Criteria andRepayTransNoLessThanOrEqualTo(String value) {
            addCriterion("repay_trans_no <=", value, "repayTransNo");
            return (Criteria) this;
        }

        public Criteria andRepayTransNoLike(String value) {
            addCriterion("repay_trans_no like", value, "repayTransNo");
            return (Criteria) this;
        }

        public Criteria andRepayTransNoNotLike(String value) {
            addCriterion("repay_trans_no not like", value, "repayTransNo");
            return (Criteria) this;
        }

        public Criteria andRepayTransNoIn(List<String> values) {
            addCriterion("repay_trans_no in", values, "repayTransNo");
            return (Criteria) this;
        }

        public Criteria andRepayTransNoNotIn(List<String> values) {
            addCriterion("repay_trans_no not in", values, "repayTransNo");
            return (Criteria) this;
        }

        public Criteria andRepayTransNoBetween(String value1, String value2) {
            addCriterion("repay_trans_no between", value1, value2, "repayTransNo");
            return (Criteria) this;
        }

        public Criteria andRepayTransNoNotBetween(String value1, String value2) {
            addCriterion("repay_trans_no not between", value1, value2, "repayTransNo");
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