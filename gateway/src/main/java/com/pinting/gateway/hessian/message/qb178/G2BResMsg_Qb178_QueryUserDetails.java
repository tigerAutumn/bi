package com.pinting.gateway.hessian.message.qb178;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.qb178.in.model.QueryUserDetailsDataResModel;

import java.util.List;

/**
 * Author:      shh
 * Date:        2017/7/31
 * Description: 查询会员详情出参
 */
public class G2BResMsg_Qb178_QueryUserDetails extends ResMsg {

    private static final long serialVersionUID = 7271869734761355234L;

    /* 总记录数 */
    private	Integer	total_num;

    /* 当前页 */
    private	Integer	current_page;

    /* 明细数据 */
    private List<QueryUserDetailsDataResModel> data;

    public Integer getTotal_num() {
        return total_num;
    }

    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
    }

    public Integer getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(Integer current_page) {
        this.current_page = current_page;
    }

    public List<QueryUserDetailsDataResModel> getData() {
        return data;
    }

    public void setData(List<QueryUserDetailsDataResModel> data) {
        this.data = data;
    }
}
