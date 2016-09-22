package com.atguigu.survey.component.service.m;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.AnswerDao;
import com.atguigu.survey.component.dao.i.QuestionDao;
import com.atguigu.survey.component.dao.i.SurveyDao;
import com.atguigu.survey.component.service.i.StatisticsService;
import com.atguigu.survey.entities.guest.Answer;
import com.atguigu.survey.entities.guest.Bag;
import com.atguigu.survey.entities.guest.Question;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.model.Page;

@Service
public class StatisticsServiceImpl extends BaseServiceImpl<Survey> implements StatisticsService{

	@Autowired
	private SurveyDao surveyDao;
	
	@Autowired
	private AnswerDao answerDao;

	@Autowired
	private QuestionDao questionDao;
	
	@Override
	public Page<Survey> getAllAvailableSurvey(String pageNoStr) {
		int totalRecordNo = surveyDao.getAllAvailableSurveyCount();
		
		Page<Survey> page = new Page<>(pageNoStr, totalRecordNo);
		
		int pageNo = page.getPageNo();
		
		int pageSize = page.getPageSize();
		
		List<Survey> list = surveyDao.getAllAvailableSurveyList(pageNo, pageSize);
		
		page.setList(list);
		
		return page;
	}

	@Override
	public List<String> getTextAnswerList(Integer questionId) {
		return answerDao.getTextAnswerList(questionId);
	}

	@Override
	public JFreeChart generateDateset(Integer questionId) {
		
		//1.创建DefaultPieDataset对象
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		//index：遍历选项数组时的索引
		//key：optionArr[index]
		//value：key被选中的次数——'%,index,%'查询得到的结果
		//dataset.setValue(key, value);
		
		//2.查询Question对象
		Question question = questionDao.getEntityById(questionId);
		String questionName = question.getQuestionName();
		String[] optionArr = question.getOptionArr();
		
		//3.查询Question被参与的次数
		int questionEngagedCount = questionDao.getQuestionEngagedCount(questionId);
		
		//4.遍历optionArr数组
		for (int i = 0; i < optionArr.length; i++) {
			//最终生成图表的标签
			String option = optionArr[i];
			
			//查询当前选项被参与的次数
			int optionEngagedCount = questionDao.getOptionEngagedCount(questionId, i);
			
			//将上面两个值保存到Dataset对象中
			dataset.setValue(option, optionEngagedCount);
		}
		
		//5.组装JFreeChart对象
		String title = questionName + " " + questionEngagedCount + "次参与";
		
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset);
		
		return chart;
	}

	@Override
	public HSSFWorkbook generateWorkBook(Integer surveyId) {
		
		//一、查询所需要的数据
		//[1]调查名称
		Survey survey = surveyDao.getEntityById(surveyId);
		
		//[2]调查被参与的次数
		int surveyEngagedCount = surveyDao.getSurveyEngagedCount(surveyId);
		
		//[3]List<Question>
		List<Question> questionList = new ArrayList<>();
		
		Set<Bag> bagSet = survey.getBagSet();
		for (Bag bag : bagSet) {
			Set<Question> questionSet = bag.getQuestionSet();
			questionList.addAll(questionSet);
		}
		
		//[4]List<Answer>：根据surveyId查询SURVEY_ANSWER表得到的
		List<Answer> answerList = answerDao.getAnswerListBySurveyId(surveyId);
		
		//二、将数据转换为适合向workbook对象中保存的格式
		Map<String,Map<Integer,String>> bigMap = new HashMap<>();
		
		//1.遍历answerList
		for (Answer answer : answerList) {
			//2.从Answer对象中获取UUID的值
			String uuid = answer.getUuid();
			
			//3.由于在遍历过程中，相同的UUID有可能在bigMap中保存过，所以先尝试获取
			Map<Integer, String> smallMap = bigMap.get(uuid);
			
			//4.检查smallMap是否为空
			if(smallMap == null) {
				//5.如果为空，则说明本次循环是第一次保存
				smallMap = new HashMap<>();
				bigMap.put(uuid, smallMap);
			}
			
			//6.将数据填充到smallMap中
			Integer questionId = answer.getQuestionId();
			String content = answer.getContent();
			smallMap.put(questionId, content);
		}
		
		//三、创建workbook对象封装数据
		//1.创建代表Excel文件的HSSFWorkBook对象
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		//2.通过workbook对象创建代表工作表的HSSFSheet对象
		String sheetTitle = survey.getSurveyName() + " " + surveyEngagedCount + "次参与";
		
		HSSFSheet sheet = workbook.createSheet(sheetTitle);
		
		//3.通过sheet对象创建第一行
		HSSFRow row = sheet.createRow(0);
		
		//4.使用List<Question>填充第一行
		for(int i = 0; i < questionList.size(); i++) {
			Question question = questionList.get(i);
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(question.getQuestionName());
		}
		
		//5.使用bigMap生成后面的每一行：注意后面的每一行的索引从1开始
		Set<Entry<String, Map<Integer, String>>> entrySet = bigMap.entrySet();
		List<Entry<String, Map<Integer, String>>> entryList = new ArrayList<>(entrySet);
		for(int i = 0; i < entryList.size(); i++) {
			
			Entry<String, Map<Integer, String>> entry = entryList.get(i);
			
			//创建后面的每一行
			HSSFRow everyRow = sheet.createRow(i+1);
			
			//每一个smallMap生成一行
			Map<Integer, String> smallMap = entry.getValue();
			
			//为了保证生成的每一个单元格都和第一行的问题相对应，
			//所以遍历List<Question>，用每一个Question对象的ID值，
			//从smallMap中读取当前单元格的答案数据
			for(int j = 0; j < questionList.size(); j++) {
				
				//获取每一个Question对象
				Question question = questionList.get(j);
				
				//获取当前Question对象的ID值
				Integer questionId = question.getQuestionId();
				
				//根据questionId从smallMap中获取对应的答案数据
				String content = smallMap.get(questionId);
				
				//以当前循环变量作为索引值创建新的单元格
				HSSFCell cell = everyRow.createCell(j);
				cell.setCellValue(content);
			}
			
		}
		
		return workbook;
	}

}
