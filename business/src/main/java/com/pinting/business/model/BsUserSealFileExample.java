package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsUserSealFileExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsUserSealFileExample() {
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

        public Criteria andUserSrcIsNull() {
            addCriterion("user_src is null");
            return (Criteria) this;
        }

        public Criteria andUserSrcIsNotNull() {
            addCriterion("user_src is not null");
            return (Criteria) this;
        }

        public Criteria andUserSrcEqualTo(String value) {
            addCriterion("user_src =", value, "userSrc");
            return (Criteria) this;
        }

        public Criteria andUserSrcNotEqualTo(String value) {
            addCriterion("user_src <>", value, "userSrc");
            return (Criteria) this;
        }

        public Criteria andUserSrcGreaterThan(String value) {
            addCriterion("user_src >", value, "userSrc");
            return (Criteria) this;
        }

        public Criteria andUserSrcGreaterThanOrEqualTo(String value) {
            addCriterion("user_src >=", value, "userSrc");
            return (Criteria) this;
        }

        public Criteria andUserSrcLessThan(String value) {
            addCriterion("user_src <", value, "userSrc");
            return (Criteria) this;
        }

        public Criteria andUserSrcLessThanOrEqualTo(String value) {
            addCriterion("user_src <=", value, "userSrc");
            return (Criteria) this;
        }

        public Criteria andUserSrcLike(String value) {
            addCriterion("user_src like", value, "userSrc");
            return (Criteria) this;
        }

        public Criteria andUserSrcNotLike(String value) {
            addCriterion("user_src not like", value, "userSrc");
            return (Criteria) this;
        }

        public Criteria andUserSrcIn(List<String> values) {
            addCriterion("user_src in", values, "userSrc");
            return (Criteria) this;
        }

        public Criteria andUserSrcNotIn(List<String> values) {
            addCriterion("user_src not in", values, "userSrc");
            return (Criteria) this;
        }

        public Criteria andUserSrcBetween(String value1, String value2) {
            addCriterion("user_src between", value1, value2, "userSrc");
            return (Criteria) this;
        }

        public Criteria andUserSrcNotBetween(String value1, String value2) {
            addCriterion("user_src not between", value1, value2, "userSrc");
            return (Criteria) this;
        }

        public Criteria andAgreementNoIsNull() {
            addCriterion("agreement_no is null");
            return (Criteria) this;
        }

        public Criteria andAgreementNoIsNotNull() {
            addCriterion("agreement_no is not null");
            return (Criteria) this;
        }

        public Criteria andAgreementNoEqualTo(String value) {
            addCriterion("agreement_no =", value, "agreementNo");
            return (Criteria) this;
        }

        public Criteria andAgreementNoNotEqualTo(String value) {
            addCriterion("agreement_no <>", value, "agreementNo");
            return (Criteria) this;
        }

        public Criteria andAgreementNoGreaterThan(String value) {
            addCriterion("agreement_no >", value, "agreementNo");
            return (Criteria) this;
        }

        public Criteria andAgreementNoGreaterThanOrEqualTo(String value) {
            addCriterion("agreement_no >=", value, "agreementNo");
            return (Criteria) this;
        }

        public Criteria andAgreementNoLessThan(String value) {
            addCriterion("agreement_no <", value, "agreementNo");
            return (Criteria) this;
        }

        public Criteria andAgreementNoLessThanOrEqualTo(String value) {
            addCriterion("agreement_no <=", value, "agreementNo");
            return (Criteria) this;
        }

        public Criteria andAgreementNoLike(String value) {
            addCriterion("agreement_no like", value, "agreementNo");
            return (Criteria) this;
        }

        public Criteria andAgreementNoNotLike(String value) {
            addCriterion("agreement_no not like", value, "agreementNo");
            return (Criteria) this;
        }

        public Criteria andAgreementNoIn(List<String> values) {
            addCriterion("agreement_no in", values, "agreementNo");
            return (Criteria) this;
        }

        public Criteria andAgreementNoNotIn(List<String> values) {
            addCriterion("agreement_no not in", values, "agreementNo");
            return (Criteria) this;
        }

        public Criteria andAgreementNoBetween(String value1, String value2) {
            addCriterion("agreement_no between", value1, value2, "agreementNo");
            return (Criteria) this;
        }

        public Criteria andAgreementNoNotBetween(String value1, String value2) {
            addCriterion("agreement_no not between", value1, value2, "agreementNo");
            return (Criteria) this;
        }

        public Criteria andSrcAddressIsNull() {
            addCriterion("src_address is null");
            return (Criteria) this;
        }

        public Criteria andSrcAddressIsNotNull() {
            addCriterion("src_address is not null");
            return (Criteria) this;
        }

        public Criteria andSrcAddressEqualTo(String value) {
            addCriterion("src_address =", value, "srcAddress");
            return (Criteria) this;
        }

        public Criteria andSrcAddressNotEqualTo(String value) {
            addCriterion("src_address <>", value, "srcAddress");
            return (Criteria) this;
        }

        public Criteria andSrcAddressGreaterThan(String value) {
            addCriterion("src_address >", value, "srcAddress");
            return (Criteria) this;
        }

        public Criteria andSrcAddressGreaterThanOrEqualTo(String value) {
            addCriterion("src_address >=", value, "srcAddress");
            return (Criteria) this;
        }

        public Criteria andSrcAddressLessThan(String value) {
            addCriterion("src_address <", value, "srcAddress");
            return (Criteria) this;
        }

        public Criteria andSrcAddressLessThanOrEqualTo(String value) {
            addCriterion("src_address <=", value, "srcAddress");
            return (Criteria) this;
        }

        public Criteria andSrcAddressLike(String value) {
            addCriterion("src_address like", value, "srcAddress");
            return (Criteria) this;
        }

        public Criteria andSrcAddressNotLike(String value) {
            addCriterion("src_address not like", value, "srcAddress");
            return (Criteria) this;
        }

        public Criteria andSrcAddressIn(List<String> values) {
            addCriterion("src_address in", values, "srcAddress");
            return (Criteria) this;
        }

        public Criteria andSrcAddressNotIn(List<String> values) {
            addCriterion("src_address not in", values, "srcAddress");
            return (Criteria) this;
        }

        public Criteria andSrcAddressBetween(String value1, String value2) {
            addCriterion("src_address between", value1, value2, "srcAddress");
            return (Criteria) this;
        }

        public Criteria andSrcAddressNotBetween(String value1, String value2) {
            addCriterion("src_address not between", value1, value2, "srcAddress");
            return (Criteria) this;
        }

        public Criteria andFileAddressIsNull() {
            addCriterion("file_address is null");
            return (Criteria) this;
        }

        public Criteria andFileAddressIsNotNull() {
            addCriterion("file_address is not null");
            return (Criteria) this;
        }

        public Criteria andFileAddressEqualTo(String value) {
            addCriterion("file_address =", value, "fileAddress");
            return (Criteria) this;
        }

        public Criteria andFileAddressNotEqualTo(String value) {
            addCriterion("file_address <>", value, "fileAddress");
            return (Criteria) this;
        }

        public Criteria andFileAddressGreaterThan(String value) {
            addCriterion("file_address >", value, "fileAddress");
            return (Criteria) this;
        }

        public Criteria andFileAddressGreaterThanOrEqualTo(String value) {
            addCriterion("file_address >=", value, "fileAddress");
            return (Criteria) this;
        }

        public Criteria andFileAddressLessThan(String value) {
            addCriterion("file_address <", value, "fileAddress");
            return (Criteria) this;
        }

        public Criteria andFileAddressLessThanOrEqualTo(String value) {
            addCriterion("file_address <=", value, "fileAddress");
            return (Criteria) this;
        }

        public Criteria andFileAddressLike(String value) {
            addCriterion("file_address like", value, "fileAddress");
            return (Criteria) this;
        }

        public Criteria andFileAddressNotLike(String value) {
            addCriterion("file_address not like", value, "fileAddress");
            return (Criteria) this;
        }

        public Criteria andFileAddressIn(List<String> values) {
            addCriterion("file_address in", values, "fileAddress");
            return (Criteria) this;
        }

        public Criteria andFileAddressNotIn(List<String> values) {
            addCriterion("file_address not in", values, "fileAddress");
            return (Criteria) this;
        }

        public Criteria andFileAddressBetween(String value1, String value2) {
            addCriterion("file_address between", value1, value2, "fileAddress");
            return (Criteria) this;
        }

        public Criteria andFileAddressNotBetween(String value1, String value2) {
            addCriterion("file_address not between", value1, value2, "fileAddress");
            return (Criteria) this;
        }

        public Criteria andSealTypeIsNull() {
            addCriterion("seal_type is null");
            return (Criteria) this;
        }

        public Criteria andSealTypeIsNotNull() {
            addCriterion("seal_type is not null");
            return (Criteria) this;
        }

        public Criteria andSealTypeEqualTo(String value) {
            addCriterion("seal_type =", value, "sealType");
            return (Criteria) this;
        }

        public Criteria andSealTypeNotEqualTo(String value) {
            addCriterion("seal_type <>", value, "sealType");
            return (Criteria) this;
        }

        public Criteria andSealTypeGreaterThan(String value) {
            addCriterion("seal_type >", value, "sealType");
            return (Criteria) this;
        }

        public Criteria andSealTypeGreaterThanOrEqualTo(String value) {
            addCriterion("seal_type >=", value, "sealType");
            return (Criteria) this;
        }

        public Criteria andSealTypeLessThan(String value) {
            addCriterion("seal_type <", value, "sealType");
            return (Criteria) this;
        }

        public Criteria andSealTypeLessThanOrEqualTo(String value) {
            addCriterion("seal_type <=", value, "sealType");
            return (Criteria) this;
        }

        public Criteria andSealTypeLike(String value) {
            addCriterion("seal_type like", value, "sealType");
            return (Criteria) this;
        }

        public Criteria andSealTypeNotLike(String value) {
            addCriterion("seal_type not like", value, "sealType");
            return (Criteria) this;
        }

        public Criteria andSealTypeIn(List<String> values) {
            addCriterion("seal_type in", values, "sealType");
            return (Criteria) this;
        }

        public Criteria andSealTypeNotIn(List<String> values) {
            addCriterion("seal_type not in", values, "sealType");
            return (Criteria) this;
        }

        public Criteria andSealTypeBetween(String value1, String value2) {
            addCriterion("seal_type between", value1, value2, "sealType");
            return (Criteria) this;
        }

        public Criteria andSealTypeNotBetween(String value1, String value2) {
            addCriterion("seal_type not between", value1, value2, "sealType");
            return (Criteria) this;
        }

        public Criteria andRelativeInfoIsNull() {
            addCriterion("relative_info is null");
            return (Criteria) this;
        }

        public Criteria andRelativeInfoIsNotNull() {
            addCriterion("relative_info is not null");
            return (Criteria) this;
        }

        public Criteria andRelativeInfoEqualTo(String value) {
            addCriterion("relative_info =", value, "relativeInfo");
            return (Criteria) this;
        }

        public Criteria andRelativeInfoNotEqualTo(String value) {
            addCriterion("relative_info <>", value, "relativeInfo");
            return (Criteria) this;
        }

        public Criteria andRelativeInfoGreaterThan(String value) {
            addCriterion("relative_info >", value, "relativeInfo");
            return (Criteria) this;
        }

        public Criteria andRelativeInfoGreaterThanOrEqualTo(String value) {
            addCriterion("relative_info >=", value, "relativeInfo");
            return (Criteria) this;
        }

        public Criteria andRelativeInfoLessThan(String value) {
            addCriterion("relative_info <", value, "relativeInfo");
            return (Criteria) this;
        }

        public Criteria andRelativeInfoLessThanOrEqualTo(String value) {
            addCriterion("relative_info <=", value, "relativeInfo");
            return (Criteria) this;
        }

        public Criteria andRelativeInfoLike(String value) {
            addCriterion("relative_info like", value, "relativeInfo");
            return (Criteria) this;
        }

        public Criteria andRelativeInfoNotLike(String value) {
            addCriterion("relative_info not like", value, "relativeInfo");
            return (Criteria) this;
        }

        public Criteria andRelativeInfoIn(List<String> values) {
            addCriterion("relative_info in", values, "relativeInfo");
            return (Criteria) this;
        }

        public Criteria andRelativeInfoNotIn(List<String> values) {
            addCriterion("relative_info not in", values, "relativeInfo");
            return (Criteria) this;
        }

        public Criteria andRelativeInfoBetween(String value1, String value2) {
            addCriterion("relative_info between", value1, value2, "relativeInfo");
            return (Criteria) this;
        }

        public Criteria andRelativeInfoNotBetween(String value1, String value2) {
            addCriterion("relative_info not between", value1, value2, "relativeInfo");
            return (Criteria) this;
        }

        public Criteria andSealStatusIsNull() {
            addCriterion("seal_status is null");
            return (Criteria) this;
        }

        public Criteria andSealStatusIsNotNull() {
            addCriterion("seal_status is not null");
            return (Criteria) this;
        }

        public Criteria andSealStatusEqualTo(String value) {
            addCriterion("seal_status =", value, "sealStatus");
            return (Criteria) this;
        }

        public Criteria andSealStatusNotEqualTo(String value) {
            addCriterion("seal_status <>", value, "sealStatus");
            return (Criteria) this;
        }

        public Criteria andSealStatusGreaterThan(String value) {
            addCriterion("seal_status >", value, "sealStatus");
            return (Criteria) this;
        }

        public Criteria andSealStatusGreaterThanOrEqualTo(String value) {
            addCriterion("seal_status >=", value, "sealStatus");
            return (Criteria) this;
        }

        public Criteria andSealStatusLessThan(String value) {
            addCriterion("seal_status <", value, "sealStatus");
            return (Criteria) this;
        }

        public Criteria andSealStatusLessThanOrEqualTo(String value) {
            addCriterion("seal_status <=", value, "sealStatus");
            return (Criteria) this;
        }

        public Criteria andSealStatusLike(String value) {
            addCriterion("seal_status like", value, "sealStatus");
            return (Criteria) this;
        }

        public Criteria andSealStatusNotLike(String value) {
            addCriterion("seal_status not like", value, "sealStatus");
            return (Criteria) this;
        }

        public Criteria andSealStatusIn(List<String> values) {
            addCriterion("seal_status in", values, "sealStatus");
            return (Criteria) this;
        }

        public Criteria andSealStatusNotIn(List<String> values) {
            addCriterion("seal_status not in", values, "sealStatus");
            return (Criteria) this;
        }

        public Criteria andSealStatusBetween(String value1, String value2) {
            addCriterion("seal_status between", value1, value2, "sealStatus");
            return (Criteria) this;
        }

        public Criteria andSealStatusNotBetween(String value1, String value2) {
            addCriterion("seal_status not between", value1, value2, "sealStatus");
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