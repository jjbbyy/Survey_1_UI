package com.atguigu.survey.entities.manager;

public class Res {
	
	private Integer resId;
	private String servletPath;
	private int resCode;
	private int resPos;
	private boolean publicRes = false;
	
	public Res() {
		// TODO Auto-generated constructor stub
	}

	public Res(Integer resId, String servletPath, int resCode, int resPos,
			boolean publicRes) {
		super();
		this.resId = resId;
		this.servletPath = servletPath;
		this.resCode = resCode;
		this.resPos = resPos;
		this.publicRes = publicRes;
	}

	@Override
	public String toString() {
		return "Res [resId=" + resId + ", servletPath=" + servletPath
				+ ", resCode=" + resCode + ", resPos=" + resPos
				+ ", publicRes=" + publicRes + "]";
	}

	public Integer getResId() {
		return resId;
	}

	public void setResId(Integer resId) {
		this.resId = resId;
	}

	public String getServletPath() {
		return servletPath;
	}

	public void setServletPath(String servletPath) {
		this.servletPath = servletPath;
	}

	public int getResCode() {
		return resCode;
	}

	public void setResCode(int resCode) {
		this.resCode = resCode;
	}

	public int getResPos() {
		return resPos;
	}

	public void setResPos(int resPos) {
		this.resPos = resPos;
	}

	public boolean isPublicRes() {
		return publicRes;
	}

	public void setPublicRes(boolean publicRes) {
		this.publicRes = publicRes;
	}

}
