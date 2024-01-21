package com.pinting.core.base;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDao<T> {
	protected Logger log = null;
	
	public BaseDao(){
		this.log = LoggerFactory.getLogger(this.getClass());
	}
	
	@Autowired
	public SqlSessionTemplate sqlSessionTemplate;

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
}
