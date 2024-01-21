package com.pinting.business.service.manage;

import com.pinting.business.model.dto.StagingProductsLoanDTO;
import com.pinting.business.model.vo.StagingProductsLoanVO;

import java.util.List;

/**
 * 分期产品出借查询
 * Created by shh on 2016/11/7 19:58.
 */
public interface StagingProductsLoanService {

    /**
     * 品听-分期产品出借查询 列表
     * @param record
     * @return
     */
    public List<StagingProductsLoanVO> queryPTStagingProductsLoan(StagingProductsLoanVO record);

    /**
     * 品听-分期产品出借查询 统计
     * @param record
     * @return
     */
    public int queryPTStagingProductsLoanCount(StagingProductsLoanVO record);

    /**
     * 钱报分期产品出借查询
     * @param dto
     * @return
     */
    List<StagingProductsLoanVO> queryQBStagingProductsLoan(StagingProductsLoanDTO dto);

    /**
     * 钱报分期产品出借查询统计
     * @param dto
     * @return
     */
    int queryQBStagingProductsLoanCount(StagingProductsLoanDTO dto);

    /**
     * 杭商-分期产品出借查询 列表
     * @param record
     * @return
     */
    public List<StagingProductsLoanVO> queryHSStagingProductsLoan(StagingProductsLoanVO record);

    /**
     * 杭商-分期产品出借查询 统计
     * @param record
     * @return
     */
    public int queryHSStagingProductsLoanCount(StagingProductsLoanVO record);

}
