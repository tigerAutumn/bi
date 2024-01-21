package com.pinting.business.service.site;

import com.pinting.business.model.BsUserMessageRead;

/**
 * Author:      cyb
 * Date:        2017/6/20
 * Description: 用户消息阅读记录：目的是控制各个消息提示红点
 */
public interface UserMessageReadService {

    /**
     * 根据位置查询阅读记录
     * @param userId    用户ID
     * @param position  位置：公告：NOTICE；活动：ACTIVITY
     * @return
     */
    BsUserMessageRead queryByPosition(Integer userId, String position);

    /**
     * 更新阅读时间
     * @param id    编号
     */
    BsUserMessageRead updateMessageRead(Integer id);

    /**
     * 新增用户消息阅读记录
     * @param userId    用户ID
     * @param position  位置：公告：NOTICE；活动：ACTIVITY
     * @return
     */
    BsUserMessageRead addMessageRead(Integer userId, String position);

}
