package com.pinting.business.model.vo;

/**
 * Author:      cyb
 * Date:        2017/6/26
 * Description:
 */
public class ActivityTeamInfoVO extends ActivityBaseVO {

    /* 团队号 */
    private String teamName;

    /* 团队ID */
    private Integer teamId;

    /* 参与状态 */
    private String status;

    /* 满员人数 */
    private Integer fullNumber;

    /* 抱团人数 */
    private Integer currentNumber;

    /* 团队序号 */
    private Integer serial;

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getFullNumber() {
        return fullNumber;
    }

    public void setFullNumber(Integer fullNumber) {
        this.fullNumber = fullNumber;
    }

    public Integer getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(Integer currentNumber) {
        this.currentNumber = currentNumber;
    }
}
