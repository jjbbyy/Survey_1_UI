package com.atguigu.survey.component.dao.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseDao;
import com.atguigu.survey.entities.manager.Res;

public interface ResDao extends BaseDao<Res>{

	boolean checkServletPathExists(String servletPath);

	Integer getMaxPos();

	Integer getMaxCode(Integer maxPos);

	List<Res> getResList();

	Res updateResStatus(Integer resId);

	void batchDelete(List<Integer> resIdList);

	Res getResByServletPath(String servletPath);

}
