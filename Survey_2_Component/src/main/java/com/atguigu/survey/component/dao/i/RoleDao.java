package com.atguigu.survey.component.dao.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseDao;
import com.atguigu.survey.entities.manager.Role;

public interface RoleDao extends BaseDao<Role>{

	List<Role> getAllRoleList();

	void updateRoleName(String roleName, Integer roleId);

	void batchDelete(List<Integer> roleIdList);

	List<Integer> getCurrentAuthIdList(Integer roleId);

	void removeOldRelationship(Integer roleId);

	void saveNewRelationship(Integer roleId, List<Integer> authIdList);

	Role getRoleByName(String roleName);

}
