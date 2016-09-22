package com.atguigu.survey.component.dao.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseDao;
import com.atguigu.survey.entities.Employee;

public interface EmployeeDao extends BaseDao<Employee>{

	List<Employee> getEmpList();

}
