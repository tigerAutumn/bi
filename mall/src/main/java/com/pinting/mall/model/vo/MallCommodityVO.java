package com.pinting.mall.model.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pinting.mall.model.MallCommodityInfo;

public class MallCommodityVO {

	private String commTypeName; //类别名称
    
   // private List<MallCommodityInfo> commInfoList;
    
    private ArrayList<HashMap<String, Object>> commInfoList;

	public String getCommTypeName() {
		return commTypeName;
	}

	public void setCommTypeName(String commTypeName) {
		this.commTypeName = commTypeName;
	}

	public ArrayList<HashMap<String, Object>> getCommInfoList() {
		return commInfoList;
	}

	public void setCommInfoList(ArrayList<HashMap<String, Object>> commInfoList) {
		this.commInfoList = commInfoList;
	}

	/*public List<MallCommodityInfo> getCommInfoList() {
		return commInfoList;
	}

	public void setCommInfoList(List<MallCommodityInfo> commInfoList) {
		this.commInfoList = commInfoList;
	}*/

	
    
}
