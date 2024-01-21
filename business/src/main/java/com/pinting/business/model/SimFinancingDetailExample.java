package com.pinting.business.model;

import java.util.ArrayList;
import java.util.List;

public class SimFinancingDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SimFinancingDetailExample() {
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

        public Criteria andTotalIdIsNull() {
            addCriterion("total_id is null");
            return (Criteria) this;
        }

        public Criteria andTotalIdIsNotNull() {
            addCriterion("total_id is not null");
            return (Criteria) this;
        }

        public Criteria andTotalIdEqualTo(Integer value) {
            addCriterion("total_id =", value, "totalId");
            return (Criteria) this;
        }

        public Criteria andTotalIdNotEqualTo(Integer value) {
            addCriterion("total_id <>", value, "totalId");
            return (Criteria) this;
        }

        public Criteria andTotalIdGreaterThan(Integer value) {
            addCriterion("total_id >", value, "totalId");
            return (Criteria) this;
        }

        public Criteria andTotalIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_id >=", value, "totalId");
            return (Criteria) this;
        }

        public Criteria andTotalIdLessThan(Integer value) {
            addCriterion("total_id <", value, "totalId");
            return (Criteria) this;
        }

        public Criteria andTotalIdLessThanOrEqualTo(Integer value) {
            addCriterion("total_id <=", value, "totalId");
            return (Criteria) this;
        }

        public Criteria andTotalIdIn(List<Integer> values) {
            addCriterion("total_id in", values, "totalId");
            return (Criteria) this;
        }

        public Criteria andTotalIdNotIn(List<Integer> values) {
            addCriterion("total_id not in", values, "totalId");
            return (Criteria) this;
        }

        public Criteria andTotalIdBetween(Integer value1, Integer value2) {
            addCriterion("total_id between", value1, value2, "totalId");
            return (Criteria) this;
        }

        public Criteria andTotalIdNotBetween(Integer value1, Integer value2) {
            addCriterion("total_id not between", value1, value2, "totalId");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoIsNull() {
            addCriterion("product_order_no is null");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoIsNotNull() {
            addCriterion("product_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoEqualTo(String value) {
            addCriterion("product_order_no =", value, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoNotEqualTo(String value) {
            addCriterion("product_order_no <>", value, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoGreaterThan(String value) {
            addCriterion("product_order_no >", value, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("product_order_no >=", value, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoLessThan(String value) {
            addCriterion("product_order_no <", value, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoLessThanOrEqualTo(String value) {
            addCriterion("product_order_no <=", value, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoLike(String value) {
            addCriterion("product_order_no like", value, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoNotLike(String value) {
            addCriterion("product_order_no not like", value, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoIn(List<String> values) {
            addCriterion("product_order_no in", values, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoNotIn(List<String> values) {
            addCriterion("product_order_no not in", values, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoBetween(String value1, String value2) {
            addCriterion("product_order_no between", value1, value2, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoNotBetween(String value1, String value2) {
            addCriterion("product_order_no not between", value1, value2, "productOrderNo");
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

        public Criteria andProductAmountIsNull() {
            addCriterion("product_amount is null");
            return (Criteria) this;
        }

        public Criteria andProductAmountIsNotNull() {
            addCriterion("product_amount is not null");
            return (Criteria) this;
        }

        public Criteria andProductAmountEqualTo(Double value) {
            addCriterion("product_amount =", value, "productAmount");
            return (Criteria) this;
        }

        public Criteria andProductAmountNotEqualTo(Double value) {
            addCriterion("product_amount <>", value, "productAmount");
            return (Criteria) this;
        }

        public Criteria andProductAmountGreaterThan(Double value) {
            addCriterion("product_amount >", value, "productAmount");
            return (Criteria) this;
        }

        public Criteria andProductAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("product_amount >=", value, "productAmount");
            return (Criteria) this;
        }

        public Criteria andProductAmountLessThan(Double value) {
            addCriterion("product_amount <", value, "productAmount");
            return (Criteria) this;
        }

        public Criteria andProductAmountLessThanOrEqualTo(Double value) {
            addCriterion("product_amount <=", value, "productAmount");
            return (Criteria) this;
        }

        public Criteria andProductAmountIn(List<Double> values) {
            addCriterion("product_amount in", values, "productAmount");
            return (Criteria) this;
        }

        public Criteria andProductAmountNotIn(List<Double> values) {
            addCriterion("product_amount not in", values, "productAmount");
            return (Criteria) this;
        }

        public Criteria andProductAmountBetween(Double value1, Double value2) {
            addCriterion("product_amount between", value1, value2, "productAmount");
            return (Criteria) this;
        }

        public Criteria andProductAmountNotBetween(Double value1, Double value2) {
            addCriterion("product_amount not between", value1, value2, "productAmount");
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