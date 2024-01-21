package com.pinting.manage.model;

import java.util.Date;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class InsUser implements java.io.Serializable {

	// Fields

	private Integer userid;
	private String username;
	private String usertype;
	private String password;
	private Date opendate;
	private String state;
	private Date lastlogin;
	private Date closedate;
	private String usernote;
	private Integer perdayloginnum;
	private Integer failloginnum;
	private Date faillogindate;

	// Constructors

	/** default constructor */
	public InsUser() {
	}

	/** full constructor */
	public InsUser( String username, String usertype,
			String password, Date opendate, String state,
			Date lastlogin, Date closedate, String usernote,
			Integer perdayloginnum, Integer failloginnum, Date faillogindate) {
		this.username = username;
		this.usertype = usertype;
		this.password = password;
		this.opendate = opendate;
		this.state = state;
		this.lastlogin = lastlogin;
		this.closedate = closedate;
		this.usernote = usernote;
		this.perdayloginnum = perdayloginnum;
		this.failloginnum = failloginnum;
		this.faillogindate = faillogindate;
	}

	// Property accessors

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}


	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsertype() {
		return this.usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public Date getOpendate() {
		return this.opendate;
	}

	public void setOpendate(Date opendate) {
		this.opendate = opendate;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getLastlogin() {
		return this.lastlogin;
	}

	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
	}

	public Date getClosedate() {
		return this.closedate;
	}

	public void setClosedate(Date closedate) {
		this.closedate = closedate;
	}

	public String getUsernote() {
		return this.usernote;
	}

	public void setUsernote(String usernote) {
		this.usernote = usernote;
	}


	public Integer getPerdayloginnum() {
		return this.perdayloginnum;
	}

	public void setPerdayloginnum(Integer perdayloginnum) {
		this.perdayloginnum = perdayloginnum;
	}

	public Integer getFailloginnum() {
		return this.failloginnum;
	}

	public void setFailloginnum(Integer failloginnum) {
		this.failloginnum = failloginnum;
	}

	public Date getFaillogindate() {
		return this.faillogindate;
	}

	public void setFaillogindate(Date faillogindate) {
		this.faillogindate = faillogindate;
	}

}