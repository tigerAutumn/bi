package com.pinting.gateway.baofoo.out.util;

/**
 * Created by 剑钊 on 2016/7/12.
 */
public class PartnerBaoFooEnv {


    /**
     * 商户号
     */
    private String memberIdTo;

    /**
     * 代付终端号
     */
    private String pay4TerminalId;

    /**
     * 余额查询终端号
     */
    private String terminalId;

    /**
     * 商户备注信息
     */
    private String remarks;

    /**
     * 私钥路径
     */
    private String pfxPath;

    /**
     * 代付公钥路径
     */
    private String pay4CerPath;

    /**
     * 私钥密码
     */
    private String pfxPwd;

    /**
     * md5 加密key
     */
    private String md5Key;

    public String getMemberIdTo() {
        return memberIdTo;
    }

    public void setMemberIdTo(String memberIdTo) {
        this.memberIdTo = memberIdTo;
    }


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public String getPay4TerminalId() {
        return pay4TerminalId;
    }

    public void setPay4TerminalId(String pay4TerminalId) {
        this.pay4TerminalId = pay4TerminalId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getPfxPath() {
        return pfxPath;
    }

    public void setPfxPath(String pfxPath) {
        this.pfxPath = pfxPath;
    }

    public String getPay4CerPath() {
        return pay4CerPath;
    }

    public void setPay4CerPath(String pay4CerPath) {
        this.pay4CerPath = pay4CerPath;
    }

    public String getPfxPwd() {
        return pfxPwd;
    }

    public void setPfxPwd(String pfxPwd) {
        this.pfxPwd = pfxPwd;
    }

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }
}
