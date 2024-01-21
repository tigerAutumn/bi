package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsQuestionnaireQuotaExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsQuestionnaireQuotaExample() {
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

        public Criteria andOperateUserIdIsNull() {
            addCriterion("operate_user_id is null");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdIsNotNull() {
            addCriterion("operate_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdEqualTo(Integer value) {
            addCriterion("operate_user_id =", value, "operate_user_id");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdNotEqualTo(Integer value) {
            addCriterion("operate_user_id <>", value, "operate_user_id");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdGreaterThan(Integer value) {
            addCriterion("operate_user_id >", value, "operate_user_id");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("operate_user_id >=", value, "operate_user_id");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdLessThan(Integer value) {
            addCriterion("operate_user_id <", value, "operate_user_id");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("operate_user_id <=", value, "operate_user_id");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdIn(List<Integer> values) {
            addCriterion("operate_user_id in", values, "operate_user_id");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdNotIn(List<Integer> values) {
            addCriterion("operate_user_id not in", values, "operate_user_id");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdBetween(Integer value1, Integer value2) {
            addCriterion("operate_user_id between", value1, value2, "operate_user_id");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("operate_user_id not between", value1, value2, "operate_user_id");
            return (Criteria) this;
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

        public Criteria andEvaluateTypeIsNull() {
            addCriterion("evaluate_type is null");
            return (Criteria) this;
        }

        public Criteria andEvaluateTypeIsNotNull() {
            addCriterion("evaluate_type is not null");
            return (Criteria) this;
        }

        public Criteria andEvaluateTypeEqualTo(String value) {
            addCriterion("evaluate_type =", value, "evaluateType");
            return (Criteria) this;
        }

        public Criteria andEvaluateTypeNotEqualTo(String value) {
            addCriterion("evaluate_type <>", value, "evaluateType");
            return (Criteria) this;
        }

        public Criteria andEvaluateTypeGreaterThan(String value) {
            addCriterion("evaluate_type >", value, "evaluateType");
            return (Criteria) this;
        }

        public Criteria andEvaluateTypeGreaterThanOrEqualTo(String value) {
            addCriterion("evaluate_type >=", value, "evaluateType");
            return (Criteria) this;
        }

        public Criteria andEvaluateTypeLessThan(String value) {
            addCriterion("evaluate_type <", value, "evaluateType");
            return (Criteria) this;
        }

        public Criteria andEvaluateTypeLessThanOrEqualTo(String value) {
            addCriterion("evaluate_type <=", value, "evaluateType");
            return (Criteria) this;
        }

        public Criteria andEvaluateTypeLike(String value) {
            addCriterion("evaluate_type like", value, "evaluateType");
            return (Criteria) this;
        }

        public Criteria andEvaluateTypeNotLike(String value) {
            addCriterion("evaluate_type not like", value, "evaluateType");
            return (Criteria) this;
        }

        public Criteria andEvaluateTypeIn(List<String> values) {
            addCriterion("evaluate_type in", values, "evaluateType");
            return (Criteria) this;
        }

        public Criteria andEvaluateTypeNotIn(List<String> values) {
            addCriterion("evaluate_type not in", values, "evaluateType");
            return (Criteria) this;
        }

        public Criteria andEvaluateTypeBetween(String value1, String value2) {
            addCriterion("evaluate_type between", value1, value2, "evaluateType");
            return (Criteria) this;
        }

        public Criteria andEvaluateTypeNotBetween(String value1, String value2) {
            addCriterion("evaluate_type not between", value1, value2, "evaluateType");
            return (Criteria) this;
        }

        public Criteria andAmountLimitIsNull() {
            addCriterion("amount_limit is null");
            return (Criteria) this;
        }

        public Criteria andAmountLimitIsNotNull() {
            addCriterion("amount_limit is not null");
            return (Criteria) this;
        }

        public Criteria andAmountLimitEqualTo(Double value) {
            addCriterion("amount_limit =", value, "amountLimit");
            return (Criteria) this;
        }

        public Criteria andAmountLimitNotEqualTo(Double value) {
            addCriterion("amount_limit <>", value, "amountLimit");
            return (Criteria) this;
        }

        public Criteria andAmountLimitGreaterThan(Double value) {
            addCriterion("amount_limit >", value, "amountLimit");
            return (Criteria) this;
        }

        public Criteria andAmountLimitGreaterThanOrEqualTo(Double value) {
            addCriterion("amount_limit >=", value, "amountLimit");
            return (Criteria) this;
        }

        public Criteria andAmountLimitLessThan(Double value) {
            addCriterion("amount_limit <", value, "amountLimit");
            return (Criteria) this;
        }

        public Criteria andAmountLimitLessThanOrEqualTo(Double value) {
            addCriterion("amount_limit <=", value, "amountLimit");
            return (Criteria) this;
        }

        public Criteria andAmountLimitIn(List<Double> values) {
            addCriterion("amount_limit in", values, "amountLimit");
            return (Criteria) this;
        }

        public Criteria andAmountLimitNotIn(List<Double> values) {
            addCriterion("amount_limit not in", values, "amountLimit");
            return (Criteria) this;
        }

        public Criteria andAmountLimitBetween(Double value1, Double value2) {
            addCriterion("amount_limit between", value1, value2, "amountLimit");
            return (Criteria) this;
        }

        public Criteria andAmountLimitNotBetween(Double value1, Double value2) {
            addCriterion("amount_limit not between", value1, value2, "amountLimit");
            return (Criteria) this;
        }

        public Criteria andPeriodLimitIsNull() {
            addCriterion("period_limit is null");
            return (Criteria) this;
        }

        public Criteria andPeriodLimitIsNotNull() {
            addCriterion("period_limit is not null");
            return (Criteria) this;
        }

        public Criteria andPeriodLimitEqualTo(Integer value) {
            addCriterion("period_limit =", value, "periodLimit");
            return (Criteria) this;
        }

        public Criteria andPeriodLimitNotEqualTo(Integer value) {
            addCriterion("period_limit <>", value, "periodLimit");
            return (Criteria) this;
        }

        public Criteria andPeriodLimitGreaterThan(Integer value) {
            addCriterion("period_limit >", value, "periodLimit");
            return (Criteria) this;
        }

        public Criteria andPeriodLimitGreaterThanOrEqualTo(Integer value) {
            addCriterion("period_limit >=", value, "periodLimit");
            return (Criteria) this;
        }

        public Criteria andPeriodLimitLessThan(Integer value) {
            addCriterion("period_limit <", value, "periodLimit");
            return (Criteria) this;
        }

        public Criteria andPeriodLimitLessThanOrEqualTo(Integer value) {
            addCriterion("period_limit <=", value, "periodLimit");
            return (Criteria) this;
        }

        public Criteria andPeriodLimitIn(List<Integer> values) {
            addCriterion("period_limit in", values, "periodLimit");
            return (Criteria) this;
        }

        public Criteria andPeriodLimitNotIn(List<Integer> values) {
            addCriterion("period_limit not in", values, "periodLimit");
            return (Criteria) this;
        }

        public Criteria andPeriodLimitBetween(Integer value1, Integer value2) {
            addCriterion("period_limit between", value1, value2, "periodLimit");
            return (Criteria) this;
        }

        public Criteria andPeriodLimitNotBetween(Integer value1, Integer value2) {
            addCriterion("period_limit not between", value1, value2, "periodLimit");
            return (Criteria) this;
        }

        public Criteria andTermIsNull() {
            addCriterion("term is null");
            return (Criteria) this;
        }

        public Criteria andTermIsNotNull() {
            addCriterion("term is not null");
            return (Criteria) this;
        }

        public Criteria andTermEqualTo(Integer value) {
            addCriterion("term =", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermNotEqualTo(Integer value) {
            addCriterion("term <>", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermGreaterThan(Integer value) {
            addCriterion("term >", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermGreaterThanOrEqualTo(Integer value) {
            addCriterion("term >=", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermLessThan(Integer value) {
            addCriterion("term <", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermLessThanOrEqualTo(Integer value) {
            addCriterion("term <=", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermIn(List<Integer> values) {
            addCriterion("term in", values, "term");
            return (Criteria) this;
        }

        public Criteria andTermNotIn(List<Integer> values) {
            addCriterion("term not in", values, "term");
            return (Criteria) this;
        }

        public Criteria andTermBetween(Integer value1, Integer value2) {
            addCriterion("term between", value1, value2, "term");
            return (Criteria) this;
        }

        public Criteria andTermNotBetween(Integer value1, Integer value2) {
            addCriterion("term not between", value1, value2, "term");
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