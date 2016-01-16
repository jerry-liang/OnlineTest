package com.fivestars.websites.onlinetest.action.admin;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("admin")
@Namespace("/admin")
public class HomeAction {
	
	
	@Action(value = "home", results = { @Result(name = "success", location = "/WEB-INF/views/admin/home.jsp") })
	public String home() {
		return ActionSupport.SUCCESS;
	}
}