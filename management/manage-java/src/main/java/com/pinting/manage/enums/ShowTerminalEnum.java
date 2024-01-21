package com.pinting.manage.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ShowTerminalEnum {
	H5("H5", "H5"),
	PC("PC", "网站"),
	H5_178("H5_178", "钱报H5"),
	PC_178("PC_178", "钱报PC"),
	APP("APP", "APP"),
    H5_KQ("H5_KQ", "柯桥日报H5"),
    PC_KQ("PC_KQ", "柯桥日报PC"),
    H5_HN("H5_HN", "海宁日报H5"),
    PC_HN("PC_HN", "海宁日报PC"),
    H5_RUIAN("H5_RUIAN", "瑞安日报H5"),
    PC_RUIAN("PC_RUIAN", "瑞安日报PC"),
    H5_QD("H5_QD", "七店H5"),
    H5_QHD_JT("H5_QHD_JT", "秦皇岛交通广播H5"),
    H5_QHD_XW("H5_QHD_XW", "秦皇岛新闻891H5"),
    H5_QHD_TV("H5_QHD_TV", "秦皇岛电视台今日报道H5"),
    PC_QHD_ST("PC_QHD_ST", "视听之友PC"),
    H5_QHD_ST("H5_QHD_ST", "视听之友H5"),
    H5_QHD_SJC("H5_QHD_SJC", "1038私家车广播H5"),
	;
	
    
    private ShowTerminalEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private String code;
    
    private String description;

    private static List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    static {
        for (ShowTerminalEnum s : EnumSet.allOf(ShowTerminalEnum.class)) {
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
