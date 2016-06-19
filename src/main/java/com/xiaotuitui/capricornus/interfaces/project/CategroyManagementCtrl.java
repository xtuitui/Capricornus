package com.xiaotuitui.capricornus.interfaces.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xiaotuitui.capricornus.application.CategorySrv;
import com.xiaotuitui.capricornus.domain.model.Category;
import com.xiaotuitui.capricornus.util.constant.MessageCode;
import com.xiaotuitui.framework.interfaces.BaseCtrl;

@Controller
@RequestMapping(value = "/project/category")
public class CategroyManagementCtrl extends BaseCtrl{
	
	@Autowired
	private CategorySrv categorySrv;
	
	@RequestMapping(value = "/toCategory", method = RequestMethod.GET)
	public String toCategory(HttpServletRequest request){
		request.setAttribute("categoryList", categorySrv.queryAllCategory());
		return "/project/category/category";
	}
	
	@RequestMapping(value = "/addCategory", method = RequestMethod.POST)
	public void addCategory(HttpServletResponse response, Category category){
		Category checkCategory = categorySrv.queryCategoryByName(category.getName());
		if(checkCategory!=null){
			ajaxErrorData(response, MessageCode.CATEGORY_ALREADY_EXIST);
		}else{
			categorySrv.createCategory(category);
			ajaxSuccess(response);
		}
	}
	
	@RequestMapping(value = "/deleteCategory", method = RequestMethod.POST)
	public void deleteCategory(HttpServletResponse response, @RequestParam(value = "categoryId") Integer categoryId){
		categorySrv.removeCategory(categoryId);
		ajaxSuccess(response);
	}
	
	@RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
	public void updateCategory(HttpServletResponse response, Category category){
		Category checkCategory = categorySrv.queryCategoryByName(category.getName());
		if(checkCategory != null && ! checkCategory.getId().equals(category.getId())){
			ajaxErrorData(response, MessageCode.CATEGORY_ALREADY_EXIST);
		}else{
			categorySrv.updateCategory(category);
			ajaxSuccess(response);
		}
	}
	
}