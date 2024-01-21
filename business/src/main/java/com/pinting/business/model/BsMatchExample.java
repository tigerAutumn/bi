package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BsMatchExample {
    protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public BsMatchExample() {
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

		protected void addCriterionForJDBCDate(String condition, Date value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			addCriterion(condition, new java.sql.Date(value.getTime()),
					property);
		}

		protected void addCriterionForJDBCDate(String condition,
				List<Date> values, String property) {
			if (values == null || values.size() == 0) {
				throw new RuntimeException("Value list for " + property
						+ " cannot be null or empty");
			}
			List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
			Iterator<Date> iter = values.iterator();
			while (iter.hasNext()) {
				dateList.add(new java.sql.Date(iter.next().getTime()));
			}
			addCriterion(condition, dateList, property);
		}

		protected void addCriterionForJDBCDate(String condition, Date value1,
				Date value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			addCriterion(condition, new java.sql.Date(value1.getTime()),
					new java.sql.Date(value2.getTime()), property);
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

		public Criteria andUserIdIsNull() {
			addCriterion("user_id is null");
			return (Criteria) this;
		}

		public Criteria andUserIdIsNotNull() {
			addCriterion("user_id is not null");
			return (Criteria) this;
		}

		public Criteria andUserIdEqualTo(Integer value) {
			addCriterion("user_id =", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotEqualTo(Integer value) {
			addCriterion("user_id <>", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdGreaterThan(Integer value) {
			addCriterion("user_id >", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("user_id >=", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdLessThan(Integer value) {
			addCriterion("user_id <", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdLessThanOrEqualTo(Integer value) {
			addCriterion("user_id <=", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdIn(List<Integer> values) {
			addCriterion("user_id in", values, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotIn(List<Integer> values) {
			addCriterion("user_id not in", values, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdBetween(Integer value1, Integer value2) {
			addCriterion("user_id between", value1, value2, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
			addCriterion("user_id not between", value1, value2, "userId");
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

		public Criteria andLoanRelativeIdIsNull() {
			addCriterion("loan_relative_id is null");
			return (Criteria) this;
		}

		public Criteria andLoanRelativeIdIsNotNull() {
			addCriterion("loan_relative_id is not null");
			return (Criteria) this;
		}

		public Criteria andLoanRelativeIdEqualTo(Integer value) {
			addCriterion("loan_relative_id =", value, "loanRelativeId");
			return (Criteria) this;
		}

		public Criteria andLoanRelativeIdNotEqualTo(Integer value) {
			addCriterion("loan_relative_id <>", value, "loanRelativeId");
			return (Criteria) this;
		}

		public Criteria andLoanRelativeIdGreaterThan(Integer value) {
			addCriterion("loan_relative_id >", value, "loanRelativeId");
			return (Criteria) this;
		}

		public Criteria andLoanRelativeIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("loan_relative_id >=", value, "loanRelativeId");
			return (Criteria) this;
		}

		public Criteria andLoanRelativeIdLessThan(Integer value) {
			addCriterion("loan_relative_id <", value, "loanRelativeId");
			return (Criteria) this;
		}

		public Criteria andLoanRelativeIdLessThanOrEqualTo(Integer value) {
			addCriterion("loan_relative_id <=", value, "loanRelativeId");
			return (Criteria) this;
		}

		public Criteria andLoanRelativeIdIn(List<Integer> values) {
			addCriterion("loan_relative_id in", values, "loanRelativeId");
			return (Criteria) this;
		}

		public Criteria andLoanRelativeIdNotIn(List<Integer> values) {
			addCriterion("loan_relative_id not in", values, "loanRelativeId");
			return (Criteria) this;
		}

		public Criteria andLoanRelativeIdBetween(Integer value1, Integer value2) {
			addCriterion("loan_relative_id between", value1, value2,
					"loanRelativeId");
			return (Criteria) this;
		}

		public Criteria andLoanRelativeIdNotBetween(Integer value1,
				Integer value2) {
			addCriterion("loan_relative_id not between", value1, value2,
					"loanRelativeId");
			return (Criteria) this;
		}

		public Criteria andPropertySymbolIsNull() {
			addCriterion("property_symbol is null");
			return (Criteria) this;
		}

		public Criteria andPropertySymbolIsNotNull() {
			addCriterion("property_symbol is not null");
			return (Criteria) this;
		}

		public Criteria andPropertySymbolEqualTo(String value) {
			addCriterion("property_symbol =", value, "propertySymbol");
			return (Criteria) this;
		}

		public Criteria andPropertySymbolNotEqualTo(String value) {
			addCriterion("property_symbol <>", value, "propertySymbol");
			return (Criteria) this;
		}

		public Criteria andPropertySymbolGreaterThan(String value) {
			addCriterion("property_symbol >", value, "propertySymbol");
			return (Criteria) this;
		}

		public Criteria andPropertySymbolGreaterThanOrEqualTo(String value) {
			addCriterion("property_symbol >=", value, "propertySymbol");
			return (Criteria) this;
		}

		public Criteria andPropertySymbolLessThan(String value) {
			addCriterion("property_symbol <", value, "propertySymbol");
			return (Criteria) this;
		}

		public Criteria andPropertySymbolLessThanOrEqualTo(String value) {
			addCriterion("property_symbol <=", value, "propertySymbol");
			return (Criteria) this;
		}

		public Criteria andPropertySymbolLike(String value) {
			addCriterion("property_symbol like", value, "propertySymbol");
			return (Criteria) this;
		}

		public Criteria andPropertySymbolNotLike(String value) {
			addCriterion("property_symbol not like", value, "propertySymbol");
			return (Criteria) this;
		}

		public Criteria andPropertySymbolIn(List<String> values) {
			addCriterion("property_symbol in", values, "propertySymbol");
			return (Criteria) this;
		}

		public Criteria andPropertySymbolNotIn(List<String> values) {
			addCriterion("property_symbol not in", values, "propertySymbol");
			return (Criteria) this;
		}

		public Criteria andPropertySymbolBetween(String value1, String value2) {
			addCriterion("property_symbol between", value1, value2,
					"propertySymbol");
			return (Criteria) this;
		}

		public Criteria andPropertySymbolNotBetween(String value1, String value2) {
			addCriterion("property_symbol not between", value1, value2,
					"propertySymbol");
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

		public Criteria andLeftPrincipalIsNull() {
			addCriterion("left_principal is null");
			return (Criteria) this;
		}

		public Criteria andLeftPrincipalIsNotNull() {
			addCriterion("left_principal is not null");
			return (Criteria) this;
		}

		public Criteria andLeftPrincipalEqualTo(Double value) {
			addCriterion("left_principal =", value, "leftPrincipal");
			return (Criteria) this;
		}

		public Criteria andLeftPrincipalNotEqualTo(Double value) {
			addCriterion("left_principal <>", value, "leftPrincipal");
			return (Criteria) this;
		}

		public Criteria andLeftPrincipalGreaterThan(Double value) {
			addCriterion("left_principal >", value, "leftPrincipal");
			return (Criteria) this;
		}

		public Criteria andLeftPrincipalGreaterThanOrEqualTo(Double value) {
			addCriterion("left_principal >=", value, "leftPrincipal");
			return (Criteria) this;
		}

		public Criteria andLeftPrincipalLessThan(Double value) {
			addCriterion("left_principal <", value, "leftPrincipal");
			return (Criteria) this;
		}

		public Criteria andLeftPrincipalLessThanOrEqualTo(Double value) {
			addCriterion("left_principal <=", value, "leftPrincipal");
			return (Criteria) this;
		}

		public Criteria andLeftPrincipalIn(List<Double> values) {
			addCriterion("left_principal in", values, "leftPrincipal");
			return (Criteria) this;
		}

		public Criteria andLeftPrincipalNotIn(List<Double> values) {
			addCriterion("left_principal not in", values, "leftPrincipal");
			return (Criteria) this;
		}

		public Criteria andLeftPrincipalBetween(Double value1, Double value2) {
			addCriterion("left_principal between", value1, value2,
					"leftPrincipal");
			return (Criteria) this;
		}

		public Criteria andLeftPrincipalNotBetween(Double value1, Double value2) {
			addCriterion("left_principal not between", value1, value2,
					"leftPrincipal");
			return (Criteria) this;
		}

		public Criteria andRepayAmountIsNull() {
			addCriterion("repay_amount is null");
			return (Criteria) this;
		}

		public Criteria andRepayAmountIsNotNull() {
			addCriterion("repay_amount is not null");
			return (Criteria) this;
		}

		public Criteria andRepayAmountEqualTo(Double value) {
			addCriterion("repay_amount =", value, "repayAmount");
			return (Criteria) this;
		}

		public Criteria andRepayAmountNotEqualTo(Double value) {
			addCriterion("repay_amount <>", value, "repayAmount");
			return (Criteria) this;
		}

		public Criteria andRepayAmountGreaterThan(Double value) {
			addCriterion("repay_amount >", value, "repayAmount");
			return (Criteria) this;
		}

		public Criteria andRepayAmountGreaterThanOrEqualTo(Double value) {
			addCriterion("repay_amount >=", value, "repayAmount");
			return (Criteria) this;
		}

		public Criteria andRepayAmountLessThan(Double value) {
			addCriterion("repay_amount <", value, "repayAmount");
			return (Criteria) this;
		}

		public Criteria andRepayAmountLessThanOrEqualTo(Double value) {
			addCriterion("repay_amount <=", value, "repayAmount");
			return (Criteria) this;
		}

		public Criteria andRepayAmountIn(List<Double> values) {
			addCriterion("repay_amount in", values, "repayAmount");
			return (Criteria) this;
		}

		public Criteria andRepayAmountNotIn(List<Double> values) {
			addCriterion("repay_amount not in", values, "repayAmount");
			return (Criteria) this;
		}

		public Criteria andRepayAmountBetween(Double value1, Double value2) {
			addCriterion("repay_amount between", value1, value2, "repayAmount");
			return (Criteria) this;
		}

		public Criteria andRepayAmountNotBetween(Double value1, Double value2) {
			addCriterion("repay_amount not between", value1, value2,
					"repayAmount");
			return (Criteria) this;
		}

		public Criteria andLastRepayDateIsNull() {
			addCriterion("last_repay_date is null");
			return (Criteria) this;
		}

		public Criteria andLastRepayDateIsNotNull() {
			addCriterion("last_repay_date is not null");
			return (Criteria) this;
		}

		public Criteria andLastRepayDateEqualTo(Date value) {
			addCriterion("last_repay_date =", value, "lastRepayDate");
			return (Criteria) this;
		}

		public Criteria andLastRepayDateNotEqualTo(Date value) {
			addCriterion("last_repay_date <>", value, "lastRepayDate");
			return (Criteria) this;
		}

		public Criteria andLastRepayDateGreaterThan(Date value) {
			addCriterion("last_repay_date >", value, "lastRepayDate");
			return (Criteria) this;
		}

		public Criteria andLastRepayDateGreaterThanOrEqualTo(Date value) {
			addCriterion("last_repay_date >=", value, "lastRepayDate");
			return (Criteria) this;
		}

		public Criteria andLastRepayDateLessThan(Date value) {
			addCriterion("last_repay_date <", value, "lastRepayDate");
			return (Criteria) this;
		}

		public Criteria andLastRepayDateLessThanOrEqualTo(Date value) {
			addCriterion("last_repay_date <=", value, "lastRepayDate");
			return (Criteria) this;
		}

		public Criteria andLastRepayDateIn(List<Date> values) {
			addCriterion("last_repay_date in", values, "lastRepayDate");
			return (Criteria) this;
		}

		public Criteria andLastRepayDateNotIn(List<Date> values) {
			addCriterion("last_repay_date not in", values, "lastRepayDate");
			return (Criteria) this;
		}

		public Criteria andLastRepayDateBetween(Date value1, Date value2) {
			addCriterion("last_repay_date between", value1, value2,
					"lastRepayDate");
			return (Criteria) this;
		}

		public Criteria andLastRepayDateNotBetween(Date value1, Date value2) {
			addCriterion("last_repay_date not between", value1, value2,
					"lastRepayDate");
			return (Criteria) this;
		}

		public Criteria andRepayStatusIsNull() {
			addCriterion("repay_status is null");
			return (Criteria) this;
		}

		public Criteria andRepayStatusIsNotNull() {
			addCriterion("repay_status is not null");
			return (Criteria) this;
		}

		public Criteria andRepayStatusEqualTo(String value) {
			addCriterion("repay_status =", value, "repayStatus");
			return (Criteria) this;
		}

		public Criteria andRepayStatusNotEqualTo(String value) {
			addCriterion("repay_status <>", value, "repayStatus");
			return (Criteria) this;
		}

		public Criteria andRepayStatusGreaterThan(String value) {
			addCriterion("repay_status >", value, "repayStatus");
			return (Criteria) this;
		}

		public Criteria andRepayStatusGreaterThanOrEqualTo(String value) {
			addCriterion("repay_status >=", value, "repayStatus");
			return (Criteria) this;
		}

		public Criteria andRepayStatusLessThan(String value) {
			addCriterion("repay_status <", value, "repayStatus");
			return (Criteria) this;
		}

		public Criteria andRepayStatusLessThanOrEqualTo(String value) {
			addCriterion("repay_status <=", value, "repayStatus");
			return (Criteria) this;
		}

		public Criteria andRepayStatusLike(String value) {
			addCriterion("repay_status like", value, "repayStatus");
			return (Criteria) this;
		}

		public Criteria andRepayStatusNotLike(String value) {
			addCriterion("repay_status not like", value, "repayStatus");
			return (Criteria) this;
		}

		public Criteria andRepayStatusIn(List<String> values) {
			addCriterion("repay_status in", values, "repayStatus");
			return (Criteria) this;
		}

		public Criteria andRepayStatusNotIn(List<String> values) {
			addCriterion("repay_status not in", values, "repayStatus");
			return (Criteria) this;
		}

		public Criteria andRepayStatusBetween(String value1, String value2) {
			addCriterion("repay_status between", value1, value2, "repayStatus");
			return (Criteria) this;
		}

		public Criteria andRepayStatusNotBetween(String value1, String value2) {
			addCriterion("repay_status not between", value1, value2,
					"repayStatus");
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

		public Criteria andMatchDateIsNull() {
			addCriterion("match_date is null");
			return (Criteria) this;
		}

		public Criteria andMatchDateIsNotNull() {
			addCriterion("match_date is not null");
			return (Criteria) this;
		}

		public Criteria andMatchDateEqualTo(Date value) {
			addCriterionForJDBCDate("match_date =", value, "matchDate");
			return (Criteria) this;
		}

		public Criteria andMatchDateNotEqualTo(Date value) {
			addCriterionForJDBCDate("match_date <>", value, "matchDate");
			return (Criteria) this;
		}

		public Criteria andMatchDateGreaterThan(Date value) {
			addCriterionForJDBCDate("match_date >", value, "matchDate");
			return (Criteria) this;
		}

		public Criteria andMatchDateGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("match_date >=", value, "matchDate");
			return (Criteria) this;
		}

		public Criteria andMatchDateLessThan(Date value) {
			addCriterionForJDBCDate("match_date <", value, "matchDate");
			return (Criteria) this;
		}

		public Criteria andMatchDateLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("match_date <=", value, "matchDate");
			return (Criteria) this;
		}

		public Criteria andMatchDateIn(List<Date> values) {
			addCriterionForJDBCDate("match_date in", values, "matchDate");
			return (Criteria) this;
		}

		public Criteria andMatchDateNotIn(List<Date> values) {
			addCriterionForJDBCDate("match_date not in", values, "matchDate");
			return (Criteria) this;
		}

		public Criteria andMatchDateBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("match_date between", value1, value2,
					"matchDate");
			return (Criteria) this;
		}

		public Criteria andMatchDateNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("match_date not between", value1, value2,
					"matchDate");
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