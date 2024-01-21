package com.pinting.manage.controller;

import com.pinting.business.dao.MUserMapper;
import com.pinting.business.hessian.manage.message.ReqMsg_MUser_login;
import com.pinting.business.hessian.manage.message.ResMsg_MUser_login;
import com.pinting.business.model.MUser;
import com.pinting.business.model.MUserExample;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.site.UserService;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.core.util.encrypt.MD5Util;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.GeneratePasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 登入
 *
 * @author Linkin
 * @Project: manage-java
 * @Title: LoginController.java
 * @date 2015-2-3 下午12:12:56
 * @Copyright: 2015 bigangwan.com Inc. All rights reserved.
 */
@Controller
public class LoginController {
    @Autowired
    @Qualifier("dispatchService")
    private HessianService siteService;
    @Autowired
    private MUserMapper userMapper;
    @Autowired
    private BsSysConfigService bsSysConfigService;

    public void setSiteService(HessianService siteService) {
        this.siteService = siteService;
    }

    @Autowired
    private UserService userService;

    public static Integer LOGIN_STATUS_2 = 2; // 管理员销户状态
    public static Integer LOGIN_STATUS_3 = 3; // 管理员冻结状态

    /**
     * 打开登入界面
     *
     * @return
     */
    @RequestMapping("/login/index")
    public String login() {


        return "/login/index";
    }

    /**
     * 密码复杂度校验
     * @param password
     * @return
     */
    private boolean isValidPwd(String password) {
        if (StringUtil.isEmpty(password)) {
            return false;
        }
        if (password.length() < 8 || password.length() > 16) {
            return false;
        }
        if (!GeneratePasswordUtil.checkPassword(password)) {
            return false;
        }
        if (!GeneratePasswordUtil.checkStr(password)) {
            return false;
        }
        return true;
    }

    /**
     * 用户登录
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login/login")
    public @ResponseBody
    String logo(ReqMsg_MUser_login req, HttpServletRequest request, HttpServletResponse response,
                @RequestParam("email") String email,
                @RequestParam("passwd") String passwd,
                @RequestParam("verCode") String verCode,
                Map<String, Object> model) {
        /* 检查各项是否为空 */
        if (StringUtil.isBlank(email)) return "emailEmpty";
        if (StringUtil.isBlank(passwd)) return "passwdEmpty";
        if (StringUtil.isBlank(verCode)) return "verCodeEmpty";

        /* 检查验证码是正确 */
        CookieManager cookie = new CookieManager(request);
        boolean isCode = isCode(verCode, request);
        cookie.setValue(CookieEnums._MANAGE_CODE_GROUP.getName(), CookieEnums._MANAGE_CODE.getName(), "", false);
        cookie.save(response, CookieEnums._MANAGE_CODE_GROUP.getName(), true);
        if (!isCode) {
            // 判断的同时将原本的验证码清空掉
            return "verCodeError";
        }

