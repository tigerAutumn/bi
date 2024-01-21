package com.pinting.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 校验手机
 */
public class MobileCheckUtil {
    
    public static boolean isMobile(String mobile) {
        boolean isMobile = false;
        Pattern pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        Matcher matcher = pattern.matcher(mobile);
        isMobile = matcher.matches();
        return isMobile;
    }
    
    public static void main(String[] args) {
        System.out.println(MobileCheckUtil.isMobile("157293840119"));
        System.out.println(MobileCheckUtil.isMobile("11111111111"));
    }
    
}
