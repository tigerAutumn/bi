package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;
import java.util.List;

/**
 * Created by cyb on 2017/11/6.
 */
public class ResMsg_Activity_GiftNumber extends ResMsg {

    private static final long serialVersionUID = 7895893078371904761L;

    private List<HashMap<String, Object>> giftNumber;

    public List<HashMap<String, Object>> getGiftNumber() {
        return giftNumber;
    }

    public void setGiftNumber(List<HashMap<String, Object>> giftNumber) {
        this.giftNumber = giftNumber;
    }
}
