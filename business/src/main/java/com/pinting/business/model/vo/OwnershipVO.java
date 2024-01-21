package com.pinting.business.model.vo;

import java.util.List;

/**
 * 归属变更查询VO
 * @author HuanXiong
 * @version 2016-6-13 上午11:07:06
 */
public class OwnershipVO extends PageInfoObject {

    /**  */
    private static final long serialVersionUID = 6738656672481252191L;
    
    private Integer id; // bs_user_customer_manager  ID
    
    private String userName;    //理财人姓名
    
    private String userNameAccurate;    // 精确查询姓名
    
    private String mobile;
    
    private String idCard;
    
    private Long deptId;    // 营业部ID
    
    private String deptName;    // 营业部
    
    private Long dafyUserId; // 客户经理
    
    private Long cookieUserId;	// cookie中的达飞userID
    
    private List<Long> deptList;
    
    public Long getCookieUserId() {
		return cookieUserId;
	}

	public void setCookieUserId(Long cookieUserId) {
		this.cookieUserId = cookieUserId;
	}

	public List<Long> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<Long> deptList) {
        this.deptList = deptList;
    }

    public String getUserNameAccurate() {
        return userNameAccurate;
    }

    public void setUserNameAccurate(String userNameAccurate) {
        this.userNameAccurate = userNameAccurate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getDafyUserId() {
        return dafyUserId;
    }

    public void setDafyUserId(Long dafyUserId) {
        this.dafyUserId = dafyUserId;
    }
    
}
