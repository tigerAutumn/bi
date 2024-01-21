package com.pinting.business.model.vo;

/**
 * 优惠券（红包、加息券）到期提醒VO
 *
 * @author shh
 * @date 2018/5/26 16:53
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public class TicketReminderNotifyVO {

    /* 用户编号 */
    private Integer userId;

    /* 用户注册手机号 */
    private String mobile;

    /* 即将到期的优惠券数量 */
    private Integer ticketCount;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(Integer ticketCount) {
        this.ticketCount = ticketCount;
    }
}
