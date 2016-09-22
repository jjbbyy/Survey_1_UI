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
			
			var bagName = $(this).prev().text();
			
			var firstConfirm = confirm("你真的要删除"+bagName+"这个包裹吗？");
			
			if(firstConfirm) {
				
				var secondConfirm = confirm("深度删除，真的确定吗？");
				if(secondConfirm) {
					return true;
				}
				
			}

			return false;
		});
	});
	
</script>
</head>
<body>

	<%@ include file="/res_jsp/guest_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		[设计调查]
		<table class="dataTable">
			<tr>
				<td colspan="4">调查基本信息</td>
			</tr>
			<tr>
				<td><img src="${survey.logoPath }"/></td>
				<td>${survey.surveyName }</td>
				<td>
					<a href="guest/bag/toAddUI/${survey.surveyId }">创建包裹</a>
				</td>
				<td>
					<a href="guest/bag/toAdjustUI/${survey.surveyId }">调整包裹的顺序</a>
				</td>
			</tr>
		</table>
		
		<br/><br/>
		
		<table class="dataTable">
			
			<c:if test="${empty survey.bagSet }">
				<tr>
					<td>尚未创建包裹</td>
				</tr>
			</c:if>
			<c:if test="${!empty survey.bagSet }">
			
				<tr>
					<td colspan="2">包裹列表</td>
				</tr>
				
				<tr>
					<td>包裹名称</td>
					<td>基本操作</td>
				</tr>
				
				<c:forEach items="${requestScope.survey.bagSet }" var="bag">
					
					<tr>
						<td>${bag.bagName }</td>
						<td>
							<a href="guest/bag/removeBag/${bag.bagId }/${survey.surveyId}">删除包裹</a>
							<a href="guest/bag/toEditUI/${bag.bagId }/${survey.surveyId}">更新包裹</a>
							<a href="guest/bag/toMoveOrCopyUI/${bag.bagId }/${survey.surveyId}"><span style="color:red">移动/复制包裹</span></a>
							<a href="guest/question/toAddUI/${survey.surveyId}/${bag.bagId}">创建问题</a>
							<a class="deeplyRemove" style="background-color: black;color: yellow;" href="guest/bag/deeplyRemove/${bag.bagId }/${survey.surveyId}">深度删除</a>
						</td>
					</tr>
					
					<tr>
						<!-- 为了让问题列表和包裹列表区分开，这里留一个空单元格 -->
						<td></td>
						<td>
							<!-- 由于问题列表要显示的信息也比较多，所以这里嵌套一个表格 -->
							<table class="dataTable">
								<c:if test="${empty bag.questionSet }">
									<tr>
										<td>尚未创建问题</td>
									</tr>
								</c:if>
								<c:if test="${!empty bag.questionSet }">
									
									<tr>
										<td>问题详情</td>
										<td>问题操作</td>
									</tr>
									
									<c:forEach items="${bag.questionSet }" var="question">
										<tr>
											<td>
												${question.questionName }
												
												<c:if test="${question.questionType==0 }">
													<c:forEach items="${question.optionArr }" var="option">
														<input type="radio"/>${option }
													</c:forEach>
												</c:if>
												
												<c:if test="${question.questionType==1 }">
													<c:forEach items="${question.optionArr }" var="option">
														<input type="checkbox"/>${option }
													</c:forEach>
												</c:if>
												
												<c:if test="${question.questionType==2 }">
													<input type="text" class="longInput"/>
												</c:if>
												
											</td>
											<td>
												<a href="guest/question/removeQuestion/${question.questionId}/${survey.surveyId}">删除问题</a>
												<a href="guest/question/toEditUI/${question.questionId}/${survey.surveyId}">更新问题</a>
											</td>
										</tr>
									</c:forEach>
									
								</c:if>
							</table>
						</td>
					</tr>
					
				</c:forEach>
			
			</c:if>
			
			<tr>
				<td colspan="2">
					<a href="guest/survey/completed/${survey.surveyId }">完成调查</a>
				</td>
			</tr>
			
		</table>
		
	</div>
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>