package com.pinting.manage.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pinting.business.hessian.manage.message.ReqMsg_Bs19PayBank_BankListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Bs19PayBank_Bs19PayBankPrimaryKey;
import com.pinting.business.hessian.manage.message.ReqMsg_Bs19PayBank_IsAvailableModify;
import com.pinting.business.hessian.manage.message.ReqMsg_Bs19PayBank_Save;
import com.pinting.business.hessian.manage.message.ReqMsg_BsBankCard_BsBankCardStatusModify;
import com.pinting.business.hessian.manage.message.ResMsg_Bs19PayBank_Bs19PayBankPrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_Bs19PayBank_IsAvailableModify;
import com.pinting.business.hessian.manage.message.ResMsg_Bs19PayBank_Save;
import com.pinting.business.hessian.manage.message.ResMsg_BsBankCard_BsBankCardStatusModify;
import com.pinting.business.hessian.manage.message.ResMsg_BsBank_BsBankPrimaryKey;
import com.pinting.business.hessian.manage.message.ReqMsg_BsBankCard_BankCardListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_BsBankCard_CardRecordListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_BsBank_BankListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_BsBank_BsBankModify;
import com.pinting.business.hessian.manage.message.ReqMsg_BsBank_BsBankPrimaryKey;
import com.pinting.business.hessian.manage.message.ReqMsg_BsBank_BsBankStatusModify;
import com.pinting.business.hessian.manage.message.ResMsg_BsBankCard_BankCardListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_BsBankCard_CardRecordListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_BsBank_BankListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_BsBank_BsBankModify;
import com.pinting.business.hessian.manage.message.ResMsg_BsBank_BsBankStatusModify;
import com.pinting.business.hessian.manage.message.ResMsg_Bs19PayBank_BankListQuery;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.util.Constants;
import com.pinting.util.GlobEnv;
import com.pinting.util.ReturnDWZAjax;
/**
 * 绑定银行卡管理/银行维护/19付银行渠道维护
 * @author caonengwen
 *
 */
@Controller
@RequestMapping("/bank")
public class BankControll {

	@Autowired
	@Qualifier("dispatchService")
	private HessianService hessianService;

	/**
	 * 银行卡管理
	 * 
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/card/index")
	public String bankCardInit(ReqMsg_BsBankCard_BankCardListQuery reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		String pageNum = reqMsg.getPageNum();
		String numPerPage = reqMsg.getNumPerPage();
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
			reqMsg.setPageNum(pageNum);
			reqMsg.setNumPerPage(numPerPage);
		}
		ResMsg_BsBankCard_BankCardListQuery resp = (ResMsg_BsBankCard_BankCardListQuery) hessianService
				.handleMsg(reqMsg);
		model.put("bankCardsList", resp.getBankCards());
		model.put("bankList", resp.getBankNameList());
		model.put("pageNum", resp.getPageNum());
		model.put("numPerPage", resp.getNumPerPage());
		model.put("totalRows", resp.getTotalRows());
		model.put("cardOwner", reqMsg.getCardOwner());
		model.put("mobile", reqMsg.getMobile());
		model.put("obligateMobile", reqMsg.getObligateMobile());
		model.put("idCard", reqMsg.getIdCard());
		model.put("bankId", reqMsg.getBankId());
		model.put("status", reqMsg.getStatus());
		model.put("isFirst", reqMsg.getIsFirst());
		model.put("cardNo", reqMsg.getCardNo());
		return "/bank/card_index";
	}
	
	/**
	 * 解绑银行卡
	 * 
	 */
	@RequestMapping("/statusModifyCard")
	@ResponseBody
	public Map<Object, Object> modifyStatusCard(
			ReqMsg_BsBankCard_BsBankCardStatusModify reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		ResMsg_BsBankCard_BsBankCardStatusModify resp = (ResMsg_BsBankCard_BsBankCardStatusModify) hessianService
				.handleMsg(reqMsg);
		if (resp.getSignMsg() == ResMsg_BsBankCard_BsBankCardStatusModify.SUCCESS) {
			return ReturnDWZAjax.success("操作成功！");
		}else {
			return ReturnDWZAjax.fail(resp.getMsg());
		}
	}

