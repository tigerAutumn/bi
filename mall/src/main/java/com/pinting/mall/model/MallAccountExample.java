package com.pinting.mall.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MallAccountExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MallAccountExample() {
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

        public Criteria andAccountTypeIsNull() {
            addCriterion("account_type is null");
            return (Criteria) this;
        }

        public Criteria andAccountTypeIsNotNull() {
            addCriterion("account_type is not null");
            return (Criteria) this;
        }

        public Criteria andAccountTypeEqualTo(String value) {
            addCriterion("account_type =", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeNotEqualTo(String value) {
            addCriterion("account_type <>", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeGreaterThan(String value) {
            addCriterion("account_type >", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeGreaterThanOrEqualTo(String value) {
            addCriterion("account_type >=", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeLessThan(String value) {
            addCriterion("account_type <", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeLessThanOrEqualTo(String value) {
            addCriterion("account_type <=", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeLike(String value) {
            addCriterion("account_type like", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeNotLike(String value) {
            addCriterion("account_type not like", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeIn(List<String> values) {
            addCriterion("account_type in", values, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeNotIn(List<String> values) {
            addCriterion("account_type not in", values, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeBetween(String value1, String value2) {
            addCriterion("account_type between", value1, value2, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeNotBetween(String value1, String value2) {
            addCriterion("account_type not between", value1, value2, "accountType");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNull() {
            addCriterion("balance is null");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNotNull() {
            addCriterion("balance is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceEqualTo(Long value) {
            addCriterion("balance =", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotEqualTo(Long value) {
            addCriterion("balance <>", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThan(Long value) {
            addCriterion("balance >", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThanOrEqualTo(Long value) {
            addCriterion("balance >=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThan(Long value) {
            addCriterion("balance <", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThanOrEqualTo(Long value) {
            addCriterion("balance <=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceIn(List<Long> values) {
            addCriterion("balance in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotIn(List<Long> values) {
            addCriterion("balance not in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceBetween(Long value1, Long value2) {
            addCriterion("balance between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotBetween(Long value1, Long value2) {
            addCriterion("balance not between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andAvaliableBalanceIsNull() {
            addCriterion("avaliable_balance is null");
            return (Criteria) this;
        }

        public Criteria andAvaliableBalanceIsNotNull() {
            addCriterion("avaliable_balance is not null");
            return (Criteria) this;
        }

        public Criteria andAvaliableBalanceEqualTo(Long value) {
            addCriterion("avaliable_balance =", value, "avaliableBalance");
            return (Criteria) this;
        }

        public Criteria andAvaliableBalanceNotEqualTo(Long value) {
            addCriterion("avaliable_balance <>", value, "avaliableBalance");
            return (Criteria) this;
        }

        public Criteria andAvaliableBalanceGreaterThan(Long value) {
            addCriterion("avaliable_balance >", value, "avaliableBalance");
            return (Criteria) this;
        }

        public Criteria andAvaliableBalanceGreaterThanOrEqualTo(Long value) {
            addCriterion("avaliable_balance >=", value, "avaliableBalance");
            return (Criteria) this;
        }

        public Criteria andAvaliableBalanceLessThan(Long value) {
            addCriterion("avaliable_balance <", value, "avaliableBalance");
            return (Criteria) this;
        }

        public Criteria andAvaliableBalanceLessThanOrEqualTo(Long value) {
            addCriterion("avaliable_balance <=", value, "avaliableBalance");
            return (Criteria) this;
        }

        public Criteria andAvaliableBalanceIn(List<Long> values) {
            addCriterion("avaliable_balance in", values, "avaliableBalance");
            return (Criteria) this;
        }

        public Criteria andAvaliableBalanceNotIn(List<Long> values) {
            addCriterion("avaliable_balance not in", values, "avaliableBalance");
            return (Criteria) this;
        }

        public Criteria andAvaliableBalanceBetween(Long value1, Long value2) {
            addCriterion("avaliable_balance between", value1, value2, "avaliableBalance");
            return (Criteria) this;
        }

        public Criteria andAvaliableBalanceNotBetween(Long value1, Long value2) {
            addCriterion("avaliable_balance not between", value1, value2, "avaliableBalance");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceIsNull() {
            addCriterion("freeze_balance is null");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceIsNotNull() {
            addCriterion("freeze_balance is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceEqualTo(Long value) {
            addCriterion("freeze_balance =", value, "freezeBalance");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceNotEqualTo(Long value) {
            addCriterion("freeze_balance <>", value, "freezeBalance");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceGreaterThan(Long value) {
            addCriterion("freeze_balance >", value, "freezeBalance");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceGreaterThanOrEqualTo(Long value) {
            addCriterion("freeze_balance >=", value, "freezeBalance");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceLessThan(Long value) {
            addCriterion("freeze_balance <", value, "freezeBalance");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceLessThanOrEqualTo(Long value) {
            addCriterion("freeze_balance <=", value, "freezeBalance");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceIn(List<Long> values) {
            addCriterion("freeze_balance in", values, "freezeBalance");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceNotIn(List<Long> values) {
            addCriterion("freeze_balance not in", values, "freezeBalance");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceBetween(Long value1, Long value2) {
            addCriterion("freeze_balance between", value1, value2, "freezeBalance");
            return (Criteria) this;
        }

        public Criteria andFreezeBalanceNotBetween(Long value1, Long value2) {
            addCriterion("freeze_balance not between", value1, value2, "freezeBalance");
            return (Criteria) this;
        }

        public Criteria andTotalGainPointsIsNull() {
            addCriterion("total_gain_points is null");
            return (Criteria) this;
        }

        public Criteria andTotalGainPointsIsNotNull() {
            addCriterion("total_gain_points is not null");
            return (Criteria) this;
        }

        public Criteria andTotalGainPointsEqualTo(Long value) {
            addCriterion("total_gain_points =", value, "totalGainPoints");
            return (Criteria) this;
        }

        public Criteria andTotalGainPointsNotEqualTo(Long value) {
            addCriterion("total_gain_points <>", value, "totalGainPoints");
            return (Criteria) this;
        }

        public Criteria andTotalGainPointsGreaterThan(Long value) {
            addCriterion("total_gain_points >", value, "totalGainPoints");
            return (Criteria) this;
        }

        public Criteria andTotalGainPointsGreaterThanOrEqualTo(Long value) {
            addCriterion("total_gain_points >=", value, "totalGainPoints");
            return (Criteria) this;
        }

        public Criteria andTotalGainPointsLessThan(Long value) {
            addCriterion("total_gain_points <", value, "totalGainPoints");
            return (Criteria) this;
        }

        public Criteria andTotalGainPointsLessThanOrEqualTo(Long value) {
            addCriterion("total_gain_points <=", value, "totalGainPoints");
            return (Criteria) this;
        }

        public Criteria andTotalGainPointsIn(List<Long> values) {
            addCriterion("total_gain_points in", values, "totalGainPoints");
            return (Criteria) this;
        }

        public Criteria andTotalGainPointsNotIn(List<Long> values) {
            addCriterion("total_gain_points not in", values, "totalGainPoints");
            return (Criteria) this;
        }

        public Criteria andTotalGainPointsBetween(Long value1, Long value2) {
            addCriterion("total_gain_points between", value1, value2, "totalGainPoints");
            return (Criteria) this;
        }

        public Criteria andTotalGainPointsNotBetween(Long value1, Long value2) {
            addCriterion("total_gain_points not between", value1, value2, "totalGainPoints");
            return (Criteria) this;
        }

        public Criteria andTotalUsedPointsIsNull() {
            addCriterion("total_used_points is null");
            return (Criteria) this;
        }

        public Criteria andTotalUsedPointsIsNotNull() {
            addCriterion("total_used_points is not null");
            return (Criteria) this;
        }

        public Criteria andTotalUsedPointsEqualTo(Long value) {
            addCriterion("total_used_points =", value, "totalUsedPoints");
            return (Criteria) this;
        }

        public Criteria andTotalUsedPointsNotEqualTo(Long value) {
            addCriterion("total_used_points <>", value, "totalUsedPoints");
            return (Criteria) this;
        }

        public Criteria andTotalUsedPointsGreaterThan(Long value) {
            addCriterion("total_used_points >", value, "totalUsedPoints");
            return (Criteria) this;
        }

        public Criteria andTotalUsedPointsGreaterThanOrEqualTo(Long value) {
            addCriterion("total_used_points >=", value, "totalUsedPoints");
            return (Criteria) this;
        }

        public Criteria andTotalUsedPointsLessThan(Long value) {
            addCriterion("total_used_points <", value, "totalUsedPoints");
            return (Criteria) this;
        }

        public Criteria andTotalUsedPointsLessThanOrEqualTo(Long value) {
            addCriterion("total_used_points <=", value, "totalUsedPoints");
            return (Criteria) this;
        }

        public Criteria andTotalUsedPointsIn(List<Long> values) {
            addCriterion("total_used_points in", values, "totalUsedPoints");
            return (Criteria) this;
        }

        public Criteria andTotalUsedPointsNotIn(List<Long> values) {
            addCriterion("total_used_points not in", values, "totalUsedPoints");
            return (Criteria) this;
        }

        public Criteria andTotalUsedPointsBetween(Long value1, Long value2) {
            addCriterion("total_used_points between", value1, value2, "totalUsedPoints");
            return (Criteria) this;
        }

        public Criteria andTotalUsedPointsNotBetween(Long value1, Long value2) {
            addCriterion("total_used_points not between", value1, value2, "totalUsedPoints");
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