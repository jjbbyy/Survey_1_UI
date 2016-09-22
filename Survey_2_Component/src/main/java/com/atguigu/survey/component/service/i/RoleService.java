package com.atguigu.survey.component.service.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseService;
import com.atguigu.survey.entities.manager.Role;

public interface RoleService extends BaseService<Role>{

	void batchDelete(List<Integer> roleIdList);

	void updateRoleName(String roleName, Integer roleId);

	List<Role> getAllRoleList();

	void updateRelationship(Integer roleId, List<Integer> authIdList);

	List<Integer> getCurrentAuthIdList(Integer roleId);

}
