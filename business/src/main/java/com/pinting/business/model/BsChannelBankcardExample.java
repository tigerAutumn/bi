package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsChannelBankcardExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsChannelBankcardExample() {
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

        public Criteria andCardNoIsNull() {
            addCriterion("card_no is null");
            return (Criteria) this;
        }

        public Criteria andCardNoIsNotNull() {
            addCriterion("card_no is not null");
            return (Criteria) this;
        }

        public Criteria andCardNoEqualTo(String value) {
            addCriterion("card_no =", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotEqualTo(String value) {
            addCriterion("card_no <>", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoGreaterThan(String value) {
            addCriterion("card_no >", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoGreaterThanOrEqualTo(String value) {
            addCriterion("card_no >=", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLessThan(String value) {
            addCriterion("card_no <", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLessThanOrEqualTo(String value) {
            addCriterion("card_no <=", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLike(String value) {
            addCriterion("card_no like", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotLike(String value) {
            addCriterion("card_no not like", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoIn(List<String> values) {
            addCriterion("card_no in", values, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotIn(List<String> values) {
            addCriterion("card_no not in", values, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoBetween(String value1, String value2) {
            addCriterion("card_no between", value1, value2, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotBetween(String value1, String value2) {
            addCriterion("card_no not between", value1, value2, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardOwnerIsNull() {
            addCriterion("card_owner is null");
            return (Criteria) this;
        }

        public Criteria andCardOwnerIsNotNull() {
            addCriterion("card_owner is not null");
            return (Criteria) this;
        }

        public Criteria andCardOwnerEqualTo(String value) {
            addCriterion("card_owner =", value, "cardOwner");
            return (Criteria) this;
        }

        public Criteria andCardOwnerNotEqualTo(String value) {
            addCriterion("card_owner <>", value, "cardOwner");
            return (Criteria) this;
        }

        public Criteria andCardOwnerGreaterThan(String value) {
            addCriterion("card_owner >", value, "cardOwner");
            return (Criteria) this;
        }

        public Criteria andCardOwnerGreaterThanOrEqualTo(String value) {
            addCriterion("card_owner >=", value, "cardOwner");
            return (Criteria) this;
        }

        public Criteria andCardOwnerLessThan(String value) {
            addCriterion("card_owner <", value, "cardOwner");
            return (Criteria) this;
        }

        public Criteria andCardOwnerLessThanOrEqualTo(String value) {
            addCriterion("card_owner <=", value, "cardOwner");
            return (Criteria) this;
        }

        public Criteria andCardOwnerLike(String value) {
            addCriterion("card_owner like", value, "cardOwner");
            return (Criteria) this;
        }

        public Criteria andCardOwnerNotLike(String value) {
            addCriterion("card_owner not like", value, "cardOwner");
            return (Criteria) this;
        }

        public Criteria andCardOwnerIn(List<String> values) {
            addCriterion("card_owner in", values, "cardOwner");
            return (Criteria) this;
        }

        public Criteria andCardOwnerNotIn(List<String> values) {
            addCriterion("card_owner not in", values, "cardOwner");
            return (Criteria) this;
        }

        public Criteria andCardOwnerBetween(String value1, String value2) {
            addCriterion("card_owner between", value1, value2, "cardOwner");
            return (Criteria) this;
        }

        public Criteria andCardOwnerNotBetween(String value1, String value2) {
            addCriterion("card_owner not between", value1, value2, "cardOwner");
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

        public Criteria andBankIdIsNull() {
            addCriterion("bank_id is null");
            return (Criteria) this;
        }

        public Criteria andBankIdIsNotNull() {
            addCriterion("bank_id is not null");
            return (Criteria) this;
        }

        public Criteria andBankIdEqualTo(Integer value) {
            addCriterion("bank_id =", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotEqualTo(Integer value) {
            addCriterion("bank_id <>", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdGreaterThan(Integer value) {
            addCriterion("bank_id >", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("bank_id >=", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLessThan(Integer value) {
            addCriterion("bank_id <", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLessThanOrEqualTo(Integer value) {
            addCriterion("bank_id <=", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdIn(List<Integer> values) {
            addCriterion("bank_id in", values, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotIn(List<Integer> values) {
            addCriterion("bank_id not in", values, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdBetween(Integer value1, Integer value2) {
            addCriterion("bank_id between", value1, value2, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotBetween(Integer value1, Integer value2) {
            addCriterion("bank_id not between", value1, value2, "bankId");
            return (Criteria) this;
        }

        public Criteria andBindTimeIsNull() {
            addCriterion("bind_time is null");
            return (Criteria) this;
        }

        public Criteria andBindTimeIsNotNull() {
            addCriterion("bind_time is not null");
            return (Criteria) this;
        }

        public Criteria andBindTimeEqualTo(Date value) {
            addCriterion("bind_time =", value, "bindTime");
            return (Criteria) this;
        }

        public Criteria andBindTimeNotEqualTo(Date value) {
            addCriterion("bind_time <>", value, "bindTime");
            return (Criteria) this;
        }

        public Criteria andBindTimeGreaterThan(Date value) {
            addCriterion("bind_time >", value, "bindTime");
            return (Criteria) this;
        }

        public Criteria andBindTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("bind_time >=", value, "bindTime");
            return (Criteria) this;
        }

        public Criteria andBindTimeLessThan(Date value) {
            addCriterion("bind_time <", value, "bindTime");
            return (Criteria) this;
        }

        public Criteria andBindTimeLessThanOrEqualTo(Date value) {
            addCriterion("bind_time <=", value, "bindTime");
            return (Criteria) this;
        }

        public Criteria andBindTimeIn(List<Date> values) {
            addCriterion("bind_time in", values, "bindTime");
            return (Criteria) this;
        }

        public Criteria andBindTimeNotIn(List<Date> values) {
            addCriterion("bind_time not in", values, "bindTime");
            return (Criteria) this;
        }

        public Criteria andBindTimeBetween(Date value1, Date value2) {
            addCriterion("bind_time between", value1, value2, "bindTime");
            return (Criteria) this;
        }

        public Criteria andBindTimeNotBetween(Date value1, Date value2) {
            addCriterion("bind_time not between", value1, value2, "bindTime");
            return (Criteria) this;
        }

        public Criteria andUnbindTimeIsNull() {
            addCriterion("unbind_time is null");
            return (Criteria) this;
        }

        public Criteria andUnbindTimeIsNotNull() {
            addCriterion("unbind_time is not null");
            return (Criteria) this;
        }

        public Criteria andUnbindTimeEqualTo(Date value) {
            addCriterion("unbind_time =", value, "unbindTime");
            return (Criteria) this;
        }

        public Criteria andUnbindTimeNotEqualTo(Date value) {
            addCriterion("unbind_time <>", value, "unbindTime");
            return (Criteria) this;
        }

        public Criteria andUnbindTimeGreaterThan(Date value) {
            addCriterion("unbind_time >", value, "unbindTime");
            return (Criteria) this;
        }

        public Criteria andUnbindTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("unbind_time >=", value, "unbindTime");
            return (Criteria) this;
        }

        public Criteria andUnbindTimeLessThan(Date value) {
            addCriterion("unbind_time <", value, "unbindTime");
            return (Criteria) this;
        }

        public Criteria andUnbindTimeLessThanOrEqualTo(Date value) {
            addCriterion("unbind_time <=", value, "unbindTime");
            return (Criteria) this;
        }

        public Criteria andUnbindTimeIn(List<Date> values) {
            addCriterion("unbind_time in", values, "unbindTime");
            return (Criteria) this;
        }

        public Criteria andUnbindTimeNotIn(List<Date> values) {
            addCriterion("unbind_time not in", values, "unbindTime");
            return (Criteria) this;
        }

        public Criteria andUnbindTimeBetween(Date value1, Date value2) {
            addCriterion("unbind_time between", value1, value2, "unbindTime");
            return (Criteria) this;
        }

        public Criteria andUnbindTimeNotBetween(Date value1, Date value2) {
            addCriterion("unbind_time not between", value1, value2, "unbindTime");
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

        public Criteria andSubBranchNameIsNull() {
            addCriterion("sub_branch_name is null");
            return (Criteria) this;
        }

        public Criteria andSubBranchNameIsNotNull() {
            addCriterion("sub_branch_name is not null");
            return (Criteria) this;
        }

        public Criteria andSubBranchNameEqualTo(String value) {
            addCriterion("sub_branch_name =", value, "subBranchName");
            return (Criteria) this;
        }

        public Criteria andSubBranchNameNotEqualTo(String value) {
            addCriterion("sub_branch_name <>", value, "subBranchName");
            return (Criteria) this;
        }

        public Criteria andSubBranchNameGreaterThan(String value) {
            addCriterion("sub_branch_name >", value, "subBranchName");
            return (Criteria) this;
        }

        public Criteria andSubBranchNameGreaterThanOrEqualTo(String value) {
            addCriterion("sub_branch_name >=", value, "subBranchName");
            return (Criteria) this;
        }

        public Criteria andSubBranchNameLessThan(String value) {
            addCriterion("sub_branch_name <", value, "subBranchName");
            return (Criteria) this;
        }

        public Criteria andSubBranchNameLessThanOrEqualTo(String value) {
            addCriterion("sub_branch_name <=", value, "subBranchName");
            return (Criteria) this;
        }

        public Criteria andSubBranchNameLike(String value) {
            addCriterion("sub_branch_name like", value, "subBranchName");
            return (Criteria) this;
        }

        public Criteria andSubBranchNameNotLike(String value) {
            addCriterion("sub_branch_name not like", value, "subBranchName");
            return (Criteria) this;
        }

        public Criteria andSubBranchNameIn(List<String> values) {
            addCriterion("sub_branch_name in", values, "subBranchName");
            return (Criteria) this;
        }

        public Criteria andSubBranchNameNotIn(List<String> values) {
            addCriterion("sub_branch_name not in", values, "subBranchName");
            return (Criteria) this;
        }

        public Criteria andSubBranchNameBetween(String value1, String value2) {
            addCriterion("sub_branch_name between", value1, value2, "subBranchName");
            return (Criteria) this;
        }

        public Criteria andSubBranchNameNotBetween(String value1, String value2) {
            addCriterion("sub_branch_name not between", value1, value2, "subBranchName");
            return (Criteria) this;
        }

        public Criteria andOpenProvinceIsNull() {
            addCriterion("open_province is null");
            return (Criteria) this;
        }

        public Criteria andOpenProvinceIsNotNull() {
            addCriterion("open_province is not null");
            return (Criteria) this;
        }

        public Criteria andOpenProvinceEqualTo(Integer value) {
            addCriterion("open_province =", value, "openProvince");
            return (Criteria) this;
        }

        public Criteria andOpenProvinceNotEqualTo(Integer value) {
            addCriterion("open_province <>", value, "openProvince");
            return (Criteria) this;
        }

        public Criteria andOpenProvinceGreaterThan(Integer value) {
            addCriterion("open_province >", value, "openProvince");
            return (Criteria) this;
        }

        public Criteria andOpenProvinceGreaterThanOrEqualTo(Integer value) {
            addCriterion("open_province >=", value, "openProvince");
            return (Criteria) this;
        }

        public Criteria andOpenProvinceLessThan(Integer value) {
            addCriterion("open_province <", value, "openProvince");
            return (Criteria) this;
        }

        public Criteria andOpenProvinceLessThanOrEqualTo(Integer value) {
            addCriterion("open_province <=", value, "openProvince");
            return (Criteria) this;
        }

        public Criteria andOpenProvinceIn(List<Integer> values) {
            addCriterion("open_province in", values, "openProvince");
            return (Criteria) this;
        }

        public Criteria andOpenProvinceNotIn(List<Integer> values) {
            addCriterion("open_province not in", values, "openProvince");
            return (Criteria) this;
        }

        public Criteria andOpenProvinceBetween(Integer value1, Integer value2) {
            addCriterion("open_province between", value1, value2, "openProvince");
            return (Criteria) this;
        }

        public Criteria andOpenProvinceNotBetween(Integer value1, Integer value2) {
            addCriterion("open_province not between", value1, value2, "openProvince");
            return (Criteria) this;
        }

        public Criteria andOpenCityIsNull() {
            addCriterion("open_city is null");
            return (Criteria) this;
        }

        public Criteria andOpenCityIsNotNull() {
            addCriterion("open_city is not null");
            return (Criteria) this;
        }

        public Criteria andOpenCityEqualTo(Integer value) {
            addCriterion("open_city =", value, "openCity");
            return (Criteria) this;
        }

        public Criteria andOpenCityNotEqualTo(Integer value) {
            addCriterion("open_city <>", value, "openCity");
            return (Criteria) this;
        }

        public Criteria andOpenCityGreaterThan(Integer value) {
            addCriterion("open_city >", value, "openCity");
            return (Criteria) this;
        }

        public Criteria andOpenCityGreaterThanOrEqualTo(Integer value) {
            addCriterion("open_city >=", value, "openCity");
            return (Criteria) this;
        }

        public Criteria andOpenCityLessThan(Integer value) {
            addCriterion("open_city <", value, "openCity");
            return (Criteria) this;
        }

        public Criteria andOpenCityLessThanOrEqualTo(Integer value) {
            addCriterion("open_city <=", value, "openCity");
            return (Criteria) this;
        }

        public Criteria andOpenCityIn(List<Integer> values) {
            addCriterion("open_city in", values, "openCity");
            return (Criteria) this;
        }

        public Criteria andOpenCityNotIn(List<Integer> values) {
            addCriterion("open_city not in", values, "openCity");
            return (Criteria) this;
        }

        public Criteria andOpenCityBetween(Integer value1, Integer value2) {
            addCriterion("open_city between", value1, value2, "openCity");
            return (Criteria) this;
        }

        public Criteria andOpenCityNotBetween(Integer value1, Integer value2) {
            addCriterion("open_city not between", value1, value2, "openCity");
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