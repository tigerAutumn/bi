package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsUserPoliceVerifyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsUserPoliceVerifyExample() {
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

        public Criteria andBusinessTypeIsNull() {
            addCriterion("business_type is null");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeIsNotNull() {
            addCriterion("business_type is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeEqualTo(String value) {
            addCriterion("business_type =", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeNotEqualTo(String value) {
            addCriterion("business_type <>", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeGreaterThan(String value) {
            addCriterion("business_type >", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeGreaterThanOrEqualTo(String value) {
            addCriterion("business_type >=", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeLessThan(String value) {
            addCriterion("business_type <", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeLessThanOrEqualTo(String value) {
            addCriterion("business_type <=", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeLike(String value) {
            addCriterion("business_type like", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeNotLike(String value) {
            addCriterion("business_type not like", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeIn(List<String> values) {
            addCriterion("business_type in", values, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeNotIn(List<String> values) {
            addCriterion("business_type not in", values, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeBetween(String value1, String value2) {
            addCriterion("business_type between", value1, value2, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeNotBetween(String value1, String value2) {
            addCriterion("business_type not between", value1, value2, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessIdIsNull() {
            addCriterion("business_id is null");
            return (Criteria) this;
        }

        public Criteria andBusinessIdIsNotNull() {
            addCriterion("business_id is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessIdEqualTo(Integer value) {
            addCriterion("business_id =", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdNotEqualTo(Integer value) {
            addCriterion("business_id <>", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdGreaterThan(Integer value) {
            addCriterion("business_id >", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("business_id >=", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdLessThan(Integer value) {
            addCriterion("business_id <", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdLessThanOrEqualTo(Integer value) {
            addCriterion("business_id <=", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdIn(List<Integer> values) {
            addCriterion("business_id in", values, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdNotIn(List<Integer> values) {
            addCriterion("business_id not in", values, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdBetween(Integer value1, Integer value2) {
            addCriterion("business_id between", value1, value2, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdNotBetween(Integer value1, Integer value2) {
            addCriterion("business_id not between", value1, value2, "businessId");
            return (Criteria) this;
        }

        public Criteria andVerifyResultIsNull() {
            addCriterion("verify_result is null");
            return (Criteria) this;
        }

        public Criteria andVerifyResultIsNotNull() {
            addCriterion("verify_result is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyResultEqualTo(String value) {
            addCriterion("verify_result =", value, "verifyResult");
            return (Criteria) this;
        }

        public Criteria andVerifyResultNotEqualTo(String value) {
            addCriterion("verify_result <>", value, "verifyResult");
            return (Criteria) this;
        }

        public Criteria andVerifyResultGreaterThan(String value) {
            addCriterion("verify_result >", value, "verifyResult");
            return (Criteria) this;
        }

        public Criteria andVerifyResultGreaterThanOrEqualTo(String value) {
            addCriterion("verify_result >=", value, "verifyResult");
            return (Criteria) this;
        }

        public Criteria andVerifyResultLessThan(String value) {
            addCriterion("verify_result <", value, "verifyResult");
            return (Criteria) this;
        }

        public Criteria andVerifyResultLessThanOrEqualTo(String value) {
            addCriterion("verify_result <=", value, "verifyResult");
            return (Criteria) this;
        }

        public Criteria andVerifyResultLike(String value) {
            addCriterion("verify_result like", value, "verifyResult");
            return (Criteria) this;
        }

        public Criteria andVerifyResultNotLike(String value) {
            addCriterion("verify_result not like", value, "verifyResult");
            return (Criteria) this;
        }

        public Criteria andVerifyResultIn(List<String> values) {
            addCriterion("verify_result in", values, "verifyResult");
            return (Criteria) this;
        }

        public Criteria andVerifyResultNotIn(List<String> values) {
            addCriterion("verify_result not in", values, "verifyResult");
            return (Criteria) this;
        }

        public Criteria andVerifyResultBetween(String value1, String value2) {
            addCriterion("verify_result between", value1, value2, "verifyResult");
            return (Criteria) this;
        }

        public Criteria andVerifyResultNotBetween(String value1, String value2) {
            addCriterion("verify_result not between", value1, value2, "verifyResult");
            return (Criteria) this;
        }

        public Criteria andScoreIsNull() {
            addCriterion("score is null");
            return (Criteria) this;
        }

        public Criteria andScoreIsNotNull() {
            addCriterion("score is not null");
            return (Criteria) this;
        }

        public Criteria andScoreEqualTo(Integer value) {
            addCriterion("score =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(Integer value) {
            addCriterion("score <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(Integer value) {
            addCriterion("score >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("score >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(Integer value) {
            addCriterion("score <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(Integer value) {
            addCriterion("score <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<Integer> values) {
            addCriterion("score in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<Integer> values) {
            addCriterion("score not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(Integer value1, Integer value2) {
            addCriterion("score between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("score not between", value1, value2, "score");
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

        public Criteria andIdCardFrontPicIsNull() {
            addCriterion("id_card_front_pic is null");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontPicIsNotNull() {
            addCriterion("id_card_front_pic is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontPicEqualTo(String value) {
            addCriterion("id_card_front_pic =", value, "idCardFrontPic");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontPicNotEqualTo(String value) {
            addCriterion("id_card_front_pic <>", value, "idCardFrontPic");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontPicGreaterThan(String value) {
            addCriterion("id_card_front_pic >", value, "idCardFrontPic");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontPicGreaterThanOrEqualTo(String value) {
            addCriterion("id_card_front_pic >=", value, "idCardFrontPic");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontPicLessThan(String value) {
            addCriterion("id_card_front_pic <", value, "idCardFrontPic");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontPicLessThanOrEqualTo(String value) {
            addCriterion("id_card_front_pic <=", value, "idCardFrontPic");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontPicLike(String value) {
            addCriterion("id_card_front_pic like", value, "idCardFrontPic");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontPicNotLike(String value) {
            addCriterion("id_card_front_pic not like", value, "idCardFrontPic");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontPicIn(List<String> values) {
            addCriterion("id_card_front_pic in", values, "idCardFrontPic");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontPicNotIn(List<String> values) {
            addCriterion("id_card_front_pic not in", values, "idCardFrontPic");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontPicBetween(String value1, String value2) {
            addCriterion("id_card_front_pic between", value1, value2, "idCardFrontPic");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontPicNotBetween(String value1, String value2) {
            addCriterion("id_card_front_pic not between", value1, value2, "idCardFrontPic");
            return (Criteria) this;
        }

        public Criteria andIdCardBackPicIsNull() {
            addCriterion("id_card_back_pic is null");
            return (Criteria) this;
        }

        public Criteria andIdCardBackPicIsNotNull() {
            addCriterion("id_card_back_pic is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardBackPicEqualTo(String value) {
            addCriterion("id_card_back_pic =", value, "idCardBackPic");
            return (Criteria) this;
        }

        public Criteria andIdCardBackPicNotEqualTo(String value) {
            addCriterion("id_card_back_pic <>", value, "idCardBackPic");
            return (Criteria) this;
        }

        public Criteria andIdCardBackPicGreaterThan(String value) {
            addCriterion("id_card_back_pic >", value, "idCardBackPic");
            return (Criteria) this;
        }

        public Criteria andIdCardBackPicGreaterThanOrEqualTo(String value) {
            addCriterion("id_card_back_pic >=", value, "idCardBackPic");
            return (Criteria) this;
        }

        public Criteria andIdCardBackPicLessThan(String value) {
            addCriterion("id_card_back_pic <", value, "idCardBackPic");
            return (Criteria) this;
        }

        public Criteria andIdCardBackPicLessThanOrEqualTo(String value) {
            addCriterion("id_card_back_pic <=", value, "idCardBackPic");
            return (Criteria) this;
        }

        public Criteria andIdCardBackPicLike(String value) {
            addCriterion("id_card_back_pic like", value, "idCardBackPic");
            return (Criteria) this;
        }

        public Criteria andIdCardBackPicNotLike(String value) {
            addCriterion("id_card_back_pic not like", value, "idCardBackPic");
            return (Criteria) this;
        }

        public Criteria andIdCardBackPicIn(List<String> values) {
            addCriterion("id_card_back_pic in", values, "idCardBackPic");
            return (Criteria) this;
        }

        public Criteria andIdCardBackPicNotIn(List<String> values) {
            addCriterion("id_card_back_pic not in", values, "idCardBackPic");
            return (Criteria) this;
        }

        public Criteria andIdCardBackPicBetween(String value1, String value2) {
            addCriterion("id_card_back_pic between", value1, value2, "idCardBackPic");
            return (Criteria) this;
        }

        public Criteria andIdCardBackPicNotBetween(String value1, String value2) {
            addCriterion("id_card_back_pic not between", value1, value2, "idCardBackPic");
            return (Criteria) this;
        }

        public Criteria andLivenessPicBlinkIsNull() {
            addCriterion("liveness_pic_blink is null");
            return (Criteria) this;
        }

        public Criteria andLivenessPicBlinkIsNotNull() {
            addCriterion("liveness_pic_blink is not null");
            return (Criteria) this;
        }

        public Criteria andLivenessPicBlinkEqualTo(String value) {
            addCriterion("liveness_pic_blink =", value, "livenessPicBlink");
            return (Criteria) this;
        }

        public Criteria andLivenessPicBlinkNotEqualTo(String value) {
            addCriterion("liveness_pic_blink <>", value, "livenessPicBlink");
            return (Criteria) this;
        }

        public Criteria andLivenessPicBlinkGreaterThan(String value) {
            addCriterion("liveness_pic_blink >", value, "livenessPicBlink");
            return (Criteria) this;
        }

        public Criteria andLivenessPicBlinkGreaterThanOrEqualTo(String value) {
            addCriterion("liveness_pic_blink >=", value, "livenessPicBlink");
            return (Criteria) this;
        }

        public Criteria andLivenessPicBlinkLessThan(String value) {
            addCriterion("liveness_pic_blink <", value, "livenessPicBlink");
            return (Criteria) this;
        }

        public Criteria andLivenessPicBlinkLessThanOrEqualTo(String value) {
            addCriterion("liveness_pic_blink <=", value, "livenessPicBlink");
            return (Criteria) this;
        }

        public Criteria andLivenessPicBlinkLike(String value) {
            addCriterion("liveness_pic_blink like", value, "livenessPicBlink");
            return (Criteria) this;
        }

        public Criteria andLivenessPicBlinkNotLike(String value) {
            addCriterion("liveness_pic_blink not like", value, "livenessPicBlink");
            return (Criteria) this;
        }

        public Criteria andLivenessPicBlinkIn(List<String> values) {
            addCriterion("liveness_pic_blink in", values, "livenessPicBlink");
            return (Criteria) this;
        }

        public Criteria andLivenessPicBlinkNotIn(List<String> values) {
            addCriterion("liveness_pic_blink not in", values, "livenessPicBlink");
            return (Criteria) this;
        }

        public Criteria andLivenessPicBlinkBetween(String value1, String value2) {
            addCriterion("liveness_pic_blink between", value1, value2, "livenessPicBlink");
            return (Criteria) this;
        }

        public Criteria andLivenessPicBlinkNotBetween(String value1, String value2) {
            addCriterion("liveness_pic_blink not between", value1, value2, "livenessPicBlink");
            return (Criteria) this;
        }

        public Criteria andLivenessPicSayIsNull() {
            addCriterion("liveness_pic_say is null");
            return (Criteria) this;
        }

        public Criteria andLivenessPicSayIsNotNull() {
            addCriterion("liveness_pic_say is not null");
            return (Criteria) this;
        }

        public Criteria andLivenessPicSayEqualTo(String value) {
            addCriterion("liveness_pic_say =", value, "livenessPicSay");
            return (Criteria) this;
        }

        public Criteria andLivenessPicSayNotEqualTo(String value) {
            addCriterion("liveness_pic_say <>", value, "livenessPicSay");
            return (Criteria) this;
        }

        public Criteria andLivenessPicSayGreaterThan(String value) {
            addCriterion("liveness_pic_say >", value, "livenessPicSay");
            return (Criteria) this;
        }

        public Criteria andLivenessPicSayGreaterThanOrEqualTo(String value) {
            addCriterion("liveness_pic_say >=", value, "livenessPicSay");
            return (Criteria) this;
        }

        public Criteria andLivenessPicSayLessThan(String value) {
            addCriterion("liveness_pic_say <", value, "livenessPicSay");
            return (Criteria) this;
        }

        public Criteria andLivenessPicSayLessThanOrEqualTo(String value) {
            addCriterion("liveness_pic_say <=", value, "livenessPicSay");
            return (Criteria) this;
        }

        public Criteria andLivenessPicSayLike(String value) {
            addCriterion("liveness_pic_say like", value, "livenessPicSay");
            return (Criteria) this;
        }

        public Criteria andLivenessPicSayNotLike(String value) {
            addCriterion("liveness_pic_say not like", value, "livenessPicSay");
            return (Criteria) this;
        }

        public Criteria andLivenessPicSayIn(List<String> values) {
            addCriterion("liveness_pic_say in", values, "livenessPicSay");
            return (Criteria) this;
        }

        public Criteria andLivenessPicSayNotIn(List<String> values) {
            addCriterion("liveness_pic_say not in", values, "livenessPicSay");
            return (Criteria) this;
        }

        public Criteria andLivenessPicSayBetween(String value1, String value2) {
            addCriterion("liveness_pic_say between", value1, value2, "livenessPicSay");
            return (Criteria) this;
        }

        public Criteria andLivenessPicSayNotBetween(String value1, String value2) {
            addCriterion("liveness_pic_say not between", value1, value2, "livenessPicSay");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRightHeadIsNull() {
            addCriterion("liveness_pic_right_head is null");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRightHeadIsNotNull() {
            addCriterion("liveness_pic_right_head is not null");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRightHeadEqualTo(String value) {
            addCriterion("liveness_pic_right_head =", value, "livenessPicRightHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRightHeadNotEqualTo(String value) {
            addCriterion("liveness_pic_right_head <>", value, "livenessPicRightHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRightHeadGreaterThan(String value) {
            addCriterion("liveness_pic_right_head >", value, "livenessPicRightHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRightHeadGreaterThanOrEqualTo(String value) {
            addCriterion("liveness_pic_right_head >=", value, "livenessPicRightHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRightHeadLessThan(String value) {
            addCriterion("liveness_pic_right_head <", value, "livenessPicRightHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRightHeadLessThanOrEqualTo(String value) {
            addCriterion("liveness_pic_right_head <=", value, "livenessPicRightHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRightHeadLike(String value) {
            addCriterion("liveness_pic_right_head like", value, "livenessPicRightHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRightHeadNotLike(String value) {
            addCriterion("liveness_pic_right_head not like", value, "livenessPicRightHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRightHeadIn(List<String> values) {
            addCriterion("liveness_pic_right_head in", values, "livenessPicRightHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRightHeadNotIn(List<String> values) {
            addCriterion("liveness_pic_right_head not in", values, "livenessPicRightHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRightHeadBetween(String value1, String value2) {
            addCriterion("liveness_pic_right_head between", value1, value2, "livenessPicRightHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRightHeadNotBetween(String value1, String value2) {
            addCriterion("liveness_pic_right_head not between", value1, value2, "livenessPicRightHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicLeftHeadIsNull() {
            addCriterion("liveness_pic_left_head is null");
            return (Criteria) this;
        }

        public Criteria andLivenessPicLeftHeadIsNotNull() {
            addCriterion("liveness_pic_left_head is not null");
            return (Criteria) this;
        }

        public Criteria andLivenessPicLeftHeadEqualTo(String value) {
            addCriterion("liveness_pic_left_head =", value, "livenessPicLeftHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicLeftHeadNotEqualTo(String value) {
            addCriterion("liveness_pic_left_head <>", value, "livenessPicLeftHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicLeftHeadGreaterThan(String value) {
            addCriterion("liveness_pic_left_head >", value, "livenessPicLeftHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicLeftHeadGreaterThanOrEqualTo(String value) {
            addCriterion("liveness_pic_left_head >=", value, "livenessPicLeftHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicLeftHeadLessThan(String value) {
            addCriterion("liveness_pic_left_head <", value, "livenessPicLeftHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicLeftHeadLessThanOrEqualTo(String value) {
            addCriterion("liveness_pic_left_head <=", value, "livenessPicLeftHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicLeftHeadLike(String value) {
            addCriterion("liveness_pic_left_head like", value, "livenessPicLeftHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicLeftHeadNotLike(String value) {
            addCriterion("liveness_pic_left_head not like", value, "livenessPicLeftHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicLeftHeadIn(List<String> values) {
            addCriterion("liveness_pic_left_head in", values, "livenessPicLeftHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicLeftHeadNotIn(List<String> values) {
            addCriterion("liveness_pic_left_head not in", values, "livenessPicLeftHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicLeftHeadBetween(String value1, String value2) {
            addCriterion("liveness_pic_left_head between", value1, value2, "livenessPicLeftHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicLeftHeadNotBetween(String value1, String value2) {
            addCriterion("liveness_pic_left_head not between", value1, value2, "livenessPicLeftHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRaiseHeadIsNull() {
            addCriterion("liveness_pic_raise_head is null");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRaiseHeadIsNotNull() {
            addCriterion("liveness_pic_raise_head is not null");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRaiseHeadEqualTo(String value) {
            addCriterion("liveness_pic_raise_head =", value, "livenessPicRaiseHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRaiseHeadNotEqualTo(String value) {
            addCriterion("liveness_pic_raise_head <>", value, "livenessPicRaiseHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRaiseHeadGreaterThan(String value) {
            addCriterion("liveness_pic_raise_head >", value, "livenessPicRaiseHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRaiseHeadGreaterThanOrEqualTo(String value) {
            addCriterion("liveness_pic_raise_head >=", value, "livenessPicRaiseHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRaiseHeadLessThan(String value) {
            addCriterion("liveness_pic_raise_head <", value, "livenessPicRaiseHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRaiseHeadLessThanOrEqualTo(String value) {
            addCriterion("liveness_pic_raise_head <=", value, "livenessPicRaiseHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRaiseHeadLike(String value) {
            addCriterion("liveness_pic_raise_head like", value, "livenessPicRaiseHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRaiseHeadNotLike(String value) {
            addCriterion("liveness_pic_raise_head not like", value, "livenessPicRaiseHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRaiseHeadIn(List<String> values) {
            addCriterion("liveness_pic_raise_head in", values, "livenessPicRaiseHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRaiseHeadNotIn(List<String> values) {
            addCriterion("liveness_pic_raise_head not in", values, "livenessPicRaiseHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRaiseHeadBetween(String value1, String value2) {
            addCriterion("liveness_pic_raise_head between", value1, value2, "livenessPicRaiseHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicRaiseHeadNotBetween(String value1, String value2) {
            addCriterion("liveness_pic_raise_head not between", value1, value2, "livenessPicRaiseHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicDropHeadIsNull() {
            addCriterion("liveness_pic_drop_head is null");
            return (Criteria) this;
        }

        public Criteria andLivenessPicDropHeadIsNotNull() {
            addCriterion("liveness_pic_drop_head is not null");
            return (Criteria) this;
        }

        public Criteria andLivenessPicDropHeadEqualTo(String value) {
            addCriterion("liveness_pic_drop_head =", value, "livenessPicDropHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicDropHeadNotEqualTo(String value) {
            addCriterion("liveness_pic_drop_head <>", value, "livenessPicDropHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicDropHeadGreaterThan(String value) {
            addCriterion("liveness_pic_drop_head >", value, "livenessPicDropHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicDropHeadGreaterThanOrEqualTo(String value) {
            addCriterion("liveness_pic_drop_head >=", value, "livenessPicDropHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicDropHeadLessThan(String value) {
            addCriterion("liveness_pic_drop_head <", value, "livenessPicDropHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicDropHeadLessThanOrEqualTo(String value) {
            addCriterion("liveness_pic_drop_head <=", value, "livenessPicDropHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicDropHeadLike(String value) {
            addCriterion("liveness_pic_drop_head like", value, "livenessPicDropHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicDropHeadNotLike(String value) {
            addCriterion("liveness_pic_drop_head not like", value, "livenessPicDropHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicDropHeadIn(List<String> values) {
            addCriterion("liveness_pic_drop_head in", values, "livenessPicDropHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicDropHeadNotIn(List<String> values) {
            addCriterion("liveness_pic_drop_head not in", values, "livenessPicDropHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicDropHeadBetween(String value1, String value2) {
            addCriterion("liveness_pic_drop_head between", value1, value2, "livenessPicDropHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicDropHeadNotBetween(String value1, String value2) {
            addCriterion("liveness_pic_drop_head not between", value1, value2, "livenessPicDropHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicShakeHeadIsNull() {
            addCriterion("liveness_pic_shake_head is null");
            return (Criteria) this;
        }

        public Criteria andLivenessPicShakeHeadIsNotNull() {
            addCriterion("liveness_pic_shake_head is not null");
            return (Criteria) this;
        }

        public Criteria andLivenessPicShakeHeadEqualTo(String value) {
            addCriterion("liveness_pic_shake_head =", value, "livenessPicShakeHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicShakeHeadNotEqualTo(String value) {
            addCriterion("liveness_pic_shake_head <>", value, "livenessPicShakeHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicShakeHeadGreaterThan(String value) {
            addCriterion("liveness_pic_shake_head >", value, "livenessPicShakeHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicShakeHeadGreaterThanOrEqualTo(String value) {
            addCriterion("liveness_pic_shake_head >=", value, "livenessPicShakeHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicShakeHeadLessThan(String value) {
            addCriterion("liveness_pic_shake_head <", value, "livenessPicShakeHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicShakeHeadLessThanOrEqualTo(String value) {
            addCriterion("liveness_pic_shake_head <=", value, "livenessPicShakeHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicShakeHeadLike(String value) {
            addCriterion("liveness_pic_shake_head like", value, "livenessPicShakeHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicShakeHeadNotLike(String value) {
            addCriterion("liveness_pic_shake_head not like", value, "livenessPicShakeHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicShakeHeadIn(List<String> values) {
            addCriterion("liveness_pic_shake_head in", values, "livenessPicShakeHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicShakeHeadNotIn(List<String> values) {
            addCriterion("liveness_pic_shake_head not in", values, "livenessPicShakeHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicShakeHeadBetween(String value1, String value2) {
            addCriterion("liveness_pic_shake_head between", value1, value2, "livenessPicShakeHead");
            return (Criteria) this;
        }

        public Criteria andLivenessPicShakeHeadNotBetween(String value1, String value2) {
            addCriterion("liveness_pic_shake_head not between", value1, value2, "livenessPicShakeHead");
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

        public Criteria andCheckerIsNull() {
            addCriterion("checker is null");
            return (Criteria) this;
        }

        public Criteria andCheckerIsNotNull() {
            addCriterion("checker is not null");
            return (Criteria) this;
        }

        public Criteria andCheckerEqualTo(Integer value) {
            addCriterion("checker =", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerNotEqualTo(Integer value) {
            addCriterion("checker <>", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerGreaterThan(Integer value) {
            addCriterion("checker >", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerGreaterThanOrEqualTo(Integer value) {
            addCriterion("checker >=", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerLessThan(Integer value) {
            addCriterion("checker <", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerLessThanOrEqualTo(Integer value) {
            addCriterion("checker <=", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerIn(List<Integer> values) {
            addCriterion("checker in", values, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerNotIn(List<Integer> values) {
            addCriterion("checker not in", values, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerBetween(Integer value1, Integer value2) {
            addCriterion("checker between", value1, value2, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerNotBetween(Integer value1, Integer value2) {
            addCriterion("checker not between", value1, value2, "checker");
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

        public Criteria andCheckDescIsNull() {
            addCriterion("check_desc is null");
            return (Criteria) this;
        }

        public Criteria andCheckDescIsNotNull() {
            addCriterion("check_desc is not null");
            return (Criteria) this;
        }

        public Criteria andCheckDescEqualTo(String value) {
            addCriterion("check_desc =", value, "checkDesc");
            return (Criteria) this;
        }

        public Criteria andCheckDescNotEqualTo(String value) {
            addCriterion("check_desc <>", value, "checkDesc");
            return (Criteria) this;
        }

        public Criteria andCheckDescGreaterThan(String value) {
            addCriterion("check_desc >", value, "checkDesc");
            return (Criteria) this;
        }

        public Criteria andCheckDescGreaterThanOrEqualTo(String value) {
            addCriterion("check_desc >=", value, "checkDesc");
            return (Criteria) this;
        }

        public Criteria andCheckDescLessThan(String value) {
            addCriterion("check_desc <", value, "checkDesc");
            return (Criteria) this;
        }

        public Criteria andCheckDescLessThanOrEqualTo(String value) {
            addCriterion("check_desc <=", value, "checkDesc");
            return (Criteria) this;
        }

        public Criteria andCheckDescLike(String value) {
            addCriterion("check_desc like", value, "checkDesc");
            return (Criteria) this;
        }

        public Criteria andCheckDescNotLike(String value) {
            addCriterion("check_desc not like", value, "checkDesc");
            return (Criteria) this;
        }

        public Criteria andCheckDescIn(List<String> values) {
            addCriterion("check_desc in", values, "checkDesc");
            return (Criteria) this;
        }

        public Criteria andCheckDescNotIn(List<String> values) {
            addCriterion("check_desc not in", values, "checkDesc");
            return (Criteria) this;
        }

        public Criteria andCheckDescBetween(String value1, String value2) {
            addCriterion("check_desc between", value1, value2, "checkDesc");
            return (Criteria) this;
        }

        public Criteria andCheckDescNotBetween(String value1, String value2) {
            addCriterion("check_desc not between", value1, value2, "checkDesc");
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