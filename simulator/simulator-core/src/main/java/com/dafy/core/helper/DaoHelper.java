/**
 * DaoHelper 
 * @author yanwl
 * @version 1.0
 * @date 2015-11-19
 */
package com.dafy.core.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import com.dafy.core.model.IDataObject;
import com.dafy.core.model.IPOJO;

public class DaoHelper extends SqlSessionDaoSupport{
	
	private static SqlSessionTemplate sessionTemplate;
	
	/**
	 * 新增
	 * @param nameSpace
	 * @param sqlID
	 * @param entity
	 * @return 返回主键id
	 */
	public static boolean insert(String nameSpace, String sqlID, IPOJO entity) {
		
		return sessionTemplate.insert(getStatement(nameSpace, sqlID), entity)>=0?true:false;
	}
	
	
	/**
	 * 修改
	 * @param nameSpace
	 * @param sqlID
	 * @param entity
	 * @return
	 */
	public static boolean update(String nameSpace, String sqlID, IPOJO entity) {
		
		return sessionTemplate.update(getStatement(nameSpace, sqlID), entity)>=0?true:false;
	}
	
	public static boolean update(String nameSpace, String sqlID, Object pk) {
		
		return sessionTemplate.update(getStatement(nameSpace, sqlID), pk)>=0?true:false;
	}
	
	/**
	 * 修改
	 * @param nameSpace
	 * @param sqlID
	 * @param entity
	 * @return
	 */
	public static int updateByAffectedRows(String nameSpace, String sqlID, IPOJO entity) {
		
		return sessionTemplate.update(getStatement(nameSpace, sqlID), entity);
	}
	
	/**
	 * 删除
	 * @param nameSpace
	 * @param sqlID
	 * @param id
	 * @return
	 */
	public static boolean delete(String nameSpace, String sqlID, Object id) {
		
		return sessionTemplate.delete(getStatement(nameSpace, sqlID) , id)>=0?true:false;
	}
	
	/**
	 * 删除
	 * @param nameSpace
	 * @param sqlID
	 * @param entity
	 * @return
	 */
	public static boolean delete(String nameSpace, String sqlID, IPOJO entity) {
		
		return sessionTemplate.delete(getStatement(nameSpace, sqlID), entity)>=0?true:false;
	}
	
	/**
	 * 查询总数
	 * @param nameSpace
	 * @param sqlID
	 * @param data
	 * @return
	 */
	public static int count(String nameSpace, String sqlID, Object data) {
		
		return sessionTemplate.selectOne(getStatement(nameSpace, sqlID), data);
	}
	
	/**
	 * 查询单条
	 * @param nameSpace
	 * @param sqlID
	 * @param data
	 * @return
	 */
	public static Object query(String nameSpace, String sqlID, Object data) {
		
		return sessionTemplate.selectOne(getStatement(nameSpace, sqlID), data);
	}
	
	/**
	 * 查询多条
	 * @param nameSpace
	 * @param sqlID
	 * @param data
	 * @return
	 */
	public static List<? extends IDataObject> list(String nameSpace, String sqlID, Object data) {
		if(data != null) {
			return sessionTemplate.selectList(getStatement(nameSpace, sqlID), data);
		}
		return sessionTemplate.selectList(getStatement(nameSpace, sqlID));
	}
	
	/**
	 * 分页查询多条
	 * @param nameSpace
	 * @param sqlID
	 * @param data
	 * @param pageIndex 当前页索引 0 代表第一页
	 * @param pageSize 每页显示的条数
	 * @return
	 */
	public static List<? extends IDataObject> listByPage(String nameSpace, String sqlID, Map<String, Object> data,int pageIndex,int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageIndex*pageSize);
		map.put("pageSize", pageSize);
		
		map.putAll(data);
		return sessionTemplate.selectList(getStatement(nameSpace, sqlID), map);
	}
	
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
		sessionTemplate = sqlSessionTemplate;
	}
	
	public static SqlSessionTemplate getSqlSessionTemplate() {
		return sessionTemplate;
	}
	
	private static String getStatement(String nameSpace, String sqlID) {
		
		return nameSpace + "." + sqlID;
	}
}
