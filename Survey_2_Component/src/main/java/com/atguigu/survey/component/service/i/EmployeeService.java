package com.atguigu.survey.component.service.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseService;
import com.atguigu.survey.entities.Employee;

public interface EmployeeService extends BaseService<Employee> {

	List<Employee> getEmpList();

}
