package com.pinting.manage.enums;



/**
 * 枚举常量
 */
public enum CookieEnums {
	
	
	_MANAGE_PLAT_FORM("_managePlatForm"),
	_MANAGE_PLAT_FORM_EMAIL("_managePlatFormEmail"),
	_MANAGE_PLAT_FORM_NAME("_managePlatFormName"),
	_MANAGE_PLAT_FORM_USER_ID("_managePlatFormUserId"),
	_MANAGE_PLAT_FORM_ROLE_ID("_managePlatFormRoleId"),
	_MANAGE_PLAT_FORM_ROLE_NAME("_managePlatFormRoleName"),
	_MANAGE_PLAT_FORM_DAFY_USERID("_managePlatFormDafyUserId"),
	_MANAGE_PLAT_FORM_DAFY_USERNME("_managePlatFormDafyUserName"),
	_MANAGE_PLAT_FORM_DAFY_DEPTID("_managePlatFormDafyDeptId"),
	_MANAGE_PLAT_FORM_DAFY_DEPTCODE("_managePlatFormDafyDeptCode"),
	_MANAGE_PLAT_FORM_DAFY_DEPTNAME("_managePlatFormDafyDeptName"),
	_MANAGE_PLAT_FORM_DAFY_ISMANAGER("_managePlatFormDafyIsManager"),
	_MANAGE_PLAT_FORM_DAFY_ISDAFYUSER("_managePlatFormDafyIsDafyUser"),
	
	
	_MANAGE_OPEN_ID("_openId"),
	_MANAGE_WX_NICK("_wxNick"),
	_MANAGE_WX_HEAD_IMG_URL("_wxHeadImgUrl"),
	
	
	_MANAGE_CODE_GROUP("_manage_code_group"),
	_MANAGE_CODE("_manage_code");
	
	
	private String name;	
	
	CookieEnums(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}