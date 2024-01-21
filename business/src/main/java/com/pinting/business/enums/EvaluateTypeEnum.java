package com.pinting.business.enums;

/**
 * 风险测评类型
 * @author SHENGUOPING
 * @date  2018年1月19日 下午3:39:44
 */
public enum EvaluateTypeEnum {

	EVALUATE_TYPE_CONSERVATIVE("conservative", "保守型"),
	EVALUATE_TYPE_STEADY("steady", "稳健型"),
	EVALUATE_TYPE_BALANCED("balanced", "平衡型"),
	EVALUATE_TYPE_AGGRESSIVE("aggressive", "进取型"),
	EVALUATE_TYPE_RADICAL("radical", "激进型"),
    ;
	
	/** 编码 */
    private String code;

    /** 名字 */
    private String name;

    /**
     * 私有的构造方法
     * @param code
     * @param name
     */
    private EvaluateTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 
     * @param code
     * @return {@link EvaluateTypeEnum} 实例
     */
    public static EvaluateTypeEnum find(String code) {
        for (EvaluateTypeEnum transCode : EvaluateTypeEnum.values()) {
            if (transCode.getCode().equals(code)) {
                return transCode;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    
}
