package com.atguigu.survey.tag;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.atguigu.survey.utils.GlobalNames;

/**
 * 实现问题标签中的回显部分
 * <atguigu:questionDetail bagId="" inputName="" questionType="" inputValue=""/>
 * 	效果：
 * 		选择题：checked="checked"
 * 		简答题：以前填写过的文本值
 * @author Creathin
 *
 */
public class QuestionDetailTag extends SimpleTagSupport {
	
	private Integer bagId;
	
	private String inputName;
	
	private Integer questionType;
	
	private String inputValue;
	
	@Override
	public void doTag() throws JspException, IOException {
		
		//1.准备工作
		//①准备Web资源对象
		PageContext pageContext = (PageContext) getJspContext();
		ServletRequest request = pageContext.getRequest();
		HttpSession session = pageContext.getSession();
		JspWriter out = pageContext.getOut();
		
		//②获取allBagMap
		Map<Integer, Map<String,String[]>> allBagMap = 
				(Map<Integer, Map<String, String[]>>) session.getAttribute(GlobalNames.ALL_BAG_MAP);
		
		//③使用bagId从allBagMap中获取param
		Map<String, String[]> param = allBagMap.get(bagId);
		
		if(param == null || param.size() == 0) {
			//如果不能根据bagId获取到param，那么就不必执行后续操作了
			return ;
		}
		
		//④根据inputName从param中获取value
		//inputName相当于请求参数名
		//value相当于请求参数值
		String[] historyValue = param.get(inputName);
		
		if(historyValue == null || historyValue.length == 0) {
			//如果以前没有参与过当前问题的当前选项，那么historyValue就是空，此时也不必检查是否回显了
			return ;
		}
		
		//⑤判断当前题型
		//选择题
		if(questionType == 0 || questionType == 1) {
			
			//为了便于调用contains()方法判断是否在数组中，将数组转换为List集合
			List<String> historyValueList = Arrays.asList(historyValue);
			
			boolean contains = historyValueList.contains(inputValue);
			
			if(contains) {
				out.print("checked='checked'");
			}
			
		}
		
		//简答题
		if(questionType == 2) {
			String textValue = historyValue[0];
			out.print("value='"+textValue+"'");
		}
	}

	public void setBagId(Integer bagId) {
		this.bagId = bagId;
	}
	
	public void setInputName(String inputName) {
		this.inputName = inputName;
	}
	
	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}
	
	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}
}
