package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BsBatchBuyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsBatchBuyExample() {
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

        public Criteria andPropertySymbolIsNull() {
            addCriterion("property_symbol is null");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolIsNotNull() {
            addCriterion("property_symbol is not null");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolEqualTo(String value) {
            addCriterion("property_symbol =", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolNotEqualTo(String value) {
            addCriterion("property_symbol <>", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolGreaterThan(String value) {
            addCriterion("property_symbol >", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolGreaterThanOrEqualTo(String value) {
            addCriterion("property_symbol >=", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolLessThan(String value) {
            addCriterion("property_symbol <", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolLessThanOrEqualTo(String value) {
            addCriterion("property_symbol <=", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolLike(String value) {
            addCriterion("property_symbol like", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolNotLike(String value) {
            addCriterion("property_symbol not like", value, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolIn(List<String> values) {
            addCriterion("property_symbol in", values, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolNotIn(List<String> values) {
            addCriterion("property_symbol not in", values, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolBetween(String value1, String value2) {
            addCriterion("property_symbol between", value1, value2, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolNotBetween(String value1, String value2) {
            addCriterion("property_symbol not between", value1, value2, "propertySymbol");
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

        public Criteria andSendAmountIsNull() {
            addCriterion("send_amount is null");
            return (Criteria) this;
        }

        public Criteria andSendAmountIsNotNull() {
            addCriterion("send_amount is not null");
            return (Criteria) this;
        }

        public Criteria andSendAmountEqualTo(Double value) {
            addCriterion("send_amount =", value, "sendAmount");
            return (Criteria) this;
        }

        public Criteria andSendAmountNotEqualTo(Double value) {
            addCriterion("send_amount <>", value, "sendAmount");
            return (Criteria) this;
        }

        public Criteria andSendAmountGreaterThan(Double value) {
            addCriterion("send_amount >", value, "sendAmount");
            return (Criteria) this;
        }

        public Criteria andSendAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("send_amount >=", value, "sendAmount");
            return (Criteria) this;
        }

        public Criteria andSendAmountLessThan(Double value) {
            addCriterion("send_amount <", value, "sendAmount");
            return (Criteria) this;
        }

        public Criteria andSendAmountLessThanOrEqualTo(Double value) {
            addCriterion("send_amount <=", value, "sendAmount");
            return (Criteria) this;
        }

        public Criteria andSendAmountIn(List<Double> values) {
            addCriterion("send_amount in", values, "sendAmount");
            return (Criteria) this;
        }

        public Criteria andSendAmountNotIn(List<Double> values) {
            addCriterion("send_amount not in", values, "sendAmount");
            return (Criteria) this;
        }

        public Criteria andSendAmountBetween(Double value1, Double value2) {
            addCriterion("send_amount between", value1, value2, "sendAmount");
            return (Criteria) this;
        }

        public Criteria andSendAmountNotBetween(Double value1, Double value2) {
            addCriterion("send_amount not between", value1, value2, "sendAmount");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountIsNull() {
            addCriterion("receive_amount is null");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountIsNotNull() {
            addCriterion("receive_amount is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountEqualTo(Double value) {
            addCriterion("receive_amount =", value, "receiveAmount");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountNotEqualTo(Double value) {
            addCriterion("receive_amount <>", value, "receiveAmount");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountGreaterThan(Double value) {
            addCriterion("receive_amount >", value, "receiveAmount");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("receive_amount >=", value, "receiveAmount");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountLessThan(Double value) {
            addCriterion("receive_amount <", value, "receiveAmount");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountLessThanOrEqualTo(Double value) {
            addCriterion("receive_amount <=", value, "receiveAmount");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountIn(List<Double> values) {
            addCriterion("receive_amount in", values, "receiveAmount");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountNotIn(List<Double> values) {
            addCriterion("receive_amount not in", values, "receiveAmount");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountBetween(Double value1, Double value2) {
            addCriterion("receive_amount between", value1, value2, "receiveAmount");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountNotBetween(Double value1, Double value2) {
            addCriterion("receive_amount not between", value1, value2, "receiveAmount");
            return (Criteria) this;
        }

        public Criteria andDafyRateIsNull() {
            addCriterion("dafy_rate is null");
            return (Criteria) this;
        }

        public Criteria andDafyRateIsNotNull() {
            addCriterion("dafy_rate is not null");
            return (Criteria) this;
        }

        public Criteria andDafyRateEqualTo(Double value) {
            addCriterion("dafy_rate =", value, "dafyRate");
            return (Criteria) this;
        }

        public Criteria andDafyRateNotEqualTo(Double value) {
            addCriterion("dafy_rate <>", value, "dafyRate");
            return (Criteria) this;
        }

        public Criteria andDafyRateGreaterThan(Double value) {
            addCriterion("dafy_rate >", value, "dafyRate");
            return (Criteria) this;
        }

        public Criteria andDafyRateGreaterThanOrEqualTo(Double value) {
            addCriterion("dafy_rate >=", value, "dafyRate");
            return (Criteria) this;
        }

        public Criteria andDafyRateLessThan(Double value) {
            addCriterion("dafy_rate <", value, "dafyRate");
            return (Criteria) this;
        }

        public Criteria andDafyRateLessThanOrEqualTo(Double value) {
            addCriterion("dafy_rate <=", value, "dafyRate");
            return (Criteria) this;
        }

        public Criteria andDafyRateIn(List<Double> values) {
            addCriterion("dafy_rate in", values, "dafyRate");
            return (Criteria) this;
        }

        public Criteria andDafyRateNotIn(List<Double> values) {
            addCriterion("dafy_rate not in", values, "dafyRate");
            return (Criteria) this;
        }

        public Criteria andDafyRateBetween(Double value1, Double value2) {
            addCriterion("dafy_rate between", value1, value2, "dafyRate");
            return (Criteria) this;
        }

        public Criteria andDafyRateNotBetween(Double value1, Double value2) {
            addCriterion("dafy_rate not between", value1, value2, "dafyRate");
            return (Criteria) this;
        }

        public Criteria andSendBatchIdIsNull() {
            addCriterion("send_batch_id is null");
            return (Criteria) this;
        }

        public Criteria andSendBatchIdIsNotNull() {
            addCriterion("send_batch_id is not null");
            return (Criteria) this;
        }

        public Criteria andSendBatchIdEqualTo(String value) {
            addCriterion("send_batch_id =", value, "sendBatchId");
            return (Criteria) this;
        }

        public Criteria andSendBatchIdNotEqualTo(String value) {
            addCriterion("send_batch_id <>", value, "sendBatchId");
            return (Criteria) this;
        }

        public Criteria andSendBatchIdGreaterThan(String value) {
            addCriterion("send_batch_id >", value, "sendBatchId");
            return (Criteria) this;
        }

        public Criteria andSendBatchIdGreaterThanOrEqualTo(String value) {
            addCriterion("send_batch_id >=", value, "sendBatchId");
            return (Criteria) this;
        }

        public Criteria andSendBatchIdLessThan(String value) {
            addCriterion("send_batch_id <", value, "sendBatchId");
            return (Criteria) this;
        }

        public Criteria andSendBatchIdLessThanOrEqualTo(String value) {
            addCriterion("send_batch_id <=", value, "sendBatchId");
            return (Criteria) this;
        }

        public Criteria andSendBatchIdLike(String value) {
            addCriterion("send_batch_id like", value, "sendBatchId");
            return (Criteria) this;
        }

        public Criteria andSendBatchIdNotLike(String value) {
            addCriterion("send_batch_id not like", value, "sendBatchId");
            return (Criteria) this;
        }

        public Criteria andSendBatchIdIn(List<String> values) {
            addCriterion("send_batch_id in", values, "sendBatchId");
            return (Criteria) this;
        }

        public Criteria andSendBatchIdNotIn(List<String> values) {
            addCriterion("send_batch_id not in", values, "sendBatchId");
            return (Criteria) this;
        }

        public Criteria andSendBatchIdBetween(String value1, String value2) {
            addCriterion("send_batch_id between", value1, value2, "sendBatchId");
            return (Criteria) this;
        }

        public Criteria andSendBatchIdNotBetween(String value1, String value2) {
            addCriterion("send_batch_id not between", value1, value2, "sendBatchId");
            return (Criteria) this;
        }

        public Criteria andPay19OrderNoIsNull() {
            addCriterion("pay19_order_no is null");
            return (Criteria) this;
        }

        public Criteria andPay19OrderNoIsNotNull() {
            addCriterion("pay19_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andPay19OrderNoEqualTo(String value) {
            addCriterion("pay19_order_no =", value, "pay19OrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19OrderNoNotEqualTo(String value) {
            addCriterion("pay19_order_no <>", value, "pay19OrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19OrderNoGreaterThan(String value) {
            addCriterion("pay19_order_no >", value, "pay19OrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19OrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("pay19_order_no >=", value, "pay19OrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19OrderNoLessThan(String value) {
            addCriterion("pay19_order_no <", value, "pay19OrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19OrderNoLessThanOrEqualTo(String value) {
            addCriterion("pay19_order_no <=", value, "pay19OrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19OrderNoLike(String value) {
            addCriterion("pay19_order_no like", value, "pay19OrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19OrderNoNotLike(String value) {
            addCriterion("pay19_order_no not like", value, "pay19OrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19OrderNoIn(List<String> values) {
            addCriterion("pay19_order_no in", values, "pay19OrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19OrderNoNotIn(List<String> values) {
            addCriterion("pay19_order_no not in", values, "pay19OrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19OrderNoBetween(String value1, String value2) {
            addCriterion("pay19_order_no between", value1, value2, "pay19OrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19OrderNoNotBetween(String value1, String value2) {
            addCriterion("pay19_order_no not between", value1, value2, "pay19OrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19MpOrderNoIsNull() {
            addCriterion("pay19_mp_order_no is null");
            return (Criteria) this;
        }

        public Criteria andPay19MpOrderNoIsNotNull() {
            addCriterion("pay19_mp_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andPay19MpOrderNoEqualTo(String value) {
            addCriterion("pay19_mp_order_no =", value, "pay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19MpOrderNoNotEqualTo(String value) {
            addCriterion("pay19_mp_order_no <>", value, "pay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19MpOrderNoGreaterThan(String value) {
            addCriterion("pay19_mp_order_no >", value, "pay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19MpOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("pay19_mp_order_no >=", value, "pay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19MpOrderNoLessThan(String value) {
            addCriterion("pay19_mp_order_no <", value, "pay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19MpOrderNoLessThanOrEqualTo(String value) {
            addCriterion("pay19_mp_order_no <=", value, "pay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19MpOrderNoLike(String value) {
            addCriterion("pay19_mp_order_no like", value, "pay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19MpOrderNoNotLike(String value) {
            addCriterion("pay19_mp_order_no not like", value, "pay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19MpOrderNoIn(List<String> values) {
            addCriterion("pay19_mp_order_no in", values, "pay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19MpOrderNoNotIn(List<String> values) {
            addCriterion("pay19_mp_order_no not in", values, "pay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19MpOrderNoBetween(String value1, String value2) {
            addCriterion("pay19_mp_order_no between", value1, value2, "pay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andPay19MpOrderNoNotBetween(String value1, String value2) {
            addCriterion("pay19_mp_order_no not between", value1, value2, "pay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andDafyPay19MpOrderNoIsNull() {
            addCriterion("dafy_pay19_mp_order_no is null");
            return (Criteria) this;
        }

        public Criteria andDafyPay19MpOrderNoIsNotNull() {
            addCriterion("dafy_pay19_mp_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andDafyPay19MpOrderNoEqualTo(String value) {
            addCriterion("dafy_pay19_mp_order_no =", value, "dafyPay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andDafyPay19MpOrderNoNotEqualTo(String value) {
            addCriterion("dafy_pay19_mp_order_no <>", value, "dafyPay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andDafyPay19MpOrderNoGreaterThan(String value) {
            addCriterion("dafy_pay19_mp_order_no >", value, "dafyPay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andDafyPay19MpOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("dafy_pay19_mp_order_no >=", value, "dafyPay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andDafyPay19MpOrderNoLessThan(String value) {
            addCriterion("dafy_pay19_mp_order_no <", value, "dafyPay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andDafyPay19MpOrderNoLessThanOrEqualTo(String value) {
            addCriterion("dafy_pay19_mp_order_no <=", value, "dafyPay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andDafyPay19MpOrderNoLike(String value) {
            addCriterion("dafy_pay19_mp_order_no like", value, "dafyPay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andDafyPay19MpOrderNoNotLike(String value) {
            addCriterion("dafy_pay19_mp_order_no not like", value, "dafyPay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andDafyPay19MpOrderNoIn(List<String> values) {
            addCriterion("dafy_pay19_mp_order_no in", values, "dafyPay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andDafyPay19MpOrderNoNotIn(List<String> values) {
            addCriterion("dafy_pay19_mp_order_no not in", values, "dafyPay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andDafyPay19MpOrderNoBetween(String value1, String value2) {
            addCriterion("dafy_pay19_mp_order_no between", value1, value2, "dafyPay19MpOrderNo");
            return (Criteria) this;
        }

        public Criteria andDafyPay19MpOrderNoNotBetween(String value1, String value2) {
            addCriterion("dafy_pay19_mp_order_no not between", value1, value2, "dafyPay19MpOrderNo");
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

        public Criteria andSendTimeIsNull() {
            addCriterion("send_time is null");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNotNull() {
            addCriterion("send_time is not null");
            return (Criteria) this;
        }

        public Criteria andSendTimeEqualTo(Date value) {
            addCriterion("send_time =", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotEqualTo(Date value) {
            addCriterion("send_time <>", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThan(Date value) {
            addCriterion("send_time >", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("send_time >=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThan(Date value) {
            addCriterion("send_time <", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThanOrEqualTo(Date value) {
            addCriterion("send_time <=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeIn(List<Date> values) {
            addCriterion("send_time in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotIn(List<Date> values) {
            addCriterion("send_time not in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeBetween(Date value1, Date value2) {
            addCriterion("send_time between", value1, value2, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotBetween(Date value1, Date value2) {
            addCriterion("send_time not between", value1, value2, "sendTime");
            return (Criteria) this;
        }

        public Criteria andExpectTimeIsNull() {
            addCriterion("expect_time is null");
            return (Criteria) this;
        }

        public Criteria andExpectTimeIsNotNull() {
            addCriterion("expect_time is not null");
            return (Criteria) this;
        }

        public Criteria andExpectTimeEqualTo(Date value) {
            addCriterion("expect_time =", value, "expectTime");
            return (Criteria) this;
        }

        public Criteria andExpectTimeNotEqualTo(Date value) {
            addCriterion("expect_time <>", value, "expectTime");
            return (Criteria) this;
        }

        public Criteria andExpectTimeGreaterThan(Date value) {
            addCriterion("expect_time >", value, "expectTime");
            return (Criteria) this;
        }

        public Criteria andExpectTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("expect_time >=", value, "expectTime");
            return (Criteria) this;
        }

        public Criteria andExpectTimeLessThan(Date value) {
            addCriterion("expect_time <", value, "expectTime");
            return (Criteria) this;
        }

        public Criteria andExpectTimeLessThanOrEqualTo(Date value) {
            addCriterion("expect_time <=", value, "expectTime");
            return (Criteria) this;
        }

        public Criteria andExpectTimeIn(List<Date> values) {
            addCriterion("expect_time in", values, "expectTime");
            return (Criteria) this;
        }

        public Criteria andExpectTimeNotIn(List<Date> values) {
            addCriterion("expect_time not in", values, "expectTime");
            return (Criteria) this;
        }

        public Criteria andExpectTimeBetween(Date value1, Date value2) {
            addCriterion("expect_time between", value1, value2, "expectTime");
            return (Criteria) this;
        }

        public Criteria andExpectTimeNotBetween(Date value1, Date value2) {
            addCriterion("expect_time not between", value1, value2, "expectTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeIsNull() {
            addCriterion("return_time is null");
            return (Criteria) this;
        }

        public Criteria andReturnTimeIsNotNull() {
            addCriterion("return_time is not null");
            return (Criteria) this;
        }

        public Criteria andReturnTimeEqualTo(Date value) {
            addCriterion("return_time =", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeNotEqualTo(Date value) {
            addCriterion("return_time <>", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeGreaterThan(Date value) {
            addCriterion("return_time >", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("return_time >=", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeLessThan(Date value) {
            addCriterion("return_time <", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeLessThanOrEqualTo(Date value) {
            addCriterion("return_time <=", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeIn(List<Date> values) {
            addCriterion("return_time in", values, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeNotIn(List<Date> values) {
            addCriterion("return_time not in", values, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeBetween(Date value1, Date value2) {
            addCriterion("return_time between", value1, value2, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeNotBetween(Date value1, Date value2) {
            addCriterion("return_time not between", value1, value2, "returnTime");
            return (Criteria) this;
        }

        public Criteria andFinancingDateIsNull() {
            addCriterion("financing_date is null");
            return (Criteria) this;
        }

        public Criteria andFinancingDateIsNotNull() {
            addCriterion("financing_date is not null");
            return (Criteria) this;
        }

        public Criteria andFinancingDateEqualTo(Date value) {
            addCriterionForJDBCDate("financing_date =", value, "financingDate");
            return (Criteria) this;
        }

        public Criteria andFinancingDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("financing_date <>", value, "financingDate");
            return (Criteria) this;
        }

        public Criteria andFinancingDateGreaterThan(Date value) {
            addCriterionForJDBCDate("financing_date >", value, "financingDate");
            return (Criteria) this;
        }

        public Criteria andFinancingDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("financing_date >=", value, "financingDate");
            return (Criteria) this;
        }

        public Criteria andFinancingDateLessThan(Date value) {
            addCriterionForJDBCDate("financing_date <", value, "financingDate");
            return (Criteria) this;
        }

        public Criteria andFinancingDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("financing_date <=", value, "financingDate");
            return (Criteria) this;
        }

        public Criteria andFinancingDateIn(List<Date> values) {
            addCriterionForJDBCDate("financing_date in", values, "financingDate");
            return (Criteria) this;
        }

        public Criteria andFinancingDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("financing_date not in", values, "financingDate");
            return (Criteria) this;
        }

        public Criteria andFinancingDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("financing_date between", value1, value2, "financingDate");
            return (Criteria) this;
        }

        public Criteria andFinancingDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("financing_date not between", value1, value2, "financingDate");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnCodeIsNull() {
            addCriterion("pay19_return_code is null");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnCodeIsNotNull() {
            addCriterion("pay19_return_code is not null");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnCodeEqualTo(String value) {
            addCriterion("pay19_return_code =", value, "pay19ReturnCode");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnCodeNotEqualTo(String value) {
            addCriterion("pay19_return_code <>", value, "pay19ReturnCode");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnCodeGreaterThan(String value) {
            addCriterion("pay19_return_code >", value, "pay19ReturnCode");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnCodeGreaterThanOrEqualTo(String value) {
            addCriterion("pay19_return_code >=", value, "pay19ReturnCode");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnCodeLessThan(String value) {
            addCriterion("pay19_return_code <", value, "pay19ReturnCode");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnCodeLessThanOrEqualTo(String value) {
            addCriterion("pay19_return_code <=", value, "pay19ReturnCode");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnCodeLike(String value) {
            addCriterion("pay19_return_code like", value, "pay19ReturnCode");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnCodeNotLike(String value) {
            addCriterion("pay19_return_code not like", value, "pay19ReturnCode");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnCodeIn(List<String> values) {
            addCriterion("pay19_return_code in", values, "pay19ReturnCode");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnCodeNotIn(List<String> values) {
            addCriterion("pay19_return_code not in", values, "pay19ReturnCode");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnCodeBetween(String value1, String value2) {
            addCriterion("pay19_return_code between", value1, value2, "pay19ReturnCode");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnCodeNotBetween(String value1, String value2) {
            addCriterion("pay19_return_code not between", value1, value2, "pay19ReturnCode");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnMsgIsNull() {
            addCriterion("pay19_return_msg is null");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnMsgIsNotNull() {
            addCriterion("pay19_return_msg is not null");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnMsgEqualTo(String value) {
            addCriterion("pay19_return_msg =", value, "pay19ReturnMsg");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnMsgNotEqualTo(String value) {
            addCriterion("pay19_return_msg <>", value, "pay19ReturnMsg");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnMsgGreaterThan(String value) {
            addCriterion("pay19_return_msg >", value, "pay19ReturnMsg");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnMsgGreaterThanOrEqualTo(String value) {
            addCriterion("pay19_return_msg >=", value, "pay19ReturnMsg");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnMsgLessThan(String value) {
            addCriterion("pay19_return_msg <", value, "pay19ReturnMsg");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnMsgLessThanOrEqualTo(String value) {
            addCriterion("pay19_return_msg <=", value, "pay19ReturnMsg");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnMsgLike(String value) {
            addCriterion("pay19_return_msg like", value, "pay19ReturnMsg");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnMsgNotLike(String value) {
            addCriterion("pay19_return_msg not like", value, "pay19ReturnMsg");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnMsgIn(List<String> values) {
            addCriterion("pay19_return_msg in", values, "pay19ReturnMsg");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnMsgNotIn(List<String> values) {
            addCriterion("pay19_return_msg not in", values, "pay19ReturnMsg");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnMsgBetween(String value1, String value2) {
            addCriterion("pay19_return_msg between", value1, value2, "pay19ReturnMsg");
            return (Criteria) this;
        }

        public Criteria andPay19ReturnMsgNotBetween(String value1, String value2) {
            addCriterion("pay19_return_msg not between", value1, value2, "pay19ReturnMsg");
            return (Criteria) this;
        }

        public Criteria andDafyReturnCodeIsNull() {
            addCriterion("dafy_return_code is null");
            return (Criteria) this;
        }

        public Criteria andDafyReturnCodeIsNotNull() {
            addCriterion("dafy_return_code is not null");
            return (Criteria) this;
        }

        public Criteria andDafyReturnCodeEqualTo(String value) {
            addCriterion("dafy_return_code =", value, "dafyReturnCode");
            return (Criteria) this;
        }

        public Criteria andDafyReturnCodeNotEqualTo(String value) {
            addCriterion("dafy_return_code <>", value, "dafyReturnCode");
            return (Criteria) this;
        }

        public Criteria andDafyReturnCodeGreaterThan(String value) {
            addCriterion("dafy_return_code >", value, "dafyReturnCode");
            return (Criteria) this;
        }

        public Criteria andDafyReturnCodeGreaterThanOrEqualTo(String value) {
            addCriterion("dafy_return_code >=", value, "dafyReturnCode");
            return (Criteria) this;
        }

        public Criteria andDafyReturnCodeLessThan(String value) {
            addCriterion("dafy_return_code <", value, "dafyReturnCode");
            return (Criteria) this;
        }

        public Criteria andDafyReturnCodeLessThanOrEqualTo(String value) {
            addCriterion("dafy_return_code <=", value, "dafyReturnCode");
            return (Criteria) this;
        }

        public Criteria andDafyReturnCodeLike(String value) {
            addCriterion("dafy_return_code like", value, "dafyReturnCode");
            return (Criteria) this;
        }

        public Criteria andDafyReturnCodeNotLike(String value) {
            addCriterion("dafy_return_code not like", value, "dafyReturnCode");
            return (Criteria) this;
        }

        public Criteria andDafyReturnCodeIn(List<String> values) {
            addCriterion("dafy_return_code in", values, "dafyReturnCode");
            return (Criteria) this;
        }

        public Criteria andDafyReturnCodeNotIn(List<String> values) {
            addCriterion("dafy_return_code not in", values, "dafyReturnCode");
            return (Criteria) this;
        }

        public Criteria andDafyReturnCodeBetween(String value1, String value2) {
            addCriterion("dafy_return_code between", value1, value2, "dafyReturnCode");
            return (Criteria) this;
        }

        public Criteria andDafyReturnCodeNotBetween(String value1, String value2) {
            addCriterion("dafy_return_code not between", value1, value2, "dafyReturnCode");
            return (Criteria) this;
        }

        public Criteria andDafyReturnMsgIsNull() {
            addCriterion("dafy_return_msg is null");
            return (Criteria) this;
        }

        public Criteria andDafyReturnMsgIsNotNull() {
            addCriterion("dafy_return_msg is not null");
            return (Criteria) this;
        }

        public Criteria andDafyReturnMsgEqualTo(String value) {
            addCriterion("dafy_return_msg =", value, "dafyReturnMsg");
            return (Criteria) this;
        }

        public Criteria andDafyReturnMsgNotEqualTo(String value) {
            addCriterion("dafy_return_msg <>", value, "dafyReturnMsg");
            return (Criteria) this;
        }

        public Criteria andDafyReturnMsgGreaterThan(String value) {
            addCriterion("dafy_return_msg >", value, "dafyReturnMsg");
            return (Criteria) this;
        }

        public Criteria andDafyReturnMsgGreaterThanOrEqualTo(String value) {
            addCriterion("dafy_return_msg >=", value, "dafyReturnMsg");
            return (Criteria) this;
        }

        public Criteria andDafyReturnMsgLessThan(String value) {
            addCriterion("dafy_return_msg <", value, "dafyReturnMsg");
            return (Criteria) this;
        }

        public Criteria andDafyReturnMsgLessThanOrEqualTo(String value) {
            addCriterion("dafy_return_msg <=", value, "dafyReturnMsg");
            return (Criteria) this;
        }

        public Criteria andDafyReturnMsgLike(String value) {
            addCriterion("dafy_return_msg like", value, "dafyReturnMsg");
            return (Criteria) this;
        }

        public Criteria andDafyReturnMsgNotLike(String value) {
            addCriterion("dafy_return_msg not like", value, "dafyReturnMsg");
            return (Criteria) this;
        }

        public Criteria andDafyReturnMsgIn(List<String> values) {
            addCriterion("dafy_return_msg in", values, "dafyReturnMsg");
            return (Criteria) this;
        }

        public Criteria andDafyReturnMsgNotIn(List<String> values) {
            addCriterion("dafy_return_msg not in", values, "dafyReturnMsg");
            return (Criteria) this;
        }

        public Criteria andDafyReturnMsgBetween(String value1, String value2) {
            addCriterion("dafy_return_msg between", value1, value2, "dafyReturnMsg");
            return (Criteria) this;
        }

        public Criteria andDafyReturnMsgNotBetween(String value1, String value2) {
            addCriterion("dafy_return_msg not between", value1, value2, "dafyReturnMsg");
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