package com.github.waboot.platform.login;

import java.io.Serializable;

/**
 * 用户信息bean
 * @author wangjing.dc@qq.com
 *
 */
public class UserInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String userId;
	private String userName;
	private String password;
	private String orgId;
	private String orgName;
	
	
	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getOrgId() {
		return orgId;
	}



	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}



	public String getOrgName() {
		return orgName;
	}



	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


}