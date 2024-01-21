package com.pinting.business.service.common.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.dao.Bs19payBankMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.vo.Pay19BankVO;
import com.pinting.business.service.common.BankService;

public class BankServiceImpl implements BankService {
	
	@Autowired
	private Bs19payBankMapper bs19payBankMapper;

	@Override
	public String checkTranAvailable(String bankChannel, String bankCode,
			Integer transChannel, Double amount) {
		String str = null;
		if(bankChannel == null || bankCode == null || transChannel == null || amount == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		List<Pay19BankVO> list = bs19payBankMapper.selectByChanelPayTypeBankCode(bankChannel, transChannel, bankCode);
		if(list == null || list.size() == 0){
			str = "当前时间暂不可用";
			return str;
		}else{
			Pay19BankVO vo = list.get(0);
			if(vo.getIsAvailable() != 1){
				str = "不可用";
				return str;
			}
			if(vo.getOneTop() < amount){
				str = "超过限额";
				return str;
			}
		}
		
		return str;
	}

}
