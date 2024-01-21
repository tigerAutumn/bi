package com.pinting.mall.aspect;

import com.alibaba.fastjson.JSON;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.mall.dao.MallCommodityInfoMapper;
import com.pinting.mall.dao.MallExchangeOrdersMapper;
import com.pinting.mall.hessian.site.message.ResMsg_MallExchangeFlow_ExchangeCommodity;
import com.pinting.mall.model.MallExchangeOrders;
import com.pinting.mall.model.MallExchangeOrdersExample;
import com.pinting.mall.model.vo.MallCommodityInfoAndTypeVO;
import com.pinting.mall.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * 积分商城兑换商品成功后发放虚拟产品
 * 1. 红包
 * 2. 加息券
 */
@Aspect
@Component
public class ExchangeCommodityResultAspect {

    private Logger log = LoggerFactory.getLogger(ExchangeCommodityResultAspect.class);

    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);


    @Pointcut("execution(public * com.pinting.mall.service.site.impl.MallExchangeFlowServiceImpl.exchangeCommodity(..))")
    public void exchangeBuyPointcut() {
    }

    @Autowired
    private MallExchangeOrdersMapper mallExchangeOrdersMapper;
    @Autowired
    private MallCommodityInfoMapper commInfoMapper;


    @AfterReturning("exchangeBuyPointcut()")
    public void balanceBuyAfter(JoinPoint call) {
        ResMsg_MallExchangeFlow_ExchangeCommodity res = (ResMsg_MallExchangeFlow_ExchangeCommodity) call.getArgs()[1];
        HashMap<String, Object> extendProMap = res.getExtendMap();
        if (extendProMap != null) {
            autoSendCommodity(extendProMap);

            //remove
            extendProMap.remove("orderNo");
        }
        res.setExtendMap(extendProMap);
    }

    /**
     * 自动发放兑换的加息券和红包
     *
     * @param extendProMap
     */
    private void autoSendCommodity(HashMap<String, Object> extendProMap) {

        String orderNo = (String) extendProMap.get("orderNo");

        if (StringUtils.isNotBlank(orderNo)) {
            MallExchangeOrdersExample exchangeOrdersExample = new MallExchangeOrdersExample();
            exchangeOrdersExample.createCriteria().andOrderNoEqualTo(orderNo);
            List<MallExchangeOrders> exchangeOrdersList = mallExchangeOrdersMapper.selectByExample(exchangeOrdersExample);
            if (CollectionUtils.isNotEmpty(exchangeOrdersList)) {
                MallExchangeOrders exchangeOrders = exchangeOrdersList.get(0);
                MallCommodityInfoAndTypeVO infoAndTypeVO = commInfoMapper.selectMallCommodityInfoAndTypeById(exchangeOrders.getCommId());

                if (Constants.COMM_PROPERTY_RED_PACKET.equals(infoAndTypeVO.getCommTypeCode())
                        || Constants.COMM_PROPERTY_INTEREST_TACKET.equals(infoAndTypeVO.getCommTypeCode())) {
                    log.info(">>>自动发放" + infoAndTypeVO.getCommTypeName() + "：入redis走定时-mall_send_ticket_queue:" + exchangeOrders.getId());
                    jsClientDaoSupport.rpush(Constants.MALL_SEND_TICKET_QUEUE_KEY, JSON.toJSONString(exchangeOrders.getId()));
                }
            }
        }
    }
}
