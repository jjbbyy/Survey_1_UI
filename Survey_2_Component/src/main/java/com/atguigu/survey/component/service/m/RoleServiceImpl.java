package com.atguigu.survey.component.service.m;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.RoleDao;
import com.atguigu.survey.component.service.i.RoleService;
import com.atguigu.survey.entities.manager.Role;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{
	
	@Autowired
	private RoleDao roleDao;

	public List<Role> getAllRoleList() {
		return roleDao.getAllRoleList();
	}

	public void updateRoleName(String roleName, Integer roleId) {
		roleDao.updateRoleName(roleName, roleId);
	}

	public void batchDelete(List<Integer> roleIdList) {
		roleDao.batchDelete(roleIdList);
	}

	public List<Integer> getCurrentAuthIdList(Integer roleId) {
		return roleDao.getCurrentAuthIdList(roleId);
	}

	public void updateRelationship(Integer roleId, List<Integer> authIdList) {
		
		//1.删除旧的关联关系
		roleDao.removeOldRelationship(roleId);
		
		//2.保存新的关联关系
		if(authIdList != null && authIdList.size() != 0) {
			roleDao.saveNewRelationship(roleId, authIdList);
		}
		
	}
}
