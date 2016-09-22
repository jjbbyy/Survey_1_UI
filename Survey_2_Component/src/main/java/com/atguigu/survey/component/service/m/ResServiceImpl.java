package com.atguigu.survey.component.service.m;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.ResDao;
import com.atguigu.survey.component.service.i.ResService;
import com.atguigu.survey.entities.manager.Res;

@Service
public class ResServiceImpl extends BaseServiceImpl<Res> implements ResService{
	
	@Autowired
	private ResDao resDao;

	@Override
	public boolean checkServletPathExists(String servletPath) {
		return resDao.checkServletPathExists(servletPath);
	}

	@Override
	public Integer getMaxPos() {
		return resDao.getMaxPos();
	}

	@Override
	public Integer getMaxCode(Integer maxPos) {
		return resDao.getMaxCode(maxPos);
	}

	@Override
	public List<Res> getResList() {
		return resDao.getResList();
	}

	@Override
	public Res updateResStatus(Integer resId) {
		return resDao.updateResStatus(resId);
	}

	@Override
	public void batchDelete(List<Integer> resIdList) {
		resDao.batchDelete(resIdList);
	}

	@Override
	public Res getResByServletPath(String servletPath) {
		return resDao.getResByServletPath(servletPath);
	}

}
