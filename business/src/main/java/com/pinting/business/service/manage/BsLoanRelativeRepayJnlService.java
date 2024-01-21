package com.pinting.business.service.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.pinting.business.model.vo.BsMatchVO;
/**
 * 拉取债权关系还款流水存库
 * @author HuanXiong
 * @version 2016-4-27 下午6:15:44
 */
public interface BsLoanRelativeRepayJnlService {
    
    /**
     * 拉取债权关系还款流水存库
     * @param customerId    借款人客户号
     * @param borrowNo  借款编号
     * @param id BsLoanRelativeAmountChange表id
     */
    public void drawRepayment(String customerId, String borrowNo, String id);
    
    /**
     * 查询还款列表
     * @param start
     * @param numPerPage
     * @return
     */
    public ArrayList<HashMap<String, Object>> queryRepayJnDataGrid(String customerId, String borrowNo, int start, int numPerPage);
    
    /**
     * 查询债权关系
     * @param start
     * @param numPerPage
     * @return
     */
    public ArrayList<HashMap<String, Object>> queryDataGrid(int start, int numPerPage);
    
    /**
     * 债权关系总条数
     * @return
     */
    public int countDataGrid();
    
    /**
     * 根据subAccountId查询债权明细
     * @param subAccountId
     * @return
     */
    public List<BsMatchVO> getMatchListBySubAccountId(int subAccountId);
    
}
