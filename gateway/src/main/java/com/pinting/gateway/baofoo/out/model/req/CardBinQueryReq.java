package com.pinting.gateway.baofoo.out.model.req;

/**
 * Created by 剑钊 on 2016/8/24.
 */
public class CardBinQueryReq extends BaoFooOutBaseReq {

    /**
     * 银行卡号
     */
    private String bank_card_no;

    /**
     * 流水号
     */
    private String trans_serial_no;

    private String trade_date;

    public String getBank_card_no() {
        return bank_card_no;
    }

    public void setBank_card_no(String bank_card_no) {
        this.bank_card_no = bank_card_no;
    }

    public String getTrans_serial_no() {
        return trans_serial_no;
    }

    public void setTrans_serial_no(String trans_serial_no) {
        this.trans_serial_no = trans_serial_no;
    }

    public String getTrade_date() {
        return trade_date;
    }

    public void setTrade_date(String trade_date) {
        this.trade_date = trade_date;
    }
}
