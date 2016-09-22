package com.atguigu.survey.entities.guest;

public class Answer {
	
	private Integer answerId;
	
	//答案内容
	private String content;
	
	//批次
	private String uuid;
	
	private Integer questionId;
	
	private Integer surveyId;
	
	public Answer() {
		
	}

	public Answer(Integer answerId, String content, String uuid,
			Integer questionId, Integer surveyId) {
		super();
		this.answerId = answerId;
		this.content = content;
		this.uuid = uuid;
		this.questionId = questionId;
		this.surveyId = surveyId;
	}

	@Override
	public String toString() {
		return "Answer [answerId=" + answerId + "@ content=" + content
				+ "@ uuid=" + uuid + "@ questionId=" + questionId
				+ "@ surveyId=" + surveyId + "]";
	}

	public Integer getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

}
