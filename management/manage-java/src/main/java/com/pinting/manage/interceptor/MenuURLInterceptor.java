package com.pinting.manage.interceptor;

import java.net.URLDecoder;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinting.business.facade.manage.MRoleFacade;
import com.pinting.business.hessian.manage.message.ReqMsg_MHome_MenuQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MHome_MenuQuery;
import com.pinting.business.model.vo.MMenuRoleVO;
import com.pinting.business.service.manage.MMenuService;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.manage.enums.SpecialURLEnum;
import com.pinting.util.GlobEnv;
/**
 * 菜单权限限制
 * @author bianyatian
 * @2016-4-8 下午3:02:22
 */
public class MenuURLInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	@Qualifier("dispatchService")
	private HessianService manageService;
	
	@Autowired
	private MMenuService mMenuService;
	
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(CollectionUtils.isEmpty(MRoleFacade.map)){
			List<MMenuRoleVO> menuList = mMenuService.findAllChildMenu();
			//获取链接并进行替代，存入map
			for (MMenuRoleVO mMenuRoleVO : menuList) {
				String url = mMenuRoleVO.getUrl().replace("/", "").replace("：", "").replace(":", "");
				LinkedList<Integer> urlList = MRoleFacade.map.get(url);
				if(MRoleFacade.map.get(url) != null){
					urlList = MRoleFacade.map.get(url);
					urlList.add(mMenuRoleVO.getRoleId()==null?-1:mMenuRoleVO.getRoleId());
				}else{
					urlList = new LinkedList<Integer>();
					urlList.addFirst(mMenuRoleVO.getRoleId()==null?-1:mMenuRoleVO.getRoleId());
				}
				MRoleFacade.map.put(url, urlList);
			}
		}
		
		StringBuffer burl = new StringBuffer(request.getServletPath());
		String urlStr = burl.toString();
		if(StringUtil.isNotEmpty(request.getQueryString()) && !request.getQueryString().startsWith("_=")) {
			urlStr = burl.append("?"+request.getQueryString()).toString();
  			urlStr = StringUtils.substringBefore(urlStr, "&_=");
  			urlStr = URLDecoder.decode(urlStr,"UTF-8").replace("/", "").replace("：", "").replace(":", "");
  		}else{
  			urlStr = urlStr.replace("/", "").replace("：", "").replace(":", "");
  		}
		
		CookieManager cookie = new CookieManager(request);
		//String _userId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		String _roleId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_ROLE_ID.getName(), true);
		if(StringUtil.isBlank(_roleId)){
			return true;
		}else{
			LinkedList<Integer> list = MRoleFacade.map.get(urlStr);//获得该链接对应的roleId列表
			if(CollectionUtils.isEmpty(list)){
				//list为空，则可以访问
				return true;
			}else{
				for (Integer rId : list) {
					if(rId == Integer.valueOf(_roleId)){
						return true;
					}
				}
				response.sendRedirect(GlobEnv.getWebURL("/errors/noRoot.htm"));
				return false;
			}
			
		}
		
	}

}
