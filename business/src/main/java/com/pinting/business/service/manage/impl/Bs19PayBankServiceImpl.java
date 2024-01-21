package com.pinting.business.service.manage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.Bs19payBankMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.Bs19payBank;
import com.pinting.business.model.Bs19payBankExample;
import com.pinting.business.model.vo.Bs19payBankVO;
import com.pinting.business.service.manage.Bs19PayBankService;
import com.pinting.business.service.site.BankCardService;
import com.pinting.gateway.hessian.message.loan.B2GReqMsg_BankLimit_LimitList;
import com.pinting.gateway.hessian.message.loan.model.BankLimit;
import com.pinting.gateway.out.service.loan.NoticeService;

@Service
public class Bs19PayBankServiceImpl implements Bs19PayBankService{
	
	@Autowired
	public Bs19payBankMapper bs19payBankMapper;
	@Autowired
	public BankCardService bankCardService;
	@Autowired
	public NoticeService noticeService;

	@Override
	public List<Bs19payBankVO> bs19payBankPage(Bs19payBankVO record) {
		return bs19payBankMapper.bs19payBankPage(record);
	}

	@Override
	public int bs19payBankCount(Bs19payBankVO record) {
		return bs19payBankMapper.bs19payBankCount(record);
	}

	@Override
	public int updateBs19payBankKey(Bs19payBank record) {
		return bs19payBankMapper.updateByPrimaryKeySelective2(record);
	}

	@Override
	public int addBs19payBank(Bs19payBank record) {
		
		//1、查询数据库已存在同一银行相同支付类型数据列表（需要在此基础上面新增一个）
		Bs19payBankExample bs19payBankExample = new Bs19payBankExample();
		bs19payBankExample.createCriteria().andBankIdEqualTo(record.getBankId()).andPayTypeEqualTo(record.getPayType());
		List<Bs19payBank> bs19payBanks = bs19payBankMapper.selectByExample(bs19payBankExample);
		if (!CollectionUtils.isEmpty(bs19payBanks)) {
			//1.1、如果查询到数据，证明是新增一个支付通道，则需要考虑
			//1.1.1 判断新增数据是否被设置为主通道，如果是主通道则需要把其它支付通道设置为2(副通道)
			if (record.getIsMain() == 1) {
				Bs19payBank bs19payBank = new Bs19payBank();
				bs19payBank.setIsMain(2);
				bs19payBankMapper.updateByExampleSelective(bs19payBank, bs19payBankExample);
			}
			//1.1.2 重新查询同一银行相同支付类型列表（其它支付通道数据可能在上一步被改变）
			List<Bs19payBank> bs19payBankList = bs19payBankMapper.selectByExample(bs19payBankExample);
			//1.1.3 设置每个支付通道的优先级
			if (record.getChannelPriority() <= bs19payBankList.size()) {
				for (Bs19payBank bs19payBank : bs19payBankList) {
					if (bs19payBank.getChannelPriority() >= record.getChannelPriority()) {
						bs19payBank.setChannelPriority(bs19payBank.getChannelPriority()+1);
						bs19payBankMapper.updateByPrimaryKeySelective(bs19payBank);
					}
				}
				return bs19payBankMapper.insert(record);
			}else {
				record.setChannelPriority(bs19payBankList.size()+1);
				return bs19payBankMapper.insert(record);
			}
			
		}else {
			//1.2、如果查询不到列表，证明需要新加的数据是第一条，优先级设置为1，直接新增
			record.setChannelPriority(1);
			return bs19payBankMapper.insert(record);
		}
	}

	@Override
	public Bs19payBank selectByPrimaryKey(Integer id) {
		return bs19payBankMapper.selectByPrimaryKey(id);
	}

	@Override
	public Bs19payBank selectBs19payBank(Bs19payBank record) {
		return bs19payBankMapper.selectBs19payBank(record);
	}

	@Override
	public List<Bs19payBank> bs19payBankList(Bs19payBank record) {
		Bs19payBankExample example = new Bs19payBankExample();
		example.createCriteria().andBankIdEqualTo(record.getBankId());
		example.createCriteria().andPayTypeEqualTo(record.getPayType());
		return bs19payBankMapper.selectByExample(example);
	}

