package com.pinting.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.core.cookie.CookieManager;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;

public class Util {

	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport
			.getInstance(Constants.REDIS_SUBSYS_SITE);

	/**
	 * 生成 用户邀请码 生成规则：用户编号 + 1137
	 * 
	 * @param userId
	 * @return
	 */
	public static String generateInvitationCode(Integer userId) {
		Integer invitationCode = userId + 1137;
		return String.valueOf(invitationCode);
	}

	/**
	 * 根据邀请码获得用户编号 获得规则：邀请码 - 1137
	 * 
	 * @param invitationCode
	 * @return 成功则返回用户编号，否则返回-1
	 */
	public static Integer getUserIdByInvitationCode(String invitationCode) {
		Integer userId = -1;
		try {
			userId = Integer.valueOf(invitationCode) - 1137;
			if (userId <= 0) {
				return -1;
			}
		} catch (NumberFormatException e) {
			return -1;
		}
		return userId;
	}
	
	/**
     * 出借计划协议生成协议编号，生成规则：ZDCJ + yyyyMMddHHmmssSSS
     * @return
     */
    public static String getAgreementNumber() {
    	String str = "ZDCJ"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    	return str;
    }
    
    /**
     * 借款协议生成协议编号，生成规则：JK + yyyyMMddHHmmssSSS
     * @return
     */
    public static String getLoanAgreementNumber() {
    	String str = "JK"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    	return str;
    }

	/**
	 * 债权转让协议生成协议编号，生成规则：ZQZR + yyyyMMddHHmmssSSS
	 * @return
	 */
	public static String getClaimsAgreementNumber() {
		String str = "ZQZR"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		return str;
	}

	/**
	 * 重复提交校验
	 * 
	 * @param request
	 * @return 重复提交返回true，否则返回false
	 */
	public static boolean isRepeatSubmit(HttpServletRequest request, HttpServletResponse response) {
		try {
			CookieManager manager = new CookieManager(request);
	  		String _account = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
	  		String serverToken = getServerToken(request, _account);
			if (StringUtil.isEmpty(serverToken)) {
				return true;
			}
			// String clientToken = request.getParameter("token");
			String clientToken = manager.getValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_TOKEN.getName(), true);
			if (StringUtil.isEmpty(clientToken)) {
				return true;
			}
			if (!serverToken.equals(clientToken)) {
				return true;
			}
			
			manager.setValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_TOKEN.getName(), "", true);
        	manager.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", -1, true);
        	
