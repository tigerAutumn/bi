package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsCustomerDafyDeptExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsCustomerDafyDeptExample() {
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

        public Criteria andDeptCodeIsNull() {
            addCriterion("dept_code is null");
            return (Criteria) this;
        }

        public Criteria andDeptCodeIsNotNull() {
            addCriterion("dept_code is not null");
            return (Criteria) this;
        }

        public Criteria andDeptCodeEqualTo(String value) {
            addCriterion("dept_code =", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeNotEqualTo(String value) {
            addCriterion("dept_code <>", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeGreaterThan(String value) {
            addCriterion("dept_code >", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeGreaterThanOrEqualTo(String value) {
            addCriterion("dept_code >=", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeLessThan(String value) {
            addCriterion("dept_code <", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeLessThanOrEqualTo(String value) {
            addCriterion("dept_code <=", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeLike(String value) {
            addCriterion("dept_code like", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeNotLike(String value) {
            addCriterion("dept_code not like", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeIn(List<String> values) {
            addCriterion("dept_code in", values, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeNotIn(List<String> values) {
            addCriterion("dept_code not in", values, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeBetween(String value1, String value2) {
            addCriterion("dept_code between", value1, value2, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeNotBetween(String value1, String value2) {
            addCriterion("dept_code not between", value1, value2, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptNameIsNull() {
            addCriterion("dept_name is null");
            return (Criteria) this;
        }

        public Criteria andDeptNameIsNotNull() {
            addCriterion("dept_name is not null");
            return (Criteria) this;
        }

        public Criteria andDeptNameEqualTo(String value) {
            addCriterion("dept_name =", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotEqualTo(String value) {
            addCriterion("dept_name <>", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameGreaterThan(String value) {
            addCriterion("dept_name >", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameGreaterThanOrEqualTo(String value) {
            addCriterion("dept_name >=", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameLessThan(String value) {
            addCriterion("dept_name <", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameLessThanOrEqualTo(String value) {
            addCriterion("dept_name <=", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameLike(String value) {
            addCriterion("dept_name like", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotLike(String value) {
            addCriterion("dept_name not like", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameIn(List<String> values) {
            addCriterion("dept_name in", values, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotIn(List<String> values) {
            addCriterion("dept_name not in", values, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameBetween(String value1, String value2) {
            addCriterion("dept_name between", value1, value2, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotBetween(String value1, String value2) {
            addCriterion("dept_name not between", value1, value2, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptLevelIsNull() {
            addCriterion("dept_level is null");
            return (Criteria) this;
        }

        public Criteria andDeptLevelIsNotNull() {
            addCriterion("dept_level is not null");
            return (Criteria) this;
        }

        public Criteria andDeptLevelEqualTo(Integer value) {
            addCriterion("dept_level =", value, "deptLevel");
            return (Criteria) this;
        }

        public Criteria andDeptLevelNotEqualTo(Integer value) {
            addCriterion("dept_level <>", value, "deptLevel");
            return (Criteria) this;
        }

        public Criteria andDeptLevelGreaterThan(Integer value) {
            addCriterion("dept_level >", value, "deptLevel");
            return (Criteria) this;
        }

        public Criteria andDeptLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("dept_level >=", value, "deptLevel");
            return (Criteria) this;
        }

        public Criteria andDeptLevelLessThan(Integer value) {
            addCriterion("dept_level <", value, "deptLevel");
            return (Criteria) this;
        }

        public Criteria andDeptLevelLessThanOrEqualTo(Integer value) {
            addCriterion("dept_level <=", value, "deptLevel");
            return (Criteria) this;
        }

        public Criteria andDeptLevelIn(List<Integer> values) {
            addCriterion("dept_level in", values, "deptLevel");
            return (Criteria) this;
        }

        public Criteria andDeptLevelNotIn(List<Integer> values) {
            addCriterion("dept_level not in", values, "deptLevel");
            return (Criteria) this;
        }

        public Criteria andDeptLevelBetween(Integer value1, Integer value2) {
            addCriterion("dept_level between", value1, value2, "deptLevel");
            return (Criteria) this;
        }

        public Criteria andDeptLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("dept_level not between", value1, value2, "deptLevel");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeIsNull() {
            addCriterion("province_code is null");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeIsNotNull() {
            addCriterion("province_code is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeEqualTo(String value) {
            addCriterion("province_code =", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeNotEqualTo(String value) {
            addCriterion("province_code <>", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeGreaterThan(String value) {
            addCriterion("province_code >", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeGreaterThanOrEqualTo(String value) {
            addCriterion("province_code >=", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeLessThan(String value) {
            addCriterion("province_code <", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeLessThanOrEqualTo(String value) {
            addCriterion("province_code <=", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeLike(String value) {
            addCriterion("province_code like", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeNotLike(String value) {
            addCriterion("province_code not like", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeIn(List<String> values) {
            addCriterion("province_code in", values, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeNotIn(List<String> values) {
            addCriterion("province_code not in", values, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeBetween(String value1, String value2) {
            addCriterion("province_code between", value1, value2, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeNotBetween(String value1, String value2) {
            addCriterion("province_code not between", value1, value2, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceNameIsNull() {
            addCriterion("province_name is null");
            return (Criteria) this;
        }

        public Criteria andProvinceNameIsNotNull() {
            addCriterion("province_name is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceNameEqualTo(String value) {
            addCriterion("province_name =", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameNotEqualTo(String value) {
            addCriterion("province_name <>", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameGreaterThan(String value) {
            addCriterion("province_name >", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameGreaterThanOrEqualTo(String value) {
            addCriterion("province_name >=", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameLessThan(String value) {
            addCriterion("province_name <", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameLessThanOrEqualTo(String value) {
            addCriterion("province_name <=", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameLike(String value) {
            addCriterion("province_name like", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameNotLike(String value) {
            addCriterion("province_name not like", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameIn(List<String> values) {
            addCriterion("province_name in", values, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameNotIn(List<String> values) {
            addCriterion("province_name not in", values, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameBetween(String value1, String value2) {
            addCriterion("province_name between", value1, value2, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameNotBetween(String value1, String value2) {
            addCriterion("province_name not between", value1, value2, "provinceName");
            return (Criteria) this;
        }

        public Criteria andCityCodeIsNull() {
            addCriterion("city_code is null");
            return (Criteria) this;
        }

        public Criteria andCityCodeIsNotNull() {
            addCriterion("city_code is not null");
            return (Criteria) this;
        }

        public Criteria andCityCodeEqualTo(String value) {
            addCriterion("city_code =", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotEqualTo(String value) {
            addCriterion("city_code <>", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeGreaterThan(String value) {
            addCriterion("city_code >", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeGreaterThanOrEqualTo(String value) {
            addCriterion("city_code >=", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeLessThan(String value) {
            addCriterion("city_code <", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeLessThanOrEqualTo(String value) {
            addCriterion("city_code <=", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeLike(String value) {
            addCriterion("city_code like", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotLike(String value) {
            addCriterion("city_code not like", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeIn(List<String> values) {
            addCriterion("city_code in", values, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotIn(List<String> values) {
            addCriterion("city_code not in", values, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeBetween(String value1, String value2) {
            addCriterion("city_code between", value1, value2, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotBetween(String value1, String value2) {
            addCriterion("city_code not between", value1, value2, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityNameIsNull() {
            addCriterion("city_name is null");
            return (Criteria) this;
        }

        public Criteria andCityNameIsNotNull() {
            addCriterion("city_name is not null");
            return (Criteria) this;
        }

        public Criteria andCityNameEqualTo(String value) {
            addCriterion("city_name =", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotEqualTo(String value) {
            addCriterion("city_name <>", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameGreaterThan(String value) {
            addCriterion("city_name >", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameGreaterThanOrEqualTo(String value) {
            addCriterion("city_name >=", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLessThan(String value) {
            addCriterion("city_name <", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLessThanOrEqualTo(String value) {
            addCriterion("city_name <=", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLike(String value) {
            addCriterion("city_name like", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotLike(String value) {
            addCriterion("city_name not like", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameIn(List<String> values) {
            addCriterion("city_name in", values, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotIn(List<String> values) {
            addCriterion("city_name not in", values, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameBetween(String value1, String value2) {
            addCriterion("city_name between", value1, value2, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotBetween(String value1, String value2) {
            addCriterion("city_name not between", value1, value2, "cityName");
            return (Criteria) this;
        }

        public Criteria andDeptAddressIsNull() {
            addCriterion("dept_address is null");
            return (Criteria) this;
        }

        public Criteria andDeptAddressIsNotNull() {
            addCriterion("dept_address is not null");
            return (Criteria) this;
        }

        public Criteria andDeptAddressEqualTo(String value) {
            addCriterion("dept_address =", value, "deptAddress");
            return (Criteria) this;
        }

        public Criteria andDeptAddressNotEqualTo(String value) {
            addCriterion("dept_address <>", value, "deptAddress");
            return (Criteria) this;
        }

        public Criteria andDeptAddressGreaterThan(String value) {
            addCriterion("dept_address >", value, "deptAddress");
            return (Criteria) this;
        }

        public Criteria andDeptAddressGreaterThanOrEqualTo(String value) {
            addCriterion("dept_address >=", value, "deptAddress");
            return (Criteria) this;
        }

        public Criteria andDeptAddressLessThan(String value) {
            addCriterion("dept_address <", value, "deptAddress");
            return (Criteria) this;
        }

        public Criteria andDeptAddressLessThanOrEqualTo(String value) {
            addCriterion("dept_address <=", value, "deptAddress");
            return (Criteria) this;
        }

        public Criteria andDeptAddressLike(String value) {
            addCriterion("dept_address like", value, "deptAddress");
            return (Criteria) this;
        }

        public Criteria andDeptAddressNotLike(String value) {
            addCriterion("dept_address not like", value, "deptAddress");
            return (Criteria) this;
        }

        public Criteria andDeptAddressIn(List<String> values) {
            addCriterion("dept_address in", values, "deptAddress");
            return (Criteria) this;
        }

        public Criteria andDeptAddressNotIn(List<String> values) {
            addCriterion("dept_address not in", values, "deptAddress");
            return (Criteria) this;
        }

        public Criteria andDeptAddressBetween(String value1, String value2) {
            addCriterion("dept_address between", value1, value2, "deptAddress");
            return (Criteria) this;
        }

        public Criteria andDeptAddressNotBetween(String value1, String value2) {
            addCriterion("dept_address not between", value1, value2, "deptAddress");
            return (Criteria) this;
        }

        public Criteria andIsSaleDeptIsNull() {
            addCriterion("is_sale_dept is null");
            return (Criteria) this;
        }

        public Criteria andIsSaleDeptIsNotNull() {
            addCriterion("is_sale_dept is not null");
            return (Criteria) this;
        }

        public Criteria andIsSaleDeptEqualTo(String value) {
            addCriterion("is_sale_dept =", value, "isSaleDept");
            return (Criteria) this;
        }

        public Criteria andIsSaleDeptNotEqualTo(String value) {
            addCriterion("is_sale_dept <>", value, "isSaleDept");
            return (Criteria) this;
        }

        public Criteria andIsSaleDeptGreaterThan(String value) {
            addCriterion("is_sale_dept >", value, "isSaleDept");
            return (Criteria) this;
        }

        public Criteria andIsSaleDeptGreaterThanOrEqualTo(String value) {
            addCriterion("is_sale_dept >=", value, "isSaleDept");
            return (Criteria) this;
        }

        public Criteria andIsSaleDeptLessThan(String value) {
            addCriterion("is_sale_dept <", value, "isSaleDept");
            return (Criteria) this;
        }

        public Criteria andIsSaleDeptLessThanOrEqualTo(String value) {
            addCriterion("is_sale_dept <=", value, "isSaleDept");
            return (Criteria) this;
        }

        public Criteria andIsSaleDeptLike(String value) {
            addCriterion("is_sale_dept like", value, "isSaleDept");
            return (Criteria) this;
        }

        public Criteria andIsSaleDeptNotLike(String value) {
            addCriterion("is_sale_dept not like", value, "isSaleDept");
            return (Criteria) this;
        }

        public Criteria andIsSaleDeptIn(List<String> values) {
            addCriterion("is_sale_dept in", values, "isSaleDept");
            return (Criteria) this;
        }

        public Criteria andIsSaleDeptNotIn(List<String> values) {
            addCriterion("is_sale_dept not in", values, "isSaleDept");
            return (Criteria) this;
        }

        public Criteria andIsSaleDeptBetween(String value1, String value2) {
            addCriterion("is_sale_dept between", value1, value2, "isSaleDept");
            return (Criteria) this;
        }

        public Criteria andIsSaleDeptNotBetween(String value1, String value2) {
            addCriterion("is_sale_dept not between", value1, value2, "isSaleDept");
            return (Criteria) this;
        }

        public Criteria andIsLeafIsNull() {
            addCriterion("is_leaf is null");
            return (Criteria) this;
        }

        public Criteria andIsLeafIsNotNull() {
            addCriterion("is_leaf is not null");
            return (Criteria) this;
        }

        public Criteria andIsLeafEqualTo(String value) {
            addCriterion("is_leaf =", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafNotEqualTo(String value) {
            addCriterion("is_leaf <>", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafGreaterThan(String value) {
            addCriterion("is_leaf >", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafGreaterThanOrEqualTo(String value) {
            addCriterion("is_leaf >=", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafLessThan(String value) {
            addCriterion("is_leaf <", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafLessThanOrEqualTo(String value) {
            addCriterion("is_leaf <=", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafLike(String value) {
            addCriterion("is_leaf like", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafNotLike(String value) {
            addCriterion("is_leaf not like", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafIn(List<String> values) {
            addCriterion("is_leaf in", values, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafNotIn(List<String> values) {
            addCriterion("is_leaf not in", values, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafBetween(String value1, String value2) {
            addCriterion("is_leaf between", value1, value2, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafNotBetween(String value1, String value2) {
            addCriterion("is_leaf not between", value1, value2, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andManagerDafyIdIsNull() {
            addCriterion("manager_dafy_id is null");
            return (Criteria) this;
        }

        public Criteria andManagerDafyIdIsNotNull() {
            addCriterion("manager_dafy_id is not null");
            return (Criteria) this;
        }

        public Criteria andManagerDafyIdEqualTo(Integer value) {
            addCriterion("manager_dafy_id =", value, "managerDafyId");
            return (Criteria) this;
        }

        public Criteria andManagerDafyIdNotEqualTo(Integer value) {
            addCriterion("manager_dafy_id <>", value, "managerDafyId");
            return (Criteria) this;
        }

        public Criteria andManagerDafyIdGreaterThan(Integer value) {
            addCriterion("manager_dafy_id >", value, "managerDafyId");
            return (Criteria) this;
        }

        public Criteria andManagerDafyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("manager_dafy_id >=", value, "managerDafyId");
            return (Criteria) this;
        }

        public Criteria andManagerDafyIdLessThan(Integer value) {
            addCriterion("manager_dafy_id <", value, "managerDafyId");
            return (Criteria) this;
        }

        public Criteria andManagerDafyIdLessThanOrEqualTo(Integer value) {
            addCriterion("manager_dafy_id <=", value, "managerDafyId");
            return (Criteria) this;
        }

        public Criteria andManagerDafyIdIn(List<Integer> values) {
            addCriterion("manager_dafy_id in", values, "managerDafyId");
            return (Criteria) this;
        }

        public Criteria andManagerDafyIdNotIn(List<Integer> values) {
            addCriterion("manager_dafy_id not in", values, "managerDafyId");
            return (Criteria) this;
        }

        public Criteria andManagerDafyIdBetween(Integer value1, Integer value2) {
            addCriterion("manager_dafy_id between", value1, value2, "managerDafyId");
            return (Criteria) this;
        }

        public Criteria andManagerDafyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("manager_dafy_id not between", value1, value2, "managerDafyId");
            return (Criteria) this;
        }

        public Criteria andManageNameIsNull() {
            addCriterion("manage_name is null");
            return (Criteria) this;
        }

        public Criteria andManageNameIsNotNull() {
            addCriterion("manage_name is not null");
            return (Criteria) this;
        }

        public Criteria andManageNameEqualTo(String value) {
            addCriterion("manage_name =", value, "manageName");
            return (Criteria) this;
        }

        public Criteria andManageNameNotEqualTo(String value) {
            addCriterion("manage_name <>", value, "manageName");
            return (Criteria) this;
        }

        public Criteria andManageNameGreaterThan(String value) {
            addCriterion("manage_name >", value, "manageName");
            return (Criteria) this;
        }

        public Criteria andManageNameGreaterThanOrEqualTo(String value) {
            addCriterion("manage_name >=", value, "manageName");
            return (Criteria) this;
        }

        public Criteria andManageNameLessThan(String value) {
            addCriterion("manage_name <", value, "manageName");
            return (Criteria) this;
        }

        public Criteria andManageNameLessThanOrEqualTo(String value) {
            addCriterion("manage_name <=", value, "manageName");
            return (Criteria) this;
        }

        public Criteria andManageNameLike(String value) {
            addCriterion("manage_name like", value, "manageName");
            return (Criteria) this;
        }

        public Criteria andManageNameNotLike(String value) {
            addCriterion("manage_name not like", value, "manageName");
            return (Criteria) this;
        }

        public Criteria andManageNameIn(List<String> values) {
            addCriterion("manage_name in", values, "manageName");
            return (Criteria) this;
        }

        public Criteria andManageNameNotIn(List<String> values) {
            addCriterion("manage_name not in", values, "manageName");
            return (Criteria) this;
        }

        public Criteria andManageNameBetween(String value1, String value2) {
            addCriterion("manage_name between", value1, value2, "manageName");
            return (Criteria) this;
        }

        public Criteria andManageNameNotBetween(String value1, String value2) {
            addCriterion("manage_name not between", value1, value2, "manageName");
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