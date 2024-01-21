package com.pinting.business.hessian.manage.message;
import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Statistics_StatisticsListQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7410572872860631030L;
	private Integer id;		
	private String type;		
	private Date time;  
	private String typeName;	
	private Double value;	
	private String note;
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