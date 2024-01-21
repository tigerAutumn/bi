package com.pinting.open.pojo.response.asset;

import java.util.List;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.asset.LoadMatchVO;

public class MyLoadMatchResponse extends Response {

	private Integer totalPage;   //总页数
	
	private List<LoadMatchVO> dataList; //债权明细列表

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public List<LoadMatchVO> getDataList() {
		return dataList;
	}

	public void setDataList(List<LoadMatchVO> dataList) {
		this.dataList = dataList;
	}
}
