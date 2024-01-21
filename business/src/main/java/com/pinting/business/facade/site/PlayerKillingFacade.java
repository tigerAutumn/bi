package com.pinting.business.facade.site;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.ReqMsg_PlayerKilling_ActivityStatus;
import com.pinting.business.hessian.site.message.ReqMsg_PlayerKilling_Index;
import com.pinting.business.hessian.site.message.ResMsg_PlayerKilling_ActivityStatus;
import com.pinting.business.hessian.site.message.ResMsg_PlayerKilling_Index;
import com.pinting.business.model.BsProduct;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.BsSubAccountExample;
import com.pinting.business.model.vo.PlayerKillingVO;
import com.pinting.business.service.site.ProductService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;
/**
 * 12月13日活动--投资PK瓜分两万现金
 * @author syl
 */
@Component("PlayerKilling")
public class PlayerKillingFacade {

	@Autowired
	private	ProductService	productService;
	@Autowired
	private	BsSubAccountMapper bsSubAccountMapper;
	
	
	/**
	 * 投资PK瓜分两万现金进入首页信息
	 * @param req
	 * @param res
	 */
	public void index(ReqMsg_PlayerKilling_Index req, ResMsg_PlayerKilling_Index res){
		res.setWinner("no");
		
		//查询知足派标的信息
		List<HashMap<String, Object>>  rankingContentment = new ArrayList<HashMap<String, Object>>();
		for (int i=1; i<=20; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("rowno", i);
			map.put("userName", "----");
			map.put("buyAmount", "---");
			rankingContentment.add(map);
		}
		List<BsProduct> productContentment = productService.queryProductByName("欢乐Pk计划安心投");
		if (!CollectionUtils.isEmpty(productContentment)) {
			res.setInvestAmountContentment(productContentment.get(0).getCurrTotalAmount());
			res.setProductIdContentment(productContentment.get(0).getId());
			List<PlayerKillingVO> playerKillingVOs = productService.queryUserInvestList(productContentment.get(0).getId());
			

			for (PlayerKillingVO playerKillingVO : playerKillingVOs) {
				rankingContentment.get(playerKillingVO.getRowno()-1).put("userName", playerKillingVO.getUserName());
				rankingContentment.get(playerKillingVO.getRowno()-1).put("buyAmount", MoneyUtil.format(playerKillingVO.getBuyAmount())+ "元");
			}
			res.setRankingContentment(rankingContentment);
		}else {
			res.setInvestAmountContentment(0.0);
			res.setProductIdContentment(1);
			res.setRankingContentment(rankingContentment);
		}
		
		//查询现实派标的信息
		List<HashMap<String, Object>>  rankingReal = new ArrayList<HashMap<String, Object>>();
		for (int i=1; i<=20; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("rowno", i);
			map.put("userName", "----");
			map.put("buyAmount", "---");
			rankingReal.add(map);
		}
		
		List<BsProduct> productReal = productService.queryProductByName("欢乐pk计划短期乐");
		if (!CollectionUtils.isEmpty(productReal)) {
			res.setInvestAmountReal(productReal.get(0).getCurrTotalAmount());
			res.setProductIdReal(productReal.get(0).getId());
			List<PlayerKillingVO> playerKillingVOs = productService.queryUserInvestList(productReal.get(0).getId());
			for (PlayerKillingVO playerKillingVO : playerKillingVOs) {
				rankingReal.get(playerKillingVO.getRowno()-1).put("userName", playerKillingVO.getUserName());
				rankingReal.get(playerKillingVO.getRowno()-1).put("buyAmount", MoneyUtil.format(playerKillingVO.getBuyAmount())+ "元");
			}
			
			res.setRankingReal(rankingReal);
		}else {
			res.setInvestAmountReal(0.0);
			res.setProductIdContentment(1);
			res.setRankingReal(rankingReal);
		}
		
		if (!CollectionUtils.isEmpty(productReal) && !CollectionUtils.isEmpty(productContentment)) {
			if ( productContentment.get(0).getCurrTotalAmount().compareTo(15000000.00) == 0 && productReal.get(0).getCurrTotalAmount().compareTo(15000000.00) < 0) {
	    		res.setWinner("contentment");
			}else if ( productContentment.get(0).getCurrTotalAmount().compareTo(15000000.00) < 0 && productReal.get(0).getCurrTotalAmount().compareTo(15000000.00) == 0) {
				res.setWinner("real");
			}else if (productContentment.get(0).getCurrTotalAmount().compareTo(15000000.00) == 0 && productReal.get(0).getCurrTotalAmount().compareTo(15000000.00) == 0) {
				BsSubAccountExample contentmentExample = new BsSubAccountExample();
				contentmentExample.createCriteria().andProductIdEqualTo(productContentment.get(0).getId());
				contentmentExample.setOrderByClause("open_time DESC");
				List<BsSubAccount> contentmentSubAccountList = bsSubAccountMapper.selectByExample(contentmentExample);
				
				
				BsSubAccountExample realExample = new BsSubAccountExample();
				realExample.createCriteria().andProductIdEqualTo(productReal.get(0).getId());
				realExample.setOrderByClause("open_time DESC");
				List<BsSubAccount> realSubAccountList = bsSubAccountMapper.selectByExample(realExample);
				
				
				if (contentmentSubAccountList.get(0).getOpenTime().compareTo(realSubAccountList.get(0).getOpenTime())>0) {
					res.setWinner("real");
				}else {
					res.setWinner("contentment");
				}
			}
		}
		
		
	}
	
	
	
