package com.pinting.business.dayend.task.process;

import com.alibaba.fastjson.JSONObject;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.coreflow.core.service.DispatcherService;
import com.pinting.business.coreflow.core.enums.BusinessTypeEnum;
import com.pinting.business.coreflow.repay.util.ConstantsForRepay;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnPayOrders;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 还款业务结果处理轮询（DepFixedRepayHandleTask）调用的线程
 * Created by cyb on 2017/12/15.
 */
public class RepayHandleProcess implements Runnable {

    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    private Logger log = LoggerFactory.getLogger(RepayHandleProcess.class);

    private DepFixedRepayPaymentService depFixedRepayPaymentService;
    
    private DispatcherService dispatchService;

    private JSONObject repayBillJson;

    private LnPayOrders lnPayOrder;
    
    private LnLoan lnLoan;

    private PartnerEnum partnerEnum;

    public void setPartnerEnum(PartnerEnum partnerEnum) {
        this.partnerEnum = partnerEnum;
    }

    public DepFixedRepayPaymentService getDepFixedRepayPaymentService() {
        return depFixedRepayPaymentService;
    }

    public void setDepFixedRepayPaymentService(DepFixedRepayPaymentService depFixedRepayPaymentService) {
        this.depFixedRepayPaymentService = depFixedRepayPaymentService;
    }
    
    public DispatcherService getDispatchService() {
		return dispatchService;
	}

	public void setDispatchService(DispatcherService dispatchService) {
		this.dispatchService = dispatchService;
	}

	public void setRepayBillJson(JSONObject repayBillJson) {
        this.repayBillJson = repayBillJson;
    }

    public void setLnPayOrder(LnPayOrders lnPayOrder) {
        this.lnPayOrder = lnPayOrder;
    }

    public LnLoan getLnLoan() {
		return lnLoan;
	}

	public void setLnLoan(LnLoan lnLoan) {
		this.lnLoan = lnLoan;
	}

	@Override
    public void run() {
        //线程开始，存放redis数据
        Map<String, String> map = jsClientDaoSupport.getHashMapFromObj("repay_handle_process");
        if (MapUtils.isEmpty(map) || map.size() == 0) {
            map = new HashMap<>();
        }
        map.put(lnPayOrder.getOrderNo(), lnPayOrder.getOrderNo());
        jsClientDaoSupport.addObjOfHashMap(map, "repay_handle_process", -1);
        try {
            if(repayBillJson != null) {
                if(PartnerEnum.YUN_DAI_SELF.equals(partnerEnum)) {
                	//如果是云贷分期产品,则走coreflow代码
                	if(BusinessTypeEnum.EQUAL_PRINCIPAL_INTEREST.getCode().equals(lnLoan.getPartnerBusinessFlag()) || 
                			BusinessTypeEnum.AVERAGE_CAPITAL_PLUS_INTEREST.getCode().equals(lnLoan.getPartnerBusinessFlag())) {
                		FlowContext flowContext = new FlowContext();
                		flowContext.setTransCode(ConstantsForRepay.REPAY_SHARE_PROFIT);
                		flowContext.setBusinessType(lnLoan.getPartnerBusinessFlag());
                		flowContext.setPartnerEnum(partnerEnum);
                		flowContext.getExtendMap().put("repayBillJson", repayBillJson);
                		flowContext.getExtendMap().put("lnPayOrder", lnPayOrder);
                		flowContext.getExtendMap().put("lnLoan", lnLoan);
                		dispatchService.dispatcherService(flowContext);
                	} else {
                		depFixedRepayPaymentService.yunDaiBillSync(repayBillJson, lnPayOrder);
                	}
                } else if( PartnerEnum.ZSD.equals(partnerEnum) ) {
                    depFixedRepayPaymentService.zsdBillSync(repayBillJson, lnPayOrder);
                } else if(PartnerEnum.SEVEN_DAI_SELF.equals(partnerEnum)) {
                    depFixedRepayPaymentService.sevenDaiBillSync(repayBillJson, lnPayOrder);
                } else {
                    log.error("还款业务处理定时线程合作方类型有误");
                }
            } else {
                log.info(">>>还款业务处理队列lpop为空<<<");
            }
        } catch (Exception e) {
            log.error("还款业务处理定时线程执行异常：{}", e.getMessage());
        } finally{
            jsClientDaoSupport.deleteObjOfHashMap(lnPayOrder.getOrderNo(), "repay_handle_process");
        }
    }

}
