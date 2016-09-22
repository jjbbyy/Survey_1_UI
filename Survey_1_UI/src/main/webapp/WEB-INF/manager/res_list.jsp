<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/res_jsp/base.jsp" %>
<script type="text/javascript">
	$(function(){
		
		$(".toggleStatus").click(function(){
			
			//1.获取当前资源的id值
			var resId = this.id;//访问input标签的id属性
			//console.log("resId="+resId);
			console.log(this);
			
			//※为了让Ajax回调函数中能够操作input标签对象，在外面声明变量将this的引用保存起来
			var thisReference = this;
			
			//2.为Ajax请求的$.post()方法准备参数数据
			var url = "${pageContext.request.contextPath}/manager/res/toggleStatus";
			var paramData = {"resId":resId,"time":new Date()};
			var callBack = function(respData){
				//{"message":"成功","statusValue":"受保护资源/公共资源"}
				alert(respData.message+" "+respData.statusValue);
				
				//JavaScript中，this代表调用当前函数的那个对象
				//this.value = respData.statusValue;
				
				//由于当前Ajax回调函数是由jQuery框架调用的，所以此时这里的this已经不是原来的那个input标签对象了
				console.log(this);
				
				//使用外面保存过的input标签对象的引用执行操作
				thisReference.value = respData.statusValue;
			};
			var type = "json";
			
			//3.调用$.post()方法发送Ajax请求
			$.post(url, paramData, callBack, type);
		});
		
	});
</script>
</head>
<body>

	<%@ include file="/res_jsp/manager_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		[资源列表]
		<form action="manager/res/batchDelete" method="post">
		<table class="dataTable">
			
			<c:if test="${empty resList }">
				<tr>
					<td>暂时没有资源可以显示</td>
				</tr>
			</c:if>
			<c:if test="${!empty resList }">
				
				<tr>
					<td>ID</td>
					<td>资源名称</td>
					<td>资源状态</td>
					<td>删除</td>
				</tr>
				
				<c:forEach items="${resList }" var="res">
					
					<tr>
						<td>${res.resId }</td>
						<td>${res.servletPath }</td>
						<td>
							<input type="button" 
								   id="${res.resId }"
								   class="toggleStatus" 
								   value="${res.publicRes?'公共资源':'受保护资源' }"/>
							<!-- 注意在这里不用使用这种按钮，因为它在表单里时，点击后会提交表单<button>xxx</button> -->
						</td>
						<td>
							<input id="label${res.resId }" type="checkbox" name="resIdList" value="${res.resId }"/>
							<label for="label${res.resId }">点我更轻松</label>
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