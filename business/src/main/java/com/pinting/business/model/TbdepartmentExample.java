package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbdepartmentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbdepartmentExample() {
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

        public Criteria andLidIsNull() {
            addCriterion("lId is null");
            return (Criteria) this;
        }

        public Criteria andLidIsNotNull() {
            addCriterion("lId is not null");
            return (Criteria) this;
        }

        public Criteria andLidEqualTo(Long value) {
            addCriterion("lId =", value, "lid");
            return (Criteria) this;
        }

        public Criteria andLidNotEqualTo(Long value) {
            addCriterion("lId <>", value, "lid");
            return (Criteria) this;
        }

        public Criteria andLidGreaterThan(Long value) {
            addCriterion("lId >", value, "lid");
            return (Criteria) this;
        }

        public Criteria andLidGreaterThanOrEqualTo(Long value) {
            addCriterion("lId >=", value, "lid");
            return (Criteria) this;
        }

        public Criteria andLidLessThan(Long value) {
            addCriterion("lId <", value, "lid");
            return (Criteria) this;
        }

        public Criteria andLidLessThanOrEqualTo(Long value) {
            addCriterion("lId <=", value, "lid");
            return (Criteria) this;
        }

        public Criteria andLidIn(List<Long> values) {
            addCriterion("lId in", values, "lid");
            return (Criteria) this;
        }

        public Criteria andLidNotIn(List<Long> values) {
            addCriterion("lId not in", values, "lid");
            return (Criteria) this;
        }

        public Criteria andLidBetween(Long value1, Long value2) {
            addCriterion("lId between", value1, value2, "lid");
            return (Criteria) this;
        }

        public Criteria andLidNotBetween(Long value1, Long value2) {
            addCriterion("lId not between", value1, value2, "lid");
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

        public Criteria andBisleafIsNull() {
            addCriterion("bIsLeaf is null");
            return (Criteria) this;
        }

        public Criteria andBisleafIsNotNull() {
            addCriterion("bIsLeaf is not null");
            return (Criteria) this;
        }

        public Criteria andBisleafEqualTo(Integer value) {
            addCriterion("bIsLeaf =", value, "bisleaf");
            return (Criteria) this;
        }

        public Criteria andBisleafNotEqualTo(Integer value) {
            addCriterion("bIsLeaf <>", value, "bisleaf");
            return (Criteria) this;
        }

        public Criteria andBisleafGreaterThan(Integer value) {
            addCriterion("bIsLeaf >", value, "bisleaf");
            return (Criteria) this;
        }

        public Criteria andBisleafGreaterThanOrEqualTo(Integer value) {
            addCriterion("bIsLeaf >=", value, "bisleaf");
            return (Criteria) this;
        }

        public Criteria andBisleafLessThan(Integer value) {
            addCriterion("bIsLeaf <", value, "bisleaf");
            return (Criteria) this;
        }

        public Criteria andBisleafLessThanOrEqualTo(Integer value) {
            addCriterion("bIsLeaf <=", value, "bisleaf");
            return (Criteria) this;
        }

        public Criteria andBisleafIn(List<Integer> values) {
            addCriterion("bIsLeaf in", values, "bisleaf");
            return (Criteria) this;
        }

        public Criteria andBisleafNotIn(List<Integer> values) {
            addCriterion("bIsLeaf not in", values, "bisleaf");
            return (Criteria) this;
        }

        public Criteria andBisleafBetween(Integer value1, Integer value2) {
            addCriterion("bIsLeaf between", value1, value2, "bisleaf");
            return (Criteria) this;
        }

        public Criteria andBisleafNotBetween(Integer value1, Integer value2) {
            addCriterion("bIsLeaf not between", value1, value2, "bisleaf");
            return (Criteria) this;
        }

        public Criteria andBissalesdeptIsNull() {
            addCriterion("bIsSalesDept is null");
            return (Criteria) this;
        }

        public Criteria andBissalesdeptIsNotNull() {
            addCriterion("bIsSalesDept is not null");
            return (Criteria) this;
        }

        public Criteria andBissalesdeptEqualTo(Integer value) {
            addCriterion("bIsSalesDept =", value, "bissalesdept");
            return (Criteria) this;
        }

        public Criteria andBissalesdeptNotEqualTo(Integer value) {
            addCriterion("bIsSalesDept <>", value, "bissalesdept");
            return (Criteria) this;
        }

        public Criteria andBissalesdeptGreaterThan(Integer value) {
            addCriterion("bIsSalesDept >", value, "bissalesdept");
            return (Criteria) this;
        }

        public Criteria andBissalesdeptGreaterThanOrEqualTo(Integer value) {
            addCriterion("bIsSalesDept >=", value, "bissalesdept");
            return (Criteria) this;
        }

        public Criteria andBissalesdeptLessThan(Integer value) {
            addCriterion("bIsSalesDept <", value, "bissalesdept");
            return (Criteria) this;
        }

        public Criteria andBissalesdeptLessThanOrEqualTo(Integer value) {
            addCriterion("bIsSalesDept <=", value, "bissalesdept");
            return (Criteria) this;
        }

        public Criteria andBissalesdeptIn(List<Integer> values) {
            addCriterion("bIsSalesDept in", values, "bissalesdept");
            return (Criteria) this;
        }

        public Criteria andBissalesdeptNotIn(List<Integer> values) {
            addCriterion("bIsSalesDept not in", values, "bissalesdept");
            return (Criteria) this;
        }

        public Criteria andBissalesdeptBetween(Integer value1, Integer value2) {
            addCriterion("bIsSalesDept between", value1, value2, "bissalesdept");
            return (Criteria) this;
        }

        public Criteria andBissalesdeptNotBetween(Integer value1, Integer value2) {
            addCriterion("bIsSalesDept not between", value1, value2, "bissalesdept");
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

        public Criteria andStrdeptname0IsNull() {
            addCriterion("strDeptName0 is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptname0IsNotNull() {
            addCriterion("strDeptName0 is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptname0EqualTo(String value) {
            addCriterion("strDeptName0 =", value, "strdeptname0");
            return (Criteria) this;
        }

        public Criteria andStrdeptname0NotEqualTo(String value) {
            addCriterion("strDeptName0 <>", value, "strdeptname0");
            return (Criteria) this;
        }

        public Criteria andStrdeptname0GreaterThan(String value) {
            addCriterion("strDeptName0 >", value, "strdeptname0");
            return (Criteria) this;
        }

        public Criteria andStrdeptname0GreaterThanOrEqualTo(String value) {
            addCriterion("strDeptName0 >=", value, "strdeptname0");
            return (Criteria) this;
        }

        public Criteria andStrdeptname0LessThan(String value) {
            addCriterion("strDeptName0 <", value, "strdeptname0");
            return (Criteria) this;
        }

        public Criteria andStrdeptname0LessThanOrEqualTo(String value) {
            addCriterion("strDeptName0 <=", value, "strdeptname0");
            return (Criteria) this;
        }

        public Criteria andStrdeptname0Like(String value) {
            addCriterion("strDeptName0 like", value, "strdeptname0");
            return (Criteria) this;
        }

        public Criteria andStrdeptname0NotLike(String value) {
            addCriterion("strDeptName0 not like", value, "strdeptname0");
            return (Criteria) this;
        }

        public Criteria andStrdeptname0In(List<String> values) {
            addCriterion("strDeptName0 in", values, "strdeptname0");
            return (Criteria) this;
        }

        public Criteria andStrdeptname0NotIn(List<String> values) {
            addCriterion("strDeptName0 not in", values, "strdeptname0");
            return (Criteria) this;
        }

        public Criteria andStrdeptname0Between(String value1, String value2) {
            addCriterion("strDeptName0 between", value1, value2, "strdeptname0");
            return (Criteria) this;
        }

        public Criteria andStrdeptname0NotBetween(String value1, String value2) {
            addCriterion("strDeptName0 not between", value1, value2, "strdeptname0");
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

        public Criteria andStrdeptname1IsNull() {
            addCriterion("strDeptName1 is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptname1IsNotNull() {
            addCriterion("strDeptName1 is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptname1EqualTo(String value) {
            addCriterion("strDeptName1 =", value, "strdeptname1");
            return (Criteria) this;
        }

        public Criteria andStrdeptname1NotEqualTo(String value) {
            addCriterion("strDeptName1 <>", value, "strdeptname1");
            return (Criteria) this;
        }

        public Criteria andStrdeptname1GreaterThan(String value) {
            addCriterion("strDeptName1 >", value, "strdeptname1");
            return (Criteria) this;
        }

        public Criteria andStrdeptname1GreaterThanOrEqualTo(String value) {
            addCriterion("strDeptName1 >=", value, "strdeptname1");
            return (Criteria) this;
        }

        public Criteria andStrdeptname1LessThan(String value) {
            addCriterion("strDeptName1 <", value, "strdeptname1");
            return (Criteria) this;
        }

        public Criteria andStrdeptname1LessThanOrEqualTo(String value) {
            addCriterion("strDeptName1 <=", value, "strdeptname1");
            return (Criteria) this;
        }

        public Criteria andStrdeptname1Like(String value) {
            addCriterion("strDeptName1 like", value, "strdeptname1");
            return (Criteria) this;
        }

        public Criteria andStrdeptname1NotLike(String value) {
            addCriterion("strDeptName1 not like", value, "strdeptname1");
            return (Criteria) this;
        }

        public Criteria andStrdeptname1In(List<String> values) {
            addCriterion("strDeptName1 in", values, "strdeptname1");
            return (Criteria) this;
        }

        public Criteria andStrdeptname1NotIn(List<String> values) {
            addCriterion("strDeptName1 not in", values, "strdeptname1");
            return (Criteria) this;
        }

        public Criteria andStrdeptname1Between(String value1, String value2) {
            addCriterion("strDeptName1 between", value1, value2, "strdeptname1");
            return (Criteria) this;
        }

        public Criteria andStrdeptname1NotBetween(String value1, String value2) {
            addCriterion("strDeptName1 not between", value1, value2, "strdeptname1");
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

        public Criteria andStrdeptname2IsNull() {
            addCriterion("strDeptName2 is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptname2IsNotNull() {
            addCriterion("strDeptName2 is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptname2EqualTo(String value) {
            addCriterion("strDeptName2 =", value, "strdeptname2");
            return (Criteria) this;
        }

        public Criteria andStrdeptname2NotEqualTo(String value) {
            addCriterion("strDeptName2 <>", value, "strdeptname2");
            return (Criteria) this;
        }

        public Criteria andStrdeptname2GreaterThan(String value) {
            addCriterion("strDeptName2 >", value, "strdeptname2");
            return (Criteria) this;
        }

        public Criteria andStrdeptname2GreaterThanOrEqualTo(String value) {
            addCriterion("strDeptName2 >=", value, "strdeptname2");
            return (Criteria) this;
        }

        public Criteria andStrdeptname2LessThan(String value) {
            addCriterion("strDeptName2 <", value, "strdeptname2");
            return (Criteria) this;
        }

        public Criteria andStrdeptname2LessThanOrEqualTo(String value) {
            addCriterion("strDeptName2 <=", value, "strdeptname2");
            return (Criteria) this;
        }

        public Criteria andStrdeptname2Like(String value) {
            addCriterion("strDeptName2 like", value, "strdeptname2");
            return (Criteria) this;
        }

        public Criteria andStrdeptname2NotLike(String value) {
            addCriterion("strDeptName2 not like", value, "strdeptname2");
            return (Criteria) this;
        }

        public Criteria andStrdeptname2In(List<String> values) {
            addCriterion("strDeptName2 in", values, "strdeptname2");
            return (Criteria) this;
        }

        public Criteria andStrdeptname2NotIn(List<String> values) {
            addCriterion("strDeptName2 not in", values, "strdeptname2");
            return (Criteria) this;
        }

        public Criteria andStrdeptname2Between(String value1, String value2) {
            addCriterion("strDeptName2 between", value1, value2, "strdeptname2");
            return (Criteria) this;
        }

        public Criteria andStrdeptname2NotBetween(String value1, String value2) {
            addCriterion("strDeptName2 not between", value1, value2, "strdeptname2");
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

        public Criteria andStrdeptname3IsNull() {
            addCriterion("strDeptName3 is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptname3IsNotNull() {
            addCriterion("strDeptName3 is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptname3EqualTo(String value) {
            addCriterion("strDeptName3 =", value, "strdeptname3");
            return (Criteria) this;
        }

        public Criteria andStrdeptname3NotEqualTo(String value) {
            addCriterion("strDeptName3 <>", value, "strdeptname3");
            return (Criteria) this;
        }

        public Criteria andStrdeptname3GreaterThan(String value) {
            addCriterion("strDeptName3 >", value, "strdeptname3");
            return (Criteria) this;
        }

        public Criteria andStrdeptname3GreaterThanOrEqualTo(String value) {
            addCriterion("strDeptName3 >=", value, "strdeptname3");
            return (Criteria) this;
        }

        public Criteria andStrdeptname3LessThan(String value) {
            addCriterion("strDeptName3 <", value, "strdeptname3");
            return (Criteria) this;
        }

        public Criteria andStrdeptname3LessThanOrEqualTo(String value) {
            addCriterion("strDeptName3 <=", value, "strdeptname3");
            return (Criteria) this;
        }

        public Criteria andStrdeptname3Like(String value) {
            addCriterion("strDeptName3 like", value, "strdeptname3");
            return (Criteria) this;
        }

        public Criteria andStrdeptname3NotLike(String value) {
            addCriterion("strDeptName3 not like", value, "strdeptname3");
            return (Criteria) this;
        }

        public Criteria andStrdeptname3In(List<String> values) {
            addCriterion("strDeptName3 in", values, "strdeptname3");
            return (Criteria) this;
        }

        public Criteria andStrdeptname3NotIn(List<String> values) {
            addCriterion("strDeptName3 not in", values, "strdeptname3");
            return (Criteria) this;
        }

        public Criteria andStrdeptname3Between(String value1, String value2) {
            addCriterion("strDeptName3 between", value1, value2, "strdeptname3");
            return (Criteria) this;
        }

        public Criteria andStrdeptname3NotBetween(String value1, String value2) {
            addCriterion("strDeptName3 not between", value1, value2, "strdeptname3");
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

        public Criteria andStrdeptname4IsNull() {
            addCriterion("strDeptName4 is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptname4IsNotNull() {
            addCriterion("strDeptName4 is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptname4EqualTo(String value) {
            addCriterion("strDeptName4 =", value, "strdeptname4");
            return (Criteria) this;
        }

        public Criteria andStrdeptname4NotEqualTo(String value) {
            addCriterion("strDeptName4 <>", value, "strdeptname4");
            return (Criteria) this;
        }

        public Criteria andStrdeptname4GreaterThan(String value) {
            addCriterion("strDeptName4 >", value, "strdeptname4");
            return (Criteria) this;
        }

        public Criteria andStrdeptname4GreaterThanOrEqualTo(String value) {
            addCriterion("strDeptName4 >=", value, "strdeptname4");
            return (Criteria) this;
        }

        public Criteria andStrdeptname4LessThan(String value) {
            addCriterion("strDeptName4 <", value, "strdeptname4");
            return (Criteria) this;
        }

        public Criteria andStrdeptname4LessThanOrEqualTo(String value) {
            addCriterion("strDeptName4 <=", value, "strdeptname4");
            return (Criteria) this;
        }

        public Criteria andStrdeptname4Like(String value) {
            addCriterion("strDeptName4 like", value, "strdeptname4");
            return (Criteria) this;
        }

        public Criteria andStrdeptname4NotLike(String value) {
            addCriterion("strDeptName4 not like", value, "strdeptname4");
            return (Criteria) this;
        }

        public Criteria andStrdeptname4In(List<String> values) {
            addCriterion("strDeptName4 in", values, "strdeptname4");
            return (Criteria) this;
        }

        public Criteria andStrdeptname4NotIn(List<String> values) {
            addCriterion("strDeptName4 not in", values, "strdeptname4");
            return (Criteria) this;
        }

        public Criteria andStrdeptname4Between(String value1, String value2) {
            addCriterion("strDeptName4 between", value1, value2, "strdeptname4");
            return (Criteria) this;
        }

        public Criteria andStrdeptname4NotBetween(String value1, String value2) {
            addCriterion("strDeptName4 not between", value1, value2, "strdeptname4");
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

        public Criteria andStrdeptname5IsNull() {
            addCriterion("strDeptName5 is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptname5IsNotNull() {
            addCriterion("strDeptName5 is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptname5EqualTo(String value) {
            addCriterion("strDeptName5 =", value, "strdeptname5");
            return (Criteria) this;
        }

        public Criteria andStrdeptname5NotEqualTo(String value) {
            addCriterion("strDeptName5 <>", value, "strdeptname5");
            return (Criteria) this;
        }

        public Criteria andStrdeptname5GreaterThan(String value) {
            addCriterion("strDeptName5 >", value, "strdeptname5");
            return (Criteria) this;
        }

        public Criteria andStrdeptname5GreaterThanOrEqualTo(String value) {
            addCriterion("strDeptName5 >=", value, "strdeptname5");
            return (Criteria) this;
        }

        public Criteria andStrdeptname5LessThan(String value) {
            addCriterion("strDeptName5 <", value, "strdeptname5");
            return (Criteria) this;
        }

        public Criteria andStrdeptname5LessThanOrEqualTo(String value) {
            addCriterion("strDeptName5 <=", value, "strdeptname5");
            return (Criteria) this;
        }

        public Criteria andStrdeptname5Like(String value) {
            addCriterion("strDeptName5 like", value, "strdeptname5");
            return (Criteria) this;
        }

        public Criteria andStrdeptname5NotLike(String value) {
            addCriterion("strDeptName5 not like", value, "strdeptname5");
            return (Criteria) this;
        }

        public Criteria andStrdeptname5In(List<String> values) {
            addCriterion("strDeptName5 in", values, "strdeptname5");
            return (Criteria) this;
        }

        public Criteria andStrdeptname5NotIn(List<String> values) {
            addCriterion("strDeptName5 not in", values, "strdeptname5");
            return (Criteria) this;
        }

        public Criteria andStrdeptname5Between(String value1, String value2) {
            addCriterion("strDeptName5 between", value1, value2, "strdeptname5");
            return (Criteria) this;
        }

        public Criteria andStrdeptname5NotBetween(String value1, String value2) {
            addCriterion("strDeptName5 not between", value1, value2, "strdeptname5");
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

        public Criteria andStrdeptname6IsNull() {
            addCriterion("strDeptName6 is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptname6IsNotNull() {
            addCriterion("strDeptName6 is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptname6EqualTo(String value) {
            addCriterion("strDeptName6 =", value, "strdeptname6");
            return (Criteria) this;
        }

        public Criteria andStrdeptname6NotEqualTo(String value) {
            addCriterion("strDeptName6 <>", value, "strdeptname6");
            return (Criteria) this;
        }

        public Criteria andStrdeptname6GreaterThan(String value) {
            addCriterion("strDeptName6 >", value, "strdeptname6");
            return (Criteria) this;
        }

        public Criteria andStrdeptname6GreaterThanOrEqualTo(String value) {
            addCriterion("strDeptName6 >=", value, "strdeptname6");
            return (Criteria) this;
        }

        public Criteria andStrdeptname6LessThan(String value) {
            addCriterion("strDeptName6 <", value, "strdeptname6");
            return (Criteria) this;
        }

        public Criteria andStrdeptname6LessThanOrEqualTo(String value) {
            addCriterion("strDeptName6 <=", value, "strdeptname6");
            return (Criteria) this;
        }

        public Criteria andStrdeptname6Like(String value) {
            addCriterion("strDeptName6 like", value, "strdeptname6");
            return (Criteria) this;
        }

        public Criteria andStrdeptname6NotLike(String value) {
            addCriterion("strDeptName6 not like", value, "strdeptname6");
            return (Criteria) this;
        }

        public Criteria andStrdeptname6In(List<String> values) {
            addCriterion("strDeptName6 in", values, "strdeptname6");
            return (Criteria) this;
        }

        public Criteria andStrdeptname6NotIn(List<String> values) {
            addCriterion("strDeptName6 not in", values, "strdeptname6");
            return (Criteria) this;
        }

        public Criteria andStrdeptname6Between(String value1, String value2) {
            addCriterion("strDeptName6 between", value1, value2, "strdeptname6");
            return (Criteria) this;
        }

        public Criteria andStrdeptname6NotBetween(String value1, String value2) {
            addCriterion("strDeptName6 not between", value1, value2, "strdeptname6");
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

        public Criteria andStrdeptname7IsNull() {
            addCriterion("strDeptName7 is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptname7IsNotNull() {
            addCriterion("strDeptName7 is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptname7EqualTo(String value) {
            addCriterion("strDeptName7 =", value, "strdeptname7");
            return (Criteria) this;
        }

        public Criteria andStrdeptname7NotEqualTo(String value) {
            addCriterion("strDeptName7 <>", value, "strdeptname7");
            return (Criteria) this;
        }

        public Criteria andStrdeptname7GreaterThan(String value) {
            addCriterion("strDeptName7 >", value, "strdeptname7");
            return (Criteria) this;
        }

        public Criteria andStrdeptname7GreaterThanOrEqualTo(String value) {
            addCriterion("strDeptName7 >=", value, "strdeptname7");
            return (Criteria) this;
        }

        public Criteria andStrdeptname7LessThan(String value) {
            addCriterion("strDeptName7 <", value, "strdeptname7");
            return (Criteria) this;
        }

        public Criteria andStrdeptname7LessThanOrEqualTo(String value) {
            addCriterion("strDeptName7 <=", value, "strdeptname7");
            return (Criteria) this;
        }

        public Criteria andStrdeptname7Like(String value) {
            addCriterion("strDeptName7 like", value, "strdeptname7");
            return (Criteria) this;
        }

        public Criteria andStrdeptname7NotLike(String value) {
            addCriterion("strDeptName7 not like", value, "strdeptname7");
            return (Criteria) this;
        }

        public Criteria andStrdeptname7In(List<String> values) {
            addCriterion("strDeptName7 in", values, "strdeptname7");
            return (Criteria) this;
        }

        public Criteria andStrdeptname7NotIn(List<String> values) {
            addCriterion("strDeptName7 not in", values, "strdeptname7");
            return (Criteria) this;
        }

        public Criteria andStrdeptname7Between(String value1, String value2) {
            addCriterion("strDeptName7 between", value1, value2, "strdeptname7");
            return (Criteria) this;
        }

        public Criteria andStrdeptname7NotBetween(String value1, String value2) {
            addCriterion("strDeptName7 not between", value1, value2, "strdeptname7");
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

        public Criteria andStrdeptname8IsNull() {
            addCriterion("strDeptName8 is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptname8IsNotNull() {
            addCriterion("strDeptName8 is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptname8EqualTo(String value) {
            addCriterion("strDeptName8 =", value, "strdeptname8");
            return (Criteria) this;
        }

        public Criteria andStrdeptname8NotEqualTo(String value) {
            addCriterion("strDeptName8 <>", value, "strdeptname8");
            return (Criteria) this;
        }

        public Criteria andStrdeptname8GreaterThan(String value) {
            addCriterion("strDeptName8 >", value, "strdeptname8");
            return (Criteria) this;
        }

        public Criteria andStrdeptname8GreaterThanOrEqualTo(String value) {
            addCriterion("strDeptName8 >=", value, "strdeptname8");
            return (Criteria) this;
        }

        public Criteria andStrdeptname8LessThan(String value) {
            addCriterion("strDeptName8 <", value, "strdeptname8");
            return (Criteria) this;
        }

        public Criteria andStrdeptname8LessThanOrEqualTo(String value) {
            addCriterion("strDeptName8 <=", value, "strdeptname8");
            return (Criteria) this;
        }

        public Criteria andStrdeptname8Like(String value) {
            addCriterion("strDeptName8 like", value, "strdeptname8");
            return (Criteria) this;
        }

        public Criteria andStrdeptname8NotLike(String value) {
            addCriterion("strDeptName8 not like", value, "strdeptname8");
            return (Criteria) this;
        }

        public Criteria andStrdeptname8In(List<String> values) {
            addCriterion("strDeptName8 in", values, "strdeptname8");
            return (Criteria) this;
        }

        public Criteria andStrdeptname8NotIn(List<String> values) {
            addCriterion("strDeptName8 not in", values, "strdeptname8");
            return (Criteria) this;
        }

        public Criteria andStrdeptname8Between(String value1, String value2) {
            addCriterion("strDeptName8 between", value1, value2, "strdeptname8");
            return (Criteria) this;
        }

        public Criteria andStrdeptname8NotBetween(String value1, String value2) {
            addCriterion("strDeptName8 not between", value1, value2, "strdeptname8");
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

        public Criteria andStrdeptname9IsNull() {
            addCriterion("strDeptName9 is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptname9IsNotNull() {
            addCriterion("strDeptName9 is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptname9EqualTo(String value) {
            addCriterion("strDeptName9 =", value, "strdeptname9");
            return (Criteria) this;
        }

        public Criteria andStrdeptname9NotEqualTo(String value) {
            addCriterion("strDeptName9 <>", value, "strdeptname9");
            return (Criteria) this;
        }

        public Criteria andStrdeptname9GreaterThan(String value) {
            addCriterion("strDeptName9 >", value, "strdeptname9");
            return (Criteria) this;
        }

        public Criteria andStrdeptname9GreaterThanOrEqualTo(String value) {
            addCriterion("strDeptName9 >=", value, "strdeptname9");
            return (Criteria) this;
        }

        public Criteria andStrdeptname9LessThan(String value) {
            addCriterion("strDeptName9 <", value, "strdeptname9");
            return (Criteria) this;
        }

        public Criteria andStrdeptname9LessThanOrEqualTo(String value) {
            addCriterion("strDeptName9 <=", value, "strdeptname9");
            return (Criteria) this;
        }

        public Criteria andStrdeptname9Like(String value) {
            addCriterion("strDeptName9 like", value, "strdeptname9");
            return (Criteria) this;
        }

        public Criteria andStrdeptname9NotLike(String value) {
            addCriterion("strDeptName9 not like", value, "strdeptname9");
            return (Criteria) this;
        }

        public Criteria andStrdeptname9In(List<String> values) {
            addCriterion("strDeptName9 in", values, "strdeptname9");
            return (Criteria) this;
        }

        public Criteria andStrdeptname9NotIn(List<String> values) {
            addCriterion("strDeptName9 not in", values, "strdeptname9");
            return (Criteria) this;
        }

        public Criteria andStrdeptname9Between(String value1, String value2) {
            addCriterion("strDeptName9 between", value1, value2, "strdeptname9");
            return (Criteria) this;
        }

        public Criteria andStrdeptname9NotBetween(String value1, String value2) {
            addCriterion("strDeptName9 not between", value1, value2, "strdeptname9");
            return (Criteria) this;
        }

        public Criteria andLmanageridIsNull() {
            addCriterion("lManagerId is null");
            return (Criteria) this;
        }

        public Criteria andLmanageridIsNotNull() {
            addCriterion("lManagerId is not null");
            return (Criteria) this;
        }

        public Criteria andLmanageridEqualTo(Long value) {
            addCriterion("lManagerId =", value, "lmanagerid");
            return (Criteria) this;
        }

        public Criteria andLmanageridNotEqualTo(Long value) {
            addCriterion("lManagerId <>", value, "lmanagerid");
            return (Criteria) this;
        }

        public Criteria andLmanageridGreaterThan(Long value) {
            addCriterion("lManagerId >", value, "lmanagerid");
            return (Criteria) this;
        }

        public Criteria andLmanageridGreaterThanOrEqualTo(Long value) {
            addCriterion("lManagerId >=", value, "lmanagerid");
            return (Criteria) this;
        }

        public Criteria andLmanageridLessThan(Long value) {
            addCriterion("lManagerId <", value, "lmanagerid");
            return (Criteria) this;
        }

        public Criteria andLmanageridLessThanOrEqualTo(Long value) {
            addCriterion("lManagerId <=", value, "lmanagerid");
            return (Criteria) this;
        }

        public Criteria andLmanageridIn(List<Long> values) {
            addCriterion("lManagerId in", values, "lmanagerid");
            return (Criteria) this;
        }

        public Criteria andLmanageridNotIn(List<Long> values) {
            addCriterion("lManagerId not in", values, "lmanagerid");
            return (Criteria) this;
        }

        public Criteria andLmanageridBetween(Long value1, Long value2) {
            addCriterion("lManagerId between", value1, value2, "lmanagerid");
            return (Criteria) this;
        }

        public Criteria andLmanageridNotBetween(Long value1, Long value2) {
            addCriterion("lManagerId not between", value1, value2, "lmanagerid");
            return (Criteria) this;
        }

        public Criteria andStrmanagernameIsNull() {
            addCriterion("strManagerName is null");
            return (Criteria) this;
        }

        public Criteria andStrmanagernameIsNotNull() {
            addCriterion("strManagerName is not null");
            return (Criteria) this;
        }

        public Criteria andStrmanagernameEqualTo(String value) {
            addCriterion("strManagerName =", value, "strmanagername");
            return (Criteria) this;
        }

        public Criteria andStrmanagernameNotEqualTo(String value) {
            addCriterion("strManagerName <>", value, "strmanagername");
            return (Criteria) this;
        }

        public Criteria andStrmanagernameGreaterThan(String value) {
            addCriterion("strManagerName >", value, "strmanagername");
            return (Criteria) this;
        }

        public Criteria andStrmanagernameGreaterThanOrEqualTo(String value) {
            addCriterion("strManagerName >=", value, "strmanagername");
            return (Criteria) this;
        }

        public Criteria andStrmanagernameLessThan(String value) {
            addCriterion("strManagerName <", value, "strmanagername");
            return (Criteria) this;
        }

        public Criteria andStrmanagernameLessThanOrEqualTo(String value) {
            addCriterion("strManagerName <=", value, "strmanagername");
            return (Criteria) this;
        }

        public Criteria andStrmanagernameLike(String value) {
            addCriterion("strManagerName like", value, "strmanagername");
            return (Criteria) this;
        }

        public Criteria andStrmanagernameNotLike(String value) {
            addCriterion("strManagerName not like", value, "strmanagername");
            return (Criteria) this;
        }

        public Criteria andStrmanagernameIn(List<String> values) {
            addCriterion("strManagerName in", values, "strmanagername");
            return (Criteria) this;
        }

        public Criteria andStrmanagernameNotIn(List<String> values) {
            addCriterion("strManagerName not in", values, "strmanagername");
            return (Criteria) this;
        }

        public Criteria andStrmanagernameBetween(String value1, String value2) {
            addCriterion("strManagerName between", value1, value2, "strmanagername");
            return (Criteria) this;
        }

        public Criteria andStrmanagernameNotBetween(String value1, String value2) {
            addCriterion("strManagerName not between", value1, value2, "strmanagername");
            return (Criteria) this;
        }

        public Criteria andStrmanagermobileIsNull() {
            addCriterion("strManagerMobile is null");
            return (Criteria) this;
        }

        public Criteria andStrmanagermobileIsNotNull() {
            addCriterion("strManagerMobile is not null");
            return (Criteria) this;
        }

        public Criteria andStrmanagermobileEqualTo(String value) {
            addCriterion("strManagerMobile =", value, "strmanagermobile");
            return (Criteria) this;
        }

        public Criteria andStrmanagermobileNotEqualTo(String value) {
            addCriterion("strManagerMobile <>", value, "strmanagermobile");
            return (Criteria) this;
        }

        public Criteria andStrmanagermobileGreaterThan(String value) {
            addCriterion("strManagerMobile >", value, "strmanagermobile");
            return (Criteria) this;
        }

        public Criteria andStrmanagermobileGreaterThanOrEqualTo(String value) {
            addCriterion("strManagerMobile >=", value, "strmanagermobile");
            return (Criteria) this;
        }

        public Criteria andStrmanagermobileLessThan(String value) {
            addCriterion("strManagerMobile <", value, "strmanagermobile");
            return (Criteria) this;
        }

        public Criteria andStrmanagermobileLessThanOrEqualTo(String value) {
            addCriterion("strManagerMobile <=", value, "strmanagermobile");
            return (Criteria) this;
        }

        public Criteria andStrmanagermobileLike(String value) {
            addCriterion("strManagerMobile like", value, "strmanagermobile");
            return (Criteria) this;
        }

        public Criteria andStrmanagermobileNotLike(String value) {
            addCriterion("strManagerMobile not like", value, "strmanagermobile");
            return (Criteria) this;
        }

        public Criteria andStrmanagermobileIn(List<String> values) {
            addCriterion("strManagerMobile in", values, "strmanagermobile");
            return (Criteria) this;
        }

        public Criteria andStrmanagermobileNotIn(List<String> values) {
            addCriterion("strManagerMobile not in", values, "strmanagermobile");
            return (Criteria) this;
        }

        public Criteria andStrmanagermobileBetween(String value1, String value2) {
            addCriterion("strManagerMobile between", value1, value2, "strmanagermobile");
            return (Criteria) this;
        }

        public Criteria andStrmanagermobileNotBetween(String value1, String value2) {
            addCriterion("strManagerMobile not between", value1, value2, "strmanagermobile");
            return (Criteria) this;
        }

        public Criteria andStrmanagermailIsNull() {
            addCriterion("strManagerMail is null");
            return (Criteria) this;
        }

        public Criteria andStrmanagermailIsNotNull() {
            addCriterion("strManagerMail is not null");
            return (Criteria) this;
        }

        public Criteria andStrmanagermailEqualTo(String value) {
            addCriterion("strManagerMail =", value, "strmanagermail");
            return (Criteria) this;
        }

        public Criteria andStrmanagermailNotEqualTo(String value) {
            addCriterion("strManagerMail <>", value, "strmanagermail");
            return (Criteria) this;
        }

        public Criteria andStrmanagermailGreaterThan(String value) {
            addCriterion("strManagerMail >", value, "strmanagermail");
            return (Criteria) this;
        }

        public Criteria andStrmanagermailGreaterThanOrEqualTo(String value) {
            addCriterion("strManagerMail >=", value, "strmanagermail");
            return (Criteria) this;
        }

        public Criteria andStrmanagermailLessThan(String value) {
            addCriterion("strManagerMail <", value, "strmanagermail");
            return (Criteria) this;
        }

        public Criteria andStrmanagermailLessThanOrEqualTo(String value) {
            addCriterion("strManagerMail <=", value, "strmanagermail");
            return (Criteria) this;
        }

        public Criteria andStrmanagermailLike(String value) {
            addCriterion("strManagerMail like", value, "strmanagermail");
            return (Criteria) this;
        }

        public Criteria andStrmanagermailNotLike(String value) {
            addCriterion("strManagerMail not like", value, "strmanagermail");
            return (Criteria) this;
        }

        public Criteria andStrmanagermailIn(List<String> values) {
            addCriterion("strManagerMail in", values, "strmanagermail");
            return (Criteria) this;
        }

        public Criteria andStrmanagermailNotIn(List<String> values) {
            addCriterion("strManagerMail not in", values, "strmanagermail");
            return (Criteria) this;
        }

        public Criteria andStrmanagermailBetween(String value1, String value2) {
            addCriterion("strManagerMail between", value1, value2, "strmanagermail");
            return (Criteria) this;
        }

        public Criteria andStrmanagermailNotBetween(String value1, String value2) {
            addCriterion("strManagerMail not between", value1, value2, "strmanagermail");
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

        public Criteria andNsaleslevelIsNull() {
            addCriterion("nSalesLevel is null");
            return (Criteria) this;
        }

        public Criteria andNsaleslevelIsNotNull() {
            addCriterion("nSalesLevel is not null");
            return (Criteria) this;
        }

        public Criteria andNsaleslevelEqualTo(Integer value) {
            addCriterion("nSalesLevel =", value, "nsaleslevel");
            return (Criteria) this;
        }

        public Criteria andNsaleslevelNotEqualTo(Integer value) {
            addCriterion("nSalesLevel <>", value, "nsaleslevel");
            return (Criteria) this;
        }

        public Criteria andNsaleslevelGreaterThan(Integer value) {
            addCriterion("nSalesLevel >", value, "nsaleslevel");
            return (Criteria) this;
        }

        public Criteria andNsaleslevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("nSalesLevel >=", value, "nsaleslevel");
            return (Criteria) this;
        }

        public Criteria andNsaleslevelLessThan(Integer value) {
            addCriterion("nSalesLevel <", value, "nsaleslevel");
            return (Criteria) this;
        }

        public Criteria andNsaleslevelLessThanOrEqualTo(Integer value) {
            addCriterion("nSalesLevel <=", value, "nsaleslevel");
            return (Criteria) this;
        }

        public Criteria andNsaleslevelIn(List<Integer> values) {
            addCriterion("nSalesLevel in", values, "nsaleslevel");
            return (Criteria) this;
        }

        public Criteria andNsaleslevelNotIn(List<Integer> values) {
            addCriterion("nSalesLevel not in", values, "nsaleslevel");
            return (Criteria) this;
        }

        public Criteria andNsaleslevelBetween(Integer value1, Integer value2) {
            addCriterion("nSalesLevel between", value1, value2, "nsaleslevel");
            return (Criteria) this;
        }

        public Criteria andNsaleslevelNotBetween(Integer value1, Integer value2) {
            addCriterion("nSalesLevel not between", value1, value2, "nsaleslevel");
            return (Criteria) this;
        }

        public Criteria andStropendayIsNull() {
            addCriterion("strOpenDay is null");
            return (Criteria) this;
        }

        public Criteria andStropendayIsNotNull() {
            addCriterion("strOpenDay is not null");
            return (Criteria) this;
        }

        public Criteria andStropendayEqualTo(String value) {
            addCriterion("strOpenDay =", value, "stropenday");
            return (Criteria) this;
        }

        public Criteria andStropendayNotEqualTo(String value) {
            addCriterion("strOpenDay <>", value, "stropenday");
            return (Criteria) this;
        }

        public Criteria andStropendayGreaterThan(String value) {
            addCriterion("strOpenDay >", value, "stropenday");
            return (Criteria) this;
        }

        public Criteria andStropendayGreaterThanOrEqualTo(String value) {
            addCriterion("strOpenDay >=", value, "stropenday");
            return (Criteria) this;
        }

        public Criteria andStropendayLessThan(String value) {
            addCriterion("strOpenDay <", value, "stropenday");
            return (Criteria) this;
        }

        public Criteria andStropendayLessThanOrEqualTo(String value) {
            addCriterion("strOpenDay <=", value, "stropenday");
            return (Criteria) this;
        }

        public Criteria andStropendayLike(String value) {
            addCriterion("strOpenDay like", value, "stropenday");
            return (Criteria) this;
        }

        public Criteria andStropendayNotLike(String value) {
            addCriterion("strOpenDay not like", value, "stropenday");
            return (Criteria) this;
        }

        public Criteria andStropendayIn(List<String> values) {
            addCriterion("strOpenDay in", values, "stropenday");
            return (Criteria) this;
        }

        public Criteria andStropendayNotIn(List<String> values) {
            addCriterion("strOpenDay not in", values, "stropenday");
            return (Criteria) this;
        }

        public Criteria andStropendayBetween(String value1, String value2) {
            addCriterion("strOpenDay between", value1, value2, "stropenday");
            return (Criteria) this;
        }

        public Criteria andStropendayNotBetween(String value1, String value2) {
            addCriterion("strOpenDay not between", value1, value2, "stropenday");
            return (Criteria) this;
        }

        public Criteria andStrdeptmobileIsNull() {
            addCriterion("strDeptMobile is null");
            return (Criteria) this;
        }

        public Criteria andStrdeptmobileIsNotNull() {
            addCriterion("strDeptMobile is not null");
            return (Criteria) this;
        }

        public Criteria andStrdeptmobileEqualTo(String value) {
            addCriterion("strDeptMobile =", value, "strdeptmobile");
            return (Criteria) this;
        }

        public Criteria andStrdeptmobileNotEqualTo(String value) {
            addCriterion("strDeptMobile <>", value, "strdeptmobile");
            return (Criteria) this;
        }

        public Criteria andStrdeptmobileGreaterThan(String value) {
            addCriterion("strDeptMobile >", value, "strdeptmobile");
            return (Criteria) this;
        }

        public Criteria andStrdeptmobileGreaterThanOrEqualTo(String value) {
            addCriterion("strDeptMobile >=", value, "strdeptmobile");
            return (Criteria) this;
        }

        public Criteria andStrdeptmobileLessThan(String value) {
            addCriterion("strDeptMobile <", value, "strdeptmobile");
            return (Criteria) this;
        }

        public Criteria andStrdeptmobileLessThanOrEqualTo(String value) {
            addCriterion("strDeptMobile <=", value, "strdeptmobile");
            return (Criteria) this;
        }

        public Criteria andStrdeptmobileLike(String value) {
            addCriterion("strDeptMobile like", value, "strdeptmobile");
            return (Criteria) this;
        }

        public Criteria andStrdeptmobileNotLike(String value) {
            addCriterion("strDeptMobile not like", value, "strdeptmobile");
            return (Criteria) this;
        }

        public Criteria andStrdeptmobileIn(List<String> values) {
            addCriterion("strDeptMobile in", values, "strdeptmobile");
            return (Criteria) this;
        }

        public Criteria andStrdeptmobileNotIn(List<String> values) {
            addCriterion("strDeptMobile not in", values, "strdeptmobile");
            return (Criteria) this;
        }

        public Criteria andStrdeptmobileBetween(String value1, String value2) {
            addCriterion("strDeptMobile between", value1, value2, "strdeptmobile");
            return (Criteria) this;
        }

        public Criteria andStrdeptmobileNotBetween(String value1, String value2) {
            addCriterion("strDeptMobile not between", value1, value2, "strdeptmobile");
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