package com.atguigu.survey.component.service.m;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.AdminDao;
import com.atguigu.survey.component.dao.i.ResDao;
import com.atguigu.survey.component.dao.i.UserDao;
import com.atguigu.survey.component.service.i.AdminService;
import com.atguigu.survey.e.AdminAlreadyExistsException;
import com.atguigu.survey.e.AdminLoginFailedException;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.manager.Admin;
import com.atguigu.survey.entities.manager.Role;
import com.atguigu.survey.utils.DataprocessUtils;

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService{
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private ResDao resDao;
	
	@Autowired
	private UserDao userDao;

	public List<Admin> getAllAdminList() {
		return adminDao.getAllAdminList();
	}

	public void saveAdmin(Admin admin) {
		
		String adminName = admin.getAdminName();
		
		boolean exists = adminDao.checkAdminName(adminName);
		
		if(exists) {
			throw new AdminAlreadyExistsException("此账号已经被使用！");
		}
		
		String adminPwd = admin.getAdminPwd();
		adminPwd = DataprocessUtils.md5(adminPwd);
		admin.setAdminPwd(adminPwd);
		
		adminDao.saveEntity(admin);
		
	}
	
	@Override
	public Admin login(Admin admin) {
		
		String adminPwd = admin.getAdminPwd();
		adminPwd = DataprocessUtils.md5(adminPwd);
		admin.setAdminPwd(adminPwd);
		
		Admin loginAdmin = adminDao.getAdminForLogin(admin);
		
		if(loginAdmin == null) {
			//抛异常
			throw new AdminLoginFailedException("账号密码错误！");
		}
		
		return loginAdmin;
	}

	public List<Integer> getCurrentIdList(Integer adminId) {
		return adminDao.getCurrentIdList(adminId);
	}

	public void updateRelationship(Integer adminId, List<Integer> roleIdList) {
		
		adminDao.removeOldRelationship(adminId);
		
		if(roleIdList != null && roleIdList.size() != 0) {
			adminDao.saveNewRelationship(adminId, roleIdList);
		}
		
		//※计算Admin的权限码数组
		//1.根据adminId查询Admin对象
		Admin admin = adminDao.getEntityById(adminId);
		
		//2.通过Admin对象获取Role集合
		Set<Role> roleSet = admin.getRoleSet();
		
		//3.查询系统中最大达权限位
		int maxPos = resDao.getMaxPos();
		
		//4.计算权限码数组
		String codeArrStr = DataprocessUtils.calculateCodeArr(roleSet, maxPos);
		
		//5.将权限码数组设置到Admin对象中
		admin.setCodeArrStr(codeArrStr);
	}

	@Override
	public void updateCalculateAllCode() {
		
		Integer maxPos = resDao.getMaxPos();
		
		//1.查询全部的Admin和User对象
		List<Admin> adminList = adminDao.getAllAdminList();
		List<User> userList = userDao.getAllUserList();
		
		//2.遍历adminList，逐个重新计算权限码数组值
		for (Admin admin : adminList) {
			String codeArrStr = DataprocessUtils.calculateCodeArr(admin.getRoleSet(), maxPos);
			admin.setCodeArrStr(codeArrStr);
		}
		
		//3.遍历userList，逐个重新计算权限码数组值
		for (User user : userList) {
			String codeArrStr = DataprocessUtils.calculateCodeArr(user.getRoleSet(), maxPos);
			user.setCodeArrStr(codeArrStr);
		}
		
	}
}
