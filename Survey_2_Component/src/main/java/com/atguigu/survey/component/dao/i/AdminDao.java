package com.atguigu.survey.component.dao.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseDao;
import com.atguigu.survey.entities.manager.Admin;

public interface AdminDao extends BaseDao<Admin>{

	Admin getAdminForLogin(Admin admin);

	List<Admin> getAllAdminList();

	boolean checkAdminName(String adminName);

	List<Integer> getCurrentIdList(Integer adminId);

	void removeOldRelationship(Integer adminId);

	void saveNewRelationship(Integer adminId, List<Integer> roleIdList);

}
