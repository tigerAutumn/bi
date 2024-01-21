package com.pinting.business.model.vo;

import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/2/23
 * Description: 2017周年庆分享页面信息
 */
public class AnniversarySharePageInfoVO {

    /* 是否助力过 */
    private String isJoined;

    /* 分享者元宝个数 */
    private Integer goldIngotNum;

    /* 助力好友列表 */
    private List<AnniversaryHelpFriendVO> friendList;

    /* 已解锁元宝个数 */
    private Integer unlockedNum;

    public Integer getUnlockedNum() {
        return unlockedNum;
    }

    public void setUnlockedNum(Integer unlockedNum) {
        this.unlockedNum = unlockedNum;
    }

    public Integer getGoldIngotNum() {
        return goldIngotNum;
    }

    public void setGoldIngotNum(Integer goldIngotNum) {
        this.goldIngotNum = goldIngotNum;
    }

    public List<AnniversaryHelpFriendVO> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<AnniversaryHelpFriendVO> friendList) {
        this.friendList = friendList;
    }

    public String getIsJoined() {
        return isJoined;
    }

    public void setIsJoined(String isJoined) {
        this.isJoined = isJoined;
    }
}
