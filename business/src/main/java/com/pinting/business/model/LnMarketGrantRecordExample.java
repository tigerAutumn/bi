package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LnMarketGrantRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LnMarketGrantRecordExample() {
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

        public Criteria andPartnerCodeIsNull() {
            addCriterion("partner_code is null");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeIsNotNull() {
            addCriterion("partner_code is not null");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeEqualTo(String value) {
            addCriterion("partner_code =", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeNotEqualTo(String value) {
            addCriterion("partner_code <>", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeGreaterThan(String value) {
            addCriterion("partner_code >", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeGreaterThanOrEqualTo(String value) {
            addCriterion("partner_code >=", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeLessThan(String value) {
            addCriterion("partner_code <", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeLessThanOrEqualTo(String value) {
            addCriterion("partner_code <=", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeLike(String value) {
            addCriterion("partner_code like", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeNotLike(String value) {
            addCriterion("partner_code not like", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeIn(List<String> values) {
            addCriterion("partner_code in", values, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeNotIn(List<String> values) {
            addCriterion("partner_code not in", values, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeBetween(String value1, String value2) {
            addCriterion("partner_code between", value1, value2, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeNotBetween(String value1, String value2) {
            addCriterion("partner_code not between", value1, value2, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoIsNull() {
            addCriterion("partner_order_no is null");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoIsNotNull() {
            addCriterion("partner_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoEqualTo(String value) {
            addCriterion("partner_order_no =", value, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoNotEqualTo(String value) {
            addCriterion("partner_order_no <>", value, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoGreaterThan(String value) {
            addCriterion("partner_order_no >", value, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("partner_order_no >=", value, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoLessThan(String value) {
            addCriterion("partner_order_no <", value, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoLessThanOrEqualTo(String value) {
            addCriterion("partner_order_no <=", value, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoLike(String value) {
            addCriterion("partner_order_no like", value, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoNotLike(String value) {
            addCriterion("partner_order_no not like", value, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoIn(List<String> values) {
            addCriterion("partner_order_no in", values, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoNotIn(List<String> values) {
            addCriterion("partner_order_no not in", values, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoBetween(String value1, String value2) {
            addCriterion("partner_order_no between", value1, value2, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andPartnerOrderNoNotBetween(String value1, String value2) {
            addCriterion("partner_order_no not between", value1, value2, "partnerOrderNo");
            return (Criteria) this;
        }

        public Criteria andGrantItemIsNull() {
            addCriterion("grant_item is null");
            return (Criteria) this;
        }

        public Criteria andGrantItemIsNotNull() {
            addCriterion("grant_item is not null");
            return (Criteria) this;
        }

        public Criteria andGrantItemEqualTo(String value) {
            addCriterion("grant_item =", value, "grantItem");
            return (Criteria) this;
        }

        public Criteria andGrantItemNotEqualTo(String value) {
            addCriterion("grant_item <>", value, "grantItem");
            return (Criteria) this;
        }

        public Criteria andGrantItemGreaterThan(String value) {
            addCriterion("grant_item >", value, "grantItem");
            return (Criteria) this;
        }

        public Criteria andGrantItemGreaterThanOrEqualTo(String value) {
            addCriterion("grant_item >=", value, "grantItem");
            return (Criteria) this;
        }

        public Criteria andGrantItemLessThan(String value) {
            addCriterion("grant_item <", value, "grantItem");
            return (Criteria) this;
        }

        public Criteria andGrantItemLessThanOrEqualTo(String value) {
            addCriterion("grant_item <=", value, "grantItem");
            return (Criteria) this;
        }

        public Criteria andGrantItemLike(String value) {
            addCriterion("grant_item like", value, "grantItem");
            return (Criteria) this;
        }

        public Criteria andGrantItemNotLike(String value) {
            addCriterion("grant_item not like", value, "grantItem");
            return (Criteria) this;
        }

        public Criteria andGrantItemIn(List<String> values) {
            addCriterion("grant_item in", values, "grantItem");
            return (Criteria) this;
        }

        public Criteria andGrantItemNotIn(List<String> values) {
            addCriterion("grant_item not in", values, "grantItem");
            return (Criteria) this;
        }

        public Criteria andGrantItemBetween(String value1, String value2) {
            addCriterion("grant_item between", value1, value2, "grantItem");
            return (Criteria) this;
        }

        public Criteria andGrantItemNotBetween(String value1, String value2) {
            addCriterion("grant_item not between", value1, value2, "grantItem");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdIsNull() {
            addCriterion("partner_user_id is null");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdIsNotNull() {
            addCriterion("partner_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdEqualTo(String value) {
            addCriterion("partner_user_id =", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdNotEqualTo(String value) {
            addCriterion("partner_user_id <>", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdGreaterThan(String value) {
            addCriterion("partner_user_id >", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("partner_user_id >=", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdLessThan(String value) {
            addCriterion("partner_user_id <", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdLessThanOrEqualTo(String value) {
            addCriterion("partner_user_id <=", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdLike(String value) {
            addCriterion("partner_user_id like", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdNotLike(String value) {
            addCriterion("partner_user_id not like", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdIn(List<String> values) {
            addCriterion("partner_user_id in", values, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdNotIn(List<String> values) {
            addCriterion("partner_user_id not in", values, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdBetween(String value1, String value2) {
            addCriterion("partner_user_id between", value1, value2, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdNotBetween(String value1, String value2) {
            addCriterion("partner_user_id not between", value1, value2, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdIsNull() {
            addCriterion("bgw_bind_id is null");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdIsNotNull() {
            addCriterion("bgw_bind_id is not null");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdEqualTo(String value) {
            addCriterion("bgw_bind_id =", value, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdNotEqualTo(String value) {
            addCriterion("bgw_bind_id <>", value, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdGreaterThan(String value) {
            addCriterion("bgw_bind_id >", value, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdGreaterThanOrEqualTo(String value) {
            addCriterion("bgw_bind_id >=", value, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdLessThan(String value) {
            addCriterion("bgw_bind_id <", value, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdLessThanOrEqualTo(String value) {
            addCriterion("bgw_bind_id <=", value, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdLike(String value) {
            addCriterion("bgw_bind_id like", value, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdNotLike(String value) {
            addCriterion("bgw_bind_id not like", value, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdIn(List<String> values) {
            addCriterion("bgw_bind_id in", values, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdNotIn(List<String> values) {
            addCriterion("bgw_bind_id not in", values, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdBetween(String value1, String value2) {
            addCriterion("bgw_bind_id between", value1, value2, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andBgwBindIdNotBetween(String value1, String value2) {
            addCriterion("bgw_bind_id not between", value1, value2, "bgwBindId");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(Double value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(Double value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(Double value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(Double value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(Double value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<Double> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<Double> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(Double value1, Double value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(Double value1, Double value2) {
            addCriterion("amount not between", value1, value2, "amount");
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

        public Criteria andFinishTimeIsNull() {
            addCriterion("finish_time is null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNotNull() {
            addCriterion("finish_time is not null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeEqualTo(Date value) {
            addCriterion("finish_time =", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotEqualTo(Date value) {
            addCriterion("finish_time <>", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThan(Date value) {
            addCriterion("finish_time >", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("finish_time >=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThan(Date value) {
            addCriterion("finish_time <", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThanOrEqualTo(Date value) {
            addCriterion("finish_time <=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIn(List<Date> values) {
            addCriterion("finish_time in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotIn(List<Date> values) {
            addCriterion("finish_time not in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeBetween(Date value1, Date value2) {
            addCriterion("finish_time between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotBetween(Date value1, Date value2) {
            addCriterion("finish_time not between", value1, value2, "finishTime");
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

        public Criteria andInformStatusIsNull() {
            addCriterion("inform_status is null");
            return (Criteria) this;
        }

        public Criteria andInformStatusIsNotNull() {
            addCriterion("inform_status is not null");
            return (Criteria) this;
        }

        public Criteria andInformStatusEqualTo(String value) {
            addCriterion("inform_status =", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusNotEqualTo(String value) {
            addCriterion("inform_status <>", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusGreaterThan(String value) {
            addCriterion("inform_status >", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusGreaterThanOrEqualTo(String value) {
            addCriterion("inform_status >=", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusLessThan(String value) {
            addCriterion("inform_status <", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusLessThanOrEqualTo(String value) {
            addCriterion("inform_status <=", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusLike(String value) {
            addCriterion("inform_status like", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusNotLike(String value) {
            addCriterion("inform_status not like", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusIn(List<String> values) {
            addCriterion("inform_status in", values, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusNotIn(List<String> values) {
            addCriterion("inform_status not in", values, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusBetween(String value1, String value2) {
            addCriterion("inform_status between", value1, value2, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusNotBetween(String value1, String value2) {
            addCriterion("inform_status not between", value1, value2, "informStatus");
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