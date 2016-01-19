package com.fivestars.websites.onlinetest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fivestars.websites.onlinetest.dao.UserAnswerDAO;
import com.fivestars.websites.onlinetest.dao.UserQuizDAO;
import com.fivestars.websites.onlinetest.model.UserAnswer;
import com.fivestars.websites.onlinetest.model.UserQuiz;
import com.fivestars.websites.onlinetest.service.UserQuizService;

@Transactional
@Service("userQuizService")
public class UserQuizServiceImpl implements UserQuizService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserQuizServiceImpl.class);
	
	@Autowired
	private UserQuizDAO userQuizDao;
	@Autowired
	private UserAnswerDAO userAnswerDao;
	
	@Override
	public List<UserQuiz> getUserParticipatedQuiz(String userName) {
		Criterion userNameEq = Restrictions.eq("userName", userName);
		List<UserQuiz> userQuizList =  userQuizDao.listSome(new Criterion[] {userNameEq});
		// remove duplicate. The duplicate is generated for different UserAnswer in UserQuiz, because
		// UserAnswer is non-lazy-load
		List<UserQuiz> nonDuplicateList = new ArrayList<>();
		for (UserQuiz quiz : userQuizList) {
			if (!nonDuplicateList.contains(quiz)) {
				nonDuplicateList.add(quiz);
			}
		}
		return nonDuplicateList;
	}

	@Override
	public Integer createUserQuiz(UserQuiz userQuiz) {
		Integer recordId = userQuizDao.save(userQuiz);
		LOGGER.info("[UserQuizService]Successfully created user quiz record of id " + recordId);
		return recordId;
	}

	@Override
	public void updateUserQuiz(UserQuiz userQuiz) {
		userQuizDao.saveOrUpdate(userQuiz);
		LOGGER.info("[UserQuizService]Successfully updated user quiz record of id " + userQuiz.getRecordId());
	}

	@Override
	public void deleteUserQuiz(Integer recordId) {
		userQuizDao.delete(recordId);
		LOGGER.info("[UserQuizService]Successfully delete user quiz record of id " + recordId);
	}

	@Override
	public UserQuiz loadUserQuizById(Integer recordId) {
		return userQuizDao.get(recordId);
	}

	@Override
	public UserAnswer getAnswerBySubject(Integer recordId, Integer subjectId) {
		UserQuiz userQuiz = userQuizDao.load(recordId);
		for (UserAnswer userAnswer : userQuiz.getUserAnswers()) {
			if (userAnswer.getSubjectId().equals(subjectId)) {
				return userAnswer;
			}
		}
		LOGGER.warn("[UserQuizService]Cannot find answer for subject " + subjectId + " in the user quiz record " + recordId);
		return null;
	}

	@Override
	public void updateUserAnswer(UserAnswer userAnswer) {
		userAnswerDao.saveOrUpdate(userAnswer);
		LOGGER.info("[UserQuizService]Successfully updated user answer of id " + userAnswer.getAnswerId());
	}

	@Override
	public void gradeUserAnswer(Integer answerId, Double score) {
		UserAnswer answer = userAnswerDao.get(answerId);
		answer.setScore(score);
		updateUserAnswer(answer);
	}

}