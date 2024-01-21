package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsInterestTicketGrantAttributeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsInterestTicketGrantAttributeExample() {
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

        public Criteria andTicketNameIsNull() {
            addCriterion("ticket_name is null");
            return (Criteria) this;
        }

        public Criteria andTicketNameIsNotNull() {
            addCriterion("ticket_name is not null");
            return (Criteria) this;
        }

        public Criteria andTicketNameEqualTo(String value) {
            addCriterion("ticket_name =", value, "ticketName");
            return (Criteria) this;
        }

        public Criteria andTicketNameNotEqualTo(String value) {
            addCriterion("ticket_name <>", value, "ticketName");
            return (Criteria) this;
        }

        public Criteria andTicketNameGreaterThan(String value) {
            addCriterion("ticket_name >", value, "ticketName");
            return (Criteria) this;
        }

        public Criteria andTicketNameGreaterThanOrEqualTo(String value) {
            addCriterion("ticket_name >=", value, "ticketName");
            return (Criteria) this;
        }

        public Criteria andTicketNameLessThan(String value) {
            addCriterion("ticket_name <", value, "ticketName");
            return (Criteria) this;
        }

        public Criteria andTicketNameLessThanOrEqualTo(String value) {
            addCriterion("ticket_name <=", value, "ticketName");
            return (Criteria) this;
        }

        public Criteria andTicketNameLike(String value) {
            addCriterion("ticket_name like", value, "ticketName");
            return (Criteria) this;
        }

        public Criteria andTicketNameNotLike(String value) {
            addCriterion("ticket_name not like", value, "ticketName");
            return (Criteria) this;
        }

        public Criteria andTicketNameIn(List<String> values) {
            addCriterion("ticket_name in", values, "ticketName");
            return (Criteria) this;
        }

        public Criteria andTicketNameNotIn(List<String> values) {
            addCriterion("ticket_name not in", values, "ticketName");
            return (Criteria) this;
        }

        public Criteria andTicketNameBetween(String value1, String value2) {
            addCriterion("ticket_name between", value1, value2, "ticketName");
            return (Criteria) this;
        }

        public Criteria andTicketNameNotBetween(String value1, String value2) {
            addCriterion("ticket_name not between", value1, value2, "ticketName");
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

        public Criteria andGrantTotalIsNull() {
            addCriterion("grant_total is null");
            return (Criteria) this;
        }

        public Criteria andGrantTotalIsNotNull() {
            addCriterion("grant_total is not null");
            return (Criteria) this;
        }

        public Criteria andGrantTotalEqualTo(Integer value) {
            addCriterion("grant_total =", value, "grantTotal");
            return (Criteria) this;
        }

        public Criteria andGrantTotalNotEqualTo(Integer value) {
            addCriterion("grant_total <>", value, "grantTotal");
            return (Criteria) this;
        }

        public Criteria andGrantTotalGreaterThan(Integer value) {
            addCriterion("grant_total >", value, "grantTotal");
            return (Criteria) this;
        }

        public Criteria andGrantTotalGreaterThanOrEqualTo(Integer value) {
            addCriterion("grant_total >=", value, "grantTotal");
            return (Criteria) this;
        }

        public Criteria andGrantTotalLessThan(Integer value) {
            addCriterion("grant_total <", value, "grantTotal");
            return (Criteria) this;
        }

        public Criteria andGrantTotalLessThanOrEqualTo(Integer value) {
            addCriterion("grant_total <=", value, "grantTotal");
            return (Criteria) this;
        }

        public Criteria andGrantTotalIn(List<Integer> values) {
            addCriterion("grant_total in", values, "grantTotal");
            return (Criteria) this;
        }

        public Criteria andGrantTotalNotIn(List<Integer> values) {
            addCriterion("grant_total not in", values, "grantTotal");
            return (Criteria) this;
        }

        public Criteria andGrantTotalBetween(Integer value1, Integer value2) {
            addCriterion("grant_total between", value1, value2, "grantTotal");
            return (Criteria) this;
        }

        public Criteria andGrantTotalNotBetween(Integer value1, Integer value2) {
            addCriterion("grant_total not between", value1, value2, "grantTotal");
            return (Criteria) this;
        }

        public Criteria andGrantNumIsNull() {
            addCriterion("grant_num is null");
            return (Criteria) this;
        }

        public Criteria andGrantNumIsNotNull() {
            addCriterion("grant_num is not null");
            return (Criteria) this;
        }

        public Criteria andGrantNumEqualTo(Integer value) {
            addCriterion("grant_num =", value, "grantNum");
            return (Criteria) this;
        }

        public Criteria andGrantNumNotEqualTo(Integer value) {
            addCriterion("grant_num <>", value, "grantNum");
            return (Criteria) this;
        }

        public Criteria andGrantNumGreaterThan(Integer value) {
            addCriterion("grant_num >", value, "grantNum");
            return (Criteria) this;
        }

        public Criteria andGrantNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("grant_num >=", value, "grantNum");
            return (Criteria) this;
        }

        public Criteria andGrantNumLessThan(Integer value) {
            addCriterion("grant_num <", value, "grantNum");
            return (Criteria) this;
        }

        public Criteria andGrantNumLessThanOrEqualTo(Integer value) {
            addCriterion("grant_num <=", value, "grantNum");
            return (Criteria) this;
        }

        public Criteria andGrantNumIn(List<Integer> values) {
            addCriterion("grant_num in", values, "grantNum");
            return (Criteria) this;
        }

        public Criteria andGrantNumNotIn(List<Integer> values) {
            addCriterion("grant_num not in", values, "grantNum");
            return (Criteria) this;
        }

        public Criteria andGrantNumBetween(Integer value1, Integer value2) {
            addCriterion("grant_num between", value1, value2, "grantNum");
            return (Criteria) this;
        }

        public Criteria andGrantNumNotBetween(Integer value1, Integer value2) {
            addCriterion("grant_num not between", value1, value2, "grantNum");
            return (Criteria) this;
        }

        public Criteria andValidTermTypeIsNull() {
            addCriterion("valid_term_type is null");
            return (Criteria) this;
        }

        public Criteria andValidTermTypeIsNotNull() {
            addCriterion("valid_term_type is not null");
            return (Criteria) this;
        }

        public Criteria andValidTermTypeEqualTo(String value) {
            addCriterion("valid_term_type =", value, "validTermType");
            return (Criteria) this;
        }

        public Criteria andValidTermTypeNotEqualTo(String value) {
            addCriterion("valid_term_type <>", value, "validTermType");
            return (Criteria) this;
        }

        public Criteria andValidTermTypeGreaterThan(String value) {
            addCriterion("valid_term_type >", value, "validTermType");
            return (Criteria) this;
        }

        public Criteria andValidTermTypeGreaterThanOrEqualTo(String value) {
            addCriterion("valid_term_type >=", value, "validTermType");
            return (Criteria) this;
        }

        public Criteria andValidTermTypeLessThan(String value) {
            addCriterion("valid_term_type <", value, "validTermType");
            return (Criteria) this;
        }

        public Criteria andValidTermTypeLessThanOrEqualTo(String value) {
            addCriterion("valid_term_type <=", value, "validTermType");
            return (Criteria) this;
        }

        public Criteria andValidTermTypeLike(String value) {
            addCriterion("valid_term_type like", value, "validTermType");
            return (Criteria) this;
        }

        public Criteria andValidTermTypeNotLike(String value) {
            addCriterion("valid_term_type not like", value, "validTermType");
            return (Criteria) this;
        }

        public Criteria andValidTermTypeIn(List<String> values) {
            addCriterion("valid_term_type in", values, "validTermType");
            return (Criteria) this;
        }

        public Criteria andValidTermTypeNotIn(List<String> values) {
            addCriterion("valid_term_type not in", values, "validTermType");
            return (Criteria) this;
        }

        public Criteria andValidTermTypeBetween(String value1, String value2) {
            addCriterion("valid_term_type between", value1, value2, "validTermType");
            return (Criteria) this;
        }

        public Criteria andValidTermTypeNotBetween(String value1, String value2) {
            addCriterion("valid_term_type not between", value1, value2, "validTermType");
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

        public Criteria andAvailableDaysIsNull() {
            addCriterion("available_days is null");
            return (Criteria) this;
        }

        public Criteria andAvailableDaysIsNotNull() {
            addCriterion("available_days is not null");
            return (Criteria) this;
        }

        public Criteria andAvailableDaysEqualTo(Integer value) {
            addCriterion("available_days =", value, "availableDays");
            return (Criteria) this;
        }

        public Criteria andAvailableDaysNotEqualTo(Integer value) {
            addCriterion("available_days <>", value, "availableDays");
            return (Criteria) this;
        }

        public Criteria andAvailableDaysGreaterThan(Integer value) {
            addCriterion("available_days >", value, "availableDays");
            return (Criteria) this;
        }

        public Criteria andAvailableDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("available_days >=", value, "availableDays");
            return (Criteria) this;
        }

        public Criteria andAvailableDaysLessThan(Integer value) {
            addCriterion("available_days <", value, "availableDays");
            return (Criteria) this;
        }

        public Criteria andAvailableDaysLessThanOrEqualTo(Integer value) {
            addCriterion("available_days <=", value, "availableDays");
            return (Criteria) this;
        }

        public Criteria andAvailableDaysIn(List<Integer> values) {
            addCriterion("available_days in", values, "availableDays");
            return (Criteria) this;
        }

        public Criteria andAvailableDaysNotIn(List<Integer> values) {
            addCriterion("available_days not in", values, "availableDays");
            return (Criteria) this;
        }

        public Criteria andAvailableDaysBetween(Integer value1, Integer value2) {
            addCriterion("available_days between", value1, value2, "availableDays");
            return (Criteria) this;
        }

        public Criteria andAvailableDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("available_days not between", value1, value2, "availableDays");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelIsNull() {
            addCriterion("notify_channel is null");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelIsNotNull() {
            addCriterion("notify_channel is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelEqualTo(String value) {
            addCriterion("notify_channel =", value, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelNotEqualTo(String value) {
            addCriterion("notify_channel <>", value, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelGreaterThan(String value) {
            addCriterion("notify_channel >", value, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelGreaterThanOrEqualTo(String value) {
            addCriterion("notify_channel >=", value, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelLessThan(String value) {
            addCriterion("notify_channel <", value, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelLessThanOrEqualTo(String value) {
            addCriterion("notify_channel <=", value, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelLike(String value) {
            addCriterion("notify_channel like", value, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelNotLike(String value) {
            addCriterion("notify_channel not like", value, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelIn(List<String> values) {
            addCriterion("notify_channel in", values, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelNotIn(List<String> values) {
            addCriterion("notify_channel not in", values, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelBetween(String value1, String value2) {
            addCriterion("notify_channel between", value1, value2, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andNotifyChannelNotBetween(String value1, String value2) {
            addCriterion("notify_channel not between", value1, value2, "notifyChannel");
            return (Criteria) this;
        }

        public Criteria andInvestLimitIsNull() {
            addCriterion("invest_limit is null");
            return (Criteria) this;
        }

        public Criteria andInvestLimitIsNotNull() {
            addCriterion("invest_limit is not null");
            return (Criteria) this;
        }

        public Criteria andInvestLimitEqualTo(Double value) {
            addCriterion("invest_limit =", value, "investLimit");
            return (Criteria) this;
        }

        public Criteria andInvestLimitNotEqualTo(Double value) {
            addCriterion("invest_limit <>", value, "investLimit");
            return (Criteria) this;
        }

        public Criteria andInvestLimitGreaterThan(Double value) {
            addCriterion("invest_limit >", value, "investLimit");
            return (Criteria) this;
        }

        public Criteria andInvestLimitGreaterThanOrEqualTo(Double value) {
            addCriterion("invest_limit >=", value, "investLimit");
            return (Criteria) this;
        }

        public Criteria andInvestLimitLessThan(Double value) {
            addCriterion("invest_limit <", value, "investLimit");
            return (Criteria) this;
        }

        public Criteria andInvestLimitLessThanOrEqualTo(Double value) {
            addCriterion("invest_limit <=", value, "investLimit");
            return (Criteria) this;
        }

        public Criteria andInvestLimitIn(List<Double> values) {
            addCriterion("invest_limit in", values, "investLimit");
            return (Criteria) this;
        }

        public Criteria andInvestLimitNotIn(List<Double> values) {
            addCriterion("invest_limit not in", values, "investLimit");
            return (Criteria) this;
        }

        public Criteria andInvestLimitBetween(Double value1, Double value2) {
            addCriterion("invest_limit between", value1, value2, "investLimit");
            return (Criteria) this;
        }

        public Criteria andInvestLimitNotBetween(Double value1, Double value2) {
            addCriterion("invest_limit not between", value1, value2, "investLimit");
            return (Criteria) this;
        }

        public Criteria andProductLimitIsNull() {
            addCriterion("product_limit is null");
            return (Criteria) this;
        }

        public Criteria andProductLimitIsNotNull() {
            addCriterion("product_limit is not null");
            return (Criteria) this;
        }

        public Criteria andProductLimitEqualTo(String value) {
            addCriterion("product_limit =", value, "productLimit");
            return (Criteria) this;
        }

        public Criteria andProductLimitNotEqualTo(String value) {
            addCriterion("product_limit <>", value, "productLimit");
            return (Criteria) this;
        }

        public Criteria andProductLimitGreaterThan(String value) {
            addCriterion("product_limit >", value, "productLimit");
            return (Criteria) this;
        }

        public Criteria andProductLimitGreaterThanOrEqualTo(String value) {
            addCriterion("product_limit >=", value, "productLimit");
            return (Criteria) this;
        }

        public Criteria andProductLimitLessThan(String value) {
            addCriterion("product_limit <", value, "productLimit");
            return (Criteria) this;
        }

        public Criteria andProductLimitLessThanOrEqualTo(String value) {
            addCriterion("product_limit <=", value, "productLimit");
            return (Criteria) this;
        }

        public Criteria andProductLimitLike(String value) {
            addCriterion("product_limit like", value, "productLimit");
            return (Criteria) this;
        }

        public Criteria andProductLimitNotLike(String value) {
            addCriterion("product_limit not like", value, "productLimit");
            return (Criteria) this;
        }

        public Criteria andProductLimitIn(List<String> values) {
            addCriterion("product_limit in", values, "productLimit");
            return (Criteria) this;
        }

        public Criteria andProductLimitNotIn(List<String> values) {
            addCriterion("product_limit not in", values, "productLimit");
            return (Criteria) this;
        }

        public Criteria andProductLimitBetween(String value1, String value2) {
            addCriterion("product_limit between", value1, value2, "productLimit");
            return (Criteria) this;
        }

        public Criteria andProductLimitNotBetween(String value1, String value2) {
            addCriterion("product_limit not between", value1, value2, "productLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitIsNull() {
            addCriterion("term_limit is null");
            return (Criteria) this;
        }

        public Criteria andTermLimitIsNotNull() {
            addCriterion("term_limit is not null");
            return (Criteria) this;
        }

        public Criteria andTermLimitEqualTo(String value) {
            addCriterion("term_limit =", value, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitNotEqualTo(String value) {
            addCriterion("term_limit <>", value, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitGreaterThan(String value) {
            addCriterion("term_limit >", value, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitGreaterThanOrEqualTo(String value) {
            addCriterion("term_limit >=", value, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitLessThan(String value) {
            addCriterion("term_limit <", value, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitLessThanOrEqualTo(String value) {
            addCriterion("term_limit <=", value, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitLike(String value) {
            addCriterion("term_limit like", value, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitNotLike(String value) {
            addCriterion("term_limit not like", value, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitIn(List<String> values) {
            addCriterion("term_limit in", values, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitNotIn(List<String> values) {
            addCriterion("term_limit not in", values, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitBetween(String value1, String value2) {
            addCriterion("term_limit between", value1, value2, "termLimit");
            return (Criteria) this;
        }

        public Criteria andTermLimitNotBetween(String value1, String value2) {
            addCriterion("term_limit not between", value1, value2, "termLimit");
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