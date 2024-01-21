package com.pinting.open.pojo.model.index;

/**
 * Author:      cyb
 * Date:        2017/6/30
 * Description:
 */
public class Notice {

    private Integer id;

    private String title;

    /* YES-已读；NO-未读 */
    private String isRead;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
}
