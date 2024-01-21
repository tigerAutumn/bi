package com.pinting.gateway.baofoo.out.util;

import com.pinting.core.util.GlobEnvUtil;
import com.pinting.gateway.util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 剑钊
 * @title 合作方宝付账户参数
 * @2016/10/19 10:26.
 */
public class PartnerBaoFooEnvUtil {

    public static Map<String,PartnerBaoFooEnv> transferEnvMap=new HashMap<String, PartnerBaoFooEnv>(){
        {
            PartnerBaoFooEnv transferEnv=new PartnerBaoFooEnv();
            transferEnv.setMemberIdTo(GlobEnvUtil.get("zan.baofoo.member.id"));
            transferEnv.setPay4TerminalId(GlobEnvUtil.get("zan.baofoo.pay4.terminal.id"));
            transferEnv.setTerminalId(GlobEnvUtil.get("zan.baofoo.terminal.id"));
            transferEnv.setPfxPath(GlobEnvUtil.get("zan.baofoo.pfx.url"));
            transferEnv.setPay4CerPath(GlobEnvUtil.get("zan.baofoo.pay4.cer.url"));
            transferEnv.setPfxPwd(GlobEnvUtil.get("zan.baofoo.pfx.pwd"));
            transferEnv.setMd5Key(GlobEnvUtil.get("zan.baofoo.counter.md5.key"));
            transferEnv.setRemarks("赞分期宝付参数");
            put(Constants.THIRD_SYS_CHANNEL_ZAN,transferEnv);
            

        }
    };
}
