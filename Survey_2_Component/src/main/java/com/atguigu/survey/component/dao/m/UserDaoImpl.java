package com.atguigu.survey.component.dao.m;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.atguigu.survey.base.m.BaseDaoImpl;
import com.atguigu.survey.component.dao.i.UserDao;
import com.atguigu.survey.entities.guest.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

	@Override
	public boolean checkUserNameExists(String userName) {
		
		String hql = "select count(*) from User u where u.userName=?";
		
		return getTotalRecordNoByHql(hql, userName) > 0;
	}

	@Override
	public User getUserByLogin(User user) {
		String hql = "FrOm User u where u.userName=? and u.userPwd=?";
		return getEntityByHql(hql, user.getUserName(), user.getUserPwd());
	}

	@Override
	public List<User> getAllUserList() {
		
		String hql = "From User";
		
		return getListByHql(hql);
	}

}
