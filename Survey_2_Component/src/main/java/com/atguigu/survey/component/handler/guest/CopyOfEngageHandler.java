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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.survey.component.service.i.EngageService;
import com.atguigu.survey.entities.guest.Bag;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.model.Page;
import com.atguigu.survey.utils.GlobalNames;

//@Controller
public class CopyOfEngageHandler {
	
	@Autowired
	private EngageService engageService;
	
	@RequestMapping("/guest/engage/engage")
	public String engage(
			HttpServletRequest request, 
			HttpSession session,
			@RequestParam("bagId") Integer bagId) {
		
		
		//合并答案
		//[1]从Session域中获取allBagMap
		Map<Integer,Map<String,String[]>> allBagMap = (Map<Integer, Map<String, String[]>>) session.getAttribute(GlobalNames.ALL_BAG_MAP);
		
		//[2]从请求参数中获取bagId值
		
		//[3]获取封装当前请求参数的Map：param
		Map<String,String[]> paramFromRequest = request.getParameterMap();
		
		//为了避免request对象中获取的Map存在的问题，每次合并答案时都创建新的Map对象
		Map<String,String[]> param = new HashMap<>(paramFromRequest);
		
		//===============为了测试数据，打印param===============
		/*Set<Entry<String, String[]>> entrySet = param.entrySet();
		for (Entry<String, String[]> entry : entrySet) {
			String key = entry.getKey();
			String[] value = entry.getValue();
			List<String> valueList = Arrays.asList(value);
			System.out.println(key+" "+valueList);
		}*/
		
		//=================================================
		
		//[4]将param以bagId为键，保存到allBagMap中
		allBagMap.put(bagId, param);
		//===============为了测试数据，打印allBagMap===============
		Set<Entry<Integer,Map<String,String[]>>> entrySet = allBagMap.entrySet();
		for (Entry<Integer, Map<String, String[]>> entry : entrySet) {
			Integer bagIdTest = entry.getKey();
			Map<String, String[]> paramTest = entry.getValue();
			System.out.println("bagId="+bagIdTest);
			Set<Entry<String, String[]>> paramEntrySet = paramTest.entrySet();
			for (Entry<String, String[]> paramEntry : paramEntrySet) {
				String key = paramEntry.getKey();
				String[] value = paramEntry.getValue();
				List<String> valueList = Arrays.asList(value);
				System.out.println(key+" "+valueList);
			}
			System.out.println("===================================");
		}
		
		//====================================================
		
		//判断当前要执行的操作是四个按钮中的哪一个
		if(param.containsKey("submit_prev")) {
			//准备当前索引值
			//①从请求参数中获取旧的包裹索引值
			String currentIndexOldStr = request.getParameter(GlobalNames.CURRENT_INDEX);
			
			//②转换为整数类型
			Integer currentIndexOld = Integer.parseInt(currentIndexOldStr);
			
			//③计算新的索引值
			int currentIndexNew = currentIndexOld - 1;
			
			//④将新的索引值保存到请求域中
			request.setAttribute(GlobalNames.CURRENT_INDEX, currentIndexNew);
			
			//准备当前Bag对象
			//①从Session域中获取bagList
			List<Bag> bagList = (List<Bag>) session.getAttribute(GlobalNames.BAG_LIST);
			
			//②根据新索引获取Bag对象
			Bag bag = bagList.get(currentIndexNew);
			
			//③保存到请求域中到页面上显示
			request.setAttribute(GlobalNames.CURRENT_BAG, bag);
			
			return "guest/engage_engage";
		}
		
		if(param.containsKey("submit_next")) {
			
			//准备当前索引值
			//①从请求参数中获取旧的包裹索引值
			String currentIndexOldStr = request.getParameter(GlobalNames.CURRENT_INDEX);
			
			//②转换为整数类型
			Integer currentIndexOld = Integer.parseInt(currentIndexOldStr);
			
			//③计算新的索引值
			int currentIndexNew = currentIndexOld + 1;
			
			//④将新的索引值保存到请求域中
			request.setAttribute(GlobalNames.CURRENT_INDEX, currentIndexNew);
			
			//准备当前Bag对象
			//①从Session域中获取bagList
			List<Bag> bagList = (List<Bag>) session.getAttribute(GlobalNames.BAG_LIST);
			
			//②根据新索引获取Bag对象
			Bag bag = bagList.get(currentIndexNew);
			
			//③保存到请求域中到页面上显示
			request.setAttribute(GlobalNames.CURRENT_BAG, bag);
			
			return "guest/engage_engage";
			
		}
		
		if(param.containsKey("submit_done")) {
			
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
		Survey survey = engageService.getEntityById(surveyId);
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
