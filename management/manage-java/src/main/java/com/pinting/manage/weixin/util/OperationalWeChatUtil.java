package com.pinting.manage.weixin.util;

import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Component
public class OperationalWeChatUtil {
	private static Logger log = LoggerFactory.getLogger(OperationalWeChatUtil.class);
	public static final String ENCODE      = "UTF-8";
	public static String       timestamp   = "";
	public static String       signature   = "";
	public static String       nonceStr    = "";
	public static String appid = "";
   
    //String getAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
	private static final String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token";//获取token的链接
	
	
    /**
     * 	创建权限校验信息
    		appId: '', // 必填，公众号的唯一标识
    		timestamp: , // 必填，生成签名的时间戳
    		nonceStr: '', // 必填，生成签名的随机串
    		signature: '',// 必填，签名，见附录1
     * @param request
     * @return
     */
    public  Map<String, String> createAuth(HttpServletRequest request) {
    	if(StringUtil.isEmpty(appid)) {
            Map<String,Object> map = getTokenAndTicket();
            appid = map.get("appid") == null ? "" : map.get("appid").toString();
        }

        String url = request.getRequestURL().toString();
        try{//针对当前配置（一般为https)，保持url和访问的一致
        	String webUrl = GlobEnvUtil.getWebURL("");//本网配置的微网地址
        	url = url.replace(new URL(url).getHost(), new URL(webUrl).getHost());
        	url = url.replace(new URL(url).getProtocol(), new URL(webUrl).getProtocol());
        }catch (Exception e) {
        	e.printStackTrace();
		}
        
        if (request.getQueryString() != null && !request.getQueryString().equals("")) {
            url += "?" + request.getQueryString();
        }

        if (url.equals(OperationalWeChatUtil.tokenUrl)) {
            url += "/";
        }

        Map<String,Object> map = getTokenAndTicket();
        //String accessToken = map.get("accessToken").toString();
       
        String jsapiTicket = map.get("ticket") == null ? "" : map.get("ticket").toString();
        
        Map<String, String> sign =  new HashMap<String, String>();
        
        nonceStr = create_nonce_str();
        timestamp = create_timestamp();
        
        String str;
        //注意这里参数名必须全部小写，且必须有序
        str = "jsapi_ticket=" + jsapiTicket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url;

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(str.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        sign.put("nonceStr", nonceStr);
        sign.put("timestamp", timestamp);
        sign.put("signature", signature);
        sign.put("appId", appid);
        
        return sign;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
    
    private  Map<String,Object> getTokenAndTicket() {
    	
    	String appId = com.pinting.business.util.OperationalWeChatUtil.appid;
    	String secret = com.pinting.business.util.OperationalWeChatUtil.secret;
        String accessToken = com.pinting.business.util.OperationalWeChatUtil.getAccessToken(null, appId, secret);
        String ticket = com.pinting.business.util.OperationalWeChatUtil.getJsapiTicket(null, appId, secret);
        log.info("s-ticket:"+ticket);
        log.info("s-appid:"+com.pinting.business.util.OperationalWeChatUtil.appid);
        log.info("s-secret:"+com.pinting.business.util.OperationalWeChatUtil.secret);
        log.info("s-token:"+com.pinting.business.util.OperationalWeChatUtil.token);
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("ticket", ticket);
        map.put("appid", com.pinting.business.util.OperationalWeChatUtil.appid);
        map.put("secret", com.pinting.business.util.OperationalWeChatUtil.secret);
        map.put("token", com.pinting.business.util.OperationalWeChatUtil.token);
        return map;
    }
    
    /**
     * 验证签名
     * 
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public boolean checkSignature(String signature, String timestamp, String nonce) {
    	Map<String,Object> map = getTokenAndTicket();
        String token = map.get("token").toString();
    	
    	String[] arr = new String[] { token, timestamp, nonce };
        // 将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        content = null;
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }
    
    /**
     * 将字节数组转换为十六进制字符串
     * 
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     * 
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
                'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }
    
    /**
     * 发送 HTTP GET 请求
     * @param url
     *          请求的 URL 地址
     * @param param
     *          请求的参数内容
     * @param isForm
     *          是否是form请求
     * @param encode
     *          编码方式，null为默认编码
     * @return
     * @throws IOException 
     */
    public static String sendGet(String url, String param, boolean isForm, String encode)
                                                                                         throws IOException {
        String result = "";

        // 打开连接，并设置参数
        URL httpUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
        conn.setDoInput(true); // 设置可输入
        conn.setDoOutput(true);// 设置可输出
        conn.setRequestMethod("GET");//设置请求方式为GET
        if (isForm)
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        conn.connect();

        // 输出
        write(conn.getOutputStream(), param, encode);
        conn.getOutputStream().flush();
        conn.getOutputStream().close();

        //输入
        result = read(conn.getInputStream(), 1024, ENCODE);
        conn.getInputStream().close();

        // 关闭连接
        conn.disconnect();

        return result;
    }

    /**
     * 发送 HTTP POST 请求
     * @param url
     *          请求的 URL 地址
     * @param param
     *          请求的参数内容
     * @param isForm
     *          是否是form请求
     * @param encode
     *          编码方式，null为默认编码
     * @return
     * @throws IOException 
     */
    public String sendPost(String url, String param, boolean isForm, String encode)
                                                                                          throws IOException {
        String result = "";

        // 打开连接，并设置参数
        URL httpUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
        conn.setDoInput(true); // 设置可输入
        conn.setDoOutput(true);// 设置可输出
        conn.setRequestMethod("POST");//设置请求方式为POST
        if (isForm)
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        conn.connect();

        // 输出
        write(conn.getOutputStream(), param, encode);
        conn.getOutputStream().flush();
        conn.getOutputStream().close();

        //输入
        result = read(conn.getInputStream(), 1024, ENCODE);
        conn.getInputStream().close();

        // 关闭连接
        conn.disconnect();

        return result;
    }

    /**
     * 指定输入流和编码方式输入指定信息
     * @param out
     * @param msg
     * @param encode
     * @throws IOException 
     */
    public static void write(OutputStream out, String msg, String encode) throws IOException {
        byte[] b = msg.getBytes(encode);
        out.write(b);
        out.flush();
    }

    /**
     * 指定缓存大小，编码方式读取输入流
     * @param in
     *          输入流
     * @param length
     *          缓存大小
     * @param encode
     *          编码方式
     * @return
     * @throws IOException
     */
    public static String read(InputStream in, int length, String encode) throws IOException {
        StringBuilder result = new StringBuilder();

        byte[] b = new byte[length];
        int len = -1;
        while (-1 != (len = in.read(b)))
            result.append(new String(b, 0, len, encode));

        return result.toString();
    }



    /**
     * 根据内容类型判断文件扩展名
     *
     * @param contentType 内容类型
     * @return
     */
    private static String getFileExpandedName(String contentType) {
        String fileEndWitsh = "";
        log.info("文件格式 {} ", contentType);
        if ("image/jpeg".equals(contentType))
            fileEndWitsh = ".jpg";
        if ("image/jpg".equals(contentType))
            fileEndWitsh = ".jpg";
        else if("image/png".equals(contentType))
            fileEndWitsh = ".png";
        else if("image/gif".equals(contentType))
            fileEndWitsh = ".gif";
        else if("image/x-png".equals(contentType))
            fileEndWitsh = ".png";
        else if ("image/bmp".equals(contentType))
            fileEndWitsh = ".bmp";
        log.info("文件后缀 {} ", fileEndWitsh);
        return fileEndWitsh;
    }

    /**
     *
     * 根据文件id下载文件
     */
    private static InputStream getInputStream(HttpURLConnection http) {
        InputStream is = null;
        try {
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.connect();
            // 获取文件转化为byte流
            is = http.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;

    }

    /**
     *
     * 获取下载图片信息（jpg）
     * @param mediaId 文件的id
     *
     * @throws Exception
     */
    public String saveImageToDisk(String mediaId, String picName, String basePath) throws Exception {
        Map<String,Object> map = getTokenAndTicket();
        String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + map.get("accessToken") + "&media_id=" + mediaId;
        URL urlGet = new URL(url);
        HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
        InputStream inputStream = getInputStream(http);
        log.info(http.toString());
        log.info(inputStream.toString());
        String fileExt = getFileExpandedName(http.getHeaderField("Content-Type"));
        if(StringUtil.isBlank(fileExt)) {
            throw new Exception("暂不支持当前文件格式");
        }

        byte[] data = new byte[10240];
        int len;
        FileOutputStream fileOutputStream = null;
        // 生成空文件
        File file = new File(basePath + File.separator + picName + fileExt);
        //判断目标文件所在的目录是否存在
        if(!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        fileOutputStream = new FileOutputStream(basePath + File.separator + picName + fileExt);
        while ((len = inputStream.read(data)) != -1) {
            fileOutputStream.write(data, 0, len);
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return picName + fileExt;
    }
}
