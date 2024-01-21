package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsAutoInterestTicketRuleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsAutoInterestTicketRuleExample() {
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

        public Criteria andSerialNoIsNull() {
            addCriterion("serial_no is null");
            return (Criteria) this;
        }

        public Criteria andSerialNoIsNotNull() {
            addCriterion("serial_no is not null");
            return (Criteria) this;
        }

        public Criteria andSerialNoEqualTo(String value) {
            addCriterion("serial_no =", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotEqualTo(String value) {
            addCriterion("serial_no <>", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThan(String value) {
            addCriterion("serial_no >", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThanOrEqualTo(String value) {
            addCriterion("serial_no >=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThan(String value) {
            addCriterion("serial_no <", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThanOrEqualTo(String value) {
            addCriterion("serial_no <=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLike(String value) {
            addCriterion("serial_no like", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotLike(String value) {
            addCriterion("serial_no not like", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoIn(List<String> values) {
            addCriterion("serial_no in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotIn(List<String> values) {
            addCriterion("serial_no not in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoBetween(String value1, String value2) {
            addCriterion("serial_no between", value1, value2, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotBetween(String value1, String value2) {
            addCriterion("serial_no not between", value1, value2, "serialNo");
            return (Criteria) this;
        }

        public Criteria andAgentIdsIsNull() {
            addCriterion("agent_ids is null");
            return (Criteria) this;
        }

        public Criteria andAgentIdsIsNotNull() {
            addCriterion("agent_ids is not null");
            return (Criteria) this;
        }

        public Criteria andAgentIdsEqualTo(String value) {
            addCriterion("agent_ids =", value, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsNotEqualTo(String value) {
            addCriterion("agent_ids <>", value, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsGreaterThan(String value) {
            addCriterion("agent_ids >", value, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsGreaterThanOrEqualTo(String value) {
            addCriterion("agent_ids >=", value, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsLessThan(String value) {
            addCriterion("agent_ids <", value, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsLessThanOrEqualTo(String value) {
            addCriterion("agent_ids <=", value, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsLike(String value) {
            addCriterion("agent_ids like", value, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsNotLike(String value) {
            addCriterion("agent_ids not like", value, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsIn(List<String> values) {
            addCriterion("agent_ids in", values, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsNotIn(List<String> values) {
            addCriterion("agent_ids not in", values, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsBetween(String value1, String value2) {
            addCriterion("agent_ids between", value1, value2, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsNotBetween(String value1, String value2) {
            addCriterion("agent_ids not between", value1, value2, "agentIds");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeIsNull() {
            addCriterion("trigger_type is null");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeIsNotNull() {
            addCriterion("trigger_type is not null");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeEqualTo(String value) {
            addCriterion("trigger_type =", value, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeNotEqualTo(String value) {
            addCriterion("trigger_type <>", value, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeGreaterThan(String value) {
            addCriterion("trigger_type >", value, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeGreaterThanOrEqualTo(String value) {
            addCriterion("trigger_type >=", value, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeLessThan(String value) {
            addCriterion("trigger_type <", value, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeLessThanOrEqualTo(String value) {
            addCriterion("trigger_type <=", value, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeLike(String value) {
            addCriterion("trigger_type like", value, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeNotLike(String value) {
            addCriterion("trigger_type not like", value, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeIn(List<String> values) {
            addCriterion("trigger_type in", values, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeNotIn(List<String> values) {
            addCriterion("trigger_type not in", values, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeBetween(String value1, String value2) {
            addCriterion("trigger_type between", value1, value2, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeNotBetween(String value1, String value2) {
            addCriterion("trigger_type not between", value1, value2, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeStartIsNull() {
            addCriterion("trigger_time_start is null");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeStartIsNotNull() {
            addCriterion("trigger_time_start is not null");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeStartEqualTo(Date value) {
            addCriterion("trigger_time_start =", value, "triggerTimeStart");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeStartNotEqualTo(Date value) {
            addCriterion("trigger_time_start <>", value, "triggerTimeStart");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeStartGreaterThan(Date value) {
            addCriterion("trigger_time_start >", value, "triggerTimeStart");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeStartGreaterThanOrEqualTo(Date value) {
            addCriterion("trigger_time_start >=", value, "triggerTimeStart");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeStartLessThan(Date value) {
            addCriterion("trigger_time_start <", value, "triggerTimeStart");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeStartLessThanOrEqualTo(Date value) {
            addCriterion("trigger_time_start <=", value, "triggerTimeStart");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeStartIn(List<Date> values) {
            addCriterion("trigger_time_start in", values, "triggerTimeStart");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeStartNotIn(List<Date> values) {
            addCriterion("trigger_time_start not in", values, "triggerTimeStart");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeStartBetween(Date value1, Date value2) {
            addCriterion("trigger_time_start between", value1, value2, "triggerTimeStart");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeStartNotBetween(Date value1, Date value2) {
            addCriterion("trigger_time_start not between", value1, value2, "triggerTimeStart");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeEndIsNull() {
            addCriterion("trigger_time_end is null");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeEndIsNotNull() {
            addCriterion("trigger_time_end is not null");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeEndEqualTo(Date value) {
            addCriterion("trigger_time_end =", value, "triggerTimeEnd");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeEndNotEqualTo(Date value) {
            addCriterion("trigger_time_end <>", value, "triggerTimeEnd");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeEndGreaterThan(Date value) {
            addCriterion("trigger_time_end >", value, "triggerTimeEnd");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeEndGreaterThanOrEqualTo(Date value) {
            addCriterion("trigger_time_end >=", value, "triggerTimeEnd");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeEndLessThan(Date value) {
            addCriterion("trigger_time_end <", value, "triggerTimeEnd");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeEndLessThanOrEqualTo(Date value) {
            addCriterion("trigger_time_end <=", value, "triggerTimeEnd");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeEndIn(List<Date> values) {
            addCriterion("trigger_time_end in", values, "triggerTimeEnd");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeEndNotIn(List<Date> values) {
            addCriterion("trigger_time_end not in", values, "triggerTimeEnd");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeEndBetween(Date value1, Date value2) {
            addCriterion("trigger_time_end between", value1, value2, "triggerTimeEnd");
            return (Criteria) this;
        }

        public Criteria andTriggerTimeEndNotBetween(Date value1, Date value2) {
            addCriterion("trigger_time_end not between", value1, value2, "triggerTimeEnd");
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