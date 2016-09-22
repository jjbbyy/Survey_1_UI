package com.atguigu.survey.component.service.m;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.ResDao;
import com.atguigu.survey.component.dao.i.RoleDao;
import com.atguigu.survey.component.dao.i.UserDao;
import com.atguigu.survey.component.service.i.UserService;
import com.atguigu.survey.e.UserLoginFailedException;
import com.atguigu.survey.e.UserNameAlreadyExistsException;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.manager.Role;
import com.atguigu.survey.utils.DataprocessUtils;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private ResDao resDao;

	@Override
	public void regist(User user) {
		
		boolean exists = userDao.checkUserNameExists(user.getUserName());
		
		if(exists) {
			//抛出自定义异常
			throw new UserNameAlreadyExistsException("用户名已存在，请重新注册！");
		}
		
		//将密码进行MD5加密
		String userPwd = user.getUserPwd();
		userPwd = DataprocessUtils.md5(userPwd);
		user.setUserPwd(userPwd);
		
		//※权限相关操作
		//1.给User分配相关Role
		//①创建Set集合，保存Role对象
		Set<Role> roleSet = new HashSet<>();
		
		//②检查当前用户的类别
		if(user.isCompany()) {
			
			//③如果是企业用户，就查询“企业用户”角色
			String roleName = "企业用户";
			
			Role role = roleDao.getRoleByName(roleName);
			
			//④将role对象放入roleSet
			roleSet.add(role);
			
		}else{
			
			//⑤如果是个人用户，就查询“个人用户”角色
			String roleName = "个人用户";
			
			Role role = roleDao.getRoleByName(roleName);
			
			//⑥将role对象放入roleSet
			roleSet.add(role);
			
		}
		
		//⑦将roleSet设置到User对象中
		//如果忘记设置，那么inner_user_role中间表中就没有相关数据了
		user.setRoleSet(roleSet);
		
		//2.根据Set<Role>计算权限码数组
		Integer maxPos = resDao.getMaxPos();
		
		String codeArrStr = DataprocessUtils.calculateCodeArr(roleSet, maxPos);
		
		user.setCodeArrStr(codeArrStr);
		
		//如果不抛出异常，则将User对象保存到数据库中
		userDao.saveEntity(user);
		
	}

	@Override
	public User login(User user) {
		
		//在验证用户名密码之前执行加密操作
		//加密之后按照密文来进行比较
		String userPwd = user.getUserPwd();
		userPwd = DataprocessUtils.md5(userPwd);
		user.setUserPwd(userPwd);
		
		User loginUser = userDao.getUserByLogin(user);
		
		if(loginUser == null) {
			//抛出自定义异常
			throw new UserLoginFailedException("用户名、密码错误！");
		}
		
		return loginUser;
	}

}
