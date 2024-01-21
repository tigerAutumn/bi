package com.pinting.open.pojo.response.asset;

import com.pinting.open.base.response.Response;

/**
 * 解绑卡申请——人脸核身验证
 * Created by zousheng on 2018/05/25.
 */
public class UnbindApplyPoliceVerifyResponse extends Response {

    private String scoreVerifyResult; // score分数审核结果  通过：true，失败：false
    private Integer residueDegree; // 剩余可验证次数
    private String  serialNo;   // 图片编号唯一标识（对应批次）

    public Integer getResidueDegree() {
        return residueDegree;
    }

    public void setResidueDegree(Integer residueDegree) {
        this.residueDegree = residueDegree;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getScoreVerifyResult() {
        return scoreVerifyResult;
    }

    public void setScoreVerifyResult(String scoreVerifyResult) {
        this.scoreVerifyResult = scoreVerifyResult;
    }
}
