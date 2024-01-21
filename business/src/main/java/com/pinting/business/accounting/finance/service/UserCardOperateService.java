package com.pinting.business.accounting.finance.service;

import com.pinting.business.hessian.site.message.ReqMsg_Bank_UnbindApplyPoliceVerify;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_UploadPicPoliceVerify;
import com.pinting.business.hessian.site.message.ResMsg_Bank_UnbindApplyPoliceVerify;
import com.pinting.business.hessian.site.message.ResMsg_Bank_UploadPicPoliceVerify;
import com.pinting.business.model.vo.UnbindCheckResVO;

/**
 * Created by babyshark on 2016/9/9.
 */
public interface UserCardOperateService {

    /**
     * 预绑卡
     * @param userName      银行卡持有人姓名
     * @param idCard        身份证
     * @param cardNo        银行卡号
     * @param mobile        预留手机号
     * @param bankId        银行ID
     * @param userId        用户ID
     * @param terminalType  终端类型（1-H5端；2-PC端；3-Android端；4-iOS端）
     */
    String preBindCard(String userName, String idCard, String cardNo, String mobile, String bankId, String userId, String terminalType);

    /**
     * 正式绑卡
     * @param bgwOrderNo    币港湾绑卡订单号
     * @param smsCode       验证码
     * @param userId        用户ID
     */
    void bindCard(String bgwOrderNo, String smsCode, String userId);

    /**
     * 解绑卡
     * @param userId 用户编号
     * @param cardNo 卡号
     */
    void unBindCard(Integer userId, String cardNo);

    /**
     * 批量绑卡
     */
    void batchBindCard(String mobile, String userName, String idCard, String cardNo);

    /**
     * 云贷解绑卡
     */
    void yunUnBindCard(Integer lnUserId);
    
    /**
     * 解绑前校验
     * @author bianyatian
     * @param userId
     * @param bankCardId
     */
    UnbindCheckResVO unBindCheck(Integer userId,Integer bankCardId);

    /**
     * 解绑卡申请——人脸核身验证
     * @param req
     * @param res
     * @return
     */
    void unbindApplyPoliceVerify(ReqMsg_Bank_UnbindApplyPoliceVerify req, ResMsg_Bank_UnbindApplyPoliceVerify res);

    /**
     * 人脸核身验证上传检测图片
     * @param req
     * @param res
     */
    void uploadPicPoliceVerify(ReqMsg_Bank_UploadPicPoliceVerify req, ResMsg_Bank_UploadPicPoliceVerify res);
}
