package com.pinting.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.accounting.finance.service.SysReturnMoneyService;
import com.pinting.business.model.vo.SysReturnManageResVO;
import com.pinting.core.util.StringUtil;
import com.pinting.util.ReturnDWZAjax;

/**
 * 存量资金提前赎回
 * @author bianyatian
 * @2018-1-22 上午10:40:05
 */
@Controller
public class SysReturnController {

	@Autowired
	private SysReturnMoneyService sysReturnMoneyService;
	
	/**
	 * 进入提前赎回页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/sysReturn/index")
	public String sysReturn(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model){
	
		return "system/sysReturn_index";
	}
	
	/**
	 * 传入batchIds，校验是否可以进行回款
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sysReturn/checkBefore")
	public @ResponseBody Map<Object,Object> checkBefore(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		String batchIdStr = request.getParameter("batchIds");
		if(StringUtil.isBlank(batchIdStr)){
			//dataMap.put("message", "批次号不能为空！");
			return ReturnDWZAjax.fail("批次号不能为空！");
		}
		batchIdStr = batchIdStr.replaceAll("，", ",");
		String[] batchIds = batchIdStr.split(",");
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<String> sendBatchIdList = new ArrayList<String>();
		for (String id : batchIds) {
			//去掉相同的batchId
			map.put(id, id);
		}
		Iterator<String> it = map.keySet().iterator();  
		while(it.hasNext()){
			String key = (String)it.next();
			batchIdStr = map.get(key).toString();
			if(StringUtil.isNotBlank(batchIdStr.trim())){
				sendBatchIdList.add(batchIdStr);
			}
		}
		String message = sysReturnMoneyService.return4ManageCheck(sendBatchIdList);
		if(StringUtil.isBlank(message)){
			//dataMap.put("message", 1);
			return ReturnDWZAjax.success("");
		}else{
			//dataMap.put("message", message);
			return ReturnDWZAjax.fail(message);
		}
	}
	
	/**
	 * 查询对应batchIds的应回款明细
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/sysReturn/checkAfter")
	public String checkAfter(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model){
		String batchIdStr = request.getParameter("batchIds");
		model.put("batchIds", batchIdStr);
		batchIdStr = batchIdStr.replaceAll("，", ",");
		String[] batchIds = batchIdStr.split(",");
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<String> sendBatchIdList = new ArrayList<String>();
		for (String id : batchIds) {
			//去掉相同的batchId
			map.put(id, id);
		}
		Iterator<String> it = map.keySet().iterator();  
		while(it.hasNext()){
			String key = (String)it.next();
			batchIdStr = map.get(key).toString();
			if(StringUtil.isNotBlank(batchIdStr.trim())){
				sendBatchIdList.add(batchIdStr);
			}
		}
		SysReturnManageResVO resVo = sysReturnMoneyService.return4ManageGetList(sendBatchIdList);
		model.put("sumTotal", resVo.getSumTotal());
		model.put("sumPrincipal", resVo.getSumPrincipal());
		model.put("list", resVo.getDetailList());
		return "system/sysReturn_index";
	}
	
	/**
	 * 传入batchIds，进行回款
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sysReturn/doSysReturn")
	public @ResponseBody Map<Object,Object> doSysReturn(HttpServletRequest request, HttpServletResponse response){
		String batchIdStr = request.getParameter("batchIds");
		if(StringUtil.isBlank(batchIdStr)){
			return ReturnDWZAjax.fail("批次号不能为空！");
		}
		batchIdStr = batchIdStr.replaceAll("，", ",");
		String[] batchIds = batchIdStr.split(",");
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<String> sendBatchIdList = new ArrayList<String>();
		for (String id : batchIds) {
			//去掉相同的batchId
			map.put(id, id);
		}
		Iterator<String> it = map.keySet().iterator();  
		while(it.hasNext()){
			String key = (String)it.next();
			batchIdStr = map.get(key).toString();
			if(StringUtil.isNotBlank(batchIdStr.trim())){
				sendBatchIdList.add(batchIdStr);
			}
		}
		String message = sysReturnMoneyService.return4ManageCheck(sendBatchIdList);
		if(StringUtil.isBlank(message)){
			message = sysReturnMoneyService.doSysReturn(sendBatchIdList);
			if(StringUtil.isBlank(message)){
				return ReturnDWZAjax.success("操作成功！");
			}else{
				return ReturnDWZAjax.fail(message);
			}
		}else{
			return ReturnDWZAjax.fail(message);
		}
	}
	
}
