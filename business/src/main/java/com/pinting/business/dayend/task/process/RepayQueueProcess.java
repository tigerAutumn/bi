package com.pinting.business.dayend.task.process;

import com.alibaba.fastjson.JSON;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;
import com.pinting.business.accounting.loan.service.RepayPaymentService;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.coreflow.core.service.DispatcherService;
import com.pinting.business.coreflow.repay.util.ConstantsForRepay;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.dto.RepayQueueDTO;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 还款队列定时（RepayQueueTask）调用的线程
 * @author bianyatian
 * @2017-11-17 下午7:34:52
 */
public class RepayQueueProcess implements Runnable{

	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    
	private Logger log = LoggerFactory.getLogger(RepayQueueProcess.class);
    		
    private DepFixedRepayPaymentService depFixedRepayPaymentService;

    private RepayPaymentService repayPaymentService;
    
    private DispatcherService dispatcherService;
    
    private RepayQueueDTO repayQueueDTO;
    
    
	public void setDepFixedRepayPaymentService(
			DepFixedRepayPaymentService depFixedRepayPaymentService) {
		this.depFixedRepayPaymentService = depFixedRepayPaymentService;
	}

	public void setRepayPaymentService(RepayPaymentService repayPaymentService) {
		this.repayPaymentService = repayPaymentService;
	}
	
	public void setDispatcherService(DispatcherService dispatcherService) {
		this.dispatcherService = dispatcherService;
	}

	public void setRepayQueueDTO(RepayQueueDTO repayQueueDTO) {
		this.repayQueueDTO = repayQueueDTO;
	}

	@Override
	public void run() {
		//线程开始，存放redis数据
		Map<String, String> map = jsClientDaoSupport.getHashMapFromObj("repay_process");
		if (MapUtils.isEmpty(map) || map.size() == 0) {
			map = new HashMap<>();
		}
		map.put(repayQueueDTO.getLnPayOrder().getOrderNo(), repayQueueDTO.getLnPayOrder().getOrderNo());
		jsClientDaoSupport.addObjOfHashMap(map, "repay_process", -1);
		try {
			if(repayQueueDTO != null){
				log.info(">>>DK还款线程，repayQueueDTO数据："+ JSON.toJSONString(repayQueueDTO) +"<<<");
				//调用还款业务
				if (repayQueueDTO.getLnPayOrder() == null || repayQueueDTO.getLnPayOrder().getPartnerCode() == null) {
					throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "未找到对应的代扣还款订单信息或未找到合作方编码");
				}
				if (PartnerEnum.YUN_DAI_SELF.getCode().equals(repayQueueDTO.getChannel())) {
					HashMap<String, Object> extendMap = new HashMap<String, Object>();
					extendMap.put("lnPayOrders",	repayQueueDTO.getLnPayOrder());
					extendMap.put("lnBindCard",		repayQueueDTO.getLnBindCard());
					extendMap.put("userSignNo",	repayQueueDTO.getUserSignNo());
					extendMap.put("payIp",		repayQueueDTO.getPayIP());
					FlowContext flowContext = new FlowContext();
					flowContext.setTransCode(ConstantsForRepay.REPAY_WITHHOLDING);
					flowContext.setPartnerEnum(PartnerEnum.YUN_DAI_SELF);
					flowContext.setBusinessType("");
					flowContext.setExtendMap(extendMap);
					dispatcherService.dispatcherService(flowContext);
				} else if( PartnerEnum.SEVEN_DAI_SELF.getCode().equals(repayQueueDTO.getChannel()) ) {
					depFixedRepayPaymentService.withholdingRepayAsync(repayQueueDTO.getLnPayOrder(),repayQueueDTO.getLnBindCard(),repayQueueDTO.getUserSignNo(),repayQueueDTO.getPayIP());
				} else if(PartnerEnum.ZSD.getCode().equals(repayQueueDTO.getChannel())) {
					depFixedRepayPaymentService.withholdingRepayZsdAsync(repayQueueDTO.getLnPayOrder(),repayQueueDTO.getLnBindCard());
				} else if(PartnerEnum.ZAN.getCode().equals(repayQueueDTO.getChannel())) {
					repayPaymentService.withholdingRepaySendBaoFoo(repayQueueDTO.getLnPayOrder(),repayQueueDTO.getLnBindCard());
				}else {
					log.error("合作方类型有误");
				}
			}else{
				log.info(">>>还款队列lpop为空<<<");
			}
		} catch (Exception e) {
			log.error("还款队列线程执行异常", e);
		} finally{
			jsClientDaoSupport.deleteObjOfHashMap(repayQueueDTO.getLnPayOrder().getOrderNo(), "repay_process");
		}
	}


}
