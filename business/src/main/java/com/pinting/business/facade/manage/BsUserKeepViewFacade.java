package com.pinting.business.facade.manage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_BsUserKeepView_BsUserRetentionListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_BsUserKeepView_BsUserRetentionListQuery;
import com.pinting.business.model.vo.BsUserKeepViewVO;
import com.pinting.business.service.manage.BsUserKeepViewService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.StringUtil;

@Component("BsUserKeepView")
public class BsUserKeepViewFacade {
	
	@Autowired
	private BsUserKeepViewService bsUserKeepViewService;
	
	private Logger log = LoggerFactory.getLogger(BsUserKeepViewFacade.class);
	
	/**
	 * 用户留存率查询
	 * @param req
	 * @param res
	 */
	public void bsUserRetentionListQuery(ReqMsg_BsUserKeepView_BsUserRetentionListQuery req, ResMsg_BsUserKeepView_BsUserRetentionListQuery res) {
		int pageNum = req.getPageNum();
		int numPerPage = req.getNumPerPage();
		BsUserKeepViewVO bsUserKeepView = new BsUserKeepViewVO();
		bsUserKeepView.setPageNum(pageNum);
		bsUserKeepView.setNumPerPage(numPerPage);
		if (req.getsRegisterTime() != null && !"".equals(req.getsRegisterTime())) {
			bsUserKeepView.setsRegisterTime(req.getsRegisterTime());
		}
		if (req.geteRegisterTime() != null && !"".equals(req.geteRegisterTime())) {
			bsUserKeepView.seteRegisterTime(req.geteRegisterTime());
		}
		if (StringUtil.isNotEmpty(req.getAgentIds())) {
			String[] agentIds = req.getAgentIds().split(",");
			if(agentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : agentIds) {
					if(StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				bsUserKeepView.setAgentIds(ids);
			}
		}
		if (StringUtil.isNotEmpty(req.getNonAgentId())) {
			bsUserKeepView.setNonAgentId(req.getNonAgentId());
		}
		if (req.getOrderDirection() != null && (!"".equals(req.getOrderDirection())) && req.getOrderField() != null && (!"".equals(req.getOrderField()))) {
			bsUserKeepView.setOrderDirection(req.getOrderDirection());
			bsUserKeepView.setOrderField(req.getOrderField());
		}
		int totalRows = bsUserKeepViewService.findUserKeepViewCount(bsUserKeepView);
		if (totalRows > 0) {
			List<BsUserKeepViewVO> bsUserList =  bsUserKeepViewService.findUserKeepViewList(bsUserKeepView);
			List<BsUserKeepViewVO> userKeepList = new ArrayList<BsUserKeepViewVO>(); // 存放到res中
			if (bsUserList != null && bsUserList.size() != 0) {
				for (int i=0;i<bsUserList.size();i++) {
					BsUserKeepViewVO viewVo = bsUserList.get(i);
				    
					BsUserKeepViewVO userKeepViewVO = new BsUserKeepViewVO();
					userKeepViewVO.setRegistDate(viewVo.getRegistDate());
					userKeepViewVO.setRegistUserCountTotal(viewVo.getAgentIdRegistTotal() ==null?0:viewVo.getAgentIdRegistTotal());
					
					userKeepViewVO.setAllAgentIds(viewVo.getAllAgentIds());
					userKeepViewVO.setAllAgentNames(viewVo.getAllAgentNames());
					
					// login_count/regist_count
					if((viewVo.getAgentIdRegistTotal() != null && viewVo.getAgentIdRegistTotal() != 0) && (viewVo.getDay2LoginNumTotal() != null && viewVo.getDay2LoginNumTotal() != 0)) {
						userKeepViewVO.setDay2KeepRate(decimalPoint(((Double.parseDouble((viewVo.getDay2LoginNumTotal().toString())))/(Double.parseDouble((viewVo.getAgentIdRegistTotal().toString()))))*100));
					} else {
						userKeepViewVO.setDay2KeepRate(0.00);
					}
					if((viewVo.getAgentIdRegistTotal() != null && viewVo.getAgentIdRegistTotal() != 0) && (viewVo.getDay3LoginNumTotal() != null && viewVo.getDay3LoginNumTotal() != 0)) {
						userKeepViewVO.setDay3KeepRate(decimalPoint(((Double.parseDouble((viewVo.getDay3LoginNumTotal().toString())))/(Double.parseDouble((viewVo.getAgentIdRegistTotal().toString()))))*100));
					} else {
						userKeepViewVO.setDay3KeepRate(0.00);
					}
					if((viewVo.getAgentIdRegistTotal() != null && viewVo.getAgentIdRegistTotal() != 0) && (viewVo.getDay7LoginNumTotal() != null && viewVo.getDay7LoginNumTotal() != 0)) {
						userKeepViewVO.setDay7KeepRate(decimalPoint(((Double.parseDouble((viewVo.getDay7LoginNumTotal().toString())))/(Double.parseDouble((viewVo.getAgentIdRegistTotal().toString()))))*100));
					} else {
						userKeepViewVO.setDay7KeepRate(0.00);
					}
					if((viewVo.getAgentIdRegistTotal() != null && viewVo.getAgentIdRegistTotal() != 0) && (viewVo.getDay14LoginNumTotal() != null && viewVo.getDay14LoginNumTotal() != 0)) {
						userKeepViewVO.setDay14KeepRate(decimalPoint(((Double.parseDouble((viewVo.getDay14LoginNumTotal().toString())))/(Double.parseDouble((viewVo.getAgentIdRegistTotal().toString()))))*100));
					} else {
						userKeepViewVO.setDay14KeepRate(0.00);
					}
					if((viewVo.getAgentIdRegistTotal() != null && viewVo.getAgentIdRegistTotal() != 0) && (viewVo.getDay30LoginNumTotal() != null && viewVo.getDay30LoginNumTotal() != 0)) {
						userKeepViewVO.setDay30KeepRate(decimalPoint(((Double.parseDouble((viewVo.getDay30LoginNumTotal().toString())))/(Double.parseDouble((viewVo.getAgentIdRegistTotal().toString()))))*100));
					} else {
						userKeepViewVO.setDay30KeepRate(0.00);
					}
					if((viewVo.getAgentIdRegistTotal() != null && viewVo.getAgentIdRegistTotal() != 0) && (viewVo.getDay60LoginNumTotal() != null && viewVo.getDay60LoginNumTotal() != 0)) {
						userKeepViewVO.setDay60KeepRate(decimalPoint(((Double.parseDouble((viewVo.getDay60LoginNumTotal().toString())))/(Double.parseDouble((viewVo.getAgentIdRegistTotal().toString()))))*100));
					} else {
						userKeepViewVO.setDay60KeepRate(0.00);
					}
					userKeepList.add(userKeepViewVO);
					
				}
				
			}
			res.setValueList(BeanUtils.classToArrayList(userKeepList));
		}
		res.setTotalRows(totalRows);
		res.setPageNum(pageNum);
		res.setNumPerPage(numPerPage);
	}
	
	/**
	 * 保留两位小数点
	 * @param d
	 * @return
	 */
	private Double decimalPoint(Double d) {
		BigDecimal bd = new BigDecimal(d);  
		Double result = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		return result;
	}

}
