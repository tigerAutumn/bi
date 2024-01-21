package com.pinting.schedule.mongodb.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pinting.core.util.MoneyUtil;
import com.pinting.schedule.mongodb.model.BaseDBObject;

/**
 * @ClassName: BaseMongoManager
 * @Description: mongo基础库
 * @author Gemma
 * @date 2018-6-11 19:48:50
 * */
public class MongoManage {
	
	public JSONArray transportMap(List<BaseDBObject> listSensors){
	    JSONArray arrayList = new JSONArray();
	    if(listSensors != null && listSensors.size() > 0){
	        for (Object list :  listSensors){
	            JSONObject json = new JSONObject();
	            String strMap = JSON.toJSONString(list);
	            JSONObject js = JSON.parseObject(strMap);
	            json.put("total",		MoneyUtil.defaultRound(js.getString("total")).doubleValue());
	            json.put("count",		js.getInteger("count"));
	            json.put("trans_type",  js.getString("trans_type"));
	            json.put("partner_code",  js.getString("partner_code"));
//	            JSONArray array = js.getJSONArray("item");
//	            if(array != null && array.size() > 0){
//	                for (int i=0;i<array.size();i++){
//	                    JSONObject jo = array.getJSONObject(i);
//	                    String type = jo.getString("trans_type");
//	                    switch (type){
//	                        case "2":
//	                            json.put("total",	jo.getDouble("total"));
//	                            break;
//	                        case "1":
//	                            json.put("count",	jo.getInteger("count"));
//	                            break;
//	                    }
//	                }
//	            }
	            arrayList.add(json);
	        }
	    } else {
	    	JSONObject json = new JSONObject();
	    	json.put("total",		MoneyUtil.defaultRound(0d).doubleValue());
            json.put("count",		0);
            json.put("trans_type",  "");
            json.put("partner_code","");
            arrayList.add(json);
	    }
	    return arrayList;
	}
	/**
     * 生成查询条件的语句
     *
     * @Title: createCriteria
     * @Description: 根据不同条件生产SQL
     * @param gtMap greater than >
     * @param ltMap less than <
     * @param eqMap 等值查询 ==
     * @param gteMap greater equal >=
     * @param lteMap less equal <=
     * @param regexMap 模糊查询
     * @param inMap in
     * @param neMap !=
     * @return Criteria 查询的语句
     * @throws
     */
    @SuppressWarnings("rawtypes")
    public Criteria createCriteria(Map<String, Object> gtMap,
            Map<String, Object> ltMap, 
            Map<String, Object> eqMap,
            Map<String, Object> gteMap, 
            Map<String, Object> lteMap,
            Map<String, String> regexMap, 
            Map<String, Collection> inMap,
            Map<String, Object> neMap) {
    	
		Criteria cri = new Criteria();
		List<Criteria> listC = new ArrayList<Criteria>();
		if (gtMap != null && gtMap.size() > 0) {
			Iterator<?> queryiter = gtMap.entrySet().iterator();
			while ( queryiter.hasNext() ) {
				Map.Entry entry = (Map.Entry) queryiter.next(); 
			    String _key = (String) entry.getKey(); 
			    Object _val = entry.getValue(); 
			    if( _val != null ) {
			    	listC.add(cri.where(_key).gt(_val));
			    }
			}
		}
		
		if (ltMap != null && ltMap.size() > 0) {
			Iterator<?> queryiter = ltMap.entrySet().iterator();
			while ( queryiter.hasNext() ) {
				Map.Entry entry = (Map.Entry) queryiter.next(); 
			    String _key = (String) entry.getKey(); 
			    Object _val = entry.getValue(); 
			    if( _val != null ) {
			    	listC.add(cri.where(_key).lt(_val));
			    }
			}
		} 
         
		if (eqMap != null && eqMap.size() > 0) {
			Iterator<?> queryiter = eqMap.entrySet().iterator();
			while ( queryiter.hasNext() ) {
				Map.Entry entry = (Map.Entry) queryiter.next(); 
			    String _key = (String) entry.getKey(); 
			    Object _val = entry.getValue(); 
			    if( _val != null ) {
			    	listC.add(cri.where(_key).is(_val));
			    }
			}
		} 
        
		if (gteMap != null && gteMap.size() > 0) {
			Iterator<?> queryiter = gteMap.entrySet().iterator();
			while ( queryiter.hasNext() ) {
				Map.Entry entry = (Map.Entry) queryiter.next(); 
			    String _key = (String) entry.getKey(); 
			    Object _val = entry.getValue(); 
			    if( _val != null ) {
			    	listC.add(cri.where(_key).gte(_val));
			    }
			}
		} 
		
		if (lteMap != null && lteMap.size() > 0) {
			Iterator<?> queryiter = lteMap.entrySet().iterator();
			while ( queryiter.hasNext() ) {
				Map.Entry entry = (Map.Entry) queryiter.next(); 
			    String _key = (String) entry.getKey(); 
			    Object _val = entry.getValue(); 
			    if( _val != null ) {
			    	listC.add(cri.where(_key).lte(_val));
			    }
			}
		}  
		
		if (regexMap != null && regexMap.size() > 0) {
			Iterator<?> queryiter = regexMap.entrySet().iterator();
			while ( queryiter.hasNext() ) {
				Map.Entry entry = (Map.Entry) queryiter.next(); 
			    String _key = (String) entry.getKey(); 
			    String _val = (String) entry.getValue(); 
			    if( _val != null ) {
			    	listC.add(cri.where(_key).regex(_val));
			    }
			}
		}   

		if (inMap != null && inMap.size() > 0) {
			Iterator<?> queryiter = inMap.entrySet().iterator();
			while ( queryiter.hasNext() ) {
				Map.Entry entry = (Map.Entry) queryiter.next(); 
			    String _key = (String) entry.getKey(); 
			    String _val = (String) entry.getValue(); 
			    if( _val != null ) {
			    	listC.add(cri.where(_key).in(_val));
			    }
			}
		}   
		
		if (neMap != null && neMap.size() > 0) {
			Iterator<?> queryiter = neMap.entrySet().iterator();
			while ( queryiter.hasNext() ) {
				Map.Entry entry = (Map.Entry) queryiter.next(); 
			    String _key = (String) entry.getKey(); 
			    String _val = (String) entry.getValue(); 
			    if( _val != null ) {
			    	listC.add(cri.where(_key).ne(_val));
			    }
			}
		}
        
        if(listC.size() > 0){
            Criteria [] cs = new Criteria[listC.size()];
            cri.andOperator(listC.toArray(cs));
        }
        return cri;
    }
}
