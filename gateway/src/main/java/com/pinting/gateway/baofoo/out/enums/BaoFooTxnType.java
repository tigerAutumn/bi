package com.pinting.gateway.baofoo.out.enums;

/**
 * Created by 剑钊 on 2016/7/19.
 */
public class BaoFooTxnType {

    /**
     * 证件类型
     * 01为身份证
     */
    public static String ID_CARD_TYPE="01";

    /**
     * 交易类型
     */
    public static String TXN_TYPE="0431";

    /**
     * 安全服务交易类型
     */
    public static String SECURITY_TXN_TYPE="3001";

    /**
     * 接入类型
     */
    public static String BIZ_TYPE="0000";

    /**
     * 预绑卡交易子类
     */
    public static String TXN_SUB_TYPE_PREPARED_BIND_CARD="11";
    /**
     * 确认绑卡交易子类
     */
    public static String TXN_SUB_TYPE_BIND_CARD="01";

    /**
     * 解绑卡交易子类
     */
    public static String TXN_SUB_TYPE_UNBIND_CARD="02";

    /**
     * 查询绑定关系类交易
     */
    public static String TXN_SUB_TYPE_BIND_RELATION_QUERY="03";

    /**
     * 发送短信
     */
    public static String TXN_SUB_TYPE_BIND_SEND_SMS="05";

    /**
     * 预支付交易子类
     */
    public static String TXN_SUB_TYPE_PREPARED_QUICK_PAY="15";

    /**
     * 确认支付交易子类
     */
    public static String TXN_SUB_TYPE_QUICK_PAY="04";
    
    /**
     * 代扣支付交易
     */
    public static String TXN_SUB_TYPE_CUT_PAYMENT="13";

    /**
     * 交易状态查询类交易
     */
    public static String TXN_SUB_TYPE_QUICK_PAY_STATUS_QUERY="06";

    /**
     * 卡bin查询类交易
     */
    public static String TXN_SUB_TYPE_CARD_BIN_QUERY="01361";

    /**
     * 网银加密类型
     */
    public static String COUNTER_KEY_TYPE="1";

    /**
     * 网银支付结果通知类型
     */
    public static String COUNTER_NOTICE_TYPE="1";

    /**
     *
     */
    public static String COUNTER_MARK="|";

    /**
     * 异步通知md5分隔符
     */
    public static String ASYNCHRONOUS_NOTICE_MARK="~|~";

    /**
     * 默认商品数量
     */
    public static String COUNTER_AMOUNT="1";
}
