package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsPayLimitExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsPayLimitExample() {
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

        public Criteria andPayBusinessTypeIsNull() {
            addCriterion("pay_business_type is null");
            return (Criteria) this;
        }

        public Criteria andPayBusinessTypeIsNotNull() {
            addCriterion("pay_business_type is not null");
            return (Criteria) this;
        }

        public Criteria andPayBusinessTypeEqualTo(String value) {
            addCriterion("pay_business_type =", value, "payBusinessType");
            return (Criteria) this;
        }

        public Criteria andPayBusinessTypeNotEqualTo(String value) {
            addCriterion("pay_business_type <>", value, "payBusinessType");
            return (Criteria) this;
        }

        public Criteria andPayBusinessTypeGreaterThan(String value) {
            addCriterion("pay_business_type >", value, "payBusinessType");
            return (Criteria) this;
        }

        public Criteria andPayBusinessTypeGreaterThanOrEqualTo(String value) {
            addCriterion("pay_business_type >=", value, "payBusinessType");
            return (Criteria) this;
        }

        public Criteria andPayBusinessTypeLessThan(String value) {
            addCriterion("pay_business_type <", value, "payBusinessType");
            return (Criteria) this;
        }

        public Criteria andPayBusinessTypeLessThanOrEqualTo(String value) {
            addCriterion("pay_business_type <=", value, "payBusinessType");
            return (Criteria) this;
        }

        public Criteria andPayBusinessTypeLike(String value) {
            addCriterion("pay_business_type like", value, "payBusinessType");
            return (Criteria) this;
        }

        public Criteria andPayBusinessTypeNotLike(String value) {
            addCriterion("pay_business_type not like", value, "payBusinessType");
            return (Criteria) this;
        }

        public Criteria andPayBusinessTypeIn(List<String> values) {
            addCriterion("pay_business_type in", values, "payBusinessType");
            return (Criteria) this;
        }

        public Criteria andPayBusinessTypeNotIn(List<String> values) {
            addCriterion("pay_business_type not in", values, "payBusinessType");
            return (Criteria) this;
        }

        public Criteria andPayBusinessTypeBetween(String value1, String value2) {
            addCriterion("pay_business_type between", value1, value2, "payBusinessType");
            return (Criteria) this;
        }

        public Criteria andPayBusinessTypeNotBetween(String value1, String value2) {
            addCriterion("pay_business_type not between", value1, value2, "payBusinessType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeIsNull() {
            addCriterion("time_type is null");
            return (Criteria) this;
        }

        public Criteria andTimeTypeIsNotNull() {
            addCriterion("time_type is not null");
            return (Criteria) this;
        }

        public Criteria andTimeTypeEqualTo(String value) {
            addCriterion("time_type =", value, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeNotEqualTo(String value) {
            addCriterion("time_type <>", value, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeGreaterThan(String value) {
            addCriterion("time_type >", value, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("time_type >=", value, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeLessThan(String value) {
            addCriterion("time_type <", value, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeLessThanOrEqualTo(String value) {
            addCriterion("time_type <=", value, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeLike(String value) {
            addCriterion("time_type like", value, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeNotLike(String value) {
            addCriterion("time_type not like", value, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeIn(List<String> values) {
            addCriterion("time_type in", values, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeNotIn(List<String> values) {
            addCriterion("time_type not in", values, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeBetween(String value1, String value2) {
            addCriterion("time_type between", value1, value2, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeNotBetween(String value1, String value2) {
            addCriterion("time_type not between", value1, value2, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeStartIsNull() {
            addCriterion("time_start is null");
            return (Criteria) this;
        }

        public Criteria andTimeStartIsNotNull() {
            addCriterion("time_start is not null");
            return (Criteria) this;
        }

        public Criteria andTimeStartEqualTo(String value) {
            addCriterion("time_start =", value, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeStartNotEqualTo(String value) {
            addCriterion("time_start <>", value, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeStartGreaterThan(String value) {
            addCriterion("time_start >", value, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeStartGreaterThanOrEqualTo(String value) {
            addCriterion("time_start >=", value, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeStartLessThan(String value) {
            addCriterion("time_start <", value, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeStartLessThanOrEqualTo(String value) {
            addCriterion("time_start <=", value, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeStartLike(String value) {
            addCriterion("time_start like", value, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeStartNotLike(String value) {
            addCriterion("time_start not like", value, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeStartIn(List<String> values) {
            addCriterion("time_start in", values, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeStartNotIn(List<String> values) {
            addCriterion("time_start not in", values, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeStartBetween(String value1, String value2) {
            addCriterion("time_start between", value1, value2, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeStartNotBetween(String value1, String value2) {
            addCriterion("time_start not between", value1, value2, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeEndIsNull() {
            addCriterion("time_end is null");
            return (Criteria) this;
        }

        public Criteria andTimeEndIsNotNull() {
            addCriterion("time_end is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEndEqualTo(String value) {
            addCriterion("time_end =", value, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andTimeEndNotEqualTo(String value) {
            addCriterion("time_end <>", value, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andTimeEndGreaterThan(String value) {
            addCriterion("time_end >", value, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andTimeEndGreaterThanOrEqualTo(String value) {
            addCriterion("time_end >=", value, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andTimeEndLessThan(String value) {
            addCriterion("time_end <", value, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andTimeEndLessThanOrEqualTo(String value) {
            addCriterion("time_end <=", value, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andTimeEndLike(String value) {
            addCriterion("time_end like", value, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andTimeEndNotLike(String value) {
            addCriterion("time_end not like", value, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andTimeEndIn(List<String> values) {
            addCriterion("time_end in", values, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andTimeEndNotIn(List<String> values) {
            addCriterion("time_end not in", values, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andTimeEndBetween(String value1, String value2) {
            addCriterion("time_end between", value1, value2, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andTimeEndNotBetween(String value1, String value2) {
            addCriterion("time_end not between", value1, value2, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andLimitTypeIsNull() {
            addCriterion("limit_type is null");
            return (Criteria) this;
        }

        public Criteria andLimitTypeIsNotNull() {
            addCriterion("limit_type is not null");
            return (Criteria) this;
        }

        public Criteria andLimitTypeEqualTo(String value) {
            addCriterion("limit_type =", value, "limitType");
            return (Criteria) this;
        }

        public Criteria andLimitTypeNotEqualTo(String value) {
            addCriterion("limit_type <>", value, "limitType");
            return (Criteria) this;
        }

        public Criteria andLimitTypeGreaterThan(String value) {
            addCriterion("limit_type >", value, "limitType");
            return (Criteria) this;
        }

        public Criteria andLimitTypeGreaterThanOrEqualTo(String value) {
            addCriterion("limit_type >=", value, "limitType");
            return (Criteria) this;
        }

        public Criteria andLimitTypeLessThan(String value) {
            addCriterion("limit_type <", value, "limitType");
            return (Criteria) this;
        }

        public Criteria andLimitTypeLessThanOrEqualTo(String value) {
            addCriterion("limit_type <=", value, "limitType");
            return (Criteria) this;
        }

        public Criteria andLimitTypeLike(String value) {
            addCriterion("limit_type like", value, "limitType");
            return (Criteria) this;
        }

        public Criteria andLimitTypeNotLike(String value) {
            addCriterion("limit_type not like", value, "limitType");
            return (Criteria) this;
        }

        public Criteria andLimitTypeIn(List<String> values) {
            addCriterion("limit_type in", values, "limitType");
            return (Criteria) this;
        }

        public Criteria andLimitTypeNotIn(List<String> values) {
            addCriterion("limit_type not in", values, "limitType");
            return (Criteria) this;
        }

        public Criteria andLimitTypeBetween(String value1, String value2) {
            addCriterion("limit_type between", value1, value2, "limitType");
            return (Criteria) this;
        }

        public Criteria andLimitTypeNotBetween(String value1, String value2) {
            addCriterion("limit_type not between", value1, value2, "limitType");
            return (Criteria) this;
        }

        public Criteria andLimitEquleTypeIsNull() {
            addCriterion("limit_equle_type is null");
            return (Criteria) this;
        }

        public Criteria andLimitEquleTypeIsNotNull() {
            addCriterion("limit_equle_type is not null");
            return (Criteria) this;
        }

        public Criteria andLimitEquleTypeEqualTo(String value) {
            addCriterion("limit_equle_type =", value, "limitEquleType");
            return (Criteria) this;
        }

        public Criteria andLimitEquleTypeNotEqualTo(String value) {
            addCriterion("limit_equle_type <>", value, "limitEquleType");
            return (Criteria) this;
        }

        public Criteria andLimitEquleTypeGreaterThan(String value) {
            addCriterion("limit_equle_type >", value, "limitEquleType");
            return (Criteria) this;
        }

        public Criteria andLimitEquleTypeGreaterThanOrEqualTo(String value) {
            addCriterion("limit_equle_type >=", value, "limitEquleType");
            return (Criteria) this;
        }

        public Criteria andLimitEquleTypeLessThan(String value) {
            addCriterion("limit_equle_type <", value, "limitEquleType");
            return (Criteria) this;
        }

        public Criteria andLimitEquleTypeLessThanOrEqualTo(String value) {
            addCriterion("limit_equle_type <=", value, "limitEquleType");
            return (Criteria) this;
        }

        public Criteria andLimitEquleTypeLike(String value) {
            addCriterion("limit_equle_type like", value, "limitEquleType");
            return (Criteria) this;
        }

        public Criteria andLimitEquleTypeNotLike(String value) {
            addCriterion("limit_equle_type not like", value, "limitEquleType");
            return (Criteria) this;
        }

        public Criteria andLimitEquleTypeIn(List<String> values) {
            addCriterion("limit_equle_type in", values, "limitEquleType");
            return (Criteria) this;
        }

        public Criteria andLimitEquleTypeNotIn(List<String> values) {
            addCriterion("limit_equle_type not in", values, "limitEquleType");
            return (Criteria) this;
        }

        public Criteria andLimitEquleTypeBetween(String value1, String value2) {
            addCriterion("limit_equle_type between", value1, value2, "limitEquleType");
            return (Criteria) this;
        }

        public Criteria andLimitEquleTypeNotBetween(String value1, String value2) {
            addCriterion("limit_equle_type not between", value1, value2, "limitEquleType");
            return (Criteria) this;
        }

        public Criteria andLimitVauleIsNull() {
            addCriterion("limit_vaule is null");
            return (Criteria) this;
        }

        public Criteria andLimitVauleIsNotNull() {
            addCriterion("limit_vaule is not null");
            return (Criteria) this;
        }

        public Criteria andLimitVauleEqualTo(Integer value) {
            addCriterion("limit_vaule =", value, "limitVaule");
            return (Criteria) this;
        }

        public Criteria andLimitVauleNotEqualTo(Integer value) {
            addCriterion("limit_vaule <>", value, "limitVaule");
            return (Criteria) this;
        }

        public Criteria andLimitVauleGreaterThan(Integer value) {
            addCriterion("limit_vaule >", value, "limitVaule");
            return (Criteria) this;
        }

        public Criteria andLimitVauleGreaterThanOrEqualTo(Integer value) {
            addCriterion("limit_vaule >=", value, "limitVaule");
            return (Criteria) this;
        }

        public Criteria andLimitVauleLessThan(Integer value) {
            addCriterion("limit_vaule <", value, "limitVaule");
            return (Criteria) this;
        }

        public Criteria andLimitVauleLessThanOrEqualTo(Integer value) {
            addCriterion("limit_vaule <=", value, "limitVaule");
            return (Criteria) this;
        }

        public Criteria andLimitVauleIn(List<Integer> values) {
            addCriterion("limit_vaule in", values, "limitVaule");
            return (Criteria) this;
        }

        public Criteria andLimitVauleNotIn(List<Integer> values) {
            addCriterion("limit_vaule not in", values, "limitVaule");
            return (Criteria) this;
        }

        public Criteria andLimitVauleBetween(Integer value1, Integer value2) {
            addCriterion("limit_vaule between", value1, value2, "limitVaule");
            return (Criteria) this;
        }

        public Criteria andLimitVauleNotBetween(Integer value1, Integer value2) {
            addCriterion("limit_vaule not between", value1, value2, "limitVaule");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNull() {
            addCriterion("is_delete is null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNotNull() {
            addCriterion("is_delete is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteEqualTo(String value) {
            addCriterion("is_delete =", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotEqualTo(String value) {
            addCriterion("is_delete <>", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThan(String value) {
            addCriterion("is_delete >", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThanOrEqualTo(String value) {
            addCriterion("is_delete >=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThan(String value) {
            addCriterion("is_delete <", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThanOrEqualTo(String value) {
            addCriterion("is_delete <=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLike(String value) {
            addCriterion("is_delete like", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotLike(String value) {
            addCriterion("is_delete not like", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIn(List<String> values) {
            addCriterion("is_delete in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotIn(List<String> values) {
            addCriterion("is_delete not in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteBetween(String value1, String value2) {
            addCriterion("is_delete between", value1, value2, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotBetween(String value1, String value2) {
            addCriterion("is_delete not between", value1, value2, "isDelete");
            return (Criteria) this;
        }

        public Criteria andMUserIdIsNull() {
            addCriterion("m_user_id is null");
            return (Criteria) this;
        }

        public Criteria andMUserIdIsNotNull() {
            addCriterion("m_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andMUserIdEqualTo(Integer value) {
            addCriterion("m_user_id =", value, "mUserId");
            return (Criteria) this;
        }

        public Criteria andMUserIdNotEqualTo(Integer value) {
            addCriterion("m_user_id <>", value, "mUserId");
            return (Criteria) this;
        }

        public Criteria andMUserIdGreaterThan(Integer value) {
            addCriterion("m_user_id >", value, "mUserId");
            return (Criteria) this;
        }

        public Criteria andMUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("m_user_id >=", value, "mUserId");
            return (Criteria) this;
        }

        public Criteria andMUserIdLessThan(Integer value) {
            addCriterion("m_user_id <", value, "mUserId");
            return (Criteria) this;
        }

        public Criteria andMUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("m_user_id <=", value, "mUserId");
            return (Criteria) this;
        }

        public Criteria andMUserIdIn(List<Integer> values) {
            addCriterion("m_user_id in", values, "mUserId");
            return (Criteria) this;
        }

        public Criteria andMUserIdNotIn(List<Integer> values) {
            addCriterion("m_user_id not in", values, "mUserId");
            return (Criteria) this;
        }

        public Criteria andMUserIdBetween(Integer value1, Integer value2) {
            addCriterion("m_user_id between", value1, value2, "mUserId");
            return (Criteria) this;
        }

        public Criteria andMUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("m_user_id not between", value1, value2, "mUserId");
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