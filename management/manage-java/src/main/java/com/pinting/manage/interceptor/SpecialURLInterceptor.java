package com.pinting.manage.interceptor;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * 敏感链接权限控制
 * @author bianyatian
 * @2016-4-8 下午3:02:46
 */
public class SpecialURLInterceptor extends HandlerInterceptorAdapter {
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
		String urlStr = burl.toString().replace("/", "");
		//根据拦截到的burl在枚举类中找对应的枚举，若无，则不需要拦截，直接返回true，若有，则根据父菜单链接查询是否有权限
		for (SpecialURLEnum urlEnum : EnumSet.allOf(SpecialURLEnum.class)) {
			if(urlStr.startsWith(urlEnum.getCode().replace("/", ""))){
				String fUrl = urlEnum.getDescription().replace("/", "").replace("：", "").replace(":", ""); //父菜单链接
				CookieManager cookie = new CookieManager(request);
				String _roleId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_ROLE_ID.getName(), true);
				if(StringUtil.isBlank(_roleId)){
					return true;
				}else{
					LinkedList<Integer> list = MRoleFacade.map.get(fUrl);//获得该链接对应的roleId列表
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
		
		return true;
		
	}
}
