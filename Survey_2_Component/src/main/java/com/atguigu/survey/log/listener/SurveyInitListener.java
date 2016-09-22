package com.atguigu.survey.log.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.atguigu.survey.component.service.i.SurveyLogService;
import com.atguigu.survey.log.router.KeyBinder;
import com.atguigu.survey.utils.DataprocessUtils;
import com.atguigu.survey.utils.GlobalNames;

public class SurveyInitListener implements ApplicationListener<ContextRefreshedEvent>{
	
	@Autowired
	private SurveyLogService surveyLogService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		//ApplicationContext applicationContext = event.getApplicationContext();
		
		//System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆IOC容器启动了！☆☆☆☆☆☆☆☆☆☆☆☆"+applicationContext);
		
		//1.通过事件对象获取触发事件的IOC容器对象
		ApplicationContext applicationContext = event.getApplicationContext();
		
		//2.获取当前容器的父容器
		ApplicationContext parent = applicationContext.getParent();
		
		//System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆IOC容器启动了！☆☆☆☆☆☆☆☆☆☆☆☆"+parent);
		
		//3.如果父容器为null，则表示当前容器是Spring的IOC容器
		if(parent == null) {
			
			//当月
			String tableName = DataprocessUtils.generateTableName(0);
			
			KeyBinder.bindKey(GlobalNames.DATASOURCE_LOG);
			surveyLogService.createTable(tableName);
			
			//后三个月
			tableName = DataprocessUtils.generateTableName(1);
			
			KeyBinder.bindKey(GlobalNames.DATASOURCE_LOG);
			surveyLogService.createTable(tableName);
			
			tableName = DataprocessUtils.generateTableName(2);
			
			KeyBinder.bindKey(GlobalNames.DATASOURCE_LOG);
			surveyLogService.createTable(tableName);
			
			tableName = DataprocessUtils.generateTableName(3);
			
			KeyBinder.bindKey(GlobalNames.DATASOURCE_LOG);
			surveyLogService.createTable(tableName);
		}
		
	}

}
