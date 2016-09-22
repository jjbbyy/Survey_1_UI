package com.atguigu.survey.component.service.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseService;
import com.atguigu.survey.entities.manager.Res;

public interface ResService extends BaseService<Res>{

	boolean checkServletPathExists(String servletPath);

	Integer getMaxPos();

	Integer getMaxCode(Integer maxPos);

	List<Res> getResList();

	Res updateResStatus(Integer resId);

	void batchDelete(List<Integer> resIdList);

	Res getResByServletPath(String servletPath);

}
