package com.pinting.business.accounting.loan.util;

import com.alibaba.fastjson.JSON;
import com.pinting.business.model.vo.LoanNoticeVO;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class redisUtil {
	
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    private static Logger logger = LoggerFactory.getLogger(redisUtil.class);

    /**
     * 处理中订单入redis
     * @param vo
     */
	public static void rpushRedis(LoanNoticeVO vo){
	    try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("orderNo", vo.getPayOrderNo());
            jsonObject.put("orderDetail", JSONObject.fromObject(vo).toString());
            logger.info(">>>入订单队列process_order数据:" + jsonObject.toString() + "<<<");
            jsClientDaoSupport.rpush("process_order", jsonObject.toString());
        }catch (Exception e){
            logger.error("处理中订单入redis异常",e);
        }
	}
}
