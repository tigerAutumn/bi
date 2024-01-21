package com.pinting.gateway.baofoo.out.util;

import com.pinting.core.util.GlobEnvUtil;
import com.pinting.gateway.mobile.in.util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 剑钊 on 2016/11/2.
 */
public class TransAccountUtil {

    public static Map<String,TransAccount> transferEnvMap=new HashMap<String, TransAccount>(){
        {
            TransAccount transferEnv=new TransAccount();
            transferEnv.setAccountTo(GlobEnvUtil.get("dafy.acctTrans.accountTo"));
            transferEnv.setMemberIdTo(GlobEnvUtil.get("dafy.acctTrans.memberIdTo"));
            transferEnv.setToAcctName(GlobEnvUtil.get("dafy.acctTrans.nameTo"));
            put(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI,transferEnv);

            TransAccount transferEnv7=new TransAccount();
            transferEnv7.setAccountTo(GlobEnvUtil.get("7dai.acctTrans.accountTo"));
            transferEnv7.setMemberIdTo(GlobEnvUtil.get("7dai.acctTrans.memberIdTo"));
            transferEnv7.setToAcctName(GlobEnvUtil.get("7dai.acctTrans.nameTo"));
            put(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI,transferEnv7);

            TransAccount transferEnvZan=new TransAccount();
            transferEnvZan.setAccountTo(GlobEnvUtil.get("zan.acctTrans.accountTo"));
            transferEnvZan.setMemberIdTo(GlobEnvUtil.get("zan.acctTrans.memberIdTo"));
            transferEnvZan.setToAcctName(GlobEnvUtil.get("zan.acctTrans.nameTo"));
            put(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN,transferEnvZan);

            TransAccount transferEnvZsd=new TransAccount();
            transferEnvZsd.setAccountTo(GlobEnvUtil.get("zsd.acctTrans.accountTo"));
            transferEnvZsd.setMemberIdTo(GlobEnvUtil.get("zsd.acctTrans.memberIdTo"));
            transferEnvZsd.setToAcctName(GlobEnvUtil.get("zsd.acctTrans.nameTo"));
            put(Constants.PRODUCT_PROPERTY_SYMBOL_ZSD,transferEnvZsd);

            TransAccount transferEnvMain=new TransAccount();
            transferEnvMain.setAccountTo(GlobEnvUtil.get("baofoo.member.acc.no"));
            transferEnvMain.setMemberIdTo(BaoFooHttpUtil.memberId);
            transferEnvMain.setToAcctName(GlobEnvUtil.get("baofoo.member.acc.name"));
            put(Constants.PRODUCT_PROPERTY_SYMBOL_MAIN,transferEnvMain);

        }
    };
}
