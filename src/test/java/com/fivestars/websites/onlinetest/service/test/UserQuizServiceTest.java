package com.fivestars.websites.onlinetest.service.test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fivestars.websites.onlinetest.constant.QuizConst;
import com.fivestars.websites.onlinetest.entity.AnswerAnswerEntity;
import com.fivestars.websites.onlinetest.entity.SingleChoiceAnswerEntity;
import com.fivestars.websites.onlinetest.model.QuizOwnership;
import com.fivestars.websites.onlinetest.model.UserAnswer;
import com.fivestars.websites.onlinetest.model.UserQuiz;
import com.fivestars.websites.onlinetest.service.UserQuizService;

public class UserQuizServiceTest {

	private static ApplicationContext context;
	private static UserQuizService userQuizService;
	private ObjectMapper mapper = new ObjectMapper();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		userQuizService = context.getBean(UserQuizService.class);
	}
	
	@Test
	public void testUserQuizOwnership() {
		QuizOwnership ownership = prepareQuizOwnership();
		Integer ownershipId = userQuizService.addUserQuizOwnership(ownership);
		assertTrue(userQuizService.isUserOwnQuiz(118, "owner"));
		userQuizService.deleteUserQuizOwnership(ownershipId);
		
		ownership.setExpired(QuizConst.EXPIRED_TRUE);
		ownershipId = userQuizService.addUserQuizOwnership(ownership);
		assertFalse(userQuizService.isUserOwnQuiz(118, "owner"));
		userQuizService.deleteUserQuizOwnership(ownershipId);
	}
	
	@Test
	public void testGetUserQuiz() throws IOException {
		UserQuiz userQuiz = prepareUserQuiz();
		String userName = "another_user_" + Math.random() * 1000000;
		userQuiz.setUserName(userName);
		Integer recordId = userQuizService.createUserQuiz(userQuiz);
		assertNotNull(recordId);
		
		List<UserQuiz> userQuizList = userQuizService.getUserParticipatedQuiz(userName);
		assertThat(userQuizList.size(), equalTo(1));
		
		UserAnswer answer1 = userQuizService.getAnswerBySubject(recordId, 173);
		assertThat(answer1.getAnswer(), equalTo(mapper.writeValueAsString(new SingleChoiceAnswerEntity(29))));
		UserAnswer answer2 = userQuizService.getAnswerBySubject(recordId, 174);
		assertThat(answer2.getAnswer(), equalTo(mapper.writeValueAsString(new AnswerAnswerEntity("红酒"))));
		
		userQuizService.deleteUserQuiz(recordId);
	}

	@Test
	public void testUserQuiz() throws IOException {
		UserQuiz userQuiz = prepareUserQuiz();
		Integer recordId = userQuizService.createUserQuiz(userQuiz);
		assertNotNull(recordId);
		
		UserQuiz userQuizInDB = userQuizService.loadUserQuizById(recordId);
		assertThat(userQuizInDB.getUserAnswers().size(), equalTo(2));
		
		userQuizInDB.setScore(new Double(100));
		userQuizService.updateUserQuiz(userQuizInDB);
		userQuizInDB = userQuizService.loadUserQuizById(recordId);
		assertThat(userQuizInDB.getScore(), equalTo(new Double("100")));
		
		userQuizService.deleteUserQuiz(recordId);
		userQuizInDB = userQuizService.loadUserQuizById(recordId);
		assertNull(userQuizInDB);
	}
	
	private QuizOwnership prepareQuizOwnership() {
		QuizOwnership ownership = new QuizOwnership();
		ownership.setUserName("owner");
		ownership.setQuizId(118);
		ownership.setBuyDate(new Date());
		ownership.setExpired(QuizConst.EXPIRED_FALSE);
		return ownership;
	}

	private UserQuiz prepareUserQuiz() throws IOException {
		UserQuiz userQuiz = new UserQuiz();
		userQuiz.setUserName("test_user");
		userQuiz.setQuizId(118);
		userQuiz.setScore(new Double(75));
		userQuiz.setFeedbackId(0);
		userQuiz.setQuizDate(new Date());
		userQuiz.setEvaluator("test_teacher");
		Set<UserAnswer> userAnswerSet = new HashSet<>();
		userQuiz.setUserAnswers(userAnswerSet);
		
		UserAnswer answer1 = new UserAnswer();
		answer1.setUserName("test_user");
		answer1.setQuizId(118);
		answer1.setSubjectId(173);
		SingleChoiceAnswerEntity singleChoice = new SingleChoiceAnswerEntity(29);
		answer1.setAnswer(mapper.writeValueAsString(singleChoice));
		answer1.setAnswerDate(new Date());
		answer1.setScore(new Double(10));
		answer1.setUserQuiz(userQuiz);
		userAnswerSet.add(answer1);
		
		UserAnswer answer2 = new UserAnswer();
		answer2.setUserName("test_user");
		answer2.setQuizId(118);
		answer2.setSubjectId(174);
		AnswerAnswerEntity answer = new AnswerAnswerEntity("红酒");
		answer2.setAnswer(mapper.writeValueAsString(answer));
		answer2.setAnswerDate(new Date());
		answer2.setScore(new Double(15));
		answer2.setUserQuiz(userQuiz);
		userAnswerSet.add(answer2);
		
		return userQuiz;
	}
}
