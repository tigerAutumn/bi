package com.pinting.open.pojo.response.asset;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.asset.Ticket;

import java.util.List;

/**
 * Created by cyb on 2018/3/30.
 */
public class TicketListResponse extends Response {

    private Integer userId;
    private String  type;   // RED_PACKET-红包; INTEREST_TICKET-加息券
    private Integer count;  // 优惠券条数
    private String  isSupportRed;
    private String  isSupportInterestTicket;
    private List<Ticket> dataList;// 详情

    public String getIsSupportRed() {
        return isSupportRed;
    }

    public void setIsSupportRed(String isSupportRed) {
        this.isSupportRed = isSupportRed;
    }

    public String getIsSupportInterestTicket() {
        return isSupportInterestTicket;
    }

    public void setIsSupportInterestTicket(String isSupportInterestTicket) {
        this.isSupportInterestTicket = isSupportInterestTicket;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Ticket> getDataList() {
        return dataList;
    }

    public void setDataList(List<Ticket> dataList) {
        this.dataList = dataList;
    }
}
