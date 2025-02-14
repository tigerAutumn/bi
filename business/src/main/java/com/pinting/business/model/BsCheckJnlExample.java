package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsCheckJnlExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsCheckJnlExample() {
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

        public Criteria andTransJnlIdIsNull() {
            addCriterion("trans_jnl_id is null");
            return (Criteria) this;
        }

        public Criteria andTransJnlIdIsNotNull() {
            addCriterion("trans_jnl_id is not null");
            return (Criteria) this;
        }

        public Criteria andTransJnlIdEqualTo(Integer value) {
            addCriterion("trans_jnl_id =", value, "transJnlId");
            return (Criteria) this;
        }

        public Criteria andTransJnlIdNotEqualTo(Integer value) {
            addCriterion("trans_jnl_id <>", value, "transJnlId");
            return (Criteria) this;
        }

        public Criteria andTransJnlIdGreaterThan(Integer value) {
            addCriterion("trans_jnl_id >", value, "transJnlId");
            return (Criteria) this;
        }

        public Criteria andTransJnlIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("trans_jnl_id >=", value, "transJnlId");
            return (Criteria) this;
        }

        public Criteria andTransJnlIdLessThan(Integer value) {
            addCriterion("trans_jnl_id <", value, "transJnlId");
            return (Criteria) this;
        }

        public Criteria andTransJnlIdLessThanOrEqualTo(Integer value) {
            addCriterion("trans_jnl_id <=", value, "transJnlId");
            return (Criteria) this;
        }

        public Criteria andTransJnlIdIn(List<Integer> values) {
            addCriterion("trans_jnl_id in", values, "transJnlId");
            return (Criteria) this;
        }

        public Criteria andTransJnlIdNotIn(List<Integer> values) {
            addCriterion("trans_jnl_id not in", values, "transJnlId");
            return (Criteria) this;
        }

        public Criteria andTransJnlIdBetween(Integer value1, Integer value2) {
            addCriterion("trans_jnl_id between", value1, value2, "transJnlId");
            return (Criteria) this;
        }

        public Criteria andTransJnlIdNotBetween(Integer value1, Integer value2) {
            addCriterion("trans_jnl_id not between", value1, value2, "transJnlId");
            return (Criteria) this;
        }

        public Criteria andTimeIsNull() {
            addCriterion("time is null");
            return (Criteria) this;
        }

        public Criteria andTimeIsNotNull() {
            addCriterion("time is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEqualTo(Date value) {
            addCriterion("time =", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotEqualTo(Date value) {
            addCriterion("time <>", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThan(Date value) {
            addCriterion("time >", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("time >=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThan(Date value) {
            addCriterion("time <", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThanOrEqualTo(Date value) {
            addCriterion("time <=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeIn(List<Date> values) {
            addCriterion("time in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotIn(List<Date> values) {
            addCriterion("time not in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeBetween(Date value1, Date value2) {
            addCriterion("time between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotBetween(Date value1, Date value2) {
            addCriterion("time not between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andSysAmountIsNull() {
            addCriterion("sys_amount is null");
            return (Criteria) this;
        }

        public Criteria andSysAmountIsNotNull() {
            addCriterion("sys_amount is not null");
            return (Criteria) this;
        }

        public Criteria andSysAmountEqualTo(Double value) {
            addCriterion("sys_amount =", value, "sysAmount");
            return (Criteria) this;
        }

        public Criteria andSysAmountNotEqualTo(Double value) {
            addCriterion("sys_amount <>", value, "sysAmount");
            return (Criteria) this;
        }

        public Criteria andSysAmountGreaterThan(Double value) {
            addCriterion("sys_amount >", value, "sysAmount");
            return (Criteria) this;
        }

        public Criteria andSysAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("sys_amount >=", value, "sysAmount");
            return (Criteria) this;
        }

        public Criteria andSysAmountLessThan(Double value) {
            addCriterion("sys_amount <", value, "sysAmount");
            return (Criteria) this;
        }

        public Criteria andSysAmountLessThanOrEqualTo(Double value) {
            addCriterion("sys_amount <=", value, "sysAmount");
            return (Criteria) this;
        }

        public Criteria andSysAmountIn(List<Double> values) {
            addCriterion("sys_amount in", values, "sysAmount");
            return (Criteria) this;
        }

        public Criteria andSysAmountNotIn(List<Double> values) {
            addCriterion("sys_amount not in", values, "sysAmount");
            return (Criteria) this;
        }

        public Criteria andSysAmountBetween(Double value1, Double value2) {
            addCriterion("sys_amount between", value1, value2, "sysAmount");
            return (Criteria) this;
        }

        public Criteria andSysAmountNotBetween(Double value1, Double value2) {
            addCriterion("sys_amount not between", value1, value2, "sysAmount");
            return (Criteria) this;
        }

        public Criteria andDoneAmountIsNull() {
            addCriterion("done_amount is null");
            return (Criteria) this;
        }

        public Criteria andDoneAmountIsNotNull() {
            addCriterion("done_amount is not null");
            return (Criteria) this;
        }

        public Criteria andDoneAmountEqualTo(Double value) {
            addCriterion("done_amount =", value, "doneAmount");
            return (Criteria) this;
        }

        public Criteria andDoneAmountNotEqualTo(Double value) {
            addCriterion("done_amount <>", value, "doneAmount");
            return (Criteria) this;
        }

        public Criteria andDoneAmountGreaterThan(Double value) {
            addCriterion("done_amount >", value, "doneAmount");
            return (Criteria) this;
        }

        public Criteria andDoneAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("done_amount >=", value, "doneAmount");
            return (Criteria) this;
        }

        public Criteria andDoneAmountLessThan(Double value) {
            addCriterion("done_amount <", value, "doneAmount");
            return (Criteria) this;
        }

        public Criteria andDoneAmountLessThanOrEqualTo(Double value) {
            addCriterion("done_amount <=", value, "doneAmount");
            return (Criteria) this;
        }

        public Criteria andDoneAmountIn(List<Double> values) {
            addCriterion("done_amount in", values, "doneAmount");
            return (Criteria) this;
        }

        public Criteria andDoneAmountNotIn(List<Double> values) {
            addCriterion("done_amount not in", values, "doneAmount");
            return (Criteria) this;
        }

        public Criteria andDoneAmountBetween(Double value1, Double value2) {
            addCriterion("done_amount between", value1, value2, "doneAmount");
            return (Criteria) this;
        }

        public Criteria andDoneAmountNotBetween(Double value1, Double value2) {
            addCriterion("done_amount not between", value1, value2, "doneAmount");
            return (Criteria) this;
        }

        public Criteria andResultIsNull() {
            addCriterion("result is null");
            return (Criteria) this;
        }

        public Criteria andResultIsNotNull() {
            addCriterion("result is not null");
            return (Criteria) this;
        }

        public Criteria andResultEqualTo(Integer value) {
            addCriterion("result =", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotEqualTo(Integer value) {
            addCriterion("result <>", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThan(Integer value) {
            addCriterion("result >", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThanOrEqualTo(Integer value) {
            addCriterion("result >=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThan(Integer value) {
            addCriterion("result <", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThanOrEqualTo(Integer value) {
            addCriterion("result <=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultIn(List<Integer> values) {
            addCriterion("result in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotIn(List<Integer> values) {
            addCriterion("result not in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultBetween(Integer value1, Integer value2) {
            addCriterion("result between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotBetween(Integer value1, Integer value2) {
            addCriterion("result not between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andInfoIsNull() {
            addCriterion("info is null");
            return (Criteria) this;
        }

        public Criteria andInfoIsNotNull() {
            addCriterion("info is not null");
            return (Criteria) this;
        }

        public Criteria andInfoEqualTo(String value) {
            addCriterion("info =", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoNotEqualTo(String value) {
            addCriterion("info <>", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoGreaterThan(String value) {
            addCriterion("info >", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoGreaterThanOrEqualTo(String value) {
            addCriterion("info >=", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoLessThan(String value) {
            addCriterion("info <", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoLessThanOrEqualTo(String value) {
            addCriterion("info <=", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoLike(String value) {
            addCriterion("info like", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoNotLike(String value) {
            addCriterion("info not like", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoIn(List<String> values) {
            addCriterion("info in", values, "info");
            return (Criteria) this;
        }

        public Criteria andInfoNotIn(List<String> values) {
            addCriterion("info not in", values, "info");
            return (Criteria) this;
        }

        public Criteria andInfoBetween(String value1, String value2) {
            addCriterion("info between", value1, value2, "info");
            return (Criteria) this;
        }

        public Criteria andInfoNotBetween(String value1, String value2) {
            addCriterion("info not between", value1, value2, "info");
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