package com.pinting.gateway.hessian.message.qb178;

import java.util.List;
import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.qb178.model.QueryBalanceJnlInfo;

public class G2BResMsg_Qb178_QueryBalanceJnl extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8591533592405425355L;

	/* 会员资金流水列表 */
	private List<QueryBalanceJnlInfo> data;
	/*总记录数*/
	private	Integer	total_num;
	/*当前页*/
	private	Integer	current_page;
	
	public List<QueryBalanceJnlInfo> getData() {
		return data;
	}
	public void setData(List<QueryBalanceJnlInfo> data) {
		this.data = data;
	}
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

}
