package com.atguigu.survey.log.router;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class SurveyLogRouter extends AbstractRoutingDataSource{

	@Override
	protected Object determineCurrentLookupKey() {
		
		//1.尝试从当前线程上获取数据源的键
		String key = KeyBinder.getKey();
		
		//2.检查key是否为null
		if(key != null) {
			
			//3.将用完的key从当前线程上移除
			KeyBinder.removeKey();
			
			//4.将key值返回，用于决定现在要使用的数据源
			return key;
		}
		
		//5.如果没有获取到key，那么就返回null，使用主数据源
		return null;
	}

}
