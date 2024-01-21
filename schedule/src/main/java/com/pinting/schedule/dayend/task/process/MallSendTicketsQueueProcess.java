package com.pinting.schedule.dayend.task.process;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.service.site.RedPacketService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.service.site.TicketInterestService;
import com.pinting.business.util.Constants;
import com.pinting.core.exception.PTException;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.mall.dao.MallSendCommodityMapper;
import com.pinting.mall.model.MallExchangeOrders;
import com.pinting.mall.model.MallSendCommodity;
import com.pinting.mall.model.MallSendCommodityExample;
import com.pinting.mall.model.vo.MallExchangeOrdersCommodityVO;
import com.pinting.mall.service.MallExchangeOrdersService;
import com.pinting.schedule.enums.PTMessageEnum;

/**
 * 红包、优惠券-积分商城兑换成功后发放MallSendTicketsTask调用的线程
 * @project schedule
 * @author bianyatian
 * @2018-5-14 下午1:51:31
 */
public class MallSendTicketsQueueProcess implements Runnable{

	private Logger log = LoggerFactory.getLogger(MallSendTicketsQueueProcess.class);
	
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	
	private Integer orderId;
	
	private MallExchangeOrdersService mallExchangeOrdersService;
	
	private RedPacketService redPacketService;
	
	private TicketInterestService ticketInterestService;
	
    private SpecialJnlService specialJnlService;
    
    private MallSendCommodityMapper mallSendCommodityMapper;
	
	@Override
	public void run() {
		//线程开始，存放redis数据（正在进行中的）
		Map<String, String> map = jsClientDaoSupport.getHashMapFromObj("mall_send_tickets_process");
		
		if (MapUtils.isEmpty(map) || map.size() == 0) {
			map = new HashMap<>();
		}
		map.put(orderId.toString(), orderId.toString());
		
		jsClientDaoSupport.addObjOfHashMap(map, "mall_send_tickets_process", -1);
		
		try {
			if(orderId != null){
				MallExchangeOrdersCommodityVO exchangeOrdersCommodityVO = mallExchangeOrdersService.selectByOrderId(orderId);
				if(exchangeOrdersCommodityVO == null){
					log.info(">>>【红包和加息券】发放定时：未查到商城订单数据");
					return;
				}
				if(Constants.MALL_EXCHANGE_ORDERS_STATUS_SUCCESS.equals(exchangeOrdersCommodityVO.getOrderStatus())){
					
					if(Constants.MALL_COMMODITY_TYPE_RED_PACKET.equals(exchangeOrdersCommodityVO.getCommTypeCode())){
						//根据红包名称发放
						try {
							//jsClientDaoSupport.lock(RedisLockEnum.MALL_SEND_RED_PACKET.getKey()+exchangeOrdersCommodityVO.getCommName());
							//发放红包
							boolean flag = redPacketService.sendMallRedPacketByName(exchangeOrdersCommodityVO.getUserId(), 
									exchangeOrdersCommodityVO.getCommName(), exchangeOrdersCommodityVO.getOrderSuccTime());
							//修改订单状态，修改发货状态
							updateOrderStatus(exchangeOrdersCommodityVO.getOrderId(), flag, exchangeOrdersCommodityVO.getOrderNo());
						} catch (Exception e) {
							log.error("【红包和加息券】发放队列线程执行异常:红包发放异常", e);
						} 
					}else if(Constants.MALL_COMMODITY_TYPE_INTEREST_TACKET.equals(exchangeOrdersCommodityVO.getCommTypeCode())){
						//根据优惠券名称发放
						try {
							//jsClientDaoSupport.lock(RedisLockEnum.MALL_SEND_TICKET.getKey()+exchangeOrdersCommodityVO.getCommName());
							//发放加息券
							boolean flag = ticketInterestService.sendMallTicketByName(exchangeOrdersCommodityVO.getUserId(), 
									exchangeOrdersCommodityVO.getCommName(), exchangeOrdersCommodityVO.getOrderSuccTime());
							//修改订单状态，修改发货状态
							updateOrderStatus(exchangeOrdersCommodityVO.getOrderId(), flag, exchangeOrdersCommodityVO.getOrderNo());
						} catch (Exception e) {
							log.error("【红包和加息券】发放队列线程执行异常:红包发放异常", e);
						}
					}
				}else{
					log.info(">>>【红包和加息券】发放定时：商城订单状态为："+exchangeOrdersCommodityVO.getOrderStatus()+",不进行发放");
					//入告警表信息
					specialJnlService.warn4FailNoSMS(null,
                            "积分商城-红包和加息券发放，订单：" +  exchangeOrdersCommodityVO.getOrderNo() + "失败，商城订单状态不正确",  exchangeOrdersCommodityVO.getOrderNo(), "积分商城-红包和加息券发放失败");
					throw new PTException(PTMessageEnum.DATA_VALIDATE_ERROR.getCode(),"商城订单状态不正确");
				}
				
			}else{
				log.info(">>>【红包和加息券】发放队列lpop为空<<<");
			}
		} catch (Exception e) {
			log.error("【红包和加息券】发放队列线程执行异常", e);
		} finally {
			//删除redis数据
			jsClientDaoSupport.deleteObjOfHashMap(orderId.toString(),	"mall_send_tickets_process");
		}
	}


