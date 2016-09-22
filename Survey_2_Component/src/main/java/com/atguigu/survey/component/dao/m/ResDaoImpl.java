package com.atguigu.survey.component.dao.m;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.atguigu.survey.base.m.BaseDaoImpl;
import com.atguigu.survey.component.dao.i.ResDao;
import com.atguigu.survey.entities.manager.Res;

@Repository
public class ResDaoImpl extends BaseDaoImpl<Res> implements ResDao{

	@Override
	public boolean checkServletPathExists(String servletPath) {
		
		String sql = "SELECT COUNT(*) FROM survey_res WHERE servlet_path=?";
		
		return getTotalRecordNoBySql(sql, servletPath) > 0;
	}

	@Override
	public Integer getMaxPos() {
		
		String hql = "select max(r.resPos) from Res r";
		
		return (Integer) getQuery(hql).uniqueResult();
	}

	@Override
	public Integer getMaxCode(Integer maxPos) {
		
		String hql = "select max(r.resCode) from Res r where r.resPos=?";
		
		return (Integer) getQuery(hql, maxPos).uniqueResult();
	}

	@Override
	public List<Res> getResList() {
		
		String hql = "From Res r order by r.servletPath";
		
		return getListByHql(hql);
	}

	@Override
	public Res updateResStatus(Integer resId) {
		
		Res res = getEntityById(resId);
		
		res.setPublicRes(!res.isPublicRes());
		
		return res;
	}

	@Override
	public void batchDelete(List<Integer> resIdList) {
		String sql = "delete from survey_res where res_id=?";
		Object[][] params = new Object[resIdList.size()][1];
		
		for (int i = 0; i < resIdList.size(); i++) {
			Integer resId = resIdList.get(i);
			Object[] param = new Object[]{resId};
			params[i] = param;
		}
		
		batchUpdate(sql, params);
	}

	@Override
	public Res getResByServletPath(String servletPath) {
		String hql = "From Res s where s.servletPath=?";
		return getEntityByHql(hql, servletPath);
	}

}
