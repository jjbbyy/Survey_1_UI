package com.atguigu.survey.component.dao.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseDao;
import com.atguigu.survey.entities.guest.Bag;

public interface BagDao extends BaseDao<Bag>{

	void updateBag(Bag bag);

	void removeBag(Bag bag);

	List<Bag> getBagListBySurveyId(Integer surveyId);

	void batchUpdateBagOrder(List<Integer> bagOrderList, List<Integer> bagIdList);

	void updateRelationshipByMove(Integer bagId, Integer surveyId);

}
