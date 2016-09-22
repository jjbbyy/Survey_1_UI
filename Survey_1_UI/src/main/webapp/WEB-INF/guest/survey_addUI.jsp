<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/res_jsp/base.jsp" %>
</head>
<body>

	<%@ include file="/res_jsp/guest_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		[创建调查]
		<form action="guest/survey/saveSurvey" method="post" enctype="multipart/form-data">
			
			<table class="formTable">
			
				<c:if test="${exception != null }">
					<tr>
						<td colspan="2">${exception.message }</td>
					</tr>
				</c:if>
				
				<tr>
					<td>调查名称</td>
					<td>
						<input type="text" 
							   name="surveyName" 
							   class="longInput" 
							   value="${requestScope.survey.surveyName }"/>
					</td>
				</tr>
				
				<tr>
					<td>选择图片</td>
					<td>
						<!-- 文件上传框提交的数据是二进制数据，在handler方法中需要使用MultipartFile类型接收 -->
						<!-- 所以不能使用String类型的logoPath属性接收 -->
						<!-- 如果强行使用logoPath属性接收，就会有类型转换失败问题，在页面上表现为400 -->
						<input type="file" name="logoFile"/>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="submit" value="保存"/>
					</td>
				</tr>
				
			</table>
			
		</form>
		
	</div>
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>