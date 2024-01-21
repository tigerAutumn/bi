package com.pinting.manage.controller;

import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_BsSpecialJnl_ListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_BsUserBonus_Bonus2JSH;
import com.pinting.business.hessian.manage.message.ResMsg_BsSpecialJnl_ListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_BsUserBonus_Bonus2JSH;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;


/**
 * 异常回款查询/异常交易告警查询
 * @author caonengwen
 *
 */
@Controller
public class SpecialPaymentController {
	
	@Autowired
	@Qualifier("dispatchService")
	private HessianService hessianService;
	/**
	 * 异常回款查询
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/special/payment/index")
	public String paymentInit(ReqMsg_BsSpecialJnl_ListQuery reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model){
		String pageNum = reqMsg.getPageNum();
		String numPerPage = reqMsg.getNumPerPage();
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
			reqMsg.setPageNum(pageNum);
			reqMsg.setNumPerPage(numPerPage);
		}
		reqMsg.setType("【用户回款到卡失败】");
		ResMsg_BsSpecialJnl_ListQuery resp = (ResMsg_BsSpecialJnl_ListQuery) hessianService
				.handleMsg(reqMsg);
		model.put("specialJnlList", resp.getSpecialJnls());
		model.put("amounts", resp.getAmounts() == null ? 0 : resp.getAmounts());
		model.put("pageNum", resp.getPageNum());
		model.put("numPerPage", resp.getNumPerPage());
		model.put("totalRows", resp.getTotalRows());
		model.put("userName", reqMsg.getUserName());
		model.put("mobile", reqMsg.getMobile());
		model.put("status", reqMsg.getStatus());
		model.put("orderNo", reqMsg.getOrderNo());
		return "/specialJnl/payment_index";
	}
	
	/**
	 * 异常交易告警查询
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/special/warning/index")
	public String warningInit(ReqMsg_BsSpecialJnl_ListQuery reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model){
		
		String pageNum = reqMsg.getPageNum();
		String numPerPage = reqMsg.getNumPerPage();
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
			reqMsg.setPageNum(pageNum);
			reqMsg.setNumPerPage(numPerPage);
		}
		if(StringUtil.isBlank(reqMsg.getType())){
			ArrayList<String> list = new ArrayList<String>();
			list.add("【卡bin不存在】");
			list.add("【达飞实名认证通知】");
			list.add("【达飞绑卡通知】");
			list.add("日终【获取达飞债权关系数据】");
			list.add("日终【达飞理财资金对账】");
			list.add("日终【达飞理财资金明细对账】");
			list.add("【奖励金转提失败】：金额");
			list.add("【用户提现异常】");
			reqMsg.setTypeList(list);
		}
		ResMsg_BsSpecialJnl_ListQuery resp = (ResMsg_BsSpecialJnl_ListQuery) hessianService
				.handleMsg(reqMsg);
		model.put("specialJnlList", resp.getSpecialJnls());
		model.put("amounts", resp.getAmounts() == null ? 0 : resp.getAmounts());
		model.put("pageNum", resp.getPageNum());
		model.put("numPerPage", resp.getNumPerPage());
		model.put("totalRows", resp.getTotalRows());
		model.put("userName", reqMsg.getUserName());
		model.put("mobile", reqMsg.getMobile());
		model.put("status", reqMsg.getStatus());
		model.put("orderNo", reqMsg.getOrderNo());
		model.put("type", reqMsg.getType());
		return "/specialJnl/warning_index";
	}
	
	@ResponseBody
	@RequestMapping("/specialJnl/detail")
	public Map<Object, Object> setUpInit(HttpServletRequest request, Map<String, Object> model) {
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), 
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		if(userId != null) {
			ReqMsg_BsUserBonus_Bonus2JSH req = new ReqMsg_BsUserBonus_Bonus2JSH();
			req.setmUserId(Integer.parseInt(userId));
			String id = request.getParameter("id");
			req.setId(Integer.parseInt(id));
			ResMsg_BsUserBonus_Bonus2JSH res = (ResMsg_BsUserBonus_Bonus2JSH) hessianService.handleMsg(req);
			if(!res.isFlag() && ConstantUtil.BSRESCODE_FAIL.equals(res.getResCode())) {
				return ReturnDWZAjax.fail(res.getResMsg());
			} else{
				return ReturnDWZAjax.success("操作成功！");
			}
		} else {
			return ReturnDWZAjax.fail("未取到该管理用户cookie中的id");
		}
	}
	
}
