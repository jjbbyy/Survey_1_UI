package com.atguigu.survey.component.dao.m;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.atguigu.survey.base.m.BaseDaoImpl;
import com.atguigu.survey.component.dao.i.AnswerDao;
import com.atguigu.survey.entities.guest.Answer;

@Repository
public class AnswerDaoImpl extends BaseDaoImpl<Answer> implements AnswerDao{

	@Override
	public void batchSave(List<Answer> answerList) {
		
		String uuid = UUID.randomUUID().toString();
		
		String sql = "INSERT INTO `survey_answer`(`CONTENT`,`UUID`,`QUESTION_ID`,`SURVEY_ID`) VALUES(?,?,?,?)";
		
		Object[][] params = new Object[answerList.size()][4];
		
		for (int i = 0; i < answerList.size(); i++) {
			Answer answer = answerList.get(i);
			params[i] = new Object[]{answer.getContent(), uuid, answer.getQuestionId(), answer.getSurveyId()};
		}
		
		batchUpdate(sql, params);
		
	}

	@Override
	public List<String> getTextAnswerList(Integer questionId) {
		
		String hql = "select a.content from Answer a where a.questionId=?";
		
		return getQuery(hql, questionId).list();
	}

	@Override
	public List<Answer> getAnswerListBySurveyId(Integer surveyId) {
		
		String hql = "From Answer a where a.surveyId=?";
		
		return getListByHql(hql, surveyId);
	}

}
