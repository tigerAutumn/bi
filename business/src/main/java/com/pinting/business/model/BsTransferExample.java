package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsTransferExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsTransferExample() {
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

        public Criteria andUserId1IsNull() {
            addCriterion("user_id1 is null");
            return (Criteria) this;
        }

        public Criteria andUserId1IsNotNull() {
            addCriterion("user_id1 is not null");
            return (Criteria) this;
        }

        public Criteria andUserId1EqualTo(Integer value) {
            addCriterion("user_id1 =", value, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1NotEqualTo(Integer value) {
            addCriterion("user_id1 <>", value, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1GreaterThan(Integer value) {
            addCriterion("user_id1 >", value, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1GreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id1 >=", value, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1LessThan(Integer value) {
            addCriterion("user_id1 <", value, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1LessThanOrEqualTo(Integer value) {
            addCriterion("user_id1 <=", value, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1In(List<Integer> values) {
            addCriterion("user_id1 in", values, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1NotIn(List<Integer> values) {
            addCriterion("user_id1 not in", values, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1Between(Integer value1, Integer value2) {
            addCriterion("user_id1 between", value1, value2, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1NotBetween(Integer value1, Integer value2) {
            addCriterion("user_id1 not between", value1, value2, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId2IsNull() {
            addCriterion("user_id2 is null");
            return (Criteria) this;
        }

        public Criteria andUserId2IsNotNull() {
            addCriterion("user_id2 is not null");
            return (Criteria) this;
        }

        public Criteria andUserId2EqualTo(Integer value) {
            addCriterion("user_id2 =", value, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2NotEqualTo(Integer value) {
            addCriterion("user_id2 <>", value, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2GreaterThan(Integer value) {
            addCriterion("user_id2 >", value, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2GreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id2 >=", value, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2LessThan(Integer value) {
            addCriterion("user_id2 <", value, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2LessThanOrEqualTo(Integer value) {
            addCriterion("user_id2 <=", value, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2In(List<Integer> values) {
            addCriterion("user_id2 in", values, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2NotIn(List<Integer> values) {
            addCriterion("user_id2 not in", values, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2Between(Integer value1, Integer value2) {
            addCriterion("user_id2 between", value1, value2, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2NotBetween(Integer value1, Integer value2) {
            addCriterion("user_id2 not between", value1, value2, "userId2");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNull() {
            addCriterion("product_id is null");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNotNull() {
            addCriterion("product_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductIdEqualTo(Integer value) {
            addCriterion("product_id =", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotEqualTo(Integer value) {
            addCriterion("product_id <>", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThan(Integer value) {
            addCriterion("product_id >", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("product_id >=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThan(Integer value) {
            addCriterion("product_id <", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThanOrEqualTo(Integer value) {
            addCriterion("product_id <=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdIn(List<Integer> values) {
            addCriterion("product_id in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotIn(List<Integer> values) {
            addCriterion("product_id not in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdBetween(Integer value1, Integer value2) {
            addCriterion("product_id between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotBetween(Integer value1, Integer value2) {
            addCriterion("product_id not between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andSubAccountId1IsNull() {
            addCriterion("sub_account_id1 is null");
            return (Criteria) this;
        }

        public Criteria andSubAccountId1IsNotNull() {
            addCriterion("sub_account_id1 is not null");
            return (Criteria) this;
        }

        public Criteria andSubAccountId1EqualTo(Integer value) {
            addCriterion("sub_account_id1 =", value, "subAccountId1");
            return (Criteria) this;
        }

        public Criteria andSubAccountId1NotEqualTo(Integer value) {
            addCriterion("sub_account_id1 <>", value, "subAccountId1");
            return (Criteria) this;
        }

        public Criteria andSubAccountId1GreaterThan(Integer value) {
            addCriterion("sub_account_id1 >", value, "subAccountId1");
            return (Criteria) this;
        }

        public Criteria andSubAccountId1GreaterThanOrEqualTo(Integer value) {
            addCriterion("sub_account_id1 >=", value, "subAccountId1");
            return (Criteria) this;
        }

        public Criteria andSubAccountId1LessThan(Integer value) {
            addCriterion("sub_account_id1 <", value, "subAccountId1");
            return (Criteria) this;
        }

        public Criteria andSubAccountId1LessThanOrEqualTo(Integer value) {
            addCriterion("sub_account_id1 <=", value, "subAccountId1");
            return (Criteria) this;
        }

        public Criteria andSubAccountId1In(List<Integer> values) {
            addCriterion("sub_account_id1 in", values, "subAccountId1");
            return (Criteria) this;
        }

        public Criteria andSubAccountId1NotIn(List<Integer> values) {
            addCriterion("sub_account_id1 not in", values, "subAccountId1");
            return (Criteria) this;
        }

        public Criteria andSubAccountId1Between(Integer value1, Integer value2) {
            addCriterion("sub_account_id1 between", value1, value2, "subAccountId1");
            return (Criteria) this;
        }

        public Criteria andSubAccountId1NotBetween(Integer value1, Integer value2) {
            addCriterion("sub_account_id1 not between", value1, value2, "subAccountId1");
            return (Criteria) this;
        }

        public Criteria andSubAccountId2IsNull() {
            addCriterion("sub_account_id2 is null");
            return (Criteria) this;
        }

        public Criteria andSubAccountId2IsNotNull() {
            addCriterion("sub_account_id2 is not null");
            return (Criteria) this;
        }

        public Criteria andSubAccountId2EqualTo(Integer value) {
            addCriterion("sub_account_id2 =", value, "subAccountId2");
            return (Criteria) this;
        }

        public Criteria andSubAccountId2NotEqualTo(Integer value) {
            addCriterion("sub_account_id2 <>", value, "subAccountId2");
            return (Criteria) this;
        }

        public Criteria andSubAccountId2GreaterThan(Integer value) {
            addCriterion("sub_account_id2 >", value, "subAccountId2");
            return (Criteria) this;
        }

        public Criteria andSubAccountId2GreaterThanOrEqualTo(Integer value) {
            addCriterion("sub_account_id2 >=", value, "subAccountId2");
            return (Criteria) this;
        }

        public Criteria andSubAccountId2LessThan(Integer value) {
            addCriterion("sub_account_id2 <", value, "subAccountId2");
            return (Criteria) this;
        }

        public Criteria andSubAccountId2LessThanOrEqualTo(Integer value) {
            addCriterion("sub_account_id2 <=", value, "subAccountId2");
            return (Criteria) this;
        }

        public Criteria andSubAccountId2In(List<Integer> values) {
            addCriterion("sub_account_id2 in", values, "subAccountId2");
            return (Criteria) this;
        }

        public Criteria andSubAccountId2NotIn(List<Integer> values) {
            addCriterion("sub_account_id2 not in", values, "subAccountId2");
            return (Criteria) this;
        }

        public Criteria andSubAccountId2Between(Integer value1, Integer value2) {
            addCriterion("sub_account_id2 between", value1, value2, "subAccountId2");
            return (Criteria) this;
        }

        public Criteria andSubAccountId2NotBetween(Integer value1, Integer value2) {
            addCriterion("sub_account_id2 not between", value1, value2, "subAccountId2");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(Double value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(Double value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(Double value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(Double value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(Double value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(Double value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<Double> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<Double> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(Double value1, Double value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(Double value1, Double value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andWorthIsNull() {
            addCriterion("worth is null");
            return (Criteria) this;
        }

        public Criteria andWorthIsNotNull() {
            addCriterion("worth is not null");
            return (Criteria) this;
        }

        public Criteria andWorthEqualTo(Double value) {
            addCriterion("worth =", value, "worth");
            return (Criteria) this;
        }

        public Criteria andWorthNotEqualTo(Double value) {
            addCriterion("worth <>", value, "worth");
            return (Criteria) this;
        }

        public Criteria andWorthGreaterThan(Double value) {
            addCriterion("worth >", value, "worth");
            return (Criteria) this;
        }

        public Criteria andWorthGreaterThanOrEqualTo(Double value) {
            addCriterion("worth >=", value, "worth");
            return (Criteria) this;
        }

        public Criteria andWorthLessThan(Double value) {
            addCriterion("worth <", value, "worth");
            return (Criteria) this;
        }

        public Criteria andWorthLessThanOrEqualTo(Double value) {
            addCriterion("worth <=", value, "worth");
            return (Criteria) this;
        }

        public Criteria andWorthIn(List<Double> values) {
            addCriterion("worth in", values, "worth");
            return (Criteria) this;
        }

        public Criteria andWorthNotIn(List<Double> values) {
            addCriterion("worth not in", values, "worth");
            return (Criteria) this;
        }

        public Criteria andWorthBetween(Double value1, Double value2) {
            addCriterion("worth between", value1, value2, "worth");
            return (Criteria) this;
        }

        public Criteria andWorthNotBetween(Double value1, Double value2) {
            addCriterion("worth not between", value1, value2, "worth");
            return (Criteria) this;
        }

        public Criteria andOldRateIsNull() {
            addCriterion("old_rate is null");
            return (Criteria) this;
        }

        public Criteria andOldRateIsNotNull() {
            addCriterion("old_rate is not null");
            return (Criteria) this;
        }

        public Criteria andOldRateEqualTo(Double value) {
            addCriterion("old_rate =", value, "oldRate");
            return (Criteria) this;
        }

        public Criteria andOldRateNotEqualTo(Double value) {
            addCriterion("old_rate <>", value, "oldRate");
            return (Criteria) this;
        }

        public Criteria andOldRateGreaterThan(Double value) {
            addCriterion("old_rate >", value, "oldRate");
            return (Criteria) this;
        }

        public Criteria andOldRateGreaterThanOrEqualTo(Double value) {
            addCriterion("old_rate >=", value, "oldRate");
            return (Criteria) this;
        }

        public Criteria andOldRateLessThan(Double value) {
            addCriterion("old_rate <", value, "oldRate");
            return (Criteria) this;
        }

        public Criteria andOldRateLessThanOrEqualTo(Double value) {
            addCriterion("old_rate <=", value, "oldRate");
            return (Criteria) this;
        }

        public Criteria andOldRateIn(List<Double> values) {
            addCriterion("old_rate in", values, "oldRate");
            return (Criteria) this;
        }

        public Criteria andOldRateNotIn(List<Double> values) {
            addCriterion("old_rate not in", values, "oldRate");
            return (Criteria) this;
        }

        public Criteria andOldRateBetween(Double value1, Double value2) {
            addCriterion("old_rate between", value1, value2, "oldRate");
            return (Criteria) this;
        }

        public Criteria andOldRateNotBetween(Double value1, Double value2) {
            addCriterion("old_rate not between", value1, value2, "oldRate");
            return (Criteria) this;
        }

        public Criteria andRealRateIsNull() {
            addCriterion("real_rate is null");
            return (Criteria) this;
        }

        public Criteria andRealRateIsNotNull() {
            addCriterion("real_rate is not null");
            return (Criteria) this;
        }

        public Criteria andRealRateEqualTo(Double value) {
            addCriterion("real_rate =", value, "realRate");
            return (Criteria) this;
        }

        public Criteria andRealRateNotEqualTo(Double value) {
            addCriterion("real_rate <>", value, "realRate");
            return (Criteria) this;
        }

        public Criteria andRealRateGreaterThan(Double value) {
            addCriterion("real_rate >", value, "realRate");
            return (Criteria) this;
        }

        public Criteria andRealRateGreaterThanOrEqualTo(Double value) {
            addCriterion("real_rate >=", value, "realRate");
            return (Criteria) this;
        }

        public Criteria andRealRateLessThan(Double value) {
            addCriterion("real_rate <", value, "realRate");
            return (Criteria) this;
        }

        public Criteria andRealRateLessThanOrEqualTo(Double value) {
            addCriterion("real_rate <=", value, "realRate");
            return (Criteria) this;
        }

        public Criteria andRealRateIn(List<Double> values) {
            addCriterion("real_rate in", values, "realRate");
            return (Criteria) this;
        }

        public Criteria andRealRateNotIn(List<Double> values) {
            addCriterion("real_rate not in", values, "realRate");
            return (Criteria) this;
        }

        public Criteria andRealRateBetween(Double value1, Double value2) {
            addCriterion("real_rate between", value1, value2, "realRate");
            return (Criteria) this;
        }

        public Criteria andRealRateNotBetween(Double value1, Double value2) {
            addCriterion("real_rate not between", value1, value2, "realRate");
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

        public Criteria andDistributeTimeIsNull() {
            addCriterion("distribute_time is null");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeIsNotNull() {
            addCriterion("distribute_time is not null");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeEqualTo(Date value) {
            addCriterion("distribute_time =", value, "distributeTime");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeNotEqualTo(Date value) {
            addCriterion("distribute_time <>", value, "distributeTime");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeGreaterThan(Date value) {
            addCriterion("distribute_time >", value, "distributeTime");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("distribute_time >=", value, "distributeTime");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeLessThan(Date value) {
            addCriterion("distribute_time <", value, "distributeTime");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeLessThanOrEqualTo(Date value) {
            addCriterion("distribute_time <=", value, "distributeTime");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeIn(List<Date> values) {
            addCriterion("distribute_time in", values, "distributeTime");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeNotIn(List<Date> values) {
            addCriterion("distribute_time not in", values, "distributeTime");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeBetween(Date value1, Date value2) {
            addCriterion("distribute_time between", value1, value2, "distributeTime");
            return (Criteria) this;
        }

        public Criteria andDistributeTimeNotBetween(Date value1, Date value2) {
            addCriterion("distribute_time not between", value1, value2, "distributeTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeIsNull() {
            addCriterion("deal_time is null");
            return (Criteria) this;
        }

        public Criteria andDealTimeIsNotNull() {
            addCriterion("deal_time is not null");
            return (Criteria) this;
        }

        public Criteria andDealTimeEqualTo(Date value) {
            addCriterion("deal_time =", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeNotEqualTo(Date value) {
            addCriterion("deal_time <>", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeGreaterThan(Date value) {
            addCriterion("deal_time >", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("deal_time >=", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeLessThan(Date value) {
            addCriterion("deal_time <", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeLessThanOrEqualTo(Date value) {
            addCriterion("deal_time <=", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeIn(List<Date> values) {
            addCriterion("deal_time in", values, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeNotIn(List<Date> values) {
            addCriterion("deal_time not in", values, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeBetween(Date value1, Date value2) {
            addCriterion("deal_time between", value1, value2, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeNotBetween(Date value1, Date value2) {
            addCriterion("deal_time not between", value1, value2, "dealTime");
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