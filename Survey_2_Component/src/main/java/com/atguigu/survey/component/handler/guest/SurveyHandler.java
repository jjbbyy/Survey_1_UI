package com.atguigu.survey.component.handler.guest;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.atguigu.survey.component.service.i.SurveyService;
import com.atguigu.survey.e.FileTooLargeException;
import com.atguigu.survey.e.FileTooLargeExceptionEdit;
import com.atguigu.survey.e.FileTypeInvalidException;
import com.atguigu.survey.e.FileTypeInvalidExceptionEdit;
import com.atguigu.survey.e.RemoveSurveyFailedException;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.model.Page;
import com.atguigu.survey.utils.DataprocessUtils;
import com.atguigu.survey.utils.GlobalNames;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Controller
public class SurveyHandler {
	
	@Autowired
	private SurveyService surveyService;
	
	@RequestMapping("/guest/survey/completed/{surveyId}")
	public String complete(@PathVariable("surveyId") Integer surveyId) {
		
		surveyService.updateSurveyStatus(surveyId);
		
		return "redirect:/index.jsp";
	}
	
	@RequestMapping("/guest/survey/deeplyRemove/{surveyId}")
	public void deeplyRemove(
			@PathVariable("surveyId") Integer surveyId,
			HttpServletRequest request, 
			HttpServletResponse response) throws IOException {
		
		surveyService.removeDeeply(surveyId);
		
		//“哪来的回哪去”
		//获取点击的超链接所在的页面的URL地址
		String referer = request.getHeader("Referer");
		
		//重定向到这个位置即可
		response.sendRedirect(referer);
		
	}
	
	@RequestMapping("/guest/survey/toDesignUI/{surveyId}")
	public String toDesignUI(
			@PathVariable("surveyId") Integer surveyId,
			Map<String, Object> map) {
		
		Survey survey = surveyService.getEntityById(surveyId);
		map.put("survey", survey);
		
		return "guest/survey_design";
	}
	
	@RequestMapping("/guest/survey/updateSurvey")
	public String updateSurvey(
			HttpServletRequest request,
			HttpSession session, 
			Survey survey, 
			@RequestParam("logoFile") MultipartFile logoFile) throws IOException {
		
		//不适合的更新方式
		//surveyService.updateEntity(survey);
		
		//1.处理上传文件
		if(!logoFile.isEmpty()) {
			
			//检查文件数据是否正确
			if(logoFile.getSize() > 1024*100) {
				request.setAttribute("survey", survey);
				throw new FileTooLargeExceptionEdit("文件太大了，不要超过100K！[更新]");
			}
			
			String contentType = logoFile.getContentType();
			
			Set<String> allowedTypes = new HashSet<>();
			allowedTypes.add("image/jpg");
			allowedTypes.add("image/jpeg");
			allowedTypes.add("image/gif");
			allowedTypes.add("image/png");
			
			if(!allowedTypes.contains(contentType)) {
				request.setAttribute("survey", survey);
				throw new FileTypeInvalidExceptionEdit("请上传图片！[更新]");
			}
			
			InputStream inputStream = logoFile.getInputStream();
			
			ServletContext servletContext = session.getServletContext();
			
			String virtualPath = "/surveyLogos";
			
			String realPath = servletContext.getRealPath(virtualPath);
			
			String logoPath = DataprocessUtils.resizeImages(inputStream, realPath);
			
			survey.setLogoPath(logoPath);
			
		}
		
		//2.执行更新
		surveyService.updateSurvey(survey);
		
		return "redirect:/guest/survey/showMyUncompleted";
	}
	
	@RequestMapping("/guest/survey/toEditUI/{surveyId}")
	public String toEditUI(
			@PathVariable("surveyId") Integer surveyId,
			Map<String, Object> map) {
		
		Survey survey = surveyService.getEntityById(surveyId);
		map.put("survey", survey);
		
		return "guest/survey_editUI";
	}
	
	@RequestMapping("/guest/survey/removeSurvey/{surveyId}")
	public void removeSurvey(
			@PathVariable("surveyId") Integer surveyId,
			HttpServletRequest request, 
			HttpServletResponse response) throws IOException {
		
		try {
			surveyService.removeEntity(surveyId);
		} catch (Exception e) {
			e.printStackTrace();
			
			Throwable cause = e.getCause();
			
			if(cause != null && cause instanceof MySQLIntegrityConstraintViolationException) {
				throw new RemoveSurveyFailedException("调查中还有包裹，无法删除！");
			}
			
		}
		
		//“哪来的回哪去”
		//获取点击的超链接所在的页面的URL地址
		String referer = request.getHeader("Referer");
		
		//重定向到这个位置即可
		response.sendRedirect(referer);
		
	}
	
	/*@RequestMapping("/guest/survey/removeSurvey/{surveyId}")
	public String removeSurvey(@PathVariable("surveyId") Integer surveyId) {
		
		surveyService.removeEntity(surveyId);
		
		return "redirect:/guest/survey/showMyUncompleted";
	}*/
	
	@RequestMapping("/guest/survey/showMyUncompleted")
	public String showMyUncompleted(
			Map<String, Object> map, 
			@RequestParam(value="pageNoStr", required=false) String pageNoStr, 
			HttpSession session) {
		
		//1.从Session域中获取登录的User对象
		User user = (User) session.getAttribute(GlobalNames.LOGIN_USRE);
		
		//2.创建Page对象
		Page<Survey> page = surveyService.getMyUncompleted(pageNoStr, user);
		map.put(GlobalNames.PAGE, page);
		
		return "guest/survey_list";
	}
	