        //手机号需要调用云贷接口验证
        Integer dafyRoles = null;
        Pattern p = Pattern.compile("^[1][34587]\\d{9}$");
        Matcher m = p.matcher(email);
        if (m.matches()) {
            Map<String, Object> map = userService.checkClientManagerLogin(email);
            boolean result = (boolean) map.get("result");
            if (result) {
                String dafyUserId = (String) map.get("dafyUserId");
                String dafyUserName = (String) map.get("dafyUserName");
                String dafyDeptCode = (String) map.get("dafyDeptCode");
                String dafyDeptName = (String) map.get("dafyDeptName");
                String dafyDeptId = (String) map.get("dafyDeptId");
                Integer isManager = (Integer) map.get("isManager"); //1:部门主管,0:不是部门主管
                if (map.containsKey("dafyRoles")) {
                    dafyRoles = Integer.valueOf((String) map.get("dafyRoles")); //角色id
                }
                cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERID.getName(), dafyUserId, true);
                cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERNME.getName(), dafyUserName, true);
                cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTCODE.getName(), dafyDeptCode, true);
                cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTNAME.getName(), dafyDeptName, true);
                cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTID.getName(), dafyDeptId, true);
                cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISMANAGER.getName(), String.valueOf(isManager), true);
                cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISDAFYUSER.getName(), "yes", true);
            } else {
                return "n";
            }
        }

        //先查询用户
        MUserExample example = new MUserExample();
        if (m.matches()) {
            example.createCriteria().andMobileEqualTo(email);
        } else {
            example.createCriteria().andEmailEqualTo(email);
        }
        List<MUser> mUserList = userMapper.selectByExample(example);

        // 存在该用户
        // 连续登录失败3次后，冻结管理员账号
        if (mUserList != null && mUserList.size() > 0) {
            MUser mUser = mUserList.get(0);

            if (LOGIN_STATUS_3.equals(mUser.getStatus()) || LOGIN_STATUS_2.equals(mUser.getStatus())) {
                // 账号冻结状态或销户状态,不允许登录
                return "n";
            }

            boolean isLoginCheck = true; // 默认登录校验通过

            // 密码复杂度校验
            if (!isValidPwd(passwd)) {
                isLoginCheck = false;
            } else if (mUser.getPassword() != null &&
                    !mUser.getPassword().equals(MD5Util.encryptMD5(passwd + MD5Util.getEncryptkey()))) {
                isLoginCheck = false;
            } else {
                /* 检查用户名密码是否正确 */
                String psw = MD5Util.encryptMD5(passwd + MD5Util.getEncryptkey());
                //发起Hessian 登录接口验证
                req.setPassword(psw);
                req.setEmail(email);
                req.setRoleId(dafyRoles);
                ResMsg_MUser_login resp = (ResMsg_MUser_login) siteService.handleMsg(req);

                if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
                    /* 存储Cookie信息 */
                    cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_EMAIL.getName(), resp.getEmail(), true);
                    cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_NAME.getName(), resp.getName(), true);
                    cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), String.valueOf(resp.getId()), true);
                    cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_ROLE_ID.getName(), String.valueOf(resp.getRoleId()), true);
                    cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_ROLE_NAME.getName(), resp.getRoleName(), true);
                    cookie.save(response, CookieEnums._MANAGE_PLAT_FORM.getName(), true);
                    return "y";
                } else {
                    isLoginCheck = false;
                }
            }

            if (!isLoginCheck) {
                // 记录当天登录时间在当天00:00:00后，当前23:59:59前，当天失败次数
                MUser mUser2 = new MUser();
                mUser2.setId(mUser.getId());

                if (mUser.getLoginFailTime() != null && (mUser.getLoginFailTime().after(com.pinting.business.util.DateUtil.getDateBegin(new Date()))
                        && mUser.getLoginFailTime().before(com.pinting.business.util.DateUtil.getDateEnd(new Date())))) {

                    Integer LoginFailTimes = mUser.getLoginFailTimes() == null ? 1 : mUser.getLoginFailTimes() + 1;
                    mUser2.setLoginFailTimes(LoginFailTimes);
                    mUser2.setLoginFailTime(new Date());
                    if (LoginFailTimes.compareTo(3) >= 0) {
                        mUser2.setStatus(LOGIN_STATUS_3);
                    }
                    userMapper.updateByPrimaryKeySelective(mUser2);
                } else {
                    mUser2.setLoginFailTimes(1);
                    mUser2.setLoginFailTime(new Date());
                    userMapper.updateByPrimaryKeySelective(mUser2);
                }
            }
            return "n";
        }
        // 不存在该账户
        else {
            return "n";
        }
    }


