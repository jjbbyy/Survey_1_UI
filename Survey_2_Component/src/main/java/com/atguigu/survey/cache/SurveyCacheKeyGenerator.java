package com.atguigu.survey.cache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

public class SurveyCacheKeyGenerator implements KeyGenerator{

	@Override
	public Object generate(Object target, Method method, Object... params) {
		
		StringBuilder builder = new StringBuilder();
		
		String className = target.getClass().getName();
		
		String methodName = method.getName();
		
		builder.append(className).append(".").append(methodName).append(".");
		
		for (Object param : params) {
			builder.append(param).append(".");
		}
		
		String key = builder.substring(0, builder.lastIndexOf("."));
		
		System.out.println(key);
		
		return key;
	}

}
