package com.pinting.open.pojo.response.index;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.index.Notice;

import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/6/30
 * Description: 首页消息信息
 */
public class IndexMessageResponse extends Response {

    /* 公告列表 */
    private List<Notice> noticeList;

    /* 活动中心提示红点-是否已读 */
    private String activityIsRead;

    public List<Notice> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(List<Notice> noticeList) {
        this.noticeList = noticeList;
    }

    public String getActivityIsRead() {
        return activityIsRead;
    }

    public void setActivityIsRead(String activityIsRead) {
        this.activityIsRead = activityIsRead;
    }
}
