<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="logoDiv" class="borderDiv">尚硅谷在线调查系统</div>

<div id="topDiv" class="borderDiv">
	
	<!-- 1.检查用户是否已经登录 -->
	<c:if test="${sessionScope.loginUser == null }">
		<!-- 如果没有登录，则显示“登录”、“注册”超链接 -->
		[<a href="guest/user/toLoginUI">登录</a>]
		[<a href="guest/user/toRegistUI">注册</a>]
	</c:if>
	<c:if test="${sessionScope.loginUser != null }">
		<!-- 如果已经登录则显示欢迎信息，和可以访问的超链接 -->
		[欢迎您：${sessionScope.loginUser.userName }]
		[<a href="guest/user/logout">退出登录</a>]
		
		<!-- 根据用户类型显示可以访问的超链接 -->
		<c:if test="${sessionScope.loginUser.company }">
			<!-- 如果是企业用户，则显示创建调查和我的未完成调查 -->
			[<a href="guest/survey/toAddUI">创建调查</a>]
			[<a href="guest/survey/showMyUncompleted">我的未完成调查</a>]
		</c:if>
		
		<!-- 不论个人用户还是企业用户都可以参与调查 -->
		[<a href="guest/engage/showAllAvailableSurvey">参与调查</a>]
		
	</c:if>
	
	<!-- 不论是否登录，都可以回到首页 -->
	[<a href="index.jsp">首页</a>]
</div>