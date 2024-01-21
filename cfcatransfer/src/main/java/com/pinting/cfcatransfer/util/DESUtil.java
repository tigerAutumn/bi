package com.pinting.cfcatransfer.util;

import java.security.Key;  
import java.security.SecureRandom;  
  
import javax.crypto.Cipher;  
import javax.crypto.KeyGenerator;  
  
import sun.misc.BASE64Decoder;  
  
  
public class DESUtil {  
    Key key;  
  
    public DESUtil() {  
  
    }  
  
    public DESUtil(String str) {  
        setKey(str); // 生成密匙  
    }  
  
    public Key getKey() {  
        return key;  
    }  
  
    public void setKey(Key key) {  
        this.key = key;  
    }  
  
   /** 
     * 根据参数生成 KEY   
    */  
    public void setKey (String strKey) {  
        try {  
            KeyGenerator _generator = KeyGenerator.getInstance("DES");  
            //防止linux下 随机生成key   
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );  
            secureRandom.setSeed(strKey.getBytes());  
              
            _generator.init(56,secureRandom);  
            this.key = _generator.generateKey();  
            _generator = null;  
        } catch (Exception e) {  
            throw new RuntimeException(  
                    "Error initializing  class. Cause: " + e);  
        }  
    }
  
   /** 
     * 根据参数生成 KEY      
     */  
    /*public void setKey(String strKey) { 
        try { 
            KeyGenerator _generator = KeyGenerator.getInstance("DES"); 
            _generator.init(new SecureRandom(strKey.getBytes())); 
            this.key = _generator.generateKey(); 
            _generator = null; 
        } catch (Exception e) { 
            throw new RuntimeException( 
                    "Error initializing  class. Cause: " + e); 
        } 
    }*/
  
    /** 
     * 加密 String 明文输入 ,String 密文输出 
     */  
    public String encryptStr(String strMing) {  
        byte[] byteMi = null;  
        byte[] byteMing = null;  
        String strMi = "";  
        try {  
            byteMing = strMing.getBytes("UTF8");  
            byteMi = this.encryptByte(byteMing);  
            strMi = BASE64Encoder.encode(byteMi);  
        } catch (Exception e) {  
            throw new RuntimeException(  
                    "Error initializing  class. Cause: " + e);  
        } finally {  
            byteMing = null;  
            byteMi = null;  
        }  
        return strMi;  
    }  
  
    /** 
     * 解密 以 String 密文输入 ,String 明文输出 
     * 
     * @param strMi 
     * @return 
     */  
    public String decryptStr(String strMi) {  
        BASE64Decoder base64De = new BASE64Decoder();  
        byte[] byteMing = null;  
        byte[] byteMi = null;  
        String strMing = "";  
        try {  
            byteMi = base64De.decodeBuffer(strMi);  
            byteMing = this.decryptByte(byteMi);  
            strMing = new String(byteMing, "UTF8");  
        } catch (Exception e) {  
            throw new RuntimeException(  
                    "Error initializing  class. Cause: " + e);  
        } finally {  
            base64De = null;  
            byteMing = null;  
            byteMi = null;  
        }  
        return strMing;  
    }  
  
    /** 
     * 加密以 byte[] 明文输入 ,byte[] 密文输出 
     * 
     * @param byteS 
     * @return 
     */  
    private byte[] encryptByte(byte[] byteS) {  
        byte[] byteFina = null;  
        Cipher cipher;  
        try {  
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");  
            cipher.init(Cipher.ENCRYPT_MODE, key);  
            byteFina = cipher.doFinal(byteS);  
        } catch (Exception e) {  
            throw new RuntimeException(  
                    "Error initializing  class. Cause: " + e);  
        } finally {  
            cipher = null;  
        }  
        return byteFina;  
    }  
  
    /** 
     * 解密以 byte[] 密文输入 , 以 byte[] 明文输出 
     * 
     * @param byteD 
     * @return 
     */  
    private byte[] decryptByte(byte[] byteD) {  
        Cipher cipher;  
        byte[] byteFina = null;  
        try {  
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");  
            cipher.init(Cipher.DECRYPT_MODE, key);  
            byteFina = cipher.doFinal(byteD);  
        } catch (Exception e) {  
            throw new RuntimeException(  
                    "Error initializing  class. Cause: " + e);  
        } finally {  
            cipher = null;  
        }  
        return byteFina;  
    }  
   
    public static void main(String[] args) throws Exception {  
        DESUtil des = new DESUtil("dafy77777770dafy.com1,.*");  
        // DES 加密文件  
        // des.encryptFile("G:/test.doc", "G:/ 加密 test.doc");  
        // DES 解密文件  
        // des.decryptFile("G:/ 加密 test.doc", "G:/ 解密 test.doc");  
        String str1 = "pf1I33atL7skvOc1kvJ3jMv2ZlWb2U5LxG6lm2htFHgEY01pZ1ba71j7rv2Ba1vSoWgqzpa3uIBnx/FoTRphohBfdhy3hA6wF2f0I8LEh+YlmmK78YMyzwoygSiUrFxhgfiAw4ccCWSijGT49fFMODh/MBVo8BaRBER/X09h56WEzga+woMG28bfyxFzwIXqlVQsVUNZckxnNr8BnDn5fJtdWSDfsUEq3PqVRFHOjRdFzWGcTxUxZA==";  
        // DES 加密字符串  
//        String str2 = des.encryptStr(str1);  
        // DES 解密字符串  
        String deStr = des.decryptStr(str1);  
//        System.out.println(" 加密前： " + str1);  
//        System.out.println(" 加密后： " + str2);  
//        System.out.println(" 加密后长度： " + str2.length());  
//        System.out.println(" 解密后： " + deStr);  
        System.out.println(deStr);
    }  
 }  