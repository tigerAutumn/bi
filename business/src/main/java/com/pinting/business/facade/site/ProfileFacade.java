package com.pinting.business.facade.site;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.ReqMsg_Profile_EmailBind;
import com.pinting.business.hessian.site.message.ReqMsg_Profile_MobileBind;
import com.pinting.business.hessian.site.message.ReqMsg_Profile_PasswordModify;
import com.pinting.business.hessian.site.message.ReqMsg_Profile_PayPasswordModify;
import com.pinting.business.hessian.site.message.ReqMsg_Profile_RealNameBind;
import com.pinting.business.hessian.site.message.ReqMsg_Profile_UrgentBind;
import com.pinting.business.hessian.site.message.ResMsg_Profile_EmailBind;
import com.pinting.business.hessian.site.message.ResMsg_Profile_MobileBind;
import com.pinting.business.hessian.site.message.ResMsg_Profile_PasswordModify;
import com.pinting.business.hessian.site.message.ResMsg_Profile_PayPasswordModify;
import com.pinting.business.hessian.site.message.ResMsg_Profile_RealNameBind;
import com.pinting.business.hessian.site.message.ResMsg_Profile_UrgentBind;
import com.pinting.business.model.BsUser;
import com.pinting.business.service.site.EmailService;
import com.pinting.business.service.site.SMSService;
import com.pinting.business.service.site.UserService;
import com.pinting.core.util.encrypt.MD5Util;

