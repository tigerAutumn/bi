package com.pinting.business.dayend.task;

import com.pinting.business.accounting.finance.model.SysActTransSendInfo;
import com.pinting.business.accounting.finance.service.SysProductBuyService;
import com.pinting.business.dao.BsBatchBuyMapper;
import com.pinting.business.dao.BsChannelCheckMapper;
import com.pinting.business.model.BsBatchBuy;
import com.pinting.business.model.BsChannelCheck;
import com.pinting.business.model.BsChannelCheckExample;
import com.pinting.business.model.vo.BsSubAc4BatchBuyVO;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;


/**
 * 统计今天所有购买的产品
 * 将购买金额(总金额)转账给达飞账户
 * @Project: business
 * @Title: SysAcctTrans4BatchBuyTask.java
 * @author dingpf
 * @date 2015-11-19 下午3:06:28
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Deprecated
public class SysAcctTrans4BatchBuyTask {
	private Logger log = LoggerFactory.getLogger(SysAcctTrans4BatchBuyTask.class);
	
	@Autowired
	private SysProductBuyService sysProductBuyService;
	@Autowired
	private BsChannelCheckMapper bsChannelCheckMapper;
	@Autowired
	private SpecialJnlService specialJnlService;
	@Autowired
	private BsBatchBuyMapper bsBatchBuyMapper;

	/**
	 * 任务执行
	 */
	public void execute() {
		// 定时任务{系统转账理财总金额给达飞}
		log.info("=========定时任务{系统钱包转账-云贷理财购买}开始=========");
		//查询渠道互转审核状态，判断是否允许触发系统钱包转账
		BsChannelCheckExample example = new BsChannelCheckExample();
		Date currDay = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
		example.createCriteria().andCreateTimeBetween(currDay, DateUtil.addDays(currDay, 1));
		List<BsChannelCheck> checks = bsChannelCheckMapper.selectByExample(example);
		//有渠道互转审核记录
		if(checks!=null && checks.size()>0){
			log.info("=========定时任务{系统钱包转账-云贷理财购买}发现渠道互转审核记录["+checks.size()+"]笔=========");
			boolean isTransfer = true; //是否转账标志，true：转账；false：不转账
			for (BsChannelCheck check : checks) {
				String status = check.getStatus();
				if(!Constants.CHANNEL_CHECK_STATUS_CONFIRMED.equals(status)){
					isTransfer = false;
					break;
				}
			}
			//只要有一条审核记录状态为非确认，则不触发转账
			if(isTransfer){
				log.info("=========定时任务{系统钱包转账-云贷理财购买}渠道互转审核记录均已确认，修改审核记录状态为完成，并触发系统钱包转账=========");
				//修改所有审核记录状态为完成
				BsChannelCheck record = new BsChannelCheck();
				record.setStatus(Constants.CHANNEL_CHECK_STATUS_FINISHED);
				record.setUpdateTime(new Date());
				bsChannelCheckMapper.updateByExampleSelective(record, example);
				//触发云贷转账
				List<BsSubAc4BatchBuyVO> yunProducts = sysProductBuyService.preparePartnerDailyProduct(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI);
				if (yunProducts != null) {
					log.info("=========定时任务{系统钱包转账-云贷理财购买}云贷数据准备完成，共"+yunProducts.size()+"条=========");
					for (BsSubAc4BatchBuyVO buyVO : yunProducts){
						String orderNo = Util.generateSysOrderNo("YTS");
						try {
							BsBatchBuy updateBuy = new BsBatchBuy();
							updateBuy.setId(buyVO.getBatchBuyId());
							updateBuy.setPay19OrderNo(orderNo);
							bsBatchBuyMapper.updateByPrimaryKeySelective(updateBuy);
							SysActTransSendInfo yunTransInfo = new SysActTransSendInfo();
							yunTransInfo.setPropertySymbol(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI);
							yunTransInfo.setTransMoney(buyVO.getProductAmount());
							yunTransInfo.setTransNo(orderNo);
							sysProductBuyService.trans2Partner(yunTransInfo);
						} catch (Exception e){
							log.error("=========定时任务{系统钱包转账-云贷理财购买}订单号"+orderNo+"转账异常=========", e);
							//告警
							specialJnlService.warn4Fail(buyVO.getProductAmount(),
									"定时任务{系统钱包转账-云贷理财购买}订单号["+orderNo+"]理财购买发起异常："+ StringUtil.left(e.getMessage(), 20),
									orderNo,"系统钱包转账-云贷理财购买",true);
						}

					}
				}
				List<BsSubAc4BatchBuyVO> sevenProducts = sysProductBuyService.preparePartnerDailyProduct(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI);
				if (sevenProducts != null) {
					log.info("=========定时任务{系统钱包转账-云贷理财购买}七贷数据准备完成，共"+sevenProducts.size()+"条=========");
				}
				
			}
			//有记录，但不满足触发条件
			else{
				log.info("=========定时任务{系统钱包转账-云贷理财购买}有渠道互转审核记录，但状态为非确认，不触发系统钱包转账=========");
			}
		}
		//无渠道互转审核记录，不触发系统钱包转账
		else{
			log.info("=========定时任务{系统钱包转账-云贷理财购买}无渠道互转审核记录，不触发系统钱包转账=========");
		}
	}

}
