package com.atguigu.survey.component.service.m;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.BagDao;
import com.atguigu.survey.component.dao.i.QuestionDao;
import com.atguigu.survey.component.service.i.BagService;
import com.atguigu.survey.entities.guest.Bag;
import com.atguigu.survey.entities.guest.Question;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.utils.DataprocessUtils;

@Service
public class BagServiceImpl extends BaseServiceImpl<Bag> implements BagService{
	
	@Autowired
	private BagDao bagDao;
	
	@Autowired
	private QuestionDao questionDao;

	@Override
	public void saveBag(Bag bag) {
		Integer bagId = bagDao.saveEntity(bag);
		bag.setBagOrder(bagId);
	}

	@Override
	public void updateBag(Bag bag) {
		bagDao.updateBag(bag);
	}

	@Override
	public void removeDeeply(Integer bagId) {
		Bag bag = bagDao.getEntityById(bagId);
		bagDao.removeBag(bag);
	}

	@Override
	public List<Bag> getBagListBySurveyId(Integer surveyId) {
		
		return bagDao.getBagListBySurveyId(surveyId);
	}

	@Override
	public void batchUpdateBagOrder(List<Integer> bagOrderList,
			List<Integer> bagIdList) {
		bagDao.batchUpdateBagOrder(bagOrderList,bagIdList);
	}

	@Override
	public void updateRelationshipByMove(Integer bagId, Integer surveyId) {
		bagDao.updateRelationshipByMove(bagId, surveyId);
	}

	@Override
	public void updateRelationshipByCopy(Integer bagId, Integer surveyId) {
		
		//1.根据bagId查询Bag对象，作为源对象
		Bag source = bagDao.getEntityById(bagId);
		
		//2.复制源对象
		Bag target = (Bag) DataprocessUtils.deeplyCopy(source);
		
		//3.给Bag对象设置新的目标调查的关联关系
		target.setSurvey(new Survey(surveyId));
		
		//target.getSurvey().setSurveyId(surveyId);
		
		//4.保存Bag对象
		bagDao.saveEntity(target);
		
		//5.保存Bag对象中关联的Question集合
		Set<Question> questionSet = target.getQuestionSet();
		questionDao.batchSave(questionSet);
		
	}

}
