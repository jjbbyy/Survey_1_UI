package com.atguigu.survey.component.service.m;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.AuthDao;
import com.atguigu.survey.component.service.i.AuthService;
import com.atguigu.survey.entities.manager.Auth;

@Service
public class AuthServiceImpl extends BaseServiceImpl<Auth> implements AuthService{
	
	@Autowired
	private AuthDao authDao;
	
	public List<Auth> getAllAuthList() {
		
		return authDao.getAllAuthList();
	}

	public void updateAuthName(String authName, Integer authId) {
		authDao.updateAuthName(authName, authId);
	}

	public void batchDelete(List<Integer> authIdList) {
		authDao.batchDelete(authIdList);
	}

	@Override
	public List<Integer> getCurrentResIdList(Integer authId) {
		return authDao.getCurrentResIdList(authId);
	}

	@Override
	public void updateRelationship(Integer authId, List<Integer> resIdList) {
		
		//1.删除旧的数据
		authDao.removeOldRelationship(authId);
		
		//2.保存新的数据
		if(resIdList != null) {
			authDao.saveNewRelationship(authId, resIdList);
		}
		
	}

}
