package com.pinting.gateway.hessian.message.xicai;

import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.xicai.model.UserInfo;

public class G2BResMsg_Xicai_UserCount extends ResMsg {

	private static final long serialVersionUID = -1261734371189920995L;

	private Integer code;
	
	private Integer total;
	
	private List<UserInfo> list;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<UserInfo> getList() {
		return list;
	}

	public void setList(List<UserInfo> list) {
		this.list = list;
	}
}
