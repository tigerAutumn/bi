package com.pinting.business.facade.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_BsActivityLuckyDraw_GetList;
import com.pinting.business.hessian.manage.message.ResMsg_BsActivityLuckyDraw_GetList;
import com.pinting.business.service.manage.BsActivityLuckyDrawService;

@Component("BsActivityLuckyDraw")
public class BsActivityLuckyDrawFacade {

	 @Autowired
	 private BsActivityLuckyDrawService bsActivityLuckyDrawService;
	 
	 public void getList(ReqMsg_BsActivityLuckyDraw_GetList req,
				ResMsg_BsActivityLuckyDraw_GetList res){
		 bsActivityLuckyDrawService.selectList(req, res);
	 }
}
