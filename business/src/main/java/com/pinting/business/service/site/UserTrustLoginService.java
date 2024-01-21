package com.pinting.business.service.site;

import com.pinting.business.hessian.site.message.ReqMsg_User_TrustLogin;
import com.pinting.business.model.BsUser;

/**
 * Created by babyshark on 2017/7/29.
 */
public interface UserTrustLoginService {

    /**
     * 信任免登（用户不存在则自动注册）
     * @param req
     * @return 用户编号、手机号、用户类型、渠道编号
     */
    BsUser trustLogin(ReqMsg_User_TrustLogin req);
}
