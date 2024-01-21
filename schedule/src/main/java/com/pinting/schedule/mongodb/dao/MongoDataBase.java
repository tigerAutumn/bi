package com.pinting.schedule.mongodb.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;


public interface MongoDataBase<T> {
	/**
	 * @params params查询条件,collectionName集合名称
	 * @return 符合条件的笔数
	 * @description 统计笔数
	 * */
	long count(Criteria cri, String collectionName);
	
	/**
	 * @param T新增对象 ,collectionName集合名称
	 * @return 
	 * @description 新增
	 * */
    void insert(Object object, String collectionName);
	
    /**
     * @param params查询条件,collectionName集合名称
     * @return 符合条件的对象
     * @description 根据条件查找单个对象
     * */
    Object findOne(Criteria cri, String collectionName);
    
    /**
     * @param collectionName集合名称 
     * @return List<T> 返回符合条件的对象列表 
     * @description 查找所有 
     * */
    List<Object> findAll(String collectionName);
   
    /**
     * @param queryParams查询条件, updateParams更新目标, collectionName集合名称
     * @return 
     * @description 修改/更新 
     * */
    void update(Map<String, Object> queryParams, Map<String, Object> updateParams, String collectionName);
    
    /**
     * @param collectionName集合名称
     * @description 创建集合
     * */
    void createCollection(String collectionName);

    /**
     * @param params删除条件, collectionName集合名称
     * @description 根据条件删除
     * */
    void remove( Map<String, Object> params, String collectionName );
}
