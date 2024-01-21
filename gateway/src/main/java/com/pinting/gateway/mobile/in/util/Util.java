package com.pinting.gateway.mobile.in.util;

import javax.servlet.http.HttpServletRequest;


public class Util {

	
	
	/**
	 * 生成 用户邀请码
	 * 生成规则：用户编号 + 1137
	 * @param userId
	 * @return
	 */
	public static String generateInvitationCode(Integer userId){
		Integer invitationCode = userId + 1137;
		return String.valueOf(invitationCode);
	}
	/**
	 * 根据邀请码获得用户编号
	 * 获得规则：邀请码 - 1137
	 * @param invitationCode
	 * @return 成功则返回用户编号，否则返回-1
	 */
	public static Integer getUserIdByInvitationCode(String invitationCode){
		Integer userId = -1;
		try {
			userId = Integer.valueOf(invitationCode) - 1137;
			if(userId <= 0){
				return -1;
			}
		} catch (NumberFormatException e) {
			return -1;
		}
		return userId;
	}

}
