package com.pinting.business.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrebleGiftListVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3194301739917815937L;

	private String selectDate; //排行榜日期
	
	private ArrayList<HashMap<String, Object>> list; //排行榜列表
	
	private int listSize; //每日排行榜的数量

	public String getSelectDate() {
		return selectDate;
	}

	public void setSelectDate(String selectDate) {
		this.selectDate = selectDate;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public ArrayList<HashMap<String, Object>> getList() {
		return list;
	}

	public void setList(ArrayList<HashMap<String, Object>> list) {
		this.list = list;
	}
	
	
}
