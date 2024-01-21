package com.pinting.mall.mybatis.handle;

import java.util.Map;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

/**
 * Map 类型结果字段名称转驼峰格式
 * 
 * @author zousheng
 */
public class MapWrapperFactory implements ObjectWrapperFactory
{
	@Override
	public boolean hasWrapperFor(Object object)
	{
		return object != null && object instanceof Map;
	}

	@Override
	public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object)
	{
		return new FieldNameConvertMapWrapper(metaObject, (Map) object);
	}
}
