package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsLoanRelativeRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsLoanRelativeRecordExample() {
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

        public Criteria andCustomerIdIsNull() {
            addCriterion("customer_id is null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNotNull() {
            addCriterion("customer_id is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdEqualTo(String value) {
            addCriterion("customer_id =", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotEqualTo(String value) {
            addCriterion("customer_id <>", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThan(String value) {
            addCriterion("customer_id >", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThanOrEqualTo(String value) {
            addCriterion("customer_id >=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThan(String value) {
            addCriterion("customer_id <", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThanOrEqualTo(String value) {
            addCriterion("customer_id <=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLike(String value) {
            addCriterion("customer_id like", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotLike(String value) {
            addCriterion("customer_id not like", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIn(List<String> values) {
            addCriterion("customer_id in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotIn(List<String> values) {
            addCriterion("customer_id not in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdBetween(String value1, String value2) {
            addCriterion("customer_id between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotBetween(String value1, String value2) {
            addCriterion("customer_id not between", value1, value2, "customerId");
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

        public Criteria andBuyTimeIsNull() {
            addCriterion("buy_time is null");
            return (Criteria) this;
        }

        public Criteria andBuyTimeIsNotNull() {
            addCriterion("buy_time is not null");
            return (Criteria) this;
        }

        public Criteria andBuyTimeEqualTo(Date value) {
            addCriterion("buy_time =", value, "buyTime");
            return (Criteria) this;
        }

        public Criteria andBuyTimeNotEqualTo(Date value) {
            addCriterion("buy_time <>", value, "buyTime");
            return (Criteria) this;
        }

        public Criteria andBuyTimeGreaterThan(Date value) {
            addCriterion("buy_time >", value, "buyTime");
            return (Criteria) this;
        }

        public Criteria andBuyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("buy_time >=", value, "buyTime");
            return (Criteria) this;
        }

        public Criteria andBuyTimeLessThan(Date value) {
            addCriterion("buy_time <", value, "buyTime");
            return (Criteria) this;
        }

        public Criteria andBuyTimeLessThanOrEqualTo(Date value) {
            addCriterion("buy_time <=", value, "buyTime");
            return (Criteria) this;
        }

        public Criteria andBuyTimeIn(List<Date> values) {
            addCriterion("buy_time in", values, "buyTime");
            return (Criteria) this;
        }

        public Criteria andBuyTimeNotIn(List<Date> values) {
            addCriterion("buy_time not in", values, "buyTime");
            return (Criteria) this;
        }

        public Criteria andBuyTimeBetween(Date value1, Date value2) {
            addCriterion("buy_time between", value1, value2, "buyTime");
            return (Criteria) this;
        }

        public Criteria andBuyTimeNotBetween(Date value1, Date value2) {
            addCriterion("buy_time not between", value1, value2, "buyTime");
            return (Criteria) this;
        }

        public Criteria andLenderCustomerIdIsNull() {
            addCriterion("lender_customer_id is null");
            return (Criteria) this;
        }

        public Criteria andLenderCustomerIdIsNotNull() {
            addCriterion("lender_customer_id is not null");
            return (Criteria) this;
        }

        public Criteria andLenderCustomerIdEqualTo(String value) {
            addCriterion("lender_customer_id =", value, "lenderCustomerId");
            return (Criteria) this;
        }

        public Criteria andLenderCustomerIdNotEqualTo(String value) {
            addCriterion("lender_customer_id <>", value, "lenderCustomerId");
            return (Criteria) this;
        }

        public Criteria andLenderCustomerIdGreaterThan(String value) {
            addCriterion("lender_customer_id >", value, "lenderCustomerId");
            return (Criteria) this;
        }

        public Criteria andLenderCustomerIdGreaterThanOrEqualTo(String value) {
            addCriterion("lender_customer_id >=", value, "lenderCustomerId");
            return (Criteria) this;
        }

        public Criteria andLenderCustomerIdLessThan(String value) {
            addCriterion("lender_customer_id <", value, "lenderCustomerId");
            return (Criteria) this;
        }

        public Criteria andLenderCustomerIdLessThanOrEqualTo(String value) {
            addCriterion("lender_customer_id <=", value, "lenderCustomerId");
            return (Criteria) this;
        }

        public Criteria andLenderCustomerIdLike(String value) {
            addCriterion("lender_customer_id like", value, "lenderCustomerId");
            return (Criteria) this;
        }

        public Criteria andLenderCustomerIdNotLike(String value) {
            addCriterion("lender_customer_id not like", value, "lenderCustomerId");
            return (Criteria) this;
        }

        public Criteria andLenderCustomerIdIn(List<String> values) {
            addCriterion("lender_customer_id in", values, "lenderCustomerId");
            return (Criteria) this;
        }

        public Criteria andLenderCustomerIdNotIn(List<String> values) {
            addCriterion("lender_customer_id not in", values, "lenderCustomerId");
            return (Criteria) this;
        }

        public Criteria andLenderCustomerIdBetween(String value1, String value2) {
            addCriterion("lender_customer_id between", value1, value2, "lenderCustomerId");
            return (Criteria) this;
        }

        public Criteria andLenderCustomerIdNotBetween(String value1, String value2) {
            addCriterion("lender_customer_id not between", value1, value2, "lenderCustomerId");
            return (Criteria) this;
        }

        public Criteria andLenderIdCardIsNull() {
            addCriterion("lender_id_card is null");
            return (Criteria) this;
        }

        public Criteria andLenderIdCardIsNotNull() {
            addCriterion("lender_id_card is not null");
            return (Criteria) this;
        }

        public Criteria andLenderIdCardEqualTo(String value) {
            addCriterion("lender_id_card =", value, "lenderIdCard");
            return (Criteria) this;
        }

        public Criteria andLenderIdCardNotEqualTo(String value) {
            addCriterion("lender_id_card <>", value, "lenderIdCard");
            return (Criteria) this;
        }

        public Criteria andLenderIdCardGreaterThan(String value) {
            addCriterion("lender_id_card >", value, "lenderIdCard");
            return (Criteria) this;
        }

        public Criteria andLenderIdCardGreaterThanOrEqualTo(String value) {
            addCriterion("lender_id_card >=", value, "lenderIdCard");
            return (Criteria) this;
        }

        public Criteria andLenderIdCardLessThan(String value) {
            addCriterion("lender_id_card <", value, "lenderIdCard");
            return (Criteria) this;
        }

        public Criteria andLenderIdCardLessThanOrEqualTo(String value) {
            addCriterion("lender_id_card <=", value, "lenderIdCard");
            return (Criteria) this;
        }

        public Criteria andLenderIdCardLike(String value) {
            addCriterion("lender_id_card like", value, "lenderIdCard");
            return (Criteria) this;
        }

        public Criteria andLenderIdCardNotLike(String value) {
            addCriterion("lender_id_card not like", value, "lenderIdCard");
            return (Criteria) this;
        }

        public Criteria andLenderIdCardIn(List<String> values) {
            addCriterion("lender_id_card in", values, "lenderIdCard");
            return (Criteria) this;
        }

        public Criteria andLenderIdCardNotIn(List<String> values) {
            addCriterion("lender_id_card not in", values, "lenderIdCard");
            return (Criteria) this;
        }

        public Criteria andLenderIdCardBetween(String value1, String value2) {
            addCriterion("lender_id_card between", value1, value2, "lenderIdCard");
            return (Criteria) this;
        }

        public Criteria andLenderIdCardNotBetween(String value1, String value2) {
            addCriterion("lender_id_card not between", value1, value2, "lenderIdCard");
            return (Criteria) this;
        }

        public Criteria andLenderNameIsNull() {
            addCriterion("lender_name is null");
            return (Criteria) this;
        }

        public Criteria andLenderNameIsNotNull() {
            addCriterion("lender_name is not null");
            return (Criteria) this;
        }

        public Criteria andLenderNameEqualTo(String value) {
            addCriterion("lender_name =", value, "lenderName");
            return (Criteria) this;
        }

        public Criteria andLenderNameNotEqualTo(String value) {
            addCriterion("lender_name <>", value, "lenderName");
            return (Criteria) this;
        }

        public Criteria andLenderNameGreaterThan(String value) {
            addCriterion("lender_name >", value, "lenderName");
            return (Criteria) this;
        }

        public Criteria andLenderNameGreaterThanOrEqualTo(String value) {
            addCriterion("lender_name >=", value, "lenderName");
            return (Criteria) this;
        }

        public Criteria andLenderNameLessThan(String value) {
            addCriterion("lender_name <", value, "lenderName");
            return (Criteria) this;
        }

        public Criteria andLenderNameLessThanOrEqualTo(String value) {
            addCriterion("lender_name <=", value, "lenderName");
            return (Criteria) this;
        }

        public Criteria andLenderNameLike(String value) {
            addCriterion("lender_name like", value, "lenderName");
            return (Criteria) this;
        }

        public Criteria andLenderNameNotLike(String value) {
            addCriterion("lender_name not like", value, "lenderName");
            return (Criteria) this;
        }

        public Criteria andLenderNameIn(List<String> values) {
            addCriterion("lender_name in", values, "lenderName");
            return (Criteria) this;
        }

        public Criteria andLenderNameNotIn(List<String> values) {
            addCriterion("lender_name not in", values, "lenderName");
            return (Criteria) this;
        }

        public Criteria andLenderNameBetween(String value1, String value2) {
            addCriterion("lender_name between", value1, value2, "lenderName");
            return (Criteria) this;
        }

        public Criteria andLenderNameNotBetween(String value1, String value2) {
            addCriterion("lender_name not between", value1, value2, "lenderName");
            return (Criteria) this;
        }

        public Criteria andBorrowerCustomerIdIsNull() {
            addCriterion("borrower_customer_id is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerCustomerIdIsNotNull() {
            addCriterion("borrower_customer_id is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerCustomerIdEqualTo(String value) {
            addCriterion("borrower_customer_id =", value, "borrowerCustomerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerCustomerIdNotEqualTo(String value) {
            addCriterion("borrower_customer_id <>", value, "borrowerCustomerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerCustomerIdGreaterThan(String value) {
            addCriterion("borrower_customer_id >", value, "borrowerCustomerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerCustomerIdGreaterThanOrEqualTo(String value) {
            addCriterion("borrower_customer_id >=", value, "borrowerCustomerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerCustomerIdLessThan(String value) {
            addCriterion("borrower_customer_id <", value, "borrowerCustomerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerCustomerIdLessThanOrEqualTo(String value) {
            addCriterion("borrower_customer_id <=", value, "borrowerCustomerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerCustomerIdLike(String value) {
            addCriterion("borrower_customer_id like", value, "borrowerCustomerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerCustomerIdNotLike(String value) {
            addCriterion("borrower_customer_id not like", value, "borrowerCustomerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerCustomerIdIn(List<String> values) {
            addCriterion("borrower_customer_id in", values, "borrowerCustomerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerCustomerIdNotIn(List<String> values) {
            addCriterion("borrower_customer_id not in", values, "borrowerCustomerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerCustomerIdBetween(String value1, String value2) {
            addCriterion("borrower_customer_id between", value1, value2, "borrowerCustomerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerCustomerIdNotBetween(String value1, String value2) {
            addCriterion("borrower_customer_id not between", value1, value2, "borrowerCustomerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdCardIsNull() {
            addCriterion("borrower_id_card is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdCardIsNotNull() {
            addCriterion("borrower_id_card is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdCardEqualTo(String value) {
            addCriterion("borrower_id_card =", value, "borrowerIdCard");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdCardNotEqualTo(String value) {
            addCriterion("borrower_id_card <>", value, "borrowerIdCard");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdCardGreaterThan(String value) {
            addCriterion("borrower_id_card >", value, "borrowerIdCard");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdCardGreaterThanOrEqualTo(String value) {
            addCriterion("borrower_id_card >=", value, "borrowerIdCard");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdCardLessThan(String value) {
            addCriterion("borrower_id_card <", value, "borrowerIdCard");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdCardLessThanOrEqualTo(String value) {
            addCriterion("borrower_id_card <=", value, "borrowerIdCard");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdCardLike(String value) {
            addCriterion("borrower_id_card like", value, "borrowerIdCard");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdCardNotLike(String value) {
            addCriterion("borrower_id_card not like", value, "borrowerIdCard");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdCardIn(List<String> values) {
            addCriterion("borrower_id_card in", values, "borrowerIdCard");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdCardNotIn(List<String> values) {
            addCriterion("borrower_id_card not in", values, "borrowerIdCard");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdCardBetween(String value1, String value2) {
            addCriterion("borrower_id_card between", value1, value2, "borrowerIdCard");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdCardNotBetween(String value1, String value2) {
            addCriterion("borrower_id_card not between", value1, value2, "borrowerIdCard");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameIsNull() {
            addCriterion("borrower_name is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameIsNotNull() {
            addCriterion("borrower_name is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameEqualTo(String value) {
            addCriterion("borrower_name =", value, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameNotEqualTo(String value) {
            addCriterion("borrower_name <>", value, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameGreaterThan(String value) {
            addCriterion("borrower_name >", value, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameGreaterThanOrEqualTo(String value) {
            addCriterion("borrower_name >=", value, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameLessThan(String value) {
            addCriterion("borrower_name <", value, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameLessThanOrEqualTo(String value) {
            addCriterion("borrower_name <=", value, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameLike(String value) {
            addCriterion("borrower_name like", value, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameNotLike(String value) {
            addCriterion("borrower_name not like", value, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameIn(List<String> values) {
            addCriterion("borrower_name in", values, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameNotIn(List<String> values) {
            addCriterion("borrower_name not in", values, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameBetween(String value1, String value2) {
            addCriterion("borrower_name between", value1, value2, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameNotBetween(String value1, String value2) {
            addCriterion("borrower_name not between", value1, value2, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerBankcardIsNull() {
            addCriterion("borrower_bankcard is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerBankcardIsNotNull() {
            addCriterion("borrower_bankcard is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerBankcardEqualTo(String value) {
            addCriterion("borrower_bankcard =", value, "borrowerBankcard");
            return (Criteria) this;
        }

        public Criteria andBorrowerBankcardNotEqualTo(String value) {
            addCriterion("borrower_bankcard <>", value, "borrowerBankcard");
            return (Criteria) this;
        }

        public Criteria andBorrowerBankcardGreaterThan(String value) {
            addCriterion("borrower_bankcard >", value, "borrowerBankcard");
            return (Criteria) this;
        }

        public Criteria andBorrowerBankcardGreaterThanOrEqualTo(String value) {
            addCriterion("borrower_bankcard >=", value, "borrowerBankcard");
            return (Criteria) this;
        }

        public Criteria andBorrowerBankcardLessThan(String value) {
            addCriterion("borrower_bankcard <", value, "borrowerBankcard");
            return (Criteria) this;
        }

        public Criteria andBorrowerBankcardLessThanOrEqualTo(String value) {
            addCriterion("borrower_bankcard <=", value, "borrowerBankcard");
            return (Criteria) this;
        }

        public Criteria andBorrowerBankcardLike(String value) {
            addCriterion("borrower_bankcard like", value, "borrowerBankcard");
            return (Criteria) this;
        }

        public Criteria andBorrowerBankcardNotLike(String value) {
            addCriterion("borrower_bankcard not like", value, "borrowerBankcard");
            return (Criteria) this;
        }

        public Criteria andBorrowerBankcardIn(List<String> values) {
            addCriterion("borrower_bankcard in", values, "borrowerBankcard");
            return (Criteria) this;
        }

        public Criteria andBorrowerBankcardNotIn(List<String> values) {
            addCriterion("borrower_bankcard not in", values, "borrowerBankcard");
            return (Criteria) this;
        }

        public Criteria andBorrowerBankcardBetween(String value1, String value2) {
            addCriterion("borrower_bankcard between", value1, value2, "borrowerBankcard");
            return (Criteria) this;
        }

        public Criteria andBorrowerBankcardNotBetween(String value1, String value2) {
            addCriterion("borrower_bankcard not between", value1, value2, "borrowerBankcard");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNull() {
            addCriterion("product_name is null");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNotNull() {
            addCriterion("product_name is not null");
            return (Criteria) this;
        }

        public Criteria andProductNameEqualTo(String value) {
            addCriterion("product_name =", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotEqualTo(String value) {
            addCriterion("product_name <>", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThan(String value) {
            addCriterion("product_name >", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThanOrEqualTo(String value) {
            addCriterion("product_name >=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThan(String value) {
            addCriterion("product_name <", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThanOrEqualTo(String value) {
            addCriterion("product_name <=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLike(String value) {
            addCriterion("product_name like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotLike(String value) {
            addCriterion("product_name not like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameIn(List<String> values) {
            addCriterion("product_name in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotIn(List<String> values) {
            addCriterion("product_name not in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameBetween(String value1, String value2) {
            addCriterion("product_name between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotBetween(String value1, String value2) {
            addCriterion("product_name not between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andBorrowProductNameIsNull() {
            addCriterion("borrow_product_name is null");
            return (Criteria) this;
        }

        public Criteria andBorrowProductNameIsNotNull() {
            addCriterion("borrow_product_name is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowProductNameEqualTo(String value) {
            addCriterion("borrow_product_name =", value, "borrowProductName");
            return (Criteria) this;
        }

        public Criteria andBorrowProductNameNotEqualTo(String value) {
            addCriterion("borrow_product_name <>", value, "borrowProductName");
            return (Criteria) this;
        }

        public Criteria andBorrowProductNameGreaterThan(String value) {
            addCriterion("borrow_product_name >", value, "borrowProductName");
            return (Criteria) this;
        }

        public Criteria andBorrowProductNameGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_product_name >=", value, "borrowProductName");
            return (Criteria) this;
        }

        public Criteria andBorrowProductNameLessThan(String value) {
            addCriterion("borrow_product_name <", value, "borrowProductName");
            return (Criteria) this;
        }

        public Criteria andBorrowProductNameLessThanOrEqualTo(String value) {
            addCriterion("borrow_product_name <=", value, "borrowProductName");
            return (Criteria) this;
        }

        public Criteria andBorrowProductNameLike(String value) {
            addCriterion("borrow_product_name like", value, "borrowProductName");
            return (Criteria) this;
        }

        public Criteria andBorrowProductNameNotLike(String value) {
            addCriterion("borrow_product_name not like", value, "borrowProductName");
            return (Criteria) this;
        }

        public Criteria andBorrowProductNameIn(List<String> values) {
            addCriterion("borrow_product_name in", values, "borrowProductName");
            return (Criteria) this;
        }

        public Criteria andBorrowProductNameNotIn(List<String> values) {
            addCriterion("borrow_product_name not in", values, "borrowProductName");
            return (Criteria) this;
        }

        public Criteria andBorrowProductNameBetween(String value1, String value2) {
            addCriterion("borrow_product_name between", value1, value2, "borrowProductName");
            return (Criteria) this;
        }

        public Criteria andBorrowProductNameNotBetween(String value1, String value2) {
            addCriterion("borrow_product_name not between", value1, value2, "borrowProductName");
            return (Criteria) this;
        }

        public Criteria andBorrowAmountIsNull() {
            addCriterion("borrow_amount is null");
            return (Criteria) this;
        }

        public Criteria andBorrowAmountIsNotNull() {
            addCriterion("borrow_amount is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowAmountEqualTo(Double value) {
            addCriterion("borrow_amount =", value, "borrowAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowAmountNotEqualTo(Double value) {
            addCriterion("borrow_amount <>", value, "borrowAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowAmountGreaterThan(Double value) {
            addCriterion("borrow_amount >", value, "borrowAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("borrow_amount >=", value, "borrowAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowAmountLessThan(Double value) {
            addCriterion("borrow_amount <", value, "borrowAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowAmountLessThanOrEqualTo(Double value) {
            addCriterion("borrow_amount <=", value, "borrowAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowAmountIn(List<Double> values) {
            addCriterion("borrow_amount in", values, "borrowAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowAmountNotIn(List<Double> values) {
            addCriterion("borrow_amount not in", values, "borrowAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowAmountBetween(Double value1, Double value2) {
            addCriterion("borrow_amount between", value1, value2, "borrowAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowAmountNotBetween(Double value1, Double value2) {
            addCriterion("borrow_amount not between", value1, value2, "borrowAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountIsNull() {
            addCriterion("init_amount is null");
            return (Criteria) this;
        }

        public Criteria andInitAmountIsNotNull() {
            addCriterion("init_amount is not null");
            return (Criteria) this;
        }

        public Criteria andInitAmountEqualTo(Double value) {
            addCriterion("init_amount =", value, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountNotEqualTo(Double value) {
            addCriterion("init_amount <>", value, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountGreaterThan(Double value) {
            addCriterion("init_amount >", value, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("init_amount >=", value, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountLessThan(Double value) {
            addCriterion("init_amount <", value, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountLessThanOrEqualTo(Double value) {
            addCriterion("init_amount <=", value, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountIn(List<Double> values) {
            addCriterion("init_amount in", values, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountNotIn(List<Double> values) {
            addCriterion("init_amount not in", values, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountBetween(Double value1, Double value2) {
            addCriterion("init_amount between", value1, value2, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountNotBetween(Double value1, Double value2) {
            addCriterion("init_amount not between", value1, value2, "initAmount");
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

        public Criteria andLeftAmountIsNull() {
            addCriterion("left_amount is null");
            return (Criteria) this;
        }

        public Criteria andLeftAmountIsNotNull() {
            addCriterion("left_amount is not null");
            return (Criteria) this;
        }

        public Criteria andLeftAmountEqualTo(Double value) {
            addCriterion("left_amount =", value, "leftAmount");
            return (Criteria) this;
        }

        public Criteria andLeftAmountNotEqualTo(Double value) {
            addCriterion("left_amount <>", value, "leftAmount");
            return (Criteria) this;
        }

        public Criteria andLeftAmountGreaterThan(Double value) {
            addCriterion("left_amount >", value, "leftAmount");
            return (Criteria) this;
        }

        public Criteria andLeftAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("left_amount >=", value, "leftAmount");
            return (Criteria) this;
        }

        public Criteria andLeftAmountLessThan(Double value) {
            addCriterion("left_amount <", value, "leftAmount");
            return (Criteria) this;
        }

        public Criteria andLeftAmountLessThanOrEqualTo(Double value) {
            addCriterion("left_amount <=", value, "leftAmount");
            return (Criteria) this;
        }

        public Criteria andLeftAmountIn(List<Double> values) {
            addCriterion("left_amount in", values, "leftAmount");
            return (Criteria) this;
        }

        public Criteria andLeftAmountNotIn(List<Double> values) {
            addCriterion("left_amount not in", values, "leftAmount");
            return (Criteria) this;
        }

        public Criteria andLeftAmountBetween(Double value1, Double value2) {
            addCriterion("left_amount between", value1, value2, "leftAmount");
            return (Criteria) this;
        }

        public Criteria andLeftAmountNotBetween(Double value1, Double value2) {
            addCriterion("left_amount not between", value1, value2, "leftAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowIdIsNull() {
            addCriterion("borrow_id is null");
            return (Criteria) this;
        }

        public Criteria andBorrowIdIsNotNull() {
            addCriterion("borrow_id is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowIdEqualTo(String value) {
            addCriterion("borrow_id =", value, "borrowId");
            return (Criteria) this;
        }

        public Criteria andBorrowIdNotEqualTo(String value) {
            addCriterion("borrow_id <>", value, "borrowId");
            return (Criteria) this;
        }

        public Criteria andBorrowIdGreaterThan(String value) {
            addCriterion("borrow_id >", value, "borrowId");
            return (Criteria) this;
        }

        public Criteria andBorrowIdGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_id >=", value, "borrowId");
            return (Criteria) this;
        }

        public Criteria andBorrowIdLessThan(String value) {
            addCriterion("borrow_id <", value, "borrowId");
            return (Criteria) this;
        }

        public Criteria andBorrowIdLessThanOrEqualTo(String value) {
            addCriterion("borrow_id <=", value, "borrowId");
            return (Criteria) this;
        }

        public Criteria andBorrowIdLike(String value) {
            addCriterion("borrow_id like", value, "borrowId");
            return (Criteria) this;
        }

        public Criteria andBorrowIdNotLike(String value) {
            addCriterion("borrow_id not like", value, "borrowId");
            return (Criteria) this;
        }

        public Criteria andBorrowIdIn(List<String> values) {
            addCriterion("borrow_id in", values, "borrowId");
            return (Criteria) this;
        }

        public Criteria andBorrowIdNotIn(List<String> values) {
            addCriterion("borrow_id not in", values, "borrowId");
            return (Criteria) this;
        }

        public Criteria andBorrowIdBetween(String value1, String value2) {
            addCriterion("borrow_id between", value1, value2, "borrowId");
            return (Criteria) this;
        }

        public Criteria andBorrowIdNotBetween(String value1, String value2) {
            addCriterion("borrow_id not between", value1, value2, "borrowId");
            return (Criteria) this;
        }

        public Criteria andBorrowTransNoIsNull() {
            addCriterion("borrow_trans_no is null");
            return (Criteria) this;
        }

        public Criteria andBorrowTransNoIsNotNull() {
            addCriterion("borrow_trans_no is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowTransNoEqualTo(String value) {
            addCriterion("borrow_trans_no =", value, "borrowTransNo");
            return (Criteria) this;
        }

        public Criteria andBorrowTransNoNotEqualTo(String value) {
            addCriterion("borrow_trans_no <>", value, "borrowTransNo");
            return (Criteria) this;
        }

        public Criteria andBorrowTransNoGreaterThan(String value) {
            addCriterion("borrow_trans_no >", value, "borrowTransNo");
            return (Criteria) this;
        }

        public Criteria andBorrowTransNoGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_trans_no >=", value, "borrowTransNo");
            return (Criteria) this;
        }

        public Criteria andBorrowTransNoLessThan(String value) {
            addCriterion("borrow_trans_no <", value, "borrowTransNo");
            return (Criteria) this;
        }

        public Criteria andBorrowTransNoLessThanOrEqualTo(String value) {
            addCriterion("borrow_trans_no <=", value, "borrowTransNo");
            return (Criteria) this;
        }

        public Criteria andBorrowTransNoLike(String value) {
            addCriterion("borrow_trans_no like", value, "borrowTransNo");
            return (Criteria) this;
        }

        public Criteria andBorrowTransNoNotLike(String value) {
            addCriterion("borrow_trans_no not like", value, "borrowTransNo");
            return (Criteria) this;
        }

        public Criteria andBorrowTransNoIn(List<String> values) {
            addCriterion("borrow_trans_no in", values, "borrowTransNo");
            return (Criteria) this;
        }

        public Criteria andBorrowTransNoNotIn(List<String> values) {
            addCriterion("borrow_trans_no not in", values, "borrowTransNo");
            return (Criteria) this;
        }

        public Criteria andBorrowTransNoBetween(String value1, String value2) {
            addCriterion("borrow_trans_no between", value1, value2, "borrowTransNo");
            return (Criteria) this;
        }

        public Criteria andBorrowTransNoNotBetween(String value1, String value2) {
            addCriterion("borrow_trans_no not between", value1, value2, "borrowTransNo");
            return (Criteria) this;
        }

        public Criteria andBorrowApplyTimeIsNull() {
            addCriterion("borrow_apply_time is null");
            return (Criteria) this;
        }

        public Criteria andBorrowApplyTimeIsNotNull() {
            addCriterion("borrow_apply_time is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowApplyTimeEqualTo(Date value) {
            addCriterion("borrow_apply_time =", value, "borrowApplyTime");
            return (Criteria) this;
        }

        public Criteria andBorrowApplyTimeNotEqualTo(Date value) {
            addCriterion("borrow_apply_time <>", value, "borrowApplyTime");
            return (Criteria) this;
        }

        public Criteria andBorrowApplyTimeGreaterThan(Date value) {
            addCriterion("borrow_apply_time >", value, "borrowApplyTime");
            return (Criteria) this;
        }

        public Criteria andBorrowApplyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("borrow_apply_time >=", value, "borrowApplyTime");
            return (Criteria) this;
        }

        public Criteria andBorrowApplyTimeLessThan(Date value) {
            addCriterion("borrow_apply_time <", value, "borrowApplyTime");
            return (Criteria) this;
        }

        public Criteria andBorrowApplyTimeLessThanOrEqualTo(Date value) {
            addCriterion("borrow_apply_time <=", value, "borrowApplyTime");
            return (Criteria) this;
        }

        public Criteria andBorrowApplyTimeIn(List<Date> values) {
            addCriterion("borrow_apply_time in", values, "borrowApplyTime");
            return (Criteria) this;
        }

        public Criteria andBorrowApplyTimeNotIn(List<Date> values) {
            addCriterion("borrow_apply_time not in", values, "borrowApplyTime");
            return (Criteria) this;
        }

        public Criteria andBorrowApplyTimeBetween(Date value1, Date value2) {
            addCriterion("borrow_apply_time between", value1, value2, "borrowApplyTime");
            return (Criteria) this;
        }

        public Criteria andBorrowApplyTimeNotBetween(Date value1, Date value2) {
            addCriterion("borrow_apply_time not between", value1, value2, "borrowApplyTime");
            return (Criteria) this;
        }

        public Criteria andTimeIsNull() {
            addCriterion("time is null");
            return (Criteria) this;
        }

        public Criteria andTimeIsNotNull() {
            addCriterion("time is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEqualTo(Date value) {
            addCriterion("time =", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotEqualTo(Date value) {
            addCriterion("time <>", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThan(Date value) {
            addCriterion("time >", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("time >=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThan(Date value) {
            addCriterion("time <", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThanOrEqualTo(Date value) {
            addCriterion("time <=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeIn(List<Date> values) {
            addCriterion("time in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotIn(List<Date> values) {
            addCriterion("time not in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeBetween(Date value1, Date value2) {
            addCriterion("time between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotBetween(Date value1, Date value2) {
            addCriterion("time not between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andChannelIsNull() {
            addCriterion("channel is null");
            return (Criteria) this;
        }

        public Criteria andChannelIsNotNull() {
            addCriterion("channel is not null");
            return (Criteria) this;
        }

        public Criteria andChannelEqualTo(String value) {
            addCriterion("channel =", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotEqualTo(String value) {
            addCriterion("channel <>", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThan(String value) {
            addCriterion("channel >", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThanOrEqualTo(String value) {
            addCriterion("channel >=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThan(String value) {
            addCriterion("channel <", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThanOrEqualTo(String value) {
            addCriterion("channel <=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLike(String value) {
            addCriterion("channel like", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotLike(String value) {
            addCriterion("channel not like", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelIn(List<String> values) {
            addCriterion("channel in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotIn(List<String> values) {
            addCriterion("channel not in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelBetween(String value1, String value2) {
            addCriterion("channel between", value1, value2, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotBetween(String value1, String value2) {
            addCriterion("channel not between", value1, value2, "channel");
            return (Criteria) this;
        }

        public Criteria andIsRepayIsNull() {
            addCriterion("is_repay is null");
            return (Criteria) this;
        }

        public Criteria andIsRepayIsNotNull() {
            addCriterion("is_repay is not null");
            return (Criteria) this;
        }

        public Criteria andIsRepayEqualTo(String value) {
            addCriterion("is_repay =", value, "isRepay");
            return (Criteria) this;
        }

        public Criteria andIsRepayNotEqualTo(String value) {
            addCriterion("is_repay <>", value, "isRepay");
            return (Criteria) this;
        }

        public Criteria andIsRepayGreaterThan(String value) {
            addCriterion("is_repay >", value, "isRepay");
            return (Criteria) this;
        }

        public Criteria andIsRepayGreaterThanOrEqualTo(String value) {
            addCriterion("is_repay >=", value, "isRepay");
            return (Criteria) this;
        }

        public Criteria andIsRepayLessThan(String value) {
            addCriterion("is_repay <", value, "isRepay");
            return (Criteria) this;
        }

        public Criteria andIsRepayLessThanOrEqualTo(String value) {
            addCriterion("is_repay <=", value, "isRepay");
            return (Criteria) this;
        }

        public Criteria andIsRepayLike(String value) {
            addCriterion("is_repay like", value, "isRepay");
            return (Criteria) this;
        }

        public Criteria andIsRepayNotLike(String value) {
            addCriterion("is_repay not like", value, "isRepay");
            return (Criteria) this;
        }

        public Criteria andIsRepayIn(List<String> values) {
            addCriterion("is_repay in", values, "isRepay");
            return (Criteria) this;
        }

        public Criteria andIsRepayNotIn(List<String> values) {
            addCriterion("is_repay not in", values, "isRepay");
            return (Criteria) this;
        }

        public Criteria andIsRepayBetween(String value1, String value2) {
            addCriterion("is_repay between", value1, value2, "isRepay");
            return (Criteria) this;
        }

        public Criteria andIsRepayNotBetween(String value1, String value2) {
            addCriterion("is_repay not between", value1, value2, "isRepay");
            return (Criteria) this;
        }

        public Criteria andRepayTimeIsNull() {
            addCriterion("repay_time is null");
            return (Criteria) this;
        }

        public Criteria andRepayTimeIsNotNull() {
            addCriterion("repay_time is not null");
            return (Criteria) this;
        }

        public Criteria andRepayTimeEqualTo(Date value) {
            addCriterion("repay_time =", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeNotEqualTo(Date value) {
            addCriterion("repay_time <>", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeGreaterThan(Date value) {
            addCriterion("repay_time >", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("repay_time >=", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeLessThan(Date value) {
            addCriterion("repay_time <", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeLessThanOrEqualTo(Date value) {
            addCriterion("repay_time <=", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeIn(List<Date> values) {
            addCriterion("repay_time in", values, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeNotIn(List<Date> values) {
            addCriterion("repay_time not in", values, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeBetween(Date value1, Date value2) {
            addCriterion("repay_time between", value1, value2, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeNotBetween(Date value1, Date value2) {
            addCriterion("repay_time not between", value1, value2, "repayTime");
            return (Criteria) this;
        }

        public Criteria andBorrowerCreditAmountIsNull() {
            addCriterion("borrower_credit_amount is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerCreditAmountIsNotNull() {
            addCriterion("borrower_credit_amount is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerCreditAmountEqualTo(Double value) {
            addCriterion("borrower_credit_amount =", value, "borrowerCreditAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowerCreditAmountNotEqualTo(Double value) {
            addCriterion("borrower_credit_amount <>", value, "borrowerCreditAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowerCreditAmountGreaterThan(Double value) {
            addCriterion("borrower_credit_amount >", value, "borrowerCreditAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowerCreditAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("borrower_credit_amount >=", value, "borrowerCreditAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowerCreditAmountLessThan(Double value) {
            addCriterion("borrower_credit_amount <", value, "borrowerCreditAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowerCreditAmountLessThanOrEqualTo(Double value) {
            addCriterion("borrower_credit_amount <=", value, "borrowerCreditAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowerCreditAmountIn(List<Double> values) {
            addCriterion("borrower_credit_amount in", values, "borrowerCreditAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowerCreditAmountNotIn(List<Double> values) {
            addCriterion("borrower_credit_amount not in", values, "borrowerCreditAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowerCreditAmountBetween(Double value1, Double value2) {
            addCriterion("borrower_credit_amount between", value1, value2, "borrowerCreditAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowerCreditAmountNotBetween(Double value1, Double value2) {
            addCriterion("borrower_credit_amount not between", value1, value2, "borrowerCreditAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowerBorrowNumIsNull() {
            addCriterion("borrower_borrow_num is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerBorrowNumIsNotNull() {
            addCriterion("borrower_borrow_num is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerBorrowNumEqualTo(Integer value) {
            addCriterion("borrower_borrow_num =", value, "borrowerBorrowNum");
            return (Criteria) this;
        }

        public Criteria andBorrowerBorrowNumNotEqualTo(Integer value) {
            addCriterion("borrower_borrow_num <>", value, "borrowerBorrowNum");
            return (Criteria) this;
        }

        public Criteria andBorrowerBorrowNumGreaterThan(Integer value) {
            addCriterion("borrower_borrow_num >", value, "borrowerBorrowNum");
            return (Criteria) this;
        }

        public Criteria andBorrowerBorrowNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrower_borrow_num >=", value, "borrowerBorrowNum");
            return (Criteria) this;
        }

        public Criteria andBorrowerBorrowNumLessThan(Integer value) {
            addCriterion("borrower_borrow_num <", value, "borrowerBorrowNum");
            return (Criteria) this;
        }

        public Criteria andBorrowerBorrowNumLessThanOrEqualTo(Integer value) {
            addCriterion("borrower_borrow_num <=", value, "borrowerBorrowNum");
            return (Criteria) this;
        }

        public Criteria andBorrowerBorrowNumIn(List<Integer> values) {
            addCriterion("borrower_borrow_num in", values, "borrowerBorrowNum");
            return (Criteria) this;
        }

        public Criteria andBorrowerBorrowNumNotIn(List<Integer> values) {
            addCriterion("borrower_borrow_num not in", values, "borrowerBorrowNum");
            return (Criteria) this;
        }

        public Criteria andBorrowerBorrowNumBetween(Integer value1, Integer value2) {
            addCriterion("borrower_borrow_num between", value1, value2, "borrowerBorrowNum");
            return (Criteria) this;
        }

        public Criteria andBorrowerBorrowNumNotBetween(Integer value1, Integer value2) {
            addCriterion("borrower_borrow_num not between", value1, value2, "borrowerBorrowNum");
            return (Criteria) this;
        }

        public Criteria andBorrowerTotalAmountIsNull() {
            addCriterion("borrower_total_amount is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerTotalAmountIsNotNull() {
            addCriterion("borrower_total_amount is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerTotalAmountEqualTo(Double value) {
            addCriterion("borrower_total_amount =", value, "borrowerTotalAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowerTotalAmountNotEqualTo(Double value) {
            addCriterion("borrower_total_amount <>", value, "borrowerTotalAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowerTotalAmountGreaterThan(Double value) {
            addCriterion("borrower_total_amount >", value, "borrowerTotalAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowerTotalAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("borrower_total_amount >=", value, "borrowerTotalAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowerTotalAmountLessThan(Double value) {
            addCriterion("borrower_total_amount <", value, "borrowerTotalAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowerTotalAmountLessThanOrEqualTo(Double value) {
            addCriterion("borrower_total_amount <=", value, "borrowerTotalAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowerTotalAmountIn(List<Double> values) {
            addCriterion("borrower_total_amount in", values, "borrowerTotalAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowerTotalAmountNotIn(List<Double> values) {
            addCriterion("borrower_total_amount not in", values, "borrowerTotalAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowerTotalAmountBetween(Double value1, Double value2) {
            addCriterion("borrower_total_amount between", value1, value2, "borrowerTotalAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowerTotalAmountNotBetween(Double value1, Double value2) {
            addCriterion("borrower_total_amount not between", value1, value2, "borrowerTotalAmount");
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