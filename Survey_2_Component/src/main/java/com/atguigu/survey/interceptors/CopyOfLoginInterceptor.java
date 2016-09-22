package com.atguigu.survey.interceptors;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.atguigu.survey.e.UserOperationForbiddenException;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.utils.GlobalNames;

public class CopyOfLoginInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//参数中的handler其实就是当前请求对应的handler对象
		//静态资源对应的handler对象是DefaultServletHttpRequestHandler
		if(handler instanceof DefaultServletHttpRequestHandler) {
			//如果检测到当前请求是静态资源，则直接放行
			return true;
		}
		
		//公共资源：不需要登录就可以访问的资源
		Set<String> publicResSet = new HashSet<>();
		publicResSet.add("/guest/user/toLoginUI");
		publicResSet.add("/guest/user/toRegistUI");
		publicResSet.add("/guest/user/login");
		publicResSet.add("/guest/user/regist");
		publicResSet.add("/guest/user/logout");
		
		publicResSet.add("/manager/admin/toMainUI");
		publicResSet.add("/manager/admin/toLoginUI");
		publicResSet.add("/manager/admin/login");
		publicResSet.add("/manager/admin/logout");
		
		//获取当前请求的servletPath
		//servletPath=/guest/user/toLoginUI
		String servletPath = request.getServletPath();
		System.out.println("servletPath="+servletPath);
		
		//检查当前请求是否是公共资源，如果是，则直接放行
		if(publicResSet.contains(servletPath)) {
			return true;
		}
		
		//如果不是，则检查登录状态
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(GlobalNames.LOGIN_USRE);
		if(user == null) {
			//如果没有登录，则抛出自定义异常
			throw new UserOperationForbiddenException("当前操作需要登录后再执行！");
		}
		
		//如果已经登录，则可以放行
		return true;
	}

}
