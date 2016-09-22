package com.atguigu.survey.component.dao.m;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.atguigu.survey.base.m.BaseDaoImpl;
import com.atguigu.survey.component.dao.i.SurveyDao;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.entities.guest.User;

@Repository
public class SurveyDaoImpl extends BaseDaoImpl<Survey> implements SurveyDao{

	@Override
	public int getMyUncompletedCount(User user) {
		
		String hql = "select count(*) from Survey s where s.completedStatus=false and s.user=?";
		
		return getTotalRecordNoByHql(hql, user);
	}

	@Override
	public List<Survey> getMyUncompletedList(int pageNo, int pageSize, User user) {
		
		String hql = "From Survey s where s.completedStatus=false and s.user=? order by s.surveyId desc";
		
		return getLimitedListByHql(hql, pageNo, pageSize, user);
	}

	@Override
	public void updateSurvey(Survey survey) {
		
		String logoPath = survey.getLogoPath();
		
		if("res_static/logo.gif".equals(logoPath)) {
			
			String hql = "update Survey s set s.surveyName=? where s.surveyId=?";
			updateEntityByHql(hql, survey.getSurveyName(), survey.getSurveyId());
			
		}else{
			
			String hql = "update Survey s set s.surveyName=?,s.logoPath=? where s.surveyId=?";
			updateEntityByHql(hql, survey.getSurveyName(), survey.getLogoPath(), survey.getSurveyId());
			
		}
		
	}

	@Override
	public void removeSurvey(Survey survey) {
		getSession().delete(survey);
	}

	@Override
	public int getAllAvailableSurveyCount() {
		String hql = "select count(*) from Survey s where s.completedStatus=true";
		
		return getTotalRecordNoByHql(hql);
	}

	@Override
	public List<Survey> getAllAvailableSurveyList(int pageNo, int pageSize) {
		String hql = "From Survey s where s.completedStatus=true order by s.surveyId desc";
		
		return getLimitedListByHql(hql, pageNo, pageSize);
	}

	@Override
	public int getSurveyEngagedCount(Integer surveyId) {
		
		String sql = "SELECT COUNT(DISTINCT `uuid`) FROM survey_answer WHERE survey_id=?";
		
		return getTotalRecordNoBySql(sql, surveyId);
	}

}
