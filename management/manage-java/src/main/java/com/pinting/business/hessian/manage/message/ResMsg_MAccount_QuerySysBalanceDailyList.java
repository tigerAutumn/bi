package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 每日日终账务查询
 * @author SHENGUOPING
 * @date  2017年11月22日 下午10:14:54
 */
public class ResMsg_MAccount_QuerySysBalanceDailyList extends ResMsg {

	private static final long serialVersionUID = 4497624819247965979L;

	private ArrayList<HashMap<String,Object>> valueList;

    private Integer totalRows;

    private Integer pageNum;

    private Integer numPerPage;
    
    public ArrayList<HashMap<String, Object>> getValueList() {
        return valueList;
    }

    public void setValueList(ArrayList<HashMap<String, Object>> valueList) {
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
