package com.atguigu.survey.component.service.i;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.JFreeChart;

import com.atguigu.survey.base.i.BaseService;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.model.Page;

public interface StatisticsService extends BaseService<Survey>{

	Page<Survey> getAllAvailableSurvey(String pageNoStr);

	List<String> getTextAnswerList(Integer questionId);

	/**
	 * 查询数据，封装到一个没有修饰过的JFreeChart对象中
	 * @param questionId
	 * @return
	 */
	JFreeChart generateDateset(Integer questionId);

	HSSFWorkbook generateWorkBook(Integer surveyId);

}
