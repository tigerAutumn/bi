package com.pinting.business.model;

import java.util.Date;

import com.pinting.business.model.vo.PageInfoObject;

public class BsAppMessage extends PageInfoObject{
    /**
	 * 序列化编号
	 */
	private static final long serialVersionUID = 7853317299008130023L;

	private Integer id;

    private Integer releasePart;

    private String name;

    private String title;

    private String pushChar;

    private String pushAbstract;

    private Integer pushType;

    private Integer appPage;

    private String content;

    private Integer isSend;

    private Date createTime;

    private Date pushTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReleasePart() {
        return releasePart;
    }

    public void setReleasePart(Integer releasePart) {
        this.releasePart = releasePart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPushChar() {
        return pushChar;
    }

    public void setPushChar(String pushChar) {
        this.pushChar = pushChar;
    }

    public String getPushAbstract() {
        return pushAbstract;
    }

    public void setPushAbstract(String pushAbstract) {
        this.pushAbstract = pushAbstract;
    }

    public Integer getPushType() {
        return pushType;
    }

    public void setPushType(Integer pushType) {
        this.pushType = pushType;
    }

    public Integer getAppPage() {
        return appPage;
    }

    public void setAppPage(Integer appPage) {
        this.appPage = appPage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }
}