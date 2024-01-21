package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.pinting.business.hessian.manage.message.ReqMsg_BsDailyBonus_ListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_BsDailyBonus_ServiceDetailList;
import com.pinting.business.hessian.manage.message.ResMsg_BsDailyBonus_ListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_BsDailyBonus_ServiceDetailList;
import com.pinting.business.model.BsDailyBonus;
import com.pinting.business.model.vo.BsDailyBonusVO;
import com.pinting.business.service.manage.BsDailyBonusService;
import com.pinting.business.util.BeanUtils;

@Component("BsDailyBonus")
public class BsDailyBonusFacade {
	
	@Autowired
	private BsDailyBonusService bsDailyBonusService;
	/**
	 * 奖励金明细查询,和客服菜单共用
	 * @param req
	 * @param res
	 */
	public void listQuery(ReqMsg_BsDailyBonus_ListQuery req,ResMsg_BsDailyBonus_ListQuery res){
		BsDailyBonusVO bsDailyBonusVO = new BsDailyBonusVO();
		bsDailyBonusVO.setByName(req.getByName());
		bsDailyBonusVO.setByMobile(req.getByMobile());
		
		bsDailyBonusVO.setRecommendName(req.getRecommendName());
		bsDailyBonusVO.setRecommendMobile(req.getRecommendMobile());
		
		bsDailyBonusVO.setPageNum(Integer.parseInt(req.getPageNum()));
		bsDailyBonusVO.setNumPerPage(Integer.parseInt(req.getNumPerPage()));
		int totalRows = bsDailyBonusService.bsDailyBonusCount(bsDailyBonusVO);
		
		if (totalRows > 0) {
			List<BsDailyBonusVO> list = bsDailyBonusService.bsDailyBonusPage(bsDailyBonusVO);
			ArrayList<HashMap<String, Object>> mapList = BeanUtils.classToArrayList(list);
			res.setBsDailyBonuss(mapList);
		}
		Double bonuss = bsDailyBonusService.bonusSum(bsDailyBonusVO);
		res.setAllBonus(bonuss);
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(String.valueOf(totalRows));
	}

	/**
	 * 客服菜单中点击详情查看
	 * @param req
	 * @param res
	 */
	public void serviceDetailList(ReqMsg_BsDailyBonus_ServiceDetailList req,ResMsg_BsDailyBonus_ServiceDetailList res){
		BsDailyBonusVO bsDailyBonus = new BsDailyBonusVO();
		bsDailyBonus.setSubAccountId(req.getSubAccountId());
		List<BsDailyBonusVO> list = bsDailyBonusService.bsDailyBonus4ServiceDetail(bsDailyBonus);
		ArrayList<HashMap<String, Object>> mapList = BeanUtils.classToArrayList(list);
		res.setBsDailyBonuss(mapList);
		Double bonuss = bsDailyBonusService.bonusSum4ServiceDetail(bsDailyBonus);
		res.setAllBonus(bonuss);
	}
}
