package com.pinting.gateway.baofoo.out.enums;

/**
 * Created by 剑钊 on 2016/8/16.
 */
public enum Pay4AnotherRespCode {

    /** 成功*/
    SUCCESS_CODE_00000("0000", "正在处理中"),
    SUCCESS_CODE_200("200", "代付交易成功"),


    ERROR_CODE_0001("0001","参数格式不正确"),
    ERROR_CODE_0002("0002","代付证书无效"),
    ERROR_CODE_0003("0003","报文格式不正确"),
    ERROR_CODE_0004("0004","交易请求记录超过上限"),
    ERROR_CODE_0201("0201","业务未开通"),
    ERROR_CODE_0202("0202","商户不存在"),
    ERROR_CODE_0203("0203","代付未绑定IP"),
    ERROR_CODE_0204("0204","代付终端号不存在"),
    ERROR_CODE_0205("0205","代付账号被列入黑名单"),
    ERROR_CODE_0206("0206","交易受限"),
    ERROR_CODE_0300("0300","代付交易未明，请发起该笔订单查询"),
    ERROR_CODE_0301("0301","代付交易失败"),
    ERROR_CODE_0401("0401","代付交易查证信息不存在"),
    ERROR_CODE_0501("0501","代付白名单添加失败"),
    
    
    ERROR_CODE_0999("0999","代付主机系统繁忙")
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
    Pay4AnotherRespCode(String code, String description) {
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
