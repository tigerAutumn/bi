package com.pinting.manage.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.model.BsUser;
import com.pinting.business.model.Tbdatapermission;
import com.pinting.business.model.Tbdepartment;
import com.pinting.business.model.Tbemployee;
import com.pinting.business.model.vo.BsDafyFinanceChangeRecordVO;
import com.pinting.business.model.vo.CustomerQueryVO;
import com.pinting.business.model.vo.DafyBusinessManagerVO;
import com.pinting.business.model.vo.FinancialIntentionVO;
import com.pinting.business.model.vo.FinancialRechargeRecordVO;
import com.pinting.business.model.vo.FinancialRedeemVO;
import com.pinting.business.model.vo.FinancialSettlementVO;
import com.pinting.business.model.vo.FinancialUserInvestDetailVO;
import com.pinting.business.model.vo.OwnershipVO;
import com.pinting.business.model.vo.PageInfoObject;
import com.pinting.business.service.manage.FinancialIntentionService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.ExportUtil;
import com.pinting.util.ReturnDWZAjax;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author HuanXiong
 * @version 2016-6-2 下午3:52:01
 */
@Controller
public class FinancialIntentionController {
    
    @Autowired
    private FinancialIntentionService financialIntentionService;
    @Autowired
    private UserService userService;

    @RequestMapping("/financialIntention/financialIntentionIndex")
    public String financialIntentionIndex(FinancialIntentionVO req, HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
    	//获取产品类型
        String productType = request.getParameter("productType")==null?Constants.PRODUCT_TYPE_NORMAL:request.getParameter("productType");
        
    	CookieManager cookie = new CookieManager(request);
        String mobile = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_EMAIL.getName(), true);
        String dafyUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERID.getName(), true);
        String dafyDeptId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTID.getName(), true);
        String dafyDeptCode = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTCODE.getName(), true);
        String dafyUserName = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERNME.getName(), true);
        String dafyDeptName = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTNAME.getName(), true);
        String isManager = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISMANAGER.getName(), true);
        String isDafyUserString = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISDAFYUSER.getName(), true);
        
        if("yes".equals(isDafyUserString)) {
        	String selectDeptId = request.getParameter("selectDeptId");
            List<Tbemployee> userList = new ArrayList<Tbemployee>();
        	//查询达飞用户权限
        	List<Tbdatapermission> missionList = financialIntentionService.selectUserMission(Long.valueOf(dafyUserId));
        	//普通客户经理
        	if(CollectionUtils.isEmpty(missionList) && "0".equals(isManager)) {
        		model.put("deptStatus", "disable");
    			model.put("userStatus", "disable");
    			Tbemployee user = new Tbemployee();
    			user.setLuserid(Long.valueOf(dafyUserId));
    			user.setStrmobile(mobile);
    			user.setStrname(dafyUserName);
    			userList.add(user);
        	}
        	else {
        		model.put("deptStatus", "able");
    			model.put("userStatus", "able");
    			if(StringUtil.isNotBlank(selectDeptId)) {
    				boolean flag = true;
    				for(Tbdatapermission mission : missionList) {
    		    		if(mission.getStrdeptcode().equals(dafyDeptCode)) {
    		    			flag = false;
    		    			break;
    		    		}
    		    	}
    				//没有特殊权限，非部门主管同时选择的部门又是自己所属部门，则只能查看自己信息
    				if(flag && selectDeptId.equals(dafyDeptId) && "0".equals(isManager)) {
    					Tbemployee user = new Tbemployee();
    	    			user.setLuserid(Long.valueOf(dafyUserId));
    	    			user.setStrmobile(mobile);
    	    			user.setStrname(dafyUserName);
    	    			userList.add(user);
    				}
    				else {
    					//查询部门下的客户经理
            			userList = financialIntentionService.selectEmployeeByDeptId(Long.valueOf(selectDeptId));
    				}
        		}
        	}
        	
        	model.put("dafyUserId", dafyUserId);
        	model.put("dafyUserName", dafyUserName);
        	model.put("dafyDeptId", dafyDeptId);
        	model.put("dafyDeptName", dafyDeptName);
        	
        	model.put("isDafyUser", isDafyUserString);
            model.put("userList", userList);
            
            String isSelect = request.getParameter("isSelect");
            if("y".equals(isSelect)) {
            	List<Long> deptIds = new ArrayList<Long>();
            	List<Long> copyDeptIds = new ArrayList<Long>();
            	String isOrDafyUserId = null;
            	//已指定营业部
            	if(StringUtil.isNotBlank(selectDeptId)) {
            		deptIds.add(Long.valueOf(selectDeptId));
                }
            	//未指定营业部
        		if(CollectionUtils.isEmpty(deptIds)) {
        			Map<Long,Tbdepartment> deptMap = new HashMap<Long,Tbdepartment>();
        			List<Tbdepartment> lowDept = financialIntentionService.selectLowLevelDept(Long.valueOf(dafyUserId));
        			for(Tbdepartment dept : lowDept) {
        				deptMap.put(dept.getLid(), dept);
        			}
        			
        			//1为部门主管
        			if(Integer.valueOf(isManager) == 1) {
        				Tbdepartment myDept = financialIntentionService.selectTbdepartmentByKey(Long.valueOf(dafyDeptId));
        				Integer level = myDept.getNcurrentlevel();
        				String sql = "select * from tbdepartment where strDeptCode"+level+"='"+myDept.getStrdeptcode()+"'";
        				List<Tbdepartment> nextDeptList = financialIntentionService.selectTbdepartmentSQL(sql);
        				for(Tbdepartment dept : nextDeptList) {
        					deptMap.put(dept.getLid(), dept);
        				}
        			}
        			else {
        				if(!deptMap.containsKey(Long.valueOf(dafyDeptId))) {
        					isOrDafyUserId = dafyUserId;
        				}
        			}
        			deptIds = new ArrayList<Long>(deptMap.keySet());
        		}
        		copyDeptIds.addAll(deptIds);
            	
            	List<FinancialIntentionVO> dataGrid = new ArrayList<FinancialIntentionVO>();
                Integer count = 0;
                if(StringUtil.isNotBlank(dafyUserId) && StringUtil.isNotBlank(dafyDeptId)) {
                	FinancialIntentionVO copyReq = new FinancialIntentionVO();
                	org.springframework.beans.BeanUtils.copyProperties(req, copyReq);
                    dataGrid = financialIntentionService.queryFinancialIntentionGrid(req, deptIds, isOrDafyUserId, productType);
                    count = financialIntentionService.countFinancialIntention(copyReq, copyDeptIds, isOrDafyUserId, productType);
                }
                
                //将数据返回给页面
                model.put("selectDeptId", selectDeptId);
                model.put("dataGrid", dataGrid);
                req.setTotalRows(count);
            }
        }
        model.put("productType", productType);
        model.put("req", req);
        
        if(Constants.PRODUCT_TYPE_ZAN.equals(productType)){
        	return "/financial/financial_intention_index_zan";
        }else{
        	return "/financial/financial_intention_index";
        }
        
    }
    
    @RequestMapping("/financialIntention/financialIntentionExport")
    public void financialIntentionExport(FinancialIntentionVO req, HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
    	//获取产品类型
        String productType = request.getParameter("productType")==null?Constants.PRODUCT_TYPE_NORMAL:request.getParameter("productType");
    	
    	CookieManager cookie = new CookieManager(request);
    	String dafyUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERID.getName(), true);
    	String dafyDeptId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTID.getName(), true);
    	String isManager = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISMANAGER.getName(), true);
    	String isDafyUserString = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISDAFYUSER.getName(), true);
    	if("yes".equals(isDafyUserString)) {
    		String isOrDafyUserId = null;
    		String selectDeptId = request.getParameter("selectDeptId");
        	List<Long> deptIds = new ArrayList<Long>();
        	//已指定营业部
        	if(StringUtil.isNotBlank(selectDeptId)) {
        		deptIds.add(Long.valueOf(selectDeptId));
            }
        	//未指定营业部
    		if(CollectionUtils.isEmpty(deptIds)) {
    			Map<Long,Tbdepartment> deptMap = new HashMap<Long,Tbdepartment>();
    			List<Tbdepartment> lowDept = financialIntentionService.selectLowLevelDept(Long.valueOf(dafyUserId));
    			for(Tbdepartment dept : lowDept) {
    				deptMap.put(dept.getLid(), dept);
    			}
    			
    			//1为部门主管
    			if(Integer.valueOf(isManager) == 1) {
    				Tbdepartment myDept = financialIntentionService.selectTbdepartmentByKey(Long.valueOf(dafyDeptId));
    				Integer level = myDept.getNcurrentlevel();
    				String sql = "select * from tbdepartment where strDeptCode"+level+"='"+myDept.getStrdeptcode()+"'";
    				List<Tbdepartment> deptList = financialIntentionService.selectTbdepartmentSQL(sql);
    				for(Tbdepartment dept : deptList) {
    					deptMap.put(dept.getLid(), dept);
    				}
    			}
    			else {
    				if(!deptMap.containsKey(Long.valueOf(dafyDeptId))) {
    					isOrDafyUserId = dafyUserId;
    				}
    			}
    			deptIds = new ArrayList<Long>(deptMap.keySet());
    		}
    		
    		List<Long> copyDeptIds = new ArrayList<Long>();
    		copyDeptIds.addAll(deptIds);
    		FinancialIntentionVO copyReq = new FinancialIntentionVO();
    		org.springframework.beans.BeanUtils.copyProperties(req, copyReq);
    		
    		Integer count = financialIntentionService.countFinancialIntention(copyReq, copyDeptIds, isOrDafyUserId, productType);
    		req.setNumPerPage(count);
    		req.setPageNum(0);
        	List<FinancialIntentionVO> dataGrid = financialIntentionService.queryFinancialIntentionGrid(req, deptIds, isOrDafyUserId, productType);
        	List<HashMap<String,Object>> datas = BeanUtils.classToArrayList(dataGrid);
    		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
    		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
    		List<Object> titles = new ArrayList<Object>();
    		titles.add("理财人姓名");
    		titles.add("手机号");
    		titles.add("身份证");
    		titles.add("产品名称");
    		titles.add("客户经理");
    		titles.add("营业部");
    		titles.add("理财意向状态");
    		titles.add("预期年化收益率");
    		titles.add("红包抵扣");
    		titles.add("奖励利率");
    		titles.add("理财周期");
    		titles.add("理财金额");
    		if(Constants.PRODUCT_TYPE_ZAN.equals(productType)){
    			titles.add("出借金额");
    		}
    		titles.add("充值金额");
    		titles.add("当前收益");
    		titles.add("申请时间");
    		titles.add("购买成功时间");
    		titles.add("计息开始时间");
    		titles.add("实际赎回时间");
    		titleMap.put("title", titles);
    		list.add(titleMap);
    		if(!CollectionUtils.isEmpty(datas)) {
    			int i = 0;
    			for (HashMap<String,Object> data : datas) {
    				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
    				List<Object> obj = new ArrayList<Object>();
    				obj.add(data.get("userName"));
    				obj.add(StringUtil.substringBefore((String)data.get("mobile"), StringUtil.right((String)data.get("mobile"), 4))+"****");
    				obj.add("****"+StringUtil.substringAfter(StringUtil.substringBefore((String)data.get("idCard"), StringUtil.right((String)data.get("idCard"), 4)), StringUtil.left((String)data.get("idCard"), 4))+"****");
    				obj.add(data.get("productName"));
    				obj.add(data.get("managerName"));
    				obj.add(data.get("deptName"));
    				if(Constants.PRODUCT_TYPE_ZAN.equals(productType)){
    					obj.add((String)data.get("statusStr"));
    				}else if(Constants.PRODUCT_TYPE_NORMAL.equals(productType)){
    					Integer status = (Integer) data.get("status");
        				switch(status) {
        				case 2:
        					obj.add("投资中");
        					break;
        				case 5:
        					obj.add("已完成");
        					break;
        				case 7:
        					obj.add("回款中");
        					break;
        				}
    				}
    				
    				obj.add(data.get("baseRate"));
    				obj.add(data.get("subtract"));
    				obj.add("0.00");
    				Integer term = (Integer)data.get("term");
    				if(term < 0) {
    					obj.add(Math.abs(term)+"天");
    				}
    				else if(term == 12) {
    					obj.add("365天");
    				}
    				else {
    					obj.add(term*30+"天");
    				}
    				if(Constants.PRODUCT_TYPE_ZAN.equals(productType)){
    					obj.add(data.get("openBalance") == null ? "" :MoneyUtil.format((Double)data.get("openBalance")));
    	    		}else{
    	    			obj.add(data.get("balance") == null ? "" :MoneyUtil.format((Double)data.get("balance")));
    	    		}
    				if(Constants.PRODUCT_TYPE_ZAN.equals(productType)){
    					obj.add(data.get("balance") == null ? "" :MoneyUtil.format((Double)data.get("balance")));
    				}
    				obj.add(data.get("rechargeAmount") == null ? "" :MoneyUtil.format((Double)data.get("rechargeAmount")));
    				obj.add(data.get("accumulationInerest") == null ? "" :MoneyUtil.format((Double)data.get("accumulationInerest")));
    				obj.add(DateUtil.format((Date)data.get("applyTime")));
    				obj.add(DateUtil.format((Date)data.get("successTime")));
    				obj.add(DateUtil.formatYYYYMMDD((Date)data.get("interestBeginDate")));
    				if(Constants.PRODUCT_TYPE_ZAN.equals(productType)){
    					if("已完成".equals(data.get("statusStr"))){
    						obj.add(DateUtil.formatYYYYMMDD((Date)data.get("paymentDate")));
    					}else{
    						obj.add("");
    					}
    					
    				}
    				else if(Constants.PRODUCT_TYPE_NORMAL.equals(productType)){
    					obj.add(DateUtil.formatYYYYMMDD((Date)data.get("paymentDate")));
    				}
    				
    				datasMap.put("user"+(++i), obj);
    				list.add(datasMap);
    			}
    		}
    		
    		try {
    			ExportUtil.exportExcel(response, request, "理财意向", list);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    /**
     * 根据部门编码或名称进行模糊查询
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/financialIntention/selectDeptByParam")
	@ResponseBody
    public Map<String, Object> selectDeptByParam(HttpServletRequest request, HttpServletResponse response) {
    	Map<String, Object> model = new HashMap<String, Object>();
    	CookieManager cookie = new CookieManager(request);
    	String isManager = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISMANAGER.getName(), true);
    	String dafyUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERID.getName(), true);
    	String dafyDeptId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTID.getName(), true);
    	String param = request.getParameter("param");
    	List<Tbdepartment> list = financialIntentionService.selectDeptListByParam(param, Long.valueOf(dafyUserId), Long.valueOf(dafyDeptId), Integer.valueOf(isManager));
    	model.put("deptList", list);
    	return model;
    }
    
    
    
    @RequestMapping("/financialIntention/selectUserByDeptId")
	@ResponseBody
	 public Map<String, Object> selectUserByDeptId(HttpServletRequest request, HttpServletResponse response) {
    	CookieManager cookie = new CookieManager(request);
    	String dafyUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERID.getName(), true);
    	String isManager = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISMANAGER.getName(), true);
    	String dafyDeptId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTID.getName(), true);
    	Map<String, Object> model = new HashMap<String, Object>();
    	String deptId = request.getParameter("deptId");
    	List<Tbemployee> list = new ArrayList<Tbemployee>();
    	boolean flag = true;
    	List<Tbdepartment> lowDept = financialIntentionService.selectLowLevelDept(Long.valueOf(dafyUserId));
    	for(Tbdepartment dept : lowDept) {
    		if(dept.getLid().equals(Long.valueOf(dafyDeptId))) {
    			flag = false;
    			break;
    		}
    	}
    	//没有特殊权限，非部门主管同时选择的部门又是自己所属部门，则只能查看自己信息
    	if(flag && dafyDeptId.equals(deptId) && "0".equals(isManager)) {
    		Tbemployee tb = financialIntentionService.selectSingleTbemployee(Long.valueOf(dafyUserId));
    		list.add(tb);
    	}
    	else {
    		list = financialIntentionService.selectEmployeeByDeptId(Long.valueOf(deptId));
    	}
    	model.put("userList", list);
    	return model;
    }
    
    
    @RequestMapping("/financialIntention/selectAllUserByDeptId")
	@ResponseBody
	 public Map<String, Object> selectAllUserByDeptId(HttpServletRequest request, HttpServletResponse response) {
    	CookieManager cookie = new CookieManager(request);
    	String dafyUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERID.getName(), true);
    	String isManager = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISMANAGER.getName(), true);
    	String dafyDeptId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTID.getName(), true);
    	Map<String, Object> model = new HashMap<String, Object>();
    	String deptId = request.getParameter("deptId");
    	List<Tbemployee> list = new ArrayList<Tbemployee>();
    	boolean flag = true;
    	List<Tbdepartment> lowDept = financialIntentionService.selectLowLevelDept(Long.valueOf(dafyUserId));
    	for(Tbdepartment dept : lowDept) {
    		if(dept.getLid().equals(Long.valueOf(dafyDeptId))) {
    			flag = false;
    			break;
    		}
    	}
    	//没有特殊权限，非部门主管同时选择的部门又是自己所属部门，则只能查看自己信息
    	if(flag && dafyDeptId.equals(deptId) && "0".equals(isManager)) {
    		Tbemployee tb = financialIntentionService.selectSingleTbemployee(Long.valueOf(dafyUserId));
    		list.add(tb);
    	}
    	else {
    		list = financialIntentionService.selectAllEmployeeByDeptId(Long.valueOf(deptId));
    	}
    	model.put("userList", list);
    	return model;
    }

    @RequestMapping("/financialIntention/userInvestDetail")
    public String userInvestDetail(Integer subAccountId, HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
    	FinancialUserInvestDetailVO userInvestDetail = financialIntentionService.selectFinancialUserInvestDetail(subAccountId);
    	model.put("userInvestDetail", userInvestDetail);
    	return "/financial/user_invest_detail";
    }
    
    @RequestMapping("/financialIntention/rechargeRecord")
    public String rechargeRecord(Integer subAccountId, HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
    	FinancialRechargeRecordVO rechargeRecord = financialIntentionService.financialRechargeRecord(subAccountId);
    	model.put("rechargeRecord", rechargeRecord);
    	return "/financial/recharge_record";
    }
    
    @RequestMapping("/financialIntention/customerQueryIndex")
    public String customerQueryIndex(CustomerQueryVO req,HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
	   /* 此处代码是不根据客户经理权限进行查询   客户经理信息的代码
	    * 	//检查查询参数
    	String dafyUserId = request.getParameter("dafyUserId");
    	String dafyDeptId = request.getParameter("dafyDeptId");
    	String dafyDeptCode = request.getParameter("dafyDeptCode");
    	
    	String departmentName = request.getParameter("departmentName");
    	String employeeName = request.getParameter("employeeName");
    	String userName = request.getParameter("userName");
    	String mobile = request.getParameter("mobile");
    	String idcard = request.getParameter("idcard");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
		}
    	
        List<CustomerQueryVO> dataGrid = new ArrayList<CustomerQueryVO>();
        Integer count = 0;
        dataGrid = financialIntentionService.customerQueryIndex(dafyUserId==null||"".equals(dafyUserId)?null:Long.valueOf(dafyUserId), dafyDeptId==null||"".equals(dafyDeptId)?null:Long.valueOf(dafyDeptId), userName, mobile, idcard, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
        count = financialIntentionService.customerQueryCount(dafyUserId==null||"".equals(dafyUserId)?null:Long.valueOf(dafyUserId), dafyDeptId==null||"".equals(dafyDeptId)?null:Long.valueOf(dafyDeptId), userName, mobile, idcard);
        if (dafyDeptId!=null && !"".equals(dafyDeptId)) {
        	List<Tbemployee> vos = financialIntentionService.selectEmployeeByDeptId(Long.valueOf(dafyDeptId));
        	model.put("employeeList", vos);
		}
        
        //将数据返回给页面
        model.put("dataGrid", dataGrid);
        model.put("count", count);
        model.put("req", req);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);*/
    	
    	/**
    	 * 新改造的功能   需要根据客户经理权限进行客户信息的查询
    	 */
    	CookieManager cookie = new CookieManager(request);
        String dafyUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERID.getName(), true);
        String dafyDeptId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTID.getName(), true);
        String dafyUserName = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERNME.getName(), true);
        String dafyDeptName = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTNAME.getName(), true);
        String isManager = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISMANAGER.getName(), true);
        String isDafyUserString = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISDAFYUSER.getName(), true);
        String mobile = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_EMAIL.getName(), true);
        // boolean isDafyUser = "yes".equals(isDafyUserString)?true:false;
        List<Tbemployee> userList = new ArrayList<Tbemployee>();
        
        if("yes".equals(isDafyUserString)) {
        	//查询达飞用户权限
        	List<Tbdatapermission> missionList = financialIntentionService.selectUserMission(Long.valueOf(dafyUserId));
        	//普通客户经理
        	if(CollectionUtils.isEmpty(missionList) && "0".equals(isManager)) {
        		model.put("deptStatus", "disable");
    			model.put("userStatus", "disable");
    			Tbemployee user = new Tbemployee();
    			user.setLuserid(Long.valueOf(dafyUserId));
    			user.setStrmobile(mobile);
    			user.setStrname(dafyUserName);
    			userList.add(user);
        	}
        	else {
        		model.put("deptStatus", "able");
    			model.put("userStatus", "able");
        	}
        	
        	model.put("dafyUserId", dafyUserId);
        	model.put("dafyUserName", dafyUserName);
        	model.put("dafyDeptId", dafyDeptId);
        	model.put("dafyDeptName", dafyDeptName);
        	model.put("dafyMobile", mobile);
        }
        model.put("isDafyUser", isDafyUserString);
        model.put("userList", userList);
        model.put("count", 0);
    	
    	
        return "/financial/customer_query_index";
    }
    
    
    
    @RequestMapping("/financialIntention/customerQueryData")
    public String customerQueryData(CustomerQueryVO req,HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
	    //检查查询参数
		
    	CookieManager cookie = new CookieManager(request);
        String isManager = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISMANAGER.getName(), true);
        String mobileCookie = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_EMAIL.getName(), true);
        String dafyDeptCode = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTCODE.getName(), true);
        
    	
    	String departmentName = request.getParameter("departmentName");
    	String employeeName = request.getParameter("employeeName");
    	String userName = request.getParameter("userName");
    	String mobile = request.getParameter("mobile");
    	String idcard = request.getParameter("idcard");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		
		String selectDeptId = request.getParameter("selectDeptId");
		String deptName = request.getParameter("deptName");
		String managerName = request.getParameter("managerName");
		
		String deptStatus = request.getParameter("deptStatus");
		String userStatus = request.getParameter("userStatus");
		String isDafyUser = request.getParameter("isDafyUser");
		
		
    	String dafyUserId = request.getParameter("dafyUserId");
    	String dafyDeptId = request.getParameter("dafyDeptId");
    	String dafyUserName = request.getParameter("dafyUserName");
    	String dafyDeptName = request.getParameter("dafyDeptName");
    	
		model.put("selectDeptId", selectDeptId);
		model.put("deptName", deptName);
		model.put("managerName", managerName);
		model.put("dafyDeptName", dafyDeptName);
		
		model.put("deptStatus", deptStatus);
		model.put("userStatus", userStatus);
		model.put("isDafyUser", isDafyUser);
		model.put("dafyUserName", dafyUserName);
      	model.put("dafyUserId", dafyUserId);
    	model.put("dafyDeptId", dafyDeptId);
		
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
		}
    	

		if("yes".equals(isDafyUser)) {
			if (managerName !=null && !"".equals(managerName)&& !"-1".equals(managerName)) {
		        List<CustomerQueryVO> dataGrid = new ArrayList<CustomerQueryVO>();
		        Integer count = 0;
		        dataGrid = financialIntentionService.customerQueryIndex(managerName==null||"".equals(managerName)?null:Long.valueOf(managerName), selectDeptId==null||"".equals(selectDeptId)?null:Long.valueOf(selectDeptId), userName, mobile, idcard, req.getIsBindBank(),Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		        count = financialIntentionService.customerQueryCount(managerName==null||"".equals(managerName)?null:Long.valueOf(managerName), selectDeptId==null||"".equals(selectDeptId)?null:Long.valueOf(selectDeptId), userName, mobile, idcard,req.getIsBindBank());
		        if ("disable".equals(userStatus)) {
		        	List<Tbemployee> userList = new ArrayList<Tbemployee>();
		        	Tbemployee user = new Tbemployee();
        			user.setLuserid(Long.valueOf(dafyUserId));
        			user.setStrname(dafyUserName);
        			user.setStrmobile(mobileCookie);
        			userList.add(user);
        			model.put("userList", userList);
				}else {
			        if (selectDeptId!=null && !"".equals(selectDeptId)) {
			        	
			        	List<Tbdatapermission> missionList = financialIntentionService.selectUserMission(Long.valueOf(dafyUserId));
			        	List<Tbemployee> userList = new ArrayList<Tbemployee>();
	    				boolean flag = true;
	    				for(Tbdatapermission mission : missionList) {
	    		    		if(mission.getStrdeptcode().equals(dafyDeptCode)) {
	    		    			flag = false;
	    		    			break;
	    		    		}
	    		    	}
	    				//没有特殊权限，非部门主管同时选择的部门又是自己所属部门，则只能查看自己信息
	    				if(flag && selectDeptId.equals(dafyDeptId) && "0".equals(isManager)) {
	    					Tbemployee user = new Tbemployee();
	    	    			user.setLuserid(Long.valueOf(dafyUserId));
	    	    			user.setStrmobile(mobileCookie);
	    	    			user.setStrname(dafyUserName);
	    	    			userList.add(user);
	    				}
	    				else {
	    					//查询部门下的客户经理
	            			userList = financialIntentionService.selectEmployeeByDeptId(Long.valueOf(selectDeptId));
	    				}
			        	
			        	model.put("userList", userList);
					}
				}

		        model.put("dataGrid", dataGrid);
		        model.put("count", count);
			}else {
				if (selectDeptId !=null && !"".equals(selectDeptId)) {
			        List<CustomerQueryVO> dataGrid = new ArrayList<CustomerQueryVO>();
			        Integer count = 0;
			        dataGrid = financialIntentionService.customerQueryIndex(managerName==null||"".equals(managerName)||"-1".equals(managerName)?null:Long.valueOf(managerName), selectDeptId==null||"".equals(selectDeptId)?null:Long.valueOf(selectDeptId), userName, mobile, idcard,req.getIsBindBank(), Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
			        count = financialIntentionService.customerQueryCount(managerName==null||"".equals(managerName)||"-1".equals(managerName)?null:Long.valueOf(managerName), selectDeptId==null||"".equals(selectDeptId)?null:Long.valueOf(selectDeptId), userName, mobile, idcard,req.getIsBindBank());
			        if (selectDeptId!=null && !"".equals(selectDeptId)) {
			        	List<Tbdatapermission> missionList = financialIntentionService.selectUserMission(Long.valueOf(dafyUserId));
			        	List<Tbemployee> userList = new ArrayList<Tbemployee>();
	    				boolean flag = true;
	    				for(Tbdatapermission mission : missionList) {
	    		    		if(mission.getStrdeptcode().equals(dafyDeptCode)) {
	    		    			flag = false;
	    		    			break;
	    		    		}
	    		    	}
	    				//没有特殊权限，非部门主管同时选择的部门又是自己所属部门，则只能查看自己信息
	    				if(flag && selectDeptId.equals(dafyDeptId) && "0".equals(isManager)) {
	    					Tbemployee user = new Tbemployee();
	    	    			user.setLuserid(Long.valueOf(dafyUserId));
	    	    			user.setStrmobile(mobile);
	    	    			user.setStrname(dafyUserName);
	    	    			userList.add(user);
	    				}
	    				else {
	    					//查询部门下的客户经理
	            			userList = financialIntentionService.selectEmployeeByDeptId(Long.valueOf(selectDeptId));
	    				}
			        	
			        	model.put("userList", userList);
					}
			        model.put("dataGrid", dataGrid);
			        model.put("count", count);
				}else {
					

			        
			        List<Long> employeeList = financialIntentionService.selectDeptListByManagerId(Long.valueOf(dafyUserId), Long.valueOf(dafyDeptId), isManager);

			        if (CollectionUtils.isEmpty(employeeList)) {
			        	employeeList =null;
					}
			        
					List<CustomerQueryVO> dataGrid = new ArrayList<CustomerQueryVO>();
			        Integer count = 0;
			        if ("1".equals(isManager)) {
			        	 dataGrid = financialIntentionService.customerQueryByEmployee(userName, mobile, idcard,employeeList,null,req.getIsBindBank(), Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
					     count = financialIntentionService.customerQueryByEmployeeCount(userName, mobile, idcard,employeeList,null,req.getIsBindBank());
					}else {
			        	 dataGrid = financialIntentionService.customerQueryByEmployee(userName, mobile, idcard,employeeList,Long.valueOf(dafyUserId),req.getIsBindBank(), Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
					     count = financialIntentionService.customerQueryByEmployeeCount(userName, mobile, idcard,employeeList,Long.valueOf(dafyUserId),req.getIsBindBank());
					}
			       
			        model.put("dataGrid", dataGrid);
			        model.put("count", count);
					
				}
			}
		}
        
        //将数据返回给页面
        model.put("req", req);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
        return "/financial/customer_query_index";
    }
    
    
    
    
	private Map<String, List<Object>> deptTitles(){
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("理财人ID");
		titles.add("理财人姓名");
		titles.add("手机号");
		titles.add("性别");
		titles.add("年龄");
		titles.add("身份证编号");
		titles.add("客户经理");
		titles.add("营业部");
		titles.add("绑卡状态");
		titles.add("注册日期");
		titles.add("首次购买日期");
		titleMap.put("title", titles);
		return titleMap;
	}
    
    
	@RequestMapping("/financialIntention/customerQueryExport")
	public void customerQueryExport(CustomerQueryVO req,HttpServletRequest request, HttpServletResponse response) {
		
		//需要导出的数据
		String dafyDeptCode = request.getParameter("dafyDeptCode");
    	
    	String departmentName = request.getParameter("departmentName");
    	String employeeName = request.getParameter("employeeName");
    	String userName = request.getParameter("userName");
    	String mobile = request.getParameter("mobile");
    	String idcard = request.getParameter("idcard");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		
		String selectDeptId = request.getParameter("selectDeptId");
		String deptName = request.getParameter("deptName");
		String managerName = request.getParameter("managerName");
		
		String deptStatus = request.getParameter("deptStatus");
		String userStatus = request.getParameter("userStatus");
		String isDafyUser = request.getParameter("isDafyUser");
		
		
    	String dafyUserId = request.getParameter("dafyUserId");
    	String dafyDeptId = request.getParameter("dafyDeptId");
    	String dafyUserName = request.getParameter("dafyUserName");
    	
    	
    	
	    	List<CustomerQueryVO> dataGrid = new ArrayList<CustomerQueryVO>();
	    	if("yes".equals(isDafyUser)) {	
			if (managerName !=null && !"".equals(managerName)&& !"-1".equals(managerName)) {
		        Integer count = 0;
		        count = financialIntentionService.customerQueryCount(managerName==null||"".equals(managerName)?null:Long.valueOf(managerName), selectDeptId==null||"".equals(selectDeptId)?null:Long.valueOf(selectDeptId), userName, mobile, idcard,req.getIsBindBank());
		        dataGrid = financialIntentionService.customerQueryIndex(managerName==null||"".equals(managerName)?null:Long.valueOf(managerName), selectDeptId==null||"".equals(selectDeptId)?null:Long.valueOf(selectDeptId), userName, mobile, idcard,req.getIsBindBank(), 1, count);
		    }else {
				if (selectDeptId !=null && !"".equals(selectDeptId)) {
			        Integer count = 0;
			        count = financialIntentionService.customerQueryCount(managerName==null||"".equals(managerName)||"-1".equals(managerName)?null:Long.valueOf(managerName), selectDeptId==null||"".equals(selectDeptId)?null:Long.valueOf(selectDeptId), userName, mobile, idcard,req.getIsBindBank());
			        dataGrid = financialIntentionService.customerQueryIndex(managerName==null||"".equals(managerName)||"-1".equals(managerName)?null:Long.valueOf(managerName), selectDeptId==null||"".equals(selectDeptId)?null:Long.valueOf(selectDeptId), userName, mobile, idcard,req.getIsBindBank(), 1, count);
			    }else {
					
					
			    	CookieManager cookie = new CookieManager(request);
			        String isManager = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISMANAGER.getName(), true);
			        
			       // dataGrid = financialIntentionService.customerQueryByEmployee(userName, mobile, idcard,employeeList, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
			        //count = financialIntentionService.customerQueryByEmployeeCount(userName, mobile, idcard,employeeList);
			        List<Long> employeeList = financialIntentionService.selectDeptListByManagerId(Long.valueOf(dafyUserId), Long.valueOf(dafyDeptId), isManager);
		        	
			        
			        if (CollectionUtils.isEmpty(employeeList)) {
			        	employeeList =null;
					}
			        Integer count = 0;
			        if ("1".equals(isManager)) {
			        	  count = financialIntentionService.customerQueryByEmployeeCount(userName, mobile, idcard,employeeList,null,req.getIsBindBank());
			        	 dataGrid = financialIntentionService.customerQueryByEmployee(userName, mobile, idcard,employeeList,null,req.getIsBindBank(), Integer.valueOf(1), Integer.valueOf(count));
					   
					}else {
						 count = financialIntentionService.customerQueryByEmployeeCount(userName, mobile, idcard,employeeList,Long.valueOf(dafyUserId),req.getIsBindBank());
			        	 dataGrid = financialIntentionService.customerQueryByEmployee(userName, mobile, idcard,employeeList,Long.valueOf(dafyUserId),req.getIsBindBank(), Integer.valueOf(1), Integer.valueOf(count));
					    
					}
				}
			}
    	}
        
        List<HashMap<String,Object>> datas =BeanUtils.classToArrayList(dataGrid);
        
		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
		
		list.add(deptTitles());
		if(datas != null && !datas.isEmpty()) {
			int i = 0;
			for (HashMap<String,Object> data : datas) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(data.get("userId"));
				obj.add(data.get("userName"));
				obj.add(data.get("mobile"));
				obj.add(data.get("sex"));
				obj.add(data.get("age"));
				obj.add(data.get("idCard"));
				obj.add(data.get("customerName"));
				obj.add(data.get("dafyDeptName"));
				if ("1".equals(data.get("isBindBank"))) {
					obj.add("已绑卡");
				}else if ("2".equals(data.get("isBindBank"))) {
					obj.add("未绑卡");
				}else {
					obj.add("");
				}
				
				obj.add(DateUtil.format((Date)data.get("registerTime")));
				obj.add(DateUtil.format((Date)data.get("firstBuyTime")));
				datasMap.put("user"+(++i), obj);
				list.add(datasMap);
			}
		}
		
		try {
			ExportUtil.exportExcel(response, request, "客户查询", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	 
	}
    
    /**
     * 
     * 根据名称模糊查询营业部名称
     * @param param  查询参数
     * @return
     */
    @ResponseBody
    @RequestMapping("/financialIntention/queryDafyDept")
    public Map<String, Object> queryDafyDept(String param) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Tbdepartment> vos = financialIntentionService.selectDeptListByParamNoRoles(param);
        result.put("departments", vos);
        return result;
    }
    
    /**
     * 
     * 根据营业部ID查询客户经理人列表
     * @param departmentId  部门ID
     * @return
     */
    @ResponseBody
    @RequestMapping("/financialIntention/queryDafyEmployee")
    public Map<String, Object> queryDafyEmployee(String departmentId) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Tbemployee> vos = financialIntentionService.selectEmployeeByDeptId(Long.valueOf(departmentId));
        result.put("employees", vos);
        return result;
    }
    
    
    
    /**
     * 查询归属变更记录
     * @param req
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/financialIntention/queryOwnershipChangeRecord")
    public String queryOwnershipChangeRecord(Integer userId, Map<String, Object> model,
                                   HttpServletRequest request, HttpServletResponse response) {

		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
		}
		
        Integer count = financialIntentionService.queryOwnershipChangeRecordCount(userId);
        if (count != null && count > 0) {
            List<BsDafyFinanceChangeRecordVO> financeChangeRecord = financialIntentionService.queryOwnershipChangeRecord(userId,Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
            ArrayList<HashMap<String, Object>> dataGrid = BeanUtils.classToArrayList(financeChangeRecord);
            model.put("dataGrid", dataGrid);
        }
        model.put("userId", userId);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);
        model.put("totalRows", count);
        return "/financial/ownership_change_record";
    }
    
    
    /**
     * 查看手机号码详情
     * @param req
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/financialIntention/queryMobileByUserId")
    public String queryMobileByUserId(Integer userId, Map<String, Object> model,
                                   HttpServletRequest request, HttpServletResponse response) {
    	if (userId != null) {
    		BsUser user = userService.findUserById(userId);
    		model.put("mobile", user.getMobile());
    		model.put("userName", user.getUserName());
		}
        return "/financial/customer_query_mobile_detail";
    }

    /**
	 * 生成二维码（达飞客户经理）
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("financialIntention/matrixImage")
	public String matrixImage(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String mobile = request.getParameter("id");
		String link = GlobEnvUtil.get("wechat.server.web")+"/micro2.0/user/register_first_index?recommendId="+mobile;
		model.put("link", link);
		return "/financial/dafy_customer_matrix";
	}
	
	/**
     * 客户归属变更--页面
     * @param request
     * @param response
     * @param model
     * @param pageInfo
     * @return
     */
    @RequestMapping("/financialIntention/ownershipChange")
    public String ownershipChange(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model, PageInfoObject pageInfo) {
        // 查询条件准备
        OwnershipVO req = new OwnershipVO();
        String userName = request.getParameter("userName");
        String mobile = request.getParameter("mobile");
        String idCard = request.getParameter("idCard");
        String deptName = request.getParameter("deptName");
        String dafyUserId = request.getParameter("dafyUserId");
        String lid = request.getParameter("lid");
        String userNameAccurate = request.getParameter("userNameAccurate");
        String left = request.getParameter("left");
        String choosedUserList = request.getParameter("choosedUserList");
        String leftCount = request.getParameter("leftCount");
        String rightCount = request.getParameter("rightCount");
        req.setUserName(StringUtil.isNotBlank(userName) ? userName : null);
        req.setMobile(StringUtil.isNotBlank(mobile) ? mobile : null);
        req.setIdCard(StringUtil.isNotBlank(idCard) ? idCard : null);
        req.setDeptName(StringUtil.isNotBlank(deptName) ? deptName : null);
        req.setDafyUserId(StringUtil.isNotBlank(dafyUserId) ? Long.valueOf(dafyUserId) : null);
        req.setDeptId(StringUtil.isNotBlank(lid) ? Long.valueOf(lid) : null);
        req.setUserNameAccurate(StringUtil.isNotBlank(userNameAccurate) ? userNameAccurate : null);
        req.setPageNum(pageInfo.getPageNum());
        req.setNumPerPage(Constants.MANAGE_1000_NUMPERPAGE);
        
     
        // 查询条件准备 
        if (null != req.getDeptId()) {
        	   CookieManager cookie = new CookieManager(request);
               String dafyDeptCode = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTCODE.getName(), true);
               String dafyUserName = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERNME.getName(), true);
               String dafyDeptName = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTNAME.getName(), true);
               String isManager = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISMANAGER.getName(), true);
               String dafyDeptId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTID.getName(), true);
               String dafyMobile = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_EMAIL.getName(), true);
               String dafyUserIdCookie = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERID.getName(), true);
               
            List<Tbdatapermission> missionList = financialIntentionService.selectUserMission(Long.valueOf(dafyUserIdCookie));
        	List<Tbemployee> userList = new ArrayList<Tbemployee>();
        	boolean flag = true;
			for(Tbdatapermission mission : missionList) {
	    		if(mission.getStrdeptcode().equals(dafyDeptCode)) {
	    			flag = false;
	    			break;
	    		}
	    	}
			//没有特殊权限，非部门主管同时选择的部门又是自己所属部门，则只能查看自己信息
			if(flag && req.getDeptId().equals(Long.parseLong(dafyDeptId)) && "0".equals(isManager)) {
				Tbemployee user = new Tbemployee();
    			user.setLuserid(Long.valueOf(dafyUserIdCookie));
    			user.setStrmobile(dafyMobile);
    			user.setStrname(dafyUserName);
    			userList.add(user);
			}
			else {
				//查询部门下的客户经理
    			userList = financialIntentionService.selectAllEmployeeByDeptId(Long.valueOf(req.getDeptId()));
			}
		
        	
           // List<Tbemployee> employees = financialIntentionService.selectEmployeeByDeptId(req.getDeptId());
            model.put("employees", userList);
        }
        model.put("req", req);
        model.put("left", left);
        model.put("choosedUserList", choosedUserList);
        model.put("rightCount", StringUtil.isBlank(rightCount) ? "0" : rightCount);
        model.put("leftCount", StringUtil.isBlank(leftCount) ? "0" : leftCount);
