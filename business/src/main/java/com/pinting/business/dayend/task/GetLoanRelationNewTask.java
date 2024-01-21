package com.pinting.business.dayend.task;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.dao.BsBatchBuyMapper;
import com.pinting.business.dayend.task.process.AddNewLoanRelativeRecordProcess;
import com.pinting.business.model.BsBatchBuy;
import com.pinting.business.service.manage.BsSpecialJnlService;
import com.pinting.business.service.site.LoanRelationService;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_GetLoanRelationNew;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_GetLoanRelationNew;
import com.pinting.gateway.hessian.message.dafy.model.Data;
import com.pinting.gateway.hessian.message.dafy.model.LoanRelationData;
import com.pinting.gateway.out.service.DafyTransportService;
/**
 * 通过接口获取债权关系，并记录
 * @author bianyatian
 * @2016-1-20 下午4:04:21
 */
@Deprecated
public class GetLoanRelationNewTask {
	private Logger log = LoggerFactory.getLogger(GetLoanRelationNewTask.class);
	@Autowired
	private BsBatchBuyMapper bsBatchBuyMapper;
	@Autowired
	private DafyTransportService dafyTransportService;
	@Autowired
	private LoanRelationService loanRelationService;
	@Autowired
	private BsSpecialJnlService bsSpecialJnlService;
	/**
	 * 
	 */
	@Deprecated
	public void execute() {
		log.info("================定时任务{获取新的债权关系}开始================");
		try {
			//查询状态为BUY_DAFY_SUCCESS的数据
		    //和状态为DAFY_RETURN_SUCCESS或DAFY_RETURN_FAIL，且send_batch_id不存在于bs_loan_relative_record表order_no字段中
			List<BsBatchBuy> batchBuysList = bsBatchBuyMapper.selectBsBatchBuy4LoanRelation();
			if(batchBuysList != null && batchBuysList.size()>0){
				log.info("================bs_batch_buy表中状态为BUY_DAFY_SUCCESS的数据条数："+batchBuysList.size()+"================");
				for (BsBatchBuy bsBatchBuy : batchBuysList) {
					try {
						List<Data> list = new ArrayList<Data>();
						int pageIndex = 1;
						int pageNum = 50;
						int count = pageNum;
						while((count/pageNum + (count%pageNum > 0?1:0)) >= pageIndex){
							//获取数据
							B2GReqMsg_Customer_GetLoanRelationNew req = new B2GReqMsg_Customer_GetLoanRelationNew();
							req.setOrderNo(bsBatchBuy.getSendBatchId());
							req.setPageIndex(String.valueOf(pageIndex));
							req.setPageNum(String.valueOf(pageNum));
							B2GResMsg_Customer_GetLoanRelationNew res = null;
							res = dafyTransportService.queryLoanRelationNew(req);
							if(res == null){
								count = 0;
								pageIndex = 1;
							}else{
								count = Integer.valueOf(res.getCount()== null ? "0":res.getCount());
								pageIndex = Integer.valueOf(res.getPageIndex()== null ? "1":res.getPageIndex())+1;
								if(res.getData() != null && res.getData().size()>0){
									list.addAll(res.getData());
								}
							}
						}
						log.info("================获取到的债权关系数据条数："+list.size()+"================");
						AddNewLoanRelativeRecordProcess process = new AddNewLoanRelativeRecordProcess();
						process.setList(list);
						process.setLoanRelationService(loanRelationService);
						process.setSendBatchId(bsBatchBuy.getSendBatchId());
						new Thread(process).start();
					} catch (Exception e) {
						log.error("================定时任务{获取新的债权关系}批次号["+bsBatchBuy.getSendBatchId()+"]查询债权关系异常================", e);
						String type = "定时任务{获取新的债权关系}";
						String detail = "定时任务{获取新的债权关系}批次号["+bsBatchBuy.getSendBatchId()+"]查询债权关系异常" + StringUtil.left(e.getMessage(), 20);
						bsSpecialJnlService.addSpecialJnl(type, detail);
					}
				}
			}
			
		} catch (Exception e) {
			log.error("================定时任务{获取新的债权关系}报错================", e);
			String type = "定时任务{获取新的债权关系}";
			String detail = "定时任务{获取新的债权关系}查询债权关系异常" + StringUtil.left(e.getMessage(), 20);
			bsSpecialJnlService.addSpecialJnl(type, detail);
		}
		log.info("================定时任务{获取新的债权关系}结束================");
	}
	
}
