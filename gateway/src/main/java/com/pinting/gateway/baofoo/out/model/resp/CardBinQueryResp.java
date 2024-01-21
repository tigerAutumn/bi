package com.pinting.gateway.baofoo.out.model.resp;

/**
 * Created by 剑钊 on 2016/7/19.
 */
public class CardBinQueryResp extends BaoFooBaseResp {


    private String bank_card_no;

    /**
     * 银行描述
     */
    private String bank_description;

    /**
     * 银行代码
     * 如：CIB
     */
    private String pay_code;

    /**
     * 卡类型
     * 如：借记卡
     */
    private String card_type;

    /**
     * 卡描述
     */
    private String card_description;

    /**
     * 卡bin
     */
    private String card_bin;

    public String getBank_card_no() {
        return bank_card_no;
    }

    public void setBank_card_no(String bank_card_no) {
        this.bank_card_no = bank_card_no;
    }

    public String getBank_description() {
        return bank_description;
    }

    public void setBank_description(String bank_description) {
        this.bank_description = bank_description;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getCard_description() {
        return card_description;
    }

    public void setCard_description(String card_description) {
        this.card_description = card_description;
    }

    public String getCard_bin() {
        return card_bin;
    }

    public void setCard_bin(String card_bin) {
        this.card_bin = card_bin;
    }
}
