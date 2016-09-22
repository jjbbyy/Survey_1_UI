package com.atguigu.survey.component.dao.m;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.atguigu.survey.base.m.BaseDaoImpl;
import com.atguigu.survey.component.dao.i.QuestionDao;
import com.atguigu.survey.entities.guest.Question;

@Repository
public class QuestionDaoImpl extends BaseDaoImpl<Question> implements QuestionDao{

	@Override
	public void batchSave(Set<Question> questionSet) {
		String sql = "INSERT INTO "
				+ "`survey_question`(`QUESTION_NAME`,`QUESTION_TYPE`,`OPTIONS`,`bag_id`) "
				+ "VALUES(?,?,?,?)";
		
		Object[][] params = new Object[questionSet.size()][4];
		
		List<Question> questionList = new ArrayList<>(questionSet);
		
		for(int i = 0; i < questionList.size(); i++) {
			
			Question question = questionList.get(i);
			
			params[i] = new Object[]{
					question.getQuestionName(),
					question.getQuestionType(),
					question.getOptions(),
					question.getBag().getBagId()
					};
		}
		
		batchUpdate(sql, params);
		
	}

	@Override
	public int getQuestionEngagedCount(Integer questionId) {
		
		String sql = "SELECT COUNT(question_id) FROM survey_answer WHERE question_id=?";
		
		return getTotalRecordNoBySql(sql, questionId);
	}

	@Override
	public int getOptionEngagedCount(Integer questionId, int index) {
		
		String likeValue = "%,"+index+",%";
		
		String sql = "SELECT COUNT(*) FROM survey_answer WHERE question_id=? AND CONCAT(',',content,',') LIKE ?";
		
		return getTotalRecordNoBySql(sql, questionId, likeValue);
	}

}
