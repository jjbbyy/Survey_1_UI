package com.atguigu.survey.entities.manager;

public class SurveyLog {
	
	//①OID
	private Integer logId;
	
	//②目标方法方法名
	private String methodName;
	
	//③实参
	private String params;
	
	//④返回值
	private String returnValue;
	
	//⑤操作人
	private String operator;
	
	//⑥操作时间
	private String operateTime;
	
	//⑦异常类型
	private String exceptionType;
	
	//⑧异常信息
	private String exceptionMessage;
	
	//⑨目标方法所在类的类型
	private String typeName;
	
	public SurveyLog() {
		
	}

	public SurveyLog(Integer logId, String methodName, String params,
			String returnValue, String operator, String operateTime,
			String exceptionType, String exceptionMessage, String typeName) {
		super();
		this.logId = logId;
		this.methodName = methodName;
		this.params = params;
		this.returnValue = returnValue;
		this.operator = operator;
		this.operateTime = operateTime;
		this.exceptionType = exceptionType;
		this.exceptionMessage = exceptionMessage;
		this.typeName = typeName;
	}

	@Override
	public String toString() {
		return "SurveyLog [logId=" + logId + ", methodName=" + methodName
				+ ", params=" + params + ", returnValue=" + returnValue
				+ ", operator=" + operator + ", operateTime=" + operateTime
				+ ", exceptionType=" + exceptionType + ", exceptionMessage="
				+ exceptionMessage + ", typeName=" + typeName + "]";
	}

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
