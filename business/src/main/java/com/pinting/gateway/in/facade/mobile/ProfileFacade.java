package com.pinting.gateway.in.facade.mobile;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.ReqMsg_Profile_PasswordModify;
import com.pinting.business.hessian.site.message.ReqMsg_Profile_PayPasswordModify;
import com.pinting.business.hessian.site.message.ResMsg_Profile_PasswordModify;
import com.pinting.business.hessian.site.message.ResMsg_Profile_PayPasswordModify;
import com.pinting.business.model.BsUser;
import com.pinting.business.service.site.UserService;
import com.pinting.core.util.encrypt.MD5Util;
import com.pinting.gateway.in.util.InterfaceVersion;

/**
 * @Project: business
 * @Title: ProfileFacade.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:44:29
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("appProfile")
public class ProfileFacade {
    @Autowired
    private UserService  userService;

    // 修改登录密码
    @InterfaceVersion("PasswordModify/1.0.0")
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
    @InterfaceVersion("PayPasswordModify/1.0.0")
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
        bsUser.setPayPassword(MD5Util.encryptMD5(req.getNewPayPassWord() + MD5Util.getEncryptkey()));
        bsUser.setPayFailTimes(0);
        userService.updateBsUser(bsUser);

    }
}
