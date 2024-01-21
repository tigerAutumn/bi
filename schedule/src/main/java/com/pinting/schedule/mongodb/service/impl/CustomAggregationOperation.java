package com.pinting.schedule.mongodb.service.impl;

import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import com.mongodb.DBObject;

/**
 * 自定义Class实现 Aggregation.lookup
 * @author gemma
 * @date 2018-6-14 17:05:43
 * */

public class CustomAggregationOperation implements AggregationOperation {
	
	private DBObject operation;
	
	public CustomAggregationOperation ( DBObject operation ) { 
		this.operation = operation;
	}
	
	@Override
	public DBObject toDBObject(AggregationOperationContext context) {
		return context.getMappedObject(operation);
	}

}
