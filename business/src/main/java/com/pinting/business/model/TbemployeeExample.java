package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbemployeeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbemployeeExample() {
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

        public Criteria andLuseridIsNull() {
            addCriterion("lUserId is null");
            return (Criteria) this;
        }

        public Criteria andLuseridIsNotNull() {
            addCriterion("lUserId is not null");
            return (Criteria) this;
        }

        public Criteria andLuseridEqualTo(Long value) {
            addCriterion("lUserId =", value, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridNotEqualTo(Long value) {
            addCriterion("lUserId <>", value, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridGreaterThan(Long value) {
            addCriterion("lUserId >", value, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridGreaterThanOrEqualTo(Long value) {
            addCriterion("lUserId >=", value, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridLessThan(Long value) {
            addCriterion("lUserId <", value, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridLessThanOrEqualTo(Long value) {
            addCriterion("lUserId <=", value, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridIn(List<Long> values) {
            addCriterion("lUserId in", values, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridNotIn(List<Long> values) {
            addCriterion("lUserId not in", values, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridBetween(Long value1, Long value2) {
            addCriterion("lUserId between", value1, value2, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridNotBetween(Long value1, Long value2) {
            addCriterion("lUserId not between", value1, value2, "luserid");
            return (Criteria) this;
        }

        public Criteria andStrloginidIsNull() {
            addCriterion("strLoginId is null");
            return (Criteria) this;
        }

        public Criteria andStrloginidIsNotNull() {
            addCriterion("strLoginId is not null");
            return (Criteria) this;
        }

        public Criteria andStrloginidEqualTo(String value) {
            addCriterion("strLoginId =", value, "strloginid");
            return (Criteria) this;
        }

        public Criteria andStrloginidNotEqualTo(String value) {
            addCriterion("strLoginId <>", value, "strloginid");
            return (Criteria) this;
        }

        public Criteria andStrloginidGreaterThan(String value) {
            addCriterion("strLoginId >", value, "strloginid");
            return (Criteria) this;
        }

        public Criteria andStrloginidGreaterThanOrEqualTo(String value) {
            addCriterion("strLoginId >=", value, "strloginid");
            return (Criteria) this;
        }

        public Criteria andStrloginidLessThan(String value) {
            addCriterion("strLoginId <", value, "strloginid");
            return (Criteria) this;
        }

        public Criteria andStrloginidLessThanOrEqualTo(String value) {
            addCriterion("strLoginId <=", value, "strloginid");
            return (Criteria) this;
        }

        public Criteria andStrloginidLike(String value) {
            addCriterion("strLoginId like", value, "strloginid");
            return (Criteria) this;
        }

        public Criteria andStrloginidNotLike(String value) {
            addCriterion("strLoginId not like", value, "strloginid");
            return (Criteria) this;
        }

        public Criteria andStrloginidIn(List<String> values) {
            addCriterion("strLoginId in", values, "strloginid");
            return (Criteria) this;
        }

        public Criteria andStrloginidNotIn(List<String> values) {
            addCriterion("strLoginId not in", values, "strloginid");
            return (Criteria) this;
        }

        public Criteria andStrloginidBetween(String value1, String value2) {
            addCriterion("strLoginId between", value1, value2, "strloginid");
            return (Criteria) this;
        }

        public Criteria andStrloginidNotBetween(String value1, String value2) {
            addCriterion("strLoginId not between", value1, value2, "strloginid");
            return (Criteria) this;
        }

        public Criteria andStrnameIsNull() {
            addCriterion("strName is null");
            return (Criteria) this;
        }

        public Criteria andStrnameIsNotNull() {
            addCriterion("strName is not null");
            return (Criteria) this;
        }

        public Criteria andStrnameEqualTo(String value) {
            addCriterion("strName =", value, "strname");
            return (Criteria) this;
        }

        public Criteria andStrnameNotEqualTo(String value) {
            addCriterion("strName <>", value, "strname");
            return (Criteria) this;
        }

        public Criteria andStrnameGreaterThan(String value) {
            addCriterion("strName >", value, "strname");
            return (Criteria) this;
        }

        public Criteria andStrnameGreaterThanOrEqualTo(String value) {
            addCriterion("strName >=", value, "strname");
            return (Criteria) this;
        }

        public Criteria andStrnameLessThan(String value) {
            addCriterion("strName <", value, "strname");
            return (Criteria) this;
        }

        public Criteria andStrnameLessThanOrEqualTo(String value) {
            addCriterion("strName <=", value, "strname");
            return (Criteria) this;
        }

        public Criteria andStrnameLike(String value) {
            addCriterion("strName like", value, "strname");
            return (Criteria) this;
        }

        public Criteria andStrnameNotLike(String value) {
            addCriterion("strName not like", value, "strname");
            return (Criteria) this;
        }

        public Criteria andStrnameIn(List<String> values) {
            addCriterion("strName in", values, "strname");
            return (Criteria) this;
        }

        public Criteria andStrnameNotIn(List<String> values) {
            addCriterion("strName not in", values, "strname");
            return (Criteria) this;
        }

        public Criteria andStrnameBetween(String value1, String value2) {
            addCriterion("strName between", value1, value2, "strname");
            return (Criteria) this;
        }

        public Criteria andStrnameNotBetween(String value1, String value2) {
            addCriterion("strName not between", value1, value2, "strname");
            return (Criteria) this;
        }

        public Criteria andNsexIsNull() {
            addCriterion("nSex is null");
            return (Criteria) this;
        }

        public Criteria andNsexIsNotNull() {
            addCriterion("nSex is not null");
            return (Criteria) this;
        }

        public Criteria andNsexEqualTo(Integer value) {
            addCriterion("nSex =", value, "nsex");
            return (Criteria) this;
        }

        public Criteria andNsexNotEqualTo(Integer value) {
            addCriterion("nSex <>", value, "nsex");
            return (Criteria) this;
        }

        public Criteria andNsexGreaterThan(Integer value) {
            addCriterion("nSex >", value, "nsex");
            return (Criteria) this;
        }

        public Criteria andNsexGreaterThanOrEqualTo(Integer value) {
            addCriterion("nSex >=", value, "nsex");
            return (Criteria) this;
        }

        public Criteria andNsexLessThan(Integer value) {
            addCriterion("nSex <", value, "nsex");
            return (Criteria) this;
        }

        public Criteria andNsexLessThanOrEqualTo(Integer value) {
            addCriterion("nSex <=", value, "nsex");
            return (Criteria) this;
        }

        public Criteria andNsexIn(List<Integer> values) {
            addCriterion("nSex in", values, "nsex");
            return (Criteria) this;
        }

        public Criteria andNsexNotIn(List<Integer> values) {
            addCriterion("nSex not in", values, "nsex");
            return (Criteria) this;
        }

        public Criteria andNsexBetween(Integer value1, Integer value2) {
            addCriterion("nSex between", value1, value2, "nsex");
            return (Criteria) this;
        }

        public Criteria andNsexNotBetween(Integer value1, Integer value2) {
            addCriterion("nSex not between", value1, value2, "nsex");
            return (Criteria) this;
        }

        public Criteria andStremployeenoIsNull() {
            addCriterion("strEmployeeNo is null");
            return (Criteria) this;
        }

        public Criteria andStremployeenoIsNotNull() {
            addCriterion("strEmployeeNo is not null");
            return (Criteria) this;
        }

        public Criteria andStremployeenoEqualTo(String value) {
            addCriterion("strEmployeeNo =", value, "stremployeeno");
            return (Criteria) this;
        }

        public Criteria andStremployeenoNotEqualTo(String value) {
            addCriterion("strEmployeeNo <>", value, "stremployeeno");
            return (Criteria) this;
        }

        public Criteria andStremployeenoGreaterThan(String value) {
            addCriterion("strEmployeeNo >", value, "stremployeeno");
            return (Criteria) this;
        }

        public Criteria andStremployeenoGreaterThanOrEqualTo(String value) {
            addCriterion("strEmployeeNo >=", value, "stremployeeno");
            return (Criteria) this;
        }

        public Criteria andStremployeenoLessThan(String value) {
            addCriterion("strEmployeeNo <", value, "stremployeeno");
            return (Criteria) this;
        }

        public Criteria andStremployeenoLessThanOrEqualTo(String value) {
            addCriterion("strEmployeeNo <=", value, "stremployeeno");
            return (Criteria) this;
        }

        public Criteria andStremployeenoLike(String value) {
            addCriterion("strEmployeeNo like", value, "stremployeeno");
            return (Criteria) this;
        }

        public Criteria andStremployeenoNotLike(String value) {
            addCriterion("strEmployeeNo not like", value, "stremployeeno");
            return (Criteria) this;
        }

        public Criteria andStremployeenoIn(List<String> values) {
            addCriterion("strEmployeeNo in", values, "stremployeeno");
            return (Criteria) this;
        }

        public Criteria andStremployeenoNotIn(List<String> values) {
            addCriterion("strEmployeeNo not in", values, "stremployeeno");
            return (Criteria) this;
        }

        public Criteria andStremployeenoBetween(String value1, String value2) {
            addCriterion("strEmployeeNo between", value1, value2, "stremployeeno");
            return (Criteria) this;
        }

        public Criteria andStremployeenoNotBetween(String value1, String value2) {
            addCriterion("strEmployeeNo not between", value1, value2, "stremployeeno");
            return (Criteria) this;
        }

        public Criteria andStrtitleIsNull() {
            addCriterion("strTitle is null");
            return (Criteria) this;
        }

        public Criteria andStrtitleIsNotNull() {
            addCriterion("strTitle is not null");
            return (Criteria) this;
        }

        public Criteria andStrtitleEqualTo(String value) {
            addCriterion("strTitle =", value, "strtitle");
            return (Criteria) this;
        }

        public Criteria andStrtitleNotEqualTo(String value) {
            addCriterion("strTitle <>", value, "strtitle");
            return (Criteria) this;
        }

        public Criteria andStrtitleGreaterThan(String value) {
            addCriterion("strTitle >", value, "strtitle");
            return (Criteria) this;
        }

        public Criteria andStrtitleGreaterThanOrEqualTo(String value) {
            addCriterion("strTitle >=", value, "strtitle");
            return (Criteria) this;
        }

        public Criteria andStrtitleLessThan(String value) {
            addCriterion("strTitle <", value, "strtitle");
            return (Criteria) this;
        }

        public Criteria andStrtitleLessThanOrEqualTo(String value) {
            addCriterion("strTitle <=", value, "strtitle");
            return (Criteria) this;
        }

        public Criteria andStrtitleLike(String value) {
            addCriterion("strTitle like", value, "strtitle");
            return (Criteria) this;
        }

        public Criteria andStrtitleNotLike(String value) {
            addCriterion("strTitle not like", value, "strtitle");
            return (Criteria) this;
        }

        public Criteria andStrtitleIn(List<String> values) {
            addCriterion("strTitle in", values, "strtitle");
            return (Criteria) this;
        }

        public Criteria andStrtitleNotIn(List<String> values) {
            addCriterion("strTitle not in", values, "strtitle");
            return (Criteria) this;
        }

        public Criteria andStrtitleBetween(String value1, String value2) {
            addCriterion("strTitle between", value1, value2, "strtitle");
            return (Criteria) this;
        }

        public Criteria andStrtitleNotBetween(String value1, String value2) {
            addCriterion("strTitle not between", value1, value2, "strtitle");
            return (Criteria) this;
        }

        public Criteria andStrroleIsNull() {
            addCriterion("strRole is null");
            return (Criteria) this;
        }

        public Criteria andStrroleIsNotNull() {
            addCriterion("strRole is not null");
            return (Criteria) this;
        }

        public Criteria andStrroleEqualTo(String value) {
            addCriterion("strRole =", value, "strrole");
            return (Criteria) this;
        }

        public Criteria andStrroleNotEqualTo(String value) {
            addCriterion("strRole <>", value, "strrole");
            return (Criteria) this;
        }

        public Criteria andStrroleGreaterThan(String value) {
            addCriterion("strRole >", value, "strrole");
            return (Criteria) this;
        }

        public Criteria andStrroleGreaterThanOrEqualTo(String value) {
            addCriterion("strRole >=", value, "strrole");
            return (Criteria) this;
        }

        public Criteria andStrroleLessThan(String value) {
            addCriterion("strRole <", value, "strrole");
            return (Criteria) this;
        }

        public Criteria andStrroleLessThanOrEqualTo(String value) {
            addCriterion("strRole <=", value, "strrole");
            return (Criteria) this;
        }

        public Criteria andStrroleLike(String value) {
            addCriterion("strRole like", value, "strrole");
            return (Criteria) this;
        }

        public Criteria andStrroleNotLike(String value) {
            addCriterion("strRole not like", value, "strrole");
            return (Criteria) this;
        }

        public Criteria andStrroleIn(List<String> values) {
            addCriterion("strRole in", values, "strrole");
            return (Criteria) this;
        }

        public Criteria andStrroleNotIn(List<String> values) {
            addCriterion("strRole not in", values, "strrole");
            return (Criteria) this;
        }

        public Criteria andStrroleBetween(String value1, String value2) {
            addCriterion("strRole between", value1, value2, "strrole");
            return (Criteria) this;
        }

        public Criteria andStrroleNotBetween(String value1, String value2) {
            addCriterion("strRole not between", value1, value2, "strrole");
            return (Criteria) this;
        }

        public Criteria andStrmobileIsNull() {
            addCriterion("strMobile is null");
            return (Criteria) this;
        }

        public Criteria andStrmobileIsNotNull() {
            addCriterion("strMobile is not null");
            return (Criteria) this;
        }

        public Criteria andStrmobileEqualTo(String value) {
            addCriterion("strMobile =", value, "strmobile");
            return (Criteria) this;
        }

        public Criteria andStrmobileNotEqualTo(String value) {
            addCriterion("strMobile <>", value, "strmobile");
            return (Criteria) this;
        }

        public Criteria andStrmobileGreaterThan(String value) {
            addCriterion("strMobile >", value, "strmobile");
            return (Criteria) this;
        }

        public Criteria andStrmobileGreaterThanOrEqualTo(String value) {
            addCriterion("strMobile >=", value, "strmobile");
            return (Criteria) this;
        }

        public Criteria andStrmobileLessThan(String value) {
            addCriterion("strMobile <", value, "strmobile");
            return (Criteria) this;
        }

        public Criteria andStrmobileLessThanOrEqualTo(String value) {
            addCriterion("strMobile <=", value, "strmobile");
            return (Criteria) this;
        }

        public Criteria andStrmobileLike(String value) {
            addCriterion("strMobile like", value, "strmobile");
            return (Criteria) this;
        }

        public Criteria andStrmobileNotLike(String value) {
            addCriterion("strMobile not like", value, "strmobile");
            return (Criteria) this;
        }

        public Criteria andStrmobileIn(List<String> values) {
            addCriterion("strMobile in", values, "strmobile");
            return (Criteria) this;
        }

        public Criteria andStrmobileNotIn(List<String> values) {
            addCriterion("strMobile not in", values, "strmobile");
            return (Criteria) this;
        }

        public Criteria andStrmobileBetween(String value1, String value2) {
            addCriterion("strMobile between", value1, value2, "strmobile");
            return (Criteria) this;
        }

        public Criteria andStrmobileNotBetween(String value1, String value2) {
            addCriterion("strMobile not between", value1, value2, "strmobile");
            return (Criteria) this;
        }

        public Criteria andStremailIsNull() {
            addCriterion("strEmail is null");
            return (Criteria) this;
        }

        public Criteria andStremailIsNotNull() {
            addCriterion("strEmail is not null");
            return (Criteria) this;
        }

        public Criteria andStremailEqualTo(String value) {
            addCriterion("strEmail =", value, "stremail");
            return (Criteria) this;
        }

        public Criteria andStremailNotEqualTo(String value) {
            addCriterion("strEmail <>", value, "stremail");
            return (Criteria) this;
        }

        public Criteria andStremailGreaterThan(String value) {
            addCriterion("strEmail >", value, "stremail");
            return (Criteria) this;
        }

        public Criteria andStremailGreaterThanOrEqualTo(String value) {
            addCriterion("strEmail >=", value, "stremail");
            return (Criteria) this;
        }

        public Criteria andStremailLessThan(String value) {
            addCriterion("strEmail <", value, "stremail");
            return (Criteria) this;
        }

        public Criteria andStremailLessThanOrEqualTo(String value) {
            addCriterion("strEmail <=", value, "stremail");
            return (Criteria) this;
        }

        public Criteria andStremailLike(String value) {
            addCriterion("strEmail like", value, "stremail");
            return (Criteria) this;
        }

        public Criteria andStremailNotLike(String value) {
            addCriterion("strEmail not like", value, "stremail");
            return (Criteria) this;
        }

        public Criteria andStremailIn(List<String> values) {
            addCriterion("strEmail in", values, "stremail");
            return (Criteria) this;
        }

        public Criteria andStremailNotIn(List<String> values) {
            addCriterion("strEmail not in", values, "stremail");
            return (Criteria) this;
        }

        public Criteria andStremailBetween(String value1, String value2) {
            addCriterion("strEmail between", value1, value2, "stremail");
            return (Criteria) this;
        }

        public Criteria andStremailNotBetween(String value1, String value2) {
            addCriterion("strEmail not between", value1, value2, "stremail");
            return (Criteria) this;
        }

        public Criteria andStrbirthdayIsNull() {
            addCriterion("strBirthday is null");
            return (Criteria) this;
        }

        public Criteria andStrbirthdayIsNotNull() {
            addCriterion("strBirthday is not null");
            return (Criteria) this;
        }

        public Criteria andStrbirthdayEqualTo(String value) {
            addCriterion("strBirthday =", value, "strbirthday");
            return (Criteria) this;
        }

        public Criteria andStrbirthdayNotEqualTo(String value) {
            addCriterion("strBirthday <>", value, "strbirthday");
            return (Criteria) this;
        }

        public Criteria andStrbirthdayGreaterThan(String value) {
            addCriterion("strBirthday >", value, "strbirthday");
            return (Criteria) this;
        }

        public Criteria andStrbirthdayGreaterThanOrEqualTo(String value) {
            addCriterion("strBirthday >=", value, "strbirthday");
            return (Criteria) this;
        }

        public Criteria andStrbirthdayLessThan(String value) {
            addCriterion("strBirthday <", value, "strbirthday");
            return (Criteria) this;
        }

        public Criteria andStrbirthdayLessThanOrEqualTo(String value) {
            addCriterion("strBirthday <=", value, "strbirthday");
            return (Criteria) this;
        }

        public Criteria andStrbirthdayLike(String value) {
            addCriterion("strBirthday like", value, "strbirthday");
            return (Criteria) this;
        }

        public Criteria andStrbirthdayNotLike(String value) {
            addCriterion("strBirthday not like", value, "strbirthday");
            return (Criteria) this;
        }

        public Criteria andStrbirthdayIn(List<String> values) {
            addCriterion("strBirthday in", values, "strbirthday");
            return (Criteria) this;
        }

        public Criteria andStrbirthdayNotIn(List<String> values) {
            addCriterion("strBirthday not in", values, "strbirthday");
            return (Criteria) this;
        }

        public Criteria andStrbirthdayBetween(String value1, String value2) {
            addCriterion("strBirthday between", value1, value2, "strbirthday");
            return (Criteria) this;
        }

        public Criteria andStrbirthdayNotBetween(String value1, String value2) {
            addCriterion("strBirthday not between", value1, value2, "strbirthday");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeIsNull() {
            addCriterion("strDeptCode is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeIsNotNull() {
            addCriterion("strDeptCode is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeEqualTo(String value) {
            addCriterion("strDeptCode =", value, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeNotEqualTo(String value) {
            addCriterion("strDeptCode <>", value, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeGreaterThan(String value) {
            addCriterion("strDeptCode >", value, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeGreaterThanOrEqualTo(String value) {
            addCriterion("strDeptCode >=", value, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeLessThan(String value) {
            addCriterion("strDeptCode <", value, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeLessThanOrEqualTo(String value) {
            addCriterion("strDeptCode <=", value, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeLike(String value) {
            addCriterion("strDeptCode like", value, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeNotLike(String value) {
            addCriterion("strDeptCode not like", value, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeIn(List<String> values) {
            addCriterion("strDeptCode in", values, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeNotIn(List<String> values) {
            addCriterion("strDeptCode not in", values, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeBetween(String value1, String value2) {
            addCriterion("strDeptCode between", value1, value2, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcodeNotBetween(String value1, String value2) {
            addCriterion("strDeptCode not between", value1, value2, "strdeptcode");
            return (Criteria) this;
        }

        public Criteria andStrdeptnameIsNull() {
            addCriterion("strDeptName is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptnameIsNotNull() {
            addCriterion("strDeptName is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptnameEqualTo(String value) {
            addCriterion("strDeptName =", value, "strdeptname");
            return (Criteria) this;
        }

        public Criteria andStrdeptnameNotEqualTo(String value) {
            addCriterion("strDeptName <>", value, "strdeptname");
            return (Criteria) this;
        }

        public Criteria andStrdeptnameGreaterThan(String value) {
            addCriterion("strDeptName >", value, "strdeptname");
            return (Criteria) this;
        }

        public Criteria andStrdeptnameGreaterThanOrEqualTo(String value) {
            addCriterion("strDeptName >=", value, "strdeptname");
            return (Criteria) this;
        }

        public Criteria andStrdeptnameLessThan(String value) {
            addCriterion("strDeptName <", value, "strdeptname");
            return (Criteria) this;
        }

        public Criteria andStrdeptnameLessThanOrEqualTo(String value) {
            addCriterion("strDeptName <=", value, "strdeptname");
            return (Criteria) this;
        }

        public Criteria andStrdeptnameLike(String value) {
            addCriterion("strDeptName like", value, "strdeptname");
            return (Criteria) this;
        }

        public Criteria andStrdeptnameNotLike(String value) {
            addCriterion("strDeptName not like", value, "strdeptname");
            return (Criteria) this;
        }

        public Criteria andStrdeptnameIn(List<String> values) {
            addCriterion("strDeptName in", values, "strdeptname");
            return (Criteria) this;
        }

        public Criteria andStrdeptnameNotIn(List<String> values) {
            addCriterion("strDeptName not in", values, "strdeptname");
            return (Criteria) this;
        }

        public Criteria andStrdeptnameBetween(String value1, String value2) {
            addCriterion("strDeptName between", value1, value2, "strdeptname");
            return (Criteria) this;
        }

        public Criteria andStrdeptnameNotBetween(String value1, String value2) {
            addCriterion("strDeptName not between", value1, value2, "strdeptname");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelIsNull() {
            addCriterion("nCurrentLevel is null");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelIsNotNull() {
            addCriterion("nCurrentLevel is not null");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelEqualTo(Integer value) {
            addCriterion("nCurrentLevel =", value, "ncurrentlevel");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelNotEqualTo(Integer value) {
            addCriterion("nCurrentLevel <>", value, "ncurrentlevel");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelGreaterThan(Integer value) {
            addCriterion("nCurrentLevel >", value, "ncurrentlevel");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("nCurrentLevel >=", value, "ncurrentlevel");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelLessThan(Integer value) {
            addCriterion("nCurrentLevel <", value, "ncurrentlevel");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelLessThanOrEqualTo(Integer value) {
            addCriterion("nCurrentLevel <=", value, "ncurrentlevel");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelIn(List<Integer> values) {
            addCriterion("nCurrentLevel in", values, "ncurrentlevel");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelNotIn(List<Integer> values) {
            addCriterion("nCurrentLevel not in", values, "ncurrentlevel");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelBetween(Integer value1, Integer value2) {
            addCriterion("nCurrentLevel between", value1, value2, "ncurrentlevel");
            return (Criteria) this;
        }

        public Criteria andNcurrentlevelNotBetween(Integer value1, Integer value2) {
            addCriterion("nCurrentLevel not between", value1, value2, "ncurrentlevel");
            return (Criteria) this;
        }

        public Criteria andNworkstateIsNull() {
            addCriterion("nWorkState is null");
            return (Criteria) this;
        }

        public Criteria andNworkstateIsNotNull() {
            addCriterion("nWorkState is not null");
            return (Criteria) this;
        }

        public Criteria andNworkstateEqualTo(Integer value) {
            addCriterion("nWorkState =", value, "nworkstate");
            return (Criteria) this;
        }

        public Criteria andNworkstateNotEqualTo(Integer value) {
            addCriterion("nWorkState <>", value, "nworkstate");
            return (Criteria) this;
        }

        public Criteria andNworkstateGreaterThan(Integer value) {
            addCriterion("nWorkState >", value, "nworkstate");
            return (Criteria) this;
        }

        public Criteria andNworkstateGreaterThanOrEqualTo(Integer value) {
            addCriterion("nWorkState >=", value, "nworkstate");
            return (Criteria) this;
        }

        public Criteria andNworkstateLessThan(Integer value) {
            addCriterion("nWorkState <", value, "nworkstate");
            return (Criteria) this;
        }

        public Criteria andNworkstateLessThanOrEqualTo(Integer value) {
            addCriterion("nWorkState <=", value, "nworkstate");
            return (Criteria) this;
        }

        public Criteria andNworkstateIn(List<Integer> values) {
            addCriterion("nWorkState in", values, "nworkstate");
            return (Criteria) this;
        }

        public Criteria andNworkstateNotIn(List<Integer> values) {
            addCriterion("nWorkState not in", values, "nworkstate");
            return (Criteria) this;
        }

        public Criteria andNworkstateBetween(Integer value1, Integer value2) {
            addCriterion("nWorkState between", value1, value2, "nworkstate");
            return (Criteria) this;
        }

        public Criteria andNworkstateNotBetween(Integer value1, Integer value2) {
            addCriterion("nWorkState not between", value1, value2, "nworkstate");
            return (Criteria) this;
        }

        public Criteria andNisshutdownIsNull() {
            addCriterion("nIsShutdown is null");
            return (Criteria) this;
        }

        public Criteria andNisshutdownIsNotNull() {
            addCriterion("nIsShutdown is not null");
            return (Criteria) this;
        }

        public Criteria andNisshutdownEqualTo(Integer value) {
            addCriterion("nIsShutdown =", value, "nisshutdown");
            return (Criteria) this;
        }

        public Criteria andNisshutdownNotEqualTo(Integer value) {
            addCriterion("nIsShutdown <>", value, "nisshutdown");
            return (Criteria) this;
        }

        public Criteria andNisshutdownGreaterThan(Integer value) {
            addCriterion("nIsShutdown >", value, "nisshutdown");
            return (Criteria) this;
        }

        public Criteria andNisshutdownGreaterThanOrEqualTo(Integer value) {
            addCriterion("nIsShutdown >=", value, "nisshutdown");
            return (Criteria) this;
        }

        public Criteria andNisshutdownLessThan(Integer value) {
            addCriterion("nIsShutdown <", value, "nisshutdown");
            return (Criteria) this;
        }

        public Criteria andNisshutdownLessThanOrEqualTo(Integer value) {
            addCriterion("nIsShutdown <=", value, "nisshutdown");
            return (Criteria) this;
        }

        public Criteria andNisshutdownIn(List<Integer> values) {
            addCriterion("nIsShutdown in", values, "nisshutdown");
            return (Criteria) this;
        }

        public Criteria andNisshutdownNotIn(List<Integer> values) {
            addCriterion("nIsShutdown not in", values, "nisshutdown");
            return (Criteria) this;
        }

        public Criteria andNisshutdownBetween(Integer value1, Integer value2) {
            addCriterion("nIsShutdown between", value1, value2, "nisshutdown");
            return (Criteria) this;
        }

        public Criteria andNisshutdownNotBetween(Integer value1, Integer value2) {
            addCriterion("nIsShutdown not between", value1, value2, "nisshutdown");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode0IsNull() {
            addCriterion("strDeptCode0 is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode0IsNotNull() {
            addCriterion("strDeptCode0 is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode0EqualTo(String value) {
            addCriterion("strDeptCode0 =", value, "strdeptcode0");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode0NotEqualTo(String value) {
            addCriterion("strDeptCode0 <>", value, "strdeptcode0");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode0GreaterThan(String value) {
            addCriterion("strDeptCode0 >", value, "strdeptcode0");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode0GreaterThanOrEqualTo(String value) {
            addCriterion("strDeptCode0 >=", value, "strdeptcode0");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode0LessThan(String value) {
            addCriterion("strDeptCode0 <", value, "strdeptcode0");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode0LessThanOrEqualTo(String value) {
            addCriterion("strDeptCode0 <=", value, "strdeptcode0");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode0Like(String value) {
            addCriterion("strDeptCode0 like", value, "strdeptcode0");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode0NotLike(String value) {
            addCriterion("strDeptCode0 not like", value, "strdeptcode0");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode0In(List<String> values) {
            addCriterion("strDeptCode0 in", values, "strdeptcode0");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode0NotIn(List<String> values) {
            addCriterion("strDeptCode0 not in", values, "strdeptcode0");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode0Between(String value1, String value2) {
            addCriterion("strDeptCode0 between", value1, value2, "strdeptcode0");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode0NotBetween(String value1, String value2) {
            addCriterion("strDeptCode0 not between", value1, value2, "strdeptcode0");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode1IsNull() {
            addCriterion("strDeptCode1 is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode1IsNotNull() {
            addCriterion("strDeptCode1 is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode1EqualTo(String value) {
            addCriterion("strDeptCode1 =", value, "strdeptcode1");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode1NotEqualTo(String value) {
            addCriterion("strDeptCode1 <>", value, "strdeptcode1");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode1GreaterThan(String value) {
            addCriterion("strDeptCode1 >", value, "strdeptcode1");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode1GreaterThanOrEqualTo(String value) {
            addCriterion("strDeptCode1 >=", value, "strdeptcode1");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode1LessThan(String value) {
            addCriterion("strDeptCode1 <", value, "strdeptcode1");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode1LessThanOrEqualTo(String value) {
            addCriterion("strDeptCode1 <=", value, "strdeptcode1");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode1Like(String value) {
            addCriterion("strDeptCode1 like", value, "strdeptcode1");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode1NotLike(String value) {
            addCriterion("strDeptCode1 not like", value, "strdeptcode1");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode1In(List<String> values) {
            addCriterion("strDeptCode1 in", values, "strdeptcode1");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode1NotIn(List<String> values) {
            addCriterion("strDeptCode1 not in", values, "strdeptcode1");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode1Between(String value1, String value2) {
            addCriterion("strDeptCode1 between", value1, value2, "strdeptcode1");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode1NotBetween(String value1, String value2) {
            addCriterion("strDeptCode1 not between", value1, value2, "strdeptcode1");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode2IsNull() {
            addCriterion("strDeptCode2 is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode2IsNotNull() {
            addCriterion("strDeptCode2 is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode2EqualTo(String value) {
            addCriterion("strDeptCode2 =", value, "strdeptcode2");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode2NotEqualTo(String value) {
            addCriterion("strDeptCode2 <>", value, "strdeptcode2");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode2GreaterThan(String value) {
            addCriterion("strDeptCode2 >", value, "strdeptcode2");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode2GreaterThanOrEqualTo(String value) {
            addCriterion("strDeptCode2 >=", value, "strdeptcode2");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode2LessThan(String value) {
            addCriterion("strDeptCode2 <", value, "strdeptcode2");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode2LessThanOrEqualTo(String value) {
            addCriterion("strDeptCode2 <=", value, "strdeptcode2");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode2Like(String value) {
            addCriterion("strDeptCode2 like", value, "strdeptcode2");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode2NotLike(String value) {
            addCriterion("strDeptCode2 not like", value, "strdeptcode2");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode2In(List<String> values) {
            addCriterion("strDeptCode2 in", values, "strdeptcode2");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode2NotIn(List<String> values) {
            addCriterion("strDeptCode2 not in", values, "strdeptcode2");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode2Between(String value1, String value2) {
            addCriterion("strDeptCode2 between", value1, value2, "strdeptcode2");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode2NotBetween(String value1, String value2) {
            addCriterion("strDeptCode2 not between", value1, value2, "strdeptcode2");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode3IsNull() {
            addCriterion("strDeptCode3 is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode3IsNotNull() {
            addCriterion("strDeptCode3 is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode3EqualTo(String value) {
            addCriterion("strDeptCode3 =", value, "strdeptcode3");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode3NotEqualTo(String value) {
            addCriterion("strDeptCode3 <>", value, "strdeptcode3");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode3GreaterThan(String value) {
            addCriterion("strDeptCode3 >", value, "strdeptcode3");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode3GreaterThanOrEqualTo(String value) {
            addCriterion("strDeptCode3 >=", value, "strdeptcode3");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode3LessThan(String value) {
            addCriterion("strDeptCode3 <", value, "strdeptcode3");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode3LessThanOrEqualTo(String value) {
            addCriterion("strDeptCode3 <=", value, "strdeptcode3");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode3Like(String value) {
            addCriterion("strDeptCode3 like", value, "strdeptcode3");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode3NotLike(String value) {
            addCriterion("strDeptCode3 not like", value, "strdeptcode3");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode3In(List<String> values) {
            addCriterion("strDeptCode3 in", values, "strdeptcode3");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode3NotIn(List<String> values) {
            addCriterion("strDeptCode3 not in", values, "strdeptcode3");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode3Between(String value1, String value2) {
            addCriterion("strDeptCode3 between", value1, value2, "strdeptcode3");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode3NotBetween(String value1, String value2) {
            addCriterion("strDeptCode3 not between", value1, value2, "strdeptcode3");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode4IsNull() {
            addCriterion("strDeptCode4 is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode4IsNotNull() {
            addCriterion("strDeptCode4 is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode4EqualTo(String value) {
            addCriterion("strDeptCode4 =", value, "strdeptcode4");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode4NotEqualTo(String value) {
            addCriterion("strDeptCode4 <>", value, "strdeptcode4");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode4GreaterThan(String value) {
            addCriterion("strDeptCode4 >", value, "strdeptcode4");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode4GreaterThanOrEqualTo(String value) {
            addCriterion("strDeptCode4 >=", value, "strdeptcode4");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode4LessThan(String value) {
            addCriterion("strDeptCode4 <", value, "strdeptcode4");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode4LessThanOrEqualTo(String value) {
            addCriterion("strDeptCode4 <=", value, "strdeptcode4");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode4Like(String value) {
            addCriterion("strDeptCode4 like", value, "strdeptcode4");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode4NotLike(String value) {
            addCriterion("strDeptCode4 not like", value, "strdeptcode4");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode4In(List<String> values) {
            addCriterion("strDeptCode4 in", values, "strdeptcode4");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode4NotIn(List<String> values) {
            addCriterion("strDeptCode4 not in", values, "strdeptcode4");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode4Between(String value1, String value2) {
            addCriterion("strDeptCode4 between", value1, value2, "strdeptcode4");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode4NotBetween(String value1, String value2) {
            addCriterion("strDeptCode4 not between", value1, value2, "strdeptcode4");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode5IsNull() {
            addCriterion("strDeptCode5 is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode5IsNotNull() {
            addCriterion("strDeptCode5 is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode5EqualTo(String value) {
            addCriterion("strDeptCode5 =", value, "strdeptcode5");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode5NotEqualTo(String value) {
            addCriterion("strDeptCode5 <>", value, "strdeptcode5");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode5GreaterThan(String value) {
            addCriterion("strDeptCode5 >", value, "strdeptcode5");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode5GreaterThanOrEqualTo(String value) {
            addCriterion("strDeptCode5 >=", value, "strdeptcode5");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode5LessThan(String value) {
            addCriterion("strDeptCode5 <", value, "strdeptcode5");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode5LessThanOrEqualTo(String value) {
            addCriterion("strDeptCode5 <=", value, "strdeptcode5");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode5Like(String value) {
            addCriterion("strDeptCode5 like", value, "strdeptcode5");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode5NotLike(String value) {
            addCriterion("strDeptCode5 not like", value, "strdeptcode5");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode5In(List<String> values) {
            addCriterion("strDeptCode5 in", values, "strdeptcode5");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode5NotIn(List<String> values) {
            addCriterion("strDeptCode5 not in", values, "strdeptcode5");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode5Between(String value1, String value2) {
            addCriterion("strDeptCode5 between", value1, value2, "strdeptcode5");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode5NotBetween(String value1, String value2) {
            addCriterion("strDeptCode5 not between", value1, value2, "strdeptcode5");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode6IsNull() {
            addCriterion("strDeptCode6 is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode6IsNotNull() {
            addCriterion("strDeptCode6 is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode6EqualTo(String value) {
            addCriterion("strDeptCode6 =", value, "strdeptcode6");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode6NotEqualTo(String value) {
            addCriterion("strDeptCode6 <>", value, "strdeptcode6");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode6GreaterThan(String value) {
            addCriterion("strDeptCode6 >", value, "strdeptcode6");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode6GreaterThanOrEqualTo(String value) {
            addCriterion("strDeptCode6 >=", value, "strdeptcode6");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode6LessThan(String value) {
            addCriterion("strDeptCode6 <", value, "strdeptcode6");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode6LessThanOrEqualTo(String value) {
            addCriterion("strDeptCode6 <=", value, "strdeptcode6");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode6Like(String value) {
            addCriterion("strDeptCode6 like", value, "strdeptcode6");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode6NotLike(String value) {
            addCriterion("strDeptCode6 not like", value, "strdeptcode6");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode6In(List<String> values) {
            addCriterion("strDeptCode6 in", values, "strdeptcode6");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode6NotIn(List<String> values) {
            addCriterion("strDeptCode6 not in", values, "strdeptcode6");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode6Between(String value1, String value2) {
            addCriterion("strDeptCode6 between", value1, value2, "strdeptcode6");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode6NotBetween(String value1, String value2) {
            addCriterion("strDeptCode6 not between", value1, value2, "strdeptcode6");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode7IsNull() {
            addCriterion("strDeptCode7 is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode7IsNotNull() {
            addCriterion("strDeptCode7 is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode7EqualTo(String value) {
            addCriterion("strDeptCode7 =", value, "strdeptcode7");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode7NotEqualTo(String value) {
            addCriterion("strDeptCode7 <>", value, "strdeptcode7");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode7GreaterThan(String value) {
            addCriterion("strDeptCode7 >", value, "strdeptcode7");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode7GreaterThanOrEqualTo(String value) {
            addCriterion("strDeptCode7 >=", value, "strdeptcode7");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode7LessThan(String value) {
            addCriterion("strDeptCode7 <", value, "strdeptcode7");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode7LessThanOrEqualTo(String value) {
            addCriterion("strDeptCode7 <=", value, "strdeptcode7");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode7Like(String value) {
            addCriterion("strDeptCode7 like", value, "strdeptcode7");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode7NotLike(String value) {
            addCriterion("strDeptCode7 not like", value, "strdeptcode7");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode7In(List<String> values) {
            addCriterion("strDeptCode7 in", values, "strdeptcode7");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode7NotIn(List<String> values) {
            addCriterion("strDeptCode7 not in", values, "strdeptcode7");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode7Between(String value1, String value2) {
            addCriterion("strDeptCode7 between", value1, value2, "strdeptcode7");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode7NotBetween(String value1, String value2) {
            addCriterion("strDeptCode7 not between", value1, value2, "strdeptcode7");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode8IsNull() {
            addCriterion("strDeptCode8 is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode8IsNotNull() {
            addCriterion("strDeptCode8 is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode8EqualTo(String value) {
            addCriterion("strDeptCode8 =", value, "strdeptcode8");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode8NotEqualTo(String value) {
            addCriterion("strDeptCode8 <>", value, "strdeptcode8");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode8GreaterThan(String value) {
            addCriterion("strDeptCode8 >", value, "strdeptcode8");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode8GreaterThanOrEqualTo(String value) {
            addCriterion("strDeptCode8 >=", value, "strdeptcode8");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode8LessThan(String value) {
            addCriterion("strDeptCode8 <", value, "strdeptcode8");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode8LessThanOrEqualTo(String value) {
            addCriterion("strDeptCode8 <=", value, "strdeptcode8");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode8Like(String value) {
            addCriterion("strDeptCode8 like", value, "strdeptcode8");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode8NotLike(String value) {
            addCriterion("strDeptCode8 not like", value, "strdeptcode8");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode8In(List<String> values) {
            addCriterion("strDeptCode8 in", values, "strdeptcode8");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode8NotIn(List<String> values) {
            addCriterion("strDeptCode8 not in", values, "strdeptcode8");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode8Between(String value1, String value2) {
            addCriterion("strDeptCode8 between", value1, value2, "strdeptcode8");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode8NotBetween(String value1, String value2) {
            addCriterion("strDeptCode8 not between", value1, value2, "strdeptcode8");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode9IsNull() {
            addCriterion("strDeptCode9 is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode9IsNotNull() {
            addCriterion("strDeptCode9 is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode9EqualTo(String value) {
            addCriterion("strDeptCode9 =", value, "strdeptcode9");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode9NotEqualTo(String value) {
            addCriterion("strDeptCode9 <>", value, "strdeptcode9");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode9GreaterThan(String value) {
            addCriterion("strDeptCode9 >", value, "strdeptcode9");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode9GreaterThanOrEqualTo(String value) {
            addCriterion("strDeptCode9 >=", value, "strdeptcode9");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode9LessThan(String value) {
            addCriterion("strDeptCode9 <", value, "strdeptcode9");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode9LessThanOrEqualTo(String value) {
            addCriterion("strDeptCode9 <=", value, "strdeptcode9");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode9Like(String value) {
            addCriterion("strDeptCode9 like", value, "strdeptcode9");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode9NotLike(String value) {
            addCriterion("strDeptCode9 not like", value, "strdeptcode9");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode9In(List<String> values) {
            addCriterion("strDeptCode9 in", values, "strdeptcode9");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode9NotIn(List<String> values) {
            addCriterion("strDeptCode9 not in", values, "strdeptcode9");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode9Between(String value1, String value2) {
            addCriterion("strDeptCode9 between", value1, value2, "strdeptcode9");
            return (Criteria) this;
        }

        public Criteria andStrdeptcode9NotBetween(String value1, String value2) {
            addCriterion("strDeptCode9 not between", value1, value2, "strdeptcode9");
            return (Criteria) this;
        }

        public Criteria andDtcreatetimeIsNull() {
            addCriterion("dtCreateTime is null");
            return (Criteria) this;
        }

        public Criteria andDtcreatetimeIsNotNull() {
            addCriterion("dtCreateTime is not null");
            return (Criteria) this;
        }

        public Criteria andDtcreatetimeEqualTo(Date value) {
            addCriterion("dtCreateTime =", value, "dtcreatetime");
            return (Criteria) this;
        }

        public Criteria andDtcreatetimeNotEqualTo(Date value) {
            addCriterion("dtCreateTime <>", value, "dtcreatetime");
            return (Criteria) this;
        }

        public Criteria andDtcreatetimeGreaterThan(Date value) {
            addCriterion("dtCreateTime >", value, "dtcreatetime");
            return (Criteria) this;
        }

        public Criteria andDtcreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("dtCreateTime >=", value, "dtcreatetime");
            return (Criteria) this;
        }

        public Criteria andDtcreatetimeLessThan(Date value) {
            addCriterion("dtCreateTime <", value, "dtcreatetime");
            return (Criteria) this;
        }

        public Criteria andDtcreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("dtCreateTime <=", value, "dtcreatetime");
            return (Criteria) this;
        }

        public Criteria andDtcreatetimeIn(List<Date> values) {
            addCriterion("dtCreateTime in", values, "dtcreatetime");
            return (Criteria) this;
        }

        public Criteria andDtcreatetimeNotIn(List<Date> values) {
            addCriterion("dtCreateTime not in", values, "dtcreatetime");
            return (Criteria) this;
        }

        public Criteria andDtcreatetimeBetween(Date value1, Date value2) {
            addCriterion("dtCreateTime between", value1, value2, "dtcreatetime");
            return (Criteria) this;
        }

        public Criteria andDtcreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("dtCreateTime not between", value1, value2, "dtcreatetime");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincecodeIsNull() {
            addCriterion("strDeptProvinceCode is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincecodeIsNotNull() {
            addCriterion("strDeptProvinceCode is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincecodeEqualTo(String value) {
            addCriterion("strDeptProvinceCode =", value, "strdeptprovincecode");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincecodeNotEqualTo(String value) {
            addCriterion("strDeptProvinceCode <>", value, "strdeptprovincecode");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincecodeGreaterThan(String value) {
            addCriterion("strDeptProvinceCode >", value, "strdeptprovincecode");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincecodeGreaterThanOrEqualTo(String value) {
            addCriterion("strDeptProvinceCode >=", value, "strdeptprovincecode");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincecodeLessThan(String value) {
            addCriterion("strDeptProvinceCode <", value, "strdeptprovincecode");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincecodeLessThanOrEqualTo(String value) {
            addCriterion("strDeptProvinceCode <=", value, "strdeptprovincecode");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincecodeLike(String value) {
            addCriterion("strDeptProvinceCode like", value, "strdeptprovincecode");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincecodeNotLike(String value) {
            addCriterion("strDeptProvinceCode not like", value, "strdeptprovincecode");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincecodeIn(List<String> values) {
            addCriterion("strDeptProvinceCode in", values, "strdeptprovincecode");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincecodeNotIn(List<String> values) {
            addCriterion("strDeptProvinceCode not in", values, "strdeptprovincecode");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincecodeBetween(String value1, String value2) {
            addCriterion("strDeptProvinceCode between", value1, value2, "strdeptprovincecode");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincecodeNotBetween(String value1, String value2) {
            addCriterion("strDeptProvinceCode not between", value1, value2, "strdeptprovincecode");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincenameIsNull() {
            addCriterion("strDeptProvinceName is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincenameIsNotNull() {
            addCriterion("strDeptProvinceName is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincenameEqualTo(String value) {
            addCriterion("strDeptProvinceName =", value, "strdeptprovincename");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincenameNotEqualTo(String value) {
            addCriterion("strDeptProvinceName <>", value, "strdeptprovincename");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincenameGreaterThan(String value) {
            addCriterion("strDeptProvinceName >", value, "strdeptprovincename");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincenameGreaterThanOrEqualTo(String value) {
            addCriterion("strDeptProvinceName >=", value, "strdeptprovincename");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincenameLessThan(String value) {
            addCriterion("strDeptProvinceName <", value, "strdeptprovincename");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincenameLessThanOrEqualTo(String value) {
            addCriterion("strDeptProvinceName <=", value, "strdeptprovincename");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincenameLike(String value) {
            addCriterion("strDeptProvinceName like", value, "strdeptprovincename");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincenameNotLike(String value) {
            addCriterion("strDeptProvinceName not like", value, "strdeptprovincename");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincenameIn(List<String> values) {
            addCriterion("strDeptProvinceName in", values, "strdeptprovincename");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincenameNotIn(List<String> values) {
            addCriterion("strDeptProvinceName not in", values, "strdeptprovincename");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincenameBetween(String value1, String value2) {
            addCriterion("strDeptProvinceName between", value1, value2, "strdeptprovincename");
            return (Criteria) this;
        }

        public Criteria andStrdeptprovincenameNotBetween(String value1, String value2) {
            addCriterion("strDeptProvinceName not between", value1, value2, "strdeptprovincename");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitycodeIsNull() {
            addCriterion("strDeptCityCode is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitycodeIsNotNull() {
            addCriterion("strDeptCityCode is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitycodeEqualTo(String value) {
            addCriterion("strDeptCityCode =", value, "strdeptcitycode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitycodeNotEqualTo(String value) {
            addCriterion("strDeptCityCode <>", value, "strdeptcitycode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitycodeGreaterThan(String value) {
            addCriterion("strDeptCityCode >", value, "strdeptcitycode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitycodeGreaterThanOrEqualTo(String value) {
            addCriterion("strDeptCityCode >=", value, "strdeptcitycode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitycodeLessThan(String value) {
            addCriterion("strDeptCityCode <", value, "strdeptcitycode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitycodeLessThanOrEqualTo(String value) {
            addCriterion("strDeptCityCode <=", value, "strdeptcitycode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitycodeLike(String value) {
            addCriterion("strDeptCityCode like", value, "strdeptcitycode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitycodeNotLike(String value) {
            addCriterion("strDeptCityCode not like", value, "strdeptcitycode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitycodeIn(List<String> values) {
            addCriterion("strDeptCityCode in", values, "strdeptcitycode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitycodeNotIn(List<String> values) {
            addCriterion("strDeptCityCode not in", values, "strdeptcitycode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitycodeBetween(String value1, String value2) {
            addCriterion("strDeptCityCode between", value1, value2, "strdeptcitycode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitycodeNotBetween(String value1, String value2) {
            addCriterion("strDeptCityCode not between", value1, value2, "strdeptcitycode");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitynameIsNull() {
            addCriterion("strDeptCityName is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitynameIsNotNull() {
            addCriterion("strDeptCityName is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitynameEqualTo(String value) {
            addCriterion("strDeptCityName =", value, "strdeptcityname");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitynameNotEqualTo(String value) {
            addCriterion("strDeptCityName <>", value, "strdeptcityname");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitynameGreaterThan(String value) {
            addCriterion("strDeptCityName >", value, "strdeptcityname");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitynameGreaterThanOrEqualTo(String value) {
            addCriterion("strDeptCityName >=", value, "strdeptcityname");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitynameLessThan(String value) {
            addCriterion("strDeptCityName <", value, "strdeptcityname");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitynameLessThanOrEqualTo(String value) {
            addCriterion("strDeptCityName <=", value, "strdeptcityname");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitynameLike(String value) {
            addCriterion("strDeptCityName like", value, "strdeptcityname");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitynameNotLike(String value) {
            addCriterion("strDeptCityName not like", value, "strdeptcityname");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitynameIn(List<String> values) {
            addCriterion("strDeptCityName in", values, "strdeptcityname");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitynameNotIn(List<String> values) {
            addCriterion("strDeptCityName not in", values, "strdeptcityname");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitynameBetween(String value1, String value2) {
            addCriterion("strDeptCityName between", value1, value2, "strdeptcityname");
            return (Criteria) this;
        }

        public Criteria andStrdeptcitynameNotBetween(String value1, String value2) {
            addCriterion("strDeptCityName not between", value1, value2, "strdeptcityname");
            return (Criteria) this;
        }

        public Criteria andStrdeptaddressIsNull() {
            addCriterion("strDeptAddress is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptaddressIsNotNull() {
            addCriterion("strDeptAddress is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptaddressEqualTo(String value) {
            addCriterion("strDeptAddress =", value, "strdeptaddress");
            return (Criteria) this;
        }

        public Criteria andStrdeptaddressNotEqualTo(String value) {
            addCriterion("strDeptAddress <>", value, "strdeptaddress");
            return (Criteria) this;
        }

        public Criteria andStrdeptaddressGreaterThan(String value) {
            addCriterion("strDeptAddress >", value, "strdeptaddress");
            return (Criteria) this;
        }

        public Criteria andStrdeptaddressGreaterThanOrEqualTo(String value) {
            addCriterion("strDeptAddress >=", value, "strdeptaddress");
            return (Criteria) this;
        }

        public Criteria andStrdeptaddressLessThan(String value) {
            addCriterion("strDeptAddress <", value, "strdeptaddress");
            return (Criteria) this;
        }

        public Criteria andStrdeptaddressLessThanOrEqualTo(String value) {
            addCriterion("strDeptAddress <=", value, "strdeptaddress");
            return (Criteria) this;
        }

        public Criteria andStrdeptaddressLike(String value) {
            addCriterion("strDeptAddress like", value, "strdeptaddress");
            return (Criteria) this;
        }

        public Criteria andStrdeptaddressNotLike(String value) {
            addCriterion("strDeptAddress not like", value, "strdeptaddress");
            return (Criteria) this;
        }

        public Criteria andStrdeptaddressIn(List<String> values) {
            addCriterion("strDeptAddress in", values, "strdeptaddress");
            return (Criteria) this;
        }

        public Criteria andStrdeptaddressNotIn(List<String> values) {
            addCriterion("strDeptAddress not in", values, "strdeptaddress");
            return (Criteria) this;
        }

        public Criteria andStrdeptaddressBetween(String value1, String value2) {
            addCriterion("strDeptAddress between", value1, value2, "strdeptaddress");
            return (Criteria) this;
        }

        public Criteria andStrdeptaddressNotBetween(String value1, String value2) {
            addCriterion("strDeptAddress not between", value1, value2, "strdeptaddress");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeIsNull() {
            addCriterion("dtUpdateTime is null");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeIsNotNull() {
            addCriterion("dtUpdateTime is not null");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeEqualTo(Date value) {
            addCriterion("dtUpdateTime =", value, "dtupdatetime");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeNotEqualTo(Date value) {
            addCriterion("dtUpdateTime <>", value, "dtupdatetime");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeGreaterThan(Date value) {
            addCriterion("dtUpdateTime >", value, "dtupdatetime");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("dtUpdateTime >=", value, "dtupdatetime");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeLessThan(Date value) {
            addCriterion("dtUpdateTime <", value, "dtupdatetime");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("dtUpdateTime <=", value, "dtupdatetime");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeIn(List<Date> values) {
            addCriterion("dtUpdateTime in", values, "dtupdatetime");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeNotIn(List<Date> values) {
            addCriterion("dtUpdateTime not in", values, "dtupdatetime");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeBetween(Date value1, Date value2) {
            addCriterion("dtUpdateTime between", value1, value2, "dtupdatetime");
            return (Criteria) this;
        }

        public Criteria andDtupdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("dtUpdateTime not between", value1, value2, "dtupdatetime");
            return (Criteria) this;
        }

        public Criteria andStrworkdayIsNull() {
            addCriterion("strWorkDay is null");
            return (Criteria) this;
        }

        public Criteria andStrworkdayIsNotNull() {
            addCriterion("strWorkDay is not null");
            return (Criteria) this;
        }

        public Criteria andStrworkdayEqualTo(String value) {
            addCriterion("strWorkDay =", value, "strworkday");
            return (Criteria) this;
        }

        public Criteria andStrworkdayNotEqualTo(String value) {
            addCriterion("strWorkDay <>", value, "strworkday");
            return (Criteria) this;
        }

        public Criteria andStrworkdayGreaterThan(String value) {
            addCriterion("strWorkDay >", value, "strworkday");
            return (Criteria) this;
        }

        public Criteria andStrworkdayGreaterThanOrEqualTo(String value) {
            addCriterion("strWorkDay >=", value, "strworkday");
            return (Criteria) this;
        }

        public Criteria andStrworkdayLessThan(String value) {
            addCriterion("strWorkDay <", value, "strworkday");
            return (Criteria) this;
        }

        public Criteria andStrworkdayLessThanOrEqualTo(String value) {
            addCriterion("strWorkDay <=", value, "strworkday");
            return (Criteria) this;
        }

        public Criteria andStrworkdayLike(String value) {
            addCriterion("strWorkDay like", value, "strworkday");
            return (Criteria) this;
        }

        public Criteria andStrworkdayNotLike(String value) {
            addCriterion("strWorkDay not like", value, "strworkday");
            return (Criteria) this;
        }

        public Criteria andStrworkdayIn(List<String> values) {
            addCriterion("strWorkDay in", values, "strworkday");
            return (Criteria) this;
        }

        public Criteria andStrworkdayNotIn(List<String> values) {
            addCriterion("strWorkDay not in", values, "strworkday");
            return (Criteria) this;
        }

        public Criteria andStrworkdayBetween(String value1, String value2) {
            addCriterion("strWorkDay between", value1, value2, "strworkday");
            return (Criteria) this;
        }

        public Criteria andStrworkdayNotBetween(String value1, String value2) {
            addCriterion("strWorkDay not between", value1, value2, "strworkday");
            return (Criteria) this;
        }

        public Criteria andLoldsalesidIsNull() {
            addCriterion("lOldSalesId is null");
            return (Criteria) this;
        }

        public Criteria andLoldsalesidIsNotNull() {
            addCriterion("lOldSalesId is not null");
            return (Criteria) this;
        }

        public Criteria andLoldsalesidEqualTo(Long value) {
            addCriterion("lOldSalesId =", value, "loldsalesid");
            return (Criteria) this;
        }

        public Criteria andLoldsalesidNotEqualTo(Long value) {
            addCriterion("lOldSalesId <>", value, "loldsalesid");
            return (Criteria) this;
        }

        public Criteria andLoldsalesidGreaterThan(Long value) {
            addCriterion("lOldSalesId >", value, "loldsalesid");
            return (Criteria) this;
        }

        public Criteria andLoldsalesidGreaterThanOrEqualTo(Long value) {
            addCriterion("lOldSalesId >=", value, "loldsalesid");
            return (Criteria) this;
        }

        public Criteria andLoldsalesidLessThan(Long value) {
            addCriterion("lOldSalesId <", value, "loldsalesid");
            return (Criteria) this;
        }

        public Criteria andLoldsalesidLessThanOrEqualTo(Long value) {
            addCriterion("lOldSalesId <=", value, "loldsalesid");
            return (Criteria) this;
        }

        public Criteria andLoldsalesidIn(List<Long> values) {
            addCriterion("lOldSalesId in", values, "loldsalesid");
            return (Criteria) this;
        }

        public Criteria andLoldsalesidNotIn(List<Long> values) {
            addCriterion("lOldSalesId not in", values, "loldsalesid");
            return (Criteria) this;
        }

        public Criteria andLoldsalesidBetween(Long value1, Long value2) {
            addCriterion("lOldSalesId between", value1, value2, "loldsalesid");
            return (Criteria) this;
        }

        public Criteria andLoldsalesidNotBetween(Long value1, Long value2) {
            addCriterion("lOldSalesId not between", value1, value2, "loldsalesid");
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