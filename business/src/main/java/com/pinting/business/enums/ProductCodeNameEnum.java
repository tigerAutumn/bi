package com.pinting.business.enums;

import java.util.HashMap;
import java.util.Map;

public enum ProductCodeNameEnum {
	
	PRODUCT_180_120("029", "浙大网新-180天(12%)"),
	PRODUCT_180_105("022", "浙大网新-180天(10.5%)"),
	PRODUCT_365_01("014", "浙大网新-1年"),
	PRODUCT_365_02("030", "浙大网新-1年(14%)"),
	PRODUCT_90("028", "浙大网新-90天(10%)"),
	;
	private ProductCodeNameEnum(String code,String productName){
		this.code = code;
		this.productName = productName;
	}
	
	private String code;
	private String productName;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	public static ProductCodeNameEnum getEnumByCode(String code){
		if (null == code) {
			return null;
		}
		for (ProductCodeNameEnum type : values()) {
			if (type.getCode().equals(code.trim()))
				return type;
		}
		return null;
	}
	
	/**
	 * 转出Map
	 * @return
	 */
	public static Map<String, String> toMap() {
		Map<String, String> enumDataMap = new HashMap<String, String>();
		for (ProductCodeNameEnum type : values()) {
			enumDataMap.put(type.getCode(), type.getProductName());
		}
		return enumDataMap;
	}
	
}
