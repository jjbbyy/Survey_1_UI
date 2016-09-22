package com.atguigu.survey.base.m;

import org.springframework.beans.factory.annotation.Autowired;

import com.atguigu.survey.base.i.BaseDao;
import com.atguigu.survey.base.i.BaseService;

public class BaseServiceImpl<T> implements BaseService<T>{

	@Autowired
	private BaseDao<T> baseDao;
	
	@Override
	public T getEntityById(Integer entityId) {
		return baseDao.getEntityById(entityId);
	}

	@Override
	public void updateEntity(T t) {
		baseDao.updateEntity(t);
	}

	@Override
	public void removeEntity(Integer entityId) {
		baseDao.removeEntity(entityId);
	}

	@Override
	public Integer saveEntity(T t) {
		return baseDao.saveEntity(t);
	}

}
