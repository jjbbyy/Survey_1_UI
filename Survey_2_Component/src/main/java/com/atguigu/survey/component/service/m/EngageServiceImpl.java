package com.atguigu.survey.component.service.m;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.AnswerDao;
import com.atguigu.survey.component.dao.i.SurveyDao;
import com.atguigu.survey.component.service.i.EngageService;
import com.atguigu.survey.entities.guest.Answer;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.model.Page;
import com.atguigu.survey.utils.DataprocessUtils;

@Service
public class EngageServiceImpl extends BaseServiceImpl<Survey> implements EngageService{

	@Autowired
	private SurveyDao surveyDao;
	
	@Autowired
	private AnswerDao answerDao;
	
	public EngageServiceImpl() {
		
	}

	@Override
	public Page<Survey> getAllAvailableSurveyPage(String pageNoStr) {
		
		int totalRecordNo = surveyDao.getAllAvailableSurveyCount();
		
		Page<Survey> page = new Page<>(pageNoStr, totalRecordNo);
		
		int pageNo = page.getPageNo();
		
		int pageSize = page.getPageSize();
		
		List<Survey> list = surveyDao.getAllAvailableSurveyList(pageNo, pageSize);
		
		page.setList(list);
		
		return page;
	}

	@Override
	public void parseAndSaveAnswers(
			Map<Integer, Map<String, String[]>> allBagMap,
			Integer surveyId) {
		
		//最终存放Answer数据的集合
		List<Answer> answerList = new ArrayList<>();
		
		//由于最终的答案数据不需要包裹的id，所以对于allBagMap，我们只取其中values部分即可
		Collection<Map<String, String[]>> values = allBagMap.values();
		
		//遍历values
		for (Map<String, String[]> param : values) {
			
			//遍历param
			Set<Entry<String, String[]>> entrySet = param.entrySet();
			
			for (Entry<String, String[]> entry : entrySet) {
				String key = entry.getKey();
				String[] value = entry.getValue();
				
				//检查key是否是以"q"开头
				if(!key.startsWith("q")) {
					//如果不是以q开头则停止本次循环
					continue ;
				}
				
				//根据value数组转换得到
				String content = DataprocessUtils.convertValueArrToContent(value);
				
				//从key中解析得到
				String questionIdStr = key.substring(1);
				
				Integer questionId = Integer.parseInt(questionIdStr);
				
				Answer answer = new Answer(null, content, null, questionId, surveyId);
				answerList.add(answer);
				
			}
			
		}
		
		for (Answer answer : answerList) {
			System.out.println(answer);
		}
		
		answerDao.batchSave(answerList);
		
	}

	@Override
	public Survey getSurveyById(Integer surveyId) {
		return surveyDao.getEntityById(surveyId);
	}

}
