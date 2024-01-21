/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.util;


/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: CipherUtil.java, v 0.1 2015-8-7 下午6:34:18 BabyShark Exp $
 */
public class Pay19CipherUtil {
    private final static String defaultDESKey = "51wenzi.com.hongchun";

    public static String getDefaultdeskey() {
        return defaultDESKey;
    }

    //    public static String encryptDES(String encryptdata) {
    //        return StringUtil.isEmpty(encryptdata) ? "" : Pay19CipherUtil.encryptData(encryptdata,
    //            defaultDESKey);
    //    }

    /**
     * @param encryptdata
     *            要加密的明码
     * @param encryptkey
     *            加密的密钥
     * @return 加密后的暗码
     * @throws Exception
     */
    public static String encryptData(String encryptdata, String encryptkey) {
        try {
            Pay19DESPlus desPlus = new Pay19DESPlus(encryptkey);
            return desPlus.encrypt(encryptdata);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return "";
    }

    /**
     * @param decryptdata
     *            要解密的暗码
     * @param decryptkey
     *            解密的密钥
     * @return 解密后的明码
     * @throws Exception
     */
    public static String decryptData(String decryptdata, String decryptkey) throws Exception {
        Pay19DESPlus desPlus = new Pay19DESPlus(decryptkey);
        return desPlus.decrypt(decryptdata);
    }

    public static void main(String[] args) throws Exception {
        //String decryData = CipherUtil.decryptData("175244","3XoqZLmBdeM9wHmluIEZ0rSt4yqzTa9exd6RybyxnlM8iKGTRomP8aPWpoTGmbiw2M0iZGwEGgbjAA1GqdYF98TMaiCAV9eh14wCVaQxmWtlFtwTVqT3phgQt66VN2NK");
        String decryData2 = Pay19CipherUtil.encryptData("175244", "51wenzi.com.hongchun");
//        System.out.println(decryData);
        System.out.println(decryData2);
        System.out.println(Pay19CipherUtil.decryptData("ef79d603a03b01d8", Pay19CipherUtil.getDefaultdeskey()));

    }
}
