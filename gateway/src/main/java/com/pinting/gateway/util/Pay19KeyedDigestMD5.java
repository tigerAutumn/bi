package com.pinting.gateway.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Pay19KeyedDigestMD5 {
//    private final static String defaultMd5Key = "51wenzi.com.hongchun";
    public final static String  encode_UTF8   = "UTF-8";
    public final static String  encode_GBK    = "GBK";

//    public static String getDefaultmd5key() {
//        return defaultMd5Key;
//    }

    public static byte[] getKeyedDigest(byte[] buffer, byte[] key) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(buffer);
            return md5.digest(key);
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static String getKeyedDigest(String strSrc, String key) {
    	return getKeyedDigest(strSrc, key, "UTF8");
    }

    public static String getKeyedDigest(String strSrc, String key, String encode) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(strSrc.getBytes(encode));

            String result = "";
            byte[] temp;
            temp = md5.digest(key.getBytes(encode));
            for (int i = 0; i < temp.length; i++) {
                result += Integer.toHexString((0x000000ff & temp[i]) | 0xffffff00).substring(6);
            }

            return result;

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Pay19KeyedDigestMD5 md5 = new Pay19KeyedDigestMD5();
        String mi;
        String s = "version_id=1.1&merchant_id=6250&order_date=20070327&order_id=4057&amount=0.01&currency=RMB&returl=http://test.wangpiao.net/payment/19Pay_Return.aspx&pm_id=&pc_id=&merchant_key=123456789";
        mi = md5.getKeyedDigest(s, "");

        System.out.println("mi:" + mi);

    }

}
