package com.pinting.schedule.mongodb.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.pinting.schedule.mongodb.dao.MongoDataBase;
import com.pinting.schedule.mongodb.model.LnPayOrders;

@Component
public class MongoDataBaseImpl implements MongoDataBase<T> {
	
	@Resource( name = "mongoTemplate" )
	@Autowired
	private MongoTemplate mongoTemplate;

	public long count(Criteria cri, String collectionName) {
		return mongoTemplate.count( new Query( cri ), collectionName );
	}
	
	public void insert(Object object, String collectionName) {
		mongoTemplate.insert(object, collectionName);
	}
	
	public Object findOne(Criteria cri, String collectionName) {
		return mongoTemplate.findOne(new Query( cri ), Object.class,
                collectionName);
	}
	
	public List<Object> findAll(String collectionName) {
		return mongoTemplate.findAll(Object.class, collectionName);
	}
	
	public void update(Map<String, Object> queryParams, Map<String, Object> updateParams, String collectionName) {
		
		Iterator<?> queryiter = queryParams.entrySet().iterator();
		Criteria cri = new Criteria();
		List<Criteria> listC = new ArrayList<Criteria>();
		while ( queryiter.hasNext() ) {
			Map.Entry entry = (Map.Entry) queryiter.next(); 
		    String key = (String) entry.getKey(); 
		    Object val = entry.getValue(); 
		    if(val != null) {
		    	listC.add(cri.where(key).is(val));
		    }
		}
		if(listC.size() > 0){
            Criteria [] cs = new Criteria[listC.size()];
            cri.andOperator(listC.toArray(cs));
        }
		
		Update update = new Update();
		Iterator updateIter = updateParams.entrySet().iterator();
		while ( updateIter.hasNext() ) {
			Map.Entry entry = (Map.Entry) updateIter.next(); 
		    String key = (String) entry.getKey(); 
		    Object val = entry.getValue(); 
			update.set( key, val );
		}
		
		mongoTemplate.upsert(new Query( cri ), update, T.class,
                collectionName);
	}
	
	public void createCollection(String collectionName) {
		mongoTemplate.createCollection(collectionName);
	}
	
	public void remove(Map<String, Object> params, String collectionName) {
		mongoTemplate.remove(new Query(Criteria.where("id").is(params.get("id"))), LnPayOrders.class, collectionName);
	}
}
