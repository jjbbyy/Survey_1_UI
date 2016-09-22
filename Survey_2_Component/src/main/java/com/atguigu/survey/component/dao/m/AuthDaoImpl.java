package com.atguigu.survey.component.dao.m;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.atguigu.survey.base.m.BaseDaoImpl;
import com.atguigu.survey.component.dao.i.AuthDao;
import com.atguigu.survey.entities.manager.Auth;

@Repository
public class AuthDaoImpl extends BaseDaoImpl<Auth> implements AuthDao{
	
	public List<Auth> getAllAuthList() {
		return getListByHql("From Auth");
	}

	public void updateAuthName(String authName, Integer authId) {
		String hql = "update Auth a set a.authName=? where a.authId=?";
		updateEntityByHql(hql, authName, authId);
	}

	public void batchDelete(List<Integer> authIdList) {
		String sql = "delete from survey_auth where auth_id=?";
		Object[][] params = new Object[authIdList.size()][1];
		for (int i = 0; i < authIdList.size(); i++) {
			params[i] = new Object[]{authIdList.get(i)};
		}
		batchUpdate(sql, params);
	}

	@Override
	public List<Integer> getCurrentResIdList(Integer authId) {
		
		String sql = "select res_id from inner_auth_res where auth_id=?";
		
		return getListBySql(sql, authId);
	}

	@Override
	public void removeOldRelationship(Integer authId) {
		String sql = "DELETE FROM `inner_auth_res` WHERE auth_id=?";
		updateEntityBySql(sql, authId);
	}

	@Override
	public void saveNewRelationship(Integer authId, List<Integer> resIdList) {
		String sql = "insert into `inner_auth_res`(auth_id,res_id) values(?,?)";
		Object[][] params = new Object[resIdList.size()][2];
		for (int i = 0; i < resIdList.size(); i++) {
			Integer resId = resIdList.get(i);
			params[i] = new Object[]{authId, resId};
		}
		batchUpdate(sql, params);
	}

}
