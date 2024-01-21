package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsSysReceiveMoneyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsSysReceiveMoneyExample() {
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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
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

        public Criteria andPayReqTimeIsNull() {
            addCriterion("pay_req_time is null");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeIsNotNull() {
            addCriterion("pay_req_time is not null");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeEqualTo(Date value) {
            addCriterion("pay_req_time =", value, "payReqTime");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeNotEqualTo(Date value) {
            addCriterion("pay_req_time <>", value, "payReqTime");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeGreaterThan(Date value) {
            addCriterion("pay_req_time >", value, "payReqTime");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("pay_req_time >=", value, "payReqTime");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeLessThan(Date value) {
            addCriterion("pay_req_time <", value, "payReqTime");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeLessThanOrEqualTo(Date value) {
            addCriterion("pay_req_time <=", value, "payReqTime");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeIn(List<Date> values) {
            addCriterion("pay_req_time in", values, "payReqTime");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeNotIn(List<Date> values) {
            addCriterion("pay_req_time not in", values, "payReqTime");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeBetween(Date value1, Date value2) {
            addCriterion("pay_req_time between", value1, value2, "payReqTime");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeNotBetween(Date value1, Date value2) {
            addCriterion("pay_req_time not between", value1, value2, "payReqTime");
            return (Criteria) this;
        }

        public Criteria andPayFinshTimeIsNull() {
            addCriterion("pay_finsh_time is null");
            return (Criteria) this;
        }

        public Criteria andPayFinshTimeIsNotNull() {
            addCriterion("pay_finsh_time is not null");
            return (Criteria) this;
        }

        public Criteria andPayFinshTimeEqualTo(Date value) {
            addCriterion("pay_finsh_time =", value, "payFinshTime");
            return (Criteria) this;
        }

        public Criteria andPayFinshTimeNotEqualTo(Date value) {
            addCriterion("pay_finsh_time <>", value, "payFinshTime");
            return (Criteria) this;
        }

        public Criteria andPayFinshTimeGreaterThan(Date value) {
            addCriterion("pay_finsh_time >", value, "payFinshTime");
            return (Criteria) this;
        }

        public Criteria andPayFinshTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("pay_finsh_time >=", value, "payFinshTime");
            return (Criteria) this;
        }

        public Criteria andPayFinshTimeLessThan(Date value) {
            addCriterion("pay_finsh_time <", value, "payFinshTime");
            return (Criteria) this;
        }

        public Criteria andPayFinshTimeLessThanOrEqualTo(Date value) {
            addCriterion("pay_finsh_time <=", value, "payFinshTime");
            return (Criteria) this;
        }

        public Criteria andPayFinshTimeIn(List<Date> values) {
            addCriterion("pay_finsh_time in", values, "payFinshTime");
            return (Criteria) this;
        }

        public Criteria andPayFinshTimeNotIn(List<Date> values) {
            addCriterion("pay_finsh_time not in", values, "payFinshTime");
            return (Criteria) this;
        }

        public Criteria andPayFinshTimeBetween(Date value1, Date value2) {
            addCriterion("pay_finsh_time between", value1, value2, "payFinshTime");
            return (Criteria) this;
        }

        public Criteria andPayFinshTimeNotBetween(Date value1, Date value2) {
            addCriterion("pay_finsh_time not between", value1, value2, "payFinshTime");
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

        public Criteria andProductOrderNoIsNull() {
            addCriterion("product_order_no is null");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoIsNotNull() {
            addCriterion("product_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoEqualTo(String value) {
            addCriterion("product_order_no =", value, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoNotEqualTo(String value) {
            addCriterion("product_order_no <>", value, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoGreaterThan(String value) {
            addCriterion("product_order_no >", value, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("product_order_no >=", value, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoLessThan(String value) {
            addCriterion("product_order_no <", value, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoLessThanOrEqualTo(String value) {
            addCriterion("product_order_no <=", value, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoLike(String value) {
            addCriterion("product_order_no like", value, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoNotLike(String value) {
            addCriterion("product_order_no not like", value, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoIn(List<String> values) {
            addCriterion("product_order_no in", values, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoNotIn(List<String> values) {
            addCriterion("product_order_no not in", values, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoBetween(String value1, String value2) {
            addCriterion("product_order_no between", value1, value2, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductOrderNoNotBetween(String value1, String value2) {
            addCriterion("product_order_no not between", value1, value2, "productOrderNo");
            return (Criteria) this;
        }

        public Criteria andProductCodeIsNull() {
            addCriterion("product_code is null");
            return (Criteria) this;
        }

        public Criteria andProductCodeIsNotNull() {
            addCriterion("product_code is not null");
            return (Criteria) this;
        }

        public Criteria andProductCodeEqualTo(String value) {
            addCriterion("product_code =", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotEqualTo(String value) {
            addCriterion("product_code <>", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeGreaterThan(String value) {
            addCriterion("product_code >", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeGreaterThanOrEqualTo(String value) {
            addCriterion("product_code >=", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLessThan(String value) {
            addCriterion("product_code <", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLessThanOrEqualTo(String value) {
            addCriterion("product_code <=", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLike(String value) {
            addCriterion("product_code like", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotLike(String value) {
            addCriterion("product_code not like", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeIn(List<String> values) {
            addCriterion("product_code in", values, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotIn(List<String> values) {
            addCriterion("product_code not in", values, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeBetween(String value1, String value2) {
            addCriterion("product_code between", value1, value2, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotBetween(String value1, String value2) {
            addCriterion("product_code not between", value1, value2, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductAmountIsNull() {
            addCriterion("product_amount is null");
            return (Criteria) this;
        }

        public Criteria andProductAmountIsNotNull() {
            addCriterion("product_amount is not null");
            return (Criteria) this;
        }

        public Criteria andProductAmountEqualTo(Double value) {
            addCriterion("product_amount =", value, "productAmount");
            return (Criteria) this;
        }

        public Criteria andProductAmountNotEqualTo(Double value) {
            addCriterion("product_amount <>", value, "productAmount");
            return (Criteria) this;
        }

        public Criteria andProductAmountGreaterThan(Double value) {
            addCriterion("product_amount >", value, "productAmount");
            return (Criteria) this;
        }

        public Criteria andProductAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("product_amount >=", value, "productAmount");
            return (Criteria) this;
        }

        public Criteria andProductAmountLessThan(Double value) {
            addCriterion("product_amount <", value, "productAmount");
            return (Criteria) this;
        }

        public Criteria andProductAmountLessThanOrEqualTo(Double value) {
            addCriterion("product_amount <=", value, "productAmount");
            return (Criteria) this;
        }

        public Criteria andProductAmountIn(List<Double> values) {
            addCriterion("product_amount in", values, "productAmount");
            return (Criteria) this;
        }

        public Criteria andProductAmountNotIn(List<Double> values) {
            addCriterion("product_amount not in", values, "productAmount");
            return (Criteria) this;
        }

        public Criteria andProductAmountBetween(Double value1, Double value2) {
            addCriterion("product_amount between", value1, value2, "productAmount");
            return (Criteria) this;
        }

        public Criteria andProductAmountNotBetween(Double value1, Double value2) {
            addCriterion("product_amount not between", value1, value2, "productAmount");
            return (Criteria) this;
        }

        public Criteria andProductInterestIsNull() {
            addCriterion("product_interest is null");
            return (Criteria) this;
        }

        public Criteria andProductInterestIsNotNull() {
            addCriterion("product_interest is not null");
            return (Criteria) this;
        }

        public Criteria andProductInterestEqualTo(Double value) {
            addCriterion("product_interest =", value, "productInterest");
            return (Criteria) this;
        }

        public Criteria andProductInterestNotEqualTo(Double value) {
            addCriterion("product_interest <>", value, "productInterest");
            return (Criteria) this;
        }

        public Criteria andProductInterestGreaterThan(Double value) {
            addCriterion("product_interest >", value, "productInterest");
            return (Criteria) this;
        }

        public Criteria andProductInterestGreaterThanOrEqualTo(Double value) {
            addCriterion("product_interest >=", value, "productInterest");
            return (Criteria) this;
        }

        public Criteria andProductInterestLessThan(Double value) {
            addCriterion("product_interest <", value, "productInterest");
            return (Criteria) this;
        }

        public Criteria andProductInterestLessThanOrEqualTo(Double value) {
            addCriterion("product_interest <=", value, "productInterest");
            return (Criteria) this;
        }

        public Criteria andProductInterestIn(List<Double> values) {
            addCriterion("product_interest in", values, "productInterest");
            return (Criteria) this;
        }

        public Criteria andProductInterestNotIn(List<Double> values) {
            addCriterion("product_interest not in", values, "productInterest");
            return (Criteria) this;
        }

        public Criteria andProductInterestBetween(Double value1, Double value2) {
            addCriterion("product_interest between", value1, value2, "productInterest");
            return (Criteria) this;
        }

        public Criteria andProductInterestNotBetween(Double value1, Double value2) {
            addCriterion("product_interest not between", value1, value2, "productInterest");
            return (Criteria) this;
        }

        public Criteria andProductReturnTermIsNull() {
            addCriterion("product_return_term is null");
            return (Criteria) this;
        }

        public Criteria andProductReturnTermIsNotNull() {
            addCriterion("product_return_term is not null");
            return (Criteria) this;
        }

        public Criteria andProductReturnTermEqualTo(String value) {
            addCriterion("product_return_term =", value, "productReturnTerm");
            return (Criteria) this;
        }

        public Criteria andProductReturnTermNotEqualTo(String value) {
            addCriterion("product_return_term <>", value, "productReturnTerm");
            return (Criteria) this;
        }

        public Criteria andProductReturnTermGreaterThan(String value) {
            addCriterion("product_return_term >", value, "productReturnTerm");
            return (Criteria) this;
        }

        public Criteria andProductReturnTermGreaterThanOrEqualTo(String value) {
            addCriterion("product_return_term >=", value, "productReturnTerm");
            return (Criteria) this;
        }

        public Criteria andProductReturnTermLessThan(String value) {
            addCriterion("product_return_term <", value, "productReturnTerm");
            return (Criteria) this;
        }

        public Criteria andProductReturnTermLessThanOrEqualTo(String value) {
            addCriterion("product_return_term <=", value, "productReturnTerm");
            return (Criteria) this;
        }

        public Criteria andProductReturnTermLike(String value) {
            addCriterion("product_return_term like", value, "productReturnTerm");
            return (Criteria) this;
        }

        public Criteria andProductReturnTermNotLike(String value) {
            addCriterion("product_return_term not like", value, "productReturnTerm");
            return (Criteria) this;
        }

        public Criteria andProductReturnTermIn(List<String> values) {
            addCriterion("product_return_term in", values, "productReturnTerm");
            return (Criteria) this;
        }

        public Criteria andProductReturnTermNotIn(List<String> values) {
            addCriterion("product_return_term not in", values, "productReturnTerm");
            return (Criteria) this;
        }

        public Criteria andProductReturnTermBetween(String value1, String value2) {
            addCriterion("product_return_term between", value1, value2, "productReturnTerm");
            return (Criteria) this;
        }

        public Criteria andProductReturnTermNotBetween(String value1, String value2) {
            addCriterion("product_return_term not between", value1, value2, "productReturnTerm");
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