package com.pinting.business.accounting.loan.enums;

/**
 * 借款科目信息表 计数保留规则、分期计算规则、本金余数规则、比率类型等的枚举
 * @author bianyatian
 * @2016-9-7 下午4:38:28
 */
public enum LoanSubjectRulesEnum {
	
	RULE("code","description");
	
	 /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    LoanSubjectRulesEnum(String code, String description) {
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
    
   /**
    * 计数保留规则
    * @author bianyatian
    * @2016-9-7 下午8:39:49
    */
   public static enum ReserveRuleEnum{
	   ROUND("ROUND","四舍五入"),
	   FLOOR("FLOOR","小数点后直接舍弃"),
	   CEIL("CEIL","存在小数直接进位");
		
		 /** code */
	    private String code;

	    /** description */
	    private String description;

	    /**
	     * 私有的构造方法
	     * @param code
	     * @param description
	     */
	    ReserveRuleEnum(String code, String description) {
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
   
   /**
    * 分期计算规则
    * @author bianyatian
    * @2016-9-7 下午8:41:03
    */
   public static enum CalRuleEnum{
	   BEFORE("BEFORE","先把本金分期，然后按照费率进行计算具体金额"),
	   AFTER("AFTER","先按照费率进行计算具体金额然后再进行分期");
		
		 /** code */
	    private String code;

	    /** description */
	    private String description;

	    /**
	     * 私有的构造方法
	     * @param code
	     * @param description
	     */
	    CalRuleEnum(String code, String description) {
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
   
   
   /**
    * 余数规则
    * @author bianyatian
    * @2016-9-7 下午8:42:38
    */
   public static enum LeftRuleEnum{
	   FIRST("FIRST","本金分期后多余的金额加到第一期"),
	   LAST("LAST","本金分期后多余的金额加到最后一期");
		
		 /** code */
	    private String code;

	    /** description */
	    private String description;

	    /**
	     * 私有的构造方法
	     * @param code
	     * @param description
	     */
	    LeftRuleEnum(String code, String description) {
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
   /**
    * 比率类型
    * @author bianyatian
    * @2016-9-7 下午8:44:21
    */
   public static enum RateTypeEnum{
	   
	   RATE("RATE","按比率收取"),
	   FIXED("FIXED","固定值"),
	   PER_DAY("PER_DAY","每天比率"),
	   CUSTOMIZED("CUSTOMIZED","自定义");
		
		 /** code */
	    private String code;

	    /** description */
	    private String description;

	    /**
	     * 私有的构造方法
	     * @param code
	     * @param description
	     */
	    RateTypeEnum(String code, String description) {
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
}
