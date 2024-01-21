package com.pinting.business.model.vo;

import com.pinting.business.model.BsAgent;

public class SpreadChannelVO extends BsAgent{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer pageNum = 1;
    
    private Integer numPerPage = 100;
    
    private Integer start = 0;
    
    private String operationName;
    
    private Integer rowno;




	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public Integer getRowno() {
		return rowno;
	}

	public void setRowno(Integer rowno) {
		this.rowno = rowno;
	}

}
