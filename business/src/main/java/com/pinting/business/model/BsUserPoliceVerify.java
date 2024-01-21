package com.pinting.business.model;

import java.util.Date;

public class BsUserPoliceVerify {
    private Integer id; // 人脸核身验证表ID

    private Integer userId; // 用户编号ID

    private String businessType; // 人脸核身关联业务类型：unbind_bank_card  解绑卡

    private Integer businessId; // 关联业务对应ID：解绑卡业务对应 bs_bank_card.id

    private String verifyResult; // 人脸核身验证结果：success 验证成功  fail 验证失败

    private Integer score; // 分数

    private String serialNo; // 唯一标识流水号

    private String idCardFrontPic; // 身份证正面照

    private String idCardBackPic; // 身份证背面照

    private String livenessPicBlink; // 活体检测图-眨眼

    private String livenessPicSay; // 活体检测图-张嘴

    private String livenessPicRightHead; // 活体检测图-向右摇头

    private String livenessPicLeftHead; // 活体检测图-向左摇头

    private String livenessPicRaiseHead; // 活体检测图-抬头

    private String livenessPicDropHead; // 活体检测图-低头

    private String livenessPicShakeHead; // 活体检测图-摇摇头

    private String checkStatus; // 审核状态：UNCHECKED 待审核            PASS 已通过            REFUSE 未通过

    private Integer checker; // 审核人ID

    private Date checkTime; // 审核时间

    private String checkDesc; // 审核结果描述

    private String note; // 备注（验证结果信息备注）

    private Date createTime; // 创建时间

    private Date updateTime; // 更新时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(String verifyResult) {
        this.verifyResult = verifyResult;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getIdCardFrontPic() {
        return idCardFrontPic;
    }

    public void setIdCardFrontPic(String idCardFrontPic) {
        this.idCardFrontPic = idCardFrontPic;
    }

    public String getIdCardBackPic() {
        return idCardBackPic;
    }

    public void setIdCardBackPic(String idCardBackPic) {
        this.idCardBackPic = idCardBackPic;
    }

    public String getLivenessPicBlink() {
        return livenessPicBlink;
    }

    public void setLivenessPicBlink(String livenessPicBlink) {
        this.livenessPicBlink = livenessPicBlink;
    }

    public String getLivenessPicSay() {
        return livenessPicSay;
    }

    public void setLivenessPicSay(String livenessPicSay) {
        this.livenessPicSay = livenessPicSay;
    }

    public String getLivenessPicRightHead() {
        return livenessPicRightHead;
    }

    public void setLivenessPicRightHead(String livenessPicRightHead) {
        this.livenessPicRightHead = livenessPicRightHead;
    }

    public String getLivenessPicLeftHead() {
        return livenessPicLeftHead;
    }

    public void setLivenessPicLeftHead(String livenessPicLeftHead) {
        this.livenessPicLeftHead = livenessPicLeftHead;
    }

    public String getLivenessPicRaiseHead() {
        return livenessPicRaiseHead;
    }

    public void setLivenessPicRaiseHead(String livenessPicRaiseHead) {
        this.livenessPicRaiseHead = livenessPicRaiseHead;
    }

    public String getLivenessPicDropHead() {
        return livenessPicDropHead;
    }

    public void setLivenessPicDropHead(String livenessPicDropHead) {
        this.livenessPicDropHead = livenessPicDropHead;
    }

    public String getLivenessPicShakeHead() {
        return livenessPicShakeHead;
    }

    public void setLivenessPicShakeHead(String livenessPicShakeHead) {
        this.livenessPicShakeHead = livenessPicShakeHead;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Integer getChecker() {
        return checker;
    }

    public void setChecker(Integer checker) {
        this.checker = checker;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckDesc() {
        return checkDesc;
    }

    public void setCheckDesc(String checkDesc) {
        this.checkDesc = checkDesc;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}