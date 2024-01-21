package com.pinting.manage.controller;

import com.pinting.business.service.manage.CacheService;
import com.pinting.business.util.Constants;
import com.pinting.core.base.BaseController;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.util.ReturnDWZAjax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统缓存管理Controller
 *
 * @author zousheng
 * @date 2018-05-04
 */
@Controller
@RequestMapping("/sys/redisCache")
public class SysCacheController extends BaseController {

    @Autowired
    private CacheService cacheService;

    /**
     * 进入系统缓存管理页
     *
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Map<String, Object> model) {
        model.put("dataGrid", cacheService.findCacheKeyList());
        return "cache/index";
    }

    /**
     * 刷新缓存key
     *
     * @param cacheKeys
     * @return
     */
    @RequestMapping("/refreshCacheKey")
    public @ResponseBody
    Map<Object, Object> refreshCacheKey(@RequestParam(value = "cacheKeys[]") String[] cacheKeys) {
        try {
            cacheService.refreshCacheKey(cacheKeys);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDWZAjax.fail(e.getMessage());
        }
        return ReturnDWZAjax.success("刷新缓存成功！");
    }

    /**
     * 启用缓存key
     *
     * @param cacheKeys
     * @return
     */
    @RequestMapping("/openCachekey")
    @ResponseBody
    public Map<Object, Object> openCachekey(@RequestParam(value = "cacheKeys[]") String[] cacheKeys) {
        try {
            cacheService.openCachekey(cacheKeys);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDWZAjax.fail(e.getMessage());
        }
        return ReturnDWZAjax.success("启用缓存成功！");
    }

    /**
     * 关闭缓存key
     *
     * @param cacheKeys
     * @return
     */
    @RequestMapping("/closeCachekey")
    @ResponseBody
    public Map<Object, Object> closeCachekey(@RequestParam(value = "cacheKeys[]") String[] cacheKeys) {
        try {
            cacheService.closeCachekey(cacheKeys);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDWZAjax.fail(e.getMessage());
        }
        return ReturnDWZAjax.success("关闭缓存成功！");
    }

    /**
     * 进入修改缓存失效时间页面
     *
     * @param cacheKey
     * @return
     */
    @RequestMapping("/refreshCachekeyExpire/page")
    public String refreshCachekeyExpire(@RequestParam String cacheKey, Map<String, Object> model) {

        model.put("cacheKeyVO", cacheService.findCacheKey(cacheKey));
        return "cache/detail";
    }

    /**
     * 修改缓存失效时间
     *
     * @param cacheKey
     * @param expire
     * @return
     */
    @RequestMapping("/refreshCachekeyExpire")
    @ResponseBody
    public Map<Object, Object> refreshCachekeyExpire(@RequestParam String cacheKey, @RequestParam int expire) {
        try {
            cacheService.refreshCachekeyExpire(cacheKey, expire);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDWZAjax.fail(e.getMessage());
        }
        return ReturnDWZAjax.success("刷新缓存失效时间成功！");
    }

    /**
     * 提供给测试使用,查询缓存key值
     *
     * @return
     */
    private Logger log = LoggerFactory.getLogger(SysCacheController.class);
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @RequestMapping("/getCacheKeyInfo")
    @ResponseBody
    public Map<Object, Object> redisCacheData(String redisCacheTable, String redisCacheKey) {
        Object cacheObj = jsClientDaoSupport.getObj(Object.class, redisCacheTable, redisCacheKey);
        if (cacheObj != null) {
            log.info("读取缓存数据成功，cachekey：" + redisCacheTable + "|" + redisCacheKey);
            Map map = new HashMap<Object, Object>();
            map.put("redisData", cacheObj);
            return map;
        } else {
            log.info("没有缓存数据");
            return ReturnDWZAjax.success("没有缓存数据");
        }
    }
}
