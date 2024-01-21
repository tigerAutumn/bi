package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bs19payCheckRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public Bs19payCheckRecordExample() {
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

        public Criteria andBatchSerialNoIsNull() {
            addCriterion("batch_serial_no is null");
            return (Criteria) this;
        }

        public Criteria andBatchSerialNoIsNotNull() {
            addCriterion("batch_serial_no is not null");
            return (Criteria) this;
        }

        public Criteria andBatchSerialNoEqualTo(String value) {
            addCriterion("batch_serial_no =", value, "batchSerialNo");
            return (Criteria) this;
        }

        public Criteria andBatchSerialNoNotEqualTo(String value) {
            addCriterion("batch_serial_no <>", value, "batchSerialNo");
            return (Criteria) this;
        }

        public Criteria andBatchSerialNoGreaterThan(String value) {
            addCriterion("batch_serial_no >", value, "batchSerialNo");
            return (Criteria) this;
        }

        public Criteria andBatchSerialNoGreaterThanOrEqualTo(String value) {
            addCriterion("batch_serial_no >=", value, "batchSerialNo");
            return (Criteria) this;
        }

        public Criteria andBatchSerialNoLessThan(String value) {
            addCriterion("batch_serial_no <", value, "batchSerialNo");
            return (Criteria) this;
        }

        public Criteria andBatchSerialNoLessThanOrEqualTo(String value) {
            addCriterion("batch_serial_no <=", value, "batchSerialNo");
            return (Criteria) this;
        }

        public Criteria andBatchSerialNoLike(String value) {
            addCriterion("batch_serial_no like", value, "batchSerialNo");
            return (Criteria) this;
        }

        public Criteria andBatchSerialNoNotLike(String value) {
            addCriterion("batch_serial_no not like", value, "batchSerialNo");
            return (Criteria) this;
        }

        public Criteria andBatchSerialNoIn(List<String> values) {
            addCriterion("batch_serial_no in", values, "batchSerialNo");
            return (Criteria) this;
        }

        public Criteria andBatchSerialNoNotIn(List<String> values) {
            addCriterion("batch_serial_no not in", values, "batchSerialNo");
            return (Criteria) this;
        }

        public Criteria andBatchSerialNoBetween(String value1, String value2) {
            addCriterion("batch_serial_no between", value1, value2, "batchSerialNo");
            return (Criteria) this;
        }

        public Criteria andBatchSerialNoNotBetween(String value1, String value2) {
            addCriterion("batch_serial_no not between", value1, value2, "batchSerialNo");
            return (Criteria) this;
        }

        public Criteria andRelativeOrderNoIsNull() {
            addCriterion("relative_order_no is null");
            return (Criteria) this;
        }

        public Criteria andRelativeOrderNoIsNotNull() {
            addCriterion("relative_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andRelativeOrderNoEqualTo(String value) {
            addCriterion("relative_order_no =", value, "relativeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRelativeOrderNoNotEqualTo(String value) {
            addCriterion("relative_order_no <>", value, "relativeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRelativeOrderNoGreaterThan(String value) {
            addCriterion("relative_order_no >", value, "relativeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRelativeOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("relative_order_no >=", value, "relativeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRelativeOrderNoLessThan(String value) {
            addCriterion("relative_order_no <", value, "relativeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRelativeOrderNoLessThanOrEqualTo(String value) {
            addCriterion("relative_order_no <=", value, "relativeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRelativeOrderNoLike(String value) {
            addCriterion("relative_order_no like", value, "relativeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRelativeOrderNoNotLike(String value) {
            addCriterion("relative_order_no not like", value, "relativeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRelativeOrderNoIn(List<String> values) {
            addCriterion("relative_order_no in", values, "relativeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRelativeOrderNoNotIn(List<String> values) {
            addCriterion("relative_order_no not in", values, "relativeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRelativeOrderNoBetween(String value1, String value2) {
            addCriterion("relative_order_no between", value1, value2, "relativeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRelativeOrderNoNotBetween(String value1, String value2) {
            addCriterion("relative_order_no not between", value1, value2, "relativeOrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19OrderJnlIsNull() {
            addCriterion("pay19_order_jnl is null");
            return (Criteria) this;
        }

        public Criteria andPay19OrderJnlIsNotNull() {
            addCriterion("pay19_order_jnl is not null");
            return (Criteria) this;
        }

        public Criteria andPay19OrderJnlEqualTo(String value) {
            addCriterion("pay19_order_jnl =", value, "pay19OrderJnl");
            return (Criteria) this;
        }

        public Criteria andPay19OrderJnlNotEqualTo(String value) {
            addCriterion("pay19_order_jnl <>", value, "pay19OrderJnl");
            return (Criteria) this;
        }

        public Criteria andPay19OrderJnlGreaterThan(String value) {
            addCriterion("pay19_order_jnl >", value, "pay19OrderJnl");
            return (Criteria) this;
        }

        public Criteria andPay19OrderJnlGreaterThanOrEqualTo(String value) {
            addCriterion("pay19_order_jnl >=", value, "pay19OrderJnl");
            return (Criteria) this;
        }

        public Criteria andPay19OrderJnlLessThan(String value) {
            addCriterion("pay19_order_jnl <", value, "pay19OrderJnl");
            return (Criteria) this;
        }

        public Criteria andPay19OrderJnlLessThanOrEqualTo(String value) {
            addCriterion("pay19_order_jnl <=", value, "pay19OrderJnl");
            return (Criteria) this;
        }

        public Criteria andPay19OrderJnlLike(String value) {
            addCriterion("pay19_order_jnl like", value, "pay19OrderJnl");
            return (Criteria) this;
        }

        public Criteria andPay19OrderJnlNotLike(String value) {
            addCriterion("pay19_order_jnl not like", value, "pay19OrderJnl");
            return (Criteria) this;
        }

        public Criteria andPay19OrderJnlIn(List<String> values) {
            addCriterion("pay19_order_jnl in", values, "pay19OrderJnl");
            return (Criteria) this;
        }

        public Criteria andPay19OrderJnlNotIn(List<String> values) {
            addCriterion("pay19_order_jnl not in", values, "pay19OrderJnl");
            return (Criteria) this;
        }

        public Criteria andPay19OrderJnlBetween(String value1, String value2) {
            addCriterion("pay19_order_jnl between", value1, value2, "pay19OrderJnl");
            return (Criteria) this;
        }

        public Criteria andPay19OrderJnlNotBetween(String value1, String value2) {
            addCriterion("pay19_order_jnl not between", value1, value2, "pay19OrderJnl");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyOrderTimeIsNull() {
            addCriterion("trade_company_order_time is null");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyOrderTimeIsNotNull() {
            addCriterion("trade_company_order_time is not null");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyOrderTimeEqualTo(Date value) {
            addCriterion("trade_company_order_time =", value, "tradeCompanyOrderTime");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyOrderTimeNotEqualTo(Date value) {
            addCriterion("trade_company_order_time <>", value, "tradeCompanyOrderTime");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyOrderTimeGreaterThan(Date value) {
            addCriterion("trade_company_order_time >", value, "tradeCompanyOrderTime");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyOrderTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("trade_company_order_time >=", value, "tradeCompanyOrderTime");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyOrderTimeLessThan(Date value) {
            addCriterion("trade_company_order_time <", value, "tradeCompanyOrderTime");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyOrderTimeLessThanOrEqualTo(Date value) {
            addCriterion("trade_company_order_time <=", value, "tradeCompanyOrderTime");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyOrderTimeIn(List<Date> values) {
            addCriterion("trade_company_order_time in", values, "tradeCompanyOrderTime");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyOrderTimeNotIn(List<Date> values) {
            addCriterion("trade_company_order_time not in", values, "tradeCompanyOrderTime");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyOrderTimeBetween(Date value1, Date value2) {
            addCriterion("trade_company_order_time between", value1, value2, "tradeCompanyOrderTime");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyOrderTimeNotBetween(Date value1, Date value2) {
            addCriterion("trade_company_order_time not between", value1, value2, "tradeCompanyOrderTime");
            return (Criteria) this;
        }

        public Criteria andOrderSubmitTimeIsNull() {
            addCriterion("order_submit_time is null");
            return (Criteria) this;
        }

        public Criteria andOrderSubmitTimeIsNotNull() {
            addCriterion("order_submit_time is not null");
            return (Criteria) this;
        }

        public Criteria andOrderSubmitTimeEqualTo(Date value) {
            addCriterion("order_submit_time =", value, "orderSubmitTime");
            return (Criteria) this;
        }

        public Criteria andOrderSubmitTimeNotEqualTo(Date value) {
            addCriterion("order_submit_time <>", value, "orderSubmitTime");
            return (Criteria) this;
        }

        public Criteria andOrderSubmitTimeGreaterThan(Date value) {
            addCriterion("order_submit_time >", value, "orderSubmitTime");
            return (Criteria) this;
        }

        public Criteria andOrderSubmitTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("order_submit_time >=", value, "orderSubmitTime");
            return (Criteria) this;
        }

        public Criteria andOrderSubmitTimeLessThan(Date value) {
            addCriterion("order_submit_time <", value, "orderSubmitTime");
            return (Criteria) this;
        }

        public Criteria andOrderSubmitTimeLessThanOrEqualTo(Date value) {
            addCriterion("order_submit_time <=", value, "orderSubmitTime");
            return (Criteria) this;
        }

        public Criteria andOrderSubmitTimeIn(List<Date> values) {
            addCriterion("order_submit_time in", values, "orderSubmitTime");
            return (Criteria) this;
        }

        public Criteria andOrderSubmitTimeNotIn(List<Date> values) {
            addCriterion("order_submit_time not in", values, "orderSubmitTime");
            return (Criteria) this;
        }

        public Criteria andOrderSubmitTimeBetween(Date value1, Date value2) {
            addCriterion("order_submit_time between", value1, value2, "orderSubmitTime");
            return (Criteria) this;
        }

        public Criteria andOrderSubmitTimeNotBetween(Date value1, Date value2) {
            addCriterion("order_submit_time not between", value1, value2, "orderSubmitTime");
            return (Criteria) this;
        }

        public Criteria andOrderFinishTimeIsNull() {
            addCriterion("order_finish_time is null");
            return (Criteria) this;
        }

        public Criteria andOrderFinishTimeIsNotNull() {
            addCriterion("order_finish_time is not null");
            return (Criteria) this;
        }

        public Criteria andOrderFinishTimeEqualTo(Date value) {
            addCriterion("order_finish_time =", value, "orderFinishTime");
            return (Criteria) this;
        }

        public Criteria andOrderFinishTimeNotEqualTo(Date value) {
            addCriterion("order_finish_time <>", value, "orderFinishTime");
            return (Criteria) this;
        }

        public Criteria andOrderFinishTimeGreaterThan(Date value) {
            addCriterion("order_finish_time >", value, "orderFinishTime");
            return (Criteria) this;
        }

        public Criteria andOrderFinishTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("order_finish_time >=", value, "orderFinishTime");
            return (Criteria) this;
        }

        public Criteria andOrderFinishTimeLessThan(Date value) {
            addCriterion("order_finish_time <", value, "orderFinishTime");
            return (Criteria) this;
        }

        public Criteria andOrderFinishTimeLessThanOrEqualTo(Date value) {
            addCriterion("order_finish_time <=", value, "orderFinishTime");
            return (Criteria) this;
        }

        public Criteria andOrderFinishTimeIn(List<Date> values) {
            addCriterion("order_finish_time in", values, "orderFinishTime");
            return (Criteria) this;
        }

        public Criteria andOrderFinishTimeNotIn(List<Date> values) {
            addCriterion("order_finish_time not in", values, "orderFinishTime");
            return (Criteria) this;
        }

        public Criteria andOrderFinishTimeBetween(Date value1, Date value2) {
            addCriterion("order_finish_time between", value1, value2, "orderFinishTime");
            return (Criteria) this;
        }

        public Criteria andOrderFinishTimeNotBetween(Date value1, Date value2) {
            addCriterion("order_finish_time not between", value1, value2, "orderFinishTime");
            return (Criteria) this;
        }

        public Criteria andOrderSettleTimeIsNull() {
            addCriterion("order_settle_time is null");
            return (Criteria) this;
        }

        public Criteria andOrderSettleTimeIsNotNull() {
            addCriterion("order_settle_time is not null");
            return (Criteria) this;
        }

        public Criteria andOrderSettleTimeEqualTo(Date value) {
            addCriterion("order_settle_time =", value, "orderSettleTime");
            return (Criteria) this;
        }

        public Criteria andOrderSettleTimeNotEqualTo(Date value) {
            addCriterion("order_settle_time <>", value, "orderSettleTime");
            return (Criteria) this;
        }

        public Criteria andOrderSettleTimeGreaterThan(Date value) {
            addCriterion("order_settle_time >", value, "orderSettleTime");
            return (Criteria) this;
        }

        public Criteria andOrderSettleTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("order_settle_time >=", value, "orderSettleTime");
            return (Criteria) this;
        }

        public Criteria andOrderSettleTimeLessThan(Date value) {
            addCriterion("order_settle_time <", value, "orderSettleTime");
            return (Criteria) this;
        }

        public Criteria andOrderSettleTimeLessThanOrEqualTo(Date value) {
            addCriterion("order_settle_time <=", value, "orderSettleTime");
            return (Criteria) this;
        }

        public Criteria andOrderSettleTimeIn(List<Date> values) {
            addCriterion("order_settle_time in", values, "orderSettleTime");
            return (Criteria) this;
        }

        public Criteria andOrderSettleTimeNotIn(List<Date> values) {
            addCriterion("order_settle_time not in", values, "orderSettleTime");
            return (Criteria) this;
        }

        public Criteria andOrderSettleTimeBetween(Date value1, Date value2) {
            addCriterion("order_settle_time between", value1, value2, "orderSettleTime");
            return (Criteria) this;
        }

        public Criteria andOrderSettleTimeNotBetween(Date value1, Date value2) {
            addCriterion("order_settle_time not between", value1, value2, "orderSettleTime");
            return (Criteria) this;
        }

        public Criteria andTranTypeIsNull() {
            addCriterion("tran_type is null");
            return (Criteria) this;
        }

        public Criteria andTranTypeIsNotNull() {
            addCriterion("tran_type is not null");
            return (Criteria) this;
        }

        public Criteria andTranTypeEqualTo(String value) {
            addCriterion("tran_type =", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeNotEqualTo(String value) {
            addCriterion("tran_type <>", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeGreaterThan(String value) {
            addCriterion("tran_type >", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeGreaterThanOrEqualTo(String value) {
            addCriterion("tran_type >=", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeLessThan(String value) {
            addCriterion("tran_type <", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeLessThanOrEqualTo(String value) {
            addCriterion("tran_type <=", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeLike(String value) {
            addCriterion("tran_type like", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeNotLike(String value) {
            addCriterion("tran_type not like", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeIn(List<String> values) {
            addCriterion("tran_type in", values, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeNotIn(List<String> values) {
            addCriterion("tran_type not in", values, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeBetween(String value1, String value2) {
            addCriterion("tran_type between", value1, value2, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeNotBetween(String value1, String value2) {
            addCriterion("tran_type not between", value1, value2, "tranType");
            return (Criteria) this;
        }

        public Criteria andOrderSrcIsNull() {
            addCriterion("order_src is null");
            return (Criteria) this;
        }

        public Criteria andOrderSrcIsNotNull() {
            addCriterion("order_src is not null");
            return (Criteria) this;
        }

        public Criteria andOrderSrcEqualTo(String value) {
            addCriterion("order_src =", value, "orderSrc");
            return (Criteria) this;
        }

        public Criteria andOrderSrcNotEqualTo(String value) {
            addCriterion("order_src <>", value, "orderSrc");
            return (Criteria) this;
        }

        public Criteria andOrderSrcGreaterThan(String value) {
            addCriterion("order_src >", value, "orderSrc");
            return (Criteria) this;
        }

        public Criteria andOrderSrcGreaterThanOrEqualTo(String value) {
            addCriterion("order_src >=", value, "orderSrc");
            return (Criteria) this;
        }

        public Criteria andOrderSrcLessThan(String value) {
            addCriterion("order_src <", value, "orderSrc");
            return (Criteria) this;
        }

        public Criteria andOrderSrcLessThanOrEqualTo(String value) {
            addCriterion("order_src <=", value, "orderSrc");
            return (Criteria) this;
        }

        public Criteria andOrderSrcLike(String value) {
            addCriterion("order_src like", value, "orderSrc");
            return (Criteria) this;
        }

        public Criteria andOrderSrcNotLike(String value) {
            addCriterion("order_src not like", value, "orderSrc");
            return (Criteria) this;
        }

        public Criteria andOrderSrcIn(List<String> values) {
            addCriterion("order_src in", values, "orderSrc");
            return (Criteria) this;
        }

        public Criteria andOrderSrcNotIn(List<String> values) {
            addCriterion("order_src not in", values, "orderSrc");
            return (Criteria) this;
        }

        public Criteria andOrderSrcBetween(String value1, String value2) {
            addCriterion("order_src between", value1, value2, "orderSrc");
            return (Criteria) this;
        }

        public Criteria andOrderSrcNotBetween(String value1, String value2) {
            addCriterion("order_src not between", value1, value2, "orderSrc");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeIsNull() {
            addCriterion("currency_type is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeIsNotNull() {
            addCriterion("currency_type is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeEqualTo(String value) {
            addCriterion("currency_type =", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeNotEqualTo(String value) {
            addCriterion("currency_type <>", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeGreaterThan(String value) {
            addCriterion("currency_type >", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeGreaterThanOrEqualTo(String value) {
            addCriterion("currency_type >=", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeLessThan(String value) {
            addCriterion("currency_type <", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeLessThanOrEqualTo(String value) {
            addCriterion("currency_type <=", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeLike(String value) {
            addCriterion("currency_type like", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeNotLike(String value) {
            addCriterion("currency_type not like", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeIn(List<String> values) {
            addCriterion("currency_type in", values, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeNotIn(List<String> values) {
            addCriterion("currency_type not in", values, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeBetween(String value1, String value2) {
            addCriterion("currency_type between", value1, value2, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeNotBetween(String value1, String value2) {
            addCriterion("currency_type not between", value1, value2, "currencyType");
            return (Criteria) this;
        }

        public Criteria andTranAmountIsNull() {
            addCriterion("tran_amount is null");
            return (Criteria) this;
        }

        public Criteria andTranAmountIsNotNull() {
            addCriterion("tran_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTranAmountEqualTo(Double value) {
            addCriterion("tran_amount =", value, "tranAmount");
            return (Criteria) this;
        }

        public Criteria andTranAmountNotEqualTo(Double value) {
            addCriterion("tran_amount <>", value, "tranAmount");
            return (Criteria) this;
        }

        public Criteria andTranAmountGreaterThan(Double value) {
            addCriterion("tran_amount >", value, "tranAmount");
            return (Criteria) this;
        }

        public Criteria andTranAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("tran_amount >=", value, "tranAmount");
            return (Criteria) this;
        }

        public Criteria andTranAmountLessThan(Double value) {
            addCriterion("tran_amount <", value, "tranAmount");
            return (Criteria) this;
        }

        public Criteria andTranAmountLessThanOrEqualTo(Double value) {
            addCriterion("tran_amount <=", value, "tranAmount");
            return (Criteria) this;
        }

        public Criteria andTranAmountIn(List<Double> values) {
            addCriterion("tran_amount in", values, "tranAmount");
            return (Criteria) this;
        }

        public Criteria andTranAmountNotIn(List<Double> values) {
            addCriterion("tran_amount not in", values, "tranAmount");
            return (Criteria) this;
        }

        public Criteria andTranAmountBetween(Double value1, Double value2) {
            addCriterion("tran_amount between", value1, value2, "tranAmount");
            return (Criteria) this;
        }

        public Criteria andTranAmountNotBetween(Double value1, Double value2) {
            addCriterion("tran_amount not between", value1, value2, "tranAmount");
            return (Criteria) this;
        }

        public Criteria andCommissionIsNull() {
            addCriterion("commission is null");
            return (Criteria) this;
        }

        public Criteria andCommissionIsNotNull() {
            addCriterion("commission is not null");
            return (Criteria) this;
        }

        public Criteria andCommissionEqualTo(Double value) {
            addCriterion("commission =", value, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionNotEqualTo(Double value) {
            addCriterion("commission <>", value, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionGreaterThan(Double value) {
            addCriterion("commission >", value, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionGreaterThanOrEqualTo(Double value) {
            addCriterion("commission >=", value, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionLessThan(Double value) {
            addCriterion("commission <", value, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionLessThanOrEqualTo(Double value) {
            addCriterion("commission <=", value, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionIn(List<Double> values) {
            addCriterion("commission in", values, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionNotIn(List<Double> values) {
            addCriterion("commission not in", values, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionBetween(Double value1, Double value2) {
            addCriterion("commission between", value1, value2, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionNotBetween(Double value1, Double value2) {
            addCriterion("commission not between", value1, value2, "commission");
            return (Criteria) this;
        }

        public Criteria andSettleAmountIsNull() {
            addCriterion("settle_amount is null");
            return (Criteria) this;
        }

        public Criteria andSettleAmountIsNotNull() {
            addCriterion("settle_amount is not null");
            return (Criteria) this;
        }

        public Criteria andSettleAmountEqualTo(Double value) {
            addCriterion("settle_amount =", value, "settleAmount");
            return (Criteria) this;
        }

        public Criteria andSettleAmountNotEqualTo(Double value) {
            addCriterion("settle_amount <>", value, "settleAmount");
            return (Criteria) this;
        }

        public Criteria andSettleAmountGreaterThan(Double value) {
            addCriterion("settle_amount >", value, "settleAmount");
            return (Criteria) this;
        }

        public Criteria andSettleAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("settle_amount >=", value, "settleAmount");
            return (Criteria) this;
        }

        public Criteria andSettleAmountLessThan(Double value) {
            addCriterion("settle_amount <", value, "settleAmount");
            return (Criteria) this;
        }

        public Criteria andSettleAmountLessThanOrEqualTo(Double value) {
            addCriterion("settle_amount <=", value, "settleAmount");
            return (Criteria) this;
        }

        public Criteria andSettleAmountIn(List<Double> values) {
            addCriterion("settle_amount in", values, "settleAmount");
            return (Criteria) this;
        }

        public Criteria andSettleAmountNotIn(List<Double> values) {
            addCriterion("settle_amount not in", values, "settleAmount");
            return (Criteria) this;
        }

        public Criteria andSettleAmountBetween(Double value1, Double value2) {
            addCriterion("settle_amount between", value1, value2, "settleAmount");
            return (Criteria) this;
        }

        public Criteria andSettleAmountNotBetween(Double value1, Double value2) {
            addCriterion("settle_amount not between", value1, value2, "settleAmount");
            return (Criteria) this;
        }

        public Criteria andOrderDescIsNull() {
            addCriterion("order_desc is null");
            return (Criteria) this;
        }

        public Criteria andOrderDescIsNotNull() {
            addCriterion("order_desc is not null");
            return (Criteria) this;
        }

        public Criteria andOrderDescEqualTo(String value) {
            addCriterion("order_desc =", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescNotEqualTo(String value) {
            addCriterion("order_desc <>", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescGreaterThan(String value) {
            addCriterion("order_desc >", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescGreaterThanOrEqualTo(String value) {
            addCriterion("order_desc >=", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescLessThan(String value) {
            addCriterion("order_desc <", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescLessThanOrEqualTo(String value) {
            addCriterion("order_desc <=", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescLike(String value) {
            addCriterion("order_desc like", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescNotLike(String value) {
            addCriterion("order_desc not like", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescIn(List<String> values) {
            addCriterion("order_desc in", values, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescNotIn(List<String> values) {
            addCriterion("order_desc not in", values, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescBetween(String value1, String value2) {
            addCriterion("order_desc between", value1, value2, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescNotBetween(String value1, String value2) {
            addCriterion("order_desc not between", value1, value2, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyNoteIsNull() {
            addCriterion("trade_company_note is null");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyNoteIsNotNull() {
            addCriterion("trade_company_note is not null");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyNoteEqualTo(String value) {
            addCriterion("trade_company_note =", value, "tradeCompanyNote");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyNoteNotEqualTo(String value) {
            addCriterion("trade_company_note <>", value, "tradeCompanyNote");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyNoteGreaterThan(String value) {
            addCriterion("trade_company_note >", value, "tradeCompanyNote");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyNoteGreaterThanOrEqualTo(String value) {
            addCriterion("trade_company_note >=", value, "tradeCompanyNote");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyNoteLessThan(String value) {
            addCriterion("trade_company_note <", value, "tradeCompanyNote");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyNoteLessThanOrEqualTo(String value) {
            addCriterion("trade_company_note <=", value, "tradeCompanyNote");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyNoteLike(String value) {
            addCriterion("trade_company_note like", value, "tradeCompanyNote");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyNoteNotLike(String value) {
            addCriterion("trade_company_note not like", value, "tradeCompanyNote");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyNoteIn(List<String> values) {
            addCriterion("trade_company_note in", values, "tradeCompanyNote");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyNoteNotIn(List<String> values) {
            addCriterion("trade_company_note not in", values, "tradeCompanyNote");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyNoteBetween(String value1, String value2) {
            addCriterion("trade_company_note between", value1, value2, "tradeCompanyNote");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyNoteNotBetween(String value1, String value2) {
            addCriterion("trade_company_note not between", value1, value2, "tradeCompanyNote");
            return (Criteria) this;
        }

        public Criteria andRefundOrderNoIsNull() {
            addCriterion("refund_order_no is null");
            return (Criteria) this;
        }

        public Criteria andRefundOrderNoIsNotNull() {
            addCriterion("refund_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andRefundOrderNoEqualTo(String value) {
            addCriterion("refund_order_no =", value, "refundOrderNo");
            return (Criteria) this;
        }

        public Criteria andRefundOrderNoNotEqualTo(String value) {
            addCriterion("refund_order_no <>", value, "refundOrderNo");
            return (Criteria) this;
        }

        public Criteria andRefundOrderNoGreaterThan(String value) {
            addCriterion("refund_order_no >", value, "refundOrderNo");
            return (Criteria) this;
        }

        public Criteria andRefundOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("refund_order_no >=", value, "refundOrderNo");
            return (Criteria) this;
        }

        public Criteria andRefundOrderNoLessThan(String value) {
            addCriterion("refund_order_no <", value, "refundOrderNo");
            return (Criteria) this;
        }

        public Criteria andRefundOrderNoLessThanOrEqualTo(String value) {
            addCriterion("refund_order_no <=", value, "refundOrderNo");
            return (Criteria) this;
        }

        public Criteria andRefundOrderNoLike(String value) {
            addCriterion("refund_order_no like", value, "refundOrderNo");
            return (Criteria) this;
        }

        public Criteria andRefundOrderNoNotLike(String value) {
            addCriterion("refund_order_no not like", value, "refundOrderNo");
            return (Criteria) this;
        }

        public Criteria andRefundOrderNoIn(List<String> values) {
            addCriterion("refund_order_no in", values, "refundOrderNo");
            return (Criteria) this;
        }

        public Criteria andRefundOrderNoNotIn(List<String> values) {
            addCriterion("refund_order_no not in", values, "refundOrderNo");
            return (Criteria) this;
        }

        public Criteria andRefundOrderNoBetween(String value1, String value2) {
            addCriterion("refund_order_no between", value1, value2, "refundOrderNo");
            return (Criteria) this;
        }

        public Criteria andRefundOrderNoNotBetween(String value1, String value2) {
            addCriterion("refund_order_no not between", value1, value2, "refundOrderNo");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeIsNull() {
            addCriterion("refund_create_time is null");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeIsNotNull() {
            addCriterion("refund_create_time is not null");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeEqualTo(Date value) {
            addCriterion("refund_create_time =", value, "refundCreateTime");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeNotEqualTo(Date value) {
            addCriterion("refund_create_time <>", value, "refundCreateTime");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeGreaterThan(Date value) {
            addCriterion("refund_create_time >", value, "refundCreateTime");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("refund_create_time >=", value, "refundCreateTime");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeLessThan(Date value) {
            addCriterion("refund_create_time <", value, "refundCreateTime");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("refund_create_time <=", value, "refundCreateTime");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeIn(List<Date> values) {
            addCriterion("refund_create_time in", values, "refundCreateTime");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeNotIn(List<Date> values) {
            addCriterion("refund_create_time not in", values, "refundCreateTime");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeBetween(Date value1, Date value2) {
            addCriterion("refund_create_time between", value1, value2, "refundCreateTime");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("refund_create_time not between", value1, value2, "refundCreateTime");
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