	/**
	 * 投资PK瓜分两万现金查询标的状态
	 * @param req
	 * @param res
	 */
	public void activityStatus(ReqMsg_PlayerKilling_ActivityStatus req, ResMsg_PlayerKilling_ActivityStatus res){
		
		List<BsProduct> productContentment = productService.queryProductByName("欢乐Pk计划安心投");
		List<BsProduct> productReal = productService.queryProductByName("欢乐pk计划短期乐");
		if (CollectionUtils.isEmpty(productContentment)) {
			throw new PTMessageException(PTMessageEnum.PK_ACTIVITY_NOT_EXIST);
		}
		if (CollectionUtils.isEmpty(productReal)) {
			throw new PTMessageException(PTMessageEnum.PK_ACTIVITY_NOT_EXIST);
		}
		
		//活动未开始
		if (Constants.PRODUCT_STATUS_PUBLISH_NO.equals(productContentment.get(0).getStatus()) ||
				Constants.PRODUCT_STATUS_PUBLISH_YES.equals(productContentment.get(0).getStatus())	) {
			res.setStatusContentment("NOT_START");
		}
		if (Constants.PRODUCT_STATUS_PUBLISH_NO.equals(productReal.get(0).getStatus()) ||
				Constants.PRODUCT_STATUS_PUBLISH_YES.equals(productReal.get(0).getStatus())	) {
			res.setStatusReal("NOT_START");
		}
		
		//活动进行中
		
		if (Constants.PRODUCT_STATUS_OPENING.equals(productContentment.get(0).getStatus()) ) {
			res.setStatusContentment("OPEN");
		}
		if (Constants.PRODUCT_STATUS_OPENING.equals(productReal.get(0).getStatus())) {
			res.setStatusReal("OPEN");
		}

		//其中一方胜利
		if ( productContentment.get(0).getCurrTotalAmount().compareTo(15000000.00) == 0 && productReal.get(0).getCurrTotalAmount().compareTo(15000000.00) < 0) {
			res.setStatusContentment("WIN");
		}else if ( productContentment.get(0).getCurrTotalAmount().compareTo(15000000.00) < 0 && productReal.get(0).getCurrTotalAmount().compareTo(15000000.00) == 0) {
			res.setStatusReal("WIN");
		}else if (productContentment.get(0).getCurrTotalAmount().compareTo(15000000.00) == 0 && productReal.get(0).getCurrTotalAmount().compareTo(15000000.00) == 0) {
			res.setStatusContentment("FINISH");
			res.setStatusReal("FINISH");
		}
	
	
		
		//活动已结束
		if (Constants.PRODUCT_STATUS_FINISH.equals(productContentment.get(0).getStatus())
				&& Constants.PRODUCT_STATUS_FINISH.equals(productReal.get(0).getStatus())) {
			res.setStatusContentment("FINISH");
		}
		if (Constants.PRODUCT_STATUS_FINISH.equals(productReal.get(0).getStatus())
				&& Constants.PRODUCT_STATUS_FINISH.equals(productContentment.get(0).getStatus())) {
			res.setStatusReal("FINISH");
		}
		
	}
	
}
