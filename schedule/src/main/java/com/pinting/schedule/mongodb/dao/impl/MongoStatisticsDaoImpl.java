package com.pinting.schedule.mongodb.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SimpleTimeZone;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.mongodb.BasicDBObject;
import com.pinting.business.util.DateUtil;
import com.pinting.schedule.mongodb.model.BaseDBObject;
import com.pinting.schedule.mongodb.service.impl.CustomAggregationOperation;
import com.pinting.schedule.mongodb.util.MongoManage;

@Component
public class MongoStatisticsDaoImpl extends MongoDataBaseImpl {
	
	private Logger logger = LoggerFactory.getLogger(MongoStatisticsDaoImpl.class);

	
	@Resource( name = "mongoTemplate" )
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public JSONArray sumTransAmt(Aggregation aggregation, String collectionName) {
		long startTime = System.currentTimeMillis();
        AggregationResults<BaseDBObject> outputType = mongoTemplate.aggregate(aggregation, collectionName, BaseDBObject.class ); 
        List<BaseDBObject> listSensors =  outputType.getMappedResults();
        JSONArray json = (new MongoManage()).transportMap(listSensors);
        long endTime = System.currentTimeMillis();
        logger.info("耗时:"+(endTime-startTime));
        return json;
	}
	/**
	 * 单表查询
	 * @param collectionName-MongoDB 集合名称, channel 渠道, channelTransType, channel, stt 状态, transDate 日期
	 * 
	 * */
	public JSONArray sumOrdersSummary( String collectionName, String transType, String channel_trans_type, String channel, int stt, Date transDate ) {
		JSONArray sumSummary = null;
		try {
			String checkDate1 = DateUtil.getDate(DateUtils.addDays(transDate, -1));
			String checkDate2 = DateUtil.getDate(DateUtils.addDays(transDate, 0));
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			format.setCalendar(new GregorianCalendar(new SimpleTimeZone(8, "GMT")));
			
			Date D1 = format.parse(checkDate1);
			Date D2 = format.parse(checkDate2);
			Criteria arrCri = Criteria.where("trans_type").is(transType)
						.and("status").is(stt).and("update_time").gte(D1).lt(D2);
			
			if( !"".equals(channel_trans_type) ) {
				arrCri.and("channel_trans_type").is(channel_trans_type);
			}
			
			if( !"".equals(channel) ) {
				arrCri.and("channel").is(channel);
			}
			
			logger.info("业务"+transType+"查询条件="+arrCri.getCriteriaObject());
			
			Aggregation agg = Aggregation.newAggregation( 
					Aggregation.match( arrCri ),//查询条件
					Aggregation.group("trans_type").sum("amount").as("total").count().as("count"),
					Aggregation.project("count","total").and("trans_type").previousOperation()
	        );  
			sumSummary = sumTransAmt( agg , collectionName );
			logger.info("业务"+transType+"业务汇总信息="+sumSummary);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		return sumSummary;
	}
	
	/**
	 * 代偿查询 
	 * @param stt状态, transDate 日期,partnerCode 资产端
	 * */
	public JSONArray sumCompensateSummary( String partnerCode, String stt, Date transDate ) {
		
		JSONArray sumSummary = null;
		try {
			String checkDate1 = DateUtil.getDate(DateUtils.addDays(transDate, -1));
			String checkDate2 = DateUtil.getDate(DateUtils.addDays(transDate, 0));
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			format.setCalendar(new GregorianCalendar(new SimpleTimeZone(8, "GMT")));
			
			Date D1 = format.parse(checkDate1);
			Date D2 = format.parse(checkDate2);
			
			Criteria arrCri = Criteria.where("partner_code").is(partnerCode).and("create_time").gte(D1).lt(D2);
		
			logger.info("代偿统计查询条件="+arrCri.getCriteriaObject());
			
			Aggregation agg = Aggregation.newAggregation( 
					Aggregation.match( arrCri ),//查询条件
					Aggregation.group("partner_code").sum("total_mount").as("total").count().as("count"),
					Aggregation.project("count", "total").and("partner_code").previousOperation(),
					new CustomAggregationOperation(
							new BasicDBObject(
								"$lookup",
								new BasicDBObject("from", com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_LNCOMPENSATEDETAIL_KEY) 
								.append("localField", 	"id") 
								.append("foreignField", "compensate_id") 
								.append("as", "Temple")
							)
					)
	        ); 
			
			sumSummary = sumTransAmt( agg , com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_LNCOMPENSATE_KEY );
			logger.info("代偿业务汇总信息="+sumSummary);
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			
		}
		return sumSummary;
	}
	
	/**
	 * 还款多表关联查询
	 * @param 	partnerCode-资产端  
	 * 			isOffLine-线下还款标识true-是 false-否 
	 * 			payment_channel_id-主辅商户通道标识 1-主通道 2-辅通道  
	 * 			stt 状态 SUCC/FAIL/INIT  
	 * 			transDate 交易日期
	 * */
	public JSONArray sumLnPayOrderSummary( String partnerCode, boolean isOffLine, int payment_channel_id, int stt, Date transDate ) {
		JSONArray sumSummary = null;
		try {
			String checkDate1 = DateUtil.getDate(DateUtils.addDays(transDate, -1));
			String checkDate2 = DateUtil.getDate(DateUtils.addDays(transDate, 0));
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			format.setCalendar(new GregorianCalendar(new SimpleTimeZone(8, "GMT")));
			
			Date D1 = format.parse(checkDate1);
			Date D2 = format.parse(checkDate2);
			
			Criteria arrCri = Criteria.where("trans_type").is("REPAY")
						.and("channel_trans_type").is("DK")
						.and("payment_channel_id").is(payment_channel_id)
						.and("partner_code").is(partnerCode)
						.and("status").is(stt).and("update_time").gte(D1).lt(D2);
			if( isOffLine ) {
				arrCri.and("Temple.repay_type").is("OFFLINE_REPAY");
			}
			
			logger.info("还款统计查询条件="+arrCri.getCriteriaObject());

			Aggregation agg = Aggregation.newAggregation( 
					new CustomAggregationOperation(
							new BasicDBObject(
								"$lookup",
								new BasicDBObject("from", com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_LNREPAY_KEY)
								.append("localField", 	"order_no")
								.append("foreignField", "pay_order_no")
								.append("as", "Temple")
							)
					),
					Aggregation.match( arrCri ),//查询条件
					Aggregation.group("partner_code").sum("amount").as("total").count().as("count"),
					Aggregation.project("count", "total").and("partner_code").previousOperation()
					
	        );  
			sumSummary = sumTransAmt( agg , com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_LNPAYORDERS_KEY );
			logger.info("还款业务汇总信息="+sumSummary);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sumSummary;
	}
	
	/**
	 * 单表查询
	 * @param collectionName-MongoDB 集合名称, transType 交易类型, channelTransType, stt 状态, transDate 日期
	 * 
	 * */
	public JSONArray sumOrdersSummary(String collectionName, String transType, String channel,
			String partnerCode, String channel_trans_type, int stt, Date transDate ) {
		JSONArray sumSummary = null;
		try {
			String checkDate1 = DateUtil.getDate(DateUtils.addDays(transDate, -1));
			String checkDate2 = DateUtil.getDate(DateUtils.addDays(transDate, 0));
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			format.setCalendar(new GregorianCalendar(new SimpleTimeZone(8, "GMT")));
			
			Date D1 = format.parse(checkDate1);
			Date D2 = format.parse(checkDate2);
			Criteria arrCri = Criteria.where("trans_type").is(transType)
						.and("status").is(stt).and("update_time").gte(D1).lt(D2);
			if( !"".equals(channel_trans_type) ) {
				arrCri.and("channel_trans_type").is(channel_trans_type);
			}
			if (!"".equals(channel)) {
				arrCri.and("channel").is(channel);
			}
			if (!"".equals(partnerCode)) {
				arrCri.and("partner_code").is(partnerCode);
			}
			logger.info("业务"+transType+"查询条件="+arrCri.getCriteriaObject());
			
			Aggregation agg = Aggregation.newAggregation( 
					Aggregation.match( arrCri ),//查询条件
					Aggregation.group("trans_type").sum("amount").as("total").count().as("count"),
					Aggregation.project("count","total").and("trans_type").previousOperation()
	        );  
			sumSummary = sumTransAmt( agg , collectionName );
			logger.info("业务"+transType+"业务汇总信息="+sumSummary);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		return sumSummary;
	}
	
}
