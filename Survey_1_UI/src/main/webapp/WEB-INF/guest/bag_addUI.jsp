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
		
		[创建包裹]
		<form action="guest/bag/saveBag" method="post">
		
			<!-- 表单隐藏域的name属性值：控制将值提交到服务器端的形式 -->
			<!-- 1.作为目标实体类对象的属性或级联属性：survey.surveyId -->
			<!-- 2.不考虑目标实体类，让目标handler方法另外使用@RequestParam形式接收 -->
			
			<!-- 表单隐藏域的value属性值：真正要提交到服务器端的值 -->
			<input type="hidden" name="survey.surveyId" value="${surveyId }"/>
		
			<table class="formTable">
				
				<tr>
					<td>包裹名称</td>
					<td>
						<input type="text" name="bagName" class="longInput"/>
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