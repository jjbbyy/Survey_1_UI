<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/res_jsp/base.jsp" %>
<script type="text/javascript">
	$(function(){
		//页面初始化时将选项所在的tr隐藏
		$("#optionTr").hide();
		
		//在点击单选按钮后，切换选项所在tr的显示状态
		//:radio选择器选择页面上所有单选按钮
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
		
		[创建问题]
		<form action="guest/question/saveQuestion" method="post">
		
			<input type="hidden" name="surveyId" value="${surveyId }"/>
			<input type="hidden" name="bag.bagId" value="${bagId }"/>
			
			<table class="formTable">
				
				<tr>
					<td>问题名称</td>
					<td>
						<input type="text" name="questionName" class="longInput"/>
					</td>
				</tr>
				
				<tr>
					<td>选择题型</td>
					<td>
						<input id="radio01" type="radio" name="questionType" value="0"/>
						<label for="radio01">单选题</label>
						
						<input id="radio02" type="radio" name="questionType" value="1"/>
						<label for="radio02">多选题</label>
						
						<input id="radio03" type="radio" name="questionType" value="2" checked="checked"/>
						<label for="radio03">简答题</label>
					</td>
				</tr>
				
				<tr id="optionTr">
					<td>选项</td>
					<td>
						<textarea class="multiInput" name="options"></textarea>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="submit" value="保存"/>
					</td>
				</tr>
				
			</table>
			
		</form>
		
	</div>
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>