	/**
	 * 购买失败记录
	 * 
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/record/index")
	public String bankCardRecordInit(
			ReqMsg_BsBankCard_CardRecordListQuery reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		String pageNum = reqMsg.getPageNum();
		String numPerPage = reqMsg.getNumPerPage();
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
			reqMsg.setPageNum(pageNum);
			reqMsg.setNumPerPage(numPerPage);
		}
		ResMsg_BsBankCard_CardRecordListQuery resp = (ResMsg_BsBankCard_CardRecordListQuery) hessianService
				.handleMsg(reqMsg);
		model.put("payOrdersList", resp.getPayOrders());
		model.put("pageNum", resp.getPageNum());
		model.put("numPerPage", resp.getNumPerPage());
		model.put("totalRows", resp.getTotalRows());
		model.put("status", reqMsg.getStatus());
		model.put("bankCardNo", reqMsg.getBankCardNo());
		return "/bank/record_index";
	}

	/**
	 * 银行维护
	 * 
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String bankIint(ReqMsg_BsBank_BankListQuery reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		String pageNum = reqMsg.getPageNum();
		String numPerPage = reqMsg.getNumPerPage();
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
			reqMsg.setPageNum(pageNum);
			reqMsg.setNumPerPage(numPerPage);
		}
		ResMsg_BsBank_BankListQuery resp = (ResMsg_BsBank_BankListQuery) hessianService
				.handleMsg(reqMsg);
		model.put("bankList", resp.getBanks());
		model.put("pageNum", resp.getPageNum());
		model.put("numPerPage", resp.getNumPerPage());
		model.put("totalRows", resp.getTotalRows());
		model.put("status", reqMsg.getStatus());
		model.put("name", reqMsg.getName());
		return "/bank/index";
	}

	/**
	 * 禁用/启用银行
	 * 
	 */
	@RequestMapping("/statusModify")
	@ResponseBody
	public Map<Object, Object> modifyStatusBank(
			ReqMsg_BsBank_BsBankStatusModify reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		ResMsg_BsBank_BsBankStatusModify resp = (ResMsg_BsBank_BsBankStatusModify) hessianService
				.handleMsg(reqMsg);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
			return ReturnDWZAjax.success("操作成功！");
		} else {
			return ReturnDWZAjax.fail(resp.getResMsg());
		}
	}

	/**
	 * 进入银行添加/修改页面
	 * 
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/detail")
	public String detail(ReqMsg_BsBank_BsBankPrimaryKey reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		if (null != reqMsg.getId() && 0 != reqMsg.getId()) {// 编辑
			ResMsg_BsBank_BsBankPrimaryKey resp = (ResMsg_BsBank_BsBankPrimaryKey) hessianService
					.handleMsg(reqMsg);
			String mUrl = GlobEnv.get("micro.web")+request.getContextPath()+"/";
			model.put("mUrl", mUrl);
			model.put("bsBank", resp);
		}
		return "/bank/detail";
	}

	/**
	 * 银行添加或修改
	 * 
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public @ResponseBody
	Map<Object, Object> save(ReqMsg_BsBank_BsBankModify reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		reqMsg.setLargeLogo(getUrl(request, reqMsg.getLargeLogo()));
		reqMsg.setSmallLogo(getUrl(request, reqMsg.getSmallLogo()));
		ResMsg_BsBank_BsBankModify resp = (ResMsg_BsBank_BsBankModify) hessianService
				.handleMsg(reqMsg);
		if (resp.getFlag() == 1) {
			return ReturnDWZAjax.success("新增成功！");
		} else if (resp.getFlag() == 2) {
			return ReturnDWZAjax.success("修改成功！");
		} else if (resp.getFlag() == 3) {
			return ReturnDWZAjax.toAjaxString("301", "该银行已存在！");
		} else {
			return ReturnDWZAjax.fail("对不起，操作失败！");
		}
	}

	/**
	 * 19付银行渠道维护
	 * 
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/bs19PayBank/index")
	public String nineteenPayBankIint(ReqMsg_Bs19PayBank_BankListQuery reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		String pageNum = reqMsg.getPageNum();
		String numPerPage = reqMsg.getNumPerPage();
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
			reqMsg.setPageNum(pageNum);
			reqMsg.setNumPerPage(numPerPage);
		}
		ResMsg_Bs19PayBank_BankListQuery resp = (ResMsg_Bs19PayBank_BankListQuery) hessianService
				.handleMsg(reqMsg);
		model.put("bs19PayBankList", resp.getBs19PayBank());
		model.put("pageNum", resp.getPageNum());
		model.put("numPerPage", resp.getNumPerPage());
		model.put("totalRows", resp.getTotalRows());
		model.put("pay19BankCode", reqMsg.getPay19BankCode());
		model.put("name", reqMsg.getName());
		model.put("payType", reqMsg.getPayType());
		model.put("isAvailable", reqMsg.getIsAvailable());
		model.put("isMain", reqMsg.getIsMain());
		model.put("channelPriority", reqMsg.getChannelPriority());
		model.put("channel", reqMsg.getChannel());
		return "/bank/bs19PayBank_index";
	}

	/**
	 * 禁用/启用19付银行渠道
	 * 
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/isAvailableModify")
	@ResponseBody
	public Map<Object, Object> isAvailableModify(
			ReqMsg_Bs19PayBank_IsAvailableModify reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		ResMsg_Bs19PayBank_IsAvailableModify resp = (ResMsg_Bs19PayBank_IsAvailableModify) hessianService
				.handleMsg(reqMsg);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
			return ReturnDWZAjax.success("操作成功！");
		} else {
			return ReturnDWZAjax.fail(resp.getResMsg());
		}
	}
	
	
	/**
	 * 进入19付银行渠道添加/修改页面
	 * 
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/bs19PayBank/detail")
	public String bs19PayBankDetail(ReqMsg_Bs19PayBank_Bs19PayBankPrimaryKey reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
			ResMsg_Bs19PayBank_Bs19PayBankPrimaryKey resp = (ResMsg_Bs19PayBank_Bs19PayBankPrimaryKey) hessianService
					.handleMsg(reqMsg);
			model.put("bs19PayBank", resp);
			model.put("bsBankList", resp.getBsBanks());
		return "/bank/bs19PayBank_detail";
	}
	
	
	/**
	 * 添加或修改
	 * 
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/bs19PayBank/save")
	public @ResponseBody
	Map<Object, Object> bs19PayBankSave(ReqMsg_Bs19PayBank_Save reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		ResMsg_Bs19PayBank_Save resp = (ResMsg_Bs19PayBank_Save) hessianService
				.handleMsg(reqMsg);
		if(resp.getFlag() != null) {
			if (resp.getFlag() == 1) {
				return ReturnDWZAjax.success("新增成功！");
			} else if (resp.getFlag() == 2) {
				return ReturnDWZAjax.success("修改成功！");
			} else if (resp.getFlag() == 3) {
				return ReturnDWZAjax.toAjaxString("301", "该渠道已存在！");
			} else if (resp.getFlag() == 4) {
				return ReturnDWZAjax.toAjaxString("302", "该渠道优先级为1已存在！");
			}else {
				return ReturnDWZAjax.fail("操作失败！");
			}
		} else {
			return ReturnDWZAjax.fail(resp.getResMsg());
		}

	}
	
	/**
	 * 去url中除域名
	 * @param request
	 * @param url需要去掉域名的url
	 * @return
	 */
	private String getUrl(HttpServletRequest request,String url){//
		String path = request.getContextPath()+"/";
		String mUrl = GlobEnv.get("micro.web")+path;
		if(url.indexOf(mUrl) != -1){//说明含有域名
			 url = url.replaceAll(mUrl,"");
		}
		return url;
	}

}