//	/**
////	 * 用户登录
////	 *
////	 * @param username
////	 * @param password
////	 * @param request
////	 * @param response
////	 * @return
////	 */
////	@RequestMapping("/login/login")
////	public @ResponseBody String logo(ReqMsg_MUser_login req,HttpServletRequest request, HttpServletResponse response,
////			@RequestParam("email") String email,
////			@RequestParam("passwd") String passwd,
////			@RequestParam("verCode") String verCode,
////			Map<String, Object> model) {
////		/* 检查各项是否为空 */
////		if (StringUtil.isBlank(email)) return "emailEmpty";
////		if (StringUtil.isBlank(passwd)) return "passwdEmpty";
////		if (StringUtil.isBlank(verCode)) return "verCodeEmpty";
////
////		/* 检查验证码是正确 */
////		CookieManager cookie = new CookieManager(request);
////		boolean isCode = isCode(verCode, request);
////		cookie.setValue(CookieEnums._MANAGE_CODE_GROUP.getName(), CookieEnums._MANAGE_CODE.getName(), "", false);
////		cookie.save(response, CookieEnums._MANAGE_CODE_GROUP.getName(), true);
////		if (!isCode) {
////			// 判断的同时将原本的验证码清空掉
////			return "verCodeError";
////		}
////
//////		String userId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
//////				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
/////*		if (userId != null && !userId.trim().equals("")) {
////			return "exsit";
////		}
////*/
////		//手机号需要调用云贷接口验证
////		Integer dafyRoles = null;
////		Pattern p = Pattern.compile("^[1][34587]\\d{9}$");
////		Matcher m = p.matcher(email);
////		if(m.matches()) {
////			Map<String, Object> map = userService.checkClientManagerLogin(email);
////			boolean result = (boolean)map.get("result");
////			if(result) {
////				String dafyUserId = (String) map.get("dafyUserId");
////				String dafyUserName = (String) map.get("dafyUserName");
////				String dafyDeptCode = (String) map.get("dafyDeptCode");
////				String dafyDeptName = (String) map.get("dafyDeptName");
////				String dafyDeptId = (String) map.get("dafyDeptId");
////				Integer isManager = (Integer) map.get("isManager"); //1:部门主管,0:不是部门主管
////				if(map.containsKey("dafyRoles")) {
////					dafyRoles = Integer.valueOf((String) map.get("dafyRoles")); //角色id
////				}
////				cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERID.getName(), dafyUserId, true);
////				cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERNME.getName(), dafyUserName, true);
////				cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTCODE.getName(), dafyDeptCode, true);
////				cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTNAME.getName(), dafyDeptName, true);
////				cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTID.getName(), dafyDeptId, true);
////				cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISMANAGER.getName(), String.valueOf(isManager), true);
////				cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISDAFYUSER.getName(), "yes", true);
////			}
////			else {
////				return "n";
////			}
////		}
////
////		//先查询用户
////		MUserExample example = new MUserExample();
////		if(m.matches()) {
////			example.createCriteria().andMobileEqualTo(email);
////		} else {
////			example.createCriteria().andEmailEqualTo(email);
////		}
////		List<MUser> mUserList =userMapper.selectByExample(example);
////		//查询登录失败的限制次数
////		BsSysConfig sys = bsSysConfigService.findKey(Constants.USER_LOGIN_ALWAYS_FAIL_TIME_LIMIT);
////		Integer failLimit = sys != null ? Integer.valueOf(sys.getConfValue()) : 5;
////		//连续登录失败后锁定时间
////		BsSysConfig sysLock = bsSysConfigService.findKey(Constants.USER_LOGIN_LOCK_TIME);
////        Integer lockMinute = sysLock != null ? Integer.valueOf(sysLock.getConfValue()) : 15;
////		//存在该用户
////		if (mUserList != null && mUserList.size() > 0) {
////			MUser mUser = mUserList.get(0);
////
////			//失败次数超过次数
////			if (null!=mUser.getLoginFailTimes() && mUser.getLoginFailTimes() >= failLimit) {
////				//失败时间不超过规定时间
////				Integer diffSeconds = DateUtil.getDiffeSeconds(new Date(), mUser.getLoginFailTime());//已锁住秒数
////				if (diffSeconds < lockMinute*60) {
////
////					return (int)Math.ceil(MoneyUtil.divide(lockMinute*60 - diffSeconds, 60).doubleValue())+"";
////				}
////				//失败时间超过规定时间或者失败时间为空
////				else {
////					if (mUser.getPassword() != null &&
////							!mUser.getPassword().equals(MD5Util.encryptMD5(passwd + MD5Util.getEncryptkey()))) {
////						MUser mUser2 = new MUser();
////						mUser2.setId(mUser.getId());
////						mUser2.setLoginFailTimes(1);
////						mUser2.setLoginFailTime(new Date());
////						userMapper.updateByPrimaryKeySelective(mUser2);
////						return "n";
////					} else {
////						/* 检查用户名密码是否正确 */
////						String psw = MD5Util.encryptMD5(passwd+MD5Util.getEncryptkey());
////						//发起Hessian 登录接口验证
////						req.setPassword(psw);
////						req.setEmail(email);
////						req.setRoleId(dafyRoles);
////						ResMsg_MUser_login resp=(ResMsg_MUser_login) siteService.handleMsg(req);
////						//
////						//返回成功
////
////						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
////							/* 存储Cookie信息 */
////							cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_EMAIL.getName(), resp.getEmail(), true);
////							cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_NAME.getName(), resp.getName(), true);
////							cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), String.valueOf(resp.getId()), true);
////							cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_ROLE_ID.getName(), String.valueOf(resp.getRoleId()), true);
////							cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_ROLE_NAME.getName(), resp.getRoleName(), true);
////							cookie.save(response, CookieEnums._MANAGE_PLAT_FORM.getName(), true);
////
////							return "y";
////						}
////						return "n";
////					}
////				}
////			}
////			//失败次数不超过次数
////			else {
////				if (mUser.getPassword() != null &&
////						!mUser.getPassword().equals(MD5Util.encryptMD5(passwd + MD5Util.getEncryptkey()))) {
////					MUser mUser3 = new MUser();
////					mUser3.setId(mUser.getId());
////					mUser3.setLoginFailTimes(mUser.getLoginFailTimes() == null?1:mUser.getLoginFailTimes() + 1);
////					mUser3.setLoginFailTime(new Date());
////					userMapper.updateByPrimaryKeySelective(mUser3);
////					if (mUser.getLoginFailTimes() == 3) {
////						return "lastChance";
////					}
////					if (mUser.getLoginFailTimes() == 4) {
////						return lockMinute+"";
////					}
////					return "n";
////				} else {
////					/* 检查用户名密码是否正确 */
////					String psw = MD5Util.encryptMD5(passwd+MD5Util.getEncryptkey());
////					//发起Hessian 登录接口验证
////					req.setPassword(psw);
////					req.setEmail(email);
////					req.setRoleId(dafyRoles);
////					ResMsg_MUser_login resp=(ResMsg_MUser_login) siteService.handleMsg(req);
////					//
////					//返回成功
////
////					if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
////						/* 存储Cookie信息 */
////						cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_EMAIL.getName(), resp.getEmail(), true);
////						cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_NAME.getName(), resp.getName(), true);
////						cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), String.valueOf(resp.getId()), true);
////						cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_ROLE_ID.getName(), String.valueOf(resp.getRoleId()), true);
////						cookie.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_ROLE_NAME.getName(), resp.getRoleName(), true);
////						cookie.save(response, CookieEnums._MANAGE_PLAT_FORM.getName(), true);
////
////						return "y";
////					}
////					return "n";
////				}
////			}
////		}
////		// 不存在该账户
////		else {
////			return "n";
////		}
////	}

    /**
     * 判断验证码
     *
     * @param code
     * @param request
     * @return
     */
    private boolean isCode(String code, HttpServletRequest request) {
        CookieManager cookie = new CookieManager(request);
        String _code = cookie.getValue(CookieEnums._MANAGE_CODE_GROUP.getName(), CookieEnums._MANAGE_CODE.getName(), false);

        return _code.equalsIgnoreCase(code);
    }

    /**
     * 登出
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login/out")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        CookieManager cookie = new CookieManager(request);
        cookie.cleanAll(response, CookieEnums._MANAGE_PLAT_FORM.getName(), true);

        return "/login/index";
    }

    /**
     * 通过手机号调用云贷接口，判断用户是否有登录密码
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login/gainPassword")
    @ResponseBody
    public boolean gainPassword(HttpServletRequest request, HttpServletResponse response) {
        String mobile = request.getParameter("mobile");
        return userService.gainInitPassword(mobile);
    }

    /**
     * 发送管理台初始密码
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login/sendPassword")
    @ResponseBody
    public boolean sendPasssword(HttpServletRequest request, HttpServletResponse response) {
        String mobile = request.getParameter("mobile");
        return userService.sendInitPassword(mobile);
    }
}
