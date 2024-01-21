package com.pinting.open.pojo.model.more;

public class News {

	private Integer id;

    private String type;

    private String subject;

    private String keywords;

    private String summary;

    private String subjectImg;

    private String receiverType;

    private Integer priority;

    private String status;

    private Long mUserId;

    private String publishTime;

    private String source;

    private Integer readTimes;

    private String createTime;

    private String updateTime;

    private String content;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getSubjectImg() {
		return subjectImg;
	}

	public void setSubjectImg(String subjectImg) {
		this.subjectImg = subjectImg;
	}

	public String getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getmUserId() {
		return mUserId;
	}

	public void setmUserId(Long mUserId) {
		this.mUserId = mUserId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getReadTimes() {
		return readTimes;
	}

	public void setReadTimes(Integer readTimes) {
		this.readTimes = readTimes;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
