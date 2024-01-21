package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class LnRepayScheduleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LnRepayScheduleExample() {
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

        public Criteria andLoanIdIsNull() {
            addCriterion("loan_id is null");
            return (Criteria) this;
        }

        public Criteria andLoanIdIsNotNull() {
            addCriterion("loan_id is not null");
            return (Criteria) this;
        }

        public Criteria andLoanIdEqualTo(Integer value) {
            addCriterion("loan_id =", value, "loanId");
            return (Criteria) this;
        }

        public Criteria andLoanIdNotEqualTo(Integer value) {
            addCriterion("loan_id <>", value, "loanId");
            return (Criteria) this;
        }

        public Criteria andLoanIdGreaterThan(Integer value) {
            addCriterion("loan_id >", value, "loanId");
            return (Criteria) this;
        }

        public Criteria andLoanIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("loan_id >=", value, "loanId");
            return (Criteria) this;
        }

        public Criteria andLoanIdLessThan(Integer value) {
            addCriterion("loan_id <", value, "loanId");
            return (Criteria) this;
        }

        public Criteria andLoanIdLessThanOrEqualTo(Integer value) {
            addCriterion("loan_id <=", value, "loanId");
            return (Criteria) this;
        }

        public Criteria andLoanIdIn(List<Integer> values) {
            addCriterion("loan_id in", values, "loanId");
            return (Criteria) this;
        }

        public Criteria andLoanIdNotIn(List<Integer> values) {
            addCriterion("loan_id not in", values, "loanId");
            return (Criteria) this;
        }

        public Criteria andLoanIdBetween(Integer value1, Integer value2) {
            addCriterion("loan_id between", value1, value2, "loanId");
            return (Criteria) this;
        }

        public Criteria andLoanIdNotBetween(Integer value1, Integer value2) {
            addCriterion("loan_id not between", value1, value2, "loanId");
            return (Criteria) this;
        }

        public Criteria andPartnerRepayIdIsNull() {
            addCriterion("partner_repay_id is null");
            return (Criteria) this;
        }

        public Criteria andPartnerRepayIdIsNotNull() {
            addCriterion("partner_repay_id is not null");
            return (Criteria) this;
        }

        public Criteria andPartnerRepayIdEqualTo(String value) {
            addCriterion("partner_repay_id =", value, "partnerRepayId");
            return (Criteria) this;
        }

        public Criteria andPartnerRepayIdNotEqualTo(String value) {
            addCriterion("partner_repay_id <>", value, "partnerRepayId");
            return (Criteria) this;
        }

        public Criteria andPartnerRepayIdGreaterThan(String value) {
            addCriterion("partner_repay_id >", value, "partnerRepayId");
            return (Criteria) this;
        }

        public Criteria andPartnerRepayIdGreaterThanOrEqualTo(String value) {
            addCriterion("partner_repay_id >=", value, "partnerRepayId");
            return (Criteria) this;
        }

        public Criteria andPartnerRepayIdLessThan(String value) {
            addCriterion("partner_repay_id <", value, "partnerRepayId");
            return (Criteria) this;
        }

        public Criteria andPartnerRepayIdLessThanOrEqualTo(String value) {
            addCriterion("partner_repay_id <=", value, "partnerRepayId");
            return (Criteria) this;
        }

        public Criteria andPartnerRepayIdLike(String value) {
            addCriterion("partner_repay_id like", value, "partnerRepayId");
            return (Criteria) this;
        }

        public Criteria andPartnerRepayIdNotLike(String value) {
            addCriterion("partner_repay_id not like", value, "partnerRepayId");
            return (Criteria) this;
        }

        public Criteria andPartnerRepayIdIn(List<String> values) {
            addCriterion("partner_repay_id in", values, "partnerRepayId");
            return (Criteria) this;
        }

        public Criteria andPartnerRepayIdNotIn(List<String> values) {
            addCriterion("partner_repay_id not in", values, "partnerRepayId");
            return (Criteria) this;
        }

        public Criteria andPartnerRepayIdBetween(String value1, String value2) {
            addCriterion("partner_repay_id between", value1, value2, "partnerRepayId");
            return (Criteria) this;
        }

        public Criteria andPartnerRepayIdNotBetween(String value1, String value2) {
            addCriterion("partner_repay_id not between", value1, value2, "partnerRepayId");
            return (Criteria) this;
        }

        public Criteria andSerialIdIsNull() {
            addCriterion("serial_id is null");
            return (Criteria) this;
        }

        public Criteria andSerialIdIsNotNull() {
            addCriterion("serial_id is not null");
            return (Criteria) this;
        }

        public Criteria andSerialIdEqualTo(Integer value) {
            addCriterion("serial_id =", value, "serialId");
            return (Criteria) this;
        }

        public Criteria andSerialIdNotEqualTo(Integer value) {
            addCriterion("serial_id <>", value, "serialId");
            return (Criteria) this;
        }

        public Criteria andSerialIdGreaterThan(Integer value) {
            addCriterion("serial_id >", value, "serialId");
            return (Criteria) this;
        }

        public Criteria andSerialIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("serial_id >=", value, "serialId");
            return (Criteria) this;
        }

        public Criteria andSerialIdLessThan(Integer value) {
            addCriterion("serial_id <", value, "serialId");
            return (Criteria) this;
        }

        public Criteria andSerialIdLessThanOrEqualTo(Integer value) {
            addCriterion("serial_id <=", value, "serialId");
            return (Criteria) this;
        }

        public Criteria andSerialIdIn(List<Integer> values) {
            addCriterion("serial_id in", values, "serialId");
            return (Criteria) this;
        }

        public Criteria andSerialIdNotIn(List<Integer> values) {
            addCriterion("serial_id not in", values, "serialId");
            return (Criteria) this;
        }

        public Criteria andSerialIdBetween(Integer value1, Integer value2) {
            addCriterion("serial_id between", value1, value2, "serialId");
            return (Criteria) this;
        }

        public Criteria andSerialIdNotBetween(Integer value1, Integer value2) {
            addCriterion("serial_id not between", value1, value2, "serialId");
            return (Criteria) this;
        }

        public Criteria andPlanDateIsNull() {
            addCriterion("plan_date is null");
            return (Criteria) this;
        }

        public Criteria andPlanDateIsNotNull() {
            addCriterion("plan_date is not null");
            return (Criteria) this;
        }

        public Criteria andPlanDateEqualTo(Date value) {
            addCriterionForJDBCDate("plan_date =", value, "planDate");
            return (Criteria) this;
        }

        public Criteria andPlanDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("plan_date <>", value, "planDate");
            return (Criteria) this;
        }

        public Criteria andPlanDateGreaterThan(Date value) {
            addCriterionForJDBCDate("plan_date >", value, "planDate");
            return (Criteria) this;
        }

        public Criteria andPlanDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("plan_date >=", value, "planDate");
            return (Criteria) this;
        }

        public Criteria andPlanDateLessThan(Date value) {
            addCriterionForJDBCDate("plan_date <", value, "planDate");
            return (Criteria) this;
        }

        public Criteria andPlanDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("plan_date <=", value, "planDate");
            return (Criteria) this;
        }

        public Criteria andPlanDateIn(List<Date> values) {
            addCriterionForJDBCDate("plan_date in", values, "planDate");
            return (Criteria) this;
        }

        public Criteria andPlanDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("plan_date not in", values, "planDate");
            return (Criteria) this;
        }

        public Criteria andPlanDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("plan_date between", value1, value2, "planDate");
            return (Criteria) this;
        }

        public Criteria andPlanDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("plan_date not between", value1, value2, "planDate");
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

        public Criteria andPlanTotalIsNull() {
            addCriterion("plan_total is null");
            return (Criteria) this;
        }

        public Criteria andPlanTotalIsNotNull() {
            addCriterion("plan_total is not null");
            return (Criteria) this;
        }

        public Criteria andPlanTotalEqualTo(Double value) {
            addCriterion("plan_total =", value, "planTotal");
            return (Criteria) this;
        }

        public Criteria andPlanTotalNotEqualTo(Double value) {
            addCriterion("plan_total <>", value, "planTotal");
            return (Criteria) this;
        }

        public Criteria andPlanTotalGreaterThan(Double value) {
            addCriterion("plan_total >", value, "planTotal");
            return (Criteria) this;
        }

        public Criteria andPlanTotalGreaterThanOrEqualTo(Double value) {
            addCriterion("plan_total >=", value, "planTotal");
            return (Criteria) this;
        }

        public Criteria andPlanTotalLessThan(Double value) {
            addCriterion("plan_total <", value, "planTotal");
            return (Criteria) this;
        }

        public Criteria andPlanTotalLessThanOrEqualTo(Double value) {
            addCriterion("plan_total <=", value, "planTotal");
            return (Criteria) this;
        }

        public Criteria andPlanTotalIn(List<Double> values) {
            addCriterion("plan_total in", values, "planTotal");
            return (Criteria) this;
        }

        public Criteria andPlanTotalNotIn(List<Double> values) {
            addCriterion("plan_total not in", values, "planTotal");
            return (Criteria) this;
        }

        public Criteria andPlanTotalBetween(Double value1, Double value2) {
            addCriterion("plan_total between", value1, value2, "planTotal");
            return (Criteria) this;
        }

        public Criteria andPlanTotalNotBetween(Double value1, Double value2) {
            addCriterion("plan_total not between", value1, value2, "planTotal");
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

        public Criteria andReturnFlagIsNull() {
            addCriterion("return_flag is null");
            return (Criteria) this;
        }

        public Criteria andReturnFlagIsNotNull() {
            addCriterion("return_flag is not null");
            return (Criteria) this;
        }

        public Criteria andReturnFlagEqualTo(String value) {
            addCriterion("return_flag =", value, "returnFlag");
            return (Criteria) this;
        }

        public Criteria andReturnFlagNotEqualTo(String value) {
            addCriterion("return_flag <>", value, "returnFlag");
            return (Criteria) this;
        }

        public Criteria andReturnFlagGreaterThan(String value) {
            addCriterion("return_flag >", value, "returnFlag");
            return (Criteria) this;
        }

        public Criteria andReturnFlagGreaterThanOrEqualTo(String value) {
            addCriterion("return_flag >=", value, "returnFlag");
            return (Criteria) this;
        }

        public Criteria andReturnFlagLessThan(String value) {
            addCriterion("return_flag <", value, "returnFlag");
            return (Criteria) this;
        }

        public Criteria andReturnFlagLessThanOrEqualTo(String value) {
            addCriterion("return_flag <=", value, "returnFlag");
            return (Criteria) this;
        }

        public Criteria andReturnFlagLike(String value) {
            addCriterion("return_flag like", value, "returnFlag");
            return (Criteria) this;
        }

        public Criteria andReturnFlagNotLike(String value) {
            addCriterion("return_flag not like", value, "returnFlag");
            return (Criteria) this;
        }

        public Criteria andReturnFlagIn(List<String> values) {
            addCriterion("return_flag in", values, "returnFlag");
            return (Criteria) this;
        }

        public Criteria andReturnFlagNotIn(List<String> values) {
            addCriterion("return_flag not in", values, "returnFlag");
            return (Criteria) this;
        }

        public Criteria andReturnFlagBetween(String value1, String value2) {
            addCriterion("return_flag between", value1, value2, "returnFlag");
            return (Criteria) this;
        }

        public Criteria andReturnFlagNotBetween(String value1, String value2) {
            addCriterion("return_flag not between", value1, value2, "returnFlag");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoIsNull() {
            addCriterion("pay_order_no is null");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoIsNotNull() {
            addCriterion("pay_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoEqualTo(String value) {
            addCriterion("pay_order_no =", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoNotEqualTo(String value) {
            addCriterion("pay_order_no <>", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoGreaterThan(String value) {
            addCriterion("pay_order_no >", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("pay_order_no >=", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoLessThan(String value) {
            addCriterion("pay_order_no <", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoLessThanOrEqualTo(String value) {
            addCriterion("pay_order_no <=", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoLike(String value) {
            addCriterion("pay_order_no like", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoNotLike(String value) {
            addCriterion("pay_order_no not like", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoIn(List<String> values) {
            addCriterion("pay_order_no in", values, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoNotIn(List<String> values) {
            addCriterion("pay_order_no not in", values, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoBetween(String value1, String value2) {
            addCriterion("pay_order_no between", value1, value2, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoNotBetween(String value1, String value2) {
            addCriterion("pay_order_no not between", value1, value2, "payOrderNo");
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