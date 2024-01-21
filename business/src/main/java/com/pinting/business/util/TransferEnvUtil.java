package com.pinting.business.util;

import com.pinting.business.accounting.loan.enums.PartnerEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 剑钊 on 2016/7/14.
 */
public class TransferEnvUtil {

    public static Map<String,TransferEnv> transferEnvMap=new HashMap<String, TransferEnv>(){
        {
            TransferEnv transferEnv=new TransferEnv();
//            transferEnv.setAccountFrom(GlobEnvUtil.get("dafy.acctTrans.accountFrom"));
//            transferEnv.setAccountTo(GlobEnvUtil.get("dafy.acctTrans.accountTo"));
//            transferEnv.setMemberIdTo(GlobEnvUtil.get("dafy.acctTrans.memberIdTo"));
//            transferEnv.setToAcctName(GlobEnvUtil.get("dafy.acctTrans.nameTo"));
            transferEnv.setTransTarget("币港湾转云贷");
            transferEnv.setSysSubAccountREG("REG_YUN");
            transferEnv.setSysSubAccountRETURN("RETURN_YUN");
            transferEnv.setRemarks("币港湾转账到达飞-用于结算");//因为之前就是这个文字，所以不把达飞改为云贷，保持一致
//			transferEnv.setNotifyUrl(GlobEnvUtil.get("19pay.notice.accttrans"));
            put(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI,transferEnv);

            transferEnv.setTransTarget("币港湾转云贷");
            transferEnv.setSysSubAccountREG("REG_YUN");
            transferEnv.setSysSubAccountRETURN("RETURN_YUN");
            transferEnv.setRemarks("币港湾转账到达飞-用于结算");//因为之前就是这个文字，所以不把达飞改为云贷，保持一致
//			transferEnv.setNotifyUrl(GlobEnvUtil.get("19pay.notice.accttrans"));
            put(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF,transferEnv);

            TransferEnv transferEnv7=new TransferEnv();
//            transferEnv7.setAccountFrom(GlobEnvUtil.get("7dai.acctTrans.accountFrom"));
//            transferEnv7.setAccountTo(GlobEnvUtil.get("7dai.acctTrans.accountTo"));
//            transferEnv7.setMemberIdTo(GlobEnvUtil.get("7dai.acctTrans.memberIdTo"));
//            transferEnv7.setToAcctName(GlobEnvUtil.get("7dai.acctTrans.nameTo"));
//			transferEnv.setNotifyUrl(GlobEnvUtil.get("19pay.notice.accttrans"));
            transferEnv7.setTransTarget("币港湾转7贷");
            transferEnv7.setSysSubAccountREG("REG_7");
            transferEnv7.setSysSubAccountRETURN("RETURN_7");
            transferEnv7.setRemarks("币港湾转账到7贷-用于结算");
            put(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI,transferEnv7);

            TransferEnv transferEnvZan=new TransferEnv();
//            transferEnvZan.setAccountFrom(GlobEnvUtil.get("zan.acctTrans.accountFrom"));
//            transferEnvZan.setAccountTo(GlobEnvUtil.get("zan.acctTrans.accountTo"));
//            transferEnvZan.setMemberIdTo(GlobEnvUtil.get("zan.acctTrans.memberIdTo"));
//            transferEnvZan.setToAcctName(GlobEnvUtil.get("zan.acctTrans.nameTo"));
//			transferEnv.setNotifyUrl(GlobEnvUtil.get("19pay.notice.accttrans"));
            transferEnvZan.setTransTarget("币港湾转赞分期");
            transferEnvZan.setSysSubAccountREG("REG_ZAN");
            transferEnvZan.setSysSubAccountRETURN("RETURN_ZAN");
            transferEnvZan.setRemarks("币港湾转账到赞分期-用于结算");
            put(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN,transferEnvZan);
            
            TransferEnv transferEnvZsd = new TransferEnv();
//          transferEnvZan.setAccountFrom(GlobEnvUtil.get("zan.acctTrans.accountFrom"));
//          transferEnvZan.setAccountTo(GlobEnvUtil.get("zan.acctTrans.accountTo"));
//          transferEnvZan.setMemberIdTo(GlobEnvUtil.get("zan.acctTrans.memberIdTo"));
//          transferEnvZan.setToAcctName(GlobEnvUtil.get("zan.acctTrans.nameTo"));
//			transferEnv.setNotifyUrl(GlobEnvUtil.get("19pay.notice.accttrans"));
            transferEnvZsd.setTransTarget("币港湾转赞时贷");
            transferEnvZsd.setSysSubAccountREG("BGW_REG_ZSD");
            transferEnvZsd.setSysSubAccountRETURN("BGW_RETURN_ZSD");
            transferEnvZsd.setRemarks("币港湾转账到赞时贷-用于结算");
            put(Constants.PRODUCT_PROPERTY_SYMBOL_ZSD, transferEnvZsd);

            TransferEnv transferEnvSevenSelf = new TransferEnv();
            transferEnvSevenSelf.setTransTarget("币港湾转7贷");
            transferEnvSevenSelf.setSysSubAccountREG("BGW_REG_7");
            transferEnvSevenSelf.setSysSubAccountRETURN("BGW_RETURN_7");
            transferEnvSevenSelf.setRemarks("币港湾转账到7贷-用于结算");
            put(PartnerEnum.SEVEN_DAI_SELF.getCode(), transferEnvSevenSelf);
        }
    };
}
