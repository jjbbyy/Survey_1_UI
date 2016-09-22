package com.atguigu.survey.component.dao.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseDao;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.entities.guest.User;

public interface SurveyDao extends BaseDao<Survey>{

	int getMyUncompletedCount(User user);

	List<Survey> getMyUncompletedList(int pageNo, int pageSize, User user);

	void updateSurvey(Survey survey);

	void removeSurvey(Survey survey);

	int getAllAvailableSurveyCount();

	List<Survey> getAllAvailableSurveyList(int pageNo, int pageSize);

	int getSurveyEngagedCount(Integer surveyId);

}
