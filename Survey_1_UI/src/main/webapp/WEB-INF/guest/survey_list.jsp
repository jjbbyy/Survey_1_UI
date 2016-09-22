<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/res_jsp/base.jsp" %>
<script type="text/javascript">
	
	$(function(){
		$(".deeplyRemove").click(function(){
			
			var surveyName = $(this).parents("tr").children("td:eq(2)").text();
			
			var firstConfirm = confirm("真的要删除"+surveyName+"这个调查吗？");
			
			if(!firstConfirm) {
				return false;
			}
			
			var secondConfirm = confirm("亲，这个操作可危险啦！会把里面的包裹和问题都删掉！真的要这么干吗？ ");
			
			if(secondConfirm) {
				return true;
			}
			
			return false;
		});
	});
	
</script>
</head>
<body>

	<%@ include file="/res_jsp/guest_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		[显示未完成调查]
		
		<table class="dataTable">
			
			<c:if test="${empty page.list }">
				<tr>
					<td>您尚未创建调查！</td>
				</tr>
			</c:if>
			<c:if test="${!empty page.list }">
				<tr>
					<td>ID</td>
					<td>Logo</td>
					<td>调查名称</td>
					<td colspan="4">操作</td>
				</tr>
				
				<c:forEach items="${page.list }" var="survey">
					
					<tr>
						<td>${survey.surveyId }</td>
						<td>
							<img src="${survey.logoPath }"/>
						</td>
						<td>${survey.surveyName }</td>
						<td>
							<a href="guest/survey/toDesignUI/${survey.surveyId }">设计</a>
						</td>
						<td>
							<a href="guest/survey/removeSurvey/${survey.surveyId }">删除</a>
						</td>
						<td>
							<a href="guest/survey/toEditUI/${survey.surveyId }">更新</a>
						</td>
						<td style="background-color: black;">
							<a href="guest/survey/deeplyRemove/${survey.surveyId }" class="deeplyRemove"><span style="color: yellow;">深度删除</span></a>
						</td>
					</tr>
					
				</c:forEach>
				
				<tr>
					<td colspan="7">
						<!-- 
						value属性：要设置的具体指
						scope属性：要将值设置到哪个域对象中
						var属性：将值保存到域对象中时，属性名
						 -->
						<c:set value="guest/survey/showMyUncompleted" var="targetUrl" scope="page"/>
						<%@ include file="/res_jsp/navigator.jsp" %>
					</td>
				</tr>
				
			</c:if>
			
		</table>
		
	</div>
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>