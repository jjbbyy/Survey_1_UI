package com.atguigu.survey.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.atguigu.survey.component.service.i.ResService;
import com.atguigu.survey.entities.manager.Res;
import com.atguigu.survey.utils.DataprocessUtils;

/**
 * 作用：拦截每一个handler请求，检查是否已经作为资源保存到了数据库中，如果没有保存，则执行保存操作
 * ※这是一个临时使用的拦截器，当项目中的所有资源都已经保存到了数据库中，就可以不使用这个拦截器了
 * @author Creathin
 *
 */
public class ResGenerateInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private ResService resService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		if(handler instanceof DefaultServletHttpRequestHandler) {
			return true;
		}
		
		//获取当前请求的servletPath
		String servletPath = request.getServletPath();
		
		//将servletPath中附加值的部分去掉
		servletPath = DataprocessUtils.cutUrl(servletPath);
		
		//检查当前servletPath是否已经保存到数据库中
		boolean exists = resService.checkServletPathExists(servletPath);
		
		if(exists) {
			//如果已经保存了，则直接放行
			return true;
		}
		
		//计算权限码、权限位的值保存
		//1.声明两个变量保存最终的权限码、权限位
		Integer finalCode = null;
		Integer finalPos = null;
		
		//2.声明一个变量保存系统允许的最大值
		int systemCode = 1 << 30;
		
		//3.查询系统中最大的权限位
		Integer maxPos = resService.getMaxPos();
		
		//4.查询最大权限位范围内的最大权限码
		Integer maxCode = (maxPos == null)?null:resService.getMaxCode(maxPos);
		
		//5.根据第一次保存的情况设置最终权限码、权限位
		if(maxPos == null) {
			finalCode = 1;
			finalPos = 0;
		}else if(maxCode >= systemCode) {
			//6.如果不是第一次保存，检查权限码有没有达到最大值
			finalCode = 1;
			finalPos = maxPos + 1;
		}else{
			//7.如果没有达到最大值，则根据这个情况设置最终权限码、权限位
			finalCode = maxCode << 1;
			finalPos = maxPos;
		}
		
		//8.创建Res对象
		Res res = new Res(null, servletPath, finalCode, finalPos, false);
		
		//9.保存Res对象
		resService.saveEntity(res);
		
		return true;
	}

}
