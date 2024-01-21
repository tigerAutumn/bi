package com.pinting.business.service.site.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsBankCardMapper;
import com.pinting.business.dao.BsCardBinMapper;
import com.pinting.business.model.BsBankCard;
import com.pinting.business.model.BsBankCardExample;
import com.pinting.business.model.BsCardBin;
import com.pinting.business.model.BsCardBinExample;
import com.pinting.business.model.vo.BsBankCardVO;
import com.pinting.business.service.site.BankCardService;
import com.pinting.business.service.site.CardBinService;

@Service
public class CardBinServiceImpl implements CardBinService{

	@Autowired
	private BsCardBinMapper bsCardBinMapper;

	@Override
	public BsCardBin findCardBinByCardNo(String cardNo) {
		
		List<BsCardBin> carBinList=null;
		int n= 10;
		while(n>2)
		{
			BsCardBinExample cardBinExample = new BsCardBinExample();
			String cardBin = cardNo.substring(0, n);
			cardBinExample.createCriteria().andCardBinLike(cardBin+"%").andBankCardLenEqualTo(cardNo.length());
			carBinList = bsCardBinMapper.selectByExample(cardBinExample);
			if(carBinList != null && carBinList.size() == 1){
				return carBinList.get(0);
			}
			n--;
		}
		return null;
		
	}

	
	public static void main(String []args){
		String cardNo = "622019283";
		int cardNoLength = cardNo.length();
        String sqlCardNo = cardNo;
        for(int i=1;i<cardNoLength;i++){
        	sqlCardNo = sqlCardNo +","+ sqlCardNo.subSequence(0, i);
        }
        System.out.println(sqlCardNo);
	}

}
