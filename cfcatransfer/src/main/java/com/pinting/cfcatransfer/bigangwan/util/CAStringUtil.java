package com.pinting.cfcatransfer.bigangwan.util;


/**
 * 
 * @Author: daimin
 * @Description: 字符串工具
 * @CodeReviewer:clsu
 */
public class CAStringUtil {
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 判断字符串是否为空
     * 
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (null == str || "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串是否不为空
     * 
     */
    public static boolean isNotEmpty(String str) {
        if (str != null && !"".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 替换指定的字符串中的元素为空
     * @param string
     * @param replacement
     * @return
     */
    public static String replace(String string, String replacement) {
        if (string != null) {
            return string.replaceAll(replacement, "");
        } else {
            return null;
        }
    }

    /**
     * 去掉字符串的两边空格
     * @param string
     * @return
     */
    public static String trim(String string) {
        if (isEmpty(string)) {
            return "";
        } else {
            return string.trim();
        }
    }

    /**
     * 得到报文指定节点的内容
     * @param xmlString
     * @param nodeName
     * @return
     */
    public static String getNodeText(String xmlString, String nodeName) {
        String beginName = "<" + nodeName + ">";
        String endName = "</" + nodeName + ">";
        int beginIndex = xmlString.indexOf(beginName);
        if (beginIndex == -1) {
            return "";
        } else {
            int endIndex = xmlString.indexOf(endName);
            if (endIndex == -1) {
                return "";
            }
            String nodeValue = xmlString.substring(beginIndex + beginName.length(), endIndex);
            return nodeValue;
        }

    }

    
    public static String bytes2hex(byte[] bytes) {
        String result = "";
        String b = "";
        for (int i = 0; i < bytes.length; i++) {
            b = Integer.toHexString(bytes[i] & 0xFF);
            if (b.length() == 1) {
                b = "0" + b;
            }
            result += b;
        }
        return result.toUpperCase();
    }
    
    public static boolean checkResultDataValid(byte[] resultData){
        boolean valid=true;
        String result=new String(resultData);
        if(result.startsWith("<Error>")){
            valid=false;
        }
        return valid;
    }

    
}
