package com.pinting.manage.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.util.ExportUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.hessian.manage.message.ReqMsg_BsActivityLuckyDrawDetail_BsActivityLuckyBonusList;
import com.pinting.business.hessian.manage.message.ReqMsg_BsActivityLuckyDraw_GetList;
import com.pinting.business.hessian.manage.message.ResMsg_BsActivityLuckyDrawDetail_BsActivityLuckyBonusList;
import com.pinting.business.hessian.manage.message.ResMsg_BsActivityLuckyDraw_GetList;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.util.Constants;

@Controller
public class ActivityLuckyDrawDetailController {
	
	@Autowired
	@Qualifier("dispatchService")
	private HessianService dispatchService;
	
	
	/**
	 * 用户抽奖信息统计
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/userActivityLucky/index")
	public String userActivityLuckyIndex(ReqMsg_BsActivityLuckyDraw_GetList req,
			HttpServletRequest request, Map<String, Object> model) {
		if(StringUtils.isBlank(req.getAgents())){
			model.put("agentSize", 0);
		}else{
			String agent[] = req.getAgents().split(",");
			if(agent[0].equals("-1") || agent[0].equals("")){
				model.put("agentSize", agent.length-1);
			}else{
				model.put("agentSize", agent.length);
			}
		}
		if("N".equals(req.getIsUserDraw()) && "Y".equals(req.getIsWin())){
			req.setIsWin("");
		}
		model.put("agents", req.getAgents());
		ResMsg_BsActivityLuckyDraw_GetList res = (ResMsg_BsActivityLuckyDraw_GetList)dispatchService.handleMsg(req);
		model.put("count", res.getTotalRows());
		model.put("list", res.getValueList());
		model.put("req", req);
		return "/activityLucky/index";
	}

	/**
	 * 导出excel
	 * @param req
	 * @param response
	 * @param request
     */
	@RequestMapping("/userActivityLucky/exportXls")
	public void exportXls(ReqMsg_BsActivityLuckyDraw_GetList req, HttpServletResponse response,
						  HttpServletRequest request) {
		List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
		//设置标题
		Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("活动类型");
		titles.add("中奖时间");
		//加薪计划活动需导出用户id
		if("38".equals(req.getActivityName())) {
			titles.add("用户id");
		}else {
			titles.add("手机号码");
		}
		titles.add("姓名");
		titles.add("性别");
		titles.add("奖品");
		titles.add("交易编号");
		titles.add("渠道");
		titles.add("是否抽奖");
		titles.add("是否中奖");
		//判断是否是加薪计划，若是增加十个字段头
		if("38".equals(req.getActivityName())) {
			titles.add("产品名称");
			titles.add("产品终端");
			titles.add("订单号");
			titles.add("购买时间");
			titles.add("购买金额");
			titles.add("期限");
			titles.add("利率");
			titles.add("年化出借额");
			titles.add("使用红包");
			titles.add("使用加息券");
		}
		titleMap.put("title", titles);
		list.add(titleMap);
		req.setPageNum(1);
		req.setNumPerPage(req.getCount());
		ResMsg_BsActivityLuckyDraw_GetList resp = (ResMsg_BsActivityLuckyDraw_GetList) dispatchService.handleMsg(req);
		List<HashMap<String, Object>> datas = resp.getValueList();
		//设置导出excel内容
		if (datas != null && !datas.isEmpty()) {
			int i = 0;
			//判断是否是加薪计划活动，若是需要导出20个字段
			if("38".equals(req.getActivityName())) {
				for (HashMap<String, Object> data : datas) {
					Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
					List<Object> obj = new ArrayList<Object>();
					obj.add(data.get("activityName"));
					obj.add(data.get("userDrawTime"));
					obj.add(data.get("userId"));
					obj.add(data.get("userName"));
					obj.add(data.get("gender"));
					obj.add(data.get("awardContent"));
					obj.add(data.get("id"));
					obj.add(data.get("agentName"));
					obj.add(data.get("isUserDraw").toString().equals("Y") ? "已抽奖" : "未抽奖");
					obj.add(data.get("isWin").toString().equals("Y") ? "已中奖" : "未中奖");
					obj.add(data.get("productName"));
					obj.add(getAgentNameByCode((String)data.get("showTerminal")));
					obj.add(data.get("orderNo"));
					obj.add(data.get("openTime"));
					obj.add(data.get("purchasingPrice"));
					obj.add(data.get("term"));
					obj.add(data.get("productRate"));
					obj.add(data.get("yearInterest"));
					obj.add(data.get("useRedPacket"));
					obj.add(data.get("useTicket"));
					datasMap.put("user" + (++i), obj);
					list.add(datasMap);
				}
			}else {
				for (HashMap<String, Object> data : datas) {
					Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
					List<Object> obj = new ArrayList<Object>();
					if(data.get("activityName").equals("2017周周乐活动")) {
						obj.add("全民刮刮乐");
					} else {
						obj.add(data.get("activityName"));
					}

					obj.add(data.get("userDrawTime"));
					obj.add(data.get("mobile"));
					obj.add(data.get("userName"));
					obj.add(data.get("gender"));
					obj.add(data.get("awardContent"));
					obj.add(data.get("id"));
					obj.add(data.get("agentName"));
					obj.add(data.get("isUserDraw").toString().equals("Y") ? "已抽奖" : "未抽奖");
					obj.add(data.get("isWin").toString().equals("Y") ? "已中奖" : "未中奖");
					datasMap.put("user" + (++i), obj);
					list.add(datasMap);
				}
			}
			
		}

		try {
			ExportUtil.exportExcel(response, request, "抽奖情况查询", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//根据产品展示终端判断渠道名称
	private String getAgentNameByCode(String terminal) {
		if(terminal!=null&&terminal!="") {
			if(terminal.contains("QHD")) {
				return "秦皇岛系列"+"("+terminal+")";
			}else if(terminal.contains("QD")) {
				return "七店"+"("+terminal+")";
			}else if(terminal.contains("178")||terminal.contains("KQ")||terminal.contains("HN")||terminal.contains("RUIAN")) {
				return "钱报系列"+"("+terminal+")";
			}else {
				return "币港湾"+"("+terminal+")";
			}
		}else {
			return "";
		}
	}

	
	/**
	 * 用户抽奖信息统计--已废除
	 * @param reqMsg
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/activityLucky/index")
	public String bsActivityLuckyBonusInit(ReqMsg_BsActivityLuckyDrawDetail_BsActivityLuckyBonusList reqMsg,
			HttpServletRequest request, Map<String, Object> model) {
		Integer pageNum = reqMsg.getPageNum();
		Integer numPerPage = reqMsg.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			reqMsg.setPageNum(pageNum);
			reqMsg.setNumPerPage(numPerPage);
		}
		String orderDirection = request.getParameter("orderDirection");
		String orderField = request.getParameter("orderField");
		if (orderDirection != null && orderField != null && !"".equals(orderDirection) && !"".equals(orderField)) {
			reqMsg.setOrderDirection(orderDirection);
			reqMsg.setOrderField(orderField);
			model.put("orderDirection", orderDirection);
			model.put("orderField", orderField);
		} else {
			reqMsg.setOrderDirection("desc");
			reqMsg.setOrderField("draw_time"); // 抽奖时间
			model.put("orderDirection", reqMsg.getOrderDirection());
			model.put("orderField", reqMsg.getOrderField());
		}
		if (reqMsg.getQueryDateFlag() !=  null && reqMsg.getQueryDateFlag().equals("QUERYDATE")) {
			ResMsg_BsActivityLuckyDrawDetail_BsActivityLuckyBonusList resMsg = (ResMsg_BsActivityLuckyDrawDetail_BsActivityLuckyBonusList) dispatchService.handleMsg(reqMsg);
			model.put("totalRows", resMsg.getTotalRows());
			model.put("activityLuckyList", resMsg.getValueList());
		} else { // 抽奖查询,查询时间默认为活动开始时间、当天时间
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = sdf.parse("2016-01-29 00:00:00");
				reqMsg.setBeginTime(date);
				reqMsg.setOverTime(new Date());
				ResMsg_BsActivityLuckyDrawDetail_BsActivityLuckyBonusList resMsg = (ResMsg_BsActivityLuckyDrawDetail_BsActivityLuckyBonusList) dispatchService.handleMsg(reqMsg);
				model.put("totalRows", resMsg.getTotalRows());
				model.put("activityLuckyList", resMsg.getValueList());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		model.put("req", reqMsg);
		model.put("pageNum", reqMsg.getPageNum());
		model.put("numPerPage", reqMsg.getNumPerPage());
		return "/activityLucky/index";
	}

}
