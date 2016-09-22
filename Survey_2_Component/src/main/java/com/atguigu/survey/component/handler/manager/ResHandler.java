package com.atguigu.survey.component.handler.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.survey.component.service.i.ResService;
import com.atguigu.survey.entities.manager.Res;

@Controller
public class ResHandler {
	
	@Autowired
	private ResService resService;
	
	@RequestMapping("/manager/res/batchDelete")
	public String batchDelete(@RequestParam("resIdList") List<Integer> resIdList) {
		
		resService.batchDelete(resIdList);
		
		return "redirect:/manager/res/showList";
	}
	
	@ResponseBody
	@RequestMapping("/manager/res/toggleStatus")
	public Map<String, String> toggleStatus(@RequestParam("resId") Integer resId) {
		
		//执行修改操作
		Res res = resService.updateResStatus(resId);
		
		//修改后的按钮名称
		String statusValue = res.isPublicRes()?"公共资源":"受保护资源";
		
		//根据数据修改情况封装返回给浏览器的响应数据
		Map<String, String> map = new HashMap<>();
		map.put("message", "成功！");
		map.put("statusValue", statusValue);
		
		return map;
	}
	
	@RequestMapping("/manager/res/showList")
	public String showList(Map<String, Object> map) {
		
		List<Res> resList = resService.getResList();
		map.put("resList", resList);
		
		return "manager/res_list";
	}

}
