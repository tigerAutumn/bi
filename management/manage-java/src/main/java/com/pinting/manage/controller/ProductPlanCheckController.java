package com.pinting.manage.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_ProductPlanCheck_PlanCheck;
import com.pinting.business.hessian.manage.message.ReqMsg_ProductPlanCheck_PlanDetail;
import com.pinting.business.hessian.manage.message.ReqMsg_ProductPlanCheck_PlanListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_ProductPlanStatutsChange_ScheduleRegist4AuthPass;
import com.pinting.business.hessian.manage.message.ReqMsg_ProductPlanStatutsChange_ScheduleRegistDelete4AuthReturn;
import com.pinting.business.hessian.manage.message.ResMsg_ProductPlanCheck_PlanCheck;
import com.pinting.business.hessian.manage.message.ResMsg_ProductPlanCheck_PlanDetail;
import com.pinting.business.hessian.manage.message.ResMsg_ProductPlanCheck_PlanListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_ProductPlanStatutsChange_ScheduleRegist4AuthPass;
import com.pinting.business.hessian.manage.message.ResMsg_ProductPlanStatutsChange_ScheduleRegistDelete4AuthReturn;
import com.pinting.business.model.BsProduct;
import com.pinting.business.model.BsProductSerial;
import com.pinting.business.model.BsProductSerialExample;
import com.pinting.business.service.manage.MProductSerialService;
import com.pinting.business.service.manage.MProductService;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.manage.enums.EditorEum;
import com.pinting.manage.enums.ShowTerminalEnum;
import com.pinting.util.Constants;
import com.pinting.util.EditorUtil;
import com.pinting.util.ReturnDWZAjax;


