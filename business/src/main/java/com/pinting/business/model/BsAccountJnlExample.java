package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsAccountJnlExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsAccountJnlExample() {
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

        public Criteria andTransTimeIsNull() {
            addCriterion("trans_time is null");
            return (Criteria) this;
        }

        public Criteria andTransTimeIsNotNull() {
            addCriterion("trans_time is not null");
            return (Criteria) this;
        }

        public Criteria andTransTimeEqualTo(Date value) {
            addCriterion("trans_time =", value, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeNotEqualTo(Date value) {
            addCriterion("trans_time <>", value, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeGreaterThan(Date value) {
            addCriterion("trans_time >", value, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("trans_time >=", value, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeLessThan(Date value) {
            addCriterion("trans_time <", value, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeLessThanOrEqualTo(Date value) {
            addCriterion("trans_time <=", value, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeIn(List<Date> values) {
            addCriterion("trans_time in", values, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeNotIn(List<Date> values) {
            addCriterion("trans_time not in", values, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeBetween(Date value1, Date value2) {
            addCriterion("trans_time between", value1, value2, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeNotBetween(Date value1, Date value2) {
            addCriterion("trans_time not between", value1, value2, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransCodeIsNull() {
            addCriterion("trans_code is null");
            return (Criteria) this;
        }

        public Criteria andTransCodeIsNotNull() {
            addCriterion("trans_code is not null");
            return (Criteria) this;
        }

        public Criteria andTransCodeEqualTo(String value) {
            addCriterion("trans_code =", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeNotEqualTo(String value) {
            addCriterion("trans_code <>", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeGreaterThan(String value) {
            addCriterion("trans_code >", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeGreaterThanOrEqualTo(String value) {
            addCriterion("trans_code >=", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeLessThan(String value) {
            addCriterion("trans_code <", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeLessThanOrEqualTo(String value) {
            addCriterion("trans_code <=", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeLike(String value) {
            addCriterion("trans_code like", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeNotLike(String value) {
            addCriterion("trans_code not like", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeIn(List<String> values) {
            addCriterion("trans_code in", values, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeNotIn(List<String> values) {
            addCriterion("trans_code not in", values, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeBetween(String value1, String value2) {
            addCriterion("trans_code between", value1, value2, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeNotBetween(String value1, String value2) {
            addCriterion("trans_code not between", value1, value2, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransTypeIsNull() {
            addCriterion("trans_type is null");
            return (Criteria) this;
        }

        public Criteria andTransTypeIsNotNull() {
            addCriterion("trans_type is not null");
            return (Criteria) this;
        }

        public Criteria andTransTypeEqualTo(Integer value) {
            addCriterion("trans_type =", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotEqualTo(Integer value) {
            addCriterion("trans_type <>", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeGreaterThan(Integer value) {
            addCriterion("trans_type >", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("trans_type >=", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeLessThan(Integer value) {
            addCriterion("trans_type <", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeLessThanOrEqualTo(Integer value) {
            addCriterion("trans_type <=", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeIn(List<Integer> values) {
            addCriterion("trans_type in", values, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotIn(List<Integer> values) {
            addCriterion("trans_type not in", values, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeBetween(Integer value1, Integer value2) {
            addCriterion("trans_type between", value1, value2, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("trans_type not between", value1, value2, "transType");
            return (Criteria) this;
        }

        public Criteria andTransNameIsNull() {
            addCriterion("trans_name is null");
            return (Criteria) this;
        }

        public Criteria andTransNameIsNotNull() {
            addCriterion("trans_name is not null");
            return (Criteria) this;
        }

        public Criteria andTransNameEqualTo(String value) {
            addCriterion("trans_name =", value, "transName");
            return (Criteria) this;
        }

        public Criteria andTransNameNotEqualTo(String value) {
            addCriterion("trans_name <>", value, "transName");
            return (Criteria) this;
        }

        public Criteria andTransNameGreaterThan(String value) {
            addCriterion("trans_name >", value, "transName");
            return (Criteria) this;
        }

        public Criteria andTransNameGreaterThanOrEqualTo(String value) {
            addCriterion("trans_name >=", value, "transName");
            return (Criteria) this;
        }

        public Criteria andTransNameLessThan(String value) {
            addCriterion("trans_name <", value, "transName");
            return (Criteria) this;
        }

        public Criteria andTransNameLessThanOrEqualTo(String value) {
            addCriterion("trans_name <=", value, "transName");
            return (Criteria) this;
        }

        public Criteria andTransNameLike(String value) {
            addCriterion("trans_name like", value, "transName");
            return (Criteria) this;
        }

        public Criteria andTransNameNotLike(String value) {
            addCriterion("trans_name not like", value, "transName");
            return (Criteria) this;
        }

        public Criteria andTransNameIn(List<String> values) {
            addCriterion("trans_name in", values, "transName");
            return (Criteria) this;
        }

        public Criteria andTransNameNotIn(List<String> values) {
            addCriterion("trans_name not in", values, "transName");
            return (Criteria) this;
        }

        public Criteria andTransNameBetween(String value1, String value2) {
            addCriterion("trans_name between", value1, value2, "transName");
            return (Criteria) this;
        }

        public Criteria andTransNameNotBetween(String value1, String value2) {
            addCriterion("trans_name not between", value1, value2, "transName");
            return (Criteria) this;
        }

        public Criteria andTransAmountIsNull() {
            addCriterion("trans_amount is null");
            return (Criteria) this;
        }

        public Criteria andTransAmountIsNotNull() {
            addCriterion("trans_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTransAmountEqualTo(Double value) {
            addCriterion("trans_amount =", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountNotEqualTo(Double value) {
            addCriterion("trans_amount <>", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountGreaterThan(Double value) {
            addCriterion("trans_amount >", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("trans_amount >=", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountLessThan(Double value) {
            addCriterion("trans_amount <", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountLessThanOrEqualTo(Double value) {
            addCriterion("trans_amount <=", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountIn(List<Double> values) {
            addCriterion("trans_amount in", values, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountNotIn(List<Double> values) {
            addCriterion("trans_amount not in", values, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountBetween(Double value1, Double value2) {
            addCriterion("trans_amount between", value1, value2, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountNotBetween(Double value1, Double value2) {
            addCriterion("trans_amount not between", value1, value2, "transAmount");
            return (Criteria) this;
        }

        public Criteria andSysTimeIsNull() {
            addCriterion("sys_time is null");
            return (Criteria) this;
        }

        public Criteria andSysTimeIsNotNull() {
            addCriterion("sys_time is not null");
            return (Criteria) this;
        }

        public Criteria andSysTimeEqualTo(Date value) {
            addCriterion("sys_time =", value, "sysTime");
            return (Criteria) this;
        }

        public Criteria andSysTimeNotEqualTo(Date value) {
            addCriterion("sys_time <>", value, "sysTime");
            return (Criteria) this;
        }

        public Criteria andSysTimeGreaterThan(Date value) {
            addCriterion("sys_time >", value, "sysTime");
            return (Criteria) this;
        }

        public Criteria andSysTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sys_time >=", value, "sysTime");
            return (Criteria) this;
        }

        public Criteria andSysTimeLessThan(Date value) {
            addCriterion("sys_time <", value, "sysTime");
            return (Criteria) this;
        }

        public Criteria andSysTimeLessThanOrEqualTo(Date value) {
            addCriterion("sys_time <=", value, "sysTime");
            return (Criteria) this;
        }

        public Criteria andSysTimeIn(List<Date> values) {
            addCriterion("sys_time in", values, "sysTime");
            return (Criteria) this;
        }

        public Criteria andSysTimeNotIn(List<Date> values) {
            addCriterion("sys_time not in", values, "sysTime");
            return (Criteria) this;
        }

        public Criteria andSysTimeBetween(Date value1, Date value2) {
            addCriterion("sys_time between", value1, value2, "sysTime");
            return (Criteria) this;
        }

        public Criteria andSysTimeNotBetween(Date value1, Date value2) {
            addCriterion("sys_time not between", value1, value2, "sysTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeIsNull() {
            addCriterion("channel_time is null");
            return (Criteria) this;
        }

        public Criteria andChannelTimeIsNotNull() {
            addCriterion("channel_time is not null");
            return (Criteria) this;
        }

        public Criteria andChannelTimeEqualTo(Date value) {
            addCriterion("channel_time =", value, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeNotEqualTo(Date value) {
            addCriterion("channel_time <>", value, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeGreaterThan(Date value) {
            addCriterion("channel_time >", value, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("channel_time >=", value, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeLessThan(Date value) {
            addCriterion("channel_time <", value, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeLessThanOrEqualTo(Date value) {
            addCriterion("channel_time <=", value, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeIn(List<Date> values) {
            addCriterion("channel_time in", values, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeNotIn(List<Date> values) {
            addCriterion("channel_time not in", values, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeBetween(Date value1, Date value2) {
            addCriterion("channel_time between", value1, value2, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeNotBetween(Date value1, Date value2) {
            addCriterion("channel_time not between", value1, value2, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoIsNull() {
            addCriterion("channel_jnl_no is null");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoIsNotNull() {
            addCriterion("channel_jnl_no is not null");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoEqualTo(String value) {
            addCriterion("channel_jnl_no =", value, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoNotEqualTo(String value) {
            addCriterion("channel_jnl_no <>", value, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoGreaterThan(String value) {
            addCriterion("channel_jnl_no >", value, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoGreaterThanOrEqualTo(String value) {
            addCriterion("channel_jnl_no >=", value, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoLessThan(String value) {
            addCriterion("channel_jnl_no <", value, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoLessThanOrEqualTo(String value) {
            addCriterion("channel_jnl_no <=", value, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoLike(String value) {
            addCriterion("channel_jnl_no like", value, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoNotLike(String value) {
            addCriterion("channel_jnl_no not like", value, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoIn(List<String> values) {
            addCriterion("channel_jnl_no in", values, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoNotIn(List<String> values) {
            addCriterion("channel_jnl_no not in", values, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoBetween(String value1, String value2) {
            addCriterion("channel_jnl_no between", value1, value2, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andChannelJnlNoNotBetween(String value1, String value2) {
            addCriterion("channel_jnl_no not between", value1, value2, "channelJnlNo");
            return (Criteria) this;
        }

        public Criteria andCdFlag1IsNull() {
            addCriterion("cd_flag1 is null");
            return (Criteria) this;
        }

        public Criteria andCdFlag1IsNotNull() {
            addCriterion("cd_flag1 is not null");
            return (Criteria) this;
        }

        public Criteria andCdFlag1EqualTo(Integer value) {
            addCriterion("cd_flag1 =", value, "cdFlag1");
            return (Criteria) this;
        }

        public Criteria andCdFlag1NotEqualTo(Integer value) {
            addCriterion("cd_flag1 <>", value, "cdFlag1");
            return (Criteria) this;
        }

        public Criteria andCdFlag1GreaterThan(Integer value) {
            addCriterion("cd_flag1 >", value, "cdFlag1");
            return (Criteria) this;
        }

        public Criteria andCdFlag1GreaterThanOrEqualTo(Integer value) {
            addCriterion("cd_flag1 >=", value, "cdFlag1");
            return (Criteria) this;
        }

        public Criteria andCdFlag1LessThan(Integer value) {
            addCriterion("cd_flag1 <", value, "cdFlag1");
            return (Criteria) this;
        }

        public Criteria andCdFlag1LessThanOrEqualTo(Integer value) {
            addCriterion("cd_flag1 <=", value, "cdFlag1");
            return (Criteria) this;
        }

        public Criteria andCdFlag1In(List<Integer> values) {
            addCriterion("cd_flag1 in", values, "cdFlag1");
            return (Criteria) this;
        }

        public Criteria andCdFlag1NotIn(List<Integer> values) {
            addCriterion("cd_flag1 not in", values, "cdFlag1");
            return (Criteria) this;
        }

        public Criteria andCdFlag1Between(Integer value1, Integer value2) {
            addCriterion("cd_flag1 between", value1, value2, "cdFlag1");
            return (Criteria) this;
        }

        public Criteria andCdFlag1NotBetween(Integer value1, Integer value2) {
            addCriterion("cd_flag1 not between", value1, value2, "cdFlag1");
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

        public Criteria andAccountId1IsNull() {
            addCriterion("account_id1 is null");
            return (Criteria) this;
        }

        public Criteria andAccountId1IsNotNull() {
            addCriterion("account_id1 is not null");
            return (Criteria) this;
        }

        public Criteria andAccountId1EqualTo(Integer value) {
            addCriterion("account_id1 =", value, "accountId1");
            return (Criteria) this;
        }

        public Criteria andAccountId1NotEqualTo(Integer value) {
            addCriterion("account_id1 <>", value, "accountId1");
            return (Criteria) this;
        }

        public Criteria andAccountId1GreaterThan(Integer value) {
            addCriterion("account_id1 >", value, "accountId1");
            return (Criteria) this;
        }

        public Criteria andAccountId1GreaterThanOrEqualTo(Integer value) {
            addCriterion("account_id1 >=", value, "accountId1");
            return (Criteria) this;
        }

        public Criteria andAccountId1LessThan(Integer value) {
            addCriterion("account_id1 <", value, "accountId1");
            return (Criteria) this;
        }

        public Criteria andAccountId1LessThanOrEqualTo(Integer value) {
            addCriterion("account_id1 <=", value, "accountId1");
            return (Criteria) this;
        }

        public Criteria andAccountId1In(List<Integer> values) {
            addCriterion("account_id1 in", values, "accountId1");
            return (Criteria) this;
        }

        public Criteria andAccountId1NotIn(List<Integer> values) {
            addCriterion("account_id1 not in", values, "accountId1");
            return (Criteria) this;
        }

        public Criteria andAccountId1Between(Integer value1, Integer value2) {
            addCriterion("account_id1 between", value1, value2, "accountId1");
            return (Criteria) this;
        }

        public Criteria andAccountId1NotBetween(Integer value1, Integer value2) {
            addCriterion("account_id1 not between", value1, value2, "accountId1");
            return (Criteria) this;
        }

        public Criteria andAccountName1IsNull() {
            addCriterion("account_name1 is null");
            return (Criteria) this;
        }

        public Criteria andAccountName1IsNotNull() {
            addCriterion("account_name1 is not null");
            return (Criteria) this;
        }

        public Criteria andAccountName1EqualTo(String value) {
            addCriterion("account_name1 =", value, "accountName1");
            return (Criteria) this;
        }

        public Criteria andAccountName1NotEqualTo(String value) {
            addCriterion("account_name1 <>", value, "accountName1");
            return (Criteria) this;
        }

        public Criteria andAccountName1GreaterThan(String value) {
            addCriterion("account_name1 >", value, "accountName1");
            return (Criteria) this;
        }

        public Criteria andAccountName1GreaterThanOrEqualTo(String value) {
            addCriterion("account_name1 >=", value, "accountName1");
            return (Criteria) this;
        }

        public Criteria andAccountName1LessThan(String value) {
            addCriterion("account_name1 <", value, "accountName1");
            return (Criteria) this;
        }

        public Criteria andAccountName1LessThanOrEqualTo(String value) {
            addCriterion("account_name1 <=", value, "accountName1");
            return (Criteria) this;
        }

        public Criteria andAccountName1Like(String value) {
            addCriterion("account_name1 like", value, "accountName1");
            return (Criteria) this;
        }

        public Criteria andAccountName1NotLike(String value) {
            addCriterion("account_name1 not like", value, "accountName1");
            return (Criteria) this;
        }

        public Criteria andAccountName1In(List<String> values) {
            addCriterion("account_name1 in", values, "accountName1");
            return (Criteria) this;
        }

        public Criteria andAccountName1NotIn(List<String> values) {
            addCriterion("account_name1 not in", values, "accountName1");
            return (Criteria) this;
        }

        public Criteria andAccountName1Between(String value1, String value2) {
            addCriterion("account_name1 between", value1, value2, "accountName1");
            return (Criteria) this;
        }

        public Criteria andAccountName1NotBetween(String value1, String value2) {
            addCriterion("account_name1 not between", value1, value2, "accountName1");
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

        public Criteria andSubAccountCode1IsNull() {
            addCriterion("sub_account_code1 is null");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode1IsNotNull() {
            addCriterion("sub_account_code1 is not null");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode1EqualTo(String value) {
            addCriterion("sub_account_code1 =", value, "subAccountCode1");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode1NotEqualTo(String value) {
            addCriterion("sub_account_code1 <>", value, "subAccountCode1");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode1GreaterThan(String value) {
            addCriterion("sub_account_code1 >", value, "subAccountCode1");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode1GreaterThanOrEqualTo(String value) {
            addCriterion("sub_account_code1 >=", value, "subAccountCode1");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode1LessThan(String value) {
            addCriterion("sub_account_code1 <", value, "subAccountCode1");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode1LessThanOrEqualTo(String value) {
            addCriterion("sub_account_code1 <=", value, "subAccountCode1");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode1Like(String value) {
            addCriterion("sub_account_code1 like", value, "subAccountCode1");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode1NotLike(String value) {
            addCriterion("sub_account_code1 not like", value, "subAccountCode1");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode1In(List<String> values) {
            addCriterion("sub_account_code1 in", values, "subAccountCode1");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode1NotIn(List<String> values) {
            addCriterion("sub_account_code1 not in", values, "subAccountCode1");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode1Between(String value1, String value2) {
            addCriterion("sub_account_code1 between", value1, value2, "subAccountCode1");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode1NotBetween(String value1, String value2) {
            addCriterion("sub_account_code1 not between", value1, value2, "subAccountCode1");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance1IsNull() {
            addCriterion("before_balance1 is null");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance1IsNotNull() {
            addCriterion("before_balance1 is not null");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance1EqualTo(Double value) {
            addCriterion("before_balance1 =", value, "beforeBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance1NotEqualTo(Double value) {
            addCriterion("before_balance1 <>", value, "beforeBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance1GreaterThan(Double value) {
            addCriterion("before_balance1 >", value, "beforeBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance1GreaterThanOrEqualTo(Double value) {
            addCriterion("before_balance1 >=", value, "beforeBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance1LessThan(Double value) {
            addCriterion("before_balance1 <", value, "beforeBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance1LessThanOrEqualTo(Double value) {
            addCriterion("before_balance1 <=", value, "beforeBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance1In(List<Double> values) {
            addCriterion("before_balance1 in", values, "beforeBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance1NotIn(List<Double> values) {
            addCriterion("before_balance1 not in", values, "beforeBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance1Between(Double value1, Double value2) {
            addCriterion("before_balance1 between", value1, value2, "beforeBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance1NotBetween(Double value1, Double value2) {
            addCriterion("before_balance1 not between", value1, value2, "beforeBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterBalance1IsNull() {
            addCriterion("after_balance1 is null");
            return (Criteria) this;
        }

        public Criteria andAfterBalance1IsNotNull() {
            addCriterion("after_balance1 is not null");
            return (Criteria) this;
        }

        public Criteria andAfterBalance1EqualTo(Double value) {
            addCriterion("after_balance1 =", value, "afterBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterBalance1NotEqualTo(Double value) {
            addCriterion("after_balance1 <>", value, "afterBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterBalance1GreaterThan(Double value) {
            addCriterion("after_balance1 >", value, "afterBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterBalance1GreaterThanOrEqualTo(Double value) {
            addCriterion("after_balance1 >=", value, "afterBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterBalance1LessThan(Double value) {
            addCriterion("after_balance1 <", value, "afterBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterBalance1LessThanOrEqualTo(Double value) {
            addCriterion("after_balance1 <=", value, "afterBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterBalance1In(List<Double> values) {
            addCriterion("after_balance1 in", values, "afterBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterBalance1NotIn(List<Double> values) {
            addCriterion("after_balance1 not in", values, "afterBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterBalance1Between(Double value1, Double value2) {
            addCriterion("after_balance1 between", value1, value2, "afterBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterBalance1NotBetween(Double value1, Double value2) {
            addCriterion("after_balance1 not between", value1, value2, "afterBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance1IsNull() {
            addCriterion("before_avialable_balance1 is null");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance1IsNotNull() {
            addCriterion("before_avialable_balance1 is not null");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance1EqualTo(Double value) {
            addCriterion("before_avialable_balance1 =", value, "beforeAvialableBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance1NotEqualTo(Double value) {
            addCriterion("before_avialable_balance1 <>", value, "beforeAvialableBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance1GreaterThan(Double value) {
            addCriterion("before_avialable_balance1 >", value, "beforeAvialableBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance1GreaterThanOrEqualTo(Double value) {
            addCriterion("before_avialable_balance1 >=", value, "beforeAvialableBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance1LessThan(Double value) {
            addCriterion("before_avialable_balance1 <", value, "beforeAvialableBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance1LessThanOrEqualTo(Double value) {
            addCriterion("before_avialable_balance1 <=", value, "beforeAvialableBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance1In(List<Double> values) {
            addCriterion("before_avialable_balance1 in", values, "beforeAvialableBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance1NotIn(List<Double> values) {
            addCriterion("before_avialable_balance1 not in", values, "beforeAvialableBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance1Between(Double value1, Double value2) {
            addCriterion("before_avialable_balance1 between", value1, value2, "beforeAvialableBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance1NotBetween(Double value1, Double value2) {
            addCriterion("before_avialable_balance1 not between", value1, value2, "beforeAvialableBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance1IsNull() {
            addCriterion("after_avialable_balance1 is null");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance1IsNotNull() {
            addCriterion("after_avialable_balance1 is not null");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance1EqualTo(Double value) {
            addCriterion("after_avialable_balance1 =", value, "afterAvialableBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance1NotEqualTo(Double value) {
            addCriterion("after_avialable_balance1 <>", value, "afterAvialableBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance1GreaterThan(Double value) {
            addCriterion("after_avialable_balance1 >", value, "afterAvialableBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance1GreaterThanOrEqualTo(Double value) {
            addCriterion("after_avialable_balance1 >=", value, "afterAvialableBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance1LessThan(Double value) {
            addCriterion("after_avialable_balance1 <", value, "afterAvialableBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance1LessThanOrEqualTo(Double value) {
            addCriterion("after_avialable_balance1 <=", value, "afterAvialableBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance1In(List<Double> values) {
            addCriterion("after_avialable_balance1 in", values, "afterAvialableBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance1NotIn(List<Double> values) {
            addCriterion("after_avialable_balance1 not in", values, "afterAvialableBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance1Between(Double value1, Double value2) {
            addCriterion("after_avialable_balance1 between", value1, value2, "afterAvialableBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance1NotBetween(Double value1, Double value2) {
            addCriterion("after_avialable_balance1 not between", value1, value2, "afterAvialableBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance1IsNull() {
            addCriterion("before_freeze_balance1 is null");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance1IsNotNull() {
            addCriterion("before_freeze_balance1 is not null");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance1EqualTo(Double value) {
            addCriterion("before_freeze_balance1 =", value, "beforeFreezeBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance1NotEqualTo(Double value) {
            addCriterion("before_freeze_balance1 <>", value, "beforeFreezeBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance1GreaterThan(Double value) {
            addCriterion("before_freeze_balance1 >", value, "beforeFreezeBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance1GreaterThanOrEqualTo(Double value) {
            addCriterion("before_freeze_balance1 >=", value, "beforeFreezeBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance1LessThan(Double value) {
            addCriterion("before_freeze_balance1 <", value, "beforeFreezeBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance1LessThanOrEqualTo(Double value) {
            addCriterion("before_freeze_balance1 <=", value, "beforeFreezeBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance1In(List<Double> values) {
            addCriterion("before_freeze_balance1 in", values, "beforeFreezeBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance1NotIn(List<Double> values) {
            addCriterion("before_freeze_balance1 not in", values, "beforeFreezeBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance1Between(Double value1, Double value2) {
            addCriterion("before_freeze_balance1 between", value1, value2, "beforeFreezeBalance1");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance1NotBetween(Double value1, Double value2) {
            addCriterion("before_freeze_balance1 not between", value1, value2, "beforeFreezeBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance1IsNull() {
            addCriterion("after_freeze_balance1 is null");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance1IsNotNull() {
            addCriterion("after_freeze_balance1 is not null");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance1EqualTo(Double value) {
            addCriterion("after_freeze_balance1 =", value, "afterFreezeBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance1NotEqualTo(Double value) {
            addCriterion("after_freeze_balance1 <>", value, "afterFreezeBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance1GreaterThan(Double value) {
            addCriterion("after_freeze_balance1 >", value, "afterFreezeBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance1GreaterThanOrEqualTo(Double value) {
            addCriterion("after_freeze_balance1 >=", value, "afterFreezeBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance1LessThan(Double value) {
            addCriterion("after_freeze_balance1 <", value, "afterFreezeBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance1LessThanOrEqualTo(Double value) {
            addCriterion("after_freeze_balance1 <=", value, "afterFreezeBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance1In(List<Double> values) {
            addCriterion("after_freeze_balance1 in", values, "afterFreezeBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance1NotIn(List<Double> values) {
            addCriterion("after_freeze_balance1 not in", values, "afterFreezeBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance1Between(Double value1, Double value2) {
            addCriterion("after_freeze_balance1 between", value1, value2, "afterFreezeBalance1");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance1NotBetween(Double value1, Double value2) {
            addCriterion("after_freeze_balance1 not between", value1, value2, "afterFreezeBalance1");
            return (Criteria) this;
        }

        public Criteria andCdFlag2IsNull() {
            addCriterion("cd_flag2 is null");
            return (Criteria) this;
        }

        public Criteria andCdFlag2IsNotNull() {
            addCriterion("cd_flag2 is not null");
            return (Criteria) this;
        }

        public Criteria andCdFlag2EqualTo(Integer value) {
            addCriterion("cd_flag2 =", value, "cdFlag2");
            return (Criteria) this;
        }

        public Criteria andCdFlag2NotEqualTo(Integer value) {
            addCriterion("cd_flag2 <>", value, "cdFlag2");
            return (Criteria) this;
        }

        public Criteria andCdFlag2GreaterThan(Integer value) {
            addCriterion("cd_flag2 >", value, "cdFlag2");
            return (Criteria) this;
        }

        public Criteria andCdFlag2GreaterThanOrEqualTo(Integer value) {
            addCriterion("cd_flag2 >=", value, "cdFlag2");
            return (Criteria) this;
        }

        public Criteria andCdFlag2LessThan(Integer value) {
            addCriterion("cd_flag2 <", value, "cdFlag2");
            return (Criteria) this;
        }

        public Criteria andCdFlag2LessThanOrEqualTo(Integer value) {
            addCriterion("cd_flag2 <=", value, "cdFlag2");
            return (Criteria) this;
        }

        public Criteria andCdFlag2In(List<Integer> values) {
            addCriterion("cd_flag2 in", values, "cdFlag2");
            return (Criteria) this;
        }

        public Criteria andCdFlag2NotIn(List<Integer> values) {
            addCriterion("cd_flag2 not in", values, "cdFlag2");
            return (Criteria) this;
        }

        public Criteria andCdFlag2Between(Integer value1, Integer value2) {
            addCriterion("cd_flag2 between", value1, value2, "cdFlag2");
            return (Criteria) this;
        }

        public Criteria andCdFlag2NotBetween(Integer value1, Integer value2) {
            addCriterion("cd_flag2 not between", value1, value2, "cdFlag2");
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

        public Criteria andAccountId2IsNull() {
            addCriterion("account_id2 is null");
            return (Criteria) this;
        }

        public Criteria andAccountId2IsNotNull() {
            addCriterion("account_id2 is not null");
            return (Criteria) this;
        }

        public Criteria andAccountId2EqualTo(Integer value) {
            addCriterion("account_id2 =", value, "accountId2");
            return (Criteria) this;
        }

        public Criteria andAccountId2NotEqualTo(Integer value) {
            addCriterion("account_id2 <>", value, "accountId2");
            return (Criteria) this;
        }

        public Criteria andAccountId2GreaterThan(Integer value) {
            addCriterion("account_id2 >", value, "accountId2");
            return (Criteria) this;
        }

        public Criteria andAccountId2GreaterThanOrEqualTo(Integer value) {
            addCriterion("account_id2 >=", value, "accountId2");
            return (Criteria) this;
        }

        public Criteria andAccountId2LessThan(Integer value) {
            addCriterion("account_id2 <", value, "accountId2");
            return (Criteria) this;
        }

        public Criteria andAccountId2LessThanOrEqualTo(Integer value) {
            addCriterion("account_id2 <=", value, "accountId2");
            return (Criteria) this;
        }

        public Criteria andAccountId2In(List<Integer> values) {
            addCriterion("account_id2 in", values, "accountId2");
            return (Criteria) this;
        }

        public Criteria andAccountId2NotIn(List<Integer> values) {
            addCriterion("account_id2 not in", values, "accountId2");
            return (Criteria) this;
        }

        public Criteria andAccountId2Between(Integer value1, Integer value2) {
            addCriterion("account_id2 between", value1, value2, "accountId2");
            return (Criteria) this;
        }

        public Criteria andAccountId2NotBetween(Integer value1, Integer value2) {
            addCriterion("account_id2 not between", value1, value2, "accountId2");
            return (Criteria) this;
        }

        public Criteria andAccountName2IsNull() {
            addCriterion("account_name2 is null");
            return (Criteria) this;
        }

        public Criteria andAccountName2IsNotNull() {
            addCriterion("account_name2 is not null");
            return (Criteria) this;
        }

        public Criteria andAccountName2EqualTo(String value) {
            addCriterion("account_name2 =", value, "accountName2");
            return (Criteria) this;
        }

        public Criteria andAccountName2NotEqualTo(String value) {
            addCriterion("account_name2 <>", value, "accountName2");
            return (Criteria) this;
        }

        public Criteria andAccountName2GreaterThan(String value) {
            addCriterion("account_name2 >", value, "accountName2");
            return (Criteria) this;
        }

        public Criteria andAccountName2GreaterThanOrEqualTo(String value) {
            addCriterion("account_name2 >=", value, "accountName2");
            return (Criteria) this;
        }

        public Criteria andAccountName2LessThan(String value) {
            addCriterion("account_name2 <", value, "accountName2");
            return (Criteria) this;
        }

        public Criteria andAccountName2LessThanOrEqualTo(String value) {
            addCriterion("account_name2 <=", value, "accountName2");
            return (Criteria) this;
        }

        public Criteria andAccountName2Like(String value) {
            addCriterion("account_name2 like", value, "accountName2");
            return (Criteria) this;
        }

        public Criteria andAccountName2NotLike(String value) {
            addCriterion("account_name2 not like", value, "accountName2");
            return (Criteria) this;
        }

        public Criteria andAccountName2In(List<String> values) {
            addCriterion("account_name2 in", values, "accountName2");
            return (Criteria) this;
        }

        public Criteria andAccountName2NotIn(List<String> values) {
            addCriterion("account_name2 not in", values, "accountName2");
            return (Criteria) this;
        }

        public Criteria andAccountName2Between(String value1, String value2) {
            addCriterion("account_name2 between", value1, value2, "accountName2");
            return (Criteria) this;
        }

        public Criteria andAccountName2NotBetween(String value1, String value2) {
            addCriterion("account_name2 not between", value1, value2, "accountName2");
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

        public Criteria andSubAccountCode2IsNull() {
            addCriterion("sub_account_code2 is null");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode2IsNotNull() {
            addCriterion("sub_account_code2 is not null");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode2EqualTo(String value) {
            addCriterion("sub_account_code2 =", value, "subAccountCode2");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode2NotEqualTo(String value) {
            addCriterion("sub_account_code2 <>", value, "subAccountCode2");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode2GreaterThan(String value) {
            addCriterion("sub_account_code2 >", value, "subAccountCode2");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode2GreaterThanOrEqualTo(String value) {
            addCriterion("sub_account_code2 >=", value, "subAccountCode2");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode2LessThan(String value) {
            addCriterion("sub_account_code2 <", value, "subAccountCode2");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode2LessThanOrEqualTo(String value) {
            addCriterion("sub_account_code2 <=", value, "subAccountCode2");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode2Like(String value) {
            addCriterion("sub_account_code2 like", value, "subAccountCode2");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode2NotLike(String value) {
            addCriterion("sub_account_code2 not like", value, "subAccountCode2");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode2In(List<String> values) {
            addCriterion("sub_account_code2 in", values, "subAccountCode2");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode2NotIn(List<String> values) {
            addCriterion("sub_account_code2 not in", values, "subAccountCode2");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode2Between(String value1, String value2) {
            addCriterion("sub_account_code2 between", value1, value2, "subAccountCode2");
            return (Criteria) this;
        }

        public Criteria andSubAccountCode2NotBetween(String value1, String value2) {
            addCriterion("sub_account_code2 not between", value1, value2, "subAccountCode2");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance2IsNull() {
            addCriterion("before_balance2 is null");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance2IsNotNull() {
            addCriterion("before_balance2 is not null");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance2EqualTo(Double value) {
            addCriterion("before_balance2 =", value, "beforeBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance2NotEqualTo(Double value) {
            addCriterion("before_balance2 <>", value, "beforeBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance2GreaterThan(Double value) {
            addCriterion("before_balance2 >", value, "beforeBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance2GreaterThanOrEqualTo(Double value) {
            addCriterion("before_balance2 >=", value, "beforeBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance2LessThan(Double value) {
            addCriterion("before_balance2 <", value, "beforeBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance2LessThanOrEqualTo(Double value) {
            addCriterion("before_balance2 <=", value, "beforeBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance2In(List<Double> values) {
            addCriterion("before_balance2 in", values, "beforeBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance2NotIn(List<Double> values) {
            addCriterion("before_balance2 not in", values, "beforeBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance2Between(Double value1, Double value2) {
            addCriterion("before_balance2 between", value1, value2, "beforeBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeBalance2NotBetween(Double value1, Double value2) {
            addCriterion("before_balance2 not between", value1, value2, "beforeBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterBalance2IsNull() {
            addCriterion("after_balance2 is null");
            return (Criteria) this;
        }

        public Criteria andAfterBalance2IsNotNull() {
            addCriterion("after_balance2 is not null");
            return (Criteria) this;
        }

        public Criteria andAfterBalance2EqualTo(Double value) {
            addCriterion("after_balance2 =", value, "afterBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterBalance2NotEqualTo(Double value) {
            addCriterion("after_balance2 <>", value, "afterBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterBalance2GreaterThan(Double value) {
            addCriterion("after_balance2 >", value, "afterBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterBalance2GreaterThanOrEqualTo(Double value) {
            addCriterion("after_balance2 >=", value, "afterBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterBalance2LessThan(Double value) {
            addCriterion("after_balance2 <", value, "afterBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterBalance2LessThanOrEqualTo(Double value) {
            addCriterion("after_balance2 <=", value, "afterBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterBalance2In(List<Double> values) {
            addCriterion("after_balance2 in", values, "afterBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterBalance2NotIn(List<Double> values) {
            addCriterion("after_balance2 not in", values, "afterBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterBalance2Between(Double value1, Double value2) {
            addCriterion("after_balance2 between", value1, value2, "afterBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterBalance2NotBetween(Double value1, Double value2) {
            addCriterion("after_balance2 not between", value1, value2, "afterBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance2IsNull() {
            addCriterion("before_avialable_balance2 is null");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance2IsNotNull() {
            addCriterion("before_avialable_balance2 is not null");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance2EqualTo(Double value) {
            addCriterion("before_avialable_balance2 =", value, "beforeAvialableBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance2NotEqualTo(Double value) {
            addCriterion("before_avialable_balance2 <>", value, "beforeAvialableBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance2GreaterThan(Double value) {
            addCriterion("before_avialable_balance2 >", value, "beforeAvialableBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance2GreaterThanOrEqualTo(Double value) {
            addCriterion("before_avialable_balance2 >=", value, "beforeAvialableBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance2LessThan(Double value) {
            addCriterion("before_avialable_balance2 <", value, "beforeAvialableBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance2LessThanOrEqualTo(Double value) {
            addCriterion("before_avialable_balance2 <=", value, "beforeAvialableBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance2In(List<Double> values) {
            addCriterion("before_avialable_balance2 in", values, "beforeAvialableBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance2NotIn(List<Double> values) {
            addCriterion("before_avialable_balance2 not in", values, "beforeAvialableBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance2Between(Double value1, Double value2) {
            addCriterion("before_avialable_balance2 between", value1, value2, "beforeAvialableBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeAvialableBalance2NotBetween(Double value1, Double value2) {
            addCriterion("before_avialable_balance2 not between", value1, value2, "beforeAvialableBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance2IsNull() {
            addCriterion("after_avialable_balance2 is null");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance2IsNotNull() {
            addCriterion("after_avialable_balance2 is not null");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance2EqualTo(Double value) {
            addCriterion("after_avialable_balance2 =", value, "afterAvialableBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance2NotEqualTo(Double value) {
            addCriterion("after_avialable_balance2 <>", value, "afterAvialableBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance2GreaterThan(Double value) {
            addCriterion("after_avialable_balance2 >", value, "afterAvialableBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance2GreaterThanOrEqualTo(Double value) {
            addCriterion("after_avialable_balance2 >=", value, "afterAvialableBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance2LessThan(Double value) {
            addCriterion("after_avialable_balance2 <", value, "afterAvialableBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance2LessThanOrEqualTo(Double value) {
            addCriterion("after_avialable_balance2 <=", value, "afterAvialableBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance2In(List<Double> values) {
            addCriterion("after_avialable_balance2 in", values, "afterAvialableBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance2NotIn(List<Double> values) {
            addCriterion("after_avialable_balance2 not in", values, "afterAvialableBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance2Between(Double value1, Double value2) {
            addCriterion("after_avialable_balance2 between", value1, value2, "afterAvialableBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterAvialableBalance2NotBetween(Double value1, Double value2) {
            addCriterion("after_avialable_balance2 not between", value1, value2, "afterAvialableBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance2IsNull() {
            addCriterion("before_freeze_balance2 is null");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance2IsNotNull() {
            addCriterion("before_freeze_balance2 is not null");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance2EqualTo(Double value) {
            addCriterion("before_freeze_balance2 =", value, "beforeFreezeBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance2NotEqualTo(Double value) {
            addCriterion("before_freeze_balance2 <>", value, "beforeFreezeBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance2GreaterThan(Double value) {
            addCriterion("before_freeze_balance2 >", value, "beforeFreezeBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance2GreaterThanOrEqualTo(Double value) {
            addCriterion("before_freeze_balance2 >=", value, "beforeFreezeBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance2LessThan(Double value) {
            addCriterion("before_freeze_balance2 <", value, "beforeFreezeBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance2LessThanOrEqualTo(Double value) {
            addCriterion("before_freeze_balance2 <=", value, "beforeFreezeBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance2In(List<Double> values) {
            addCriterion("before_freeze_balance2 in", values, "beforeFreezeBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance2NotIn(List<Double> values) {
            addCriterion("before_freeze_balance2 not in", values, "beforeFreezeBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance2Between(Double value1, Double value2) {
            addCriterion("before_freeze_balance2 between", value1, value2, "beforeFreezeBalance2");
            return (Criteria) this;
        }

        public Criteria andBeforeFreezeBalance2NotBetween(Double value1, Double value2) {
            addCriterion("before_freeze_balance2 not between", value1, value2, "beforeFreezeBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance2IsNull() {
            addCriterion("after_freeze_balance2 is null");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance2IsNotNull() {
            addCriterion("after_freeze_balance2 is not null");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance2EqualTo(Double value) {
            addCriterion("after_freeze_balance2 =", value, "afterFreezeBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance2NotEqualTo(Double value) {
            addCriterion("after_freeze_balance2 <>", value, "afterFreezeBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance2GreaterThan(Double value) {
            addCriterion("after_freeze_balance2 >", value, "afterFreezeBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance2GreaterThanOrEqualTo(Double value) {
            addCriterion("after_freeze_balance2 >=", value, "afterFreezeBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance2LessThan(Double value) {
            addCriterion("after_freeze_balance2 <", value, "afterFreezeBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance2LessThanOrEqualTo(Double value) {
            addCriterion("after_freeze_balance2 <=", value, "afterFreezeBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance2In(List<Double> values) {
            addCriterion("after_freeze_balance2 in", values, "afterFreezeBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance2NotIn(List<Double> values) {
            addCriterion("after_freeze_balance2 not in", values, "afterFreezeBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance2Between(Double value1, Double value2) {
            addCriterion("after_freeze_balance2 between", value1, value2, "afterFreezeBalance2");
            return (Criteria) this;
        }

        public Criteria andAfterFreezeBalance2NotBetween(Double value1, Double value2) {
            addCriterion("after_freeze_balance2 not between", value1, value2, "afterFreezeBalance2");
            return (Criteria) this;
        }

        public Criteria andFeeIsNull() {
            addCriterion("fee is null");
            return (Criteria) this;
        }

        public Criteria andFeeIsNotNull() {
            addCriterion("fee is not null");
            return (Criteria) this;
        }

        public Criteria andFeeEqualTo(Double value) {
            addCriterion("fee =", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotEqualTo(Double value) {
            addCriterion("fee <>", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeGreaterThan(Double value) {
            addCriterion("fee >", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeGreaterThanOrEqualTo(Double value) {
            addCriterion("fee >=", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeLessThan(Double value) {
            addCriterion("fee <", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeLessThanOrEqualTo(Double value) {
            addCriterion("fee <=", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeIn(List<Double> values) {
            addCriterion("fee in", values, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotIn(List<Double> values) {
            addCriterion("fee not in", values, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeBetween(Double value1, Double value2) {
            addCriterion("fee between", value1, value2, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotBetween(Double value1, Double value2) {
            addCriterion("fee not between", value1, value2, "fee");
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

        public Criteria andCheckStatusIsNull() {
            addCriterion("check_status is null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNotNull() {
            addCriterion("check_status is not null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusEqualTo(Integer value) {
            addCriterion("check_status =", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotEqualTo(Integer value) {
            addCriterion("check_status <>", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThan(Integer value) {
            addCriterion("check_status >", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_status >=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThan(Integer value) {
            addCriterion("check_status <", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThanOrEqualTo(Integer value) {
            addCriterion("check_status <=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIn(List<Integer> values) {
            addCriterion("check_status in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotIn(List<Integer> values) {
            addCriterion("check_status not in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusBetween(Integer value1, Integer value2) {
            addCriterion("check_status between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("check_status not between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andIsCheckIsNull() {
            addCriterion("is_check is null");
            return (Criteria) this;
        }

        public Criteria andIsCheckIsNotNull() {
            addCriterion("is_check is not null");
            return (Criteria) this;
        }

        public Criteria andIsCheckEqualTo(Integer value) {
            addCriterion("is_check =", value, "isCheck");
            return (Criteria) this;
        }

        public Criteria andIsCheckNotEqualTo(Integer value) {
            addCriterion("is_check <>", value, "isCheck");
            return (Criteria) this;
        }

        public Criteria andIsCheckGreaterThan(Integer value) {
            addCriterion("is_check >", value, "isCheck");
            return (Criteria) this;
        }

        public Criteria andIsCheckGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_check >=", value, "isCheck");
            return (Criteria) this;
        }

        public Criteria andIsCheckLessThan(Integer value) {
            addCriterion("is_check <", value, "isCheck");
            return (Criteria) this;
        }

        public Criteria andIsCheckLessThanOrEqualTo(Integer value) {
            addCriterion("is_check <=", value, "isCheck");
            return (Criteria) this;
        }

        public Criteria andIsCheckIn(List<Integer> values) {
            addCriterion("is_check in", values, "isCheck");
            return (Criteria) this;
        }

        public Criteria andIsCheckNotIn(List<Integer> values) {
            addCriterion("is_check not in", values, "isCheck");
            return (Criteria) this;
        }

        public Criteria andIsCheckBetween(Integer value1, Integer value2) {
            addCriterion("is_check between", value1, value2, "isCheck");
            return (Criteria) this;
        }

        public Criteria andIsCheckNotBetween(Integer value1, Integer value2) {
            addCriterion("is_check not between", value1, value2, "isCheck");
            return (Criteria) this;
        }

        public Criteria andRespCodeIsNull() {
            addCriterion("resp_code is null");
            return (Criteria) this;
        }

        public Criteria andRespCodeIsNotNull() {
            addCriterion("resp_code is not null");
            return (Criteria) this;
        }

        public Criteria andRespCodeEqualTo(String value) {
            addCriterion("resp_code =", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeNotEqualTo(String value) {
            addCriterion("resp_code <>", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeGreaterThan(String value) {
            addCriterion("resp_code >", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeGreaterThanOrEqualTo(String value) {
            addCriterion("resp_code >=", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeLessThan(String value) {
            addCriterion("resp_code <", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeLessThanOrEqualTo(String value) {
            addCriterion("resp_code <=", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeLike(String value) {
            addCriterion("resp_code like", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeNotLike(String value) {
            addCriterion("resp_code not like", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeIn(List<String> values) {
            addCriterion("resp_code in", values, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeNotIn(List<String> values) {
            addCriterion("resp_code not in", values, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeBetween(String value1, String value2) {
            addCriterion("resp_code between", value1, value2, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeNotBetween(String value1, String value2) {
            addCriterion("resp_code not between", value1, value2, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespMsgIsNull() {
            addCriterion("resp_msg is null");
            return (Criteria) this;
        }

        public Criteria andRespMsgIsNotNull() {
            addCriterion("resp_msg is not null");
            return (Criteria) this;
        }

        public Criteria andRespMsgEqualTo(String value) {
            addCriterion("resp_msg =", value, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgNotEqualTo(String value) {
            addCriterion("resp_msg <>", value, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgGreaterThan(String value) {
            addCriterion("resp_msg >", value, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgGreaterThanOrEqualTo(String value) {
            addCriterion("resp_msg >=", value, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgLessThan(String value) {
            addCriterion("resp_msg <", value, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgLessThanOrEqualTo(String value) {
            addCriterion("resp_msg <=", value, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgLike(String value) {
            addCriterion("resp_msg like", value, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgNotLike(String value) {
            addCriterion("resp_msg not like", value, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgIn(List<String> values) {
            addCriterion("resp_msg in", values, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgNotIn(List<String> values) {
            addCriterion("resp_msg not in", values, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgBetween(String value1, String value2) {
            addCriterion("resp_msg between", value1, value2, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRespMsgNotBetween(String value1, String value2) {
            addCriterion("resp_msg not between", value1, value2, "respMsg");
            return (Criteria) this;
        }

        public Criteria andRelativeJnlIsNull() {
            addCriterion("relative_jnl is null");
            return (Criteria) this;
        }

        public Criteria andRelativeJnlIsNotNull() {
            addCriterion("relative_jnl is not null");
            return (Criteria) this;
        }

        public Criteria andRelativeJnlEqualTo(String value) {
            addCriterion("relative_jnl =", value, "relativeJnl");
            return (Criteria) this;
        }

        public Criteria andRelativeJnlNotEqualTo(String value) {
            addCriterion("relative_jnl <>", value, "relativeJnl");
            return (Criteria) this;
        }

        public Criteria andRelativeJnlGreaterThan(String value) {
            addCriterion("relative_jnl >", value, "relativeJnl");
            return (Criteria) this;
        }

        public Criteria andRelativeJnlGreaterThanOrEqualTo(String value) {
            addCriterion("relative_jnl >=", value, "relativeJnl");
            return (Criteria) this;
        }

        public Criteria andRelativeJnlLessThan(String value) {
            addCriterion("relative_jnl <", value, "relativeJnl");
            return (Criteria) this;
        }

        public Criteria andRelativeJnlLessThanOrEqualTo(String value) {
            addCriterion("relative_jnl <=", value, "relativeJnl");
            return (Criteria) this;
        }

        public Criteria andRelativeJnlLike(String value) {
            addCriterion("relative_jnl like", value, "relativeJnl");
            return (Criteria) this;
        }

        public Criteria andRelativeJnlNotLike(String value) {
            addCriterion("relative_jnl not like", value, "relativeJnl");
            return (Criteria) this;
        }

        public Criteria andRelativeJnlIn(List<String> values) {
            addCriterion("relative_jnl in", values, "relativeJnl");
            return (Criteria) this;
        }

        public Criteria andRelativeJnlNotIn(List<String> values) {
            addCriterion("relative_jnl not in", values, "relativeJnl");
            return (Criteria) this;
        }

        public Criteria andRelativeJnlBetween(String value1, String value2) {
            addCriterion("relative_jnl between", value1, value2, "relativeJnl");
            return (Criteria) this;
        }

        public Criteria andRelativeJnlNotBetween(String value1, String value2) {
            addCriterion("relative_jnl not between", value1, value2, "relativeJnl");
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