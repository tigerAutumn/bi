package com.pinting.business.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 剑钊 on 2016/8/3.
 */
public enum BaoFooEnum {

    INIT("INIT","新建"),
    PREING("PREING","预绑定中"),
    BINDING ("BINDING","绑定中"),
    COMM_FAIL("COMM_FAIL","通讯失败"),
    PRE_BIND_FAIL("PRE_BIND_FAIL","预绑卡失败"),
    PRE_BIND_SUCCESS("PRE_BIND_SUCCESS","预绑卡成功"),
    BIND_FAIL("FAIL","绑卡失败"),
    BIND_SUCCESS("BINDED","绑卡成功"),
    PRE_ORDER_SUCCESS("PRE_ORDER_SUCCESS","预下单成功"),
    PRE_ORDER_FAIL("PRE_ORDER_FAIL","预下单失败"),
    PAY_SUCCESS("PAY_SUCCESS","订单成功"),
    PAY_FAIL("PAY_FAIL","订单失败"),
    PAYING("PAYING","订单处理中"),
    UNBIND_SUCCESS("UNBINDED","卡解绑成功")

    ;

    public static Map<String,String> bankMap=new HashMap<>();

    public static Map<String,String> cardBinMap=new HashMap<>();

    public static Map<String,String> ebankMap=new HashMap<>();
    
    public static Map<String,String> ebankPayMap=new HashMap<>();

    public static Map<String,String> pay4BankMap=new HashMap<>();

    public static Map<String,String> bankCodeNameMap=new HashMap<>();

    public static Map<String,String> unionBankIdNameMap=new HashMap<>();
    
