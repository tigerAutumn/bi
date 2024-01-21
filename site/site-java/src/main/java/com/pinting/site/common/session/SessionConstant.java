package com.pinting.site.common.session;

import com.pinting.util.Constants;

/**
 * 分布式session常量
 * Created by cyb on 2018/2/24.
 */

public class SessionConstant {

    public static final String USER_SESSION_KEY = "user_id";//用户信息的key

    public static final String JSESSIONID = "session_id"; //jsessionid的key

    public static final String SESSION_KEY = "session"; //分布式sessionId

    public static final String getGroupSessionKey () {
        return Constants.REDIS_SUBSYS_SITE + "|" + SESSION_KEY;
    }

}