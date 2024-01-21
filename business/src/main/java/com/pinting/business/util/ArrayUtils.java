package com.pinting.business.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils extends org.apache.commons.lang.ArrayUtils {

    private static Logger logger = LoggerFactory.getLogger(ArrayUtils.class);

    /**
     * 切分字符串转换为Str[]数组
     *
     * @param str 被切分的字符串
     * @return 切分后的集合
     */
    public static String[] split2Str(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        str = str.replaceAll("，", ",").trim();
        return StringUtils.split(str, ",");
    }

    /**
     * 切分字符串转换为List
     *
     * @param str 被切分的字符串
     * @return 切分后的集合
     */
    public static List<Integer> split2Integer(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String[] strs = split2Str(str);
        if (strs != null && strs.length > 0) {
            List<Integer> list = new ArrayList<>(strs.length);
            for (String strInfo : strs) {
                if (StringUtils.isNotBlank(strInfo)) {
                    try {
                        list.add(Integer.valueOf(strInfo.trim()));
                    } catch (Exception e) {
                        logger.error("字符串："+ strInfo +"转换Integer型List失败");
                    }
                }
            }
            return list;
        } else {
            return null;
        }
    }
}
