package com.pinting.business.accounting.loan.enums;

import com.pinting.business.util.Constants;

/**
 * Created by 剑钊 on 2016/8/23.
 */
public enum LoanStatus {

    //借款状态
    LOAN_STATUS_CHECKED("CHECKED","审核通过"),
    LOAN_STATUS_PAIED("PAIED","放款成功"),
    LOAN_STATUS_PAYING("PAYING","放款中"),
    LOAN_STATUS_FAIL("PAY_FAIL","放款失败"),
    LOAN_STATUS_LATE("LATE","逾期"),
    LOAN_STATUS_BAD("BAD","坏账"),

    //还款状态
    REPAY_STATUS_INIT("INIT","初始状态"),
    REPAY_STATUS_PAYING("REPAYING","还款中"),
    REPAY_STATUS_REPAIED ("REPAIED","还款成功"),
    REPAY_STATUS_FAIL("REPAY_FAIL","还款失败"),


    //还款计划状态
    REPAY_SCHEDULE_STATUS_INIT("INIT","未还款"),
    REPAY_SCHEDULE_STATUS_PART_REPAY("PART_REPAY","部分未还款"),
    REPAY_SCHEDULE_STATUS_LATE_NOT("LATE_NOT","逾期未还款"),
    REPAY_SCHEDULE_STATUS_REPAIED("REPAIED","已还款"),
    REPAY_SCHEDULE_STATUS_LATE_REPAIED("LATE_REPAIED","逾期已还款"),
    REPAY_SCHEDULE_STATUS_CANCELLED("CANCELLED", "废除"),



    //资产方订单信息
    ORDERS_STATUS_INIT("1","新建"),
    ORDERS_STATUS_PRE_SUCCESS("3","预下单成功"),
    ORDERS_STATUS_PRE_FAIL("4","预下单失败"),
    ORDERS_STATUS_PAYING("5","支付处理中"),
    ORDERS_STATUS_SUCCESS("6","支付成功"),
    ORDERS_STATUS_FAIL("7","支付失败"),

    ORDERS_JNL_STATUS_INIT("INIT","新建"),
    ORDERS_JNL_STATUS_COMM_FAIL("COMM_FAIL","通讯失败"),
    ORDERS_JNL_STATUS_PRE_SUCCESS("PRE_SUCC","预下单成功"),
    ORDERS_JNL_STATUS_PRE_FAIL("PRE_FAIL","预下单失败"),
    ORDERS_JNL_STATUS_PAYING("PROCESS","支付处理中"),
    ORDERS_JNL_STATUS_SUCCESS("SUCCESS","支付成功"),
    ORDERS_JNL_STATUS_FAIL("FAIL","支付失败"),

    //交易类型
    TRANS_TYPE_BIND_CARD("BIND_CARD","绑卡"),
    TRANS_TYPE_UN_BIND_CARD("UN_BIND_CARD","解绑卡"),
    TRANS_TYPE_LOAN("LOAN","借款"),
    TRANS_TYPE_REPAY("REPAY","还款"),
    TRANS_TYPE_MARKET("MARKET_PAY","营销"),
    TRANS_TYPE_WITHDRAW_2_DEP_REPAY_CARD("WITHDRAW_2_DEP_REPAY_CARD","提现到恒丰还款实体户（卡）"),
    TRANS_TYPE_CUT_REPAY_2_BORROWER("CUT_REPAY_2_BORROWER","批量代扣还款到借款人"),
    TRANS_TYPE_DF_2_BORROWER("DF_2_BORROWER","代付到借款人"),
    TRANS_TYPE_REPAY_2_DEP_TARGET("REPAY_2_DEP_TARGET","借款人还款至标的"),
    TRANS_TYPE_COMP_REPAY_2_DEP_TARGET("COMP_REPAY_2_DEP_TARGET","代偿人还款至标的"),
    TRANS_TYPE_REPAY_2_INVESTOR("REPAY_2_INVESTOR","标的还款至投资人账户"),

