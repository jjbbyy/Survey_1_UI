package com.atguigu.survey.component.service.m;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.SurveyDao;
import com.atguigu.survey.component.service.i.SurveyService;
import com.atguigu.survey.e.BagEmptyException;
import com.atguigu.survey.e.SurveyEmptyException;
import com.atguigu.survey.entities.guest.Bag;
import com.atguigu.survey.entities.guest.Question;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.model.Page;

@Service
public class SurveyServiceImpl extends BaseServiceImpl<Survey> implements SurveyService{
	
	@Autowired
	private SurveyDao surveyDao;

	@Override
	public Page<Survey> getMyUncompleted(String pageNoStr, User user) {
		
		//1.查询数据库得到总记录数
		int totalRecordNo = surveyDao.getMyUncompletedCount(user);
		
		//2.创建Page对象
		Page<Survey> page = new Page<>(pageNoStr, totalRecordNo);
		
		//3.获取经过修正的pageNo
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		//4.使用经过修正的pageNo和pageSize查询list集合
		List<Survey> list = surveyDao.getMyUncompletedList(pageNo, pageSize, user);
		
		//5.将list集合设置到Page对象中
		page.setList(list);
		
		//6.返回Page对象
		return page;
	}

	@Override
	public void updateSurvey(Survey survey) {
		surveyDao.updateSurvey(survey);
	}

	@Override
	public void removeDeeply(Integer surveyId) {
		//surveyDao.removeEntity(surveyId);
		Survey survey = surveyDao.getEntityById(surveyId);
		surveyDao.removeSurvey(survey);
	}

	@Override
	public void updateSurveyStatus(Integer surveyId) {
		
		//[1]根据surveyId查询Survey对象
		Survey survey = surveyDao.getEntityById(surveyId);
		
		//[2]检查Survey对象的bagSet是否为空
		Set<Bag> bagSet = survey.getBagSet();
		if(bagSet == null || bagSet.size() == 0) {
			//如果为空，则抛出异常：SurveyEmptyException
			throw new SurveyEmptyException("调查是空的，不能完成，请继续完善！");
		}
		
		//[3]遍历bagSet，获取每一个bag的questionSet
		for (Bag bag : bagSet) {
			Set<Question> questionSet = bag.getQuestionSet();
			//[4]检查questionSet是否为空
			if(questionSet == null || questionSet.size() == 0) {
				//如果为空，则抛出异常：BagEmptyException
				throw new BagEmptyException("当前调查中有空的包裹，不能完成，请继续完善！");
			}
		}
		
		//[5]将Survey对象的完成状态设置为true
		survey.setCompletedStatus(true);
		
	}

}