	@Override
	public int updateBs19payBank(Bs19payBank record) {
		Bs19payBankExample example = new Bs19payBankExample();
		example.createCriteria().andBankIdEqualTo(record.getBankId());
		example.createCriteria().andPayTypeEqualTo(record.getPayType());
		Bs19payBank bs19payBank = new Bs19payBank();
		bs19payBank.setChannelPriority(record.getChannelPriority());
		return bs19payBankMapper.updateByExampleSelective(bs19payBank, example);
	}
	
	
	@Override
	public int detailBs19payBankKey(Bs19payBank record) {

		Bs19payBankExample bs19payBankExample = new Bs19payBankExample();
		bs19payBankExample.createCriteria().andBankIdEqualTo(record.getBankId()).andPayTypeEqualTo(record.getPayType());
		List<Bs19payBank> bs19payBanks = bs19payBankMapper.selectByExample(bs19payBankExample);
		
		Bs19payBank oldBank = bs19payBankMapper.selectByPrimaryKey(record.getId());
		
		//判断是否更改了银行和支付类型
		if(!record.getBankId().equals(oldBank.getBankId()) || !record.getPayType().equals(oldBank.getPayType())){
			Bs19payBankExample oldBankExample = new Bs19payBankExample();
			oldBankExample.createCriteria().andBankIdEqualTo(oldBank.getBankId()).andPayTypeEqualTo(oldBank.getPayType());
			List<Bs19payBank> oldBankList = bs19payBankMapper.selectByExample(oldBankExample);
			if (!CollectionUtils.isEmpty(oldBankList)&& oldBankList.size()>1) {
				for (Bs19payBank bs19payBank : oldBankList) {
					if (bs19payBank.getChannelPriority()> oldBank.getChannelPriority()) {
						bs19payBank.setChannelPriority(bs19payBank.getChannelPriority()-1);
						bs19payBankMapper.updateByPrimaryKey(bs19payBank);
					}
				}
			}
			
			
			if (!CollectionUtils.isEmpty(bs19payBanks)) {
				//1.1、如果查询到数据，证明是新增一个支付通道，则需要考虑
				//1.1.1 判断新增数据是否被设置为主通道，如果是主通道则需要把其它支付通道设置为2(副通道)
				if (record.getIsMain() == 1) {
					Bs19payBank bs19payBank = new Bs19payBank();
					bs19payBank.setIsMain(2);
					bs19payBankMapper.updateByExampleSelective(bs19payBank, bs19payBankExample);
				}
				//1.1.2 重新查询同一银行相同支付类型列表（其它支付通道数据可能在上一步被改变）
				List<Bs19payBank> bs19payBankList = bs19payBankMapper.selectByExample(bs19payBankExample);
				//1.1.3 设置每个支付通道的优先级
				if (record.getChannelPriority() <= bs19payBankList.size()) {
					for (Bs19payBank bs19payBank : bs19payBankList) {
						if (bs19payBank.getChannelPriority() >= record.getChannelPriority()) {
							bs19payBank.setChannelPriority(bs19payBank.getChannelPriority()+1);
							bs19payBankMapper.updateByPrimaryKeySelective(bs19payBank);
						}
					}
					return bs19payBankMapper.updateByPrimaryKeySelective(record);
				}else {
					record.setChannelPriority(bs19payBankList.size()+1);
					return bs19payBankMapper.updateByPrimaryKeySelective(record);
				}
				
			}else {
				//1.2、如果查询不到列表，证明需要新加的数据是第一条，优先级设置为1，直接新增
				record.setChannelPriority(1);
				return bs19payBankMapper.updateByPrimaryKeySelective(record);
			}

		}else {
			
			if (!CollectionUtils.isEmpty(bs19payBanks)) {
				if (record.getIsMain() == 1) {
					Bs19payBank bs19payBank = new Bs19payBank();
					bs19payBank.setIsMain(2);
					bs19payBankMapper.updateByExampleSelective(bs19payBank, bs19payBankExample);
				}
				
				List<Bs19payBank> bs19payBankList = bs19payBankMapper.selectByExample(bs19payBankExample);

				
				if (record.getChannelPriority() > oldBank.getChannelPriority()) {
					for (Bs19payBank bs19payBank : bs19payBankList) {
						if (bs19payBank.getChannelPriority()> oldBank.getChannelPriority() && bs19payBank.getChannelPriority() <= record.getChannelPriority() ) {
							bs19payBank.setChannelPriority(bs19payBank.getChannelPriority()-1);
							bs19payBankMapper.updateByPrimaryKey(bs19payBank);
						}
					}
					
					if (record.getChannelPriority() > bs19payBankList.size()) {
						record.setChannelPriority(bs19payBankList.size());
						return bs19payBankMapper.updateByPrimaryKeySelective(record);
					}else {
						return bs19payBankMapper.updateByPrimaryKeySelective(record);
					}

				}else if (record.getChannelPriority() < oldBank.getChannelPriority()) {
					for (Bs19payBank bs19payBank : bs19payBankList) {
						if (bs19payBank.getChannelPriority()>= record.getChannelPriority() && bs19payBank.getChannelPriority()< oldBank.getChannelPriority()) {
							bs19payBank.setChannelPriority(bs19payBank.getChannelPriority()+1);
							bs19payBankMapper.updateByPrimaryKey(bs19payBank);
						}
						
					}
					return bs19payBankMapper.updateByPrimaryKeySelective(record);
				}else if (record.getChannelPriority() == oldBank.getChannelPriority()) {
					return bs19payBankMapper.updateByPrimaryKeySelective(record);
				}else {
					new PTMessageException(PTMessageEnum.BANK_CHANNEL_ERROR);
				}
				return 0;
			
			}else {
				record.setChannelPriority(1);
				return bs19payBankMapper.updateByPrimaryKeySelective(record);
			}
			
		}
	}
	
	
	
	@Override
	public void sendNotice() {
		List<BankLimit> bankList = bankCardService.selectBankLimitList();
		B2GReqMsg_BankLimit_LimitList req = new B2GReqMsg_BankLimit_LimitList();
		req.setBankLimits(bankList);
		noticeService.noticeBankLimit(req);
	}

}