//        int removeCount = 0;
        if(!(StringUtil.isBlank(userName) && StringUtil.isBlank(mobile) && StringUtil.isBlank(idCard)
                && StringUtil.isBlank(deptName) && (StringUtil.isBlank(dafyUserId) || "-1".equals(dafyUserId)) && StringUtil.isBlank(lid))) {
            CookieManager cookie = new CookieManager(request);
            String isManager = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISMANAGER.getName(), true);
            String dafyDeptId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTID.getName(), true);
            String cookieDafyUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERID.getName(), true);
            // 用户无此权限
            if(StringUtil.isBlank(dafyDeptId) || StringUtil.isBlank(cookieDafyUserId)) {
                return "/financial/ownership_change";
            }
            req.setCookieUserId(Long.valueOf(cookieDafyUserId));
            
/*            List<Long> deptList = financialIntentionService.selectDeptListByManagerId(Long.valueOf(cookieDafyUserId), Long.valueOf(dafyDeptId), isManager);
            if (CollectionUtils.isEmpty(deptList)) {
                return "/financial/ownership_change";
            }
            req.setDeptList(deptList);*/
            
            
            List<Long> deptIds = new ArrayList<Long>();
        	List<Long> copyDeptIds = new ArrayList<Long>();
        	String isOrDafyUserId = null;

			Map<Long,Tbdepartment> deptMap = new HashMap<Long,Tbdepartment>();
			List<Tbdepartment> lowDept = financialIntentionService.selectLowLevelDept(Long.valueOf(cookieDafyUserId));
			for(Tbdepartment dept : lowDept) {
				deptMap.put(dept.getLid(), dept);
			}
			
			//1为部门主管
			if(Integer.valueOf(isManager) == 1) {
				Tbdepartment myDept = financialIntentionService.selectTbdepartmentByKey(Long.valueOf(dafyDeptId));
				Integer level = myDept.getNcurrentlevel();
				String sql = "select * from tbdepartment where strDeptCode"+level+"='"+myDept.getStrdeptcode()+"'";
				List<Tbdepartment> nextDeptList = financialIntentionService.selectTbdepartmentSQL(sql);
				for(Tbdepartment dept : nextDeptList) {
					deptMap.put(dept.getLid(), dept);
				}
			}
			else {
				if(!deptMap.containsKey(Long.valueOf(dafyDeptId))) {
					isOrDafyUserId = dafyUserId;
				}
			}
			deptIds = new ArrayList<Long>(deptMap.keySet());
    		copyDeptIds.addAll(deptIds);
            
    		req.setDeptList(deptIds);
            List<OwnershipVO> grid = financialIntentionService.queryOwnershipGrid(req);
            Integer count = financialIntentionService.countOwnershipGrid(req);
            if(StringUtil.isNotBlank(choosedUserList)) {
                JSONArray jsonArray = JSONArray.fromObject(choosedUserList);
                Object[] objList = jsonArray.toArray();
                for (Object object : objList) {
                    if(object instanceof JSONObject) {
                        JSONObject jsonObject = (JSONObject) object;
                        String idKey = (String)jsonObject.get("id");
                        for (Iterator<OwnershipVO> iterator = grid.iterator(); iterator.hasNext();) {
                            OwnershipVO ownershipVO = (OwnershipVO) iterator.next();
                            if(ownershipVO.getId().equals(Integer.parseInt(idKey))) {
                                iterator.remove();
//                                removeCount ++;
                            }
                        }
                    }
                }
            }
            req.setTotalRows(count);
            model.put("dataGrid", grid);
            model.put("count", count);
            model.put("leftCount", grid.size());
