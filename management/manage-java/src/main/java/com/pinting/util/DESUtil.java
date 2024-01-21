package com.pinting.util;

import com.pinting.core.util.encrypt.BASE64Encoder;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;
  
  
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
    }
 }  