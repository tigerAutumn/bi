package com.pinting.business.enums;

/**
 * 支付通道枚举
 * @author SHENGP
 * @date  2017年6月2日 上午9:48:05
 */
public enum PayPathEnum {

	SMALL_AMT("03", "SMALL_AMT", "小额"),
	SUPER_BANK("06", "SUPER_BANK", "超网"),
	BIG_AMT("09", "BIG_AMT", "大额"),
	;
	
	private String code;

	private String payPathVal;
	
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
	
	public String getPayPathVal() {
		return payPathVal;
	}

	public void setPayPathVal(String payPathVal) {
		this.payPathVal = payPathVal;
	}

	/**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private PayPathEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
	
    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private PayPathEnum(String code, String payPathVal, String description) {
        this.code = code;
        this.payPathVal = payPathVal;
        this.description = description;
    }
    
    /**
     * 
     * @param code
     * @return {@link PayPathEnum} 实例
     */
    public static PayPathEnum find(String code) {
        for (PayPathEnum payPath : PayPathEnum.values()) {
            if (payPath.getCode().equals(code)) {
                return payPath;
            }
        }
        return null;
    }
}
