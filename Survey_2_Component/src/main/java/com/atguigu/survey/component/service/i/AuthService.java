package com.atguigu.survey.component.service.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseService;
import com.atguigu.survey.entities.manager.Auth;

public interface AuthService extends BaseService<Auth>{

	List<Auth> getAllAuthList();

	void updateAuthName(String authName, Integer authId);

	void batchDelete(List<Integer> authIdList);

	List<Integer> getCurrentResIdList(Integer authId);

	void updateRelationship(Integer authId, List<Integer> resIdList);

}
