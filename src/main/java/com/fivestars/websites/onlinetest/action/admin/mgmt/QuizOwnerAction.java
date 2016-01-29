package com.fivestars.websites.onlinetest.action.admin.mgmt;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fivestars.websites.onlinetest.model.QuizOwnership;
import com.fivestars.websites.onlinetest.service.QuizOwnerService;
import com.opensymphony.xwork2.ActionSupport;

import lombok.Getter;
import lombok.Setter;

@ParentPackage("admin")
@InterceptorRef(value="global")
@Namespace("/admin/quizowner")
public class QuizOwnerAction{
	@Getter @Setter
	private String userName;
	@Getter @Setter
	private List<Integer> quizIds;
	@Getter @Setter
	private String selectedIds;
	
	@Autowired
	private QuizOwnerService quizOwnerService;
	
	@Action(value = "loadOwnedQuizzes", results = { @Result(name="success", type = "json")})
	public String loadOwnedQuizzes() throws JsonProcessingException {
		List<QuizOwnership> quizzes = quizOwnerService.loadByUser(userName);
		quizIds = new ArrayList<>();
		for(QuizOwnership each : quizzes) {
			quizIds.add(each.getQuizId());
		}
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "saveOwnedQuizzes", results = { @Result(name="success", type = "json")})
	public String saveOwnedQuizzes() throws JsonProcessingException {
		List<QuizOwnership> quizzes = quizOwnerService.loadByUser(userName);
		
		// init
		quizIds = new ArrayList<>();
		for(String id : selectedIds.split(",")){
			quizIds.add(Integer.parseInt(id));
		}
		
		// remove 
		for(QuizOwnership each : quizzes) {
			if(!quizIds.contains(each.getQuizId())) {
				quizOwnerService.delete(each.getOwnershipId());
			} else {
				quizIds.remove(each.getQuizId());
			}
		}
		
		// add new
		for(int id : quizIds) {
			QuizOwnership ownership = new QuizOwnership();
			ownership.setUserName(userName);
			ownership.setQuizId(id);
			quizOwnerService.save(ownership);
		}
		
		return ActionSupport.SUCCESS;
	}
}