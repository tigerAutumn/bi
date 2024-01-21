package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsInterestTicketInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsInterestTicketInfoExample() {
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

        public Criteria andSerialNoIsNull() {
            addCriterion("serial_no is null");
            return (Criteria) this;
        }

        public Criteria andSerialNoIsNotNull() {
            addCriterion("serial_no is not null");
            return (Criteria) this;
        }

        public Criteria andSerialNoEqualTo(String value) {
            addCriterion("serial_no =", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotEqualTo(String value) {
            addCriterion("serial_no <>", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThan(String value) {
            addCriterion("serial_no >", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThanOrEqualTo(String value) {
            addCriterion("serial_no >=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThan(String value) {
            addCriterion("serial_no <", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThanOrEqualTo(String value) {
            addCriterion("serial_no <=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLike(String value) {
            addCriterion("serial_no like", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotLike(String value) {
            addCriterion("serial_no not like", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoIn(List<String> values) {
            addCriterion("serial_no in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotIn(List<String> values) {
            addCriterion("serial_no not in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoBetween(String value1, String value2) {
            addCriterion("serial_no between", value1, value2, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotBetween(String value1, String value2) {
            addCriterion("serial_no not between", value1, value2, "serialNo");
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

        public Criteria andTicketAprIsNull() {
            addCriterion("ticket_apr is null");
            return (Criteria) this;
        }

        public Criteria andTicketAprIsNotNull() {
            addCriterion("ticket_apr is not null");
            return (Criteria) this;
        }

        public Criteria andTicketAprEqualTo(Double value) {
            addCriterion("ticket_apr =", value, "ticketApr");
            return (Criteria) this;
        }

        public Criteria andTicketAprNotEqualTo(Double value) {
            addCriterion("ticket_apr <>", value, "ticketApr");
            return (Criteria) this;
        }

        public Criteria andTicketAprGreaterThan(Double value) {
            addCriterion("ticket_apr >", value, "ticketApr");
            return (Criteria) this;
        }

        public Criteria andTicketAprGreaterThanOrEqualTo(Double value) {
            addCriterion("ticket_apr >=", value, "ticketApr");
            return (Criteria) this;
        }

        public Criteria andTicketAprLessThan(Double value) {
            addCriterion("ticket_apr <", value, "ticketApr");
            return (Criteria) this;
        }

        public Criteria andTicketAprLessThanOrEqualTo(Double value) {
            addCriterion("ticket_apr <=", value, "ticketApr");
            return (Criteria) this;
        }

        public Criteria andTicketAprIn(List<Double> values) {
            addCriterion("ticket_apr in", values, "ticketApr");
            return (Criteria) this;
        }

        public Criteria andTicketAprNotIn(List<Double> values) {
            addCriterion("ticket_apr not in", values, "ticketApr");
            return (Criteria) this;
        }

        public Criteria andTicketAprBetween(Double value1, Double value2) {
            addCriterion("ticket_apr between", value1, value2, "ticketApr");
            return (Criteria) this;
        }

        public Criteria andTicketAprNotBetween(Double value1, Double value2) {
            addCriterion("ticket_apr not between", value1, value2, "ticketApr");
            return (Criteria) this;
        }

        public Criteria andAuthAccountIdIsNull() {
            addCriterion("auth_account_id is null");
            return (Criteria) this;
        }

        public Criteria andAuthAccountIdIsNotNull() {
            addCriterion("auth_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andAuthAccountIdEqualTo(Integer value) {
            addCriterion("auth_account_id =", value, "authAccountId");
            return (Criteria) this;
        }

        public Criteria andAuthAccountIdNotEqualTo(Integer value) {
            addCriterion("auth_account_id <>", value, "authAccountId");
            return (Criteria) this;
        }

        public Criteria andAuthAccountIdGreaterThan(Integer value) {
            addCriterion("auth_account_id >", value, "authAccountId");
            return (Criteria) this;
        }

        public Criteria andAuthAccountIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("auth_account_id >=", value, "authAccountId");
            return (Criteria) this;
        }

        public Criteria andAuthAccountIdLessThan(Integer value) {
            addCriterion("auth_account_id <", value, "authAccountId");
            return (Criteria) this;
        }

        public Criteria andAuthAccountIdLessThanOrEqualTo(Integer value) {
            addCriterion("auth_account_id <=", value, "authAccountId");
            return (Criteria) this;
        }

        public Criteria andAuthAccountIdIn(List<Integer> values) {
            addCriterion("auth_account_id in", values, "authAccountId");
            return (Criteria) this;
        }

        public Criteria andAuthAccountIdNotIn(List<Integer> values) {
            addCriterion("auth_account_id not in", values, "authAccountId");
            return (Criteria) this;
        }

        public Criteria andAuthAccountIdBetween(Integer value1, Integer value2) {
            addCriterion("auth_account_id between", value1, value2, "authAccountId");
            return (Criteria) this;
        }

        public Criteria andAuthAccountIdNotBetween(Integer value1, Integer value2) {
            addCriterion("auth_account_id not between", value1, value2, "authAccountId");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andInterestAmountIsNull() {
            addCriterion("interest_amount is null");
            return (Criteria) this;
        }

        public Criteria andInterestAmountIsNotNull() {
            addCriterion("interest_amount is not null");
            return (Criteria) this;
        }

        public Criteria andInterestAmountEqualTo(Double value) {
            addCriterion("interest_amount =", value, "interestAmount");
            return (Criteria) this;
        }

        public Criteria andInterestAmountNotEqualTo(Double value) {
            addCriterion("interest_amount <>", value, "interestAmount");
            return (Criteria) this;
        }

        public Criteria andInterestAmountGreaterThan(Double value) {
            addCriterion("interest_amount >", value, "interestAmount");
            return (Criteria) this;
        }

        public Criteria andInterestAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("interest_amount >=", value, "interestAmount");
            return (Criteria) this;
        }

        public Criteria andInterestAmountLessThan(Double value) {
            addCriterion("interest_amount <", value, "interestAmount");
            return (Criteria) this;
        }

        public Criteria andInterestAmountLessThanOrEqualTo(Double value) {
            addCriterion("interest_amount <=", value, "interestAmount");
            return (Criteria) this;
        }

        public Criteria andInterestAmountIn(List<Double> values) {
            addCriterion("interest_amount in", values, "interestAmount");
            return (Criteria) this;
        }

        public Criteria andInterestAmountNotIn(List<Double> values) {
            addCriterion("interest_amount not in", values, "interestAmount");
            return (Criteria) this;
        }

        public Criteria andInterestAmountBetween(Double value1, Double value2) {
            addCriterion("interest_amount between", value1, value2, "interestAmount");
            return (Criteria) this;
        }

        public Criteria andInterestAmountNotBetween(Double value1, Double value2) {
            addCriterion("interest_amount not between", value1, value2, "interestAmount");
            return (Criteria) this;
        }

        public Criteria andUseTimeIsNull() {
            addCriterion("use_time is null");
            return (Criteria) this;
        }

        public Criteria andUseTimeIsNotNull() {
            addCriterion("use_time is not null");
            return (Criteria) this;
        }

        public Criteria andUseTimeEqualTo(Date value) {
            addCriterion("use_time =", value, "useTime");
            return (Criteria) this;
        }

        public Criteria andUseTimeNotEqualTo(Date value) {
            addCriterion("use_time <>", value, "useTime");
            return (Criteria) this;
        }

        public Criteria andUseTimeGreaterThan(Date value) {
            addCriterion("use_time >", value, "useTime");
            return (Criteria) this;
        }

        public Criteria andUseTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("use_time >=", value, "useTime");
            return (Criteria) this;
        }

        public Criteria andUseTimeLessThan(Date value) {
            addCriterion("use_time <", value, "useTime");
            return (Criteria) this;
        }

        public Criteria andUseTimeLessThanOrEqualTo(Date value) {
            addCriterion("use_time <=", value, "useTime");
            return (Criteria) this;
        }

        public Criteria andUseTimeIn(List<Date> values) {
            addCriterion("use_time in", values, "useTime");
            return (Criteria) this;
        }

        public Criteria andUseTimeNotIn(List<Date> values) {
            addCriterion("use_time not in", values, "useTime");
            return (Criteria) this;
        }

        public Criteria andUseTimeBetween(Date value1, Date value2) {
            addCriterion("use_time between", value1, value2, "useTime");
            return (Criteria) this;
        }

        public Criteria andUseTimeNotBetween(Date value1, Date value2) {
            addCriterion("use_time not between", value1, value2, "useTime");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartIsNull() {
            addCriterion("use_time_start is null");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartIsNotNull() {
            addCriterion("use_time_start is not null");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartEqualTo(Date value) {
            addCriterion("use_time_start =", value, "useTimeStart");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartNotEqualTo(Date value) {
            addCriterion("use_time_start <>", value, "useTimeStart");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartGreaterThan(Date value) {
            addCriterion("use_time_start >", value, "useTimeStart");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartGreaterThanOrEqualTo(Date value) {
            addCriterion("use_time_start >=", value, "useTimeStart");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartLessThan(Date value) {
            addCriterion("use_time_start <", value, "useTimeStart");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartLessThanOrEqualTo(Date value) {
            addCriterion("use_time_start <=", value, "useTimeStart");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartIn(List<Date> values) {
            addCriterion("use_time_start in", values, "useTimeStart");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartNotIn(List<Date> values) {
            addCriterion("use_time_start not in", values, "useTimeStart");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartBetween(Date value1, Date value2) {
            addCriterion("use_time_start between", value1, value2, "useTimeStart");
            return (Criteria) this;
        }

        public Criteria andUseTimeStartNotBetween(Date value1, Date value2) {
            addCriterion("use_time_start not between", value1, value2, "useTimeStart");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndIsNull() {
            addCriterion("use_time_end is null");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndIsNotNull() {
            addCriterion("use_time_end is not null");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndEqualTo(Date value) {
            addCriterion("use_time_end =", value, "useTimeEnd");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndNotEqualTo(Date value) {
            addCriterion("use_time_end <>", value, "useTimeEnd");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndGreaterThan(Date value) {
            addCriterion("use_time_end >", value, "useTimeEnd");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndGreaterThanOrEqualTo(Date value) {
            addCriterion("use_time_end >=", value, "useTimeEnd");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndLessThan(Date value) {
            addCriterion("use_time_end <", value, "useTimeEnd");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndLessThanOrEqualTo(Date value) {
            addCriterion("use_time_end <=", value, "useTimeEnd");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndIn(List<Date> values) {
            addCriterion("use_time_end in", values, "useTimeEnd");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndNotIn(List<Date> values) {
            addCriterion("use_time_end not in", values, "useTimeEnd");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndBetween(Date value1, Date value2) {
            addCriterion("use_time_end between", value1, value2, "useTimeEnd");
            return (Criteria) this;
        }

        public Criteria andUseTimeEndNotBetween(Date value1, Date value2) {
            addCriterion("use_time_end not between", value1, value2, "useTimeEnd");
            return (Criteria) this;
        }

        public Criteria andMsgStatusIsNull() {
            addCriterion("msg_status is null");
            return (Criteria) this;
        }

        public Criteria andMsgStatusIsNotNull() {
            addCriterion("msg_status is not null");
            return (Criteria) this;
        }

        public Criteria andMsgStatusEqualTo(String value) {
            addCriterion("msg_status =", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusNotEqualTo(String value) {
            addCriterion("msg_status <>", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusGreaterThan(String value) {
            addCriterion("msg_status >", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusGreaterThanOrEqualTo(String value) {
            addCriterion("msg_status >=", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusLessThan(String value) {
            addCriterion("msg_status <", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusLessThanOrEqualTo(String value) {
            addCriterion("msg_status <=", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusLike(String value) {
            addCriterion("msg_status like", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusNotLike(String value) {
            addCriterion("msg_status not like", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusIn(List<String> values) {
            addCriterion("msg_status in", values, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusNotIn(List<String> values) {
            addCriterion("msg_status not in", values, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusBetween(String value1, String value2) {
            addCriterion("msg_status between", value1, value2, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusNotBetween(String value1, String value2) {
            addCriterion("msg_status not between", value1, value2, "msgStatus");
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