package com.pinting.gateway.hessian.message.dafy.model;

public class DafyDeptInfoModel {

	private String strDeptCode;
	
	private String strDeptName;
	
	private Integer nCurrentLevel;
	
	private String strDeptProvinceCode;
	
	private String strDeptProvinceName;
	
	private String strDeptCityCode;
	
	private String strDeptCityName;
	
	private String strDeptAddress;
	
	private String lManagerId;
	
	private String strManagerName;
	
	private boolean bIsSalesDept;
	
	private boolean bIsLeaf; //true是叶子节点，false不是叶子节点

	public boolean isbIsLeaf() {
		return bIsLeaf;
	}

	public void setbIsLeaf(boolean bIsLeaf) {
		this.bIsLeaf = bIsLeaf;
	}

	public String getStrDeptCode() {
		return strDeptCode;
	}

	public void setStrDeptCode(String strDeptCode) {
		this.strDeptCode = strDeptCode;
	}

	public String getStrDeptName() {
		return strDeptName;
	}

	public void setStrDeptName(String strDeptName) {
		this.strDeptName = strDeptName;
	}

	public Integer getnCurrentLevel() {
		return nCurrentLevel;
	}

	public void setnCurrentLevel(Integer nCurrentLevel) {
		this.nCurrentLevel = nCurrentLevel;
	}

	public String getStrDeptProvinceCode() {
		return strDeptProvinceCode;
	}

	public void setStrDeptProvinceCode(String strDeptProvinceCode) {
		this.strDeptProvinceCode = strDeptProvinceCode;
	}

	public String getStrDeptProvinceName() {
		return strDeptProvinceName;
	}

	public void setStrDeptProvinceName(String strDeptProvinceName) {
		this.strDeptProvinceName = strDeptProvinceName;
	}

	public String getStrDeptCityCode() {
		return strDeptCityCode;
	}

	public void setStrDeptCityCode(String strDeptCityCode) {
		this.strDeptCityCode = strDeptCityCode;
	}

	public String getStrDeptCityName() {
		return strDeptCityName;
	}

	public void setStrDeptCityName(String strDeptCityName) {
		this.strDeptCityName = strDeptCityName;
	}

	public String getStrDeptAddress() {
		return strDeptAddress;
	}

	public void setStrDeptAddress(String strDeptAddress) {
		this.strDeptAddress = strDeptAddress;
	}

	public String getlManagerId() {
		return lManagerId;
	}

	public void setlManagerId(String lManagerId) {
		this.lManagerId = lManagerId;
	}

	public String getStrManagerName() {
		return strManagerName;
	}

	public void setStrManagerName(String strManagerName) {
		this.strManagerName = strManagerName;
	}

	public boolean getbIsSalesDept() {
		return bIsSalesDept;
	}

	public void setbIsSalesDept(boolean bIsSalesDept) {
		this.bIsSalesDept = bIsSalesDept;
	}
}
