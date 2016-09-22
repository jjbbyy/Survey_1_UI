package com.atguigu.survey.component.handler.guest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.survey.component.service.i.EmployeeService;
import com.atguigu.survey.entities.Employee;

@Controller
public class EmpHandler {
	
	@Autowired
	private EmployeeService employeeService;
	
	public EmpHandler() {
		System.out.println("EmpHandler对象创建了！……");
	}
	
	@RequestMapping("/emp/showList")
	public String showList(Map<String, Object> map) {
		
		List<Employee> empList = new ArrayList<>();
		
		empList.add(new Employee());
		
		map.put("empList", empList);
		
		return "guest/emp_list";
	}

}
