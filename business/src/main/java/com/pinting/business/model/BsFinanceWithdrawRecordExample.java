package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsFinanceWithdrawRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsFinanceWithdrawRecordExample() {
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

        public Criteria andWithdrawTimeIsNull() {
            addCriterion("withdraw_time is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawTimeIsNotNull() {
            addCriterion("withdraw_time is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawTimeEqualTo(Date value) {
            addCriterion("withdraw_time =", value, "withdrawTime");
            return (Criteria) this;
        }

        public Criteria andWithdrawTimeNotEqualTo(Date value) {
            addCriterion("withdraw_time <>", value, "withdrawTime");
            return (Criteria) this;
        }

        public Criteria andWithdrawTimeGreaterThan(Date value) {
            addCriterion("withdraw_time >", value, "withdrawTime");
            return (Criteria) this;
        }

        public Criteria andWithdrawTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("withdraw_time >=", value, "withdrawTime");
            return (Criteria) this;
        }

        public Criteria andWithdrawTimeLessThan(Date value) {
            addCriterion("withdraw_time <", value, "withdrawTime");
            return (Criteria) this;
        }

        public Criteria andWithdrawTimeLessThanOrEqualTo(Date value) {
            addCriterion("withdraw_time <=", value, "withdrawTime");
            return (Criteria) this;
        }

        public Criteria andWithdrawTimeIn(List<Date> values) {
            addCriterion("withdraw_time in", values, "withdrawTime");
            return (Criteria) this;
        }

        public Criteria andWithdrawTimeNotIn(List<Date> values) {
            addCriterion("withdraw_time not in", values, "withdrawTime");
            return (Criteria) this;
        }

        public Criteria andWithdrawTimeBetween(Date value1, Date value2) {
            addCriterion("withdraw_time between", value1, value2, "withdrawTime");
            return (Criteria) this;
        }

        public Criteria andWithdrawTimeNotBetween(Date value1, Date value2) {
            addCriterion("withdraw_time not between", value1, value2, "withdrawTime");
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

        public Criteria andConfirmUserIdIsNull() {
            addCriterion("confirm_user_id is null");
            return (Criteria) this;
        }

        public Criteria andConfirmUserIdIsNotNull() {
            addCriterion("confirm_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmUserIdEqualTo(Integer value) {
            addCriterion("confirm_user_id =", value, "confirmUserId");
            return (Criteria) this;
        }

        public Criteria andConfirmUserIdNotEqualTo(Integer value) {
            addCriterion("confirm_user_id <>", value, "confirmUserId");
            return (Criteria) this;
        }

        public Criteria andConfirmUserIdGreaterThan(Integer value) {
            addCriterion("confirm_user_id >", value, "confirmUserId");
            return (Criteria) this;
        }

        public Criteria andConfirmUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("confirm_user_id >=", value, "confirmUserId");
            return (Criteria) this;
        }

        public Criteria andConfirmUserIdLessThan(Integer value) {
            addCriterion("confirm_user_id <", value, "confirmUserId");
            return (Criteria) this;
        }

        public Criteria andConfirmUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("confirm_user_id <=", value, "confirmUserId");
            return (Criteria) this;
        }

        public Criteria andConfirmUserIdIn(List<Integer> values) {
            addCriterion("confirm_user_id in", values, "confirmUserId");
            return (Criteria) this;
        }

        public Criteria andConfirmUserIdNotIn(List<Integer> values) {
            addCriterion("confirm_user_id not in", values, "confirmUserId");
            return (Criteria) this;
        }

        public Criteria andConfirmUserIdBetween(Integer value1, Integer value2) {
            addCriterion("confirm_user_id between", value1, value2, "confirmUserId");
            return (Criteria) this;
        }

        public Criteria andConfirmUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("confirm_user_id not between", value1, value2, "confirmUserId");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeIsNull() {
            addCriterion("confirm_time is null");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeIsNotNull() {
            addCriterion("confirm_time is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeEqualTo(Date value) {
            addCriterion("confirm_time =", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeNotEqualTo(Date value) {
            addCriterion("confirm_time <>", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeGreaterThan(Date value) {
            addCriterion("confirm_time >", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("confirm_time >=", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeLessThan(Date value) {
            addCriterion("confirm_time <", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeLessThanOrEqualTo(Date value) {
            addCriterion("confirm_time <=", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeIn(List<Date> values) {
            addCriterion("confirm_time in", values, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeNotIn(List<Date> values) {
            addCriterion("confirm_time not in", values, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeBetween(Date value1, Date value2) {
            addCriterion("confirm_time between", value1, value2, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeNotBetween(Date value1, Date value2) {
            addCriterion("confirm_time not between", value1, value2, "confirmTime");
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