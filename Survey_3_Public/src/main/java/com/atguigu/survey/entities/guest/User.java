package com.atguigu.survey.entities.guest;

import java.util.Set;

import com.atguigu.survey.entities.manager.Role;

public class User {
	
	private Integer userId;
	private String userName;
	private String userPwd;
	
	//true:企业用户
	//false:个人用户
	private boolean company;
	
	private Set<Role> roleSet;
	
	private String codeArrStr;
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(Integer userId, String userName, String userPwd, boolean company) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPwd = userPwd;
		this.company = company;
	}

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

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public boolean isCompany() {
		return company;
	}

	public void setCompany(boolean company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName
				+ ", userPwd=" + userPwd + ", company=" + company + "]";
	}
	
	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}
	
	public Set<Role> getRoleSet() {
		return roleSet;
	}
	
	public void setCodeArrStr(String codeArrStr) {
		this.codeArrStr = codeArrStr;
	}
	
	public String getCodeArrStr() {
		return codeArrStr;
	}
	
}
