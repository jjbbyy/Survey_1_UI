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
		
		[给权限分配资源]
		<form action="manager/auth/doDistribute" method="post">
			
			<input type="hidden" name="authId" value="${authId }"/>
			
			<table class="dataTable">
			
				<c:if test="${empty allResList }">
					<tr>
						<td>暂时没有可以分配的资源</td>
					</tr>
				</c:if>
				<c:if test="${!empty allResList }">
					
					<c:forEach items="${allResList }" var="res">
						
						<tr>
							<td>
								<input id="checkbox${res.resId }" 
									   type="checkbox" 
									   name="resIdList" 
									   value="${res.resId }"
									   
										<c:forEach items="${currentResIdList }" var="resIdHistory">
											<c:if test="${res.resId==resIdHistory }">checked="checked"</c:if>
										</c:forEach>
									   
									   />
								<label for="checkbox${res.resId }">${res.servletPath }</label>
							</td>
						</tr>
						
					</c:forEach>
					
					<tr>
						<td>
							<input type="submit" value="OK"/>
						</td>
					</tr>
				
				</c:if>
			
			</table>
		</form>
		
		<%-- ${allResList }
		<br/>
		${currentResIdList }
		
		[1,2,3,4,5,6,7,8,9]
		[3,4,5]
		
		<br/>
		${authId } --%>
		
	</div>
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>