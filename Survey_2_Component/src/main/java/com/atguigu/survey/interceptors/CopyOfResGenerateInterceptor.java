package com.atguigu.survey.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.atguigu.survey.component.service.i.ResService;
import com.atguigu.survey.entities.manager.Res;

/**
 * 作用：拦截每一个handler请求，检查是否已经作为资源保存到了数据库中，如果没有保存，则执行保存操作
 * ※这是一个临时使用的拦截器，当项目中的所有资源都已经保存到了数据库中，就可以不使用这个拦截器了
 * @author Creathin
 *
 */
public class CopyOfResGenerateInterceptor extends HandlerInterceptorAdapter {
	
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
		
		//检查当前servletPath是否已经保存到数据库中
		boolean exists = resService.checkServletPathExists(servletPath);
		
		if(exists) {
			//如果已经保存了，则直接放行
			return true;
		}
		
		//如果尚未保存则计算当前资源的权限码、权限位
		int systemCode = 1 << 30;
		
		//1.声明两个变量，用于保存最终的权限码、权限位
		Integer finalCode = null;
		Integer finalPos = null;
		
		//2.当前系统中最大权限位
		Integer maxPos = resService.getMaxPos();
		
		//3.当前系统中最大权限位范围内的最大权限码
		//位 码
		//0	-2147483648
		//0	1
		//0	2
		//0	4
		//0	8
		//……
		//0	1073741824
		//1	-2147483648
		//1	1
		//1	2
		//1	4
		//1	8
		Integer maxCode = (maxPos == null)?null:resService.getMaxCode(maxPos);
		
		//4.如果是第一次保存，那么就将最终权限码和权限位设置为初始值
		if(maxPos == null) {
			finalCode = -2147483648;
			finalPos = 0;
			
		}else if(maxCode == systemCode) {
			//5.检查maxCode中的1是否已经移动到了最左边
			finalPos = maxPos + 1;
		}else{
			finalPos = maxPos;
		}
		
		if(maxCode != null) {
			//6.让maxCode继续左移
			finalCode = maxCode << 1;
		}
		
		//创建资源对象
		Res res = new Res(null, servletPath, finalCode, finalPos, false);
		
		//保存资源对象
		resService.saveEntity(res);
		
		return true;
	}

}
