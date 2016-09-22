<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/res_jsp/base.jsp" %>
<script type="text/javascript">
	
	$(function(){
		
		$(":text").change(function(){
			
			var roleName = $.trim(this.value);
			var id = this.id;
			
			if(roleName == "") {
				this.value = this.defaultValue;
				return ;
			}
			
			var url = "${pageContext.request.contextPath}/manager/role/updateRoleName";
			var paramData = {"roleName":roleName,"roleId":id,"time":new Date()};
			var callBack = function(respData){
				
			};
			var type = "text";
			
			$.post(url, paramData, callBack, type);
			
		});
		
	});
	
</script>
</head>
<body>

	<%@ include file="/res_jsp/manager_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		[显示角色列表]
		<form action="manager/role/batchDelete" method="post">
			<table class="dataTable">
				
				<c:if test="${empty roleList }">
					
					<tr>
						<td>当前尚未创建任何角色</td>
					</tr>
					
				</c:if>
				
				<c:if test="${!empty roleList }">
					<tr>
						<td>ID</td>
						<td>Role Name</td>
						<td>删除</td>
						<td>分配</td>
					</tr>
					
					<c:forEach items="${roleList }" var="role">
						
						<tr>
							<td>${role.roleId }</td>
							<td>
								<input id="${role.roleId }" type="text" class="longInput" value="${role.roleName }"/>
							</td>
							<td>
								<input type="checkbox" name="roleIdList" value="${role.roleId }"/>
							</td>
							<td>
								<a href="manager/role/toDispatcherUI/${role.roleId }">分配权限</a>
							</td>
						</tr>
						
					</c:forEach>
					
					<tr>
						<td colspan="4">
							<input type="submit" value="批量删除"/>
						</td>
					</tr>
					
				</c:if>
				
			</table>
		</form>
		
	</div>
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>