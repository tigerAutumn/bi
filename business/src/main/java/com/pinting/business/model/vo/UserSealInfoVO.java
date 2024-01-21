package com.pinting.business.model.vo;


/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: UserSealReq.java, v 0.1 2015-9-17 上午10:01:14 BabyShark Exp $
 */
public class UserSealInfoVO {
    private Integer userId;

    private String  userName;

    private String  idCard;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

}
