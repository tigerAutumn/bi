package com.pinting.gateway.hfbank.out.model;

/**
 * 对账文件资金进出数据
 * @author SHENGP
 * @date  2017年5月9日 下午3:24:05
 */
public class FundDataCheckResModel extends BaseResModel {

	/**
	 * 目标文件地址
	 */
	private String destFileName;

	private String resResult;
	
	public String getDestFileName() {
		return destFileName;
	}

	public void setDestFileName(String destFileName) {
		this.destFileName = destFileName;
	}

	public String getResResult() {
		return resResult;
	}

	public void setResResult(String resResult) {
		this.resResult = resResult;
	}
	
}
