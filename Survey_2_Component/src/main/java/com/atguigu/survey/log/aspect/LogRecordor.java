package com.atguigu.survey.log.aspect;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.beans.factory.annotation.Autowired;

import com.atguigu.survey.component.service.i.SurveyLogService;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.manager.Admin;
import com.atguigu.survey.entities.manager.SurveyLog;
import com.atguigu.survey.log.router.KeyBinder;
import com.atguigu.survey.log.thread.RequestBinder;
import com.atguigu.survey.utils.GlobalNames;

public class LogRecordor {
	
	@Autowired
	private SurveyLogService surveyLogService;
	
	public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
		
		Object returnObject = null;
		
		String methodName = null;
		
		String typeName = null;
		
		Object[] args = null;
		
		String exceptionType = "目标方法没有抛出异常";
		
		String exceptionMessage = "没有异常消息可以显示";
		
		try {
			
			//通过joinPoint对象获取目标方法相关信息
			//1.获取上层方法调用时传入的实际参数
			args = joinPoint.getArgs();
			
			//2.获取目标方法的签名
			Signature signature = joinPoint.getSignature();
			
			//3.通过签名获取目标方法的方法名
			methodName = signature.getName();
			
			//4.通过签名获取目标方法所在的类型的全类名
			typeName = signature.getDeclaringTypeName();

			//调用目标方法
			//1.需要传入Object[]类型的实际参数
			//2.将返回值赋值给returnObject
			returnObject = joinPoint.proceed(args);
			
		} catch(Throwable e) {

			exceptionType = e.getClass().getName();
			
			exceptionMessage = e.getMessage();
			
			//尝试获取异常的原因
			Throwable cause = e.getCause();
			
			//判断异常原因对象是否为null
			while(cause != null) {
				
				//如果不为空，则使用原因的异常信息
				exceptionType = cause.getClass().getName();
				exceptionMessage = cause.getMessage();
				
				//获取原因的原因
				cause = cause.getCause();
				
				//※陷阱：无限死循环
				//cause = e.getCause();
			}
			
			//将异常对象继续向上抛出
			throw e;
			
			/*int [] i = new int[5];
			
			for (int j = 0; j < i.length; j++) {
				
			}
			
			for(Throwable cause = e.getCause(); cause != null; cause = cause.getCause()) {
				exceptionType = cause.getClass().getName();
				exceptionMessage = cause.getMessage();
			}*/

		} finally {
			
			String params = "目标方法没有传入参数";
			
			if(args != null) {
				
				List<Object> argList = Arrays.asList(args);
				params = argList.toString();
				
			}
			
			String returnValue = "目标方法没有返回值";
			
			if(returnObject != null) {
				returnValue = returnObject.toString();
			}
			
			//从当前线程上获取request对象
			HttpServletRequest request = RequestBinder.getRequest();
			
			HttpSession session = request.getSession();
			
			User user = (User) session.getAttribute(GlobalNames.LOGIN_USRE);
			Admin admin = (Admin) session.getAttribute(GlobalNames.LOGIN_ADMIN);
			
			String userPart = (user == null)?"[User未登录]":user.getUserName();
			String adminPart = (admin == null)?"[Admin未登录]":admin.getAdminName();
			
			String operator = userPart+"/"+adminPart;
			
			String operateTime = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date());

			SurveyLog surveyLog = new SurveyLog(null, methodName, params, returnValue, operator, operateTime, exceptionType, exceptionMessage, typeName);
			//surveyLogService.saveEntity(surveyLog);
			
			//※需要将数据库切换为日志数据库
			KeyBinder.bindKey(GlobalNames.DATASOURCE_LOG);
			
			surveyLogService.saveSurveyLog(surveyLog);
			
		}
		
		return returnObject;
	}

}
