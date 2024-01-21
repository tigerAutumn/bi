package com.pinting.gateway.test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import com.pinting.gateway.dafy.in.model.ReceiveMoneyNoticeReqModel;

/**
 * @Project: gateway
 * @Title: Test.java
 * @author Zhou Changzai
 * @date 2015-4-1 下午5:32:46
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class Test {
	public static void main(String[] args) throws ClassNotFoundException {
		Class modelClass = Class.forName(ReceiveMoneyNoticeReqModel.class.getName());
		//判断是否需要嵌套转换1
		String listName = null;
		Field[] fields = modelClass.getDeclaredFields();
		for (Field field : fields) {
			String fieldClassName = field.getType().getName();
			if(List.class.getName().equals(fieldClassName)){
				listName = field.getName();
				Type type = field.getGenericType();
				if(type instanceof ParameterizedType){
					ParameterizedType pt = (ParameterizedType)type;
					Class<?> c = (Class<?>)pt.getActualTypeArguments()[0];
					System.out.println(c.getName());
				}
				System.out.println();
				break;
			}
		}
		System.out.println(listName);
		
	}
}
