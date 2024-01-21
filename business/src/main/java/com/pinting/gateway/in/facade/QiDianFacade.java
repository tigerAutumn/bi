package com.pinting.gateway.in.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.pinting.business.dao.BsDeptSalesMapper;
import com.pinting.business.dao.BsSalesMapper;
import com.pinting.business.model.BsDeptSales;
import com.pinting.business.model.BsSales;
import com.pinting.business.model.BsSalesExample;
import com.pinting.business.service.manage.BsSalesService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.gateway.hessian.message.qidian.G2BReqMsg_QiDian_FranchiseeRegist;
import com.pinting.gateway.hessian.message.qidian.G2BResMsg_QiDian_FranchiseeRegist;
import com.pinting.gateway.hessian.message.qidian.model.FranchiseeResult;
import com.pinting.gateway.hessian.message.qidian.model.Franchisees;

@Component("QiDian")
public class QiDianFacade {
	
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	private Logger log = LoggerFactory.getLogger(QiDianFacade.class);

	
	@Autowired
	private BsSalesService bsSalesService;
	@Autowired
	private BsDeptSalesMapper bsDeptSalesMapper;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private BsSalesMapper bsSalesMapper;
	
	public void franchiseeRegist(
			G2BReqMsg_QiDian_FranchiseeRegist req,
			G2BResMsg_QiDian_FranchiseeRegist res){
		log.info("====================>Business平台已收到店主注册请求：" + JSON.toJSONString(req));
		
		List<FranchiseeResult> 	franchiseeResult = new ArrayList<>();
		
		//1、添加bs_sales、bs_dept_sales 
		List<Franchisees>   franchisees = req.getFranchisees();
		for (final Franchisees franchisee : franchisees) {
			BsSales bsSalesResult = new BsSales();
			
			//需要判断数据库中是否已经存在此店主ID
			BsSalesExample bsSalesExample = new BsSalesExample();
			bsSalesExample.createCriteria().andInviteCodeEqualTo("qd"+franchisee.getFranchiseeId());
			List<BsSales> bsSales = bsSalesMapper.selectByExample(bsSalesExample);
			if (!CollectionUtils.isEmpty(bsSales)) {
				log.info("====================>七店店主注册,店主id已经存在于数据库，此条数据不处理：" + franchisee.getFranchiseeId());
				bsSalesResult = bsSales.get(0);
			}else {
				bsSalesResult = transactionTemplate.execute(new TransactionCallback<BsSales>(){
	                @Override
	                public BsSales doInTransaction(TransactionStatus status) {
	                	
	        			BsSales bsSales = new BsSales();
	        			bsSales.setSalesName(franchisee.getFranchiseeName());
	        			bsSales.setMobile(franchisee.getFranchiseeMobile());
	        			bsSales.setInviteCode("qd"+franchisee.getFranchiseeId());
	        			bsSales.setEntryTime(null);
	        			bsSales.setStatus(1);
	        			bsSales.setNote(franchisee.getFranchiseeId());
	        			bsSales.setCreateTime(new Date());
	        			bsSales.setUpdateTime(new Date());
	        			bsSalesService.addBsSales(bsSales);
	        			
	        			BsDeptSales bsDeptSales = new BsDeptSales();
	        			bsDeptSales.setSalesId(bsSales.getId());
	        			bsDeptSales.setDeptId(3);
	        			bsDeptSales.setCreateTime(new Date());
	        			bsDeptSales.setUpdateTime(new Date());
	        			bsDeptSalesMapper.insertSelective(bsDeptSales);
	        			
	        			return bsSales;
	                }
	            });
			}
			
			//给返回对象赋值
			if (bsSalesResult != null && bsSalesResult.getId() != null) {
				FranchiseeResult result = new FranchiseeResult();
				result.setFranchiseeId(franchisee.getFranchiseeId());
				result.setBgwFranchiseeId(bsSalesResult.getInviteCode());
				String link = GlobEnvUtil.get("wechat.server.web")+"/micro2.0/user/register_first_index?recommendId="+bsSalesResult.getInviteCode();
				result.setInviteLink(link);
				result.setUpdateTime(bsSalesResult.getUpdateTime());
				franchiseeResult.add(result);
			}else {
				log.info("店主注册失败，失败店主id"+franchisee.getFranchiseeId());
			}

		}
		res.setFranchiseeResult(franchiseeResult);
		
		log.info(">>>>>>>>>>>>>>>>>七店店主注册执行完成");
	}
	
	
}
