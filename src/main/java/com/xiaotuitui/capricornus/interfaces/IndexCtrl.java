package com.xiaotuitui.capricornus.interfaces;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaotuitui.capricornus.application.UserSrv;
import com.xiaotuitui.capricornus.domain.model.User;
import com.xiaotuitui.capricornus.util.constant.Constant;
import com.xiaotuitui.framework.interfaces.BaseCtrl;

@Controller
@RequestMapping(value = "/index")
public class IndexCtrl extends BaseCtrl{
	
	@Autowired
	private UserSrv userSrv;
	
	@RequestMapping(value = "/toLogin", method = RequestMethod.GET)
	public String toLogin(){
		return "/index/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(HttpServletRequest request, HttpSession session, HttpServletResponse response){
		User user = userSrv.queryUserByUsername(request.getParameter("username"));
		if(user==null){
			ajaxError(request, response);
		}else{
			String password = user.getPassword();
			if(!password.equals(request.getParameter("password"))){
				ajaxError(request, response);
			}else{
				doSession(request, session, user);
				ajaxSuccess(request, response);
			}
		}
	}

	private void doSession(HttpServletRequest request, HttpSession session, User user) {
		session.removeAttribute(Constant.USER);
		session.invalidate();
		Cookie[] cookies = request.getCookies();
		if(cookies!=null&&cookies.length>0){
			Cookie cookie = cookies[0];
			if(cookie!=null){
				cookie.setMaxAge(0);
			}
		}
		session = request.getSession(true);
		session.setAttribute(Constant.USER, user);
	}

}