package com.pinting.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSales_ListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSales_MatrixImage;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSales_Modify;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSales_PrimaryKey;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSales_StatusModify;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSales_UserListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_BsSales_ListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_BsSales_MatrixImage;
import com.pinting.business.hessian.manage.message.ResMsg_BsSales_Modify;
import com.pinting.business.hessian.manage.message.ResMsg_BsSales_PrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_BsSales_StatusModify;
import com.pinting.business.hessian.manage.message.ResMsg_BsSales_UserListQuery;
import com.pinting.business.model.vo.BsSalesDirectInviteVO;
import com.pinting.business.service.manage.BsSalesService;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.ExportUtil;
import com.pinting.util.ReturnDWZAjax;

/**
 * 销售人员业绩
 * @author caonengwen
 *
 */
@Controller
@RequestMapping("/sales")
public class BsSalesController {
	
	@Autowired
	@Qualifier("dispatchService")
	private HessianService hessianService;
	
	@Autowired
	private BsSalesService bsSalesService;
	
	/**
	 * 销售业绩统计
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(ReqMsg_BsSales_ListQuery reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model){
		String pageNum = reqMsg.getPageNum();
		String numPerPage = reqMsg.getNumPerPage();
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
			reqMsg.setPageNum(pageNum);
			reqMsg.setNumPerPage(numPerPage);
		}
		ResMsg_BsSales_ListQuery resp = (ResMsg_BsSales_ListQuery) hessianService
				.handleMsg(reqMsg);
		model.put("salesList", resp.getSalesList());
		model.put("pageNum", resp.getPageNum());
		model.put("numPerPage", resp.getNumPerPage());
		model.put("totalRows", resp.getTotalRows());
		model.put("startTime", reqMsg.getStartTime());
		model.put("endTime", reqMsg.getEndTime());
		model.put("salesName", reqMsg.getSalesName());
		model.put("req", reqMsg);
		model.put("deptList", resp.getDeptList());
		if (reqMsg.getOrderField() != null) {
			model.put(reqMsg.getOrderField(), reqMsg.getOrderDirection());
			model.put("orderField", reqMsg.getOrderField());
			model.put("orderDirection", reqMsg.getOrderDirection());
		}
		return "/sales/sales_index";
	}
	
	
	/**
	 * 销售人员业绩excel导出
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping("/xls")
	public void xls(ReqMsg_BsSales_ListQuery req,HttpServletResponse response,
			HttpServletRequest request) {
		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
		
		//设置标题
		list.add(titles());
		req.setPageNum("0");
		req.setNumPerPage(req.getTotalRows().toString());
		ResMsg_BsSales_ListQuery resp = (ResMsg_BsSales_ListQuery) hessianService
				.handleMsg(req);
		List<HashMap<String,Object>> datas =resp.getSalesList();
		//设置导出excel内容
		if(datas != null && !datas.isEmpty()) {
			int i = 0;
			for (HashMap<String,Object> data : datas) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
//				obj.add(data.get("id"));
				obj.add(data.get("salesName"));
				obj.add(data.get("mobile"));
				obj.add(data.get("inviteCode"));
				obj.add(data.get("directPeople"));
				// 已投资客户
				obj.add(data.get("investmentUser") == null ? 0 : data.get("investmentUser")); 
				obj.add(data.get("directMoney") == null ? 0.00 : MoneyUtil.format(data.get("directMoney").toString()));
				// 直接邀请年化金额
				obj.add(data.get("directAnnualAmount") == null ? 0.00 : MoneyUtil.format(data.get("directAnnualAmount").toString())); 
				obj.add(data.get("indirectPeople"));
				obj.add(data.get("indirectMoney") == null ? 0.00 : MoneyUtil.format(data.get("indirectMoney").toString()));
				obj.add(data.get("entryTimeStr"));
				obj.add(getUserStatus(data.get("status")));
				datasMap.put("user"+(++i), obj);
				list.add(datasMap);
			}
		}
		
		try {
			ExportUtil.exportExcel(response, request, "销售人员业绩查询", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * 销售人员业绩excel导出
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping("/xls_dept_query")
	public void xlsDeptQuery(ReqMsg_BsSales_ListQuery req,HttpServletResponse response,
			HttpServletRequest request) {
		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
		
		//设置操作员ID
		CookieManager cookie = new CookieManager(request);
		String userId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		req.setmUserId(Integer.parseInt(userId));
		
		//设置标题
		list.add(titles());
		req.setPageNum("0");
		req.setNumPerPage(req.getTotalRows().toString());
		ResMsg_BsSales_ListQuery resp = (ResMsg_BsSales_ListQuery) hessianService
				.handleMsg(req);
		List<HashMap<String,Object>> datas =resp.getSalesList();
		//设置导出excel内容
		if(datas != null && !datas.isEmpty()) {
			int i = 0;
			for (HashMap<String,Object> data : datas) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
//				obj.add(data.get("id"));
				obj.add(data.get("salesName"));
				obj.add(data.get("mobile"));
				obj.add(data.get("inviteCode"));
				obj.add(data.get("directPeople"));
				// 已投资客户
				obj.add(data.get("investmentUser") == null ? 0 : data.get("investmentUser")); 
				obj.add(data.get("directMoney") == null ? 0.00 : MoneyUtil.format(data.get("directMoney").toString()));
				// 直接邀请年化金额
				obj.add(data.get("directAnnualAmount") == null ? 0.00 : MoneyUtil.format(data.get("directAnnualAmount").toString())); 
				obj.add(data.get("indirectPeople"));
				obj.add(data.get("indirectMoney") == null ? 0.00 : MoneyUtil.format(data.get("indirectMoney").toString()));
				obj.add(data.get("entryTimeStr"));
				obj.add(getUserStatus(data.get("status")));
				datasMap.put("user"+(++i), obj);
				list.add(datasMap);
			}
		}
		
		try {
			ExportUtil.exportExcel(response, request, "销售人员业绩查询", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Map<String, List<Object>> titles(){
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
//		titles.add("编号");
		titles.add("姓名");
		titles.add("手机号码");
		titles.add("邀请码");
		titles.add("直接邀请客户");
		titles.add("已投资客户");
		titles.add("直接邀请金额");
		titles.add("直接邀请年化金额	");
		titles.add("间接邀请客户");
		titles.add("间接邀请金额");
		titles.add("入职时间");
		titles.add("状态");
		titleMap.put("title", titles);
		return titleMap;
	}
	
	private String getUserStatus(Object obj) {
		String status = "";
		if(obj != null){
			if("1".equals(obj.toString())) {
				status = "在职";
			}else if("2".equals(obj.toString())){
				status = "离职";
			}
		}
		return status;
	}
	
	@RequestMapping("/user/index")
	public String userIndex(ReqMsg_BsSales_UserListQuery reqMsg,
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
		ResMsg_BsSales_UserListQuery resp = (ResMsg_BsSales_UserListQuery) hessianService
				.handleMsg(reqMsg);
		model.put("userSalesList", resp.getUserSalesList());
		model.put("pageNum", resp.getPageNum());
		model.put("numPerPage", resp.getNumPerPage());
		model.put("totalRows", resp.getTotalRows());
		model.put("startTime", reqMsg.getStartTime());
		model.put("salesName", reqMsg.getSalesName());
		model.put("endTime", reqMsg.getEndTime());
		model.put("userName", reqMsg.getUserName());
		model.put("mobile", reqMsg.getMobile());
		model.put("startMoney", reqMsg.getStartMoney());
		model.put("endMoney", reqMsg.getEndMoney());
		model.put("id", reqMsg.getId());
		model.put("grade", reqMsg.getGrade());
		return "/sales/user_index";
	}
	
	/**
	 * 销售人员邀请相关用户excel导出
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping("/user/xls")
	public void userXls(ReqMsg_BsSales_UserListQuery req,HttpServletResponse response,
			HttpServletRequest request) {
		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
		//设置标题
		list.add(userTitles());
		req.setPageNum("0");
		req.setNumPerPage(req.getTotalRows().toString());
		ResMsg_BsSales_UserListQuery resp = (ResMsg_BsSales_UserListQuery) hessianService
				.handleMsg(req);
		List<HashMap<String,Object>> datas =resp.getUserSalesList();
		//设置导出excel内容
		if(datas != null && !datas.isEmpty()) {
			int i = 0;
			for (HashMap<String,Object> data : datas) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(data.get("id"));
				obj.add(data.get("userName"));
				obj.add(data.get("mobile") );
				if(null != req.getGrade()){
					if(req.getGrade() == 1)
						obj.add(data.get("salesName"));
					else
						obj.add(data.get("recommendName"));
				}
				obj.add(data.get("productName"));
				obj.add(data.get("balance"));
				obj.add(data.get("openTime"));
				datasMap.put("user"+(++i), obj);
				list.add(datasMap);
			}
		}
		try {
			ExportUtil.exportExcel(response, request, req.getSalesName()+"业绩详情", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Map<String, List<Object>> userTitles(){
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("编号");
		titles.add("姓名");
		titles.add("手机号码");
		titles.add("邀请人");
		titles.add("购买产品");
		titles.add("购买金额");
		titles.add("购买时间");
		titleMap.put("title", titles);
		return titleMap;
	}
	
	/**
	 * 注销
	 * 
	 */
	@RequestMapping("/status")
	@ResponseBody
	public Map<Object, Object> modifyStatus(
			ReqMsg_BsSales_StatusModify reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		ResMsg_BsSales_StatusModify resp = (ResMsg_BsSales_StatusModify) hessianService
				.handleMsg(reqMsg);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
			return ReturnDWZAjax.success("操作成功！");
		} else {
			return ReturnDWZAjax.fail(resp.getResMsg());
		}
	}
	
	/**
	 * 进入工作人员（销售）添加/修改页面
	 * 
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("sales/detail")
	public String bs19PayBankDetail(ReqMsg_BsSales_PrimaryKey reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		ResMsg_BsSales_PrimaryKey resp = (ResMsg_BsSales_PrimaryKey) hessianService
					.handleMsg(reqMsg);
		model.put("bsSales", resp);
		model.put("depts", resp.getDeptList());
		return "sales/sales_detail";
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
	Map<Object, Object> save(ReqMsg_BsSales_Modify reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		ResMsg_BsSales_Modify resp = (ResMsg_BsSales_Modify) hessianService
				.handleMsg(reqMsg);
		if (resp.getFlag() == 1) {
			return ReturnDWZAjax.success("新增成功！");
		} else if (resp.getFlag() == 2) {
			return ReturnDWZAjax.success("修改成功！");
		} else if (resp.getFlag() == 3) {
			return ReturnDWZAjax.toAjaxString("301", "该邀请码存在！");
		} else {
			return ReturnDWZAjax.fail("操作失败！");
		}
	}
	
	/**
	 * 销售业绩统计(销售使用)
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("query/index")
	public String salesIndex(ReqMsg_BsSales_ListQuery reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model){
		String pageNum = reqMsg.getPageNum();
		String numPerPage = reqMsg.getNumPerPage();
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			reqMsg.setPageNum(pageNum);
		}
		ResMsg_BsSales_ListQuery resp = (ResMsg_BsSales_ListQuery) hessianService
				.handleMsg(reqMsg);
		model.put("salesList", resp.getSalesList());
		model.put("pageNum", resp.getPageNum());
//		model.put("numPerPage", resp.getNumPerPage());
		model.put("numPerPage", reqMsg.getNumPerPage());
		model.put("totalRows", resp.getTotalRows());
		model.put("startTime", reqMsg.getStartTime());
		model.put("endTime", reqMsg.getEndTime());
		model.put("salesName", reqMsg.getSalesName());
		if (reqMsg.getOrderField() != null) {
			model.put(reqMsg.getOrderField(), reqMsg.getOrderDirection());
			model.put("orderField", reqMsg.getOrderField());
			model.put("orderDirection", reqMsg.getOrderDirection());
		}
		return "/sales/sales_query_index";
	}
	
	/**
	 * 生成二维码（销售人员）
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("sales/matrixImage")
	public String matrixImage(ReqMsg_BsSales_MatrixImage reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		ResMsg_BsSales_MatrixImage resp = (ResMsg_BsSales_MatrixImage) hessianService
					.handleMsg(reqMsg);
			model.put("bsSales", resp);
		return "sales/sales_matrix";
	}
	
	@RequestMapping("/directInviteUsers")
	public String directInviteUsers(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model, String salesId, String pageNum, String numPerPage) {
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
		}
		
		int count = bsSalesService.countSalesDirectInviteUsers(Integer.valueOf(salesId));
		List<BsSalesDirectInviteVO> salesDirectInvites = new ArrayList<BsSalesDirectInviteVO>();
		if(count > 0){
			salesDirectInvites = bsSalesService.selectSalesDirectInviteUsers(Integer.valueOf(salesId), pageNum, numPerPage);
		}
		
		model.put("users", salesDirectInvites);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("totalRows", count);
		model.put("salesId", salesId);
		
		return "sales/direct_invite";
	}
	
	
	
	/**
	 * 部门主管查询本部门销售业绩统计数据(销售使用)
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("dept_manager_query/index")
	public String deptManagerSalesIndex(ReqMsg_BsSales_ListQuery reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model){
		//设置操作员ID
		CookieManager cookie = new CookieManager(request);
		String userId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		reqMsg.setmUserId(Integer.parseInt(userId));
		
		String pageNum = reqMsg.getPageNum();
		String numPerPage = reqMsg.getNumPerPage();
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			//numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
			reqMsg.setPageNum(pageNum);
			reqMsg.setNumPerPage(numPerPage);
		}
		ResMsg_BsSales_ListQuery resp = (ResMsg_BsSales_ListQuery) hessianService
				.handleMsg(reqMsg);
		model.put("salesList", resp.getSalesList());
		model.put("pageNum", resp.getPageNum());
//		model.put("numPerPage", resp.getNumPerPage());
		model.put("numPerPage", reqMsg.getNumPerPage());
		model.put("totalRows", resp.getTotalRows());
		model.put("startTime", reqMsg.getStartTime());
		model.put("endTime", reqMsg.getEndTime());
		model.put("salesName", reqMsg.getSalesName());
		if (reqMsg.getOrderField() != null) {
			model.put(reqMsg.getOrderField(), reqMsg.getOrderDirection());
			model.put("orderField", reqMsg.getOrderField());
			model.put("orderDirection", reqMsg.getOrderDirection());
		}
		return "/sales/dept_query_index";
	}
	
}
