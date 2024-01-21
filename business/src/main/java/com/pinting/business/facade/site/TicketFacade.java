package com.pinting.business.facade.site;

import com.pinting.business.dao.BsProductMapper;
import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.BsProduct;
import com.pinting.business.service.site.ProductService;
import com.pinting.business.service.site.RedPacketService;
import com.pinting.business.service.site.TicketInterestService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cyb on 2018/3/30.
 */
@Component("Ticket")
public class TicketFacade {

    @Autowired
    private RedPacketService redPacketService;

    @Autowired
    private TicketInterestService ticketInterestService;

    @Autowired
    private ProductService productService;

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
                res.setCount(redRes.getCount());
                res.setIsSupport(product.getIsSupportRedPacket());
            } else if(Constants.TICKET_INTEREST_TYPE_INTEREST_TICKET.equals(req.getType()) && Constants.IS_SUPPORT_TICKET_INTEREST_TRUE.equals(res.getIsSupportInterestTicket())) {
                // 加息券
                ticketInterestService.ticketInterestList(req, res);
                res.setIsSupport(product.getIsSupportIncrInterest());
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
                DecimalFormat format = new DecimalFormat("#.##");
                redPacketService.queryRedPacketList(redReq, redRes);
                ArrayList<HashMap<String, Object>> dataGrid = redRes.getDataGrid();
                Double full;
                for (HashMap<String, Object> map: dataGrid) {
                    if(map.get("full") instanceof Double) {
                        full = (Double) map.get("full");
                    } else {
                        String fullStr = (String) map.get("full");
                        full = Double.valueOf(fullStr);
                    }
                    if(full.compareTo(10000d) >= 0) {
                        Double fullWan = MoneyUtil.divide(full, 10000d).doubleValue();
                        map.put("full", format.format(fullWan));
                        map.put("isWan", "yes");
                    } else {
                        map.put("full", format.format(full));
                    }
                }
                res.setCount(redRes.getCount());
                res.setUserId(req.getUserId());
                res.setType(req.getType());
                res.setDataList(redRes.getDataGrid());
            } else if(Constants.TICKET_INTEREST_TYPE_INTEREST_TICKET.equals(req.getType())) {
                // 加息券
                ticketInterestService.ticketInterestList(req, res);
            }
        }
    }
}
