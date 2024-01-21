package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2016/8/29
 * Description:
 */
public class ResMsg_Match_QueryLnLoanRelationList extends ResMsg {

    private static final long serialVersionUID = 2230415125072979038L;

    private List<HashMap<String, Object>> grid;

    private int totalRows;

    private int totalPages;

    public List<HashMap<String, Object>> getGrid() {
        return grid;
    }

    public void setGrid(List<HashMap<String, Object>> grid) {
        this.grid = grid;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
