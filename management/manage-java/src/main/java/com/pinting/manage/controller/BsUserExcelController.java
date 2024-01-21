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

import com.pinting.business.hessian.manage.message.ReqMsg_BsUserExcel_BsUserListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_BsUserExcel_BsUserListQuery;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.util.ExportUtil;

@Controller
public class BsUserExcelController {
	
	@Autowired
	@Qualifier("dispatchService")
	private HessianService manageService;
	
	
	@RequestMapping("/baseUserInfoExport/index")
	@ResponseBody
	public void baseUserInfoExport(ReqMsg_BsUserExcel_BsUserListQuery req,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		
		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
		//设置标题
		list.add(titles());
		
		ResMsg_BsUserExcel_BsUserListQuery resp = (ResMsg_BsUserExcel_BsUserListQuery) manageService.handleMsg(req);
		ArrayList<HashMap<String, Object>> datas = resp.getUserList();
		if(datas != null && !datas.isEmpty()) {
			int i = 0;
			for (HashMap<String, Object> data : datas) {
				int k = ++i;
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(k);
				obj.add(data.get("id") == null ? "" : data.get("id"));
				obj.add(data.get("age") == null ? "" : data.get("age"));
				obj.add(data.get("sex") == null ? "" : data.get("sex"));
				obj.add(data.get("agentName") == null ? "" : data.get("agentName"));
				obj.add(data.get("registerTime") == null ? "" : data.get("registerTime"));
				obj.add(data.get("firstBuyTime") == null ? "" : data.get("firstBuyTime"));
				obj.add(data.get("firstInvestDevice") == null ? "" : data.get("firstInvestDevice"));
				obj.add(data.get("area") == null ? "" : data.get("area"));
				obj.add(data.get("totalInterest") == null ? "" : data.get("totalInterest"));
				obj.add(data.get("totalTrans") == null ? "" : data.get("totalTrans"));
				obj.add(data.get("weightInvestTrem") == null ? "" : data.get("weightInvestTrem"));
				obj.add(data.get("recommendId") == null ? "" : data.get("recommendId"));
				datasMap.put("user"+k, obj);
				list.add(datasMap);
			}
			try {
				ExportUtil.exportLocalExcel(response, request, "用户分析基础数据", list);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private Map<String, List<Object>> titles(){
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("序号");
		titles.add("用户编码");
		titles.add("用户年龄");
		titles.add("用户性别");
		titles.add("来源渠道");
		titles.add("注册时间");
		titles.add("首次投资时间");
		titles.add("首次投资设备信息");
		titles.add("购买手机号码地域信息");
		titles.add("总投资金额");
		titles.add("投资次数");
		titles.add("加权投资期限");
		titles.add("推荐人用户编码");
		titleMap.put("title", titles);
		return titleMap;
	}
}

