package com.pinting.open.pojo.response.asset;

import java.util.List;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.asset.RedPacket;

public class MyRedPacketListResponse extends Response {

	private List<RedPacket> dataList;

	public List<RedPacket> getDataList() {
		return dataList;
	}

	public void setDataList(List<RedPacket> dataList) {
		this.dataList = dataList;
	}
}
