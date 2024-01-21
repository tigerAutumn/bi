package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsWaterConservationSignUpExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsWaterConservationSignUpExample() {
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

        public Criteria andActivityIdIsNull() {
            addCriterion("activity_id is null");
            return (Criteria) this;
        }

        public Criteria andActivityIdIsNotNull() {
            addCriterion("activity_id is not null");
            return (Criteria) this;
        }

        public Criteria andActivityIdEqualTo(Integer value) {
            addCriterion("activity_id =", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdNotEqualTo(Integer value) {
            addCriterion("activity_id <>", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdGreaterThan(Integer value) {
            addCriterion("activity_id >", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("activity_id >=", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdLessThan(Integer value) {
            addCriterion("activity_id <", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdLessThanOrEqualTo(Integer value) {
            addCriterion("activity_id <=", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdIn(List<Integer> values) {
            addCriterion("activity_id in", values, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdNotIn(List<Integer> values) {
            addCriterion("activity_id not in", values, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdBetween(Integer value1, Integer value2) {
            addCriterion("activity_id between", value1, value2, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdNotBetween(Integer value1, Integer value2) {
            addCriterion("activity_id not between", value1, value2, "activityId");
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

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andFamilyNumIsNull() {
            addCriterion("family_num is null");
            return (Criteria) this;
        }

        public Criteria andFamilyNumIsNotNull() {
            addCriterion("family_num is not null");
            return (Criteria) this;
        }

        public Criteria andFamilyNumEqualTo(Integer value) {
            addCriterion("family_num =", value, "familyNum");
            return (Criteria) this;
        }

        public Criteria andFamilyNumNotEqualTo(Integer value) {
            addCriterion("family_num <>", value, "familyNum");
            return (Criteria) this;
        }

        public Criteria andFamilyNumGreaterThan(Integer value) {
            addCriterion("family_num >", value, "familyNum");
            return (Criteria) this;
        }

        public Criteria andFamilyNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("family_num >=", value, "familyNum");
            return (Criteria) this;
        }

        public Criteria andFamilyNumLessThan(Integer value) {
            addCriterion("family_num <", value, "familyNum");
            return (Criteria) this;
        }

        public Criteria andFamilyNumLessThanOrEqualTo(Integer value) {
            addCriterion("family_num <=", value, "familyNum");
            return (Criteria) this;
        }

        public Criteria andFamilyNumIn(List<Integer> values) {
            addCriterion("family_num in", values, "familyNum");
            return (Criteria) this;
        }

        public Criteria andFamilyNumNotIn(List<Integer> values) {
            addCriterion("family_num not in", values, "familyNum");
            return (Criteria) this;
        }

        public Criteria andFamilyNumBetween(Integer value1, Integer value2) {
            addCriterion("family_num between", value1, value2, "familyNum");
            return (Criteria) this;
        }

        public Criteria andFamilyNumNotBetween(Integer value1, Integer value2) {
            addCriterion("family_num not between", value1, value2, "familyNum");
            return (Criteria) this;
        }

        public Criteria andMonthWaterRateIsNull() {
            addCriterion("month_water_rate is null");
            return (Criteria) this;
        }

        public Criteria andMonthWaterRateIsNotNull() {
            addCriterion("month_water_rate is not null");
            return (Criteria) this;
        }

        public Criteria andMonthWaterRateEqualTo(Double value) {
            addCriterion("month_water_rate =", value, "monthWaterRate");
            return (Criteria) this;
        }

        public Criteria andMonthWaterRateNotEqualTo(Double value) {
            addCriterion("month_water_rate <>", value, "monthWaterRate");
            return (Criteria) this;
        }

        public Criteria andMonthWaterRateGreaterThan(Double value) {
            addCriterion("month_water_rate >", value, "monthWaterRate");
            return (Criteria) this;
        }

        public Criteria andMonthWaterRateGreaterThanOrEqualTo(Double value) {
            addCriterion("month_water_rate >=", value, "monthWaterRate");
            return (Criteria) this;
        }

        public Criteria andMonthWaterRateLessThan(Double value) {
            addCriterion("month_water_rate <", value, "monthWaterRate");
            return (Criteria) this;
        }

        public Criteria andMonthWaterRateLessThanOrEqualTo(Double value) {
            addCriterion("month_water_rate <=", value, "monthWaterRate");
            return (Criteria) this;
        }

        public Criteria andMonthWaterRateIn(List<Double> values) {
            addCriterion("month_water_rate in", values, "monthWaterRate");
            return (Criteria) this;
        }

        public Criteria andMonthWaterRateNotIn(List<Double> values) {
            addCriterion("month_water_rate not in", values, "monthWaterRate");
            return (Criteria) this;
        }

        public Criteria andMonthWaterRateBetween(Double value1, Double value2) {
            addCriterion("month_water_rate between", value1, value2, "monthWaterRate");
            return (Criteria) this;
        }

        public Criteria andMonthWaterRateNotBetween(Double value1, Double value2) {
            addCriterion("month_water_rate not between", value1, value2, "monthWaterRate");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andWaterSavePhotoIsNull() {
            addCriterion("water_save_photo is null");
            return (Criteria) this;
        }

        public Criteria andWaterSavePhotoIsNotNull() {
            addCriterion("water_save_photo is not null");
            return (Criteria) this;
        }

        public Criteria andWaterSavePhotoEqualTo(String value) {
            addCriterion("water_save_photo =", value, "waterSavePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterSavePhotoNotEqualTo(String value) {
            addCriterion("water_save_photo <>", value, "waterSavePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterSavePhotoGreaterThan(String value) {
            addCriterion("water_save_photo >", value, "waterSavePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterSavePhotoGreaterThanOrEqualTo(String value) {
            addCriterion("water_save_photo >=", value, "waterSavePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterSavePhotoLessThan(String value) {
            addCriterion("water_save_photo <", value, "waterSavePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterSavePhotoLessThanOrEqualTo(String value) {
            addCriterion("water_save_photo <=", value, "waterSavePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterSavePhotoLike(String value) {
            addCriterion("water_save_photo like", value, "waterSavePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterSavePhotoNotLike(String value) {
            addCriterion("water_save_photo not like", value, "waterSavePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterSavePhotoIn(List<String> values) {
            addCriterion("water_save_photo in", values, "waterSavePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterSavePhotoNotIn(List<String> values) {
            addCriterion("water_save_photo not in", values, "waterSavePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterSavePhotoBetween(String value1, String value2) {
            addCriterion("water_save_photo between", value1, value2, "waterSavePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterSavePhotoNotBetween(String value1, String value2) {
            addCriterion("water_save_photo not between", value1, value2, "waterSavePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterRatePhotoIsNull() {
            addCriterion("water_rate_photo is null");
            return (Criteria) this;
        }

        public Criteria andWaterRatePhotoIsNotNull() {
            addCriterion("water_rate_photo is not null");
            return (Criteria) this;
        }

        public Criteria andWaterRatePhotoEqualTo(String value) {
            addCriterion("water_rate_photo =", value, "waterRatePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterRatePhotoNotEqualTo(String value) {
            addCriterion("water_rate_photo <>", value, "waterRatePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterRatePhotoGreaterThan(String value) {
            addCriterion("water_rate_photo >", value, "waterRatePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterRatePhotoGreaterThanOrEqualTo(String value) {
            addCriterion("water_rate_photo >=", value, "waterRatePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterRatePhotoLessThan(String value) {
            addCriterion("water_rate_photo <", value, "waterRatePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterRatePhotoLessThanOrEqualTo(String value) {
            addCriterion("water_rate_photo <=", value, "waterRatePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterRatePhotoLike(String value) {
            addCriterion("water_rate_photo like", value, "waterRatePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterRatePhotoNotLike(String value) {
            addCriterion("water_rate_photo not like", value, "waterRatePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterRatePhotoIn(List<String> values) {
            addCriterion("water_rate_photo in", values, "waterRatePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterRatePhotoNotIn(List<String> values) {
            addCriterion("water_rate_photo not in", values, "waterRatePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterRatePhotoBetween(String value1, String value2) {
            addCriterion("water_rate_photo between", value1, value2, "waterRatePhoto");
            return (Criteria) this;
        }

        public Criteria andWaterRatePhotoNotBetween(String value1, String value2) {
            addCriterion("water_rate_photo not between", value1, value2, "waterRatePhoto");
            return (Criteria) this;
        }

        public Criteria andVoteNumIsNull() {
            addCriterion("vote_num is null");
            return (Criteria) this;
        }

        public Criteria andVoteNumIsNotNull() {
            addCriterion("vote_num is not null");
            return (Criteria) this;
        }

        public Criteria andVoteNumEqualTo(Integer value) {
            addCriterion("vote_num =", value, "voteNum");
            return (Criteria) this;
        }

        public Criteria andVoteNumNotEqualTo(Integer value) {
            addCriterion("vote_num <>", value, "voteNum");
            return (Criteria) this;
        }

        public Criteria andVoteNumGreaterThan(Integer value) {
            addCriterion("vote_num >", value, "voteNum");
            return (Criteria) this;
        }

        public Criteria andVoteNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("vote_num >=", value, "voteNum");
            return (Criteria) this;
        }

        public Criteria andVoteNumLessThan(Integer value) {
            addCriterion("vote_num <", value, "voteNum");
            return (Criteria) this;
        }

        public Criteria andVoteNumLessThanOrEqualTo(Integer value) {
            addCriterion("vote_num <=", value, "voteNum");
            return (Criteria) this;
        }

        public Criteria andVoteNumIn(List<Integer> values) {
            addCriterion("vote_num in", values, "voteNum");
            return (Criteria) this;
        }

        public Criteria andVoteNumNotIn(List<Integer> values) {
            addCriterion("vote_num not in", values, "voteNum");
            return (Criteria) this;
        }

        public Criteria andVoteNumBetween(Integer value1, Integer value2) {
            addCriterion("vote_num between", value1, value2, "voteNum");
            return (Criteria) this;
        }

        public Criteria andVoteNumNotBetween(Integer value1, Integer value2) {
            addCriterion("vote_num not between", value1, value2, "voteNum");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNull() {
            addCriterion("check_status is null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNotNull() {
            addCriterion("check_status is not null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusEqualTo(String value) {
            addCriterion("check_status =", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotEqualTo(String value) {
            addCriterion("check_status <>", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThan(String value) {
            addCriterion("check_status >", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThanOrEqualTo(String value) {
            addCriterion("check_status >=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThan(String value) {
            addCriterion("check_status <", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThanOrEqualTo(String value) {
            addCriterion("check_status <=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLike(String value) {
            addCriterion("check_status like", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotLike(String value) {
            addCriterion("check_status not like", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIn(List<String> values) {
            addCriterion("check_status in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotIn(List<String> values) {
            addCriterion("check_status not in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusBetween(String value1, String value2) {
            addCriterion("check_status between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotBetween(String value1, String value2) {
            addCriterion("check_status not between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdIsNull() {
            addCriterion("check_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdIsNotNull() {
            addCriterion("check_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdEqualTo(Integer value) {
            addCriterion("check_user_id =", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdNotEqualTo(Integer value) {
            addCriterion("check_user_id <>", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdGreaterThan(Integer value) {
            addCriterion("check_user_id >", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_user_id >=", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdLessThan(Integer value) {
            addCriterion("check_user_id <", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("check_user_id <=", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdIn(List<Integer> values) {
            addCriterion("check_user_id in", values, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdNotIn(List<Integer> values) {
            addCriterion("check_user_id not in", values, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdBetween(Integer value1, Integer value2) {
            addCriterion("check_user_id between", value1, value2, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("check_user_id not between", value1, value2, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIsNull() {
            addCriterion("check_time is null");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIsNotNull() {
            addCriterion("check_time is not null");
            return (Criteria) this;
        }

        public Criteria andCheckTimeEqualTo(Date value) {
            addCriterion("check_time =", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotEqualTo(Date value) {
            addCriterion("check_time <>", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeGreaterThan(Date value) {
            addCriterion("check_time >", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("check_time >=", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeLessThan(Date value) {
            addCriterion("check_time <", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeLessThanOrEqualTo(Date value) {
            addCriterion("check_time <=", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIn(List<Date> values) {
            addCriterion("check_time in", values, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotIn(List<Date> values) {
            addCriterion("check_time not in", values, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeBetween(Date value1, Date value2) {
            addCriterion("check_time between", value1, value2, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotBetween(Date value1, Date value2) {
            addCriterion("check_time not between", value1, value2, "checkTime");
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