/**
 * @Project: business
 * @Title: ProfileFacade.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:44:29
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("Profile")
public class ProfileFacade {
    @Autowired
    private UserService  userService;
    @Autowired
    private SMSService   smsService;
    @Autowired
    private EmailService emailService;

    // 修改绑定手机
    public void mobileBind(ReqMsg_Profile_MobileBind req, ResMsg_Profile_MobileBind resp) {
        BsUser bsUser = userService.findUserByMobile(req.getOldMobile());
        if (bsUser == null) {
            throw new PTMessageException(PTMessageEnum.MOBILE_NO_EXIT);
        }
        if (userService.findUserByMobile(req.getNewMobile()) != null) {
            throw new PTMessageException(PTMessageEnum.MOBILE_IS_EXIT);
        }
        try {
            smsService.validateIdentity(req.getOldMobile(), req.getOldMobileCode(), false);
        } catch (PTMessageException e) {
            if (PTMessageEnum.MOBILE_CODE_WRONG_ERROR.getCode().equals(e.getErrCode())) {
                throw new PTMessageException(PTMessageEnum.MOBILE_OLD_CODE_WRONG_ERROR);
            }
            throw e;
        }
        try {
            smsService.validateIdentity(req.getNewMobile(), req.getNewMobileCode(), false);
        } catch (PTMessageException e) {
            if (PTMessageEnum.MOBILE_CODE_WRONG_ERROR.getCode().equals(e.getErrCode())) {
                throw new PTMessageException(PTMessageEnum.MOBILE_NEW_CODE_WRONG_ERROR);
            }
            throw e;
        }
        smsService.validateIdentity(req.getOldMobile(), req.getOldMobileCode());
        smsService.validateIdentity(req.getNewMobile(), req.getNewMobileCode());
        bsUser.setMobile(req.getNewMobile());
        userService.updateBsUser(bsUser);
    }

    // 绑定邮箱
    public void emailBind(ReqMsg_Profile_EmailBind req, ResMsg_Profile_EmailBind resp) {
        BsUser bsUser = null;
        if (req.getFlag()) {//新增绑定
            bsUser = userService.findUserByEmail(req.getEmail());
            if (bsUser != null) {
                throw new PTMessageException(PTMessageEnum.EMAIL_IS_EXIT);
            }
            bsUser = userService.findUserById(req.getUserID());
            try {
                emailService.validateIdentity(req.getEmail(), req.getEmailCode(), false);
            } catch (PTMessageException e) {
                throw e;
            }
            emailService.validateIdentity(req.getEmail(), req.getEmailCode());
            bsUser.setEmail(req.getEmail());
        } else {//修改绑定
            bsUser = userService.findUserByEmail(req.getNewEmail());
            if (bsUser != null) {
                throw new PTMessageException(PTMessageEnum.EMAIL_IS_EXIT);
            }
            bsUser = userService.findUserById(req.getUserID());
            if (!bsUser.getEmail().equals(req.getOldEmail())) {
                throw new PTMessageException(PTMessageEnum.USER_EMAIL_NOT_MATCH);
            }
            try {
                emailService.validateIdentity(req.getOldEmail(), req.getOldEmailCode(), false);
            } catch (PTMessageException e) {
                if (PTMessageEnum.EMAIL_CODE_WRONG_ERROR.getCode().equals(e.getErrCode())) {
                    throw new PTMessageException(PTMessageEnum.EMAIL_OLD_CODE_WRONG_ERROR);
                }
                throw e;
            }
            try {
                emailService.validateIdentity(req.getNewEmail(), req.getNewEmailCode(), false);
            } catch (PTMessageException e) {
                if (PTMessageEnum.EMAIL_CODE_WRONG_ERROR.getCode().equals(e.getErrCode())) {
                    throw new PTMessageException(PTMessageEnum.EMAIL_NEW_CODE_WRONG_ERROR);
                }
                throw e;
            }
            emailService.validateIdentity(req.getOldEmail(), req.getOldEmailCode());
            emailService.validateIdentity(req.getNewEmail(), req.getNewEmailCode());
            bsUser.setEmail(req.getNewEmail());
        }
        userService.updateBsUser(bsUser);

    }

    // 紧急联系人
    public void urgentBind(ReqMsg_Profile_UrgentBind req, ResMsg_Profile_UrgentBind resp) {
        BsUser bsUser = userService.findUserById(req.getUserId());
        if (!bsUser.getPayPassword().equals(
            MD5Util.encryptMD5(req.getPayPassWord() + MD5Util.getEncryptkey()))) {
            throw new PTMessageException(PTMessageEnum.USER_PAY_PASS_ERROR);
        }
        if (req.getUrgentMobile().equals(bsUser.getMobile())) {
            throw new PTMessageException(PTMessageEnum.MOBILE_IS_SAME_ERROR);
        }
        bsUser.setUrgentName(req.getUrgentName());
        bsUser.setUrgentMobile(req.getUrgentMobile());
        bsUser.setRelation(req.getRelation());
        userService.updateBsUser(bsUser);

    }

    // 实名认证
    public void realNameBind(ReqMsg_Profile_RealNameBind req, ResMsg_Profile_RealNameBind resp) {
        BsUser bsUser = new BsUser();
        bsUser.setId(req.getUserId());
        bsUser.setUserName(req.getUserName());
        bsUser.setIdCard(req.getIdCard());
        userService.updateBsUser(bsUser);

    }

    // 修改登录密码
    public void passwordModify(ReqMsg_Profile_PasswordModify req, ResMsg_Profile_PasswordModify resp) {
        BsUser bsUser = userService.findUserById(req.getUserId());
        if(req.getNewPassWord().equals(req.getOldPassWord())){
            throw new PTMessageException(PTMessageEnum.PASS_LOGIN_PASS_SAME_ERROR);
        }
        if (!bsUser.getPassword().equals(
            MD5Util.encryptMD5(req.getOldPassWord() + MD5Util.getEncryptkey()))) {
            throw new PTMessageException(PTMessageEnum.USER_OLD_PASS_ERROR);
        }
        if (!StringUtils.isBlank(bsUser.getPayPassword())) {
            if (bsUser.getPayPassword().equals(
                MD5Util.encryptMD5(req.getNewPassWord() + MD5Util.getEncryptkey()))) {
                throw new PTMessageException(PTMessageEnum.PAY_LOGIN_PASS_SAME_ERROR);
            }
        }
        bsUser.setPassword(MD5Util.encryptMD5(req.getNewPassWord() + MD5Util.getEncryptkey()));
        bsUser.setLoginFailTimes(0);
        userService.updateBsUser(bsUser);

    }

    // 修改支付密码
    public void payPasswordModify(ReqMsg_Profile_PayPasswordModify req,
                                  ResMsg_Profile_PayPasswordModify resp) {
        BsUser bsUser = userService.findUserById(req.getUserId());
        if (!bsUser.getPayPassword().equals(
            MD5Util.encryptMD5(req.getOldPayPassWord() + MD5Util.getEncryptkey()))) {
            throw new PTMessageException(PTMessageEnum.USER_OLD_PAY_PASS_ERROR);
        }
        if (bsUser.getPassword().equals(
            MD5Util.encryptMD5(req.getNewPayPassWord() + MD5Util.getEncryptkey()))) {
            throw new PTMessageException(PTMessageEnum.PASS_LOGIN_PAY_SAME_ERROR);
        }
        if (bsUser.getPayPassword().equals(
        		MD5Util.encryptMD5(req.getNewPayPassWord() + MD5Util.getEncryptkey()))) {
        	throw new PTMessageException(PTMessageEnum.PAY_LOGIN_PAY_SAME_ERROR);
        }
        bsUser
            .setPayPassword(MD5Util.encryptMD5(req.getNewPayPassWord() + MD5Util.getEncryptkey()));
        bsUser.setPayFailTimes(0);
        userService.updateBsUser(bsUser);

    }
}
