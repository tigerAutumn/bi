package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsSmsRecordJnlExample {
    protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public BsSmsRecordJnlExample() {
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

		public Criteria andSendTimeIsNull() {
			addCriterion("send_time is null");
			return (Criteria) this;
		}

		public Criteria andSendTimeIsNotNull() {
			addCriterion("send_time is not null");
			return (Criteria) this;
		}

		public Criteria andSendTimeEqualTo(Date value) {
			addCriterion("send_time =", value, "sendTime");
			return (Criteria) this;
		}

		public Criteria andSendTimeNotEqualTo(Date value) {
			addCriterion("send_time <>", value, "sendTime");
			return (Criteria) this;
		}

		public Criteria andSendTimeGreaterThan(Date value) {
			addCriterion("send_time >", value, "sendTime");
			return (Criteria) this;
		}

		public Criteria andSendTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("send_time >=", value, "sendTime");
			return (Criteria) this;
		}

		public Criteria andSendTimeLessThan(Date value) {
			addCriterion("send_time <", value, "sendTime");
			return (Criteria) this;
		}

		public Criteria andSendTimeLessThanOrEqualTo(Date value) {
			addCriterion("send_time <=", value, "sendTime");
			return (Criteria) this;
		}

		public Criteria andSendTimeIn(List<Date> values) {
			addCriterion("send_time in", values, "sendTime");
			return (Criteria) this;
		}

		public Criteria andSendTimeNotIn(List<Date> values) {
			addCriterion("send_time not in", values, "sendTime");
			return (Criteria) this;
		}

		public Criteria andSendTimeBetween(Date value1, Date value2) {
			addCriterion("send_time between", value1, value2, "sendTime");
			return (Criteria) this;
		}

		public Criteria andSendTimeNotBetween(Date value1, Date value2) {
			addCriterion("send_time not between", value1, value2, "sendTime");
			return (Criteria) this;
		}

		public Criteria andTypeIsNull() {
			addCriterion("type is null");
			return (Criteria) this;
		}

		public Criteria andTypeIsNotNull() {
			addCriterion("type is not null");
			return (Criteria) this;
		}

		public Criteria andTypeEqualTo(String value) {
			addCriterion("type =", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeNotEqualTo(String value) {
			addCriterion("type <>", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeGreaterThan(String value) {
			addCriterion("type >", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeGreaterThanOrEqualTo(String value) {
			addCriterion("type >=", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeLessThan(String value) {
			addCriterion("type <", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeLessThanOrEqualTo(String value) {
			addCriterion("type <=", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeLike(String value) {
			addCriterion("type like", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeNotLike(String value) {
			addCriterion("type not like", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeIn(List<String> values) {
			addCriterion("type in", values, "type");
			return (Criteria) this;
		}

		public Criteria andTypeNotIn(List<String> values) {
			addCriterion("type not in", values, "type");
			return (Criteria) this;
		}

		public Criteria andTypeBetween(String value1, String value2) {
			addCriterion("type between", value1, value2, "type");
			return (Criteria) this;
		}

		public Criteria andTypeNotBetween(String value1, String value2) {
			addCriterion("type not between", value1, value2, "type");
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
			addCriterion("partner_code not between", value1, value2,
					"partnerCode");
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

		public Criteria andArriveTimeIsNull() {
			addCriterion("arrive_time is null");
			return (Criteria) this;
		}

		public Criteria andArriveTimeIsNotNull() {
			addCriterion("arrive_time is not null");
			return (Criteria) this;
		}

		public Criteria andArriveTimeEqualTo(Date value) {
			addCriterion("arrive_time =", value, "arriveTime");
			return (Criteria) this;
		}

		public Criteria andArriveTimeNotEqualTo(Date value) {
			addCriterion("arrive_time <>", value, "arriveTime");
			return (Criteria) this;
		}

		public Criteria andArriveTimeGreaterThan(Date value) {
			addCriterion("arrive_time >", value, "arriveTime");
			return (Criteria) this;
		}

		public Criteria andArriveTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("arrive_time >=", value, "arriveTime");
			return (Criteria) this;
		}

		public Criteria andArriveTimeLessThan(Date value) {
			addCriterion("arrive_time <", value, "arriveTime");
			return (Criteria) this;
		}

		public Criteria andArriveTimeLessThanOrEqualTo(Date value) {
			addCriterion("arrive_time <=", value, "arriveTime");
			return (Criteria) this;
		}

		public Criteria andArriveTimeIn(List<Date> values) {
			addCriterion("arrive_time in", values, "arriveTime");
			return (Criteria) this;
		}

		public Criteria andArriveTimeNotIn(List<Date> values) {
			addCriterion("arrive_time not in", values, "arriveTime");
			return (Criteria) this;
		}

		public Criteria andArriveTimeBetween(Date value1, Date value2) {
			addCriterion("arrive_time between", value1, value2, "arriveTime");
			return (Criteria) this;
		}

		public Criteria andArriveTimeNotBetween(Date value1, Date value2) {
			addCriterion("arrive_time not between", value1, value2,
					"arriveTime");
			return (Criteria) this;
		}

		public Criteria andPlatformsIdIsNull() {
			addCriterion("platforms_id is null");
			return (Criteria) this;
		}

		public Criteria andPlatformsIdIsNotNull() {
			addCriterion("platforms_id is not null");
			return (Criteria) this;
		}

		public Criteria andPlatformsIdEqualTo(Integer value) {
			addCriterion("platforms_id =", value, "platformsId");
			return (Criteria) this;
		}

		public Criteria andPlatformsIdNotEqualTo(Integer value) {
			addCriterion("platforms_id <>", value, "platformsId");
			return (Criteria) this;
		}

		public Criteria andPlatformsIdGreaterThan(Integer value) {
			addCriterion("platforms_id >", value, "platformsId");
			return (Criteria) this;
		}

		public Criteria andPlatformsIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("platforms_id >=", value, "platformsId");
			return (Criteria) this;
		}

		public Criteria andPlatformsIdLessThan(Integer value) {
			addCriterion("platforms_id <", value, "platformsId");
			return (Criteria) this;
		}

		public Criteria andPlatformsIdLessThanOrEqualTo(Integer value) {
			addCriterion("platforms_id <=", value, "platformsId");
			return (Criteria) this;
		}

		public Criteria andPlatformsIdIn(List<Integer> values) {
			addCriterion("platforms_id in", values, "platformsId");
			return (Criteria) this;
		}

		public Criteria andPlatformsIdNotIn(List<Integer> values) {
			addCriterion("platforms_id not in", values, "platformsId");
			return (Criteria) this;
		}

		public Criteria andPlatformsIdBetween(Integer value1, Integer value2) {
			addCriterion("platforms_id between", value1, value2, "platformsId");
			return (Criteria) this;
		}

		public Criteria andPlatformsIdNotBetween(Integer value1, Integer value2) {
			addCriterion("platforms_id not between", value1, value2,
					"platformsId");
			return (Criteria) this;
		}

		public Criteria andStatusCodeIsNull() {
			addCriterion("status_code is null");
			return (Criteria) this;
		}

		public Criteria andStatusCodeIsNotNull() {
			addCriterion("status_code is not null");
			return (Criteria) this;
		}

		public Criteria andStatusCodeEqualTo(Integer value) {
			addCriterion("status_code =", value, "statusCode");
			return (Criteria) this;
		}

		public Criteria andStatusCodeNotEqualTo(Integer value) {
			addCriterion("status_code <>", value, "statusCode");
			return (Criteria) this;
		}

		public Criteria andStatusCodeGreaterThan(Integer value) {
			addCriterion("status_code >", value, "statusCode");
			return (Criteria) this;
		}

		public Criteria andStatusCodeGreaterThanOrEqualTo(Integer value) {
			addCriterion("status_code >=", value, "statusCode");
			return (Criteria) this;
		}

		public Criteria andStatusCodeLessThan(Integer value) {
			addCriterion("status_code <", value, "statusCode");
			return (Criteria) this;
		}

		public Criteria andStatusCodeLessThanOrEqualTo(Integer value) {
			addCriterion("status_code <=", value, "statusCode");
			return (Criteria) this;
		}

		public Criteria andStatusCodeIn(List<Integer> values) {
			addCriterion("status_code in", values, "statusCode");
			return (Criteria) this;
		}

		public Criteria andStatusCodeNotIn(List<Integer> values) {
			addCriterion("status_code not in", values, "statusCode");
			return (Criteria) this;
		}

		public Criteria andStatusCodeBetween(Integer value1, Integer value2) {
			addCriterion("status_code between", value1, value2, "statusCode");
			return (Criteria) this;
		}

		public Criteria andStatusCodeNotBetween(Integer value1, Integer value2) {
			addCriterion("status_code not between", value1, value2,
					"statusCode");
			return (Criteria) this;
		}

		public Criteria andStatusMsgIsNull() {
			addCriterion("status_msg is null");
			return (Criteria) this;
		}

		public Criteria andStatusMsgIsNotNull() {
			addCriterion("status_msg is not null");
			return (Criteria) this;
		}

		public Criteria andStatusMsgEqualTo(String value) {
			addCriterion("status_msg =", value, "statusMsg");
			return (Criteria) this;
		}

		public Criteria andStatusMsgNotEqualTo(String value) {
			addCriterion("status_msg <>", value, "statusMsg");
			return (Criteria) this;
		}

		public Criteria andStatusMsgGreaterThan(String value) {
			addCriterion("status_msg >", value, "statusMsg");
			return (Criteria) this;
		}

		public Criteria andStatusMsgGreaterThanOrEqualTo(String value) {
			addCriterion("status_msg >=", value, "statusMsg");
			return (Criteria) this;
		}

		public Criteria andStatusMsgLessThan(String value) {
			addCriterion("status_msg <", value, "statusMsg");
			return (Criteria) this;
		}

		public Criteria andStatusMsgLessThanOrEqualTo(String value) {
			addCriterion("status_msg <=", value, "statusMsg");
			return (Criteria) this;
		}

		public Criteria andStatusMsgLike(String value) {
			addCriterion("status_msg like", value, "statusMsg");
			return (Criteria) this;
		}

		public Criteria andStatusMsgNotLike(String value) {
			addCriterion("status_msg not like", value, "statusMsg");
			return (Criteria) this;
		}

		public Criteria andStatusMsgIn(List<String> values) {
			addCriterion("status_msg in", values, "statusMsg");
			return (Criteria) this;
		}

		public Criteria andStatusMsgNotIn(List<String> values) {
			addCriterion("status_msg not in", values, "statusMsg");
			return (Criteria) this;
		}

		public Criteria andStatusMsgBetween(String value1, String value2) {
			addCriterion("status_msg between", value1, value2, "statusMsg");
			return (Criteria) this;
		}

		public Criteria andStatusMsgNotBetween(String value1, String value2) {
			addCriterion("status_msg not between", value1, value2, "statusMsg");
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