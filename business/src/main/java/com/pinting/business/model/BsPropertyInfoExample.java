package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsPropertyInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsPropertyInfoExample() {
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

        public Criteria andPropertyNameIsNull() {
            addCriterion("property_name is null");
            return (Criteria) this;
        }

        public Criteria andPropertyNameIsNotNull() {
            addCriterion("property_name is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyNameEqualTo(String value) {
            addCriterion("property_name =", value, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameNotEqualTo(String value) {
            addCriterion("property_name <>", value, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameGreaterThan(String value) {
            addCriterion("property_name >", value, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameGreaterThanOrEqualTo(String value) {
            addCriterion("property_name >=", value, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameLessThan(String value) {
            addCriterion("property_name <", value, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameLessThanOrEqualTo(String value) {
            addCriterion("property_name <=", value, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameLike(String value) {
            addCriterion("property_name like", value, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameNotLike(String value) {
            addCriterion("property_name not like", value, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameIn(List<String> values) {
            addCriterion("property_name in", values, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameNotIn(List<String> values) {
            addCriterion("property_name not in", values, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameBetween(String value1, String value2) {
            addCriterion("property_name between", value1, value2, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameNotBetween(String value1, String value2) {
            addCriterion("property_name not between", value1, value2, "propertyName");
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
            addCriterion("property_symbol between", value1, value2, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySymbolNotBetween(String value1, String value2) {
            addCriterion("property_symbol not between", value1, value2, "propertySymbol");
            return (Criteria) this;
        }

        public Criteria andPropertySummaryIsNull() {
            addCriterion("property_summary is null");
            return (Criteria) this;
        }

        public Criteria andPropertySummaryIsNotNull() {
            addCriterion("property_summary is not null");
            return (Criteria) this;
        }

        public Criteria andPropertySummaryEqualTo(String value) {
            addCriterion("property_summary =", value, "propertySummary");
            return (Criteria) this;
        }

        public Criteria andPropertySummaryNotEqualTo(String value) {
            addCriterion("property_summary <>", value, "propertySummary");
            return (Criteria) this;
        }

        public Criteria andPropertySummaryGreaterThan(String value) {
            addCriterion("property_summary >", value, "propertySummary");
            return (Criteria) this;
        }

        public Criteria andPropertySummaryGreaterThanOrEqualTo(String value) {
            addCriterion("property_summary >=", value, "propertySummary");
            return (Criteria) this;
        }

        public Criteria andPropertySummaryLessThan(String value) {
            addCriterion("property_summary <", value, "propertySummary");
            return (Criteria) this;
        }

        public Criteria andPropertySummaryLessThanOrEqualTo(String value) {
            addCriterion("property_summary <=", value, "propertySummary");
            return (Criteria) this;
        }

        public Criteria andPropertySummaryLike(String value) {
            addCriterion("property_summary like", value, "propertySummary");
            return (Criteria) this;
        }

        public Criteria andPropertySummaryNotLike(String value) {
            addCriterion("property_summary not like", value, "propertySummary");
            return (Criteria) this;
        }

        public Criteria andPropertySummaryIn(List<String> values) {
            addCriterion("property_summary in", values, "propertySummary");
            return (Criteria) this;
        }

        public Criteria andPropertySummaryNotIn(List<String> values) {
            addCriterion("property_summary not in", values, "propertySummary");
            return (Criteria) this;
        }

        public Criteria andPropertySummaryBetween(String value1, String value2) {
            addCriterion("property_summary between", value1, value2, "propertySummary");
            return (Criteria) this;
        }

        public Criteria andPropertySummaryNotBetween(String value1, String value2) {
            addCriterion("property_summary not between", value1, value2, "propertySummary");
            return (Criteria) this;
        }

        public Criteria andReturnSourceIsNull() {
            addCriterion("return_source is null");
            return (Criteria) this;
        }

        public Criteria andReturnSourceIsNotNull() {
            addCriterion("return_source is not null");
            return (Criteria) this;
        }

        public Criteria andReturnSourceEqualTo(String value) {
            addCriterion("return_source =", value, "returnSource");
            return (Criteria) this;
        }

        public Criteria andReturnSourceNotEqualTo(String value) {
            addCriterion("return_source <>", value, "returnSource");
            return (Criteria) this;
        }

        public Criteria andReturnSourceGreaterThan(String value) {
            addCriterion("return_source >", value, "returnSource");
            return (Criteria) this;
        }

        public Criteria andReturnSourceGreaterThanOrEqualTo(String value) {
            addCriterion("return_source >=", value, "returnSource");
            return (Criteria) this;
        }

        public Criteria andReturnSourceLessThan(String value) {
            addCriterion("return_source <", value, "returnSource");
            return (Criteria) this;
        }

        public Criteria andReturnSourceLessThanOrEqualTo(String value) {
            addCriterion("return_source <=", value, "returnSource");
            return (Criteria) this;
        }

        public Criteria andReturnSourceLike(String value) {
            addCriterion("return_source like", value, "returnSource");
            return (Criteria) this;
        }

        public Criteria andReturnSourceNotLike(String value) {
            addCriterion("return_source not like", value, "returnSource");
            return (Criteria) this;
        }

        public Criteria andReturnSourceIn(List<String> values) {
            addCriterion("return_source in", values, "returnSource");
            return (Criteria) this;
        }

        public Criteria andReturnSourceNotIn(List<String> values) {
            addCriterion("return_source not in", values, "returnSource");
            return (Criteria) this;
        }

        public Criteria andReturnSourceBetween(String value1, String value2) {
            addCriterion("return_source between", value1, value2, "returnSource");
            return (Criteria) this;
        }

        public Criteria andReturnSourceNotBetween(String value1, String value2) {
            addCriterion("return_source not between", value1, value2, "returnSource");
            return (Criteria) this;
        }

        public Criteria andFundSecurityIsNull() {
            addCriterion("fund_security is null");
            return (Criteria) this;
        }

        public Criteria andFundSecurityIsNotNull() {
            addCriterion("fund_security is not null");
            return (Criteria) this;
        }

        public Criteria andFundSecurityEqualTo(String value) {
            addCriterion("fund_security =", value, "fundSecurity");
            return (Criteria) this;
        }

        public Criteria andFundSecurityNotEqualTo(String value) {
            addCriterion("fund_security <>", value, "fundSecurity");
            return (Criteria) this;
        }

        public Criteria andFundSecurityGreaterThan(String value) {
            addCriterion("fund_security >", value, "fundSecurity");
            return (Criteria) this;
        }

        public Criteria andFundSecurityGreaterThanOrEqualTo(String value) {
            addCriterion("fund_security >=", value, "fundSecurity");
            return (Criteria) this;
        }

        public Criteria andFundSecurityLessThan(String value) {
            addCriterion("fund_security <", value, "fundSecurity");
            return (Criteria) this;
        }

        public Criteria andFundSecurityLessThanOrEqualTo(String value) {
            addCriterion("fund_security <=", value, "fundSecurity");
            return (Criteria) this;
        }

        public Criteria andFundSecurityLike(String value) {
            addCriterion("fund_security like", value, "fundSecurity");
            return (Criteria) this;
        }

        public Criteria andFundSecurityNotLike(String value) {
            addCriterion("fund_security not like", value, "fundSecurity");
            return (Criteria) this;
        }

        public Criteria andFundSecurityIn(List<String> values) {
            addCriterion("fund_security in", values, "fundSecurity");
            return (Criteria) this;
        }

        public Criteria andFundSecurityNotIn(List<String> values) {
            addCriterion("fund_security not in", values, "fundSecurity");
            return (Criteria) this;
        }

        public Criteria andFundSecurityBetween(String value1, String value2) {
            addCriterion("fund_security between", value1, value2, "fundSecurity");
            return (Criteria) this;
        }

        public Criteria andFundSecurityNotBetween(String value1, String value2) {
            addCriterion("fund_security not between", value1, value2, "fundSecurity");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckIsNull() {
            addCriterion("orgnize_check is null");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckIsNotNull() {
            addCriterion("orgnize_check is not null");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckEqualTo(String value) {
            addCriterion("orgnize_check =", value, "orgnizeCheck");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckNotEqualTo(String value) {
            addCriterion("orgnize_check <>", value, "orgnizeCheck");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckGreaterThan(String value) {
            addCriterion("orgnize_check >", value, "orgnizeCheck");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckGreaterThanOrEqualTo(String value) {
            addCriterion("orgnize_check >=", value, "orgnizeCheck");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckLessThan(String value) {
            addCriterion("orgnize_check <", value, "orgnizeCheck");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckLessThanOrEqualTo(String value) {
            addCriterion("orgnize_check <=", value, "orgnizeCheck");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckLike(String value) {
            addCriterion("orgnize_check like", value, "orgnizeCheck");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckNotLike(String value) {
            addCriterion("orgnize_check not like", value, "orgnizeCheck");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckIn(List<String> values) {
            addCriterion("orgnize_check in", values, "orgnizeCheck");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckNotIn(List<String> values) {
            addCriterion("orgnize_check not in", values, "orgnizeCheck");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckBetween(String value1, String value2) {
            addCriterion("orgnize_check between", value1, value2, "orgnizeCheck");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckNotBetween(String value1, String value2) {
            addCriterion("orgnize_check not between", value1, value2, "orgnizeCheck");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckPicsIsNull() {
            addCriterion("orgnize_check_pics is null");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckPicsIsNotNull() {
            addCriterion("orgnize_check_pics is not null");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckPicsEqualTo(String value) {
            addCriterion("orgnize_check_pics =", value, "orgnizeCheckPics");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckPicsNotEqualTo(String value) {
            addCriterion("orgnize_check_pics <>", value, "orgnizeCheckPics");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckPicsGreaterThan(String value) {
            addCriterion("orgnize_check_pics >", value, "orgnizeCheckPics");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckPicsGreaterThanOrEqualTo(String value) {
            addCriterion("orgnize_check_pics >=", value, "orgnizeCheckPics");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckPicsLessThan(String value) {
            addCriterion("orgnize_check_pics <", value, "orgnizeCheckPics");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckPicsLessThanOrEqualTo(String value) {
            addCriterion("orgnize_check_pics <=", value, "orgnizeCheckPics");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckPicsLike(String value) {
            addCriterion("orgnize_check_pics like", value, "orgnizeCheckPics");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckPicsNotLike(String value) {
            addCriterion("orgnize_check_pics not like", value, "orgnizeCheckPics");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckPicsIn(List<String> values) {
            addCriterion("orgnize_check_pics in", values, "orgnizeCheckPics");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckPicsNotIn(List<String> values) {
            addCriterion("orgnize_check_pics not in", values, "orgnizeCheckPics");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckPicsBetween(String value1, String value2) {
            addCriterion("orgnize_check_pics between", value1, value2, "orgnizeCheckPics");
            return (Criteria) this;
        }

        public Criteria andOrgnizeCheckPicsNotBetween(String value1, String value2) {
            addCriterion("orgnize_check_pics not between", value1, value2, "orgnizeCheckPics");
            return (Criteria) this;
        }

        public Criteria andCoopProtocolPicsIsNull() {
            addCriterion("coop_protocol_pics is null");
            return (Criteria) this;
        }

        public Criteria andCoopProtocolPicsIsNotNull() {
            addCriterion("coop_protocol_pics is not null");
            return (Criteria) this;
        }

        public Criteria andCoopProtocolPicsEqualTo(String value) {
            addCriterion("coop_protocol_pics =", value, "coopProtocolPics");
            return (Criteria) this;
        }

        public Criteria andCoopProtocolPicsNotEqualTo(String value) {
            addCriterion("coop_protocol_pics <>", value, "coopProtocolPics");
            return (Criteria) this;
        }

        public Criteria andCoopProtocolPicsGreaterThan(String value) {
            addCriterion("coop_protocol_pics >", value, "coopProtocolPics");
            return (Criteria) this;
        }

        public Criteria andCoopProtocolPicsGreaterThanOrEqualTo(String value) {
            addCriterion("coop_protocol_pics >=", value, "coopProtocolPics");
            return (Criteria) this;
        }

        public Criteria andCoopProtocolPicsLessThan(String value) {
            addCriterion("coop_protocol_pics <", value, "coopProtocolPics");
            return (Criteria) this;
        }

        public Criteria andCoopProtocolPicsLessThanOrEqualTo(String value) {
            addCriterion("coop_protocol_pics <=", value, "coopProtocolPics");
            return (Criteria) this;
        }

        public Criteria andCoopProtocolPicsLike(String value) {
            addCriterion("coop_protocol_pics like", value, "coopProtocolPics");
            return (Criteria) this;
        }

        public Criteria andCoopProtocolPicsNotLike(String value) {
            addCriterion("coop_protocol_pics not like", value, "coopProtocolPics");
            return (Criteria) this;
        }

        public Criteria andCoopProtocolPicsIn(List<String> values) {
            addCriterion("coop_protocol_pics in", values, "coopProtocolPics");
            return (Criteria) this;
        }

        public Criteria andCoopProtocolPicsNotIn(List<String> values) {
            addCriterion("coop_protocol_pics not in", values, "coopProtocolPics");
            return (Criteria) this;
        }

        public Criteria andCoopProtocolPicsBetween(String value1, String value2) {
            addCriterion("coop_protocol_pics between", value1, value2, "coopProtocolPics");
            return (Criteria) this;
        }

        public Criteria andCoopProtocolPicsNotBetween(String value1, String value2) {
            addCriterion("coop_protocol_pics not between", value1, value2, "coopProtocolPics");
            return (Criteria) this;
        }

        public Criteria andRatingGradeIsNull() {
            addCriterion("rating_grade is null");
            return (Criteria) this;
        }

        public Criteria andRatingGradeIsNotNull() {
            addCriterion("rating_grade is not null");
            return (Criteria) this;
        }

        public Criteria andRatingGradeEqualTo(String value) {
            addCriterion("rating_grade =", value, "ratingGrade");
            return (Criteria) this;
        }

        public Criteria andRatingGradeNotEqualTo(String value) {
            addCriterion("rating_grade <>", value, "ratingGrade");
            return (Criteria) this;
        }

        public Criteria andRatingGradeGreaterThan(String value) {
            addCriterion("rating_grade >", value, "ratingGrade");
            return (Criteria) this;
        }

        public Criteria andRatingGradeGreaterThanOrEqualTo(String value) {
            addCriterion("rating_grade >=", value, "ratingGrade");
            return (Criteria) this;
        }

        public Criteria andRatingGradeLessThan(String value) {
            addCriterion("rating_grade <", value, "ratingGrade");
            return (Criteria) this;
        }

        public Criteria andRatingGradeLessThanOrEqualTo(String value) {
            addCriterion("rating_grade <=", value, "ratingGrade");
            return (Criteria) this;
        }

        public Criteria andRatingGradeLike(String value) {
            addCriterion("rating_grade like", value, "ratingGrade");
            return (Criteria) this;
        }

        public Criteria andRatingGradeNotLike(String value) {
            addCriterion("rating_grade not like", value, "ratingGrade");
            return (Criteria) this;
        }

        public Criteria andRatingGradeIn(List<String> values) {
            addCriterion("rating_grade in", values, "ratingGrade");
            return (Criteria) this;
        }

        public Criteria andRatingGradeNotIn(List<String> values) {
            addCriterion("rating_grade not in", values, "ratingGrade");
            return (Criteria) this;
        }

        public Criteria andRatingGradeBetween(String value1, String value2) {
            addCriterion("rating_grade between", value1, value2, "ratingGrade");
            return (Criteria) this;
        }

        public Criteria andRatingGradeNotBetween(String value1, String value2) {
            addCriterion("rating_grade not between", value1, value2, "ratingGrade");
            return (Criteria) this;
        }

        public Criteria andRatingGradePicsIsNull() {
            addCriterion("rating_grade_pics is null");
            return (Criteria) this;
        }

        public Criteria andRatingGradePicsIsNotNull() {
            addCriterion("rating_grade_pics is not null");
            return (Criteria) this;
        }

        public Criteria andRatingGradePicsEqualTo(String value) {
            addCriterion("rating_grade_pics =", value, "ratingGradePics");
            return (Criteria) this;
        }

        public Criteria andRatingGradePicsNotEqualTo(String value) {
            addCriterion("rating_grade_pics <>", value, "ratingGradePics");
            return (Criteria) this;
        }

        public Criteria andRatingGradePicsGreaterThan(String value) {
            addCriterion("rating_grade_pics >", value, "ratingGradePics");
            return (Criteria) this;
        }

        public Criteria andRatingGradePicsGreaterThanOrEqualTo(String value) {
            addCriterion("rating_grade_pics >=", value, "ratingGradePics");
            return (Criteria) this;
        }

        public Criteria andRatingGradePicsLessThan(String value) {
            addCriterion("rating_grade_pics <", value, "ratingGradePics");
            return (Criteria) this;
        }

        public Criteria andRatingGradePicsLessThanOrEqualTo(String value) {
            addCriterion("rating_grade_pics <=", value, "ratingGradePics");
            return (Criteria) this;
        }

        public Criteria andRatingGradePicsLike(String value) {
            addCriterion("rating_grade_pics like", value, "ratingGradePics");
            return (Criteria) this;
        }

        public Criteria andRatingGradePicsNotLike(String value) {
            addCriterion("rating_grade_pics not like", value, "ratingGradePics");
            return (Criteria) this;
        }

        public Criteria andRatingGradePicsIn(List<String> values) {
            addCriterion("rating_grade_pics in", values, "ratingGradePics");
            return (Criteria) this;
        }

        public Criteria andRatingGradePicsNotIn(List<String> values) {
            addCriterion("rating_grade_pics not in", values, "ratingGradePics");
            return (Criteria) this;
        }

        public Criteria andRatingGradePicsBetween(String value1, String value2) {
            addCriterion("rating_grade_pics between", value1, value2, "ratingGradePics");
            return (Criteria) this;
        }

        public Criteria andRatingGradePicsNotBetween(String value1, String value2) {
            addCriterion("rating_grade_pics not between", value1, value2, "ratingGradePics");
            return (Criteria) this;
        }

        public Criteria andLoanProtocolPicsIsNull() {
            addCriterion("loan_protocol_pics is null");
            return (Criteria) this;
        }

        public Criteria andLoanProtocolPicsIsNotNull() {
            addCriterion("loan_protocol_pics is not null");
            return (Criteria) this;
        }

        public Criteria andLoanProtocolPicsEqualTo(String value) {
            addCriterion("loan_protocol_pics =", value, "loanProtocolPics");
            return (Criteria) this;
        }

        public Criteria andLoanProtocolPicsNotEqualTo(String value) {
            addCriterion("loan_protocol_pics <>", value, "loanProtocolPics");
            return (Criteria) this;
        }

        public Criteria andLoanProtocolPicsGreaterThan(String value) {
            addCriterion("loan_protocol_pics >", value, "loanProtocolPics");
            return (Criteria) this;
        }

        public Criteria andLoanProtocolPicsGreaterThanOrEqualTo(String value) {
            addCriterion("loan_protocol_pics >=", value, "loanProtocolPics");
            return (Criteria) this;
        }

        public Criteria andLoanProtocolPicsLessThan(String value) {
            addCriterion("loan_protocol_pics <", value, "loanProtocolPics");
            return (Criteria) this;
        }

        public Criteria andLoanProtocolPicsLessThanOrEqualTo(String value) {
            addCriterion("loan_protocol_pics <=", value, "loanProtocolPics");
            return (Criteria) this;
        }

        public Criteria andLoanProtocolPicsLike(String value) {
            addCriterion("loan_protocol_pics like", value, "loanProtocolPics");
            return (Criteria) this;
        }

        public Criteria andLoanProtocolPicsNotLike(String value) {
            addCriterion("loan_protocol_pics not like", value, "loanProtocolPics");
            return (Criteria) this;
        }

        public Criteria andLoanProtocolPicsIn(List<String> values) {
            addCriterion("loan_protocol_pics in", values, "loanProtocolPics");
            return (Criteria) this;
        }

        public Criteria andLoanProtocolPicsNotIn(List<String> values) {
            addCriterion("loan_protocol_pics not in", values, "loanProtocolPics");
            return (Criteria) this;
        }

        public Criteria andLoanProtocolPicsBetween(String value1, String value2) {
            addCriterion("loan_protocol_pics between", value1, value2, "loanProtocolPics");
            return (Criteria) this;
        }

        public Criteria andLoanProtocolPicsNotBetween(String value1, String value2) {
            addCriterion("loan_protocol_pics not between", value1, value2, "loanProtocolPics");
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