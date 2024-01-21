/**
 * funball.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.pinting.business.util.WelinkUtil;


/**
 * 内部业务使用的错误码
 * 
 * @author Baby shark love blowing wind
 * @version $Id: GatewayBaseCode.java, v 0.1 2015-9-7 下午2:49:07 BabyShark Exp $
 */
public enum GatewayBaseCodeEnum {
    /** 交易成功  */
    TRANS_SUCCESS("TRS_SUCC", "交易成功"),
    /** 交易失败 */
    TRANS_ERROR("TRS_FAIL", "交易失败"),
    /** 参数非法 */
    PARAM_ILLEGAL("PARAM_ILLEGAL", "参数非法"),
    /** 系统异常 */
    SYSTEM_ERROR("SYSTEM_ERROR", "系统异常"),
    /** 未知异常 */
    UNKNOWN_ERROR("UNKNOWN_ERROR", "系统异常"),
    /** 远程接口调用异常*/
    RPC_EXCEPTION("gateway_RPC_EXCEPTION", "远程接口调用异常"),
    /** 远程接口返回false*/
    RPC_RESULT_FALSE("gateway_RPC_RESULT_FALSE", "远程接口返回false"),

    /** 报文解密失败*/
    RETURN_MSG_DECODE_FAIL("RETURN_MSG_DECODE_FAIL", "报文解密失败"),
    /** 报文消息摘要校验失败*/
    RETURN_MSG_HASH_ERROR("RETURN_MSG_HASH_ERROR", "报文消息摘要校验失败"),
    /** 登录超时*/
    RETURN_MSG_LOGIN_EXPIRED("RETURN_MSG_LOGIN_EXPIRED", "登录超时"),
    /** token错误*/
    RETURN_MSG_TOKEN_ERROR("RETURN_MSG_TOKEN_ERROR", "token错误"),
    /** 系统登录校验失败*/
    REUTRN_MSG_LOGIN_FAIL("REUTRN_MSG_LOGIN_FAIL", "系统登录校验失败"),
    /** 图片上传失败*/
    PIC_UPLOAD_FAIL("PIC_UPLOAD_FAIL", "图片上传失败"),
    /** 音频上传失败*/
    VOICE_UPLOAD_FAIL("VOICE_UPLOAD_FAIL", "音频上传失败"),
    /** 图片上传消息摘要错误*/
    PIC_UPLOAD_HASH_ERROR("PIC_UPLOAD_HASH_ERROR", "图片上传消息摘要错误"),
    /** 音频上传消息摘要错误*/
    VOICE_UPLOAD_HASH_ERROR("VOICE_UPLOAD_HASH_ERROR", "音频上传消息摘要错误"),
    /** 风险评分查询异常*/
    QUERY_RISK_SCORE_ERROR("QUERY_RISK_SCORE_ERROR", "风险评分查询异常"),
    /** 风险评分查询超时*/
    QUERY_RISK_SCORE_EXPIRED("QUERY_RISK_SCORE_EXPIRED", "风险评分查询超时"),
    /** ftp下载文件失败 */
    FTP_DOWNLOAD_FAIL("FTP_DOWNLOAD_FAIL", "ftp下载文件失败"),
    /** 查询公民身份信息失败 */
    QUERY_NCIIC_FAIL("QUERY_NCIIC_FAIL", "查询公民身份信息失败 "),
    /** PFX证书保存失败 */
    PFX_SAVE_FAIL("PFX_SAVE_FAIL", "PFX证书保存失败"),
    /** 获取PFX证书失败 */
    GET_PFX_FAIL("GET_PFX_FAIL", "获取PFX证书失败"),
    /** 申请证书及下载执行失败 */
    APPLY_AND_DOWNLOAD_CERT_FAIL("APPLY_AND_DOWNLOAD_CERT_FAIL", "申请证书及下载执行失败"),
    /** 申请证书P10失败 */
    APPLY_P10_FAIL("APPLY_P10_FAIL", "申请证书P10失败"),

    /** 获取同盾风控信息失败 */
    GET_FRAUD_METRIX_POLICY_RESULT_ERROR("GET_FRAUD_METRIX_POLICY_RESULT_ERROR", "获取同盾风控信息失败"), 
    /** 获取同盾命中规则详情失败 */
    GET_FRAUD_METRIX_HIT_RULE_DETAIL_ERROR("GET_FRAUD_METRIX_HIT_RULE_DETAIL_ERROR", "获取同盾命中规则详情失败"), ;

    /** 错误码 */
    private String code;
    /** 错误描述 */
    private String desc;

    private GatewayBaseCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;

    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根据编码获取错误码
     * **/
    public static GatewayBaseCodeEnum getByCode(String code) {
        for (GatewayBaseCodeEnum funballBaseCode : values()) {
            if (funballBaseCode.getCode().equals(code)) {
                return funballBaseCode;
            }
        }
        return null;
    }

}
