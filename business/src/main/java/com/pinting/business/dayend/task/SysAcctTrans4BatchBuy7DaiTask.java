package com.pinting.business.dayend.task;

import com.pinting.business.accounting.finance.model.SysActTransSendInfo;
import com.pinting.business.accounting.finance.service.SysProductBuyService;
import com.pinting.business.dao.BsBatchBuyMapper;
import com.pinting.business.model.BsBatchBuy;
import com.pinting.business.model.BsBatchBuyExample;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;


/**
 * 统计今天所有购买的产品(七贷)
 * 将购买金额(总金额)转账给达飞账户
 * @Project: business
 * @Title: SysAcctTrans4BatchBuyTask.java
 * @author dingpf
 * @date 2015-11-19 下午3:06:28
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Deprecated
public class SysAcctTrans4BatchBuy7DaiTask {
	private Logger log = LoggerFactory.getLogger(SysAcctTrans4BatchBuy7DaiTask.class);
	
	@Autowired
	private SysProductBuyService sysProductBuyService;
	@Autowired
	private SpecialJnlService specialJnlService;
	@Autowired
	private BsBatchBuyMapper batchBuyMapper;

	/**
	 * 七贷理财购买任务执行
	 */
	public void execute() {
		// 定时任务{系统转账理财总金额给达飞}
		log.info("=========定时任务{系统钱包转账-七贷理财购买}开始=========");
		//查询渠道互转审核状态，判断是否允许触发系统钱包转账
		Date currDay = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));

		//查询bs_batch_buy中七贷的数据
		BsBatchBuyExample batchBuyExample = new BsBatchBuyExample();
		batchBuyExample.createCriteria().andPropertySymbolEqualTo(Constants.PROPERTY_SYMBOL_7_DAI).andFinancingDateEqualTo(DateUtil.addDays(currDay, -1)).andStatusEqualTo(Constants.BATCHBUY_STATUS_INIT);
		List<BsBatchBuy> bsBatchBuyList = batchBuyMapper.selectByExample(batchBuyExample);

		if (!CollectionUtils.isEmpty(bsBatchBuyList)) {
			for (BsBatchBuy buy : bsBatchBuyList){
				String orderNo = Util.generateSysOrderNo("7TS");
				try {
					BsBatchBuy updateBuy = new BsBatchBuy();
					updateBuy.setId(buy.getId());
					updateBuy.setPay19OrderNo(orderNo);
					batchBuyMapper.updateByPrimaryKeySelective(updateBuy);
					SysActTransSendInfo sevenTransInfo = new SysActTransSendInfo();
					sevenTransInfo.setTransNo(orderNo);
					sevenTransInfo.setTransMoney(buy.getAmount());
					sevenTransInfo.setPropertySymbol(buy.getPropertySymbol());
					sysProductBuyService.trans2Partner(sevenTransInfo);
				} catch (Exception e){
					log.info("=========定时任务{系统钱包转账-七贷理财购买}执行订单号"+orderNo+"转账异常=========", e);
					//告警
					specialJnlService.warn4Fail(buy.getAmount(),
							"定时任务{系统钱包转账-七贷理财购买}批次号["+orderNo+"]理财购买发起异常："+ StringUtil.left(e.getMessage(), 20),
							orderNo,"系统钱包转账-七贷理财购买",true);
				}
			}
		}else {
			log.info("=========定时任务{系统钱包转账-七贷理财购买}无记录，不触发系统钱包转账=========");
		}

		
	}

}
