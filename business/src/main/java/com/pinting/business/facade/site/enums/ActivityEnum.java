package com.pinting.business.facade.site.enums;

/**
 * Author:      cyb
 * Date:        2017/1/24
 * Description: 活动枚举
 */
public enum ActivityEnum {

    LANTERN_FESTIVAL_2017_SHAKE(9, "2017元宵摇一摇"),
    LANTERN_FESTIVAL_2017_BUY(10, "2017元宵理财活动"),
    ACTIVITY_FOR_GIRL_2017(11, "2017女神节活动");

    private Integer id;

    private String content;

    private ActivityEnum(Integer id, String content) {
        this.id = id;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
