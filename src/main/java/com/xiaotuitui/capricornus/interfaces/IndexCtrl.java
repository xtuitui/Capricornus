package com.xiaotuitui.capricornus.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/index")
public class IndexCtrl {
	
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, HttpServletResponse response){
		return "/index";
	}


}