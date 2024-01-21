package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsAutoRedPacketRuleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsAutoRedPacketRuleExample() {
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

        public Criteria andAgentIdsIsNull() {
            addCriterion("agent_ids is null");
            return (Criteria) this;
        }

        public Criteria andAgentIdsIsNotNull() {
            addCriterion("agent_ids is not null");
            return (Criteria) this;
        }

        public Criteria andAgentIdsEqualTo(String value) {
            addCriterion("agent_ids =", value, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsNotEqualTo(String value) {
            addCriterion("agent_ids <>", value, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsGreaterThan(String value) {
            addCriterion("agent_ids >", value, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsGreaterThanOrEqualTo(String value) {
            addCriterion("agent_ids >=", value, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsLessThan(String value) {
            addCriterion("agent_ids <", value, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsLessThanOrEqualTo(String value) {
            addCriterion("agent_ids <=", value, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsLike(String value) {
            addCriterion("agent_ids like", value, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsNotLike(String value) {
            addCriterion("agent_ids not like", value, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsIn(List<String> values) {
            addCriterion("agent_ids in", values, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsNotIn(List<String> values) {
            addCriterion("agent_ids not in", values, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsBetween(String value1, String value2) {
            addCriterion("agent_ids between", value1, value2, "agentIds");
            return (Criteria) this;
        }

        public Criteria andAgentIdsNotBetween(String value1, String value2) {
            addCriterion("agent_ids not between", value1, value2, "agentIds");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeIsNull() {
            addCriterion("trigger_type is null");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeIsNotNull() {
            addCriterion("trigger_type is not null");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeEqualTo(String value) {
            addCriterion("trigger_type =", value, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeNotEqualTo(String value) {
            addCriterion("trigger_type <>", value, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeGreaterThan(String value) {
            addCriterion("trigger_type >", value, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeGreaterThanOrEqualTo(String value) {
            addCriterion("trigger_type >=", value, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeLessThan(String value) {
            addCriterion("trigger_type <", value, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeLessThanOrEqualTo(String value) {
            addCriterion("trigger_type <=", value, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeLike(String value) {
            addCriterion("trigger_type like", value, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeNotLike(String value) {
            addCriterion("trigger_type not like", value, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeIn(List<String> values) {
            addCriterion("trigger_type in", values, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeNotIn(List<String> values) {
            addCriterion("trigger_type not in", values, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeBetween(String value1, String value2) {
            addCriterion("trigger_type between", value1, value2, "triggerType");
            return (Criteria) this;
        }

        public Criteria andTriggerTypeNotBetween(String value1, String value2) {
            addCriterion("trigger_type not between", value1, value2, "triggerType");
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

        public Criteria andTriggerAmountStartIsNull() {
            addCriterion("trigger_amount_start is null");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountStartIsNotNull() {
            addCriterion("trigger_amount_start is not null");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountStartEqualTo(Double value) {
            addCriterion("trigger_amount_start =", value, "triggerAmountStart");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountStartNotEqualTo(Double value) {
            addCriterion("trigger_amount_start <>", value, "triggerAmountStart");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountStartGreaterThan(Double value) {
            addCriterion("trigger_amount_start >", value, "triggerAmountStart");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountStartGreaterThanOrEqualTo(Double value) {
            addCriterion("trigger_amount_start >=", value, "triggerAmountStart");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountStartLessThan(Double value) {
            addCriterion("trigger_amount_start <", value, "triggerAmountStart");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountStartLessThanOrEqualTo(Double value) {
            addCriterion("trigger_amount_start <=", value, "triggerAmountStart");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountStartIn(List<Double> values) {
            addCriterion("trigger_amount_start in", values, "triggerAmountStart");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountStartNotIn(List<Double> values) {
            addCriterion("trigger_amount_start not in", values, "triggerAmountStart");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountStartBetween(Double value1, Double value2) {
            addCriterion("trigger_amount_start between", value1, value2, "triggerAmountStart");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountStartNotBetween(Double value1, Double value2) {
            addCriterion("trigger_amount_start not between", value1, value2, "triggerAmountStart");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountEndIsNull() {
            addCriterion("trigger_amount_end is null");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountEndIsNotNull() {
            addCriterion("trigger_amount_end is not null");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountEndEqualTo(Double value) {
            addCriterion("trigger_amount_end =", value, "triggerAmountEnd");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountEndNotEqualTo(Double value) {
            addCriterion("trigger_amount_end <>", value, "triggerAmountEnd");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountEndGreaterThan(Double value) {
            addCriterion("trigger_amount_end >", value, "triggerAmountEnd");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountEndGreaterThanOrEqualTo(Double value) {
            addCriterion("trigger_amount_end >=", value, "triggerAmountEnd");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountEndLessThan(Double value) {
            addCriterion("trigger_amount_end <", value, "triggerAmountEnd");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountEndLessThanOrEqualTo(Double value) {
            addCriterion("trigger_amount_end <=", value, "triggerAmountEnd");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountEndIn(List<Double> values) {
            addCriterion("trigger_amount_end in", values, "triggerAmountEnd");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountEndNotIn(List<Double> values) {
            addCriterion("trigger_amount_end not in", values, "triggerAmountEnd");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountEndBetween(Double value1, Double value2) {
            addCriterion("trigger_amount_end between", value1, value2, "triggerAmountEnd");
            return (Criteria) this;
        }

        public Criteria andTriggerAmountEndNotBetween(Double value1, Double value2) {
            addCriterion("trigger_amount_end not between", value1, value2, "triggerAmountEnd");
            return (Criteria) this;
        }

        public Criteria andTriggerInviteNumIsNull() {
            addCriterion("trigger_invite_num is null");
            return (Criteria) this;
        }

        public Criteria andTriggerInviteNumIsNotNull() {
            addCriterion("trigger_invite_num is not null");
            return (Criteria) this;
        }

        public Criteria andTriggerInviteNumEqualTo(Integer value) {
            addCriterion("trigger_invite_num =", value, "triggerInviteNum");
            return (Criteria) this;
        }

        public Criteria andTriggerInviteNumNotEqualTo(Integer value) {
            addCriterion("trigger_invite_num <>", value, "triggerInviteNum");
            return (Criteria) this;
        }

        public Criteria andTriggerInviteNumGreaterThan(Integer value) {
            addCriterion("trigger_invite_num >", value, "triggerInviteNum");
            return (Criteria) this;
        }

        public Criteria andTriggerInviteNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("trigger_invite_num >=", value, "triggerInviteNum");
            return (Criteria) this;
        }

        public Criteria andTriggerInviteNumLessThan(Integer value) {
            addCriterion("trigger_invite_num <", value, "triggerInviteNum");
            return (Criteria) this;
        }

        public Criteria andTriggerInviteNumLessThanOrEqualTo(Integer value) {
            addCriterion("trigger_invite_num <=", value, "triggerInviteNum");
            return (Criteria) this;
        }

        public Criteria andTriggerInviteNumIn(List<Integer> values) {
            addCriterion("trigger_invite_num in", values, "triggerInviteNum");
            return (Criteria) this;
        }

        public Criteria andTriggerInviteNumNotIn(List<Integer> values) {
            addCriterion("trigger_invite_num not in", values, "triggerInviteNum");
            return (Criteria) this;
        }

        public Criteria andTriggerInviteNumBetween(Integer value1, Integer value2) {
            addCriterion("trigger_invite_num between", value1, value2, "triggerInviteNum");
            return (Criteria) this;
        }

        public Criteria andTriggerInviteNumNotBetween(Integer value1, Integer value2) {
            addCriterion("trigger_invite_num not between", value1, value2, "triggerInviteNum");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeStartIsNull() {
            addCriterion("distribute_time_start is null");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeStartIsNotNull() {
            addCriterion("distribute_time_start is not null");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeStartEqualTo(Date value) {
            addCriterion("distribute_time_start =", value, "distributeTimeStart");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeStartNotEqualTo(Date value) {
            addCriterion("distribute_time_start <>", value, "distributeTimeStart");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeStartGreaterThan(Date value) {
            addCriterion("distribute_time_start >", value, "distributeTimeStart");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeStartGreaterThanOrEqualTo(Date value) {
            addCriterion("distribute_time_start >=", value, "distributeTimeStart");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeStartLessThan(Date value) {
            addCriterion("distribute_time_start <", value, "distributeTimeStart");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeStartLessThanOrEqualTo(Date value) {
            addCriterion("distribute_time_start <=", value, "distributeTimeStart");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeStartIn(List<Date> values) {
            addCriterion("distribute_time_start in", values, "distributeTimeStart");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeStartNotIn(List<Date> values) {
            addCriterion("distribute_time_start not in", values, "distributeTimeStart");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeStartBetween(Date value1, Date value2) {
            addCriterion("distribute_time_start between", value1, value2, "distributeTimeStart");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeStartNotBetween(Date value1, Date value2) {
            addCriterion("distribute_time_start not between", value1, value2, "distributeTimeStart");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeEndIsNull() {
            addCriterion("distribute_time_end is null");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeEndIsNotNull() {
            addCriterion("distribute_time_end is not null");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeEndEqualTo(Date value) {
            addCriterion("distribute_time_end =", value, "distributeTimeEnd");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeEndNotEqualTo(Date value) {
            addCriterion("distribute_time_end <>", value, "distributeTimeEnd");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeEndGreaterThan(Date value) {
            addCriterion("distribute_time_end >", value, "distributeTimeEnd");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeEndGreaterThanOrEqualTo(Date value) {
            addCriterion("distribute_time_end >=", value, "distributeTimeEnd");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeEndLessThan(Date value) {
            addCriterion("distribute_time_end <", value, "distributeTimeEnd");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeEndLessThanOrEqualTo(Date value) {
            addCriterion("distribute_time_end <=", value, "distributeTimeEnd");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeEndIn(List<Date> values) {
            addCriterion("distribute_time_end in", values, "distributeTimeEnd");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeEndNotIn(List<Date> values) {
            addCriterion("distribute_time_end not in", values, "distributeTimeEnd");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeEndBetween(Date value1, Date value2) {
            addCriterion("distribute_time_end between", value1, value2, "distributeTimeEnd");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeEndNotBetween(Date value1, Date value2) {
            addCriterion("distribute_time_end not between", value1, value2, "distributeTimeEnd");
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