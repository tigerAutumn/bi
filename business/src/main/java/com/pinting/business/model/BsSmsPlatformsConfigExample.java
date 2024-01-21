package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsSmsPlatformsConfigExample {
    protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public BsSmsPlatformsConfigExample() {
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

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
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

		public Criteria andPlatformsNameIsNull() {
			addCriterion("platforms_name is null");
			return (Criteria) this;
		}

		public Criteria andPlatformsNameIsNotNull() {
			addCriterion("platforms_name is not null");
			return (Criteria) this;
		}

		public Criteria andPlatformsNameEqualTo(String value) {
			addCriterion("platforms_name =", value, "platformsName");
			return (Criteria) this;
		}

		public Criteria andPlatformsNameNotEqualTo(String value) {
			addCriterion("platforms_name <>", value, "platformsName");
			return (Criteria) this;
		}

		public Criteria andPlatformsNameGreaterThan(String value) {
			addCriterion("platforms_name >", value, "platformsName");
			return (Criteria) this;
		}

		public Criteria andPlatformsNameGreaterThanOrEqualTo(String value) {
			addCriterion("platforms_name >=", value, "platformsName");
			return (Criteria) this;
		}

		public Criteria andPlatformsNameLessThan(String value) {
			addCriterion("platforms_name <", value, "platformsName");
			return (Criteria) this;
		}

		public Criteria andPlatformsNameLessThanOrEqualTo(String value) {
			addCriterion("platforms_name <=", value, "platformsName");
			return (Criteria) this;
		}

		public Criteria andPlatformsNameLike(String value) {
			addCriterion("platforms_name like", value, "platformsName");
			return (Criteria) this;
		}

		public Criteria andPlatformsNameNotLike(String value) {
			addCriterion("platforms_name not like", value, "platformsName");
			return (Criteria) this;
		}

		public Criteria andPlatformsNameIn(List<String> values) {
			addCriterion("platforms_name in", values, "platformsName");
			return (Criteria) this;
		}

		public Criteria andPlatformsNameNotIn(List<String> values) {
			addCriterion("platforms_name not in", values, "platformsName");
			return (Criteria) this;
		}

		public Criteria andPlatformsNameBetween(String value1, String value2) {
			addCriterion("platforms_name between", value1, value2,
					"platformsName");
			return (Criteria) this;
		}

		public Criteria andPlatformsNameNotBetween(String value1, String value2) {
			addCriterion("platforms_name not between", value1, value2,
					"platformsName");
			return (Criteria) this;
		}

		public Criteria andPlatformsCodeIsNull() {
			addCriterion("platforms_code is null");
			return (Criteria) this;
		}

		public Criteria andPlatformsCodeIsNotNull() {
			addCriterion("platforms_code is not null");
			return (Criteria) this;
		}

		public Criteria andPlatformsCodeEqualTo(String value) {
			addCriterion("platforms_code =", value, "platformsCode");
			return (Criteria) this;
		}

		public Criteria andPlatformsCodeNotEqualTo(String value) {
			addCriterion("platforms_code <>", value, "platformsCode");
			return (Criteria) this;
		}

		public Criteria andPlatformsCodeGreaterThan(String value) {
			addCriterion("platforms_code >", value, "platformsCode");
			return (Criteria) this;
		}

		public Criteria andPlatformsCodeGreaterThanOrEqualTo(String value) {
			addCriterion("platforms_code >=", value, "platformsCode");
			return (Criteria) this;
		}

		public Criteria andPlatformsCodeLessThan(String value) {
			addCriterion("platforms_code <", value, "platformsCode");
			return (Criteria) this;
		}

		public Criteria andPlatformsCodeLessThanOrEqualTo(String value) {
			addCriterion("platforms_code <=", value, "platformsCode");
			return (Criteria) this;
		}

		public Criteria andPlatformsCodeLike(String value) {
			addCriterion("platforms_code like", value, "platformsCode");
			return (Criteria) this;
		}

		public Criteria andPlatformsCodeNotLike(String value) {
			addCriterion("platforms_code not like", value, "platformsCode");
			return (Criteria) this;
		}

		public Criteria andPlatformsCodeIn(List<String> values) {
			addCriterion("platforms_code in", values, "platformsCode");
			return (Criteria) this;
		}

		public Criteria andPlatformsCodeNotIn(List<String> values) {
			addCriterion("platforms_code not in", values, "platformsCode");
			return (Criteria) this;
		}

		public Criteria andPlatformsCodeBetween(String value1, String value2) {
			addCriterion("platforms_code between", value1, value2,
					"platformsCode");
			return (Criteria) this;
		}

		public Criteria andPlatformsCodeNotBetween(String value1, String value2) {
			addCriterion("platforms_code not between", value1, value2,
					"platformsCode");
			return (Criteria) this;
		}

		public Criteria andPlatformsTypeIsNull() {
			addCriterion("platforms_type is null");
			return (Criteria) this;
		}

		public Criteria andPlatformsTypeIsNotNull() {
			addCriterion("platforms_type is not null");
			return (Criteria) this;
		}

		public Criteria andPlatformsTypeEqualTo(String value) {
			addCriterion("platforms_type =", value, "platformsType");
			return (Criteria) this;
		}

		public Criteria andPlatformsTypeNotEqualTo(String value) {
			addCriterion("platforms_type <>", value, "platformsType");
			return (Criteria) this;
		}

		public Criteria andPlatformsTypeGreaterThan(String value) {
			addCriterion("platforms_type >", value, "platformsType");
			return (Criteria) this;
		}

		public Criteria andPlatformsTypeGreaterThanOrEqualTo(String value) {
			addCriterion("platforms_type >=", value, "platformsType");
			return (Criteria) this;
		}

		public Criteria andPlatformsTypeLessThan(String value) {
			addCriterion("platforms_type <", value, "platformsType");
			return (Criteria) this;
		}

		public Criteria andPlatformsTypeLessThanOrEqualTo(String value) {
			addCriterion("platforms_type <=", value, "platformsType");
			return (Criteria) this;
		}

		public Criteria andPlatformsTypeLike(String value) {
			addCriterion("platforms_type like", value, "platformsType");
			return (Criteria) this;
		}

		public Criteria andPlatformsTypeNotLike(String value) {
			addCriterion("platforms_type not like", value, "platformsType");
			return (Criteria) this;
		}

		public Criteria andPlatformsTypeIn(List<String> values) {
			addCriterion("platforms_type in", values, "platformsType");
			return (Criteria) this;
		}

		public Criteria andPlatformsTypeNotIn(List<String> values) {
			addCriterion("platforms_type not in", values, "platformsType");
			return (Criteria) this;
		}

		public Criteria andPlatformsTypeBetween(String value1, String value2) {
			addCriterion("platforms_type between", value1, value2,
					"platformsType");
			return (Criteria) this;
		}

		public Criteria andPlatformsTypeNotBetween(String value1, String value2) {
			addCriterion("platforms_type not between", value1, value2,
					"platformsType");
			return (Criteria) this;
		}

		public Criteria andPriorityIsNull() {
			addCriterion("priority is null");
			return (Criteria) this;
		}

		public Criteria andPriorityIsNotNull() {
			addCriterion("priority is not null");
			return (Criteria) this;
		}

		public Criteria andPriorityEqualTo(Integer value) {
			addCriterion("priority =", value, "priority");
			return (Criteria) this;
		}

		public Criteria andPriorityNotEqualTo(Integer value) {
			addCriterion("priority <>", value, "priority");
			return (Criteria) this;
		}

		public Criteria andPriorityGreaterThan(Integer value) {
			addCriterion("priority >", value, "priority");
			return (Criteria) this;
		}

		public Criteria andPriorityGreaterThanOrEqualTo(Integer value) {
			addCriterion("priority >=", value, "priority");
			return (Criteria) this;
		}

		public Criteria andPriorityLessThan(Integer value) {
			addCriterion("priority <", value, "priority");
			return (Criteria) this;
		}

		public Criteria andPriorityLessThanOrEqualTo(Integer value) {
			addCriterion("priority <=", value, "priority");
			return (Criteria) this;
		}

		public Criteria andPriorityIn(List<Integer> values) {
			addCriterion("priority in", values, "priority");
			return (Criteria) this;
		}

		public Criteria andPriorityNotIn(List<Integer> values) {
			addCriterion("priority not in", values, "priority");
			return (Criteria) this;
		}

		public Criteria andPriorityBetween(Integer value1, Integer value2) {
			addCriterion("priority between", value1, value2, "priority");
			return (Criteria) this;
		}

		public Criteria andPriorityNotBetween(Integer value1, Integer value2) {
			addCriterion("priority not between", value1, value2, "priority");
			return (Criteria) this;
		}

		public Criteria andPlatformsActIsNull() {
			addCriterion("platforms_act is null");
			return (Criteria) this;
		}

		public Criteria andPlatformsActIsNotNull() {
			addCriterion("platforms_act is not null");
			return (Criteria) this;
		}

		public Criteria andPlatformsActEqualTo(String value) {
			addCriterion("platforms_act =", value, "platformsAct");
			return (Criteria) this;
		}

		public Criteria andPlatformsActNotEqualTo(String value) {
			addCriterion("platforms_act <>", value, "platformsAct");
			return (Criteria) this;
		}

		public Criteria andPlatformsActGreaterThan(String value) {
			addCriterion("platforms_act >", value, "platformsAct");
			return (Criteria) this;
		}

		public Criteria andPlatformsActGreaterThanOrEqualTo(String value) {
			addCriterion("platforms_act >=", value, "platformsAct");
			return (Criteria) this;
		}

		public Criteria andPlatformsActLessThan(String value) {
			addCriterion("platforms_act <", value, "platformsAct");
			return (Criteria) this;
		}

		public Criteria andPlatformsActLessThanOrEqualTo(String value) {
			addCriterion("platforms_act <=", value, "platformsAct");
			return (Criteria) this;
		}

		public Criteria andPlatformsActLike(String value) {
			addCriterion("platforms_act like", value, "platformsAct");
			return (Criteria) this;
		}

		public Criteria andPlatformsActNotLike(String value) {
			addCriterion("platforms_act not like", value, "platformsAct");
			return (Criteria) this;
		}

		public Criteria andPlatformsActIn(List<String> values) {
			addCriterion("platforms_act in", values, "platformsAct");
			return (Criteria) this;
		}

		public Criteria andPlatformsActNotIn(List<String> values) {
			addCriterion("platforms_act not in", values, "platformsAct");
			return (Criteria) this;
		}

		public Criteria andPlatformsActBetween(String value1, String value2) {
			addCriterion("platforms_act between", value1, value2,
					"platformsAct");
			return (Criteria) this;
		}

		public Criteria andPlatformsActNotBetween(String value1, String value2) {
			addCriterion("platforms_act not between", value1, value2,
					"platformsAct");
			return (Criteria) this;
		}

		public Criteria andPlatformsPassIsNull() {
			addCriterion("platforms_pass is null");
			return (Criteria) this;
		}

		public Criteria andPlatformsPassIsNotNull() {
			addCriterion("platforms_pass is not null");
			return (Criteria) this;
		}

		public Criteria andPlatformsPassEqualTo(String value) {
			addCriterion("platforms_pass =", value, "platformsPass");
			return (Criteria) this;
		}

		public Criteria andPlatformsPassNotEqualTo(String value) {
			addCriterion("platforms_pass <>", value, "platformsPass");
			return (Criteria) this;
		}

		public Criteria andPlatformsPassGreaterThan(String value) {
			addCriterion("platforms_pass >", value, "platformsPass");
			return (Criteria) this;
		}

		public Criteria andPlatformsPassGreaterThanOrEqualTo(String value) {
			addCriterion("platforms_pass >=", value, "platformsPass");
			return (Criteria) this;
		}

		public Criteria andPlatformsPassLessThan(String value) {
			addCriterion("platforms_pass <", value, "platformsPass");
			return (Criteria) this;
		}

		public Criteria andPlatformsPassLessThanOrEqualTo(String value) {
			addCriterion("platforms_pass <=", value, "platformsPass");
			return (Criteria) this;
		}

		public Criteria andPlatformsPassLike(String value) {
			addCriterion("platforms_pass like", value, "platformsPass");
			return (Criteria) this;
		}

		public Criteria andPlatformsPassNotLike(String value) {
			addCriterion("platforms_pass not like", value, "platformsPass");
			return (Criteria) this;
		}

		public Criteria andPlatformsPassIn(List<String> values) {
			addCriterion("platforms_pass in", values, "platformsPass");
			return (Criteria) this;
		}

		public Criteria andPlatformsPassNotIn(List<String> values) {
			addCriterion("platforms_pass not in", values, "platformsPass");
			return (Criteria) this;
		}

		public Criteria andPlatformsPassBetween(String value1, String value2) {
			addCriterion("platforms_pass between", value1, value2,
					"platformsPass");
			return (Criteria) this;
		}

		public Criteria andPlatformsPassNotBetween(String value1, String value2) {
			addCriterion("platforms_pass not between", value1, value2,
					"platformsPass");
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
			addCriterion("create_time not between", value1, value2,
					"createTime");
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
			addCriterion("update_time not between", value1, value2,
					"updateTime");
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

		protected Criterion(String condition, Object value, Object secondValue,
				String typeHandler) {
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