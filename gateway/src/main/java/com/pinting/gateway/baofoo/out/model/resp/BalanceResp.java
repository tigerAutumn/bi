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
public class BalanceResp<T>{

    private static Logger log = LoggerFactory.getLogger(BalanceResp.class);

    private BalanceBaseResp<T> trans_content;

    public BalanceBaseResp<T> getTrans_content() {
        return trans_content;
    }

    public void setTrans_content(BalanceBaseResp<T> trans_content) {
        this.trans_content = trans_content;
    }


    public Object str2Obj(String respStr) throws IOException {

        log.info("===============================================宝付返回值："+respStr+"==========================================================");
        if (StringUtils.isBlank(respStr) || respStr.isEmpty()) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
        }
        return JSON.parseObject(respStr,this.getClass());
    }
}
