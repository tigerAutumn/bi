package com.pinting.mall.util;

import org.apache.commons.lang.StringUtils;

public class RegexpUtils {

    public static final String NUMERIC = "^[0-9]*$";
    public static final String MOBILE = "1\\d{10}";
    public static final String CHINESE = "[\\u4E00-\\uFA29\\uE7C7-\\uE7F3]+";
    public static final String CHINESE_LETTER = "[\\u4E00-\\uFA29\\uE7C7-\\uE7F3a-zA-Z]+";

    /**
     * 校验手机号格式
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        if (StringUtils.isNotBlank(mobile)) {
            return mobile.matches(MOBILE);
        }
        return false;
    }

    /**
     * 校验非手机号格式
     *
     * @param mobile
     * @return
     */
    public static boolean notMobile(String mobile) {
        return !isMobile(mobile);
    }

    /**
     * 校验纯数字
     *
     * @param numeric
     * @return
     */
    public static boolean isNumeric(String numeric) {
        if (StringUtils.isNotBlank(numeric)) {
            return numeric.matches(NUMERIC);
        }
        return false;
    }

    /**
     * 校验中文
     *
     * @param name
     * @return
     */
    public static boolean isChinese(String name) {
        if (StringUtils.isNotBlank(name)) {
            return name.matches(CHINESE);
        }
        return false;
    }

    /**
     * 校验中文、英文
     *
     * @param name
     * @return
     */
    public static boolean isChineseOrLetter(String name) {
        if (StringUtils.isNotBlank(name)) {
            return name.matches(CHINESE_LETTER);
        }
        return false;
    }

    /**
     * 校验非中文、英文
     *
     * @param name
     * @return
     */
    public static boolean notChineseOrLetter(String name) {
        return !isChineseOrLetter(name);
    }

//    public static void main(String[] args) {
//        String str = "我是中文 a";
//
//        System.out.println(RegexpUtils.isChineseOrLetter(str));
//    }
}
