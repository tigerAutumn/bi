package com.pinting.business.enums;

import java.util.HashMap;
import java.util.Map;

public enum Ecup2016TeamsEnum {
	//波兰、威尔士、葡萄牙、法国、德国、比利时、意大利、冰岛
	EcupTeam_1("波兰",0.0),
	EcupTeam_2("威尔士",0.0),
	EcupTeam_3("葡萄牙",0.0),
	EcupTeam_4("法国",0.0),
	EcupTeam_5("德国",0.0),
	EcupTeam_6("比利时",0.0),
	EcupTeam_7("意大利",0.0),
	EcupTeam_8("冰岛",0.0),
	
	;
	
	private Ecup2016TeamsEnum(String teamName,Double supportRate){
		this.supportRate = supportRate;
		this.teamName = teamName; 
	}



	private String teamName;
	
	private Double supportRate;
	
	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public Double getSupportRate() {
		return supportRate;
	}
	public void setSupportRate(Double supportRate) {
		this.supportRate = supportRate;
	}
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	public static Ecup2016TeamsEnum getEnumByTeamName(String teamName){
		if (null == teamName) {
			return null;
		}
		for (Ecup2016TeamsEnum type : values()) {
			if (type.getTeamName().equals(teamName.trim()))
				return type;
		}
		return null;
	}
	
	/**
	 * 转出Map
	 * @return
	 */
	public static Map<String, Double> toMap() {
		Map<String, Double> enumDataMap = new HashMap<String, Double>();
		for (Ecup2016TeamsEnum type : values()) {
			enumDataMap.put(type.getTeamName(), type.getSupportRate());
		}
		return enumDataMap;
	}

}
