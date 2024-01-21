package com.pinting.mall.model;

public class MallCommodityInfoWithBLOBs extends MallCommodityInfo {
    private String commDetails; // 商品介绍

    private String exchangeNote; // 兑换需知

    public String getCommDetails() {
        return commDetails;
    }

    public void setCommDetails(String commDetails) {
        this.commDetails = commDetails;
    }

    public String getExchangeNote() {
        return exchangeNote;
    }

    public void setExchangeNote(String exchangeNote) {
        this.exchangeNote = exchangeNote;
    }
}