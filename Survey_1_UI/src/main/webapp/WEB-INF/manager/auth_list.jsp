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
			
			var authName = $.trim(this.value);
			var id = this.id;
			
			if(authName == "") {
				this.value = this.defaultValue;
				return ;
			}
			
			var url = "${pageContext.request.contextPath}/manager/auth/updateAuthName";
			var paramData = {"authName":authName,"authId":id,"time":new Date()};
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
		
		[显示权限列表]
		<form action="manager/auth/batchDelete" method="post">
			<table class="dataTable">
				
				<c:if test="${empty authList }">
					
					<tr>
						<td>当前尚未创建任何权限</td>
					</tr>
					
				</c:if>
				
				<c:if test="${!empty authList }">
					<tr>
						<td>ID</td>
						<td>Auth Name</td>
						<td>删除</td>
						<td>分配</td>
					</tr>
					
					<c:forEach items="${authList }" var="auth">
						
						<tr>
							<td>${auth.authId }</td>
							<td>
								<input id="${auth.authId }" type="text" class="longInput" value="${auth.authName }"/>
							</td>
							<td>
								<input type="checkbox" name="authIdList" value="${auth.authId }"/>
							</td>
							<td>
								<a href="manager/auth/toDistributeUI/${auth.authId }">分配资源</a>
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