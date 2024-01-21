package com.pinting.manage.controller.mall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.mall.enums.MallPropertyEnum;
import com.pinting.mall.enums.MallSendStatusEnum;
import com.pinting.mall.model.MallSendCommodity;
import com.pinting.mall.model.common.PagerModelRspDTO;
import com.pinting.mall.model.common.PagerReqDTO;
import com.pinting.mall.model.vo.MallExchangeOrdersCommodityVO;
import com.pinting.mall.model.vo.MallExchangeOrdersVO;
import com.pinting.mall.service.MallExchangeOrdersService;
import com.pinting.mall.service.MallSendCommodityService;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.ExportUtil;
import com.pinting.util.ReturnDWZAjax;

/**
 * 订单管理
 * @author SHENGUOPING
 * @date  2018年5月14日 下午4:33:42
 */
@Controller
public class MallExchangeOrdersController {
	
	@Autowired
	private MallExchangeOrdersService mallExchangeOrdersService;
	@Autowired
	private MallSendCommodityService mallSendCommodityService;
	
	/**
	 * 订单管理list
	 * @param request
	 * @param response
	 * @param model
     * @return
     */
	@RequestMapping("/mallExchangeOrders/findList")
	public String findOrdersList(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute PagerReqDTO pagerReq, Map<String, Object> model) {
		String startTime = request.getParameter("exchangeStartTime");
		String endTime = request.getParameter("exchangeEndTime");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		String queryFlag = request.getParameter("queryFlag");
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
		}
	
		MallExchangeOrdersVO record = new MallExchangeOrdersVO();
		String commName = request.getParameter("commName");
		String commProperty = request.getParameter("commProperty");
		String mobile = request.getParameter("mobile");
		String status = request.getParameter("status");
		String deliveryInfo = request.getParameter("deliveryInfo");
		
		if (StringUtils.isNotEmpty(commName)) {
			record.setCommName(commName.trim());
		}
		if (StringUtils.isNotEmpty(commProperty)) {
			record.setCommProperty(commProperty);
		}
		if (StringUtils.isNotEmpty(mobile)) {
			record.setMobile(mobile.trim());
		}
		if (StringUtils.isNotEmpty(queryFlag)) {
			record.setStatus(status);
		} else {
			record.setStatus(MallSendStatusEnum.STAY_SEND.getCode());
		}
		if (StringUtils.isNotEmpty(startTime)) {			
			record.setExchangeStartTime(startTime);
		}
		if (StringUtils.isNotEmpty(endTime)) {			
			record.setExchangeEndTime(endTime);
		}
		if (StringUtils.isNotEmpty(deliveryInfo)) {			
			record.setDeliveryInfo(deliveryInfo.trim());
		}
		
		PagerModelRspDTO pageList = mallExchangeOrdersService.findManageExchangeOrdersList(record, pagerReq);
		model.put("pageList", pageList);
		
