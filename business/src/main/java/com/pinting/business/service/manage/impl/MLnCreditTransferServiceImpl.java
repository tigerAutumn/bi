package com.pinting.business.service.manage.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.LnCreditTransferMapper;
import com.pinting.business.model.vo.LnCreditTransfer2VIPVO;
import com.pinting.business.model.vo.LnCreditTransferMQueryVO;
import com.pinting.business.model.vo.LnCreditTransferMVO;
import com.pinting.business.service.manage.MLnCreditTransferService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;

/**
 * 管理台债权转让相关
 * @author bianyatian
 * @2016-12-2 下午2:56:15
 */
@Service
public class MLnCreditTransferServiceImpl implements MLnCreditTransferService {

	@Autowired
	private LnCreditTransferMapper lnCreditTransferMapper;
	@Override
	public List<LnCreditTransferMVO> getTransferList(
			LnCreditTransferMQueryVO queryVo) {
		
		return lnCreditTransferMapper.selectLnCreditTransferVO4Manage(queryVo);
	}
	@Override
	public int countTransferList(LnCreditTransferMQueryVO queryVo) {
		
		return lnCreditTransferMapper.countLnCreditTransferVO4Manage(queryVo);
	}
	@Override
	public Double transSumPrincipal(LnCreditTransferMQueryVO queryVo) {
		Double transSumPrincipal = lnCreditTransferMapper.transSumPrincipal(queryVo);
		return transSumPrincipal== null?0d:transSumPrincipal;
	}
	@Override
	public Double transSumInterest(LnCreditTransferMQueryVO queryVo) {
		Double transSumInterest = lnCreditTransferMapper.transSumInterest(queryVo);
		return transSumInterest== null?0d:transSumInterest;
	}
	@Override
	public LnCreditTransfer2VIPVO getTransfer2VIP(
			LnCreditTransferMQueryVO queryVo) {
		LnCreditTransfer2VIPVO vo = new LnCreditTransfer2VIPVO(); 
		vo.setCount(lnCreditTransferMapper.countLnCreditTransferVO2VIP(queryVo));
		vo.setList(lnCreditTransferMapper.selectLnCreditTransferVO2VIP(queryVo));
		Double amount = lnCreditTransferMapper.transSumAmount2VIP(queryVo);
		vo.setTransSumAmount(amount == null ? 0d :amount );
		return vo;
	}
	@Override
	public LnCreditTransfer2VIPVO getTransfer2VIP4Export(
			LnCreditTransferMQueryVO queryVo) {
		LnCreditTransfer2VIPVO vo = new LnCreditTransfer2VIPVO(); 
		Integer count = lnCreditTransferMapper.countLnCreditTransferVO2VIP(queryVo);
		vo.setCount(count);
		if(count>0){
			queryVo.setStart(1);
			queryVo.setNumPerPage(count);
			vo.setList(lnCreditTransferMapper.selectLnCreditTransferVO2VIP(queryVo));
		}
		return vo;
	}

	@Override
	public LnCreditTransfer2VIPVO getTransferPay(
			LnCreditTransferMQueryVO queryVo) {
		LnCreditTransfer2VIPVO vo = new LnCreditTransfer2VIPVO();
		Date endDate = DateUtil.parseDate("2017-11-08");
		if(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(queryVo.getPartnerCode())
				&& queryVo.getOverTime().compareTo(endDate) <0){
			//查询在2017-11-08之前的数据，并且是云贷产品的
			vo.setList(lnCreditTransferMapper.selectLnCreditTransferPayYun(queryVo));
			vo.setLnCreditTransferMVO(lnCreditTransferMapper.transSumAmountYun(queryVo));
		}else{
			
			vo.setList(lnCreditTransferMapper.selectLnCreditTransferPayYunZSD(queryVo));
			vo.setLnCreditTransferMVO(lnCreditTransferMapper.transSumAmountYunZSD(queryVo));
			
		}
		
		vo.setCount(lnCreditTransferMapper.countLnCreditTransferPayYunZSD(queryVo));
		
		return vo;
	}
	
	@Override
	public LnCreditTransfer2VIPVO getTransferPay4Export(
			LnCreditTransferMQueryVO queryVo) {
		LnCreditTransfer2VIPVO vo = new LnCreditTransfer2VIPVO(); 
		Integer count = lnCreditTransferMapper.countLnCreditTransferPayYunZSD(queryVo);
		vo.setCount(count);
		if(count>0){
			queryVo.setStart(1);
			queryVo.setNumPerPage(count);
			Date endDate = DateUtil.parseDate("2017-11-08");
			if(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(queryVo.getPartnerCode())
					&& queryVo.getOverTime().compareTo(endDate) <0){
				//查询在2017-11-08之前的数据，并且是云贷产品的
				vo.setList(lnCreditTransferMapper.selectLnCreditTransferPayYun(queryVo));
			}else{
				vo.setList(lnCreditTransferMapper.selectLnCreditTransferPayYunZSD(queryVo));
			}
			
		}
		return vo;
	}

}
