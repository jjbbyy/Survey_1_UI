package com.atguigu.survey.component.handler.guest;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.survey.component.service.i.UserService;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.utils.GlobalNames;

@Controller
public class UserHandler {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/guest/user/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		//session.removeAttribute(GlobalNames.LOGIN_USRE);
		
		return "redirect:/index.jsp";
	}
	
	@RequestMapping("/guest/user/login")
	public String login(User user, HttpSession session) {
		
		//返回值需要是一个从数据库中查询得到的User对象
		User loginUser = userService.login(user);
		
		//如果将表单提交的user对象放在Session域中，则缺失很多其他属性值
		//session.setAttribute(GlobalNames.LOGIN_USRE, user);
		
		session.setAttribute(GlobalNames.LOGIN_USRE, loginUser);
		
		return "redirect:/index.jsp";
	}
	
	@RequestMapping("/guest/user/regist")
	public String regist(User user) {
		
		userService.regist(user);
		
		return "guest/user_login";
	}

}
