package com.pinting.mall.facade;

import com.pinting.mall.enums.PTMessageEnum;
import com.pinting.mall.exception.PTMessageException;
import com.pinting.mall.hessian.site.message.ReqMsg_MallExchange_MallExchangeList;
import com.pinting.mall.hessian.site.message.ReqMsg_MallExchange_mallExchangeDetail;
import com.pinting.mall.hessian.site.message.ResMsg_MallExchange_MallExchangeList;
import com.pinting.mall.hessian.site.message.ResMsg_MallExchange_mallExchangeDetail;
import com.pinting.mall.model.vo.MallExchangeVO;
import com.pinting.mall.service.site.MallExchangeService;
import com.pinting.mall.util.BeanUtils;
import com.pinting.mall.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 积分商城商品兑换facade
 *
 * @author shh
 * @date 2018/5/16 10:45
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Component("MallExchange")
public class MallExchangeFacade {

    @Autowired
    private MallExchangeService mallExchangeService;

    /**
     * 我的兑换列表
     * @param req
     * @param res
     */
    public void mallExchangeList(ReqMsg_MallExchange_MallExchangeList req,
                                 ResMsg_MallExchange_MallExchangeList res){
        List<MallExchangeVO> list = mallExchangeService.queryMallExchangeByUserId(req.getUserIdParam(),
                req.getPageIndex(), req.getPageSize());
        int pageSize=req.getPageSize();
        res.setTotalCount((int)Math.ceil((double)mallExchangeService.queryMallExchangeCountByUserId(req.getUserIdParam())/pageSize));
        res.setMallExchangeData(BeanUtils.classToArrayList(list));
        res.setPageIndex(req.getPageIndex());
    }

    /**
     * 我的兑换详情
     * @param req
     * @param resp
     */
    public void mallExchangeDetail(ReqMsg_MallExchange_mallExchangeDetail req, ResMsg_MallExchange_mallExchangeDetail resp) {
        /* 商品属性：虚拟、实物 */
        if(Constants.COMM_PROPERTY_REAL.equals(req.getCommProperty())) {
            // 1、实物商品
            MallExchangeVO resultVo = mallExchangeService.queryExchangeRealDetailByUserId(req.getUserIdParam(), req.getCommId(), req.getOrderId());
            if(null == resultVo) {
                throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
            }else {
                resp.setCommPictureUrl(resultVo.getCommPictureUrl()); // 商品主图
                resp.setCommName(resultVo.getCommName()); // 商品名称
                resp.setExchangeTime(resultVo.getExchangeTime()); // 兑换时间
                resp.setSendStatus(resultVo.getSendStatus()); // 发货状态
                resp.setCommId(resultVo.getCommId()); // 商品编号
                resp.setPayPoints(resultVo.getPayPoints()); // 支出积分
                resp.setRecName(resultVo.getRecName()); // 收货人姓名
                resp.setRecMobile(resultVo.getRecMobile()); // 收货人电话
                resp.setRecAdressDetail(resultVo.getRecAdressDetail()); // 收货人详细地址
                resp.setOrderStatus(resultVo.getOrderStatus()); // 订单状态
                resp.setSendCommodityTime(resultVo.getSendCommodityTime()); // 发货时间
                resp.setDeliveryNote(resultVo.getDeliveryNote()); // 发货信息
                resp.setCommProperty(resultVo.getCommProperty()); // 商品属性
                resp.setRecAdress(resultVo.getRecAdress()); // 收货人地址省市区 recAdress
            }
        }else if(Constants.COMM_PROPERTY_EMPTY.equals(req.getCommProperty())) {
            // 2、虚拟商品
            MallExchangeVO resultVo = mallExchangeService.queryExchangeEmptyDetailByUserId(req.getUserIdParam(), req.getCommId(), req.getOrderId());
            if(null == resultVo) {
                throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
            }else {
                resp.setCommPictureUrl(resultVo.getCommPictureUrl());
                resp.setCommName(resultVo.getCommName());
                resp.setExchangeTime(resultVo.getExchangeTime());
                resp.setSendStatus(resultVo.getSendStatus());
                resp.setCommId(resultVo.getCommId());
                resp.setPayPoints(resultVo.getPayPoints());
                resp.setOrderStatus(resultVo.getOrderStatus());
                resp.setSendCommodityTime(resultVo.getSendCommodityTime());
                resp.setDeliveryNote(resultVo.getDeliveryNote());
                resp.setCommProperty(resultVo.getCommProperty());
                resp.setMobile(resultVo.getMobile());
                resp.setUserName(resultVo.getUserName());
            }
        }
    }

}
