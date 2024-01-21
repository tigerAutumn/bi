package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LnCompensateRelationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LnCompensateRelationExample() {
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

        public Criteria andDepPlanIdIsNull() {
            addCriterion("dep_plan_id is null");
            return (Criteria) this;
        }

        public Criteria andDepPlanIdIsNotNull() {
            addCriterion("dep_plan_id is not null");
            return (Criteria) this;
        }

        public Criteria andDepPlanIdEqualTo(Integer value) {
            addCriterion("dep_plan_id =", value, "depPlanId");
            return (Criteria) this;
        }

        public Criteria andDepPlanIdNotEqualTo(Integer value) {
            addCriterion("dep_plan_id <>", value, "depPlanId");
            return (Criteria) this;
        }

        public Criteria andDepPlanIdGreaterThan(Integer value) {
            addCriterion("dep_plan_id >", value, "depPlanId");
            return (Criteria) this;
        }

        public Criteria andDepPlanIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dep_plan_id >=", value, "depPlanId");
            return (Criteria) this;
        }

        public Criteria andDepPlanIdLessThan(Integer value) {
            addCriterion("dep_plan_id <", value, "depPlanId");
            return (Criteria) this;
        }

        public Criteria andDepPlanIdLessThanOrEqualTo(Integer value) {
            addCriterion("dep_plan_id <=", value, "depPlanId");
            return (Criteria) this;
        }

        public Criteria andDepPlanIdIn(List<Integer> values) {
            addCriterion("dep_plan_id in", values, "depPlanId");
            return (Criteria) this;
        }

        public Criteria andDepPlanIdNotIn(List<Integer> values) {
            addCriterion("dep_plan_id not in", values, "depPlanId");
            return (Criteria) this;
        }

        public Criteria andDepPlanIdBetween(Integer value1, Integer value2) {
            addCriterion("dep_plan_id between", value1, value2, "depPlanId");
            return (Criteria) this;
        }

        public Criteria andDepPlanIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dep_plan_id not between", value1, value2, "depPlanId");
            return (Criteria) this;
        }

        public Criteria andLoanRelationIdIsNull() {
            addCriterion("loan_relation_id is null");
            return (Criteria) this;
        }

        public Criteria andLoanRelationIdIsNotNull() {
            addCriterion("loan_relation_id is not null");
            return (Criteria) this;
        }

        public Criteria andLoanRelationIdEqualTo(Integer value) {
            addCriterion("loan_relation_id =", value, "loanRelationId");
            return (Criteria) this;
        }

        public Criteria andLoanRelationIdNotEqualTo(Integer value) {
            addCriterion("loan_relation_id <>", value, "loanRelationId");
            return (Criteria) this;
        }

        public Criteria andLoanRelationIdGreaterThan(Integer value) {
            addCriterion("loan_relation_id >", value, "loanRelationId");
            return (Criteria) this;
        }

        public Criteria andLoanRelationIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("loan_relation_id >=", value, "loanRelationId");
            return (Criteria) this;
        }

        public Criteria andLoanRelationIdLessThan(Integer value) {
            addCriterion("loan_relation_id <", value, "loanRelationId");
            return (Criteria) this;
        }

        public Criteria andLoanRelationIdLessThanOrEqualTo(Integer value) {
            addCriterion("loan_relation_id <=", value, "loanRelationId");
            return (Criteria) this;
        }

        public Criteria andLoanRelationIdIn(List<Integer> values) {
            addCriterion("loan_relation_id in", values, "loanRelationId");
            return (Criteria) this;
        }

        public Criteria andLoanRelationIdNotIn(List<Integer> values) {
            addCriterion("loan_relation_id not in", values, "loanRelationId");
            return (Criteria) this;
        }

        public Criteria andLoanRelationIdBetween(Integer value1, Integer value2) {
            addCriterion("loan_relation_id between", value1, value2, "loanRelationId");
            return (Criteria) this;
        }

        public Criteria andLoanRelationIdNotBetween(Integer value1, Integer value2) {
            addCriterion("loan_relation_id not between", value1, value2, "loanRelationId");
            return (Criteria) this;
        }

        public Criteria andCompUserIdIsNull() {
            addCriterion("comp_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCompUserIdIsNotNull() {
            addCriterion("comp_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCompUserIdEqualTo(Integer value) {
            addCriterion("comp_user_id =", value, "compUserId");
            return (Criteria) this;
        }

        public Criteria andCompUserIdNotEqualTo(Integer value) {
            addCriterion("comp_user_id <>", value, "compUserId");
            return (Criteria) this;
        }

        public Criteria andCompUserIdGreaterThan(Integer value) {
            addCriterion("comp_user_id >", value, "compUserId");
            return (Criteria) this;
        }

        public Criteria andCompUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("comp_user_id >=", value, "compUserId");
            return (Criteria) this;
        }

        public Criteria andCompUserIdLessThan(Integer value) {
            addCriterion("comp_user_id <", value, "compUserId");
            return (Criteria) this;
        }

        public Criteria andCompUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("comp_user_id <=", value, "compUserId");
            return (Criteria) this;
        }

        public Criteria andCompUserIdIn(List<Integer> values) {
            addCriterion("comp_user_id in", values, "compUserId");
            return (Criteria) this;
        }

        public Criteria andCompUserIdNotIn(List<Integer> values) {
            addCriterion("comp_user_id not in", values, "compUserId");
            return (Criteria) this;
        }

        public Criteria andCompUserIdBetween(Integer value1, Integer value2) {
            addCriterion("comp_user_id between", value1, value2, "compUserId");
            return (Criteria) this;
        }

        public Criteria andCompUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("comp_user_id not between", value1, value2, "compUserId");
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

        public Criteria andCompHfUserIdIsNull() {
            addCriterion("comp_hf_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCompHfUserIdIsNotNull() {
            addCriterion("comp_hf_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCompHfUserIdEqualTo(String value) {
            addCriterion("comp_hf_user_id =", value, "compHfUserId");
            return (Criteria) this;
        }

        public Criteria andCompHfUserIdNotEqualTo(String value) {
            addCriterion("comp_hf_user_id <>", value, "compHfUserId");
            return (Criteria) this;
        }

        public Criteria andCompHfUserIdGreaterThan(String value) {
            addCriterion("comp_hf_user_id >", value, "compHfUserId");
            return (Criteria) this;
        }

        public Criteria andCompHfUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("comp_hf_user_id >=", value, "compHfUserId");
            return (Criteria) this;
        }

        public Criteria andCompHfUserIdLessThan(String value) {
            addCriterion("comp_hf_user_id <", value, "compHfUserId");
            return (Criteria) this;
        }

        public Criteria andCompHfUserIdLessThanOrEqualTo(String value) {
            addCriterion("comp_hf_user_id <=", value, "compHfUserId");
            return (Criteria) this;
        }

        public Criteria andCompHfUserIdLike(String value) {
            addCriterion("comp_hf_user_id like", value, "compHfUserId");
            return (Criteria) this;
        }

        public Criteria andCompHfUserIdNotLike(String value) {
            addCriterion("comp_hf_user_id not like", value, "compHfUserId");
            return (Criteria) this;
        }

        public Criteria andCompHfUserIdIn(List<String> values) {
            addCriterion("comp_hf_user_id in", values, "compHfUserId");
            return (Criteria) this;
        }

        public Criteria andCompHfUserIdNotIn(List<String> values) {
            addCriterion("comp_hf_user_id not in", values, "compHfUserId");
            return (Criteria) this;
        }

        public Criteria andCompHfUserIdBetween(String value1, String value2) {
            addCriterion("comp_hf_user_id between", value1, value2, "compHfUserId");
            return (Criteria) this;
        }

        public Criteria andCompHfUserIdNotBetween(String value1, String value2) {
            addCriterion("comp_hf_user_id not between", value1, value2, "compHfUserId");
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

        public Criteria andPrincipalIsNull() {
            addCriterion("principal is null");
            return (Criteria) this;
        }

        public Criteria andPrincipalIsNotNull() {
            addCriterion("principal is not null");
            return (Criteria) this;
        }

        public Criteria andPrincipalEqualTo(Double value) {
            addCriterion("principal =", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalNotEqualTo(Double value) {
            addCriterion("principal <>", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalGreaterThan(Double value) {
            addCriterion("principal >", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalGreaterThanOrEqualTo(Double value) {
            addCriterion("principal >=", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalLessThan(Double value) {
            addCriterion("principal <", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalLessThanOrEqualTo(Double value) {
            addCriterion("principal <=", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalIn(List<Double> values) {
            addCriterion("principal in", values, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalNotIn(List<Double> values) {
            addCriterion("principal not in", values, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalBetween(Double value1, Double value2) {
            addCriterion("principal between", value1, value2, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalNotBetween(Double value1, Double value2) {
            addCriterion("principal not between", value1, value2, "principal");
            return (Criteria) this;
        }

        public Criteria andInterestIsNull() {
            addCriterion("interest is null");
            return (Criteria) this;
        }

        public Criteria andInterestIsNotNull() {
            addCriterion("interest is not null");
            return (Criteria) this;
        }

        public Criteria andInterestEqualTo(Double value) {
            addCriterion("interest =", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotEqualTo(Double value) {
            addCriterion("interest <>", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestGreaterThan(Double value) {
            addCriterion("interest >", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestGreaterThanOrEqualTo(Double value) {
            addCriterion("interest >=", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestLessThan(Double value) {
            addCriterion("interest <", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestLessThanOrEqualTo(Double value) {
            addCriterion("interest <=", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestIn(List<Double> values) {
            addCriterion("interest in", values, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotIn(List<Double> values) {
            addCriterion("interest not in", values, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestBetween(Double value1, Double value2) {
            addCriterion("interest between", value1, value2, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotBetween(Double value1, Double value2) {
            addCriterion("interest not between", value1, value2, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestDayIsNull() {
            addCriterion("interest_day is null");
            return (Criteria) this;
        }

        public Criteria andInterestDayIsNotNull() {
            addCriterion("interest_day is not null");
            return (Criteria) this;
        }

        public Criteria andInterestDayEqualTo(Integer value) {
            addCriterion("interest_day =", value, "interestDay");
            return (Criteria) this;
        }

        public Criteria andInterestDayNotEqualTo(Integer value) {
            addCriterion("interest_day <>", value, "interestDay");
            return (Criteria) this;
        }

        public Criteria andInterestDayGreaterThan(Integer value) {
            addCriterion("interest_day >", value, "interestDay");
            return (Criteria) this;
        }

        public Criteria andInterestDayGreaterThanOrEqualTo(Integer value) {
            addCriterion("interest_day >=", value, "interestDay");
            return (Criteria) this;
        }

        public Criteria andInterestDayLessThan(Integer value) {
            addCriterion("interest_day <", value, "interestDay");
            return (Criteria) this;
        }

        public Criteria andInterestDayLessThanOrEqualTo(Integer value) {
            addCriterion("interest_day <=", value, "interestDay");
            return (Criteria) this;
        }

        public Criteria andInterestDayIn(List<Integer> values) {
            addCriterion("interest_day in", values, "interestDay");
            return (Criteria) this;
        }

        public Criteria andInterestDayNotIn(List<Integer> values) {
            addCriterion("interest_day not in", values, "interestDay");
            return (Criteria) this;
        }

        public Criteria andInterestDayBetween(Integer value1, Integer value2) {
            addCriterion("interest_day between", value1, value2, "interestDay");
            return (Criteria) this;
        }

        public Criteria andInterestDayNotBetween(Integer value1, Integer value2) {
            addCriterion("interest_day not between", value1, value2, "interestDay");
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