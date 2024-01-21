package com.pinting.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.model.vo.CGBindCardResVO;
import com.pinting.core.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class Util {

	public static void convertToFile(List<CGBindCardResVO> list){
		String prePath = "/home/pinting/server/";
		List<String> content = new ArrayList<>();
		content.add(detailFileTitles());

		if (!CollectionUtils.isEmpty(list)) {
			for (CGBindCardResVO data : list) {
				StringBuffer contentBuffer = new StringBuffer();
				contentBuffer.append(StringUtil.isEmpty(data.getHfUserId()) ? "" : data.getHfUserId()).append(",");
				contentBuffer.append(StringUtil.isEmpty(data.getResCode()) ? "" : data.getResCode()).append(",");
				contentBuffer.append(StringUtil.isEmpty(data.getResMsg()) ? "" : data.getResMsg()).append(",");
                contentBuffer.append(StringUtil.isEmpty(data.getOrderNo()) ? "" : data.getOrderNo()).append(",");
                contentBuffer.append(StringUtil.isEmpty(data.getNote()) ? "" : data.getNote());
                content.add(contentBuffer.toString());
			}
		}
		try {
			com.pinting.business.util.ExportUtil.exportLocalCSV(prePath, content,"bindCardRes_" + com.pinting.core.util.DateUtil.formatYYYYMMDD(new Date()) + ".csv");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回明细文件的标题
	 *
	 * @return
	 */
	private static String detailFileTitles() {
		StringBuffer header = new StringBuffer();
		header.append("恒丰用户编号").append(",");
		header.append("返回码").append(",");
		header.append("返回信息").append(",");
        header.append("订单号").append(",");
        header.append("备注");
		return header.toString();
	}


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
	
	
	/**
     * 重复提交校验
     * 
     * @param request
     * @return 重复提交返回true，否则返回false
     */
    public static boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession().getAttribute("token");
        if (serverToken == null) {
            return true;
        }
        String clinetToken = request.getParameter("token");
        if (clinetToken == null) {
        	return true;
        }
        if (!serverToken.equals(clinetToken)) {
        	return true;
        }
        request.getSession().removeAttribute("token");
        return false;
    }

	/**
	 * 创建token
	 * @param request
	 * @param response
	 * @return
	 */
	public static String createToken(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("token", UUID.randomUUID().toString());
		String serverToken = (String) request.getSession().getAttribute("token");
		return serverToken;
	}

	/**
	 * 创建token
	 * @param request
	 * @return
	 */
	public static void createToken(HttpServletRequest request) {
		request.getSession().setAttribute("token", UUID.randomUUID().toString());
	}
}
