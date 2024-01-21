package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BsUserKeepViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsUserKeepViewExample() {
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

        public Criteria andRegistDateIsNull() {
            addCriterion("regist_date is null");
            return (Criteria) this;
        }

        public Criteria andRegistDateIsNotNull() {
            addCriterion("regist_date is not null");
            return (Criteria) this;
        }

        public Criteria andRegistDateEqualTo(Date value) {
            addCriterionForJDBCDate("regist_date =", value, "registDate");
            return (Criteria) this;
        }

        public Criteria andRegistDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("regist_date <>", value, "registDate");
            return (Criteria) this;
        }

        public Criteria andRegistDateGreaterThan(Date value) {
            addCriterionForJDBCDate("regist_date >", value, "registDate");
            return (Criteria) this;
        }

        public Criteria andRegistDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("regist_date >=", value, "registDate");
            return (Criteria) this;
        }

        public Criteria andRegistDateLessThan(Date value) {
            addCriterionForJDBCDate("regist_date <", value, "registDate");
            return (Criteria) this;
        }

        public Criteria andRegistDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("regist_date <=", value, "registDate");
            return (Criteria) this;
        }

        public Criteria andRegistDateIn(List<Date> values) {
            addCriterionForJDBCDate("regist_date in", values, "registDate");
            return (Criteria) this;
        }

        public Criteria andRegistDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("regist_date not in", values, "registDate");
            return (Criteria) this;
        }

        public Criteria andRegistDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("regist_date between", value1, value2, "registDate");
            return (Criteria) this;
        }

        public Criteria andRegistDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("regist_date not between", value1, value2, "registDate");
            return (Criteria) this;
        }

        public Criteria andExtensiveAgentIdIsNull() {
            addCriterion("extensive_agent_id is null");
            return (Criteria) this;
        }

        public Criteria andExtensiveAgentIdIsNotNull() {
            addCriterion("extensive_agent_id is not null");
            return (Criteria) this;
        }

        public Criteria andExtensiveAgentIdEqualTo(Integer value) {
            addCriterion("extensive_agent_id =", value, "extensiveAgentId");
            return (Criteria) this;
        }

        public Criteria andExtensiveAgentIdNotEqualTo(Integer value) {
            addCriterion("extensive_agent_id <>", value, "extensiveAgentId");
            return (Criteria) this;
        }

        public Criteria andExtensiveAgentIdGreaterThan(Integer value) {
            addCriterion("extensive_agent_id >", value, "extensiveAgentId");
            return (Criteria) this;
        }

        public Criteria andExtensiveAgentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("extensive_agent_id >=", value, "extensiveAgentId");
            return (Criteria) this;
        }

        public Criteria andExtensiveAgentIdLessThan(Integer value) {
            addCriterion("extensive_agent_id <", value, "extensiveAgentId");
            return (Criteria) this;
        }

        public Criteria andExtensiveAgentIdLessThanOrEqualTo(Integer value) {
            addCriterion("extensive_agent_id <=", value, "extensiveAgentId");
            return (Criteria) this;
        }

        public Criteria andExtensiveAgentIdIn(List<Integer> values) {
            addCriterion("extensive_agent_id in", values, "extensiveAgentId");
            return (Criteria) this;
        }

        public Criteria andExtensiveAgentIdNotIn(List<Integer> values) {
            addCriterion("extensive_agent_id not in", values, "extensiveAgentId");
            return (Criteria) this;
        }

        public Criteria andExtensiveAgentIdBetween(Integer value1, Integer value2) {
            addCriterion("extensive_agent_id between", value1, value2, "extensiveAgentId");
            return (Criteria) this;
        }

        public Criteria andExtensiveAgentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("extensive_agent_id not between", value1, value2, "extensiveAgentId");
            return (Criteria) this;
        }

        public Criteria andAgentNameIsNull() {
            addCriterion("agent_name is null");
            return (Criteria) this;
        }

        public Criteria andAgentNameIsNotNull() {
            addCriterion("agent_name is not null");
            return (Criteria) this;
        }

        public Criteria andAgentNameEqualTo(String value) {
            addCriterion("agent_name =", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotEqualTo(String value) {
            addCriterion("agent_name <>", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameGreaterThan(String value) {
            addCriterion("agent_name >", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("agent_name >=", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLessThan(String value) {
            addCriterion("agent_name <", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLessThanOrEqualTo(String value) {
            addCriterion("agent_name <=", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLike(String value) {
            addCriterion("agent_name like", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotLike(String value) {
            addCriterion("agent_name not like", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameIn(List<String> values) {
            addCriterion("agent_name in", values, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotIn(List<String> values) {
            addCriterion("agent_name not in", values, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameBetween(String value1, String value2) {
            addCriterion("agent_name between", value1, value2, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotBetween(String value1, String value2) {
            addCriterion("agent_name not between", value1, value2, "agentName");
            return (Criteria) this;
        }

        public Criteria andRegistNumIsNull() {
            addCriterion("regist_num is null");
            return (Criteria) this;
        }

        public Criteria andRegistNumIsNotNull() {
            addCriterion("regist_num is not null");
            return (Criteria) this;
        }

        public Criteria andRegistNumEqualTo(Integer value) {
            addCriterion("regist_num =", value, "registNum");
            return (Criteria) this;
        }

        public Criteria andRegistNumNotEqualTo(Integer value) {
            addCriterion("regist_num <>", value, "registNum");
            return (Criteria) this;
        }

        public Criteria andRegistNumGreaterThan(Integer value) {
            addCriterion("regist_num >", value, "registNum");
            return (Criteria) this;
        }

        public Criteria andRegistNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("regist_num >=", value, "registNum");
            return (Criteria) this;
        }

        public Criteria andRegistNumLessThan(Integer value) {
            addCriterion("regist_num <", value, "registNum");
            return (Criteria) this;
        }

        public Criteria andRegistNumLessThanOrEqualTo(Integer value) {
            addCriterion("regist_num <=", value, "registNum");
            return (Criteria) this;
        }

        public Criteria andRegistNumIn(List<Integer> values) {
            addCriterion("regist_num in", values, "registNum");
            return (Criteria) this;
        }

        public Criteria andRegistNumNotIn(List<Integer> values) {
            addCriterion("regist_num not in", values, "registNum");
            return (Criteria) this;
        }

        public Criteria andRegistNumBetween(Integer value1, Integer value2) {
            addCriterion("regist_num between", value1, value2, "registNum");
            return (Criteria) this;
        }

        public Criteria andRegistNumNotBetween(Integer value1, Integer value2) {
            addCriterion("regist_num not between", value1, value2, "registNum");
            return (Criteria) this;
        }

        public Criteria andDay2LoginNumIsNull() {
            addCriterion("day2_login_num is null");
            return (Criteria) this;
        }

        public Criteria andDay2LoginNumIsNotNull() {
            addCriterion("day2_login_num is not null");
            return (Criteria) this;
        }

        public Criteria andDay2LoginNumEqualTo(Integer value) {
            addCriterion("day2_login_num =", value, "day2LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay2LoginNumNotEqualTo(Integer value) {
            addCriterion("day2_login_num <>", value, "day2LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay2LoginNumGreaterThan(Integer value) {
            addCriterion("day2_login_num >", value, "day2LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay2LoginNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("day2_login_num >=", value, "day2LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay2LoginNumLessThan(Integer value) {
            addCriterion("day2_login_num <", value, "day2LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay2LoginNumLessThanOrEqualTo(Integer value) {
            addCriterion("day2_login_num <=", value, "day2LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay2LoginNumIn(List<Integer> values) {
            addCriterion("day2_login_num in", values, "day2LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay2LoginNumNotIn(List<Integer> values) {
            addCriterion("day2_login_num not in", values, "day2LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay2LoginNumBetween(Integer value1, Integer value2) {
            addCriterion("day2_login_num between", value1, value2, "day2LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay2LoginNumNotBetween(Integer value1, Integer value2) {
            addCriterion("day2_login_num not between", value1, value2, "day2LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay3LoginNumIsNull() {
            addCriterion("day3_login_num is null");
            return (Criteria) this;
        }

        public Criteria andDay3LoginNumIsNotNull() {
            addCriterion("day3_login_num is not null");
            return (Criteria) this;
        }

        public Criteria andDay3LoginNumEqualTo(Integer value) {
            addCriterion("day3_login_num =", value, "day3LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay3LoginNumNotEqualTo(Integer value) {
            addCriterion("day3_login_num <>", value, "day3LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay3LoginNumGreaterThan(Integer value) {
            addCriterion("day3_login_num >", value, "day3LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay3LoginNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("day3_login_num >=", value, "day3LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay3LoginNumLessThan(Integer value) {
            addCriterion("day3_login_num <", value, "day3LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay3LoginNumLessThanOrEqualTo(Integer value) {
            addCriterion("day3_login_num <=", value, "day3LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay3LoginNumIn(List<Integer> values) {
            addCriterion("day3_login_num in", values, "day3LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay3LoginNumNotIn(List<Integer> values) {
            addCriterion("day3_login_num not in", values, "day3LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay3LoginNumBetween(Integer value1, Integer value2) {
            addCriterion("day3_login_num between", value1, value2, "day3LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay3LoginNumNotBetween(Integer value1, Integer value2) {
            addCriterion("day3_login_num not between", value1, value2, "day3LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay7LoginNumIsNull() {
            addCriterion("day7_login_num is null");
            return (Criteria) this;
        }

        public Criteria andDay7LoginNumIsNotNull() {
            addCriterion("day7_login_num is not null");
            return (Criteria) this;
        }

        public Criteria andDay7LoginNumEqualTo(Integer value) {
            addCriterion("day7_login_num =", value, "day7LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay7LoginNumNotEqualTo(Integer value) {
            addCriterion("day7_login_num <>", value, "day7LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay7LoginNumGreaterThan(Integer value) {
            addCriterion("day7_login_num >", value, "day7LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay7LoginNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("day7_login_num >=", value, "day7LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay7LoginNumLessThan(Integer value) {
            addCriterion("day7_login_num <", value, "day7LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay7LoginNumLessThanOrEqualTo(Integer value) {
            addCriterion("day7_login_num <=", value, "day7LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay7LoginNumIn(List<Integer> values) {
            addCriterion("day7_login_num in", values, "day7LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay7LoginNumNotIn(List<Integer> values) {
            addCriterion("day7_login_num not in", values, "day7LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay7LoginNumBetween(Integer value1, Integer value2) {
            addCriterion("day7_login_num between", value1, value2, "day7LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay7LoginNumNotBetween(Integer value1, Integer value2) {
            addCriterion("day7_login_num not between", value1, value2, "day7LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay14LoginNumIsNull() {
            addCriterion("day14_login_num is null");
            return (Criteria) this;
        }

        public Criteria andDay14LoginNumIsNotNull() {
            addCriterion("day14_login_num is not null");
            return (Criteria) this;
        }

        public Criteria andDay14LoginNumEqualTo(Integer value) {
            addCriterion("day14_login_num =", value, "day14LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay14LoginNumNotEqualTo(Integer value) {
            addCriterion("day14_login_num <>", value, "day14LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay14LoginNumGreaterThan(Integer value) {
            addCriterion("day14_login_num >", value, "day14LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay14LoginNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("day14_login_num >=", value, "day14LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay14LoginNumLessThan(Integer value) {
            addCriterion("day14_login_num <", value, "day14LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay14LoginNumLessThanOrEqualTo(Integer value) {
            addCriterion("day14_login_num <=", value, "day14LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay14LoginNumIn(List<Integer> values) {
            addCriterion("day14_login_num in", values, "day14LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay14LoginNumNotIn(List<Integer> values) {
            addCriterion("day14_login_num not in", values, "day14LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay14LoginNumBetween(Integer value1, Integer value2) {
            addCriterion("day14_login_num between", value1, value2, "day14LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay14LoginNumNotBetween(Integer value1, Integer value2) {
            addCriterion("day14_login_num not between", value1, value2, "day14LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay30LoginNumIsNull() {
            addCriterion("day30_login_num is null");
            return (Criteria) this;
        }

        public Criteria andDay30LoginNumIsNotNull() {
            addCriterion("day30_login_num is not null");
            return (Criteria) this;
        }

        public Criteria andDay30LoginNumEqualTo(Integer value) {
            addCriterion("day30_login_num =", value, "day30LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay30LoginNumNotEqualTo(Integer value) {
            addCriterion("day30_login_num <>", value, "day30LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay30LoginNumGreaterThan(Integer value) {
            addCriterion("day30_login_num >", value, "day30LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay30LoginNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("day30_login_num >=", value, "day30LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay30LoginNumLessThan(Integer value) {
            addCriterion("day30_login_num <", value, "day30LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay30LoginNumLessThanOrEqualTo(Integer value) {
            addCriterion("day30_login_num <=", value, "day30LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay30LoginNumIn(List<Integer> values) {
            addCriterion("day30_login_num in", values, "day30LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay30LoginNumNotIn(List<Integer> values) {
            addCriterion("day30_login_num not in", values, "day30LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay30LoginNumBetween(Integer value1, Integer value2) {
            addCriterion("day30_login_num between", value1, value2, "day30LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay30LoginNumNotBetween(Integer value1, Integer value2) {
            addCriterion("day30_login_num not between", value1, value2, "day30LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay60LoginNumIsNull() {
            addCriterion("day60_login_num is null");
            return (Criteria) this;
        }

        public Criteria andDay60LoginNumIsNotNull() {
            addCriterion("day60_login_num is not null");
            return (Criteria) this;
        }

        public Criteria andDay60LoginNumEqualTo(Integer value) {
            addCriterion("day60_login_num =", value, "day60LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay60LoginNumNotEqualTo(Integer value) {
            addCriterion("day60_login_num <>", value, "day60LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay60LoginNumGreaterThan(Integer value) {
            addCriterion("day60_login_num >", value, "day60LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay60LoginNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("day60_login_num >=", value, "day60LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay60LoginNumLessThan(Integer value) {
            addCriterion("day60_login_num <", value, "day60LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay60LoginNumLessThanOrEqualTo(Integer value) {
            addCriterion("day60_login_num <=", value, "day60LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay60LoginNumIn(List<Integer> values) {
            addCriterion("day60_login_num in", values, "day60LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay60LoginNumNotIn(List<Integer> values) {
            addCriterion("day60_login_num not in", values, "day60LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay60LoginNumBetween(Integer value1, Integer value2) {
            addCriterion("day60_login_num between", value1, value2, "day60LoginNum");
            return (Criteria) this;
        }

        public Criteria andDay60LoginNumNotBetween(Integer value1, Integer value2) {
            addCriterion("day60_login_num not between", value1, value2, "day60LoginNum");
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