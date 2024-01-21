package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 人脸核身验证上传检测图片
 * Created by zousheng on 2018/05/25.
 */
public class ReqMsg_Bank_UploadPicPoliceVerify extends ReqMsg {

    private static final long serialVersionUID = 123281681207882190L;
    private Integer userId; // 用户ID
    private String  serialNo; // 图片编号唯一标识（对应人脸核身验证记录）
    private String idCardFrontPic; // 身份证正面照
    private String idCardBackPic; // 身份证背面照
    private String livenessPicBlink; // 活体检测图-眨眼
    private String livenessPicSay; // 活体检测图-张嘴
    private String livenessPicRightHead; // 活体检测图-向右摇头
    private String livenessPicLeftHead; // 活体检测图-向左摇头
    private String livenessPicRaiseHead; // 活体检测图-抬头
    private String livenessPicDropHead; // 活体检测图-低头
    private String livenessPicShakeHead; // 活体检测图-摇摇头

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
}
