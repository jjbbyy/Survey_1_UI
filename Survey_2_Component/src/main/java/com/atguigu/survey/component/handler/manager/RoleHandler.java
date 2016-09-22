package com.atguigu.survey.component.handler.manager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.survey.component.service.i.AdminService;
import com.atguigu.survey.component.service.i.AuthService;
import com.atguigu.survey.component.service.i.RoleService;
import com.atguigu.survey.entities.manager.Auth;
import com.atguigu.survey.entities.manager.Role;

@Controller
public class RoleHandler {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping("/manager/role/dispatcher")
	public String dispatcher(
			@RequestParam("roleId") Integer roleId, 
			@RequestParam(value="authIdList", required=false) List<Integer> authIdList) {
		
		roleService.updateRelationship(roleId, authIdList);
		
		adminService.updateCalculateAllCode();
		
		return "redirect:/manager/role/showList";
	}
	
	@RequestMapping("/manager/role/toDispatcherUI/{roleId}")
	public String toDispatcherUI(@PathVariable("roleId") Integer roleId, Map<String,Object> map) {
		
		List<Auth> allAuthList = authService.getAllAuthList();
		List<Integer> currentIdList = roleService.getCurrentAuthIdList(roleId);
		
		map.put("allAuthList", allAuthList);
		map.put("currentIdList", currentIdList);
		map.put("roleId", roleId);
		
		return "manager/distribute_role_auth";
	}
	
	@RequestMapping("/manager/role/batchDelete")
	public String batchDelete(@RequestParam("roleIdList") List<Integer> roleIdList) {
		
		roleService.batchDelete(roleIdList);
		
		return "redirect:/manager/role/showList";
	}
	
	@RequestMapping("/manager/role/updateRoleName")
	public void updateRoleName(@RequestParam("roleName") String roleName, @RequestParam("roleId") Integer roleId) {
		
		roleService.updateRoleName(roleName, roleId);
		
	}
	
	@RequestMapping("/manager/role/saveRole")
	public String saveRole(Role role) {
		
		roleService.saveEntity(role);
		
		return "redirect:/manager/role/showList";
	}
	
	@RequestMapping("/manager/role/showList")
	public String showList(Map<String, Object> map) {
		
		List<Role> roleList = roleService.getAllRoleList();
		map.put("roleList", roleList);
		
		return "manager/role_list";
	}

}
