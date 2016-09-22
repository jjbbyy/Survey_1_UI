package com.atguigu.survey.entities.guest;

import java.util.Set;

public class Survey {
	
	private Integer surveyId;
	private String surveyName;
	private boolean completedStatus = false;
	
	//<base .../>
	//<img src="res_static/logo.gif"/>
	private String logoPath = "res_static/logo.gif";
	
	private User user;
	
	private Set<Bag> bagSet;
	
	public Survey() {
		
	}

	public Survey(Integer surveyId) {
		super();
		this.surveyId = surveyId;
	}

	public Survey(Integer surveyId, String surveyName, boolean completedStatus,
			String logoPath, User user) {
		super();
		this.surveyId = surveyId;
		this.surveyName = surveyName;
		this.completedStatus = completedStatus;
		this.logoPath = logoPath;
		this.user = user;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	public boolean isCompletedStatus() {
		return completedStatus;
	}

	public void setCompletedStatus(boolean completedStatus) {
		this.completedStatus = completedStatus;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Survey [surveyId=" + surveyId + ", surveyName=" + surveyName
				+ ", completedStatus=" + completedStatus + ", logoPath="
				+ logoPath + "]";
	}

	public Set<Bag> getBagSet() {
		return bagSet;
	}

	public void setBagSet(Set<Bag> bagSet) {
		this.bagSet = bagSet;
	}

}
