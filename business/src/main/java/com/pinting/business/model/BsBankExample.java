package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsBankExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsBankExample() {
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andUnionBankIdIsNull() {
            addCriterion("union_bank_id is null");
            return (Criteria) this;
        }

        public Criteria andUnionBankIdIsNotNull() {
            addCriterion("union_bank_id is not null");
            return (Criteria) this;
        }

        public Criteria andUnionBankIdEqualTo(String value) {
            addCriterion("union_bank_id =", value, "unionBankId");
            return (Criteria) this;
        }

        public Criteria andUnionBankIdNotEqualTo(String value) {
            addCriterion("union_bank_id <>", value, "unionBankId");
            return (Criteria) this;
        }

        public Criteria andUnionBankIdGreaterThan(String value) {
            addCriterion("union_bank_id >", value, "unionBankId");
            return (Criteria) this;
        }

        public Criteria andUnionBankIdGreaterThanOrEqualTo(String value) {
            addCriterion("union_bank_id >=", value, "unionBankId");
            return (Criteria) this;
        }

        public Criteria andUnionBankIdLessThan(String value) {
            addCriterion("union_bank_id <", value, "unionBankId");
            return (Criteria) this;
        }

        public Criteria andUnionBankIdLessThanOrEqualTo(String value) {
            addCriterion("union_bank_id <=", value, "unionBankId");
            return (Criteria) this;
        }

        public Criteria andUnionBankIdLike(String value) {
            addCriterion("union_bank_id like", value, "unionBankId");
            return (Criteria) this;
        }

        public Criteria andUnionBankIdNotLike(String value) {
            addCriterion("union_bank_id not like", value, "unionBankId");
            return (Criteria) this;
        }

        public Criteria andUnionBankIdIn(List<String> values) {
            addCriterion("union_bank_id in", values, "unionBankId");
            return (Criteria) this;
        }

        public Criteria andUnionBankIdNotIn(List<String> values) {
            addCriterion("union_bank_id not in", values, "unionBankId");
            return (Criteria) this;
        }

        public Criteria andUnionBankIdBetween(String value1, String value2) {
            addCriterion("union_bank_id between", value1, value2, "unionBankId");
            return (Criteria) this;
        }

        public Criteria andUnionBankIdNotBetween(String value1, String value2) {
            addCriterion("union_bank_id not between", value1, value2, "unionBankId");
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

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andNoteIsNull() {
            addCriterion("note is null");
            return (Criteria) this;
        }

        public Criteria andNoteIsNotNull() {
            addCriterion("note is not null");
            return (Criteria) this;
        }

        public Criteria andNoteEqualTo(String value) {
            addCriterion("note =", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotEqualTo(String value) {
            addCriterion("note <>", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThan(String value) {
            addCriterion("note >", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThanOrEqualTo(String value) {
            addCriterion("note >=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThan(String value) {
            addCriterion("note <", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThanOrEqualTo(String value) {
            addCriterion("note <=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLike(String value) {
            addCriterion("note like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotLike(String value) {
            addCriterion("note not like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteIn(List<String> values) {
            addCriterion("note in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotIn(List<String> values) {
            addCriterion("note not in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteBetween(String value1, String value2) {
            addCriterion("note between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotBetween(String value1, String value2) {
            addCriterion("note not between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andSmallLogoIsNull() {
            addCriterion("small_logo is null");
            return (Criteria) this;
        }

        public Criteria andSmallLogoIsNotNull() {
            addCriterion("small_logo is not null");
            return (Criteria) this;
        }

        public Criteria andSmallLogoEqualTo(String value) {
            addCriterion("small_logo =", value, "smallLogo");
            return (Criteria) this;
        }

        public Criteria andSmallLogoNotEqualTo(String value) {
            addCriterion("small_logo <>", value, "smallLogo");
            return (Criteria) this;
        }

        public Criteria andSmallLogoGreaterThan(String value) {
            addCriterion("small_logo >", value, "smallLogo");
            return (Criteria) this;
        }

        public Criteria andSmallLogoGreaterThanOrEqualTo(String value) {
            addCriterion("small_logo >=", value, "smallLogo");
            return (Criteria) this;
        }

        public Criteria andSmallLogoLessThan(String value) {
            addCriterion("small_logo <", value, "smallLogo");
            return (Criteria) this;
        }

        public Criteria andSmallLogoLessThanOrEqualTo(String value) {
            addCriterion("small_logo <=", value, "smallLogo");
            return (Criteria) this;
        }

        public Criteria andSmallLogoLike(String value) {
            addCriterion("small_logo like", value, "smallLogo");
            return (Criteria) this;
        }

        public Criteria andSmallLogoNotLike(String value) {
            addCriterion("small_logo not like", value, "smallLogo");
            return (Criteria) this;
        }

        public Criteria andSmallLogoIn(List<String> values) {
            addCriterion("small_logo in", values, "smallLogo");
            return (Criteria) this;
        }

        public Criteria andSmallLogoNotIn(List<String> values) {
            addCriterion("small_logo not in", values, "smallLogo");
            return (Criteria) this;
        }

        public Criteria andSmallLogoBetween(String value1, String value2) {
            addCriterion("small_logo between", value1, value2, "smallLogo");
            return (Criteria) this;
        }

        public Criteria andSmallLogoNotBetween(String value1, String value2) {
            addCriterion("small_logo not between", value1, value2, "smallLogo");
            return (Criteria) this;
        }

        public Criteria andLargeLogoIsNull() {
            addCriterion("large_logo is null");
            return (Criteria) this;
        }

        public Criteria andLargeLogoIsNotNull() {
            addCriterion("large_logo is not null");
            return (Criteria) this;
        }

        public Criteria andLargeLogoEqualTo(String value) {
            addCriterion("large_logo =", value, "largeLogo");
            return (Criteria) this;
        }

        public Criteria andLargeLogoNotEqualTo(String value) {
            addCriterion("large_logo <>", value, "largeLogo");
            return (Criteria) this;
        }

        public Criteria andLargeLogoGreaterThan(String value) {
            addCriterion("large_logo >", value, "largeLogo");
            return (Criteria) this;
        }

        public Criteria andLargeLogoGreaterThanOrEqualTo(String value) {
            addCriterion("large_logo >=", value, "largeLogo");
            return (Criteria) this;
        }

        public Criteria andLargeLogoLessThan(String value) {
            addCriterion("large_logo <", value, "largeLogo");
            return (Criteria) this;
        }

        public Criteria andLargeLogoLessThanOrEqualTo(String value) {
            addCriterion("large_logo <=", value, "largeLogo");
            return (Criteria) this;
        }

        public Criteria andLargeLogoLike(String value) {
            addCriterion("large_logo like", value, "largeLogo");
            return (Criteria) this;
        }

        public Criteria andLargeLogoNotLike(String value) {
            addCriterion("large_logo not like", value, "largeLogo");
            return (Criteria) this;
        }

        public Criteria andLargeLogoIn(List<String> values) {
            addCriterion("large_logo in", values, "largeLogo");
            return (Criteria) this;
        }

        public Criteria andLargeLogoNotIn(List<String> values) {
            addCriterion("large_logo not in", values, "largeLogo");
            return (Criteria) this;
        }

        public Criteria andLargeLogoBetween(String value1, String value2) {
            addCriterion("large_logo between", value1, value2, "largeLogo");
            return (Criteria) this;
        }

        public Criteria andLargeLogoNotBetween(String value1, String value2) {
            addCriterion("large_logo not between", value1, value2, "largeLogo");
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