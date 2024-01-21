package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;
import java.util.List;

/**
 * 解绑卡申请——人脸核身验证
 * Created by zousheng on 2018/05/25.
 */
public class ResMsg_Bank_UnbindApplyPoliceVerify extends ResMsg {

    private static final long serialVersionUID = 4489737385109580690L;

    private Boolean scoreVerifyResult; // score分数审核结果  通过：true，失败：false
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

    public Boolean getScoreVerifyResult() {
        return scoreVerifyResult;
    }

    public void setScoreVerifyResult(Boolean scoreVerifyResult) {
        this.scoreVerifyResult = scoreVerifyResult;
    }
}
