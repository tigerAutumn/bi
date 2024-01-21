package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BsDailyStatExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsDailyStatExample() {
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

        public Criteria andTotalRegistUserIsNull() {
            addCriterion("total_regist_user is null");
            return (Criteria) this;
        }

        public Criteria andTotalRegistUserIsNotNull() {
            addCriterion("total_regist_user is not null");
            return (Criteria) this;
        }

        public Criteria andTotalRegistUserEqualTo(Integer value) {
            addCriterion("total_regist_user =", value, "totalRegistUser");
            return (Criteria) this;
        }

        public Criteria andTotalRegistUserNotEqualTo(Integer value) {
            addCriterion("total_regist_user <>", value, "totalRegistUser");
            return (Criteria) this;
        }

        public Criteria andTotalRegistUserGreaterThan(Integer value) {
            addCriterion("total_regist_user >", value, "totalRegistUser");
            return (Criteria) this;
        }

        public Criteria andTotalRegistUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_regist_user >=", value, "totalRegistUser");
            return (Criteria) this;
        }

        public Criteria andTotalRegistUserLessThan(Integer value) {
            addCriterion("total_regist_user <", value, "totalRegistUser");
            return (Criteria) this;
        }

        public Criteria andTotalRegistUserLessThanOrEqualTo(Integer value) {
            addCriterion("total_regist_user <=", value, "totalRegistUser");
            return (Criteria) this;
        }

        public Criteria andTotalRegistUserIn(List<Integer> values) {
            addCriterion("total_regist_user in", values, "totalRegistUser");
            return (Criteria) this;
        }

        public Criteria andTotalRegistUserNotIn(List<Integer> values) {
            addCriterion("total_regist_user not in", values, "totalRegistUser");
            return (Criteria) this;
        }

        public Criteria andTotalRegistUserBetween(Integer value1, Integer value2) {
            addCriterion("total_regist_user between", value1, value2, "totalRegistUser");
            return (Criteria) this;
        }

        public Criteria andTotalRegistUserNotBetween(Integer value1, Integer value2) {
            addCriterion("total_regist_user not between", value1, value2, "totalRegistUser");
            return (Criteria) this;
        }

        public Criteria andTotalInvestUserIsNull() {
            addCriterion("total_invest_user is null");
            return (Criteria) this;
        }

        public Criteria andTotalInvestUserIsNotNull() {
            addCriterion("total_invest_user is not null");
            return (Criteria) this;
        }

        public Criteria andTotalInvestUserEqualTo(Integer value) {
            addCriterion("total_invest_user =", value, "totalInvestUser");
            return (Criteria) this;
        }

        public Criteria andTotalInvestUserNotEqualTo(Integer value) {
            addCriterion("total_invest_user <>", value, "totalInvestUser");
            return (Criteria) this;
        }

        public Criteria andTotalInvestUserGreaterThan(Integer value) {
            addCriterion("total_invest_user >", value, "totalInvestUser");
            return (Criteria) this;
        }

        public Criteria andTotalInvestUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_invest_user >=", value, "totalInvestUser");
            return (Criteria) this;
        }

        public Criteria andTotalInvestUserLessThan(Integer value) {
            addCriterion("total_invest_user <", value, "totalInvestUser");
            return (Criteria) this;
        }

        public Criteria andTotalInvestUserLessThanOrEqualTo(Integer value) {
            addCriterion("total_invest_user <=", value, "totalInvestUser");
            return (Criteria) this;
        }

        public Criteria andTotalInvestUserIn(List<Integer> values) {
            addCriterion("total_invest_user in", values, "totalInvestUser");
            return (Criteria) this;
        }

        public Criteria andTotalInvestUserNotIn(List<Integer> values) {
            addCriterion("total_invest_user not in", values, "totalInvestUser");
            return (Criteria) this;
        }

        public Criteria andTotalInvestUserBetween(Integer value1, Integer value2) {
            addCriterion("total_invest_user between", value1, value2, "totalInvestUser");
            return (Criteria) this;
        }

        public Criteria andTotalInvestUserNotBetween(Integer value1, Integer value2) {
            addCriterion("total_invest_user not between", value1, value2, "totalInvestUser");
            return (Criteria) this;
        }

        public Criteria andTotalReinvestUserIsNull() {
            addCriterion("total_reinvest_user is null");
            return (Criteria) this;
        }

        public Criteria andTotalReinvestUserIsNotNull() {
            addCriterion("total_reinvest_user is not null");
            return (Criteria) this;
        }

        public Criteria andTotalReinvestUserEqualTo(Integer value) {
            addCriterion("total_reinvest_user =", value, "totalReinvestUser");
            return (Criteria) this;
        }

        public Criteria andTotalReinvestUserNotEqualTo(Integer value) {
            addCriterion("total_reinvest_user <>", value, "totalReinvestUser");
            return (Criteria) this;
        }

        public Criteria andTotalReinvestUserGreaterThan(Integer value) {
            addCriterion("total_reinvest_user >", value, "totalReinvestUser");
            return (Criteria) this;
        }

        public Criteria andTotalReinvestUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_reinvest_user >=", value, "totalReinvestUser");
            return (Criteria) this;
        }

        public Criteria andTotalReinvestUserLessThan(Integer value) {
            addCriterion("total_reinvest_user <", value, "totalReinvestUser");
            return (Criteria) this;
        }

        public Criteria andTotalReinvestUserLessThanOrEqualTo(Integer value) {
            addCriterion("total_reinvest_user <=", value, "totalReinvestUser");
            return (Criteria) this;
        }

        public Criteria andTotalReinvestUserIn(List<Integer> values) {
            addCriterion("total_reinvest_user in", values, "totalReinvestUser");
            return (Criteria) this;
        }

        public Criteria andTotalReinvestUserNotIn(List<Integer> values) {
            addCriterion("total_reinvest_user not in", values, "totalReinvestUser");
            return (Criteria) this;
        }

        public Criteria andTotalReinvestUserBetween(Integer value1, Integer value2) {
            addCriterion("total_reinvest_user between", value1, value2, "totalReinvestUser");
            return (Criteria) this;
        }

        public Criteria andTotalReinvestUserNotBetween(Integer value1, Integer value2) {
            addCriterion("total_reinvest_user not between", value1, value2, "totalReinvestUser");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountIsNull() {
            addCriterion("total_invest_amount is null");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountIsNotNull() {
            addCriterion("total_invest_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountEqualTo(Double value) {
            addCriterion("total_invest_amount =", value, "totalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountNotEqualTo(Double value) {
            addCriterion("total_invest_amount <>", value, "totalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountGreaterThan(Double value) {
            addCriterion("total_invest_amount >", value, "totalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("total_invest_amount >=", value, "totalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountLessThan(Double value) {
            addCriterion("total_invest_amount <", value, "totalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountLessThanOrEqualTo(Double value) {
            addCriterion("total_invest_amount <=", value, "totalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountIn(List<Double> values) {
            addCriterion("total_invest_amount in", values, "totalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountNotIn(List<Double> values) {
            addCriterion("total_invest_amount not in", values, "totalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountBetween(Double value1, Double value2) {
            addCriterion("total_invest_amount between", value1, value2, "totalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andTotalInvestAmountNotBetween(Double value1, Double value2) {
            addCriterion("total_invest_amount not between", value1, value2, "totalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyNewWxUserIsNull() {
            addCriterion("daily_new_wx_user is null");
            return (Criteria) this;
        }

        public Criteria andDailyNewWxUserIsNotNull() {
            addCriterion("daily_new_wx_user is not null");
            return (Criteria) this;
        }

        public Criteria andDailyNewWxUserEqualTo(Integer value) {
            addCriterion("daily_new_wx_user =", value, "dailyNewWxUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewWxUserNotEqualTo(Integer value) {
            addCriterion("daily_new_wx_user <>", value, "dailyNewWxUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewWxUserGreaterThan(Integer value) {
            addCriterion("daily_new_wx_user >", value, "dailyNewWxUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewWxUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("daily_new_wx_user >=", value, "dailyNewWxUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewWxUserLessThan(Integer value) {
            addCriterion("daily_new_wx_user <", value, "dailyNewWxUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewWxUserLessThanOrEqualTo(Integer value) {
            addCriterion("daily_new_wx_user <=", value, "dailyNewWxUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewWxUserIn(List<Integer> values) {
            addCriterion("daily_new_wx_user in", values, "dailyNewWxUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewWxUserNotIn(List<Integer> values) {
            addCriterion("daily_new_wx_user not in", values, "dailyNewWxUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewWxUserBetween(Integer value1, Integer value2) {
            addCriterion("daily_new_wx_user between", value1, value2, "dailyNewWxUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewWxUserNotBetween(Integer value1, Integer value2) {
            addCriterion("daily_new_wx_user not between", value1, value2, "dailyNewWxUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestTimesIsNull() {
            addCriterion("daily_new_user_invest_times is null");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestTimesIsNotNull() {
            addCriterion("daily_new_user_invest_times is not null");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestTimesEqualTo(Integer value) {
            addCriterion("daily_new_user_invest_times =", value, "dailyNewUserInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestTimesNotEqualTo(Integer value) {
            addCriterion("daily_new_user_invest_times <>", value, "dailyNewUserInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestTimesGreaterThan(Integer value) {
            addCriterion("daily_new_user_invest_times >", value, "dailyNewUserInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("daily_new_user_invest_times >=", value, "dailyNewUserInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestTimesLessThan(Integer value) {
            addCriterion("daily_new_user_invest_times <", value, "dailyNewUserInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestTimesLessThanOrEqualTo(Integer value) {
            addCriterion("daily_new_user_invest_times <=", value, "dailyNewUserInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestTimesIn(List<Integer> values) {
            addCriterion("daily_new_user_invest_times in", values, "dailyNewUserInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestTimesNotIn(List<Integer> values) {
            addCriterion("daily_new_user_invest_times not in", values, "dailyNewUserInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestTimesBetween(Integer value1, Integer value2) {
            addCriterion("daily_new_user_invest_times between", value1, value2, "dailyNewUserInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("daily_new_user_invest_times not between", value1, value2, "dailyNewUserInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestTimesIsNull() {
            addCriterion("daily_old_user_invest_times is null");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestTimesIsNotNull() {
            addCriterion("daily_old_user_invest_times is not null");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestTimesEqualTo(Integer value) {
            addCriterion("daily_old_user_invest_times =", value, "dailyOldUserInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestTimesNotEqualTo(Integer value) {
            addCriterion("daily_old_user_invest_times <>", value, "dailyOldUserInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestTimesGreaterThan(Integer value) {
            addCriterion("daily_old_user_invest_times >", value, "dailyOldUserInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("daily_old_user_invest_times >=", value, "dailyOldUserInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestTimesLessThan(Integer value) {
            addCriterion("daily_old_user_invest_times <", value, "dailyOldUserInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestTimesLessThanOrEqualTo(Integer value) {
            addCriterion("daily_old_user_invest_times <=", value, "dailyOldUserInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestTimesIn(List<Integer> values) {
            addCriterion("daily_old_user_invest_times in", values, "dailyOldUserInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestTimesNotIn(List<Integer> values) {
            addCriterion("daily_old_user_invest_times not in", values, "dailyOldUserInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestTimesBetween(Integer value1, Integer value2) {
            addCriterion("daily_old_user_invest_times between", value1, value2, "dailyOldUserInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("daily_old_user_invest_times not between", value1, value2, "dailyOldUserInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestTimesIsNull() {
            addCriterion("daily_total_invest_times is null");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestTimesIsNotNull() {
            addCriterion("daily_total_invest_times is not null");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestTimesEqualTo(Integer value) {
            addCriterion("daily_total_invest_times =", value, "dailyTotalInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestTimesNotEqualTo(Integer value) {
            addCriterion("daily_total_invest_times <>", value, "dailyTotalInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestTimesGreaterThan(Integer value) {
            addCriterion("daily_total_invest_times >", value, "dailyTotalInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("daily_total_invest_times >=", value, "dailyTotalInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestTimesLessThan(Integer value) {
            addCriterion("daily_total_invest_times <", value, "dailyTotalInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestTimesLessThanOrEqualTo(Integer value) {
            addCriterion("daily_total_invest_times <=", value, "dailyTotalInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestTimesIn(List<Integer> values) {
            addCriterion("daily_total_invest_times in", values, "dailyTotalInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestTimesNotIn(List<Integer> values) {
            addCriterion("daily_total_invest_times not in", values, "dailyTotalInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestTimesBetween(Integer value1, Integer value2) {
            addCriterion("daily_total_invest_times between", value1, value2, "dailyTotalInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("daily_total_invest_times not between", value1, value2, "dailyTotalInvestTimes");
            return (Criteria) this;
        }

        public Criteria andDailyNewRegistUserIsNull() {
            addCriterion("daily_new_regist_user is null");
            return (Criteria) this;
        }

        public Criteria andDailyNewRegistUserIsNotNull() {
            addCriterion("daily_new_regist_user is not null");
            return (Criteria) this;
        }

        public Criteria andDailyNewRegistUserEqualTo(Integer value) {
            addCriterion("daily_new_regist_user =", value, "dailyNewRegistUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewRegistUserNotEqualTo(Integer value) {
            addCriterion("daily_new_regist_user <>", value, "dailyNewRegistUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewRegistUserGreaterThan(Integer value) {
            addCriterion("daily_new_regist_user >", value, "dailyNewRegistUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewRegistUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("daily_new_regist_user >=", value, "dailyNewRegistUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewRegistUserLessThan(Integer value) {
            addCriterion("daily_new_regist_user <", value, "dailyNewRegistUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewRegistUserLessThanOrEqualTo(Integer value) {
            addCriterion("daily_new_regist_user <=", value, "dailyNewRegistUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewRegistUserIn(List<Integer> values) {
            addCriterion("daily_new_regist_user in", values, "dailyNewRegistUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewRegistUserNotIn(List<Integer> values) {
            addCriterion("daily_new_regist_user not in", values, "dailyNewRegistUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewRegistUserBetween(Integer value1, Integer value2) {
            addCriterion("daily_new_regist_user between", value1, value2, "dailyNewRegistUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewRegistUserNotBetween(Integer value1, Integer value2) {
            addCriterion("daily_new_regist_user not between", value1, value2, "dailyNewRegistUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAmountIsNull() {
            addCriterion("daily_new_user_invest_amount is null");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAmountIsNotNull() {
            addCriterion("daily_new_user_invest_amount is not null");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAmountEqualTo(Double value) {
            addCriterion("daily_new_user_invest_amount =", value, "dailyNewUserInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAmountNotEqualTo(Double value) {
            addCriterion("daily_new_user_invest_amount <>", value, "dailyNewUserInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAmountGreaterThan(Double value) {
            addCriterion("daily_new_user_invest_amount >", value, "dailyNewUserInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("daily_new_user_invest_amount >=", value, "dailyNewUserInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAmountLessThan(Double value) {
            addCriterion("daily_new_user_invest_amount <", value, "dailyNewUserInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAmountLessThanOrEqualTo(Double value) {
            addCriterion("daily_new_user_invest_amount <=", value, "dailyNewUserInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAmountIn(List<Double> values) {
            addCriterion("daily_new_user_invest_amount in", values, "dailyNewUserInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAmountNotIn(List<Double> values) {
            addCriterion("daily_new_user_invest_amount not in", values, "dailyNewUserInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAmountBetween(Double value1, Double value2) {
            addCriterion("daily_new_user_invest_amount between", value1, value2, "dailyNewUserInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAmountNotBetween(Double value1, Double value2) {
            addCriterion("daily_new_user_invest_amount not between", value1, value2, "dailyNewUserInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestAmountIsNull() {
            addCriterion("daily_old_user_invest_amount is null");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestAmountIsNotNull() {
            addCriterion("daily_old_user_invest_amount is not null");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestAmountEqualTo(Double value) {
            addCriterion("daily_old_user_invest_amount =", value, "dailyOldUserInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestAmountNotEqualTo(Double value) {
            addCriterion("daily_old_user_invest_amount <>", value, "dailyOldUserInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestAmountGreaterThan(Double value) {
            addCriterion("daily_old_user_invest_amount >", value, "dailyOldUserInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("daily_old_user_invest_amount >=", value, "dailyOldUserInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestAmountLessThan(Double value) {
            addCriterion("daily_old_user_invest_amount <", value, "dailyOldUserInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestAmountLessThanOrEqualTo(Double value) {
            addCriterion("daily_old_user_invest_amount <=", value, "dailyOldUserInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestAmountIn(List<Double> values) {
            addCriterion("daily_old_user_invest_amount in", values, "dailyOldUserInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestAmountNotIn(List<Double> values) {
            addCriterion("daily_old_user_invest_amount not in", values, "dailyOldUserInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestAmountBetween(Double value1, Double value2) {
            addCriterion("daily_old_user_invest_amount between", value1, value2, "dailyOldUserInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyOldUserInvestAmountNotBetween(Double value1, Double value2) {
            addCriterion("daily_old_user_invest_amount not between", value1, value2, "dailyOldUserInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestAmountIsNull() {
            addCriterion("daily_total_invest_amount is null");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestAmountIsNotNull() {
            addCriterion("daily_total_invest_amount is not null");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestAmountEqualTo(Double value) {
            addCriterion("daily_total_invest_amount =", value, "dailyTotalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestAmountNotEqualTo(Double value) {
            addCriterion("daily_total_invest_amount <>", value, "dailyTotalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestAmountGreaterThan(Double value) {
            addCriterion("daily_total_invest_amount >", value, "dailyTotalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("daily_total_invest_amount >=", value, "dailyTotalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestAmountLessThan(Double value) {
            addCriterion("daily_total_invest_amount <", value, "dailyTotalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestAmountLessThanOrEqualTo(Double value) {
            addCriterion("daily_total_invest_amount <=", value, "dailyTotalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestAmountIn(List<Double> values) {
            addCriterion("daily_total_invest_amount in", values, "dailyTotalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestAmountNotIn(List<Double> values) {
            addCriterion("daily_total_invest_amount not in", values, "dailyTotalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestAmountBetween(Double value1, Double value2) {
            addCriterion("daily_total_invest_amount between", value1, value2, "dailyTotalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyTotalInvestAmountNotBetween(Double value1, Double value2) {
            addCriterion("daily_total_invest_amount not between", value1, value2, "dailyTotalInvestAmount");
            return (Criteria) this;
        }

        public Criteria andDailyNewBindUserIsNull() {
            addCriterion("daily_new_bind_user is null");
            return (Criteria) this;
        }

        public Criteria andDailyNewBindUserIsNotNull() {
            addCriterion("daily_new_bind_user is not null");
            return (Criteria) this;
        }

        public Criteria andDailyNewBindUserEqualTo(Integer value) {
            addCriterion("daily_new_bind_user =", value, "dailyNewBindUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewBindUserNotEqualTo(Integer value) {
            addCriterion("daily_new_bind_user <>", value, "dailyNewBindUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewBindUserGreaterThan(Integer value) {
            addCriterion("daily_new_bind_user >", value, "dailyNewBindUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewBindUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("daily_new_bind_user >=", value, "dailyNewBindUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewBindUserLessThan(Integer value) {
            addCriterion("daily_new_bind_user <", value, "dailyNewBindUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewBindUserLessThanOrEqualTo(Integer value) {
            addCriterion("daily_new_bind_user <=", value, "dailyNewBindUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewBindUserIn(List<Integer> values) {
            addCriterion("daily_new_bind_user in", values, "dailyNewBindUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewBindUserNotIn(List<Integer> values) {
            addCriterion("daily_new_bind_user not in", values, "dailyNewBindUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewBindUserBetween(Integer value1, Integer value2) {
            addCriterion("daily_new_bind_user between", value1, value2, "dailyNewBindUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewBindUserNotBetween(Integer value1, Integer value2) {
            addCriterion("daily_new_bind_user not between", value1, value2, "dailyNewBindUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewInvestUserIsNull() {
            addCriterion("daily_new_invest_user is null");
            return (Criteria) this;
        }

        public Criteria andDailyNewInvestUserIsNotNull() {
            addCriterion("daily_new_invest_user is not null");
            return (Criteria) this;
        }

        public Criteria andDailyNewInvestUserEqualTo(Integer value) {
            addCriterion("daily_new_invest_user =", value, "dailyNewInvestUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewInvestUserNotEqualTo(Integer value) {
            addCriterion("daily_new_invest_user <>", value, "dailyNewInvestUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewInvestUserGreaterThan(Integer value) {
            addCriterion("daily_new_invest_user >", value, "dailyNewInvestUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewInvestUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("daily_new_invest_user >=", value, "dailyNewInvestUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewInvestUserLessThan(Integer value) {
            addCriterion("daily_new_invest_user <", value, "dailyNewInvestUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewInvestUserLessThanOrEqualTo(Integer value) {
            addCriterion("daily_new_invest_user <=", value, "dailyNewInvestUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewInvestUserIn(List<Integer> values) {
            addCriterion("daily_new_invest_user in", values, "dailyNewInvestUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewInvestUserNotIn(List<Integer> values) {
            addCriterion("daily_new_invest_user not in", values, "dailyNewInvestUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewInvestUserBetween(Integer value1, Integer value2) {
            addCriterion("daily_new_invest_user between", value1, value2, "dailyNewInvestUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewInvestUserNotBetween(Integer value1, Integer value2) {
            addCriterion("daily_new_invest_user not between", value1, value2, "dailyNewInvestUser");
            return (Criteria) this;
        }

        public Criteria andDailyInvestUserIsNull() {
            addCriterion("daily_invest_user is null");
            return (Criteria) this;
        }

        public Criteria andDailyInvestUserIsNotNull() {
            addCriterion("daily_invest_user is not null");
            return (Criteria) this;
        }

        public Criteria andDailyInvestUserEqualTo(Integer value) {
            addCriterion("daily_invest_user =", value, "dailyInvestUser");
            return (Criteria) this;
        }

        public Criteria andDailyInvestUserNotEqualTo(Integer value) {
            addCriterion("daily_invest_user <>", value, "dailyInvestUser");
            return (Criteria) this;
        }

        public Criteria andDailyInvestUserGreaterThan(Integer value) {
            addCriterion("daily_invest_user >", value, "dailyInvestUser");
            return (Criteria) this;
        }

        public Criteria andDailyInvestUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("daily_invest_user >=", value, "dailyInvestUser");
            return (Criteria) this;
        }

        public Criteria andDailyInvestUserLessThan(Integer value) {
            addCriterion("daily_invest_user <", value, "dailyInvestUser");
            return (Criteria) this;
        }

        public Criteria andDailyInvestUserLessThanOrEqualTo(Integer value) {
            addCriterion("daily_invest_user <=", value, "dailyInvestUser");
            return (Criteria) this;
        }

        public Criteria andDailyInvestUserIn(List<Integer> values) {
            addCriterion("daily_invest_user in", values, "dailyInvestUser");
            return (Criteria) this;
        }

        public Criteria andDailyInvestUserNotIn(List<Integer> values) {
            addCriterion("daily_invest_user not in", values, "dailyInvestUser");
            return (Criteria) this;
        }

        public Criteria andDailyInvestUserBetween(Integer value1, Integer value2) {
            addCriterion("daily_invest_user between", value1, value2, "dailyInvestUser");
            return (Criteria) this;
        }

        public Criteria andDailyInvestUserNotBetween(Integer value1, Integer value2) {
            addCriterion("daily_invest_user not between", value1, value2, "dailyInvestUser");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAnnualIsNull() {
            addCriterion("daily_new_user_invest_annual is null");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAnnualIsNotNull() {
            addCriterion("daily_new_user_invest_annual is not null");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAnnualEqualTo(Double value) {
            addCriterion("daily_new_user_invest_annual =", value, "dailyNewUserInvestAnnual");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAnnualNotEqualTo(Double value) {
            addCriterion("daily_new_user_invest_annual <>", value, "dailyNewUserInvestAnnual");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAnnualGreaterThan(Double value) {
            addCriterion("daily_new_user_invest_annual >", value, "dailyNewUserInvestAnnual");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAnnualGreaterThanOrEqualTo(Double value) {
            addCriterion("daily_new_user_invest_annual >=", value, "dailyNewUserInvestAnnual");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAnnualLessThan(Double value) {
            addCriterion("daily_new_user_invest_annual <", value, "dailyNewUserInvestAnnual");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAnnualLessThanOrEqualTo(Double value) {
            addCriterion("daily_new_user_invest_annual <=", value, "dailyNewUserInvestAnnual");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAnnualIn(List<Double> values) {
            addCriterion("daily_new_user_invest_annual in", values, "dailyNewUserInvestAnnual");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAnnualNotIn(List<Double> values) {
            addCriterion("daily_new_user_invest_annual not in", values, "dailyNewUserInvestAnnual");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAnnualBetween(Double value1, Double value2) {
            addCriterion("daily_new_user_invest_annual between", value1, value2, "dailyNewUserInvestAnnual");
            return (Criteria) this;
        }

        public Criteria andDailyNewUserInvestAnnualNotBetween(Double value1, Double value2) {
            addCriterion("daily_new_user_invest_annual not between", value1, value2, "dailyNewUserInvestAnnual");
            return (Criteria) this;
        }

        public Criteria andDailyInvestAnnualIsNull() {
            addCriterion("daily_invest_annual is null");
            return (Criteria) this;
        }

        public Criteria andDailyInvestAnnualIsNotNull() {
            addCriterion("daily_invest_annual is not null");
            return (Criteria) this;
        }

        public Criteria andDailyInvestAnnualEqualTo(Double value) {
            addCriterion("daily_invest_annual =", value, "dailyInvestAnnual");
            return (Criteria) this;
        }

        public Criteria andDailyInvestAnnualNotEqualTo(Double value) {
            addCriterion("daily_invest_annual <>", value, "dailyInvestAnnual");
            return (Criteria) this;
        }

        public Criteria andDailyInvestAnnualGreaterThan(Double value) {
            addCriterion("daily_invest_annual >", value, "dailyInvestAnnual");
            return (Criteria) this;
        }

        public Criteria andDailyInvestAnnualGreaterThanOrEqualTo(Double value) {
            addCriterion("daily_invest_annual >=", value, "dailyInvestAnnual");
            return (Criteria) this;
        }

        public Criteria andDailyInvestAnnualLessThan(Double value) {
            addCriterion("daily_invest_annual <", value, "dailyInvestAnnual");
            return (Criteria) this;
        }

        public Criteria andDailyInvestAnnualLessThanOrEqualTo(Double value) {
            addCriterion("daily_invest_annual <=", value, "dailyInvestAnnual");
            return (Criteria) this;
        }

        public Criteria andDailyInvestAnnualIn(List<Double> values) {
            addCriterion("daily_invest_annual in", values, "dailyInvestAnnual");
            return (Criteria) this;
        }

        public Criteria andDailyInvestAnnualNotIn(List<Double> values) {
            addCriterion("daily_invest_annual not in", values, "dailyInvestAnnual");
            return (Criteria) this;
        }

        public Criteria andDailyInvestAnnualBetween(Double value1, Double value2) {
            addCriterion("daily_invest_annual between", value1, value2, "dailyInvestAnnual");
            return (Criteria) this;
        }

        public Criteria andDailyInvestAnnualNotBetween(Double value1, Double value2) {
            addCriterion("daily_invest_annual not between", value1, value2, "dailyInvestAnnual");
            return (Criteria) this;
        }

        public Criteria andStatDateIsNull() {
            addCriterion("stat_date is null");
            return (Criteria) this;
        }

        public Criteria andStatDateIsNotNull() {
            addCriterion("stat_date is not null");
            return (Criteria) this;
        }

        public Criteria andStatDateEqualTo(Date value) {
            addCriterionForJDBCDate("stat_date =", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("stat_date <>", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateGreaterThan(Date value) {
            addCriterionForJDBCDate("stat_date >", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("stat_date >=", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateLessThan(Date value) {
            addCriterionForJDBCDate("stat_date <", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("stat_date <=", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateIn(List<Date> values) {
            addCriterionForJDBCDate("stat_date in", values, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("stat_date not in", values, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("stat_date between", value1, value2, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("stat_date not between", value1, value2, "statDate");
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