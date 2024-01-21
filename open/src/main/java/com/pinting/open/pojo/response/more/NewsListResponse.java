package com.pinting.open.pojo.response.more;

import java.util.List;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.more.News;

public class NewsListResponse extends Response {

	private List<News> dataList;
	
	private Integer totalPage;

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public List<News> getDataList() {
		return dataList;
	}

	public void setDataList(List<News> dataList) {
		this.dataList = dataList;
	}

}
