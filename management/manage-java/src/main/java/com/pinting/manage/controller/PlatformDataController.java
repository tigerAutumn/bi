package com.pinting.manage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ResMsg_BsUserExcel_BsUserListQuery;
import com.pinting.business.model.vo.Platform4ManageProductVO;
import com.pinting.business.model.vo.Platform4ManageVO;
import com.pinting.business.service.manage.BsSubAccountService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.util.ExportUtil;
import com.pinting.util.ReturnDWZAjax;

@Controller
public class PlatformDataController {

	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	
	@Autowired
	private BsSubAccountService bsSubAccountService;
	
	@RequestMapping("/platformData/index")
	public String platformDataIndex(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model){
		//默认输出年月为当前时间前一个月;
		Date now = new Date();
		Date lastMonth = DateUtil.addMonths(now, -1);
		String year = DateUtil.formatDateTime(lastMonth, "yyyyy");
		String month = DateUtil.formatDateTime(lastMonth, "MM");
		model.put("year", year);
		model.put("month", month);
		return "platformData/index";
	}
	@RequestMapping("/platformData/exportBefore")
	public @ResponseBody Map<Object, Object> platformDataExportBefore(HttpServletRequest request, HttpServletResponse response){
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		if(StringUtil.isNotBlank(year) && StringUtil.isNotBlank(month)){
			String mapKey = year+"-"+month;
			//获取redis中的数据
			Map<String, String> map = (Map<String, String>) jsClientDaoSupport.getHashMapFromObj("Platform4Manage");
	        JSONObject jsonObject = JSONObject.fromObject(map.get(mapKey));
	        if(jsonObject == null || jsonObject.isNullObject()){
	        	return ReturnDWZAjax.fail("无数据");
	        }else{
	        	return ReturnDWZAjax.success("有数据可导出");
	        }
		}else{
			return ReturnDWZAjax.fail("请选择年月");
		}
		
	}
	
