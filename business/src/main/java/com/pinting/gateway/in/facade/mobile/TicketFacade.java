package com.pinting.gateway.in.facade.mobile;

import com.pinting.business.dao.BsProductMapper;
import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.BsProduct;
import com.pinting.business.service.site.ProductService;
import com.pinting.business.service.site.RedPacketService;
import com.pinting.business.service.site.TicketInterestService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.in.util.InterfaceVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cyb on 2018/4/2.
 */
@Component("appTicket")
public class TicketFacade {

    @Autowired
    private RedPacketService redPacketService;

    @Autowired
    private TicketInterestService ticketInterestService;

    @Autowired
    private ProductService productService;

    @InterfaceVersion("TicketList/1.0.0")
    public void ticketList(ReqMsg_Ticket_TicketList req, ResMsg_Ticket_TicketList res) {
        if (Constants.TICKET_INTEREST_BIZ_TYPE_BUY.equals(req.getBizType())) {
            // 购买入口
            BsProduct product = productService.selectById(req.getProductId());
            res.setIsSupportRed(StringUtil.isBlank(product.getIsSupportRedPacket()) ? Constants.IS_SUPPORT_RED_PACKET_FALSE  : product.getIsSupportRedPacket());
            res.setIsSupportInterestTicket(StringUtil.isBlank(product.getIsSupportIncrInterest()) ? Constants.IS_SUPPORT_TICKET_INTEREST_FALSE : product.getIsSupportIncrInterest());
            if(Constants.TICKET_INTEREST_TYPE_RED_PACKET.equals(req.getType()) && Constants.IS_SUPPORT_RED_PACKET_TRUE.equals(res.getIsSupportRed())) {
                // 红包
                ReqMsg_RedPacketInfo_RedPacketList redReq = new ReqMsg_RedPacketInfo_RedPacketList();
                redReq.setUserId(req.getUserId());
                redReq.setStatus(req.getStatus());  // 状态
                redReq.setAmount(req.getAmount());  // 金额
                redReq.setProductId(req.getProductId());   // 产品ID（便于获取期限，购买时用）
                ResMsg_RedPacketInfo_RedPacketList redRes = new ResMsg_RedPacketInfo_RedPacketList();
                redPacketService.redPacketList(redReq, redRes);
                res.setUserId(req.getUserId());
                res.setType(req.getType());
                res.setDataList(redRes.getDataGrid());
            } else if(Constants.TICKET_INTEREST_TYPE_INTEREST_TICKET.equals(req.getType()) && Constants.IS_SUPPORT_TICKET_INTEREST_TRUE.equals(res.getIsSupportInterestTicket())) {
                // 加息券
                ticketInterestService.ticketInterestList(req, res);
                List<HashMap<String, Object>> list = res.getDataList();
                for(HashMap<String, Object> map : list) {
                    map.put("useTimeStart", DateUtil.formatDateTime(DateUtil.parseDateTime((String)map.get("useTimeStart")), "yyyy-MM-dd"));
                    map.put("useTimeEnd", DateUtil.formatDateTime(DateUtil.parseDateTime((String)map.get("useTimeEnd")), "yyyy-MM-dd"));
                    if(null != map.get("productLimit")) {
                        String limitStr = (String) map.get("productLimit");
                        StringBuffer limit = new StringBuffer();
                        String[] productLimitArray = limitStr.split(",");
                        for (String productLimit: productLimitArray) {
                            if(Constants.PRODUCT_TYPE_BIGANGWAN_SERIAL.equals(productLimit)) {
                                limit.append("港湾系列").append(",");
                            }
                            if(Constants.PRODUCT_TYPE_YONGJIN_SERIAL.equals(productLimit)) {
                                limit.append("涌金系列").append(",");
                            }
                            if(Constants.PRODUCT_TYPE_KUAHONG_SERIAL.equals(productLimit)) {
                                limit.append("跨虹系列").append(",");
                            }
                            if(Constants.PRODUCT_TYPE_BAOXIN_SERIAL.equals(productLimit)) {
                                limit.append("保信系列").append(",");
                            }
                        }
                        limit.delete(limit.length() - 1, limit.length());
                        map.put("productLimit", limit.toString());
                    }
                }
            } else {
                res.setUserId(req.getUserId());
                res.setType(req.getType());
                res.setDataList(new ArrayList<HashMap<String, Object>>());
                res.setCount(0);
            }
        } else if(Constants.TICKET_INTEREST_BIZ_TYPE_TICKET.equals(req.getBizType())) {
            // 普通优惠券列表入口
            if(Constants.TICKET_INTEREST_TYPE_RED_PACKET.equals(req.getType())) {
                // 红包
                ReqMsg_RedPacketInfo_QueryRedPacketList redReq = new ReqMsg_RedPacketInfo_QueryRedPacketList();
                redReq.setUserId(req.getUserId());
                redReq.setStatus(req.getStatus());
                redReq.setPageNum(req.getPageNum());
                redReq.setNumPerPage(req.getNumPerPage());
                ResMsg_RedPacketInfo_QueryRedPacketList redRes = new ResMsg_RedPacketInfo_QueryRedPacketList();
                redPacketService.queryRedPacketList(redReq, redRes);
                ArrayList<HashMap<String, Object>> dataGrid = redRes.getDataGrid();
                for (HashMap<String, Object> map: dataGrid) {
                    if(null != map.get("termLimit")) {
                    	String limitStr = (String) map.get("termLimit");
                        StringBuffer limit = new StringBuffer();
                        limit.append("限"+limitStr+"月产品使用");
                        map.put("termLimit", limit.toString());
                    }
                    map.put("useTimeStart", DateUtil.formatDateTime((Date)map.get("useTimeStart"), "yyyy-MM-dd"));
                    map.put("useTimeEnd", DateUtil.formatDateTime((Date)map.get("useTimeEnd"), "yyyy-MM-dd"));
                }
                res.setCount(redRes.getCount());
                res.setUserId(req.getUserId());
                res.setType(req.getType());
                res.setDataList(redRes.getDataGrid());
            } else if(Constants.TICKET_INTEREST_TYPE_INTEREST_TICKET.equals(req.getType())) {
                // 加息券
                ticketInterestService.ticketInterestList(req, res);
                List<HashMap<String, Object>> list = res.getDataList();
                DecimalFormat format = new DecimalFormat("#.##");
                for(HashMap<String, Object> map : list) {
                	map.put("useTimeStart", DateUtil.formatDateTime((Date)map.get("useTimeStart"), "yyyy-MM-dd"));
                    map.put("useTimeEnd", DateUtil.formatDateTime((Date)map.get("useTimeEnd"), "yyyy-MM-dd"));
                    if(null != map.get("productLimit")) {
                        String limitStr = (String) map.get("productLimit");
                        StringBuffer limit = new StringBuffer();
                        String[] productLimitArray = limitStr.split(",");
		                for (String productLimit: productLimitArray) {
		                    if(Constants.PRODUCT_TYPE_BIGANGWAN_SERIAL.equals(productLimit)) {
		                        limit.append("港湾系列").append(",");
		                    }
		                    if(Constants.PRODUCT_TYPE_YONGJIN_SERIAL.equals(productLimit)) {
		                        limit.append("涌金系列").append(",");
		                    }
		                    if(Constants.PRODUCT_TYPE_KUAHONG_SERIAL.equals(productLimit)) {
		                        limit.append("跨虹系列").append(",");
		                    }
		                    if(Constants.PRODUCT_TYPE_BAOXIN_SERIAL.equals(productLimit)) {
		                        limit.append("保信系列").append(",");
		                    }
		                }
		                limit.delete(limit.length() - 1, limit.length());
		                map.put("productLimit", limit.toString());
                    }
                    if(null != map.get("termLimit")) {
                    	String limitStr = (String) map.get("termLimit");
                        StringBuffer limit = new StringBuffer();
                        limit.append("限"+limitStr+"天产品使用");
                        map.put("termLimit", limit.toString());
                    }
                    map.put("full", format.format(map.get("full")));
                }
            }
        }
    }
}
