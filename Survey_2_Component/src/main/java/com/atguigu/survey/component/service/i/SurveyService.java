package com.atguigu.survey.component.service.i;

import com.atguigu.survey.base.i.BaseService;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.model.Page;

public interface SurveyService extends BaseService<Survey>{

	Page<Survey> getMyUncompleted(String pageNoStr, User user);

	void updateSurvey(Survey survey);

	void removeDeeply(Integer surveyId);

	void updateSurveyStatus(Integer surveyId);

}
