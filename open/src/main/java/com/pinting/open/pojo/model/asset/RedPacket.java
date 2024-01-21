package com.pinting.open.pojo.model.asset;

public class RedPacket {
	private Integer id; //红包ID

	private String status; //红包状态
	
	private Double full; //满额
	
	private Double subtract; //减额
	
	private String serialName; //红包名称
	
	private String useTimeStart; //红包开始时间
	
	private String useTimeEnd; //红包结束时间
	
	private String target; //标的

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getFull() {
		return full;
	}

	public void setFull(Double full) {
		this.full = full;
	}

	public Double getSubtract() {
		return subtract;
	}

	public void setSubtract(Double subtract) {
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
	
}
