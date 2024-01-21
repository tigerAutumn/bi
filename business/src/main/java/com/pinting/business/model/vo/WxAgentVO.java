package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 公众号渠道管理VO
 *
 * @author shh
 * @date 2018/6/15 9:21
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public class WxAgentVO {

    /* 渠道编号 */
    private Integer wxAgentId;

    /* 渠道名称 */
    private String agentName;

    /* 关注人数 */
    private Integer followersCount;

    /* 取消关注人数 */
    private Integer unFollowersCount;

    /* 浏览人数 */
    private Integer pageViewTimes;

    /* 净关注人数 */
    private Integer netFollowersCount;

    /* 微信昵称 */
    private String nick;

    /* 性别，值为1时是男性，值为2时是女性，值为0时是未知  */
    private String sex;

    /* 省份 */
    private String province;

    /* 关注时间 */
    private Date followTime;

    public Integer getWxAgentId() {
        return wxAgentId;
    }

    public void setWxAgentId(Integer wxAgentId) {
        this.wxAgentId = wxAgentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    public Integer getUnFollowersCount() {
        return unFollowersCount;
    }

    public void setUnFollowersCount(Integer unFollowersCount) {
        this.unFollowersCount = unFollowersCount;
    }

    public Integer getPageViewTimes() {
        return pageViewTimes;
    }

    public void setPageViewTimes(Integer pageViewTimes) {
        this.pageViewTimes = pageViewTimes;
    }

    public Integer getNetFollowersCount() {
        return netFollowersCount;
    }

    public void setNetFollowersCount(Integer netFollowersCount) {
        this.netFollowersCount = netFollowersCount;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Date getFollowTime() {
        return followTime;
    }

    public void setFollowTime(Date followTime) {
        this.followTime = followTime;
    }
}
