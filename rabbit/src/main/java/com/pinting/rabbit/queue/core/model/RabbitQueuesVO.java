package com.pinting.rabbit.queue.core.model;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * 队列业务流程上下文件
 */
public class RabbitQueuesVO<T> implements Serializable {

    private String queuesNO;// 队列编号

    private String sendDate = DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"); // 入队列时间（格式 yyyy-MM-dd HH:mm:ss）

    private String rabbitEventCode; // 事件类型

    private String rabbitBusinessCode; // 业务类型

    private Integer executeCount = 0; // 消息执行次数（第一次入队列，初始默认0）

    private HashMap<String, String> extendMap = new HashMap<>(); //扩展字段

    private T bodyVO; // 队列对象

    public RabbitQueuesVO() {
    }

    public String getQueuesNO() {
        return queuesNO;
    }

    public void setQueuesNO(String queuesNO) {
        this.queuesNO = queuesNO;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getRabbitEventCode() {
        return rabbitEventCode;
    }

    public void setRabbitEventCode(String rabbitEventCode) {
        this.rabbitEventCode = rabbitEventCode;
    }

    public String getRabbitBusinessCode() {
        return rabbitBusinessCode;
    }

    public void setRabbitBusinessCode(String rabbitBusinessCode) {
        this.rabbitBusinessCode = rabbitBusinessCode;
    }

    public Integer getExecuteCount() {
        return executeCount;
    }

    public void setExecuteCount(Integer executeCount) {
        this.executeCount = executeCount;
    }

    public HashMap<String, String> getExtendMap() {
        return extendMap;
    }

    public void setExtendMap(HashMap<String, String> extendMap) {
        this.extendMap = extendMap;
    }

    public void setExtendData(String key, String value) {
        this.extendMap.put(key, value);
    }

    public Object getExtendData(String key) {
        return this.extendMap.get(key);
    }

    public T getBodyVO() {
        return bodyVO;
    }

    public void setBodyVO(T bodyVO) {
        this.bodyVO = bodyVO;
    }

    public T getBodyVO(Class<T> clazz) {
        return JSONObject.parseObject(JSONObject.toJSONString(bodyVO), clazz);
    }
}

