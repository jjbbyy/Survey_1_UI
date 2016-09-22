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
		
		[查看日志数据]
		<table class="dataTable">
			<c:if test="${empty page.list }">
				<tr><td>暂时没有日志数据</td></tr>
			</c:if>
			
			<c:if test="${!empty page.list }">
				
				<c:forEach items="${page.list }" var="surveyLog">
					<tr>
						<td>ID</td>
						<td>详情</td>
					</tr>
					<tr>
						<td>${surveyLog.logId }</td>
						<td>
							目标方法：${surveyLog.methodName }<br/>
							方式所属类型：${surveyLog.typeName }<br/>
							实际参数：${surveyLog.params }<br/>
							返回值：${surveyLog.returnValue }<br/>
							操作人：${surveyLog.operator }<br/>
							操作时间：${surveyLog.operateTime }<br/>
							异常类型：${surveyLog.exceptionType }<br/>
							异常信息：${surveyLog.exceptionMessage }<br/>
						</td>
					</tr>
				</c:forEach>
				
				<tr>
					<td colspan="2">
						<c:set var="targetUrl" value="manager/log/showList" scope="page"/>
						<%@ include file="/res_jsp/navigator.jsp" %>
					</td>
				</tr>
				
			</c:if>
			
		</table>
		
	</div>
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>