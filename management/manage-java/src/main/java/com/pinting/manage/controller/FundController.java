package com.pinting.manage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_Appoint_InfoQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Appoint_ListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Appoint_SaveAppoint;
import com.pinting.business.hessian.manage.message.ReqMsg_MFund_DeleteFund;
import com.pinting.business.hessian.manage.message.ReqMsg_MFund_InfoQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MFund_ListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MFund_NetInfoQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MFund_NetListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MFund_NetSave;
import com.pinting.business.hessian.manage.message.ReqMsg_MFund_SaveFund;
import com.pinting.business.hessian.manage.message.ResMsg_Appoint_InfoQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Appoint_ListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Appoint_SaveAppoint;
import com.pinting.business.hessian.manage.message.ResMsg_MFund_DeleteFund;
import com.pinting.business.hessian.manage.message.ResMsg_MFund_InfoQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MFund_ListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MFund_NetInfoQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MFund_NetListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MFund_NetSave;
import com.pinting.business.hessian.manage.message.ResMsg_MFund_SaveFund;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;

@Controller
public class FundController {

	@Autowired
	@Qualifier("dispatchService")
	private HessianService fundService;
	
	@RequestMapping("/product/fund/index")
	public String fundHomeInit(ReqMsg_MFund_ListQuery req,HashMap<String,Object> model){
		
		ResMsg_MFund_ListQuery res = (ResMsg_MFund_ListQuery) fundService.handleMsg(req);
		model.put("product", res);
		model.put("fundList", res.getValueList());
		return "/product/fund/index";
	}
	
	@RequestMapping("/product/fund/detail")
	public String fundSaveInit(ReqMsg_MFund_InfoQuery req,HashMap<String,Object> model){
		
		model.put("type", req.getOperateType());
		
		if(req.getOperateType().equals("create")){
			
			return "/product/fund/detail";
		}
		ResMsg_MFund_InfoQuery res = (ResMsg_MFund_InfoQuery) fundService.handleMsg(req);
		
		model.put("product", res);
		
		return "/product/fund/detail";
		
	}
	
	@RequestMapping("/product/fund/save")
	public @ResponseBody Map<Object,Object> fundSave(ReqMsg_MFund_SaveFund req){
		
		String massage = "";
		if(req.getOperateType().equals("create")){
			massage = "创建";
		}else{
			massage = "更新";
		}
		
		
		try{
			ResMsg_MFund_SaveFund res = (ResMsg_MFund_SaveFund) fundService.handleMsg(req);
			if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())){
				return ReturnDWZAjax.success(massage + "成功！");
			}else{
				return ReturnDWZAjax.fail(massage + "失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnDWZAjax.fail(massage + "失败！");
		}
		
	}
	
	
	@RequestMapping("/product/fund/delete")
	public @ResponseBody Map<Object,Object> fundDeleteSave (ReqMsg_MFund_DeleteFund reqMsg){
		
		String massage = "";
		if(reqMsg.getOperateType().equals("able")){
			massage = "重新展示";
		}else if(reqMsg.getOperateType().equals("diable")){
			massage = "下架";
		}
		
		try{
			ResMsg_MFund_DeleteFund resMsg = (ResMsg_MFund_DeleteFund) fundService.handleMsg(reqMsg);
			if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())){
				return ReturnDWZAjax.success(massage + "成功！");
			}else{
				return ReturnDWZAjax.fail(massage + "失败！");
			}
		} catch(Exception e){
			e.printStackTrace();
			return ReturnDWZAjax.fail(massage + "失败！");
		}
	}
	
	@RequestMapping("/fund/appoint/index")
	public String fundAppointInit(ReqMsg_Appoint_ListQuery req,HashMap<String,Object> model){
		
		ResMsg_Appoint_ListQuery res = (ResMsg_Appoint_ListQuery) fundService.handleMsg(req);
		model.put("product", res);
		model.put("appointList", res.getValueList());
		model.put("deal", Constants.APPOINT_DEAL);
		return "/product/appoint/index";
	}
	
	@RequestMapping("/fund/appoint/detail")
	public String fundAppointDetailInit(ReqMsg_Appoint_InfoQuery req,HashMap<String,Object> model){
		
		ResMsg_Appoint_InfoQuery res = (ResMsg_Appoint_InfoQuery) fundService.handleMsg(req);
		model.put("product",res);
		
		return "/product/appoint/detail";
	}
	
	@RequestMapping("/fund/appoint/save")
	public @ResponseBody Map<Object,Object> fundAppointSave(ReqMsg_Appoint_SaveAppoint req,HashMap<String,Object> model,HttpServletRequest request){
		
		try{
			CookieManager manager = new CookieManager(request);
			
			String mUserId = manager.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
			req.setUserId(mUserId);
			ResMsg_Appoint_SaveAppoint res = (ResMsg_Appoint_SaveAppoint) fundService.handleMsg(req);
			if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())){
				return ReturnDWZAjax.success("更新成功！");
			}else{
				return ReturnDWZAjax.fail("更新失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnDWZAjax.fail("更新失败！");
		}
	}
	@RequestMapping("/product/fund/net/index")
	public String fundNetInit(ReqMsg_MFund_NetListQuery req,HashMap<String,Object> model){
		
	
			ResMsg_MFund_NetListQuery res = (ResMsg_MFund_NetListQuery) fundService.handleMsg(req);
			
			model.put("product", res);
			model.put("netList", res.getNetList());
			
			return "/product/fund/net_index";
	}
	@RequestMapping("/product/fund/net/detail")
	public String fundNetDetail(ReqMsg_MFund_NetInfoQuery req,HashMap<String,Object> model){
		ResMsg_MFund_NetInfoQuery res = (ResMsg_MFund_NetInfoQuery) fundService.handleMsg(req);
		if(req.getId()!=null)
		{
		
			model.put("net", res);
		}	
		model.put("fundLists", res.getFundList());
			return "/product/fund/net_detail";
	}

	@RequestMapping("/product/fund/net/save")
	public  @ResponseBody Map<Object,Object>  fundNetSave(ReqMsg_MFund_NetSave req,HashMap<String,Object> model){
		try{
			
			ResMsg_MFund_NetSave res = (ResMsg_MFund_NetSave) fundService.handleMsg(req);
			if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())){
				return ReturnDWZAjax.success("操作成功！");
			}else{
				return ReturnDWZAjax.fail(res.getResMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnDWZAjax.fail("操作失败！");
		}
	}
	
}
