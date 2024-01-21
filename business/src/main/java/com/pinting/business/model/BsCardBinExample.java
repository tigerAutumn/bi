package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsCardBinExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsCardBinExample() {
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

        public Criteria andCardBinIsNull() {
            addCriterion("card_bin is null");
            return (Criteria) this;
        }

        public Criteria andCardBinIsNotNull() {
            addCriterion("card_bin is not null");
            return (Criteria) this;
        }

        public Criteria andCardBinEqualTo(String value) {
            addCriterion("card_bin =", value, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinNotEqualTo(String value) {
            addCriterion("card_bin <>", value, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinGreaterThan(String value) {
            addCriterion("card_bin >", value, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinGreaterThanOrEqualTo(String value) {
            addCriterion("card_bin >=", value, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinLessThan(String value) {
            addCriterion("card_bin <", value, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinLessThanOrEqualTo(String value) {
            addCriterion("card_bin <=", value, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinLike(String value) {
            addCriterion("card_bin like", value, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinNotLike(String value) {
            addCriterion("card_bin not like", value, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinIn(List<String> values) {
            addCriterion("card_bin in", values, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinNotIn(List<String> values) {
            addCriterion("card_bin not in", values, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinBetween(String value1, String value2) {
            addCriterion("card_bin between", value1, value2, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinNotBetween(String value1, String value2) {
            addCriterion("card_bin not between", value1, value2, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinLenIsNull() {
            addCriterion("card_bin_len is null");
            return (Criteria) this;
        }

        public Criteria andCardBinLenIsNotNull() {
            addCriterion("card_bin_len is not null");
            return (Criteria) this;
        }

        public Criteria andCardBinLenEqualTo(Integer value) {
            addCriterion("card_bin_len =", value, "cardBinLen");
            return (Criteria) this;
        }

        public Criteria andCardBinLenNotEqualTo(Integer value) {
            addCriterion("card_bin_len <>", value, "cardBinLen");
            return (Criteria) this;
        }

        public Criteria andCardBinLenGreaterThan(Integer value) {
            addCriterion("card_bin_len >", value, "cardBinLen");
            return (Criteria) this;
        }

        public Criteria andCardBinLenGreaterThanOrEqualTo(Integer value) {
            addCriterion("card_bin_len >=", value, "cardBinLen");
            return (Criteria) this;
        }

        public Criteria andCardBinLenLessThan(Integer value) {
            addCriterion("card_bin_len <", value, "cardBinLen");
            return (Criteria) this;
        }

        public Criteria andCardBinLenLessThanOrEqualTo(Integer value) {
            addCriterion("card_bin_len <=", value, "cardBinLen");
            return (Criteria) this;
        }

        public Criteria andCardBinLenIn(List<Integer> values) {
            addCriterion("card_bin_len in", values, "cardBinLen");
            return (Criteria) this;
        }

        public Criteria andCardBinLenNotIn(List<Integer> values) {
            addCriterion("card_bin_len not in", values, "cardBinLen");
            return (Criteria) this;
        }

        public Criteria andCardBinLenBetween(Integer value1, Integer value2) {
            addCriterion("card_bin_len between", value1, value2, "cardBinLen");
            return (Criteria) this;
        }

        public Criteria andCardBinLenNotBetween(Integer value1, Integer value2) {
            addCriterion("card_bin_len not between", value1, value2, "cardBinLen");
            return (Criteria) this;
        }

        public Criteria andBankCardLenIsNull() {
            addCriterion("bank_card_len is null");
            return (Criteria) this;
        }

        public Criteria andBankCardLenIsNotNull() {
            addCriterion("bank_card_len is not null");
            return (Criteria) this;
        }

        public Criteria andBankCardLenEqualTo(Integer value) {
            addCriterion("bank_card_len =", value, "bankCardLen");
            return (Criteria) this;
        }

        public Criteria andBankCardLenNotEqualTo(Integer value) {
            addCriterion("bank_card_len <>", value, "bankCardLen");
            return (Criteria) this;
        }

        public Criteria andBankCardLenGreaterThan(Integer value) {
            addCriterion("bank_card_len >", value, "bankCardLen");
            return (Criteria) this;
        }

        public Criteria andBankCardLenGreaterThanOrEqualTo(Integer value) {
            addCriterion("bank_card_len >=", value, "bankCardLen");
            return (Criteria) this;
        }

        public Criteria andBankCardLenLessThan(Integer value) {
            addCriterion("bank_card_len <", value, "bankCardLen");
            return (Criteria) this;
        }

        public Criteria andBankCardLenLessThanOrEqualTo(Integer value) {
            addCriterion("bank_card_len <=", value, "bankCardLen");
            return (Criteria) this;
        }

        public Criteria andBankCardLenIn(List<Integer> values) {
            addCriterion("bank_card_len in", values, "bankCardLen");
            return (Criteria) this;
        }

        public Criteria andBankCardLenNotIn(List<Integer> values) {
            addCriterion("bank_card_len not in", values, "bankCardLen");
            return (Criteria) this;
        }

        public Criteria andBankCardLenBetween(Integer value1, Integer value2) {
            addCriterion("bank_card_len between", value1, value2, "bankCardLen");
            return (Criteria) this;
        }

        public Criteria andBankCardLenNotBetween(Integer value1, Integer value2) {
            addCriterion("bank_card_len not between", value1, value2, "bankCardLen");
            return (Criteria) this;
        }

        public Criteria andBankNameDescIsNull() {
            addCriterion("bank_name_desc is null");
            return (Criteria) this;
        }

        public Criteria andBankNameDescIsNotNull() {
            addCriterion("bank_name_desc is not null");
            return (Criteria) this;
        }

        public Criteria andBankNameDescEqualTo(String value) {
            addCriterion("bank_name_desc =", value, "bankNameDesc");
            return (Criteria) this;
        }

        public Criteria andBankNameDescNotEqualTo(String value) {
            addCriterion("bank_name_desc <>", value, "bankNameDesc");
            return (Criteria) this;
        }

        public Criteria andBankNameDescGreaterThan(String value) {
            addCriterion("bank_name_desc >", value, "bankNameDesc");
            return (Criteria) this;
        }

        public Criteria andBankNameDescGreaterThanOrEqualTo(String value) {
            addCriterion("bank_name_desc >=", value, "bankNameDesc");
            return (Criteria) this;
        }

        public Criteria andBankNameDescLessThan(String value) {
            addCriterion("bank_name_desc <", value, "bankNameDesc");
            return (Criteria) this;
        }

        public Criteria andBankNameDescLessThanOrEqualTo(String value) {
            addCriterion("bank_name_desc <=", value, "bankNameDesc");
            return (Criteria) this;
        }

        public Criteria andBankNameDescLike(String value) {
            addCriterion("bank_name_desc like", value, "bankNameDesc");
            return (Criteria) this;
        }

        public Criteria andBankNameDescNotLike(String value) {
            addCriterion("bank_name_desc not like", value, "bankNameDesc");
            return (Criteria) this;
        }

        public Criteria andBankNameDescIn(List<String> values) {
            addCriterion("bank_name_desc in", values, "bankNameDesc");
            return (Criteria) this;
        }

        public Criteria andBankNameDescNotIn(List<String> values) {
            addCriterion("bank_name_desc not in", values, "bankNameDesc");
            return (Criteria) this;
        }

        public Criteria andBankNameDescBetween(String value1, String value2) {
            addCriterion("bank_name_desc between", value1, value2, "bankNameDesc");
            return (Criteria) this;
        }

        public Criteria andBankNameDescNotBetween(String value1, String value2) {
            addCriterion("bank_name_desc not between", value1, value2, "bankNameDesc");
            return (Criteria) this;
        }

        public Criteria andBankCardTypeDescIsNull() {
            addCriterion("bank_card_type_desc is null");
            return (Criteria) this;
        }

        public Criteria andBankCardTypeDescIsNotNull() {
            addCriterion("bank_card_type_desc is not null");
            return (Criteria) this;
        }

        public Criteria andBankCardTypeDescEqualTo(String value) {
            addCriterion("bank_card_type_desc =", value, "bankCardTypeDesc");
            return (Criteria) this;
        }

        public Criteria andBankCardTypeDescNotEqualTo(String value) {
            addCriterion("bank_card_type_desc <>", value, "bankCardTypeDesc");
            return (Criteria) this;
        }

        public Criteria andBankCardTypeDescGreaterThan(String value) {
            addCriterion("bank_card_type_desc >", value, "bankCardTypeDesc");
            return (Criteria) this;
        }

        public Criteria andBankCardTypeDescGreaterThanOrEqualTo(String value) {
            addCriterion("bank_card_type_desc >=", value, "bankCardTypeDesc");
            return (Criteria) this;
        }

        public Criteria andBankCardTypeDescLessThan(String value) {
            addCriterion("bank_card_type_desc <", value, "bankCardTypeDesc");
            return (Criteria) this;
        }

        public Criteria andBankCardTypeDescLessThanOrEqualTo(String value) {
            addCriterion("bank_card_type_desc <=", value, "bankCardTypeDesc");
            return (Criteria) this;
        }

        public Criteria andBankCardTypeDescLike(String value) {
            addCriterion("bank_card_type_desc like", value, "bankCardTypeDesc");
            return (Criteria) this;
        }

        public Criteria andBankCardTypeDescNotLike(String value) {
            addCriterion("bank_card_type_desc not like", value, "bankCardTypeDesc");
            return (Criteria) this;
        }

        public Criteria andBankCardTypeDescIn(List<String> values) {
            addCriterion("bank_card_type_desc in", values, "bankCardTypeDesc");
            return (Criteria) this;
        }

        public Criteria andBankCardTypeDescNotIn(List<String> values) {
            addCriterion("bank_card_type_desc not in", values, "bankCardTypeDesc");
            return (Criteria) this;
        }

        public Criteria andBankCardTypeDescBetween(String value1, String value2) {
            addCriterion("bank_card_type_desc between", value1, value2, "bankCardTypeDesc");
            return (Criteria) this;
        }

        public Criteria andBankCardTypeDescNotBetween(String value1, String value2) {
            addCriterion("bank_card_type_desc not between", value1, value2, "bankCardTypeDesc");
            return (Criteria) this;
        }

        public Criteria andBankCardFuncTypeIsNull() {
            addCriterion("bank_card_func_type is null");
            return (Criteria) this;
        }

        public Criteria andBankCardFuncTypeIsNotNull() {
            addCriterion("bank_card_func_type is not null");
            return (Criteria) this;
        }

        public Criteria andBankCardFuncTypeEqualTo(String value) {
            addCriterion("bank_card_func_type =", value, "bankCardFuncType");
            return (Criteria) this;
        }

        public Criteria andBankCardFuncTypeNotEqualTo(String value) {
            addCriterion("bank_card_func_type <>", value, "bankCardFuncType");
            return (Criteria) this;
        }

        public Criteria andBankCardFuncTypeGreaterThan(String value) {
            addCriterion("bank_card_func_type >", value, "bankCardFuncType");
            return (Criteria) this;
        }

        public Criteria andBankCardFuncTypeGreaterThanOrEqualTo(String value) {
            addCriterion("bank_card_func_type >=", value, "bankCardFuncType");
            return (Criteria) this;
        }

        public Criteria andBankCardFuncTypeLessThan(String value) {
            addCriterion("bank_card_func_type <", value, "bankCardFuncType");
            return (Criteria) this;
        }

        public Criteria andBankCardFuncTypeLessThanOrEqualTo(String value) {
            addCriterion("bank_card_func_type <=", value, "bankCardFuncType");
            return (Criteria) this;
        }

        public Criteria andBankCardFuncTypeLike(String value) {
            addCriterion("bank_card_func_type like", value, "bankCardFuncType");
            return (Criteria) this;
        }

        public Criteria andBankCardFuncTypeNotLike(String value) {
            addCriterion("bank_card_func_type not like", value, "bankCardFuncType");
            return (Criteria) this;
        }

        public Criteria andBankCardFuncTypeIn(List<String> values) {
            addCriterion("bank_card_func_type in", values, "bankCardFuncType");
            return (Criteria) this;
        }

        public Criteria andBankCardFuncTypeNotIn(List<String> values) {
            addCriterion("bank_card_func_type not in", values, "bankCardFuncType");
            return (Criteria) this;
        }

        public Criteria andBankCardFuncTypeBetween(String value1, String value2) {
            addCriterion("bank_card_func_type between", value1, value2, "bankCardFuncType");
            return (Criteria) this;
        }

        public Criteria andBankCardFuncTypeNotBetween(String value1, String value2) {
            addCriterion("bank_card_func_type not between", value1, value2, "bankCardFuncType");
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