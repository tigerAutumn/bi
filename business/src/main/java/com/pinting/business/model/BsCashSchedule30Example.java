package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BsCashSchedule30Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsCashSchedule30Example() {
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

        public Criteria andCashBaseAmountIsNull() {
            addCriterion("cash_base_amount is null");
            return (Criteria) this;
        }

        public Criteria andCashBaseAmountIsNotNull() {
            addCriterion("cash_base_amount is not null");
            return (Criteria) this;
        }

        public Criteria andCashBaseAmountEqualTo(Double value) {
            addCriterion("cash_base_amount =", value, "cashBaseAmount");
            return (Criteria) this;
        }

        public Criteria andCashBaseAmountNotEqualTo(Double value) {
            addCriterion("cash_base_amount <>", value, "cashBaseAmount");
            return (Criteria) this;
        }

        public Criteria andCashBaseAmountGreaterThan(Double value) {
            addCriterion("cash_base_amount >", value, "cashBaseAmount");
            return (Criteria) this;
        }

        public Criteria andCashBaseAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("cash_base_amount >=", value, "cashBaseAmount");
            return (Criteria) this;
        }

        public Criteria andCashBaseAmountLessThan(Double value) {
            addCriterion("cash_base_amount <", value, "cashBaseAmount");
            return (Criteria) this;
        }

        public Criteria andCashBaseAmountLessThanOrEqualTo(Double value) {
            addCriterion("cash_base_amount <=", value, "cashBaseAmount");
            return (Criteria) this;
        }

        public Criteria andCashBaseAmountIn(List<Double> values) {
            addCriterion("cash_base_amount in", values, "cashBaseAmount");
            return (Criteria) this;
        }

        public Criteria andCashBaseAmountNotIn(List<Double> values) {
            addCriterion("cash_base_amount not in", values, "cashBaseAmount");
            return (Criteria) this;
        }

        public Criteria andCashBaseAmountBetween(Double value1, Double value2) {
            addCriterion("cash_base_amount between", value1, value2, "cashBaseAmount");
            return (Criteria) this;
        }

        public Criteria andCashBaseAmountNotBetween(Double value1, Double value2) {
            addCriterion("cash_base_amount not between", value1, value2, "cashBaseAmount");
            return (Criteria) this;
        }

        public Criteria andBashInterestAmountIsNull() {
            addCriterion("bash_interest_amount is null");
            return (Criteria) this;
        }

        public Criteria andBashInterestAmountIsNotNull() {
            addCriterion("bash_interest_amount is not null");
            return (Criteria) this;
        }

        public Criteria andBashInterestAmountEqualTo(Double value) {
            addCriterion("bash_interest_amount =", value, "bashInterestAmount");
            return (Criteria) this;
        }

        public Criteria andBashInterestAmountNotEqualTo(Double value) {
            addCriterion("bash_interest_amount <>", value, "bashInterestAmount");
            return (Criteria) this;
        }

        public Criteria andBashInterestAmountGreaterThan(Double value) {
            addCriterion("bash_interest_amount >", value, "bashInterestAmount");
            return (Criteria) this;
        }

        public Criteria andBashInterestAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("bash_interest_amount >=", value, "bashInterestAmount");
            return (Criteria) this;
        }

        public Criteria andBashInterestAmountLessThan(Double value) {
            addCriterion("bash_interest_amount <", value, "bashInterestAmount");
            return (Criteria) this;
        }

        public Criteria andBashInterestAmountLessThanOrEqualTo(Double value) {
            addCriterion("bash_interest_amount <=", value, "bashInterestAmount");
            return (Criteria) this;
        }

        public Criteria andBashInterestAmountIn(List<Double> values) {
            addCriterion("bash_interest_amount in", values, "bashInterestAmount");
            return (Criteria) this;
        }

        public Criteria andBashInterestAmountNotIn(List<Double> values) {
            addCriterion("bash_interest_amount not in", values, "bashInterestAmount");
            return (Criteria) this;
        }

        public Criteria andBashInterestAmountBetween(Double value1, Double value2) {
            addCriterion("bash_interest_amount between", value1, value2, "bashInterestAmount");
            return (Criteria) this;
        }

        public Criteria andBashInterestAmountNotBetween(Double value1, Double value2) {
            addCriterion("bash_interest_amount not between", value1, value2, "bashInterestAmount");
            return (Criteria) this;
        }

        public Criteria andCashBonusAmountIsNull() {
            addCriterion("cash_bonus_amount is null");
            return (Criteria) this;
        }

        public Criteria andCashBonusAmountIsNotNull() {
            addCriterion("cash_bonus_amount is not null");
            return (Criteria) this;
        }

        public Criteria andCashBonusAmountEqualTo(Double value) {
            addCriterion("cash_bonus_amount =", value, "cashBonusAmount");
            return (Criteria) this;
        }

        public Criteria andCashBonusAmountNotEqualTo(Double value) {
            addCriterion("cash_bonus_amount <>", value, "cashBonusAmount");
            return (Criteria) this;
        }

        public Criteria andCashBonusAmountGreaterThan(Double value) {
            addCriterion("cash_bonus_amount >", value, "cashBonusAmount");
            return (Criteria) this;
        }

        public Criteria andCashBonusAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("cash_bonus_amount >=", value, "cashBonusAmount");
            return (Criteria) this;
        }

        public Criteria andCashBonusAmountLessThan(Double value) {
            addCriterion("cash_bonus_amount <", value, "cashBonusAmount");
            return (Criteria) this;
        }

        public Criteria andCashBonusAmountLessThanOrEqualTo(Double value) {
            addCriterion("cash_bonus_amount <=", value, "cashBonusAmount");
            return (Criteria) this;
        }

        public Criteria andCashBonusAmountIn(List<Double> values) {
            addCriterion("cash_bonus_amount in", values, "cashBonusAmount");
            return (Criteria) this;
        }

        public Criteria andCashBonusAmountNotIn(List<Double> values) {
            addCriterion("cash_bonus_amount not in", values, "cashBonusAmount");
            return (Criteria) this;
        }

        public Criteria andCashBonusAmountBetween(Double value1, Double value2) {
            addCriterion("cash_bonus_amount between", value1, value2, "cashBonusAmount");
            return (Criteria) this;
        }

        public Criteria andCashBonusAmountNotBetween(Double value1, Double value2) {
            addCriterion("cash_bonus_amount not between", value1, value2, "cashBonusAmount");
            return (Criteria) this;
        }
        
        
        public Criteria andQiDaiAmountIsNull() {
            addCriterion("qi_dai_amount is null");
            return (Criteria) this;
        }

        public Criteria andQiDaiAmountIsNotNull() {
            addCriterion("qi_dai_amount is not null");
            return (Criteria) this;
        }

        public Criteria andQiDaiAmountEqualTo(Double value) {
            addCriterion("qi_dai_amount =", value, "qiDaiAmount");
            return (Criteria) this;
        }

        public Criteria andQiDaiAmountNotEqualTo(Double value) {
            addCriterion("qi_dai_amount <>", value, "qiDaiAmount");
            return (Criteria) this;
        }

        public Criteria andQiDaiAmountGreaterThan(Double value) {
            addCriterion("qi_dai_amount >", value, "qiDaiAmount");
            return (Criteria) this;
        }

        public Criteria andQiDaiAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("qi_dai_amount >=", value, "qiDaiAmount");
            return (Criteria) this;
        }

        public Criteria andQiDaiAmountLessThan(Double value) {
            addCriterion("qi_dai_amount <", value, "qiDaiAmount");
            return (Criteria) this;
        }

        public Criteria andQiDaiAmountLessThanOrEqualTo(Double value) {
            addCriterion("qi_dai_amount <=", value, "qiDaiAmount");
            return (Criteria) this;
        }

        public Criteria andQiDaiAmountIn(List<Double> values) {
            addCriterion("qi_dai_amount in", values, "qiDaiAmount");
            return (Criteria) this;
        }

        public Criteria andQiDaiAmountNotIn(List<Double> values) {
            addCriterion("qi_dai_amount not in", values, "qiDaiAmount");
            return (Criteria) this;
        }

        public Criteria andQiDaiAmountBetween(Double value1, Double value2) {
            addCriterion("qi_dai_amount between", value1, value2, "qiDaiAmount");
            return (Criteria) this;
        }

        public Criteria andQiDaiAmountNotBetween(Double value1, Double value2) {
            addCriterion("qi_dai_amount not between", value1, value2, "qiDaiAmount");
            return (Criteria) this;
        }
        
        
        
        public Criteria andYunDaiAmountIsNull() {
            addCriterion("yun_dai_amount is null");
            return (Criteria) this;
        }

        public Criteria andYunDaiAmountIsNotNull() {
            addCriterion("yun_dai_amount is not null");
            return (Criteria) this;
        }

        public Criteria andYunDaiAmountEqualTo(Double value) {
            addCriterion("yun_dai_amount =", value, "yunDaiAmount");
            return (Criteria) this;
        }

        public Criteria andYunDaiAmountNotEqualTo(Double value) {
            addCriterion("yun_dai_amount <>", value, "yunDaiAmount");
            return (Criteria) this;
        }

        public Criteria andYunDaiAmountGreaterThan(Double value) {
            addCriterion("yun_dai_amount >", value, "yunDaiAmount");
            return (Criteria) this;
        }

        public Criteria andYunDaiAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("yun_dai_amount >=", value, "yunDaiAmount");
            return (Criteria) this;
        }

        public Criteria andYunDaiAmountLessThan(Double value) {
            addCriterion("yun_dai_amount <", value, "yunDaiAmount");
            return (Criteria) this;
        }

        public Criteria andYunDaiAmountLessThanOrEqualTo(Double value) {
            addCriterion("yun_dai_amount <=", value, "yunDaiAmount");
            return (Criteria) this;
        }

        public Criteria andYunDaiAmountIn(List<Double> values) {
            addCriterion("yun_dai_amount in", values, "yunDaiAmount");
            return (Criteria) this;
        }

        public Criteria andYunDaiAmountNotIn(List<Double> values) {
            addCriterion("yun_dai_amount not in", values, "yunDaiAmount");
            return (Criteria) this;
        }

        public Criteria andYunDaiAmountBetween(Double value1, Double value2) {
            addCriterion("yun_dai_amount between", value1, value2, "yunDaiAmount");
            return (Criteria) this;
        }

        public Criteria andYunDaiAmountNotBetween(Double value1, Double value2) {
            addCriterion("yun_dai_amount not between", value1, value2, "yunDaiAmount");
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