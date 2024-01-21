package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pinting.business.aspect.cache.ClearRedisCache;
import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.aspect.cache.RedisCache;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.pinting.business.hessian.manage.message.ReqMsg_Bs19PayBank_BankListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Bs19PayBank_Bs19PayBankPrimaryKey;
import com.pinting.business.hessian.manage.message.ReqMsg_Bs19PayBank_IsAvailableModify;
import com.pinting.business.hessian.manage.message.ReqMsg_Bs19PayBank_Save;
import com.pinting.business.hessian.manage.message.ResMsg_Bs19PayBank_BankListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Bs19PayBank_Bs19PayBankPrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_Bs19PayBank_IsAvailableModify;
import com.pinting.business.hessian.manage.message.ResMsg_Bs19PayBank_Save;
import com.pinting.business.hessian.manage.message.ResMsg_BsBank_BsBankModify;
import com.pinting.business.model.Bs19payBank;
import com.pinting.business.model.BsBank;
import com.pinting.business.model.vo.Bs19payBankVO;
import com.pinting.business.service.manage.Bs19PayBankService;
import com.pinting.business.service.manage.BsBankService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;

@Component("Bs19PayBank")
public class Bs19PayBankFacade {
	
	@Autowired
	private Bs19PayBankService bs19PayBankService;
	
	@Autowired
	private BsBankService bsBankService;
	
