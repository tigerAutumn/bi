package com.pinting.gateway.bird.in.interceptor;

import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 剑钊 on 2016/8/10.
 */
@Deprecated
public class AccessTokenInterceptor extends HandlerInterceptorAdapter {


    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_GATEWAY);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        return checkToken(request, response);
    }

    private boolean checkToken(HttpServletRequest request, HttpServletResponse response) {

        String accessToken = request.getParameter("token");
        try {
            String birdAccessToken = jsClientDaoSupport.getString("bird_access_token");

            if (StringUtils.isNotBlank(accessToken) && accessToken.equals(birdAccessToken)) {
                return true;
            } else {
                return true;
                //TODO: throw new PTMessageException(PTMessageEnum.TOKEN_EXPIRE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof PTMessageException){
                throw (PTMessageException) e;
            }
            throw new PTMessageException(PTMessageEnum.SYSTEM_ERROR);
        }

    }

}
