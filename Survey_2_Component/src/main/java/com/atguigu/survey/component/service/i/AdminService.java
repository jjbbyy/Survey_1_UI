package com.atguigu.survey.component.service.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseService;
import com.atguigu.survey.entities.manager.Admin;

public interface AdminService extends BaseService<Admin>{

	Admin login(Admin admin);

	void saveAdmin(Admin admin);

	List<Admin> getAllAdminList();

	void updateRelationship(Integer adminId, List<Integer> roleIdList);

	List<Integer> getCurrentIdList(Integer adminId);

	void updateCalculateAllCode();

}
