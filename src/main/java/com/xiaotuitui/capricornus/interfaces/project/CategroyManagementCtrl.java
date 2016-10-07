package com.xiaotuitui.capricornus.interfaces.project;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xiaotuitui.capricornus.application.CategorySrv;
import com.xiaotuitui.capricornus.domain.model.Category;
import com.xiaotuitui.capricornus.domain.model.Project;
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
		if(validateCategory(response, category)){
			categorySrv.createCategory(category);
			ajaxSuccess(response);
		}
	}
	
	@RequestMapping(value = "/checkProjectCategoryDependency", method = RequestMethod.POST)
	public void checkProjectCategoryDependency(HttpServletResponse response, Category category){
		category = categorySrv.loadCategory(category.getId());
		List<Project> projectList = category.getProjects();
		ajaxSuccessData(response, projectList);
	}
	
	@RequestMapping(value = "/deleteCategory", method = RequestMethod.POST)
	public void deleteCategory(HttpServletResponse response, @RequestParam(value = "categoryId") Integer categoryId){
		categorySrv.removeCategory(categoryId);
		ajaxSuccess(response);
	}
	
	@RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
	public void updateCategory(HttpServletResponse response, Category category){
		if(validateCategory(response, category)){
			categorySrv.updateCategory(category);
			ajaxSuccess(response);
		}
	}
	
	private boolean validateCategory(HttpServletResponse response, Category category){
		Category checkCategory = categorySrv.queryCategoryByName(category.getName());
		if(checkCategory != null && ! checkCategory.getId().equals(category.getId())){
			ajaxErrorData(response, MessageCode.CATEGORY_ALREADY_EXIST);
			return false;
		}
		return true;
	}
	
	@RequestMapping(value = "/updateProjectCategory", method = RequestMethod.POST)
	public void updateProjectCategory(HttpServletResponse response, @RequestParam(value = "projectIds") List<Integer> projectIds, @RequestParam(value = "categoryIds") List<Integer> categoryIds){
		categorySrv.updateProjectCategory(projectIds, categoryIds);
		ajaxSuccess(response);
	}
	
}