	@RequestMapping("/guest/survey/saveSurvey")
	public String saveSurvey(
			Survey survey, 
			@RequestParam("logoFile") MultipartFile logoFile, 
			HttpSession session,
			HttpServletRequest request) throws IOException {
		
		//1.检查用户是否上传的文件
		//说明：即便用户没有上传文件，MultipartFile logoFile也不是null，仅仅isEmpty()方法返回true而已
		if(!logoFile.isEmpty()) {
			
			//※检查上传文件是否正确
			//I.文件大小
			long size = logoFile.getSize();
			if(size > 1024*100) {
				request.setAttribute("survey", survey);
				throw new FileTooLargeException("您上传的文件太大了，请不要超过100K！");
			}
			
			//II.文件类型
			String contentType = logoFile.getContentType();
			
			Set<String> allowedTypes = new HashSet<>();
			allowedTypes.add("image/jpg");
			allowedTypes.add("image/jpeg");
			allowedTypes.add("image/gif");
			allowedTypes.add("image/png");
			
			if(!allowedTypes.contains(contentType)) {
				request.setAttribute("survey", survey);
				throw new FileTypeInvalidException("请上传图片！");
			}
			
			//①如果用户已经上传了文件，则压缩图片并将返回的logoPath值设置到Survey对象中
			//[1]获取上传文件对应的输入流对象
			InputStream inputStream = logoFile.getInputStream();
			
			//[2]声明变量保存目标上传目录的虚拟路径
			String virtualPath = "/surveyLogos";
			
			//[3]为了将虚拟路径转换为真实的物理路径，获取ServletContext对象
			ServletContext servletContext = session.getServletContext();
			
			//[4]将虚拟路径转换为真实的物理路径
			String realPath = servletContext.getRealPath(virtualPath);
			
			//[5]压缩图片，并将logoPath的值返回
			String logoPath = DataprocessUtils.resizeImages(inputStream, realPath);
			
			//[6]将特定的logoPath值设置到Survey对象中
			survey.setLogoPath(logoPath);
			
		}else{
			//②如果用户没有上传文件，则让logoPath属性保持默认值
			//由于当前handler方法传入的Survey对象是SpringMVC自动创建的，所以这里logoPath自动就是默认值
			//不需要执行具体Java代码……
		}
		
		//2.获取当前登录的用户，给Survey对象设置关联关系
		//①从Session域中获取当前登录的User对象
		User user = (User) session.getAttribute(GlobalNames.LOGIN_USRE);
		
		//②将User对象设置到Survey对象中
		survey.setUser(user);
		
		//3.保存Survey对象
		surveyService.saveEntity(survey);
		
		return "redirect:/guest/survey/showMyUncompleted";
	}
	
	//不是最终正式的handler方法，为了复习使用
	public String resizeImages(Survey survey, @RequestParam("logoFile") MultipartFile logoFile, HttpSession session) throws IllegalStateException, IOException {
		
		//使用MultipartFile本身的方法实现文件保存
		//File dest = new File("D:\\good.jpg");
		//logoFile.transferTo(dest);
		
		InputStream inputStream = logoFile.getInputStream();
		
		//IO操作中需要用到的输入、输出流对象只能操作类似于：“D:\WorkSpace160427\……”这样的真实路径：realPath
		//而Web应用只能确定资源的虚拟路径，所以需要将虚拟路径转换为真实路径。
		//realPath指的是：Web应用中某个资源在服务器端的真实物理路径
		//例如：D:\WorkSpace160427\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Survey_1_UI\res_static\style.css
		//获取的方式：
		//1.创建资源虚拟路径字符串
		//虚拟路径：通过浏览器访问Web应用中资源的路径，是URL地址的一部分
		String virtualPath = "/surveyLogos";
		
		//2.获取ServletContext对象
		ServletContext servletContext = session.getServletContext();
		
		//3.调用ServletContext对象的getRealPath()方法
		String realPath = servletContext.getRealPath(virtualPath);
		
		//4.调用工具方法，压缩图片的同时，将上传的图片保存到/surveyLogos目录下
		DataprocessUtils.resizeImages(inputStream, realPath);
		
		return "redirect:/index.jsp";
	}
	
	//不是最终正式的handler方法，为了复习使用
	public String showFileUploadMessage(Survey survey, @RequestParam("logoFile") MultipartFile logoFile) throws IOException {
		
		//一、查看非文件上传提交的表单数据
		String surveyName = survey.getSurveyName();
		System.out.println("调查名称="+surveyName);
		
		//二、查看文件上传提交的表单数据
		//1.检查上传的数据是否为空
		boolean empty = logoFile.isEmpty();
		System.out.println("上传的文件是否为空："+empty);
		
		//2.文件大小
		long size = logoFile.getSize();
		System.out.println("文件大小："+size);
		
		//3.文件内容类型
		String contentType = logoFile.getContentType();
		System.out.println("内容类型："+contentType);
		
		//4.输入流
		InputStream inputStream = logoFile.getInputStream();
		System.out.println("输入流："+inputStream);
		
		//5.文件上传框的name属性值
		String name = logoFile.getName();
		System.out.println("文件上传框的name属性值："+name);
		
		//6.文件本身的文件名
		String originalFilename = logoFile.getOriginalFilename();
		System.out.println("文件本身的文件名："+originalFilename);
		
		return "redirect:/index.jsp";
	}
	
	@RequestMapping("/guest/survey/toAddUI")
	public String toAddUI() {
		
		return "guest/survey_addUI";
	}

}
