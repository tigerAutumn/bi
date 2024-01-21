package com.pinting.gateway.bird.out.util;

import com.pinting.gateway.baofoo.out.enums.BaoFooTxnType;
import com.pinting.gateway.baofoo.out.model.req.BaoFooOutBaseReq;
import com.pinting.gateway.baofoo.out.model.resp.BaoFooBaseResp;
import com.pinting.gateway.baofoo.out.util.RsaCodingUtil;
import com.pinting.gateway.baofoo.out.util.SecurityUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.pay19.out.util.Pay19MessageUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by 剑钊 on 2016/7/18.
 */
public class BirdMessageUtil {

    private static Logger logger = LoggerFactory.getLogger(BirdMessageUtil.class);
    private final static String respBeanPre = "com.pinting.gateway.baofoo.out.model.resp.";

    /**
     * 内容参数对象转为map
     *
     * @param req
     * @param txnType 是否需要交易子类参数
     * @return
     */
    public static HashMap<String, String> transContentParams(BaoFooOutBaseReq req, boolean txnType) {

        HashMap<String, String> map = Pay19MessageUtil.transBeanMap(req);

//        map.put("terminal_id", BirdHttpUtil.quickterminalId);
//        map.put("member_id", BirdHttpUtil.memberId);
//        if (txnType) {
//            map.put("biz_type", BaoFooTxnType.BIZ_TYPE);
//            //商户流水号 8-20 位字母和数字，每次请求 都不可重复
//            //TODO  是否统一
//            map.put("trans_serial_no", System.currentTimeMillis() + "");
//        }

        return map;
    }

    /**
     * 公共请求参数
     *
     * @param map
     * @param txnType 是否需要交易类型
     * @return
     */
    public static HashMap<String, String> commonReqParam(HashMap<String, String> map, boolean txnType) {
//        map.put("version", BirdHttpUtil.version);
//        map.put("member_id", BirdHttpUtil.memberId);
//        map.put("data_type", BirdHttpUtil.dataType);
//        if (txnType) {
//            map.put("terminal_id", BirdHttpUtil.quickterminalId);
//            map.put("txn_type", BaoFooTxnType.TXN_TYPE);
//        } else {
//            map.put("terminal_id", BirdHttpUtil.pay4terminalId);
//        }
        return map;
    }

    /**
     * resp JSON 转对象
     *
     * @param respStr
     * @param className
     * @return
     */
    public static BaoFooBaseResp parseResp(String respStr, String className) throws IOException, ClassNotFoundException {

        //如果返回的数据不包含字符串“trans_content”,则需要解密
//        if (StringUtils.isNotBlank(respStr) && !respStr.contains("trans_content")) {
//            respStr = RsaCodingUtil.decryptByPubCerFile(respStr, BirdHttpUtil.quickcerpath);
//            //解密返回值
//            respStr = SecurityUtil.Base64Decode(respStr);
//        }
//
//        if (StringUtils.isBlank(respStr) || respStr.isEmpty()) {
//            throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
//        }
//
//        JSONObject jsonObject = JSONObject.fromObject(respStr);
//        System.out.println(respStr);
//        BaoFooBaseResp respBean = (BaoFooBaseResp) JSONObject.toBean(jsonObject, Class.forName(respBeanPre + className));
//        System.out.println(respBean.getResp_code());

        return null;
    }
}
