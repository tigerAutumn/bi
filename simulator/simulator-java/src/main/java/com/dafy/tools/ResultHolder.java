package com.dafy.tools;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dafy.core.helper.page.MybatisInterceptor;
import com.dafy.core.helper.page.Page;

public class ResultHolder {
	public static String getPageData(Object obj){
		Map<String, Object> map = new HashMap<String, Object>();
		//调用一次getPage已经将threadLocal中的page对象清除，下次获取将为空
		Page page = MybatisInterceptor.getPage();
		int pageSize = page.getLimit();
		int total = page == null ? 0 : page.getTotalRecords();
		map.put("total", total);
		map.put("totalPage", (total + pageSize - 1)/pageSize);
		map.put("rows", (obj == null ? new Object(){} : obj));
		return JSON.toJSONString(map,SerializerFeature.DisableCircularReferenceDetect);
	}
}
