package com.atguigu.survey.component.service.i;

import com.atguigu.survey.base.i.BaseService;
import com.atguigu.survey.entities.manager.SurveyLog;
import com.atguigu.survey.model.Page;

public interface SurveyLogService extends BaseService<SurveyLog>{

	void createTable(String tableName);

	void saveSurveyLog(SurveyLog surveyLog);
	
	Page<SurveyLog> getPage(String pageNoStr);

}
