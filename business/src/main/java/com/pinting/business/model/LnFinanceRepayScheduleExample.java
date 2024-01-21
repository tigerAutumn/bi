package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class LnFinanceRepayScheduleExample {
    protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public LnFinanceRepayScheduleExample() {
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

		public Criteria andRelationIdIsNull() {
			addCriterion("relation_id is null");
			return (Criteria) this;
		}

		public Criteria andRelationIdIsNotNull() {
			addCriterion("relation_id is not null");
			return (Criteria) this;
		}

		public Criteria andRelationIdEqualTo(Integer value) {
			addCriterion("relation_id =", value, "relationId");
			return (Criteria) this;
		}

		public Criteria andRelationIdNotEqualTo(Integer value) {
			addCriterion("relation_id <>", value, "relationId");
			return (Criteria) this;
		}

		public Criteria andRelationIdGreaterThan(Integer value) {
			addCriterion("relation_id >", value, "relationId");
			return (Criteria) this;
		}

		public Criteria andRelationIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("relation_id >=", value, "relationId");
			return (Criteria) this;
		}

		public Criteria andRelationIdLessThan(Integer value) {
			addCriterion("relation_id <", value, "relationId");
			return (Criteria) this;
		}

		public Criteria andRelationIdLessThanOrEqualTo(Integer value) {
			addCriterion("relation_id <=", value, "relationId");
			return (Criteria) this;
		}

		public Criteria andRelationIdIn(List<Integer> values) {
			addCriterion("relation_id in", values, "relationId");
			return (Criteria) this;
		}

		public Criteria andRelationIdNotIn(List<Integer> values) {
			addCriterion("relation_id not in", values, "relationId");
			return (Criteria) this;
		}

		public Criteria andRelationIdBetween(Integer value1, Integer value2) {
			addCriterion("relation_id between", value1, value2, "relationId");
			return (Criteria) this;
		}

		public Criteria andRelationIdNotBetween(Integer value1, Integer value2) {
			addCriterion("relation_id not between", value1, value2,
					"relationId");
			return (Criteria) this;
		}

		public Criteria andRepaySerialIsNull() {
			addCriterion("repay_serial is null");
			return (Criteria) this;
		}

		public Criteria andRepaySerialIsNotNull() {
			addCriterion("repay_serial is not null");
			return (Criteria) this;
		}

		public Criteria andRepaySerialEqualTo(Integer value) {
			addCriterion("repay_serial =", value, "repaySerial");
			return (Criteria) this;
		}

		public Criteria andRepaySerialNotEqualTo(Integer value) {
			addCriterion("repay_serial <>", value, "repaySerial");
			return (Criteria) this;
		}

		public Criteria andRepaySerialGreaterThan(Integer value) {
			addCriterion("repay_serial >", value, "repaySerial");
			return (Criteria) this;
		}

		public Criteria andRepaySerialGreaterThanOrEqualTo(Integer value) {
			addCriterion("repay_serial >=", value, "repaySerial");
			return (Criteria) this;
		}

		public Criteria andRepaySerialLessThan(Integer value) {
			addCriterion("repay_serial <", value, "repaySerial");
			return (Criteria) this;
		}

		public Criteria andRepaySerialLessThanOrEqualTo(Integer value) {
			addCriterion("repay_serial <=", value, "repaySerial");
			return (Criteria) this;
		}

		public Criteria andRepaySerialIn(List<Integer> values) {
			addCriterion("repay_serial in", values, "repaySerial");
			return (Criteria) this;
		}

		public Criteria andRepaySerialNotIn(List<Integer> values) {
			addCriterion("repay_serial not in", values, "repaySerial");
			return (Criteria) this;
		}

		public Criteria andRepaySerialBetween(Integer value1, Integer value2) {
			addCriterion("repay_serial between", value1, value2, "repaySerial");
			return (Criteria) this;
		}

		public Criteria andRepaySerialNotBetween(Integer value1, Integer value2) {
			addCriterion("repay_serial not between", value1, value2,
					"repaySerial");
			return (Criteria) this;
		}

		public Criteria andPlanDateIsNull() {
			addCriterion("plan_date is null");
			return (Criteria) this;
		}

		public Criteria andPlanDateIsNotNull() {
			addCriterion("plan_date is not null");
			return (Criteria) this;
		}

		public Criteria andPlanDateEqualTo(Date value) {
			addCriterionForJDBCDate("plan_date =", value, "planDate");
			return (Criteria) this;
		}

		public Criteria andPlanDateNotEqualTo(Date value) {
			addCriterionForJDBCDate("plan_date <>", value, "planDate");
			return (Criteria) this;
		}

		public Criteria andPlanDateGreaterThan(Date value) {
			addCriterionForJDBCDate("plan_date >", value, "planDate");
			return (Criteria) this;
		}

		public Criteria andPlanDateGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("plan_date >=", value, "planDate");
			return (Criteria) this;
		}

		public Criteria andPlanDateLessThan(Date value) {
			addCriterionForJDBCDate("plan_date <", value, "planDate");
			return (Criteria) this;
		}

		public Criteria andPlanDateLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("plan_date <=", value, "planDate");
			return (Criteria) this;
		}

		public Criteria andPlanDateIn(List<Date> values) {
			addCriterionForJDBCDate("plan_date in", values, "planDate");
			return (Criteria) this;
		}

		public Criteria andPlanDateNotIn(List<Date> values) {
			addCriterionForJDBCDate("plan_date not in", values, "planDate");
			return (Criteria) this;
		}

		public Criteria andPlanDateBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("plan_date between", value1, value2,
					"planDate");
			return (Criteria) this;
		}

		public Criteria andPlanDateNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("plan_date not between", value1, value2,
					"planDate");
			return (Criteria) this;
		}

		public Criteria andPlanTotalIsNull() {
			addCriterion("plan_total is null");
			return (Criteria) this;
		}

		public Criteria andPlanTotalIsNotNull() {
			addCriterion("plan_total is not null");
			return (Criteria) this;
		}

		public Criteria andPlanTotalEqualTo(Double value) {
			addCriterion("plan_total =", value, "planTotal");
			return (Criteria) this;
		}

		public Criteria andPlanTotalNotEqualTo(Double value) {
			addCriterion("plan_total <>", value, "planTotal");
			return (Criteria) this;
		}

		public Criteria andPlanTotalGreaterThan(Double value) {
			addCriterion("plan_total >", value, "planTotal");
			return (Criteria) this;
		}

		public Criteria andPlanTotalGreaterThanOrEqualTo(Double value) {
			addCriterion("plan_total >=", value, "planTotal");
			return (Criteria) this;
		}

		public Criteria andPlanTotalLessThan(Double value) {
			addCriterion("plan_total <", value, "planTotal");
			return (Criteria) this;
		}

		public Criteria andPlanTotalLessThanOrEqualTo(Double value) {
			addCriterion("plan_total <=", value, "planTotal");
			return (Criteria) this;
		}

		public Criteria andPlanTotalIn(List<Double> values) {
			addCriterion("plan_total in", values, "planTotal");
			return (Criteria) this;
		}

		public Criteria andPlanTotalNotIn(List<Double> values) {
			addCriterion("plan_total not in", values, "planTotal");
			return (Criteria) this;
		}

		public Criteria andPlanTotalBetween(Double value1, Double value2) {
			addCriterion("plan_total between", value1, value2, "planTotal");
			return (Criteria) this;
		}

		public Criteria andPlanTotalNotBetween(Double value1, Double value2) {
			addCriterion("plan_total not between", value1, value2, "planTotal");
			return (Criteria) this;
		}

		public Criteria andPlanPrincipalIsNull() {
			addCriterion("plan_principal is null");
			return (Criteria) this;
		}

		public Criteria andPlanPrincipalIsNotNull() {
			addCriterion("plan_principal is not null");
			return (Criteria) this;
		}

		public Criteria andPlanPrincipalEqualTo(Double value) {
			addCriterion("plan_principal =", value, "planPrincipal");
			return (Criteria) this;
		}

		public Criteria andPlanPrincipalNotEqualTo(Double value) {
			addCriterion("plan_principal <>", value, "planPrincipal");
			return (Criteria) this;
		}

		public Criteria andPlanPrincipalGreaterThan(Double value) {
			addCriterion("plan_principal >", value, "planPrincipal");
			return (Criteria) this;
		}

		public Criteria andPlanPrincipalGreaterThanOrEqualTo(Double value) {
			addCriterion("plan_principal >=", value, "planPrincipal");
			return (Criteria) this;
		}

		public Criteria andPlanPrincipalLessThan(Double value) {
			addCriterion("plan_principal <", value, "planPrincipal");
			return (Criteria) this;
		}

		public Criteria andPlanPrincipalLessThanOrEqualTo(Double value) {
			addCriterion("plan_principal <=", value, "planPrincipal");
			return (Criteria) this;
		}

		public Criteria andPlanPrincipalIn(List<Double> values) {
			addCriterion("plan_principal in", values, "planPrincipal");
			return (Criteria) this;
		}

		public Criteria andPlanPrincipalNotIn(List<Double> values) {
			addCriterion("plan_principal not in", values, "planPrincipal");
			return (Criteria) this;
		}

		public Criteria andPlanPrincipalBetween(Double value1, Double value2) {
			addCriterion("plan_principal between", value1, value2,
					"planPrincipal");
			return (Criteria) this;
		}

		public Criteria andPlanPrincipalNotBetween(Double value1, Double value2) {
			addCriterion("plan_principal not between", value1, value2,
					"planPrincipal");
			return (Criteria) this;
		}

		public Criteria andPlanInterestIsNull() {
			addCriterion("plan_interest is null");
			return (Criteria) this;
		}

		public Criteria andPlanInterestIsNotNull() {
			addCriterion("plan_interest is not null");
			return (Criteria) this;
		}

		public Criteria andPlanInterestEqualTo(Double value) {
			addCriterion("plan_interest =", value, "planInterest");
			return (Criteria) this;
		}

		public Criteria andPlanInterestNotEqualTo(Double value) {
			addCriterion("plan_interest <>", value, "planInterest");
			return (Criteria) this;
		}

		public Criteria andPlanInterestGreaterThan(Double value) {
			addCriterion("plan_interest >", value, "planInterest");
			return (Criteria) this;
		}

		public Criteria andPlanInterestGreaterThanOrEqualTo(Double value) {
			addCriterion("plan_interest >=", value, "planInterest");
			return (Criteria) this;
		}

		public Criteria andPlanInterestLessThan(Double value) {
			addCriterion("plan_interest <", value, "planInterest");
			return (Criteria) this;
		}

		public Criteria andPlanInterestLessThanOrEqualTo(Double value) {
			addCriterion("plan_interest <=", value, "planInterest");
			return (Criteria) this;
		}

		public Criteria andPlanInterestIn(List<Double> values) {
			addCriterion("plan_interest in", values, "planInterest");
			return (Criteria) this;
		}

		public Criteria andPlanInterestNotIn(List<Double> values) {
			addCriterion("plan_interest not in", values, "planInterest");
			return (Criteria) this;
		}

		public Criteria andPlanInterestBetween(Double value1, Double value2) {
			addCriterion("plan_interest between", value1, value2,
					"planInterest");
			return (Criteria) this;
		}

		public Criteria andPlanInterestNotBetween(Double value1, Double value2) {
			addCriterion("plan_interest not between", value1, value2,
					"planInterest");
			return (Criteria) this;
		}

		public Criteria andPlanTransInterestIsNull() {
			addCriterion("plan_trans_interest is null");
			return (Criteria) this;
		}

		public Criteria andPlanTransInterestIsNotNull() {
			addCriterion("plan_trans_interest is not null");
			return (Criteria) this;
		}

		public Criteria andPlanTransInterestEqualTo(Double value) {
			addCriterion("plan_trans_interest =", value, "planTransInterest");
			return (Criteria) this;
		}

		public Criteria andPlanTransInterestNotEqualTo(Double value) {
			addCriterion("plan_trans_interest <>", value, "planTransInterest");
			return (Criteria) this;
		}

		public Criteria andPlanTransInterestGreaterThan(Double value) {
			addCriterion("plan_trans_interest >", value, "planTransInterest");
			return (Criteria) this;
		}

		public Criteria andPlanTransInterestGreaterThanOrEqualTo(Double value) {
			addCriterion("plan_trans_interest >=", value, "planTransInterest");
			return (Criteria) this;
		}

		public Criteria andPlanTransInterestLessThan(Double value) {
			addCriterion("plan_trans_interest <", value, "planTransInterest");
			return (Criteria) this;
		}

		public Criteria andPlanTransInterestLessThanOrEqualTo(Double value) {
			addCriterion("plan_trans_interest <=", value, "planTransInterest");
			return (Criteria) this;
		}

		public Criteria andPlanTransInterestIn(List<Double> values) {
			addCriterion("plan_trans_interest in", values, "planTransInterest");
			return (Criteria) this;
		}

		public Criteria andPlanTransInterestNotIn(List<Double> values) {
			addCriterion("plan_trans_interest not in", values,
					"planTransInterest");
			return (Criteria) this;
		}

		public Criteria andPlanTransInterestBetween(Double value1, Double value2) {
			addCriterion("plan_trans_interest between", value1, value2,
					"planTransInterest");
			return (Criteria) this;
		}

		public Criteria andPlanTransInterestNotBetween(Double value1,
				Double value2) {
			addCriterion("plan_trans_interest not between", value1, value2,
					"planTransInterest");
			return (Criteria) this;
		}

		public Criteria andPlanFeeIsNull() {
			addCriterion("plan_fee is null");
			return (Criteria) this;
		}

		public Criteria andPlanFeeIsNotNull() {
			addCriterion("plan_fee is not null");
			return (Criteria) this;
		}

		public Criteria andPlanFeeEqualTo(Double value) {
			addCriterion("plan_fee =", value, "planFee");
			return (Criteria) this;
		}

		public Criteria andPlanFeeNotEqualTo(Double value) {
			addCriterion("plan_fee <>", value, "planFee");
			return (Criteria) this;
		}

		public Criteria andPlanFeeGreaterThan(Double value) {
			addCriterion("plan_fee >", value, "planFee");
			return (Criteria) this;
		}

		public Criteria andPlanFeeGreaterThanOrEqualTo(Double value) {
			addCriterion("plan_fee >=", value, "planFee");
			return (Criteria) this;
		}

		public Criteria andPlanFeeLessThan(Double value) {
			addCriterion("plan_fee <", value, "planFee");
			return (Criteria) this;
		}

		public Criteria andPlanFeeLessThanOrEqualTo(Double value) {
			addCriterion("plan_fee <=", value, "planFee");
			return (Criteria) this;
		}

		public Criteria andPlanFeeIn(List<Double> values) {
			addCriterion("plan_fee in", values, "planFee");
			return (Criteria) this;
		}

		public Criteria andPlanFeeNotIn(List<Double> values) {
			addCriterion("plan_fee not in", values, "planFee");
			return (Criteria) this;
		}

		public Criteria andPlanFeeBetween(Double value1, Double value2) {
			addCriterion("plan_fee between", value1, value2, "planFee");
			return (Criteria) this;
		}

		public Criteria andPlanFeeNotBetween(Double value1, Double value2) {
			addCriterion("plan_fee not between", value1, value2, "planFee");
			return (Criteria) this;
		}

		public Criteria andDiffAmountIsNull() {
			addCriterion("diff_amount is null");
			return (Criteria) this;
		}

		public Criteria andDiffAmountIsNotNull() {
			addCriterion("diff_amount is not null");
			return (Criteria) this;
		}

		public Criteria andDiffAmountEqualTo(Double value) {
			addCriterion("diff_amount =", value, "diffAmount");
			return (Criteria) this;
		}

		public Criteria andDiffAmountNotEqualTo(Double value) {
			addCriterion("diff_amount <>", value, "diffAmount");
			return (Criteria) this;
		}

		public Criteria andDiffAmountGreaterThan(Double value) {
			addCriterion("diff_amount >", value, "diffAmount");
			return (Criteria) this;
		}

		public Criteria andDiffAmountGreaterThanOrEqualTo(Double value) {
			addCriterion("diff_amount >=", value, "diffAmount");
			return (Criteria) this;
		}

		public Criteria andDiffAmountLessThan(Double value) {
			addCriterion("diff_amount <", value, "diffAmount");
			return (Criteria) this;
		}

		public Criteria andDiffAmountLessThanOrEqualTo(Double value) {
			addCriterion("diff_amount <=", value, "diffAmount");
			return (Criteria) this;
		}

		public Criteria andDiffAmountIn(List<Double> values) {
			addCriterion("diff_amount in", values, "diffAmount");
			return (Criteria) this;
		}

		public Criteria andDiffAmountNotIn(List<Double> values) {
			addCriterion("diff_amount not in", values, "diffAmount");
			return (Criteria) this;
		}

		public Criteria andDiffAmountBetween(Double value1, Double value2) {
			addCriterion("diff_amount between", value1, value2, "diffAmount");
			return (Criteria) this;
		}

		public Criteria andDiffAmountNotBetween(Double value1, Double value2) {
			addCriterion("diff_amount not between", value1, value2,
					"diffAmount");
			return (Criteria) this;
		}

		public Criteria andLeftPlanInterestIsNull() {
			addCriterion("left_plan_interest is null");
			return (Criteria) this;
		}

		public Criteria andLeftPlanInterestIsNotNull() {
			addCriterion("left_plan_interest is not null");
			return (Criteria) this;
		}

		public Criteria andLeftPlanInterestEqualTo(Double value) {
			addCriterion("left_plan_interest =", value, "leftPlanInterest");
			return (Criteria) this;
		}

		public Criteria andLeftPlanInterestNotEqualTo(Double value) {
			addCriterion("left_plan_interest <>", value, "leftPlanInterest");
			return (Criteria) this;
		}

		public Criteria andLeftPlanInterestGreaterThan(Double value) {
			addCriterion("left_plan_interest >", value, "leftPlanInterest");
			return (Criteria) this;
		}

		public Criteria andLeftPlanInterestGreaterThanOrEqualTo(Double value) {
			addCriterion("left_plan_interest >=", value, "leftPlanInterest");
			return (Criteria) this;
		}

		public Criteria andLeftPlanInterestLessThan(Double value) {
			addCriterion("left_plan_interest <", value, "leftPlanInterest");
			return (Criteria) this;
		}

		public Criteria andLeftPlanInterestLessThanOrEqualTo(Double value) {
			addCriterion("left_plan_interest <=", value, "leftPlanInterest");
			return (Criteria) this;
		}

		public Criteria andLeftPlanInterestIn(List<Double> values) {
			addCriterion("left_plan_interest in", values, "leftPlanInterest");
			return (Criteria) this;
		}

		public Criteria andLeftPlanInterestNotIn(List<Double> values) {
			addCriterion("left_plan_interest not in", values,
					"leftPlanInterest");
			return (Criteria) this;
		}

		public Criteria andLeftPlanInterestBetween(Double value1, Double value2) {
			addCriterion("left_plan_interest between", value1, value2,
					"leftPlanInterest");
			return (Criteria) this;
		}

		public Criteria andLeftPlanInterestNotBetween(Double value1,
				Double value2) {
			addCriterion("left_plan_interest not between", value1, value2,
					"leftPlanInterest");
			return (Criteria) this;
		}

		public Criteria andDoneTimeIsNull() {
			addCriterion("done_time is null");
			return (Criteria) this;
		}

		public Criteria andDoneTimeIsNotNull() {
			addCriterion("done_time is not null");
			return (Criteria) this;
		}

		public Criteria andDoneTimeEqualTo(Date value) {
			addCriterion("done_time =", value, "doneTime");
			return (Criteria) this;
		}

		public Criteria andDoneTimeNotEqualTo(Date value) {
			addCriterion("done_time <>", value, "doneTime");
			return (Criteria) this;
		}

		public Criteria andDoneTimeGreaterThan(Date value) {
			addCriterion("done_time >", value, "doneTime");
			return (Criteria) this;
		}

		public Criteria andDoneTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("done_time >=", value, "doneTime");
			return (Criteria) this;
		}

		public Criteria andDoneTimeLessThan(Date value) {
			addCriterion("done_time <", value, "doneTime");
			return (Criteria) this;
		}

		public Criteria andDoneTimeLessThanOrEqualTo(Date value) {
			addCriterion("done_time <=", value, "doneTime");
			return (Criteria) this;
		}

		public Criteria andDoneTimeIn(List<Date> values) {
			addCriterion("done_time in", values, "doneTime");
			return (Criteria) this;
		}

		public Criteria andDoneTimeNotIn(List<Date> values) {
			addCriterion("done_time not in", values, "doneTime");
			return (Criteria) this;
		}

		public Criteria andDoneTimeBetween(Date value1, Date value2) {
			addCriterion("done_time between", value1, value2, "doneTime");
			return (Criteria) this;
		}

		public Criteria andDoneTimeNotBetween(Date value1, Date value2) {
			addCriterion("done_time not between", value1, value2, "doneTime");
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