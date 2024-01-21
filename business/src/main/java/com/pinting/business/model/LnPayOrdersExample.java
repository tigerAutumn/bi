package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LnPayOrdersExample {
    protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public LnPayOrdersExample() {
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

		public Criteria andPaymentChannelIdIsNull() {
			addCriterion("payment_channel_id is null");
			return (Criteria) this;
		}

		public Criteria andPaymentChannelIdIsNotNull() {
			addCriterion("payment_channel_id is not null");
			return (Criteria) this;
		}

		public Criteria andPaymentChannelIdEqualTo(Integer value) {
			addCriterion("payment_channel_id =", value, "paymentChannelId");
			return (Criteria) this;
		}

		public Criteria andPaymentChannelIdNotEqualTo(Integer value) {
			addCriterion("payment_channel_id <>", value, "paymentChannelId");
			return (Criteria) this;
		}

		public Criteria andPaymentChannelIdGreaterThan(Integer value) {
			addCriterion("payment_channel_id >", value, "paymentChannelId");
			return (Criteria) this;
		}

		public Criteria andPaymentChannelIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("payment_channel_id >=", value, "paymentChannelId");
			return (Criteria) this;
		}

		public Criteria andPaymentChannelIdLessThan(Integer value) {
			addCriterion("payment_channel_id <", value, "paymentChannelId");
			return (Criteria) this;
		}

		public Criteria andPaymentChannelIdLessThanOrEqualTo(Integer value) {
			addCriterion("payment_channel_id <=", value, "paymentChannelId");
			return (Criteria) this;
		}

		public Criteria andPaymentChannelIdIn(List<Integer> values) {
			addCriterion("payment_channel_id in", values, "paymentChannelId");
			return (Criteria) this;
		}

		public Criteria andPaymentChannelIdNotIn(List<Integer> values) {
			addCriterion("payment_channel_id not in", values,
					"paymentChannelId");
			return (Criteria) this;
		}

		public Criteria andPaymentChannelIdBetween(Integer value1,
				Integer value2) {
			addCriterion("payment_channel_id between", value1, value2,
					"paymentChannelId");
			return (Criteria) this;
		}

		public Criteria andPaymentChannelIdNotBetween(Integer value1,
				Integer value2) {
			addCriterion("payment_channel_id not between", value1, value2,
					"paymentChannelId");
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

		public Criteria andAmountIsNull() {
			addCriterion("amount is null");
			return (Criteria) this;
		}

		public Criteria andAmountIsNotNull() {
			addCriterion("amount is not null");
			return (Criteria) this;
		}

		public Criteria andAmountEqualTo(Double value) {
			addCriterion("amount =", value, "amount");
			return (Criteria) this;
		}

		public Criteria andAmountNotEqualTo(Double value) {
			addCriterion("amount <>", value, "amount");
			return (Criteria) this;
		}

		public Criteria andAmountGreaterThan(Double value) {
			addCriterion("amount >", value, "amount");
			return (Criteria) this;
		}

		public Criteria andAmountGreaterThanOrEqualTo(Double value) {
			addCriterion("amount >=", value, "amount");
			return (Criteria) this;
		}

		public Criteria andAmountLessThan(Double value) {
			addCriterion("amount <", value, "amount");
			return (Criteria) this;
		}

		public Criteria andAmountLessThanOrEqualTo(Double value) {
			addCriterion("amount <=", value, "amount");
			return (Criteria) this;
		}

		public Criteria andAmountIn(List<Double> values) {
			addCriterion("amount in", values, "amount");
			return (Criteria) this;
		}

		public Criteria andAmountNotIn(List<Double> values) {
			addCriterion("amount not in", values, "amount");
			return (Criteria) this;
		}

		public Criteria andAmountBetween(Double value1, Double value2) {
			addCriterion("amount between", value1, value2, "amount");
			return (Criteria) this;
		}

		public Criteria andAmountNotBetween(Double value1, Double value2) {
			addCriterion("amount not between", value1, value2, "amount");
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

		public Criteria andLnUserIdIsNull() {
			addCriterion("ln_user_id is null");
			return (Criteria) this;
		}

		public Criteria andLnUserIdIsNotNull() {
			addCriterion("ln_user_id is not null");
			return (Criteria) this;
		}

		public Criteria andLnUserIdEqualTo(Integer value) {
			addCriterion("ln_user_id =", value, "lnUserId");
			return (Criteria) this;
		}

		public Criteria andLnUserIdNotEqualTo(Integer value) {
			addCriterion("ln_user_id <>", value, "lnUserId");
			return (Criteria) this;
		}

		public Criteria andLnUserIdGreaterThan(Integer value) {
			addCriterion("ln_user_id >", value, "lnUserId");
			return (Criteria) this;
		}

		public Criteria andLnUserIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("ln_user_id >=", value, "lnUserId");
			return (Criteria) this;
		}

		public Criteria andLnUserIdLessThan(Integer value) {
			addCriterion("ln_user_id <", value, "lnUserId");
			return (Criteria) this;
		}

		public Criteria andLnUserIdLessThanOrEqualTo(Integer value) {
			addCriterion("ln_user_id <=", value, "lnUserId");
			return (Criteria) this;
		}

		public Criteria andLnUserIdIn(List<Integer> values) {
			addCriterion("ln_user_id in", values, "lnUserId");
			return (Criteria) this;
		}

		public Criteria andLnUserIdNotIn(List<Integer> values) {
			addCriterion("ln_user_id not in", values, "lnUserId");
			return (Criteria) this;
		}

		public Criteria andLnUserIdBetween(Integer value1, Integer value2) {
			addCriterion("ln_user_id between", value1, value2, "lnUserId");
			return (Criteria) this;
		}

		public Criteria andLnUserIdNotBetween(Integer value1, Integer value2) {
			addCriterion("ln_user_id not between", value1, value2, "lnUserId");
			return (Criteria) this;
		}

		public Criteria andSubAccountIdIsNull() {
			addCriterion("sub_account_id is null");
			return (Criteria) this;
		}

		public Criteria andSubAccountIdIsNotNull() {
			addCriterion("sub_account_id is not null");
			return (Criteria) this;
		}

		public Criteria andSubAccountIdEqualTo(Integer value) {
			addCriterion("sub_account_id =", value, "subAccountId");
			return (Criteria) this;
		}

		public Criteria andSubAccountIdNotEqualTo(Integer value) {
			addCriterion("sub_account_id <>", value, "subAccountId");
			return (Criteria) this;
		}

		public Criteria andSubAccountIdGreaterThan(Integer value) {
			addCriterion("sub_account_id >", value, "subAccountId");
			return (Criteria) this;
		}

		public Criteria andSubAccountIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("sub_account_id >=", value, "subAccountId");
			return (Criteria) this;
		}

		public Criteria andSubAccountIdLessThan(Integer value) {
			addCriterion("sub_account_id <", value, "subAccountId");
			return (Criteria) this;
		}

		public Criteria andSubAccountIdLessThanOrEqualTo(Integer value) {
			addCriterion("sub_account_id <=", value, "subAccountId");
			return (Criteria) this;
		}

		public Criteria andSubAccountIdIn(List<Integer> values) {
			addCriterion("sub_account_id in", values, "subAccountId");
			return (Criteria) this;
		}

		public Criteria andSubAccountIdNotIn(List<Integer> values) {
			addCriterion("sub_account_id not in", values, "subAccountId");
			return (Criteria) this;
		}

		public Criteria andSubAccountIdBetween(Integer value1, Integer value2) {
			addCriterion("sub_account_id between", value1, value2,
					"subAccountId");
			return (Criteria) this;
		}

		public Criteria andSubAccountIdNotBetween(Integer value1, Integer value2) {
			addCriterion("sub_account_id not between", value1, value2,
					"subAccountId");
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

		public Criteria andPayPathIsNull() {
			addCriterion("pay_path is null");
			return (Criteria) this;
		}

		public Criteria andPayPathIsNotNull() {
			addCriterion("pay_path is not null");
			return (Criteria) this;
		}

		public Criteria andPayPathEqualTo(String value) {
			addCriterion("pay_path =", value, "payPath");
			return (Criteria) this;
		}

		public Criteria andPayPathNotEqualTo(String value) {
			addCriterion("pay_path <>", value, "payPath");
			return (Criteria) this;
		}

		public Criteria andPayPathGreaterThan(String value) {
			addCriterion("pay_path >", value, "payPath");
			return (Criteria) this;
		}

		public Criteria andPayPathGreaterThanOrEqualTo(String value) {
			addCriterion("pay_path >=", value, "payPath");
			return (Criteria) this;
		}

		public Criteria andPayPathLessThan(String value) {
			addCriterion("pay_path <", value, "payPath");
			return (Criteria) this;
		}

		public Criteria andPayPathLessThanOrEqualTo(String value) {
			addCriterion("pay_path <=", value, "payPath");
			return (Criteria) this;
		}

		public Criteria andPayPathLike(String value) {
			addCriterion("pay_path like", value, "payPath");
			return (Criteria) this;
		}

		public Criteria andPayPathNotLike(String value) {
			addCriterion("pay_path not like", value, "payPath");
			return (Criteria) this;
		}

		public Criteria andPayPathIn(List<String> values) {
			addCriterion("pay_path in", values, "payPath");
			return (Criteria) this;
		}

		public Criteria andPayPathNotIn(List<String> values) {
			addCriterion("pay_path not in", values, "payPath");
			return (Criteria) this;
		}

		public Criteria andPayPathBetween(String value1, String value2) {
			addCriterion("pay_path between", value1, value2, "payPath");
			return (Criteria) this;
		}

		public Criteria andPayPathNotBetween(String value1, String value2) {
			addCriterion("pay_path not between", value1, value2, "payPath");
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

		public Criteria andBankNameIsNull() {
			addCriterion("bank_name is null");
			return (Criteria) this;
		}

		public Criteria andBankNameIsNotNull() {
			addCriterion("bank_name is not null");
			return (Criteria) this;
		}

		public Criteria andBankNameEqualTo(String value) {
			addCriterion("bank_name =", value, "bankName");
			return (Criteria) this;
		}

		public Criteria andBankNameNotEqualTo(String value) {
			addCriterion("bank_name <>", value, "bankName");
			return (Criteria) this;
		}

		public Criteria andBankNameGreaterThan(String value) {
			addCriterion("bank_name >", value, "bankName");
			return (Criteria) this;
		}

		public Criteria andBankNameGreaterThanOrEqualTo(String value) {
			addCriterion("bank_name >=", value, "bankName");
			return (Criteria) this;
		}

		public Criteria andBankNameLessThan(String value) {
			addCriterion("bank_name <", value, "bankName");
			return (Criteria) this;
		}

		public Criteria andBankNameLessThanOrEqualTo(String value) {
			addCriterion("bank_name <=", value, "bankName");
			return (Criteria) this;
		}

		public Criteria andBankNameLike(String value) {
			addCriterion("bank_name like", value, "bankName");
			return (Criteria) this;
		}

		public Criteria andBankNameNotLike(String value) {
			addCriterion("bank_name not like", value, "bankName");
			return (Criteria) this;
		}

		public Criteria andBankNameIn(List<String> values) {
			addCriterion("bank_name in", values, "bankName");
			return (Criteria) this;
		}

		public Criteria andBankNameNotIn(List<String> values) {
			addCriterion("bank_name not in", values, "bankName");
			return (Criteria) this;
		}

		public Criteria andBankNameBetween(String value1, String value2) {
			addCriterion("bank_name between", value1, value2, "bankName");
			return (Criteria) this;
		}

		public Criteria andBankNameNotBetween(String value1, String value2) {
			addCriterion("bank_name not between", value1, value2, "bankName");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeIsNull() {
			addCriterion("money_type is null");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeIsNotNull() {
			addCriterion("money_type is not null");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeEqualTo(Integer value) {
			addCriterion("money_type =", value, "moneyType");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeNotEqualTo(Integer value) {
			addCriterion("money_type <>", value, "moneyType");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeGreaterThan(Integer value) {
			addCriterion("money_type >", value, "moneyType");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeGreaterThanOrEqualTo(Integer value) {
			addCriterion("money_type >=", value, "moneyType");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeLessThan(Integer value) {
			addCriterion("money_type <", value, "moneyType");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeLessThanOrEqualTo(Integer value) {
			addCriterion("money_type <=", value, "moneyType");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeIn(List<Integer> values) {
			addCriterion("money_type in", values, "moneyType");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeNotIn(List<Integer> values) {
			addCriterion("money_type not in", values, "moneyType");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeBetween(Integer value1, Integer value2) {
			addCriterion("money_type between", value1, value2, "moneyType");
			return (Criteria) this;
		}

		public Criteria andMoneyTypeNotBetween(Integer value1, Integer value2) {
			addCriterion("money_type not between", value1, value2, "moneyType");
			return (Criteria) this;
		}

		public Criteria andTerminalTypeIsNull() {
			addCriterion("terminal_type is null");
			return (Criteria) this;
		}

		public Criteria andTerminalTypeIsNotNull() {
			addCriterion("terminal_type is not null");
			return (Criteria) this;
		}

		public Criteria andTerminalTypeEqualTo(Integer value) {
			addCriterion("terminal_type =", value, "terminalType");
			return (Criteria) this;
		}

		public Criteria andTerminalTypeNotEqualTo(Integer value) {
			addCriterion("terminal_type <>", value, "terminalType");
			return (Criteria) this;
		}

		public Criteria andTerminalTypeGreaterThan(Integer value) {
			addCriterion("terminal_type >", value, "terminalType");
			return (Criteria) this;
		}

		public Criteria andTerminalTypeGreaterThanOrEqualTo(Integer value) {
			addCriterion("terminal_type >=", value, "terminalType");
			return (Criteria) this;
		}

		public Criteria andTerminalTypeLessThan(Integer value) {
			addCriterion("terminal_type <", value, "terminalType");
			return (Criteria) this;
		}

		public Criteria andTerminalTypeLessThanOrEqualTo(Integer value) {
			addCriterion("terminal_type <=", value, "terminalType");
			return (Criteria) this;
		}

		public Criteria andTerminalTypeIn(List<Integer> values) {
			addCriterion("terminal_type in", values, "terminalType");
			return (Criteria) this;
		}

		public Criteria andTerminalTypeNotIn(List<Integer> values) {
			addCriterion("terminal_type not in", values, "terminalType");
			return (Criteria) this;
		}

		public Criteria andTerminalTypeBetween(Integer value1, Integer value2) {
			addCriterion("terminal_type between", value1, value2,
					"terminalType");
			return (Criteria) this;
		}

		public Criteria andTerminalTypeNotBetween(Integer value1, Integer value2) {
			addCriterion("terminal_type not between", value1, value2,
					"terminalType");
			return (Criteria) this;
		}

		public Criteria andStartJnlNoIsNull() {
			addCriterion("start_jnl_no is null");
			return (Criteria) this;
		}

		public Criteria andStartJnlNoIsNotNull() {
			addCriterion("start_jnl_no is not null");
			return (Criteria) this;
		}

		public Criteria andStartJnlNoEqualTo(Integer value) {
			addCriterion("start_jnl_no =", value, "startJnlNo");
			return (Criteria) this;
		}

		public Criteria andStartJnlNoNotEqualTo(Integer value) {
			addCriterion("start_jnl_no <>", value, "startJnlNo");
			return (Criteria) this;
		}

		public Criteria andStartJnlNoGreaterThan(Integer value) {
			addCriterion("start_jnl_no >", value, "startJnlNo");
			return (Criteria) this;
		}

		public Criteria andStartJnlNoGreaterThanOrEqualTo(Integer value) {
			addCriterion("start_jnl_no >=", value, "startJnlNo");
			return (Criteria) this;
		}

		public Criteria andStartJnlNoLessThan(Integer value) {
			addCriterion("start_jnl_no <", value, "startJnlNo");
			return (Criteria) this;
		}

		public Criteria andStartJnlNoLessThanOrEqualTo(Integer value) {
			addCriterion("start_jnl_no <=", value, "startJnlNo");
			return (Criteria) this;
		}

		public Criteria andStartJnlNoIn(List<Integer> values) {
			addCriterion("start_jnl_no in", values, "startJnlNo");
			return (Criteria) this;
		}

		public Criteria andStartJnlNoNotIn(List<Integer> values) {
			addCriterion("start_jnl_no not in", values, "startJnlNo");
			return (Criteria) this;
		}

		public Criteria andStartJnlNoBetween(Integer value1, Integer value2) {
			addCriterion("start_jnl_no between", value1, value2, "startJnlNo");
			return (Criteria) this;
		}

		public Criteria andStartJnlNoNotBetween(Integer value1, Integer value2) {
			addCriterion("start_jnl_no not between", value1, value2,
					"startJnlNo");
			return (Criteria) this;
		}

		public Criteria andEndJnlNoIsNull() {
			addCriterion("end_jnl_no is null");
			return (Criteria) this;
		}

		public Criteria andEndJnlNoIsNotNull() {
			addCriterion("end_jnl_no is not null");
			return (Criteria) this;
		}

		public Criteria andEndJnlNoEqualTo(Integer value) {
			addCriterion("end_jnl_no =", value, "endJnlNo");
			return (Criteria) this;
		}

		public Criteria andEndJnlNoNotEqualTo(Integer value) {
			addCriterion("end_jnl_no <>", value, "endJnlNo");
			return (Criteria) this;
		}

		public Criteria andEndJnlNoGreaterThan(Integer value) {
			addCriterion("end_jnl_no >", value, "endJnlNo");
			return (Criteria) this;
		}

		public Criteria andEndJnlNoGreaterThanOrEqualTo(Integer value) {
			addCriterion("end_jnl_no >=", value, "endJnlNo");
			return (Criteria) this;
		}

		public Criteria andEndJnlNoLessThan(Integer value) {
			addCriterion("end_jnl_no <", value, "endJnlNo");
			return (Criteria) this;
		}

		public Criteria andEndJnlNoLessThanOrEqualTo(Integer value) {
			addCriterion("end_jnl_no <=", value, "endJnlNo");
			return (Criteria) this;
		}

		public Criteria andEndJnlNoIn(List<Integer> values) {
			addCriterion("end_jnl_no in", values, "endJnlNo");
			return (Criteria) this;
		}

		public Criteria andEndJnlNoNotIn(List<Integer> values) {
			addCriterion("end_jnl_no not in", values, "endJnlNo");
			return (Criteria) this;
		}

		public Criteria andEndJnlNoBetween(Integer value1, Integer value2) {
			addCriterion("end_jnl_no between", value1, value2, "endJnlNo");
			return (Criteria) this;
		}

		public Criteria andEndJnlNoNotBetween(Integer value1, Integer value2) {
			addCriterion("end_jnl_no not between", value1, value2, "endJnlNo");
			return (Criteria) this;
		}

		public Criteria andBankIdIsNull() {
			addCriterion("bank_id is null");
			return (Criteria) this;
		}

		public Criteria andBankIdIsNotNull() {
			addCriterion("bank_id is not null");
			return (Criteria) this;
		}

		public Criteria andBankIdEqualTo(Integer value) {
			addCriterion("bank_id =", value, "bankId");
			return (Criteria) this;
		}

		public Criteria andBankIdNotEqualTo(Integer value) {
			addCriterion("bank_id <>", value, "bankId");
			return (Criteria) this;
		}

		public Criteria andBankIdGreaterThan(Integer value) {
			addCriterion("bank_id >", value, "bankId");
			return (Criteria) this;
		}

		public Criteria andBankIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("bank_id >=", value, "bankId");
			return (Criteria) this;
		}

		public Criteria andBankIdLessThan(Integer value) {
			addCriterion("bank_id <", value, "bankId");
			return (Criteria) this;
		}

		public Criteria andBankIdLessThanOrEqualTo(Integer value) {
			addCriterion("bank_id <=", value, "bankId");
			return (Criteria) this;
		}

		public Criteria andBankIdIn(List<Integer> values) {
			addCriterion("bank_id in", values, "bankId");
			return (Criteria) this;
		}

		public Criteria andBankIdNotIn(List<Integer> values) {
			addCriterion("bank_id not in", values, "bankId");
			return (Criteria) this;
		}

		public Criteria andBankIdBetween(Integer value1, Integer value2) {
			addCriterion("bank_id between", value1, value2, "bankId");
			return (Criteria) this;
		}

		public Criteria andBankIdNotBetween(Integer value1, Integer value2) {
			addCriterion("bank_id not between", value1, value2, "bankId");
			return (Criteria) this;
		}

		public Criteria andBankCardNoIsNull() {
			addCriterion("bank_card_no is null");
			return (Criteria) this;
		}

		public Criteria andBankCardNoIsNotNull() {
			addCriterion("bank_card_no is not null");
			return (Criteria) this;
		}

		public Criteria andBankCardNoEqualTo(String value) {
			addCriterion("bank_card_no =", value, "bankCardNo");
			return (Criteria) this;
		}

		public Criteria andBankCardNoNotEqualTo(String value) {
			addCriterion("bank_card_no <>", value, "bankCardNo");
			return (Criteria) this;
		}

		public Criteria andBankCardNoGreaterThan(String value) {
			addCriterion("bank_card_no >", value, "bankCardNo");
			return (Criteria) this;
		}

		public Criteria andBankCardNoGreaterThanOrEqualTo(String value) {
			addCriterion("bank_card_no >=", value, "bankCardNo");
			return (Criteria) this;
		}

		public Criteria andBankCardNoLessThan(String value) {
			addCriterion("bank_card_no <", value, "bankCardNo");
			return (Criteria) this;
		}

		public Criteria andBankCardNoLessThanOrEqualTo(String value) {
			addCriterion("bank_card_no <=", value, "bankCardNo");
			return (Criteria) this;
		}

		public Criteria andBankCardNoLike(String value) {
			addCriterion("bank_card_no like", value, "bankCardNo");
			return (Criteria) this;
		}

		public Criteria andBankCardNoNotLike(String value) {
			addCriterion("bank_card_no not like", value, "bankCardNo");
			return (Criteria) this;
		}

		public Criteria andBankCardNoIn(List<String> values) {
			addCriterion("bank_card_no in", values, "bankCardNo");
			return (Criteria) this;
		}

		public Criteria andBankCardNoNotIn(List<String> values) {
			addCriterion("bank_card_no not in", values, "bankCardNo");
			return (Criteria) this;
		}

		public Criteria andBankCardNoBetween(String value1, String value2) {
			addCriterion("bank_card_no between", value1, value2, "bankCardNo");
			return (Criteria) this;
		}

		public Criteria andBankCardNoNotBetween(String value1, String value2) {
			addCriterion("bank_card_no not between", value1, value2,
					"bankCardNo");
			return (Criteria) this;
		}

		public Criteria andAccountTypeIsNull() {
			addCriterion("account_type is null");
			return (Criteria) this;
		}

		public Criteria andAccountTypeIsNotNull() {
			addCriterion("account_type is not null");
			return (Criteria) this;
		}

		public Criteria andAccountTypeEqualTo(Integer value) {
			addCriterion("account_type =", value, "accountType");
			return (Criteria) this;
		}

		public Criteria andAccountTypeNotEqualTo(Integer value) {
			addCriterion("account_type <>", value, "accountType");
			return (Criteria) this;
		}

		public Criteria andAccountTypeGreaterThan(Integer value) {
			addCriterion("account_type >", value, "accountType");
			return (Criteria) this;
		}

		public Criteria andAccountTypeGreaterThanOrEqualTo(Integer value) {
			addCriterion("account_type >=", value, "accountType");
			return (Criteria) this;
		}

		public Criteria andAccountTypeLessThan(Integer value) {
			addCriterion("account_type <", value, "accountType");
			return (Criteria) this;
		}

		public Criteria andAccountTypeLessThanOrEqualTo(Integer value) {
			addCriterion("account_type <=", value, "accountType");
			return (Criteria) this;
		}

		public Criteria andAccountTypeIn(List<Integer> values) {
			addCriterion("account_type in", values, "accountType");
			return (Criteria) this;
		}

		public Criteria andAccountTypeNotIn(List<Integer> values) {
			addCriterion("account_type not in", values, "accountType");
			return (Criteria) this;
		}

		public Criteria andAccountTypeBetween(Integer value1, Integer value2) {
			addCriterion("account_type between", value1, value2, "accountType");
			return (Criteria) this;
		}

		public Criteria andAccountTypeNotBetween(Integer value1, Integer value2) {
			addCriterion("account_type not between", value1, value2,
					"accountType");
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

		public Criteria andTransTypeEqualTo(String value) {
			addCriterion("trans_type =", value, "transType");
			return (Criteria) this;
		}

		public Criteria andTransTypeNotEqualTo(String value) {
			addCriterion("trans_type <>", value, "transType");
			return (Criteria) this;
		}

		public Criteria andTransTypeGreaterThan(String value) {
			addCriterion("trans_type >", value, "transType");
			return (Criteria) this;
		}

		public Criteria andTransTypeGreaterThanOrEqualTo(String value) {
			addCriterion("trans_type >=", value, "transType");
			return (Criteria) this;
		}

		public Criteria andTransTypeLessThan(String value) {
			addCriterion("trans_type <", value, "transType");
			return (Criteria) this;
		}

		public Criteria andTransTypeLessThanOrEqualTo(String value) {
			addCriterion("trans_type <=", value, "transType");
			return (Criteria) this;
		}

		public Criteria andTransTypeLike(String value) {
			addCriterion("trans_type like", value, "transType");
			return (Criteria) this;
		}

		public Criteria andTransTypeNotLike(String value) {
			addCriterion("trans_type not like", value, "transType");
			return (Criteria) this;
		}

		public Criteria andTransTypeIn(List<String> values) {
			addCriterion("trans_type in", values, "transType");
			return (Criteria) this;
		}

		public Criteria andTransTypeNotIn(List<String> values) {
			addCriterion("trans_type not in", values, "transType");
			return (Criteria) this;
		}

		public Criteria andTransTypeBetween(String value1, String value2) {
			addCriterion("trans_type between", value1, value2, "transType");
			return (Criteria) this;
		}

		public Criteria andTransTypeNotBetween(String value1, String value2) {
			addCriterion("trans_type not between", value1, value2, "transType");
			return (Criteria) this;
		}

		public Criteria andChannelTransTypeIsNull() {
			addCriterion("channel_trans_type is null");
			return (Criteria) this;
		}

		public Criteria andChannelTransTypeIsNotNull() {
			addCriterion("channel_trans_type is not null");
			return (Criteria) this;
		}

		public Criteria andChannelTransTypeEqualTo(String value) {
			addCriterion("channel_trans_type =", value, "channelTransType");
			return (Criteria) this;
		}

		public Criteria andChannelTransTypeNotEqualTo(String value) {
			addCriterion("channel_trans_type <>", value, "channelTransType");
			return (Criteria) this;
		}

		public Criteria andChannelTransTypeGreaterThan(String value) {
			addCriterion("channel_trans_type >", value, "channelTransType");
			return (Criteria) this;
		}

		public Criteria andChannelTransTypeGreaterThanOrEqualTo(String value) {
			addCriterion("channel_trans_type >=", value, "channelTransType");
			return (Criteria) this;
		}

		public Criteria andChannelTransTypeLessThan(String value) {
			addCriterion("channel_trans_type <", value, "channelTransType");
			return (Criteria) this;
		}

		public Criteria andChannelTransTypeLessThanOrEqualTo(String value) {
			addCriterion("channel_trans_type <=", value, "channelTransType");
			return (Criteria) this;
		}

		public Criteria andChannelTransTypeLike(String value) {
			addCriterion("channel_trans_type like", value, "channelTransType");
			return (Criteria) this;
		}

		public Criteria andChannelTransTypeNotLike(String value) {
			addCriterion("channel_trans_type not like", value,
					"channelTransType");
			return (Criteria) this;
		}

		public Criteria andChannelTransTypeIn(List<String> values) {
			addCriterion("channel_trans_type in", values, "channelTransType");
			return (Criteria) this;
		}

		public Criteria andChannelTransTypeNotIn(List<String> values) {
			addCriterion("channel_trans_type not in", values,
					"channelTransType");
			return (Criteria) this;
		}

		public Criteria andChannelTransTypeBetween(String value1, String value2) {
			addCriterion("channel_trans_type between", value1, value2,
					"channelTransType");
			return (Criteria) this;
		}

		public Criteria andChannelTransTypeNotBetween(String value1,
				String value2) {
			addCriterion("channel_trans_type not between", value1, value2,
					"channelTransType");
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

		public Criteria andIdCardIsNull() {
			addCriterion("id_card is null");
			return (Criteria) this;
		}

		public Criteria andIdCardIsNotNull() {
			addCriterion("id_card is not null");
			return (Criteria) this;
		}

		public Criteria andIdCardEqualTo(String value) {
			addCriterion("id_card =", value, "idCard");
			return (Criteria) this;
		}

		public Criteria andIdCardNotEqualTo(String value) {
			addCriterion("id_card <>", value, "idCard");
			return (Criteria) this;
		}

		public Criteria andIdCardGreaterThan(String value) {
			addCriterion("id_card >", value, "idCard");
			return (Criteria) this;
		}

		public Criteria andIdCardGreaterThanOrEqualTo(String value) {
			addCriterion("id_card >=", value, "idCard");
			return (Criteria) this;
		}

		public Criteria andIdCardLessThan(String value) {
			addCriterion("id_card <", value, "idCard");
			return (Criteria) this;
		}

		public Criteria andIdCardLessThanOrEqualTo(String value) {
			addCriterion("id_card <=", value, "idCard");
			return (Criteria) this;
		}

		public Criteria andIdCardLike(String value) {
			addCriterion("id_card like", value, "idCard");
			return (Criteria) this;
		}

		public Criteria andIdCardNotLike(String value) {
			addCriterion("id_card not like", value, "idCard");
			return (Criteria) this;
		}

		public Criteria andIdCardIn(List<String> values) {
			addCriterion("id_card in", values, "idCard");
			return (Criteria) this;
		}

		public Criteria andIdCardNotIn(List<String> values) {
			addCriterion("id_card not in", values, "idCard");
			return (Criteria) this;
		}

		public Criteria andIdCardBetween(String value1, String value2) {
			addCriterion("id_card between", value1, value2, "idCard");
			return (Criteria) this;
		}

		public Criteria andIdCardNotBetween(String value1, String value2) {
			addCriterion("id_card not between", value1, value2, "idCard");
			return (Criteria) this;
		}

		public Criteria andUserNameIsNull() {
			addCriterion("user_name is null");
			return (Criteria) this;
		}

		public Criteria andUserNameIsNotNull() {
			addCriterion("user_name is not null");
			return (Criteria) this;
		}

		public Criteria andUserNameEqualTo(String value) {
			addCriterion("user_name =", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameNotEqualTo(String value) {
			addCriterion("user_name <>", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameGreaterThan(String value) {
			addCriterion("user_name >", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameGreaterThanOrEqualTo(String value) {
			addCriterion("user_name >=", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameLessThan(String value) {
			addCriterion("user_name <", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameLessThanOrEqualTo(String value) {
			addCriterion("user_name <=", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameLike(String value) {
			addCriterion("user_name like", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameNotLike(String value) {
			addCriterion("user_name not like", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameIn(List<String> values) {
			addCriterion("user_name in", values, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameNotIn(List<String> values) {
			addCriterion("user_name not in", values, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameBetween(String value1, String value2) {
			addCriterion("user_name between", value1, value2, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameNotBetween(String value1, String value2) {
			addCriterion("user_name not between", value1, value2, "userName");
			return (Criteria) this;
		}

		public Criteria andIsProtocolPayIsNull() {
			addCriterion("is_protocol_pay is null");
			return (Criteria) this;
		}

		public Criteria andIsProtocolPayIsNotNull() {
			addCriterion("is_protocol_pay is not null");
			return (Criteria) this;
		}

		public Criteria andIsProtocolPayEqualTo(String value) {
			addCriterion("is_protocol_pay =", value, "isProtocolPay");
			return (Criteria) this;
		}

		public Criteria andIsProtocolPayNotEqualTo(String value) {
			addCriterion("is_protocol_pay <>", value, "isProtocolPay");
			return (Criteria) this;
		}

		public Criteria andIsProtocolPayGreaterThan(String value) {
			addCriterion("is_protocol_pay >", value, "isProtocolPay");
			return (Criteria) this;
		}

		public Criteria andIsProtocolPayGreaterThanOrEqualTo(String value) {
			addCriterion("is_protocol_pay >=", value, "isProtocolPay");
			return (Criteria) this;
		}

		public Criteria andIsProtocolPayLessThan(String value) {
			addCriterion("is_protocol_pay <", value, "isProtocolPay");
			return (Criteria) this;
		}

		public Criteria andIsProtocolPayLessThanOrEqualTo(String value) {
			addCriterion("is_protocol_pay <=", value, "isProtocolPay");
			return (Criteria) this;
		}

		public Criteria andIsProtocolPayLike(String value) {
			addCriterion("is_protocol_pay like", value, "isProtocolPay");
			return (Criteria) this;
		}

		public Criteria andIsProtocolPayNotLike(String value) {
			addCriterion("is_protocol_pay not like", value, "isProtocolPay");
			return (Criteria) this;
		}

		public Criteria andIsProtocolPayIn(List<String> values) {
			addCriterion("is_protocol_pay in", values, "isProtocolPay");
			return (Criteria) this;
		}

		public Criteria andIsProtocolPayNotIn(List<String> values) {
			addCriterion("is_protocol_pay not in", values, "isProtocolPay");
			return (Criteria) this;
		}

		public Criteria andIsProtocolPayBetween(String value1, String value2) {
			addCriterion("is_protocol_pay between", value1, value2,
					"isProtocolPay");
			return (Criteria) this;
		}

		public Criteria andIsProtocolPayNotBetween(String value1, String value2) {
			addCriterion("is_protocol_pay not between", value1, value2,
					"isProtocolPay");
			return (Criteria) this;
		}

		public Criteria andReturnCodeIsNull() {
			addCriterion("return_code is null");
			return (Criteria) this;
		}

		public Criteria andReturnCodeIsNotNull() {
			addCriterion("return_code is not null");
			return (Criteria) this;
		}

		public Criteria andReturnCodeEqualTo(String value) {
			addCriterion("return_code =", value, "returnCode");
			return (Criteria) this;
		}

		public Criteria andReturnCodeNotEqualTo(String value) {
			addCriterion("return_code <>", value, "returnCode");
			return (Criteria) this;
		}

		public Criteria andReturnCodeGreaterThan(String value) {
			addCriterion("return_code >", value, "returnCode");
			return (Criteria) this;
		}

		public Criteria andReturnCodeGreaterThanOrEqualTo(String value) {
			addCriterion("return_code >=", value, "returnCode");
			return (Criteria) this;
		}

		public Criteria andReturnCodeLessThan(String value) {
			addCriterion("return_code <", value, "returnCode");
			return (Criteria) this;
		}

		public Criteria andReturnCodeLessThanOrEqualTo(String value) {
			addCriterion("return_code <=", value, "returnCode");
			return (Criteria) this;
		}

		public Criteria andReturnCodeLike(String value) {
			addCriterion("return_code like", value, "returnCode");
			return (Criteria) this;
		}

		public Criteria andReturnCodeNotLike(String value) {
			addCriterion("return_code not like", value, "returnCode");
			return (Criteria) this;
		}

		public Criteria andReturnCodeIn(List<String> values) {
			addCriterion("return_code in", values, "returnCode");
			return (Criteria) this;
		}

		public Criteria andReturnCodeNotIn(List<String> values) {
			addCriterion("return_code not in", values, "returnCode");
			return (Criteria) this;
		}

		public Criteria andReturnCodeBetween(String value1, String value2) {
			addCriterion("return_code between", value1, value2, "returnCode");
			return (Criteria) this;
		}

		public Criteria andReturnCodeNotBetween(String value1, String value2) {
			addCriterion("return_code not between", value1, value2,
					"returnCode");
			return (Criteria) this;
		}

		public Criteria andReturnMsgIsNull() {
			addCriterion("return_msg is null");
			return (Criteria) this;
		}

		public Criteria andReturnMsgIsNotNull() {
			addCriterion("return_msg is not null");
			return (Criteria) this;
		}

		public Criteria andReturnMsgEqualTo(String value) {
			addCriterion("return_msg =", value, "returnMsg");
			return (Criteria) this;
		}

		public Criteria andReturnMsgNotEqualTo(String value) {
			addCriterion("return_msg <>", value, "returnMsg");
			return (Criteria) this;
		}

		public Criteria andReturnMsgGreaterThan(String value) {
			addCriterion("return_msg >", value, "returnMsg");
			return (Criteria) this;
		}

		public Criteria andReturnMsgGreaterThanOrEqualTo(String value) {
			addCriterion("return_msg >=", value, "returnMsg");
			return (Criteria) this;
		}

		public Criteria andReturnMsgLessThan(String value) {
			addCriterion("return_msg <", value, "returnMsg");
			return (Criteria) this;
		}

		public Criteria andReturnMsgLessThanOrEqualTo(String value) {
			addCriterion("return_msg <=", value, "returnMsg");
			return (Criteria) this;
		}

		public Criteria andReturnMsgLike(String value) {
			addCriterion("return_msg like", value, "returnMsg");
			return (Criteria) this;
		}

		public Criteria andReturnMsgNotLike(String value) {
			addCriterion("return_msg not like", value, "returnMsg");
			return (Criteria) this;
		}

		public Criteria andReturnMsgIn(List<String> values) {
			addCriterion("return_msg in", values, "returnMsg");
			return (Criteria) this;
		}

		public Criteria andReturnMsgNotIn(List<String> values) {
			addCriterion("return_msg not in", values, "returnMsg");
			return (Criteria) this;
		}

		public Criteria andReturnMsgBetween(String value1, String value2) {
			addCriterion("return_msg between", value1, value2, "returnMsg");
			return (Criteria) this;
		}

		public Criteria andReturnMsgNotBetween(String value1, String value2) {
			addCriterion("return_msg not between", value1, value2, "returnMsg");
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