package com.atguigu.survey.component.dao.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseDao;
import com.atguigu.survey.entities.manager.SurveyLog;

public interface SurveyLogDao extends BaseDao<SurveyLog>{

	void createTable(String tableName);

	void saveSurveyLog(SurveyLog surveyLog);
	
	List<String> getAllTableName();
	
	int getLogCount();
	
	List<SurveyLog> getLimitedLogList(int pageNo, int pageSize);

}
