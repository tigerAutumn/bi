package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

/**
 * 解绑卡申请——人脸核身验证
 * Created by zousheng on 2018/05/25.
 */
public class UnbindApplyPoliceVerifyRequest extends Request {

    private Integer userId; // 用户ID
    private Integer  bankId; // 解绑卡ID
    private String  verifyResult; // 人脸核身验证结果：success 验证成功 fail 验证失败
    private Integer score; // 分数
    private String note; // verify_result=fail，传入失败描述
    private String idCardVerifyResult; // true 身份证验证成功， false 身份证验证失败

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getIdCardVerifyResult() {
        return idCardVerifyResult;
    }

    public void setIdCardVerifyResult(String idCardVerifyResult) {
        this.idCardVerifyResult = idCardVerifyResult;
    }

    @Override
    public String restApiUrl() {
        return  Constants.BASE_REST_URL + "/mobile/asset/unbind_apply_police_verify";
    }

    @Override
    public String testApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/asset/unbind_apply_police_verify";
    }
}