@Controller
@RequestMapping("/productPlanCheck")
public class ProductPlanCheckController {
	@Autowired
	@Qualifier("dispatchService")
	public HessianService planCheckService;
	
	
    @Autowired
    @Qualifier("manageService")
    private HessianService planStatusChangeService;
    @Autowired
    private MProductService mProductService;
    @Autowired
    private MProductSerialService productSerialService;
	/**
	 * 产品计划审核列表
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String planIndex(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model,ReqMsg_ProductPlanCheck_PlanListQuery req){
		
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage(); 
		if(pageNum==null ||pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_100_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		if(request.getParameter("orderDirection") != null && request.getParameter("orderField") != null) {
			req.setOrderDirection(request.getParameter("orderDirection"));
			req.setOrderField(request.getParameter("orderField"));
			model.put("orderDirection", request.getParameter("orderDirection"));
			model.put("orderField", request.getParameter("orderField"));
		} else {
			req.setOrderDirection("desc");
			req.setOrderField("create_time");
			model.put("orderField", req.getOrderField());
			model.put("orderDirection", req.getOrderDirection());
		}
		ResMsg_ProductPlanCheck_PlanListQuery res = (ResMsg_ProductPlanCheck_PlanListQuery) planCheckService.handleMsg(req);
		
        //查询理财计划期数
        List<BsProduct> terms = mProductService.findAllProductTerm();
        model.put("terms", terms);
        //查询理财计划利率
        List<BsProduct> rates = mProductService.findAllProductBaseRate();
        model.put("rates", rates);
        //查询产品系列列表
        BsProductSerialExample example = new BsProductSerialExample();
		example.setOrderByClause("update_time desc");
		List<BsProductSerial> serials = productSerialService.findBsProductSerials(example);
        model.put("serials", serials);
		
		model.put("req", req);
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", req.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("checkList", res.getValueList());
		
		return "/product/plan/check_index";
	}
	
	
	/**
	 * 进入计划审核页面
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/detail")
	public String detail(ReqMsg_ProductPlanCheck_PlanDetail req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        String resources = GlobEnvUtil.get("news.resources");
        String manageWeb = GlobEnvUtil.get("server.web");
        String web = GlobEnvUtil.get("news.resources");
		
		ResMsg_ProductPlanCheck_PlanDetail res = (ResMsg_ProductPlanCheck_PlanDetail) planCheckService.handleMsg(req);
		res.getProductDatas().put("note", EditorUtil.replace((String)res.getProductDatas().get("note"), resources, manageWeb, web));
		
		String showTerminal= (String)res.getProductDatas().get("showterminal");
		String showTerminalTemp = "";
		if (showTerminal != null && !"".equals(showTerminal)) {
			String[] showTerminals = showTerminal.split(",");
			int i =0;
			for (String terminalString : showTerminals) {
				for (ShowTerminalEnum editor : EnumSet.allOf(ShowTerminalEnum.class)) {
					if (editor.getCode().equals(terminalString)) {
						if (i == showTerminals.length-1) {
							showTerminalTemp = showTerminalTemp + editor.getDescription();
						}else {
							showTerminalTemp = showTerminalTemp + editor.getDescription()+",";
						}
						
					}
				}
				i++;
			}
		}
		res.getProductDatas().put("showterminal", showTerminalTemp);
		
		
		model.put("product", res.getProductDatas());
		return "/product/plan/check_detail";
	}
	
	
	
	@RequestMapping("/passCheck")
	public @ResponseBody Map<Object, Object> passCheck(ReqMsg_ProductPlanCheck_PlanCheck req,String flag,
			String startTime,HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		req.setChecker(Integer.parseInt(mUserId));
		req.setCheckType(flag);
		if ("pass".equals(flag)) {
			if (startTime == null || "".equals(startTime)) {
			}else{
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
				    Date dt1 = df.parse(startTime);
				    Date dt2 = df.parse(DateUtil.format(new Date()));
				    if (dt1.getTime() < dt2.getTime()) {
				    	return ReturnDWZAjax.fail("当前时间超过了开始时间！");
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			req.setStatus(Constants.PRODUCT_STATUS_PUBLISH_NO);
		}else if ("refuse".equals(flag)) {
			req.setStatus(Constants.PRODUCT_STATUS_PASS_NO);
		}else if ("return".equals(flag)) {
			req.setStatus(Constants.PRODUCT_STATUS_PASS_NO);
		}
		
		if (req.getProductId() == null || "".equals(req.getProductId())) {
			return ReturnDWZAjax.fail("参数有误，请重试！");
		}
		
		ResMsg_ProductPlanCheck_PlanCheck res = (ResMsg_ProductPlanCheck_PlanCheck) planCheckService.handleMsg(req);
		

		if (ConstantUtil.BSRESCODE_SUCCESS.equals("000000")) {
			
			if ("pass".equals(flag)) {
				
				if (res.getCheckFlag() != null && "REPEAT".equals(res.getCheckFlag()) ) {
					return ReturnDWZAjax.fail("只有待审核状态的理财计划可以审核！");
				}
				
				BsProduct bsProduct = res.getBsProduct();
				ReqMsg_ProductPlanStatutsChange_ScheduleRegist4AuthPass passReq = new ReqMsg_ProductPlanStatutsChange_ScheduleRegist4AuthPass();
				passReq.setBsProduct(bsProduct);
				ResMsg_ProductPlanStatutsChange_ScheduleRegist4AuthPass passRes = (ResMsg_ProductPlanStatutsChange_ScheduleRegist4AuthPass) planStatusChangeService.handleMsg(passReq);
				
			}else if ("refuse".equals(flag)) {
				
				if (res.getCheckFlag() != null && "REPEAT".equals(res.getCheckFlag()) ) {
					return ReturnDWZAjax.fail("只有待审核状态的理财计划可以审核！");
				}
				
				BsProduct bsProduct = res.getBsProduct();
				ReqMsg_ProductPlanStatutsChange_ScheduleRegistDelete4AuthReturn refuseReq = new ReqMsg_ProductPlanStatutsChange_ScheduleRegistDelete4AuthReturn();
				refuseReq.setBsProduct(bsProduct);
				ResMsg_ProductPlanStatutsChange_ScheduleRegistDelete4AuthReturn refuseRes = (ResMsg_ProductPlanStatutsChange_ScheduleRegistDelete4AuthReturn) planStatusChangeService.handleMsg(refuseReq);
				
			}else if ("return".equals(flag)) {
				
				if (res.getCheckFlag() != null && "REPEAT".equals(res.getCheckFlag()) ) {
					return ReturnDWZAjax.fail("只有待发布状态的理财计划可以退回！");
				}
				
				BsProduct bsProduct = res.getBsProduct();
				ReqMsg_ProductPlanStatutsChange_ScheduleRegistDelete4AuthReturn refuseReq = new ReqMsg_ProductPlanStatutsChange_ScheduleRegistDelete4AuthReturn();
				refuseReq.setBsProduct(bsProduct);
				ResMsg_ProductPlanStatutsChange_ScheduleRegistDelete4AuthReturn refuseRes = (ResMsg_ProductPlanStatutsChange_ScheduleRegistDelete4AuthReturn) planStatusChangeService.handleMsg(refuseReq);
				
			}
			return ReturnDWZAjax.success("操作成功！");
		} else {
			return ReturnDWZAjax.fail("操作失败，请重试！");
		}
	}
	
}
