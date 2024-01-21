package com.pinting.business.model.vo;

import java.util.List;

/**
 * Created by cyb on 2017/11/5.
 */
public class ActivityLuckyNumberVO extends ActivityBaseVO {

    /* 每日幸运号 */
    private List<KeyValue> luckyNumber;

    /* 每日投资幸运号列表 */
    private List<ActivityLuckyNumber> luckyNumberList;

    /* 等待公布。YES-需要等待；NO-无需等待 */
    private String waitPublish;

    public String getWaitPublish() {
        return waitPublish;
    }

    public void setWaitPublish(String waitPublish) {
        this.waitPublish = waitPublish;
    }

    public List<KeyValue> getLuckyNumber() {
        return luckyNumber;
    }

    public void setLuckyNumber(List<KeyValue> luckyNumber) {
        this.luckyNumber = luckyNumber;
    }


    public List<ActivityLuckyNumber> getLuckyNumberList() {
        return luckyNumberList;
    }

    public void setLuckyNumberList(List<ActivityLuckyNumber> luckyNumberList) {
        this.luckyNumberList = luckyNumberList;
    }
}
