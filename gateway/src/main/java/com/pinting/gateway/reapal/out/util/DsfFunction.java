package com.pinting.gateway.reapal.out.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang.ArrayUtils;

import com.pinting.gateway.reapal.out.config.ReapalConfig;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DsfFunction {

    public static String BuildMysign(Map<String,String> sArray, String key) {
        String prestr = CreateLinkString(sArray);  //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        prestr = prestr + key;                     //把拼接后的字符串再与安全校验码直接连接起来
        System.out.println("签名摘要明文："+prestr);
        String mysign = Md5Encrypt.md5(prestr);
        System.out.println("签名摘要密文："+mysign);
        return mysign;
    }

    /**
     * 功能：除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String,String> ParaFilter(Map<String,String> sArray){
        List<String> keys = new ArrayList<String>(sArray.keySet());
        Map<String,String> sArrayNew = new HashMap<String,String>();
        for(int i = 0; i < keys.size(); i++){
            String key = (String) keys.get(i);
            String value = (String) sArray.get(key);
            if(value == null || value.equals("") || 
                       key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("signType")){
            	continue;
            }
            sArrayNew.put(key, value);
        }

        return sArrayNew;
    }
    /**
     * 功能：把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String CreateLinkString(Map<String,String> params){
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = (String) params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符o
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
    
    /**
     * @Title: jm
     * @author Eric bjwdong@cn.ibm.com
     * @Description: 加密
     * @param @param content
     * @param @param pa
     * @param @return
     * @param @throws UnsupportedEncodingException
     * @param @throws CertificateException
     * @param @throws FileNotFoundException
     * @param @throws NoSuchAlgorithmException
     * @param @throws NoSuchPaddingException
     * @param @throws InvalidKeyException
     * @param @throws IllegalBlockSizeException
     * @param @throws BadPaddingException
     * @return String
     * @throws
     */
    public static String jm(String content,String cerCertPath) throws UnsupportedEncodingException, CertificateException, FileNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        byte[] msg = content.getBytes("GBK");     // 待加解密的消息
        // 用证书的公钥加密
        CertificateFactory cff = CertificateFactory.getInstance("X.509");
        FileInputStream fis1 = new FileInputStream(cerCertPath); // 证书文件
        Certificate cf = cff.generateCertificate(fis1);
        PublicKey pk1 = cf.getPublicKey();           // 得到证书文件携带的公钥
        Cipher c1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");      // 定义算法：RSA
        byte[] dataReturn=null;
        c1.init(Cipher.PUBLIC_KEY, pk1);
        //StringBuilder sb = new StringBuilder();
        for (int i = 0; i < msg.length; i += 100) {
            byte[] doFinal = c1.doFinal(ArrayUtils.subarray(msg, i, i + 100));

            //sb.append(new String(doFinal,"gbk"));
            dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
        }

        BASE64Encoder encoder = new BASE64Encoder();

        String afjmText=encoder.encode(dataReturn);
        System.out.println("加密开始。。。。。。");
        System.out.println(afjmText);
        return afjmText;
    }
    /**
     * @Title: jim
     * @author Eric bjwdong@cn.ibm.com
     * @Description: 解密
     * @param @param dataReturn_r
     * @param @param pa
     * @param @return
     * @param @throws Exception
     * @return String
     * @throws
     */
    public static String jim(byte [] dataReturn_r,String pa) throws Exception{
        //final String KEYSTORE_FILE = pa+"WEB-INF"+File.separator+"key"+File.separator+"clientok.p12";
        System.out.println("解密开始。。。。。。");
        final String KEYSTORE_FILE = ReapalConfig.privateKey;
        final String KEYSTORE_PASSWORD = ReapalConfig.password;
        final String KEYSTORE_ALIAS = ReapalConfig.privateKeyAlias;

        KeyStore ks = KeyStore.getInstance("PKCS12");
        FileInputStream fis = new FileInputStream(KEYSTORE_FILE);

        char[] nPassword = null;
        if((KEYSTORE_PASSWORD == null)||KEYSTORE_PASSWORD.trim().equals("")){
            nPassword = null;
        }else{
            nPassword = KEYSTORE_PASSWORD.toCharArray();
        }
        ks.load(fis,nPassword);
        fis.close();
        PrivateKey prikey = (PrivateKey)ks.getKey(KEYSTORE_ALIAS, nPassword);
        Cipher rc2 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rc2.init(Cipher.DECRYPT_MODE, prikey);
//		    byte[] rmsg2 = rc2.doFinal(dataReturn_r); // 解密后的数据，不能超过128个字节
        StringBuilder bf_r = new StringBuilder();
        byte [] bs = null;
        for (int i = 0; i < dataReturn_r.length; i += 256) {
            byte[] subarray = ArrayUtils.subarray(dataReturn_r, i,i + 256);
            byte[] doFinal = rc2.doFinal(subarray);
            bs = ArrayUtils.addAll(bs, doFinal);
            //bf_r.append(new String(doFinal,ReapalConfig.input_charset));
        }
        bf_r.append(new String(bs,ReapalConfig.input_charset));
        System.out.println("解密后明文：【" + bf_r.toString() + "】");
        return bf_r.toString();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
    	String str = "FehzzK1wfQOlz8LnH9Xbb6BNaloFP7kVqBxEvZGtEUGWlbgL4l4uOp3NzlZ0/mqqgzF+CC9aJgyX1q+7a18npAvEOhnJ0rAD6xFRzpWfee9NGRvM+/gLgB7Xf0kqauY7V0bMSbWHKR+KjhIlIn8MYgWHLnF1zBjN7dR3bDbDaeM6/sa9/hkx1TyNYGc2pQG5TpFNTo8uYLPE0BixmTkcQHsqh6QWqro0Hli3bSC0mUyZXC1fKsAVF0jS14CJ3JQ1DjKFbuUrtLg0Grev8W05rH77l0WE59fVmujkfmLofm0V/flqsz3pud6RafyQzkDxWbgBGMTld/Yzj00q0U3EzaZCmrZcsBPzGg1m8m9IO6J89ZtURYSQIihLWvzStABbdv59ZeRgkjScwUY0IyfpByLFrWGbSVnFuy64d5b/4rfgYeBNNKq9p5nEtDX6AAW22AB447y+wzgYu8yclOBmQJAMbWYBKRh04tX+mH0jnWLZwIPvfarjcelALFrXeIdqyYShDGPLLmU0llRswDBV6/ze46X4rb2Olj3rbXDOqC1i9DawZCvE+PxfD2FKKEzgWu5uc62N1TkZJYLH1fT0bHRNzVrex38UEcHzZMrpapMPFcuDHP1zoReJy3OW2oALaXpo7+eSqQ5uBwQ2wrWX7hmK5drrU3eHsAz9I4ePmnE2F+PXu4Ug/xEsN84nGTBJKpjl2JEZMzW1n8KFhxmrI+iXpYTqbgvlHmTuvncEZ4Z6GRx5ELv0yorDqPJDPsiU+8wUFlC1/t6DA66Y2Td77wGXJtCDAWydmy1aNv1n+TmMqXFmHOWZvWAe2mDVDfU1gva8p7V9MAq6PPyZJur9JLzi6aEvkRN33GxMotVb8cUsnMa6C9NrJCYbOCRwL1snjS/mol1U64rPrXAYM05HUpT2ZBTotAGmpBhfOr5EbT1OObQ0FefKHnVLjm8Zf2aPfFRVsSnd4CTPhUwCx3f90MXBwO7v6W+fgsynRmm2tPP2bBKs+80gmHFPBKz/lH2mlvO+BAurz6IjnDEGCooiaJuAtatRimdOCONaRdqKOZmQycYJplEhck6RcMDa5Ngt4a//GRJl1QB2+5Kps4VCmpGeEYyyyuwPRcANLNUw7gJPYUvRpbrVsXXuZADnqOoPIs+bC2U5ZLG846fIed33XB6zOytxnoP7Sszbm4MK/3pT1feMFkdemEs9cRl9C/LUenMXdJMV1eF6xaQI8mmBKaGZJD7UUCPdS1Y5H/NT2iRVQrXM1dr7u5mAcOupo0sCM2KtRk2Z4GWv1+rCQp8phj3pl3wrg4R56jxiFxgCNYkxR7HkisGdIKnZM8D4vTI5BxMnD9zkEr5xOZuq3GIkNg==";
    	BASE64Decoder decoder = new BASE64Decoder();
        String ss;
		try {
			byte[] bb = decoder.decodeBuffer(str);
			ss = DsfFunction.jim(bb, "");
			System.out.println(ss);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("=====================================");
		
		String str2=
				"<Resp>"
				+ "<_input_charset>utf-8</_input_charset>"
				+ "<batchBizid>100000000069033</batchBizid>"
				+ "<batchVersion>00</batchVersion>"
				+ "<batchDate>20151023</batchDate>"
				+ "<batchCurrnum>11</batchCurrnum>"
				+ "<batchStatus>22</batchStatus>"
				+ "<batchContent></batchContent>"
				+ "<sign>d2dfd1e4f2f933dff5f6db670a6905fe</sign>"
				+"<signType>MD5</signType>"
				+ "</Resp>"
				;
	    try {
			jm(str2,ReapalConfig.publicKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
