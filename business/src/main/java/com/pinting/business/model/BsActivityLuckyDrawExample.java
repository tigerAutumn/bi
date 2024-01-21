package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsActivityLuckyDrawExample {

	protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsActivityLuckyDrawExample() {
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

        public Criteria andActivityIdIsNull() {
            addCriterion("activity_id is null");
            return (Criteria) this;
        }

        public Criteria andActivityIdIsNotNull() {
            addCriterion("activity_id is not null");
            return (Criteria) this;
        }

        public Criteria andActivityIdEqualTo(Integer value) {
            addCriterion("activity_id =", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdNotEqualTo(Integer value) {
            addCriterion("activity_id <>", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdGreaterThan(Integer value) {
            addCriterion("activity_id >", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("activity_id >=", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdLessThan(Integer value) {
            addCriterion("activity_id <", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdLessThanOrEqualTo(Integer value) {
            addCriterion("activity_id <=", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdIn(List<Integer> values) {
            addCriterion("activity_id in", values, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdNotIn(List<Integer> values) {
            addCriterion("activity_id not in", values, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdBetween(Integer value1, Integer value2) {
            addCriterion("activity_id between", value1, value2, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdNotBetween(Integer value1, Integer value2) {
            addCriterion("activity_id not between", value1, value2, "activityId");
            return (Criteria) this;
        }

        public Criteria andAwardIdIsNull() {
            addCriterion("award_id is null");
            return (Criteria) this;
        }

        public Criteria andAwardIdIsNotNull() {
            addCriterion("award_id is not null");
            return (Criteria) this;
        }

        public Criteria andAwardIdEqualTo(Integer value) {
            addCriterion("award_id =", value, "awardId");
            return (Criteria) this;
        }

        public Criteria andAwardIdNotEqualTo(Integer value) {
            addCriterion("award_id <>", value, "awardId");
            return (Criteria) this;
        }

        public Criteria andAwardIdGreaterThan(Integer value) {
            addCriterion("award_id >", value, "awardId");
            return (Criteria) this;
        }

        public Criteria andAwardIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("award_id >=", value, "awardId");
            return (Criteria) this;
        }

        public Criteria andAwardIdLessThan(Integer value) {
            addCriterion("award_id <", value, "awardId");
            return (Criteria) this;
        }

        public Criteria andAwardIdLessThanOrEqualTo(Integer value) {
            addCriterion("award_id <=", value, "awardId");
            return (Criteria) this;
        }

        public Criteria andAwardIdIn(List<Integer> values) {
            addCriterion("award_id in", values, "awardId");
            return (Criteria) this;
        }

        public Criteria andAwardIdNotIn(List<Integer> values) {
            addCriterion("award_id not in", values, "awardId");
            return (Criteria) this;
        }

        public Criteria andAwardIdBetween(Integer value1, Integer value2) {
            addCriterion("award_id between", value1, value2, "awardId");
            return (Criteria) this;
        }

        public Criteria andAwardIdNotBetween(Integer value1, Integer value2) {
            addCriterion("award_id not between", value1, value2, "awardId");
            return (Criteria) this;
        }

        public Criteria andIsBackDrawIsNull() {
            addCriterion("is_back_draw is null");
            return (Criteria) this;
        }

        public Criteria andIsBackDrawIsNotNull() {
            addCriterion("is_back_draw is not null");
            return (Criteria) this;
        }

        public Criteria andIsBackDrawEqualTo(String value) {
            addCriterion("is_back_draw =", value, "isBackDraw");
            return (Criteria) this;
        }

        public Criteria andIsBackDrawNotEqualTo(String value) {
            addCriterion("is_back_draw <>", value, "isBackDraw");
            return (Criteria) this;
        }

        public Criteria andIsBackDrawGreaterThan(String value) {
            addCriterion("is_back_draw >", value, "isBackDraw");
            return (Criteria) this;
        }

        public Criteria andIsBackDrawGreaterThanOrEqualTo(String value) {
            addCriterion("is_back_draw >=", value, "isBackDraw");
            return (Criteria) this;
        }

        public Criteria andIsBackDrawLessThan(String value) {
            addCriterion("is_back_draw <", value, "isBackDraw");
            return (Criteria) this;
        }

        public Criteria andIsBackDrawLessThanOrEqualTo(String value) {
            addCriterion("is_back_draw <=", value, "isBackDraw");
            return (Criteria) this;
        }

        public Criteria andIsBackDrawLike(String value) {
            addCriterion("is_back_draw like", value, "isBackDraw");
            return (Criteria) this;
        }

        public Criteria andIsBackDrawNotLike(String value) {
            addCriterion("is_back_draw not like", value, "isBackDraw");
            return (Criteria) this;
        }

        public Criteria andIsBackDrawIn(List<String> values) {
            addCriterion("is_back_draw in", values, "isBackDraw");
            return (Criteria) this;
        }

        public Criteria andIsBackDrawNotIn(List<String> values) {
            addCriterion("is_back_draw not in", values, "isBackDraw");
            return (Criteria) this;
        }

        public Criteria andIsBackDrawBetween(String value1, String value2) {
            addCriterion("is_back_draw between", value1, value2, "isBackDraw");
            return (Criteria) this;
        }

        public Criteria andIsBackDrawNotBetween(String value1, String value2) {
            addCriterion("is_back_draw not between", value1, value2, "isBackDraw");
            return (Criteria) this;
        }

        public Criteria andBackDrawTimeIsNull() {
            addCriterion("back_draw_time is null");
            return (Criteria) this;
        }

        public Criteria andBackDrawTimeIsNotNull() {
            addCriterion("back_draw_time is not null");
            return (Criteria) this;
        }

        public Criteria andBackDrawTimeEqualTo(Date value) {
            addCriterion("back_draw_time =", value, "backDrawTime");
            return (Criteria) this;
        }

        public Criteria andBackDrawTimeNotEqualTo(Date value) {
            addCriterion("back_draw_time <>", value, "backDrawTime");
            return (Criteria) this;
        }

        public Criteria andBackDrawTimeGreaterThan(Date value) {
            addCriterion("back_draw_time >", value, "backDrawTime");
            return (Criteria) this;
        }

        public Criteria andBackDrawTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("back_draw_time >=", value, "backDrawTime");
            return (Criteria) this;
        }

        public Criteria andBackDrawTimeLessThan(Date value) {
            addCriterion("back_draw_time <", value, "backDrawTime");
            return (Criteria) this;
        }

        public Criteria andBackDrawTimeLessThanOrEqualTo(Date value) {
            addCriterion("back_draw_time <=", value, "backDrawTime");
            return (Criteria) this;
        }

        public Criteria andBackDrawTimeIn(List<Date> values) {
            addCriterion("back_draw_time in", values, "backDrawTime");
            return (Criteria) this;
        }

        public Criteria andBackDrawTimeNotIn(List<Date> values) {
            addCriterion("back_draw_time not in", values, "backDrawTime");
            return (Criteria) this;
        }

        public Criteria andBackDrawTimeBetween(Date value1, Date value2) {
            addCriterion("back_draw_time between", value1, value2, "backDrawTime");
            return (Criteria) this;
        }

        public Criteria andBackDrawTimeNotBetween(Date value1, Date value2) {
            addCriterion("back_draw_time not between", value1, value2, "backDrawTime");
            return (Criteria) this;
        }

        public Criteria andIsUserDrawIsNull() {
            addCriterion("is_user_draw is null");
            return (Criteria) this;
        }

        public Criteria andIsUserDrawIsNotNull() {
            addCriterion("is_user_draw is not null");
            return (Criteria) this;
        }

        public Criteria andIsUserDrawEqualTo(String value) {
            addCriterion("is_user_draw =", value, "isUserDraw");
            return (Criteria) this;
        }

        public Criteria andIsUserDrawNotEqualTo(String value) {
            addCriterion("is_user_draw <>", value, "isUserDraw");
            return (Criteria) this;
        }

        public Criteria andIsUserDrawGreaterThan(String value) {
            addCriterion("is_user_draw >", value, "isUserDraw");
            return (Criteria) this;
        }

        public Criteria andIsUserDrawGreaterThanOrEqualTo(String value) {
            addCriterion("is_user_draw >=", value, "isUserDraw");
            return (Criteria) this;
        }

        public Criteria andIsUserDrawLessThan(String value) {
            addCriterion("is_user_draw <", value, "isUserDraw");
            return (Criteria) this;
        }

        public Criteria andIsUserDrawLessThanOrEqualTo(String value) {
            addCriterion("is_user_draw <=", value, "isUserDraw");
            return (Criteria) this;
        }

        public Criteria andIsUserDrawLike(String value) {
            addCriterion("is_user_draw like", value, "isUserDraw");
            return (Criteria) this;
        }

        public Criteria andIsUserDrawNotLike(String value) {
            addCriterion("is_user_draw not like", value, "isUserDraw");
            return (Criteria) this;
        }

        public Criteria andIsUserDrawIn(List<String> values) {
            addCriterion("is_user_draw in", values, "isUserDraw");
            return (Criteria) this;
        }

        public Criteria andIsUserDrawNotIn(List<String> values) {
            addCriterion("is_user_draw not in", values, "isUserDraw");
            return (Criteria) this;
        }

        public Criteria andIsUserDrawBetween(String value1, String value2) {
            addCriterion("is_user_draw between", value1, value2, "isUserDraw");
            return (Criteria) this;
        }

        public Criteria andIsUserDrawNotBetween(String value1, String value2) {
            addCriterion("is_user_draw not between", value1, value2, "isUserDraw");
            return (Criteria) this;
        }

        public Criteria andUserDrawTimeIsNull() {
            addCriterion("user_draw_time is null");
            return (Criteria) this;
        }

        public Criteria andUserDrawTimeIsNotNull() {
            addCriterion("user_draw_time is not null");
            return (Criteria) this;
        }

        public Criteria andUserDrawTimeEqualTo(Date value) {
            addCriterion("user_draw_time =", value, "userDrawTime");
            return (Criteria) this;
        }

        public Criteria andUserDrawTimeNotEqualTo(Date value) {
            addCriterion("user_draw_time <>", value, "userDrawTime");
            return (Criteria) this;
        }

        public Criteria andUserDrawTimeGreaterThan(Date value) {
            addCriterion("user_draw_time >", value, "userDrawTime");
            return (Criteria) this;
        }

        public Criteria andUserDrawTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("user_draw_time >=", value, "userDrawTime");
            return (Criteria) this;
        }

        public Criteria andUserDrawTimeLessThan(Date value) {
            addCriterion("user_draw_time <", value, "userDrawTime");
            return (Criteria) this;
        }

        public Criteria andUserDrawTimeLessThanOrEqualTo(Date value) {
            addCriterion("user_draw_time <=", value, "userDrawTime");
            return (Criteria) this;
        }

        public Criteria andUserDrawTimeIn(List<Date> values) {
            addCriterion("user_draw_time in", values, "userDrawTime");
            return (Criteria) this;
        }

        public Criteria andUserDrawTimeNotIn(List<Date> values) {
            addCriterion("user_draw_time not in", values, "userDrawTime");
            return (Criteria) this;
        }

        public Criteria andUserDrawTimeBetween(Date value1, Date value2) {
            addCriterion("user_draw_time between", value1, value2, "userDrawTime");
            return (Criteria) this;
        }

        public Criteria andUserDrawTimeNotBetween(Date value1, Date value2) {
            addCriterion("user_draw_time not between", value1, value2, "userDrawTime");
            return (Criteria) this;
        }

        public Criteria andIsWinIsNull() {
            addCriterion("is_win is null");
            return (Criteria) this;
        }

        public Criteria andIsWinIsNotNull() {
            addCriterion("is_win is not null");
            return (Criteria) this;
        }

        public Criteria andIsWinEqualTo(String value) {
            addCriterion("is_win =", value, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsWinNotEqualTo(String value) {
            addCriterion("is_win <>", value, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsWinGreaterThan(String value) {
            addCriterion("is_win >", value, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsWinGreaterThanOrEqualTo(String value) {
            addCriterion("is_win >=", value, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsWinLessThan(String value) {
            addCriterion("is_win <", value, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsWinLessThanOrEqualTo(String value) {
            addCriterion("is_win <=", value, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsWinLike(String value) {
            addCriterion("is_win like", value, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsWinNotLike(String value) {
            addCriterion("is_win not like", value, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsWinIn(List<String> values) {
            addCriterion("is_win in", values, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsWinNotIn(List<String> values) {
            addCriterion("is_win not in", values, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsWinBetween(String value1, String value2) {
            addCriterion("is_win between", value1, value2, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsWinNotBetween(String value1, String value2) {
            addCriterion("is_win not between", value1, value2, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsConfirmIsNull() {
            addCriterion("is_confirm is null");
            return (Criteria) this;
        }

        public Criteria andIsConfirmIsNotNull() {
            addCriterion("is_confirm is not null");
            return (Criteria) this;
        }

        public Criteria andIsConfirmEqualTo(String value) {
            addCriterion("is_confirm =", value, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andIsConfirmNotEqualTo(String value) {
            addCriterion("is_confirm <>", value, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andIsConfirmGreaterThan(String value) {
            addCriterion("is_confirm >", value, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andIsConfirmGreaterThanOrEqualTo(String value) {
            addCriterion("is_confirm >=", value, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andIsConfirmLessThan(String value) {
            addCriterion("is_confirm <", value, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andIsConfirmLessThanOrEqualTo(String value) {
            addCriterion("is_confirm <=", value, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andIsConfirmLike(String value) {
            addCriterion("is_confirm like", value, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andIsConfirmNotLike(String value) {
            addCriterion("is_confirm not like", value, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andIsConfirmIn(List<String> values) {
            addCriterion("is_confirm in", values, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andIsConfirmNotIn(List<String> values) {
            addCriterion("is_confirm not in", values, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andIsConfirmBetween(String value1, String value2) {
            addCriterion("is_confirm between", value1, value2, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andIsConfirmNotBetween(String value1, String value2) {
            addCriterion("is_confirm not between", value1, value2, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeIsNull() {
            addCriterion("confirm_time is null");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeIsNotNull() {
            addCriterion("confirm_time is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeEqualTo(Date value) {
            addCriterion("confirm_time =", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeNotEqualTo(Date value) {
            addCriterion("confirm_time <>", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeGreaterThan(Date value) {
            addCriterion("confirm_time >", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("confirm_time >=", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeLessThan(Date value) {
            addCriterion("confirm_time <", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeLessThanOrEqualTo(Date value) {
            addCriterion("confirm_time <=", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeIn(List<Date> values) {
            addCriterion("confirm_time in", values, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeNotIn(List<Date> values) {
            addCriterion("confirm_time not in", values, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeBetween(Date value1, Date value2) {
            addCriterion("confirm_time between", value1, value2, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeNotBetween(Date value1, Date value2) {
            addCriterion("confirm_time not between", value1, value2, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andIsAutoConfirmIsNull() {
            addCriterion("is_auto_confirm is null");
            return (Criteria) this;
        }

        public Criteria andIsAutoConfirmIsNotNull() {
            addCriterion("is_auto_confirm is not null");
            return (Criteria) this;
        }

        public Criteria andIsAutoConfirmEqualTo(String value) {
            addCriterion("is_auto_confirm =", value, "isAutoConfirm");
            return (Criteria) this;
        }

        public Criteria andIsAutoConfirmNotEqualTo(String value) {
            addCriterion("is_auto_confirm <>", value, "isAutoConfirm");
            return (Criteria) this;
        }

        public Criteria andIsAutoConfirmGreaterThan(String value) {
            addCriterion("is_auto_confirm >", value, "isAutoConfirm");
            return (Criteria) this;
        }

        public Criteria andIsAutoConfirmGreaterThanOrEqualTo(String value) {
            addCriterion("is_auto_confirm >=", value, "isAutoConfirm");
            return (Criteria) this;
        }

        public Criteria andIsAutoConfirmLessThan(String value) {
            addCriterion("is_auto_confirm <", value, "isAutoConfirm");
            return (Criteria) this;
        }

        public Criteria andIsAutoConfirmLessThanOrEqualTo(String value) {
            addCriterion("is_auto_confirm <=", value, "isAutoConfirm");
            return (Criteria) this;
        }

        public Criteria andIsAutoConfirmLike(String value) {
            addCriterion("is_auto_confirm like", value, "isAutoConfirm");
            return (Criteria) this;
        }

        public Criteria andIsAutoConfirmNotLike(String value) {
            addCriterion("is_auto_confirm not like", value, "isAutoConfirm");
            return (Criteria) this;
        }

        public Criteria andIsAutoConfirmIn(List<String> values) {
            addCriterion("is_auto_confirm in", values, "isAutoConfirm");
            return (Criteria) this;
        }

        public Criteria andIsAutoConfirmNotIn(List<String> values) {
            addCriterion("is_auto_confirm not in", values, "isAutoConfirm");
            return (Criteria) this;
        }

        public Criteria andIsAutoConfirmBetween(String value1, String value2) {
            addCriterion("is_auto_confirm between", value1, value2, "isAutoConfirm");
            return (Criteria) this;
        }

        public Criteria andIsAutoConfirmNotBetween(String value1, String value2) {
            addCriterion("is_auto_confirm not between", value1, value2, "isAutoConfirm");
            return (Criteria) this;
        }

        public Criteria andOpUserIdIsNull() {
            addCriterion("op_user_id is null");
            return (Criteria) this;
        }

        public Criteria andOpUserIdIsNotNull() {
            addCriterion("op_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andOpUserIdEqualTo(Integer value) {
            addCriterion("op_user_id =", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdNotEqualTo(Integer value) {
            addCriterion("op_user_id <>", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdGreaterThan(Integer value) {
            addCriterion("op_user_id >", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("op_user_id >=", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdLessThan(Integer value) {
            addCriterion("op_user_id <", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("op_user_id <=", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdIn(List<Integer> values) {
            addCriterion("op_user_id in", values, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdNotIn(List<Integer> values) {
            addCriterion("op_user_id not in", values, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdBetween(Integer value1, Integer value2) {
            addCriterion("op_user_id between", value1, value2, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("op_user_id not between", value1, value2, "opUserId");
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