//            if(StringUtil.isBlank(rightCount)) {
//                model.put("leftCount", count);
//            } else {
////                model.put("leftCount", count - rightCountInt);
//                model.put("leftCount", grid.size());
////                model.put("leftCount", count - removeCount);
//            }
        }
        return "/financial/ownership_change";
    }
    
    /**
     * 客户归属变更--操作
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/financialIntention/changeOwnershipOption")
    public Map<Object, Object> changeOwnershipOption(HttpServletRequest request, HttpServletResponse response) {
        int count = 0;
        try {
            CookieManager cookieManager = new CookieManager(request);
            String userId = cookieManager.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
            
            String dafyUserId = request.getParameter("dafyUserIdChange");
            String choosedUserList = request.getParameter("choosedUserList");
            List<Integer> idList = new ArrayList<Integer>();
            if(StringUtil.isNotBlank(choosedUserList)) {
                JSONArray jsonArray = JSONArray.fromObject(choosedUserList);
                Object[] objList = jsonArray.toArray();
                count = objList.length;
                for (Object object : objList) {
                    if(object instanceof JSONObject) {
                        JSONObject jsonObject = (JSONObject) object;
                        String idKey = (String) jsonObject.get("id");
                        idList.add(Integer.parseInt(idKey));
                    }
                }
            }
            financialIntentionService.changeOwnership(Integer.parseInt(dafyUserId), idList, Integer.parseInt(userId));
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDWZAjax.fail("操作失败："+e.getMessage());
        }
        return ReturnDWZAjax.success("操作成功！变更记录（"+count+"）条");
    }
    
    
    //=========================财务结算-理财S=====================================
    
    //普通产品-normal，分期产品-zan
    @RequestMapping("/financialIntention/financialSettlementIndex")
    public String financialSettlementIndex(FinancialSettlementVO req, HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
    	//获取产品类型
        String productType = request.getParameter("productType")==null?Constants.PRODUCT_TYPE_NORMAL:request.getParameter("productType");
        
    	
    	/**
    	 * 一、需要进行财务管理人员的权限进行信息查询，判断可以查看数据的权限
    	 * 1、财务人员具有特殊权限--进入到查询界面营业部和客户经理框都可以编辑
    	 * 2、财务人员并不具有特殊权限，但是自己本身是部门主管--进入到查询界面营业部不可编辑，客户经理框可以下拉选择
    	 * 3、财务人员并不具有特殊权限并且自己本身不是部门主管--进入到查询界面营业部不可编辑，客户经理框也不可以编辑
    	 * 
    	 * 二、检查参数是否带查询参数，如果带查询参数则进行数据查询（isSelect == 'y'）
    	 * 1、如果参数里面已指定营业部但是未指定客户经理则根据营业部进行查询
    	 * 2、如果参数里面已指定营业部并且还指定客户经理则根据客户经理进行查询
    	 * 3、如果参数里面未指定营业部也没有指定客户经理则根据财务管理人员所具有的权限查询所有数据
    	 */
    	
    	CookieManager cookie = new CookieManager(request);
        String mobile = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_EMAIL.getName(), true);
        String dafyUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERID.getName(), true);
        String dafyDeptId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTID.getName(), true);
        String dafyUserName = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERNME.getName(), true);
        String dafyDeptName = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTNAME.getName(), true);
        String isManager = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISMANAGER.getName(), true);
        String isDafyUserString = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISDAFYUSER.getName(), true);
        String dafyDeptCode = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTCODE.getName(), true);
        if("yes".equals(isDafyUserString)) {
        	String selectDeptId = request.getParameter("selectDeptId");
            List<Tbemployee> userList = new ArrayList<Tbemployee>();
        	//查询达飞用户权限
        	List<Tbdatapermission> missionList = financialIntentionService.selectUserMission(Long.valueOf(dafyUserId));
        	//普通客户经理
        	if(CollectionUtils.isEmpty(missionList) && "0".equals(isManager)) {
        		model.put("deptStatus", "disable");
    			model.put("userStatus", "disable");
    			Tbemployee user = new Tbemployee();
    			user.setLuserid(Long.valueOf(dafyUserId));
    			user.setStrmobile(mobile);
    			user.setStrname(dafyUserName);
    			userList.add(user);
        	}
        	else {
        		model.put("deptStatus", "able");
    			model.put("userStatus", "able");
    			if(StringUtil.isNotBlank(selectDeptId)) {
        			//查询部门下的客户经理
    				boolean flag = true;
    				for(Tbdatapermission mission : missionList) {
    		    		if(mission.getStrdeptcode().equals(dafyDeptCode)) {
    		    			flag = false;
    		    			break;
    		    		}
    		    	}
    				//没有特殊权限，非部门主管同时选择的部门又是自己所属部门，则只能查看自己信息
    				if(flag && selectDeptId.equals(dafyDeptId) && "0".equals(isManager)) {
    					Tbemployee user = new Tbemployee();
    	    			user.setLuserid(Long.valueOf(dafyUserId));
    	    			user.setStrmobile(mobile);
    	    			user.setStrname(dafyUserName);
    	    			userList.add(user);
    				}
    				else {
    					//查询部门下的客户经理
            			userList = financialIntentionService.selectEmployeeByDeptId(Long.valueOf(selectDeptId));
    				}
        		}
        	}
        	
        	model.put("dafyUserId", dafyUserId);
        	model.put("dafyUserName", dafyUserName);
        	model.put("dafyDeptId", dafyDeptId);
        	model.put("dafyDeptName", dafyDeptName);
        	
        	model.put("isDafyUser", isDafyUserString);
            model.put("userList", userList);
            
            String isSelect = request.getParameter("isSelect");
            if("y".equals(isSelect)) {
            	String isOrDafyUserId = null;
            	List<Long> deptIds = new ArrayList<Long>();
            	List<Long> copyDeptIds = new ArrayList<Long>();
            	//已指定营业部
            	if(StringUtil.isNotBlank(selectDeptId)) {
            		deptIds.add(Long.valueOf(selectDeptId));
                }
            	//未指定营业部
        		if(CollectionUtils.isEmpty(deptIds)) {
        			Map<Long,Tbdepartment> deptMap = new HashMap<Long,Tbdepartment>();
        			List<Tbdepartment> lowDept = financialIntentionService.selectLowLevelDept(Long.valueOf(dafyUserId));
        			for(Tbdepartment dept : lowDept) {
        				deptMap.put(dept.getLid(), dept);
        			}
        			
        			//1为部门主管
        			if(Integer.valueOf(isManager) == 1) {
        				Tbdepartment myDept = financialIntentionService.selectTbdepartmentByKey(Long.valueOf(dafyDeptId));
        				Integer level = myDept.getNcurrentlevel();
        				String sql = "select * from tbdepartment where strDeptCode"+level+"='"+myDept.getStrdeptcode()+"'";
        				List<Tbdepartment> deptList = financialIntentionService.selectTbdepartmentSQL(sql);
        				for(Tbdepartment dept : deptList) {
        					deptMap.put(dept.getLid(), dept);
        				}
        			}
	    			else {
	    				if(!deptMap.containsKey(Long.valueOf(dafyDeptId))) {
	    					isOrDafyUserId = dafyUserId;
	    				}
	    			}
        			deptIds = new ArrayList<Long>(deptMap.keySet());
        		}
        		copyDeptIds.addAll(deptIds);
            	
            	List<FinancialSettlementVO> dataGrid = new ArrayList<FinancialSettlementVO>();
                Integer count = 0;
                if(StringUtil.isNotBlank(dafyUserId) && StringUtil.isNotBlank(dafyDeptId)) {
                	DecimalFormat formatInt = new DecimalFormat("0");
                	if (StringUtil.isNotBlank(req.getAmountStart())) {
                		String amount = formatInt.format(Double.parseDouble(req.getAmountStart()));
                		req.setAmountStart(amount);
					}
                	if (StringUtil.isNotBlank(req.getAmountEnd())) {
                		String amount = formatInt.format(Double.parseDouble(req.getAmountEnd()));
                		req.setAmountEnd(amount);
					}
                	
                	FinancialSettlementVO copyReq = new FinancialSettlementVO();
                	org.springframework.beans.BeanUtils.copyProperties(req, copyReq);
                	
                	
                	
                	
                	FinancialSettlementVO copyReq1 = new FinancialSettlementVO();
                	org.springframework.beans.BeanUtils.copyProperties(req, copyReq1);
                	
            		String startSuccessTime = req.getTimeStart();
            		String endSuccessTime = req.getTimeEnd();
            		
            		
            		if (!StringUtil.isNotBlank(req.getTimeStart()) && StringUtil.isNotBlank(req.getTimeEnd()) ) {
            			Calendar cal = Calendar.getInstance(); 
            			cal.setTime(DateUtil.parseDate(req.getTimeEnd()));
            			cal.set(GregorianCalendar.DAY_OF_MONTH, 1); 
                        Date beginTime=cal.getTime();
                        startSuccessTime = DateUtil.formatYYYYMMDD(beginTime);
            		}
            		
            		if (StringUtil.isNotBlank(req.getTimeStart()) && !StringUtil.isNotBlank(req.getTimeEnd()) ) {
            			endSuccessTime = DateUtil.formatYYYYMMDD(new Date());
            		}
            		
            		
            		
            		if (StringUtil.isNotBlank(startSuccessTime) && StringUtil.isNotBlank(endSuccessTime)) {
            			req.setTimeStart(startSuccessTime);
            			req.setTimeEnd(endSuccessTime);
            			
            			if(StringUtil.isNotBlank(startSuccessTime)) {
            				startSuccessTime += " 00:00:00";
            			}
            			if(StringUtil.isNotBlank(endSuccessTime)) {
            				endSuccessTime += " 23:59:59";
            			}
            			copyReq.setTimeStart(startSuccessTime);
            			copyReq.setTimeEnd(endSuccessTime);
            			copyReq1.setTimeStart(startSuccessTime);
            			copyReq1.setTimeEnd(endSuccessTime);
            		}else {
            			copyReq.setTimeStart(null);
            			copyReq.setTimeEnd(null);
            			copyReq1.setTimeStart(null);
            			copyReq1.setTimeEnd(null);
            		}
            		
                    dataGrid = financialIntentionService.queryFinancialSettlementGrid(copyReq1, deptIds,isOrDafyUserId,productType);
                    count = financialIntentionService.countFinancialSettlement(copyReq, copyDeptIds,isOrDafyUserId,productType);
                }
                
                //将数据返回给页面
                model.put("selectDeptId", selectDeptId);
                model.put("dataGrid", dataGrid);
                req.setTotalRows(count);
            }
        }
        model.put("req", req);
        model.put("productType", productType);
        return "/financial/financial_settlement_index";
    }
    
    @RequestMapping("/financialIntention/financialSettlementExport")
    public void financialSettlementExport(FinancialSettlementVO req, HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
    	//获取产品类型
        String productType = request.getParameter("productType")==null?Constants.PRODUCT_TYPE_NORMAL:request.getParameter("productType");
    	
    	CookieManager cookie = new CookieManager(request);
    	String dafyUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERID.getName(), true);
    	String dafyDeptId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTID.getName(), true);
    	String isManager = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISMANAGER.getName(), true);
    	String isDafyUserString = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISDAFYUSER.getName(), true);
    	if("yes".equals(isDafyUserString)) {
    		String isOrDafyUserId = null;
    		String selectDeptId = request.getParameter("selectDeptId");
        	List<Long> deptIds = new ArrayList<Long>();
        	//已指定营业部
        	if(StringUtil.isNotBlank(selectDeptId)) {
        		deptIds.add(Long.valueOf(selectDeptId));
            }
        	//未指定营业部
    		if(CollectionUtils.isEmpty(deptIds)) {
    			Map<Long,Tbdepartment> deptMap = new HashMap<Long,Tbdepartment>();
    			List<Tbdepartment> lowDept = financialIntentionService.selectLowLevelDept(Long.valueOf(dafyUserId));
    			for(Tbdepartment dept : lowDept) {
    				deptMap.put(dept.getLid(), dept);
    			}
    			
    			//1为部门主管
    			if(Integer.valueOf(isManager) == 1) {
    				Tbdepartment myDept = financialIntentionService.selectTbdepartmentByKey(Long.valueOf(dafyDeptId));
    				Integer level = myDept.getNcurrentlevel();
    				String sql = "select * from tbdepartment where strDeptCode"+level+"='"+myDept.getStrdeptcode()+"'";
    				List<Tbdepartment> deptList = financialIntentionService.selectTbdepartmentSQL(sql);
    				for(Tbdepartment dept : deptList) {
    					deptMap.put(dept.getLid(), dept);
    				}
    			}
    			else {
    				if(!deptMap.containsKey(Long.valueOf(dafyDeptId))) {
    					isOrDafyUserId = dafyUserId;
    				}
    			}
    			deptIds = new ArrayList<Long>(deptMap.keySet());
    		}
    		
    		List<Long> copyDeptIds = new ArrayList<Long>();
    		copyDeptIds.addAll(deptIds);
    		
        	DecimalFormat formatInt = new DecimalFormat("0");
        	if (StringUtil.isNotBlank(req.getAmountStart())) {
        		String amount = formatInt.format(Double.parseDouble(req.getAmountStart()));
        		req.setAmountStart(amount);
			}
        	if (StringUtil.isNotBlank(req.getAmountEnd())) {
        		String amount = formatInt.format(Double.parseDouble(req.getAmountEnd()));
        		req.setAmountEnd(amount);
			}
    		
    		FinancialSettlementVO copyReq = new FinancialSettlementVO();
    		org.springframework.beans.BeanUtils.copyProperties(req, copyReq);
    		
    		
        	
        	FinancialSettlementVO copyReq1 = new FinancialSettlementVO();
        	org.springframework.beans.BeanUtils.copyProperties(req, copyReq1);
        	
    		String startSuccessTime = req.getTimeStart();
    		String endSuccessTime = req.getTimeEnd();
    		
    		
    		if (!StringUtil.isNotBlank(req.getTimeStart()) && StringUtil.isNotBlank(req.getTimeEnd()) ) {
    			Calendar cal = Calendar.getInstance(); 
    			cal.setTime(DateUtil.parseDate(req.getTimeEnd()));
    			cal.set(GregorianCalendar.DAY_OF_MONTH, 1); 
                Date beginTime=cal.getTime();
                startSuccessTime = DateUtil.formatYYYYMMDD(beginTime);
    		}
    		
    		if (StringUtil.isNotBlank(req.getTimeStart()) && !StringUtil.isNotBlank(req.getTimeEnd()) ) {
    			endSuccessTime = DateUtil.formatYYYYMMDD(new Date());
    		}
    		
    		
    		
    		if (StringUtil.isNotBlank(startSuccessTime) && StringUtil.isNotBlank(endSuccessTime)) {
    			req.setTimeStart(startSuccessTime);
    			req.setTimeEnd(endSuccessTime);
    			
    			if(StringUtil.isNotBlank(startSuccessTime)) {
    				startSuccessTime += " 00:00:00";
    			}
    			if(StringUtil.isNotBlank(endSuccessTime)) {
    				endSuccessTime += " 23:59:59";
    			}
    			copyReq.setTimeStart(startSuccessTime);
    			copyReq.setTimeEnd(endSuccessTime);
    			copyReq1.setTimeStart(startSuccessTime);
    			copyReq1.setTimeEnd(endSuccessTime);
    		}else {
    			copyReq.setTimeStart(null);
    			copyReq.setTimeEnd(null);
    			copyReq1.setTimeStart(null);
    			copyReq1.setTimeEnd(null);
    		}
    		
    		Integer count = financialIntentionService.countFinancialSettlement(copyReq, copyDeptIds,isOrDafyUserId,productType);
    		copyReq1.setNumPerPage(count);
    		copyReq1.setPageNum(0);
        	List<FinancialSettlementVO> dataGrid = financialIntentionService.queryFinancialSettlementGrid(copyReq1, deptIds,isOrDafyUserId,productType);
        	List<HashMap<String,Object>> datas = BeanUtils.classToArrayList(dataGrid);
    		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
    		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
    		List<Object> titles = new ArrayList<Object>();
    		
    		titles.add("划扣成功日期");
    		titles.add("计息日期");
    		titles.add("出借人");
    		titles.add("出借人手机");
    		titles.add("出借人身份证");
    		titles.add("产品");
    		titles.add("合同编号");
    		titles.add("出借金额（元）");
    		titles.add("实际出借金额（元）");
    		titles.add("投资状态");
    		titles.add("期限（天）");
    		titles.add("到期时间");
    		titles.add("利率（%）");
    		titles.add("营业员");
    		titles.add("营业部名称");
    		titles.add("营业部编号");
    		titleMap.put("title", titles);
    		list.add(titleMap);
    		if(!CollectionUtils.isEmpty(datas)) {
    			int i = 0;
    			for (HashMap<String,Object> data : datas) {
    				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
    				List<Object> obj = new ArrayList<Object>();
    				
       				obj.add(DateUtil.formatYYYYMMDD((Date)data.get("successTime")));
    				obj.add(DateUtil.formatYYYYMMDD((Date)data.get("interestBeginDate")));
    				obj.add(data.get("userName"));
    				obj.add(data.get("mobile"));
    				obj.add(data.get("idCard"));
    				obj.add(data.get("productName"));
    				obj.add(data.get("contractId"));
    				obj.add(MoneyUtil.format((Double)data.get("balance")));
    				obj.add(MoneyUtil.format((Double)data.get("actualAmount")));
    				Integer status = (Integer) data.get("status");
    				switch(status) {
    				case 2:
    					obj.add("投资中");
    					break;
    				case 5:
    					obj.add("已完成");
    					break;
    				case 7:
    					obj.add("回款中");
    					break;
    				}
    				obj.add((Integer)data.get("term")+"天");
    				obj.add(DateUtil.format((Date)data.get("returnDay")));
    				obj.add(MoneyUtil.format((Double)data.get("baseRate")));
    				obj.add(data.get("managerName"));
    				obj.add(data.get("deptName"));
    				obj.add(data.get("deptCode"));
    				
    				datasMap.put("user"+(++i), obj);
    				list.add(datasMap);
    			}
    		}
    		
    		try {
    			ExportUtil.exportExcel(response, request, "达飞财务结算-理财", list);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    //=========================财务结算-理财E======================================
    
    
    /**
     * 达飞业管查询  / 理财提成核算
     * @param req
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/financialIntention/businessManagerIndex")
    public String businessManagerIndex(DafyBusinessManagerVO req, HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
    	//获取产品类型
        String productType = request.getParameter("productType")==null?Constants.PRODUCT_TYPE_NORMAL:request.getParameter("productType");
        
    	CookieManager cookie = new CookieManager(request);
        String mobile = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_EMAIL.getName(), true);
        String dafyUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERID.getName(), true);
        String dafyDeptId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTID.getName(), true);
        String dafyDeptCode = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTCODE.getName(), true);
        String dafyUserName = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERNME.getName(), true);
        String dafyDeptName = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTNAME.getName(), true);
        String isManager = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISMANAGER.getName(), true);
        String isDafyUserString = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISDAFYUSER.getName(), true);
        
        if("yes".equals(isDafyUserString)) {
        	String selectDeptId = request.getParameter("selectDeptId");
            List<Tbemployee> userList = new ArrayList<Tbemployee>();
        	//查询达飞用户权限
        	List<Tbdatapermission> missionList = financialIntentionService.selectUserMission(Long.valueOf(dafyUserId));
        	//普通客户经理
        	if(CollectionUtils.isEmpty(missionList) && "0".equals(isManager)) {
        		model.put("deptStatus", "disable");
    			model.put("userStatus", "disable");
    			Tbemployee user = new Tbemployee();
    			user.setLuserid(Long.valueOf(dafyUserId));
    			user.setStrmobile(mobile);
    			user.setStrname(dafyUserName);
    			userList.add(user);
        	}
        	else {
        		model.put("deptStatus", "able");
    			model.put("userStatus", "able");
    			if(StringUtil.isNotBlank(selectDeptId)) {
    				boolean flag = true;
    				for(Tbdatapermission mission : missionList) {
    		    		if(mission.getStrdeptcode().equals(dafyDeptCode)) {
    		    			flag = false;
    		    			break;
    		    		}
    		    	}
    				//没有特殊权限，非部门主管同时选择的部门又是自己所属部门，则只能查看自己信息
    				if(flag && selectDeptId.equals(dafyDeptId) && "0".equals(isManager)) {
    					Tbemployee user = new Tbemployee();
    	    			user.setLuserid(Long.valueOf(dafyUserId));
    	    			user.setStrmobile(mobile);
    	    			user.setStrname(dafyUserName);
    	    			userList.add(user);
    				}
    				else {
    					//查询部门下的客户经理
            			userList = financialIntentionService.selectEmployeeByDeptId(Long.valueOf(selectDeptId));
    				}
        		}
        	}
        	
        	model.put("dafyUserId", dafyUserId);
        	model.put("dafyUserName", dafyUserName);
        	model.put("dafyDeptId", dafyDeptId);
        	model.put("dafyDeptName", dafyDeptName);
        	
        	model.put("isDafyUser", isDafyUserString);
            model.put("userList", userList);
            
            String isSelect = request.getParameter("isSelect");
            if("y".equals(isSelect)) {
            	List<Long> deptIds = new ArrayList<Long>();
            	List<Long> copyDeptIds = new ArrayList<Long>();
            	String isOrDafyUserId = null;
            	//已指定营业部
            	if(StringUtil.isNotBlank(selectDeptId)) {
            		deptIds.add(Long.valueOf(selectDeptId));
                }
            	//未指定营业部
        		if(CollectionUtils.isEmpty(deptIds)) {
        			Map<Long,Tbdepartment> deptMap = new HashMap<Long,Tbdepartment>();
        			List<Tbdepartment> lowDept = financialIntentionService.selectLowLevelDept(Long.valueOf(dafyUserId));
        			for(Tbdepartment dept : lowDept) {
        				deptMap.put(dept.getLid(), dept);
        			}
        			
        			//1为部门主管
        			if(Integer.valueOf(isManager) == 1) {
        				Tbdepartment myDept = financialIntentionService.selectTbdepartmentByKey(Long.valueOf(dafyDeptId));
        				Integer level = myDept.getNcurrentlevel();
        				String sql = "select * from tbdepartment where strDeptCode"+level+"='"+myDept.getStrdeptcode()+"'";
        				List<Tbdepartment> nextDeptList = financialIntentionService.selectTbdepartmentSQL(sql);
        				for(Tbdepartment dept : nextDeptList) {
        					deptMap.put(dept.getLid(), dept);
        				}
        			}
        			else {
        				if(!deptMap.containsKey(Long.valueOf(dafyDeptId))) {
        					isOrDafyUserId = dafyUserId;
        				}
        			}
        			deptIds = new ArrayList<Long>(deptMap.keySet());
        		}
        		copyDeptIds.addAll(deptIds);
            	
                DafyBusinessManagerVO copyReq = new DafyBusinessManagerVO();
            	org.springframework.beans.BeanUtils.copyProperties(req, copyReq);
            	List<DafyBusinessManagerVO> dataGrid = financialIntentionService.selectDafyBusinessManager(req, deptIds, isOrDafyUserId, productType);
                Integer count = financialIntentionService.countDafyBusinessManager(copyReq, copyDeptIds, isOrDafyUserId, productType);
                
                //将数据返回给页面
                model.put("selectDeptId", selectDeptId);
                model.put("dataGrid", dataGrid);
                req.setTotalRows(count);
            }
        }
        model.put("req", req);
        model.put("productType", productType);
        if(Constants.PRODUCT_TYPE_ZAN.equals(productType)){
        	return "/financial/financial_business_manager_zan";
        }else{
        	return "/financial/financial_business_manager";
        }
    }
    
    /**
     * 达飞业管数据导出
     * @param req
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping("/financialIntention/businessManagerExport")
    public void businessManagerExport(DafyBusinessManagerVO req, HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
    	//获取产品类型
        String productType = request.getParameter("productType")==null?Constants.PRODUCT_TYPE_NORMAL:request.getParameter("productType");
        
    	CookieManager cookie = new CookieManager(request);
    	String dafyUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERID.getName(), true);
    	String dafyDeptId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTID.getName(), true);
    	String isManager = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISMANAGER.getName(), true);
    	String isDafyUserString = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISDAFYUSER.getName(), true);
    	if("yes".equals(isDafyUserString)) {
    		String selectDeptId = request.getParameter("selectDeptId");
        	List<Long> deptIds = new ArrayList<Long>();
        	String isOrDafyUserId = null;
        	//已指定营业部
        	if(StringUtil.isNotBlank(selectDeptId)) {
        		deptIds.add(Long.valueOf(selectDeptId));
            }
        	//未指定营业部
    		if(CollectionUtils.isEmpty(deptIds)) {
    			Map<Long,Tbdepartment> deptMap = new HashMap<Long,Tbdepartment>();
    			List<Tbdepartment> lowDept = financialIntentionService.selectLowLevelDept(Long.valueOf(dafyUserId));
    			for(Tbdepartment dept : lowDept) {
    				deptMap.put(dept.getLid(), dept);
    			}
    			
    			//1为部门主管
    			if(Integer.valueOf(isManager) == 1) {
    				Tbdepartment myDept = financialIntentionService.selectTbdepartmentByKey(Long.valueOf(dafyDeptId));
    				Integer level = myDept.getNcurrentlevel();
    				String sql = "select * from tbdepartment where strDeptCode"+level+"='"+myDept.getStrdeptcode()+"'";
    				List<Tbdepartment> deptList = financialIntentionService.selectTbdepartmentSQL(sql);
    				for(Tbdepartment dept : deptList) {
    					deptMap.put(dept.getLid(), dept);
    				}
    			}
    			else {
    				if(!deptMap.containsKey(Long.valueOf(dafyDeptId))) {
    					isOrDafyUserId = dafyUserId;
    				}
    			}
    			deptIds = new ArrayList<Long>(deptMap.keySet());
    		}
    		
    		List<Long> copyDeptIds = new ArrayList<Long>();
    		copyDeptIds.addAll(deptIds);
    		DafyBusinessManagerVO copyReq = new DafyBusinessManagerVO();
    		org.springframework.beans.BeanUtils.copyProperties(req, copyReq);
    		Integer count = financialIntentionService.countDafyBusinessManager(copyReq, copyDeptIds, isOrDafyUserId, productType);
    		req.setNumPerPage(count);
    		req.setPageNum(0);
        	List<DafyBusinessManagerVO> dataGrid = financialIntentionService.selectDafyBusinessManager(req, deptIds, isOrDafyUserId, productType);
        	List<HashMap<String,Object>> datas = BeanUtils.classToArrayList(dataGrid);
    		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
    		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
    		List<Object> titles = new ArrayList<Object>();
    		titles.add("理财人姓名");
    		titles.add("理财人手机");
    		titles.add("理财人身份证");
    		titles.add("营业部编号");
    		titles.add("营业部名称");
    		titles.add("便利店编号");
    		titles.add("便利店名称");
    		titles.add("客户经理");
    		titles.add("产品名称");
    		titles.add("理财申请编号");
    		titles.add("年化利率(%)");
    		if(Constants.PRODUCT_TYPE_ZAN.equals(productType)){
    			titles.add("购买金额(元)");
    		}
    		titles.add("投资金额(元)");
    		titles.add("实际投资金额(元)");
    		titles.add("创建时间");
    		titles.add("投资状态");
    		titles.add("投资成功时间");
    		titles.add("投资申请失败原因");
    		titles.add("封闭开始日期");
    		titles.add("封闭结束日期");
    		titles.add("申请赎回日期");
    		titles.add("预计提现日期");
    		titles.add("实际提现日期");
    		titles.add("理财周期(天)");
    		titles.add("是否续投");
    		titles.add("审批状态");
    		titles.add("封闭期利息(元)");
    		titles.add("封闭期外利息(元)");
    		titles.add("提现金额(元)");
    		titleMap.put("title", titles);
    		list.add(titleMap);
    		if(!CollectionUtils.isEmpty(datas)) {
    			int i = 0;
    			for (HashMap<String,Object> data : datas) {
    				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
    				List<Object> obj = new ArrayList<Object>();
    				obj.add(data.get("userName"));
    				obj.add(StringUtil.substringBefore((String)data.get("mobile"), StringUtil.right((String)data.get("mobile"), 4))+"****");
    				obj.add("****"+StringUtil.substringAfter(StringUtil.substringBefore((String)data.get("idCard"), StringUtil.right((String)data.get("idCard"), 4)), StringUtil.left((String)data.get("idCard"), 4))+"****");
    				obj.add(data.get("deptCode"));
    				obj.add(data.get("deptName"));
    				obj.add("无");
    				obj.add("无");
    				obj.add(data.get("managerName"));
    				obj.add(data.get("productName"));
    				obj.add(data.get("subAccountId"));
    				obj.add(MoneyUtil.format((Double)data.get("baseRate")));
    				if(Constants.PRODUCT_TYPE_ZAN.equals(productType)){
    					obj.add(MoneyUtil.format((Double)data.get("openBalance")));
    				}
    				obj.add(data.get("balance") == null ? "" :MoneyUtil.format((Double)data.get("balance")));
    				obj.add(data.get("buyAmount") == null ? "" :MoneyUtil.format((Double)data.get("buyAmount")));
    				obj.add(data.get("applyTime") == null ? "" :DateUtil.format((Date)data.get("applyTime")));
    				if(Constants.PRODUCT_TYPE_ZAN.equals(productType)){
    					obj.add((String)data.get("statusStr"));
    				}else if(Constants.PRODUCT_TYPE_NORMAL.equals(productType)){
    					Integer status = (Integer) data.get("status");
        				switch(status) {
        				case 2:
        					obj.add("投资中");
        					break;
        				case 5:
        					obj.add("已完成");
        					break;
        				case 7:
        					obj.add("回款中");
        					break;
        				}
    				}
    				
    				obj.add(data.get("successTime") == null ? "" :DateUtil.format((Date)data.get("successTime")));
    				obj.add("无");
    				obj.add(data.get("interestBeginDate") == null ? "" :DateUtil.formatYYYYMMDD((Date)data.get("interestBeginDate")));
    				obj.add(data.get("planPaymentDate") == null ? "" :DateUtil.formatYYYYMMDD((Date)data.get("planPaymentDate")));
    				obj.add(data.get("planPaymentDate") == null ? "" :DateUtil.formatYYYYMMDD((Date)data.get("planPaymentDate")));
    				obj.add(data.get("planPaymentDate") == null ? "" :DateUtil.formatYYYYMMDD((Date)data.get("planPaymentDate")));
    				if(Constants.PRODUCT_TYPE_ZAN.equals(productType)){
    					if("已完成".equals((String)data.get("statusStr"))){
    						obj.add(DateUtil.formatYYYYMMDD((Date)data.get("actualPaymentDate")));
    					}else{
    						obj.add("");
    					}
    					
    				}else if(Constants.PRODUCT_TYPE_NORMAL.equals(productType)){
    					obj.add(DateUtil.formatYYYYMMDD((Date)data.get("actualPaymentDate")));
    				}
    				
    				Integer term = (Integer)data.get("term");
    				if(term < 0) {
    					obj.add(Math.abs(term));
    				}
    				else if(term == 12) {
    					obj.add("365");
    				}
    				else {
    					obj.add(term*30);
    				}
    				obj.add("否");
    				obj.add("无");
    				obj.add(data.get("totalIncome") == null ? "" :MoneyUtil.format((Double)data.get("totalIncome")));
    				obj.add("0.00");
    				obj.add(data.get("payment") == null ? "" :MoneyUtil.format((Double)data.get("payment")));
    				datasMap.put("user"+(++i), obj);
    				list.add(datasMap);
    			}
    		}
    		
    		try {
    			ExportUtil.exportExcel(response, request, "理财提成核算", list);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    
    //=========================财务结算-赎回S=====================================
    
    @RequestMapping("/financialIntention/financialRedeemIndex")
    public String financialRedeemIndex(FinancialRedeemVO req, HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
        
    	//获取产品类型
        String productType = request.getParameter("productType")==null?Constants.PRODUCT_TYPE_NORMAL:request.getParameter("productType");
    	
    	/**
    	 * 一、需要进行财务管理人员的权限进行信息查询，判断可以查看数据的权限
    	 * 1、财务人员具有特殊权限--进入到查询界面营业部和客户经理框都可以编辑
    	 * 2、财务人员并不具有特殊权限，但是自己本身是部门主管--进入到查询界面营业部不可编辑，客户经理框可以下拉选择
    	 * 3、财务人员并不具有特殊权限并且自己本身不是部门主管--进入到查询界面营业部不可编辑，客户经理框也不可以编辑
    	 * 
    	 * 二、检查参数是否带查询参数，如果带查询参数则进行数据查询（isSelect == 'y'）
    	 * 1、如果参数里面已指定营业部但是未指定客户经理则根据营业部进行查询
    	 * 2、如果参数里面已指定营业部并且还指定客户经理则根据客户经理进行查询
    	 * 3、如果参数里面未指定营业部也没有指定客户经理则根据财务管理人员所具有的权限查询所有数据
    	 */
    	
    	CookieManager cookie = new CookieManager(request);
        String mobile = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_EMAIL.getName(), true);
        String dafyUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERID.getName(), true);
        String dafyDeptId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTID.getName(), true);
        String dafyUserName = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERNME.getName(), true);
        String dafyDeptName = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTNAME.getName(), true);
        String isManager = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISMANAGER.getName(), true);
        String isDafyUserString = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISDAFYUSER.getName(), true);
        String dafyDeptCode = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTCODE.getName(), true);
        if("yes".equals(isDafyUserString)) {
        	String selectDeptId = request.getParameter("selectDeptId");
            List<Tbemployee> userList = new ArrayList<Tbemployee>();
        	//查询达飞用户权限
        	List<Tbdatapermission> missionList = financialIntentionService.selectUserMission(Long.valueOf(dafyUserId));
        	//普通客户经理
        	if(CollectionUtils.isEmpty(missionList) && "0".equals(isManager)) {
        		model.put("deptStatus", "disable");
    			model.put("userStatus", "disable");
    			Tbemployee user = new Tbemployee();
    			user.setLuserid(Long.valueOf(dafyUserId));
    			user.setStrmobile(mobile);
    			user.setStrname(dafyUserName);
    			userList.add(user);
        	}
        	else {
        		model.put("deptStatus", "able");
    			model.put("userStatus", "able");
    			if(StringUtil.isNotBlank(selectDeptId)) {
        			//查询部门下的客户经理
    				boolean flag = true;
    				for(Tbdatapermission mission : missionList) {
    		    		if(mission.getStrdeptcode().equals(dafyDeptCode)) {
    		    			flag = false;
    		    			break;
    		    		}
    		    	}
    				//没有特殊权限，非部门主管同时选择的部门又是自己所属部门，则只能查看自己信息
    				if(flag && selectDeptId.equals(dafyDeptId) && "0".equals(isManager)) {
    					Tbemployee user = new Tbemployee();
    	    			user.setLuserid(Long.valueOf(dafyUserId));
    	    			user.setStrmobile(mobile);
    	    			user.setStrname(dafyUserName);
    	    			userList.add(user);
    				}
    				else {
    					//查询部门下的客户经理
            			userList = financialIntentionService.selectEmployeeByDeptId(Long.valueOf(selectDeptId));
    				}
        		}
        	}
        	
        	model.put("dafyUserId", dafyUserId);
        	model.put("dafyUserName", dafyUserName);
        	model.put("dafyDeptId", dafyDeptId);
        	model.put("dafyDeptName", dafyDeptName);
        	
        	model.put("isDafyUser", isDafyUserString);
            model.put("userList", userList);
            
            String isSelect = request.getParameter("isSelect");
            if("y".equals(isSelect)) {
            	String isOrDafyUserId = null;
            	List<Long> deptIds = new ArrayList<Long>();
            	List<Long> copyDeptIds = new ArrayList<Long>();
            	//已指定营业部
            	if(StringUtil.isNotBlank(selectDeptId)) {
            		deptIds.add(Long.valueOf(selectDeptId));
                }
            	//未指定营业部
        		if(CollectionUtils.isEmpty(deptIds)) {
        			Map<Long,Tbdepartment> deptMap = new HashMap<Long,Tbdepartment>();
        			List<Tbdepartment> lowDept = financialIntentionService.selectLowLevelDept(Long.valueOf(dafyUserId));
        			for(Tbdepartment dept : lowDept) {
        				deptMap.put(dept.getLid(), dept);
        			}
        			
        			//1为部门主管
        			if(Integer.valueOf(isManager) == 1) {
        				Tbdepartment myDept = financialIntentionService.selectTbdepartmentByKey(Long.valueOf(dafyDeptId));
        				Integer level = myDept.getNcurrentlevel();
        				String sql = "select * from tbdepartment where strDeptCode"+level+"='"+myDept.getStrdeptcode()+"'";
        				List<Tbdepartment> deptList = financialIntentionService.selectTbdepartmentSQL(sql);
        				for(Tbdepartment dept : deptList) {
        					deptMap.put(dept.getLid(), dept);
        				}
        			}
	    			else {
	    				if(!deptMap.containsKey(Long.valueOf(dafyDeptId))) {
	    					isOrDafyUserId = dafyUserId;
	    				}
	    			}
        			deptIds = new ArrayList<Long>(deptMap.keySet());
        		}
        		copyDeptIds.addAll(deptIds);
            	
            	List<FinancialRedeemVO> dataGrid = new ArrayList<FinancialRedeemVO>();
                Integer count = 0;
                if(StringUtil.isNotBlank(dafyUserId) && StringUtil.isNotBlank(dafyDeptId)) {
                	
            		DecimalFormat format = new DecimalFormat("0.00");
                	if (StringUtil.isNotBlank(req.getRedeemAmountStart())) {
                		String redeemAmount = format.format(Double.parseDouble(req.getRedeemAmountStart()));
                		req.setRedeemAmountStart(redeemAmount);
					}
                	if (StringUtil.isNotBlank(req.getRedeemAmountEnd())) {
                		String redeemAmount = format.format(Double.parseDouble(req.getRedeemAmountEnd()));
                		req.setRedeemAmountEnd(redeemAmount);
					}
            		
                	DecimalFormat formatInt = new DecimalFormat("0");
                	if (StringUtil.isNotBlank(req.getAmountStart())) {
                		String amount = formatInt.format(Double.parseDouble(req.getAmountStart()));
                		req.setAmountStart(amount);
					}
                	if (StringUtil.isNotBlank(req.getAmountEnd())) {
                		String amount = formatInt.format(Double.parseDouble(req.getAmountEnd()));
                		req.setAmountEnd(amount);
					}
                	
                	FinancialRedeemVO copyReq = new FinancialRedeemVO();
                	org.springframework.beans.BeanUtils.copyProperties(req, copyReq);
                	
                	FinancialRedeemVO copyReq1 = new FinancialRedeemVO();
                	org.springframework.beans.BeanUtils.copyProperties(req, copyReq1);
                	
            		String startInterestTime = req.getInterestDateStart();
            		String endInterestTime = req.getInterestDateEnd();
            		
            		
            		if (!StringUtil.isNotBlank(req.getInterestDateStart()) && StringUtil.isNotBlank(req.getInterestDateEnd()) ) {
            			Calendar cal = Calendar.getInstance(); 
            			cal.setTime(DateUtil.parseDate(req.getInterestDateEnd()));
            			cal.set(GregorianCalendar.DAY_OF_MONTH, 1); 
                        Date beginTime=cal.getTime();
            			startInterestTime = DateUtil.formatYYYYMMDD(beginTime);
            		}
            		
            		if (StringUtil.isNotBlank(req.getInterestDateStart()) && !StringUtil.isNotBlank(req.getInterestDateEnd()) ) {
            			endInterestTime = DateUtil.formatYYYYMMDD(new Date());
            		}
            		
            		
            		
            		if (StringUtil.isNotBlank(startInterestTime) && StringUtil.isNotBlank(endInterestTime)) {
            			req.setInterestDateStart(startInterestTime);
            			req.setInterestDateEnd(endInterestTime);
            			
            			if(StringUtil.isNotBlank(startInterestTime)) {
            				startInterestTime += " 00:00:00";
            			}
            			if(StringUtil.isNotBlank(endInterestTime)) {
            				endInterestTime += " 23:59:59";
            			}
            			copyReq.setInterestDateStart(startInterestTime);
            			copyReq.setInterestDateEnd(endInterestTime);
            			copyReq1.setInterestDateStart(startInterestTime);
            			copyReq1.setInterestDateEnd(endInterestTime);
            		}else {
            			copyReq.setInterestDateStart(null);
            			copyReq.setInterestDateEnd(null);
            			copyReq1.setInterestDateStart(null);
            			copyReq1.setInterestDateEnd(null);
            		}
                	
            		String startRedeemTime = req.getRedeemDateStart();
            		String endRedeemTime = req.getRedeemDateEnd();
            		
            		if (!StringUtil.isNotBlank(req.getRedeemDateStart()) && StringUtil.isNotBlank(req.getRedeemDateEnd()) ) {
            			Calendar cal = Calendar.getInstance(); 
            			cal.setTime(DateUtil.parseDate(req.getRedeemDateEnd()));
            			cal.set(GregorianCalendar.DAY_OF_MONTH, 1); 
                        Date beginTime=cal.getTime();
                        startRedeemTime = DateUtil.formatYYYYMMDD(beginTime);
            		}
            		
            		if (StringUtil.isNotBlank(req.getRedeemDateStart()) && !StringUtil.isNotBlank(req.getRedeemDateEnd()) ) {
            			endRedeemTime = DateUtil.formatYYYYMMDD(new Date());
            		}
            		
            		
            		
            		if (StringUtil.isNotBlank(startRedeemTime) && StringUtil.isNotBlank(endRedeemTime)) {
            			req.setRedeemDateStart(startRedeemTime);
            			req.setRedeemDateEnd(endRedeemTime);
            			
            			if(StringUtil.isNotBlank(startRedeemTime)) {
            				startInterestTime += " 00:00:00";
            			}
            			if(StringUtil.isNotBlank(endRedeemTime)) {
            				endInterestTime += " 23:59:59";
            			}
            			
            			copyReq.setRedeemDateStart(startRedeemTime);
            			copyReq.setRedeemDateEnd(endRedeemTime);
            			copyReq1.setRedeemDateStart(startRedeemTime);
            			copyReq1.setRedeemDateEnd(endRedeemTime);
          
            		}else {
            			copyReq.setRedeemDateStart(null);
            			copyReq.setRedeemDateEnd(null);
            			copyReq1.setRedeemDateStart(null);
            			copyReq1.setRedeemDateEnd(null);
            		}

                    dataGrid = financialIntentionService.queryFinancialRedeemGrid(copyReq1, deptIds, isOrDafyUserId, productType);
                    count = financialIntentionService.countFinancialRedeem(copyReq, copyDeptIds, isOrDafyUserId, productType);
                }
                
                
                
                
                //将数据返回给页面
                model.put("selectDeptId", selectDeptId);
                model.put("dataGrid", dataGrid);
                req.setTotalRows(count);
            }
        }
        model.put("productType", productType);
        
        model.put("req", req);
        return "/financial/financial_redeem_index";
    }
    
    @RequestMapping("/financialIntention/financialRedeemExport")
    public void financialRedeemExport(FinancialRedeemVO req, HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
    	//获取产品类型
        String productType = request.getParameter("productType")==null?Constants.PRODUCT_TYPE_NORMAL:request.getParameter("productType");
    	
    	CookieManager cookie = new CookieManager(request);
    	String dafyUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_USERID.getName(), true);
    	String dafyDeptId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_DEPTID.getName(), true);
    	String isManager = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISMANAGER.getName(), true);
    	String isDafyUserString = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISDAFYUSER.getName(), true);
    	if("yes".equals(isDafyUserString)) {
    		String isOrDafyUserId = null;
    		String selectDeptId = request.getParameter("selectDeptId");
        	List<Long> deptIds = new ArrayList<Long>();
        	//已指定营业部
        	if(StringUtil.isNotBlank(selectDeptId)) {
        		deptIds.add(Long.valueOf(selectDeptId));
            }
        	//未指定营业部
    		if(CollectionUtils.isEmpty(deptIds)) {
    			Map<Long,Tbdepartment> deptMap = new HashMap<Long,Tbdepartment>();
    			List<Tbdepartment> lowDept = financialIntentionService.selectLowLevelDept(Long.valueOf(dafyUserId));
    			for(Tbdepartment dept : lowDept) {
    				deptMap.put(dept.getLid(), dept);
    			}
    			
    			//1为部门主管
    			if(Integer.valueOf(isManager) == 1) {
    				Tbdepartment myDept = financialIntentionService.selectTbdepartmentByKey(Long.valueOf(dafyDeptId));
    				Integer level = myDept.getNcurrentlevel();
    				String sql = "select * from tbdepartment where strDeptCode"+level+"='"+myDept.getStrdeptcode()+"'";
    				List<Tbdepartment> deptList = financialIntentionService.selectTbdepartmentSQL(sql);
    				for(Tbdepartment dept : deptList) {
    					deptMap.put(dept.getLid(), dept);
    				}
    			}
    			else {
    				if(!deptMap.containsKey(Long.valueOf(dafyDeptId))) {
    					isOrDafyUserId = dafyUserId;
    				}
    			}
    			deptIds = new ArrayList<Long>(deptMap.keySet());
    		}
    		
    		List<Long> copyDeptIds = new ArrayList<Long>();
    		copyDeptIds.addAll(deptIds);
    		
    		DecimalFormat format = new DecimalFormat("0.00");
        	if (StringUtil.isNotBlank(req.getRedeemAmountStart())) {
        		String redeemAmount = format.format(Double.parseDouble(req.getRedeemAmountStart()));
        		req.setRedeemAmountStart(redeemAmount);
			}
        	if (StringUtil.isNotBlank(req.getRedeemAmountEnd())) {
        		String redeemAmount = format.format(Double.parseDouble(req.getRedeemAmountEnd()));
        		req.setRedeemAmountEnd(redeemAmount);
			}
    		
        	DecimalFormat formatInt = new DecimalFormat("0");
        	if (StringUtil.isNotBlank(req.getAmountStart())) {
        		String amount = formatInt.format(Double.parseDouble(req.getAmountStart()));
        		req.setAmountStart(amount);
			}
        	if (StringUtil.isNotBlank(req.getAmountEnd())) {
        		String amount = formatInt.format(Double.parseDouble(req.getAmountEnd()));
        		req.setAmountEnd(amount);
			}
    		
    		
    		FinancialRedeemVO copyReq = new FinancialRedeemVO();
    		org.springframework.beans.BeanUtils.copyProperties(req, copyReq);
    		
    		
        	FinancialRedeemVO copyReq1 = new FinancialRedeemVO();
        	org.springframework.beans.BeanUtils.copyProperties(req, copyReq1);
        	
    		String startInterestTime = req.getInterestDateStart();
    		String endInterestTime = req.getInterestDateEnd();
    		
    		
    		if (!StringUtil.isNotBlank(req.getInterestDateStart()) && StringUtil.isNotBlank(req.getInterestDateEnd()) ) {
    			Calendar cal = Calendar.getInstance(); 
    			cal.setTime(DateUtil.parseDate(req.getInterestDateEnd()));
    			cal.set(GregorianCalendar.DAY_OF_MONTH, 1); 
                Date beginTime=cal.getTime();
    			startInterestTime = DateUtil.formatYYYYMMDD(beginTime);
    		}
    		
    		if (StringUtil.isNotBlank(req.getInterestDateStart()) && !StringUtil.isNotBlank(req.getInterestDateEnd()) ) {
    			endInterestTime = DateUtil.formatYYYYMMDD(new Date());
    		}
    		
    		
    		
    		if (StringUtil.isNotBlank(startInterestTime) && StringUtil.isNotBlank(endInterestTime)) {
    			req.setInterestDateStart(startInterestTime);
    			req.setInterestDateEnd(endInterestTime);
    			
    			if(StringUtil.isNotBlank(startInterestTime)) {
    				startInterestTime += " 00:00:00";
    			}
    			if(StringUtil.isNotBlank(endInterestTime)) {
    				endInterestTime += " 23:59:59";
    			}
    			copyReq.setInterestDateStart(startInterestTime);
    			copyReq.setInterestDateEnd(endInterestTime);
    			copyReq1.setInterestDateStart(startInterestTime);
    			copyReq1.setInterestDateEnd(endInterestTime);
    		}else {
    			copyReq.setInterestDateStart(null);
    			copyReq.setInterestDateEnd(null);
    			copyReq1.setInterestDateStart(null);
    			copyReq1.setInterestDateEnd(null);
    		}
        	
    		String startRedeemTime = req.getRedeemDateStart();
    		String endRedeemTime = req.getRedeemDateEnd();
    		
    		if (!StringUtil.isNotBlank(req.getRedeemDateStart()) && StringUtil.isNotBlank(req.getRedeemDateEnd()) ) {
    			Calendar cal = Calendar.getInstance(); 
    			cal.setTime(DateUtil.parseDate(req.getRedeemDateEnd()));
    			cal.set(GregorianCalendar.DAY_OF_MONTH, 1); 
                Date beginTime=cal.getTime();
                startRedeemTime = DateUtil.formatYYYYMMDD(beginTime);
    		}
    		
    		if (StringUtil.isNotBlank(req.getRedeemDateStart()) && !StringUtil.isNotBlank(req.getRedeemDateEnd()) ) {
    			endRedeemTime = DateUtil.formatYYYYMMDD(new Date());
    		}
    		
    		
    		
    		if (StringUtil.isNotBlank(startRedeemTime) && StringUtil.isNotBlank(endRedeemTime)) {
    			req.setRedeemDateStart(startRedeemTime);
    			req.setRedeemDateEnd(endRedeemTime);
    			
    			if(StringUtil.isNotBlank(startRedeemTime)) {
    				startInterestTime += " 00:00:00";
    			}
    			if(StringUtil.isNotBlank(endRedeemTime)) {
    				endInterestTime += " 23:59:59";
    			}
    			
    			copyReq.setRedeemDateStart(startRedeemTime);
    			copyReq.setRedeemDateEnd(endRedeemTime);
    			copyReq1.setRedeemDateStart(startRedeemTime);
    			copyReq1.setRedeemDateEnd(endRedeemTime);
  
    		}else {
    			copyReq.setRedeemDateStart(null);
    			copyReq.setRedeemDateEnd(null);
    			copyReq1.setRedeemDateStart(null);
    			copyReq1.setRedeemDateEnd(null);
    		}
    		
    		Integer count = financialIntentionService.countFinancialRedeem(copyReq, copyDeptIds,isOrDafyUserId, productType);
    		copyReq1.setNumPerPage(count);
    		copyReq1.setPageNum(0);
        	List<FinancialRedeemVO> dataGrid = financialIntentionService.queryFinancialRedeemGrid(copyReq1, deptIds,isOrDafyUserId, productType);
        	List<HashMap<String,Object>> datas = BeanUtils.classToArrayList(dataGrid);
    		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
    		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
    		List<Object> titles = new ArrayList<Object>();
    		
    		titles.add("计息日期");
    		titles.add("赎回成功日期");
    		titles.add("出借人");
    		titles.add("出借人手机");
    		titles.add("出借人身份证");
    		titles.add("产品");
    		if(Constants.PRODUCT_TYPE_ZAN.equals(productType)){
    			titles.add("合同编号");
    		}
    		titles.add("出借金额（元）");
    		titles.add("实际出借金额（元）");
    		titles.add("投资状态");
    		titles.add("期限（天）");
    		titles.add("利率（%）");
    		titles.add("转让日期");
    		titles.add("支付金额(元)");
    		titles.add("本金(元)");
    		titles.add("利息(元)");
    		titles.add("营业员");
    		titles.add("营业部名称");
    		titles.add("营业部编号");
    		titleMap.put("title", titles);
    		list.add(titleMap);
    		if(!CollectionUtils.isEmpty(datas)) {
    			int i = 0;
    			for (HashMap<String,Object> data : datas) {
    				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
    				List<Object> obj = new ArrayList<Object>();
    				
       				obj.add(DateUtil.formatYYYYMMDD((Date)data.get("interestBeginDate")));
    				obj.add(DateUtil.formatYYYYMMDD((Date)data.get("redeemSuccessTime")));
    				obj.add(data.get("userName"));
    				obj.add(data.get("mobile"));
    				obj.add(data.get("idCard"));
    				obj.add(data.get("productName"));
    				if(Constants.PRODUCT_TYPE_ZAN.equals(productType)){
    					obj.add(data.get("contractId"));
    	    		}
    				obj.add(MoneyUtil.format((Double)data.get("balance")));
    				obj.add(MoneyUtil.format((Double)data.get("actualAmount")));
    				Integer status = (Integer) data.get("status");
    				switch(status) {
    				case 2:
    					obj.add("投资中");
    					break;
    				case 5:
    					obj.add("已结算");
    					break;
    				case 7:
    					obj.add("结算中");
    					break;
    				case 8:
    					obj.add("结算失败");
    					break;
    				}
    				obj.add((Integer)data.get("term")+"天");
    				obj.add(MoneyUtil.format((Double)data.get("baseRate")));
    				obj.add("无");
    				obj.add(MoneyUtil.format((Double)data.get("redeemBalance")));
    				obj.add(MoneyUtil.format((Double)data.get("redeemPrincipalBalance")));
    				obj.add(MoneyUtil.format((Double)data.get("redeemInterestBalance")));
    				obj.add(data.get("managerName"));
    				obj.add(data.get("deptName"));
    				obj.add(data.get("deptCode"));
    				
    				datasMap.put("user"+(++i), obj);
    				list.add(datasMap);
    			}
    		}
    		
    		try {
    			ExportUtil.exportExcel(response, request, "达飞财务结算-赎回", list);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    //=========================财务结算-赎回E======================================
    
    
    
}
