package com.pinting.manage.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pinting.business.hessian.manage.message.ReqMsg_MAccount_TradeDetailListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MAccount_TradeDetailListQuery;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.util.Constants;

/**
 * 用户交易明细控制器
 * @author yanwl
 * @date 2015-11-13
 */
@Controller
public class AccountTradeController {
	@Autowired
	@Qualifier("dispatchService")
	private HessianService userTradeDetailService;

	/**
	 * 打开注册用户管理界面
	 * 
	 */
	@RequestMapping("/account/trade/detail")
	public String accountTradeDetail(ReqMsg_MAccount_TradeDetailListQuery req,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		ResMsg_MAccount_TradeDetailListQuery resp = (ResMsg_MAccount_TradeDetailListQuery) userTradeDetailService
				.handleMsg(req);
		model.put("pageNum", resp.getPageNum());
		model.put("numPerPage", resp.getNumPerPage());
		model.put("totalRows", resp.getTotalRows());
		model.put("sMobile", resp.getsMobile());
		model.put("sUserName", resp.getsUserName());
		model.put("sTransType", resp.getsTransType());
		model.put("userTransDetails", resp.getUserTransDetail());
		
		return "/account/trade/trade_detail";
	}
}
