package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BsUserBalanceDailyRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsUserBalanceDailyRecordExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andHfUserIdIsNull() {
            addCriterion("hf_user_id is null");
            return (Criteria) this;
        }

        public Criteria andHfUserIdIsNotNull() {
            addCriterion("hf_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andHfUserIdEqualTo(String value) {
            addCriterion("hf_user_id =", value, "hfUserId");
            return (Criteria) this;
        }

        public Criteria andHfUserIdNotEqualTo(String value) {
            addCriterion("hf_user_id <>", value, "hfUserId");
            return (Criteria) this;
        }

        public Criteria andHfUserIdGreaterThan(String value) {
            addCriterion("hf_user_id >", value, "hfUserId");
            return (Criteria) this;
        }

        public Criteria andHfUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("hf_user_id >=", value, "hfUserId");
            return (Criteria) this;
        }

        public Criteria andHfUserIdLessThan(String value) {
            addCriterion("hf_user_id <", value, "hfUserId");
            return (Criteria) this;
        }

        public Criteria andHfUserIdLessThanOrEqualTo(String value) {
            addCriterion("hf_user_id <=", value, "hfUserId");
            return (Criteria) this;
        }

        public Criteria andHfUserIdLike(String value) {
            addCriterion("hf_user_id like", value, "hfUserId");
            return (Criteria) this;
        }

        public Criteria andHfUserIdNotLike(String value) {
            addCriterion("hf_user_id not like", value, "hfUserId");
            return (Criteria) this;
        }

        public Criteria andHfUserIdIn(List<String> values) {
            addCriterion("hf_user_id in", values, "hfUserId");
            return (Criteria) this;
        }

        public Criteria andHfUserIdNotIn(List<String> values) {
            addCriterion("hf_user_id not in", values, "hfUserId");
            return (Criteria) this;
        }

        public Criteria andHfUserIdBetween(String value1, String value2) {
            addCriterion("hf_user_id between", value1, value2, "hfUserId");
            return (Criteria) this;
        }

        public Criteria andHfUserIdNotBetween(String value1, String value2) {
            addCriterion("hf_user_id not between", value1, value2, "hfUserId");
            return (Criteria) this;
        }

        public Criteria andPaycheckDateIsNull() {
            addCriterion("paycheck_date is null");
            return (Criteria) this;
        }

        public Criteria andPaycheckDateIsNotNull() {
            addCriterion("paycheck_date is not null");
            return (Criteria) this;
        }

        public Criteria andPaycheckDateEqualTo(Date value) {
            addCriterionForJDBCDate("paycheck_date =", value, "paycheckDate");
            return (Criteria) this;
        }

        public Criteria andPaycheckDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("paycheck_date <>", value, "paycheckDate");
            return (Criteria) this;
        }

        public Criteria andPaycheckDateGreaterThan(Date value) {
            addCriterionForJDBCDate("paycheck_date >", value, "paycheckDate");
            return (Criteria) this;
        }

        public Criteria andPaycheckDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("paycheck_date >=", value, "paycheckDate");
            return (Criteria) this;
        }

        public Criteria andPaycheckDateLessThan(Date value) {
            addCriterionForJDBCDate("paycheck_date <", value, "paycheckDate");
            return (Criteria) this;
        }

        public Criteria andPaycheckDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("paycheck_date <=", value, "paycheckDate");
            return (Criteria) this;
        }

        public Criteria andPaycheckDateIn(List<Date> values) {
            addCriterionForJDBCDate("paycheck_date in", values, "paycheckDate");
            return (Criteria) this;
        }

        public Criteria andPaycheckDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("paycheck_date not in", values, "paycheckDate");
            return (Criteria) this;
        }

        public Criteria andPaycheckDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("paycheck_date between", value1, value2, "paycheckDate");
            return (Criteria) this;
        }

        public Criteria andPaycheckDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("paycheck_date not between", value1, value2, "paycheckDate");
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

        public Criteria andBalanceEqualTo(Double value) {
            addCriterion("balance =", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotEqualTo(Double value) {
            addCriterion("balance <>", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThan(Double value) {
            addCriterion("balance >", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThanOrEqualTo(Double value) {
            addCriterion("balance >=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThan(Double value) {
            addCriterion("balance <", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThanOrEqualTo(Double value) {
            addCriterion("balance <=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceIn(List<Double> values) {
            addCriterion("balance in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotIn(List<Double> values) {
            addCriterion("balance not in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceBetween(Double value1, Double value2) {
            addCriterion("balance between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotBetween(Double value1, Double value2) {
            addCriterion("balance not between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andDepJshBalanceIsNull() {
            addCriterion("dep_jsh_balance is null");
            return (Criteria) this;
        }

        public Criteria andDepJshBalanceIsNotNull() {
            addCriterion("dep_jsh_balance is not null");
            return (Criteria) this;
        }

        public Criteria andDepJshBalanceEqualTo(Double value) {
            addCriterion("dep_jsh_balance =", value, "depJshBalance");
            return (Criteria) this;
        }

        public Criteria andDepJshBalanceNotEqualTo(Double value) {
            addCriterion("dep_jsh_balance <>", value, "depJshBalance");
            return (Criteria) this;
        }

        public Criteria andDepJshBalanceGreaterThan(Double value) {
            addCriterion("dep_jsh_balance >", value, "depJshBalance");
            return (Criteria) this;
        }

        public Criteria andDepJshBalanceGreaterThanOrEqualTo(Double value) {
            addCriterion("dep_jsh_balance >=", value, "depJshBalance");
            return (Criteria) this;
        }

        public Criteria andDepJshBalanceLessThan(Double value) {
            addCriterion("dep_jsh_balance <", value, "depJshBalance");
            return (Criteria) this;
        }

        public Criteria andDepJshBalanceLessThanOrEqualTo(Double value) {
            addCriterion("dep_jsh_balance <=", value, "depJshBalance");
            return (Criteria) this;
        }

        public Criteria andDepJshBalanceIn(List<Double> values) {
            addCriterion("dep_jsh_balance in", values, "depJshBalance");
            return (Criteria) this;
        }

        public Criteria andDepJshBalanceNotIn(List<Double> values) {
            addCriterion("dep_jsh_balance not in", values, "depJshBalance");
            return (Criteria) this;
        }

        public Criteria andDepJshBalanceBetween(Double value1, Double value2) {
            addCriterion("dep_jsh_balance between", value1, value2, "depJshBalance");
            return (Criteria) this;
        }

        public Criteria andDepJshBalanceNotBetween(Double value1, Double value2) {
            addCriterion("dep_jsh_balance not between", value1, value2, "depJshBalance");
            return (Criteria) this;
        }

        public Criteria andZanAuthBalanceIsNull() {
            addCriterion("zan_auth_balance is null");
            return (Criteria) this;
        }

        public Criteria andZanAuthBalanceIsNotNull() {
            addCriterion("zan_auth_balance is not null");
            return (Criteria) this;
        }

        public Criteria andZanAuthBalanceEqualTo(Double value) {
            addCriterion("zan_auth_balance =", value, "zanAuthBalance");
            return (Criteria) this;
        }

        public Criteria andZanAuthBalanceNotEqualTo(Double value) {
            addCriterion("zan_auth_balance <>", value, "zanAuthBalance");
            return (Criteria) this;
        }

        public Criteria andZanAuthBalanceGreaterThan(Double value) {
            addCriterion("zan_auth_balance >", value, "zanAuthBalance");
            return (Criteria) this;
        }

        public Criteria andZanAuthBalanceGreaterThanOrEqualTo(Double value) {
            addCriterion("zan_auth_balance >=", value, "zanAuthBalance");
            return (Criteria) this;
        }

        public Criteria andZanAuthBalanceLessThan(Double value) {
            addCriterion("zan_auth_balance <", value, "zanAuthBalance");
            return (Criteria) this;
        }

        public Criteria andZanAuthBalanceLessThanOrEqualTo(Double value) {
            addCriterion("zan_auth_balance <=", value, "zanAuthBalance");
            return (Criteria) this;
        }

        public Criteria andZanAuthBalanceIn(List<Double> values) {
            addCriterion("zan_auth_balance in", values, "zanAuthBalance");
            return (Criteria) this;
        }

        public Criteria andZanAuthBalanceNotIn(List<Double> values) {
            addCriterion("zan_auth_balance not in", values, "zanAuthBalance");
            return (Criteria) this;
        }

        public Criteria andZanAuthBalanceBetween(Double value1, Double value2) {
            addCriterion("zan_auth_balance between", value1, value2, "zanAuthBalance");
            return (Criteria) this;
        }

        public Criteria andZanAuthBalanceNotBetween(Double value1, Double value2) {
            addCriterion("zan_auth_balance not between", value1, value2, "zanAuthBalance");
            return (Criteria) this;
        }

        public Criteria andYunAuthBalanceIsNull() {
            addCriterion("yun_auth_balance is null");
            return (Criteria) this;
        }

        public Criteria andYunAuthBalanceIsNotNull() {
            addCriterion("yun_auth_balance is not null");
            return (Criteria) this;
        }

        public Criteria andYunAuthBalanceEqualTo(Double value) {
            addCriterion("yun_auth_balance =", value, "yunAuthBalance");
            return (Criteria) this;
        }

        public Criteria andYunAuthBalanceNotEqualTo(Double value) {
            addCriterion("yun_auth_balance <>", value, "yunAuthBalance");
            return (Criteria) this;
        }

        public Criteria andYunAuthBalanceGreaterThan(Double value) {
            addCriterion("yun_auth_balance >", value, "yunAuthBalance");
            return (Criteria) this;
        }

        public Criteria andYunAuthBalanceGreaterThanOrEqualTo(Double value) {
            addCriterion("yun_auth_balance >=", value, "yunAuthBalance");
            return (Criteria) this;
        }

        public Criteria andYunAuthBalanceLessThan(Double value) {
            addCriterion("yun_auth_balance <", value, "yunAuthBalance");
            return (Criteria) this;
        }

        public Criteria andYunAuthBalanceLessThanOrEqualTo(Double value) {
            addCriterion("yun_auth_balance <=", value, "yunAuthBalance");
            return (Criteria) this;
        }

        public Criteria andYunAuthBalanceIn(List<Double> values) {
            addCriterion("yun_auth_balance in", values, "yunAuthBalance");
            return (Criteria) this;
        }

        public Criteria andYunAuthBalanceNotIn(List<Double> values) {
            addCriterion("yun_auth_balance not in", values, "yunAuthBalance");
            return (Criteria) this;
        }

        public Criteria andYunAuthBalanceBetween(Double value1, Double value2) {
            addCriterion("yun_auth_balance between", value1, value2, "yunAuthBalance");
            return (Criteria) this;
        }

        public Criteria andYunAuthBalanceNotBetween(Double value1, Double value2) {
            addCriterion("yun_auth_balance not between", value1, value2, "yunAuthBalance");
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