		// 将数据返回给页面
		model.put("req", record);
		return "/mall/mallExchangeOrders/index";
	}
	
	/**
	 * 订单管理 导出excel
	 * @param request
	 * @param response
     */
	@RequestMapping("/mallExchangeOrders/exportXls")
	public void mallExchangeOrdersExportExcel(@ModelAttribute PagerReqDTO pagerReq, HttpServletRequest request, HttpServletResponse response) {
		MallExchangeOrdersVO record = new MallExchangeOrdersVO();
		String commName = request.getParameter("commName");
		String commProperty = request.getParameter("commProperty");
		String mobile = request.getParameter("mobile");
		String status = request.getParameter("status");
		String startTime = request.getParameter("exchangeStartTime");
		String endTime = request.getParameter("exchangeEndTime");
		String deliveryInfo = request.getParameter("deliveryInfo");
		
		// 
		if (StringUtils.isNotEmpty(commName)) {
			record.setCommName(commName.trim());
		}
		if (StringUtils.isNotEmpty(commProperty)) {
			record.setCommProperty(commProperty);
		}
		if (StringUtils.isNotEmpty(mobile)) {
			record.setMobile(mobile.trim());
		}
		if (StringUtils.isNotEmpty(status)) {
			record.setStatus(status);
		}
		if (StringUtils.isNotEmpty(startTime)) {			
			record.setExchangeStartTime(startTime);
		}
		if (StringUtils.isNotEmpty(endTime)) {			
			record.setExchangeEndTime(endTime);
		}
		if (StringUtils.isNotEmpty(deliveryInfo)) {			
			record.setDeliveryInfo(deliveryInfo);
		}

		int count = mallExchangeOrdersService.countManageExchangeOrders(record);
		pagerReq.setPageNum(Constants.MANAGE_DEFAULT_PAGENUM);
		pagerReq.setNumPerPage(count);
		pagerReq.setCount(false); // 已统计数据，后续查询数据列表，不在统计
		List<MallExchangeOrdersVO> resultList = new ArrayList<>();

		if (count > 0) {
			PagerModelRspDTO pageList = mallExchangeOrdersService.findManageExchangeOrdersList(record, pagerReq);
			resultList = pageList.getList();
		}

		List<Map<String,List<Object>>> excelList = new ArrayList<Map<String,List<Object>>>();
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("序号");
		titles.add("兑换时间");
		titles.add("商品名称");
		titles.add("商品类别");
		titles.add("属性");
		titles.add("支出积分");
		titles.add("手机号");
		titles.add("收货信息");
		titles.add("状态");
		titles.add("发货时间");
		titleMap.put("title", titles);
		excelList.add(titleMap);

		if(!CollectionUtils.isEmpty(resultList)) {
			int i = 0; int j = 0;
			for (MallExchangeOrdersVO data : resultList) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(++j);
				obj.add(data.getExchangeTime() == null ? "" : com.pinting.core.util.DateUtil.format(data.getExchangeTime()));
				obj.add(data.getCommName());
				obj.add(data.getCommTypeName());
				if (MallPropertyEnum.REAL.getCode().equals(data.getCommProperty())) {
					obj.add("实体");
				} else if (MallPropertyEnum.EMPTY.getCode().equals(data.getCommProperty())) {
					obj.add("虚拟");
				}
				obj.add(data.getPayPoints()==null ? 0: data.getPayPoints());
				obj.add(StringUtil.isEmpty(data.getMobile()) ? "" : data.getMobile());		
				obj.add((com.pinting.business.util.Constants.MALL_COMMODITY_TYPE_RED_PACKET.equals(data.getCommTypeCode()) ||
						com.pinting.business.util.Constants.MALL_COMMODITY_TYPE_INTEREST_TACKET.equals(data.getCommTypeCode())) ? "" : data.getDeliveryInfo()); 
				if (MallSendStatusEnum.STAY_SEND.getCode().equals(data.getStatus())) {
					obj.add("未发货");
				} else if (MallSendStatusEnum.FINISHED.getCode().equals(data.getStatus())) {
					obj.add("已发货");
				}
				obj.add((data.getDeliveryTime() == null || !MallSendStatusEnum.FINISHED.getCode().equals(data.getStatus()))
						? "" : com.pinting.core.util.DateUtil.format(data.getDeliveryTime()));
				datasMap.put("mallExchangeOrders"+(++i), obj);
				excelList.add(datasMap);
			}
		}
		try {
			ExportUtil.exportExcel(response, request, "积分商城订单管理导出", excelList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
     * 进入发货展示页面
     * @param cacheKey
     * @return
     */
    @RequestMapping("/mallExchangeOrders/deliveryInfoPage")
    public String deliveryInfo(@RequestParam String orderId, Map<String, Object> model) {
    	model.put("orderId", orderId);
    	model.put("deliveryVO", mallExchangeOrdersService.selectByOrderId(Integer.parseInt(orderId)));
        return "/mall/mallExchangeOrders/delivery_detail";
    }
    
    /**
     * 进入发货信息查看页面
     * @param cacheKey
     * @return
     */
    @RequestMapping("/mallExchangeOrders/viewDeliveryPage")
    public String viewDelivery(@RequestParam String orderId, Map<String, Object> model) {
        model.put("deliveryVO", mallSendCommodityService.findByOrderId(Integer.parseInt(orderId)));
        return "/mall/mallExchangeOrders/view_delivery";
    }
    
    /**
	 * 订单发货
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping("/mallExchangeOrders/sendCommodity")
	public @ResponseBody Map<Object, Object> ordersSendCommodityUpdate(@RequestParam(value = "orderIds[]") String[] orderIds,
			String note, String deliveryNote, String operateFlag, HttpServletRequest request, HttpServletResponse response) {
		CookieManager cookie = new CookieManager(request);
		String userId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		if (orderIds != null && orderIds.length > 0) {
			for (String orderId : orderIds) {
				MallSendCommodity mallSendCommodity = mallSendCommodityService.findByOrderId(Integer.parseInt(orderId));
				if (mallSendCommodity != null) {
					// 红包和加息券判断
					MallExchangeOrdersCommodityVO exchangeOrdersCommodityVO = mallExchangeOrdersService.selectByOrderId(Integer.parseInt(orderId));
					if (exchangeOrdersCommodityVO != null && (com.pinting.business.util.Constants.MALL_COMMODITY_TYPE_RED_PACKET.equals(exchangeOrdersCommodityVO.getCommTypeCode()) ||
							com.pinting.business.util.Constants.MALL_COMMODITY_TYPE_INTEREST_TACKET.equals(exchangeOrdersCommodityVO.getCommTypeCode())) && 
							!com.pinting.business.util.Constants.MALL_EXCHANGE_ORDERS_STATUS_SEND_FAIL.equals(exchangeOrdersCommodityVO.getOrderStatus()) && 
							!"modify".equals(operateFlag)) {
						return ReturnDWZAjax.fail("发货列表中有非未发货状态的订单，请核对！");
					} else {						
						if (!MallSendStatusEnum.STAY_SEND.getCode().equals(mallSendCommodity.getStatus()) && 
								!"modify".equals(operateFlag)) {
							return ReturnDWZAjax.fail("发货列表中有非未发货状态的订单，请核对！");
						}
					}
					Boolean isSuccess = mallExchangeOrdersService.sendCommodity(userId, mallSendCommodity, 
							note, deliveryNote, operateFlag);
					if (!isSuccess) {
						return ReturnDWZAjax.fail("操作失败！");
					}
				} else {
					return ReturnDWZAjax.fail("操作失败，无此订单！");
				}
			}
			return ReturnDWZAjax.success("操作成功！");
		} else {
			return ReturnDWZAjax.fail("没有要发货的订单！");
		}
	}
	
    
    /**
     * 进入批量发货展示页面
     * @param cacheKey
     * @return
     */
    @RequestMapping("/mallExchangeOrders/batchDeliveryInfoPage")
    public String batchDeliveryInfo(String orderIds, Map<String, Object> model) {
    	model.put("orderIds", orderIds);
        return "/mall/mallExchangeOrders/batchDelivery_detail";
    }
    
	/**
	 * 批量发货
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/mallExchangeOrders/batchSendCommodity")
	public @ResponseBody Map<Object, Object> orderBatchSendCommodity(String orderIds,
			String note, String deliveryNote, String operateFlag, HttpServletRequest request, HttpServletResponse response) {
		CookieManager cookie = new CookieManager(request);
		String userId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		String[] orderIdArray = orderIds.split(",");
		if (orderIdArray != null && orderIdArray.length > 0) {
			for (String orderId : orderIdArray) {
				MallExchangeOrdersCommodityVO exchangeOrdersCommodityVO = mallExchangeOrdersService.selectByOrderId(Integer.parseInt(orderId));
				if("batchSend".equals(operateFlag) && (MallPropertyEnum.REAL.getCode().equals(exchangeOrdersCommodityVO.getCommProperty()) || 
						com.pinting.business.util.Constants.MALL_COMMODITY_TYPE_RED_PACKET.equals(exchangeOrdersCommodityVO.getCommTypeCode()) ||
						com.pinting.business.util.Constants.MALL_COMMODITY_TYPE_INTEREST_TACKET.equals(exchangeOrdersCommodityVO.getCommTypeCode()) )) {
					return ReturnDWZAjax.fail("实体商品、红包、加息券不支持批量发货，请核对！");
				}
				MallSendCommodity mallSendCommodity = mallSendCommodityService.findByOrderId(Integer.parseInt(orderId));
				if (mallSendCommodity != null) {
					if (!MallSendStatusEnum.STAY_SEND.getCode().equals(mallSendCommodity.getStatus()) && 
							!"modify".equals(operateFlag)) {
						return ReturnDWZAjax.fail("发货列表中有非未发货状态的订单，请核对！");
					}
					Boolean isSuccess = mallExchangeOrdersService.sendCommodity(userId, mallSendCommodity, 
							note, deliveryNote, operateFlag);
					if (!isSuccess) {
						return ReturnDWZAjax.fail("操作失败！");
					}
				} else {
					return ReturnDWZAjax.fail("操作失败，无此订单！");
				}
			}
			return ReturnDWZAjax.success("操作成功！");
		} else {
			return ReturnDWZAjax.fail("没有要发货的订单！");
		}
	}
	
}
