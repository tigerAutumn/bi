package com.pinting.open.pojo.response.more;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.more.News;

import java.util.List;

/**
 * Author:      shh
 * Date:        2017/6/30
 * Description: 分页查询港湾资讯、平台公告 response
 */
public class NewsListInfoResponse extends Response {

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
