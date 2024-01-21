package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cyb on 2018/3/30.
 */
public class ResMsg_Ticket_TicketList extends ResMsg {

    private static final long serialVersionUID = -6269327765746343553L;

    private Integer userId;
    private String  type;   // RED_PACKET-红包; INTEREST_TICKET-加息券
    private Integer count;  // 优惠券条数
    private String  isSupport;  // 是否只是加息券/红包（TRUE、FALSE）
    private List<HashMap<String, Object>> dataList;
    /*[{    // 详情
        Integer id;         // 红包、加息券ID
        String  type;       // RED_PACKET-红包; INTEREST_TICKET-加息券
        String  status;     // 状态：INIT-未使用；USED-已使用；OVER-已过期
        String  rate;       // 加息利率（%）
        String  full;       // 满额
        String  subtract;   // 减额
        String  serialName; // 优惠券名称
        String  useTimeStart;   // 使用有效开始时间（yyyy-MM-dd）
        String  useTimeEnd;     // 使用有效结束时间（yyyy-MM-dd）
        string  useTime;        // 使用时间（yyyy-MM-dd HH:mm:ss）
        String  target;         // 标的，限1个月产品使用
        Integer term;           // 当前产品期限
        Double  interest;       // 加息利息
        String  productName;   // 已使用产品名称
        String  productLimit;   // 产品系列限制：BIGANGWAN_SERIAL（港湾系列）YONGJIN_SERIAL（涌金系列）KUAHONG_SERIAL（跨虹系列）BAOXIN_SERIAL（保信系列）多个产品限制用逗号隔开
    }];*/

    public String getIsSupport() {
        return isSupport;
    }

    public void setIsSupport(String isSupport) {
        this.isSupport = isSupport;
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

    public List<HashMap<String, Object>> getDataList() {
        return dataList;
    }

    public void setDataList(List<HashMap<String, Object>> dataList) {
        this.dataList = dataList;
    }
}
