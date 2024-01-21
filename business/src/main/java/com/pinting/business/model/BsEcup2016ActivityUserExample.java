package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsEcup2016ActivityUserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsEcup2016ActivityUserExample() {
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

        public Criteria andChampionIsNull() {
            addCriterion("champion is null");
            return (Criteria) this;
        }

        public Criteria andChampionIsNotNull() {
            addCriterion("champion is not null");
            return (Criteria) this;
        }

        public Criteria andChampionEqualTo(String value) {
            addCriterion("champion =", value, "champion");
            return (Criteria) this;
        }

        public Criteria andChampionNotEqualTo(String value) {
            addCriterion("champion <>", value, "champion");
            return (Criteria) this;
        }

        public Criteria andChampionGreaterThan(String value) {
            addCriterion("champion >", value, "champion");
            return (Criteria) this;
        }

        public Criteria andChampionGreaterThanOrEqualTo(String value) {
            addCriterion("champion >=", value, "champion");
            return (Criteria) this;
        }

        public Criteria andChampionLessThan(String value) {
            addCriterion("champion <", value, "champion");
            return (Criteria) this;
        }

        public Criteria andChampionLessThanOrEqualTo(String value) {
            addCriterion("champion <=", value, "champion");
            return (Criteria) this;
        }

        public Criteria andChampionLike(String value) {
            addCriterion("champion like", value, "champion");
            return (Criteria) this;
        }

        public Criteria andChampionNotLike(String value) {
            addCriterion("champion not like", value, "champion");
            return (Criteria) this;
        }

        public Criteria andChampionIn(List<String> values) {
            addCriterion("champion in", values, "champion");
            return (Criteria) this;
        }

        public Criteria andChampionNotIn(List<String> values) {
            addCriterion("champion not in", values, "champion");
            return (Criteria) this;
        }

        public Criteria andChampionBetween(String value1, String value2) {
            addCriterion("champion between", value1, value2, "champion");
            return (Criteria) this;
        }

        public Criteria andChampionNotBetween(String value1, String value2) {
            addCriterion("champion not between", value1, value2, "champion");
            return (Criteria) this;
        }

        public Criteria andSilverIsNull() {
            addCriterion("silver is null");
            return (Criteria) this;
        }

        public Criteria andSilverIsNotNull() {
            addCriterion("silver is not null");
            return (Criteria) this;
        }

        public Criteria andSilverEqualTo(String value) {
            addCriterion("silver =", value, "silver");
            return (Criteria) this;
        }

        public Criteria andSilverNotEqualTo(String value) {
            addCriterion("silver <>", value, "silver");
            return (Criteria) this;
        }

        public Criteria andSilverGreaterThan(String value) {
            addCriterion("silver >", value, "silver");
            return (Criteria) this;
        }

        public Criteria andSilverGreaterThanOrEqualTo(String value) {
            addCriterion("silver >=", value, "silver");
            return (Criteria) this;
        }

        public Criteria andSilverLessThan(String value) {
            addCriterion("silver <", value, "silver");
            return (Criteria) this;
        }

        public Criteria andSilverLessThanOrEqualTo(String value) {
            addCriterion("silver <=", value, "silver");
            return (Criteria) this;
        }

        public Criteria andSilverLike(String value) {
            addCriterion("silver like", value, "silver");
            return (Criteria) this;
        }

        public Criteria andSilverNotLike(String value) {
            addCriterion("silver not like", value, "silver");
            return (Criteria) this;
        }

        public Criteria andSilverIn(List<String> values) {
            addCriterion("silver in", values, "silver");
            return (Criteria) this;
        }

        public Criteria andSilverNotIn(List<String> values) {
            addCriterion("silver not in", values, "silver");
            return (Criteria) this;
        }

        public Criteria andSilverBetween(String value1, String value2) {
            addCriterion("silver between", value1, value2, "silver");
            return (Criteria) this;
        }

        public Criteria andSilverNotBetween(String value1, String value2) {
            addCriterion("silver not between", value1, value2, "silver");
            return (Criteria) this;
        }

        public Criteria andSupportNumIsNull() {
            addCriterion("support_num is null");
            return (Criteria) this;
        }

        public Criteria andSupportNumIsNotNull() {
            addCriterion("support_num is not null");
            return (Criteria) this;
        }

        public Criteria andSupportNumEqualTo(Integer value) {
            addCriterion("support_num =", value, "supportNum");
            return (Criteria) this;
        }

        public Criteria andSupportNumNotEqualTo(Integer value) {
            addCriterion("support_num <>", value, "supportNum");
            return (Criteria) this;
        }

        public Criteria andSupportNumGreaterThan(Integer value) {
            addCriterion("support_num >", value, "supportNum");
            return (Criteria) this;
        }

        public Criteria andSupportNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("support_num >=", value, "supportNum");
            return (Criteria) this;
        }

        public Criteria andSupportNumLessThan(Integer value) {
            addCriterion("support_num <", value, "supportNum");
            return (Criteria) this;
        }

        public Criteria andSupportNumLessThanOrEqualTo(Integer value) {
            addCriterion("support_num <=", value, "supportNum");
            return (Criteria) this;
        }

        public Criteria andSupportNumIn(List<Integer> values) {
            addCriterion("support_num in", values, "supportNum");
            return (Criteria) this;
        }

        public Criteria andSupportNumNotIn(List<Integer> values) {
            addCriterion("support_num not in", values, "supportNum");
            return (Criteria) this;
        }

        public Criteria andSupportNumBetween(Integer value1, Integer value2) {
            addCriterion("support_num between", value1, value2, "supportNum");
            return (Criteria) this;
        }

        public Criteria andSupportNumNotBetween(Integer value1, Integer value2) {
            addCriterion("support_num not between", value1, value2, "supportNum");
            return (Criteria) this;
        }

        public Criteria andIsLuckyIsNull() {
            addCriterion("is_lucky is null");
            return (Criteria) this;
        }

        public Criteria andIsLuckyIsNotNull() {
            addCriterion("is_lucky is not null");
            return (Criteria) this;
        }

        public Criteria andIsLuckyEqualTo(String value) {
            addCriterion("is_lucky =", value, "isLucky");
            return (Criteria) this;
        }

        public Criteria andIsLuckyNotEqualTo(String value) {
            addCriterion("is_lucky <>", value, "isLucky");
            return (Criteria) this;
        }

        public Criteria andIsLuckyGreaterThan(String value) {
            addCriterion("is_lucky >", value, "isLucky");
            return (Criteria) this;
        }

        public Criteria andIsLuckyGreaterThanOrEqualTo(String value) {
            addCriterion("is_lucky >=", value, "isLucky");
            return (Criteria) this;
        }

        public Criteria andIsLuckyLessThan(String value) {
            addCriterion("is_lucky <", value, "isLucky");
            return (Criteria) this;
        }

        public Criteria andIsLuckyLessThanOrEqualTo(String value) {
            addCriterion("is_lucky <=", value, "isLucky");
            return (Criteria) this;
        }

        public Criteria andIsLuckyLike(String value) {
            addCriterion("is_lucky like", value, "isLucky");
            return (Criteria) this;
        }

        public Criteria andIsLuckyNotLike(String value) {
            addCriterion("is_lucky not like", value, "isLucky");
            return (Criteria) this;
        }

        public Criteria andIsLuckyIn(List<String> values) {
            addCriterion("is_lucky in", values, "isLucky");
            return (Criteria) this;
        }

        public Criteria andIsLuckyNotIn(List<String> values) {
            addCriterion("is_lucky not in", values, "isLucky");
            return (Criteria) this;
        }

        public Criteria andIsLuckyBetween(String value1, String value2) {
            addCriterion("is_lucky between", value1, value2, "isLucky");
            return (Criteria) this;
        }

        public Criteria andIsLuckyNotBetween(String value1, String value2) {
            addCriterion("is_lucky not between", value1, value2, "isLucky");
            return (Criteria) this;
        }

        public Criteria andSupportMilestoneTimeIsNull() {
            addCriterion("support_milestone_time is null");
            return (Criteria) this;
        }

        public Criteria andSupportMilestoneTimeIsNotNull() {
            addCriterion("support_milestone_time is not null");
            return (Criteria) this;
        }

        public Criteria andSupportMilestoneTimeEqualTo(Date value) {
            addCriterion("support_milestone_time =", value, "supportMilestoneTime");
            return (Criteria) this;
        }

        public Criteria andSupportMilestoneTimeNotEqualTo(Date value) {
            addCriterion("support_milestone_time <>", value, "supportMilestoneTime");
            return (Criteria) this;
        }

        public Criteria andSupportMilestoneTimeGreaterThan(Date value) {
            addCriterion("support_milestone_time >", value, "supportMilestoneTime");
            return (Criteria) this;
        }

        public Criteria andSupportMilestoneTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("support_milestone_time >=", value, "supportMilestoneTime");
            return (Criteria) this;
        }

        public Criteria andSupportMilestoneTimeLessThan(Date value) {
            addCriterion("support_milestone_time <", value, "supportMilestoneTime");
            return (Criteria) this;
        }

        public Criteria andSupportMilestoneTimeLessThanOrEqualTo(Date value) {
            addCriterion("support_milestone_time <=", value, "supportMilestoneTime");
            return (Criteria) this;
        }

        public Criteria andSupportMilestoneTimeIn(List<Date> values) {
            addCriterion("support_milestone_time in", values, "supportMilestoneTime");
            return (Criteria) this;
        }

        public Criteria andSupportMilestoneTimeNotIn(List<Date> values) {
            addCriterion("support_milestone_time not in", values, "supportMilestoneTime");
            return (Criteria) this;
        }

        public Criteria andSupportMilestoneTimeBetween(Date value1, Date value2) {
            addCriterion("support_milestone_time between", value1, value2, "supportMilestoneTime");
            return (Criteria) this;
        }

        public Criteria andSupportMilestoneTimeNotBetween(Date value1, Date value2) {
            addCriterion("support_milestone_time not between", value1, value2, "supportMilestoneTime");
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