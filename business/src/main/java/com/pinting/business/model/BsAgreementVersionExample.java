package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsAgreementVersionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsAgreementVersionExample() {
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

        public Criteria andAgreementNameIsNull() {
            addCriterion("agreement_name is null");
            return (Criteria) this;
        }

        public Criteria andAgreementNameIsNotNull() {
            addCriterion("agreement_name is not null");
            return (Criteria) this;
        }

        public Criteria andAgreementNameEqualTo(String value) {
            addCriterion("agreement_name =", value, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameNotEqualTo(String value) {
            addCriterion("agreement_name <>", value, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameGreaterThan(String value) {
            addCriterion("agreement_name >", value, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameGreaterThanOrEqualTo(String value) {
            addCriterion("agreement_name >=", value, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameLessThan(String value) {
            addCriterion("agreement_name <", value, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameLessThanOrEqualTo(String value) {
            addCriterion("agreement_name <=", value, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameLike(String value) {
            addCriterion("agreement_name like", value, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameNotLike(String value) {
            addCriterion("agreement_name not like", value, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameIn(List<String> values) {
            addCriterion("agreement_name in", values, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameNotIn(List<String> values) {
            addCriterion("agreement_name not in", values, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameBetween(String value1, String value2) {
            addCriterion("agreement_name between", value1, value2, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameNotBetween(String value1, String value2) {
            addCriterion("agreement_name not between", value1, value2, "agreementName");
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

        public Criteria andAgreementVersionIsNull() {
            addCriterion("agreement_version is null");
            return (Criteria) this;
        }

        public Criteria andAgreementVersionIsNotNull() {
            addCriterion("agreement_version is not null");
            return (Criteria) this;
        }

        public Criteria andAgreementVersionEqualTo(String value) {
            addCriterion("agreement_version =", value, "agreementVersion");
            return (Criteria) this;
        }

        public Criteria andAgreementVersionNotEqualTo(String value) {
            addCriterion("agreement_version <>", value, "agreementVersion");
            return (Criteria) this;
        }

        public Criteria andAgreementVersionGreaterThan(String value) {
            addCriterion("agreement_version >", value, "agreementVersion");
            return (Criteria) this;
        }

        public Criteria andAgreementVersionGreaterThanOrEqualTo(String value) {
            addCriterion("agreement_version >=", value, "agreementVersion");
            return (Criteria) this;
        }

        public Criteria andAgreementVersionLessThan(String value) {
            addCriterion("agreement_version <", value, "agreementVersion");
            return (Criteria) this;
        }

        public Criteria andAgreementVersionLessThanOrEqualTo(String value) {
            addCriterion("agreement_version <=", value, "agreementVersion");
            return (Criteria) this;
        }

        public Criteria andAgreementVersionLike(String value) {
            addCriterion("agreement_version like", value, "agreementVersion");
            return (Criteria) this;
        }

        public Criteria andAgreementVersionNotLike(String value) {
            addCriterion("agreement_version not like", value, "agreementVersion");
            return (Criteria) this;
        }

        public Criteria andAgreementVersionIn(List<String> values) {
            addCriterion("agreement_version in", values, "agreementVersion");
            return (Criteria) this;
        }

        public Criteria andAgreementVersionNotIn(List<String> values) {
            addCriterion("agreement_version not in", values, "agreementVersion");
            return (Criteria) this;
        }

        public Criteria andAgreementVersionBetween(String value1, String value2) {
            addCriterion("agreement_version between", value1, value2, "agreementVersion");
            return (Criteria) this;
        }

        public Criteria andAgreementVersionNotBetween(String value1, String value2) {
            addCriterion("agreement_version not between", value1, value2, "agreementVersion");
            return (Criteria) this;
        }

        public Criteria andAgreementUrlIsNull() {
            addCriterion("agreement_url is null");
            return (Criteria) this;
        }

        public Criteria andAgreementUrlIsNotNull() {
            addCriterion("agreement_url is not null");
            return (Criteria) this;
        }

        public Criteria andAgreementUrlEqualTo(String value) {
            addCriterion("agreement_url =", value, "agreementUrl");
            return (Criteria) this;
        }

        public Criteria andAgreementUrlNotEqualTo(String value) {
            addCriterion("agreement_url <>", value, "agreementUrl");
            return (Criteria) this;
        }

        public Criteria andAgreementUrlGreaterThan(String value) {
            addCriterion("agreement_url >", value, "agreementUrl");
            return (Criteria) this;
        }

        public Criteria andAgreementUrlGreaterThanOrEqualTo(String value) {
            addCriterion("agreement_url >=", value, "agreementUrl");
            return (Criteria) this;
        }

        public Criteria andAgreementUrlLessThan(String value) {
            addCriterion("agreement_url <", value, "agreementUrl");
            return (Criteria) this;
        }

        public Criteria andAgreementUrlLessThanOrEqualTo(String value) {
            addCriterion("agreement_url <=", value, "agreementUrl");
            return (Criteria) this;
        }

        public Criteria andAgreementUrlLike(String value) {
            addCriterion("agreement_url like", value, "agreementUrl");
            return (Criteria) this;
        }

        public Criteria andAgreementUrlNotLike(String value) {
            addCriterion("agreement_url not like", value, "agreementUrl");
            return (Criteria) this;
        }

        public Criteria andAgreementUrlIn(List<String> values) {
            addCriterion("agreement_url in", values, "agreementUrl");
            return (Criteria) this;
        }

        public Criteria andAgreementUrlNotIn(List<String> values) {
            addCriterion("agreement_url not in", values, "agreementUrl");
            return (Criteria) this;
        }

        public Criteria andAgreementUrlBetween(String value1, String value2) {
            addCriterion("agreement_url between", value1, value2, "agreementUrl");
            return (Criteria) this;
        }

        public Criteria andAgreementUrlNotBetween(String value1, String value2) {
            addCriterion("agreement_url not between", value1, value2, "agreementUrl");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveStartTimeIsNull() {
            addCriterion("agreement_effective_start_time is null");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveStartTimeIsNotNull() {
            addCriterion("agreement_effective_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveStartTimeEqualTo(Date value) {
            addCriterion("agreement_effective_start_time =", value, "agreementEffectiveStartTime");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveStartTimeNotEqualTo(Date value) {
            addCriterion("agreement_effective_start_time <>", value, "agreementEffectiveStartTime");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveStartTimeGreaterThan(Date value) {
            addCriterion("agreement_effective_start_time >", value, "agreementEffectiveStartTime");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("agreement_effective_start_time >=", value, "agreementEffectiveStartTime");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveStartTimeLessThan(Date value) {
            addCriterion("agreement_effective_start_time <", value, "agreementEffectiveStartTime");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("agreement_effective_start_time <=", value, "agreementEffectiveStartTime");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveStartTimeIn(List<Date> values) {
            addCriterion("agreement_effective_start_time in", values, "agreementEffectiveStartTime");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveStartTimeNotIn(List<Date> values) {
            addCriterion("agreement_effective_start_time not in", values, "agreementEffectiveStartTime");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveStartTimeBetween(Date value1, Date value2) {
            addCriterion("agreement_effective_start_time between", value1, value2, "agreementEffectiveStartTime");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("agreement_effective_start_time not between", value1, value2, "agreementEffectiveStartTime");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveEndTimeIsNull() {
            addCriterion("agreement_effective_end_time is null");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveEndTimeIsNotNull() {
            addCriterion("agreement_effective_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveEndTimeEqualTo(Date value) {
            addCriterion("agreement_effective_end_time =", value, "agreementEffectiveEndTime");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveEndTimeNotEqualTo(Date value) {
            addCriterion("agreement_effective_end_time <>", value, "agreementEffectiveEndTime");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveEndTimeGreaterThan(Date value) {
            addCriterion("agreement_effective_end_time >", value, "agreementEffectiveEndTime");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("agreement_effective_end_time >=", value, "agreementEffectiveEndTime");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveEndTimeLessThan(Date value) {
            addCriterion("agreement_effective_end_time <", value, "agreementEffectiveEndTime");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("agreement_effective_end_time <=", value, "agreementEffectiveEndTime");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveEndTimeIn(List<Date> values) {
            addCriterion("agreement_effective_end_time in", values, "agreementEffectiveEndTime");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveEndTimeNotIn(List<Date> values) {
            addCriterion("agreement_effective_end_time not in", values, "agreementEffectiveEndTime");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveEndTimeBetween(Date value1, Date value2) {
            addCriterion("agreement_effective_end_time between", value1, value2, "agreementEffectiveEndTime");
            return (Criteria) this;
        }

        public Criteria andAgreementEffectiveEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("agreement_effective_end_time not between", value1, value2, "agreementEffectiveEndTime");
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