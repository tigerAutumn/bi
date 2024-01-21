package com.pinting.gateway.in.facade.mobile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_Home_InfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_Home_InfoQuery;
import com.pinting.business.model.BsProduct;
import com.pinting.business.service.site.ProductService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.BeanUtils;
import com.pinting.gateway.in.util.InterfaceVersion;

@Component("appHome")
public class HomeFacade{
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@InterfaceVersion("InfoQuery/1.0.0")
	public void infoQuery(ReqMsg_Home_InfoQuery req, ResMsg_Home_InfoQuery res){
		BsProduct productForCount = productService.sumCurrTotalAmountAndInvestNum();
		res.setCurrTotalAmount(productForCount.getCurrTotalAmount());
//		res.setInvestNum(productForCount.getInvestNum());
//		res.setTotalIncome(userService.countUserIncome());
	}
	
}
