package com.pinting.open.pojo.response.index;

import java.util.List;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.index.ActiveVO;

public class AppActiveListResponse extends Response {

	private List<ActiveVO> dataList;

	public List<ActiveVO> getDataList() {
		return dataList;
	}

	public void setDataList(List<ActiveVO> dataList) {
		this.dataList = dataList;
	}
}
