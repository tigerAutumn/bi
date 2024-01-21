package com.pinting.open.pojo.model.asset;

/**
 * Created by cyb on 2018/3/30.
 */
public class Ticket {

    private Integer ticketId;         // 红包、加息券ID
    private String  type;       // RED_PACKET-红包; INTEREST_TICKET-加息券
    private String  status;     // 状态：INIT-未使用；USED-已使用；OVER-已过期
    private String  rate;       // 加息利率（%）
    private String  full;       // 满额
    private String  subtract;   // 减额
    private String  serialName; // 优惠券名称
    private String  useTimeStart;   // 使用有效开始时间（yyyy-MM-dd）
    private String  useTimeEnd;     // 使用有效结束时间（yyyy-MM-dd）
    private String  useTime;        // 使用时间（yyyy-MM-dd HH:mm:ss）
    private String  target;         // 标的，限1个月产品使用，限30天产品使用
    private String  productLimit;   // 产品系列限制：BIGANGWAN_SERIAL（港湾系列）YONGJIN_SERIAL（涌金系列）KUAHONG_SERIAL（跨虹系列）BAOXIN_SERIAL（保信系列）多个产品限制用逗号隔开
    private String  productName;    // 已使用产品名称

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductLimit() {
        return productLimit;
    }

    public void setProductLimit(String productLimit) {
        this.productLimit = productLimit;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getSubtract() {
        return subtract;
    }

    public void setSubtract(String subtract) {
        this.subtract = subtract;
    }

    public String getSerialName() {
        return serialName;
    }

    public void setSerialName(String serialName) {
        this.serialName = serialName;
    }

    public String getUseTimeStart() {
        return useTimeStart;
    }

    public void setUseTimeStart(String useTimeStart) {
        this.useTimeStart = useTimeStart;
    }

    public String getUseTimeEnd() {
        return useTimeEnd;
    }

    public void setUseTimeEnd(String useTimeEnd) {
        this.useTimeEnd = useTimeEnd;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
