package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LnSubjectExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LnSubjectExample() {
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

        public Criteria andChargeRuleIdIsNull() {
            addCriterion("charge_rule_id is null");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdIsNotNull() {
            addCriterion("charge_rule_id is not null");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdEqualTo(Integer value) {
            addCriterion("charge_rule_id =", value, "chargeRuleId");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdNotEqualTo(Integer value) {
            addCriterion("charge_rule_id <>", value, "chargeRuleId");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdGreaterThan(Integer value) {
            addCriterion("charge_rule_id >", value, "chargeRuleId");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("charge_rule_id >=", value, "chargeRuleId");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdLessThan(Integer value) {
            addCriterion("charge_rule_id <", value, "chargeRuleId");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdLessThanOrEqualTo(Integer value) {
            addCriterion("charge_rule_id <=", value, "chargeRuleId");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdIn(List<Integer> values) {
            addCriterion("charge_rule_id in", values, "chargeRuleId");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdNotIn(List<Integer> values) {
            addCriterion("charge_rule_id not in", values, "chargeRuleId");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdBetween(Integer value1, Integer value2) {
            addCriterion("charge_rule_id between", value1, value2, "chargeRuleId");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdNotBetween(Integer value1, Integer value2) {
            addCriterion("charge_rule_id not between", value1, value2, "chargeRuleId");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeIsNull() {
            addCriterion("subject_code is null");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeIsNotNull() {
            addCriterion("subject_code is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeEqualTo(String value) {
            addCriterion("subject_code =", value, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeNotEqualTo(String value) {
            addCriterion("subject_code <>", value, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeGreaterThan(String value) {
            addCriterion("subject_code >", value, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeGreaterThanOrEqualTo(String value) {
            addCriterion("subject_code >=", value, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeLessThan(String value) {
            addCriterion("subject_code <", value, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeLessThanOrEqualTo(String value) {
            addCriterion("subject_code <=", value, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeLike(String value) {
            addCriterion("subject_code like", value, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeNotLike(String value) {
            addCriterion("subject_code not like", value, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeIn(List<String> values) {
            addCriterion("subject_code in", values, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeNotIn(List<String> values) {
            addCriterion("subject_code not in", values, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeBetween(String value1, String value2) {
            addCriterion("subject_code between", value1, value2, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeNotBetween(String value1, String value2) {
            addCriterion("subject_code not between", value1, value2, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectNameIsNull() {
            addCriterion("subject_name is null");
            return (Criteria) this;
        }

        public Criteria andSubjectNameIsNotNull() {
            addCriterion("subject_name is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectNameEqualTo(String value) {
            addCriterion("subject_name =", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameNotEqualTo(String value) {
            addCriterion("subject_name <>", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameGreaterThan(String value) {
            addCriterion("subject_name >", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameGreaterThanOrEqualTo(String value) {
            addCriterion("subject_name >=", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameLessThan(String value) {
            addCriterion("subject_name <", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameLessThanOrEqualTo(String value) {
            addCriterion("subject_name <=", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameLike(String value) {
            addCriterion("subject_name like", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameNotLike(String value) {
            addCriterion("subject_name not like", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameIn(List<String> values) {
            addCriterion("subject_name in", values, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameNotIn(List<String> values) {
            addCriterion("subject_name not in", values, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameBetween(String value1, String value2) {
            addCriterion("subject_name between", value1, value2, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameNotBetween(String value1, String value2) {
            addCriterion("subject_name not between", value1, value2, "subjectName");
            return (Criteria) this;
        }

        public Criteria andRepayOrderIsNull() {
            addCriterion("repay_order is null");
            return (Criteria) this;
        }

        public Criteria andRepayOrderIsNotNull() {
            addCriterion("repay_order is not null");
            return (Criteria) this;
        }

        public Criteria andRepayOrderEqualTo(Integer value) {
            addCriterion("repay_order =", value, "repayOrder");
            return (Criteria) this;
        }

        public Criteria andRepayOrderNotEqualTo(Integer value) {
            addCriterion("repay_order <>", value, "repayOrder");
            return (Criteria) this;
        }

        public Criteria andRepayOrderGreaterThan(Integer value) {
            addCriterion("repay_order >", value, "repayOrder");
            return (Criteria) this;
        }

        public Criteria andRepayOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_order >=", value, "repayOrder");
            return (Criteria) this;
        }

        public Criteria andRepayOrderLessThan(Integer value) {
            addCriterion("repay_order <", value, "repayOrder");
            return (Criteria) this;
        }

        public Criteria andRepayOrderLessThanOrEqualTo(Integer value) {
            addCriterion("repay_order <=", value, "repayOrder");
            return (Criteria) this;
        }

        public Criteria andRepayOrderIn(List<Integer> values) {
            addCriterion("repay_order in", values, "repayOrder");
            return (Criteria) this;
        }

        public Criteria andRepayOrderNotIn(List<Integer> values) {
            addCriterion("repay_order not in", values, "repayOrder");
            return (Criteria) this;
        }

        public Criteria andRepayOrderBetween(Integer value1, Integer value2) {
            addCriterion("repay_order between", value1, value2, "repayOrder");
            return (Criteria) this;
        }

        public Criteria andRepayOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_order not between", value1, value2, "repayOrder");
            return (Criteria) this;
        }

        public Criteria andReserveRuleIsNull() {
            addCriterion("reserve_rule is null");
            return (Criteria) this;
        }

        public Criteria andReserveRuleIsNotNull() {
            addCriterion("reserve_rule is not null");
            return (Criteria) this;
        }

        public Criteria andReserveRuleEqualTo(String value) {
            addCriterion("reserve_rule =", value, "reserveRule");
            return (Criteria) this;
        }

        public Criteria andReserveRuleNotEqualTo(String value) {
            addCriterion("reserve_rule <>", value, "reserveRule");
            return (Criteria) this;
        }

        public Criteria andReserveRuleGreaterThan(String value) {
            addCriterion("reserve_rule >", value, "reserveRule");
            return (Criteria) this;
        }

        public Criteria andReserveRuleGreaterThanOrEqualTo(String value) {
            addCriterion("reserve_rule >=", value, "reserveRule");
            return (Criteria) this;
        }

        public Criteria andReserveRuleLessThan(String value) {
            addCriterion("reserve_rule <", value, "reserveRule");
            return (Criteria) this;
        }

        public Criteria andReserveRuleLessThanOrEqualTo(String value) {
            addCriterion("reserve_rule <=", value, "reserveRule");
            return (Criteria) this;
        }

        public Criteria andReserveRuleLike(String value) {
            addCriterion("reserve_rule like", value, "reserveRule");
            return (Criteria) this;
        }

        public Criteria andReserveRuleNotLike(String value) {
            addCriterion("reserve_rule not like", value, "reserveRule");
            return (Criteria) this;
        }

        public Criteria andReserveRuleIn(List<String> values) {
            addCriterion("reserve_rule in", values, "reserveRule");
            return (Criteria) this;
        }

        public Criteria andReserveRuleNotIn(List<String> values) {
            addCriterion("reserve_rule not in", values, "reserveRule");
            return (Criteria) this;
        }

        public Criteria andReserveRuleBetween(String value1, String value2) {
            addCriterion("reserve_rule between", value1, value2, "reserveRule");
            return (Criteria) this;
        }

        public Criteria andReserveRuleNotBetween(String value1, String value2) {
            addCriterion("reserve_rule not between", value1, value2, "reserveRule");
            return (Criteria) this;
        }

        public Criteria andCalRuleIsNull() {
            addCriterion("cal_rule is null");
            return (Criteria) this;
        }

        public Criteria andCalRuleIsNotNull() {
            addCriterion("cal_rule is not null");
            return (Criteria) this;
        }

        public Criteria andCalRuleEqualTo(String value) {
            addCriterion("cal_rule =", value, "calRule");
            return (Criteria) this;
        }

        public Criteria andCalRuleNotEqualTo(String value) {
            addCriterion("cal_rule <>", value, "calRule");
            return (Criteria) this;
        }

        public Criteria andCalRuleGreaterThan(String value) {
            addCriterion("cal_rule >", value, "calRule");
            return (Criteria) this;
        }

        public Criteria andCalRuleGreaterThanOrEqualTo(String value) {
            addCriterion("cal_rule >=", value, "calRule");
            return (Criteria) this;
        }

        public Criteria andCalRuleLessThan(String value) {
            addCriterion("cal_rule <", value, "calRule");
            return (Criteria) this;
        }

        public Criteria andCalRuleLessThanOrEqualTo(String value) {
            addCriterion("cal_rule <=", value, "calRule");
            return (Criteria) this;
        }

        public Criteria andCalRuleLike(String value) {
            addCriterion("cal_rule like", value, "calRule");
            return (Criteria) this;
        }

        public Criteria andCalRuleNotLike(String value) {
            addCriterion("cal_rule not like", value, "calRule");
            return (Criteria) this;
        }

        public Criteria andCalRuleIn(List<String> values) {
            addCriterion("cal_rule in", values, "calRule");
            return (Criteria) this;
        }

        public Criteria andCalRuleNotIn(List<String> values) {
            addCriterion("cal_rule not in", values, "calRule");
            return (Criteria) this;
        }

        public Criteria andCalRuleBetween(String value1, String value2) {
            addCriterion("cal_rule between", value1, value2, "calRule");
            return (Criteria) this;
        }

        public Criteria andCalRuleNotBetween(String value1, String value2) {
            addCriterion("cal_rule not between", value1, value2, "calRule");
            return (Criteria) this;
        }

        public Criteria andLeftRuleIsNull() {
            addCriterion("left_rule is null");
            return (Criteria) this;
        }

        public Criteria andLeftRuleIsNotNull() {
            addCriterion("left_rule is not null");
            return (Criteria) this;
        }

        public Criteria andLeftRuleEqualTo(String value) {
            addCriterion("left_rule =", value, "leftRule");
            return (Criteria) this;
        }

        public Criteria andLeftRuleNotEqualTo(String value) {
            addCriterion("left_rule <>", value, "leftRule");
            return (Criteria) this;
        }

        public Criteria andLeftRuleGreaterThan(String value) {
            addCriterion("left_rule >", value, "leftRule");
            return (Criteria) this;
        }

        public Criteria andLeftRuleGreaterThanOrEqualTo(String value) {
            addCriterion("left_rule >=", value, "leftRule");
            return (Criteria) this;
        }

        public Criteria andLeftRuleLessThan(String value) {
            addCriterion("left_rule <", value, "leftRule");
            return (Criteria) this;
        }

        public Criteria andLeftRuleLessThanOrEqualTo(String value) {
            addCriterion("left_rule <=", value, "leftRule");
            return (Criteria) this;
        }

        public Criteria andLeftRuleLike(String value) {
            addCriterion("left_rule like", value, "leftRule");
            return (Criteria) this;
        }

        public Criteria andLeftRuleNotLike(String value) {
            addCriterion("left_rule not like", value, "leftRule");
            return (Criteria) this;
        }

        public Criteria andLeftRuleIn(List<String> values) {
            addCriterion("left_rule in", values, "leftRule");
            return (Criteria) this;
        }

        public Criteria andLeftRuleNotIn(List<String> values) {
            addCriterion("left_rule not in", values, "leftRule");
            return (Criteria) this;
        }

        public Criteria andLeftRuleBetween(String value1, String value2) {
            addCriterion("left_rule between", value1, value2, "leftRule");
            return (Criteria) this;
        }

        public Criteria andLeftRuleNotBetween(String value1, String value2) {
            addCriterion("left_rule not between", value1, value2, "leftRule");
            return (Criteria) this;
        }

        public Criteria andRatetypeIsNull() {
            addCriterion("rateType is null");
            return (Criteria) this;
        }

        public Criteria andRatetypeIsNotNull() {
            addCriterion("rateType is not null");
            return (Criteria) this;
        }

        public Criteria andRatetypeEqualTo(String value) {
            addCriterion("rateType =", value, "ratetype");
            return (Criteria) this;
        }

        public Criteria andRatetypeNotEqualTo(String value) {
            addCriterion("rateType <>", value, "ratetype");
            return (Criteria) this;
        }

        public Criteria andRatetypeGreaterThan(String value) {
            addCriterion("rateType >", value, "ratetype");
            return (Criteria) this;
        }

        public Criteria andRatetypeGreaterThanOrEqualTo(String value) {
            addCriterion("rateType >=", value, "ratetype");
            return (Criteria) this;
        }

        public Criteria andRatetypeLessThan(String value) {
            addCriterion("rateType <", value, "ratetype");
            return (Criteria) this;
        }

        public Criteria andRatetypeLessThanOrEqualTo(String value) {
            addCriterion("rateType <=", value, "ratetype");
            return (Criteria) this;
        }

        public Criteria andRatetypeLike(String value) {
            addCriterion("rateType like", value, "ratetype");
            return (Criteria) this;
        }

        public Criteria andRatetypeNotLike(String value) {
            addCriterion("rateType not like", value, "ratetype");
            return (Criteria) this;
        }

        public Criteria andRatetypeIn(List<String> values) {
            addCriterion("rateType in", values, "ratetype");
            return (Criteria) this;
        }

        public Criteria andRatetypeNotIn(List<String> values) {
            addCriterion("rateType not in", values, "ratetype");
            return (Criteria) this;
        }

        public Criteria andRatetypeBetween(String value1, String value2) {
            addCriterion("rateType between", value1, value2, "ratetype");
            return (Criteria) this;
        }

        public Criteria andRatetypeNotBetween(String value1, String value2) {
            addCriterion("rateType not between", value1, value2, "ratetype");
            return (Criteria) this;
        }

        public Criteria andNumValueIsNull() {
            addCriterion("num_value is null");
            return (Criteria) this;
        }

        public Criteria andNumValueIsNotNull() {
            addCriterion("num_value is not null");
            return (Criteria) this;
        }

        public Criteria andNumValueEqualTo(Double value) {
            addCriterion("num_value =", value, "numValue");
            return (Criteria) this;
        }

        public Criteria andNumValueNotEqualTo(Double value) {
            addCriterion("num_value <>", value, "numValue");
            return (Criteria) this;
        }

        public Criteria andNumValueGreaterThan(Double value) {
            addCriterion("num_value >", value, "numValue");
            return (Criteria) this;
        }

        public Criteria andNumValueGreaterThanOrEqualTo(Double value) {
            addCriterion("num_value >=", value, "numValue");
            return (Criteria) this;
        }

        public Criteria andNumValueLessThan(Double value) {
            addCriterion("num_value <", value, "numValue");
            return (Criteria) this;
        }

        public Criteria andNumValueLessThanOrEqualTo(Double value) {
            addCriterion("num_value <=", value, "numValue");
            return (Criteria) this;
        }

        public Criteria andNumValueIn(List<Double> values) {
            addCriterion("num_value in", values, "numValue");
            return (Criteria) this;
        }

        public Criteria andNumValueNotIn(List<Double> values) {
            addCriterion("num_value not in", values, "numValue");
            return (Criteria) this;
        }

        public Criteria andNumValueBetween(Double value1, Double value2) {
            addCriterion("num_value between", value1, value2, "numValue");
            return (Criteria) this;
        }

        public Criteria andNumValueNotBetween(Double value1, Double value2) {
            addCriterion("num_value not between", value1, value2, "numValue");
            return (Criteria) this;
        }

        public Criteria andCustomizedClassIsNull() {
            addCriterion("customized_class is null");
            return (Criteria) this;
        }

        public Criteria andCustomizedClassIsNotNull() {
            addCriterion("customized_class is not null");
            return (Criteria) this;
        }

        public Criteria andCustomizedClassEqualTo(String value) {
            addCriterion("customized_class =", value, "customizedClass");
            return (Criteria) this;
        }

        public Criteria andCustomizedClassNotEqualTo(String value) {
            addCriterion("customized_class <>", value, "customizedClass");
            return (Criteria) this;
        }

        public Criteria andCustomizedClassGreaterThan(String value) {
            addCriterion("customized_class >", value, "customizedClass");
            return (Criteria) this;
        }

        public Criteria andCustomizedClassGreaterThanOrEqualTo(String value) {
            addCriterion("customized_class >=", value, "customizedClass");
            return (Criteria) this;
        }

        public Criteria andCustomizedClassLessThan(String value) {
            addCriterion("customized_class <", value, "customizedClass");
            return (Criteria) this;
        }

        public Criteria andCustomizedClassLessThanOrEqualTo(String value) {
            addCriterion("customized_class <=", value, "customizedClass");
            return (Criteria) this;
        }

        public Criteria andCustomizedClassLike(String value) {
            addCriterion("customized_class like", value, "customizedClass");
            return (Criteria) this;
        }

        public Criteria andCustomizedClassNotLike(String value) {
            addCriterion("customized_class not like", value, "customizedClass");
            return (Criteria) this;
        }

        public Criteria andCustomizedClassIn(List<String> values) {
            addCriterion("customized_class in", values, "customizedClass");
            return (Criteria) this;
        }

        public Criteria andCustomizedClassNotIn(List<String> values) {
            addCriterion("customized_class not in", values, "customizedClass");
            return (Criteria) this;
        }

        public Criteria andCustomizedClassBetween(String value1, String value2) {
            addCriterion("customized_class between", value1, value2, "customizedClass");
            return (Criteria) this;
        }

        public Criteria andCustomizedClassNotBetween(String value1, String value2) {
            addCriterion("customized_class not between", value1, value2, "customizedClass");
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