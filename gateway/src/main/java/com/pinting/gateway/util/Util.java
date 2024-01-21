/**
 * business.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.util;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: Util.java, v 0.1 2015-6-18 下午7:39:54 BabyShark Exp $
 */
public class Util {

    /**
     * 生成指定长度的数字字符串，数字位数不足前面补零
     * @param length    需要转换成的长度
     * @return
     */
    public static String generateAssignLengthNo(int length) {

        int randomNum = (int) (Math.random() * Math.pow(10, length));
        String tmpString = String.valueOf(randomNum);
        for (int i = tmpString.length(); i < length; i++) {
            tmpString = "0" + tmpString;
        }
        return tmpString;
    }

    /**
     * 身份证X统一为大写
     * 
     * @param idNo
     * @return
     */
    public static String formatIdNo2Upper(String idNo) {
        return idNo.replace('x', 'X');
    }

    /**
     * 身份证X统一为小写
     * 
     * @param idNo
     * @return
     */
    public static String formatIdNo2Lower(String idNo) {
        return idNo.replace('X', 'x');
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

}
