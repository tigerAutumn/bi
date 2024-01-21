package com.pinting.mall.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MallPoints_PointsRecord extends ResMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1495541600344102140L;

	/*积分记录列表*/
	private ArrayList<HashMap<String, Object>> pointsRecordList;
	
	private Integer count;
	
	public ArrayList<HashMap<String, Object>> getPointsRecordList() {
		return pointsRecordList;
	}
	public void setPointsRecordList(ArrayList<HashMap<String, Object>> pointsRecordList) {
		this.pointsRecordList = pointsRecordList;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}
