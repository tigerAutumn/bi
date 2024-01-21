package com.pinting.gateway.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pinting.core.exception.PTException;
import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.enums.PTMessageEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

import static com.pinting.gateway.util.JsonProcessUtils.json2SingleObject;

/**
 * by shark 2016/11/04
 */
public class HttpJsonConverter {
    private static Logger log = LoggerFactory.getLogger(HttpJsonConverter.class);

    private static final int TIMEOUT = 30000;

    public static <T> T getInstanceForUrl(String url, Object input, Class<T> outputType, String methodType) {
        try {
            String json = JsonProcessUtils.object2Json(input);
            log.info("http-request: " + json);
            String result = "";
            if("GET".equals(methodType)){
                HashMap<String, String> params = json2SingleObject(json, HashMap.class);
                result = NetUtils.sendDataByGet(url, params, TIMEOUT, null );
            }else if("DELETE".equals(methodType)){
                HashMap<String, String> params = json2SingleObject(json, HashMap.class);
                result = NetUtils.sendDataByDelete(url, params, TIMEOUT, null );
            }else if("POST".equals(methodType)){
                result = NetUtils.sendJsonDataByPost(url,json,NetUtils.ENC_UTF8,TIMEOUT);
            }else if("PUT".equals(methodType)){
                result = NetUtils.sendJsonDataByPut(url,json,NetUtils.ENC_UTF8,TIMEOUT);
            }
            log.info("http-response: " + result);
            String statusCode = result.substring(0, 3);
            String content = result.substring(3);
            if(HttpStatus.SC_OK == Integer.valueOf(statusCode)){
                return json2SingleObject(content,outputType);
            }else if(HttpStatus.SC_INTERNAL_SERVER_ERROR == Integer.valueOf(statusCode)){
                if(StringUtils.isNotEmpty(content)){
                    ResMsg rsp = (ResMsg) json2SingleObject(content,outputType);
                    if(StringUtils.isNotEmpty(rsp.getResCode())){
                        throw new PTException(rsp.getResCode(), rsp.getResMsg());
                    }else{
                        throw new PTException(PTMessageEnum.SYSTEM_ERROR.getCode(), PTMessageEnum.SYSTEM_ERROR.getMessage());
                    }
                }else{
                    throw new PTException(PTMessageEnum.SYSTEM_ERROR.getCode(), PTMessageEnum.SYSTEM_ERROR.getMessage());
                }
            }else {
                throw new PTException(PTMessageEnum.SYSTEM_ERROR.getCode(), PTMessageEnum.SYSTEM_ERROR.getMessage());
            }
        } catch (JsonProcessingException e) {
            log.error("json转换失败");
            throw new PTException("json转换失败");
        } catch (IOException e) {
            log.error("io 异常");
            throw new PTException("io 异常");
        }
    }

}
