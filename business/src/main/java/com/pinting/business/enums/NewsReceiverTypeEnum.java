package com.pinting.business.enums;

/**
 * 币港湾公告媒体信息枚举
 * @author SHENGUOPING
 * @date  2018年7月17日 上午10:42:18
 */
public enum NewsReceiverTypeEnum {

	NEWS_RECEIVER_TYPE_BGW("BGW", "币港湾"),
	NEWS_RECEIVER_TYPE_BGW178("BGW178", "海宁日报-币港湾"),
	NEWS_RECEIVER_TYPE_BGWKQ("BGWKQ", "柯桥日报-币港湾"),
	NEWS_RECEIVER_TYPE_BGWHN("BGWHN", "海宁日报-币港湾"),
	NEWS_RECEIVER_TYPE_BGWRUIAN("BGWRUIAN", "瑞安日报-币港湾"),
	NEWS_RECEIVER_TYPE_BGWQD("BGWQD", "七店-币港湾"),
	
	NEWS_RECEIVER_TYPE_BGWQHDJT("BGWQHDJT", "秦皇岛交通广播-币港湾"),
	NEWS_RECEIVER_TYPE_BGWQHDXW("BGWQHDXW", "秦皇岛新闻891-币港湾"),
	NEWS_RECEIVER_TYPE_BGWQHDTV("BGWQHDTV", "秦皇岛电视台今日报道-币港湾"),
	NEWS_RECEIVER_TYPE_BGWQHDST("BGWQHDST", "视听之友-币港湾"),
	NEWS_RECEIVER_TYPE_BGWQHDSJC("BGWQHDSJC", "1038私家车广播-币港湾"),
	;
	
	private String code;

    private String description;
    
	public static NewsReceiverTypeEnum getEnumByCode(String code){
		if (null == code) {
			return null;
		}
		for (NewsReceiverTypeEnum type : values()) {
			if (type.getCode().equals(code.trim()))
				return type;
		}
		return null;
	}
	
	NewsReceiverTypeEnum(String code, String description){
    	 this.setCode(code);
         this.setDescription(description);
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
