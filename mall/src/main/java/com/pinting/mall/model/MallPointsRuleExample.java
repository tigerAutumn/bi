package com.pinting.mall.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MallPointsRuleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MallPointsRuleExample() {
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

        public Criteria andGetSceneIsNull() {
            addCriterion("get_scene is null");
            return (Criteria) this;
        }

        public Criteria andGetSceneIsNotNull() {
            addCriterion("get_scene is not null");
            return (Criteria) this;
        }

        public Criteria andGetSceneEqualTo(String value) {
            addCriterion("get_scene =", value, "getScene");
            return (Criteria) this;
        }

        public Criteria andGetSceneNotEqualTo(String value) {
            addCriterion("get_scene <>", value, "getScene");
            return (Criteria) this;
        }

        public Criteria andGetSceneGreaterThan(String value) {
            addCriterion("get_scene >", value, "getScene");
            return (Criteria) this;
        }

        public Criteria andGetSceneGreaterThanOrEqualTo(String value) {
            addCriterion("get_scene >=", value, "getScene");
            return (Criteria) this;
        }

        public Criteria andGetSceneLessThan(String value) {
            addCriterion("get_scene <", value, "getScene");
            return (Criteria) this;
        }

        public Criteria andGetSceneLessThanOrEqualTo(String value) {
            addCriterion("get_scene <=", value, "getScene");
            return (Criteria) this;
        }

        public Criteria andGetSceneLike(String value) {
            addCriterion("get_scene like", value, "getScene");
            return (Criteria) this;
        }

        public Criteria andGetSceneNotLike(String value) {
            addCriterion("get_scene not like", value, "getScene");
            return (Criteria) this;
        }

        public Criteria andGetSceneIn(List<String> values) {
            addCriterion("get_scene in", values, "getScene");
            return (Criteria) this;
        }

        public Criteria andGetSceneNotIn(List<String> values) {
            addCriterion("get_scene not in", values, "getScene");
            return (Criteria) this;
        }

        public Criteria andGetSceneBetween(String value1, String value2) {
            addCriterion("get_scene between", value1, value2, "getScene");
            return (Criteria) this;
        }

        public Criteria andGetSceneNotBetween(String value1, String value2) {
            addCriterion("get_scene not between", value1, value2, "getScene");
            return (Criteria) this;
        }

        public Criteria andGetTimesIsNull() {
            addCriterion("get_times is null");
            return (Criteria) this;
        }

        public Criteria andGetTimesIsNotNull() {
            addCriterion("get_times is not null");
            return (Criteria) this;
        }

        public Criteria andGetTimesEqualTo(String value) {
            addCriterion("get_times =", value, "getTimes");
            return (Criteria) this;
        }

        public Criteria andGetTimesNotEqualTo(String value) {
            addCriterion("get_times <>", value, "getTimes");
            return (Criteria) this;
        }

        public Criteria andGetTimesGreaterThan(String value) {
            addCriterion("get_times >", value, "getTimes");
            return (Criteria) this;
        }

        public Criteria andGetTimesGreaterThanOrEqualTo(String value) {
            addCriterion("get_times >=", value, "getTimes");
            return (Criteria) this;
        }

        public Criteria andGetTimesLessThan(String value) {
            addCriterion("get_times <", value, "getTimes");
            return (Criteria) this;
        }

        public Criteria andGetTimesLessThanOrEqualTo(String value) {
            addCriterion("get_times <=", value, "getTimes");
            return (Criteria) this;
        }

        public Criteria andGetTimesLike(String value) {
            addCriterion("get_times like", value, "getTimes");
            return (Criteria) this;
        }

        public Criteria andGetTimesNotLike(String value) {
            addCriterion("get_times not like", value, "getTimes");
            return (Criteria) this;
        }

        public Criteria andGetTimesIn(List<String> values) {
            addCriterion("get_times in", values, "getTimes");
            return (Criteria) this;
        }

        public Criteria andGetTimesNotIn(List<String> values) {
            addCriterion("get_times not in", values, "getTimes");
            return (Criteria) this;
        }

        public Criteria andGetTimesBetween(String value1, String value2) {
            addCriterion("get_times between", value1, value2, "getTimes");
            return (Criteria) this;
        }

        public Criteria andGetTimesNotBetween(String value1, String value2) {
            addCriterion("get_times not between", value1, value2, "getTimes");
            return (Criteria) this;
        }

        public Criteria andGetTimeTypeIsNull() {
            addCriterion("get_time_type is null");
            return (Criteria) this;
        }

        public Criteria andGetTimeTypeIsNotNull() {
            addCriterion("get_time_type is not null");
            return (Criteria) this;
        }

        public Criteria andGetTimeTypeEqualTo(String value) {
            addCriterion("get_time_type =", value, "getTimeType");
            return (Criteria) this;
        }

        public Criteria andGetTimeTypeNotEqualTo(String value) {
            addCriterion("get_time_type <>", value, "getTimeType");
            return (Criteria) this;
        }

        public Criteria andGetTimeTypeGreaterThan(String value) {
            addCriterion("get_time_type >", value, "getTimeType");
            return (Criteria) this;
        }

        public Criteria andGetTimeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("get_time_type >=", value, "getTimeType");
            return (Criteria) this;
        }

        public Criteria andGetTimeTypeLessThan(String value) {
            addCriterion("get_time_type <", value, "getTimeType");
            return (Criteria) this;
        }

        public Criteria andGetTimeTypeLessThanOrEqualTo(String value) {
            addCriterion("get_time_type <=", value, "getTimeType");
            return (Criteria) this;
        }

        public Criteria andGetTimeTypeLike(String value) {
            addCriterion("get_time_type like", value, "getTimeType");
            return (Criteria) this;
        }

        public Criteria andGetTimeTypeNotLike(String value) {
            addCriterion("get_time_type not like", value, "getTimeType");
            return (Criteria) this;
        }

        public Criteria andGetTimeTypeIn(List<String> values) {
            addCriterion("get_time_type in", values, "getTimeType");
            return (Criteria) this;
        }

        public Criteria andGetTimeTypeNotIn(List<String> values) {
            addCriterion("get_time_type not in", values, "getTimeType");
            return (Criteria) this;
        }

        public Criteria andGetTimeTypeBetween(String value1, String value2) {
            addCriterion("get_time_type between", value1, value2, "getTimeType");
            return (Criteria) this;
        }

        public Criteria andGetTimeTypeNotBetween(String value1, String value2) {
            addCriterion("get_time_type not between", value1, value2, "getTimeType");
            return (Criteria) this;
        }

        public Criteria andPointsIsNull() {
            addCriterion("points is null");
            return (Criteria) this;
        }

        public Criteria andPointsIsNotNull() {
            addCriterion("points is not null");
            return (Criteria) this;
        }

        public Criteria andPointsEqualTo(Long value) {
            addCriterion("points =", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsNotEqualTo(Long value) {
            addCriterion("points <>", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsGreaterThan(Long value) {
            addCriterion("points >", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsGreaterThanOrEqualTo(Long value) {
            addCriterion("points >=", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsLessThan(Long value) {
            addCriterion("points <", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsLessThanOrEqualTo(Long value) {
            addCriterion("points <=", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsIn(List<Long> values) {
            addCriterion("points in", values, "points");
            return (Criteria) this;
        }

        public Criteria andPointsNotIn(List<Long> values) {
            addCriterion("points not in", values, "points");
            return (Criteria) this;
        }

        public Criteria andPointsBetween(Long value1, Long value2) {
            addCriterion("points between", value1, value2, "points");
            return (Criteria) this;
        }

        public Criteria andPointsNotBetween(Long value1, Long value2) {
            addCriterion("points not between", value1, value2, "points");
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

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
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

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(Integer value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(Integer value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(Integer value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(Integer value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(Integer value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(Integer value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<Integer> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<Integer> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(Integer value1, Integer value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(Integer value1, Integer value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andLastOperatorIsNull() {
            addCriterion("last_operator is null");
            return (Criteria) this;
        }

        public Criteria andLastOperatorIsNotNull() {
            addCriterion("last_operator is not null");
            return (Criteria) this;
        }

        public Criteria andLastOperatorEqualTo(Integer value) {
            addCriterion("last_operator =", value, "lastOperator");
            return (Criteria) this;
        }

        public Criteria andLastOperatorNotEqualTo(Integer value) {
            addCriterion("last_operator <>", value, "lastOperator");
            return (Criteria) this;
        }

        public Criteria andLastOperatorGreaterThan(Integer value) {
            addCriterion("last_operator >", value, "lastOperator");
            return (Criteria) this;
        }

        public Criteria andLastOperatorGreaterThanOrEqualTo(Integer value) {
            addCriterion("last_operator >=", value, "lastOperator");
            return (Criteria) this;
        }

        public Criteria andLastOperatorLessThan(Integer value) {
            addCriterion("last_operator <", value, "lastOperator");
            return (Criteria) this;
        }

        public Criteria andLastOperatorLessThanOrEqualTo(Integer value) {
            addCriterion("last_operator <=", value, "lastOperator");
            return (Criteria) this;
        }

        public Criteria andLastOperatorIn(List<Integer> values) {
            addCriterion("last_operator in", values, "lastOperator");
            return (Criteria) this;
        }

        public Criteria andLastOperatorNotIn(List<Integer> values) {
            addCriterion("last_operator not in", values, "lastOperator");
            return (Criteria) this;
        }

        public Criteria andLastOperatorBetween(Integer value1, Integer value2) {
            addCriterion("last_operator between", value1, value2, "lastOperator");
            return (Criteria) this;
        }

        public Criteria andLastOperatorNotBetween(Integer value1, Integer value2) {
            addCriterion("last_operator not between", value1, value2, "lastOperator");
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