package com.dafy.controller.app;

import com.alibaba.fastjson.JSONObject;
import com.dafy.core.util.DESUtil;
import com.dafy.core.util.ParamUtil;
import com.dafy.core.util.encrypt.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 加密与解密处理
 */
@Controller
public class EncryptController {

    public final static String OPENDESKEY = "cfgubijn";

    @RequestMapping(value = "/app/encryptStr", method = RequestMethod.GET)
    @ResponseBody
        public String encryptStr(String DATA) {
        JSONObject jsonObj = JSONObject.parseObject(DATA);
        jsonObj.remove("signOpenApi");

        String text = ParamUtil.createLinkString(jsonObj);
        String signStr = MD5Util.encryptMD5(text); //MD5加密
        jsonObj.put("signOpenApi", signStr);
        Map<String,String> reqMap = new HashMap<>();
        reqMap.put("request_json_str_", jsonObj.toString());
        return JSONObject.toJSONString(reqMap);
//        return new DESUtil(OPENDESKEY).encryptStr(reqMap);
    }

    @RequestMapping(value = "/app/decryptStr", method = RequestMethod.GET)
    @ResponseBody
    public String decryptStr(String DATA) {
        return new DESUtil(OPENDESKEY).decryptStr(DATA);
    }

    public static void main (String[] args) {

        Map<String,Object> data = new HashMap<>();
        data.put("userId", 2000045);
        data.put("bankCardId", 14145);
        data.put("version", "v_1.0.0");
        data.put("timeOpenApi", "1531966648163");
        data.put("signOpenApi", "04f861277f41e7ca3c1720203efddce3");
        data.put("appVersion", "v_2.4.0");

        JSONObject jsonObj = JSONObject.parseObject(JSONObject.toJSONString(data));
        jsonObj.remove("signOpenApi");

        String text = ParamUtil.createLinkString(jsonObj);
        String signStr = MD5Util.encryptMD5(text); //MD5加密
        jsonObj.put("signOpenApi", signStr);
        Map<String,String> reqMap = new HashMap<String,String>();
        reqMap.put("request_json_str_", jsonObj.toString());
        System.out.println(JSONObject.toJSONString(reqMap));
        System.out.println(new DESUtil(OPENDESKEY).encryptStr(reqMap.toString()));
    }
}
