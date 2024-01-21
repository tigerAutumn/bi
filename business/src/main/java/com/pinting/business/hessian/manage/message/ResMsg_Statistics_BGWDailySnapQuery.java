package com.pinting.business.hessian.manage.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 币港湾日终账务查询
 * @author SHENGP
 * @date  2017年6月15日 下午2:29:29
 */
public class ResMsg_Statistics_BGWDailySnapQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4812928012600948268L;

	private List<HashMap<String,Object>> valueList;

    private Integer totalRows;

    private Integer pageNum;

    private Integer numPerPage;

    public List<HashMap<String, Object>> getValueList() {
		return valueList;
	}

	public void setValueList(List<HashMap<String, Object>> valueList) {
		this.valueList = valueList;
	}

	public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(Integer numPerPage) {
        this.numPerPage = numPerPage;
    }
    
}