        	if(!StringUtil.isEmpty(_account)){
        		jsClientDaoSupport.setString("", "token_" + _account, 0);
			}
        	
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}

	}
	
	/**
	 * 从redis或cookie中获得token
	 * @param request
	 * @param userId
	 * @return
	 */
	public static String getServerToken(HttpServletRequest request, String userId){
		String serverToken = "";
		try {
			serverToken = jsClientDaoSupport.getString("token_" + userId);
			if(StringUtil.isEmpty(serverToken)){
				CookieManager manager = new CookieManager(request);
				serverToken = manager.getValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_TOKEN.getName(), true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serverToken;
	}

	/**
	 * 从redis或cookie中获得token
	 * @param request
	 * @param userId
	 * @return
	 */
	public static String getServerToken(HttpServletRequest request, String userId, String preKey, CookieEnums cookieEnums){
		String serverToken = "";
		try {
			serverToken = jsClientDaoSupport.getString(preKey + userId);
			if(StringUtil.isEmpty(serverToken)){
				CookieManager manager = new CookieManager(request);
				serverToken = manager.getValue(CookieEnums._SITE_BIZ.getName(), cookieEnums.getName(), true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serverToken;
	}

	/**
	 * 创建token
	 * @param request
	 * @param response
     * @return
     */
	public static String createToken(HttpServletRequest request, HttpServletResponse response) {
		CookieManager cookie = new CookieManager(request);
		String _account = cookie.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		String token = UUID.randomUUID().toString();
		if(!StringUtil.isEmpty(_account)){
			try {
				jsClientDaoSupport.setString(token, "token_" + _account, 3600);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		cookie.setValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_TOKEN.getName(), token, true);
		cookie.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", -1, true);
		request.getSession().setAttribute("token", token);
		return token;
	}

	/**
	 * 创建token
	 * @param request
	 * @param response
	 * @return
	 */
	public static String createToken(HttpServletRequest request, HttpServletResponse response, String preKey, CookieEnums cookieEnums) {
		CookieManager cookie = new CookieManager(request);
		String _account = cookie.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		String token = UUID.randomUUID().toString();
		if(!StringUtil.isEmpty(_account)){
			try {
//				jsClientDaoSupport.setString(token, "token_" + _account, 3600);
				jsClientDaoSupport.setString(token, preKey + _account, 3600);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		cookie.setValue(CookieEnums._SITE_BIZ.getName(), cookieEnums.getName(), token, true);
		cookie.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", -1, true);
		return token;
	}

	/**
	 * 重复提交校验
	 *
	 * @param request
	 * @return 重复提交返回true，否则返回false
	 */
	public static boolean isRepeatSubmit(HttpServletRequest request, HttpServletResponse response, String preKey, CookieEnums cookieEnums) {
		try {
			CookieManager manager = new CookieManager(request);
			String _account = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
			String serverToken = getServerToken(request, _account, preKey, cookieEnums);
			if (StringUtil.isEmpty(serverToken)) {
				return true;
			}
			// String clientToken = request.getParameter("token");
			String clientToken = manager.getValue(CookieEnums._SITE_BIZ.getName(), cookieEnums.getName(), true);
			if (StringUtil.isEmpty(clientToken)) {
				return true;
			}
			if (!serverToken.equals(clientToken)) {
				return true;
			}

			manager.setValue(CookieEnums._SITE_BIZ.getName(), cookieEnums.getName(), "", true);
			manager.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", -1, true);

			if(!StringUtil.isEmpty(_account)){
				jsClientDaoSupport.setString("", preKey + _account, 0);
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}

	}

	/**
	 * 创建mobile_token
	 * @param request
	 * @param response
	 * @return
	 */
	public static String createMobileToken(HttpServletRequest request, HttpServletResponse response) {
		CookieManager cookie = new CookieManager(request);
		String _account = cookie.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		String token = UUID.randomUUID().toString();
		if(!StringUtil.isEmpty(_account)){
			try {
				jsClientDaoSupport.setString(token, "mobile_token_" + _account, 3600);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			cookie.setValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_MOBILE_TOKEN.getName(), token, true);
			cookie.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", -1, true);
		}
		request.getSession().setAttribute("mobileToken", token);
		return token;
	}

	/**
	 * 重复提交校验
	 *
	 * @param request
	 * @return 重复提交返回true，否则返回false
	 */
	public static boolean isRepeatMobileSubmit(HttpServletRequest request, HttpServletResponse response) {
		try {
			CookieManager manager = new CookieManager(request);
			String _account = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
			String serverToken = "";
			try {
				serverToken = jsClientDaoSupport.getString("mobile_token_" + _account);
				if(StringUtil.isEmpty(serverToken)){
					serverToken = manager.getValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_MOBILE_TOKEN.getName(), true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (StringUtil.isEmpty(serverToken)) {
				return true;
			}
			String clientToken = request.getParameter("mobileToken");
			if (StringUtil.isEmpty(clientToken)) {
				return true;
			}
			if (!serverToken.equals(clientToken)) {
				return true;
			}
			manager.setValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_MOBILE_TOKEN.getName(), "", true);
			manager.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", -1, true);
			if(!StringUtil.isEmpty(_account)) {
				jsClientDaoSupport.setString("", "mobile_token_" + _account, 0);
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}


	public static String get6Num(){
		 //生成验证码
        Random random = new Random();
		StringBuffer password = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			String rand = String.valueOf(random.nextInt(10));
			password.append(rand);
		}
		return password.toString();
	}

}
