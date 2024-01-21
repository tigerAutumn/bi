package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class LnRepeatRepayRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LnRepeatRepayRecordExample() {
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

        public Criteria andRepayPlanIdIsNull() {
            addCriterion("repay_plan_id is null");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdIsNotNull() {
            addCriterion("repay_plan_id is not null");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdEqualTo(Integer value) {
            addCriterion("repay_plan_id =", value, "repayPlanId");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdNotEqualTo(Integer value) {
            addCriterion("repay_plan_id <>", value, "repayPlanId");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdGreaterThan(Integer value) {
            addCriterion("repay_plan_id >", value, "repayPlanId");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_plan_id >=", value, "repayPlanId");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdLessThan(Integer value) {
            addCriterion("repay_plan_id <", value, "repayPlanId");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdLessThanOrEqualTo(Integer value) {
            addCriterion("repay_plan_id <=", value, "repayPlanId");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdIn(List<Integer> values) {
            addCriterion("repay_plan_id in", values, "repayPlanId");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdNotIn(List<Integer> values) {
            addCriterion("repay_plan_id not in", values, "repayPlanId");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdBetween(Integer value1, Integer value2) {
            addCriterion("repay_plan_id between", value1, value2, "repayPlanId");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIdNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_plan_id not between", value1, value2, "repayPlanId");
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

        public Criteria andRepayOrderNoIsNull() {
            addCriterion("repay_order_no is null");
            return (Criteria) this;
        }

        public Criteria andRepayOrderNoIsNotNull() {
            addCriterion("repay_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andRepayOrderNoEqualTo(String value) {
            addCriterion("repay_order_no =", value, "repayOrderNo");
            return (Criteria) this;
        }

        public Criteria andRepayOrderNoNotEqualTo(String value) {
            addCriterion("repay_order_no <>", value, "repayOrderNo");
            return (Criteria) this;
        }

        public Criteria andRepayOrderNoGreaterThan(String value) {
            addCriterion("repay_order_no >", value, "repayOrderNo");
            return (Criteria) this;
        }

        public Criteria andRepayOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("repay_order_no >=", value, "repayOrderNo");
            return (Criteria) this;
        }

        public Criteria andRepayOrderNoLessThan(String value) {
            addCriterion("repay_order_no <", value, "repayOrderNo");
            return (Criteria) this;
        }

        public Criteria andRepayOrderNoLessThanOrEqualTo(String value) {
            addCriterion("repay_order_no <=", value, "repayOrderNo");
            return (Criteria) this;
        }

        public Criteria andRepayOrderNoLike(String value) {
            addCriterion("repay_order_no like", value, "repayOrderNo");
            return (Criteria) this;
        }

        public Criteria andRepayOrderNoNotLike(String value) {
            addCriterion("repay_order_no not like", value, "repayOrderNo");
            return (Criteria) this;
        }

        public Criteria andRepayOrderNoIn(List<String> values) {
            addCriterion("repay_order_no in", values, "repayOrderNo");
            return (Criteria) this;
        }

        public Criteria andRepayOrderNoNotIn(List<String> values) {
            addCriterion("repay_order_no not in", values, "repayOrderNo");
            return (Criteria) this;
        }

        public Criteria andRepayOrderNoBetween(String value1, String value2) {
            addCriterion("repay_order_no between", value1, value2, "repayOrderNo");
            return (Criteria) this;
        }

        public Criteria andRepayOrderNoNotBetween(String value1, String value2) {
            addCriterion("repay_order_no not between", value1, value2, "repayOrderNo");
            return (Criteria) this;
        }

        public Criteria andRepayTypeIsNull() {
            addCriterion("repay_type is null");
            return (Criteria) this;
        }

        public Criteria andRepayTypeIsNotNull() {
            addCriterion("repay_type is not null");
            return (Criteria) this;
        }

        public Criteria andRepayTypeEqualTo(String value) {
            addCriterion("repay_type =", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeNotEqualTo(String value) {
            addCriterion("repay_type <>", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeGreaterThan(String value) {
            addCriterion("repay_type >", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeGreaterThanOrEqualTo(String value) {
            addCriterion("repay_type >=", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeLessThan(String value) {
            addCriterion("repay_type <", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeLessThanOrEqualTo(String value) {
            addCriterion("repay_type <=", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeLike(String value) {
            addCriterion("repay_type like", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeNotLike(String value) {
            addCriterion("repay_type not like", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeIn(List<String> values) {
            addCriterion("repay_type in", values, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeNotIn(List<String> values) {
            addCriterion("repay_type not in", values, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeBetween(String value1, String value2) {
            addCriterion("repay_type between", value1, value2, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeNotBetween(String value1, String value2) {
            addCriterion("repay_type not between", value1, value2, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayAmountIsNull() {
            addCriterion("repay_amount is null");
            return (Criteria) this;
        }

        public Criteria andRepayAmountIsNotNull() {
            addCriterion("repay_amount is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAmountEqualTo(Double value) {
            addCriterion("repay_amount =", value, "repayAmount");
            return (Criteria) this;
        }

        public Criteria andRepayAmountNotEqualTo(Double value) {
            addCriterion("repay_amount <>", value, "repayAmount");
            return (Criteria) this;
        }

        public Criteria andRepayAmountGreaterThan(Double value) {
            addCriterion("repay_amount >", value, "repayAmount");
            return (Criteria) this;
        }

        public Criteria andRepayAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("repay_amount >=", value, "repayAmount");
            return (Criteria) this;
        }

        public Criteria andRepayAmountLessThan(Double value) {
            addCriterion("repay_amount <", value, "repayAmount");
            return (Criteria) this;
        }

        public Criteria andRepayAmountLessThanOrEqualTo(Double value) {
            addCriterion("repay_amount <=", value, "repayAmount");
            return (Criteria) this;
        }

        public Criteria andRepayAmountIn(List<Double> values) {
            addCriterion("repay_amount in", values, "repayAmount");
            return (Criteria) this;
        }

        public Criteria andRepayAmountNotIn(List<Double> values) {
            addCriterion("repay_amount not in", values, "repayAmount");
            return (Criteria) this;
        }

        public Criteria andRepayAmountBetween(Double value1, Double value2) {
            addCriterion("repay_amount between", value1, value2, "repayAmount");
            return (Criteria) this;
        }

        public Criteria andRepayAmountNotBetween(Double value1, Double value2) {
            addCriterion("repay_amount not between", value1, value2, "repayAmount");
            return (Criteria) this;
        }

        public Criteria andReturnAmountIsNull() {
            addCriterion("return_amount is null");
            return (Criteria) this;
        }

        public Criteria andReturnAmountIsNotNull() {
            addCriterion("return_amount is not null");
            return (Criteria) this;
        }

        public Criteria andReturnAmountEqualTo(Double value) {
            addCriterion("return_amount =", value, "returnAmount");
            return (Criteria) this;
        }

        public Criteria andReturnAmountNotEqualTo(Double value) {
            addCriterion("return_amount <>", value, "returnAmount");
            return (Criteria) this;
        }

        public Criteria andReturnAmountGreaterThan(Double value) {
            addCriterion("return_amount >", value, "returnAmount");
            return (Criteria) this;
        }

        public Criteria andReturnAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("return_amount >=", value, "returnAmount");
            return (Criteria) this;
        }

        public Criteria andReturnAmountLessThan(Double value) {
            addCriterion("return_amount <", value, "returnAmount");
            return (Criteria) this;
        }

        public Criteria andReturnAmountLessThanOrEqualTo(Double value) {
            addCriterion("return_amount <=", value, "returnAmount");
            return (Criteria) this;
        }

        public Criteria andReturnAmountIn(List<Double> values) {
            addCriterion("return_amount in", values, "returnAmount");
            return (Criteria) this;
        }

        public Criteria andReturnAmountNotIn(List<Double> values) {
            addCriterion("return_amount not in", values, "returnAmount");
            return (Criteria) this;
        }

        public Criteria andReturnAmountBetween(Double value1, Double value2) {
            addCriterion("return_amount between", value1, value2, "returnAmount");
            return (Criteria) this;
        }

        public Criteria andReturnAmountNotBetween(Double value1, Double value2) {
            addCriterion("return_amount not between", value1, value2, "returnAmount");
            return (Criteria) this;
        }

        public Criteria andSettleDateIsNull() {
            addCriterion("settle_date is null");
            return (Criteria) this;
        }

        public Criteria andSettleDateIsNotNull() {
            addCriterion("settle_date is not null");
            return (Criteria) this;
        }

        public Criteria andSettleDateEqualTo(Date value) {
            addCriterionForJDBCDate("settle_date =", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("settle_date <>", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateGreaterThan(Date value) {
            addCriterionForJDBCDate("settle_date >", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("settle_date >=", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateLessThan(Date value) {
            addCriterionForJDBCDate("settle_date <", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("settle_date <=", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateIn(List<Date> values) {
            addCriterionForJDBCDate("settle_date in", values, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("settle_date not in", values, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("settle_date between", value1, value2, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("settle_date not between", value1, value2, "settleDate");
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