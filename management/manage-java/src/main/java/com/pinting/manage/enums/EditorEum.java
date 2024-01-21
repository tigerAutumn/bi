package com.pinting.manage.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pinting.util.GlobEnv;

/**
 * 文本编辑器需要替换的枚举类，如<p>标签
 * @author bianyatian
 * @2016-4-5 下午3:27:21
 */
public enum EditorEum {
	
	ab_P("&lt;/p&gt;&lt;p&gt;","[br]"),
	b_P("&lt;p&gt;",""), //<p>
	a_P("&lt;/p&gt;",""), //</p>
	b_Tab("&lt;span style=\"white-space: pre;\"&gt;",""),  //前tab
	a_Tab("&lt;/span&gt;",""), //后tab
	b_Div("&lt;div&gt;",""), //前div
	a_Div("&lt;/div&gt;",""), //后div
	br("&lt;br /&gt;","[br]"), //br
	nbsp("&nbsp;"," "), //nbsp
	quot("&quot;","\""),//&quot
	http(GlobEnv.get("server.web"),""),
	;
	

	 private EditorEum(String code, String description) {
	        this.code = code;
	        this.description = description;
	    }

	    private String code;
	    
	    private String description;

	    private static List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	    static {
	        for (EditorEum s : EnumSet.allOf(EditorEum.class)) {
	            Map<String, Object> lookup = new HashMap<String, Object>();
	            lookup.put("code", s.getCode());
	            lookup.put("description", s.getDescription());
	            list.add(lookup);
	        }
	    }

	    public static List<Map<String, Object>> getMapList() {
	        return list;
	    }
	    
	    public String getCode() {
	        return code;
	    }

	    public void setCode(String code) {
	        this.code = code;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }
}
