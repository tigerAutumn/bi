package com.pinting.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.model.BsProductInvestView;
import com.pinting.business.service.manage.ProductInvestService;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.util.Constants;
import com.pinting.util.ExportUtil;

@Controller
@RequestMapping("/productInvest")
public class ProductInvestController {

	@Autowired
	private ProductInvestService investService;
	
	/**
	 * 产品投资概览
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model){
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
		}
		
		List<BsProductInvestView> list = investService.selectProductInvestList(startTime, endTime, pageNum, numPerPage);
		int count = investService.selectProductInvestCount(startTime, endTime);
		
		model.put("investList", list);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("totalRows", count);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		return "/product/invest/index";
	}
	
	@RequestMapping("/xls")
	public void xls(HttpServletRequest request,HttpServletResponse response) {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		int count = investService.selectProductInvestCount(startTime, endTime);
		List<BsProductInvestView> datas = investService.selectProductInvestList(startTime, endTime, "1", String.valueOf(count));
		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
		//设置标题
		list.add(titles());
		if(!CollectionUtils.isEmpty(datas)) {
			int i = 0;
			for (BsProductInvestView data : datas) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(DateUtil.formatYYYYMMDD(data.getDate()));
				obj.add(data.getTodayInvest7()==null?0.00d:MoneyUtil.format(data.getTodayInvest7()));
				obj.add(data.getTotalInvest7()==null?0.00d:MoneyUtil.format(data.getTotalInvest7()));
				obj.add(data.getTodayInvest30()==null?0.00d:MoneyUtil.format(data.getTodayInvest30()));
				obj.add(data.getTotalInvest30()==null?0.00d:MoneyUtil.format(data.getTotalInvest30()));
				obj.add(data.getTodayInvest90()==null?0.00d:MoneyUtil.format(data.getTodayInvest90()));
				obj.add(data.getTotalInvest90()==null?0.00d:MoneyUtil.format(data.getTotalInvest90())); 
				obj.add(data.getTodayInvest180()==null?0.00d:MoneyUtil.format(data.getTodayInvest180()));
				obj.add(data.getTotalInvest180()==null?0.00d:MoneyUtil.format(data.getTotalInvest180()));
				obj.add(data.getTodayInvest270()==null?0.00d:MoneyUtil.format(data.getTodayInvest270()));
				obj.add(data.getTotalInvest270()==null?0.00d:MoneyUtil.format(data.getTotalInvest270()));
				obj.add(data.getTodayInvest365()==null?0.00d:MoneyUtil.format(data.getTodayInvest365()));
				obj.add(data.getTotalInvest365()==null?0.00d:MoneyUtil.format(data.getTotalInvest365()));
				datasMap.put("user"+(++i), obj);
				list.add(datasMap);
			}
		}
		
		try {
			ExportUtil.exportExcel(response, request, "产品投资概览", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Map<String, List<Object>> titles(){
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("日期");
		titles.add("新用户投资7天金额");
		titles.add("7天销售额");
		titles.add("新用户投资30天金额");
		titles.add("30天销售额");
		titles.add("新用户投资90天金额");
		titles.add("90天销售额");
		titles.add("新用户投资180天金额");
		titles.add("180天销售额");
		titles.add("新用户投资270天金额");
		titles.add("270天销售额");
		titles.add("新用户投资365天金额");
		titles.add("365天销售额");
		titleMap.put("title", titles);
		return titleMap;
	}
}
