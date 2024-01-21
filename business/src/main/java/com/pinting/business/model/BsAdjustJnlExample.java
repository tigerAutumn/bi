package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsAdjustJnlExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsAdjustJnlExample() {
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

        public Criteria andCheckJnlIdIsNull() {
            addCriterion("check_jnl_id is null");
            return (Criteria) this;
        }

        public Criteria andCheckJnlIdIsNotNull() {
            addCriterion("check_jnl_id is not null");
            return (Criteria) this;
        }

        public Criteria andCheckJnlIdEqualTo(Integer value) {
            addCriterion("check_jnl_id =", value, "checkJnlId");
            return (Criteria) this;
        }

        public Criteria andCheckJnlIdNotEqualTo(Integer value) {
            addCriterion("check_jnl_id <>", value, "checkJnlId");
            return (Criteria) this;
        }

        public Criteria andCheckJnlIdGreaterThan(Integer value) {
            addCriterion("check_jnl_id >", value, "checkJnlId");
            return (Criteria) this;
        }

        public Criteria andCheckJnlIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_jnl_id >=", value, "checkJnlId");
            return (Criteria) this;
        }

        public Criteria andCheckJnlIdLessThan(Integer value) {
            addCriterion("check_jnl_id <", value, "checkJnlId");
            return (Criteria) this;
        }

        public Criteria andCheckJnlIdLessThanOrEqualTo(Integer value) {
            addCriterion("check_jnl_id <=", value, "checkJnlId");
            return (Criteria) this;
        }

        public Criteria andCheckJnlIdIn(List<Integer> values) {
            addCriterion("check_jnl_id in", values, "checkJnlId");
            return (Criteria) this;
        }

        public Criteria andCheckJnlIdNotIn(List<Integer> values) {
            addCriterion("check_jnl_id not in", values, "checkJnlId");
            return (Criteria) this;
        }

        public Criteria andCheckJnlIdBetween(Integer value1, Integer value2) {
            addCriterion("check_jnl_id between", value1, value2, "checkJnlId");
            return (Criteria) this;
        }

        public Criteria andCheckJnlIdNotBetween(Integer value1, Integer value2) {
            addCriterion("check_jnl_id not between", value1, value2, "checkJnlId");
            return (Criteria) this;
        }

        public Criteria andSysStatusIsNull() {
            addCriterion("sys_status is null");
            return (Criteria) this;
        }

        public Criteria andSysStatusIsNotNull() {
            addCriterion("sys_status is not null");
            return (Criteria) this;
        }

        public Criteria andSysStatusEqualTo(Integer value) {
            addCriterion("sys_status =", value, "sysStatus");
            return (Criteria) this;
        }

        public Criteria andSysStatusNotEqualTo(Integer value) {
            addCriterion("sys_status <>", value, "sysStatus");
            return (Criteria) this;
        }

        public Criteria andSysStatusGreaterThan(Integer value) {
            addCriterion("sys_status >", value, "sysStatus");
            return (Criteria) this;
        }

        public Criteria andSysStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("sys_status >=", value, "sysStatus");
            return (Criteria) this;
        }

        public Criteria andSysStatusLessThan(Integer value) {
            addCriterion("sys_status <", value, "sysStatus");
            return (Criteria) this;
        }

        public Criteria andSysStatusLessThanOrEqualTo(Integer value) {
            addCriterion("sys_status <=", value, "sysStatus");
            return (Criteria) this;
        }

        public Criteria andSysStatusIn(List<Integer> values) {
            addCriterion("sys_status in", values, "sysStatus");
            return (Criteria) this;
        }

        public Criteria andSysStatusNotIn(List<Integer> values) {
            addCriterion("sys_status not in", values, "sysStatus");
            return (Criteria) this;
        }

        public Criteria andSysStatusBetween(Integer value1, Integer value2) {
            addCriterion("sys_status between", value1, value2, "sysStatus");
            return (Criteria) this;
        }

        public Criteria andSysStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("sys_status not between", value1, value2, "sysStatus");
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

        public Criteria andCheckFileStatusIsNull() {
            addCriterion("check_file_status is null");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusIsNotNull() {
            addCriterion("check_file_status is not null");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusEqualTo(Integer value) {
            addCriterion("check_file_status =", value, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusNotEqualTo(Integer value) {
            addCriterion("check_file_status <>", value, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusGreaterThan(Integer value) {
            addCriterion("check_file_status >", value, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_file_status >=", value, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusLessThan(Integer value) {
            addCriterion("check_file_status <", value, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusLessThanOrEqualTo(Integer value) {
            addCriterion("check_file_status <=", value, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusIn(List<Integer> values) {
            addCriterion("check_file_status in", values, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusNotIn(List<Integer> values) {
            addCriterion("check_file_status not in", values, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusBetween(Integer value1, Integer value2) {
            addCriterion("check_file_status between", value1, value2, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("check_file_status not between", value1, value2, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileIdIsNull() {
            addCriterion("check_file_id is null");
            return (Criteria) this;
        }

        public Criteria andCheckFileIdIsNotNull() {
            addCriterion("check_file_id is not null");
            return (Criteria) this;
        }

        public Criteria andCheckFileIdEqualTo(Integer value) {
            addCriterion("check_file_id =", value, "checkFileId");
            return (Criteria) this;
        }

        public Criteria andCheckFileIdNotEqualTo(Integer value) {
            addCriterion("check_file_id <>", value, "checkFileId");
            return (Criteria) this;
        }

        public Criteria andCheckFileIdGreaterThan(Integer value) {
            addCriterion("check_file_id >", value, "checkFileId");
            return (Criteria) this;
        }

        public Criteria andCheckFileIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_file_id >=", value, "checkFileId");
            return (Criteria) this;
        }

        public Criteria andCheckFileIdLessThan(Integer value) {
            addCriterion("check_file_id <", value, "checkFileId");
            return (Criteria) this;
        }

        public Criteria andCheckFileIdLessThanOrEqualTo(Integer value) {
            addCriterion("check_file_id <=", value, "checkFileId");
            return (Criteria) this;
        }

        public Criteria andCheckFileIdIn(List<Integer> values) {
            addCriterion("check_file_id in", values, "checkFileId");
            return (Criteria) this;
        }

        public Criteria andCheckFileIdNotIn(List<Integer> values) {
            addCriterion("check_file_id not in", values, "checkFileId");
            return (Criteria) this;
        }

        public Criteria andCheckFileIdBetween(Integer value1, Integer value2) {
            addCriterion("check_file_id between", value1, value2, "checkFileId");
            return (Criteria) this;
        }

        public Criteria andCheckFileIdNotBetween(Integer value1, Integer value2) {
            addCriterion("check_file_id not between", value1, value2, "checkFileId");
            return (Criteria) this;
        }

        public Criteria andIsAdjustIsNull() {
            addCriterion("is_adjust is null");
            return (Criteria) this;
        }

        public Criteria andIsAdjustIsNotNull() {
            addCriterion("is_adjust is not null");
            return (Criteria) this;
        }

        public Criteria andIsAdjustEqualTo(Integer value) {
            addCriterion("is_adjust =", value, "isAdjust");
            return (Criteria) this;
        }

        public Criteria andIsAdjustNotEqualTo(Integer value) {
            addCriterion("is_adjust <>", value, "isAdjust");
            return (Criteria) this;
        }

        public Criteria andIsAdjustGreaterThan(Integer value) {
            addCriterion("is_adjust >", value, "isAdjust");
            return (Criteria) this;
        }

        public Criteria andIsAdjustGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_adjust >=", value, "isAdjust");
            return (Criteria) this;
        }

        public Criteria andIsAdjustLessThan(Integer value) {
            addCriterion("is_adjust <", value, "isAdjust");
            return (Criteria) this;
        }

        public Criteria andIsAdjustLessThanOrEqualTo(Integer value) {
            addCriterion("is_adjust <=", value, "isAdjust");
            return (Criteria) this;
        }

        public Criteria andIsAdjustIn(List<Integer> values) {
            addCriterion("is_adjust in", values, "isAdjust");
            return (Criteria) this;
        }

        public Criteria andIsAdjustNotIn(List<Integer> values) {
            addCriterion("is_adjust not in", values, "isAdjust");
            return (Criteria) this;
        }

        public Criteria andIsAdjustBetween(Integer value1, Integer value2) {
            addCriterion("is_adjust between", value1, value2, "isAdjust");
            return (Criteria) this;
        }

        public Criteria andIsAdjustNotBetween(Integer value1, Integer value2) {
            addCriterion("is_adjust not between", value1, value2, "isAdjust");
            return (Criteria) this;
        }

        public Criteria andAdjustUserIdIsNull() {
            addCriterion("adjust_user_id is null");
            return (Criteria) this;
        }

        public Criteria andAdjustUserIdIsNotNull() {
            addCriterion("adjust_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustUserIdEqualTo(Integer value) {
            addCriterion("adjust_user_id =", value, "adjustUserId");
            return (Criteria) this;
        }

        public Criteria andAdjustUserIdNotEqualTo(Integer value) {
            addCriterion("adjust_user_id <>", value, "adjustUserId");
            return (Criteria) this;
        }

        public Criteria andAdjustUserIdGreaterThan(Integer value) {
            addCriterion("adjust_user_id >", value, "adjustUserId");
            return (Criteria) this;
        }

        public Criteria andAdjustUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("adjust_user_id >=", value, "adjustUserId");
            return (Criteria) this;
        }

        public Criteria andAdjustUserIdLessThan(Integer value) {
            addCriterion("adjust_user_id <", value, "adjustUserId");
            return (Criteria) this;
        }

        public Criteria andAdjustUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("adjust_user_id <=", value, "adjustUserId");
            return (Criteria) this;
        }

        public Criteria andAdjustUserIdIn(List<Integer> values) {
            addCriterion("adjust_user_id in", values, "adjustUserId");
            return (Criteria) this;
        }

        public Criteria andAdjustUserIdNotIn(List<Integer> values) {
            addCriterion("adjust_user_id not in", values, "adjustUserId");
            return (Criteria) this;
        }

        public Criteria andAdjustUserIdBetween(Integer value1, Integer value2) {
            addCriterion("adjust_user_id between", value1, value2, "adjustUserId");
            return (Criteria) this;
        }

        public Criteria andAdjustUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("adjust_user_id not between", value1, value2, "adjustUserId");
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

        public Criteria andAdjustTimeIsNull() {
            addCriterion("adjust_time is null");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeIsNotNull() {
            addCriterion("adjust_time is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeEqualTo(Date value) {
            addCriterion("adjust_time =", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeNotEqualTo(Date value) {
            addCriterion("adjust_time <>", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeGreaterThan(Date value) {
            addCriterion("adjust_time >", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("adjust_time >=", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeLessThan(Date value) {
            addCriterion("adjust_time <", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeLessThanOrEqualTo(Date value) {
            addCriterion("adjust_time <=", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeIn(List<Date> values) {
            addCriterion("adjust_time in", values, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeNotIn(List<Date> values) {
            addCriterion("adjust_time not in", values, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeBetween(Date value1, Date value2) {
            addCriterion("adjust_time between", value1, value2, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeNotBetween(Date value1, Date value2) {
            addCriterion("adjust_time not between", value1, value2, "adjustTime");
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