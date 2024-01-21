package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BsDepCash30Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsDepCash30Example() {
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

        public Criteria andCashDateIsNull() {
            addCriterion("cash_date is null");
            return (Criteria) this;
        }

        public Criteria andCashDateIsNotNull() {
            addCriterion("cash_date is not null");
            return (Criteria) this;
        }

        public Criteria andCashDateEqualTo(Date value) {
            addCriterionForJDBCDate("cash_date =", value, "cashDate");
            return (Criteria) this;
        }

        public Criteria andCashDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("cash_date <>", value, "cashDate");
            return (Criteria) this;
        }

        public Criteria andCashDateGreaterThan(Date value) {
            addCriterionForJDBCDate("cash_date >", value, "cashDate");
            return (Criteria) this;
        }

        public Criteria andCashDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("cash_date >=", value, "cashDate");
            return (Criteria) this;
        }

        public Criteria andCashDateLessThan(Date value) {
            addCriterionForJDBCDate("cash_date <", value, "cashDate");
            return (Criteria) this;
        }

        public Criteria andCashDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("cash_date <=", value, "cashDate");
            return (Criteria) this;
        }

        public Criteria andCashDateIn(List<Date> values) {
            addCriterionForJDBCDate("cash_date in", values, "cashDate");
            return (Criteria) this;
        }

        public Criteria andCashDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("cash_date not in", values, "cashDate");
            return (Criteria) this;
        }

        public Criteria andCashDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("cash_date between", value1, value2, "cashDate");
            return (Criteria) this;
        }

        public Criteria andCashDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("cash_date not between", value1, value2, "cashDate");
            return (Criteria) this;
        }

        public Criteria andQuitPrincipalIsNull() {
            addCriterion("quit_principal is null");
            return (Criteria) this;
        }

        public Criteria andQuitPrincipalIsNotNull() {
            addCriterion("quit_principal is not null");
            return (Criteria) this;
        }

        public Criteria andQuitPrincipalEqualTo(Double value) {
            addCriterion("quit_principal =", value, "quitPrincipal");
            return (Criteria) this;
        }

        public Criteria andQuitPrincipalNotEqualTo(Double value) {
            addCriterion("quit_principal <>", value, "quitPrincipal");
            return (Criteria) this;
        }

        public Criteria andQuitPrincipalGreaterThan(Double value) {
            addCriterion("quit_principal >", value, "quitPrincipal");
            return (Criteria) this;
        }

        public Criteria andQuitPrincipalGreaterThanOrEqualTo(Double value) {
            addCriterion("quit_principal >=", value, "quitPrincipal");
            return (Criteria) this;
        }

        public Criteria andQuitPrincipalLessThan(Double value) {
            addCriterion("quit_principal <", value, "quitPrincipal");
            return (Criteria) this;
        }

        public Criteria andQuitPrincipalLessThanOrEqualTo(Double value) {
            addCriterion("quit_principal <=", value, "quitPrincipal");
            return (Criteria) this;
        }

        public Criteria andQuitPrincipalIn(List<Double> values) {
            addCriterion("quit_principal in", values, "quitPrincipal");
            return (Criteria) this;
        }

        public Criteria andQuitPrincipalNotIn(List<Double> values) {
            addCriterion("quit_principal not in", values, "quitPrincipal");
            return (Criteria) this;
        }

        public Criteria andQuitPrincipalBetween(Double value1, Double value2) {
            addCriterion("quit_principal between", value1, value2, "quitPrincipal");
            return (Criteria) this;
        }

        public Criteria andQuitPrincipalNotBetween(Double value1, Double value2) {
            addCriterion("quit_principal not between", value1, value2, "quitPrincipal");
            return (Criteria) this;
        }

        public Criteria andQuitInterestIsNull() {
            addCriterion("quit_interest is null");
            return (Criteria) this;
        }

        public Criteria andQuitInterestIsNotNull() {
            addCriterion("quit_interest is not null");
            return (Criteria) this;
        }

        public Criteria andQuitInterestEqualTo(Double value) {
            addCriterion("quit_interest =", value, "quitInterest");
            return (Criteria) this;
        }

        public Criteria andQuitInterestNotEqualTo(Double value) {
            addCriterion("quit_interest <>", value, "quitInterest");
            return (Criteria) this;
        }

        public Criteria andQuitInterestGreaterThan(Double value) {
            addCriterion("quit_interest >", value, "quitInterest");
            return (Criteria) this;
        }

        public Criteria andQuitInterestGreaterThanOrEqualTo(Double value) {
            addCriterion("quit_interest >=", value, "quitInterest");
            return (Criteria) this;
        }

        public Criteria andQuitInterestLessThan(Double value) {
            addCriterion("quit_interest <", value, "quitInterest");
            return (Criteria) this;
        }

        public Criteria andQuitInterestLessThanOrEqualTo(Double value) {
            addCriterion("quit_interest <=", value, "quitInterest");
            return (Criteria) this;
        }

        public Criteria andQuitInterestIn(List<Double> values) {
            addCriterion("quit_interest in", values, "quitInterest");
            return (Criteria) this;
        }

        public Criteria andQuitInterestNotIn(List<Double> values) {
            addCriterion("quit_interest not in", values, "quitInterest");
            return (Criteria) this;
        }

        public Criteria andQuitInterestBetween(Double value1, Double value2) {
            addCriterion("quit_interest between", value1, value2, "quitInterest");
            return (Criteria) this;
        }

        public Criteria andQuitInterestNotBetween(Double value1, Double value2) {
            addCriterion("quit_interest not between", value1, value2, "quitInterest");
            return (Criteria) this;
        }

        public Criteria andRepayBalanceIsNull() {
            addCriterion("repay_balance is null");
            return (Criteria) this;
        }

        public Criteria andRepayBalanceIsNotNull() {
            addCriterion("repay_balance is not null");
            return (Criteria) this;
        }

        public Criteria andRepayBalanceEqualTo(Double value) {
            addCriterion("repay_balance =", value, "repayBalance");
            return (Criteria) this;
        }

        public Criteria andRepayBalanceNotEqualTo(Double value) {
            addCriterion("repay_balance <>", value, "repayBalance");
            return (Criteria) this;
        }

        public Criteria andRepayBalanceGreaterThan(Double value) {
            addCriterion("repay_balance >", value, "repayBalance");
            return (Criteria) this;
        }

        public Criteria andRepayBalanceGreaterThanOrEqualTo(Double value) {
            addCriterion("repay_balance >=", value, "repayBalance");
            return (Criteria) this;
        }

        public Criteria andRepayBalanceLessThan(Double value) {
            addCriterion("repay_balance <", value, "repayBalance");
            return (Criteria) this;
        }

        public Criteria andRepayBalanceLessThanOrEqualTo(Double value) {
            addCriterion("repay_balance <=", value, "repayBalance");
            return (Criteria) this;
        }

        public Criteria andRepayBalanceIn(List<Double> values) {
            addCriterion("repay_balance in", values, "repayBalance");
            return (Criteria) this;
        }

        public Criteria andRepayBalanceNotIn(List<Double> values) {
            addCriterion("repay_balance not in", values, "repayBalance");
            return (Criteria) this;
        }

        public Criteria andRepayBalanceBetween(Double value1, Double value2) {
            addCriterion("repay_balance between", value1, value2, "repayBalance");
            return (Criteria) this;
        }

        public Criteria andRepayBalanceNotBetween(Double value1, Double value2) {
            addCriterion("repay_balance not between", value1, value2, "repayBalance");
            return (Criteria) this;
        }

        public Criteria andVipAmountIsNull() {
            addCriterion("vip_amount is null");
            return (Criteria) this;
        }

        public Criteria andVipAmountIsNotNull() {
            addCriterion("vip_amount is not null");
            return (Criteria) this;
        }

        public Criteria andVipAmountEqualTo(Double value) {
            addCriterion("vip_amount =", value, "vipAmount");
            return (Criteria) this;
        }

        public Criteria andVipAmountNotEqualTo(Double value) {
            addCriterion("vip_amount <>", value, "vipAmount");
            return (Criteria) this;
        }

        public Criteria andVipAmountGreaterThan(Double value) {
            addCriterion("vip_amount >", value, "vipAmount");
            return (Criteria) this;
        }

        public Criteria andVipAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("vip_amount >=", value, "vipAmount");
            return (Criteria) this;
        }

        public Criteria andVipAmountLessThan(Double value) {
            addCriterion("vip_amount <", value, "vipAmount");
            return (Criteria) this;
        }

        public Criteria andVipAmountLessThanOrEqualTo(Double value) {
            addCriterion("vip_amount <=", value, "vipAmount");
            return (Criteria) this;
        }

        public Criteria andVipAmountIn(List<Double> values) {
            addCriterion("vip_amount in", values, "vipAmount");
            return (Criteria) this;
        }

        public Criteria andVipAmountNotIn(List<Double> values) {
            addCriterion("vip_amount not in", values, "vipAmount");
            return (Criteria) this;
        }

        public Criteria andVipAmountBetween(Double value1, Double value2) {
            addCriterion("vip_amount between", value1, value2, "vipAmount");
            return (Criteria) this;
        }

        public Criteria andVipAmountNotBetween(Double value1, Double value2) {
            addCriterion("vip_amount not between", value1, value2, "vipAmount");
            return (Criteria) this;
        }

        public Criteria andVipInterestIsNull() {
            addCriterion("vip_interest is null");
            return (Criteria) this;
        }

        public Criteria andVipInterestIsNotNull() {
            addCriterion("vip_interest is not null");
            return (Criteria) this;
        }

        public Criteria andVipInterestEqualTo(Double value) {
            addCriterion("vip_interest =", value, "vipInterest");
            return (Criteria) this;
        }

        public Criteria andVipInterestNotEqualTo(Double value) {
            addCriterion("vip_interest <>", value, "vipInterest");
            return (Criteria) this;
        }

        public Criteria andVipInterestGreaterThan(Double value) {
            addCriterion("vip_interest >", value, "vipInterest");
            return (Criteria) this;
        }

        public Criteria andVipInterestGreaterThanOrEqualTo(Double value) {
            addCriterion("vip_interest >=", value, "vipInterest");
            return (Criteria) this;
        }

        public Criteria andVipInterestLessThan(Double value) {
            addCriterion("vip_interest <", value, "vipInterest");
            return (Criteria) this;
        }

        public Criteria andVipInterestLessThanOrEqualTo(Double value) {
            addCriterion("vip_interest <=", value, "vipInterest");
            return (Criteria) this;
        }

        public Criteria andVipInterestIn(List<Double> values) {
            addCriterion("vip_interest in", values, "vipInterest");
            return (Criteria) this;
        }

        public Criteria andVipInterestNotIn(List<Double> values) {
            addCriterion("vip_interest not in", values, "vipInterest");
            return (Criteria) this;
        }

        public Criteria andVipInterestBetween(Double value1, Double value2) {
            addCriterion("vip_interest between", value1, value2, "vipInterest");
            return (Criteria) this;
        }

        public Criteria andVipInterestNotBetween(Double value1, Double value2) {
            addCriterion("vip_interest not between", value1, value2, "vipInterest");
            return (Criteria) this;
        }

        public Criteria andPrepareBalanceIsNull() {
            addCriterion("prepare_balance is null");
            return (Criteria) this;
        }

        public Criteria andPrepareBalanceIsNotNull() {
            addCriterion("prepare_balance is not null");
            return (Criteria) this;
        }

        public Criteria andPrepareBalanceEqualTo(Double value) {
            addCriterion("prepare_balance =", value, "prepareBalance");
            return (Criteria) this;
        }

        public Criteria andPrepareBalanceNotEqualTo(Double value) {
            addCriterion("prepare_balance <>", value, "prepareBalance");
            return (Criteria) this;
        }

        public Criteria andPrepareBalanceGreaterThan(Double value) {
            addCriterion("prepare_balance >", value, "prepareBalance");
            return (Criteria) this;
        }

        public Criteria andPrepareBalanceGreaterThanOrEqualTo(Double value) {
            addCriterion("prepare_balance >=", value, "prepareBalance");
            return (Criteria) this;
        }

        public Criteria andPrepareBalanceLessThan(Double value) {
            addCriterion("prepare_balance <", value, "prepareBalance");
            return (Criteria) this;
        }

        public Criteria andPrepareBalanceLessThanOrEqualTo(Double value) {
            addCriterion("prepare_balance <=", value, "prepareBalance");
            return (Criteria) this;
        }

        public Criteria andPrepareBalanceIn(List<Double> values) {
            addCriterion("prepare_balance in", values, "prepareBalance");
            return (Criteria) this;
        }

        public Criteria andPrepareBalanceNotIn(List<Double> values) {
            addCriterion("prepare_balance not in", values, "prepareBalance");
            return (Criteria) this;
        }

        public Criteria andPrepareBalanceBetween(Double value1, Double value2) {
            addCriterion("prepare_balance between", value1, value2, "prepareBalance");
            return (Criteria) this;
        }

        public Criteria andPrepareBalanceNotBetween(Double value1, Double value2) {
            addCriterion("prepare_balance not between", value1, value2, "prepareBalance");
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