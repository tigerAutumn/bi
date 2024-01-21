package com.pinting.manage.service.impl;

import com.pinting.business.dao.MUserMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.MUser;
import com.pinting.core.util.encrypt.MD5Util;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
import com.pinting.manage.controller.LoginController;
import com.pinting.manage.service.MUserService;
import com.pinting.util.GeneratePasswordUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "mUserManageService")
public class MUserServiceImpl implements MUserService {

    @Autowired
    private MUserMapper userMapper;

    @Autowired
    private SMSServiceClient smsServiceClient;

    @Override
    public void resetPassword(Integer id) {
        MUser user = new MUser();
        //1、重置密码为8-16位包含数字，大写字母，小写字母，特殊字符的密码
        String newPsd = GeneratePasswordUtil.generatePassword();
        user.setPassword(MD5Util.encryptMD5(newPsd + MD5Util.getEncryptkey()));
        user.setId(id);
        user.setStatus(1);
        user.setLoginFailTimes(0);
        userMapper.updateByPrimaryKeySelective(user);
        //2、重置密码的用户有手机号码 则发送短信
        MUser mUser = userMapper.selectByPrimaryKey(id);
        if (mUser == null) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
        if (StringUtils.isNotBlank(mUser.getMobile())) {
            smsServiceClient.sendTemplateMessage(mUser.getMobile(), TemplateKey.MANAGER_RESET_PASSWORD, String.valueOf(newPsd));
        }
    }

    @Override
    public void freezeMUser(Integer id) {
        MUser user = new MUser();
        user.setStatus(LoginController.LOGIN_STATUS_3);
        user.setId(id);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void UnfreezeMUser(Integer id) {
        MUser user = new MUser();
        user.setId(id);
        user.setStatus(1);
        user.setLoginFailTimes(0);
        userMapper.updateByPrimaryKeySelective(user);
    }
}
