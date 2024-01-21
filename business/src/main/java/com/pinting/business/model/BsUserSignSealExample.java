package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsUserSignSealExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsUserSignSealExample() {
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

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
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

        public Criteria andP10IsNull() {
            addCriterion("p10 is null");
            return (Criteria) this;
        }

        public Criteria andP10IsNotNull() {
            addCriterion("p10 is not null");
            return (Criteria) this;
        }

        public Criteria andP10EqualTo(String value) {
            addCriterion("p10 =", value, "p10");
            return (Criteria) this;
        }

        public Criteria andP10NotEqualTo(String value) {
            addCriterion("p10 <>", value, "p10");
            return (Criteria) this;
        }

        public Criteria andP10GreaterThan(String value) {
            addCriterion("p10 >", value, "p10");
            return (Criteria) this;
        }

        public Criteria andP10GreaterThanOrEqualTo(String value) {
            addCriterion("p10 >=", value, "p10");
            return (Criteria) this;
        }

        public Criteria andP10LessThan(String value) {
            addCriterion("p10 <", value, "p10");
            return (Criteria) this;
        }

        public Criteria andP10LessThanOrEqualTo(String value) {
            addCriterion("p10 <=", value, "p10");
            return (Criteria) this;
        }

        public Criteria andP10Like(String value) {
            addCriterion("p10 like", value, "p10");
            return (Criteria) this;
        }

        public Criteria andP10NotLike(String value) {
            addCriterion("p10 not like", value, "p10");
            return (Criteria) this;
        }

        public Criteria andP10In(List<String> values) {
            addCriterion("p10 in", values, "p10");
            return (Criteria) this;
        }

        public Criteria andP10NotIn(List<String> values) {
            addCriterion("p10 not in", values, "p10");
            return (Criteria) this;
        }

        public Criteria andP10Between(String value1, String value2) {
            addCriterion("p10 between", value1, value2, "p10");
            return (Criteria) this;
        }

        public Criteria andP10NotBetween(String value1, String value2) {
            addCriterion("p10 not between", value1, value2, "p10");
            return (Criteria) this;
        }

        public Criteria andKeyIdentifierIsNull() {
            addCriterion("key_identifier is null");
            return (Criteria) this;
        }

        public Criteria andKeyIdentifierIsNotNull() {
            addCriterion("key_identifier is not null");
            return (Criteria) this;
        }

        public Criteria andKeyIdentifierEqualTo(String value) {
            addCriterion("key_identifier =", value, "keyIdentifier");
            return (Criteria) this;
        }

        public Criteria andKeyIdentifierNotEqualTo(String value) {
            addCriterion("key_identifier <>", value, "keyIdentifier");
            return (Criteria) this;
        }

        public Criteria andKeyIdentifierGreaterThan(String value) {
            addCriterion("key_identifier >", value, "keyIdentifier");
            return (Criteria) this;
        }

        public Criteria andKeyIdentifierGreaterThanOrEqualTo(String value) {
            addCriterion("key_identifier >=", value, "keyIdentifier");
            return (Criteria) this;
        }

        public Criteria andKeyIdentifierLessThan(String value) {
            addCriterion("key_identifier <", value, "keyIdentifier");
            return (Criteria) this;
        }

        public Criteria andKeyIdentifierLessThanOrEqualTo(String value) {
            addCriterion("key_identifier <=", value, "keyIdentifier");
            return (Criteria) this;
        }

        public Criteria andKeyIdentifierLike(String value) {
            addCriterion("key_identifier like", value, "keyIdentifier");
            return (Criteria) this;
        }

        public Criteria andKeyIdentifierNotLike(String value) {
            addCriterion("key_identifier not like", value, "keyIdentifier");
            return (Criteria) this;
        }

        public Criteria andKeyIdentifierIn(List<String> values) {
            addCriterion("key_identifier in", values, "keyIdentifier");
            return (Criteria) this;
        }

        public Criteria andKeyIdentifierNotIn(List<String> values) {
            addCriterion("key_identifier not in", values, "keyIdentifier");
            return (Criteria) this;
        }

        public Criteria andKeyIdentifierBetween(String value1, String value2) {
            addCriterion("key_identifier between", value1, value2, "keyIdentifier");
            return (Criteria) this;
        }

        public Criteria andKeyIdentifierNotBetween(String value1, String value2) {
            addCriterion("key_identifier not between", value1, value2, "keyIdentifier");
            return (Criteria) this;
        }

        public Criteria andDnIsNull() {
            addCriterion("dn is null");
            return (Criteria) this;
        }

        public Criteria andDnIsNotNull() {
            addCriterion("dn is not null");
            return (Criteria) this;
        }

        public Criteria andDnEqualTo(String value) {
            addCriterion("dn =", value, "dn");
            return (Criteria) this;
        }

        public Criteria andDnNotEqualTo(String value) {
            addCriterion("dn <>", value, "dn");
            return (Criteria) this;
        }

        public Criteria andDnGreaterThan(String value) {
            addCriterion("dn >", value, "dn");
            return (Criteria) this;
        }

        public Criteria andDnGreaterThanOrEqualTo(String value) {
            addCriterion("dn >=", value, "dn");
            return (Criteria) this;
        }

        public Criteria andDnLessThan(String value) {
            addCriterion("dn <", value, "dn");
            return (Criteria) this;
        }

        public Criteria andDnLessThanOrEqualTo(String value) {
            addCriterion("dn <=", value, "dn");
            return (Criteria) this;
        }

        public Criteria andDnLike(String value) {
            addCriterion("dn like", value, "dn");
            return (Criteria) this;
        }

        public Criteria andDnNotLike(String value) {
            addCriterion("dn not like", value, "dn");
            return (Criteria) this;
        }

        public Criteria andDnIn(List<String> values) {
            addCriterion("dn in", values, "dn");
            return (Criteria) this;
        }

        public Criteria andDnNotIn(List<String> values) {
            addCriterion("dn not in", values, "dn");
            return (Criteria) this;
        }

        public Criteria andDnBetween(String value1, String value2) {
            addCriterion("dn between", value1, value2, "dn");
            return (Criteria) this;
        }

        public Criteria andDnNotBetween(String value1, String value2) {
            addCriterion("dn not between", value1, value2, "dn");
            return (Criteria) this;
        }

        public Criteria andSequenceNoIsNull() {
            addCriterion("sequence_no is null");
            return (Criteria) this;
        }

        public Criteria andSequenceNoIsNotNull() {
            addCriterion("sequence_no is not null");
            return (Criteria) this;
        }

        public Criteria andSequenceNoEqualTo(String value) {
            addCriterion("sequence_no =", value, "sequenceNo");
            return (Criteria) this;
        }

        public Criteria andSequenceNoNotEqualTo(String value) {
            addCriterion("sequence_no <>", value, "sequenceNo");
            return (Criteria) this;
        }

        public Criteria andSequenceNoGreaterThan(String value) {
            addCriterion("sequence_no >", value, "sequenceNo");
            return (Criteria) this;
        }

        public Criteria andSequenceNoGreaterThanOrEqualTo(String value) {
            addCriterion("sequence_no >=", value, "sequenceNo");
            return (Criteria) this;
        }

        public Criteria andSequenceNoLessThan(String value) {
            addCriterion("sequence_no <", value, "sequenceNo");
            return (Criteria) this;
        }

        public Criteria andSequenceNoLessThanOrEqualTo(String value) {
            addCriterion("sequence_no <=", value, "sequenceNo");
            return (Criteria) this;
        }

        public Criteria andSequenceNoLike(String value) {
            addCriterion("sequence_no like", value, "sequenceNo");
            return (Criteria) this;
        }

        public Criteria andSequenceNoNotLike(String value) {
            addCriterion("sequence_no not like", value, "sequenceNo");
            return (Criteria) this;
        }

        public Criteria andSequenceNoIn(List<String> values) {
            addCriterion("sequence_no in", values, "sequenceNo");
            return (Criteria) this;
        }

        public Criteria andSequenceNoNotIn(List<String> values) {
            addCriterion("sequence_no not in", values, "sequenceNo");
            return (Criteria) this;
        }

        public Criteria andSequenceNoBetween(String value1, String value2) {
            addCriterion("sequence_no between", value1, value2, "sequenceNo");
            return (Criteria) this;
        }

        public Criteria andSequenceNoNotBetween(String value1, String value2) {
            addCriterion("sequence_no not between", value1, value2, "sequenceNo");
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

        public Criteria andSerialNoEqualTo(String value) {
            addCriterion("serial_no =", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotEqualTo(String value) {
            addCriterion("serial_no <>", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThan(String value) {
            addCriterion("serial_no >", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThanOrEqualTo(String value) {
            addCriterion("serial_no >=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThan(String value) {
            addCriterion("serial_no <", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThanOrEqualTo(String value) {
            addCriterion("serial_no <=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLike(String value) {
            addCriterion("serial_no like", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotLike(String value) {
            addCriterion("serial_no not like", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoIn(List<String> values) {
            addCriterion("serial_no in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotIn(List<String> values) {
            addCriterion("serial_no not in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoBetween(String value1, String value2) {
            addCriterion("serial_no between", value1, value2, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotBetween(String value1, String value2) {
            addCriterion("serial_no not between", value1, value2, "serialNo");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(String value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(String value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(String value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(String value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(String value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(String value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLike(String value) {
            addCriterion("start_time like", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotLike(String value) {
            addCriterion("start_time not like", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<String> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<String> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(String value1, String value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(String value1, String value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(String value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(String value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(String value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(String value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(String value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLike(String value) {
            addCriterion("end_time like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotLike(String value) {
            addCriterion("end_time not like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<String> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<String> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(String value1, String value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(String value1, String value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andSignatureCertIsNull() {
            addCriterion("signature_cert is null");
            return (Criteria) this;
        }

        public Criteria andSignatureCertIsNotNull() {
            addCriterion("signature_cert is not null");
            return (Criteria) this;
        }

        public Criteria andSignatureCertEqualTo(String value) {
            addCriterion("signature_cert =", value, "signatureCert");
            return (Criteria) this;
        }

        public Criteria andSignatureCertNotEqualTo(String value) {
            addCriterion("signature_cert <>", value, "signatureCert");
            return (Criteria) this;
        }

        public Criteria andSignatureCertGreaterThan(String value) {
            addCriterion("signature_cert >", value, "signatureCert");
            return (Criteria) this;
        }

        public Criteria andSignatureCertGreaterThanOrEqualTo(String value) {
            addCriterion("signature_cert >=", value, "signatureCert");
            return (Criteria) this;
        }

        public Criteria andSignatureCertLessThan(String value) {
            addCriterion("signature_cert <", value, "signatureCert");
            return (Criteria) this;
        }

        public Criteria andSignatureCertLessThanOrEqualTo(String value) {
            addCriterion("signature_cert <=", value, "signatureCert");
            return (Criteria) this;
        }

        public Criteria andSignatureCertLike(String value) {
            addCriterion("signature_cert like", value, "signatureCert");
            return (Criteria) this;
        }

        public Criteria andSignatureCertNotLike(String value) {
            addCriterion("signature_cert not like", value, "signatureCert");
            return (Criteria) this;
        }

        public Criteria andSignatureCertIn(List<String> values) {
            addCriterion("signature_cert in", values, "signatureCert");
            return (Criteria) this;
        }

        public Criteria andSignatureCertNotIn(List<String> values) {
            addCriterion("signature_cert not in", values, "signatureCert");
            return (Criteria) this;
        }

        public Criteria andSignatureCertBetween(String value1, String value2) {
            addCriterion("signature_cert between", value1, value2, "signatureCert");
            return (Criteria) this;
        }

        public Criteria andSignatureCertNotBetween(String value1, String value2) {
            addCriterion("signature_cert not between", value1, value2, "signatureCert");
            return (Criteria) this;
        }

        public Criteria andEncryptionCertIsNull() {
            addCriterion("encryption_cert is null");
            return (Criteria) this;
        }

        public Criteria andEncryptionCertIsNotNull() {
            addCriterion("encryption_cert is not null");
            return (Criteria) this;
        }

        public Criteria andEncryptionCertEqualTo(String value) {
            addCriterion("encryption_cert =", value, "encryptionCert");
            return (Criteria) this;
        }

        public Criteria andEncryptionCertNotEqualTo(String value) {
            addCriterion("encryption_cert <>", value, "encryptionCert");
            return (Criteria) this;
        }

        public Criteria andEncryptionCertGreaterThan(String value) {
            addCriterion("encryption_cert >", value, "encryptionCert");
            return (Criteria) this;
        }

        public Criteria andEncryptionCertGreaterThanOrEqualTo(String value) {
            addCriterion("encryption_cert >=", value, "encryptionCert");
            return (Criteria) this;
        }

        public Criteria andEncryptionCertLessThan(String value) {
            addCriterion("encryption_cert <", value, "encryptionCert");
            return (Criteria) this;
        }

        public Criteria andEncryptionCertLessThanOrEqualTo(String value) {
            addCriterion("encryption_cert <=", value, "encryptionCert");
            return (Criteria) this;
        }

        public Criteria andEncryptionCertLike(String value) {
            addCriterion("encryption_cert like", value, "encryptionCert");
            return (Criteria) this;
        }

        public Criteria andEncryptionCertNotLike(String value) {
            addCriterion("encryption_cert not like", value, "encryptionCert");
            return (Criteria) this;
        }

        public Criteria andEncryptionCertIn(List<String> values) {
            addCriterion("encryption_cert in", values, "encryptionCert");
            return (Criteria) this;
        }

        public Criteria andEncryptionCertNotIn(List<String> values) {
            addCriterion("encryption_cert not in", values, "encryptionCert");
            return (Criteria) this;
        }

        public Criteria andEncryptionCertBetween(String value1, String value2) {
            addCriterion("encryption_cert between", value1, value2, "encryptionCert");
            return (Criteria) this;
        }

        public Criteria andEncryptionCertNotBetween(String value1, String value2) {
            addCriterion("encryption_cert not between", value1, value2, "encryptionCert");
            return (Criteria) this;
        }

        public Criteria andEncryptionPrivateKeyIsNull() {
            addCriterion("encryption_private_key is null");
            return (Criteria) this;
        }

        public Criteria andEncryptionPrivateKeyIsNotNull() {
            addCriterion("encryption_private_key is not null");
            return (Criteria) this;
        }

        public Criteria andEncryptionPrivateKeyEqualTo(String value) {
            addCriterion("encryption_private_key =", value, "encryptionPrivateKey");
            return (Criteria) this;
        }

        public Criteria andEncryptionPrivateKeyNotEqualTo(String value) {
            addCriterion("encryption_private_key <>", value, "encryptionPrivateKey");
            return (Criteria) this;
        }

        public Criteria andEncryptionPrivateKeyGreaterThan(String value) {
            addCriterion("encryption_private_key >", value, "encryptionPrivateKey");
            return (Criteria) this;
        }

        public Criteria andEncryptionPrivateKeyGreaterThanOrEqualTo(String value) {
            addCriterion("encryption_private_key >=", value, "encryptionPrivateKey");
            return (Criteria) this;
        }

        public Criteria andEncryptionPrivateKeyLessThan(String value) {
            addCriterion("encryption_private_key <", value, "encryptionPrivateKey");
            return (Criteria) this;
        }

        public Criteria andEncryptionPrivateKeyLessThanOrEqualTo(String value) {
            addCriterion("encryption_private_key <=", value, "encryptionPrivateKey");
            return (Criteria) this;
        }

        public Criteria andEncryptionPrivateKeyLike(String value) {
            addCriterion("encryption_private_key like", value, "encryptionPrivateKey");
            return (Criteria) this;
        }

        public Criteria andEncryptionPrivateKeyNotLike(String value) {
            addCriterion("encryption_private_key not like", value, "encryptionPrivateKey");
            return (Criteria) this;
        }

        public Criteria andEncryptionPrivateKeyIn(List<String> values) {
            addCriterion("encryption_private_key in", values, "encryptionPrivateKey");
            return (Criteria) this;
        }

        public Criteria andEncryptionPrivateKeyNotIn(List<String> values) {
            addCriterion("encryption_private_key not in", values, "encryptionPrivateKey");
            return (Criteria) this;
        }

        public Criteria andEncryptionPrivateKeyBetween(String value1, String value2) {
            addCriterion("encryption_private_key between", value1, value2, "encryptionPrivateKey");
            return (Criteria) this;
        }

        public Criteria andEncryptionPrivateKeyNotBetween(String value1, String value2) {
            addCriterion("encryption_private_key not between", value1, value2, "encryptionPrivateKey");
            return (Criteria) this;
        }

        public Criteria andPfxDataIsNull() {
            addCriterion("pfx_data is null");
            return (Criteria) this;
        }

        public Criteria andPfxDataIsNotNull() {
            addCriterion("pfx_data is not null");
            return (Criteria) this;
        }

        public Criteria andPfxDataEqualTo(String value) {
            addCriterion("pfx_data =", value, "pfxData");
            return (Criteria) this;
        }

        public Criteria andPfxDataNotEqualTo(String value) {
            addCriterion("pfx_data <>", value, "pfxData");
            return (Criteria) this;
        }

        public Criteria andPfxDataGreaterThan(String value) {
            addCriterion("pfx_data >", value, "pfxData");
            return (Criteria) this;
        }

        public Criteria andPfxDataGreaterThanOrEqualTo(String value) {
            addCriterion("pfx_data >=", value, "pfxData");
            return (Criteria) this;
        }

        public Criteria andPfxDataLessThan(String value) {
            addCriterion("pfx_data <", value, "pfxData");
            return (Criteria) this;
        }

        public Criteria andPfxDataLessThanOrEqualTo(String value) {
            addCriterion("pfx_data <=", value, "pfxData");
            return (Criteria) this;
        }

        public Criteria andPfxDataLike(String value) {
            addCriterion("pfx_data like", value, "pfxData");
            return (Criteria) this;
        }

        public Criteria andPfxDataNotLike(String value) {
            addCriterion("pfx_data not like", value, "pfxData");
            return (Criteria) this;
        }

        public Criteria andPfxDataIn(List<String> values) {
            addCriterion("pfx_data in", values, "pfxData");
            return (Criteria) this;
        }

        public Criteria andPfxDataNotIn(List<String> values) {
            addCriterion("pfx_data not in", values, "pfxData");
            return (Criteria) this;
        }

        public Criteria andPfxDataBetween(String value1, String value2) {
            addCriterion("pfx_data between", value1, value2, "pfxData");
            return (Criteria) this;
        }

        public Criteria andPfxDataNotBetween(String value1, String value2) {
            addCriterion("pfx_data not between", value1, value2, "pfxData");
            return (Criteria) this;
        }

        public Criteria andPfxPathIsNull() {
            addCriterion("pfx_path is null");
            return (Criteria) this;
        }

        public Criteria andPfxPathIsNotNull() {
            addCriterion("pfx_path is not null");
            return (Criteria) this;
        }

        public Criteria andPfxPathEqualTo(String value) {
            addCriterion("pfx_path =", value, "pfxPath");
            return (Criteria) this;
        }

        public Criteria andPfxPathNotEqualTo(String value) {
            addCriterion("pfx_path <>", value, "pfxPath");
            return (Criteria) this;
        }

        public Criteria andPfxPathGreaterThan(String value) {
            addCriterion("pfx_path >", value, "pfxPath");
            return (Criteria) this;
        }

        public Criteria andPfxPathGreaterThanOrEqualTo(String value) {
            addCriterion("pfx_path >=", value, "pfxPath");
            return (Criteria) this;
        }

        public Criteria andPfxPathLessThan(String value) {
            addCriterion("pfx_path <", value, "pfxPath");
            return (Criteria) this;
        }

        public Criteria andPfxPathLessThanOrEqualTo(String value) {
            addCriterion("pfx_path <=", value, "pfxPath");
            return (Criteria) this;
        }

        public Criteria andPfxPathLike(String value) {
            addCriterion("pfx_path like", value, "pfxPath");
            return (Criteria) this;
        }

        public Criteria andPfxPathNotLike(String value) {
            addCriterion("pfx_path not like", value, "pfxPath");
            return (Criteria) this;
        }

        public Criteria andPfxPathIn(List<String> values) {
            addCriterion("pfx_path in", values, "pfxPath");
            return (Criteria) this;
        }

        public Criteria andPfxPathNotIn(List<String> values) {
            addCriterion("pfx_path not in", values, "pfxPath");
            return (Criteria) this;
        }

        public Criteria andPfxPathBetween(String value1, String value2) {
            addCriterion("pfx_path between", value1, value2, "pfxPath");
            return (Criteria) this;
        }

        public Criteria andPfxPathNotBetween(String value1, String value2) {
            addCriterion("pfx_path not between", value1, value2, "pfxPath");
            return (Criteria) this;
        }

        public Criteria andPfxPasswordIsNull() {
            addCriterion("pfx_password is null");
            return (Criteria) this;
        }

        public Criteria andPfxPasswordIsNotNull() {
            addCriterion("pfx_password is not null");
            return (Criteria) this;
        }

        public Criteria andPfxPasswordEqualTo(String value) {
            addCriterion("pfx_password =", value, "pfxPassword");
            return (Criteria) this;
        }

        public Criteria andPfxPasswordNotEqualTo(String value) {
            addCriterion("pfx_password <>", value, "pfxPassword");
            return (Criteria) this;
        }

        public Criteria andPfxPasswordGreaterThan(String value) {
            addCriterion("pfx_password >", value, "pfxPassword");
            return (Criteria) this;
        }

        public Criteria andPfxPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("pfx_password >=", value, "pfxPassword");
            return (Criteria) this;
        }

        public Criteria andPfxPasswordLessThan(String value) {
            addCriterion("pfx_password <", value, "pfxPassword");
            return (Criteria) this;
        }

        public Criteria andPfxPasswordLessThanOrEqualTo(String value) {
            addCriterion("pfx_password <=", value, "pfxPassword");
            return (Criteria) this;
        }

        public Criteria andPfxPasswordLike(String value) {
            addCriterion("pfx_password like", value, "pfxPassword");
            return (Criteria) this;
        }

        public Criteria andPfxPasswordNotLike(String value) {
            addCriterion("pfx_password not like", value, "pfxPassword");
            return (Criteria) this;
        }

        public Criteria andPfxPasswordIn(List<String> values) {
            addCriterion("pfx_password in", values, "pfxPassword");
            return (Criteria) this;
        }

        public Criteria andPfxPasswordNotIn(List<String> values) {
            addCriterion("pfx_password not in", values, "pfxPassword");
            return (Criteria) this;
        }

        public Criteria andPfxPasswordBetween(String value1, String value2) {
            addCriterion("pfx_password between", value1, value2, "pfxPassword");
            return (Criteria) this;
        }

        public Criteria andPfxPasswordNotBetween(String value1, String value2) {
            addCriterion("pfx_password not between", value1, value2, "pfxPassword");
            return (Criteria) this;
        }

        public Criteria andSealPersonIsNull() {
            addCriterion("seal_person is null");
            return (Criteria) this;
        }

        public Criteria andSealPersonIsNotNull() {
            addCriterion("seal_person is not null");
            return (Criteria) this;
        }

        public Criteria andSealPersonEqualTo(String value) {
            addCriterion("seal_person =", value, "sealPerson");
            return (Criteria) this;
        }

        public Criteria andSealPersonNotEqualTo(String value) {
            addCriterion("seal_person <>", value, "sealPerson");
            return (Criteria) this;
        }

        public Criteria andSealPersonGreaterThan(String value) {
            addCriterion("seal_person >", value, "sealPerson");
            return (Criteria) this;
        }

        public Criteria andSealPersonGreaterThanOrEqualTo(String value) {
            addCriterion("seal_person >=", value, "sealPerson");
            return (Criteria) this;
        }

        public Criteria andSealPersonLessThan(String value) {
            addCriterion("seal_person <", value, "sealPerson");
            return (Criteria) this;
        }

        public Criteria andSealPersonLessThanOrEqualTo(String value) {
            addCriterion("seal_person <=", value, "sealPerson");
            return (Criteria) this;
        }

        public Criteria andSealPersonLike(String value) {
            addCriterion("seal_person like", value, "sealPerson");
            return (Criteria) this;
        }

        public Criteria andSealPersonNotLike(String value) {
            addCriterion("seal_person not like", value, "sealPerson");
            return (Criteria) this;
        }

        public Criteria andSealPersonIn(List<String> values) {
            addCriterion("seal_person in", values, "sealPerson");
            return (Criteria) this;
        }

        public Criteria andSealPersonNotIn(List<String> values) {
            addCriterion("seal_person not in", values, "sealPerson");
            return (Criteria) this;
        }

        public Criteria andSealPersonBetween(String value1, String value2) {
            addCriterion("seal_person between", value1, value2, "sealPerson");
            return (Criteria) this;
        }

        public Criteria andSealPersonNotBetween(String value1, String value2) {
            addCriterion("seal_person not between", value1, value2, "sealPerson");
            return (Criteria) this;
        }

        public Criteria andSealOrgIsNull() {
            addCriterion("seal_org is null");
            return (Criteria) this;
        }

        public Criteria andSealOrgIsNotNull() {
            addCriterion("seal_org is not null");
            return (Criteria) this;
        }

        public Criteria andSealOrgEqualTo(String value) {
            addCriterion("seal_org =", value, "sealOrg");
            return (Criteria) this;
        }

        public Criteria andSealOrgNotEqualTo(String value) {
            addCriterion("seal_org <>", value, "sealOrg");
            return (Criteria) this;
        }

        public Criteria andSealOrgGreaterThan(String value) {
            addCriterion("seal_org >", value, "sealOrg");
            return (Criteria) this;
        }

        public Criteria andSealOrgGreaterThanOrEqualTo(String value) {
            addCriterion("seal_org >=", value, "sealOrg");
            return (Criteria) this;
        }

        public Criteria andSealOrgLessThan(String value) {
            addCriterion("seal_org <", value, "sealOrg");
            return (Criteria) this;
        }

        public Criteria andSealOrgLessThanOrEqualTo(String value) {
            addCriterion("seal_org <=", value, "sealOrg");
            return (Criteria) this;
        }

        public Criteria andSealOrgLike(String value) {
            addCriterion("seal_org like", value, "sealOrg");
            return (Criteria) this;
        }

        public Criteria andSealOrgNotLike(String value) {
            addCriterion("seal_org not like", value, "sealOrg");
            return (Criteria) this;
        }

        public Criteria andSealOrgIn(List<String> values) {
            addCriterion("seal_org in", values, "sealOrg");
            return (Criteria) this;
        }

        public Criteria andSealOrgNotIn(List<String> values) {
            addCriterion("seal_org not in", values, "sealOrg");
            return (Criteria) this;
        }

        public Criteria andSealOrgBetween(String value1, String value2) {
            addCriterion("seal_org between", value1, value2, "sealOrg");
            return (Criteria) this;
        }

        public Criteria andSealOrgNotBetween(String value1, String value2) {
            addCriterion("seal_org not between", value1, value2, "sealOrg");
            return (Criteria) this;
        }

        public Criteria andSealNameIsNull() {
            addCriterion("seal_name is null");
            return (Criteria) this;
        }

        public Criteria andSealNameIsNotNull() {
            addCriterion("seal_name is not null");
            return (Criteria) this;
        }

        public Criteria andSealNameEqualTo(String value) {
            addCriterion("seal_name =", value, "sealName");
            return (Criteria) this;
        }

        public Criteria andSealNameNotEqualTo(String value) {
            addCriterion("seal_name <>", value, "sealName");
            return (Criteria) this;
        }

        public Criteria andSealNameGreaterThan(String value) {
            addCriterion("seal_name >", value, "sealName");
            return (Criteria) this;
        }

        public Criteria andSealNameGreaterThanOrEqualTo(String value) {
            addCriterion("seal_name >=", value, "sealName");
            return (Criteria) this;
        }

        public Criteria andSealNameLessThan(String value) {
            addCriterion("seal_name <", value, "sealName");
            return (Criteria) this;
        }

        public Criteria andSealNameLessThanOrEqualTo(String value) {
            addCriterion("seal_name <=", value, "sealName");
            return (Criteria) this;
        }

        public Criteria andSealNameLike(String value) {
            addCriterion("seal_name like", value, "sealName");
            return (Criteria) this;
        }

        public Criteria andSealNameNotLike(String value) {
            addCriterion("seal_name not like", value, "sealName");
            return (Criteria) this;
        }

        public Criteria andSealNameIn(List<String> values) {
            addCriterion("seal_name in", values, "sealName");
            return (Criteria) this;
        }

        public Criteria andSealNameNotIn(List<String> values) {
            addCriterion("seal_name not in", values, "sealName");
            return (Criteria) this;
        }

        public Criteria andSealNameBetween(String value1, String value2) {
            addCriterion("seal_name between", value1, value2, "sealName");
            return (Criteria) this;
        }

        public Criteria andSealNameNotBetween(String value1, String value2) {
            addCriterion("seal_name not between", value1, value2, "sealName");
            return (Criteria) this;
        }

        public Criteria andSealCodeIsNull() {
            addCriterion("seal_code is null");
            return (Criteria) this;
        }

        public Criteria andSealCodeIsNotNull() {
            addCriterion("seal_code is not null");
            return (Criteria) this;
        }

        public Criteria andSealCodeEqualTo(String value) {
            addCriterion("seal_code =", value, "sealCode");
            return (Criteria) this;
        }

        public Criteria andSealCodeNotEqualTo(String value) {
            addCriterion("seal_code <>", value, "sealCode");
            return (Criteria) this;
        }

        public Criteria andSealCodeGreaterThan(String value) {
            addCriterion("seal_code >", value, "sealCode");
            return (Criteria) this;
        }

        public Criteria andSealCodeGreaterThanOrEqualTo(String value) {
            addCriterion("seal_code >=", value, "sealCode");
            return (Criteria) this;
        }

        public Criteria andSealCodeLessThan(String value) {
            addCriterion("seal_code <", value, "sealCode");
            return (Criteria) this;
        }

        public Criteria andSealCodeLessThanOrEqualTo(String value) {
            addCriterion("seal_code <=", value, "sealCode");
            return (Criteria) this;
        }

        public Criteria andSealCodeLike(String value) {
            addCriterion("seal_code like", value, "sealCode");
            return (Criteria) this;
        }

        public Criteria andSealCodeNotLike(String value) {
            addCriterion("seal_code not like", value, "sealCode");
            return (Criteria) this;
        }

        public Criteria andSealCodeIn(List<String> values) {
            addCriterion("seal_code in", values, "sealCode");
            return (Criteria) this;
        }

        public Criteria andSealCodeNotIn(List<String> values) {
            addCriterion("seal_code not in", values, "sealCode");
            return (Criteria) this;
        }

        public Criteria andSealCodeBetween(String value1, String value2) {
            addCriterion("seal_code between", value1, value2, "sealCode");
            return (Criteria) this;
        }

        public Criteria andSealCodeNotBetween(String value1, String value2) {
            addCriterion("seal_code not between", value1, value2, "sealCode");
            return (Criteria) this;
        }

        public Criteria andSealPasswordIsNull() {
            addCriterion("seal_password is null");
            return (Criteria) this;
        }

        public Criteria andSealPasswordIsNotNull() {
            addCriterion("seal_password is not null");
            return (Criteria) this;
        }

        public Criteria andSealPasswordEqualTo(String value) {
            addCriterion("seal_password =", value, "sealPassword");
            return (Criteria) this;
        }

        public Criteria andSealPasswordNotEqualTo(String value) {
            addCriterion("seal_password <>", value, "sealPassword");
            return (Criteria) this;
        }

        public Criteria andSealPasswordGreaterThan(String value) {
            addCriterion("seal_password >", value, "sealPassword");
            return (Criteria) this;
        }

        public Criteria andSealPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("seal_password >=", value, "sealPassword");
            return (Criteria) this;
        }

        public Criteria andSealPasswordLessThan(String value) {
            addCriterion("seal_password <", value, "sealPassword");
            return (Criteria) this;
        }

        public Criteria andSealPasswordLessThanOrEqualTo(String value) {
            addCriterion("seal_password <=", value, "sealPassword");
            return (Criteria) this;
        }

        public Criteria andSealPasswordLike(String value) {
            addCriterion("seal_password like", value, "sealPassword");
            return (Criteria) this;
        }

        public Criteria andSealPasswordNotLike(String value) {
            addCriterion("seal_password not like", value, "sealPassword");
            return (Criteria) this;
        }

        public Criteria andSealPasswordIn(List<String> values) {
            addCriterion("seal_password in", values, "sealPassword");
            return (Criteria) this;
        }

        public Criteria andSealPasswordNotIn(List<String> values) {
            addCriterion("seal_password not in", values, "sealPassword");
            return (Criteria) this;
        }

        public Criteria andSealPasswordBetween(String value1, String value2) {
            addCriterion("seal_password between", value1, value2, "sealPassword");
            return (Criteria) this;
        }

        public Criteria andSealPasswordNotBetween(String value1, String value2) {
            addCriterion("seal_password not between", value1, value2, "sealPassword");
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