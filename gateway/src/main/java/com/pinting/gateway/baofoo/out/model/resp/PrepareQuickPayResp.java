package com.pinting.gateway.baofoo.out.model.resp;

/**
 * Created by 剑钊 on 2016/7/19.
 */
public class PrepareQuickPayResp extends BaoFooBaseResp {

    /**
     * 宝付业务流水号
     */
    private String business_no;

    public String getBusiness_no() {
        return business_no;
    }

    public void setBusiness_no(String business_no) {
        this.business_no = business_no;
    }
}
