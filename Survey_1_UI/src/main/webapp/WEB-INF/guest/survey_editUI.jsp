<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/res_jsp/base.jsp" %>
</head>
<body>

	<%@ include file="/res_jsp/guest_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		[更新调查]
		<form:form action="guest/survey/updateSurvey" 
				   method="post" 
				   modelAttribute="survey"
				   enctype="multipart/form-data">
		
			<form:hidden path="surveyId"/>
			<form:hidden path="logoPath"/>
			
			<table class="formTable">
			
				<c:if test="${exception != null }">
					<tr>
						<td colspan="2">
							${exception.message }
						</td>
					</tr>
				</c:if>
				
				<tr>
					<td>调查名称</td>
					<td>
						<form:input path="surveyName" cssClass="longInput"/>
					</td>
				</tr>
				
				<tr>
					<td>当前图片</td>
					<td>
						<img src="${survey.logoPath }"/>
					</td>
				</tr>
				
				<tr>
					<td>选择图片</td>
					<td>
						<input type="file" name="logoFile"/>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="submit" value="更新"/>
					</td>
				</tr>
				
			</table>
			
		</form:form>
		
	</div>
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>