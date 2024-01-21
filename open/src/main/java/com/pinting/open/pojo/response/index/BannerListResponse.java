package com.pinting.open.pojo.response.index;

import java.util.List;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.index.BannerVO;

public class BannerListResponse extends Response {

	private List<BannerVO> dataList;

	public List<BannerVO> getDataList() {
		return dataList;
	}

	public void setDataList(List<BannerVO> dataList) {
		this.dataList = dataList;
	}
}
