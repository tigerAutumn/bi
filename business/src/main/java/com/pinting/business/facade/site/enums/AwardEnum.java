/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.facade.site.enums;

/**
 * 
 * @author HuanXiong
 * @version $Id: SupremeAwardEnum.java, v 0.1 2016-1-22 下午4:29:06 HuanXiong Exp $
 */
public enum AwardEnum {

    // 至尊钻蛋 奖品
    SUPREME_AWARD_FIRST_CLASS(24, "iphone 6s", null),
    SUPREME_AWARD_SECOND_CLASS(23, "iphone 6", null),
    SUPREME_AWARD_THIRD_CLASS(22, "ipad mini 4", null),
    SUPREME_AWARD_FOURTH_CLASS_1(12, "200元面值京东卡", null),
    SUPREME_AWARD_FOURTH_CLASS_2(13, "400元面值京东卡", null),
    SUPREME_AWARD_FOURTH_CLASS_3(14, "600元面值京东卡", null),
    SUPREME_AWARD_FOURTH_CLASS_4(15, "800元面值京东卡", null),
    SUPREME_AWARD_FOURTH_CLASS_5(16, "1000元面值京东卡", null),
    SUPREME_AWARD_FOURTH_CLASS_6(17, "1200元面值京东卡", null),
    SUPREME_AWARD_FOURTH_CLASS_7(18, "1400元面值京东卡", null),
    SUPREME_AWARD_FOURTH_CLASS_8(19, "1600元面值京东卡", null),
    SUPREME_AWARD_FOURTH_CLASS_9(20, "1800元面值京东卡", null),
    SUPREME_AWARD_FOURTH_CLASS_10(21, "2000元面值京东卡", null);
    
    private Integer id;
    
    private String content;

    private Double odds; // 概率

    private AwardEnum(Integer id, String content, Double odds) {
        this.id = id;
        this.content = content;
        this.odds = odds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getOdds() {
        return odds;
    }

    public void setOdds(Double odds) {
        this.odds = odds;
    }
}
