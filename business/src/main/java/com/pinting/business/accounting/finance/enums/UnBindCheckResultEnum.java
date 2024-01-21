package com.pinting.business.accounting.finance.enums;

/**
 * 解绑前校验返回枚举
 * @project business
 * @author bianyatian
 * @2018-5-23 下午5:27:10
 */
public enum UnBindCheckResultEnum {

	CHECK_PASS("PASS","校验通过"),
	NO_USER("NO_USER","用户信息不存在"),
	USER_STATUS_FREEZE("USER_STATUS_FREEZE","您的账户已冻结，请联系客服详询"),
	NO_BANK_CARD("NO_BANK_CARD","卡信息不存在"),//该用户该卡id为安全卡且状态可用未查询到
	AGE_OUT_RANGE("AGE_OUT_RANGE","年龄超过"),
	TOO_MANY_FAILURES("TOO_MANY_FAILURES","人脸核身验证次数过多"),
	WITHDRAW_PROGRESS("WITHDRAW_PROGRESS","有提现在进行中"),
	TOP_UP_PROGRESS("TOP_UP_PROGRESS","有充值在进行中"),
	VERIFY_PROGRESS("VERIFY_PROGRESS","有人脸核身待审核"),
	OTHER_ERROR("OTHER_ERROR","其他错误"),
	;
	/** code */
    private String code;

    /** description */
    private String description;
    
    private UnBindCheckResultEnum(String code, String description) {
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
