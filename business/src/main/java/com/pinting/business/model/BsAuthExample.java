package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BsAuthExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsAuthExample() {
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

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileCodeIsNull() {
            addCriterion("mobile_code is null");
            return (Criteria) this;
        }

        public Criteria andMobileCodeIsNotNull() {
            addCriterion("mobile_code is not null");
            return (Criteria) this;
        }

        public Criteria andMobileCodeEqualTo(String value) {
            addCriterion("mobile_code =", value, "mobileCode");
            return (Criteria) this;
        }

        public Criteria andMobileCodeNotEqualTo(String value) {
            addCriterion("mobile_code <>", value, "mobileCode");
            return (Criteria) this;
        }

        public Criteria andMobileCodeGreaterThan(String value) {
            addCriterion("mobile_code >", value, "mobileCode");
            return (Criteria) this;
        }

        public Criteria andMobileCodeGreaterThanOrEqualTo(String value) {
            addCriterion("mobile_code >=", value, "mobileCode");
            return (Criteria) this;
        }

        public Criteria andMobileCodeLessThan(String value) {
            addCriterion("mobile_code <", value, "mobileCode");
            return (Criteria) this;
        }

        public Criteria andMobileCodeLessThanOrEqualTo(String value) {
            addCriterion("mobile_code <=", value, "mobileCode");
            return (Criteria) this;
        }

        public Criteria andMobileCodeLike(String value) {
            addCriterion("mobile_code like", value, "mobileCode");
            return (Criteria) this;
        }

        public Criteria andMobileCodeNotLike(String value) {
            addCriterion("mobile_code not like", value, "mobileCode");
            return (Criteria) this;
        }

        public Criteria andMobileCodeIn(List<String> values) {
            addCriterion("mobile_code in", values, "mobileCode");
            return (Criteria) this;
        }

        public Criteria andMobileCodeNotIn(List<String> values) {
            addCriterion("mobile_code not in", values, "mobileCode");
            return (Criteria) this;
        }

        public Criteria andMobileCodeBetween(String value1, String value2) {
            addCriterion("mobile_code between", value1, value2, "mobileCode");
            return (Criteria) this;
        }

        public Criteria andMobileCodeNotBetween(String value1, String value2) {
            addCriterion("mobile_code not between", value1, value2, "mobileCode");
            return (Criteria) this;
        }

        public Criteria andMobileTimeIsNull() {
            addCriterion("mobile_time is null");
            return (Criteria) this;
        }

        public Criteria andMobileTimeIsNotNull() {
            addCriterion("mobile_time is not null");
            return (Criteria) this;
        }

        public Criteria andMobileTimeEqualTo(Date value) {
            addCriterion("mobile_time =", value, "mobileTime");
            return (Criteria) this;
        }

        public Criteria andMobileTimeNotEqualTo(Date value) {
            addCriterion("mobile_time <>", value, "mobileTime");
            return (Criteria) this;
        }

        public Criteria andMobileTimeGreaterThan(Date value) {
            addCriterion("mobile_time >", value, "mobileTime");
            return (Criteria) this;
        }

        public Criteria andMobileTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("mobile_time >=", value, "mobileTime");
            return (Criteria) this;
        }

        public Criteria andMobileTimeLessThan(Date value) {
            addCriterion("mobile_time <", value, "mobileTime");
            return (Criteria) this;
        }

        public Criteria andMobileTimeLessThanOrEqualTo(Date value) {
            addCriterion("mobile_time <=", value, "mobileTime");
            return (Criteria) this;
        }

        public Criteria andMobileTimeIn(List<Date> values) {
            addCriterion("mobile_time in", values, "mobileTime");
            return (Criteria) this;
        }

        public Criteria andMobileTimeNotIn(List<Date> values) {
            addCriterion("mobile_time not in", values, "mobileTime");
            return (Criteria) this;
        }

        public Criteria andMobileTimeBetween(Date value1, Date value2) {
            addCriterion("mobile_time between", value1, value2, "mobileTime");
            return (Criteria) this;
        }

        public Criteria andMobileTimeNotBetween(Date value1, Date value2) {
            addCriterion("mobile_time not between", value1, value2, "mobileTime");
            return (Criteria) this;
        }

        public Criteria andMobileTimesIsNull() {
            addCriterion("mobile_times is null");
            return (Criteria) this;
        }

        public Criteria andMobileTimesIsNotNull() {
            addCriterion("mobile_times is not null");
            return (Criteria) this;
        }

        public Criteria andMobileTimesEqualTo(Integer value) {
            addCriterion("mobile_times =", value, "mobileTimes");
            return (Criteria) this;
        }

        public Criteria andMobileTimesNotEqualTo(Integer value) {
            addCriterion("mobile_times <>", value, "mobileTimes");
            return (Criteria) this;
        }

        public Criteria andMobileTimesGreaterThan(Integer value) {
            addCriterion("mobile_times >", value, "mobileTimes");
            return (Criteria) this;
        }

        public Criteria andMobileTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("mobile_times >=", value, "mobileTimes");
            return (Criteria) this;
        }

        public Criteria andMobileTimesLessThan(Integer value) {
            addCriterion("mobile_times <", value, "mobileTimes");
            return (Criteria) this;
        }

        public Criteria andMobileTimesLessThanOrEqualTo(Integer value) {
            addCriterion("mobile_times <=", value, "mobileTimes");
            return (Criteria) this;
        }

        public Criteria andMobileTimesIn(List<Integer> values) {
            addCriterion("mobile_times in", values, "mobileTimes");
            return (Criteria) this;
        }

        public Criteria andMobileTimesNotIn(List<Integer> values) {
            addCriterion("mobile_times not in", values, "mobileTimes");
            return (Criteria) this;
        }

        public Criteria andMobileTimesBetween(Integer value1, Integer value2) {
            addCriterion("mobile_times between", value1, value2, "mobileTimes");
            return (Criteria) this;
        }

        public Criteria andMobileTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("mobile_times not between", value1, value2, "mobileTimes");
            return (Criteria) this;
        }

        public Criteria andMobileCodeUseTimesIsNull() {
            addCriterion("mobile_code_use_times is null");
            return (Criteria) this;
        }

        public Criteria andMobileCodeUseTimesIsNotNull() {
            addCriterion("mobile_code_use_times is not null");
            return (Criteria) this;
        }

        public Criteria andMobileCodeUseTimesEqualTo(Integer value) {
            addCriterion("mobile_code_use_times =", value, "mobileCodeUseTimes");
            return (Criteria) this;
        }

        public Criteria andMobileCodeUseTimesNotEqualTo(Integer value) {
            addCriterion("mobile_code_use_times <>", value, "mobileCodeUseTimes");
            return (Criteria) this;
        }

        public Criteria andMobileCodeUseTimesGreaterThan(Integer value) {
            addCriterion("mobile_code_use_times >", value, "mobileCodeUseTimes");
            return (Criteria) this;
        }

        public Criteria andMobileCodeUseTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("mobile_code_use_times >=", value, "mobileCodeUseTimes");
            return (Criteria) this;
        }

        public Criteria andMobileCodeUseTimesLessThan(Integer value) {
            addCriterion("mobile_code_use_times <", value, "mobileCodeUseTimes");
            return (Criteria) this;
        }

        public Criteria andMobileCodeUseTimesLessThanOrEqualTo(Integer value) {
            addCriterion("mobile_code_use_times <=", value, "mobileCodeUseTimes");
            return (Criteria) this;
        }

        public Criteria andMobileCodeUseTimesIn(List<Integer> values) {
            addCriterion("mobile_code_use_times in", values, "mobileCodeUseTimes");
            return (Criteria) this;
        }

        public Criteria andMobileCodeUseTimesNotIn(List<Integer> values) {
            addCriterion("mobile_code_use_times not in", values, "mobileCodeUseTimes");
            return (Criteria) this;
        }

        public Criteria andMobileCodeUseTimesBetween(Integer value1, Integer value2) {
            addCriterion("mobile_code_use_times between", value1, value2, "mobileCodeUseTimes");
            return (Criteria) this;
        }

        public Criteria andMobileCodeUseTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("mobile_code_use_times not between", value1, value2, "mobileCodeUseTimes");
            return (Criteria) this;
        }

        public Criteria andMobileLastTimeIsNull() {
            addCriterion("mobile_last_time is null");
            return (Criteria) this;
        }

        public Criteria andMobileLastTimeIsNotNull() {
            addCriterion("mobile_last_time is not null");
            return (Criteria) this;
        }

        public Criteria andMobileLastTimeEqualTo(Date value) {
            addCriterion("mobile_last_time =", value, "mobileLastTime");
            return (Criteria) this;
        }

        public Criteria andMobileLastTimeNotEqualTo(Date value) {
            addCriterion("mobile_last_time <>", value, "mobileLastTime");
            return (Criteria) this;
        }

        public Criteria andMobileLastTimeGreaterThan(Date value) {
            addCriterion("mobile_last_time >", value, "mobileLastTime");
            return (Criteria) this;
        }

        public Criteria andMobileLastTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("mobile_last_time >=", value, "mobileLastTime");
            return (Criteria) this;
        }

        public Criteria andMobileLastTimeLessThan(Date value) {
            addCriterion("mobile_last_time <", value, "mobileLastTime");
            return (Criteria) this;
        }

        public Criteria andMobileLastTimeLessThanOrEqualTo(Date value) {
            addCriterion("mobile_last_time <=", value, "mobileLastTime");
            return (Criteria) this;
        }

        public Criteria andMobileLastTimeIn(List<Date> values) {
            addCriterion("mobile_last_time in", values, "mobileLastTime");
            return (Criteria) this;
        }

        public Criteria andMobileLastTimeNotIn(List<Date> values) {
            addCriterion("mobile_last_time not in", values, "mobileLastTime");
            return (Criteria) this;
        }

        public Criteria andMobileLastTimeBetween(Date value1, Date value2) {
            addCriterion("mobile_last_time between", value1, value2, "mobileLastTime");
            return (Criteria) this;
        }

        public Criteria andMobileLastTimeNotBetween(Date value1, Date value2) {
            addCriterion("mobile_last_time not between", value1, value2, "mobileLastTime");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailCodeIsNull() {
            addCriterion("email_code is null");
            return (Criteria) this;
        }

        public Criteria andEmailCodeIsNotNull() {
            addCriterion("email_code is not null");
            return (Criteria) this;
        }

        public Criteria andEmailCodeEqualTo(String value) {
            addCriterion("email_code =", value, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeNotEqualTo(String value) {
            addCriterion("email_code <>", value, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeGreaterThan(String value) {
            addCriterion("email_code >", value, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeGreaterThanOrEqualTo(String value) {
            addCriterion("email_code >=", value, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeLessThan(String value) {
            addCriterion("email_code <", value, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeLessThanOrEqualTo(String value) {
            addCriterion("email_code <=", value, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeLike(String value) {
            addCriterion("email_code like", value, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeNotLike(String value) {
            addCriterion("email_code not like", value, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeIn(List<String> values) {
            addCriterion("email_code in", values, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeNotIn(List<String> values) {
            addCriterion("email_code not in", values, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeBetween(String value1, String value2) {
            addCriterion("email_code between", value1, value2, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeNotBetween(String value1, String value2) {
            addCriterion("email_code not between", value1, value2, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailTimeIsNull() {
            addCriterion("email_time is null");
            return (Criteria) this;
        }

        public Criteria andEmailTimeIsNotNull() {
            addCriterion("email_time is not null");
            return (Criteria) this;
        }

        public Criteria andEmailTimeEqualTo(Date value) {
            addCriterion("email_time =", value, "emailTime");
            return (Criteria) this;
        }

        public Criteria andEmailTimeNotEqualTo(Date value) {
            addCriterion("email_time <>", value, "emailTime");
            return (Criteria) this;
        }

        public Criteria andEmailTimeGreaterThan(Date value) {
            addCriterion("email_time >", value, "emailTime");
            return (Criteria) this;
        }

        public Criteria andEmailTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("email_time >=", value, "emailTime");
            return (Criteria) this;
        }

        public Criteria andEmailTimeLessThan(Date value) {
            addCriterion("email_time <", value, "emailTime");
            return (Criteria) this;
        }

        public Criteria andEmailTimeLessThanOrEqualTo(Date value) {
            addCriterion("email_time <=", value, "emailTime");
            return (Criteria) this;
        }

        public Criteria andEmailTimeIn(List<Date> values) {
            addCriterion("email_time in", values, "emailTime");
            return (Criteria) this;
        }

        public Criteria andEmailTimeNotIn(List<Date> values) {
            addCriterion("email_time not in", values, "emailTime");
            return (Criteria) this;
        }

        public Criteria andEmailTimeBetween(Date value1, Date value2) {
            addCriterion("email_time between", value1, value2, "emailTime");
            return (Criteria) this;
        }

        public Criteria andEmailTimeNotBetween(Date value1, Date value2) {
            addCriterion("email_time not between", value1, value2, "emailTime");
            return (Criteria) this;
        }

        public Criteria andEmailCodeUseTimesIsNull() {
            addCriterion("email_code_use_times is null");
            return (Criteria) this;
        }

        public Criteria andEmailCodeUseTimesIsNotNull() {
            addCriterion("email_code_use_times is not null");
            return (Criteria) this;
        }

        public Criteria andEmailCodeUseTimesEqualTo(Integer value) {
            addCriterion("email_code_use_times =", value, "emailCodeUseTimes");
            return (Criteria) this;
        }

        public Criteria andEmailCodeUseTimesNotEqualTo(Integer value) {
            addCriterion("email_code_use_times <>", value, "emailCodeUseTimes");
            return (Criteria) this;
        }

        public Criteria andEmailCodeUseTimesGreaterThan(Integer value) {
            addCriterion("email_code_use_times >", value, "emailCodeUseTimes");
            return (Criteria) this;
        }

        public Criteria andEmailCodeUseTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("email_code_use_times >=", value, "emailCodeUseTimes");
            return (Criteria) this;
        }

        public Criteria andEmailCodeUseTimesLessThan(Integer value) {
            addCriterion("email_code_use_times <", value, "emailCodeUseTimes");
            return (Criteria) this;
        }

        public Criteria andEmailCodeUseTimesLessThanOrEqualTo(Integer value) {
            addCriterion("email_code_use_times <=", value, "emailCodeUseTimes");
            return (Criteria) this;
        }

        public Criteria andEmailCodeUseTimesIn(List<Integer> values) {
            addCriterion("email_code_use_times in", values, "emailCodeUseTimes");
            return (Criteria) this;
        }

        public Criteria andEmailCodeUseTimesNotIn(List<Integer> values) {
            addCriterion("email_code_use_times not in", values, "emailCodeUseTimes");
            return (Criteria) this;
        }

        public Criteria andEmailCodeUseTimesBetween(Integer value1, Integer value2) {
            addCriterion("email_code_use_times between", value1, value2, "emailCodeUseTimes");
            return (Criteria) this;
        }

        public Criteria andEmailCodeUseTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("email_code_use_times not between", value1, value2, "emailCodeUseTimes");
            return (Criteria) this;
        }

        public Criteria andEmailLastTimeIsNull() {
            addCriterion("email_last_time is null");
            return (Criteria) this;
        }

        public Criteria andEmailLastTimeIsNotNull() {
            addCriterion("email_last_time is not null");
            return (Criteria) this;
        }

        public Criteria andEmailLastTimeEqualTo(Date value) {
            addCriterionForJDBCDate("email_last_time =", value, "emailLastTime");
            return (Criteria) this;
        }

        public Criteria andEmailLastTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("email_last_time <>", value, "emailLastTime");
            return (Criteria) this;
        }

        public Criteria andEmailLastTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("email_last_time >", value, "emailLastTime");
            return (Criteria) this;
        }

        public Criteria andEmailLastTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("email_last_time >=", value, "emailLastTime");
            return (Criteria) this;
        }

        public Criteria andEmailLastTimeLessThan(Date value) {
            addCriterionForJDBCDate("email_last_time <", value, "emailLastTime");
            return (Criteria) this;
        }

        public Criteria andEmailLastTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("email_last_time <=", value, "emailLastTime");
            return (Criteria) this;
        }

        public Criteria andEmailLastTimeIn(List<Date> values) {
            addCriterionForJDBCDate("email_last_time in", values, "emailLastTime");
            return (Criteria) this;
        }

        public Criteria andEmailLastTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("email_last_time not in", values, "emailLastTime");
            return (Criteria) this;
        }

        public Criteria andEmailLastTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("email_last_time between", value1, value2, "emailLastTime");
            return (Criteria) this;
        }

        public Criteria andEmailLastTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("email_last_time not between", value1, value2, "emailLastTime");
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