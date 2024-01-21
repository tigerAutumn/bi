package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 对账文件资金进出数据
 * @author SHENGP
 * @date  2017年5月9日 下午2:56:39
 */
public class B2GResMsg_HFBank_FundDataCheck extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5554724140083122308L;

	/* 返回码，10000为成功 */
    private String recode;

    /* 返回结果描述 */
    private String remsg;

    private String destFileName;
    
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

	public String getDestFileName() {
		return destFileName;
	}

	public void setDestFileName(String destFileName) {
		this.destFileName = destFileName;
	}
	
}
