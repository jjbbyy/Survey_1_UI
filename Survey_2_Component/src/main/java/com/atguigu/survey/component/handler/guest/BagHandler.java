package com.atguigu.survey.component.handler.guest;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.survey.component.service.i.BagService;
import com.atguigu.survey.component.service.i.SurveyService;
import com.atguigu.survey.e.BagOrderDuplicateException;
import com.atguigu.survey.e.RemoveBagFailedException;
import com.atguigu.survey.entities.guest.Bag;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.model.Page;
import com.atguigu.survey.utils.DataprocessUtils;
import com.atguigu.survey.utils.GlobalNames;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Controller
public class BagHandler {
	
	@Autowired
	private BagService bagService;
	
	@Autowired
	private SurveyService surveyService;
	
	@RequestMapping("/guest/bag/copyToThisSurvey/{bagId}/{surveyId}")
	public String copyToThisSurvey(
			@PathVariable("bagId") Integer bagId,
			@PathVariable("surveyId") Integer surveyId) {
		
		bagService.updateRelationshipByCopy(bagId, surveyId);
			
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	@RequestMapping("/guest/bag/moveToThisSurvey/{bagId}/{surveyId}")
	public String moveToThisSurvey(
			@PathVariable("bagId") Integer bagId,
			@PathVariable("surveyId") Integer surveyId) {
		
		bagService.updateRelationshipByMove(bagId, surveyId);
		
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	@RequestMapping("/guest/bag/toMoveOrCopyUI/{bagId}/{surveyId}")
	public String toMoveOrCopyUI(
			@PathVariable("bagId") Integer bagId,
			@PathVariable("surveyId") Integer surveyId,
			Map<String, Object> map,
			@RequestParam(value="pageNoStr", required=false) String pageNoStr,
			HttpSession session) {
		
		User user = (User) session.getAttribute(GlobalNames.LOGIN_USRE);
		
		Page<Survey> page = surveyService.getMyUncompleted(pageNoStr, user);
		
		map.put(GlobalNames.PAGE, page);
		
		map.put("surveyId", surveyId);
		
		map.put("bagId", bagId);
		
		return "guest/bag_moveOrCopyList";
	}
	
	@RequestMapping("/guest/bag/doAdjust")
	public String doAjust(
			@RequestParam("bagOrderList") List<Integer> bagOrderList,
			@RequestParam("bagIdList") List<Integer> bagIdList,
			@RequestParam("surveyId") Integer surveyId,
			HttpServletRequest request) {
		
		boolean result = DataprocessUtils.checkListDuplicate(bagOrderList);
		
		if(!result) {
			
			List<Bag> bagList = bagService.getBagListBySurveyId(surveyId);
			request.setAttribute("bagList", bagList);
			request.setAttribute("surveyId", surveyId);
			
			throw new BagOrderDuplicateException("包裹的序号不能重复！");
		}
		
		for (int i = 0; i < bagOrderList.size(); i++) {
			Integer bagOrder = bagOrderList.get(i);
			Integer bagId = bagIdList.get(i);
			System.out.println("bagOrder="+bagOrder);
			System.out.println("bagId="+bagId);
			System.out.println("----------------------");
		}
		
		bagService.batchUpdateBagOrder(bagOrderList, bagIdList);
		
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	@RequestMapping("/guest/bag/toAdjustUI/{surveyId}")
	public String toAdjustUI(Map<String, Object> map,
			@PathVariable("surveyId") Integer surveyId) {
		
		List<Bag> bagList = bagService.getBagListBySurveyId(surveyId);
		map.put("bagList", bagList);
		map.put("surveyId", surveyId);
		
		return "guest/bag_adjustUI";
	}
	
	@RequestMapping("/guest/bag/deeplyRemove/{bagId}/{surveyId}")
	public String deeplyRemove(@PathVariable("bagId") Integer bagId,
			@PathVariable("surveyId") Integer surveyId) {
		
		bagService.removeDeeply(bagId);
		
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	@RequestMapping("/guest/bag/updateBag")
	public String updateBag(Bag bag) {
		
		bagService.updateBag(bag);
		
		return "redirect:/guest/survey/toDesignUI/"+bag.getSurvey().getSurveyId();
	}
	
	@RequestMapping("/guest/bag/toEditUI/{bagId}/{surveyId}")
	public String toEditUI(@PathVariable("bagId") Integer bagId,
			@PathVariable("surveyId") Integer surveyId,
			Map<String, Object> map) {
		
		//1.根据bagId从数据库中查询Bag对象，为表单回显做准备
		Bag bag = bagService.getEntityById(bagId);
		map.put("bag", bag);
		
		//2.将surveyId保存到请求域中，目的是将来更新完成后回到调查设计页面
		map.put("surveyId", surveyId);
		
		return "guest/bag_editUI";
	}
	
	@RequestMapping("/guest/bag/removeBag/{bagId}/{surveyId}")
	public String removeBag(@PathVariable("bagId") Integer bagId,
			@PathVariable("surveyId") Integer surveyId) {
		
		try {
			bagService.removeEntity(bagId);
		} catch (Exception e) {
			e.printStackTrace();
			
			//1.尝试获取当前异常对象的原因
			Throwable cause = e.getCause();
			
			//2.在cause存在的情况下检查其类型
			if(cause != null && cause instanceof MySQLIntegrityConstraintViolationException) {
				
				//3.抛出自定义异常
				throw new RemoveBagFailedException("包裹中还有问题，无法删除！");
				
			}
			
		}
		
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	@RequestMapping("/guest/bag/saveBag")
	public String saveBag(Bag bag) {
		
		bagService.saveBag(bag);
		
		return "redirect:/guest/survey/toDesignUI/"+bag.getSurvey().getSurveyId();
	}
	
	@RequestMapping("/guest/bag/toAddUI/{surveyId}")
	public String toAddUI(
			@PathVariable("surveyId") Integer surveyId,
			Map<String, Object> map) {
		
		map.put("surveyId", surveyId);
		
		return "guest/bag_addUI";
	}

}
