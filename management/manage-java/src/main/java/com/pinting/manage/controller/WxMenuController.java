package com.pinting.manage.controller;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_MWxMenu_MWxMenuAdd;
import com.pinting.business.hessian.manage.message.ReqMsg_MWxMenu_MWxMenuDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_MWxMenu_MWxMenuModify;
import com.pinting.business.hessian.manage.message.ReqMsg_MWxMenu_MWxMenuUpdate;
import com.pinting.business.hessian.manage.message.ReqMsg_MWxMenu_WxMenuListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MWxMenu_WxParentMenusListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MWxUtil_GetTokenAndTicket;
import com.pinting.business.hessian.manage.message.ResMsg_MWxMenu_MWxMenuAdd;
import com.pinting.business.hessian.manage.message.ResMsg_MWxMenu_MWxMenuDelete;
import com.pinting.business.hessian.manage.message.ResMsg_MWxMenu_MWxMenuModify;
import com.pinting.business.hessian.manage.message.ResMsg_MWxMenu_MWxMenuUpdate;
import com.pinting.business.hessian.manage.message.ResMsg_MWxMenu_WxMenuListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MWxMenu_WxParentMenusListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MWxUtil_GetTokenAndTicket;
import com.pinting.business.service.manage.MWxMenuService;
import com.pinting.core.base.BaseController;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.util.ReturnDWZAjax;
import com.pinting.util.WeChatUtil;

/**
 * 处理菜单的Controller
 * @author yanwl
 * @date 2015-12-08
 */
@Controller
@RequestMapping("/wx/menu")
public class WxMenuController extends BaseController{
   private static final Logger log = LoggerFactory.getLogger(WxMenuController.class);
    
    @Autowired
    @Qualifier("dispatchService")
    private HessianService wxMenuService;
    
    @Autowired
    @Qualifier("manageService")
    private HessianService wxHessianService;
    
    @Autowired
    private MWxMenuService mWxMenuService;

    /**
     * 跳转至create_menu页面
     * @param request
     * @param map
     * @return
     */
    @RequestMapping("/index")
    public String menuIndex(ReqMsg_MWxMenu_WxMenuListQuery req,HashMap<String,Object> model) {
		ResMsg_MWxMenu_WxMenuListQuery resp = (ResMsg_MWxMenu_WxMenuListQuery)wxMenuService.handleMsg(req);
		model.put("menus", resp.getWxMenuList());
        return "/menu/index";
    }

    @RequestMapping("/create_menu")
    public String toCreateMenu(HttpServletRequest request, ModelMap map) {
        return "/menu/create_menu";
    }

    /**
     * 弹出增加微信菜单的弹出框
     * @param request
     * @param map
     * @return
     */
    @RequestMapping("/detail")
    public String detail(ReqMsg_MWxMenu_WxParentMenusListQuery req,HashMap<String,Object> model) {
    	ResMsg_MWxMenu_WxParentMenusListQuery resp = (ResMsg_MWxMenu_WxParentMenusListQuery)wxMenuService.handleMsg(req);
    	model.put("parentMenus", resp.getWxParentMenuList());
        return "/menu/detail";
    }
    
    /**
     * 弹出增加微信菜单的弹出框
     * @param request
     * @param map
     * @return
     */
    @RequestMapping("/detail4Update")
    public String detail4Update(ReqMsg_MWxMenu_WxParentMenusListQuery req,HashMap<String,Object> model,String menuId) {
    	ResMsg_MWxMenu_WxParentMenusListQuery resp = (ResMsg_MWxMenu_WxParentMenusListQuery)wxMenuService.handleMsg(req);
    	model.put("parentMenus", resp.getWxParentMenuList());
    	
    	model.put("mWxMenu", mWxMenuService.getMWxMenuById(Integer.parseInt(menuId)));
        return "/menu/detail4Update";
    }

