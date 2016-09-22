package com.atguigu.survey.component.handler.manager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.survey.component.service.i.AdminService;
import com.atguigu.survey.component.service.i.AuthService;
import com.atguigu.survey.component.service.i.ResService;
import com.atguigu.survey.entities.manager.Auth;
import com.atguigu.survey.entities.manager.Res;

@Controller
public class AuthHandler {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private ResService resService;
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping("/manager/auth/doDistribute")
	public String doDistribute(
			@RequestParam("authId") Integer authId, 
			@RequestParam(value="resIdList", required=false) List<Integer> resIdList) {
		
		authService.updateRelationship(authId, resIdList);
		
		adminService.updateCalculateAllCode();
		
		return "redirect:/manager/auth/showList";
	}
	
	@RequestMapping("/manager/auth/toDistributeUI/{authId}")
	public String toDistributeUI(
			@PathVariable("authId") Integer authId,
			Map<String, Object> map) {
		
//		[1]全部资源的列表：List<Res> resList
		List<Res> resList = resService.getResList();
		map.put("allResList", resList);
		
//		[2]当前权限的资源的id的列表：List<Integer> resIdList
		//查询中间表即可
		List<Integer> resIdList = authService.getCurrentResIdList(authId);
		map.put("currentResIdList", resIdList);
		
//		[3]当前权限的id：Integer authId
		map.put("authId", authId);
		
		return "manager/distribute_auth_res";
	}
	
	@RequestMapping("/manager/auth/batchDelete")
	public String batchDelete(@RequestParam(value="authIdList",required=false) List<Integer> authIdList) {
		
		if(authIdList != null) {
			authService.batchDelete(authIdList);
		}
		
		return "redirect:/manager/auth/showList";
	}
	
	@RequestMapping("/manager/auth/updateAuthName")
	public void updateAuthName(HttpServletResponse response, @RequestParam("authName") String authName, @RequestParam("authId") Integer authId) throws IOException {
		
		authService.updateAuthName(authName, authId);
		
		response.setContentType("text/html;charsert=UTF-8");
		response.getWriter().write("操作成功！");
		
	}
	
	@RequestMapping("/manager/auth/saveAuth")
	public String saveAuth(Auth auth) {
		
		authService.saveEntity(auth);
		
		return "redirect:/manager/auth/showList";
	}
	
	@RequestMapping("/manager/auth/showList")
	public String showList(Map<String, Object> map) {
		
		List<Auth> authList = authService.getAllAuthList();
		map.put("authList", authList);
		
		return "manager/auth_list";
	}

}
