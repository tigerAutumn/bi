/**
 * Copyright 2005-2015 Li Qiang.com
 * All rights reserved.
 * 
 * @project
 * @author Li Qiang
 * @version 2.0
 * @date 2012-12-31
 */
package com.dafy.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.ListUtils;

/**
 * List处理的工具类
 * @author Li Qiang
 *
 */
public class ListUtil extends ListUtils {
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isNotEmpty(List<?> list) {
		
		return list != null && !list.isEmpty();
	}
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List<?> list) {
		
		return list == null || list.isEmpty();
	}
	
	public static <T> List<T> unique(List<T> list){
		List<T> answer = new ArrayList<T>();
        for (T t : list) {
            boolean duplicated = false;
            for (T t2 : answer) {
                if (t.equals(t2)) {
                    duplicated = true;
                    break;
                }
            }
            if (!duplicated)
                answer.add(t);
        }
        list.clear();
        list.addAll(answer);
        return list;
	}
	
	/**
	 * 将Map转换为List
	 * 0： key
	 * 1: value
	 * @param populateMap
	 * @return
	 */
	public static List<Object[]> getMapToList(Map<Object, Object> populateMap) {
		if (populateMap == null) throw new RuntimeException("---------- populateMap is null ----------");
		
		List<Object[]> list = new ArrayList<Object[]>();
		Iterator<Object> it = populateMap.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			Object value = populateMap.get(key);
			
			Object[] objs = new Object[2];
			objs[0] = key;
			objs[1] = value;
			
			list.add(objs);
		}
		
		return list;
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("1");
		list.add("2");
		System.out.println(unique(list));
	}
}