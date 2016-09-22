package com.atguigu.survey.entities.guest;

import java.io.Serializable;

public class Question implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer questionId;
	private String questionName;
	private int questionType;
	private String options;
	
	private Bag bag;
	
	public Question() {
		// TODO Auto-generated constructor stub
	}

	public Question(Integer questionId, String questionName, int questionType,
			String options, Bag bag) {
		super();
		this.questionId = questionId;
		this.questionName = questionName;
		this.questionType = questionType;
		this.options = options;
		this.bag = bag;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", questionName="
				+ questionName + ", questionType=" + questionType
				+ ", options=" + options + "]";
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public int getQuestionType() {
		return questionType;
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}

	public String getOptions() {
		return options;
	}
	
	//------------------------特殊设置的方法-------------------------

	//在自动生成的方法基础上修改
	public void setOptions(String options) {
		this.options = options.replaceAll("\r\n", ",");
	}
	
	//新声明的
	public String[] getOptionArr() {
		return this.options.split(",");
	}
	
	//新声明的
	public String getOptionsForEdit() {
		return this.options.replaceAll(",", "\r\n");
	}
	
	//------------------------------------------------------------

	public Bag getBag() {
		return bag;
	}

	public void setBag(Bag bag) {
		this.bag = bag;
	}

}
