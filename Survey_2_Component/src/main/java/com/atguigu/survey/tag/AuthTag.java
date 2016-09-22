package com.atguigu.survey.tag;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.atguigu.survey.component.service.i.ResService;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.manager.Admin;
import com.atguigu.survey.entities.manager.Res;
import com.atguigu.survey.utils.DataprocessUtils;
import com.atguigu.survey.utils.GlobalNames;

public class AuthTag extends SimpleTagSupport{
	
	private String servletPath;
	
	/*
	 *
	使用@Autowired注解实现自动注入的前提，是所在的类由IOC容器创建对象，
	而现在自定义标签类的对象是由Servlet容器创建的
	@Autowired
	private ResService resService;
	也不能自己new，因为自己通过new创建的Service对象是不享受声明式事务
	*/
	
	@Override
	public void doTag() throws JspException, IOException {
		
		//1.准备工作
		PageContext pageContext = (PageContext) getJspContext();
		ServletContext servletContext = pageContext.getServletContext();
		HttpSession session = pageContext.getSession();
		
		//※从SpringIOC容器中获取ResService对象
		WebApplicationContext ioc = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		ResService resService = ioc.getBean(ResService.class);
		
		//2.根据servletPath查询Res对象
		Res res = resService.getResByServletPath(servletPath);
		
		//3.检查Res对象是否是公共资源
		if(res.isPublicRes()) {
			//4.如果是公共资源，执行标签体，并停止当前方法执行
			getJspBody().invoke(null);
			return ;
		}
		
		
		//5.如果不是公共资源，则检查名称空间：guest/manager
		if(servletPath.startsWith("/guest")) {
			//6.guest
			//①如果User已经登录
			User user = (User) session.getAttribute(GlobalNames.LOGIN_USRE);
			if(user != null) {
				//②检查User是否有权限访问当前资源
				String codeArrStr = user.getCodeArrStr();
				boolean hasRight = DataprocessUtils.checkAuthority(codeArrStr, res);
				
				//③如果有权限，则执行标签体，方法结束
				if(hasRight) {
					getJspBody().invoke(null);
					return ;
				}
				
			}
			
		}
		
		if(servletPath.startsWith("/manager")) {
			//7.manager
			//①如果Admin已经登录
			Admin admin = (Admin) session.getAttribute(GlobalNames.LOGIN_ADMIN);
			if(admin != null) {
				//②检查Admin是不是超级管理员
				String adminName = admin.getAdminName();
				if("SuperAdmin".equals(adminName)) {
					//③如果是超级管理员，则执行标签体，方法结束
					getJspBody().invoke(null);
					return ;
				}
				//④如果不是超级管理员，则检查是否具备权限
				String codeArrStr = admin.getCodeArrStr();
				boolean hasRight = DataprocessUtils.checkAuthority(codeArrStr, res);
				if(hasRight) {
					//⑤如果有权限，则执行标签体，方法结束
					getJspBody().invoke(null);
					return ;
				}
				
			}
			
		}
		
	}

	public void setServletPath(String servletPath) {
		this.servletPath = servletPath;
	}
}
