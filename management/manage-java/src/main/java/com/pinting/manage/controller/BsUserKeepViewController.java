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

import com.pinting.business.hessian.manage.message.ReqMsg_BsUserKeepView_BsUserRetentionListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_BsUserKeepView_BsUserRetentionListQuery;
import com.pinting.business.service.manage.AgentService;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.util.Constants;
import com.pinting.util.ExportUtil;

@Controller
public class BsUserKeepViewController {
	
	@Autowired
	@Qualifier("dispatchService")
	private HessianService keepViewService;
	
	@Autowired
	private AgentService agentService;
	
	/**
	 * 用户留存率查询
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/bsUserKeepView/retention")
	public String userRetentionListInit(ReqMsg_BsUserKeepView_BsUserRetentionListQuery req, HttpServletRequest request, Map<String, Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		if (req.getOrderDirection() != null && req.getOrderField() != null) {
			model.put(req.getOrderField(), req.getOrderDirection());
			model.put("orderDirection",req.getOrderField());
			model.put("orderField", req.getOrderDirection());
		}
		ResMsg_BsUserKeepView_BsUserRetentionListQuery res = (ResMsg_BsUserKeepView_BsUserRetentionListQuery) keepViewService.handleMsg(req);
		int agentTotal = agentService.findAgentsTotalRows();
		model.put("agentTotal", agentTotal);
		model.put("req", req);
		model.put("totalRows", res.getTotalRows());
		model.put("retentionList", res.getValueList());
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		return "/bsUserKeepView/retention_index";
	}
	
	/**
	 * 用户留存率查询导出excel
	 * @param req
	 * @param response
	 * @param request
	 */
	@RequestMapping("/bsUserKeepView/exportXls")
	public void complexVoteXls1(ReqMsg_BsUserKeepView_BsUserRetentionListQuery req,HttpServletResponse response, HttpServletRequest request) {
		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
		//设置标题
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("渠道ID");
		titles.add("渠道名称");
		titles.add("注册日期");
		titles.add("注册人数");
		titles.add("次日留存率");
		titles.add("第3天留存率");
		titles.add("第7天留存率");
		titles.add("第14天留存率");
		titles.add("第30天留存率");
		titles.add("第60天留存率");
		
		titleMap.put("title", titles);
		list.add(titleMap);
		req.setPageNum(1);
        req.setNumPerPage(req.getTotalRows());
		ResMsg_BsUserKeepView_BsUserRetentionListQuery res = (ResMsg_BsUserKeepView_BsUserRetentionListQuery) keepViewService.handleMsg(req);
		List<HashMap<String,Object>> datas =res.getValueList();
		//设置导出excel内容
		if(datas != null && !datas.isEmpty()) {
			int i = 0;
			for (HashMap<String,Object> data : datas) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(data.get("allAgentIds"));
				obj.add(data.get("allAgentNames"));
				obj.add(data.get("registDate"));
				obj.add(data.get("registUserCountTotal"));
				obj.add(data.get("day2KeepRate"));
				obj.add(data.get("day3KeeoRate"));
				obj.add(data.get("day7KeepRate"));
				obj.add(data.get("day14KeepRate"));
				obj.add(data.get("day30KeepRate"));
				obj.add(data.get("day60KeepRate"));
				
				datasMap.put("user"+(++i), obj);
				list.add(datasMap);
			}
		}
		try {
			ExportUtil.exportExcel(response, request, "用户留存率查询", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
