package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class LnDailyAmountExample {
    protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public LnDailyAmountExample() {
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

		public Criteria andTerm2LeftAmountIsNull() {
			addCriterion("term2_left_amount is null");
			return (Criteria) this;
		}

		public Criteria andTerm2LeftAmountIsNotNull() {
			addCriterion("term2_left_amount is not null");
			return (Criteria) this;
		}

		public Criteria andTerm2LeftAmountEqualTo(Double value) {
			addCriterion("term2_left_amount =", value, "term2LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm2LeftAmountNotEqualTo(Double value) {
			addCriterion("term2_left_amount <>", value, "term2LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm2LeftAmountGreaterThan(Double value) {
			addCriterion("term2_left_amount >", value, "term2LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm2LeftAmountGreaterThanOrEqualTo(Double value) {
			addCriterion("term2_left_amount >=", value, "term2LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm2LeftAmountLessThan(Double value) {
			addCriterion("term2_left_amount <", value, "term2LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm2LeftAmountLessThanOrEqualTo(Double value) {
			addCriterion("term2_left_amount <=", value, "term2LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm2LeftAmountIn(List<Double> values) {
			addCriterion("term2_left_amount in", values, "term2LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm2LeftAmountNotIn(List<Double> values) {
			addCriterion("term2_left_amount not in", values, "term2LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm2LeftAmountBetween(Double value1, Double value2) {
			addCriterion("term2_left_amount between", value1, value2,
					"term2LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm2LeftAmountNotBetween(Double value1,
				Double value2) {
			addCriterion("term2_left_amount not between", value1, value2,
					"term2LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm2AmountIsNull() {
			addCriterion("term2_amount is null");
			return (Criteria) this;
		}

		public Criteria andTerm2AmountIsNotNull() {
			addCriterion("term2_amount is not null");
			return (Criteria) this;
		}

		public Criteria andTerm2AmountEqualTo(Double value) {
			addCriterion("term2_amount =", value, "term2Amount");
			return (Criteria) this;
		}

		public Criteria andTerm2AmountNotEqualTo(Double value) {
			addCriterion("term2_amount <>", value, "term2Amount");
			return (Criteria) this;
		}

		public Criteria andTerm2AmountGreaterThan(Double value) {
			addCriterion("term2_amount >", value, "term2Amount");
			return (Criteria) this;
		}

		public Criteria andTerm2AmountGreaterThanOrEqualTo(Double value) {
			addCriterion("term2_amount >=", value, "term2Amount");
			return (Criteria) this;
		}

		public Criteria andTerm2AmountLessThan(Double value) {
			addCriterion("term2_amount <", value, "term2Amount");
			return (Criteria) this;
		}

		public Criteria andTerm2AmountLessThanOrEqualTo(Double value) {
			addCriterion("term2_amount <=", value, "term2Amount");
			return (Criteria) this;
		}

		public Criteria andTerm2AmountIn(List<Double> values) {
			addCriterion("term2_amount in", values, "term2Amount");
			return (Criteria) this;
		}

		public Criteria andTerm2AmountNotIn(List<Double> values) {
			addCriterion("term2_amount not in", values, "term2Amount");
			return (Criteria) this;
		}

		public Criteria andTerm2AmountBetween(Double value1, Double value2) {
			addCriterion("term2_amount between", value1, value2, "term2Amount");
			return (Criteria) this;
		}

		public Criteria andTerm2AmountNotBetween(Double value1, Double value2) {
			addCriterion("term2_amount not between", value1, value2,
					"term2Amount");
			return (Criteria) this;
		}

		public Criteria andTerm1LeftAmountIsNull() {
			addCriterion("term1_left_amount is null");
			return (Criteria) this;
		}

		public Criteria andTerm1LeftAmountIsNotNull() {
			addCriterion("term1_left_amount is not null");
			return (Criteria) this;
		}

		public Criteria andTerm1LeftAmountEqualTo(Double value) {
			addCriterion("term1_left_amount =", value, "term1LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm1LeftAmountNotEqualTo(Double value) {
			addCriterion("term1_left_amount <>", value, "term1LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm1LeftAmountGreaterThan(Double value) {
			addCriterion("term1_left_amount >", value, "term1LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm1LeftAmountGreaterThanOrEqualTo(Double value) {
			addCriterion("term1_left_amount >=", value, "term1LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm1LeftAmountLessThan(Double value) {
			addCriterion("term1_left_amount <", value, "term1LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm1LeftAmountLessThanOrEqualTo(Double value) {
			addCriterion("term1_left_amount <=", value, "term1LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm1LeftAmountIn(List<Double> values) {
			addCriterion("term1_left_amount in", values, "term1LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm1LeftAmountNotIn(List<Double> values) {
			addCriterion("term1_left_amount not in", values, "term1LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm1LeftAmountBetween(Double value1, Double value2) {
			addCriterion("term1_left_amount between", value1, value2,
					"term1LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm1LeftAmountNotBetween(Double value1,
				Double value2) {
			addCriterion("term1_left_amount not between", value1, value2,
					"term1LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm1AmountIsNull() {
			addCriterion("term1_amount is null");
			return (Criteria) this;
		}

		public Criteria andTerm1AmountIsNotNull() {
			addCriterion("term1_amount is not null");
			return (Criteria) this;
		}

		public Criteria andTerm1AmountEqualTo(Double value) {
			addCriterion("term1_amount =", value, "term1Amount");
			return (Criteria) this;
		}

		public Criteria andTerm1AmountNotEqualTo(Double value) {
			addCriterion("term1_amount <>", value, "term1Amount");
			return (Criteria) this;
		}

		public Criteria andTerm1AmountGreaterThan(Double value) {
			addCriterion("term1_amount >", value, "term1Amount");
			return (Criteria) this;
		}

		public Criteria andTerm1AmountGreaterThanOrEqualTo(Double value) {
			addCriterion("term1_amount >=", value, "term1Amount");
			return (Criteria) this;
		}

		public Criteria andTerm1AmountLessThan(Double value) {
			addCriterion("term1_amount <", value, "term1Amount");
			return (Criteria) this;
		}

		public Criteria andTerm1AmountLessThanOrEqualTo(Double value) {
			addCriterion("term1_amount <=", value, "term1Amount");
			return (Criteria) this;
		}

		public Criteria andTerm1AmountIn(List<Double> values) {
			addCriterion("term1_amount in", values, "term1Amount");
			return (Criteria) this;
		}

		public Criteria andTerm1AmountNotIn(List<Double> values) {
			addCriterion("term1_amount not in", values, "term1Amount");
			return (Criteria) this;
		}

		public Criteria andTerm1AmountBetween(Double value1, Double value2) {
			addCriterion("term1_amount between", value1, value2, "term1Amount");
			return (Criteria) this;
		}

		public Criteria andTerm1AmountNotBetween(Double value1, Double value2) {
			addCriterion("term1_amount not between", value1, value2,
					"term1Amount");
			return (Criteria) this;
		}

		public Criteria andTerm3AmountIsNull() {
			addCriterion("term3_amount is null");
			return (Criteria) this;
		}

		public Criteria andTerm3AmountIsNotNull() {
			addCriterion("term3_amount is not null");
			return (Criteria) this;
		}

		public Criteria andTerm3AmountEqualTo(Double value) {
			addCriterion("term3_amount =", value, "term3Amount");
			return (Criteria) this;
		}

		public Criteria andTerm3AmountNotEqualTo(Double value) {
			addCriterion("term3_amount <>", value, "term3Amount");
			return (Criteria) this;
		}

		public Criteria andTerm3AmountGreaterThan(Double value) {
			addCriterion("term3_amount >", value, "term3Amount");
			return (Criteria) this;
		}

		public Criteria andTerm3AmountGreaterThanOrEqualTo(Double value) {
			addCriterion("term3_amount >=", value, "term3Amount");
			return (Criteria) this;
		}

		public Criteria andTerm3AmountLessThan(Double value) {
			addCriterion("term3_amount <", value, "term3Amount");
			return (Criteria) this;
		}

		public Criteria andTerm3AmountLessThanOrEqualTo(Double value) {
			addCriterion("term3_amount <=", value, "term3Amount");
			return (Criteria) this;
		}

		public Criteria andTerm3AmountIn(List<Double> values) {
			addCriterion("term3_amount in", values, "term3Amount");
			return (Criteria) this;
		}

		public Criteria andTerm3AmountNotIn(List<Double> values) {
			addCriterion("term3_amount not in", values, "term3Amount");
			return (Criteria) this;
		}

		public Criteria andTerm3AmountBetween(Double value1, Double value2) {
			addCriterion("term3_amount between", value1, value2, "term3Amount");
			return (Criteria) this;
		}

		public Criteria andTerm3AmountNotBetween(Double value1, Double value2) {
			addCriterion("term3_amount not between", value1, value2,
					"term3Amount");
			return (Criteria) this;
		}

		public Criteria andTerm3LeftAmountIsNull() {
			addCriterion("term3_left_amount is null");
			return (Criteria) this;
		}

		public Criteria andTerm3LeftAmountIsNotNull() {
			addCriterion("term3_left_amount is not null");
			return (Criteria) this;
		}

		public Criteria andTerm3LeftAmountEqualTo(Double value) {
			addCriterion("term3_left_amount =", value, "term3LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm3LeftAmountNotEqualTo(Double value) {
			addCriterion("term3_left_amount <>", value, "term3LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm3LeftAmountGreaterThan(Double value) {
			addCriterion("term3_left_amount >", value, "term3LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm3LeftAmountGreaterThanOrEqualTo(Double value) {
			addCriterion("term3_left_amount >=", value, "term3LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm3LeftAmountLessThan(Double value) {
			addCriterion("term3_left_amount <", value, "term3LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm3LeftAmountLessThanOrEqualTo(Double value) {
			addCriterion("term3_left_amount <=", value, "term3LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm3LeftAmountIn(List<Double> values) {
			addCriterion("term3_left_amount in", values, "term3LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm3LeftAmountNotIn(List<Double> values) {
			addCriterion("term3_left_amount not in", values, "term3LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm3LeftAmountBetween(Double value1, Double value2) {
			addCriterion("term3_left_amount between", value1, value2,
					"term3LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm3LeftAmountNotBetween(Double value1,
				Double value2) {
			addCriterion("term3_left_amount not between", value1, value2,
					"term3LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm5LeftAmountIsNull() {
			addCriterion("term5_left_amount is null");
			return (Criteria) this;
		}

		public Criteria andTerm5LeftAmountIsNotNull() {
			addCriterion("term5_left_amount is not null");
			return (Criteria) this;
		}

		public Criteria andTerm5LeftAmountEqualTo(Double value) {
			addCriterion("term5_left_amount =", value, "term5LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm5LeftAmountNotEqualTo(Double value) {
			addCriterion("term5_left_amount <>", value, "term5LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm5LeftAmountGreaterThan(Double value) {
			addCriterion("term5_left_amount >", value, "term5LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm5LeftAmountGreaterThanOrEqualTo(Double value) {
			addCriterion("term5_left_amount >=", value, "term5LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm5LeftAmountLessThan(Double value) {
			addCriterion("term5_left_amount <", value, "term5LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm5LeftAmountLessThanOrEqualTo(Double value) {
			addCriterion("term5_left_amount <=", value, "term5LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm5LeftAmountIn(List<Double> values) {
			addCriterion("term5_left_amount in", values, "term5LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm5LeftAmountNotIn(List<Double> values) {
			addCriterion("term5_left_amount not in", values, "term5LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm5LeftAmountBetween(Double value1, Double value2) {
			addCriterion("term5_left_amount between", value1, value2,
					"term5LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm5LeftAmountNotBetween(Double value1,
				Double value2) {
			addCriterion("term5_left_amount not between", value1, value2,
					"term5LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm5AmountIsNull() {
			addCriterion("term5_amount is null");
			return (Criteria) this;
		}

		public Criteria andTerm5AmountIsNotNull() {
			addCriterion("term5_amount is not null");
			return (Criteria) this;
		}

		public Criteria andTerm5AmountEqualTo(Double value) {
			addCriterion("term5_amount =", value, "term5Amount");
			return (Criteria) this;
		}

		public Criteria andTerm5AmountNotEqualTo(Double value) {
			addCriterion("term5_amount <>", value, "term5Amount");
			return (Criteria) this;
		}

		public Criteria andTerm5AmountGreaterThan(Double value) {
			addCriterion("term5_amount >", value, "term5Amount");
			return (Criteria) this;
		}

		public Criteria andTerm5AmountGreaterThanOrEqualTo(Double value) {
			addCriterion("term5_amount >=", value, "term5Amount");
			return (Criteria) this;
		}

		public Criteria andTerm5AmountLessThan(Double value) {
			addCriterion("term5_amount <", value, "term5Amount");
			return (Criteria) this;
		}

		public Criteria andTerm5AmountLessThanOrEqualTo(Double value) {
			addCriterion("term5_amount <=", value, "term5Amount");
			return (Criteria) this;
		}

		public Criteria andTerm5AmountIn(List<Double> values) {
			addCriterion("term5_amount in", values, "term5Amount");
			return (Criteria) this;
		}

		public Criteria andTerm5AmountNotIn(List<Double> values) {
			addCriterion("term5_amount not in", values, "term5Amount");
			return (Criteria) this;
		}

		public Criteria andTerm5AmountBetween(Double value1, Double value2) {
			addCriterion("term5_amount between", value1, value2, "term5Amount");
			return (Criteria) this;
		}

		public Criteria andTerm5AmountNotBetween(Double value1, Double value2) {
			addCriterion("term5_amount not between", value1, value2,
					"term5Amount");
			return (Criteria) this;
		}

		public Criteria andTerm4LeftAmountIsNull() {
			addCriterion("term4_left_amount is null");
			return (Criteria) this;
		}

		public Criteria andTerm4LeftAmountIsNotNull() {
			addCriterion("term4_left_amount is not null");
			return (Criteria) this;
		}

		public Criteria andTerm4LeftAmountEqualTo(Double value) {
			addCriterion("term4_left_amount =", value, "term4LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm4LeftAmountNotEqualTo(Double value) {
			addCriterion("term4_left_amount <>", value, "term4LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm4LeftAmountGreaterThan(Double value) {
			addCriterion("term4_left_amount >", value, "term4LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm4LeftAmountGreaterThanOrEqualTo(Double value) {
			addCriterion("term4_left_amount >=", value, "term4LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm4LeftAmountLessThan(Double value) {
			addCriterion("term4_left_amount <", value, "term4LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm4LeftAmountLessThanOrEqualTo(Double value) {
			addCriterion("term4_left_amount <=", value, "term4LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm4LeftAmountIn(List<Double> values) {
			addCriterion("term4_left_amount in", values, "term4LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm4LeftAmountNotIn(List<Double> values) {
			addCriterion("term4_left_amount not in", values, "term4LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm4LeftAmountBetween(Double value1, Double value2) {
			addCriterion("term4_left_amount between", value1, value2,
					"term4LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm4LeftAmountNotBetween(Double value1,
				Double value2) {
			addCriterion("term4_left_amount not between", value1, value2,
					"term4LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm4AmountIsNull() {
			addCriterion("term4_amount is null");
			return (Criteria) this;
		}

		public Criteria andTerm4AmountIsNotNull() {
			addCriterion("term4_amount is not null");
			return (Criteria) this;
		}

		public Criteria andTerm4AmountEqualTo(Double value) {
			addCriterion("term4_amount =", value, "term4Amount");
			return (Criteria) this;
		}

		public Criteria andTerm4AmountNotEqualTo(Double value) {
			addCriterion("term4_amount <>", value, "term4Amount");
			return (Criteria) this;
		}

		public Criteria andTerm4AmountGreaterThan(Double value) {
			addCriterion("term4_amount >", value, "term4Amount");
			return (Criteria) this;
		}

		public Criteria andTerm4AmountGreaterThanOrEqualTo(Double value) {
			addCriterion("term4_amount >=", value, "term4Amount");
			return (Criteria) this;
		}

		public Criteria andTerm4AmountLessThan(Double value) {
			addCriterion("term4_amount <", value, "term4Amount");
			return (Criteria) this;
		}

		public Criteria andTerm4AmountLessThanOrEqualTo(Double value) {
			addCriterion("term4_amount <=", value, "term4Amount");
			return (Criteria) this;
		}

		public Criteria andTerm4AmountIn(List<Double> values) {
			addCriterion("term4_amount in", values, "term4Amount");
			return (Criteria) this;
		}

		public Criteria andTerm4AmountNotIn(List<Double> values) {
			addCriterion("term4_amount not in", values, "term4Amount");
			return (Criteria) this;
		}

		public Criteria andTerm4AmountBetween(Double value1, Double value2) {
			addCriterion("term4_amount between", value1, value2, "term4Amount");
			return (Criteria) this;
		}

		public Criteria andTerm4AmountNotBetween(Double value1, Double value2) {
			addCriterion("term4_amount not between", value1, value2,
					"term4Amount");
			return (Criteria) this;
		}

		public Criteria andTerm6AmountIsNull() {
			addCriterion("term6_amount is null");
			return (Criteria) this;
		}

		public Criteria andTerm6AmountIsNotNull() {
			addCriterion("term6_amount is not null");
			return (Criteria) this;
		}

		public Criteria andTerm6AmountEqualTo(Double value) {
			addCriterion("term6_amount =", value, "term6Amount");
			return (Criteria) this;
		}

		public Criteria andTerm6AmountNotEqualTo(Double value) {
			addCriterion("term6_amount <>", value, "term6Amount");
			return (Criteria) this;
		}

		public Criteria andTerm6AmountGreaterThan(Double value) {
			addCriterion("term6_amount >", value, "term6Amount");
			return (Criteria) this;
		}

		public Criteria andTerm6AmountGreaterThanOrEqualTo(Double value) {
			addCriterion("term6_amount >=", value, "term6Amount");
			return (Criteria) this;
		}

		public Criteria andTerm6AmountLessThan(Double value) {
			addCriterion("term6_amount <", value, "term6Amount");
			return (Criteria) this;
		}

		public Criteria andTerm6AmountLessThanOrEqualTo(Double value) {
			addCriterion("term6_amount <=", value, "term6Amount");
			return (Criteria) this;
		}

		public Criteria andTerm6AmountIn(List<Double> values) {
			addCriterion("term6_amount in", values, "term6Amount");
			return (Criteria) this;
		}

		public Criteria andTerm6AmountNotIn(List<Double> values) {
			addCriterion("term6_amount not in", values, "term6Amount");
			return (Criteria) this;
		}

		public Criteria andTerm6AmountBetween(Double value1, Double value2) {
			addCriterion("term6_amount between", value1, value2, "term6Amount");
			return (Criteria) this;
		}

		public Criteria andTerm6AmountNotBetween(Double value1, Double value2) {
			addCriterion("term6_amount not between", value1, value2,
					"term6Amount");
			return (Criteria) this;
		}

		public Criteria andTerm6LeftAmountIsNull() {
			addCriterion("term6_left_amount is null");
			return (Criteria) this;
		}

		public Criteria andTerm6LeftAmountIsNotNull() {
			addCriterion("term6_left_amount is not null");
			return (Criteria) this;
		}

		public Criteria andTerm6LeftAmountEqualTo(Double value) {
			addCriterion("term6_left_amount =", value, "term6LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm6LeftAmountNotEqualTo(Double value) {
			addCriterion("term6_left_amount <>", value, "term6LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm6LeftAmountGreaterThan(Double value) {
			addCriterion("term6_left_amount >", value, "term6LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm6LeftAmountGreaterThanOrEqualTo(Double value) {
			addCriterion("term6_left_amount >=", value, "term6LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm6LeftAmountLessThan(Double value) {
			addCriterion("term6_left_amount <", value, "term6LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm6LeftAmountLessThanOrEqualTo(Double value) {
			addCriterion("term6_left_amount <=", value, "term6LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm6LeftAmountIn(List<Double> values) {
			addCriterion("term6_left_amount in", values, "term6LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm6LeftAmountNotIn(List<Double> values) {
			addCriterion("term6_left_amount not in", values, "term6LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm6LeftAmountBetween(Double value1, Double value2) {
			addCriterion("term6_left_amount between", value1, value2,
					"term6LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm6LeftAmountNotBetween(Double value1,
				Double value2) {
			addCriterion("term6_left_amount not between", value1, value2,
					"term6LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm9AmountIsNull() {
			addCriterion("term9_amount is null");
			return (Criteria) this;
		}

		public Criteria andTerm9AmountIsNotNull() {
			addCriterion("term9_amount is not null");
			return (Criteria) this;
		}

		public Criteria andTerm9AmountEqualTo(Double value) {
			addCriterion("term9_amount =", value, "term9Amount");
			return (Criteria) this;
		}

		public Criteria andTerm9AmountNotEqualTo(Double value) {
			addCriterion("term9_amount <>", value, "term9Amount");
			return (Criteria) this;
		}

		public Criteria andTerm9AmountGreaterThan(Double value) {
			addCriterion("term9_amount >", value, "term9Amount");
			return (Criteria) this;
		}

		public Criteria andTerm9AmountGreaterThanOrEqualTo(Double value) {
			addCriterion("term9_amount >=", value, "term9Amount");
			return (Criteria) this;
		}

		public Criteria andTerm9AmountLessThan(Double value) {
			addCriterion("term9_amount <", value, "term9Amount");
			return (Criteria) this;
		}

		public Criteria andTerm9AmountLessThanOrEqualTo(Double value) {
			addCriterion("term9_amount <=", value, "term9Amount");
			return (Criteria) this;
		}

		public Criteria andTerm9AmountIn(List<Double> values) {
			addCriterion("term9_amount in", values, "term9Amount");
			return (Criteria) this;
		}

		public Criteria andTerm9AmountNotIn(List<Double> values) {
			addCriterion("term9_amount not in", values, "term9Amount");
			return (Criteria) this;
		}

		public Criteria andTerm9AmountBetween(Double value1, Double value2) {
			addCriterion("term9_amount between", value1, value2, "term9Amount");
			return (Criteria) this;
		}

		public Criteria andTerm9AmountNotBetween(Double value1, Double value2) {
			addCriterion("term9_amount not between", value1, value2,
					"term9Amount");
			return (Criteria) this;
		}

		public Criteria andTerm9LeftAmountIsNull() {
			addCriterion("term9_left_amount is null");
			return (Criteria) this;
		}

		public Criteria andTerm9LeftAmountIsNotNull() {
			addCriterion("term9_left_amount is not null");
			return (Criteria) this;
		}

		public Criteria andTerm9LeftAmountEqualTo(Double value) {
			addCriterion("term9_left_amount =", value, "term9LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm9LeftAmountNotEqualTo(Double value) {
			addCriterion("term9_left_amount <>", value, "term9LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm9LeftAmountGreaterThan(Double value) {
			addCriterion("term9_left_amount >", value, "term9LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm9LeftAmountGreaterThanOrEqualTo(Double value) {
			addCriterion("term9_left_amount >=", value, "term9LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm9LeftAmountLessThan(Double value) {
			addCriterion("term9_left_amount <", value, "term9LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm9LeftAmountLessThanOrEqualTo(Double value) {
			addCriterion("term9_left_amount <=", value, "term9LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm9LeftAmountIn(List<Double> values) {
			addCriterion("term9_left_amount in", values, "term9LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm9LeftAmountNotIn(List<Double> values) {
			addCriterion("term9_left_amount not in", values, "term9LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm9LeftAmountBetween(Double value1, Double value2) {
			addCriterion("term9_left_amount between", value1, value2,
					"term9LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm9LeftAmountNotBetween(Double value1,
				Double value2) {
			addCriterion("term9_left_amount not between", value1, value2,
					"term9LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm12AmountIsNull() {
			addCriterion("term12_amount is null");
			return (Criteria) this;
		}

		public Criteria andTerm12AmountIsNotNull() {
			addCriterion("term12_amount is not null");
			return (Criteria) this;
		}

		public Criteria andTerm12AmountEqualTo(Double value) {
			addCriterion("term12_amount =", value, "term12Amount");
			return (Criteria) this;
		}

		public Criteria andTerm12AmountNotEqualTo(Double value) {
			addCriterion("term12_amount <>", value, "term12Amount");
			return (Criteria) this;
		}

		public Criteria andTerm12AmountGreaterThan(Double value) {
			addCriterion("term12_amount >", value, "term12Amount");
			return (Criteria) this;
		}

		public Criteria andTerm12AmountGreaterThanOrEqualTo(Double value) {
			addCriterion("term12_amount >=", value, "term12Amount");
			return (Criteria) this;
		}

		public Criteria andTerm12AmountLessThan(Double value) {
			addCriterion("term12_amount <", value, "term12Amount");
			return (Criteria) this;
		}

		public Criteria andTerm12AmountLessThanOrEqualTo(Double value) {
			addCriterion("term12_amount <=", value, "term12Amount");
			return (Criteria) this;
		}

		public Criteria andTerm12AmountIn(List<Double> values) {
			addCriterion("term12_amount in", values, "term12Amount");
			return (Criteria) this;
		}

		public Criteria andTerm12AmountNotIn(List<Double> values) {
			addCriterion("term12_amount not in", values, "term12Amount");
			return (Criteria) this;
		}

		public Criteria andTerm12AmountBetween(Double value1, Double value2) {
			addCriterion("term12_amount between", value1, value2,
					"term12Amount");
			return (Criteria) this;
		}

		public Criteria andTerm12AmountNotBetween(Double value1, Double value2) {
			addCriterion("term12_amount not between", value1, value2,
					"term12Amount");
			return (Criteria) this;
		}

		public Criteria andTerm12LeftAmountIsNull() {
			addCriterion("term12_left_amount is null");
			return (Criteria) this;
		}

		public Criteria andTerm12LeftAmountIsNotNull() {
			addCriterion("term12_left_amount is not null");
			return (Criteria) this;
		}

		public Criteria andTerm12LeftAmountEqualTo(Double value) {
			addCriterion("term12_left_amount =", value, "term12LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm12LeftAmountNotEqualTo(Double value) {
			addCriterion("term12_left_amount <>", value, "term12LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm12LeftAmountGreaterThan(Double value) {
			addCriterion("term12_left_amount >", value, "term12LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm12LeftAmountGreaterThanOrEqualTo(Double value) {
			addCriterion("term12_left_amount >=", value, "term12LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm12LeftAmountLessThan(Double value) {
			addCriterion("term12_left_amount <", value, "term12LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm12LeftAmountLessThanOrEqualTo(Double value) {
			addCriterion("term12_left_amount <=", value, "term12LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm12LeftAmountIn(List<Double> values) {
			addCriterion("term12_left_amount in", values, "term12LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm12LeftAmountNotIn(List<Double> values) {
			addCriterion("term12_left_amount not in", values,
					"term12LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm12LeftAmountBetween(Double value1, Double value2) {
			addCriterion("term12_left_amount between", value1, value2,
					"term12LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTerm12LeftAmountNotBetween(Double value1,
				Double value2) {
			addCriterion("term12_left_amount not between", value1, value2,
					"term12LeftAmount");
			return (Criteria) this;
		}

		public Criteria andTermxAmountIsNull() {
			addCriterion("termx_amount is null");
			return (Criteria) this;
		}

		public Criteria andTermxAmountIsNotNull() {
			addCriterion("termx_amount is not null");
			return (Criteria) this;
		}

		public Criteria andTermxAmountEqualTo(Double value) {
			addCriterion("termx_amount =", value, "termxAmount");
			return (Criteria) this;
		}

		public Criteria andTermxAmountNotEqualTo(Double value) {
			addCriterion("termx_amount <>", value, "termxAmount");
			return (Criteria) this;
		}

		public Criteria andTermxAmountGreaterThan(Double value) {
			addCriterion("termx_amount >", value, "termxAmount");
			return (Criteria) this;
		}

		public Criteria andTermxAmountGreaterThanOrEqualTo(Double value) {
			addCriterion("termx_amount >=", value, "termxAmount");
			return (Criteria) this;
		}

		public Criteria andTermxAmountLessThan(Double value) {
			addCriterion("termx_amount <", value, "termxAmount");
			return (Criteria) this;
		}

		public Criteria andTermxAmountLessThanOrEqualTo(Double value) {
			addCriterion("termx_amount <=", value, "termxAmount");
			return (Criteria) this;
		}

		public Criteria andTermxAmountIn(List<Double> values) {
			addCriterion("termx_amount in", values, "termxAmount");
			return (Criteria) this;
		}

		public Criteria andTermxAmountNotIn(List<Double> values) {
			addCriterion("termx_amount not in", values, "termxAmount");
			return (Criteria) this;
		}

		public Criteria andTermxAmountBetween(Double value1, Double value2) {
			addCriterion("termx_amount between", value1, value2, "termxAmount");
			return (Criteria) this;
		}

		public Criteria andTermxAmountNotBetween(Double value1, Double value2) {
			addCriterion("termx_amount not between", value1, value2,
					"termxAmount");
			return (Criteria) this;
		}

		public Criteria andTermxLeftAmountIsNull() {
			addCriterion("termx_left_amount is null");
			return (Criteria) this;
		}

		public Criteria andTermxLeftAmountIsNotNull() {
			addCriterion("termx_left_amount is not null");
			return (Criteria) this;
		}

		public Criteria andTermxLeftAmountEqualTo(Double value) {
			addCriterion("termx_left_amount =", value, "termxLeftAmount");
			return (Criteria) this;
		}

		public Criteria andTermxLeftAmountNotEqualTo(Double value) {
			addCriterion("termx_left_amount <>", value, "termxLeftAmount");
			return (Criteria) this;
		}

		public Criteria andTermxLeftAmountGreaterThan(Double value) {
			addCriterion("termx_left_amount >", value, "termxLeftAmount");
			return (Criteria) this;
		}

		public Criteria andTermxLeftAmountGreaterThanOrEqualTo(Double value) {
			addCriterion("termx_left_amount >=", value, "termxLeftAmount");
			return (Criteria) this;
		}

		public Criteria andTermxLeftAmountLessThan(Double value) {
			addCriterion("termx_left_amount <", value, "termxLeftAmount");
			return (Criteria) this;
		}

		public Criteria andTermxLeftAmountLessThanOrEqualTo(Double value) {
			addCriterion("termx_left_amount <=", value, "termxLeftAmount");
			return (Criteria) this;
		}

		public Criteria andTermxLeftAmountIn(List<Double> values) {
			addCriterion("termx_left_amount in", values, "termxLeftAmount");
			return (Criteria) this;
		}

		public Criteria andTermxLeftAmountNotIn(List<Double> values) {
			addCriterion("termx_left_amount not in", values, "termxLeftAmount");
			return (Criteria) this;
		}

		public Criteria andTermxLeftAmountBetween(Double value1, Double value2) {
			addCriterion("termx_left_amount between", value1, value2,
					"termxLeftAmount");
			return (Criteria) this;
		}

		public Criteria andTermxLeftAmountNotBetween(Double value1,
				Double value2) {
			addCriterion("termx_left_amount not between", value1, value2,
					"termxLeftAmount");
			return (Criteria) this;
		}

		public Criteria andFreeRateIsNull() {
			addCriterion("free_rate is null");
			return (Criteria) this;
		}

		public Criteria andFreeRateIsNotNull() {
			addCriterion("free_rate is not null");
			return (Criteria) this;
		}

		public Criteria andFreeRateEqualTo(Double value) {
			addCriterion("free_rate =", value, "freeRate");
			return (Criteria) this;
		}

		public Criteria andFreeRateNotEqualTo(Double value) {
			addCriterion("free_rate <>", value, "freeRate");
			return (Criteria) this;
		}

		public Criteria andFreeRateGreaterThan(Double value) {
			addCriterion("free_rate >", value, "freeRate");
			return (Criteria) this;
		}

		public Criteria andFreeRateGreaterThanOrEqualTo(Double value) {
			addCriterion("free_rate >=", value, "freeRate");
			return (Criteria) this;
		}

		public Criteria andFreeRateLessThan(Double value) {
			addCriterion("free_rate <", value, "freeRate");
			return (Criteria) this;
		}

		public Criteria andFreeRateLessThanOrEqualTo(Double value) {
			addCriterion("free_rate <=", value, "freeRate");
			return (Criteria) this;
		}

		public Criteria andFreeRateIn(List<Double> values) {
			addCriterion("free_rate in", values, "freeRate");
			return (Criteria) this;
		}

		public Criteria andFreeRateNotIn(List<Double> values) {
			addCriterion("free_rate not in", values, "freeRate");
			return (Criteria) this;
		}

		public Criteria andFreeRateBetween(Double value1, Double value2) {
			addCriterion("free_rate between", value1, value2, "freeRate");
			return (Criteria) this;
		}

		public Criteria andFreeRateNotBetween(Double value1, Double value2) {
			addCriterion("free_rate not between", value1, value2, "freeRate");
			return (Criteria) this;
		}

		public Criteria andFreeAmountIsNull() {
			addCriterion("free_amount is null");
			return (Criteria) this;
		}

		public Criteria andFreeAmountIsNotNull() {
			addCriterion("free_amount is not null");
			return (Criteria) this;
		}

		public Criteria andFreeAmountEqualTo(Double value) {
			addCriterion("free_amount =", value, "freeAmount");
			return (Criteria) this;
		}

		public Criteria andFreeAmountNotEqualTo(Double value) {
			addCriterion("free_amount <>", value, "freeAmount");
			return (Criteria) this;
		}

		public Criteria andFreeAmountGreaterThan(Double value) {
			addCriterion("free_amount >", value, "freeAmount");
			return (Criteria) this;
		}

		public Criteria andFreeAmountGreaterThanOrEqualTo(Double value) {
			addCriterion("free_amount >=", value, "freeAmount");
			return (Criteria) this;
		}

		public Criteria andFreeAmountLessThan(Double value) {
			addCriterion("free_amount <", value, "freeAmount");
			return (Criteria) this;
		}

		public Criteria andFreeAmountLessThanOrEqualTo(Double value) {
			addCriterion("free_amount <=", value, "freeAmount");
			return (Criteria) this;
		}

		public Criteria andFreeAmountIn(List<Double> values) {
			addCriterion("free_amount in", values, "freeAmount");
			return (Criteria) this;
		}

		public Criteria andFreeAmountNotIn(List<Double> values) {
			addCriterion("free_amount not in", values, "freeAmount");
			return (Criteria) this;
		}

		public Criteria andFreeAmountBetween(Double value1, Double value2) {
			addCriterion("free_amount between", value1, value2, "freeAmount");
			return (Criteria) this;
		}

		public Criteria andFreeAmountNotBetween(Double value1, Double value2) {
			addCriterion("free_amount not between", value1, value2,
					"freeAmount");
			return (Criteria) this;
		}

		public Criteria andPartnerAmountIsNull() {
			addCriterion("partner_amount is null");
			return (Criteria) this;
		}

		public Criteria andPartnerAmountIsNotNull() {
			addCriterion("partner_amount is not null");
			return (Criteria) this;
		}

		public Criteria andPartnerAmountEqualTo(Double value) {
			addCriterion("partner_amount =", value, "partnerAmount");
			return (Criteria) this;
		}

		public Criteria andPartnerAmountNotEqualTo(Double value) {
			addCriterion("partner_amount <>", value, "partnerAmount");
			return (Criteria) this;
		}

		public Criteria andPartnerAmountGreaterThan(Double value) {
			addCriterion("partner_amount >", value, "partnerAmount");
			return (Criteria) this;
		}

		public Criteria andPartnerAmountGreaterThanOrEqualTo(Double value) {
			addCriterion("partner_amount >=", value, "partnerAmount");
			return (Criteria) this;
		}

		public Criteria andPartnerAmountLessThan(Double value) {
			addCriterion("partner_amount <", value, "partnerAmount");
			return (Criteria) this;
		}

		public Criteria andPartnerAmountLessThanOrEqualTo(Double value) {
			addCriterion("partner_amount <=", value, "partnerAmount");
			return (Criteria) this;
		}

		public Criteria andPartnerAmountIn(List<Double> values) {
			addCriterion("partner_amount in", values, "partnerAmount");
			return (Criteria) this;
		}

		public Criteria andPartnerAmountNotIn(List<Double> values) {
			addCriterion("partner_amount not in", values, "partnerAmount");
			return (Criteria) this;
		}

		public Criteria andPartnerAmountBetween(Double value1, Double value2) {
			addCriterion("partner_amount between", value1, value2,
					"partnerAmount");
			return (Criteria) this;
		}

		public Criteria andPartnerAmountNotBetween(Double value1, Double value2) {
			addCriterion("partner_amount not between", value1, value2,
					"partnerAmount");
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

		public Criteria andStatusEqualTo(String value) {
			addCriterion("status =", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusNotEqualTo(String value) {
			addCriterion("status <>", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusGreaterThan(String value) {
			addCriterion("status >", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusGreaterThanOrEqualTo(String value) {
			addCriterion("status >=", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusLessThan(String value) {
			addCriterion("status <", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusLessThanOrEqualTo(String value) {
			addCriterion("status <=", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusLike(String value) {
			addCriterion("status like", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusNotLike(String value) {
			addCriterion("status not like", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusIn(List<String> values) {
			addCriterion("status in", values, "status");
			return (Criteria) this;
		}

		public Criteria andStatusNotIn(List<String> values) {
			addCriterion("status not in", values, "status");
			return (Criteria) this;
		}

		public Criteria andStatusBetween(String value1, String value2) {
			addCriterion("status between", value1, value2, "status");
			return (Criteria) this;
		}

		public Criteria andStatusNotBetween(String value1, String value2) {
			addCriterion("status not between", value1, value2, "status");
			return (Criteria) this;
		}

		public Criteria andAvailableEndTimeIsNull() {
			addCriterion("available_end_time is null");
			return (Criteria) this;
		}

		public Criteria andAvailableEndTimeIsNotNull() {
			addCriterion("available_end_time is not null");
			return (Criteria) this;
		}

		public Criteria andAvailableEndTimeEqualTo(Date value) {
			addCriterion("available_end_time =", value, "availableEndTime");
			return (Criteria) this;
		}

		public Criteria andAvailableEndTimeNotEqualTo(Date value) {
			addCriterion("available_end_time <>", value, "availableEndTime");
			return (Criteria) this;
		}

		public Criteria andAvailableEndTimeGreaterThan(Date value) {
			addCriterion("available_end_time >", value, "availableEndTime");
			return (Criteria) this;
		}

		public Criteria andAvailableEndTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("available_end_time >=", value, "availableEndTime");
			return (Criteria) this;
		}

		public Criteria andAvailableEndTimeLessThan(Date value) {
			addCriterion("available_end_time <", value, "availableEndTime");
			return (Criteria) this;
		}

		public Criteria andAvailableEndTimeLessThanOrEqualTo(Date value) {
			addCriterion("available_end_time <=", value, "availableEndTime");
			return (Criteria) this;
		}

		public Criteria andAvailableEndTimeIn(List<Date> values) {
			addCriterion("available_end_time in", values, "availableEndTime");
			return (Criteria) this;
		}

		public Criteria andAvailableEndTimeNotIn(List<Date> values) {
			addCriterion("available_end_time not in", values,
					"availableEndTime");
			return (Criteria) this;
		}

		public Criteria andAvailableEndTimeBetween(Date value1, Date value2) {
			addCriterion("available_end_time between", value1, value2,
					"availableEndTime");
			return (Criteria) this;
		}

		public Criteria andAvailableEndTimeNotBetween(Date value1, Date value2) {
			addCriterion("available_end_time not between", value1, value2,
					"availableEndTime");
			return (Criteria) this;
		}

		public Criteria andUseDateIsNull() {
			addCriterion("use_date is null");
			return (Criteria) this;
		}

		public Criteria andUseDateIsNotNull() {
			addCriterion("use_date is not null");
			return (Criteria) this;
		}

		public Criteria andUseDateEqualTo(Date value) {
			addCriterionForJDBCDate("use_date =", value, "useDate");
			return (Criteria) this;
		}

		public Criteria andUseDateNotEqualTo(Date value) {
			addCriterionForJDBCDate("use_date <>", value, "useDate");
			return (Criteria) this;
		}

		public Criteria andUseDateGreaterThan(Date value) {
			addCriterionForJDBCDate("use_date >", value, "useDate");
			return (Criteria) this;
		}

		public Criteria andUseDateGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("use_date >=", value, "useDate");
			return (Criteria) this;
		}

		public Criteria andUseDateLessThan(Date value) {
			addCriterionForJDBCDate("use_date <", value, "useDate");
			return (Criteria) this;
		}

		public Criteria andUseDateLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("use_date <=", value, "useDate");
			return (Criteria) this;
		}

		public Criteria andUseDateIn(List<Date> values) {
			addCriterionForJDBCDate("use_date in", values, "useDate");
			return (Criteria) this;
		}

		public Criteria andUseDateNotIn(List<Date> values) {
			addCriterionForJDBCDate("use_date not in", values, "useDate");
			return (Criteria) this;
		}

		public Criteria andUseDateBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("use_date between", value1, value2,
					"useDate");
			return (Criteria) this;
		}

		public Criteria andUseDateNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("use_date not between", value1, value2,
					"useDate");
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