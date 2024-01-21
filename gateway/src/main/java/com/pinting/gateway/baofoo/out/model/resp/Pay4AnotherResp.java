package com.pinting.gateway.baofoo.out.model.resp;

import com.alibaba.fastjson.JSON;
import com.pinting.gateway.baofoo.out.util.BaoFooHttpUtil;
import com.pinting.gateway.baofoo.out.util.PartnerBaoFooEnv;
import com.pinting.gateway.baofoo.out.util.RsaCodingUtil;
import com.pinting.gateway.baofoo.out.util.SecurityUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by 剑钊 on 2016/7/21.
 */
public class Pay4AnotherResp<T>{

    private static Logger log = LoggerFactory.getLogger(Pay4AnotherResp.class);

    private Pay4AnotherBaseResp<T> trans_content;

    public Pay4AnotherBaseResp getTrans_content() {
        return trans_content;
    }

    public void setTrans_content(Pay4AnotherBaseResp<T> trans_content) {
        this.trans_content = trans_content;
    }

    public Object str2Obj(String respStr,PartnerBaoFooEnv transferEnv) throws IOException {

        log.debug("===============宝付返回值解密前："+respStr+"===============");

        //如果返回的数据不包含字符串“trans_content”,则需要解密
        if (StringUtils.isNotBlank(respStr) && !respStr.contains("trans_content")) {

            if(transferEnv==null) {
                respStr = RsaCodingUtil.decryptByPubCerFile(respStr, BaoFooHttpUtil.pay4cerpath);
            }else {
                respStr=RsaCodingUtil.decryptByPubCerFile(respStr, transferEnv.getPay4CerPath());
            }
            //解密返回值
            respStr = SecurityUtil.Base64Decode(respStr);
        }

        log.info("===============宝付返回值解密结果："+respStr+"===============");
        if (StringUtils.isBlank(respStr) || respStr.isEmpty()) {
            throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
        }
        return JSON.parseObject(respStr,this.getClass());
    }
}
