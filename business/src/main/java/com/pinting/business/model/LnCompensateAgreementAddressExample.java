package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LnCompensateAgreementAddressExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LnCompensateAgreementAddressExample() {
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

        public Criteria andAgreementTypeIsNull() {
            addCriterion("agreement_type is null");
            return (Criteria) this;
        }

        public Criteria andAgreementTypeIsNotNull() {
            addCriterion("agreement_type is not null");
            return (Criteria) this;
        }

        public Criteria andAgreementTypeEqualTo(String value) {
            addCriterion("agreement_type =", value, "agreementType");
            return (Criteria) this;
        }

        public Criteria andAgreementTypeNotEqualTo(String value) {
            addCriterion("agreement_type <>", value, "agreementType");
            return (Criteria) this;
        }

        public Criteria andAgreementTypeGreaterThan(String value) {
            addCriterion("agreement_type >", value, "agreementType");
            return (Criteria) this;
        }

        public Criteria andAgreementTypeGreaterThanOrEqualTo(String value) {
            addCriterion("agreement_type >=", value, "agreementType");
            return (Criteria) this;
        }

        public Criteria andAgreementTypeLessThan(String value) {
            addCriterion("agreement_type <", value, "agreementType");
            return (Criteria) this;
        }

        public Criteria andAgreementTypeLessThanOrEqualTo(String value) {
            addCriterion("agreement_type <=", value, "agreementType");
            return (Criteria) this;
        }

        public Criteria andAgreementTypeLike(String value) {
            addCriterion("agreement_type like", value, "agreementType");
            return (Criteria) this;
        }

        public Criteria andAgreementTypeNotLike(String value) {
            addCriterion("agreement_type not like", value, "agreementType");
            return (Criteria) this;
        }

        public Criteria andAgreementTypeIn(List<String> values) {
            addCriterion("agreement_type in", values, "agreementType");
            return (Criteria) this;
        }

        public Criteria andAgreementTypeNotIn(List<String> values) {
            addCriterion("agreement_type not in", values, "agreementType");
            return (Criteria) this;
        }

        public Criteria andAgreementTypeBetween(String value1, String value2) {
            addCriterion("agreement_type between", value1, value2, "agreementType");
            return (Criteria) this;
        }

        public Criteria andAgreementTypeNotBetween(String value1, String value2) {
            addCriterion("agreement_type not between", value1, value2, "agreementType");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlIsNull() {
            addCriterion("download_url is null");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlIsNotNull() {
            addCriterion("download_url is not null");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlEqualTo(String value) {
            addCriterion("download_url =", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlNotEqualTo(String value) {
            addCriterion("download_url <>", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlGreaterThan(String value) {
            addCriterion("download_url >", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlGreaterThanOrEqualTo(String value) {
            addCriterion("download_url >=", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlLessThan(String value) {
            addCriterion("download_url <", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlLessThanOrEqualTo(String value) {
            addCriterion("download_url <=", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlLike(String value) {
            addCriterion("download_url like", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlNotLike(String value) {
            addCriterion("download_url not like", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlIn(List<String> values) {
            addCriterion("download_url in", values, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlNotIn(List<String> values) {
            addCriterion("download_url not in", values, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlBetween(String value1, String value2) {
            addCriterion("download_url between", value1, value2, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlNotBetween(String value1, String value2) {
            addCriterion("download_url not between", value1, value2, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadNumIsNull() {
            addCriterion("download_num is null");
            return (Criteria) this;
        }

        public Criteria andDownloadNumIsNotNull() {
            addCriterion("download_num is not null");
            return (Criteria) this;
        }

        public Criteria andDownloadNumEqualTo(Integer value) {
            addCriterion("download_num =", value, "downloadNum");
            return (Criteria) this;
        }

        public Criteria andDownloadNumNotEqualTo(Integer value) {
            addCriterion("download_num <>", value, "downloadNum");
            return (Criteria) this;
        }

        public Criteria andDownloadNumGreaterThan(Integer value) {
            addCriterion("download_num >", value, "downloadNum");
            return (Criteria) this;
        }

        public Criteria andDownloadNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("download_num >=", value, "downloadNum");
            return (Criteria) this;
        }

        public Criteria andDownloadNumLessThan(Integer value) {
            addCriterion("download_num <", value, "downloadNum");
            return (Criteria) this;
        }

        public Criteria andDownloadNumLessThanOrEqualTo(Integer value) {
            addCriterion("download_num <=", value, "downloadNum");
            return (Criteria) this;
        }

        public Criteria andDownloadNumIn(List<Integer> values) {
            addCriterion("download_num in", values, "downloadNum");
            return (Criteria) this;
        }

        public Criteria andDownloadNumNotIn(List<Integer> values) {
            addCriterion("download_num not in", values, "downloadNum");
            return (Criteria) this;
        }

        public Criteria andDownloadNumBetween(Integer value1, Integer value2) {
            addCriterion("download_num between", value1, value2, "downloadNum");
            return (Criteria) this;
        }

        public Criteria andDownloadNumNotBetween(Integer value1, Integer value2) {
            addCriterion("download_num not between", value1, value2, "downloadNum");
            return (Criteria) this;
        }

        public Criteria andIsOpenIsNull() {
            addCriterion("is_open is null");
            return (Criteria) this;
        }

        public Criteria andIsOpenIsNotNull() {
            addCriterion("is_open is not null");
            return (Criteria) this;
        }

        public Criteria andIsOpenEqualTo(String value) {
            addCriterion("is_open =", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenNotEqualTo(String value) {
            addCriterion("is_open <>", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenGreaterThan(String value) {
            addCriterion("is_open >", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenGreaterThanOrEqualTo(String value) {
            addCriterion("is_open >=", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenLessThan(String value) {
            addCriterion("is_open <", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenLessThanOrEqualTo(String value) {
            addCriterion("is_open <=", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenLike(String value) {
            addCriterion("is_open like", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenNotLike(String value) {
            addCriterion("is_open not like", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenIn(List<String> values) {
            addCriterion("is_open in", values, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenNotIn(List<String> values) {
            addCriterion("is_open not in", values, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenBetween(String value1, String value2) {
            addCriterion("is_open between", value1, value2, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenNotBetween(String value1, String value2) {
            addCriterion("is_open not between", value1, value2, "isOpen");
            return (Criteria) this;
        }

        public Criteria andInformStatusIsNull() {
            addCriterion("inform_status is null");
            return (Criteria) this;
        }

        public Criteria andInformStatusIsNotNull() {
            addCriterion("inform_status is not null");
            return (Criteria) this;
        }

        public Criteria andInformStatusEqualTo(String value) {
            addCriterion("inform_status =", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusNotEqualTo(String value) {
            addCriterion("inform_status <>", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusGreaterThan(String value) {
            addCriterion("inform_status >", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusGreaterThanOrEqualTo(String value) {
            addCriterion("inform_status >=", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusLessThan(String value) {
            addCriterion("inform_status <", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusLessThanOrEqualTo(String value) {
            addCriterion("inform_status <=", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusLike(String value) {
            addCriterion("inform_status like", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusNotLike(String value) {
            addCriterion("inform_status not like", value, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusIn(List<String> values) {
            addCriterion("inform_status in", values, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusNotIn(List<String> values) {
            addCriterion("inform_status not in", values, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusBetween(String value1, String value2) {
            addCriterion("inform_status between", value1, value2, "informStatus");
            return (Criteria) this;
        }

        public Criteria andInformStatusNotBetween(String value1, String value2) {
            addCriterion("inform_status not between", value1, value2, "informStatus");
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