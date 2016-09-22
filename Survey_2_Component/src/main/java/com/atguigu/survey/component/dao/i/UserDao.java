package com.atguigu.survey.component.dao.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseDao;
import com.atguigu.survey.entities.guest.User;

public interface UserDao extends BaseDao<User>{

	boolean checkUserNameExists(String userName);

	User getUserByLogin(User user);

	List<User> getAllUserList();

}
