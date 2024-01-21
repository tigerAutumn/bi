package com.pinting.business.service.site.impl;

import com.pinting.business.dao.BsLoanRelativeRecordMapper;
import com.pinting.business.model.BsLoanRelativeAmountChange;
import com.pinting.business.model.BsLoanRelativeRecord;
import com.pinting.business.model.BsLoanRelativeRecordExample;
import com.pinting.business.model.vo.BsDailyInterestVO;
import com.pinting.business.service.site.LoanRelationService;
import com.pinting.business.service.site.LoanRelativeAmountChangeService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_GetLoanRelationUrl;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_GetLoanRelationUrl;
import com.pinting.gateway.hessian.message.dafy.model.Data;
import com.pinting.gateway.out.service.DafyTransportService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LoanRelationServiceImpl implements LoanRelationService {
	private Logger log = LoggerFactory.getLogger(LoanRelationServiceImpl.class);
    @Autowired
    private DafyTransportService       dafyTransportService;
    @Autowired
    private BsLoanRelativeRecordMapper bsLoanRelativeRecordMapper;
    @Autowired
    private LoanRelativeAmountChangeService loanRelativeAmountChangeService;
    

    @Override
    public String getDafyLoanRelationAddr() {
        B2GReqMsg_Customer_GetLoanRelationUrl req = new B2GReqMsg_Customer_GetLoanRelationUrl();
        B2GResMsg_Customer_GetLoanRelationUrl res = dafyTransportService.queryLoanRelation(req);
        String downloadUrl = res.getDownLoadUrl();
        return StringUtil.isNotEmpty(downloadUrl) ? downloadUrl : StringUtil.EMPTY;
    }

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
     * @see com.pinting.business.service.site.LoanRelationService#batchAddLoanRelations(java.util.List)
     */
    @Override
    public void batchAddLoanRelations(List<BsLoanRelativeRecord> newLoanRelations) {
        // 1. 查询所有本地的bs_loan_relative_record数据 localRecords
        BsLoanRelativeRecordExample bsLoanRelativeRecordExample = new BsLoanRelativeRecordExample();
        bsLoanRelativeRecordExample.createCriteria().andChannelEqualTo(
            Constants.LOAN_RELATIVE_CANNEL_DAFY_OLD).andIsRepayEqualTo(Constants.LOAN_RELATIVE_IS_REPAY_N);
        List<BsLoanRelativeRecord> localRecords = bsLoanRelativeRecordMapper
            .selectByExample(bsLoanRelativeRecordExample);

        // 2. localRecords对比loanRelations的数据
        if (!CollectionUtils.isEmpty(newLoanRelations)) {
            // 2.1.1 localRecords为空，直接插入数据；
            if (CollectionUtils.isEmpty(localRecords)) {
                for (BsLoanRelativeRecord bsLoanRelativeRecord : newLoanRelations) {
                    bsLoanRelativeRecordMapper.insertSelective(bsLoanRelativeRecord);
                }
            }
            // 2.1.2 localRecords不为空
            else {
                for (BsLoanRelativeRecord remoteRecord : newLoanRelations) {
                    boolean flag = true;
                    for (BsLoanRelativeRecord localRecord : localRecords) {
                        if (remoteRecord.getAmount().compareTo(localRecord.getAmount()) == 0
                            && remoteRecord.getLenderCustomerId().equals(
                                localRecord.getLenderCustomerId())
                            && remoteRecord.getBorrowerCustomerId().equals(
                                localRecord.getBorrowerCustomerId())
								&& (remoteRecord.getTime()==null || remoteRecord.getTime().equals(localRecord.getTime())) // 放款完成时间
                            && remoteRecord.getProductName().equals(localRecord.getProductName())) {
                            flag = false;
                            break;
                        }
                    }
                    // 2.1 loanRelations存在，但localRecords中不存在
                    if (flag) {
                        bsLoanRelativeRecordMapper.insertSelective(remoteRecord);
                    }
                    // 2.2 newLoanRelations存在，且localRecords中也存在，不作处理
                }
            }
            
            // 2.3 newLoanRelations不存在，但localRecords中存在，修改还款状态
            for (BsLoanRelativeRecord localRecord : localRecords) {
                boolean flag = true;
                for (BsLoanRelativeRecord remoteRecord : newLoanRelations) {
                    // 本地存在且达飞数据也存在
                    if(remoteRecord.getAmount().compareTo(localRecord.getAmount()) == 0
                            && remoteRecord.getLenderCustomerId().equals(
                                localRecord.getLenderCustomerId())
                            && remoteRecord.getBorrowerCustomerId().equals(
                                localRecord.getBorrowerCustomerId())
							&& (remoteRecord.getTime()==null || remoteRecord.getTime().equals(localRecord.getTime())) // 放款完成时间
                            && remoteRecord.getProductName().equals(localRecord.getProductName())) {
                        flag = false;
                        break;
                    }
                }
                // newLoanRelations不存在，但localRecords中存在
                if(flag) {
                    localRecord.setRepayTime(new Date());
                    localRecord.setIsRepay(Constants.LOAN_RELATIVE_IS_REPAY_Y);
                    updateLoanRelations(localRecord);
                }
            }
        }
        // 2.4 为空，则证明已经全部还清，修改状态
        else {
            if(!CollectionUtils.isEmpty(localRecords)){
                for (BsLoanRelativeRecord localRecord : localRecords) {
                    localRecord.setIsRepay(Constants.LOAN_RELATIVE_IS_REPAY_Y);
                    localRecord.setRepayTime(new Date());
                    updateLoanRelations(localRecord);
                }
            }
        }
    }

	private void insertNewRecord(Data record, String sendBatchId,String isRepay) {
		BsLoanRelativeRecord loanRelativeRecord = new BsLoanRelativeRecord();
		loanRelativeRecord.setAmount(record.getAmount());
		loanRelativeRecord.setLeftAmount(record.getAmount());
		loanRelativeRecord.setBorrowerCustomerId(record.getBorrowerId());
		loanRelativeRecord.setBorrowerIdCard(record.getBorrowerIdCard());
		loanRelativeRecord.setBorrowerName(record.getBorrowerName());
		loanRelativeRecord.setChannel(Constants.LOAN_RELATIVE_CANNEL_DAFY_NEW);
		loanRelativeRecord.setCreateTime(new Date());
		loanRelativeRecord.setCustomerId(record.getLenderId());
		loanRelativeRecord.setIsRepay(isRepay); //是否已还款
		loanRelativeRecord.setLenderCustomerId(record.getLenderId());
		loanRelativeRecord.setLenderIdCard(record.getLenderIdCard());
		loanRelativeRecord.setLenderName(record.getLenderName());
		loanRelativeRecord.setOrderNo(sendBatchId);
		loanRelativeRecord.setProductName(record.getProductName());
		loanRelativeRecord.setRemark(record.getBorrowNote());
		loanRelativeRecord.setTime(record.getCreateTime());
		loanRelativeRecord.setUpdateTime(new Date());
        bsLoanRelativeRecordMapper.insertSelective(loanRelativeRecord);
		
	}

	/**
	 * 1. 根据order_no、is_repay(未还款)、channel状态查找旧数据（获取到的新数据为空则不操作）
	 * 2. 旧数据为空，新增newList的数据，根据状态添加是否已还款字段
	 * 3. 旧数据不为空时操作
	 * 	for(newList){
	 * 		for(oldList){
	 * 			if(旧数据 能 匹配新数据){
	 * 				判断是否是已转让状态，是则修改还款状态和还款时间，否则不操作
	 * 			}
	 * 		}
	 * 		不能匹配，则新增newList的数据，根据状态添加是否已还款字段
	 * 	}
	 */
	@Override
	public void addNewBatchLoanRelationsRecord(List<Data> newList,
			String sendBatchId) {
		 // 1. 根据order_no、is_repay(未还款)、channel状态查找旧数据-->oldList
        BsLoanRelativeRecordExample bsLoanRelativeRecordExample = new BsLoanRelativeRecordExample();
        bsLoanRelativeRecordExample.createCriteria().andChannelEqualTo(
            Constants.LOAN_RELATIVE_CANNEL_DAFY_NEW).andIsRepayEqualTo(Constants.LOAN_RELATIVE_IS_REPAY_N)
            .andOrderNoEqualTo(sendBatchId);
        List<BsLoanRelativeRecord> oldList = bsLoanRelativeRecordMapper
            .selectByExample(bsLoanRelativeRecordExample);
        
        if(!CollectionUtils.isEmpty(newList)){
        	if(CollectionUtils.isEmpty(oldList)){
        		//2. oldList为空,直接添加数据
        		for (Data record : newList) {
        			if(Constants.DAFY_RETURN_LOAN_RELATIVE_STATE_2.equals(record.getState())){
        				//判断生效
        				insertNewRecord(record, sendBatchId,Constants.LOAN_RELATIVE_IS_REPAY_N);
        			}else if(Constants.DAFY_RETURN_LOAN_RELATIVE_STATE_9.equals(record.getState())
        					|| Constants.DAFY_RETURN_LOAN_RELATIVE_STATE_10.equals(record.getState())){
        				//判断结束或已转让
        				insertNewRecord(record, sendBatchId,Constants.LOAN_RELATIVE_IS_REPAY_Y);
        			}
                }
        	}else{
        		for (Data record : newList) {
        			 boolean flag = true;
        			 for (BsLoanRelativeRecord oldRecord : oldList) {
     					if(oldRecord.getAmount().compareTo(record.getAmount()) == 0
     							&& record.getBorrowerId().equals(oldRecord.getBorrowerCustomerId())
     							&& record.getLenderId().equals(oldRecord.getLenderCustomerId())
     							&& record.getBorrowNote().equals(oldRecord.getRemark())
     							&& record.getCreateTime().equals(oldRecord.getTime())){
     						if(Constants.DAFY_RETURN_LOAN_RELATIVE_STATE_9.equals(record.getState())
             					|| Constants.DAFY_RETURN_LOAN_RELATIVE_STATE_10.equals(record.getState())){
     							//已存在数据，但状态发生变化
     							oldRecord.setRepayTime(new Date());
         						oldRecord.setIsRepay(Constants.LOAN_RELATIVE_IS_REPAY_Y);
         						bsLoanRelativeRecordMapper.updateByPrimaryKeySelective(oldRecord);
     						}
     						flag = false;
     						break;
     					}
     				}
        			 //2.3 newList中的数据在oldList中 不存在，新增
        			 if (flag) {
        				 if(Constants.DAFY_RETURN_LOAN_RELATIVE_STATE_2.equals(record.getState())){
             				//判断生效
             				insertNewRecord(record, sendBatchId,Constants.LOAN_RELATIVE_IS_REPAY_N);
             			}else if(Constants.DAFY_RETURN_LOAN_RELATIVE_STATE_9.equals(record.getState())
             					|| Constants.DAFY_RETURN_LOAN_RELATIVE_STATE_10.equals(record.getState())){
             				//校验是否已经存在数据
             				BsLoanRelativeRecordExample example = new BsLoanRelativeRecordExample();
             				example.createCriteria().andChannelEqualTo(Constants.LOAN_RELATIVE_CANNEL_DAFY_NEW)
             		            .andAmountEqualTo(record.getAmount()).andBorrowerCustomerIdEqualTo(record.getBorrowerId())
             		            .andLenderCustomerIdEqualTo(record.getLenderId()).andRemarkEqualTo(record.getBorrowNote())
             		            .andTimeEqualTo(record.getCreateTime());
             		        List<BsLoanRelativeRecord> checkList = bsLoanRelativeRecordMapper
             		            .selectByExample(example);
             		        
             		        if(CollectionUtils.isEmpty(checkList)){
             		        	//判断结束或已转让
                 				insertNewRecord(record, sendBatchId,Constants.LOAN_RELATIVE_IS_REPAY_Y);
             		        }
             			}
                     }
				}
        	}
        }
		
	}

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
     *          3) newLoanRelations不存在，但localRecords中存在，修改还款状态
     *          4) newLoanRelations为空，不做处理
     *     }
	 */
	@Override
	public void addFTPNewLoanRelationsRecord(
			List<BsLoanRelativeRecord> newLoanRelations, String propertySymbol) {
		// 1. 查询所有本地的bs_loan_relative_record数据 localRecords
		List<BsLoanRelativeRecord> localRecords = new ArrayList<BsLoanRelativeRecord>();
		Integer count = bsLoanRelativeRecordMapper.countByChannelSymbol(Constants.LOAN_RELATIVE_CANNEL_DAFY_NEW_ALL,
				Constants.LOAN_RELATIVE_IS_REPAY_N, propertySymbol);
		int maxSize = 100;
		int uMaxPage = count%maxSize==0 ? count/maxSize : count/maxSize + 1;
		log.info("==================查询所有本地的bs_loan_relative_record数据总条数："+count);
		for (int i = 1; i <= uMaxPage; i++){
			List<BsLoanRelativeRecord> list = bsLoanRelativeRecordMapper.selectByChannelSymbolPage(Constants.LOAN_RELATIVE_CANNEL_DAFY_NEW_ALL,
				Constants.LOAN_RELATIVE_IS_REPAY_N, propertySymbol, maxSize*(i-1), count<(maxSize*i)?count:(maxSize*i));
			localRecords.addAll(list);
		}
		log.info("==================查询所有本地的bs_loan_relative_record数据,列表总条数："+localRecords.size());
        // 2. localRecords对比loanRelations的数据
        if (!CollectionUtils.isEmpty(newLoanRelations)) {
            // 2.1.1 localRecords为空，直接插入数据；
            if (CollectionUtils.isEmpty(localRecords)) {
                for (BsLoanRelativeRecord bsLoanRelativeRecord : newLoanRelations) {
                	bsLoanRelativeRecord.setLeftAmount(bsLoanRelativeRecord.getAmount()); //本地剩余匹配金额
                	bsLoanRelativeRecord.setInitAmount(bsLoanRelativeRecord.getAmount()); //初始匹配金额
                	 
                    bsLoanRelativeRecordMapper.insertSelective(bsLoanRelativeRecord);
                }
            }
            // 2.1.2 localRecords不为空
            else {
                for (BsLoanRelativeRecord remoteRecord : newLoanRelations) {
                    boolean flag = true;//是否是新数据
                    boolean flag1 = true;//匹配金额是否无变动
                    Double replyAmount = 0.0; //还款金额
                    Double beforeAmount = 0.0; //还款前金额
                    Integer loanRelativeId = 0;
                    Integer deleteId = -1;
                    int i = 0;
                    for (BsLoanRelativeRecord localRecord : localRecords) {
                    	if(remoteRecord.getOrderNo().equals(localRecord.getOrderNo()) //理财编号
                                && remoteRecord.getLenderCustomerId().equals(localRecord.getLenderCustomerId()) //理财人客户编号
                                && remoteRecord.getBorrowerCustomerId().equals(localRecord.getBorrowerCustomerId()) //借款人客户编号
                                && (remoteRecord.getTime()==null || remoteRecord.getTime().equals(localRecord.getTime())) // 放款完成时间
                                && remoteRecord.getProductName().equals(localRecord.getProductName()) //理财产品名称
                                && remoteRecord.getBorrowId().equals(localRecord.getBorrowId())) { //借款编号
                            flag = false;
                            //对比匹配金额
                            if(remoteRecord.getAmount().compareTo(localRecord.getAmount()) < 0){
                            	flag1 = false;
                            	replyAmount = MoneyUtil.subtract(localRecord.getAmount(), remoteRecord.getAmount()).doubleValue();
                            	beforeAmount = localRecord.getAmount();
                            	loanRelativeId = localRecord.getId();
                            	deleteId = i;
                            }
                            break;
                        }
                        i++;
                    }
                    
                    // 2.1 loanRelations存在，但localRecords中不存在
                    if (flag) {
                    	remoteRecord.setLeftAmount(remoteRecord.getAmount()); //本地剩余匹配金额
                    	remoteRecord.setInitAmount(remoteRecord.getAmount()); //初始匹配金额
                        bsLoanRelativeRecordMapper.insertSelective(remoteRecord);
                    }else{
                    	// 2.2 newLoanRelations存在，且localRecords中也存在，判断匹配金额是否一致，一致不做处理
                    	//不一致则修改金额，并且添加前后的amount差额到数据到 借款还款流水记录表
                    	//在localRecords列表中，减去已经修改的数据
                    	if(!flag1){
                    		//修改金额
                            BsLoanRelativeRecord updateRecord = new BsLoanRelativeRecord();
                        	updateRecord.setId(loanRelativeId);
                        	updateRecord.setAmount(remoteRecord.getAmount());
                        	updateRecord.setRepayTime(DateUtil.addDays(new Date(), -1));
                        	updateLoanRelations(updateRecord);
                        	//在localRecords列表中，减去已经修改的数据
                        	
                        	localRecords.remove(deleteId);
                        	//添加前后的amount差额到数据到 借款还款流水记录表
                    		BsLoanRelativeAmountChange changeRecord = new BsLoanRelativeAmountChange();
                    		changeRecord.setAfterAmount(remoteRecord.getAmount());
                    		changeRecord.setBeforeAmount(beforeAmount);
                    		changeRecord.setLoanRelativeId(loanRelativeId);
                    		changeRecord.setRepayAmount(replyAmount);
                    		changeRecord.setDealStatus(Constants.MATCH_AMOUNT_DEAL_STATUS_N);//未处理
                    		changeRecord.setPropertySymbol(remoteRecord.getPropertySymbol());
                    		loanRelativeAmountChangeService.addLoanRelativeAmountChange(changeRecord);
                    	}
                    }
                    
                }
            }
            
            // 2.3 newLoanRelations不存在，但localRecords中存在，修改还款状态
            for (BsLoanRelativeRecord localRecord : localRecords) {
                boolean flag = true;
                for (BsLoanRelativeRecord remoteRecord : newLoanRelations) {
                    // 本地存在且达飞数据也存在
                    if(remoteRecord.getOrderNo().equals(localRecord.getOrderNo()) //理财编号
                            && remoteRecord.getLenderCustomerId().equals(localRecord.getLenderCustomerId()) //理财人客户编号
                            && remoteRecord.getBorrowerCustomerId().equals(localRecord.getBorrowerCustomerId()) //借款人客户编号
							&& (remoteRecord.getTime()==null || remoteRecord.getTime().equals(localRecord.getTime())) // 放款完成时间
                            && remoteRecord.getProductName().equals(localRecord.getProductName()) //产品理财名称
                            && remoteRecord.getBorrowId().equals(localRecord.getBorrowId())) { //借款编号
                        flag = false;
                        break;
                    }
                }
                // newLoanRelations不存在，但localRecords中存在
                if(flag) {
                    
                    //添加前后的amount差额到数据到 借款还款流水记录表
            		BsLoanRelativeAmountChange changeRecord = new BsLoanRelativeAmountChange();
            		changeRecord.setAfterAmount(0.0);
            		changeRecord.setBeforeAmount(localRecord.getAmount());
            		changeRecord.setLoanRelativeId(localRecord.getId());
            		changeRecord.setRepayAmount(localRecord.getAmount());
            		changeRecord.setDealStatus(Constants.MATCH_AMOUNT_DEAL_STATUS_N);//未处理
            		changeRecord.setPropertySymbol(localRecord.getPropertySymbol());
            		loanRelativeAmountChangeService.addLoanRelativeAmountChange(changeRecord);
            		
            		//修改老数据，还款状态，匹配金额
                    BsLoanRelativeRecord updateRecord = new BsLoanRelativeRecord();
                	updateRecord.setId(localRecord.getId());
                	updateRecord.setRepayTime(DateUtil.addDays(new Date(), -1));
            		updateRecord.setAmount(0.0); //匹配金额置为0
            		updateRecord.setIsRepay(Constants.LOAN_RELATIVE_IS_REPAY_Y);
                    updateLoanRelations(updateRecord);
                    
                }
            }
        }
		
	}

	@Override
	public void updateLoanRelations(BsLoanRelativeRecord record) {
		record.setUpdateTime(new Date());
		bsLoanRelativeRecordMapper.updateByPrimaryKeySelective(record);
		
	}
}
