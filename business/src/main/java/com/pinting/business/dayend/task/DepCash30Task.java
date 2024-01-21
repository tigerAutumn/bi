package com.pinting.business.dayend.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.LnLoanRelationMapper;
import com.pinting.business.model.vo.DepCash30VO;
import com.pinting.business.service.manage.MStatisticsService;
import com.pinting.business.util.Constants;
/**
 * 云贷存管未来30天兑付查询
 * @project business
 * @title DepCash30Task.java
 * @author Dragon & cat
 * @date 2017-10-23
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Service
public class DepCash30Task {

	
	private Logger log = LoggerFactory.getLogger(DepCash30Task.class);
	@Autowired
	private MStatisticsService statisticsService;
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	private LnLoanRelationMapper lnLoanRelationMapper;
	/**
	 * 任务执行
	 */
	public void execute() {
		log.info("==================日终【未来30天兑付查询】开始====================");
		/*try {
			yunDepCash30();
		} catch (Exception e) {
			e.printStackTrace();
			log.info("==================日终【云贷存管未来30天兑付异常】异常====================");
		}		
		
		try {
			loan7DepCash30();
		} catch (Exception e) {
			e.printStackTrace();
			log.info("==================日终【七贷存管未来30天兑付异常】异常====================");
		}*/

		try {
			loanFreeDepCash30();
		} catch (Exception e) {
			e.printStackTrace();
			log.info("==================日终【自由站岗户存管未来30天兑付异常】异常====================");
		}
		
		log.info("==================日终【未来30天兑付查询】结束====================");
	}

	void loanFreeDepCash30(){
		log.info("==================日终【自由站岗户未来30天兑付查询】开始====================");
		List<DepCash30VO> list = lnLoanRelationMapper.loanFreeDepCash30();
		insertBsDepCash30(list,Constants.PRODUCT_PROPERTY_SYMBOL_FREE);
		log.info("==================日终【自由站岗户未来30天兑付查询】结束====================");
	}
	
	
	void loan7DepCash30(){
		log.info("==================日终【七贷未来30天兑付查询】开始====================");
		List<DepCash30VO> list = lnLoanRelationMapper.loan7DepCash30();
		if (!CollectionUtils.isEmpty(list)) {
			int i = 0;
			for (DepCash30VO depCash30VO : list) {
				Double balance = lnLoanRelationMapper.slectQuitInterestBalanceByDateLoan7(depCash30VO.getFinishDate());
				log.info("==================日终【七贷未来30天兑付查询】预计退出债权利息:"+balance+"====================");
				list.get(i).setQuitInterestBalance(balance == null ? 0:balance);
				list.get(i).setPartnerCode(Constants.PROPERTY_SYMBOL_7_DAI_SELF);
				i++;
			}
		}
		insertBsDepCash30(list,Constants.PROPERTY_SYMBOL_7_DAI_SELF);
		log.info("==================日终【七贷未来30天兑付查询】结束====================");
	}
	
	
	
	void zsdDepCash30(){
		log.info("==================日终【赞时贷未来30天兑付查询】开始====================");
		List<DepCash30VO> list = lnLoanRelationMapper.zsdDepCash30();
		if (!CollectionUtils.isEmpty(list)) {
			int i = 0;
			for (DepCash30VO depCash30VO : list) {
				Double balance = lnLoanRelationMapper.slectQuitInterestBalanceByDateZsd(depCash30VO.getFinishDate());
				log.info("==================日终【赞时贷未来30天兑付查询】预计退出债权利息:"+balance+"====================");
				list.get(i).setQuitInterestBalance(balance == null ? 0:balance);
				list.get(i).setPartnerCode(Constants.PROPERTY_SYMBOL_ZSD);
				i++;
			}
		}
		insertBsDepCash30(list,Constants.PROPERTY_SYMBOL_ZSD);
		log.info("==================日终【赞时贷未来30天兑付查询】结束====================");
	}
	
	
	void yunDepCash30(){
		log.info("==================日终【云贷存管未来30天兑付查询】开始====================");
		lnLoanRelationMapper.depVipDeptsAmount();
		List<DepCash30VO> list = lnLoanRelationMapper.depCash30();
		if (!CollectionUtils.isEmpty(list)) {
			int i = 0;
			for (DepCash30VO depCash30VO : list) {
				Double balance = lnLoanRelationMapper.slectQuitInterestBalanceByDate(depCash30VO.getFinishDate());
				log.info("==================日终【云贷存管未来30天兑付查询】预计退出债权利息:"+balance+"====================");
				list.get(i).setQuitInterestBalance(balance == null ? 0:balance);
				list.get(i).setPartnerCode(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF);
				i++;
			}
		}
		insertBsDepCash30(list,Constants.PROPERTY_SYMBOL_YUN_DAI_SELF);
		log.info("==================日终【云贷存管未来30天兑付查询】结束====================");
	}
	
	/**
	 * 1、删除数据，当天以后的数据删除掉
	 * 2、计算未来30内兑付统计，插入数据
	 * @param valueList
	 */
	public void insertBsDepCash30(final List<DepCash30VO> valueList,final String partnerCode){
		try {
			boolean isSucc = transactionTemplate
					.execute(new TransactionCallback<Boolean>() {
						@Override
						public Boolean doInTransaction(TransactionStatus ts) {
							try {
								
								//开始清空数据表,删除当天以及当天以后的数据
								boolean key = statisticsService.deleteBsDepCash30(partnerCode);
								if(!key){
									return false;
								}
								//插入新的数据
								statisticsService.insertDepCashScheduleList(valueList);
								
							} catch (Exception e) {
								ts.setRollbackOnly();
								log.error(
										"==================日终【存管未来30天兑付查询插入数据】更新数据库失败====================",
										e);
								return false;
							}
							return true;
						}
	
					});
			if (!isSucc) {
				throw new Exception("日终【存管未来30天兑付查询插入数据】失败");
			}
		} catch (Exception e) {
			// 由于跑批失败，此处需记录失败信息 到 特殊交易流水表
			log.error("==================日终【存管未来30天兑付查询插入数据】失败====================", e);
			
		}
		log.info("==================日终【存管未来30天兑付查询插入数据】结束====================");
	}
}
