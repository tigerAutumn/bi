package com.pinting.site.common.session;

import com.pinting.core.cookie.CookieManager;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.util.Constants;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cyb on 2018/2/24.
 */
@Component
public class SessionService {

    private final static Logger LOG = LoggerFactory.getLogger(SessionService.class);

    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_SITE);

    @SuppressWarnings("unchecked")
    public Map<String, Object> getSession(HttpServletRequest request) {
        CookieManager cookieManager = new CookieManager(request);
        String sessionId = cookieManager.getValue(CookieEnums._SITE_SESSION.getName(), SessionConstant.JSESSIONID, false);
        Map<String, Object> session = null;
        try {
            if(StringUtil.isNotBlank(sessionId)) {
                List<String> sessionList = jsClientDaoSupport.lrange(SessionConstant.getGroupSessionKey());
                if(!CollectionUtils.isEmpty(sessionList)) {
                    for (String obj: sessionList) {
                        JSONObject json = JSONObject.fromObject(obj);
                        if(json.get(SessionConstant.JSESSIONID).equals(sessionId)) {
                            return json;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Redis获取session异常" + e.getMessage(), e.getCause());
        }

        return session;
    }

    public Map<String, Object> saveSession(HttpServletRequest request, HttpServletResponse response, Integer userId) {
        CookieManager cookieManager = new CookieManager(request);
        String sid = SessionStringUtil.getUuid();
        cookieManager.setValue(CookieEnums._SITE_SESSION.getName(), SessionConstant.JSESSIONID, sid, false);
        cookieManager.save(response, CookieEnums._SITE_SESSION.getName(), null, "/", -1, false);
        Map<String, Object> session = new HashMap<>();
        session.put(SessionConstant.USER_SESSION_KEY, userId);
        session.put(SessionConstant.JSESSIONID, sid);
        try {
            jsClientDaoSupport.rpush(SessionConstant.getGroupSessionKey(), JSONObject.fromObject(session).toString());
        } catch (Exception e) {
            LOG.error("Redis保存session异常" + e.getMessage(), e.getCause());
        }
        return session;
    }

    public void removeSession(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> session = getSession(request);
            jsClientDaoSupport.lrem(SessionConstant.getGroupSessionKey(), JSONObject.fromObject(session).toString(), 0);
        } catch (Exception e) {
            LOG.error("Redis删除session异常" + e.getMessage(), e.getCause());
        }
        CookieManager cookieManager = new CookieManager(request);
        cookieManager.setValue(CookieEnums._SITE_SESSION.getName(), SessionConstant.JSESSIONID, null, false);
        cookieManager.save(response, CookieEnums._SITE_SESSION.getName(), null, "/", 0, false);
    }
}
