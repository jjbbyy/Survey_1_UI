package com.atguigu.survey.component.dao.m;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.atguigu.survey.base.m.BaseDaoImpl;
import com.atguigu.survey.component.dao.i.BagDao;
import com.atguigu.survey.entities.guest.Bag;

@Repository
public class BagDaoImpl extends BaseDaoImpl<Bag> implements BagDao{

	@Override
	public void updateBag(Bag bag) {
		String hql = "update Bag b set b.bagName=? where b.bagId=?";
		updateEntityByHql(hql, bag.getBagName(), bag.getBagId());
	}

	@Override
	public void removeBag(Bag bag) {
		getSession().delete(bag);
	}

	@Override
	public List<Bag> getBagListBySurveyId(Integer surveyId) {
		
		String hql = "From Bag b where b.survey.surveyId=?";
		
		return getListByHql(hql, surveyId);
	}

	@Override
	public void batchUpdateBagOrder(List<Integer> bagOrderList,
			List<Integer> bagIdList) {
		
		int size = bagOrderList.size();
		
		String sql = "update survey_bag set bag_order=? where bag_id=?";
		Object[][] params = new Object[size][2];
		
		for(int i = 0; i < bagOrderList.size(); i++) {
			
			//从集合中取出需要的数据
			Integer bagOrder = bagOrderList.get(i);
			Integer bagId = bagIdList.get(i);
			
			//创建一个一维数组，用来封装数据
			Object[] param = new Object[2];
			
			//给一维数组中的元素赋值
			param[0] = bagOrder;
			param[1] = bagId;
			
			//将一维数组赋值给二维数组中的特定位置
			params[i] = param;
			
			//params[i] = new Object[]{bagOrderList.get(i), bagIdList.get(i)};
			
		}
		
		batchUpdate(sql, params);
	}

	@Override
	public void updateRelationshipByMove(Integer bagId, Integer surveyId) {
		String hql = "update Bag b set b.survey.surveyId=? where b.bagId=?";
		updateEntityByHql(hql, surveyId, bagId);
	}

}
