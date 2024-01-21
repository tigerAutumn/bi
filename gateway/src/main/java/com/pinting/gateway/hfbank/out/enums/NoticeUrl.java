package com.pinting.gateway.hfbank.out.enums;
/**
 * 
 * @project gateway
 * @title NoticeUrl.java
 * @author Dragon & cat
 * @date 2017-4-10
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public enum NoticeUrl {

	 /**批量开户（四要素绑卡）*/
	BATCH_REGIST_EXT("/Gateway_client/userAction_batchRegistExt", "批量开户（四要素绑卡）"),
	 /**批量开户（实名认证）*/
	BATCH_REGIST_EXT3("/Gateway_client/userAction_batchRegistExt3", "批量开户（实名认证）"),

    /**短验绑卡申请*/
	GET_BINK_CARD_CODE2("/Gateway_client/userAction_getBinkCardCode2", "短验绑卡申请"),

    /**平台不同账户互相转账*/
	PLATFORM_ACCOUNT_CONVERSE("/Gateway_client/transferAction_platformAccountConverse", "平台不同账户互相转账"),

    /** 批量换卡 */
    BATCH_UPDATE_CARD_EXT("/Gateway_client/bankCardAction_batchUpdateCardExt", "批量换卡"),

    /** 短验绑卡确认 */
    ADD_USER2("/Gateway_client/userAction_addUser2", "短验绑卡确认"),

    /** 网关充值请求 */
    GATEWAY_RECHARGE_REQUEST("/Gateway_client/fundAction_gatewayRechargeRequest", "网关充值请求"),

    /** 快捷充值请求 */
    DIRECT_QUICK_REQUEST("/Gateway_client/fundAction_directQuickRequest", "快捷充值请求"),

    /** 快捷充值确认 */
    DIRECT_QUICK_CONFIRM("/Gateway_client/fundAction_directQuickConfirm", "快捷充值确认"),

    /** 借款人扣款还款（代扣） */
    BORROW_GUT_REPAY("/Gateway_client/repayAction_borrowCutRepay", "借款人扣款还款"),

    /** 批量提现 */
    BATCH_WITHDRAW_EXT("/Gateway_client/fundAction_batchWithdrawExt", "批量提现"),

    /** 订单状态查询 */
    QUERY_ORDER("/Gateway_client/orderQueryAction_queryOrder", "订单状态查询"),

    /** 充值订单状态查询 */
    QUERY_FUND_ORDER("/Gateway_client/orderQueryAction_getFundOrderInfo", "充值订单状态查询"),

    /** 资金余额查询 */
    GET_ACCOUNT_INFO("/Gateway_client/accountAction_getAccountInfo", "资金余额查询"),

    /** 账户余额明细查询 */
    GET_ACCOUNTN_BALACE("/Gateway_client/accountAction_getAccountN_balace", "账户余额明细查询"),

    /** 标的账户余额查询 */
    GET_PRODUCTN_BALACE("/Gateway_client/publishAction_getProductN_balance", "标的账户余额查询"),

    /** 标的发布 */
    PUBLISH("/Gateway_client/publishAction_publish", "标的发布"),

    /** 标的成废 */
    ESTABLISH_OR_ABANDON("/Gateway_client/establishAction_establishOrAbandon", "标的成废"),

    /** 批量投标 */
    BATCH_EXT("/Gateway_client/buyAction_batchExt", "批量投标"),

    /** 标的出账 */
    CHANGE_OFF("/Gateway_client/chargeOffAction_chargeOff", "标的出账"),

    /** 标的出账信息修改 */
    MODIFY_PAY_OUT_ACCT("/Gateway_client/chargeOffAction_modifyPayOutAcct", ""),

    /** 标的转让 */
    TRANSFER_DEBT("/Gateway_client/transferAction_transferDebt", "标的转让"),
    /** 借款人批量还款 */
    REPAYACTION_BATCH_EXT("/Gateway_client/repayAction_batchExt", "借款人批量还款"),

    /** 标的还款 */
    PROD_REPAY("/Gateway_client/repayAction_prodRepay", "标的还款"),

    /** 平台转账个人 */
    PLAT_TRANS("/Gateway_client/platformTransferAction_platTrans", "平台转账个人"),
    
    /** 4.2.2.标的代偿（委托）还款 */
    COMPENSATE_REPAY("/Gateway_client/repayAction_compensate", "标的代偿（委托）还款 "),
    
    /** 4.2.3.借款人还款代偿（委托） */
    REPAY_COMPENSATE("/Gateway_client/repayAction_repayCompensate", "借款人还款代偿（委托）"),
    
    /** 4.7.4 平台提现 */
    PLAT_WITHDRAW("/Gateway_client/platformTransferAction_platWithdraw", "平台提现"),
    
    /** 4.7.3 平台充值 */
    PLAT_CHARGE("/Gateway_client/platformTransferAction_platCharge", "平台充值"),

    /** 4.8.2.对账文件账户余额数据 */
    BALANCE_INFO("/Gateway_client/liquidateAction_balanceInfo",	"对账文件账户余额数据"),
 
    /** 4.8.1.对账文件资金进出数据 */
    FUNDDATA_CHECK("/Gateway_client/liquidateAction_fundData",	"对账文件资金进出数据"),
    
    /** 清算状态查询 */
    QUERY_LIQUIDATION_INFO("/Gateway_client/", "清算状态查询"),
    /** 资金变动明细查询 */
    GET_FUNDLIST_INFO("/Gateway_client/fundAction_getFundList", "资金变动明细查询"),
    /** 绑卡 */
    USER_ADD_CARD("/Gateway_client/bankCardAction_addCard", "绑卡"),
    /** 客户信息变更 */
    USER_ACTION_UPDATEPLATACCT("/Gateway_client/userAction_updatePlatAcct", "客户信息变更")
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
    NoticeUrl(String code, String description) {
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
