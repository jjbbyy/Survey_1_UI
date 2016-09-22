package com.atguigu.survey.component.handler.manager;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.survey.component.service.i.AdminService;
import com.atguigu.survey.component.service.i.RoleService;
import com.atguigu.survey.entities.manager.Admin;
import com.atguigu.survey.entities.manager.Role;
import com.atguigu.survey.utils.GlobalNames;

@Controller
public class AdminHandler {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/manager/admin/dispatcher")
	public String dispatcher(@RequestParam("adminId") Integer adminId, @RequestParam(value="roleIdList", required=false) List<Integer> roleIdList) {
		
		adminService.updateRelationship(adminId, roleIdList);
		
		return "redirect:/manager/admin/showList";
	}
	
	@RequestMapping("/manager/admin/toDispatcherUI/{adminId}")
	public String toDispatcherUI(
			@PathVariable("adminId") Integer adminId, 
			Map<String, Object> map) {
		
		List<Role> allRoleList = roleService.getAllRoleList();
		List<Integer> currentIdList = adminService.getCurrentIdList(adminId);
		
		map.put("allRoleList", allRoleList);
		map.put("currentIdList", currentIdList);
		map.put("adminId", adminId);
		
		return "manager/distribute_admin_role";
	}
	
	@RequestMapping("/manager/auth/saveAdmin")
	public String saveAdmin(Admin admin) {
		
		adminService.saveAdmin(admin);
		
		return "redirect:/manager/admin/showList";
	}
	
	@RequestMapping("/manager/admin/showList")
	public String showList(Map<String, Object> map) {
		
		List<Admin> adminList = adminService.getAllAdminList();
		map.put("adminList", adminList);
		
		return "manager/admin_list";
	}
	
	@RequestMapping("/manager/admin/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "manager/manager_main";
	}
	
	@RequestMapping("/manager/admin/login")
	public String login(Admin admin, HttpSession session) {
		
		Admin loginAdmin = adminService.login(admin);
		session.setAttribute(GlobalNames.LOGIN_ADMIN, loginAdmin);
		
		return "manager/manager_main";
	}

}
