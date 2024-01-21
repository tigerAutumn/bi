package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DafyUserExtExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DafyUserExtExample() {
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

        public Criteria andDafyUserIdIsNull() {
            addCriterion("dafy_user_id is null");
            return (Criteria) this;
        }

        public Criteria andDafyUserIdIsNotNull() {
            addCriterion("dafy_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andDafyUserIdEqualTo(String value) {
            addCriterion("dafy_user_id =", value, "dafyUserId");
            return (Criteria) this;
        }

        public Criteria andDafyUserIdNotEqualTo(String value) {
            addCriterion("dafy_user_id <>", value, "dafyUserId");
            return (Criteria) this;
        }

        public Criteria andDafyUserIdGreaterThan(String value) {
            addCriterion("dafy_user_id >", value, "dafyUserId");
            return (Criteria) this;
        }

        public Criteria andDafyUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("dafy_user_id >=", value, "dafyUserId");
            return (Criteria) this;
        }

        public Criteria andDafyUserIdLessThan(String value) {
            addCriterion("dafy_user_id <", value, "dafyUserId");
            return (Criteria) this;
        }

        public Criteria andDafyUserIdLessThanOrEqualTo(String value) {
            addCriterion("dafy_user_id <=", value, "dafyUserId");
            return (Criteria) this;
        }

        public Criteria andDafyUserIdLike(String value) {
            addCriterion("dafy_user_id like", value, "dafyUserId");
            return (Criteria) this;
        }

        public Criteria andDafyUserIdNotLike(String value) {
            addCriterion("dafy_user_id not like", value, "dafyUserId");
            return (Criteria) this;
        }

        public Criteria andDafyUserIdIn(List<String> values) {
            addCriterion("dafy_user_id in", values, "dafyUserId");
            return (Criteria) this;
        }

        public Criteria andDafyUserIdNotIn(List<String> values) {
            addCriterion("dafy_user_id not in", values, "dafyUserId");
            return (Criteria) this;
        }

        public Criteria andDafyUserIdBetween(String value1, String value2) {
            addCriterion("dafy_user_id between", value1, value2, "dafyUserId");
            return (Criteria) this;
        }

        public Criteria andDafyUserIdNotBetween(String value1, String value2) {
            addCriterion("dafy_user_id not between", value1, value2, "dafyUserId");
            return (Criteria) this;
        }

        public Criteria andDafyRegisterTimeIsNull() {
            addCriterion("dafy_register_time is null");
            return (Criteria) this;
        }

        public Criteria andDafyRegisterTimeIsNotNull() {
            addCriterion("dafy_register_time is not null");
            return (Criteria) this;
        }

        public Criteria andDafyRegisterTimeEqualTo(Date value) {
            addCriterion("dafy_register_time =", value, "dafyRegisterTime");
            return (Criteria) this;
        }

        public Criteria andDafyRegisterTimeNotEqualTo(Date value) {
            addCriterion("dafy_register_time <>", value, "dafyRegisterTime");
            return (Criteria) this;
        }

        public Criteria andDafyRegisterTimeGreaterThan(Date value) {
            addCriterion("dafy_register_time >", value, "dafyRegisterTime");
            return (Criteria) this;
        }

        public Criteria andDafyRegisterTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("dafy_register_time >=", value, "dafyRegisterTime");
            return (Criteria) this;
        }

        public Criteria andDafyRegisterTimeLessThan(Date value) {
            addCriterion("dafy_register_time <", value, "dafyRegisterTime");
            return (Criteria) this;
        }

        public Criteria andDafyRegisterTimeLessThanOrEqualTo(Date value) {
            addCriterion("dafy_register_time <=", value, "dafyRegisterTime");
            return (Criteria) this;
        }

        public Criteria andDafyRegisterTimeIn(List<Date> values) {
            addCriterion("dafy_register_time in", values, "dafyRegisterTime");
            return (Criteria) this;
        }

        public Criteria andDafyRegisterTimeNotIn(List<Date> values) {
            addCriterion("dafy_register_time not in", values, "dafyRegisterTime");
            return (Criteria) this;
        }

        public Criteria andDafyRegisterTimeBetween(Date value1, Date value2) {
            addCriterion("dafy_register_time between", value1, value2, "dafyRegisterTime");
            return (Criteria) this;
        }

        public Criteria andDafyRegisterTimeNotBetween(Date value1, Date value2) {
            addCriterion("dafy_register_time not between", value1, value2, "dafyRegisterTime");
            return (Criteria) this;
        }

        public Criteria andDafyBindCardTimeIsNull() {
            addCriterion("dafy_bind_card_time is null");
            return (Criteria) this;
        }

        public Criteria andDafyBindCardTimeIsNotNull() {
            addCriterion("dafy_bind_card_time is not null");
            return (Criteria) this;
        }

        public Criteria andDafyBindCardTimeEqualTo(Date value) {
            addCriterion("dafy_bind_card_time =", value, "dafyBindCardTime");
            return (Criteria) this;
        }

        public Criteria andDafyBindCardTimeNotEqualTo(Date value) {
            addCriterion("dafy_bind_card_time <>", value, "dafyBindCardTime");
            return (Criteria) this;
        }

        public Criteria andDafyBindCardTimeGreaterThan(Date value) {
            addCriterion("dafy_bind_card_time >", value, "dafyBindCardTime");
            return (Criteria) this;
        }

        public Criteria andDafyBindCardTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("dafy_bind_card_time >=", value, "dafyBindCardTime");
            return (Criteria) this;
        }

        public Criteria andDafyBindCardTimeLessThan(Date value) {
            addCriterion("dafy_bind_card_time <", value, "dafyBindCardTime");
            return (Criteria) this;
        }

        public Criteria andDafyBindCardTimeLessThanOrEqualTo(Date value) {
            addCriterion("dafy_bind_card_time <=", value, "dafyBindCardTime");
            return (Criteria) this;
        }

        public Criteria andDafyBindCardTimeIn(List<Date> values) {
            addCriterion("dafy_bind_card_time in", values, "dafyBindCardTime");
            return (Criteria) this;
        }

        public Criteria andDafyBindCardTimeNotIn(List<Date> values) {
            addCriterion("dafy_bind_card_time not in", values, "dafyBindCardTime");
            return (Criteria) this;
        }

        public Criteria andDafyBindCardTimeBetween(Date value1, Date value2) {
            addCriterion("dafy_bind_card_time between", value1, value2, "dafyBindCardTime");
            return (Criteria) this;
        }

        public Criteria andDafyBindCardTimeNotBetween(Date value1, Date value2) {
            addCriterion("dafy_bind_card_time not between", value1, value2, "dafyBindCardTime");
            return (Criteria) this;
        }

        public Criteria andBankCardIsNull() {
            addCriterion("bank_card is null");
            return (Criteria) this;
        }

        public Criteria andBankCardIsNotNull() {
            addCriterion("bank_card is not null");
            return (Criteria) this;
        }

        public Criteria andBankCardEqualTo(String value) {
            addCriterion("bank_card =", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardNotEqualTo(String value) {
            addCriterion("bank_card <>", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardGreaterThan(String value) {
            addCriterion("bank_card >", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardGreaterThanOrEqualTo(String value) {
            addCriterion("bank_card >=", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardLessThan(String value) {
            addCriterion("bank_card <", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardLessThanOrEqualTo(String value) {
            addCriterion("bank_card <=", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardLike(String value) {
            addCriterion("bank_card like", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardNotLike(String value) {
            addCriterion("bank_card not like", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardIn(List<String> values) {
            addCriterion("bank_card in", values, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardNotIn(List<String> values) {
            addCriterion("bank_card not in", values, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardBetween(String value1, String value2) {
            addCriterion("bank_card between", value1, value2, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardNotBetween(String value1, String value2) {
            addCriterion("bank_card not between", value1, value2, "bankCard");
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

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
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

        public Criteria andBindFailResonIsNull() {
            addCriterion("bind_fail_reson is null");
            return (Criteria) this;
        }

        public Criteria andBindFailResonIsNotNull() {
            addCriterion("bind_fail_reson is not null");
            return (Criteria) this;
        }

        public Criteria andBindFailResonEqualTo(String value) {
            addCriterion("bind_fail_reson =", value, "bindFailReson");
            return (Criteria) this;
        }

        public Criteria andBindFailResonNotEqualTo(String value) {
            addCriterion("bind_fail_reson <>", value, "bindFailReson");
            return (Criteria) this;
        }

        public Criteria andBindFailResonGreaterThan(String value) {
            addCriterion("bind_fail_reson >", value, "bindFailReson");
            return (Criteria) this;
        }

        public Criteria andBindFailResonGreaterThanOrEqualTo(String value) {
            addCriterion("bind_fail_reson >=", value, "bindFailReson");
            return (Criteria) this;
        }

        public Criteria andBindFailResonLessThan(String value) {
            addCriterion("bind_fail_reson <", value, "bindFailReson");
            return (Criteria) this;
        }

        public Criteria andBindFailResonLessThanOrEqualTo(String value) {
            addCriterion("bind_fail_reson <=", value, "bindFailReson");
            return (Criteria) this;
        }

        public Criteria andBindFailResonLike(String value) {
            addCriterion("bind_fail_reson like", value, "bindFailReson");
            return (Criteria) this;
        }

        public Criteria andBindFailResonNotLike(String value) {
            addCriterion("bind_fail_reson not like", value, "bindFailReson");
            return (Criteria) this;
        }

        public Criteria andBindFailResonIn(List<String> values) {
            addCriterion("bind_fail_reson in", values, "bindFailReson");
            return (Criteria) this;
        }

        public Criteria andBindFailResonNotIn(List<String> values) {
            addCriterion("bind_fail_reson not in", values, "bindFailReson");
            return (Criteria) this;
        }

        public Criteria andBindFailResonBetween(String value1, String value2) {
            addCriterion("bind_fail_reson between", value1, value2, "bindFailReson");
            return (Criteria) this;
        }

        public Criteria andBindFailResonNotBetween(String value1, String value2) {
            addCriterion("bind_fail_reson not between", value1, value2, "bindFailReson");
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