    //渠道交易类型
    CHANNEL_TRANS_TYPE_BIND_CARD("BIND_CARD","绑卡"),
    CHANNEL_TRANS_TYPE_UN_BIND_CARD("UN_BIND_CARD","解绑卡"),
    CHANNEL_TRANS_TYPE_DF("DF","代付"),
    CHANNEL_TRANS_TYPE_QUICK("QUICK_PAY","快捷"),
    CHANNEL_TRANS_TYPE_AUTH("AUTH_PAY","认证支付"),
    CHANNEL_TRANS_TYPE_DK("DK","代扣"),
    CHANNEL_TRANS_TYPE_NET_BANK("NETBANK", "网银支付"),
    CHANNEL_TRANS_TYPE_TRANSFER("TRANSFER","钱包转账"),
    
    CHANNEL_TRANS_TYPE_REPAY_2_DEP_TARGET("REPAY_2_DEP_TARGET","借款人还款至标的"),
    CHANNEL_TRANS_TYPE_COMP_REPAY_2_DEP_TARGET("COMP_REPAY_2_DEP_TARGET","代偿人还款至标的"),
    CHANNEL_TRANS_TYPE_REPAY_2_INVESTOR("REPAY_2_INVESTOR","标的还款至投资人账户"),
    
    //通知状态
    NOTICE_STATUS_INIT("INIT","未通知"),
    NOTICE_STATUS_SUCCESS("SUCCESS","通知成功"),
    NOTICE_STATUS_FAIL("FAIL","通知失败"),

    BAOFOO_PAY_STATUS_SUCCESS("000000","支付成功"),
    BAOFOO_PAY_STATUS_PROCESS("920003","交易处理中"),
    BAOFOO_PAY_STATUS_FAIL("920004","支付失败"),

    //存管体系还款计划还款处理标识
    DEP_REPAY_SCHEDULE_RETURN_FLAG_INIT("INIT","初始"),
    DEP_REPAY_SCHEDULE_RETURN_FLAG_DF_PENDING("DF_PENDING","代付到还款专户待处理"),
    DEP_REPAY_SCHEDULE_RETURN_FLAG_DF_PROCESS("DF_PROCESS","代付到还款专户处理中"),
    DEP_REPAY_SCHEDULE_RETURN_FLAG_DF_SUCC("DF_SUCC","代付到还款专户处理完成"),
    DEP_REPAY_SCHEDULE_RETURN_FLAG_DF_FAIL("DF_FAIL","代付到还款专户处理失败"),
    DEP_REPAY_SCHEDULE_RETURN_FLAG_DK_PROCESS("DK_PROCESS","代扣到融资人投资账户处理中"),
    DEP_REPAY_SCHEDULE_RETURN_FLAG_DK_SUCC("DK_SUCC","代扣到融资人投资账户处理完成"),
    DEP_REPAY_SCHEDULE_RETURN_FLAG_DK_FAIL("DK_FAIL","代扣到融资人投资账户处理失败"),
    DEP_REPAY_SCHEDULE_RETURN_FLAG_REPAY_SUCC("REPAY_SUCC","借款人还款到标的处理成功"),
    DEP_REPAY_SCHEDULE_RETURN_FLAG_REPAY_PROCESS("REPAY_PROCESS","借款人还款到标的处理中"),
    DEP_REPAY_SCHEDULE_RETURN_FLAG_REPAY_FAIL("REPAY_FAIL","借款人还款到标的处理失败"),
    DEP_REPAY_SCHEDULE_RETURN_FLAG_RETURN_SUCC("RETURN_SUCC","标的还款到投资人处理成功"),
    DEP_REPAY_SCHEDULE_RETURN_FLAG_RETURN_PROCESS("RETURN_PROCESS","标的还款到投资人处理中"),
    DEP_REPAY_SCHEDULE_RETURN_FLAG_RETURN_FAIL("RETURN_FAIL","标的还款到投资人处理失败"),

    ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    LoanStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
