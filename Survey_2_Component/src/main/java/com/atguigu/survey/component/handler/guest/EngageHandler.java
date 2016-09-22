package com.atguigu.survey.component.handler.guest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.survey.component.service.i.EngageService;
import com.atguigu.survey.entities.guest.Bag;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.model.Page;
import com.atguigu.survey.utils.DataprocessUtils;
import com.atguigu.survey.utils.GlobalNames;

@Controller
public class EngageHandler {
	
	@Autowired
	private EngageService engageService;
	
	@RequestMapping("/guest/engage/engage")
	public String engage(
			HttpServletRequest request, 
			HttpSession session,
			@RequestParam("bagId") Integer bagId) {
		
		//从Session域中获取allBagMap备用
		Map<Integer,Map<String,String[]>> allBagMap = (Map<Integer, Map<String, String[]>>) session.getAttribute(GlobalNames.ALL_BAG_MAP);
		
		//合并答案
		Map<String,String[]> param = DataprocessUtils.mergeAnswers(allBagMap, bagId, request);
		
		//包裹导航
		//包裹导航的准备工作
		String currentIndexOldStr = request.getParameter(GlobalNames.CURRENT_INDEX);
		
		Integer currentIndexOld = Integer.parseInt(currentIndexOldStr);
		
		List<Bag> bagList = (List<Bag>) session.getAttribute(GlobalNames.BAG_LIST);
		
		//判断当前要执行的是否是包裹导航操作
		if(param.containsKey("submit_prev") || param.containsKey("submit_next")) {
			//计算新的索引值
			Integer currentIndexNew = null;
			
			if(param.containsKey("submit_prev")) {
				currentIndexNew = currentIndexOld - 1;
			}
			
			if(param.containsKey("submit_next")) {
				currentIndexNew = currentIndexOld + 1;
			}
			
			//将新的索引值保存到请求域中
			request.setAttribute(GlobalNames.CURRENT_INDEX, currentIndexNew);
			
			//根据新的索引值获取要显示的当前包裹
			Bag bag = bagList.get(currentIndexNew);
			
			//将新的包裹保存到请求域中，到目标页面显示
			request.setAttribute(GlobalNames.CURRENT_BAG, bag);
			
			return "guest/engage_engage";
		}
		
		if(param.containsKey("submit_done")) {
			
			//点击“完成”时，解析并保存答案数据
			Survey survey = (Survey) session.getAttribute(GlobalNames.CURRENT_SURVEY);
			Integer surveyId = survey.getSurveyId();
			
			engageService.parseAndSaveAnswers(allBagMap, surveyId);
			
		}
		
		if(param.containsKey("submit_quit")) {
			
		}
		
		//清空Session域中与参与调查相关的内容
		session.removeAttribute(GlobalNames.CURRENT_SURVEY);
		session.removeAttribute(GlobalNames.LAST_INDEX);
		session.removeAttribute(GlobalNames.BAG_LIST);
		session.removeAttribute(GlobalNames.ALL_BAG_MAP);
		
		return "redirect:/index.jsp";
	}
	
	@RequestMapping("/guest/engage/entry/{surveyId}")
	public String entry(@PathVariable("surveyId") Integer surveyId, HttpServletRequest request, HttpSession session) {
		
		//1.根据surveyId查询Survey对象，保存到Session域中
		Survey survey = engageService.getSurveyById(surveyId);
		session.setAttribute(GlobalNames.CURRENT_SURVEY, survey);
		
		//2.从Survey对象中获取Set<Bag>
		Set<Bag> bagSet = survey.getBagSet();
		
		//3.将Set<Bag>转换为List<Bag>
		List<Bag> bagList = new ArrayList<>(bagSet);
		
		//4.将List<Bag>保存到Session域中
		session.setAttribute(GlobalNames.BAG_LIST, bagList);
		
		//5.获取List<Bag>的最后一个元素的索引，保存到Session域中
		int lastIndex = bagList.size() - 1;
		session.setAttribute(GlobalNames.LAST_INDEX, lastIndex);
		
		//6.从List<Bag>中获取第一个元素，保存到请求域中
		Bag bag = bagList.get(0);
		request.setAttribute(GlobalNames.CURRENT_BAG, bag);
		
		//7.将0作为当前的包裹索引保存到请求域中
		request.setAttribute(GlobalNames.CURRENT_INDEX, 0);
		
		//8.创建allBagMap，保存到Session域中
		//Map<bagId,param>
		Map<Integer, Map<String,String[]>> allBagMap = new HashMap<>();
		session.setAttribute(GlobalNames.ALL_BAG_MAP, allBagMap);
		
		return "guest/engage_engage";
	}
	
	@RequestMapping("/guest/engage/showAllAvailableSurvey")
	public String showAllAvailableSurvey(@RequestParam(value="pageNoStr", required=false) String pageNoStr, Map<String,Object> map) {
		
		Page<Survey> page = engageService.getAllAvailableSurveyPage(pageNoStr);
		map.put(GlobalNames.PAGE, page);
		
		return "guest/engage_list";
	}

}
