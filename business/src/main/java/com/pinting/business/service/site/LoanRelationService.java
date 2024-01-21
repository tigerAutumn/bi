package com.pinting.business.service.site;

import java.util.List;

import com.pinting.business.model.BsLoanRelativeRecord;
import com.pinting.gateway.hessian.message.dafy.model.Data;
import com.pinting.gateway.hessian.message.dafy.model.LoanRelationData;

public interface LoanRelationService {

	/**
	 * 获得达飞债权关系数据的下载地址
	 * @return 成功则返回下载地址，否则返回""
	 */
	public String getDafyLoanRelationAddr();
	
	/**
	 * 批量记录债权关系数据到本地数据表
     *     1. 查询所有本地的bs_loan_relative_record数据 localRecords
     *     2. localRecords数据对比newLoanRelations的数据 {
     *          1) newLoanRelations存在，但localRecords中不存在(
     *              1、localRecords为空，直接插入数据；
     *              2、localRecords不为空
     *          )
     *          2) newLoanRelations存在，且localRecords中也存在，不作处理
     *          3) newLoanRelations不存在，但localRecords中存在，修改还款状态
     *          4) newLoanRelations为空，则表示所有都已还款
     *     }
	 * @param loanRelations 债权关系数据列表
	 */
	public void batchAddLoanRelations(List<BsLoanRelativeRecord> newLoanRelations);
	
	/**
	 * 达飞债权关系全量文件获取的存储
	 *  	1. 查询所有本地的bs_loan_relative_record数据 localRecords
     *		2. localRecords数据对比newLoanRelations的数据 {
     *          1) newLoanRelations存在，但localRecords中不存在(
     *              1、localRecords为空，直接插入数据；
     *              2、localRecords不为空
     *          )
     *          2) newLoanRelations存在，且localRecords中也存在，判断匹配金额是否一致，
     *          	一致不做处理，
     *          	不一致则修改金额，并且添加前后的amount差额到数据到 借款还款流水记录表
     *          
     *          3) newLoanRelations不存在，但localRecords中存在，修改还款状态
     *          4) newLoanRelations为空，则表示所有都已还款
     *     }
	 * @param newLoanRelations
	 */
	public void addFTPNewLoanRelationsRecord(List<BsLoanRelativeRecord> newLoanRelations, String propertySymbol);
	
	/**
	 * 批量记录债权关系数据到本地数据表
	 * @param newList
	 * @param sendBatchId
	 */
	public void addNewBatchLoanRelationsRecord(List<Data> newList,String sendBatchId);
	
	/**
	 * 修改
	 * @param record
	 */
	public void updateLoanRelations(BsLoanRelativeRecord record);
	
}
