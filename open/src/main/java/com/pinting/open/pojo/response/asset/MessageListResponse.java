package com.pinting.open.pojo.response.asset;

import java.util.List;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.asset.Message;

public class MessageListResponse extends Response {

	private List<Message> dataList;
	
	private Integer totalPage;

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public List<Message> getDataList() {
		return dataList;
	}

	public void setDataList(List<Message> dataList) {
		this.dataList = dataList;
	}
}