    /**
     * 增加微信菜单
     * @param request
     * @param map
     */
    @RequestMapping("/addMenu")
    public @ResponseBody
    Map<Object, Object> saveActivity(ReqMsg_MWxMenu_MWxMenuAdd req,HttpServletRequest request, HttpServletResponse response,
                                     Map<String, Object> model) {

    	ResMsg_MWxMenu_MWxMenuAdd resp = (ResMsg_MWxMenu_MWxMenuAdd)wxMenuService.handleMsg(req);
    	String ret = "网络连接异常，请重试！";
    	if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
    		if(ConstantUtil.RESCODE_SUCCESS.equals(resp.getJson())) {
    			ret = "菜单添加成功！";
    			return ReturnDWZAjax.success(ret);
    		}else {
    			ret = "菜单添加失败！";
    			return ReturnDWZAjax.fail(ret);
    		}
    		
    	}else {
    		return ReturnDWZAjax.fail(ret);
    	}
    }
    
    /**
     * 修改微信菜单
     * @param request
     * @param map
     */
    @RequestMapping("/updateMenu")
    public @ResponseBody
    Map<Object, Object> updateActivity(ReqMsg_MWxMenu_MWxMenuUpdate req,HttpServletRequest request, HttpServletResponse response,
                                     Map<String, Object> model) {

    	ResMsg_MWxMenu_MWxMenuUpdate resp = (ResMsg_MWxMenu_MWxMenuUpdate)wxMenuService.handleMsg(req);
    	String ret = "网络连接异常，请重试！";
    	if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
    		if(ConstantUtil.RESCODE_SUCCESS.equals(resp.getJson())) {
    			ret = "菜单修改成功！";
    			return ReturnDWZAjax.success(ret);
    		}else {
    			ret = "菜单修改失败！";
    			return ReturnDWZAjax.fail(ret);
    		}
    		
    	}else {
    		return ReturnDWZAjax.fail(ret);
    	}
    }

    /**
     * 删除菜单
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteMenu")
    public Map<Object, Object> deleteMenu(ReqMsg_MWxMenu_MWxMenuDelete req, HttpServletRequest request) {
    	ResMsg_MWxMenu_MWxMenuDelete resp = (ResMsg_MWxMenu_MWxMenuDelete)wxMenuService.handleMsg(req);
    	String ret = "网络连接异常，请重试！";
    	if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
    		if(ConstantUtil.RESCODE_SUCCESS.equals(resp.getJson())) {
    			ret = "菜单删除成功！";
    			return ReturnDWZAjax.success(ret);
    		}else {
    			ret = "菜单删除失败！";
    			return ReturnDWZAjax.fail(ret);
    		}
           
    	}else {
    		return ReturnDWZAjax.fail(ret);
    	}
    }
    
    /**
     * 微信后台创建所有的菜单
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/wechat/createMenu")
    public Map<Object, Object> createMenu(ReqMsg_MWxMenu_MWxMenuModify req, HttpServletRequest request) {
    	ResMsg_MWxMenu_MWxMenuModify resp = (ResMsg_MWxMenu_MWxMenuModify)wxMenuService.handleMsg(req);
    	String ret = "网络连接异常，请重试！";
    	if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
    		String json = resp.getMenuJson();
        	log.info("json:"+json);
        	ResMsg_MWxUtil_GetTokenAndTicket resToken = (ResMsg_MWxUtil_GetTokenAndTicket)wxHessianService.handleMsg(new ReqMsg_MWxUtil_GetTokenAndTicket());
    		String accessToken = resToken.getAccessToken();// 你自己的token
        	log.info("accessToken:"+accessToken);
        	String msg = "";
        	JSONObject jo = JSONObject.fromObject(json);
        	if("[]".equals(jo.getString("button"))) {
        		msg = deleteMenu(json, accessToken);
        	}else {
        		msg = createMenu(json, accessToken);
        	}
            log.info("msg:"+msg);
            JSONObject jo1 = JSONObject.fromObject(msg);
            if(ConstantUtil.ERRCODE == jo1.getInt("errcode") && ConstantUtil.ERRMSG.equals(jo1.getString("errmsg"))) {
            	ret = "微信菜单创建成功！";
            	return ReturnDWZAjax.success(ret);
            }else {
            	ret = "微信菜单创建失败！";
            	return ReturnDWZAjax.fail(ret);
            }
    	}else {
    		return ReturnDWZAjax.fail(ret);
    	}
    }
    
    /**
     * 微信后台删除所有的菜单
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/wechat/deleteMenu")
    public Map<Object, Object> delMenu(ReqMsg_MWxMenu_MWxMenuModify req, HttpServletRequest request) {
    	ResMsg_MWxMenu_MWxMenuModify resp = (ResMsg_MWxMenu_MWxMenuModify)wxMenuService.handleMsg(req);
    	String ret = "网络连接异常，请重试！";
    	if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
    		String json = resp.getMenuJson();
    		log.info("json:"+json);
    		ResMsg_MWxUtil_GetTokenAndTicket resToken = (ResMsg_MWxUtil_GetTokenAndTicket)wxHessianService.handleMsg(new ReqMsg_MWxUtil_GetTokenAndTicket());
    		String accessToken = resToken.getAccessToken();// 你自己的token
    		log.info("accessToken:"+accessToken);
    		String msg = deleteMenu(json, accessToken);
    		log.info("msg:"+msg);
    		JSONObject jo = JSONObject.fromObject(msg);
            if(ConstantUtil.ERRCODE == jo.getInt("errcode") && ConstantUtil.ERRMSG.equals(jo.getString("errmsg"))) {
            	ret = "微信菜单删除成功！";
            	return ReturnDWZAjax.success(ret);
            }else {
            	ret = "微信菜单删除失败！";
            	return ReturnDWZAjax.fail(ret);
            }
    	}else {
    		return ReturnDWZAjax.fail(ret);
    	}
    }

    /**
    * 创建自定义菜单
    * @param params
    * @param accessToken
    */
    protected static String createMenu(String params, String accessToken) {
        try {
            String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
                         + accessToken;

            String msg = WeChatUtil.sendPost(url, params, false, "UTF-8");
            return msg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 删除自定义菜单
     * @param params
     * @param accessToken
     */
    protected static String deleteMenu(String params, String accessToken) {
    	try {
    		String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="
    				+ accessToken;
    		
    		String msg = WeChatUtil.sendPost(url, params, false, "UTF-8");
    		return msg;
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
}