	/**
	 * 修改订单表订单信息及发货表发货状态
	 * @author bianyatian
	 * @param orderId
	 * @param flag
	 * @param orderNo
	 */
	private void updateOrderStatus(Integer orderId, boolean flag, String orderNo) {
		String orderStatus = Constants.MALL_EXCHANGE_ORDERS_STATUS_SEND_FAIL;
		if(flag) {
			orderStatus = Constants.MALL_EXCHANGE_ORDERS_STATUS_FINISHED;
			//修改发货状态
			MallSendCommodityExample example = new MallSendCommodityExample();
			example.createCriteria().andOrderIdEqualTo(orderId);
			List<MallSendCommodity> list = mallSendCommodityMapper.selectByExample(example);
			if(CollectionUtils.isNotEmpty(list)){
				MallSendCommodity temp = new MallSendCommodity();
				temp.setId(list.get(0).getId());
				temp.setDeliveryNote("请在我的账户—优惠券中查询已兑换的优惠券");
				temp.setStatus(com.pinting.mall.util.Constants.SEND_COMMODITY_STATUS_FINISHED);
				temp.setUpdateTime(new Date());
				mallSendCommodityMapper.updateByPrimaryKeySelective(temp);
			}
		}else{
			//告警表插入
			specialJnlService.warn4FailNoSMS(null,
                    "积分商城-红包和加息券发放，订单：" + orderNo + "失败", orderNo, "积分商城-红包和加息券发放失败");
		}
		MallExchangeOrders exchangeOrders = new MallExchangeOrders();
		exchangeOrders.setId(orderId);
		exchangeOrders.setOrderStatus(orderStatus);
		exchangeOrders.setUpdateTime(new Date());
		mallExchangeOrdersService.updateMallExchangeOrders(exchangeOrders);
	}


	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public void setMallExchangeOrdersService(MallExchangeOrdersService mallExchangeOrdersService) {
		this.mallExchangeOrdersService = mallExchangeOrdersService;
	}

	public void setRedPacketService(RedPacketService redPacketService) {
		this.redPacketService = redPacketService;
	}

	public void setTicketInterestService(TicketInterestService ticketInterestService) {
		this.ticketInterestService = ticketInterestService;
	}

	public void setSpecialJnlService(SpecialJnlService specialJnlService) {
		this.specialJnlService = specialJnlService;
	}

	public void setMallSendCommodityMapper(MallSendCommodityMapper mallSendCommodityMapper) {
		this.mallSendCommodityMapper = mallSendCommodityMapper;
	}

	
}
