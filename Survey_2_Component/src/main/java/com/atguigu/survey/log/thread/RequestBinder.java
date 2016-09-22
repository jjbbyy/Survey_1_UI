package com.atguigu.survey.log.thread;

import javax.servlet.http.HttpServletRequest;

public class RequestBinder {
	
	private static ThreadLocal<HttpServletRequest> local = new ThreadLocal<>();
	
	public static void bindRequest(HttpServletRequest request) {
		local.set(request);
	}
	
	public static void removeRequest() {
		local.remove();
	}
	
	public static HttpServletRequest getRequest() {
		return local.get();
	}
	
	/*
	 *推理这里为什么不使用非静态资源 
	public static void main(String[] args) {
		
		//如果这些方法都是非静态的，那么每次使用时都需要创建新的对象，导致无法获取到相同的值
		//1.绑定操作
		RequestBinder requestBinder01 = new RequestBinder();
		requestBinder01.bindRequest(null);
		
		//2.获取操作
		RequestBinder requestBinder02 = new RequestBinder();
		HttpServletRequest request = requestBinder02.getRequest();
		
		//3.移除操作
		RequestBinder requestBinder03 = new RequestBinder();
		requestBinder03.removeRequest();
		
	}*/

}
