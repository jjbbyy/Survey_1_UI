package com.atguigu.survey.component.handler.manager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.survey.component.service.i.StatisticsService;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.model.Page;
import com.atguigu.survey.utils.DataprocessUtils;
import com.atguigu.survey.utils.GlobalNames;

@Controller
public class StatisticsHandler {
	
	@Autowired
	private StatisticsService statisticsService;
	
	@RequestMapping("/manager/statistics/exportExcel/{surveyId}")
	public void exportExcel(
			@PathVariable("surveyId") Integer surveyId,
			HttpServletResponse response) throws IOException {
		
		//1.准备HSSFWorkBook对象
		HSSFWorkbook workbook = statisticsService.generateWorkBook(surveyId);
		
		//2.以下载的形式，将HSSFWorkBook对象作为响应数据返回给浏览器
		//①指定响应数据的内容类型
		response.setContentType("application/vnd.ms-excel");
		//②指定响应数据的文件名
		response.setHeader("Content-Disposition", "attachment;filename="+System.nanoTime()+".xls");
		
		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		
	}
	
	@RequestMapping("/manager/statistics/showChartPictur/{questionId}")
	public void showChartPictur(
			HttpServletResponse response, 
			@PathVariable("questionId") Integer questionId) throws IOException {
		
		//1.调用Service方法生成DefaultPieDataset对象
		JFreeChart chart = statisticsService.generateDateset(questionId);
		
		//2.通过response对象获取一个“可以将二进制数据返回给浏览器”的输出流
		ServletOutputStream outputStream = response.getOutputStream();
		//PrintWriter writer = response.getWriter();
		
		//3.根据DefaultPieDataset对象生成JFreeChart图表对象，并直接将数据写入到输出流
		DataprocessUtils.processChart(chart, outputStream);
		
	}
	
	@RequestMapping("/manager/statistics/showTextAnswerList/{questionId}")
	public String showTextAnswerList(
			@PathVariable("questionId") Integer questionId,
			Map<String, Object> map) {
		
		List<String> textAnswerList = statisticsService.getTextAnswerList(questionId);
		map.put("textAnswerList", textAnswerList);
		
		return "manager/statistics_textList";
	}
	
	@RequestMapping("/manager/statistics/showSummary/{surveyId}")
	public String showSummary(
			@PathVariable("surveyId") Integer surveyId,
			Map<String, Object> map) {
		
		Survey survey = statisticsService.getEntityById(surveyId);
		map.put("survey", survey);
		
		return "manager/statistics_summary";
	}
	
	@RequestMapping("/manager/statistics/showAllAvailableSurvey")
	public String showAllAvailableSurvey(
			@RequestParam(value="pageNoStr",required=false) String pageNoStr, 
			Map<String, Object> map) {
		
		Page<Survey> page = statisticsService.getAllAvailableSurvey(pageNoStr);
		map.put(GlobalNames.PAGE, page);
		
		return "manager/statistics_list";
	}
	
}
