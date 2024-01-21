package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 根据不同显示端口查询币港湾首页所需数据 入参
 * @author huangmengjian
 * @2015-1-16 下午3:22:22
 */
public class ReqMsg_Home_InfoQuery  extends ReqMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8977342465463364067L;
	
	/*用户类型*/
	private String userType;
	/*产品显示端口*/
    private String productShowTerminal;
    /*用户id*/
    private Integer userId;

    /*micro2.0/gen2.0/gen178*/
    private String terminal;

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getProductShowTerminal() {
        return productShowTerminal;
    }

    public void setProductShowTerminal(String productShowTerminal) {
        this.productShowTerminal = productShowTerminal;
    }
    
}
