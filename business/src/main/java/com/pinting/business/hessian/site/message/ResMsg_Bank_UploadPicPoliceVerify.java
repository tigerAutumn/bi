package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 人脸核身验证上传检测图片
 * Created by zousheng on 2018/05/25.
 */
public class ResMsg_Bank_UploadPicPoliceVerify extends ResMsg {

    private static final long serialVersionUID = 6007141241098197837L;
    private Boolean uploadResult; // 上传结果 true 成功， false 失败

    public Boolean getUploadResult() {
        return uploadResult;
    }

    public void setUploadResult(Boolean uploadResult) {
        this.uploadResult = uploadResult;
    }
}
