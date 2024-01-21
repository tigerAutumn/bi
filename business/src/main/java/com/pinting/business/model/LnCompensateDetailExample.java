package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LnCompensateDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LnCompensateDetailExample() {
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

        public Criteria andCompensateIdIsNull() {
            addCriterion("compensate_id is null");
            return (Criteria) this;
        }

        public Criteria andCompensateIdIsNotNull() {
            addCriterion("compensate_id is not null");
            return (Criteria) this;
        }

        public Criteria andCompensateIdEqualTo(Integer value) {
            addCriterion("compensate_id =", value, "compensateId");
            return (Criteria) this;
        }

        public Criteria andCompensateIdNotEqualTo(Integer value) {
            addCriterion("compensate_id <>", value, "compensateId");
            return (Criteria) this;
        }

        public Criteria andCompensateIdGreaterThan(Integer value) {
            addCriterion("compensate_id >", value, "compensateId");
            return (Criteria) this;
        }

        public Criteria andCompensateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("compensate_id >=", value, "compensateId");
            return (Criteria) this;
        }

        public Criteria andCompensateIdLessThan(Integer value) {
            addCriterion("compensate_id <", value, "compensateId");
            return (Criteria) this;
        }

        public Criteria andCompensateIdLessThanOrEqualTo(Integer value) {
            addCriterion("compensate_id <=", value, "compensateId");
            return (Criteria) this;
        }

        public Criteria andCompensateIdIn(List<Integer> values) {
            addCriterion("compensate_id in", values, "compensateId");
            return (Criteria) this;
        }

        public Criteria andCompensateIdNotIn(List<Integer> values) {
            addCriterion("compensate_id not in", values, "compensateId");
            return (Criteria) this;
        }

        public Criteria andCompensateIdBetween(Integer value1, Integer value2) {
            addCriterion("compensate_id between", value1, value2, "compensateId");
            return (Criteria) this;
        }

        public Criteria andCompensateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("compensate_id not between", value1, value2, "compensateId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdIsNull() {
            addCriterion("partner_user_id is null");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdIsNotNull() {
            addCriterion("partner_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdEqualTo(String value) {
            addCriterion("partner_user_id =", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdNotEqualTo(String value) {
            addCriterion("partner_user_id <>", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdGreaterThan(String value) {
            addCriterion("partner_user_id >", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("partner_user_id >=", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdLessThan(String value) {
            addCriterion("partner_user_id <", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdLessThanOrEqualTo(String value) {
            addCriterion("partner_user_id <=", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdLike(String value) {
            addCriterion("partner_user_id like", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdNotLike(String value) {
            addCriterion("partner_user_id not like", value, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdIn(List<String> values) {
            addCriterion("partner_user_id in", values, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdNotIn(List<String> values) {
            addCriterion("partner_user_id not in", values, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdBetween(String value1, String value2) {
            addCriterion("partner_user_id between", value1, value2, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerUserIdNotBetween(String value1, String value2) {
            addCriterion("partner_user_id not between", value1, value2, "partnerUserId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdIsNull() {
            addCriterion("partner_loan_id is null");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdIsNotNull() {
            addCriterion("partner_loan_id is not null");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdEqualTo(String value) {
            addCriterion("partner_loan_id =", value, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdNotEqualTo(String value) {
            addCriterion("partner_loan_id <>", value, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdGreaterThan(String value) {
            addCriterion("partner_loan_id >", value, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdGreaterThanOrEqualTo(String value) {
            addCriterion("partner_loan_id >=", value, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdLessThan(String value) {
            addCriterion("partner_loan_id <", value, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdLessThanOrEqualTo(String value) {
            addCriterion("partner_loan_id <=", value, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdLike(String value) {
            addCriterion("partner_loan_id like", value, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdNotLike(String value) {
            addCriterion("partner_loan_id not like", value, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdIn(List<String> values) {
            addCriterion("partner_loan_id in", values, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdNotIn(List<String> values) {
            addCriterion("partner_loan_id not in", values, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdBetween(String value1, String value2) {
            addCriterion("partner_loan_id between", value1, value2, "partnerLoanId");
            return (Criteria) this;
        }

        public Criteria andPartnerLoanIdNotBetween(String value1, String value2) {
            addCriterion("partner_loan_id not between", value1, value2, "partnerLoanId");
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

        public Criteria andRepaySerialIsNull() {
            addCriterion("repay_serial is null");
            return (Criteria) this;
        }

        public Criteria andRepaySerialIsNotNull() {
            addCriterion("repay_serial is not null");
            return (Criteria) this;
        }

        public Criteria andRepaySerialEqualTo(Integer value) {
            addCriterion("repay_serial =", value, "repaySerial");
            return (Criteria) this;
        }

        public Criteria andRepaySerialNotEqualTo(Integer value) {
            addCriterion("repay_serial <>", value, "repaySerial");
            return (Criteria) this;
        }

        public Criteria andRepaySerialGreaterThan(Integer value) {
            addCriterion("repay_serial >", value, "repaySerial");
            return (Criteria) this;
        }

        public Criteria andRepaySerialGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_serial >=", value, "repaySerial");
            return (Criteria) this;
        }

        public Criteria andRepaySerialLessThan(Integer value) {
            addCriterion("repay_serial <", value, "repaySerial");
            return (Criteria) this;
        }

        public Criteria andRepaySerialLessThanOrEqualTo(Integer value) {
            addCriterion("repay_serial <=", value, "repaySerial");
            return (Criteria) this;
        }

        public Criteria andRepaySerialIn(List<Integer> values) {
            addCriterion("repay_serial in", values, "repaySerial");
            return (Criteria) this;
        }

        public Criteria andRepaySerialNotIn(List<Integer> values) {
            addCriterion("repay_serial not in", values, "repaySerial");
            return (Criteria) this;
        }

        public Criteria andRepaySerialBetween(Integer value1, Integer value2) {
            addCriterion("repay_serial between", value1, value2, "repaySerial");
            return (Criteria) this;
        }

        public Criteria andRepaySerialNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_serial not between", value1, value2, "repaySerial");
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

        public Criteria andTotalIsNull() {
            addCriterion("total is null");
            return (Criteria) this;
        }

        public Criteria andTotalIsNotNull() {
            addCriterion("total is not null");
            return (Criteria) this;
        }

        public Criteria andTotalEqualTo(Double value) {
            addCriterion("total =", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotEqualTo(Double value) {
            addCriterion("total <>", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThan(Double value) {
            addCriterion("total >", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThanOrEqualTo(Double value) {
            addCriterion("total >=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThan(Double value) {
            addCriterion("total <", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThanOrEqualTo(Double value) {
            addCriterion("total <=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalIn(List<Double> values) {
            addCriterion("total in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotIn(List<Double> values) {
            addCriterion("total not in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalBetween(Double value1, Double value2) {
            addCriterion("total between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotBetween(Double value1, Double value2) {
            addCriterion("total not between", value1, value2, "total");
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

        public Criteria andPrincipalOverdueIsNull() {
            addCriterion("principal_overdue is null");
            return (Criteria) this;
        }

        public Criteria andPrincipalOverdueIsNotNull() {
            addCriterion("principal_overdue is not null");
            return (Criteria) this;
        }

        public Criteria andPrincipalOverdueEqualTo(Double value) {
            addCriterion("principal_overdue =", value, "principalOverdue");
            return (Criteria) this;
        }

        public Criteria andPrincipalOverdueNotEqualTo(Double value) {
            addCriterion("principal_overdue <>", value, "principalOverdue");
            return (Criteria) this;
        }

        public Criteria andPrincipalOverdueGreaterThan(Double value) {
            addCriterion("principal_overdue >", value, "principalOverdue");
            return (Criteria) this;
        }

        public Criteria andPrincipalOverdueGreaterThanOrEqualTo(Double value) {
            addCriterion("principal_overdue >=", value, "principalOverdue");
            return (Criteria) this;
        }

        public Criteria andPrincipalOverdueLessThan(Double value) {
            addCriterion("principal_overdue <", value, "principalOverdue");
            return (Criteria) this;
        }

        public Criteria andPrincipalOverdueLessThanOrEqualTo(Double value) {
            addCriterion("principal_overdue <=", value, "principalOverdue");
            return (Criteria) this;
        }

        public Criteria andPrincipalOverdueIn(List<Double> values) {
            addCriterion("principal_overdue in", values, "principalOverdue");
            return (Criteria) this;
        }

        public Criteria andPrincipalOverdueNotIn(List<Double> values) {
            addCriterion("principal_overdue not in", values, "principalOverdue");
            return (Criteria) this;
        }

        public Criteria andPrincipalOverdueBetween(Double value1, Double value2) {
            addCriterion("principal_overdue between", value1, value2, "principalOverdue");
            return (Criteria) this;
        }

        public Criteria andPrincipalOverdueNotBetween(Double value1, Double value2) {
            addCriterion("principal_overdue not between", value1, value2, "principalOverdue");
            return (Criteria) this;
        }

        public Criteria andInterestOverdueIsNull() {
            addCriterion("interest_overdue is null");
            return (Criteria) this;
        }

        public Criteria andInterestOverdueIsNotNull() {
            addCriterion("interest_overdue is not null");
            return (Criteria) this;
        }

        public Criteria andInterestOverdueEqualTo(Double value) {
            addCriterion("interest_overdue =", value, "interestOverdue");
            return (Criteria) this;
        }

        public Criteria andInterestOverdueNotEqualTo(Double value) {
            addCriterion("interest_overdue <>", value, "interestOverdue");
            return (Criteria) this;
        }

        public Criteria andInterestOverdueGreaterThan(Double value) {
            addCriterion("interest_overdue >", value, "interestOverdue");
            return (Criteria) this;
        }

        public Criteria andInterestOverdueGreaterThanOrEqualTo(Double value) {
            addCriterion("interest_overdue >=", value, "interestOverdue");
            return (Criteria) this;
        }

        public Criteria andInterestOverdueLessThan(Double value) {
            addCriterion("interest_overdue <", value, "interestOverdue");
            return (Criteria) this;
        }

        public Criteria andInterestOverdueLessThanOrEqualTo(Double value) {
            addCriterion("interest_overdue <=", value, "interestOverdue");
            return (Criteria) this;
        }

        public Criteria andInterestOverdueIn(List<Double> values) {
            addCriterion("interest_overdue in", values, "interestOverdue");
            return (Criteria) this;
        }

        public Criteria andInterestOverdueNotIn(List<Double> values) {
            addCriterion("interest_overdue not in", values, "interestOverdue");
            return (Criteria) this;
        }

        public Criteria andInterestOverdueBetween(Double value1, Double value2) {
            addCriterion("interest_overdue between", value1, value2, "interestOverdue");
            return (Criteria) this;
        }

        public Criteria andInterestOverdueNotBetween(Double value1, Double value2) {
            addCriterion("interest_overdue not between", value1, value2, "interestOverdue");
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

        public Criteria andAgreementGenerateStatusIsNull() {
            addCriterion("agreement_generate_status is null");
            return (Criteria) this;
        }

        public Criteria andAgreementGenerateStatusIsNotNull() {
            addCriterion("agreement_generate_status is not null");
            return (Criteria) this;
        }

        public Criteria andAgreementGenerateStatusEqualTo(String value) {
            addCriterion("agreement_generate_status =", value, "agreement_generate_status");
            return (Criteria) this;
        }

        public Criteria andAgreementGenerateStatusNotEqualTo(String value) {
            addCriterion("agreement_generate_status <>", value, "agreement_generate_status");
            return (Criteria) this;
        }

        public Criteria andAgreementGenerateStatusGreaterThan(String value) {
            addCriterion("agreement_generate_status >", value, "agreement_generate_status");
            return (Criteria) this;
        }

        public Criteria andAgreementGenerateStatusGreaterThanOrEqualTo(String value) {
            addCriterion("agreement_generate_status >=", value, "agreement_generate_status");
            return (Criteria) this;
        }

        public Criteria andAgreementGenerateStatusLessThan(String value) {
            addCriterion("agreement_generate_status <", value, "agreement_generate_status");
            return (Criteria) this;
        }

        public Criteria andAgreementGenerateStatusLessThanOrEqualTo(String value) {
            addCriterion("agreement_generate_status <=", value, "agreement_generate_status");
            return (Criteria) this;
        }

        public Criteria andAgreementGenerateStatusLike(String value) {
            addCriterion("agreement_generate_status like", value, "agreement_generate_status");
            return (Criteria) this;
        }

        public Criteria andAgreementGenerateStatusNotLike(String value) {
            addCriterion("agreement_generate_status not like", value, "agreement_generate_status");
            return (Criteria) this;
        }

        public Criteria andAgreementGenerateStatusIn(List<String> values) {
            addCriterion("agreement_generate_status in", values, "agreement_generate_status");
            return (Criteria) this;
        }

        public Criteria andAgreementGenerateStatusNotIn(List<String> values) {
            addCriterion("agreement_generate_status not in", values, "agreement_generate_status");
            return (Criteria) this;
        }

        public Criteria andAgreementGenerateStatusBetween(String value1, String value2) {
            addCriterion("agreement_generate_status between", value1, value2, "agreement_generate_status");
            return (Criteria) this;
        }

        public Criteria andAgreementGenerateStatusNotBetween(String value1, String value2) {
            addCriterion("agreement_generate_status not between", value1, value2, "agreement_generate_status");
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