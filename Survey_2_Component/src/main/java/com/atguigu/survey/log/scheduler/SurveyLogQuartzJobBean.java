package com.atguigu.survey.log.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.atguigu.survey.component.service.i.SurveyLogService;
import com.atguigu.survey.log.router.KeyBinder;
import com.atguigu.survey.utils.DataprocessUtils;
import com.atguigu.survey.utils.GlobalNames;

public class SurveyLogQuartzJobBean extends QuartzJobBean{
	
	private SurveyLogService surveyLogService;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		String tableName = DataprocessUtils.generateTableName(1);
		
		KeyBinder.bindKey(GlobalNames.DATASOURCE_LOG);
		surveyLogService.createTable(tableName);
		
		tableName = DataprocessUtils.generateTableName(2);
		
		KeyBinder.bindKey(GlobalNames.DATASOURCE_LOG);
		surveyLogService.createTable(tableName);
		
		tableName = DataprocessUtils.generateTableName(3);
		
		KeyBinder.bindKey(GlobalNames.DATASOURCE_LOG);
		surveyLogService.createTable(tableName);
		
		tableName = DataprocessUtils.generateTableName(4);
		
		KeyBinder.bindKey(GlobalNames.DATASOURCE_LOG);
		surveyLogService.createTable(tableName);
		
		tableName = DataprocessUtils.generateTableName(5);
		
		KeyBinder.bindKey(GlobalNames.DATASOURCE_LOG);
		surveyLogService.createTable(tableName);
		
	}

	public void setSurveyLogService(SurveyLogService surveyLogService) {
		this.surveyLogService = surveyLogService;
	}
}
