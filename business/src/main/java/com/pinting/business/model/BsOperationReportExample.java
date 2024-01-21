package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsOperationReportExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsOperationReportExample() {
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

        public Criteria andReportNameIsNull() {
            addCriterion("report_name is null");
            return (Criteria) this;
        }

        public Criteria andReportNameIsNotNull() {
            addCriterion("report_name is not null");
            return (Criteria) this;
        }

        public Criteria andReportNameEqualTo(String value) {
            addCriterion("report_name =", value, "reportName");
            return (Criteria) this;
        }

        public Criteria andReportNameNotEqualTo(String value) {
            addCriterion("report_name <>", value, "reportName");
            return (Criteria) this;
        }

        public Criteria andReportNameGreaterThan(String value) {
            addCriterion("report_name >", value, "reportName");
            return (Criteria) this;
        }

        public Criteria andReportNameGreaterThanOrEqualTo(String value) {
            addCriterion("report_name >=", value, "reportName");
            return (Criteria) this;
        }

        public Criteria andReportNameLessThan(String value) {
            addCriterion("report_name <", value, "reportName");
            return (Criteria) this;
        }

        public Criteria andReportNameLessThanOrEqualTo(String value) {
            addCriterion("report_name <=", value, "reportName");
            return (Criteria) this;
        }

        public Criteria andReportNameLike(String value) {
            addCriterion("report_name like", value, "reportName");
            return (Criteria) this;
        }

        public Criteria andReportNameNotLike(String value) {
            addCriterion("report_name not like", value, "reportName");
            return (Criteria) this;
        }

        public Criteria andReportNameIn(List<String> values) {
            addCriterion("report_name in", values, "reportName");
            return (Criteria) this;
        }

        public Criteria andReportNameNotIn(List<String> values) {
            addCriterion("report_name not in", values, "reportName");
            return (Criteria) this;
        }

        public Criteria andReportNameBetween(String value1, String value2) {
            addCriterion("report_name between", value1, value2, "reportName");
            return (Criteria) this;
        }

        public Criteria andReportNameNotBetween(String value1, String value2) {
            addCriterion("report_name not between", value1, value2, "reportName");
            return (Criteria) this;
        }

        public Criteria andDisplayTimeIsNull() {
            addCriterion("display_time is null");
            return (Criteria) this;
        }

        public Criteria andDisplayTimeIsNotNull() {
            addCriterion("display_time is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayTimeEqualTo(String value) {
            addCriterion("display_time =", value, "displayTime");
            return (Criteria) this;
        }

        public Criteria andDisplayTimeNotEqualTo(String value) {
            addCriterion("display_time <>", value, "displayTime");
            return (Criteria) this;
        }

        public Criteria andDisplayTimeGreaterThan(String value) {
            addCriterion("display_time >", value, "displayTime");
            return (Criteria) this;
        }

        public Criteria andDisplayTimeGreaterThanOrEqualTo(String value) {
            addCriterion("display_time >=", value, "displayTime");
            return (Criteria) this;
        }

        public Criteria andDisplayTimeLessThan(String value) {
            addCriterion("display_time <", value, "displayTime");
            return (Criteria) this;
        }

        public Criteria andDisplayTimeLessThanOrEqualTo(String value) {
            addCriterion("display_time <=", value, "displayTime");
            return (Criteria) this;
        }

        public Criteria andDisplayTimeLike(String value) {
            addCriterion("display_time like", value, "displayTime");
            return (Criteria) this;
        }

        public Criteria andDisplayTimeNotLike(String value) {
            addCriterion("display_time not like", value, "displayTime");
            return (Criteria) this;
        }

        public Criteria andDisplayTimeIn(List<String> values) {
            addCriterion("display_time in", values, "displayTime");
            return (Criteria) this;
        }

        public Criteria andDisplayTimeNotIn(List<String> values) {
            addCriterion("display_time not in", values, "displayTime");
            return (Criteria) this;
        }

        public Criteria andDisplayTimeBetween(String value1, String value2) {
            addCriterion("display_time between", value1, value2, "displayTime");
            return (Criteria) this;
        }

        public Criteria andDisplayTimeNotBetween(String value1, String value2) {
            addCriterion("display_time not between", value1, value2, "displayTime");
            return (Criteria) this;
        }

        public Criteria andImgUrlIsNull() {
            addCriterion("img_url is null");
            return (Criteria) this;
        }

        public Criteria andImgUrlIsNotNull() {
            addCriterion("img_url is not null");
            return (Criteria) this;
        }

        public Criteria andImgUrlEqualTo(String value) {
            addCriterion("img_url =", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlNotEqualTo(String value) {
            addCriterion("img_url <>", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlGreaterThan(String value) {
            addCriterion("img_url >", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlGreaterThanOrEqualTo(String value) {
            addCriterion("img_url >=", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlLessThan(String value) {
            addCriterion("img_url <", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlLessThanOrEqualTo(String value) {
            addCriterion("img_url <=", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlLike(String value) {
            addCriterion("img_url like", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlNotLike(String value) {
            addCriterion("img_url not like", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlIn(List<String> values) {
            addCriterion("img_url in", values, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlNotIn(List<String> values) {
            addCriterion("img_url not in", values, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlBetween(String value1, String value2) {
            addCriterion("img_url between", value1, value2, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlNotBetween(String value1, String value2) {
            addCriterion("img_url not between", value1, value2, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andStorageAddressIsNull() {
            addCriterion("storage_address is null");
            return (Criteria) this;
        }

        public Criteria andStorageAddressIsNotNull() {
            addCriterion("storage_address is not null");
            return (Criteria) this;
        }

        public Criteria andStorageAddressEqualTo(String value) {
            addCriterion("storage_address =", value, "storageAddress");
            return (Criteria) this;
        }

        public Criteria andStorageAddressNotEqualTo(String value) {
            addCriterion("storage_address <>", value, "storageAddress");
            return (Criteria) this;
        }

        public Criteria andStorageAddressGreaterThan(String value) {
            addCriterion("storage_address >", value, "storageAddress");
            return (Criteria) this;
        }

        public Criteria andStorageAddressGreaterThanOrEqualTo(String value) {
            addCriterion("storage_address >=", value, "storageAddress");
            return (Criteria) this;
        }

        public Criteria andStorageAddressLessThan(String value) {
            addCriterion("storage_address <", value, "storageAddress");
            return (Criteria) this;
        }

        public Criteria andStorageAddressLessThanOrEqualTo(String value) {
            addCriterion("storage_address <=", value, "storageAddress");
            return (Criteria) this;
        }

        public Criteria andStorageAddressLike(String value) {
            addCriterion("storage_address like", value, "storageAddress");
            return (Criteria) this;
        }

        public Criteria andStorageAddressNotLike(String value) {
            addCriterion("storage_address not like", value, "storageAddress");
            return (Criteria) this;
        }

        public Criteria andStorageAddressIn(List<String> values) {
            addCriterion("storage_address in", values, "storageAddress");
            return (Criteria) this;
        }

        public Criteria andStorageAddressNotIn(List<String> values) {
            addCriterion("storage_address not in", values, "storageAddress");
            return (Criteria) this;
        }

        public Criteria andStorageAddressBetween(String value1, String value2) {
            addCriterion("storage_address between", value1, value2, "storageAddress");
            return (Criteria) this;
        }

        public Criteria andStorageAddressNotBetween(String value1, String value2) {
            addCriterion("storage_address not between", value1, value2, "storageAddress");
            return (Criteria) this;
        }

        public Criteria andIsSugguestIsNull() {
            addCriterion("is_sugguest is null");
            return (Criteria) this;
        }

        public Criteria andIsSugguestIsNotNull() {
            addCriterion("is_sugguest is not null");
            return (Criteria) this;
        }

        public Criteria andIsSugguestEqualTo(String value) {
            addCriterion("is_sugguest =", value, "isSugguest");
            return (Criteria) this;
        }

        public Criteria andIsSugguestNotEqualTo(String value) {
            addCriterion("is_sugguest <>", value, "isSugguest");
            return (Criteria) this;
        }

        public Criteria andIsSugguestGreaterThan(String value) {
            addCriterion("is_sugguest >", value, "isSugguest");
            return (Criteria) this;
        }

        public Criteria andIsSugguestGreaterThanOrEqualTo(String value) {
            addCriterion("is_sugguest >=", value, "isSugguest");
            return (Criteria) this;
        }

        public Criteria andIsSugguestLessThan(String value) {
            addCriterion("is_sugguest <", value, "isSugguest");
            return (Criteria) this;
        }

        public Criteria andIsSugguestLessThanOrEqualTo(String value) {
            addCriterion("is_sugguest <=", value, "isSugguest");
            return (Criteria) this;
        }

        public Criteria andIsSugguestLike(String value) {
            addCriterion("is_sugguest like", value, "isSugguest");
            return (Criteria) this;
        }

        public Criteria andIsSugguestNotLike(String value) {
            addCriterion("is_sugguest not like", value, "isSugguest");
            return (Criteria) this;
        }

        public Criteria andIsSugguestIn(List<String> values) {
            addCriterion("is_sugguest in", values, "isSugguest");
            return (Criteria) this;
        }

        public Criteria andIsSugguestNotIn(List<String> values) {
            addCriterion("is_sugguest not in", values, "isSugguest");
            return (Criteria) this;
        }

        public Criteria andIsSugguestBetween(String value1, String value2) {
            addCriterion("is_sugguest between", value1, value2, "isSugguest");
            return (Criteria) this;
        }

        public Criteria andIsSugguestNotBetween(String value1, String value2) {
            addCriterion("is_sugguest not between", value1, value2, "isSugguest");
            return (Criteria) this;
        }

        public Criteria andShowTerminalIsNull() {
            addCriterion("show_terminal is null");
            return (Criteria) this;
        }

        public Criteria andShowTerminalIsNotNull() {
            addCriterion("show_terminal is not null");
            return (Criteria) this;
        }

        public Criteria andShowTerminalEqualTo(String value) {
            addCriterion("show_terminal =", value, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalNotEqualTo(String value) {
            addCriterion("show_terminal <>", value, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalGreaterThan(String value) {
            addCriterion("show_terminal >", value, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalGreaterThanOrEqualTo(String value) {
            addCriterion("show_terminal >=", value, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalLessThan(String value) {
            addCriterion("show_terminal <", value, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalLessThanOrEqualTo(String value) {
            addCriterion("show_terminal <=", value, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalLike(String value) {
            addCriterion("show_terminal like", value, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalNotLike(String value) {
            addCriterion("show_terminal not like", value, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalIn(List<String> values) {
            addCriterion("show_terminal in", values, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalNotIn(List<String> values) {
            addCriterion("show_terminal not in", values, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalBetween(String value1, String value2) {
            addCriterion("show_terminal between", value1, value2, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andShowTerminalNotBetween(String value1, String value2) {
            addCriterion("show_terminal not between", value1, value2, "showTerminal");
            return (Criteria) this;
        }

        public Criteria andOpUserIdIsNull() {
            addCriterion("op_user_id is null");
            return (Criteria) this;
        }

        public Criteria andOpUserIdIsNotNull() {
            addCriterion("op_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andOpUserIdEqualTo(Integer value) {
            addCriterion("op_user_id =", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdNotEqualTo(Integer value) {
            addCriterion("op_user_id <>", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdGreaterThan(Integer value) {
            addCriterion("op_user_id >", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("op_user_id >=", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdLessThan(Integer value) {
            addCriterion("op_user_id <", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("op_user_id <=", value, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdIn(List<Integer> values) {
            addCriterion("op_user_id in", values, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdNotIn(List<Integer> values) {
            addCriterion("op_user_id not in", values, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdBetween(Integer value1, Integer value2) {
            addCriterion("op_user_id between", value1, value2, "opUserId");
            return (Criteria) this;
        }

        public Criteria andOpUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("op_user_id not between", value1, value2, "opUserId");
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