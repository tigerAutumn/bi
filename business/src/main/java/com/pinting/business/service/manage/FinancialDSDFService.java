package com.pinting.business.service.manage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.vo.RepayDSDFVO;

/**
 * 财务统计-代收代付
 * @author bianyatian
 * @2017-11-9 下午3:55:24
 */
public interface FinancialDSDFService {
	
	/**
     * 管理台归集户代收代付列表查询
     * @param name
     * @param mobile
     * @param type
     * @param timeStart
     * @param timeEnd
     * @param page
	 * @param offset
     * @return
     */
    List<RepayDSDFVO> getRepayDSDFList(String name, String mobile, String type,
    		String timeStart, String timeEnd, String status, Integer page, Integer offset);
    
    /**
     * 管理台归集户代收代付列表总条数
     * @param name
     * @param mobile
     * @param type
     * @param timeStart
     * @param timeEnd
     * @return
     */
    Integer countRepayDSDF(String name, String mobile, String type,
    		String timeStart, String timeEnd, String status);
    
    /**
     * 管理台归集户代收代付，代收代付总金额
     * @param name
     * @param mobile
     * @param type
     * @param timeStart
     * @param timeEnd
     * @return
     */
    RepayDSDFVO sumRepayDSDF(String name, String mobile, String type,
    		String timeStart, String timeEnd, String status);

    /**
     * 管理台归集户代收代付，下载批次流水数据查询
     * @param time
     * @return
     */
    List<RepayDSDFVO> queryRepayDSDFBatchFlowList(String time);

}
