package com.atguigu.survey.component.service.m;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.EmployeeDao;
import com.atguigu.survey.component.service.i.EmployeeService;
import com.atguigu.survey.entities.Employee;

@Service
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements EmployeeService{
	
	@Autowired
	private EmployeeDao employeeDao;
	
	public EmployeeServiceImpl() {
		System.out.println("EmployeeServiceImpl对象创建了！……");
	}

	@Override
	public List<Employee> getEmpList() {
		
		return employeeDao.getEmpList();
	}

}
