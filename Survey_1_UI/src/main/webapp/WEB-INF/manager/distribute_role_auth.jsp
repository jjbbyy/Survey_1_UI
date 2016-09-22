<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/res_jsp/base.jsp" %>
</head>
<body>

	<%@ include file="/res_jsp/manager_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		<h2>[请选择权限]</h2>
		
		<form action="manager/role/dispatcher" method="post">
			<input type="hidden" name="roleId" value="${roleId }"/>
			<table class="dataTable">
				
				<c:if test="${empty allAuthList }">
					<tr>
						<td>暂时没有可用的权限</td>
					</tr>
				</c:if>
				<c:if test="${!empty allAuthList }">
					
					<c:forEach items="${allAuthList }" var="auth">
						
						<tr>
							<td>
								<input id="labelId${auth.authId }" 
									   type="checkbox" 
									   name="authIdList" 
									   value="${auth.authId }"
									
									<c:forEach items="${currentIdList }" var="currentId">
										<c:if test="${currentId==auth.authId }">checked="checked"</c:if>
									</c:forEach>
										   
								/>
								<label for="labelId${auth.authId }">${auth.authName }</label>
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
		
	</div>
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>