package com.pinting.mall.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MallCommodityInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MallCommodityInfoExample() {
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

        public Criteria andCommNameIsNull() {
            addCriterion("comm_name is null");
            return (Criteria) this;
        }

        public Criteria andCommNameIsNotNull() {
            addCriterion("comm_name is not null");
            return (Criteria) this;
        }

        public Criteria andCommNameEqualTo(String value) {
            addCriterion("comm_name =", value, "commName");
            return (Criteria) this;
        }

        public Criteria andCommNameNotEqualTo(String value) {
            addCriterion("comm_name <>", value, "commName");
            return (Criteria) this;
        }

        public Criteria andCommNameGreaterThan(String value) {
            addCriterion("comm_name >", value, "commName");
            return (Criteria) this;
        }

        public Criteria andCommNameGreaterThanOrEqualTo(String value) {
            addCriterion("comm_name >=", value, "commName");
            return (Criteria) this;
        }

        public Criteria andCommNameLessThan(String value) {
            addCriterion("comm_name <", value, "commName");
            return (Criteria) this;
        }

        public Criteria andCommNameLessThanOrEqualTo(String value) {
            addCriterion("comm_name <=", value, "commName");
            return (Criteria) this;
        }

        public Criteria andCommNameLike(String value) {
            addCriterion("comm_name like", value, "commName");
            return (Criteria) this;
        }

        public Criteria andCommNameNotLike(String value) {
            addCriterion("comm_name not like", value, "commName");
            return (Criteria) this;
        }

        public Criteria andCommNameIn(List<String> values) {
            addCriterion("comm_name in", values, "commName");
            return (Criteria) this;
        }

        public Criteria andCommNameNotIn(List<String> values) {
            addCriterion("comm_name not in", values, "commName");
            return (Criteria) this;
        }

        public Criteria andCommNameBetween(String value1, String value2) {
            addCriterion("comm_name between", value1, value2, "commName");
            return (Criteria) this;
        }

        public Criteria andCommNameNotBetween(String value1, String value2) {
            addCriterion("comm_name not between", value1, value2, "commName");
            return (Criteria) this;
        }

        public Criteria andCommTypeIdIsNull() {
            addCriterion("comm_type_id is null");
            return (Criteria) this;
        }

        public Criteria andCommTypeIdIsNotNull() {
            addCriterion("comm_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andCommTypeIdEqualTo(Integer value) {
            addCriterion("comm_type_id =", value, "commTypeId");
            return (Criteria) this;
        }

        public Criteria andCommTypeIdNotEqualTo(Integer value) {
            addCriterion("comm_type_id <>", value, "commTypeId");
            return (Criteria) this;
        }

        public Criteria andCommTypeIdGreaterThan(Integer value) {
            addCriterion("comm_type_id >", value, "commTypeId");
            return (Criteria) this;
        }

        public Criteria andCommTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("comm_type_id >=", value, "commTypeId");
            return (Criteria) this;
        }

        public Criteria andCommTypeIdLessThan(Integer value) {
            addCriterion("comm_type_id <", value, "commTypeId");
            return (Criteria) this;
        }

        public Criteria andCommTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("comm_type_id <=", value, "commTypeId");
            return (Criteria) this;
        }

        public Criteria andCommTypeIdIn(List<Integer> values) {
            addCriterion("comm_type_id in", values, "commTypeId");
            return (Criteria) this;
        }

        public Criteria andCommTypeIdNotIn(List<Integer> values) {
            addCriterion("comm_type_id not in", values, "commTypeId");
            return (Criteria) this;
        }

        public Criteria andCommTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("comm_type_id between", value1, value2, "commTypeId");
            return (Criteria) this;
        }

        public Criteria andCommTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("comm_type_id not between", value1, value2, "commTypeId");
            return (Criteria) this;
        }

        public Criteria andCommPropertyIsNull() {
            addCriterion("comm_property is null");
            return (Criteria) this;
        }

        public Criteria andCommPropertyIsNotNull() {
            addCriterion("comm_property is not null");
            return (Criteria) this;
        }

        public Criteria andCommPropertyEqualTo(String value) {
            addCriterion("comm_property =", value, "commProperty");
            return (Criteria) this;
        }

        public Criteria andCommPropertyNotEqualTo(String value) {
            addCriterion("comm_property <>", value, "commProperty");
            return (Criteria) this;
        }

        public Criteria andCommPropertyGreaterThan(String value) {
            addCriterion("comm_property >", value, "commProperty");
            return (Criteria) this;
        }

        public Criteria andCommPropertyGreaterThanOrEqualTo(String value) {
            addCriterion("comm_property >=", value, "commProperty");
            return (Criteria) this;
        }

        public Criteria andCommPropertyLessThan(String value) {
            addCriterion("comm_property <", value, "commProperty");
            return (Criteria) this;
        }

        public Criteria andCommPropertyLessThanOrEqualTo(String value) {
            addCriterion("comm_property <=", value, "commProperty");
            return (Criteria) this;
        }

        public Criteria andCommPropertyLike(String value) {
            addCriterion("comm_property like", value, "commProperty");
            return (Criteria) this;
        }

        public Criteria andCommPropertyNotLike(String value) {
            addCriterion("comm_property not like", value, "commProperty");
            return (Criteria) this;
        }

        public Criteria andCommPropertyIn(List<String> values) {
            addCriterion("comm_property in", values, "commProperty");
            return (Criteria) this;
        }

        public Criteria andCommPropertyNotIn(List<String> values) {
            addCriterion("comm_property not in", values, "commProperty");
            return (Criteria) this;
        }

        public Criteria andCommPropertyBetween(String value1, String value2) {
            addCriterion("comm_property between", value1, value2, "commProperty");
            return (Criteria) this;
        }

        public Criteria andCommPropertyNotBetween(String value1, String value2) {
            addCriterion("comm_property not between", value1, value2, "commProperty");
            return (Criteria) this;
        }

        public Criteria andPointsIsNull() {
            addCriterion("points is null");
            return (Criteria) this;
        }

        public Criteria andPointsIsNotNull() {
            addCriterion("points is not null");
            return (Criteria) this;
        }

        public Criteria andPointsEqualTo(Long value) {
            addCriterion("points =", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsNotEqualTo(Long value) {
            addCriterion("points <>", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsGreaterThan(Long value) {
            addCriterion("points >", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsGreaterThanOrEqualTo(Long value) {
            addCriterion("points >=", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsLessThan(Long value) {
            addCriterion("points <", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsLessThanOrEqualTo(Long value) {
            addCriterion("points <=", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsIn(List<Long> values) {
            addCriterion("points in", values, "points");
            return (Criteria) this;
        }

        public Criteria andPointsNotIn(List<Long> values) {
            addCriterion("points not in", values, "points");
            return (Criteria) this;
        }

        public Criteria andPointsBetween(Long value1, Long value2) {
            addCriterion("points between", value1, value2, "points");
            return (Criteria) this;
        }

        public Criteria andPointsNotBetween(Long value1, Long value2) {
            addCriterion("points not between", value1, value2, "points");
            return (Criteria) this;
        }

        public Criteria andLeftCountIsNull() {
            addCriterion("left_count is null");
            return (Criteria) this;
        }

        public Criteria andLeftCountIsNotNull() {
            addCriterion("left_count is not null");
            return (Criteria) this;
        }

        public Criteria andLeftCountEqualTo(Integer value) {
            addCriterion("left_count =", value, "leftCount");
            return (Criteria) this;
        }

        public Criteria andLeftCountNotEqualTo(Integer value) {
            addCriterion("left_count <>", value, "leftCount");
            return (Criteria) this;
        }

        public Criteria andLeftCountGreaterThan(Integer value) {
            addCriterion("left_count >", value, "leftCount");
            return (Criteria) this;
        }

        public Criteria andLeftCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("left_count >=", value, "leftCount");
            return (Criteria) this;
        }

        public Criteria andLeftCountLessThan(Integer value) {
            addCriterion("left_count <", value, "leftCount");
            return (Criteria) this;
        }

        public Criteria andLeftCountLessThanOrEqualTo(Integer value) {
            addCriterion("left_count <=", value, "leftCount");
            return (Criteria) this;
        }

        public Criteria andLeftCountIn(List<Integer> values) {
            addCriterion("left_count in", values, "leftCount");
            return (Criteria) this;
        }

        public Criteria andLeftCountNotIn(List<Integer> values) {
            addCriterion("left_count not in", values, "leftCount");
            return (Criteria) this;
        }

        public Criteria andLeftCountBetween(Integer value1, Integer value2) {
            addCriterion("left_count between", value1, value2, "leftCount");
            return (Criteria) this;
        }

        public Criteria andLeftCountNotBetween(Integer value1, Integer value2) {
            addCriterion("left_count not between", value1, value2, "leftCount");
            return (Criteria) this;
        }

        public Criteria andIsRecommendIsNull() {
            addCriterion("is_recommend is null");
            return (Criteria) this;
        }

        public Criteria andIsRecommendIsNotNull() {
            addCriterion("is_recommend is not null");
            return (Criteria) this;
        }

        public Criteria andIsRecommendEqualTo(String value) {
            addCriterion("is_recommend =", value, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andIsRecommendNotEqualTo(String value) {
            addCriterion("is_recommend <>", value, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andIsRecommendGreaterThan(String value) {
            addCriterion("is_recommend >", value, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andIsRecommendGreaterThanOrEqualTo(String value) {
            addCriterion("is_recommend >=", value, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andIsRecommendLessThan(String value) {
            addCriterion("is_recommend <", value, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andIsRecommendLessThanOrEqualTo(String value) {
            addCriterion("is_recommend <=", value, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andIsRecommendLike(String value) {
            addCriterion("is_recommend like", value, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andIsRecommendNotLike(String value) {
            addCriterion("is_recommend not like", value, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andIsRecommendIn(List<String> values) {
            addCriterion("is_recommend in", values, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andIsRecommendNotIn(List<String> values) {
            addCriterion("is_recommend not in", values, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andIsRecommendBetween(String value1, String value2) {
            addCriterion("is_recommend between", value1, value2, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andIsRecommendNotBetween(String value1, String value2) {
            addCriterion("is_recommend not between", value1, value2, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andCommPictureUrlIsNull() {
            addCriterion("comm_picture_url is null");
            return (Criteria) this;
        }

        public Criteria andCommPictureUrlIsNotNull() {
            addCriterion("comm_picture_url is not null");
            return (Criteria) this;
        }

        public Criteria andCommPictureUrlEqualTo(String value) {
            addCriterion("comm_picture_url =", value, "commPictureUrl");
            return (Criteria) this;
        }

        public Criteria andCommPictureUrlNotEqualTo(String value) {
            addCriterion("comm_picture_url <>", value, "commPictureUrl");
            return (Criteria) this;
        }

        public Criteria andCommPictureUrlGreaterThan(String value) {
            addCriterion("comm_picture_url >", value, "commPictureUrl");
            return (Criteria) this;
        }

        public Criteria andCommPictureUrlGreaterThanOrEqualTo(String value) {
            addCriterion("comm_picture_url >=", value, "commPictureUrl");
            return (Criteria) this;
        }

        public Criteria andCommPictureUrlLessThan(String value) {
            addCriterion("comm_picture_url <", value, "commPictureUrl");
            return (Criteria) this;
        }

        public Criteria andCommPictureUrlLessThanOrEqualTo(String value) {
            addCriterion("comm_picture_url <=", value, "commPictureUrl");
            return (Criteria) this;
        }

        public Criteria andCommPictureUrlLike(String value) {
            addCriterion("comm_picture_url like", value, "commPictureUrl");
            return (Criteria) this;
        }

        public Criteria andCommPictureUrlNotLike(String value) {
            addCriterion("comm_picture_url not like", value, "commPictureUrl");
            return (Criteria) this;
        }

        public Criteria andCommPictureUrlIn(List<String> values) {
            addCriterion("comm_picture_url in", values, "commPictureUrl");
            return (Criteria) this;
        }

        public Criteria andCommPictureUrlNotIn(List<String> values) {
            addCriterion("comm_picture_url not in", values, "commPictureUrl");
            return (Criteria) this;
        }

        public Criteria andCommPictureUrlBetween(String value1, String value2) {
            addCriterion("comm_picture_url between", value1, value2, "commPictureUrl");
            return (Criteria) this;
        }

        public Criteria andCommPictureUrlNotBetween(String value1, String value2) {
            addCriterion("comm_picture_url not between", value1, value2, "commPictureUrl");
            return (Criteria) this;
        }

        public Criteria andSoldCountIsNull() {
            addCriterion("sold_count is null");
            return (Criteria) this;
        }

        public Criteria andSoldCountIsNotNull() {
            addCriterion("sold_count is not null");
            return (Criteria) this;
        }

        public Criteria andSoldCountEqualTo(Integer value) {
            addCriterion("sold_count =", value, "soldCount");
            return (Criteria) this;
        }

        public Criteria andSoldCountNotEqualTo(Integer value) {
            addCriterion("sold_count <>", value, "soldCount");
            return (Criteria) this;
        }

        public Criteria andSoldCountGreaterThan(Integer value) {
            addCriterion("sold_count >", value, "soldCount");
            return (Criteria) this;
        }

        public Criteria andSoldCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("sold_count >=", value, "soldCount");
            return (Criteria) this;
        }

        public Criteria andSoldCountLessThan(Integer value) {
            addCriterion("sold_count <", value, "soldCount");
            return (Criteria) this;
        }

        public Criteria andSoldCountLessThanOrEqualTo(Integer value) {
            addCriterion("sold_count <=", value, "soldCount");
            return (Criteria) this;
        }

        public Criteria andSoldCountIn(List<Integer> values) {
            addCriterion("sold_count in", values, "soldCount");
            return (Criteria) this;
        }

        public Criteria andSoldCountNotIn(List<Integer> values) {
            addCriterion("sold_count not in", values, "soldCount");
            return (Criteria) this;
        }

        public Criteria andSoldCountBetween(Integer value1, Integer value2) {
            addCriterion("sold_count between", value1, value2, "soldCount");
            return (Criteria) this;
        }

        public Criteria andSoldCountNotBetween(Integer value1, Integer value2) {
            addCriterion("sold_count not between", value1, value2, "soldCount");
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

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(Integer value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(Integer value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(Integer value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(Integer value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(Integer value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(Integer value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<Integer> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<Integer> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(Integer value1, Integer value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(Integer value1, Integer value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andLastOperatorIsNull() {
            addCriterion("last_operator is null");
            return (Criteria) this;
        }

        public Criteria andLastOperatorIsNotNull() {
            addCriterion("last_operator is not null");
            return (Criteria) this;
        }

        public Criteria andLastOperatorEqualTo(Integer value) {
            addCriterion("last_operator =", value, "lastOperator");
            return (Criteria) this;
        }

        public Criteria andLastOperatorNotEqualTo(Integer value) {
            addCriterion("last_operator <>", value, "lastOperator");
            return (Criteria) this;
        }

        public Criteria andLastOperatorGreaterThan(Integer value) {
            addCriterion("last_operator >", value, "lastOperator");
            return (Criteria) this;
        }

        public Criteria andLastOperatorGreaterThanOrEqualTo(Integer value) {
            addCriterion("last_operator >=", value, "lastOperator");
            return (Criteria) this;
        }

        public Criteria andLastOperatorLessThan(Integer value) {
            addCriterion("last_operator <", value, "lastOperator");
            return (Criteria) this;
        }

        public Criteria andLastOperatorLessThanOrEqualTo(Integer value) {
            addCriterion("last_operator <=", value, "lastOperator");
            return (Criteria) this;
        }

        public Criteria andLastOperatorIn(List<Integer> values) {
            addCriterion("last_operator in", values, "lastOperator");
            return (Criteria) this;
        }

        public Criteria andLastOperatorNotIn(List<Integer> values) {
            addCriterion("last_operator not in", values, "lastOperator");
            return (Criteria) this;
        }

        public Criteria andLastOperatorBetween(Integer value1, Integer value2) {
            addCriterion("last_operator between", value1, value2, "lastOperator");
            return (Criteria) this;
        }

        public Criteria andLastOperatorNotBetween(Integer value1, Integer value2) {
            addCriterion("last_operator not between", value1, value2, "lastOperator");
            return (Criteria) this;
        }

        public Criteria andForSaleTimeIsNull() {
            addCriterion("for_sale_time is null");
            return (Criteria) this;
        }

        public Criteria andForSaleTimeIsNotNull() {
            addCriterion("for_sale_time is not null");
            return (Criteria) this;
        }

        public Criteria andForSaleTimeEqualTo(Date value) {
            addCriterion("for_sale_time =", value, "forSaleTime");
            return (Criteria) this;
        }

        public Criteria andForSaleTimeNotEqualTo(Date value) {
            addCriterion("for_sale_time <>", value, "forSaleTime");
            return (Criteria) this;
        }

        public Criteria andForSaleTimeGreaterThan(Date value) {
            addCriterion("for_sale_time >", value, "forSaleTime");
            return (Criteria) this;
        }

        public Criteria andForSaleTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("for_sale_time >=", value, "forSaleTime");
            return (Criteria) this;
        }

        public Criteria andForSaleTimeLessThan(Date value) {
            addCriterion("for_sale_time <", value, "forSaleTime");
            return (Criteria) this;
        }

        public Criteria andForSaleTimeLessThanOrEqualTo(Date value) {
            addCriterion("for_sale_time <=", value, "forSaleTime");
            return (Criteria) this;
        }

        public Criteria andForSaleTimeIn(List<Date> values) {
            addCriterion("for_sale_time in", values, "forSaleTime");
            return (Criteria) this;
        }

        public Criteria andForSaleTimeNotIn(List<Date> values) {
            addCriterion("for_sale_time not in", values, "forSaleTime");
            return (Criteria) this;
        }

        public Criteria andForSaleTimeBetween(Date value1, Date value2) {
            addCriterion("for_sale_time between", value1, value2, "forSaleTime");
            return (Criteria) this;
        }

        public Criteria andForSaleTimeNotBetween(Date value1, Date value2) {
            addCriterion("for_sale_time not between", value1, value2, "forSaleTime");
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