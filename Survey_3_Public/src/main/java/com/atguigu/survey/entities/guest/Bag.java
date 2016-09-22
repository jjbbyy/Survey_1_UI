package com.atguigu.survey.entities.guest;

import java.io.Serializable;
import java.util.Set;

public class Bag implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer bagId;
	private String bagName;
	private Integer bagOrder;
	private transient Survey survey;
	
	private Set<Question> questionSet;
	
	public Bag() {
		// TODO Auto-generated constructor stub
	}

	public Bag(Integer bagId, String bagName, Integer bagOrder, Survey survey) {
		super();
		this.bagId = bagId;
		this.bagName = bagName;
		this.bagOrder = bagOrder;
		this.survey = survey;
	}

	public Integer getBagId() {
		return bagId;
	}

	public void setBagId(Integer bagId) {
		this.bagId = bagId;
	}

	public String getBagName() {
		return bagName;
	}

	public void setBagName(String bagName) {
		this.bagName = bagName;
	}

	public Integer getBagOrder() {
		return bagOrder;
	}

	public void setBagOrder(Integer bagOrder) {
		this.bagOrder = bagOrder;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	@Override
	public String toString() {
		return "Bag [bagId=" + bagId + ", bagName=" + bagName + ", bagOrder="
				+ bagOrder + "]";
	}

	public Set<Question> getQuestionSet() {
		return questionSet;
	}

	public void setQuestionSet(Set<Question> questionSet) {
		this.questionSet = questionSet;
	}

}