    static {
        bankMap.put("中国工商银行","ICBC");
        bankMap.put("中国农业银行","ABC");
        bankMap.put("中国建设银行","CCB");
        bankMap.put("中国银行","BOC");
        bankMap.put("中国交通银行","BCOM");
        bankMap.put("兴业银行","CIB");
        bankMap.put("中信银行","CITIC");
        bankMap.put("中国光大银行","CEB");
        bankMap.put("平安银行","PAB");
        bankMap.put("中国邮政储蓄银行","PSBC");
        bankMap.put("上海银行","SHB");
        bankMap.put("浦东发展银行","SPDB");
        bankMap.put("中国民生银行","CMBC");
        bankMap.put("招商银行","CMB");
        bankMap.put("广发银行","GDB");
        bankMap.put("华夏银行","HXB");
        
        
        
        bankMap.put("工商银行","ICBC");
        bankMap.put("农业银行","ABC");
        bankMap.put("建设银行","CCB");
        bankMap.put("交通银行","BCOM");
        bankMap.put("光大银行","CEB");
        bankMap.put("邮储银行","PSBC");
        bankMap.put("浦发银行","SPDB");
        bankMap.put("民生银行","CMBC");
        
        

        cardBinMap.put("1","中国工商银行");
        cardBinMap.put("3","中国农业银行");
        cardBinMap.put("6","中国建设银行");
        cardBinMap.put("5","中国银行");
        cardBinMap.put("17","中国交通银行");
        cardBinMap.put("2","兴业银行");
        cardBinMap.put("18","中信银行");
        cardBinMap.put("9","中国光大银行");
        cardBinMap.put("11","平安银行");
        cardBinMap.put("15","中国邮政储蓄银行");
        cardBinMap.put("14","上海银行");
        cardBinMap.put("8","浦东发展银行");
        cardBinMap.put("13","中国民生银行");
        cardBinMap.put("7","招商银行");
        cardBinMap.put("10","广发银行");
        cardBinMap.put("12","华夏银行");

        pay4BankMap.put("1","中国工商银行");
        pay4BankMap.put("3","中国农业银行");
        pay4BankMap.put("6","中国建设银行");
        pay4BankMap.put("5","中国银行");
        pay4BankMap.put("17","交通银行");
        pay4BankMap.put("2","兴业银行");
        pay4BankMap.put("18","中信银行");
        pay4BankMap.put("9","中国光大银行");
        pay4BankMap.put("11","平安银行");
        pay4BankMap.put("15","中国邮政储蓄银行");
        pay4BankMap.put("14","上海银行");
        pay4BankMap.put("8","浦发银行");
        pay4BankMap.put("13","中国民生银行");
        pay4BankMap.put("7","招商银行");
        pay4BankMap.put("10","广发银行");
        pay4BankMap.put("12","华夏银行");


        ebankMap.put("3002","中国工商银行");
        ebankMap.put("3005","中国农业银行");
        ebankMap.put("3003","中国建设银行");
        ebankMap.put("3026","中国银行");
        ebankMap.put("3020","中国交通银行");
        ebankMap.put("3009","兴业银行");
        ebankMap.put("3039","中信银行");
        ebankMap.put("3022","中国光大银行");
        ebankMap.put("3035","平安银行");
        ebankMap.put("3038","中国邮政储蓄银行");
        ebankMap.put("3059","上海银行");
        ebankMap.put("3004","浦东发展银行");
        ebankMap.put("3006","中国民生银行");
        ebankMap.put("3001","招商银行");
        ebankMap.put("3036","广发银行");
        ebankMap.put("3050","华夏银行");
        ebankMap.put("3032","北京银行");
        ebankMap.put("3037","上海农商银行");
        ebankMap.put("3060","北京农商银行");
        
        
        ebankPayMap.put("7","3001");
        ebankPayMap.put("1","3002");
        ebankPayMap.put("6","3003");
        ebankPayMap.put("8","3004");
        ebankPayMap.put("3","3005");
        ebankPayMap.put("13","3006");
        ebankPayMap.put("2","3009");
        ebankPayMap.put("17","3020");
        ebankPayMap.put("9","3022");
        ebankPayMap.put("5","3026");
        ebankPayMap.put("16","3032");
        ebankPayMap.put("11","3035");
        ebankPayMap.put("10","3036");
        ebankPayMap.put("58","3037");
        ebankPayMap.put("15","3038");
        ebankPayMap.put("18","3039");
        ebankPayMap.put("12","3050");
        ebankPayMap.put("14","3059");
        ebankPayMap.put("59","3060");
        ebankPayMap.put("3080001","3080001");//银联无卡支付
        ebankPayMap.put("3081001","3081001");//网银收银台

        bankCodeNameMap.put("ICBC","中国工商银行");
        bankCodeNameMap.put("ABC","中国农业银行");
        bankCodeNameMap.put("CCB","中国建设银行");
        bankCodeNameMap.put("BOC","中国银行");
        bankCodeNameMap.put("BCOM","中国交通银行");
        bankCodeNameMap.put("CIB","兴业银行");
        bankCodeNameMap.put("CITIC","中信银行");
        bankCodeNameMap.put("CEB","中国光大银行");
        bankCodeNameMap.put("PAB","平安银行");
        bankCodeNameMap.put("PSBC","中国邮政储蓄银行");
        bankCodeNameMap.put("SHB","上海银行");
        bankCodeNameMap.put("SPDB","浦东发展银行");
        bankCodeNameMap.put("CMBC","中国民生银行");
        bankCodeNameMap.put("CMB","招商银行");
        bankCodeNameMap.put("GDB","广发银行");
        bankCodeNameMap.put("HXB","华夏银行");
        
        
        unionBankIdNameMap.put("ICBC",	"102100099996");
        unionBankIdNameMap.put("ABC",	"103100000026");
        unionBankIdNameMap.put("CCB",	"105100000017");
        unionBankIdNameMap.put("BOC",	"104100000004");
        unionBankIdNameMap.put("BCOM",	"301290000007");
        unionBankIdNameMap.put("CIB",	"309391000011");
        unionBankIdNameMap.put("CITIC",	"302100011000");
        unionBankIdNameMap.put("CEB",	"303100000006");
        unionBankIdNameMap.put("PAB",	"307584007998");
        unionBankIdNameMap.put("PSBC",	"403100000004");
        unionBankIdNameMap.put("SHB",	"325290000012");
        unionBankIdNameMap.put("SPDB",	"310290000013");
        unionBankIdNameMap.put("CMBC",	"305100000013");
        unionBankIdNameMap.put("CMB",	"308584000013");
        unionBankIdNameMap.put("GDB",	"306581000003");
        unionBankIdNameMap.put("HXB",	"304100040000");
    }

    BaoFooEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private String code;

    private String description;

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
