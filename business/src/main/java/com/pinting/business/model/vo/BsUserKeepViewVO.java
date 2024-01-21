package com.pinting.business.model.vo;


import java.util.Date;
import java.util.List;

import com.pinting.business.model.BsUserKeepView;

public class BsUserKeepViewVO extends BsUserKeepView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2325649235435216006L;

	/** 注册人数 */
	private Integer registUserCount;
	
	/** 活跃人数 */
	private Integer activeUserCount;
	
	private Date loginDate;
	
	private Date sRegisterTime;
	
	private Date eRegisterTime;
	
	private List<Object> agentIds;
	
	private String nonAgentId;
	
	private Integer registUserCountTotal;
	
	private Double day2KeepRate;
	
	private Double day3KeepRate;
	
	private Double day7KeepRate;
	
	private Double day14KeepRate;
	
	private Double day30KeepRate;
	
	private Double day60KeepRate;
	
	private Integer agentIdRegistTotal;
	private Integer day2LoginNumTotal;
    private Integer day2RegistNumTotal;
    private Integer day3LoginNumTotal;
    private Integer day3RegistNumTotal;
    private Integer day7LoginNumTotal;
    private Integer day7RegistNumTotal;
    private Integer day14LoginNumTotal;
    private Integer day14RegistNumTotal;
    private Integer day30LoginNumTotal;
    private Integer day30RegistNumTotal;
    private Integer day60LoginNumTotal;
    private Integer day60RegistNumTotal;
    
    private String allAgentIds;
    private String allAgentNames;
    
	public Integer getRegistUserCount() {
		return registUserCount;
	}
	public void setRegistUserCount(Integer registUserCount) {
		this.registUserCount = registUserCount;
	}
	public Integer getActiveUserCount() {
		return activeUserCount;
	}
	public void setActiveUserCount(Integer activeUserCount) {
		this.activeUserCount = activeUserCount;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public Date getsRegisterTime() {
		return sRegisterTime;
	}
	public void setsRegisterTime(Date sRegisterTime) {
		this.sRegisterTime = sRegisterTime;
	}
	public Date geteRegisterTime() {
		return eRegisterTime;
	}
	public void seteRegisterTime(Date eRegisterTime) {
		this.eRegisterTime = eRegisterTime;
	}
	public List<Object> getAgentIds() {
		return agentIds;
	}
	public void setAgentIds(List<Object> agentIds) {
		this.agentIds = agentIds;
	}
	public String getNonAgentId() {
		return nonAgentId;
	}
	public void setNonAgentId(String nonAgentId) {
		this.nonAgentId = nonAgentId;
	}
	public Integer getRegistUserCountTotal() {
		return registUserCountTotal;
	}
	public void setRegistUserCountTotal(Integer registUserCountTotal) {
		this.registUserCountTotal = registUserCountTotal;
	}
	public Double getDay2KeepRate() {
		return day2KeepRate;
	}
	public void setDay2KeepRate(Double day2KeepRate) {
		this.day2KeepRate = day2KeepRate;
	}
	public Double getDay3KeepRate() {
		return day3KeepRate;
	}
	public void setDay3KeepRate(Double day3KeepRate) {
		this.day3KeepRate = day3KeepRate;
	}
	public Double getDay7KeepRate() {
		return day7KeepRate;
	}
	public void setDay7KeepRate(Double day7KeepRate) {
		this.day7KeepRate = day7KeepRate;
	}
	public Double getDay14KeepRate() {
		return day14KeepRate;
	}
	public void setDay14KeepRate(Double day14KeepRate) {
		this.day14KeepRate = day14KeepRate;
	}
	public Double getDay30KeepRate() {
		return day30KeepRate;
	}
	public void setDay30KeepRate(Double day30KeepRate) {
		this.day30KeepRate = day30KeepRate;
	}
	public Double getDay60KeepRate() {
		return day60KeepRate;
	}
	public void setDay60KeepRate(Double day60KeepRate) {
		this.day60KeepRate = day60KeepRate;
	}
	public Integer getAgentIdRegistTotal() {
		return agentIdRegistTotal;
	}
	public void setAgentIdRegistTotal(Integer agentIdRegistTotal) {
		this.agentIdRegistTotal = agentIdRegistTotal;
	}
	public Integer getDay2LoginNumTotal() {
		return day2LoginNumTotal;
	}
	public void setDay2LoginNumTotal(Integer day2LoginNumTotal) {
		this.day2LoginNumTotal = day2LoginNumTotal;
	}
	public Integer getDay2RegistNumTotal() {
		return day2RegistNumTotal;
	}
	public void setDay2RegistNumTotal(Integer day2RegistNumTotal) {
		this.day2RegistNumTotal = day2RegistNumTotal;
	}
	public Integer getDay3LoginNumTotal() {
		return day3LoginNumTotal;
	}
	public void setDay3LoginNumTotal(Integer day3LoginNumTotal) {
		this.day3LoginNumTotal = day3LoginNumTotal;
	}
	public Integer getDay3RegistNumTotal() {
		return day3RegistNumTotal;
	}
	public void setDay3RegistNumTotal(Integer day3RegistNumTotal) {
		this.day3RegistNumTotal = day3RegistNumTotal;
	}
	public Integer getDay7LoginNumTotal() {
		return day7LoginNumTotal;
	}
	public void setDay7LoginNumTotal(Integer day7LoginNumTotal) {
		this.day7LoginNumTotal = day7LoginNumTotal;
	}
	public Integer getDay7RegistNumTotal() {
		return day7RegistNumTotal;
	}
	public void setDay7RegistNumTotal(Integer day7RegistNumTotal) {
		this.day7RegistNumTotal = day7RegistNumTotal;
	}
	public Integer getDay14LoginNumTotal() {
		return day14LoginNumTotal;
	}
	public void setDay14LoginNumTotal(Integer day14LoginNumTotal) {
		this.day14LoginNumTotal = day14LoginNumTotal;
	}
	public Integer getDay14RegistNumTotal() {
		return day14RegistNumTotal;
	}
	public void setDay14RegistNumTotal(Integer day14RegistNumTotal) {
		this.day14RegistNumTotal = day14RegistNumTotal;
	}
	public Integer getDay30LoginNumTotal() {
		return day30LoginNumTotal;
	}
	public void setDay30LoginNumTotal(Integer day30LoginNumTotal) {
		this.day30LoginNumTotal = day30LoginNumTotal;
	}
	public Integer getDay30RegistNumTotal() {
		return day30RegistNumTotal;
	}
	public void setDay30RegistNumTotal(Integer day30RegistNumTotal) {
		this.day30RegistNumTotal = day30RegistNumTotal;
	}
	public Integer getDay60LoginNumTotal() {
		return day60LoginNumTotal;
	}
	public void setDay60LoginNumTotal(Integer day60LoginNumTotal) {
		this.day60LoginNumTotal = day60LoginNumTotal;
	}
	public Integer getDay60RegistNumTotal() {
		return day60RegistNumTotal;
	}
	public void setDay60RegistNumTotal(Integer day60RegistNumTotal) {
		this.day60RegistNumTotal = day60RegistNumTotal;
	}
	public String getAllAgentIds() {
		return allAgentIds;
	}
	public void setAllAgentIds(String allAgentIds) {
		this.allAgentIds = allAgentIds;
	}
	public String getAllAgentNames() {
		return allAgentNames;
	}
	public void setAllAgentNames(String allAgentNames) {
		this.allAgentNames = allAgentNames;
	}
	
}
