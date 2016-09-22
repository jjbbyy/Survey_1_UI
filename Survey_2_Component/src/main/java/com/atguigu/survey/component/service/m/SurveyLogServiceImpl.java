package com.atguigu.survey.component.service.m;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.SurveyLogDao;
import com.atguigu.survey.component.service.i.SurveyLogService;
import com.atguigu.survey.entities.manager.SurveyLog;
import com.atguigu.survey.model.Page;

@Service
public class SurveyLogServiceImpl extends BaseServiceImpl<SurveyLog> implements SurveyLogService{
	
	@Autowired
	private SurveyLogDao surveyLogDao;

	@Override
	public void createTable(String tableName) {
		surveyLogDao.createTable(tableName);
	}

	@Override
	public void saveSurveyLog(SurveyLog surveyLog) {
		surveyLogDao.saveSurveyLog(surveyLog);
	}

	@Override
	public Page<SurveyLog> getPage(String pageNoStr) {
		
		int totalRecordNo = surveyLogDao.getLogCount();
		
		Page<SurveyLog> page = new Page<>(pageNoStr, totalRecordNo);
		
		int pageNo = page.getPageNo();
		
		int pageSize = page.getPageSize();
		
		List<SurveyLog> list = surveyLogDao.getLimitedLogList(pageNo, pageSize);
		
		page.setList(list);
		
		return page;
	}

}
