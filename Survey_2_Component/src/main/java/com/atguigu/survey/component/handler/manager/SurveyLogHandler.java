package com.atguigu.survey.component.handler.manager;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.survey.component.service.i.SurveyLogService;
import com.atguigu.survey.entities.manager.SurveyLog;
import com.atguigu.survey.log.router.KeyBinder;
import com.atguigu.survey.model.Page;
import com.atguigu.survey.utils.GlobalNames;

@Controller
public class SurveyLogHandler {
	
	@Autowired
	private SurveyLogService surveyLogService;
	
	@RequestMapping("/manager/log/showList")
	public String showList(
			@RequestParam(value="pageNoStr", required=false) String pageNoStr, 
			Map<String,Object> map) {
		
		KeyBinder.bindKey(GlobalNames.DATASOURCE_LOG);
		
		Page<SurveyLog> page = surveyLogService.getPage(pageNoStr);
		map.put(GlobalNames.PAGE, page);
		
		return "manager/log_list";
	}

}
