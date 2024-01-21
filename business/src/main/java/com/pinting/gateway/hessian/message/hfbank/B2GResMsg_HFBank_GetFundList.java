package com.pinting.gateway.hessian.message.hfbank;


import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.hfbank.model.GetFundListDetailData;
/**
 * 资金变动明细查询响应信息
 * Created by yed on 2017/5/25.
 */
public class B2GResMsg_HFBank_GetFundList extends ResMsg {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1719037305830005544L;

	/* 返回码, 10000为成功 */
    private String recode;

    /* 返回结果描述 */
    private String remsg;
    
    private GetFundListDetailData data;
    
	public GetFundListDetailData getData() {
		return data;
	}

	public void setData(GetFundListDetailData data) {
		this.data = data;
	}

	public String getRecode() {
        return recode;
    }

    public void setRecode(String recode) {
        this.recode = recode;
    }

    public String getRemsg() {
        return remsg;
    }

    public void setRemsg(String remsg) {
        this.remsg = remsg;
    }
}