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
		
		[更新包裹]
		<form:form action="guest/bag/updateBag" method="post" modelAttribute="bag">
		
			<form:hidden path="bagId"/>
			
			<!-- path相当于HTML标签中的name属性：控制将数据以什么方式提交到服务器端 -->
			<!-- value属性：真正要提交到服务器端的值 -->
			<form:hidden path="survey.surveyId" value="${surveyId }"/>
			
			<table class="formTable">
				
				<tr>
					<td>包裹名称</td>
					<td>
						<form:input path="bagName" cssClass="longInput"/>
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