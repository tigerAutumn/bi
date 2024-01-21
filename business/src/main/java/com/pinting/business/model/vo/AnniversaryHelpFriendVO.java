package com.pinting.business.model.vo;

import java.io.Serializable;

/**
 * Author:      cyb
 * Date:        2017/2/23
 * Description: 2017周年庆助力好友信息
 */
public class AnniversaryHelpFriendVO implements Serializable {

    private static final long serialVersionUID = -9178872645630941639L;

    /* 被助力者用户ID */
    private Integer shareUserId;

    /* 微信openId */
    private String openId;

    /* 微信昵称 */
    private String wxNick;

    /* 微信头像 */
    private String wxHeadImg;

    /* 助力元宝个数 */
    private String goldIngotNum;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(Integer shareUserId) {
        this.shareUserId = shareUserId;
    }

    public String getWxNick() {
        return wxNick;
    }

    public void setWxNick(String wxNick) {
        this.wxNick = wxNick;
    }

    public String getWxHeadImg() {
        return wxHeadImg;
    }

    public void setWxHeadImg(String wxHeadImg) {
        this.wxHeadImg = wxHeadImg;
    }

    public String getGoldIngotNum() {
        return goldIngotNum;
    }

    public void setGoldIngotNum(String goldIngotNum) {
        this.goldIngotNum = goldIngotNum;
    }
}
