package com.pinting.business.util;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则校验
 */
public class RegexUtil {

    static final String NUMERIC = "^[0-9]*$";
    static final String EMAIL = "^[a-z0-9A-Z_\\+-]+(\\.[a-z0-9A-Z_\\+-]+)*@[a-z0-9A-Z-]+(\\.[a-z0-9A-Z-]+)*\\.([a-zA-Z]{2,})$";

    /**
     * 判断注册邮箱是否正确
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        if (StringUtils.isNotBlank(email)) {
            return email.matches(EMAIL);
        }
        return false;
    }

    /**
     * 校验纯数字
     *
     * @param str
     * @return
     * @version
     * @author zousheng
     * @date:2016年8月31日
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile(NUMERIC);
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
