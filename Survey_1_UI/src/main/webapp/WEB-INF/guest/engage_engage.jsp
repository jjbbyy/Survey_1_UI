<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.atguigu.com/survey" prefix="atguigu" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/res_jsp/base.jsp" %>
</head>
<body>

	<%@ include file="/res_jsp/guest_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		[参与调查]
		<!-- 一、调查的基本信息 -->
		<table class="dataTable">
			<tr>
				<td colspan="2">调查信息</td>
			</tr>
			<tr>
				<td><img src="${sessionScope.currentSurvey.logoPath }"/></td>
				<td>${sessionScope.currentSurvey.surveyName }</td>
			</tr>
		</table>
		
		<br/><br/>
		
		<!-- 二、每一个包裹所在的表单 -->
		<!-- 1.form标签 -->
		<form action="guest/engage/engage" method="post">
		
			<!-- 2.设置表单隐藏域 -->
			<input type="hidden" name="currentIndex" value="${requestScope.currentIndex }"/>
			<input type="hidden" name="bagId" value="${requestScope.currentBag.bagId }"/>
		
			<!-- 3.包裹数据 -->
			<table class="dataTable">
				
				<tr>
					<td>当前包裹名称：${requestScope.currentBag.bagName }</td>
				</tr>
				<tr>
					<td>
						<c:forEach items="${requestScope.currentBag.questionSet }" var="question">
							${question.questionName }
							<c:if test="${question.questionType==0 }">
								<c:forEach items="${question.optionArr }" var="option" varStatus="myStatus">
									<input type="radio" 
										   name="q${question.questionId }" 
										   value="${myStatus.index }" 
										   id="label${question.questionId }${myStatus.index }"
										   
											<atguigu:questionDetail 
												inputValue="${myStatus.index }" 
												questionType="${question.questionType }" 
												inputName="q${question.questionId }" 
												bagId="${requestScope.currentBag.bagId }"/>
										   
										   />
									<label for="label${question.questionId }${myStatus.index }">${option }</label>&nbsp;
								</c:forEach>
							</c:if>
							<c:if test="${question.questionType==1 }">
								<c:forEach items="${question.optionArr }" var="option" varStatus="myStatus">
									<input type="checkbox" 
										   name="q${question.questionId }" 
										   value="${myStatus.index }" 
										   id="label${question.questionId }${myStatus.index }"
										   
											<atguigu:questionDetail 
												inputValue="${myStatus.index }" 
												questionType="${question.questionType }" 
												inputName="q${question.questionId }" 
												bagId="${requestScope.currentBag.bagId }"/>
										   
										   />
									<label for="label${question.questionId }${myStatus.index }">${option }</label>&nbsp;
								</c:forEach>
							</c:if>
							<c:if test="${question.questionType==2 }">
								<input type="text" name="q${question.questionId }" class="longInput"
										   
									<atguigu:questionDetail 
										questionType="${question.questionType }" 
										inputName="q${question.questionId }" 
										bagId="${requestScope.currentBag.bagId }"/>
								   
								   />
							</c:if>
							<br/>
						</c:forEach>
					</td>
				</tr>
				
				<!-- 4.四个提交按钮 -->
				<tr>
					<td>
						<c:if test="${requestScope.currentIndex>0 }">
							<input type="submit" name="submit_prev" value="返回上一个包裹"/>
						</c:if>
						<c:if test="${requestScope.currentIndex<sessionScope.lastIndex }">
							<input type="submit" name="submit_next" value="进入下一个包裹"/>
						</c:if>
						<c:if test="${requestScope.currentIndex==sessionScope.lastIndex }">
							<input type="submit" name="submit_done" value="完成"/>
						</c:if>
						<input type="submit" name="submit_quit" value="放弃"/>
					</td>
				</tr>
				<%-- <tr>
					<td>
						${requestScope.currentIndex }<br/>
						${sessionScope.lastIndex }
					</td>
				</tr> --%>
			
			</table>
		
		</form>
		
		
	</div>
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>