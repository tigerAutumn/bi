package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BsBonusGrantPlanExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsBonusGrantPlanExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andBeRecommendUserIdIsNull() {
            addCriterion("be_recommend_user_id is null");
            return (Criteria) this;
        }

        public Criteria andBeRecommendUserIdIsNotNull() {
            addCriterion("be_recommend_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andBeRecommendUserIdEqualTo(Integer value) {
            addCriterion("be_recommend_user_id =", value, "beRecommendUserId");
            return (Criteria) this;
        }

        public Criteria andBeRecommendUserIdNotEqualTo(Integer value) {
            addCriterion("be_recommend_user_id <>", value, "beRecommendUserId");
            return (Criteria) this;
        }

        public Criteria andBeRecommendUserIdGreaterThan(Integer value) {
            addCriterion("be_recommend_user_id >", value, "beRecommendUserId");
            return (Criteria) this;
        }

        public Criteria andBeRecommendUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("be_recommend_user_id >=", value, "beRecommendUserId");
            return (Criteria) this;
        }

        public Criteria andBeRecommendUserIdLessThan(Integer value) {
            addCriterion("be_recommend_user_id <", value, "beRecommendUserId");
            return (Criteria) this;
        }

        public Criteria andBeRecommendUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("be_recommend_user_id <=", value, "beRecommendUserId");
            return (Criteria) this;
        }

        public Criteria andBeRecommendUserIdIn(List<Integer> values) {
            addCriterion("be_recommend_user_id in", values, "beRecommendUserId");
            return (Criteria) this;
        }

        public Criteria andBeRecommendUserIdNotIn(List<Integer> values) {
            addCriterion("be_recommend_user_id not in", values, "beRecommendUserId");
            return (Criteria) this;
        }

        public Criteria andBeRecommendUserIdBetween(Integer value1, Integer value2) {
            addCriterion("be_recommend_user_id between", value1, value2, "beRecommendUserId");
            return (Criteria) this;
        }

        public Criteria andBeRecommendUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("be_recommend_user_id not between", value1, value2, "beRecommendUserId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdIsNull() {
            addCriterion("sub_account_id is null");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdIsNotNull() {
            addCriterion("sub_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdEqualTo(Integer value) {
            addCriterion("sub_account_id =", value, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdNotEqualTo(Integer value) {
            addCriterion("sub_account_id <>", value, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdGreaterThan(Integer value) {
            addCriterion("sub_account_id >", value, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("sub_account_id >=", value, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdLessThan(Integer value) {
            addCriterion("sub_account_id <", value, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdLessThanOrEqualTo(Integer value) {
            addCriterion("sub_account_id <=", value, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdIn(List<Integer> values) {
            addCriterion("sub_account_id in", values, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdNotIn(List<Integer> values) {
            addCriterion("sub_account_id not in", values, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdBetween(Integer value1, Integer value2) {
            addCriterion("sub_account_id between", value1, value2, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSubAccountIdNotBetween(Integer value1, Integer value2) {
            addCriterion("sub_account_id not between", value1, value2, "subAccountId");
            return (Criteria) this;
        }

        public Criteria andSerialNoIsNull() {
            addCriterion("serial_no is null");
            return (Criteria) this;
        }

        public Criteria andSerialNoIsNotNull() {
            addCriterion("serial_no is not null");
            return (Criteria) this;
        }

        public Criteria andSerialNoEqualTo(Integer value) {
            addCriterion("serial_no =", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotEqualTo(Integer value) {
            addCriterion("serial_no <>", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThan(Integer value) {
            addCriterion("serial_no >", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("serial_no >=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThan(Integer value) {
            addCriterion("serial_no <", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThanOrEqualTo(Integer value) {
            addCriterion("serial_no <=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoIn(List<Integer> values) {
            addCriterion("serial_no in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotIn(List<Integer> values) {
            addCriterion("serial_no not in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoBetween(Integer value1, Integer value2) {
            addCriterion("serial_no between", value1, value2, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotBetween(Integer value1, Integer value2) {
            addCriterion("serial_no not between", value1, value2, "serialNo");
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

        public Criteria andGrantTypeIsNull() {
            addCriterion("grant_type is null");
            return (Criteria) this;
        }

        public Criteria andGrantTypeIsNotNull() {
            addCriterion("grant_type is not null");
            return (Criteria) this;
        }

        public Criteria andGrantTypeEqualTo(String value) {
            addCriterion("grant_type =", value, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeNotEqualTo(String value) {
            addCriterion("grant_type <>", value, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeGreaterThan(String value) {
            addCriterion("grant_type >", value, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeGreaterThanOrEqualTo(String value) {
            addCriterion("grant_type >=", value, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeLessThan(String value) {
            addCriterion("grant_type <", value, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeLessThanOrEqualTo(String value) {
            addCriterion("grant_type <=", value, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeLike(String value) {
            addCriterion("grant_type like", value, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeNotLike(String value) {
            addCriterion("grant_type not like", value, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeIn(List<String> values) {
            addCriterion("grant_type in", values, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeNotIn(List<String> values) {
            addCriterion("grant_type not in", values, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeBetween(String value1, String value2) {
            addCriterion("grant_type between", value1, value2, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeNotBetween(String value1, String value2) {
            addCriterion("grant_type not between", value1, value2, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantDateIsNull() {
            addCriterion("grant_date is null");
            return (Criteria) this;
        }

        public Criteria andGrantDateIsNotNull() {
            addCriterion("grant_date is not null");
            return (Criteria) this;
        }

        public Criteria andGrantDateEqualTo(Date value) {
            addCriterionForJDBCDate("grant_date =", value, "grantDate");
            return (Criteria) this;
        }

        public Criteria andGrantDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("grant_date <>", value, "grantDate");
            return (Criteria) this;
        }

        public Criteria andGrantDateGreaterThan(Date value) {
            addCriterionForJDBCDate("grant_date >", value, "grantDate");
            return (Criteria) this;
        }

        public Criteria andGrantDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("grant_date >=", value, "grantDate");
            return (Criteria) this;
        }

        public Criteria andGrantDateLessThan(Date value) {
            addCriterionForJDBCDate("grant_date <", value, "grantDate");
            return (Criteria) this;
        }

        public Criteria andGrantDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("grant_date <=", value, "grantDate");
            return (Criteria) this;
        }

        public Criteria andGrantDateIn(List<Date> values) {
            addCriterionForJDBCDate("grant_date in", values, "grantDate");
            return (Criteria) this;
        }

        public Criteria andGrantDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("grant_date not in", values, "grantDate");
            return (Criteria) this;
        }

        public Criteria andGrantDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("grant_date between", value1, value2, "grantDate");
            return (Criteria) this;
        }

        public Criteria andGrantDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("grant_date not between", value1, value2, "grantDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateIsNull() {
            addCriterion("finish_date is null");
            return (Criteria) this;
        }

        public Criteria andFinishDateIsNotNull() {
            addCriterion("finish_date is not null");
            return (Criteria) this;
        }

        public Criteria andFinishDateEqualTo(Date value) {
            addCriterion("finish_date =", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateNotEqualTo(Date value) {
            addCriterion("finish_date <>", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateGreaterThan(Date value) {
            addCriterion("finish_date >", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateGreaterThanOrEqualTo(Date value) {
            addCriterion("finish_date >=", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateLessThan(Date value) {
            addCriterion("finish_date <", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateLessThanOrEqualTo(Date value) {
            addCriterion("finish_date <=", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateIn(List<Date> values) {
            addCriterion("finish_date in", values, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateNotIn(List<Date> values) {
            addCriterion("finish_date not in", values, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateBetween(Date value1, Date value2) {
            addCriterion("finish_date between", value1, value2, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateNotBetween(Date value1, Date value2) {
            addCriterion("finish_date not between", value1, value2, "finishDate");
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

        public Criteria andOpUserIdIsNull() {
            addCriterion("op_user_id is null");
            return (Criteria) this;
        }

        public Criteria andOpUserIdIsNotNull() {
            addCriterion("op_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andOpUserIdEqualTo(Integer value) {
            addCriterion("op_user_id =", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdNotEqualTo(Integer value) {
            addCriterion("op_user_id <>", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdGreaterThan(Integer value) {
            addCriterion("op_user_id >", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("op_user_id >=", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdLessThan(Integer value) {
            addCriterion("op_user_id <", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("op_user_id <=", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdIn(List<Integer> values) {
            addCriterion("op_user_id in", values, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdNotIn(List<Integer> values) {
            addCriterion("op_user_id not in", values, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdBetween(Integer value1, Integer value2) {
            addCriterion("op_user_id between", value1, value2, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("op_user_id not between", value1, value2, "opUserId");
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