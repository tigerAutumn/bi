package com.pinting.business.hessian.manage.message;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Statistics_StatisticsListQuery extends ResMsg {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4347537085624579821L;
	private Integer id;		
	private String type;		
	private Date time;  
	private String typeName;	
	private Double value;	
	private String note;
	private List<HashMap<String, Object>> StatisticsList;
	
	public List<HashMap<String, Object>> getStatisticsList() {
		return StatisticsList;
	}
	public void setStatisticsList(List<HashMap<String, Object>> statisticsList) {
		StatisticsList = statisticsList;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}	
	
	
}