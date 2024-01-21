package com.pinting.open.pojo.response.asset;

import com.pinting.open.base.response.Response;

/**
 * 人脸核身验证上传检测图片
 * Created by zousheng on 2018/05/25.
 */
public class UploadPicPoliceVerifyResponse extends Response {

    private String uploadResult; // 上传结果 true 成功， false 失败

    public String getUploadResult() {
        return uploadResult;
    }

    public void setUploadResult(String uploadResult) {
        this.uploadResult = uploadResult;
    }
}
