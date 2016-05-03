package com.xiaotuitui.capricornus.application.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class IndexSrvImpl {
	
	@PostConstruct
	public void init(){
		System.out.println("init");
	}

}