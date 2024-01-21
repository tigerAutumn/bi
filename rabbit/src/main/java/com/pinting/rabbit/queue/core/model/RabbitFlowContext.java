package com.pinting.rabbit.queue.core.model;

import com.alibaba.fastjson.JSONObject;
import com.pinting.rabbit.queue.core.enums.RabbitBindingEnum;
import com.pinting.rabbit.queue.core.enums.RabbitBusinessEnum;
import com.pinting.rabbit.queue.core.enums.RabbitEventEnum;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 队列业务流程上下文件
 */
public class RabbitFlowContext implements Serializable {

    private JSONObject reqJson; // 请求对象
    private JSONObject resJson; // 响应对象
    private RabbitBindingEnum rabbitBindingEnum; // 队列枚举
    private RabbitEventEnum rabbitEventEnum; // 事件类型
    private RabbitBusinessEnum rabbitBusinessEnum; // 业务类型
    private Integer executeCount; // 消息执行次数
    private HashMap<String, Object> extendMap = new HashMap<>(); //扩展字段

    public RabbitFlowContext() {

    }

    /**
     * 销毁上下文
     */
    public void destroy() {
        extendMap.clear();
    }

    public JSONObject getReqJson() {
        return reqJson;
    }

    public void setReqJson(JSONObject reqJson) {
        this.reqJson = reqJson;
    }

    public JSONObject getResJson() {
        return resJson;
    }

    public void setResJson(JSONObject resJson) {
        this.resJson = resJson;
    }

    public HashMap<String, Object> getExtendMap() {
        return extendMap;
    }

    public void setExtendMap(HashMap<String, Object> extendMap) {
        this.extendMap = extendMap;
    }

    public void setExtendData(String key, Object value) {
        this.extendMap.put(key, value);
    }

    public Object getExtendData(String key) {
        return this.extendMap.get(key);
    }

    public RabbitEventEnum getRabbitEventEnum() {
        return rabbitEventEnum;
    }

    public void setRabbitEventEnum(RabbitEventEnum rabbitEventEnum) {
        this.rabbitEventEnum = rabbitEventEnum;
    }

    public RabbitBindingEnum getRabbitBindingEnum() {
        return rabbitBindingEnum;
    }

    public void setRabbitBindingEnum(RabbitBindingEnum rabbitBindingEnum) {
        this.rabbitBindingEnum = rabbitBindingEnum;
    }

    public RabbitBusinessEnum getRabbitBusinessEnum() {
        return rabbitBusinessEnum;
    }

    public void setRabbitBusinessEnum(RabbitBusinessEnum rabbitBusinessEnum) {
        this.rabbitBusinessEnum = rabbitBusinessEnum;
    }

    public Integer getExecuteCount() {
        return executeCount;
    }

    public void setExecuteCount(Integer executeCount) {
        this.executeCount = executeCount;
    }

    public RabbitQueuesVO getQueuesVO() {
        return JSONObject.parseObject(getReqJson().toJSONString(), RabbitQueuesVO.class);
    }

    public void setQueuesVO(RabbitQueuesVO queuesVO) {
        this.setReqJson(JSONObject.parseObject(JSONObject.toJSONString(queuesVO)));
    }
}

