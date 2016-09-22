package com.atguigu.survey.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.atguigu.survey.component.service.i.ResService;
import com.atguigu.survey.e.AdminOperationForbiddenException;
import com.atguigu.survey.e.HasNoRightException;
import com.atguigu.survey.e.UserOperationForbiddenException;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.manager.Admin;
import com.atguigu.survey.entities.manager.Res;
import com.atguigu.survey.log.router.KeyBinder;
import com.atguigu.survey.utils.DataprocessUtils;
import com.atguigu.survey.utils.GlobalNames;

public class AuthCheckInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	private ResService resService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
//		1.将静态资源放过
		if(handler instanceof DefaultServletHttpRequestHandler) {
			//如果检测到当前请求是静态资源，则直接放行
			return true;
		}
		
//		2.获取Session对象
		HttpSession session = request.getSession();
		
//		3.获取ServletPath
		String servletPath = request.getServletPath();
		
//		4.将ServletPath中的多余部分剪掉
		servletPath = DataprocessUtils.cutUrl(servletPath);
		
//		5.根据ServletPath获取对应的Res对象
		Res res = resService.getResByServletPath(servletPath);
		
//		6.检查是否是公共资源
		if(res.isPublicRes()) {
	//		7.如果是公共资源则放行
			return true;
		}
		
//		8.如果当前请求的目标地址是User部分的
		if(servletPath.startsWith("/guest")) {
	//		9.检查User是否登录
			User user = (User) session.getAttribute(GlobalNames.LOGIN_USRE);
			if(user == null) {
		//		10.如果没有登录则抛出自定义异常：UserLoginNeededException
				throw new UserOperationForbiddenException("请登录后再操作！");
			}
			
	//		11.已登录则检查权限
			String codeArrStr = user.getCodeArrStr();
			
			boolean hasRight = DataprocessUtils.checkAuthority(codeArrStr, res);
			
	//		12.有权限则放行
			if(hasRight) {
				return true;
			}else{
		//		13.没有权限则抛出自定义异常：HasNoRightException
				throw new HasNoRightException("抱歉，您没有权限执行这个操作！");
			}
			
		}
		
//		14.如果当前请求的目标地址是Admin部分的
		if(servletPath.startsWith("/manager")) {
	//		15.检查Admin是否登录
			Admin admin = (Admin) session.getAttribute(GlobalNames.LOGIN_ADMIN);
			
			if(admin == null) {
		//		16.如果没有登录则抛出自定义异常：AdminLoginNeededException
				throw new AdminOperationForbiddenException("请登录后再操作！");
			}
			
	//		17.如果已登录则检查是否是超级管理员
			String adminName = admin.getAdminName();
			if("SuperAdmin".equals(adminName)) {
				return true;
			}
			
	//		18.如果不是超级管理员，则检查是否具备访问目标资源的权限
			String codeArrStr = admin.getCodeArrStr();
			boolean hasRight = DataprocessUtils.checkAuthority(codeArrStr, res);
			
			if(hasRight) {
		//		19.有权限则放行
				return true;
				
			}else{
		//		20.没有权限则抛出自定义异常：HasNoRightException
				throw new HasNoRightException("抱歉，您没有权限执行这个操作！");
				
			}
			
			
		}
		
		return true;
	}

}