	@RequestMapping("/platformData/export")
	public void platformDataExport(HttpServletRequest request, HttpServletResponse response){
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		if(StringUtil.isNotBlank(year) && StringUtil.isNotBlank(month)){
			String mapKey = year+"-"+month;
			//获取redis中的数据
			Map<String, String> map = (Map<String, String>) jsClientDaoSupport.getHashMapFromObj("Platform4Manage");
	        JSONObject jsonObject = JSONObject.fromObject(map.get(mapKey));
	        if(jsonObject == null){
	        	return ;
	        }
	        Platform4ManageVO platform4ManageVO2 =  (Platform4ManageVO)JSONObject.toBean(jsonObject,Platform4ManageVO.class);
	        //模块7--本月各期限计划成交额
	        JSONArray buyJsonGroupList = jsonObject.getJSONArray("buyGroupList");
	        List<Platform4ManageProductVO> buyGroupList = new ArrayList<Platform4ManageProductVO>();
	        if(CollectionUtils.isNotEmpty(buyJsonGroupList)){
	        	 buyGroupList = (List<Platform4ManageProductVO>) JSONArray.toCollection(buyJsonGroupList , Platform4ManageProductVO.class);
	        }
	       
	        //模块12--币港湾土豪列表数据
	        JSONArray richerJsonList = jsonObject.getJSONArray("richerList");
	        List<String> richerList = new ArrayList<String>();
	        if(CollectionUtils.isNotEmpty(richerJsonList)){
	        	richerList = (List<String>)  JSONArray.toCollection(richerJsonList , String.class);
	        }
	        
	        
	        //========================导出数据============================
	        List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
			//设置标题
			list.add(titles());
			
			
			if(platform4ManageVO2 != null) {
				//=====模块1====
				list.add(getDatasMap("operatingDays","平台合规运营（天）",platform4ManageVO2.getOperatingDays()));
				list.add(getDatasMap("totalBuyAmount","累计成交额（元）",platform4ManageVO2.getTotalBuyAmount()));
				list.add(getDatasMap("totalLoanAmount","累计出借额（元）",platform4ManageVO2.getTotalLoanAmount()));
				list.add(getDatasMap("totalIncomeAmount","用户累计收益（元）",platform4ManageVO2.getTotalIncomeAmount()));
				
				//=====模块2====
				list.add(getDatasMap("totalLoanAmount1","自成立以来累计借贷金额（元） ",platform4ManageVO2.getTotalLoanAmount()));
				list.add(getDatasMap("totalLoanNumber","自成立以来累计借贷笔数（笔）",platform4ManageVO2.getTotalLoanNumber()));
				list.add(getDatasMap("currentWaitRepayAmount","当前待还借贷金额（元）",platform4ManageVO2.getCurrentWaitRepayAmount()));
				list.add(getDatasMap("currentWaitRepayNumber","当前待还借贷笔数（笔）",platform4ManageVO2.getCurrentWaitRepayNumber()));
				list.add(getDatasMap("relationBorrowerAmount","关联关系借款余额（元）",platform4ManageVO2.getRelationBorrowerAmount()));
				list.add(getDatasMap("relationBorrowerNumber","关联关系借款余额笔数（笔）",platform4ManageVO2.getRelationBorrowerNumber()));
				
				//=====模块3====
				list.add(getDatasMap("totalLenderNumber","累计出借人数（人）",platform4ManageVO2.getTotalLenderNumber()));
				list.add(getDatasMap("currentLenderNumber","当期出借人数（人）",platform4ManageVO2.getCurrentLenderNumber()));
				list.add(getDatasMap("eachLendTotalAmount","人均累计出借金额（元）",platform4ManageVO2.getEachLendTotalAmount()));
				list.add(getDatasMap("topTenLendAmtProportion","前十大出借人出借余额占比（%）",platform4ManageVO2.getTopTenLendAmtProportion()));
				list.add(getDatasMap("topLendAmtProportion","最大单一出借人出借余额占比（%）",platform4ManageVO2.getTopLendAmtProportion()));
				
				//=====模块4====
				list.add(getDatasMap("totalBorrowerNumber","累计借款人数（人）",platform4ManageVO2.getTotalBorrowerNumber()));
				list.add(getDatasMap("currentBorrowerNumber","当期借款人数（人）",platform4ManageVO2.getCurrentBorrowerNumber()));
				list.add(getDatasMap("eachBorrowTotalAmount","人均累计借款金额（元）",platform4ManageVO2.getEachBorrowTotalAmount()));
				list.add(getDatasMap("topTenBorrowAmtProportion","前十大借款人待还金额占比（%）",platform4ManageVO2.getTopTenBorrowAmtProportion()));
				list.add(getDatasMap("topBorrowAmtProportion","最大单一借款人待还金额占比（%）",platform4ManageVO2.getTopBorrowAmtProportion()));
				
				//=====模块5====
				list.add(getDatasMap("projectOverdueRate","出借人项目逾期率（%）",platform4ManageVO2.getProjectOverdueRate()));
				list.add(getDatasMap("amtOverdueRate","出借人金额逾期率（%）",platform4ManageVO2.getAmtOverdueRate()));
				list.add(getDatasMap("overdueAmount"," 借款人逾期金额（元）",platform4ManageVO2.getOverdueAmount()));
				list.add(getDatasMap("overdueNumber","借款人逾期笔数（笔）",platform4ManageVO2.getOverdueNumber()));
				list.add(getDatasMap("overdueNinnetyDaysAmount","借款人逾期90天以上金额（元）",platform4ManageVO2.getOverdueNinnetyDaysAmount()));
				list.add(getDatasMap("overdueNinnetyDaysNumber","借款人逾期90天以上笔数（笔）",platform4ManageVO2.getOverdueNinnetyDaysNumber()));
				list.add(getDatasMap("totalCompensatoryAmount","累计代偿金额（元）",platform4ManageVO2.getTotalCompensatoryAmount()));
				list.add(getDatasMap("totalCompensatoryNumber","累计代偿笔数（笔）",platform4ManageVO2.getTotalCompensatoryNumber()));
				
				//=====模块6====
				list.add(getDatasMap("monthBuyAmount","本月成交额（元）",platform4ManageVO2.getMonthBuyAmount()));
				list.add(getDatasMap("monthBuyUserNumber","本月成交人数（人）",platform4ManageVO2.getMonthBuyUserNumber()));
				list.add(getDatasMap("monthBuyNumber","本月成交笔数（笔）",platform4ManageVO2.getMonthBuyNumber()));
				list.add(getDatasMap("monthIncomeAmount","本月用户收益（元）",platform4ManageVO2.getMonthIncomeAmount()));
				list.add(getDatasMap("monthLoanAmount","本月借贷金额（元）",platform4ManageVO2.getMonthLoanAmount()));
				list.add(getDatasMap("monthLoanNumber","本月借贷笔数（笔）",platform4ManageVO2.getMonthLoanNumber()));
				
				//=====模块7====
				if(CollectionUtils.isNotEmpty(buyGroupList)){
					NumberTool tool = new NumberTool();
					for (Platform4ManageProductVO product : buyGroupList) {
						list.add(getDatasMap("product"+product.getProductTerm(),"本月"+product.getProductTerm()+"天期限成交额（元）",
								tool.format("0.00", product.getAmount())));
					}
				}
				
				//=====模块8  年龄维度分布====
				list.add(getDatasMap("age18_28Proportion","年龄层次18-28占比（%）",platform4ManageVO2.getAge18_28Proportion()));
				list.add(getDatasMap("age29_39Proportion","年龄层次29-39占比（%）",platform4ManageVO2.getAge29_39Proportion()));
				list.add(getDatasMap("age40_50Proportion","年龄层次40-50占比（%）",platform4ManageVO2.getAge40_50Proportion()));
				list.add(getDatasMap("age50MoreProportion","年龄层次50+占比（%）",platform4ManageVO2.getAge50MoreProportion()));
				
				//=====模块9====
				list.add(getDatasMap("pcProportion","网页版端口占比（%）",platform4ManageVO2.getPcProportion()));
				list.add(getDatasMap("h5Proportion","H5端口占比（%）",platform4ManageVO2.getH5Proportion()));
				list.add(getDatasMap("appProportion","app端口占比（%）",platform4ManageVO2.getAppProportion()));
				
				//=====模块10====
				list.add(getDatasMap("sexMaleProportion","性别男占比（%）",platform4ManageVO2.getSexMaleProportion()));
				list.add(getDatasMap("sexFemaleProportion","性别女占比（%）",platform4ManageVO2.getSexFemaleProportion()));
				
				//=====模块11====
				list.add(getDatasMap("mostDayBuyAmount","单日最高成交额（元）",platform4ManageVO2.getMostDayBuyAmount()));
				list.add(getDatasMap("mostOneBuyAmount","单笔最高成交额（元）",platform4ManageVO2.getMostOneBuyAmount()));
				list.add(getDatasMap("fastestSecond","最快满标时间（秒）",platform4ManageVO2.getFastestSecond()));
				list.add(getDatasMap("mostBuyTimes","成交次数最多（次）",platform4ManageVO2.getMostBuyTimes()));
				
				//=====模块12====
				if(CollectionUtils.isNotEmpty(richerList)){
					for(int i=1;i<=richerList.size();i++){
						list.add(getDatasMap("richer"+i,"币港湾土豪第"+i+"名",richerList.get(i-1)));
					}
				}
				
				try {
					ExportUtil.exportExcel(response, request, "平台数据-运营报告_"+mapKey, list);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	private Map<String, List<Object>> getDatasMap(String key,
			String note, Object data) {
		Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
		List<Object> obj = new ArrayList<Object>();
		obj.add(note);
		obj.add(data);
		datasMap.put(key, obj);
		return datasMap;
	}


	private Map<String, List<Object>> titles(){
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("字段");
		titles.add("导出数据");
		titleMap.put("title", titles);
		return titleMap;
	}
	
	/**
	 * 平台存量数据
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/platformData/inventoryDataIndex")
	public String platformInventoryDataIndex(String queryFlag, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model){
		if (StringUtil.isEmpty(queryFlag)) {
			return "platformData/inventory_data_index";
		} else {
			String endTime = StringUtil.isEmpty(request.getParameter("endTime"))? DateUtil.format(new Date()) : request.getParameter("endTime");
			String partnerCode = StringUtil.isEmpty(request.getParameter("partnerCode"))? "" : request.getParameter("partnerCode");
			String productType = StringUtil.isEmpty(request.getParameter("productType"))? "" : request.getParameter("productType");
			Double financesAuthBalance = bsSubAccountService.sumFinancesAuthBalance(endTime, productType);
			Double loanRepayBalance = bsSubAccountService.sumLoanRepayBalance(endTime, partnerCode);
			model.put("financesAuthBalance", financesAuthBalance);
			model.put("loanRepayBalance", loanRepayBalance);
			model.put("endTime", endTime);
			model.put("partnerCode", partnerCode);
			model.put("productType", productType);
			return "platformData/inventory_data_index";
		}
	}
	
}
