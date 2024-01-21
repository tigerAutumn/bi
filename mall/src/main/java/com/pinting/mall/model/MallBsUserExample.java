package com.pinting.mall.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MallBsUserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MallBsUserExample() {
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

        public Criteria andNickIsNull() {
            addCriterion("nick is null");
            return (Criteria) this;
        }

        public Criteria andNickIsNotNull() {
            addCriterion("nick is not null");
            return (Criteria) this;
        }

        public Criteria andNickEqualTo(String value) {
            addCriterion("nick =", value, "nick");
            return (Criteria) this;
        }

        public Criteria andNickNotEqualTo(String value) {
            addCriterion("nick <>", value, "nick");
            return (Criteria) this;
        }

        public Criteria andNickGreaterThan(String value) {
            addCriterion("nick >", value, "nick");
            return (Criteria) this;
        }

        public Criteria andNickGreaterThanOrEqualTo(String value) {
            addCriterion("nick >=", value, "nick");
            return (Criteria) this;
        }

        public Criteria andNickLessThan(String value) {
            addCriterion("nick <", value, "nick");
            return (Criteria) this;
        }

        public Criteria andNickLessThanOrEqualTo(String value) {
            addCriterion("nick <=", value, "nick");
            return (Criteria) this;
        }

        public Criteria andNickLike(String value) {
            addCriterion("nick like", value, "nick");
            return (Criteria) this;
        }

        public Criteria andNickNotLike(String value) {
            addCriterion("nick not like", value, "nick");
            return (Criteria) this;
        }

        public Criteria andNickIn(List<String> values) {
            addCriterion("nick in", values, "nick");
            return (Criteria) this;
        }

        public Criteria andNickNotIn(List<String> values) {
            addCriterion("nick not in", values, "nick");
            return (Criteria) this;
        }

        public Criteria andNickBetween(String value1, String value2) {
            addCriterion("nick between", value1, value2, "nick");
            return (Criteria) this;
        }

        public Criteria andNickNotBetween(String value1, String value2) {
            addCriterion("nick not between", value1, value2, "nick");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
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

        public Criteria andUrgentNameIsNull() {
            addCriterion("urgent_name is null");
            return (Criteria) this;
        }

        public Criteria andUrgentNameIsNotNull() {
            addCriterion("urgent_name is not null");
            return (Criteria) this;
        }

        public Criteria andUrgentNameEqualTo(String value) {
            addCriterion("urgent_name =", value, "urgentName");
            return (Criteria) this;
        }

        public Criteria andUrgentNameNotEqualTo(String value) {
            addCriterion("urgent_name <>", value, "urgentName");
            return (Criteria) this;
        }

        public Criteria andUrgentNameGreaterThan(String value) {
            addCriterion("urgent_name >", value, "urgentName");
            return (Criteria) this;
        }

        public Criteria andUrgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("urgent_name >=", value, "urgentName");
            return (Criteria) this;
        }

        public Criteria andUrgentNameLessThan(String value) {
            addCriterion("urgent_name <", value, "urgentName");
            return (Criteria) this;
        }

        public Criteria andUrgentNameLessThanOrEqualTo(String value) {
            addCriterion("urgent_name <=", value, "urgentName");
            return (Criteria) this;
        }

        public Criteria andUrgentNameLike(String value) {
            addCriterion("urgent_name like", value, "urgentName");
            return (Criteria) this;
        }

        public Criteria andUrgentNameNotLike(String value) {
            addCriterion("urgent_name not like", value, "urgentName");
            return (Criteria) this;
        }

        public Criteria andUrgentNameIn(List<String> values) {
            addCriterion("urgent_name in", values, "urgentName");
            return (Criteria) this;
        }

        public Criteria andUrgentNameNotIn(List<String> values) {
            addCriterion("urgent_name not in", values, "urgentName");
            return (Criteria) this;
        }

        public Criteria andUrgentNameBetween(String value1, String value2) {
            addCriterion("urgent_name between", value1, value2, "urgentName");
            return (Criteria) this;
        }

        public Criteria andUrgentNameNotBetween(String value1, String value2) {
            addCriterion("urgent_name not between", value1, value2, "urgentName");
            return (Criteria) this;
        }

        public Criteria andUrgentMobileIsNull() {
            addCriterion("urgent_mobile is null");
            return (Criteria) this;
        }

        public Criteria andUrgentMobileIsNotNull() {
            addCriterion("urgent_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andUrgentMobileEqualTo(String value) {
            addCriterion("urgent_mobile =", value, "urgentMobile");
            return (Criteria) this;
        }

        public Criteria andUrgentMobileNotEqualTo(String value) {
            addCriterion("urgent_mobile <>", value, "urgentMobile");
            return (Criteria) this;
        }

        public Criteria andUrgentMobileGreaterThan(String value) {
            addCriterion("urgent_mobile >", value, "urgentMobile");
            return (Criteria) this;
        }

        public Criteria andUrgentMobileGreaterThanOrEqualTo(String value) {
            addCriterion("urgent_mobile >=", value, "urgentMobile");
            return (Criteria) this;
        }

        public Criteria andUrgentMobileLessThan(String value) {
            addCriterion("urgent_mobile <", value, "urgentMobile");
            return (Criteria) this;
        }

        public Criteria andUrgentMobileLessThanOrEqualTo(String value) {
            addCriterion("urgent_mobile <=", value, "urgentMobile");
            return (Criteria) this;
        }

        public Criteria andUrgentMobileLike(String value) {
            addCriterion("urgent_mobile like", value, "urgentMobile");
            return (Criteria) this;
        }

        public Criteria andUrgentMobileNotLike(String value) {
            addCriterion("urgent_mobile not like", value, "urgentMobile");
            return (Criteria) this;
        }

        public Criteria andUrgentMobileIn(List<String> values) {
            addCriterion("urgent_mobile in", values, "urgentMobile");
            return (Criteria) this;
        }

        public Criteria andUrgentMobileNotIn(List<String> values) {
            addCriterion("urgent_mobile not in", values, "urgentMobile");
            return (Criteria) this;
        }

        public Criteria andUrgentMobileBetween(String value1, String value2) {
            addCriterion("urgent_mobile between", value1, value2, "urgentMobile");
            return (Criteria) this;
        }

        public Criteria andUrgentMobileNotBetween(String value1, String value2) {
            addCriterion("urgent_mobile not between", value1, value2, "urgentMobile");
            return (Criteria) this;
        }

        public Criteria andRelationIsNull() {
            addCriterion("relation is null");
            return (Criteria) this;
        }

        public Criteria andRelationIsNotNull() {
            addCriterion("relation is not null");
            return (Criteria) this;
        }

        public Criteria andRelationEqualTo(Integer value) {
            addCriterion("relation =", value, "relation");
            return (Criteria) this;
        }

        public Criteria andRelationNotEqualTo(Integer value) {
            addCriterion("relation <>", value, "relation");
            return (Criteria) this;
        }

        public Criteria andRelationGreaterThan(Integer value) {
            addCriterion("relation >", value, "relation");
            return (Criteria) this;
        }

        public Criteria andRelationGreaterThanOrEqualTo(Integer value) {
            addCriterion("relation >=", value, "relation");
            return (Criteria) this;
        }

        public Criteria andRelationLessThan(Integer value) {
            addCriterion("relation <", value, "relation");
            return (Criteria) this;
        }

        public Criteria andRelationLessThanOrEqualTo(Integer value) {
            addCriterion("relation <=", value, "relation");
            return (Criteria) this;
        }

        public Criteria andRelationIn(List<Integer> values) {
            addCriterion("relation in", values, "relation");
            return (Criteria) this;
        }

        public Criteria andRelationNotIn(List<Integer> values) {
            addCriterion("relation not in", values, "relation");
            return (Criteria) this;
        }

        public Criteria andRelationBetween(Integer value1, Integer value2) {
            addCriterion("relation between", value1, value2, "relation");
            return (Criteria) this;
        }

        public Criteria andRelationNotBetween(Integer value1, Integer value2) {
            addCriterion("relation not between", value1, value2, "relation");
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

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("password not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPayPasswordIsNull() {
            addCriterion("pay_password is null");
            return (Criteria) this;
        }

        public Criteria andPayPasswordIsNotNull() {
            addCriterion("pay_password is not null");
            return (Criteria) this;
        }

        public Criteria andPayPasswordEqualTo(String value) {
            addCriterion("pay_password =", value, "payPassword");
            return (Criteria) this;
        }

        public Criteria andPayPasswordNotEqualTo(String value) {
            addCriterion("pay_password <>", value, "payPassword");
            return (Criteria) this;
        }

        public Criteria andPayPasswordGreaterThan(String value) {
            addCriterion("pay_password >", value, "payPassword");
            return (Criteria) this;
        }

        public Criteria andPayPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("pay_password >=", value, "payPassword");
            return (Criteria) this;
        }

        public Criteria andPayPasswordLessThan(String value) {
            addCriterion("pay_password <", value, "payPassword");
            return (Criteria) this;
        }

        public Criteria andPayPasswordLessThanOrEqualTo(String value) {
            addCriterion("pay_password <=", value, "payPassword");
            return (Criteria) this;
        }

        public Criteria andPayPasswordLike(String value) {
            addCriterion("pay_password like", value, "payPassword");
            return (Criteria) this;
        }

        public Criteria andPayPasswordNotLike(String value) {
            addCriterion("pay_password not like", value, "payPassword");
            return (Criteria) this;
        }

        public Criteria andPayPasswordIn(List<String> values) {
            addCriterion("pay_password in", values, "payPassword");
            return (Criteria) this;
        }

        public Criteria andPayPasswordNotIn(List<String> values) {
            addCriterion("pay_password not in", values, "payPassword");
            return (Criteria) this;
        }

        public Criteria andPayPasswordBetween(String value1, String value2) {
            addCriterion("pay_password between", value1, value2, "payPassword");
            return (Criteria) this;
        }

        public Criteria andPayPasswordNotBetween(String value1, String value2) {
            addCriterion("pay_password not between", value1, value2, "payPassword");
            return (Criteria) this;
        }

        public Criteria andLoginTimeIsNull() {
            addCriterion("login_time is null");
            return (Criteria) this;
        }

        public Criteria andLoginTimeIsNotNull() {
            addCriterion("login_time is not null");
            return (Criteria) this;
        }

        public Criteria andLoginTimeEqualTo(Date value) {
            addCriterion("login_time =", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeNotEqualTo(Date value) {
            addCriterion("login_time <>", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeGreaterThan(Date value) {
            addCriterion("login_time >", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("login_time >=", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeLessThan(Date value) {
            addCriterion("login_time <", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeLessThanOrEqualTo(Date value) {
            addCriterion("login_time <=", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeIn(List<Date> values) {
            addCriterion("login_time in", values, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeNotIn(List<Date> values) {
            addCriterion("login_time not in", values, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeBetween(Date value1, Date value2) {
            addCriterion("login_time between", value1, value2, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeNotBetween(Date value1, Date value2) {
            addCriterion("login_time not between", value1, value2, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLogoutTimeIsNull() {
            addCriterion("logout_time is null");
            return (Criteria) this;
        }

        public Criteria andLogoutTimeIsNotNull() {
            addCriterion("logout_time is not null");
            return (Criteria) this;
        }

        public Criteria andLogoutTimeEqualTo(Date value) {
            addCriterion("logout_time =", value, "logoutTime");
            return (Criteria) this;
        }

        public Criteria andLogoutTimeNotEqualTo(Date value) {
            addCriterion("logout_time <>", value, "logoutTime");
            return (Criteria) this;
        }

        public Criteria andLogoutTimeGreaterThan(Date value) {
            addCriterion("logout_time >", value, "logoutTime");
            return (Criteria) this;
        }

        public Criteria andLogoutTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("logout_time >=", value, "logoutTime");
            return (Criteria) this;
        }

        public Criteria andLogoutTimeLessThan(Date value) {
            addCriterion("logout_time <", value, "logoutTime");
            return (Criteria) this;
        }

        public Criteria andLogoutTimeLessThanOrEqualTo(Date value) {
            addCriterion("logout_time <=", value, "logoutTime");
            return (Criteria) this;
        }

        public Criteria andLogoutTimeIn(List<Date> values) {
            addCriterion("logout_time in", values, "logoutTime");
            return (Criteria) this;
        }

        public Criteria andLogoutTimeNotIn(List<Date> values) {
            addCriterion("logout_time not in", values, "logoutTime");
            return (Criteria) this;
        }

        public Criteria andLogoutTimeBetween(Date value1, Date value2) {
            addCriterion("logout_time between", value1, value2, "logoutTime");
            return (Criteria) this;
        }

        public Criteria andLogoutTimeNotBetween(Date value1, Date value2) {
            addCriterion("logout_time not between", value1, value2, "logoutTime");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimesIsNull() {
            addCriterion("login_fail_times is null");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimesIsNotNull() {
            addCriterion("login_fail_times is not null");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimesEqualTo(Integer value) {
            addCriterion("login_fail_times =", value, "loginFailTimes");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimesNotEqualTo(Integer value) {
            addCriterion("login_fail_times <>", value, "loginFailTimes");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimesGreaterThan(Integer value) {
            addCriterion("login_fail_times >", value, "loginFailTimes");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("login_fail_times >=", value, "loginFailTimes");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimesLessThan(Integer value) {
            addCriterion("login_fail_times <", value, "loginFailTimes");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimesLessThanOrEqualTo(Integer value) {
            addCriterion("login_fail_times <=", value, "loginFailTimes");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimesIn(List<Integer> values) {
            addCriterion("login_fail_times in", values, "loginFailTimes");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimesNotIn(List<Integer> values) {
            addCriterion("login_fail_times not in", values, "loginFailTimes");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimesBetween(Integer value1, Integer value2) {
            addCriterion("login_fail_times between", value1, value2, "loginFailTimes");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("login_fail_times not between", value1, value2, "loginFailTimes");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimeIsNull() {
            addCriterion("login_fail_time is null");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimeIsNotNull() {
            addCriterion("login_fail_time is not null");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimeEqualTo(Date value) {
            addCriterion("login_fail_time =", value, "loginFailTime");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimeNotEqualTo(Date value) {
            addCriterion("login_fail_time <>", value, "loginFailTime");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimeGreaterThan(Date value) {
            addCriterion("login_fail_time >", value, "loginFailTime");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("login_fail_time >=", value, "loginFailTime");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimeLessThan(Date value) {
            addCriterion("login_fail_time <", value, "loginFailTime");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimeLessThanOrEqualTo(Date value) {
            addCriterion("login_fail_time <=", value, "loginFailTime");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimeIn(List<Date> values) {
            addCriterion("login_fail_time in", values, "loginFailTime");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimeNotIn(List<Date> values) {
            addCriterion("login_fail_time not in", values, "loginFailTime");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimeBetween(Date value1, Date value2) {
            addCriterion("login_fail_time between", value1, value2, "loginFailTime");
            return (Criteria) this;
        }

        public Criteria andLoginFailTimeNotBetween(Date value1, Date value2) {
            addCriterion("login_fail_time not between", value1, value2, "loginFailTime");
            return (Criteria) this;
        }

        public Criteria andLoginAlwaysFailTimesIsNull() {
            addCriterion("login_always_fail_times is null");
            return (Criteria) this;
        }

        public Criteria andLoginAlwaysFailTimesIsNotNull() {
            addCriterion("login_always_fail_times is not null");
            return (Criteria) this;
        }

        public Criteria andLoginAlwaysFailTimesEqualTo(Integer value) {
            addCriterion("login_always_fail_times =", value, "loginAlwaysFailTimes");
            return (Criteria) this;
        }

        public Criteria andLoginAlwaysFailTimesNotEqualTo(Integer value) {
            addCriterion("login_always_fail_times <>", value, "loginAlwaysFailTimes");
            return (Criteria) this;
        }

        public Criteria andLoginAlwaysFailTimesGreaterThan(Integer value) {
            addCriterion("login_always_fail_times >", value, "loginAlwaysFailTimes");
            return (Criteria) this;
        }

        public Criteria andLoginAlwaysFailTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("login_always_fail_times >=", value, "loginAlwaysFailTimes");
            return (Criteria) this;
        }

        public Criteria andLoginAlwaysFailTimesLessThan(Integer value) {
            addCriterion("login_always_fail_times <", value, "loginAlwaysFailTimes");
            return (Criteria) this;
        }

        public Criteria andLoginAlwaysFailTimesLessThanOrEqualTo(Integer value) {
            addCriterion("login_always_fail_times <=", value, "loginAlwaysFailTimes");
            return (Criteria) this;
        }

        public Criteria andLoginAlwaysFailTimesIn(List<Integer> values) {
            addCriterion("login_always_fail_times in", values, "loginAlwaysFailTimes");
            return (Criteria) this;
        }

        public Criteria andLoginAlwaysFailTimesNotIn(List<Integer> values) {
            addCriterion("login_always_fail_times not in", values, "loginAlwaysFailTimes");
            return (Criteria) this;
        }

        public Criteria andLoginAlwaysFailTimesBetween(Integer value1, Integer value2) {
            addCriterion("login_always_fail_times between", value1, value2, "loginAlwaysFailTimes");
            return (Criteria) this;
        }

        public Criteria andLoginAlwaysFailTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("login_always_fail_times not between", value1, value2, "loginAlwaysFailTimes");
            return (Criteria) this;
        }

        public Criteria andLoginLockTimeIsNull() {
            addCriterion("login_lock_time is null");
            return (Criteria) this;
        }

        public Criteria andLoginLockTimeIsNotNull() {
            addCriterion("login_lock_time is not null");
            return (Criteria) this;
        }

        public Criteria andLoginLockTimeEqualTo(Date value) {
            addCriterion("login_lock_time =", value, "loginLockTime");
            return (Criteria) this;
        }

        public Criteria andLoginLockTimeNotEqualTo(Date value) {
            addCriterion("login_lock_time <>", value, "loginLockTime");
            return (Criteria) this;
        }

        public Criteria andLoginLockTimeGreaterThan(Date value) {
            addCriterion("login_lock_time >", value, "loginLockTime");
            return (Criteria) this;
        }

        public Criteria andLoginLockTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("login_lock_time >=", value, "loginLockTime");
            return (Criteria) this;
        }

        public Criteria andLoginLockTimeLessThan(Date value) {
            addCriterion("login_lock_time <", value, "loginLockTime");
            return (Criteria) this;
        }

        public Criteria andLoginLockTimeLessThanOrEqualTo(Date value) {
            addCriterion("login_lock_time <=", value, "loginLockTime");
            return (Criteria) this;
        }

        public Criteria andLoginLockTimeIn(List<Date> values) {
            addCriterion("login_lock_time in", values, "loginLockTime");
            return (Criteria) this;
        }

        public Criteria andLoginLockTimeNotIn(List<Date> values) {
            addCriterion("login_lock_time not in", values, "loginLockTime");
            return (Criteria) this;
        }

        public Criteria andLoginLockTimeBetween(Date value1, Date value2) {
            addCriterion("login_lock_time between", value1, value2, "loginLockTime");
            return (Criteria) this;
        }

        public Criteria andLoginLockTimeNotBetween(Date value1, Date value2) {
            addCriterion("login_lock_time not between", value1, value2, "loginLockTime");
            return (Criteria) this;
        }

        public Criteria andPayFailTimesIsNull() {
            addCriterion("pay_fail_times is null");
            return (Criteria) this;
        }

        public Criteria andPayFailTimesIsNotNull() {
            addCriterion("pay_fail_times is not null");
            return (Criteria) this;
        }

        public Criteria andPayFailTimesEqualTo(Integer value) {
            addCriterion("pay_fail_times =", value, "payFailTimes");
            return (Criteria) this;
        }

        public Criteria andPayFailTimesNotEqualTo(Integer value) {
            addCriterion("pay_fail_times <>", value, "payFailTimes");
            return (Criteria) this;
        }

        public Criteria andPayFailTimesGreaterThan(Integer value) {
            addCriterion("pay_fail_times >", value, "payFailTimes");
            return (Criteria) this;
        }

        public Criteria andPayFailTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_fail_times >=", value, "payFailTimes");
            return (Criteria) this;
        }

        public Criteria andPayFailTimesLessThan(Integer value) {
            addCriterion("pay_fail_times <", value, "payFailTimes");
            return (Criteria) this;
        }

        public Criteria andPayFailTimesLessThanOrEqualTo(Integer value) {
            addCriterion("pay_fail_times <=", value, "payFailTimes");
            return (Criteria) this;
        }

        public Criteria andPayFailTimesIn(List<Integer> values) {
            addCriterion("pay_fail_times in", values, "payFailTimes");
            return (Criteria) this;
        }

        public Criteria andPayFailTimesNotIn(List<Integer> values) {
            addCriterion("pay_fail_times not in", values, "payFailTimes");
            return (Criteria) this;
        }

        public Criteria andPayFailTimesBetween(Integer value1, Integer value2) {
            addCriterion("pay_fail_times between", value1, value2, "payFailTimes");
            return (Criteria) this;
        }

        public Criteria andPayFailTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_fail_times not between", value1, value2, "payFailTimes");
            return (Criteria) this;
        }

        public Criteria andPayFailTimeIsNull() {
            addCriterion("pay_fail_time is null");
            return (Criteria) this;
        }

        public Criteria andPayFailTimeIsNotNull() {
            addCriterion("pay_fail_time is not null");
            return (Criteria) this;
        }

        public Criteria andPayFailTimeEqualTo(Date value) {
            addCriterion("pay_fail_time =", value, "payFailTime");
            return (Criteria) this;
        }

        public Criteria andPayFailTimeNotEqualTo(Date value) {
            addCriterion("pay_fail_time <>", value, "payFailTime");
            return (Criteria) this;
        }

        public Criteria andPayFailTimeGreaterThan(Date value) {
            addCriterion("pay_fail_time >", value, "payFailTime");
            return (Criteria) this;
        }

        public Criteria andPayFailTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("pay_fail_time >=", value, "payFailTime");
            return (Criteria) this;
        }

        public Criteria andPayFailTimeLessThan(Date value) {
            addCriterion("pay_fail_time <", value, "payFailTime");
            return (Criteria) this;
        }

        public Criteria andPayFailTimeLessThanOrEqualTo(Date value) {
            addCriterion("pay_fail_time <=", value, "payFailTime");
            return (Criteria) this;
        }

        public Criteria andPayFailTimeIn(List<Date> values) {
            addCriterion("pay_fail_time in", values, "payFailTime");
            return (Criteria) this;
        }

        public Criteria andPayFailTimeNotIn(List<Date> values) {
            addCriterion("pay_fail_time not in", values, "payFailTime");
            return (Criteria) this;
        }

        public Criteria andPayFailTimeBetween(Date value1, Date value2) {
            addCriterion("pay_fail_time between", value1, value2, "payFailTime");
            return (Criteria) this;
        }

        public Criteria andPayFailTimeNotBetween(Date value1, Date value2) {
            addCriterion("pay_fail_time not between", value1, value2, "payFailTime");
            return (Criteria) this;
        }

        public Criteria andIdCardIsNull() {
            addCriterion("id_card is null");
            return (Criteria) this;
        }

        public Criteria andIdCardIsNotNull() {
            addCriterion("id_card is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardEqualTo(String value) {
            addCriterion("id_card =", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotEqualTo(String value) {
            addCriterion("id_card <>", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardGreaterThan(String value) {
            addCriterion("id_card >", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardGreaterThanOrEqualTo(String value) {
            addCriterion("id_card >=", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardLessThan(String value) {
            addCriterion("id_card <", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardLessThanOrEqualTo(String value) {
            addCriterion("id_card <=", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardLike(String value) {
            addCriterion("id_card like", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotLike(String value) {
            addCriterion("id_card not like", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardIn(List<String> values) {
            addCriterion("id_card in", values, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotIn(List<String> values) {
            addCriterion("id_card not in", values, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardBetween(String value1, String value2) {
            addCriterion("id_card between", value1, value2, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotBetween(String value1, String value2) {
            addCriterion("id_card not between", value1, value2, "idCard");
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

        public Criteria andIsBindNameIsNull() {
            addCriterion("is_bind_name is null");
            return (Criteria) this;
        }

        public Criteria andIsBindNameIsNotNull() {
            addCriterion("is_bind_name is not null");
            return (Criteria) this;
        }

        public Criteria andIsBindNameEqualTo(Integer value) {
            addCriterion("is_bind_name =", value, "isBindName");
            return (Criteria) this;
        }

        public Criteria andIsBindNameNotEqualTo(Integer value) {
            addCriterion("is_bind_name <>", value, "isBindName");
            return (Criteria) this;
        }

        public Criteria andIsBindNameGreaterThan(Integer value) {
            addCriterion("is_bind_name >", value, "isBindName");
            return (Criteria) this;
        }

        public Criteria andIsBindNameGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_bind_name >=", value, "isBindName");
            return (Criteria) this;
        }

        public Criteria andIsBindNameLessThan(Integer value) {
            addCriterion("is_bind_name <", value, "isBindName");
            return (Criteria) this;
        }

        public Criteria andIsBindNameLessThanOrEqualTo(Integer value) {
            addCriterion("is_bind_name <=", value, "isBindName");
            return (Criteria) this;
        }

        public Criteria andIsBindNameIn(List<Integer> values) {
            addCriterion("is_bind_name in", values, "isBindName");
            return (Criteria) this;
        }

        public Criteria andIsBindNameNotIn(List<Integer> values) {
            addCriterion("is_bind_name not in", values, "isBindName");
            return (Criteria) this;
        }

        public Criteria andIsBindNameBetween(Integer value1, Integer value2) {
            addCriterion("is_bind_name between", value1, value2, "isBindName");
            return (Criteria) this;
        }

        public Criteria andIsBindNameNotBetween(Integer value1, Integer value2) {
            addCriterion("is_bind_name not between", value1, value2, "isBindName");
            return (Criteria) this;
        }

        public Criteria andIsBindBankIsNull() {
            addCriterion("is_bind_bank is null");
            return (Criteria) this;
        }

        public Criteria andIsBindBankIsNotNull() {
            addCriterion("is_bind_bank is not null");
            return (Criteria) this;
        }

        public Criteria andIsBindBankEqualTo(Integer value) {
            addCriterion("is_bind_bank =", value, "isBindBank");
            return (Criteria) this;
        }

        public Criteria andIsBindBankNotEqualTo(Integer value) {
            addCriterion("is_bind_bank <>", value, "isBindBank");
            return (Criteria) this;
        }

        public Criteria andIsBindBankGreaterThan(Integer value) {
            addCriterion("is_bind_bank >", value, "isBindBank");
            return (Criteria) this;
        }

        public Criteria andIsBindBankGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_bind_bank >=", value, "isBindBank");
            return (Criteria) this;
        }

        public Criteria andIsBindBankLessThan(Integer value) {
            addCriterion("is_bind_bank <", value, "isBindBank");
            return (Criteria) this;
        }

        public Criteria andIsBindBankLessThanOrEqualTo(Integer value) {
            addCriterion("is_bind_bank <=", value, "isBindBank");
            return (Criteria) this;
        }

        public Criteria andIsBindBankIn(List<Integer> values) {
            addCriterion("is_bind_bank in", values, "isBindBank");
            return (Criteria) this;
        }

        public Criteria andIsBindBankNotIn(List<Integer> values) {
            addCriterion("is_bind_bank not in", values, "isBindBank");
            return (Criteria) this;
        }

        public Criteria andIsBindBankBetween(Integer value1, Integer value2) {
            addCriterion("is_bind_bank between", value1, value2, "isBindBank");
            return (Criteria) this;
        }

        public Criteria andIsBindBankNotBetween(Integer value1, Integer value2) {
            addCriterion("is_bind_bank not between", value1, value2, "isBindBank");
            return (Criteria) this;
        }

        public Criteria andCurrentInterestIsNull() {
            addCriterion("current_interest is null");
            return (Criteria) this;
        }

        public Criteria andCurrentInterestIsNotNull() {
            addCriterion("current_interest is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentInterestEqualTo(Double value) {
            addCriterion("current_interest =", value, "currentInterest");
            return (Criteria) this;
        }

        public Criteria andCurrentInterestNotEqualTo(Double value) {
            addCriterion("current_interest <>", value, "currentInterest");
            return (Criteria) this;
        }

        public Criteria andCurrentInterestGreaterThan(Double value) {
            addCriterion("current_interest >", value, "currentInterest");
            return (Criteria) this;
        }

        public Criteria andCurrentInterestGreaterThanOrEqualTo(Double value) {
            addCriterion("current_interest >=", value, "currentInterest");
            return (Criteria) this;
        }

        public Criteria andCurrentInterestLessThan(Double value) {
            addCriterion("current_interest <", value, "currentInterest");
            return (Criteria) this;
        }

        public Criteria andCurrentInterestLessThanOrEqualTo(Double value) {
            addCriterion("current_interest <=", value, "currentInterest");
            return (Criteria) this;
        }

        public Criteria andCurrentInterestIn(List<Double> values) {
            addCriterion("current_interest in", values, "currentInterest");
            return (Criteria) this;
        }

        public Criteria andCurrentInterestNotIn(List<Double> values) {
            addCriterion("current_interest not in", values, "currentInterest");
            return (Criteria) this;
        }

        public Criteria andCurrentInterestBetween(Double value1, Double value2) {
            addCriterion("current_interest between", value1, value2, "currentInterest");
            return (Criteria) this;
        }

        public Criteria andCurrentInterestNotBetween(Double value1, Double value2) {
            addCriterion("current_interest not between", value1, value2, "currentInterest");
            return (Criteria) this;
        }

        public Criteria andCurrentBonusIsNull() {
            addCriterion("current_bonus is null");
            return (Criteria) this;
        }

        public Criteria andCurrentBonusIsNotNull() {
            addCriterion("current_bonus is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentBonusEqualTo(Double value) {
            addCriterion("current_bonus =", value, "currentBonus");
            return (Criteria) this;
        }

        public Criteria andCurrentBonusNotEqualTo(Double value) {
            addCriterion("current_bonus <>", value, "currentBonus");
            return (Criteria) this;
        }

        public Criteria andCurrentBonusGreaterThan(Double value) {
            addCriterion("current_bonus >", value, "currentBonus");
            return (Criteria) this;
        }

        public Criteria andCurrentBonusGreaterThanOrEqualTo(Double value) {
            addCriterion("current_bonus >=", value, "currentBonus");
            return (Criteria) this;
        }

        public Criteria andCurrentBonusLessThan(Double value) {
            addCriterion("current_bonus <", value, "currentBonus");
            return (Criteria) this;
        }

        public Criteria andCurrentBonusLessThanOrEqualTo(Double value) {
            addCriterion("current_bonus <=", value, "currentBonus");
            return (Criteria) this;
        }

        public Criteria andCurrentBonusIn(List<Double> values) {
            addCriterion("current_bonus in", values, "currentBonus");
            return (Criteria) this;
        }

        public Criteria andCurrentBonusNotIn(List<Double> values) {
            addCriterion("current_bonus not in", values, "currentBonus");
            return (Criteria) this;
        }

        public Criteria andCurrentBonusBetween(Double value1, Double value2) {
            addCriterion("current_bonus between", value1, value2, "currentBonus");
            return (Criteria) this;
        }

        public Criteria andCurrentBonusNotBetween(Double value1, Double value2) {
            addCriterion("current_bonus not between", value1, value2, "currentBonus");
            return (Criteria) this;
        }

        public Criteria andTotalInterestIsNull() {
            addCriterion("total_interest is null");
            return (Criteria) this;
        }

        public Criteria andTotalInterestIsNotNull() {
            addCriterion("total_interest is not null");
            return (Criteria) this;
        }

        public Criteria andTotalInterestEqualTo(Double value) {
            addCriterion("total_interest =", value, "totalInterest");
            return (Criteria) this;
        }

        public Criteria andTotalInterestNotEqualTo(Double value) {
            addCriterion("total_interest <>", value, "totalInterest");
            return (Criteria) this;
        }

        public Criteria andTotalInterestGreaterThan(Double value) {
            addCriterion("total_interest >", value, "totalInterest");
            return (Criteria) this;
        }

        public Criteria andTotalInterestGreaterThanOrEqualTo(Double value) {
            addCriterion("total_interest >=", value, "totalInterest");
            return (Criteria) this;
        }

        public Criteria andTotalInterestLessThan(Double value) {
            addCriterion("total_interest <", value, "totalInterest");
            return (Criteria) this;
        }

        public Criteria andTotalInterestLessThanOrEqualTo(Double value) {
            addCriterion("total_interest <=", value, "totalInterest");
            return (Criteria) this;
        }

        public Criteria andTotalInterestIn(List<Double> values) {
            addCriterion("total_interest in", values, "totalInterest");
            return (Criteria) this;
        }

        public Criteria andTotalInterestNotIn(List<Double> values) {
            addCriterion("total_interest not in", values, "totalInterest");
            return (Criteria) this;
        }

        public Criteria andTotalInterestBetween(Double value1, Double value2) {
            addCriterion("total_interest between", value1, value2, "totalInterest");
            return (Criteria) this;
        }

        public Criteria andTotalInterestNotBetween(Double value1, Double value2) {
            addCriterion("total_interest not between", value1, value2, "totalInterest");
            return (Criteria) this;
        }

        public Criteria andTotalBonusIsNull() {
            addCriterion("total_bonus is null");
            return (Criteria) this;
        }

        public Criteria andTotalBonusIsNotNull() {
            addCriterion("total_bonus is not null");
            return (Criteria) this;
        }

        public Criteria andTotalBonusEqualTo(Double value) {
            addCriterion("total_bonus =", value, "totalBonus");
            return (Criteria) this;
        }

        public Criteria andTotalBonusNotEqualTo(Double value) {
            addCriterion("total_bonus <>", value, "totalBonus");
            return (Criteria) this;
        }

        public Criteria andTotalBonusGreaterThan(Double value) {
            addCriterion("total_bonus >", value, "totalBonus");
            return (Criteria) this;
        }

        public Criteria andTotalBonusGreaterThanOrEqualTo(Double value) {
            addCriterion("total_bonus >=", value, "totalBonus");
            return (Criteria) this;
        }

        public Criteria andTotalBonusLessThan(Double value) {
            addCriterion("total_bonus <", value, "totalBonus");
            return (Criteria) this;
        }

        public Criteria andTotalBonusLessThanOrEqualTo(Double value) {
            addCriterion("total_bonus <=", value, "totalBonus");
            return (Criteria) this;
        }

        public Criteria andTotalBonusIn(List<Double> values) {
            addCriterion("total_bonus in", values, "totalBonus");
            return (Criteria) this;
        }

        public Criteria andTotalBonusNotIn(List<Double> values) {
            addCriterion("total_bonus not in", values, "totalBonus");
            return (Criteria) this;
        }

        public Criteria andTotalBonusBetween(Double value1, Double value2) {
            addCriterion("total_bonus between", value1, value2, "totalBonus");
            return (Criteria) this;
        }

        public Criteria andTotalBonusNotBetween(Double value1, Double value2) {
            addCriterion("total_bonus not between", value1, value2, "totalBonus");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawIsNull() {
            addCriterion("can_withdraw is null");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawIsNotNull() {
            addCriterion("can_withdraw is not null");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawEqualTo(Double value) {
            addCriterion("can_withdraw =", value, "canWithdraw");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawNotEqualTo(Double value) {
            addCriterion("can_withdraw <>", value, "canWithdraw");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawGreaterThan(Double value) {
            addCriterion("can_withdraw >", value, "canWithdraw");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawGreaterThanOrEqualTo(Double value) {
            addCriterion("can_withdraw >=", value, "canWithdraw");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawLessThan(Double value) {
            addCriterion("can_withdraw <", value, "canWithdraw");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawLessThanOrEqualTo(Double value) {
            addCriterion("can_withdraw <=", value, "canWithdraw");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawIn(List<Double> values) {
            addCriterion("can_withdraw in", values, "canWithdraw");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawNotIn(List<Double> values) {
            addCriterion("can_withdraw not in", values, "canWithdraw");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawBetween(Double value1, Double value2) {
            addCriterion("can_withdraw between", value1, value2, "canWithdraw");
            return (Criteria) this;
        }

        public Criteria andCanWithdrawNotBetween(Double value1, Double value2) {
            addCriterion("can_withdraw not between", value1, value2, "canWithdraw");
            return (Criteria) this;
        }

        public Criteria andTotalTransIsNull() {
            addCriterion("total_trans is null");
            return (Criteria) this;
        }

        public Criteria andTotalTransIsNotNull() {
            addCriterion("total_trans is not null");
            return (Criteria) this;
        }

        public Criteria andTotalTransEqualTo(Integer value) {
            addCriterion("total_trans =", value, "totalTrans");
            return (Criteria) this;
        }

        public Criteria andTotalTransNotEqualTo(Integer value) {
            addCriterion("total_trans <>", value, "totalTrans");
            return (Criteria) this;
        }

        public Criteria andTotalTransGreaterThan(Integer value) {
            addCriterion("total_trans >", value, "totalTrans");
            return (Criteria) this;
        }

        public Criteria andTotalTransGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_trans >=", value, "totalTrans");
            return (Criteria) this;
        }

        public Criteria andTotalTransLessThan(Integer value) {
            addCriterion("total_trans <", value, "totalTrans");
            return (Criteria) this;
        }

        public Criteria andTotalTransLessThanOrEqualTo(Integer value) {
            addCriterion("total_trans <=", value, "totalTrans");
            return (Criteria) this;
        }

        public Criteria andTotalTransIn(List<Integer> values) {
            addCriterion("total_trans in", values, "totalTrans");
            return (Criteria) this;
        }

        public Criteria andTotalTransNotIn(List<Integer> values) {
            addCriterion("total_trans not in", values, "totalTrans");
            return (Criteria) this;
        }

        public Criteria andTotalTransBetween(Integer value1, Integer value2) {
            addCriterion("total_trans between", value1, value2, "totalTrans");
            return (Criteria) this;
        }

        public Criteria andTotalTransNotBetween(Integer value1, Integer value2) {
            addCriterion("total_trans not between", value1, value2, "totalTrans");
            return (Criteria) this;
        }

        public Criteria andRecommendIdIsNull() {
            addCriterion("recommend_id is null");
            return (Criteria) this;
        }

        public Criteria andRecommendIdIsNotNull() {
            addCriterion("recommend_id is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendIdEqualTo(Integer value) {
            addCriterion("recommend_id =", value, "recommendId");
            return (Criteria) this;
        }

        public Criteria andRecommendIdNotEqualTo(Integer value) {
            addCriterion("recommend_id <>", value, "recommendId");
            return (Criteria) this;
        }

        public Criteria andRecommendIdGreaterThan(Integer value) {
            addCriterion("recommend_id >", value, "recommendId");
            return (Criteria) this;
        }

        public Criteria andRecommendIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("recommend_id >=", value, "recommendId");
            return (Criteria) this;
        }

        public Criteria andRecommendIdLessThan(Integer value) {
            addCriterion("recommend_id <", value, "recommendId");
            return (Criteria) this;
        }

        public Criteria andRecommendIdLessThanOrEqualTo(Integer value) {
            addCriterion("recommend_id <=", value, "recommendId");
            return (Criteria) this;
        }

        public Criteria andRecommendIdIn(List<Integer> values) {
            addCriterion("recommend_id in", values, "recommendId");
            return (Criteria) this;
        }

        public Criteria andRecommendIdNotIn(List<Integer> values) {
            addCriterion("recommend_id not in", values, "recommendId");
            return (Criteria) this;
        }

        public Criteria andRecommendIdBetween(Integer value1, Integer value2) {
            addCriterion("recommend_id between", value1, value2, "recommendId");
            return (Criteria) this;
        }

        public Criteria andRecommendIdNotBetween(Integer value1, Integer value2) {
            addCriterion("recommend_id not between", value1, value2, "recommendId");
            return (Criteria) this;
        }

        public Criteria andCreateChannelIsNull() {
            addCriterion("create_channel is null");
            return (Criteria) this;
        }

        public Criteria andCreateChannelIsNotNull() {
            addCriterion("create_channel is not null");
            return (Criteria) this;
        }

        public Criteria andCreateChannelEqualTo(Integer value) {
            addCriterion("create_channel =", value, "createChannel");
            return (Criteria) this;
        }

        public Criteria andCreateChannelNotEqualTo(Integer value) {
            addCriterion("create_channel <>", value, "createChannel");
            return (Criteria) this;
        }

        public Criteria andCreateChannelGreaterThan(Integer value) {
            addCriterion("create_channel >", value, "createChannel");
            return (Criteria) this;
        }

        public Criteria andCreateChannelGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_channel >=", value, "createChannel");
            return (Criteria) this;
        }

        public Criteria andCreateChannelLessThan(Integer value) {
            addCriterion("create_channel <", value, "createChannel");
            return (Criteria) this;
        }

        public Criteria andCreateChannelLessThanOrEqualTo(Integer value) {
            addCriterion("create_channel <=", value, "createChannel");
            return (Criteria) this;
        }

        public Criteria andCreateChannelIn(List<Integer> values) {
            addCriterion("create_channel in", values, "createChannel");
            return (Criteria) this;
        }

        public Criteria andCreateChannelNotIn(List<Integer> values) {
            addCriterion("create_channel not in", values, "createChannel");
            return (Criteria) this;
        }

        public Criteria andCreateChannelBetween(Integer value1, Integer value2) {
            addCriterion("create_channel between", value1, value2, "createChannel");
            return (Criteria) this;
        }

        public Criteria andCreateChannelNotBetween(Integer value1, Integer value2) {
            addCriterion("create_channel not between", value1, value2, "createChannel");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNull() {
            addCriterion("user_type is null");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNotNull() {
            addCriterion("user_type is not null");
            return (Criteria) this;
        }

        public Criteria andUserTypeEqualTo(String value) {
            addCriterion("user_type =", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotEqualTo(String value) {
            addCriterion("user_type <>", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThan(String value) {
            addCriterion("user_type >", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThanOrEqualTo(String value) {
            addCriterion("user_type >=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThan(String value) {
            addCriterion("user_type <", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThanOrEqualTo(String value) {
            addCriterion("user_type <=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLike(String value) {
            addCriterion("user_type like", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotLike(String value) {
            addCriterion("user_type not like", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeIn(List<String> values) {
            addCriterion("user_type in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotIn(List<String> values) {
            addCriterion("user_type not in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeBetween(String value1, String value2) {
            addCriterion("user_type between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotBetween(String value1, String value2) {
            addCriterion("user_type not between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andAgentIdIsNull() {
            addCriterion("agent_id is null");
            return (Criteria) this;
        }

        public Criteria andAgentIdIsNotNull() {
            addCriterion("agent_id is not null");
            return (Criteria) this;
        }

        public Criteria andAgentIdEqualTo(Integer value) {
            addCriterion("agent_id =", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotEqualTo(Integer value) {
            addCriterion("agent_id <>", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdGreaterThan(Integer value) {
            addCriterion("agent_id >", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("agent_id >=", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLessThan(Integer value) {
            addCriterion("agent_id <", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLessThanOrEqualTo(Integer value) {
            addCriterion("agent_id <=", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdIn(List<Integer> values) {
            addCriterion("agent_id in", values, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotIn(List<Integer> values) {
            addCriterion("agent_id not in", values, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdBetween(Integer value1, Integer value2) {
            addCriterion("agent_id between", value1, value2, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("agent_id not between", value1, value2, "agentId");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeIsNull() {
            addCriterion("register_time is null");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeIsNotNull() {
            addCriterion("register_time is not null");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeEqualTo(Date value) {
            addCriterion("register_time =", value, "registerTime");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeNotEqualTo(Date value) {
            addCriterion("register_time <>", value, "registerTime");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeGreaterThan(Date value) {
            addCriterion("register_time >", value, "registerTime");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("register_time >=", value, "registerTime");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeLessThan(Date value) {
            addCriterion("register_time <", value, "registerTime");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeLessThanOrEqualTo(Date value) {
            addCriterion("register_time <=", value, "registerTime");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeIn(List<Date> values) {
            addCriterion("register_time in", values, "registerTime");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeNotIn(List<Date> values) {
            addCriterion("register_time not in", values, "registerTime");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeBetween(Date value1, Date value2) {
            addCriterion("register_time between", value1, value2, "registerTime");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeNotBetween(Date value1, Date value2) {
            addCriterion("register_time not between", value1, value2, "registerTime");
            return (Criteria) this;
        }

        public Criteria andRegTerminalIsNull() {
            addCriterion("reg_terminal is null");
            return (Criteria) this;
        }

        public Criteria andRegTerminalIsNotNull() {
            addCriterion("reg_terminal is not null");
            return (Criteria) this;
        }

        public Criteria andRegTerminalEqualTo(Integer value) {
            addCriterion("reg_terminal =", value, "regTerminal");
            return (Criteria) this;
        }

        public Criteria andRegTerminalNotEqualTo(Integer value) {
            addCriterion("reg_terminal <>", value, "regTerminal");
            return (Criteria) this;
        }

        public Criteria andRegTerminalGreaterThan(Integer value) {
            addCriterion("reg_terminal >", value, "regTerminal");
            return (Criteria) this;
        }

        public Criteria andRegTerminalGreaterThanOrEqualTo(Integer value) {
            addCriterion("reg_terminal >=", value, "regTerminal");
            return (Criteria) this;
        }

        public Criteria andRegTerminalLessThan(Integer value) {
            addCriterion("reg_terminal <", value, "regTerminal");
            return (Criteria) this;
        }

        public Criteria andRegTerminalLessThanOrEqualTo(Integer value) {
            addCriterion("reg_terminal <=", value, "regTerminal");
            return (Criteria) this;
        }

        public Criteria andRegTerminalIn(List<Integer> values) {
            addCriterion("reg_terminal in", values, "regTerminal");
            return (Criteria) this;
        }

        public Criteria andRegTerminalNotIn(List<Integer> values) {
            addCriterion("reg_terminal not in", values, "regTerminal");
            return (Criteria) this;
        }

        public Criteria andRegTerminalBetween(Integer value1, Integer value2) {
            addCriterion("reg_terminal between", value1, value2, "regTerminal");
            return (Criteria) this;
        }

        public Criteria andRegTerminalNotBetween(Integer value1, Integer value2) {
            addCriterion("reg_terminal not between", value1, value2, "regTerminal");
            return (Criteria) this;
        }

        public Criteria andFirstBuyTimeIsNull() {
            addCriterion("first_buy_time is null");
            return (Criteria) this;
        }

        public Criteria andFirstBuyTimeIsNotNull() {
            addCriterion("first_buy_time is not null");
            return (Criteria) this;
        }

        public Criteria andFirstBuyTimeEqualTo(Date value) {
            addCriterion("first_buy_time =", value, "firstBuyTime");
            return (Criteria) this;
        }

        public Criteria andFirstBuyTimeNotEqualTo(Date value) {
            addCriterion("first_buy_time <>", value, "firstBuyTime");
            return (Criteria) this;
        }

        public Criteria andFirstBuyTimeGreaterThan(Date value) {
            addCriterion("first_buy_time >", value, "firstBuyTime");
            return (Criteria) this;
        }

        public Criteria andFirstBuyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("first_buy_time >=", value, "firstBuyTime");
            return (Criteria) this;
        }

        public Criteria andFirstBuyTimeLessThan(Date value) {
            addCriterion("first_buy_time <", value, "firstBuyTime");
            return (Criteria) this;
        }

        public Criteria andFirstBuyTimeLessThanOrEqualTo(Date value) {
            addCriterion("first_buy_time <=", value, "firstBuyTime");
            return (Criteria) this;
        }

        public Criteria andFirstBuyTimeIn(List<Date> values) {
            addCriterion("first_buy_time in", values, "firstBuyTime");
            return (Criteria) this;
        }

        public Criteria andFirstBuyTimeNotIn(List<Date> values) {
            addCriterion("first_buy_time not in", values, "firstBuyTime");
            return (Criteria) this;
        }

        public Criteria andFirstBuyTimeBetween(Date value1, Date value2) {
            addCriterion("first_buy_time between", value1, value2, "firstBuyTime");
            return (Criteria) this;
        }

        public Criteria andFirstBuyTimeNotBetween(Date value1, Date value2) {
            addCriterion("first_buy_time not between", value1, value2, "firstBuyTime");
            return (Criteria) this;
        }

        public Criteria andReturnPathIsNull() {
            addCriterion("return_path is null");
            return (Criteria) this;
        }

        public Criteria andReturnPathIsNotNull() {
            addCriterion("return_path is not null");
            return (Criteria) this;
        }

        public Criteria andReturnPathEqualTo(Integer value) {
            addCriterion("return_path =", value, "returnPath");
            return (Criteria) this;
        }

        public Criteria andReturnPathNotEqualTo(Integer value) {
            addCriterion("return_path <>", value, "returnPath");
            return (Criteria) this;
        }

        public Criteria andReturnPathGreaterThan(Integer value) {
            addCriterion("return_path >", value, "returnPath");
            return (Criteria) this;
        }

        public Criteria andReturnPathGreaterThanOrEqualTo(Integer value) {
            addCriterion("return_path >=", value, "returnPath");
            return (Criteria) this;
        }

        public Criteria andReturnPathLessThan(Integer value) {
            addCriterion("return_path <", value, "returnPath");
            return (Criteria) this;
        }

        public Criteria andReturnPathLessThanOrEqualTo(Integer value) {
            addCriterion("return_path <=", value, "returnPath");
            return (Criteria) this;
        }

        public Criteria andReturnPathIn(List<Integer> values) {
            addCriterion("return_path in", values, "returnPath");
            return (Criteria) this;
        }

        public Criteria andReturnPathNotIn(List<Integer> values) {
            addCriterion("return_path not in", values, "returnPath");
            return (Criteria) this;
        }

        public Criteria andReturnPathBetween(Integer value1, Integer value2) {
            addCriterion("return_path between", value1, value2, "returnPath");
            return (Criteria) this;
        }

        public Criteria andReturnPathNotBetween(Integer value1, Integer value2) {
            addCriterion("return_path not between", value1, value2, "returnPath");
            return (Criteria) this;
        }

        public Criteria andRecentBankCardIdIsNull() {
            addCriterion("recent_bank_card_id is null");
            return (Criteria) this;
        }

        public Criteria andRecentBankCardIdIsNotNull() {
            addCriterion("recent_bank_card_id is not null");
            return (Criteria) this;
        }

        public Criteria andRecentBankCardIdEqualTo(Integer value) {
            addCriterion("recent_bank_card_id =", value, "recentBankCardId");
            return (Criteria) this;
        }

        public Criteria andRecentBankCardIdNotEqualTo(Integer value) {
            addCriterion("recent_bank_card_id <>", value, "recentBankCardId");
            return (Criteria) this;
        }

        public Criteria andRecentBankCardIdGreaterThan(Integer value) {
            addCriterion("recent_bank_card_id >", value, "recentBankCardId");
            return (Criteria) this;
        }

        public Criteria andRecentBankCardIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("recent_bank_card_id >=", value, "recentBankCardId");
            return (Criteria) this;
        }

        public Criteria andRecentBankCardIdLessThan(Integer value) {
            addCriterion("recent_bank_card_id <", value, "recentBankCardId");
            return (Criteria) this;
        }

        public Criteria andRecentBankCardIdLessThanOrEqualTo(Integer value) {
            addCriterion("recent_bank_card_id <=", value, "recentBankCardId");
            return (Criteria) this;
        }

        public Criteria andRecentBankCardIdIn(List<Integer> values) {
            addCriterion("recent_bank_card_id in", values, "recentBankCardId");
            return (Criteria) this;
        }

        public Criteria andRecentBankCardIdNotIn(List<Integer> values) {
            addCriterion("recent_bank_card_id not in", values, "recentBankCardId");
            return (Criteria) this;
        }

        public Criteria andRecentBankCardIdBetween(Integer value1, Integer value2) {
            addCriterion("recent_bank_card_id between", value1, value2, "recentBankCardId");
            return (Criteria) this;
        }

        public Criteria andRecentBankCardIdNotBetween(Integer value1, Integer value2) {
            addCriterion("recent_bank_card_id not between", value1, value2, "recentBankCardId");
            return (Criteria) this;
        }

        public Criteria andLastAppVersionIsNull() {
            addCriterion("last_app_version is null");
            return (Criteria) this;
        }

        public Criteria andLastAppVersionIsNotNull() {
            addCriterion("last_app_version is not null");
            return (Criteria) this;
        }

        public Criteria andLastAppVersionEqualTo(String value) {
            addCriterion("last_app_version =", value, "lastAppVersion");
            return (Criteria) this;
        }

        public Criteria andLastAppVersionNotEqualTo(String value) {
            addCriterion("last_app_version <>", value, "lastAppVersion");
            return (Criteria) this;
        }

        public Criteria andLastAppVersionGreaterThan(String value) {
            addCriterion("last_app_version >", value, "lastAppVersion");
            return (Criteria) this;
        }

        public Criteria andLastAppVersionGreaterThanOrEqualTo(String value) {
            addCriterion("last_app_version >=", value, "lastAppVersion");
            return (Criteria) this;
        }

        public Criteria andLastAppVersionLessThan(String value) {
            addCriterion("last_app_version <", value, "lastAppVersion");
            return (Criteria) this;
        }

        public Criteria andLastAppVersionLessThanOrEqualTo(String value) {
            addCriterion("last_app_version <=", value, "lastAppVersion");
            return (Criteria) this;
        }

        public Criteria andLastAppVersionLike(String value) {
            addCriterion("last_app_version like", value, "lastAppVersion");
            return (Criteria) this;
        }

        public Criteria andLastAppVersionNotLike(String value) {
            addCriterion("last_app_version not like", value, "lastAppVersion");
            return (Criteria) this;
        }

        public Criteria andLastAppVersionIn(List<String> values) {
            addCriterion("last_app_version in", values, "lastAppVersion");
            return (Criteria) this;
        }

        public Criteria andLastAppVersionNotIn(List<String> values) {
            addCriterion("last_app_version not in", values, "lastAppVersion");
            return (Criteria) this;
        }

        public Criteria andLastAppVersionBetween(String value1, String value2) {
            addCriterion("last_app_version between", value1, value2, "lastAppVersion");
            return (Criteria) this;
        }

        public Criteria andLastAppVersionNotBetween(String value1, String value2) {
            addCriterion("last_app_version not between", value1, value2, "lastAppVersion");
            return (Criteria) this;
        }

        public Criteria andLastAppTimeIsNull() {
            addCriterion("last_app_time is null");
            return (Criteria) this;
        }

        public Criteria andLastAppTimeIsNotNull() {
            addCriterion("last_app_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastAppTimeEqualTo(Date value) {
            addCriterion("last_app_time =", value, "lastAppTime");
            return (Criteria) this;
        }

        public Criteria andLastAppTimeNotEqualTo(Date value) {
            addCriterion("last_app_time <>", value, "lastAppTime");
            return (Criteria) this;
        }

        public Criteria andLastAppTimeGreaterThan(Date value) {
            addCriterion("last_app_time >", value, "lastAppTime");
            return (Criteria) this;
        }

        public Criteria andLastAppTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_app_time >=", value, "lastAppTime");
            return (Criteria) this;
        }

        public Criteria andLastAppTimeLessThan(Date value) {
            addCriterion("last_app_time <", value, "lastAppTime");
            return (Criteria) this;
        }

        public Criteria andLastAppTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_app_time <=", value, "lastAppTime");
            return (Criteria) this;
        }

        public Criteria andLastAppTimeIn(List<Date> values) {
            addCriterion("last_app_time in", values, "lastAppTime");
            return (Criteria) this;
        }

        public Criteria andLastAppTimeNotIn(List<Date> values) {
            addCriterion("last_app_time not in", values, "lastAppTime");
            return (Criteria) this;
        }

        public Criteria andLastAppTimeBetween(Date value1, Date value2) {
            addCriterion("last_app_time between", value1, value2, "lastAppTime");
            return (Criteria) this;
        }

        public Criteria andLastAppTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_app_time not between", value1, value2, "lastAppTime");
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