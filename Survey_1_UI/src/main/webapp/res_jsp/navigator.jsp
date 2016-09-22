<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	$(function(){
		$("#go").change(function(){
			
			//1.获取当前文本框的值
			var pageNoStr = $.trim(this.value);
			
			//2.检查pageNoStr是否为非数字
			//JavaScript中有一个常量：NaN(not a number)
			if(isNaN(pageNoStr) || pageNoStr == "") {
				
				//3.如果为非数字，则恢复为默认值
				this.value = this.defaultValue;
				
				return ;
			}
			
			//3.将去掉前后空格的值写回文本框
			this.value = pageNoStr;
			
			//4.执行页面跳转
			var url = "${pageContext.request.contextPath}/${pageScope.targetUrl}?pageNoStr="+pageNoStr;
			window.location.href = url;
			
		});
	});
	
</script>
<c:if test="${page.hasPrev }">
	<a href="${pageScope.targetUrl}?pageNoStr=1">首页</a>
	<a href="${pageScope.targetUrl}?pageNoStr=${page.prev }">上一页</a>
</c:if>

[当前是第${page.pageNo }页
共${page.totalPageNo }页
共${page.totalRecordNo }条记录]

<input type="text" id="go" value="${page.pageNo }" class="shortInput"/>

<c:if test="${page.hasNext }">
	<a href="${pageScope.targetUrl}?pageNoStr=${page.next }">下一页</a>
	<a href="${pageScope.targetUrl}?pageNoStr=${page.totalPageNo }">末页</a>
</c:if>