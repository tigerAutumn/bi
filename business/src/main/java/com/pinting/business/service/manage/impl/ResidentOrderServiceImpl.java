package com.pinting.business.service.manage.impl;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.util.redisUtil;
import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.dao.BsPayResultQueueMapper;
import com.pinting.business.dao.LnPayOrdersMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsPayOrders;
import com.pinting.business.model.BsPayOrdersExample;
import com.pinting.business.model.BsPayResultQueue;
import com.pinting.business.model.LnPayOrders;
import com.pinting.business.model.LnPayOrdersExample;
import com.pinting.business.model.vo.LoanNoticeVO;
import com.pinting.business.service.manage.ResidentOrderService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.StringUtil;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 剑钊
 *
 * @2016/10/9 15:37.
 */
@Service
public class ResidentOrderServiceImpl implements ResidentOrderService {
	
	Logger log = LoggerFactory.getLogger(ResidentOrderServiceImpl.class);
	
	@Autowired
	private BsPayOrdersMapper bsPayOrdersMapper;
	
	@Autowired
	private LnPayOrdersMapper lnPayOrdersMapper;
	
    @Autowired
    private BsPayResultQueueMapper queueMapper;

    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Override
    public List<LoanNoticeVO> residentOrderManage(String orderNo) {

        Long len = jsClientDaoSupport.llen("process_order");
        if (len != null && len > 0) {
        	try{
        		List<LoanNoticeVO> loanNoticeVOList = new ArrayList<>();
                List<String> items = jsClientDaoSupport.getListOfOriString("process_order");
                for(String item :items){
                	JSONObject jsonObject = JSONObject.fromObject(item);
                	String orderNoRedis = jsonObject.getString("orderNo");
                	JSONObject orderDetail = jsonObject.getJSONObject("orderDetail");
                	if(StringUtil.isEmpty(orderNoRedis) || orderDetail.isEmpty()){
                		log.warn("宝付处理中队列数据为空,"+item);
                		continue;
                	}
                	//判断是否根据订单号查询
                    if (StringUtils.isNotBlank(orderNo)) {
                    	if(orderNo.equals(orderNoRedis)){
                    		LoanNoticeVO loanNoticeVO = (LoanNoticeVO) JSONObject.toBean(orderDetail, LoanNoticeVO.class);
                            if (loanNoticeVO != null){
                                loanNoticeVOList.add(loanNoticeVO);
                    		}
                    	}else{
                    		continue;
                    	}
                    } else {
                        LoanNoticeVO loanNoticeVO = (LoanNoticeVO) JSONObject.toBean(orderDetail, LoanNoticeVO.class);
                        if (loanNoticeVO != null) {
                            loanNoticeVOList.add(loanNoticeVO);
                        }else{
                        	continue;
                        }
                    }
                }
                return loanNoticeVOList;
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        return null;
    }

    @Override
    public boolean deleteResidentOrder(String orderNo) {

        if (StringUtils.isBlank(orderNo)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号为空");
        }
        try{
        	List<String> items = jsClientDaoSupport.getListOfOriString("process_order");
            for(String item :items){
            	JSONObject jsonObject = JSONObject.fromObject(item);
            	String orderNoRedis = jsonObject.getString("orderNo");
            	JSONObject orderDetail = jsonObject.getJSONObject("orderDetail");
            	if(StringUtil.isEmpty(orderNoRedis) || orderDetail.isEmpty()){
            		log.warn("宝付处理中队列数据为空,"+item);
            		continue;
            	}
            	if(orderNoRedis.equals(orderNo)){
                    LoanNoticeVO loanNoticeVO = (LoanNoticeVO) JSONObject.toBean(orderDetail, LoanNoticeVO.class);
                    if(loanNoticeVO==null){
                        throw  new PTMessageException(PTMessageEnum.NO_DATA_FOUND,"找不到订单");
                    }
                    Long result = jsClientDaoSupport.lrem("process_order", item, 0);
                    if(result>0){
                        return true;
                    }else {
                        return false;
                    }
            	}else{
            		continue;
            	}
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
		return false;
    }

	@Override
	public int addResidentOrder(String orderNo,Integer orderStatus) {
		
        if (StringUtils.isBlank(orderNo)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号为空");
        }
        BsPayOrdersExample bsExample = new BsPayOrdersExample();
        bsExample.createCriteria().andOrderNoEqualTo(orderNo).andStatusEqualTo(Constants.ORDER_STATUS_PAYING);
        List<BsPayOrders> bsPayOrders = bsPayOrdersMapper.selectByExample(bsExample);
        LoanNoticeVO vo = new LoanNoticeVO();
        if(bsPayOrders == null || bsPayOrders.size() == 0){
        	LnPayOrdersExample lnExample = new LnPayOrdersExample();
        	lnExample.createCriteria().andOrderNoEqualTo(orderNo).andStatusEqualTo(Constants.ORDER_STATUS_PAYING);
        	List<LnPayOrders> lnPayOrders = lnPayOrdersMapper.selectByExample(lnExample);
        	if(lnPayOrders == null || lnPayOrders.size() == 0){
        		return 0;
        	}else{
        		vo.setPayOrderNo(lnPayOrders.get(0).getOrderNo());
                vo.setChannel(lnPayOrders.get(0).getChannel());
                vo.setChannelTransType(lnPayOrders.get(0).getChannelTransType());
                vo.setTransType(lnPayOrders.get(0).getTransType());
                vo.setStatus(orderStatus);
                vo.setAmount(lnPayOrders.get(0).getAmount().toString());
                redisUtil.rpushRedis(vo);

                //并插入到消息队列表中
                BsPayResultQueue queue = new BsPayResultQueue();
                queue.setChannel(lnPayOrders.get(0).getChannel());
                queue.setChannelTransType(lnPayOrders.get(0).getChannelTransType());
                queue.setCreateTime(new Date());
                queue.setDealNum(0);
                queue.setOrderNo(lnPayOrders.get(0).getOrderNo());
                queue.setStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                queue.setTransType(lnPayOrders.get(0).getTransType());
                queue.setUpdateTime(new Date());
                queueMapper.insertSelective(queue);
        	}
        }else{
        	//放到redis
        	vo.setPayOrderNo(bsPayOrders.get(0).getOrderNo());
            vo.setChannel(bsPayOrders.get(0).getChannel());
            if(bsPayOrders.get(0).getTransType().equals(Constants.TRANS_TOP_UP) && bsPayOrders.get(0).getChannelTransType().equals(LoanStatus.CHANNEL_TRANS_TYPE_QUICK.getCode())){
            	vo.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_AUTH.getCode());
            }else{
            	vo.setChannelTransType(bsPayOrders.get(0).getChannelTransType());
            }
            vo.setTransType(bsPayOrders.get(0).getTransType());
            vo.setStatus(orderStatus);
            vo.setAmount(bsPayOrders.get(0).getAmount().toString());
            redisUtil.rpushRedis(vo);
            //并插入到消息队列表中
            BsPayResultQueue queue = new BsPayResultQueue();
            queue.setChannel(bsPayOrders.get(0).getChannel());
            queue.setChannelTransType(bsPayOrders.get(0).getChannelTransType());
            queue.setCreateTime(new Date());
            queue.setDealNum(0);
            queue.setOrderNo(bsPayOrders.get(0).getOrderNo());
            queue.setStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
            queue.setTransType(bsPayOrders.get(0).getTransType());
            queue.setUpdateTime(new Date());
            queueMapper.insertSelective(queue);
        }
        return 1;
	}
}
