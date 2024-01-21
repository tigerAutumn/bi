package com.pinting.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsAppMessageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BsAppMessageExample() {
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

        public Criteria andReleasePartIsNull() {
            addCriterion("release_part is null");
            return (Criteria) this;
        }

        public Criteria andReleasePartIsNotNull() {
            addCriterion("release_part is not null");
            return (Criteria) this;
        }

        public Criteria andReleasePartEqualTo(Integer value) {
            addCriterion("release_part =", value, "releasePart");
            return (Criteria) this;
        }

        public Criteria andReleasePartNotEqualTo(Integer value) {
            addCriterion("release_part <>", value, "releasePart");
            return (Criteria) this;
        }

        public Criteria andReleasePartGreaterThan(Integer value) {
            addCriterion("release_part >", value, "releasePart");
            return (Criteria) this;
        }

        public Criteria andReleasePartGreaterThanOrEqualTo(Integer value) {
            addCriterion("release_part >=", value, "releasePart");
            return (Criteria) this;
        }

        public Criteria andReleasePartLessThan(Integer value) {
            addCriterion("release_part <", value, "releasePart");
            return (Criteria) this;
        }

        public Criteria andReleasePartLessThanOrEqualTo(Integer value) {
            addCriterion("release_part <=", value, "releasePart");
            return (Criteria) this;
        }

        public Criteria andReleasePartIn(List<Integer> values) {
            addCriterion("release_part in", values, "releasePart");
            return (Criteria) this;
        }

        public Criteria andReleasePartNotIn(List<Integer> values) {
            addCriterion("release_part not in", values, "releasePart");
            return (Criteria) this;
        }

        public Criteria andReleasePartBetween(Integer value1, Integer value2) {
            addCriterion("release_part between", value1, value2, "releasePart");
            return (Criteria) this;
        }

        public Criteria andReleasePartNotBetween(Integer value1, Integer value2) {
            addCriterion("release_part not between", value1, value2, "releasePart");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andPushCharIsNull() {
            addCriterion("push_char is null");
            return (Criteria) this;
        }

        public Criteria andPushCharIsNotNull() {
            addCriterion("push_char is not null");
            return (Criteria) this;
        }

        public Criteria andPushCharEqualTo(String value) {
            addCriterion("push_char =", value, "pushChar");
            return (Criteria) this;
        }

        public Criteria andPushCharNotEqualTo(String value) {
            addCriterion("push_char <>", value, "pushChar");
            return (Criteria) this;
        }

        public Criteria andPushCharGreaterThan(String value) {
            addCriterion("push_char >", value, "pushChar");
            return (Criteria) this;
        }

        public Criteria andPushCharGreaterThanOrEqualTo(String value) {
            addCriterion("push_char >=", value, "pushChar");
            return (Criteria) this;
        }

        public Criteria andPushCharLessThan(String value) {
            addCriterion("push_char <", value, "pushChar");
            return (Criteria) this;
        }

        public Criteria andPushCharLessThanOrEqualTo(String value) {
            addCriterion("push_char <=", value, "pushChar");
            return (Criteria) this;
        }

        public Criteria andPushCharLike(String value) {
            addCriterion("push_char like", value, "pushChar");
            return (Criteria) this;
        }

        public Criteria andPushCharNotLike(String value) {
            addCriterion("push_char not like", value, "pushChar");
            return (Criteria) this;
        }

        public Criteria andPushCharIn(List<String> values) {
            addCriterion("push_char in", values, "pushChar");
            return (Criteria) this;
        }

        public Criteria andPushCharNotIn(List<String> values) {
            addCriterion("push_char not in", values, "pushChar");
            return (Criteria) this;
        }

        public Criteria andPushCharBetween(String value1, String value2) {
            addCriterion("push_char between", value1, value2, "pushChar");
            return (Criteria) this;
        }

        public Criteria andPushCharNotBetween(String value1, String value2) {
            addCriterion("push_char not between", value1, value2, "pushChar");
            return (Criteria) this;
        }

        public Criteria andPushAbstractIsNull() {
            addCriterion("push_abstract is null");
            return (Criteria) this;
        }

        public Criteria andPushAbstractIsNotNull() {
            addCriterion("push_abstract is not null");
            return (Criteria) this;
        }

        public Criteria andPushAbstractEqualTo(String value) {
            addCriterion("push_abstract =", value, "pushAbstract");
            return (Criteria) this;
        }

        public Criteria andPushAbstractNotEqualTo(String value) {
            addCriterion("push_abstract <>", value, "pushAbstract");
            return (Criteria) this;
        }

        public Criteria andPushAbstractGreaterThan(String value) {
            addCriterion("push_abstract >", value, "pushAbstract");
            return (Criteria) this;
        }

        public Criteria andPushAbstractGreaterThanOrEqualTo(String value) {
            addCriterion("push_abstract >=", value, "pushAbstract");
            return (Criteria) this;
        }

        public Criteria andPushAbstractLessThan(String value) {
            addCriterion("push_abstract <", value, "pushAbstract");
            return (Criteria) this;
        }

        public Criteria andPushAbstractLessThanOrEqualTo(String value) {
            addCriterion("push_abstract <=", value, "pushAbstract");
            return (Criteria) this;
        }

        public Criteria andPushAbstractLike(String value) {
            addCriterion("push_abstract like", value, "pushAbstract");
            return (Criteria) this;
        }

        public Criteria andPushAbstractNotLike(String value) {
            addCriterion("push_abstract not like", value, "pushAbstract");
            return (Criteria) this;
        }

        public Criteria andPushAbstractIn(List<String> values) {
            addCriterion("push_abstract in", values, "pushAbstract");
            return (Criteria) this;
        }

        public Criteria andPushAbstractNotIn(List<String> values) {
            addCriterion("push_abstract not in", values, "pushAbstract");
            return (Criteria) this;
        }

        public Criteria andPushAbstractBetween(String value1, String value2) {
            addCriterion("push_abstract between", value1, value2, "pushAbstract");
            return (Criteria) this;
        }

        public Criteria andPushAbstractNotBetween(String value1, String value2) {
            addCriterion("push_abstract not between", value1, value2, "pushAbstract");
            return (Criteria) this;
        }

        public Criteria andPushTypeIsNull() {
            addCriterion("push_type is null");
            return (Criteria) this;
        }

        public Criteria andPushTypeIsNotNull() {
            addCriterion("push_type is not null");
            return (Criteria) this;
        }

        public Criteria andPushTypeEqualTo(Integer value) {
            addCriterion("push_type =", value, "pushType");
            return (Criteria) this;
        }

        public Criteria andPushTypeNotEqualTo(Integer value) {
            addCriterion("push_type <>", value, "pushType");
            return (Criteria) this;
        }

        public Criteria andPushTypeGreaterThan(Integer value) {
            addCriterion("push_type >", value, "pushType");
            return (Criteria) this;
        }

        public Criteria andPushTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("push_type >=", value, "pushType");
            return (Criteria) this;
        }

        public Criteria andPushTypeLessThan(Integer value) {
            addCriterion("push_type <", value, "pushType");
            return (Criteria) this;
        }

        public Criteria andPushTypeLessThanOrEqualTo(Integer value) {
            addCriterion("push_type <=", value, "pushType");
            return (Criteria) this;
        }

        public Criteria andPushTypeIn(List<Integer> values) {
            addCriterion("push_type in", values, "pushType");
            return (Criteria) this;
        }

        public Criteria andPushTypeNotIn(List<Integer> values) {
            addCriterion("push_type not in", values, "pushType");
            return (Criteria) this;
        }

        public Criteria andPushTypeBetween(Integer value1, Integer value2) {
            addCriterion("push_type between", value1, value2, "pushType");
            return (Criteria) this;
        }

        public Criteria andPushTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("push_type not between", value1, value2, "pushType");
            return (Criteria) this;
        }

        public Criteria andAppPageIsNull() {
            addCriterion("app_page is null");
            return (Criteria) this;
        }

        public Criteria andAppPageIsNotNull() {
            addCriterion("app_page is not null");
            return (Criteria) this;
        }

        public Criteria andAppPageEqualTo(Integer value) {
            addCriterion("app_page =", value, "appPage");
            return (Criteria) this;
        }

        public Criteria andAppPageNotEqualTo(Integer value) {
            addCriterion("app_page <>", value, "appPage");
            return (Criteria) this;
        }

        public Criteria andAppPageGreaterThan(Integer value) {
            addCriterion("app_page >", value, "appPage");
            return (Criteria) this;
        }

        public Criteria andAppPageGreaterThanOrEqualTo(Integer value) {
            addCriterion("app_page >=", value, "appPage");
            return (Criteria) this;
        }

        public Criteria andAppPageLessThan(Integer value) {
            addCriterion("app_page <", value, "appPage");
            return (Criteria) this;
        }

        public Criteria andAppPageLessThanOrEqualTo(Integer value) {
            addCriterion("app_page <=", value, "appPage");
            return (Criteria) this;
        }

        public Criteria andAppPageIn(List<Integer> values) {
            addCriterion("app_page in", values, "appPage");
            return (Criteria) this;
        }

        public Criteria andAppPageNotIn(List<Integer> values) {
            addCriterion("app_page not in", values, "appPage");
            return (Criteria) this;
        }

        public Criteria andAppPageBetween(Integer value1, Integer value2) {
            addCriterion("app_page between", value1, value2, "appPage");
            return (Criteria) this;
        }

        public Criteria andAppPageNotBetween(Integer value1, Integer value2) {
            addCriterion("app_page not between", value1, value2, "appPage");
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

        public Criteria andIsSendIsNull() {
            addCriterion("is_send is null");
            return (Criteria) this;
        }

        public Criteria andIsSendIsNotNull() {
            addCriterion("is_send is not null");
            return (Criteria) this;
        }

        public Criteria andIsSendEqualTo(Integer value) {
            addCriterion("is_send =", value, "isSend");
            return (Criteria) this;
        }

        public Criteria andIsSendNotEqualTo(Integer value) {
            addCriterion("is_send <>", value, "isSend");
            return (Criteria) this;
        }

        public Criteria andIsSendGreaterThan(Integer value) {
            addCriterion("is_send >", value, "isSend");
            return (Criteria) this;
        }

        public Criteria andIsSendGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_send >=", value, "isSend");
            return (Criteria) this;
        }

        public Criteria andIsSendLessThan(Integer value) {
            addCriterion("is_send <", value, "isSend");
            return (Criteria) this;
        }

        public Criteria andIsSendLessThanOrEqualTo(Integer value) {
            addCriterion("is_send <=", value, "isSend");
            return (Criteria) this;
        }

        public Criteria andIsSendIn(List<Integer> values) {
            addCriterion("is_send in", values, "isSend");
            return (Criteria) this;
        }

        public Criteria andIsSendNotIn(List<Integer> values) {
            addCriterion("is_send not in", values, "isSend");
            return (Criteria) this;
        }

        public Criteria andIsSendBetween(Integer value1, Integer value2) {
            addCriterion("is_send between", value1, value2, "isSend");
            return (Criteria) this;
        }

        public Criteria andIsSendNotBetween(Integer value1, Integer value2) {
            addCriterion("is_send not between", value1, value2, "isSend");
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

        public Criteria andPushTimeIsNull() {
            addCriterion("push_time is null");
            return (Criteria) this;
        }

        public Criteria andPushTimeIsNotNull() {
            addCriterion("push_time is not null");
            return (Criteria) this;
        }

        public Criteria andPushTimeEqualTo(Date value) {
            addCriterion("push_time =", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeNotEqualTo(Date value) {
            addCriterion("push_time <>", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeGreaterThan(Date value) {
            addCriterion("push_time >", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("push_time >=", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeLessThan(Date value) {
            addCriterion("push_time <", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeLessThanOrEqualTo(Date value) {
            addCriterion("push_time <=", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeIn(List<Date> values) {
            addCriterion("push_time in", values, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeNotIn(List<Date> values) {
            addCriterion("push_time not in", values, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeBetween(Date value1, Date value2) {
            addCriterion("push_time between", value1, value2, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeNotBetween(Date value1, Date value2) {
            addCriterion("push_time not between", value1, value2, "pushTime");
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