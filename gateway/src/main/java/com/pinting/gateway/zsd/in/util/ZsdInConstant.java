package com.pinting.gateway.zsd.in.util;

import com.pinting.core.util.GlobEnvUtil;
import com.pinting.gateway.util.Constants;

/**
 * 
 * @project gateway
 * @title ZsdInConstant.java
 * @author Dragon & cat
 * @date 2017-9-5
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class ZsdInConstant {
	/*客户端标识*/
    public static String CLIENTKEY = "ZSD_YI79VweHF57OSI5IEBO0";
    /*客户端密码*/
    public static String CLIENTSECRET = "YTGO53LJNsaRF66q78wre";
    
    /*DES KEY*/
    public static String DESKEY = "CHANNEL4ZSD";
    
    
    static {
        if (Constants.GATEWAY_SERVER_MODE_PROD.equals(GlobEnvUtil.get("server.mode"))||Constants.GATEWAY_SERVER_MODE_TEST.equals(GlobEnvUtil.get("server.mode")) ) {
            try {
            	CLIENTKEY = GlobEnvUtil.get("zsd.in.clientKey");
            	CLIENTSECRET = GlobEnvUtil.get("zsd.in.clientSecret");
            	DESKEY = GlobEnvUtil.get("zsd.in.desKey");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
    
}
