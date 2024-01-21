package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbdatapermissionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbdatapermissionExample() {
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

        public Criteria andLidIsNull() {
            addCriterion("lId is null");
            return (Criteria) this;
        }

        public Criteria andLidIsNotNull() {
            addCriterion("lId is not null");
            return (Criteria) this;
        }

        public Criteria andLidEqualTo(Long value) {
            addCriterion("lId =", value, "lid");
            return (Criteria) this;
        }

        public Criteria andLidNotEqualTo(Long value) {
            addCriterion("lId <>", value, "lid");
            return (Criteria) this;
        }

        public Criteria andLidGreaterThan(Long value) {
            addCriterion("lId >", value, "lid");
            return (Criteria) this;
        }

        public Criteria andLidGreaterThanOrEqualTo(Long value) {
            addCriterion("lId >=", value, "lid");
            return (Criteria) this;
        }

        public Criteria andLidLessThan(Long value) {
            addCriterion("lId <", value, "lid");
            return (Criteria) this;
        }

        public Criteria andLidLessThanOrEqualTo(Long value) {
            addCriterion("lId <=", value, "lid");
            return (Criteria) this;
        }

        public Criteria andLidIn(List<Long> values) {
            addCriterion("lId in", values, "lid");
            return (Criteria) this;
        }

        public Criteria andLidNotIn(List<Long> values) {
            addCriterion("lId not in", values, "lid");
            return (Criteria) this;
        }

        public Criteria andLidBetween(Long value1, Long value2) {
            addCriterion("lId between", value1, value2, "lid");
            return (Criteria) this;
        }

        public Criteria andLidNotBetween(Long value1, Long value2) {
            addCriterion("lId not between", value1, value2, "lid");
            return (Criteria) this;
        }

        public Criteria andLuseridIsNull() {
            addCriterion("lUserId is null");
            return (Criteria) this;
        }

        public Criteria andLuseridIsNotNull() {
            addCriterion("lUserId is not null");
            return (Criteria) this;
        }

        public Criteria andLuseridEqualTo(Long value) {
            addCriterion("lUserId =", value, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridNotEqualTo(Long value) {
            addCriterion("lUserId <>", value, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridGreaterThan(Long value) {
            addCriterion("lUserId >", value, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridGreaterThanOrEqualTo(Long value) {
            addCriterion("lUserId >=", value, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridLessThan(Long value) {
            addCriterion("lUserId <", value, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridLessThanOrEqualTo(Long value) {
            addCriterion("lUserId <=", value, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridIn(List<Long> values) {
            addCriterion("lUserId in", values, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridNotIn(List<Long> values) {
            addCriterion("lUserId not in", values, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridBetween(Long value1, Long value2) {
            addCriterion("lUserId between", value1, value2, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridNotBetween(Long value1, Long value2) {
            addCriterion("lUserId not between", value1, value2, "luserid");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeIsNull() {
            addCriterion("strDeptCode is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeIsNotNull() {
            addCriterion("strDeptCode is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeEqualTo(String value) {
            addCriterion("strDeptCode =", value, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeNotEqualTo(String value) {
            addCriterion("strDeptCode <>", value, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeGreaterThan(String value) {
            addCriterion("strDeptCode >", value, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeGreaterThanOrEqualTo(String value) {
            addCriterion("strDeptCode >=", value, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeLessThan(String value) {
            addCriterion("strDeptCode <", value, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeLessThanOrEqualTo(String value) {
            addCriterion("strDeptCode <=", value, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeLike(String value) {
            addCriterion("strDeptCode like", value, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeNotLike(String value) {
            addCriterion("strDeptCode not like", value, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeIn(List<String> values) {
            addCriterion("strDeptCode in", values, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeNotIn(List<String> values) {
            addCriterion("strDeptCode not in", values, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeBetween(String value1, String value2) {
            addCriterion("strDeptCode between", value1, value2, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeNotBetween(String value1, String value2) {
            addCriterion("strDeptCode not between", value1, value2, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelIsNull() {
            addCriterion("nCurrentLevel is null");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelIsNotNull() {
            addCriterion("nCurrentLevel is not null");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelEqualTo(Integer value) {
            addCriterion("nCurrentLevel =", value, "ncurrentlevel");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelNotEqualTo(Integer value) {
            addCriterion("nCurrentLevel <>", value, "ncurrentlevel");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelGreaterThan(Integer value) {
            addCriterion("nCurrentLevel >", value, "ncurrentlevel");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("nCurrentLevel >=", value, "ncurrentlevel");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelLessThan(Integer value) {
            addCriterion("nCurrentLevel <", value, "ncurrentlevel");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelLessThanOrEqualTo(Integer value) {
            addCriterion("nCurrentLevel <=", value, "ncurrentlevel");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelIn(List<Integer> values) {
            addCriterion("nCurrentLevel in", values, "ncurrentlevel");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelNotIn(List<Integer> values) {
            addCriterion("nCurrentLevel not in", values, "ncurrentlevel");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelBetween(Integer value1, Integer value2) {
            addCriterion("nCurrentLevel between", value1, value2, "ncurrentlevel");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelNotBetween(Integer value1, Integer value2) {
            addCriterion("nCurrentLevel not between", value1, value2, "ncurrentlevel");
            return (Criteria) this;
        }

        public Criteria andLoperateidIsNull() {
            addCriterion("lOperateId is null");
            return (Criteria) this;
        }

        public Criteria andLoperateidIsNotNull() {
            addCriterion("lOperateId is not null");
            return (Criteria) this;
        }

        public Criteria andLoperateidEqualTo(Long value) {
            addCriterion("lOperateId =", value, "loperateid");
            return (Criteria) this;
        }

        public Criteria andLoperateidNotEqualTo(Long value) {
            addCriterion("lOperateId <>", value, "loperateid");
            return (Criteria) this;
        }

        public Criteria andLoperateidGreaterThan(Long value) {
            addCriterion("lOperateId >", value, "loperateid");
            return (Criteria) this;
        }

        public Criteria andLoperateidGreaterThanOrEqualTo(Long value) {
            addCriterion("lOperateId >=", value, "loperateid");
            return (Criteria) this;
        }

        public Criteria andLoperateidLessThan(Long value) {
            addCriterion("lOperateId <", value, "loperateid");
            return (Criteria) this;
        }

        public Criteria andLoperateidLessThanOrEqualTo(Long value) {
            addCriterion("lOperateId <=", value, "loperateid");
            return (Criteria) this;
        }

        public Criteria andLoperateidIn(List<Long> values) {
            addCriterion("lOperateId in", values, "loperateid");
            return (Criteria) this;
        }

        public Criteria andLoperateidNotIn(List<Long> values) {
            addCriterion("lOperateId not in", values, "loperateid");
            return (Criteria) this;
        }

        public Criteria andLoperateidBetween(Long value1, Long value2) {
            addCriterion("lOperateId between", value1, value2, "loperateid");
            return (Criteria) this;
        }

        public Criteria andLoperateidNotBetween(Long value1, Long value2) {
            addCriterion("lOperateId not between", value1, value2, "loperateid");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeIsNull() {
            addCriterion("dtUpdateTime is null");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeIsNotNull() {
            addCriterion("dtUpdateTime is not null");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeEqualTo(Date value) {
            addCriterion("dtUpdateTime =", value, "dtupdatetime");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeNotEqualTo(Date value) {
            addCriterion("dtUpdateTime <>", value, "dtupdatetime");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeGreaterThan(Date value) {
            addCriterion("dtUpdateTime >", value, "dtupdatetime");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("dtUpdateTime >=", value, "dtupdatetime");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeLessThan(Date value) {
            addCriterion("dtUpdateTime <", value, "dtupdatetime");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("dtUpdateTime <=", value, "dtupdatetime");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeIn(List<Date> values) {
            addCriterion("dtUpdateTime in", values, "dtupdatetime");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeNotIn(List<Date> values) {
            addCriterion("dtUpdateTime not in", values, "dtupdatetime");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeBetween(Date value1, Date value2) {
            addCriterion("dtUpdateTime between", value1, value2, "dtupdatetime");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("dtUpdateTime not between", value1, value2, "dtupdatetime");
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