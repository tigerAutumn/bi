package com.pinting.business.model;

public class BsBonus {
    private Integer id;

    private Integer userId;

    private Integer beRecommendUserId;

    private Double bonus;

    private String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBeRecommendUserId() {
        return beRecommendUserId;
    }

    public void setBeRecommendUserId(Integer beRecommendUserId) {
        this.beRecommendUserId = beRecommendUserId;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}