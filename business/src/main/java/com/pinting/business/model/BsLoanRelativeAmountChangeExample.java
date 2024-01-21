package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsLoanRelativeAmountChangeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsLoanRelativeAmountChangeExample() {
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

        public Criteria andPropertySymbolIsNull() {
            addCriterion("property_symbol is null");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolIsNotNull() {
            addCriterion("property_symbol is not null");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolEqualTo(String value) {
            addCriterion("property_symbol =", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolNotEqualTo(String value) {
            addCriterion("property_symbol <>", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolGreaterThan(String value) {
            addCriterion("property_symbol >", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolGreaterThanOrEqualTo(String value) {
            addCriterion("property_symbol >=", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolLessThan(String value) {
            addCriterion("property_symbol <", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolLessThanOrEqualTo(String value) {
            addCriterion("property_symbol <=", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolLike(String value) {
            addCriterion("property_symbol like", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolNotLike(String value) {
            addCriterion("property_symbol not like", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolIn(List<String> values) {
            addCriterion("property_symbol in", values, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolNotIn(List<String> values) {
            addCriterion("property_symbol not in", values, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolBetween(String value1, String value2) {
            addCriterion("property_symbol between", value1, value2, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolNotBetween(String value1, String value2) {
            addCriterion("property_symbol not between", value1, value2, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andLoanRelativeIdIsNull() {
            addCriterion("loan_relative_id is null");
            return (Criteria) this;
        }

        public Criteria andLoanRelativeIdIsNotNull() {
            addCriterion("loan_relative_id is not null");
            return (Criteria) this;
        }

        public Criteria andLoanRelativeIdEqualTo(Integer value) {
            addCriterion("loan_relative_id =", value, "loanRelativeId");
            return (Criteria) this;
        }

        public Criteria andLoanRelativeIdNotEqualTo(Integer value) {
            addCriterion("loan_relative_id <>", value, "loanRelativeId");
            return (Criteria) this;
        }

        public Criteria andLoanRelativeIdGreaterThan(Integer value) {
            addCriterion("loan_relative_id >", value, "loanRelativeId");
            return (Criteria) this;
        }

        public Criteria andLoanRelativeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("loan_relative_id >=", value, "loanRelativeId");
            return (Criteria) this;
        }

        public Criteria andLoanRelativeIdLessThan(Integer value) {
            addCriterion("loan_relative_id <", value, "loanRelativeId");
            return (Criteria) this;
        }

        public Criteria andLoanRelativeIdLessThanOrEqualTo(Integer value) {
            addCriterion("loan_relative_id <=", value, "loanRelativeId");
            return (Criteria) this;
        }

        public Criteria andLoanRelativeIdIn(List<Integer> values) {
            addCriterion("loan_relative_id in", values, "loanRelativeId");
            return (Criteria) this;
        }

        public Criteria andLoanRelativeIdNotIn(List<Integer> values) {
            addCriterion("loan_relative_id not in", values, "loanRelativeId");
            return (Criteria) this;
        }

        public Criteria andLoanRelativeIdBetween(Integer value1, Integer value2) {
            addCriterion("loan_relative_id between", value1, value2, "loanRelativeId");
            return (Criteria) this;
        }

        public Criteria andLoanRelativeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("loan_relative_id not between", value1, value2, "loanRelativeId");
            return (Criteria) this;
        }

        public Criteria andBeforeAmountIsNull() {
            addCriterion("before_amount is null");
            return (Criteria) this;
        }

        public Criteria andBeforeAmountIsNotNull() {
            addCriterion("before_amount is not null");
            return (Criteria) this;
        }

        public Criteria andBeforeAmountEqualTo(Double value) {
            addCriterion("before_amount =", value, "beforeAmount");
            return (Criteria) this;
        }

        public Criteria andBeforeAmountNotEqualTo(Double value) {
            addCriterion("before_amount <>", value, "beforeAmount");
            return (Criteria) this;
        }

        public Criteria andBeforeAmountGreaterThan(Double value) {
            addCriterion("before_amount >", value, "beforeAmount");
            return (Criteria) this;
        }

        public Criteria andBeforeAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("before_amount >=", value, "beforeAmount");
            return (Criteria) this;
        }

        public Criteria andBeforeAmountLessThan(Double value) {
            addCriterion("before_amount <", value, "beforeAmount");
            return (Criteria) this;
        }

        public Criteria andBeforeAmountLessThanOrEqualTo(Double value) {
            addCriterion("before_amount <=", value, "beforeAmount");
            return (Criteria) this;
        }

        public Criteria andBeforeAmountIn(List<Double> values) {
            addCriterion("before_amount in", values, "beforeAmount");
            return (Criteria) this;
        }

        public Criteria andBeforeAmountNotIn(List<Double> values) {
            addCriterion("before_amount not in", values, "beforeAmount");
            return (Criteria) this;
        }

        public Criteria andBeforeAmountBetween(Double value1, Double value2) {
            addCriterion("before_amount between", value1, value2, "beforeAmount");
            return (Criteria) this;
        }

        public Criteria andBeforeAmountNotBetween(Double value1, Double value2) {
            addCriterion("before_amount not between", value1, value2, "beforeAmount");
            return (Criteria) this;
        }

        public Criteria andAfterAmountIsNull() {
            addCriterion("after_amount is null");
            return (Criteria) this;
        }

        public Criteria andAfterAmountIsNotNull() {
            addCriterion("after_amount is not null");
            return (Criteria) this;
        }

        public Criteria andAfterAmountEqualTo(Double value) {
            addCriterion("after_amount =", value, "afterAmount");
            return (Criteria) this;
        }

        public Criteria andAfterAmountNotEqualTo(Double value) {
            addCriterion("after_amount <>", value, "afterAmount");
            return (Criteria) this;
        }

        public Criteria andAfterAmountGreaterThan(Double value) {
            addCriterion("after_amount >", value, "afterAmount");
            return (Criteria) this;
        }

        public Criteria andAfterAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("after_amount >=", value, "afterAmount");
            return (Criteria) this;
        }

        public Criteria andAfterAmountLessThan(Double value) {
            addCriterion("after_amount <", value, "afterAmount");
            return (Criteria) this;
        }

        public Criteria andAfterAmountLessThanOrEqualTo(Double value) {
            addCriterion("after_amount <=", value, "afterAmount");
            return (Criteria) this;
        }

        public Criteria andAfterAmountIn(List<Double> values) {
            addCriterion("after_amount in", values, "afterAmount");
            return (Criteria) this;
        }

        public Criteria andAfterAmountNotIn(List<Double> values) {
            addCriterion("after_amount not in", values, "afterAmount");
            return (Criteria) this;
        }

        public Criteria andAfterAmountBetween(Double value1, Double value2) {
            addCriterion("after_amount between", value1, value2, "afterAmount");
            return (Criteria) this;
        }

        public Criteria andAfterAmountNotBetween(Double value1, Double value2) {
            addCriterion("after_amount not between", value1, value2, "afterAmount");
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

        public Criteria andDealStatusIsNull() {
            addCriterion("deal_status is null");
            return (Criteria) this;
        }

        public Criteria andDealStatusIsNotNull() {
            addCriterion("deal_status is not null");
            return (Criteria) this;
        }

        public Criteria andDealStatusEqualTo(String value) {
            addCriterion("deal_status =", value, "dealStatus");
            return (Criteria) this;
        }

        public Criteria andDealStatusNotEqualTo(String value) {
            addCriterion("deal_status <>", value, "dealStatus");
            return (Criteria) this;
        }

        public Criteria andDealStatusGreaterThan(String value) {
            addCriterion("deal_status >", value, "dealStatus");
            return (Criteria) this;
        }

        public Criteria andDealStatusGreaterThanOrEqualTo(String value) {
            addCriterion("deal_status >=", value, "dealStatus");
            return (Criteria) this;
        }

        public Criteria andDealStatusLessThan(String value) {
            addCriterion("deal_status <", value, "dealStatus");
            return (Criteria) this;
        }

        public Criteria andDealStatusLessThanOrEqualTo(String value) {
            addCriterion("deal_status <=", value, "dealStatus");
            return (Criteria) this;
        }

        public Criteria andDealStatusLike(String value) {
            addCriterion("deal_status like", value, "dealStatus");
            return (Criteria) this;
        }

        public Criteria andDealStatusNotLike(String value) {
            addCriterion("deal_status not like", value, "dealStatus");
            return (Criteria) this;
        }

        public Criteria andDealStatusIn(List<String> values) {
            addCriterion("deal_status in", values, "dealStatus");
            return (Criteria) this;
        }

        public Criteria andDealStatusNotIn(List<String> values) {
            addCriterion("deal_status not in", values, "dealStatus");
            return (Criteria) this;
        }

        public Criteria andDealStatusBetween(String value1, String value2) {
            addCriterion("deal_status between", value1, value2, "dealStatus");
            return (Criteria) this;
        }

        public Criteria andDealStatusNotBetween(String value1, String value2) {
            addCriterion("deal_status not between", value1, value2, "dealStatus");
            return (Criteria) this;
        }

        public Criteria andIsPullDetailIsNull() {
            addCriterion("is_pull_detail is null");
            return (Criteria) this;
        }

        public Criteria andIsPullDetailIsNotNull() {
            addCriterion("is_pull_detail is not null");
            return (Criteria) this;
        }

        public Criteria andIsPullDetailEqualTo(String value) {
            addCriterion("is_pull_detail =", value, "isPullDetail");
            return (Criteria) this;
        }

        public Criteria andIsPullDetailNotEqualTo(String value) {
            addCriterion("is_pull_detail <>", value, "isPullDetail");
            return (Criteria) this;
        }

        public Criteria andIsPullDetailGreaterThan(String value) {
            addCriterion("is_pull_detail >", value, "isPullDetail");
            return (Criteria) this;
        }

        public Criteria andIsPullDetailGreaterThanOrEqualTo(String value) {
            addCriterion("is_pull_detail >=", value, "isPullDetail");
            return (Criteria) this;
        }

        public Criteria andIsPullDetailLessThan(String value) {
            addCriterion("is_pull_detail <", value, "isPullDetail");
            return (Criteria) this;
        }

        public Criteria andIsPullDetailLessThanOrEqualTo(String value) {
            addCriterion("is_pull_detail <=", value, "isPullDetail");
            return (Criteria) this;
        }

        public Criteria andIsPullDetailLike(String value) {
            addCriterion("is_pull_detail like", value, "isPullDetail");
            return (Criteria) this;
        }

        public Criteria andIsPullDetailNotLike(String value) {
            addCriterion("is_pull_detail not like", value, "isPullDetail");
            return (Criteria) this;
        }

        public Criteria andIsPullDetailIn(List<String> values) {
            addCriterion("is_pull_detail in", values, "isPullDetail");
            return (Criteria) this;
        }

        public Criteria andIsPullDetailNotIn(List<String> values) {
            addCriterion("is_pull_detail not in", values, "isPullDetail");
            return (Criteria) this;
        }

        public Criteria andIsPullDetailBetween(String value1, String value2) {
            addCriterion("is_pull_detail between", value1, value2, "isPullDetail");
            return (Criteria) this;
        }

        public Criteria andIsPullDetailNotBetween(String value1, String value2) {
            addCriterion("is_pull_detail not between", value1, value2, "isPullDetail");
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