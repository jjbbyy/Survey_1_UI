package com.atguigu.survey.component.service.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseService;
import com.atguigu.survey.entities.guest.Bag;

public interface BagService extends BaseService<Bag>{

	void saveBag(Bag bag);

	void updateBag(Bag bag);

	void removeDeeply(Integer bagId);

	List<Bag> getBagListBySurveyId(Integer surveyId);

	void batchUpdateBagOrder(List<Integer> bagOrderList, List<Integer> bagIdList);

	void updateRelationshipByMove(Integer bagId, Integer surveyId);

	void updateRelationshipByCopy(Integer bagId, Integer surveyId);

}
