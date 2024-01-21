package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LnAccountFillDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LnAccountFillDetailExample() {
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

        public Criteria andFillTypeIsNull() {
            addCriterion("fill_type is null");
            return (Criteria) this;
        }

        public Criteria andFillTypeIsNotNull() {
            addCriterion("fill_type is not null");
            return (Criteria) this;
        }

        public Criteria andFillTypeEqualTo(String value) {
            addCriterion("fill_type =", value, "fillType");
            return (Criteria) this;
        }

        public Criteria andFillTypeNotEqualTo(String value) {
            addCriterion("fill_type <>", value, "fillType");
            return (Criteria) this;
        }

        public Criteria andFillTypeGreaterThan(String value) {
            addCriterion("fill_type >", value, "fillType");
            return (Criteria) this;
        }

        public Criteria andFillTypeGreaterThanOrEqualTo(String value) {
            addCriterion("fill_type >=", value, "fillType");
            return (Criteria) this;
        }

        public Criteria andFillTypeLessThan(String value) {
            addCriterion("fill_type <", value, "fillType");
            return (Criteria) this;
        }

        public Criteria andFillTypeLessThanOrEqualTo(String value) {
            addCriterion("fill_type <=", value, "fillType");
            return (Criteria) this;
        }

        public Criteria andFillTypeLike(String value) {
            addCriterion("fill_type like", value, "fillType");
            return (Criteria) this;
        }

        public Criteria andFillTypeNotLike(String value) {
            addCriterion("fill_type not like", value, "fillType");
            return (Criteria) this;
        }

        public Criteria andFillTypeIn(List<String> values) {
            addCriterion("fill_type in", values, "fillType");
            return (Criteria) this;
        }

        public Criteria andFillTypeNotIn(List<String> values) {
            addCriterion("fill_type not in", values, "fillType");
            return (Criteria) this;
        }

        public Criteria andFillTypeBetween(String value1, String value2) {
            addCriterion("fill_type between", value1, value2, "fillType");
            return (Criteria) this;
        }

        public Criteria andFillTypeNotBetween(String value1, String value2) {
            addCriterion("fill_type not between", value1, value2, "fillType");
            return (Criteria) this;
        }

        public Criteria andFillDateIsNull() {
            addCriterion("fill_date is null");
            return (Criteria) this;
        }

        public Criteria andFillDateIsNotNull() {
            addCriterion("fill_date is not null");
            return (Criteria) this;
        }

        public Criteria andFillDateEqualTo(Date value) {
            addCriterion("fill_date =", value, "fillDate");
            return (Criteria) this;
        }

        public Criteria andFillDateNotEqualTo(Date value) {
            addCriterion("fill_date <>", value, "fillDate");
            return (Criteria) this;
        }

        public Criteria andFillDateGreaterThan(Date value) {
            addCriterion("fill_date >", value, "fillDate");
            return (Criteria) this;
        }

        public Criteria andFillDateGreaterThanOrEqualTo(Date value) {
            addCriterion("fill_date >=", value, "fillDate");
            return (Criteria) this;
        }

        public Criteria andFillDateLessThan(Date value) {
            addCriterion("fill_date <", value, "fillDate");
            return (Criteria) this;
        }

        public Criteria andFillDateLessThanOrEqualTo(Date value) {
            addCriterion("fill_date <=", value, "fillDate");
            return (Criteria) this;
        }

        public Criteria andFillDateIn(List<Date> values) {
            addCriterion("fill_date in", values, "fillDate");
            return (Criteria) this;
        }

        public Criteria andFillDateNotIn(List<Date> values) {
            addCriterion("fill_date not in", values, "fillDate");
            return (Criteria) this;
        }

        public Criteria andFillDateBetween(Date value1, Date value2) {
            addCriterion("fill_date between", value1, value2, "fillDate");
            return (Criteria) this;
        }

        public Criteria andFillDateNotBetween(Date value1, Date value2) {
            addCriterion("fill_date not between", value1, value2, "fillDate");
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

        public Criteria andOutAccountIdIsNull() {
            addCriterion("out_account_id is null");
            return (Criteria) this;
        }

        public Criteria andOutAccountIdIsNotNull() {
            addCriterion("out_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andOutAccountIdEqualTo(Integer value) {
            addCriterion("out_account_id =", value, "outAccountId");
            return (Criteria) this;
        }

        public Criteria andOutAccountIdNotEqualTo(Integer value) {
            addCriterion("out_account_id <>", value, "outAccountId");
            return (Criteria) this;
        }

        public Criteria andOutAccountIdGreaterThan(Integer value) {
            addCriterion("out_account_id >", value, "outAccountId");
            return (Criteria) this;
        }

        public Criteria andOutAccountIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_account_id >=", value, "outAccountId");
            return (Criteria) this;
        }

        public Criteria andOutAccountIdLessThan(Integer value) {
            addCriterion("out_account_id <", value, "outAccountId");
            return (Criteria) this;
        }

        public Criteria andOutAccountIdLessThanOrEqualTo(Integer value) {
            addCriterion("out_account_id <=", value, "outAccountId");
            return (Criteria) this;
        }

        public Criteria andOutAccountIdIn(List<Integer> values) {
            addCriterion("out_account_id in", values, "outAccountId");
            return (Criteria) this;
        }

        public Criteria andOutAccountIdNotIn(List<Integer> values) {
            addCriterion("out_account_id not in", values, "outAccountId");
            return (Criteria) this;
        }

        public Criteria andOutAccountIdBetween(Integer value1, Integer value2) {
            addCriterion("out_account_id between", value1, value2, "outAccountId");
            return (Criteria) this;
        }

        public Criteria andOutAccountIdNotBetween(Integer value1, Integer value2) {
            addCriterion("out_account_id not between", value1, value2, "outAccountId");
            return (Criteria) this;
        }

        public Criteria andInAccountIdIsNull() {
            addCriterion("in_account_id is null");
            return (Criteria) this;
        }

        public Criteria andInAccountIdIsNotNull() {
            addCriterion("in_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andInAccountIdEqualTo(Integer value) {
            addCriterion("in_account_id =", value, "inAccountId");
            return (Criteria) this;
        }

        public Criteria andInAccountIdNotEqualTo(Integer value) {
            addCriterion("in_account_id <>", value, "inAccountId");
            return (Criteria) this;
        }

        public Criteria andInAccountIdGreaterThan(Integer value) {
            addCriterion("in_account_id >", value, "inAccountId");
            return (Criteria) this;
        }

        public Criteria andInAccountIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("in_account_id >=", value, "inAccountId");
            return (Criteria) this;
        }

        public Criteria andInAccountIdLessThan(Integer value) {
            addCriterion("in_account_id <", value, "inAccountId");
            return (Criteria) this;
        }

        public Criteria andInAccountIdLessThanOrEqualTo(Integer value) {
            addCriterion("in_account_id <=", value, "inAccountId");
            return (Criteria) this;
        }

        public Criteria andInAccountIdIn(List<Integer> values) {
            addCriterion("in_account_id in", values, "inAccountId");
            return (Criteria) this;
        }

        public Criteria andInAccountIdNotIn(List<Integer> values) {
            addCriterion("in_account_id not in", values, "inAccountId");
            return (Criteria) this;
        }

        public Criteria andInAccountIdBetween(Integer value1, Integer value2) {
            addCriterion("in_account_id between", value1, value2, "inAccountId");
            return (Criteria) this;
        }

        public Criteria andInAccountIdNotBetween(Integer value1, Integer value2) {
            addCriterion("in_account_id not between", value1, value2, "inAccountId");
            return (Criteria) this;
        }

        public Criteria andRelativeNoIsNull() {
            addCriterion("relative_no is null");
            return (Criteria) this;
        }

        public Criteria andRelativeNoIsNotNull() {
            addCriterion("relative_no is not null");
            return (Criteria) this;
        }

        public Criteria andRelativeNoEqualTo(String value) {
            addCriterion("relative_no =", value, "relativeNo");
            return (Criteria) this;
        }

        public Criteria andRelativeNoNotEqualTo(String value) {
            addCriterion("relative_no <>", value, "relativeNo");
            return (Criteria) this;
        }

        public Criteria andRelativeNoGreaterThan(String value) {
            addCriterion("relative_no >", value, "relativeNo");
            return (Criteria) this;
        }

        public Criteria andRelativeNoGreaterThanOrEqualTo(String value) {
            addCriterion("relative_no >=", value, "relativeNo");
            return (Criteria) this;
        }

        public Criteria andRelativeNoLessThan(String value) {
            addCriterion("relative_no <", value, "relativeNo");
            return (Criteria) this;
        }

        public Criteria andRelativeNoLessThanOrEqualTo(String value) {
            addCriterion("relative_no <=", value, "relativeNo");
            return (Criteria) this;
        }

        public Criteria andRelativeNoLike(String value) {
            addCriterion("relative_no like", value, "relativeNo");
            return (Criteria) this;
        }

        public Criteria andRelativeNoNotLike(String value) {
            addCriterion("relative_no not like", value, "relativeNo");
            return (Criteria) this;
        }

        public Criteria andRelativeNoIn(List<String> values) {
            addCriterion("relative_no in", values, "relativeNo");
            return (Criteria) this;
        }

        public Criteria andRelativeNoNotIn(List<String> values) {
            addCriterion("relative_no not in", values, "relativeNo");
            return (Criteria) this;
        }

        public Criteria andRelativeNoBetween(String value1, String value2) {
            addCriterion("relative_no between", value1, value2, "relativeNo");
            return (Criteria) this;
        }

        public Criteria andRelativeNoNotBetween(String value1, String value2) {
            addCriterion("relative_no not between", value1, value2, "relativeNo");
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