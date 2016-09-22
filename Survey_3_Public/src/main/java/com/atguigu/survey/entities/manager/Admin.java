package com.atguigu.survey.entities.manager;

import java.util.Set;

public class Admin {
	
	private Integer adminId;
	private String adminName;
	private String adminPwd;
	
	private Set<Role> roleSet;
	
	//private int [] codeArr;通过hbm方式映射不方便
	private String codeArrStr;
	
	public Admin() {
		// TODO Auto-generated constructor stub
	}

	public Admin(Integer adminId, String adminName, String adminPwd) {
		super();
		this.adminId = adminId;
		this.adminName = adminName;
		this.adminPwd = adminPwd;
	}

	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", adminName=" + adminName
				+ ", adminPwd=" + adminPwd + "]";
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminPwd() {
		return adminPwd;
	}

	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}

	public Set<Role> getRoleSet() {
		return roleSet;
	}
	
	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}
	
	public void setCodeArrStr(String codeArrStr) {
		this.codeArrStr = codeArrStr;
	}
	
	public String getCodeArrStr() {
		return codeArrStr;
	}
}
