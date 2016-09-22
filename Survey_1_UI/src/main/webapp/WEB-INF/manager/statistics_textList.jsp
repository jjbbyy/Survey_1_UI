<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/res_jsp/base.jsp" %>
</head>
<body>

	<%@ include file="/res_jsp/manager_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		[显示简答题答案]
		
		<table class="dataTable">
			<c:forEach items="${textAnswerList }" var="content">
				<tr>
					<td>${content }</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>