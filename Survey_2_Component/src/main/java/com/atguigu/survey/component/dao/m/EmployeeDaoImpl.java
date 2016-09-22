package com.atguigu.survey.component.dao.m;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.atguigu.survey.base.m.BaseDaoImpl;
import com.atguigu.survey.component.dao.i.EmployeeDao;
import com.atguigu.survey.entities.Employee;

@Repository
public class EmployeeDaoImpl extends BaseDaoImpl<Employee> implements EmployeeDao{
	
	public EmployeeDaoImpl() {
		super();
	}

	@Override
	public List<Employee> getEmpList() {
		String hql = "From Employee";
		return getListByHql(hql);
	}

}
