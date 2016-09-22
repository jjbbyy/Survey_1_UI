<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/res_jsp/base.jsp" %>
<script type="text/javascript">
	
	$(function(){
		
		//一、页面初始化时，根据题型决定选项所在的行是否显示
		//1.读取当前被选中的单选按钮的值
		var type = $(":radio:checked").val();
		console.log(type);
		
		//2.检查1中的值对应的题型
		if(type == 2) {
			$("#optionTr").hide();
		}
		
		//二、点击题型时控制选项所在的行是否显示
		$(":radio").click(function(){
			
			//获取当前被选中的单选按钮的值
			var type = this.value;
			
			//检查type对应的题型是否是“单选题”或“多选题”
			if(type == 0 || type == 1) {
				$("#optionTr").show();
			}else{
				$("#optionTr").hide();
			}
			
		});
		
	});
	
</script>
</head>
<body>

	<%@ include file="/res_jsp/guest_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		[编辑问题]
		<form:form action="guest/question/updateQuestion" method="post" modelAttribute="question">
			
			<form:hidden path="questionId"/>
			<input type="hidden" name="bag.bagId" value="${question.bag.bagId }"/>
			
			<%-- SpringMVC的form:hidden标签不允许设置模型对象中没有的属性名 --%>
			<%-- <form:hidden path="surveyId"/> --%>
			
			<input type="hidden" name="surveyId" value="${surveyId }"/>
			
			<table class="formTable">
			
				<tr>
					<td>问题名称</td>
					<td>
						<form:input path="questionName" cssClass="longInput"/>
					</td>
				</tr>
				
				<tr>
					<td>选择题型</td>
					<td>
						<!-- 使用SpringMVC的form:radiobuttons标签生成一组单选按钮，便于回显 -->
						<!-- items属性取值方式一：List<Question> -->
						<!-- itemValue设置方式：questionType -->
						<!-- itemLabel设置方式：Question类中表示“单选题、多选题、简答题”的属性名 -->
						
						<!-- items属性取值方式二：Map<Integer,String> -->
						<!-- 省略itemValue、itemLabel两个属性的设置 -->
						<!-- 使用Map的键作为单选按钮的value属性值 -->
						<!-- 使用Map的值作为单选按钮的label值 -->
						
						<form:radiobuttons path="questionType" items="${requestScope.radioMap }"/>
					</td>
				</tr>
				
				<tr id="optionTr">
					<td>选项</td>
					<td>
						<%-- SpringMVC的form:textarea标签不能另外设置value属性值 --%>
						<%-- <form:textarea path="options">${question.optionsForEdit }</form:textarea> --%>
						<textarea class="multiInput" name="options">${question.optionsForEdit }</textarea>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="submit" value="更新"/>
					</td>
				</tr>
			
			</table>
			
		</form:form>
		
	</div>
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>