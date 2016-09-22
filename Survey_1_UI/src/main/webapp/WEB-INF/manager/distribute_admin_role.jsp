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
		
		<h2>[请选择角色]</h2>
		
		<form action="manager/admin/dispatcher" method="post">
			<input type="hidden" name="adminId" value="${adminId }"/>
			<table class="dataTable">
				
				<c:if test="${empty allRoleList }">
					<tr>
						<td>暂时没有可用的角色</td>
					</tr>
				</c:if>
				<c:if test="${!empty allRoleList }">
					
					<c:forEach items="${allRoleList }" var="role">
						
						<tr>
							<td>
								<input id="labelId${role.roleId }" 
									   type="checkbox" 
									   name="roleIdList" 
									   value="${role.roleId }"
									
									<c:forEach items="${currentIdList }" var="currentId">
										<c:if test="${currentId==role.roleId }">checked="checked"</c:if>
									</c:forEach>
										   
								/>
								<label for="labelId${role.roleId }">${role.roleName }</label>
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