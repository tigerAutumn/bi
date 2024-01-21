/**
 * 主动抛出异常类工具类
 * @author yanwl
 * @date 2015-10-25
 */
package com.dafy.core.util;

public class AssertUtil {
	//返回结果为false时抛出异常
	public static void assertTrue(boolean flag) {
		if(!flag) {
			throwRuntimeException();
		}
	}
	
	//返回结果对象为空时抛出异常
	public static void assertNotNull(Object obj) {
		if(obj == null) {
			throwRuntimeException();
		}
	}
	
	//运行时异常抛出
	private static void throwRuntimeException() {
		throw new RuntimeException();
	}
}
