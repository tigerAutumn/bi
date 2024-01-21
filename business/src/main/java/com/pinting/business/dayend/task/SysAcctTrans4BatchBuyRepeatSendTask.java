package com.pinting.business.dayend.task;

import com.pinting.business.accounting.finance.model.SysActTransSendInfo;
import com.pinting.business.accounting.finance.service.SysProductBuyService;
import com.pinting.business.dao.BsBatchBuyMapper;
import com.pinting.business.model.BsBatchBuy;
import com.pinting.business.model.BsBatchBuyExample;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 扫描系统产品表19付打款失败的产品记录，重新发起19付打款
 * @Project: business
 * @Title: SysAcctTrans4BatchBuyRepeatSendTask.java
 * @author dingpf
 * @date 2015-11-23 下午1:21:42
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Deprecated
public class SysAcctTrans4BatchBuyRepeatSendTask {
	private Logger log = LoggerFactory.getLogger(SysAcctTrans4BatchBuyRepeatSendTask.class);
	@Autowired
	private SysProductBuyService sysProductBuyService;
	@Autowired
	private SpecialJnlService specialJnlService;
	@Autowired
	private BsBatchBuyMapper bsBatchBuyMapper;

	/**
	 * 任务执行
	 */
	public void execute() {
		// 定时任务{系统钱包转账重发-理财购买}
		log.info("=========定时任务{系统钱包转账重发-理财购买}开始=========");
		try {
			//查询需重发的数据
			List<BsBatchBuy> batchBuys = bsBatchBuyMapper.selectBsBatchBuy4Repeat19Send();
			if(batchBuys != null && batchBuys.size()>0){
				for (BsBatchBuy bsBatchBuy : batchBuys) {
					try {
						//重置订单号，生成规则："原始订单号-XX"，其中XX为重发次数
						String newPayOrderNo = "";
						if(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI.equals(bsBatchBuy.getPropertySymbol())) {
							newPayOrderNo= Util.generateSysOrderNo("YTS");//转账云贷订单号
						}else if(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI.equals(bsBatchBuy.getPropertySymbol())){
							newPayOrderNo= Util.generateSysOrderNo("7TS");//转账7贷订单号
						}
		    			Double amount = bsBatchBuy.getAmount();
		    			//更新订单号
		    			BsBatchBuyExample example = new BsBatchBuyExample();
		    			example.createCriteria().andPay19OrderNoEqualTo(bsBatchBuy.getPay19OrderNo())
		    				.andStatusEqualTo(Constants.BATCHBUY_STATUS_19_FAIL);
		    			BsBatchBuy tempBuy = new BsBatchBuy();
		    			tempBuy.setPay19OrderNo(newPayOrderNo);
		    			bsBatchBuyMapper.updateByExampleSelective(tempBuy, example);
		    			//发起
		    			SysActTransSendInfo req = new SysActTransSendInfo();
		    			req.setTransNo(newPayOrderNo);
		    			req.setTransMoney(amount);
						req.setPropertySymbol(bsBatchBuy.getPropertySymbol());

						sysProductBuyService.trans2Partner(req);
		    			
					} catch (Exception e) {
						log.error("=========定时任务{系统钱包转账重发-理财购买}失败=========", e);
						//告警
						specialJnlService.warn4Fail(bsBatchBuy.getAmount(), 
								"定时任务{系统钱包转账重发-理财购买}19订单号["+bsBatchBuy.getPay19OrderNo()+"]理财购买发起异常："+StringUtil.left(e.getMessage(), 20),
								bsBatchBuy.getPay19OrderNo(),"系统钱包转账重发-理财购买",true);
					}
				}
			}else{
				log.info("=========定时任务{系统钱包转账重发-理财购买}无需重发=========");
			}
			
			log.info("=========定时任务{系统钱包转账重发-理财购买}结束=========");
		} catch (Exception e) {
			log.error("=========定时任务{系统钱包转账重发-理财购买}失败=========", e);
			//告警
			specialJnlService.warn4Fail(null, "定时任务{系统钱包转账重发-理财购买}理财购买发起异常："+StringUtil.left(e.getMessage(), 20),
					null,"系统钱包转账重发-理财购买",true);
		}

	}

}
