package com.pinting.open.pojo.response.asset;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.asset.RedPacket;

import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/3/16
 * Description:
 */
public class RedPacketListResponse extends Response {

    private Integer count;

    private List<RedPacket> dataList;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<RedPacket> getDataList() {
        return dataList;
    }

    public void setDataList(List<RedPacket> dataList) {
        this.dataList = dataList;
    }

}
