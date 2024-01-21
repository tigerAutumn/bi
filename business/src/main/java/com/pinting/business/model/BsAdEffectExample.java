package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsAdEffectExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsAdEffectExample() {
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

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("url not between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeIsNull() {
            addCriterion("monitor_type is null");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeIsNotNull() {
            addCriterion("monitor_type is not null");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeEqualTo(String value) {
            addCriterion("monitor_type =", value, "monitorType");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeNotEqualTo(String value) {
            addCriterion("monitor_type <>", value, "monitorType");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeGreaterThan(String value) {
            addCriterion("monitor_type >", value, "monitorType");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeGreaterThanOrEqualTo(String value) {
            addCriterion("monitor_type >=", value, "monitorType");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeLessThan(String value) {
            addCriterion("monitor_type <", value, "monitorType");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeLessThanOrEqualTo(String value) {
            addCriterion("monitor_type <=", value, "monitorType");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeLike(String value) {
            addCriterion("monitor_type like", value, "monitorType");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeNotLike(String value) {
            addCriterion("monitor_type not like", value, "monitorType");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeIn(List<String> values) {
            addCriterion("monitor_type in", values, "monitorType");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeNotIn(List<String> values) {
            addCriterion("monitor_type not in", values, "monitorType");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeBetween(String value1, String value2) {
            addCriterion("monitor_type between", value1, value2, "monitorType");
            return (Criteria) this;
        }

        public Criteria andMonitorTypeNotBetween(String value1, String value2) {
            addCriterion("monitor_type not between", value1, value2, "monitorType");
            return (Criteria) this;
        }

        public Criteria andVisitTimeIsNull() {
            addCriterion("visit_time is null");
            return (Criteria) this;
        }

        public Criteria andVisitTimeIsNotNull() {
            addCriterion("visit_time is not null");
            return (Criteria) this;
        }

        public Criteria andVisitTimeEqualTo(Date value) {
            addCriterion("visit_time =", value, "visitTime");
            return (Criteria) this;
        }

        public Criteria andVisitTimeNotEqualTo(Date value) {
            addCriterion("visit_time <>", value, "visitTime");
            return (Criteria) this;
        }

        public Criteria andVisitTimeGreaterThan(Date value) {
            addCriterion("visit_time >", value, "visitTime");
            return (Criteria) this;
        }

        public Criteria andVisitTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("visit_time >=", value, "visitTime");
            return (Criteria) this;
        }

        public Criteria andVisitTimeLessThan(Date value) {
            addCriterion("visit_time <", value, "visitTime");
            return (Criteria) this;
        }

        public Criteria andVisitTimeLessThanOrEqualTo(Date value) {
            addCriterion("visit_time <=", value, "visitTime");
            return (Criteria) this;
        }

        public Criteria andVisitTimeIn(List<Date> values) {
            addCriterion("visit_time in", values, "visitTime");
            return (Criteria) this;
        }

        public Criteria andVisitTimeNotIn(List<Date> values) {
            addCriterion("visit_time not in", values, "visitTime");
            return (Criteria) this;
        }

        public Criteria andVisitTimeBetween(Date value1, Date value2) {
            addCriterion("visit_time between", value1, value2, "visitTime");
            return (Criteria) this;
        }

        public Criteria andVisitTimeNotBetween(Date value1, Date value2) {
            addCriterion("visit_time not between", value1, value2, "visitTime");
            return (Criteria) this;
        }

        public Criteria andRegMobileIsNull() {
            addCriterion("reg_mobile is null");
            return (Criteria) this;
        }

        public Criteria andRegMobileIsNotNull() {
            addCriterion("reg_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andRegMobileEqualTo(String value) {
            addCriterion("reg_mobile =", value, "regMobile");
            return (Criteria) this;
        }

        public Criteria andRegMobileNotEqualTo(String value) {
            addCriterion("reg_mobile <>", value, "regMobile");
            return (Criteria) this;
        }

        public Criteria andRegMobileGreaterThan(String value) {
            addCriterion("reg_mobile >", value, "regMobile");
            return (Criteria) this;
        }

        public Criteria andRegMobileGreaterThanOrEqualTo(String value) {
            addCriterion("reg_mobile >=", value, "regMobile");
            return (Criteria) this;
        }

        public Criteria andRegMobileLessThan(String value) {
            addCriterion("reg_mobile <", value, "regMobile");
            return (Criteria) this;
        }

        public Criteria andRegMobileLessThanOrEqualTo(String value) {
            addCriterion("reg_mobile <=", value, "regMobile");
            return (Criteria) this;
        }

        public Criteria andRegMobileLike(String value) {
            addCriterion("reg_mobile like", value, "regMobile");
            return (Criteria) this;
        }

        public Criteria andRegMobileNotLike(String value) {
            addCriterion("reg_mobile not like", value, "regMobile");
            return (Criteria) this;
        }

        public Criteria andRegMobileIn(List<String> values) {
            addCriterion("reg_mobile in", values, "regMobile");
            return (Criteria) this;
        }

        public Criteria andRegMobileNotIn(List<String> values) {
            addCriterion("reg_mobile not in", values, "regMobile");
            return (Criteria) this;
        }

        public Criteria andRegMobileBetween(String value1, String value2) {
            addCriterion("reg_mobile between", value1, value2, "regMobile");
            return (Criteria) this;
        }

        public Criteria andRegMobileNotBetween(String value1, String value2) {
            addCriterion("reg_mobile not between", value1, value2, "regMobile");
            return (Criteria) this;
        }

        public Criteria andUtmSourceIsNull() {
            addCriterion("utm_source is null");
            return (Criteria) this;
        }

        public Criteria andUtmSourceIsNotNull() {
            addCriterion("utm_source is not null");
            return (Criteria) this;
        }

        public Criteria andUtmSourceEqualTo(String value) {
            addCriterion("utm_source =", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceNotEqualTo(String value) {
            addCriterion("utm_source <>", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceGreaterThan(String value) {
            addCriterion("utm_source >", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceGreaterThanOrEqualTo(String value) {
            addCriterion("utm_source >=", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceLessThan(String value) {
            addCriterion("utm_source <", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceLessThanOrEqualTo(String value) {
            addCriterion("utm_source <=", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceLike(String value) {
            addCriterion("utm_source like", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceNotLike(String value) {
            addCriterion("utm_source not like", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceIn(List<String> values) {
            addCriterion("utm_source in", values, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceNotIn(List<String> values) {
            addCriterion("utm_source not in", values, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceBetween(String value1, String value2) {
            addCriterion("utm_source between", value1, value2, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceNotBetween(String value1, String value2) {
            addCriterion("utm_source not between", value1, value2, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmMediumIsNull() {
            addCriterion("utm_medium is null");
            return (Criteria) this;
        }

        public Criteria andUtmMediumIsNotNull() {
            addCriterion("utm_medium is not null");
            return (Criteria) this;
        }

        public Criteria andUtmMediumEqualTo(String value) {
            addCriterion("utm_medium =", value, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumNotEqualTo(String value) {
            addCriterion("utm_medium <>", value, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumGreaterThan(String value) {
            addCriterion("utm_medium >", value, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumGreaterThanOrEqualTo(String value) {
            addCriterion("utm_medium >=", value, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumLessThan(String value) {
            addCriterion("utm_medium <", value, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumLessThanOrEqualTo(String value) {
            addCriterion("utm_medium <=", value, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumLike(String value) {
            addCriterion("utm_medium like", value, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumNotLike(String value) {
            addCriterion("utm_medium not like", value, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumIn(List<String> values) {
            addCriterion("utm_medium in", values, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumNotIn(List<String> values) {
            addCriterion("utm_medium not in", values, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumBetween(String value1, String value2) {
            addCriterion("utm_medium between", value1, value2, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumNotBetween(String value1, String value2) {
            addCriterion("utm_medium not between", value1, value2, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmTermIsNull() {
            addCriterion("utm_term is null");
            return (Criteria) this;
        }

        public Criteria andUtmTermIsNotNull() {
            addCriterion("utm_term is not null");
            return (Criteria) this;
        }

        public Criteria andUtmTermEqualTo(String value) {
            addCriterion("utm_term =", value, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermNotEqualTo(String value) {
            addCriterion("utm_term <>", value, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermGreaterThan(String value) {
            addCriterion("utm_term >", value, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermGreaterThanOrEqualTo(String value) {
            addCriterion("utm_term >=", value, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermLessThan(String value) {
            addCriterion("utm_term <", value, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermLessThanOrEqualTo(String value) {
            addCriterion("utm_term <=", value, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermLike(String value) {
            addCriterion("utm_term like", value, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermNotLike(String value) {
            addCriterion("utm_term not like", value, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermIn(List<String> values) {
            addCriterion("utm_term in", values, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermNotIn(List<String> values) {
            addCriterion("utm_term not in", values, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermBetween(String value1, String value2) {
            addCriterion("utm_term between", value1, value2, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermNotBetween(String value1, String value2) {
            addCriterion("utm_term not between", value1, value2, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmContentIsNull() {
            addCriterion("utm_content is null");
            return (Criteria) this;
        }

        public Criteria andUtmContentIsNotNull() {
            addCriterion("utm_content is not null");
            return (Criteria) this;
        }

        public Criteria andUtmContentEqualTo(String value) {
            addCriterion("utm_content =", value, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentNotEqualTo(String value) {
            addCriterion("utm_content <>", value, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentGreaterThan(String value) {
            addCriterion("utm_content >", value, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentGreaterThanOrEqualTo(String value) {
            addCriterion("utm_content >=", value, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentLessThan(String value) {
            addCriterion("utm_content <", value, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentLessThanOrEqualTo(String value) {
            addCriterion("utm_content <=", value, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentLike(String value) {
            addCriterion("utm_content like", value, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentNotLike(String value) {
            addCriterion("utm_content not like", value, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentIn(List<String> values) {
            addCriterion("utm_content in", values, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentNotIn(List<String> values) {
            addCriterion("utm_content not in", values, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentBetween(String value1, String value2) {
            addCriterion("utm_content between", value1, value2, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentNotBetween(String value1, String value2) {
            addCriterion("utm_content not between", value1, value2, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignIsNull() {
            addCriterion("utm_campaign is null");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignIsNotNull() {
            addCriterion("utm_campaign is not null");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignEqualTo(String value) {
            addCriterion("utm_campaign =", value, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignNotEqualTo(String value) {
            addCriterion("utm_campaign <>", value, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignGreaterThan(String value) {
            addCriterion("utm_campaign >", value, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignGreaterThanOrEqualTo(String value) {
            addCriterion("utm_campaign >=", value, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignLessThan(String value) {
            addCriterion("utm_campaign <", value, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignLessThanOrEqualTo(String value) {
            addCriterion("utm_campaign <=", value, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignLike(String value) {
            addCriterion("utm_campaign like", value, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignNotLike(String value) {
            addCriterion("utm_campaign not like", value, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignIn(List<String> values) {
            addCriterion("utm_campaign in", values, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignNotIn(List<String> values) {
            addCriterion("utm_campaign not in", values, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignBetween(String value1, String value2) {
            addCriterion("utm_campaign between", value1, value2, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignNotBetween(String value1, String value2) {
            addCriterion("utm_campaign not between", value1, value2, "utmCampaign");
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