	public void bankListQuery(ReqMsg_Bs19PayBank_BankListQuery req,ResMsg_Bs19PayBank_BankListQuery res){
		Bs19payBankVO record = new Bs19payBankVO();
		record.setName(req.getName());
		record.setPay19BankCode(req.getPay19BankCode());
		record.setPayType(req.getPayType());
		record.setIsMain(req.getIsMain());
		record.setChannelPriority(req.getChannelPriority());
		record.setPageNum(Integer.parseInt(req.getPageNum()));
		record.setNumPerPage(Integer.parseInt(req.getNumPerPage()));
		record.setIsAvailable(req.getIsAvailable());
		record.setChannel(req.getChannel());
		int totalRows = bs19PayBankService.bs19payBankCount(record);
		if (totalRows > 0) {
			List<Bs19payBankVO> list = bs19PayBankService.bs19payBankPage(record);
			ArrayList<HashMap<String, Object>> mapList = BeanUtils.classToArrayList(list);
			res.setBs19PayBank(mapList);
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(String.valueOf(totalRows));
	}

	@ClearRedisCache(clearKey = {ConstantsForCache.CacheKey.BANK_PAY19BANKLIST})
	public void isAvailableModify(ReqMsg_Bs19PayBank_IsAvailableModify req,ResMsg_Bs19PayBank_IsAvailableModify res){
		Bs19payBank record = new Bs19payBank();
		record.setId(req.getId());
		record.setIsAvailable(req.getIsAvailable());
		bs19PayBankService.updateBs19payBankKey(record);
	}
	
	
	public void bs19PayBankPrimaryKey(ReqMsg_Bs19PayBank_Bs19PayBankPrimaryKey req,ResMsg_Bs19PayBank_Bs19PayBankPrimaryKey res){
		List<BsBank> bsBankList = bsBankService.groupByBankName();//分组查询银行名称
		ArrayList<HashMap<String, Object>> mapList = BeanUtils.classToArrayList(bsBankList);
		res.setBsBanks(mapList);
		if(null != req.getId() && 0 != req.getId()){
			Bs19payBank bs19payBank = bs19PayBankService.selectByPrimaryKey(req.getId());
			res.setId(bs19payBank.getId());
			res.setBankId(bs19payBank.getBankId());
			res.setPay19BankCode(bs19payBank.getPay19BankCode());
			res.setPayType(bs19payBank.getPayType());
			res.setOneTop(substring(bs19payBank.getOneTop() == null ? "" : bs19payBank.getOneTop().toString()));
			res.setDayTop(substring(bs19payBank.getDayTop() == null ? "" : bs19payBank.getDayTop().toString()));
			res.setMonthTop(substring(bs19payBank.getMonthTop() == null ? "" : bs19payBank.getMonthTop().toString()));
			res.setForbiddenStart(bs19payBank.getForbiddenStart());
			res.setForbiddenEnd(bs19payBank.getForbiddenEnd());
			res.setIsAvailable(bs19payBank.getIsAvailable());
			res.setNotice(bs19payBank.getNotice());
			res.setIsMain(bs19payBank.getIsMain());
			res.setChannelPriority(bs19payBank.getChannelPriority());
			res.setChannel(bs19payBank.getChannel());
			res.setDailyNotice(bs19payBank.getDailyNotice());
		}
	}
	
	private static String substring(String str){//去掉后面的.0
		if(StringUtil.isNotBlank(str)){
			String subs = str.substring(str.length()-2);
			if(subs.equals(".0")){
				str = str.substring(0,str.length()-2);
			}
		}
		return str;
	}
	
	/**
	 * 判断操作类型是增加还是修改
	 * 一、编辑
	 * 		判断是否更改支付通道信息，如果更改了支付通道，判断是否存在同一银行相同支付类型并且支付通道相同的数据，
	 * 		如果存在这样的数据，并且已经存在那么证明不可以进行修改，提示该银行存在这种渠道
	 * 		1、可以正常保存的数据（未修改支付通道或者修改了支付通道但是不存在相同数据）
	 *		 	可以正常保存的数据（未修改支付通道或者修改了支付通道但是不存在相同数据）
	 *		 	查询和需要保存保存的数据record相同银行支付类型相同的支付通道信息（可能包含自己原来的信息）bs19payBanks
	 *		 	1.1 bs19payBanks不为空    
	 *		 		1.1.1 需要保存保存的数据record是否是主通道数据，如果是主通道数据，则把数据库中的数据置为副通道
	 *		 		1.1.2 判断更改后的数据record的优先级是上升、下降、不变
	 *		 		下降（1-->3）：oldBank.getChannelPriority() <  bs19payBank <= record.getChannelPriority() 区间内的数据优先级-1
	 *		 		上升（3-->1）：oldBank.getChannelPriority() >  bs19payBank >= record.getChannelPriority() 区间内的数据优先级+1
	 *		 		不变：直接保存
	 *		 	
	 *		 	1.2 bs19payBanks为空，证明编辑保存的数据修改了支付通道信息，需要存的数据数据库中不存在，直接把优先级置为1保存数据    
	 * 		2、修改了支付通道的修改，修改支付通道，但是修改保存的数据数据库中已存在，提示该银行存在这种渠道
	 * 
	 * 二、增加
	 * 		1、判断是否存在同一银行相同支付类型并且支付通道相同的数据
	 * 		       已存在则证明该数据不可以新增，直接返回该银行存在这种渠道
	 * 		2、新增操作
	 * 			1.1、查询数据库已存在同一银行相同支付类型数据列表（需要在此基础上面新增一个）
	 * 				        如果查询到数据，证明是新增一个支付通道，则需要考虑
	 * 					1.1.1、如果查询到数据，证明是新增一个支付通道，则需要考虑
	 * 						a  判断新增数据是否被设置为主通道，如果是主通道则需要把其它支付通道设置为2(副通道)
	 * 						b  重新查询同一银行相同支付类型列表（其它支付通道数据可能在上一步被改变）
	 * 						c  设置每个支付通道的优先级
	 * 			  				如果新增的数据record的优先级大于同一银行相同支付类型其它支付通道（bs19payBankList）的数量，那么需要设置
	 * 							 record的优先级在原来支付通道数量基础上+1（bs19payBankList.size()+1）；
	 * 			 				如果新增的数据record的优先级小于或等于同一银行相同支付类型其它支付通道（bs19payBankList）的数量，
	 * 			 				需要把数据库其它支付通道优先级大于record的优先级的数据的优先级+1后再新增支付通道数据
	 *
	 * 					1.1.2、如果查询不到列表，证明需要新加的数据是第一条，优先级设置为1，直接新增
	 *
	 * 			1.2、如果查询不到列表，证明需要新加的数据是第一条，优先级设置为1，直接新增
	 * 
	 */
	@ClearRedisCache(clearKey = {ConstantsForCache.CacheKey.BANK_PAY19BANKLIST})
	public void save(ReqMsg_Bs19PayBank_Save req,ResMsg_Bs19PayBank_Save res){
		
	
		Bs19payBank bs19payBank = new Bs19payBank();
		bs19payBank.setBankId(req.getBankId());
		bs19payBank.setPayType(req.getPayType());
		bs19payBank.setChannel(req.getChannel());
		Bs19payBank result = bs19PayBankService.selectBs19payBank(bs19payBank);
		
		bs19payBank.setId(req.getId());
		bs19payBank.setPay19BankCode(req.getPay19BankCode());
		bs19payBank.setOneTop(req.getOneTop());
		bs19payBank.setDayTop(req.getDayTop());
		bs19payBank.setMonthTop(req.getMonthTop());
		bs19payBank.setForbiddenEnd(DateUtil.parseDateTime(req.getForbiddenEnd()));
		bs19payBank.setForbiddenStart(DateUtil.parseDateTime(req.getForbiddenStart()));
		bs19payBank.setNotice(req.getNotice().trim());
		bs19payBank.setChannelPriority(req.getChannelPriority());
		bs19payBank.setIsMain(req.getIsMain());
		bs19payBank.setDailyNotice(req.getDailyNotice());
		if(null != req.getId()&&0 != req.getId()){//编辑
			if(null != result && !result.getId().equals(req.getId())){
				//修改了支付通道，并且修改的内容已存在
//				res.setFlag(ResMsg_BsBank_BsBankModify.REPEAT_NAME);
				new PTMessageException(PTMessageEnum.BANK_CHANNEL_ERROR.getCode(), "该渠道已存在！");
			}else{//符合保存条件可以直接保存（未修改支付通道或者修改了支付通道但是不存在相同数据）
				bs19PayBankService.detailBs19payBankKey(bs19payBank);
				res.setFlag(ResMsg_BsBank_BsBankModify.INSERT_SUCCESS);
			}
		}else{//新增

			if(null == result){
				bs19payBank.setIsAvailable(1);
				bs19PayBankService.addBs19payBank(bs19payBank);
				res.setFlag(ResMsg_BsBank_BsBankModify.INSERT_SUCCESS);
			}else{
				res.setFlag(ResMsg_BsBank_BsBankModify.REPEAT_NAME);
				new PTMessageException(PTMessageEnum.BANK_CHANNEL_ERROR.getCode(), "该渠道已存在！");
//				return;
			}
		}
		
		/**
		 * 编辑判断发起通知
		 * 暂时不需要发起通知   通知的代码注释掉
		 */
		//bs19PayBankService.sendNotice();
	}
}




