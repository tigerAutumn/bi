/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.manage.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 渠道类型
 * @author HuanXiong
 * @version $Id: ChannelEnum.java, v 0.1 2016-1-7 上午11:01:18 HuanXiong Exp $
 */
public enum ChannelEnum {
    DAFY("DAFY", "达飞"),
    PAY19("PAY19", "19付"),
    REAPAL("REAPAL", "融宝");
    
    private ChannelEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private String code;
    
    private String description;

    private static List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    static {
        for (ChannelEnum s : EnumSet.allOf(ChannelEnum.class)) {
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
