package com.pinting.business.accounting.loan.enums;

/**
 * 恒丰账户余额明细查询入参-acct_type
 * 平台（1-自有子账户；3手续费子账户；4-体验金子账户；5-抵用金子账户；6-加息金子账户；7-保证金子账户；10--现金垫付； 11--在途垫付；34--手续费在途子账户）  
 * 个人（投资账户 12、融资 13）
 * @author bianyatian
 * @2017-6-8 下午4:23:15
 */
public enum HFAccountTypeEnum {

	HF_ACC_TYPE_JSH("1","HF_JSH","自有子账户"),
	HF_ACC_TYPE_FEE("3","HF_FEE","手续费子账户"),
	HF_ACC_TYPE_COUPON("5","HF_COUPON","抵用金账户"),
	HF_ACC_TYPE_HF_ADVANCE("10","HF_ADVANCE","垫付金现金账户"),
	HF_ACC_TYPE_ADVANCE_TRANSIT("11","HF_ADVANCE_TRANSIT","垫付金在途账户"),
	HF_ACC_TYPE_FEE_TRANSIT("34","HF_FEE_TRANSIT","手续费在途子账户");
	
	
	/** hfcode */
    private String hfcode;
    
	/** code */
    private String code;

    /** description */
    private String description;
    
    HFAccountTypeEnum(String hfcode,String code, String description) {
    	this.hfcode = hfcode;
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

	public String getHfcode() {
		return hfcode;
	}

	public void setHfcode(String hfcode) {
		this.hfcode = hfcode;
	}
    
    
}
