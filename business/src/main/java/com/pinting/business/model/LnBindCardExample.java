package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LnBindCardExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LnBindCardExample() {
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

        public Criteria andLnUserIdIsNull() {
            addCriterion("ln_user_id is null");
            return (Criteria) this;
        }

        public Criteria andLnUserIdIsNotNull() {
            addCriterion("ln_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andLnUserIdEqualTo(Integer value) {
            addCriterion("ln_user_id =", value, "lnUserId");
            return (Criteria) this;
        }

        public Criteria andLnUserIdNotEqualTo(Integer value) {
            addCriterion("ln_user_id <>", value, "lnUserId");
            return (Criteria) this;
        }

        public Criteria andLnUserIdGreaterThan(Integer value) {
            addCriterion("ln_user_id >", value, "lnUserId");
            return (Criteria) this;
        }

        public Criteria andLnUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ln_user_id >=", value, "lnUserId");
            return (Criteria) this;
        }

        public Criteria andLnUserIdLessThan(Integer value) {
            addCriterion("ln_user_id <", value, "lnUserId");
            return (Criteria) this;
        }

        public Criteria andLnUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("ln_user_id <=", value, "lnUserId");
            return (Criteria) this;
        }

        public Criteria andLnUserIdIn(List<Integer> values) {
            addCriterion("ln_user_id in", values, "lnUserId");
            return (Criteria) this;
        }

        public Criteria andLnUserIdNotIn(List<Integer> values) {
            addCriterion("ln_user_id not in", values, "lnUserId");
            return (Criteria) this;
        }

        public Criteria andLnUserIdBetween(Integer value1, Integer value2) {
            addCriterion("ln_user_id between", value1, value2, "lnUserId");
            return (Criteria) this;
        }

        public Criteria andLnUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ln_user_id not between", value1, value2, "lnUserId");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andIdCardIsNull() {
            addCriterion("id_card is null");
            return (Criteria) this;
        }

        public Criteria andIdCardIsNotNull() {
            addCriterion("id_card is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardEqualTo(String value) {
            addCriterion("id_card =", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotEqualTo(String value) {
            addCriterion("id_card <>", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardGreaterThan(String value) {
            addCriterion("id_card >", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardGreaterThanOrEqualTo(String value) {
            addCriterion("id_card >=", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardLessThan(String value) {
            addCriterion("id_card <", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardLessThanOrEqualTo(String value) {
            addCriterion("id_card <=", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardLike(String value) {
            addCriterion("id_card like", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotLike(String value) {
            addCriterion("id_card not like", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardIn(List<String> values) {
            addCriterion("id_card in", values, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotIn(List<String> values) {
            addCriterion("id_card not in", values, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardBetween(String value1, String value2) {
            addCriterion("id_card between", value1, value2, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotBetween(String value1, String value2) {
            addCriterion("id_card not between", value1, value2, "idCard");
            return (Criteria) this;
        }

        public Criteria andBankCardIsNull() {
            addCriterion("bank_card is null");
            return (Criteria) this;
        }

        public Criteria andBankCardIsNotNull() {
            addCriterion("bank_card is not null");
            return (Criteria) this;
        }

        public Criteria andBankCardEqualTo(String value) {
            addCriterion("bank_card =", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardNotEqualTo(String value) {
            addCriterion("bank_card <>", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardGreaterThan(String value) {
            addCriterion("bank_card >", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardGreaterThanOrEqualTo(String value) {
            addCriterion("bank_card >=", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardLessThan(String value) {
            addCriterion("bank_card <", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardLessThanOrEqualTo(String value) {
            addCriterion("bank_card <=", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardLike(String value) {
            addCriterion("bank_card like", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardNotLike(String value) {
            addCriterion("bank_card not like", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardIn(List<String> values) {
            addCriterion("bank_card in", values, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardNotIn(List<String> values) {
            addCriterion("bank_card not in", values, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardBetween(String value1, String value2) {
            addCriterion("bank_card between", value1, value2, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardNotBetween(String value1, String value2) {
            addCriterion("bank_card not between", value1, value2, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCodeIsNull() {
            addCriterion("bank_code is null");
            return (Criteria) this;
        }

        public Criteria andBankCodeIsNotNull() {
            addCriterion("bank_code is not null");
            return (Criteria) this;
        }

        public Criteria andBankCodeEqualTo(String value) {
            addCriterion("bank_code =", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotEqualTo(String value) {
            addCriterion("bank_code <>", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeGreaterThan(String value) {
            addCriterion("bank_code >", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeGreaterThanOrEqualTo(String value) {
            addCriterion("bank_code >=", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeLessThan(String value) {
            addCriterion("bank_code <", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeLessThanOrEqualTo(String value) {
            addCriterion("bank_code <=", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeLike(String value) {
            addCriterion("bank_code like", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotLike(String value) {
            addCriterion("bank_code not like", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeIn(List<String> values) {
            addCriterion("bank_code in", values, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotIn(List<String> values) {
            addCriterion("bank_code not in", values, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeBetween(String value1, String value2) {
            addCriterion("bank_code between", value1, value2, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotBetween(String value1, String value2) {
            addCriterion("bank_code not between", value1, value2, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankNameIsNull() {
            addCriterion("bank_name is null");
            return (Criteria) this;
        }

        public Criteria andBankNameIsNotNull() {
            addCriterion("bank_name is not null");
            return (Criteria) this;
        }

        public Criteria andBankNameEqualTo(String value) {
            addCriterion("bank_name =", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotEqualTo(String value) {
            addCriterion("bank_name <>", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameGreaterThan(String value) {
            addCriterion("bank_name >", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("bank_name >=", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLessThan(String value) {
            addCriterion("bank_name <", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLessThanOrEqualTo(String value) {
            addCriterion("bank_name <=", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLike(String value) {
            addCriterion("bank_name like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotLike(String value) {
            addCriterion("bank_name not like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameIn(List<String> values) {
            addCriterion("bank_name in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotIn(List<String> values) {
            addCriterion("bank_name not in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameBetween(String value1, String value2) {
            addCriterion("bank_name between", value1, value2, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotBetween(String value1, String value2) {
            addCriterion("bank_name not between", value1, value2, "bankName");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoIsNull() {
            addCriterion("bgw_order_no is null");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoIsNotNull() {
            addCriterion("bgw_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoEqualTo(String value) {
            addCriterion("bgw_order_no =", value, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoNotEqualTo(String value) {
            addCriterion("bgw_order_no <>", value, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoGreaterThan(String value) {
            addCriterion("bgw_order_no >", value, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("bgw_order_no >=", value, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoLessThan(String value) {
            addCriterion("bgw_order_no <", value, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoLessThanOrEqualTo(String value) {
            addCriterion("bgw_order_no <=", value, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoLike(String value) {
            addCriterion("bgw_order_no like", value, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoNotLike(String value) {
            addCriterion("bgw_order_no not like", value, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoIn(List<String> values) {
            addCriterion("bgw_order_no in", values, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoNotIn(List<String> values) {
            addCriterion("bgw_order_no not in", values, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoBetween(String value1, String value2) {
            addCriterion("bgw_order_no between", value1, value2, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andBgwOrderNoNotBetween(String value1, String value2) {
            addCriterion("bgw_order_no not between", value1, value2, "bgwOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayBindOrderNoIsNull() {
            addCriterion("pay_bind_order_no is null");
            return (Criteria) this;
        }

        public Criteria andPayBindOrderNoIsNotNull() {
            addCriterion("pay_bind_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andPayBindOrderNoEqualTo(String value) {
            addCriterion("pay_bind_order_no =", value, "payBindOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayBindOrderNoNotEqualTo(String value) {
            addCriterion("pay_bind_order_no <>", value, "payBindOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayBindOrderNoGreaterThan(String value) {
            addCriterion("pay_bind_order_no >", value, "payBindOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayBindOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("pay_bind_order_no >=", value, "payBindOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayBindOrderNoLessThan(String value) {
            addCriterion("pay_bind_order_no <", value, "payBindOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayBindOrderNoLessThanOrEqualTo(String value) {
            addCriterion("pay_bind_order_no <=", value, "payBindOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayBindOrderNoLike(String value) {
            addCriterion("pay_bind_order_no like", value, "payBindOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayBindOrderNoNotLike(String value) {
            addCriterion("pay_bind_order_no not like", value, "payBindOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayBindOrderNoIn(List<String> values) {
            addCriterion("pay_bind_order_no in", values, "payBindOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayBindOrderNoNotIn(List<String> values) {
            addCriterion("pay_bind_order_no not in", values, "payBindOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayBindOrderNoBetween(String value1, String value2) {
            addCriterion("pay_bind_order_no between", value1, value2, "payBindOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayBindOrderNoNotBetween(String value1, String value2) {
            addCriterion("pay_bind_order_no not between", value1, value2, "payBindOrderNo");
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

        public Criteria andPayBindIdIsNull() {
            addCriterion("pay_bind_id is null");
            return (Criteria) this;
        }

        public Criteria andPayBindIdIsNotNull() {
            addCriterion("pay_bind_id is not null");
            return (Criteria) this;
        }

        public Criteria andPayBindIdEqualTo(String value) {
            addCriterion("pay_bind_id =", value, "payBindId");
            return (Criteria) this;
        }

        public Criteria andPayBindIdNotEqualTo(String value) {
            addCriterion("pay_bind_id <>", value, "payBindId");
            return (Criteria) this;
        }

        public Criteria andPayBindIdGreaterThan(String value) {
            addCriterion("pay_bind_id >", value, "payBindId");
            return (Criteria) this;
        }

        public Criteria andPayBindIdGreaterThanOrEqualTo(String value) {
            addCriterion("pay_bind_id >=", value, "payBindId");
            return (Criteria) this;
        }

        public Criteria andPayBindIdLessThan(String value) {
            addCriterion("pay_bind_id <", value, "payBindId");
            return (Criteria) this;
        }

        public Criteria andPayBindIdLessThanOrEqualTo(String value) {
            addCriterion("pay_bind_id <=", value, "payBindId");
            return (Criteria) this;
        }

        public Criteria andPayBindIdLike(String value) {
            addCriterion("pay_bind_id like", value, "payBindId");
            return (Criteria) this;
        }

        public Criteria andPayBindIdNotLike(String value) {
            addCriterion("pay_bind_id not like", value, "payBindId");
            return (Criteria) this;
        }

        public Criteria andPayBindIdIn(List<String> values) {
            addCriterion("pay_bind_id in", values, "payBindId");
            return (Criteria) this;
        }

        public Criteria andPayBindIdNotIn(List<String> values) {
            addCriterion("pay_bind_id not in", values, "payBindId");
            return (Criteria) this;
        }

        public Criteria andPayBindIdBetween(String value1, String value2) {
            addCriterion("pay_bind_id between", value1, value2, "payBindId");
            return (Criteria) this;
        }

        public Criteria andPayBindIdNotBetween(String value1, String value2) {
            addCriterion("pay_bind_id not between", value1, value2, "payBindId");
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

        public Criteria andIsFirstIsNull() {
            addCriterion("is_first is null");
            return (Criteria) this;
        }

        public Criteria andIsFirstIsNotNull() {
            addCriterion("is_first is not null");
            return (Criteria) this;
        }

        public Criteria andIsFirstEqualTo(Integer value) {
            addCriterion("is_first =", value, "isFirst");
            return (Criteria) this;
        }

        public Criteria andIsFirstNotEqualTo(Integer value) {
            addCriterion("is_first <>", value, "isFirst");
            return (Criteria) this;
        }

        public Criteria andIsFirstGreaterThan(Integer value) {
            addCriterion("is_first >", value, "isFirst");
            return (Criteria) this;
        }

        public Criteria andIsFirstGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_first >=", value, "isFirst");
            return (Criteria) this;
        }

        public Criteria andIsFirstLessThan(Integer value) {
            addCriterion("is_first <", value, "isFirst");
            return (Criteria) this;
        }

        public Criteria andIsFirstLessThanOrEqualTo(Integer value) {
            addCriterion("is_first <=", value, "isFirst");
            return (Criteria) this;
        }

        public Criteria andIsFirstIn(List<Integer> values) {
            addCriterion("is_first in", values, "isFirst");
            return (Criteria) this;
        }

        public Criteria andIsFirstNotIn(List<Integer> values) {
            addCriterion("is_first not in", values, "isFirst");
            return (Criteria) this;
        }

        public Criteria andIsFirstBetween(Integer value1, Integer value2) {
            addCriterion("is_first between", value1, value2, "isFirst");
            return (Criteria) this;
        }

        public Criteria andIsFirstNotBetween(Integer value1, Integer value2) {
            addCriterion("is_first not between", value1, value2, "isFirst");
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