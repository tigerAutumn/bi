package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bs19payCheckErrorExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public Bs19payCheckErrorExample() {
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

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andSysStatusIsNull() {
            addCriterion("sys_status is null");
            return (Criteria) this;
        }

        public Criteria andSysStatusIsNotNull() {
            addCriterion("sys_status is not null");
            return (Criteria) this;
        }

        public Criteria andSysStatusEqualTo(String value) {
            addCriterion("sys_status =", value, "sysStatus");
            return (Criteria) this;
        }

        public Criteria andSysStatusNotEqualTo(String value) {
            addCriterion("sys_status <>", value, "sysStatus");
            return (Criteria) this;
        }

        public Criteria andSysStatusGreaterThan(String value) {
            addCriterion("sys_status >", value, "sysStatus");
            return (Criteria) this;
        }

        public Criteria andSysStatusGreaterThanOrEqualTo(String value) {
            addCriterion("sys_status >=", value, "sysStatus");
            return (Criteria) this;
        }

        public Criteria andSysStatusLessThan(String value) {
            addCriterion("sys_status <", value, "sysStatus");
            return (Criteria) this;
        }

        public Criteria andSysStatusLessThanOrEqualTo(String value) {
            addCriterion("sys_status <=", value, "sysStatus");
            return (Criteria) this;
        }

        public Criteria andSysStatusLike(String value) {
            addCriterion("sys_status like", value, "sysStatus");
            return (Criteria) this;
        }

        public Criteria andSysStatusNotLike(String value) {
            addCriterion("sys_status not like", value, "sysStatus");
            return (Criteria) this;
        }

        public Criteria andSysStatusIn(List<String> values) {
            addCriterion("sys_status in", values, "sysStatus");
            return (Criteria) this;
        }

        public Criteria andSysStatusNotIn(List<String> values) {
            addCriterion("sys_status not in", values, "sysStatus");
            return (Criteria) this;
        }

        public Criteria andSysStatusBetween(String value1, String value2) {
            addCriterion("sys_status between", value1, value2, "sysStatus");
            return (Criteria) this;
        }

        public Criteria andSysStatusNotBetween(String value1, String value2) {
            addCriterion("sys_status not between", value1, value2, "sysStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusIsNull() {
            addCriterion("check_file_status is null");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusIsNotNull() {
            addCriterion("check_file_status is not null");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusEqualTo(String value) {
            addCriterion("check_file_status =", value, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusNotEqualTo(String value) {
            addCriterion("check_file_status <>", value, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusGreaterThan(String value) {
            addCriterion("check_file_status >", value, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusGreaterThanOrEqualTo(String value) {
            addCriterion("check_file_status >=", value, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusLessThan(String value) {
            addCriterion("check_file_status <", value, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusLessThanOrEqualTo(String value) {
            addCriterion("check_file_status <=", value, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusLike(String value) {
            addCriterion("check_file_status like", value, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusNotLike(String value) {
            addCriterion("check_file_status not like", value, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusIn(List<String> values) {
            addCriterion("check_file_status in", values, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusNotIn(List<String> values) {
            addCriterion("check_file_status not in", values, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusBetween(String value1, String value2) {
            addCriterion("check_file_status between", value1, value2, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileStatusNotBetween(String value1, String value2) {
            addCriterion("check_file_status not between", value1, value2, "checkFileStatus");
            return (Criteria) this;
        }

        public Criteria andIsDealIsNull() {
            addCriterion("is_deal is null");
            return (Criteria) this;
        }

        public Criteria andIsDealIsNotNull() {
            addCriterion("is_deal is not null");
            return (Criteria) this;
        }

        public Criteria andIsDealEqualTo(Integer value) {
            addCriterion("is_deal =", value, "isDeal");
            return (Criteria) this;
        }

        public Criteria andIsDealNotEqualTo(Integer value) {
            addCriterion("is_deal <>", value, "isDeal");
            return (Criteria) this;
        }

        public Criteria andIsDealGreaterThan(Integer value) {
            addCriterion("is_deal >", value, "isDeal");
            return (Criteria) this;
        }

        public Criteria andIsDealGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_deal >=", value, "isDeal");
            return (Criteria) this;
        }

        public Criteria andIsDealLessThan(Integer value) {
            addCriterion("is_deal <", value, "isDeal");
            return (Criteria) this;
        }

        public Criteria andIsDealLessThanOrEqualTo(Integer value) {
            addCriterion("is_deal <=", value, "isDeal");
            return (Criteria) this;
        }

        public Criteria andIsDealIn(List<Integer> values) {
            addCriterion("is_deal in", values, "isDeal");
            return (Criteria) this;
        }

        public Criteria andIsDealNotIn(List<Integer> values) {
            addCriterion("is_deal not in", values, "isDeal");
            return (Criteria) this;
        }

        public Criteria andIsDealBetween(Integer value1, Integer value2) {
            addCriterion("is_deal between", value1, value2, "isDeal");
            return (Criteria) this;
        }

        public Criteria andIsDealNotBetween(Integer value1, Integer value2) {
            addCriterion("is_deal not between", value1, value2, "isDeal");
            return (Criteria) this;
        }

        public Criteria andDealUserIdIsNull() {
            addCriterion("deal_user_id is null");
            return (Criteria) this;
        }

        public Criteria andDealUserIdIsNotNull() {
            addCriterion("deal_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andDealUserIdEqualTo(Integer value) {
            addCriterion("deal_user_id =", value, "dealUserId");
            return (Criteria) this;
        }

        public Criteria andDealUserIdNotEqualTo(Integer value) {
            addCriterion("deal_user_id <>", value, "dealUserId");
            return (Criteria) this;
        }

        public Criteria andDealUserIdGreaterThan(Integer value) {
            addCriterion("deal_user_id >", value, "dealUserId");
            return (Criteria) this;
        }

        public Criteria andDealUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("deal_user_id >=", value, "dealUserId");
            return (Criteria) this;
        }

        public Criteria andDealUserIdLessThan(Integer value) {
            addCriterion("deal_user_id <", value, "dealUserId");
            return (Criteria) this;
        }

        public Criteria andDealUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("deal_user_id <=", value, "dealUserId");
            return (Criteria) this;
        }

        public Criteria andDealUserIdIn(List<Integer> values) {
            addCriterion("deal_user_id in", values, "dealUserId");
            return (Criteria) this;
        }

        public Criteria andDealUserIdNotIn(List<Integer> values) {
            addCriterion("deal_user_id not in", values, "dealUserId");
            return (Criteria) this;
        }

        public Criteria andDealUserIdBetween(Integer value1, Integer value2) {
            addCriterion("deal_user_id between", value1, value2, "dealUserId");
            return (Criteria) this;
        }

        public Criteria andDealUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("deal_user_id not between", value1, value2, "dealUserId");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameIsNull() {
            addCriterion("check_file_name is null");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameIsNotNull() {
            addCriterion("check_file_name is not null");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameEqualTo(String value) {
            addCriterion("check_file_name =", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameNotEqualTo(String value) {
            addCriterion("check_file_name <>", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameGreaterThan(String value) {
            addCriterion("check_file_name >", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("check_file_name >=", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameLessThan(String value) {
            addCriterion("check_file_name <", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameLessThanOrEqualTo(String value) {
            addCriterion("check_file_name <=", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameLike(String value) {
            addCriterion("check_file_name like", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameNotLike(String value) {
            addCriterion("check_file_name not like", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameIn(List<String> values) {
            addCriterion("check_file_name in", values, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameNotIn(List<String> values) {
            addCriterion("check_file_name not in", values, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameBetween(String value1, String value2) {
            addCriterion("check_file_name between", value1, value2, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameNotBetween(String value1, String value2) {
            addCriterion("check_file_name not between", value1, value2, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andSysAmountIsNull() {
            addCriterion("sys_amount is null");
            return (Criteria) this;
        }

        public Criteria andSysAmountIsNotNull() {
            addCriterion("sys_amount is not null");
            return (Criteria) this;
        }

        public Criteria andSysAmountEqualTo(Double value) {
            addCriterion("sys_amount =", value, "sysAmount");
            return (Criteria) this;
        }

        public Criteria andSysAmountNotEqualTo(Double value) {
            addCriterion("sys_amount <>", value, "sysAmount");
            return (Criteria) this;
        }

        public Criteria andSysAmountGreaterThan(Double value) {
            addCriterion("sys_amount >", value, "sysAmount");
            return (Criteria) this;
        }

        public Criteria andSysAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("sys_amount >=", value, "sysAmount");
            return (Criteria) this;
        }

        public Criteria andSysAmountLessThan(Double value) {
            addCriterion("sys_amount <", value, "sysAmount");
            return (Criteria) this;
        }

        public Criteria andSysAmountLessThanOrEqualTo(Double value) {
            addCriterion("sys_amount <=", value, "sysAmount");
            return (Criteria) this;
        }

        public Criteria andSysAmountIn(List<Double> values) {
            addCriterion("sys_amount in", values, "sysAmount");
            return (Criteria) this;
        }

        public Criteria andSysAmountNotIn(List<Double> values) {
            addCriterion("sys_amount not in", values, "sysAmount");
            return (Criteria) this;
        }

        public Criteria andSysAmountBetween(Double value1, Double value2) {
            addCriterion("sys_amount between", value1, value2, "sysAmount");
            return (Criteria) this;
        }

        public Criteria andSysAmountNotBetween(Double value1, Double value2) {
            addCriterion("sys_amount not between", value1, value2, "sysAmount");
            return (Criteria) this;
        }

        public Criteria andDoneAmountIsNull() {
            addCriterion("done_amount is null");
            return (Criteria) this;
        }

        public Criteria andDoneAmountIsNotNull() {
            addCriterion("done_amount is not null");
            return (Criteria) this;
        }

        public Criteria andDoneAmountEqualTo(Double value) {
            addCriterion("done_amount =", value, "doneAmount");
            return (Criteria) this;
        }

        public Criteria andDoneAmountNotEqualTo(Double value) {
            addCriterion("done_amount <>", value, "doneAmount");
            return (Criteria) this;
        }

        public Criteria andDoneAmountGreaterThan(Double value) {
            addCriterion("done_amount >", value, "doneAmount");
            return (Criteria) this;
        }

        public Criteria andDoneAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("done_amount >=", value, "doneAmount");
            return (Criteria) this;
        }

        public Criteria andDoneAmountLessThan(Double value) {
            addCriterion("done_amount <", value, "doneAmount");
            return (Criteria) this;
        }

        public Criteria andDoneAmountLessThanOrEqualTo(Double value) {
            addCriterion("done_amount <=", value, "doneAmount");
            return (Criteria) this;
        }

        public Criteria andDoneAmountIn(List<Double> values) {
            addCriterion("done_amount in", values, "doneAmount");
            return (Criteria) this;
        }

        public Criteria andDoneAmountNotIn(List<Double> values) {
            addCriterion("done_amount not in", values, "doneAmount");
            return (Criteria) this;
        }

        public Criteria andDoneAmountBetween(Double value1, Double value2) {
            addCriterion("done_amount between", value1, value2, "doneAmount");
            return (Criteria) this;
        }

        public Criteria andDoneAmountNotBetween(Double value1, Double value2) {
            addCriterion("done_amount not between", value1, value2, "doneAmount");
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

        public Criteria andMerchantNoIsNull() {
            addCriterion("merchant_no is null");
            return (Criteria) this;
        }

        public Criteria andMerchantNoIsNotNull() {
            addCriterion("merchant_no is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantNoEqualTo(String value) {
            addCriterion("merchant_no =", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoNotEqualTo(String value) {
            addCriterion("merchant_no <>", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoGreaterThan(String value) {
            addCriterion("merchant_no >", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoGreaterThanOrEqualTo(String value) {
            addCriterion("merchant_no >=", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoLessThan(String value) {
            addCriterion("merchant_no <", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoLessThanOrEqualTo(String value) {
            addCriterion("merchant_no <=", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoLike(String value) {
            addCriterion("merchant_no like", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoNotLike(String value) {
            addCriterion("merchant_no not like", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoIn(List<String> values) {
            addCriterion("merchant_no in", values, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoNotIn(List<String> values) {
            addCriterion("merchant_no not in", values, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoBetween(String value1, String value2) {
            addCriterion("merchant_no between", value1, value2, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoNotBetween(String value1, String value2) {
            addCriterion("merchant_no not between", value1, value2, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andChannelIsNull() {
            addCriterion("channel is null");
            return (Criteria) this;
        }

        public Criteria andChannelIsNotNull() {
            addCriterion("channel is not null");
            return (Criteria) this;
        }

        public Criteria andChannelEqualTo(String value) {
            addCriterion("channel =", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotEqualTo(String value) {
            addCriterion("channel <>", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThan(String value) {
            addCriterion("channel >", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThanOrEqualTo(String value) {
            addCriterion("channel >=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThan(String value) {
            addCriterion("channel <", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThanOrEqualTo(String value) {
            addCriterion("channel <=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLike(String value) {
            addCriterion("channel like", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotLike(String value) {
            addCriterion("channel not like", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelIn(List<String> values) {
            addCriterion("channel in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotIn(List<String> values) {
            addCriterion("channel not in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelBetween(String value1, String value2) {
            addCriterion("channel between", value1, value2, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotBetween(String value1, String value2) {
            addCriterion("channel not between", value1, value2, "channel");
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

        public Criteria andBfOrderNoIsNull() {
            addCriterion("bf_order_no is null");
            return (Criteria) this;
        }

        public Criteria andBfOrderNoIsNotNull() {
            addCriterion("bf_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andBfOrderNoEqualTo(String value) {
            addCriterion("bf_order_no =", value, "bfOrderNo");
            return (Criteria) this;
        }

        public Criteria andBfOrderNoNotEqualTo(String value) {
            addCriterion("bf_order_no <>", value, "bfOrderNo");
            return (Criteria) this;
        }

        public Criteria andBfOrderNoGreaterThan(String value) {
            addCriterion("bf_order_no >", value, "bfOrderNo");
            return (Criteria) this;
        }

        public Criteria andBfOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("bf_order_no >=", value, "bfOrderNo");
            return (Criteria) this;
        }

        public Criteria andBfOrderNoLessThan(String value) {
            addCriterion("bf_order_no <", value, "bfOrderNo");
            return (Criteria) this;
        }

        public Criteria andBfOrderNoLessThanOrEqualTo(String value) {
            addCriterion("bf_order_no <=", value, "bfOrderNo");
            return (Criteria) this;
        }

        public Criteria andBfOrderNoLike(String value) {
            addCriterion("bf_order_no like", value, "bfOrderNo");
            return (Criteria) this;
        }

        public Criteria andBfOrderNoNotLike(String value) {
            addCriterion("bf_order_no not like", value, "bfOrderNo");
            return (Criteria) this;
        }

        public Criteria andBfOrderNoIn(List<String> values) {
            addCriterion("bf_order_no in", values, "bfOrderNo");
            return (Criteria) this;
        }

        public Criteria andBfOrderNoNotIn(List<String> values) {
            addCriterion("bf_order_no not in", values, "bfOrderNo");
            return (Criteria) this;
        }

        public Criteria andBfOrderNoBetween(String value1, String value2) {
            addCriterion("bf_order_no between", value1, value2, "bfOrderNo");
            return (Criteria) this;
        }

        public Criteria andBfOrderNoNotBetween(String value1, String value2) {
            addCriterion("bf_order_no not between", value1, value2, "bfOrderNo");
            return (Criteria) this;
        }

        public Criteria andHostSysStatusIsNull() {
            addCriterion("host_sys_status is null");
            return (Criteria) this;
        }

        public Criteria andHostSysStatusIsNotNull() {
            addCriterion("host_sys_status is not null");
            return (Criteria) this;
        }

        public Criteria andHostSysStatusEqualTo(String value) {
            addCriterion("host_sys_status =", value, "hostSysStatus");
            return (Criteria) this;
        }

        public Criteria andHostSysStatusNotEqualTo(String value) {
            addCriterion("host_sys_status <>", value, "hostSysStatus");
            return (Criteria) this;
        }

        public Criteria andHostSysStatusGreaterThan(String value) {
            addCriterion("host_sys_status >", value, "hostSysStatus");
            return (Criteria) this;
        }

        public Criteria andHostSysStatusGreaterThanOrEqualTo(String value) {
            addCriterion("host_sys_status >=", value, "hostSysStatus");
            return (Criteria) this;
        }

        public Criteria andHostSysStatusLessThan(String value) {
            addCriterion("host_sys_status <", value, "hostSysStatus");
            return (Criteria) this;
        }

        public Criteria andHostSysStatusLessThanOrEqualTo(String value) {
            addCriterion("host_sys_status <=", value, "hostSysStatus");
            return (Criteria) this;
        }

        public Criteria andHostSysStatusLike(String value) {
            addCriterion("host_sys_status like", value, "hostSysStatus");
            return (Criteria) this;
        }

        public Criteria andHostSysStatusNotLike(String value) {
            addCriterion("host_sys_status not like", value, "hostSysStatus");
            return (Criteria) this;
        }

        public Criteria andHostSysStatusIn(List<String> values) {
            addCriterion("host_sys_status in", values, "hostSysStatus");
            return (Criteria) this;
        }

        public Criteria andHostSysStatusNotIn(List<String> values) {
            addCriterion("host_sys_status not in", values, "hostSysStatus");
            return (Criteria) this;
        }

        public Criteria andHostSysStatusBetween(String value1, String value2) {
            addCriterion("host_sys_status between", value1, value2, "hostSysStatus");
            return (Criteria) this;
        }

        public Criteria andHostSysStatusNotBetween(String value1, String value2) {
            addCriterion("host_sys_status not between", value1, value2, "hostSysStatus");
            return (Criteria) this;
        }

        public Criteria andInfoIsNull() {
            addCriterion("info is null");
            return (Criteria) this;
        }

        public Criteria andInfoIsNotNull() {
            addCriterion("info is not null");
            return (Criteria) this;
        }

        public Criteria andInfoEqualTo(String value) {
            addCriterion("info =", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoNotEqualTo(String value) {
            addCriterion("info <>", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoGreaterThan(String value) {
            addCriterion("info >", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoGreaterThanOrEqualTo(String value) {
            addCriterion("info >=", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoLessThan(String value) {
            addCriterion("info <", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoLessThanOrEqualTo(String value) {
            addCriterion("info <=", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoLike(String value) {
            addCriterion("info like", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoNotLike(String value) {
            addCriterion("info not like", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoIn(List<String> values) {
            addCriterion("info in", values, "info");
            return (Criteria) this;
        }

        public Criteria andInfoNotIn(List<String> values) {
            addCriterion("info not in", values, "info");
            return (Criteria) this;
        }

        public Criteria andInfoBetween(String value1, String value2) {
            addCriterion("info between", value1, value2, "info");
            return (Criteria) this;
        }

        public Criteria andInfoNotBetween(String value1, String value2) {
            addCriterion("info not between", value1, value2, "info");
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