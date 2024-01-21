package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2016/9/2
 * Description: 根据渠道和支付类型查询银行信息的返回信息
 */
public class ResMsg_Bank_ChannelBank extends ResMsg {

    private static final long serialVersionUID = 2307951297824848446L;

    /* 当前支付通道支持的银行列表 */
    private List<HashMap<String, Object>> bankList;

    public List<HashMap<String, Object>> getBankList() {
        return bankList;
    }

    public void setBankList(List<HashMap<String, Object>> bankList) {
        this.bankList = bankList;
    }
}
