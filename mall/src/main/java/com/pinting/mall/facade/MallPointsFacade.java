package com.pinting.mall.facade;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.mall.hessian.site.message.ReqMsg_MallPoints_PointsRecord;
import com.pinting.mall.hessian.site.message.ReqMsg_MallPoints_UserPoints;
import com.pinting.mall.hessian.site.message.ResMsg_MallPoints_PointsRecord;
import com.pinting.mall.hessian.site.message.ResMsg_MallPoints_UserPoints;
import com.pinting.mall.model.MallAccount;
import com.pinting.mall.model.MallAccountJnl;
import com.pinting.mall.service.site.MallAccountService;
import com.pinting.mall.util.BeanUtils;

@Component("MallPoints")
public class MallPointsFacade {
	
	@Autowired
	private MallAccountService mallAccountService;
	
	
	/**
	 * 查询用户积分记录
	 * @author bianyatian
	 * @param req
	 * @param res
	 */
	public void pointsRecord(ReqMsg_MallPoints_PointsRecord req,ResMsg_MallPoints_PointsRecord res){
		Integer userId = req.getUserId();
		if(userId != null){
			int count = mallAccountService.countPointsRecordList(req);
			if(count >0){
				//查询用户积分记录流水
				List<MallAccountJnl> list = mallAccountService.getPointsRecordList(req);
				if(CollectionUtils.isNotEmpty(list)){
					res.setPointsRecordList(BeanUtils.classToArrayList(list));
				}
				res.setCount(count);
			}else{
				res.setCount(0);
			}
		}
	}
	
	public void userPoints(ReqMsg_MallPoints_UserPoints req, ResMsg_MallPoints_UserPoints res){
		Integer userId = req.getUserId();
		if(userId != null){
			//查询用户积分账户信息
			MallAccount account = mallAccountService.getAccountByUserId(userId);
			if(account != null){
				res.setPoints(account.getBalance());
			}
		}
	}

}
