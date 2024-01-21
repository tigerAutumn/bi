package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/2/27
 * Description: 2017周年庆分享页面信息
 */
public class ResMsg_Activity_AnniversarySharePageInfo extends ResMsg {

    private static final long serialVersionUID = -2789768310514648993L;

    /* 分享者元宝个数 */
    private Integer goldIngotNum;

    /* 是否已经助力 */
    private String isJoined;

    /* 助力好友列表 */
    private List<HashMap<String, Object>> friendList;

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

    public List<HashMap<String, Object>> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<HashMap<String, Object>> friendList) {
        this.friendList = friendList;
    }

    public String getIsJoined() {
        return isJoined;
    }

    public void setIsJoined(String isJoined) {
        this.isJoined = isJoined;
    }
}
