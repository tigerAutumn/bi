package com.pinting.business.enums;

public enum TimeTypeEnum {

	MONDAY("1","MONDAY","周一"),
	TUESDAY("2","TUESDAY","周二"),
	WEDNESDAY("3","WEDNESDAY","周三"),
	THURSDAY("4","THURSDAY","周四"),
	FRIDAY("5","FRIDAY","周五"),
	SATURDAY("6","SATURDAY","周六"),
	SUNDAY("7","SUNDAY","周日"),
	HOLIDAY("holiday","HOLIDAY","法定节假日"),
	DEFAULT("default","DEFAULT","默认"),
	;
	
	TimeTypeEnum(String code, String timeType, String description) {
        this.setCode(code);
        this.setTimeType(timeType);
        this.setDescription(description);
    }

	private String code;

    private String description;
    
    private String timeType;
    
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

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	public static TimeTypeEnum getEnumByCode(String code){
		if (null == code) {
			return null;
		}
		for (TimeTypeEnum type : values()) {
			if (type.getCode().equals(code.trim()))
				return type;
		}
		return null;
	}
    
	/**
	 * 
	 * @param code
	 * @return
	 */
	public static TimeTypeEnum getEnumByTimeType(String timeType){
		if (null == timeType) {
			return null;
		}
		for (TimeTypeEnum type : values()) {
			if (type.getTimeType().equals(timeType.trim()))
				return type;
		}
		return null;
	}
}
