package com.fivestars.websites.onlinetest.service.test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fivestars.websites.onlinetest.constant.QuizConst;
import com.fivestars.websites.onlinetest.model.Quiz;
import com.fivestars.websites.onlinetest.model.QuizCategory;
import com.fivestars.websites.onlinetest.model.QuizSubject;
import com.fivestars.websites.onlinetest.model.SubjectItem;
import com.fivestars.websites.onlinetest.service.QuizService;

public class QuizServiceTest {

	private static ApplicationContext context;
	private static QuizService quizService;
	
	/**
     * set up
     */
	@BeforeClass
    public static void before() {
    	context = new ClassPathXmlApplicationContext("applicationContext.xml");
    	quizService = (QuizService) context.getBean("quizService");
    }
	
	@Test
	public void testAddDeleteItem() {
		Quiz quiz = prepareQuiz();
		Integer quizId = quizService.createQuiz(quiz);
		Quiz quizInDB = quizService.loadQuizById(quizId);
		QuizSubject subject = quizInDB.getQuizSubjects().iterator().next();
		assertThat(subject.getSubjectItems().size(), is(4));
		// test add item
		Integer subjectId = subject.getSubjectId();
		SubjectItem item = prepareItem(subject);
		quizService.addItemToSubject(subjectId, item);
		QuizSubject subjectInDB = quizService.loadQuizSubjectById(subjectId);
		assertThat(subjectInDB.getSubjectItems().size(), is(5));
		
		SubjectItem itemInDB = null;
		for (SubjectItem itemInSet : subjectInDB.getSubjectItems()) {
			if (itemInSet.getChoice().equals("碗里")) {
				itemInDB = itemInSet;
				break;
			}
		}
		assertNotNull(itemInDB);
		Integer itemId = itemInDB.getItemId();
		// test delete item
		quizService.deleteItemFromSubject(subjectId, itemId);
		subjectInDB = quizService.loadQuizSubjectById(subjectId);
		assertThat(subjectInDB.getSubjectItems().size(), is(4));
		assertNull(quizService.loadSubjectItemById(itemId));
		
		quizService.deleteQuiz(quizId);
	}
	
	@Test
	public void testAddDeleteSubject() {
		Quiz quiz = prepareQuiz();
		Integer quizId = quizService.createQuiz(quiz);
		assertNotNull(quizId);
		Quiz quizInDB = quizService.loadQuizById(quizId);
		assertThat(quizInDB.getQuizSubjects().size(), equalTo(1));
		QuizSubject firstSubject = quizInDB.getQuizSubjects().iterator().next();
		Integer firstSubjectId = firstSubject.getSubjectId();
		
		// test add subject 
		QuizSubject secondSubject = prepareSubject(quiz);
		quizService.addSubjectToQuiz(quizId, secondSubject);
		quizInDB = quizService.loadQuizById(quizId);
		assertThat(quizInDB.getQuizSubjects().size(), equalTo(2));
		Set<QuizSubject> subjectSet = quizInDB.getQuizSubjects();
		QuizSubject secondSubjectInDB = null;
		for (QuizSubject subject : subjectSet) {
			if (subject.getQuestion().equals("吃西餐最先动那一道?")) {
				secondSubjectInDB = subject;
				break;
			}
		}
		assertNotNull(secondSubjectInDB);
		Integer secondSubjectId = secondSubjectInDB.getSubjectId();
		assertThat(secondSubjectInDB.getSubjectOrder(), equalTo(1));
		
		// test delete subject
		quizService.deleteSubjectFromQuiz(quizId, firstSubjectId);
		quizInDB = quizService.loadQuizById(quizId);
		assertThat(quizInDB.getQuizSubjects().size(), equalTo(1));
		secondSubjectInDB = quizInDB.getQuizSubjects().iterator().next();
		assertThat(secondSubjectInDB.getSubjectId(), equalTo(secondSubjectId));
		assertThat(secondSubjectInDB.getSubjectOrder(), equalTo(0));
		
		quizService.deleteQuiz(quizId);
	}
	
	@Test
	public void testShiftItem() {
		Quiz quiz = prepareQuiz();
		Integer quizId = quizService.createQuiz(quiz);
		Quiz quizInDB = quizService.loadQuizById(quizId);
		QuizSubject subject = quizInDB.getQuizSubjects().iterator().next();
		Integer subjectId = subject.getSubjectId();
		Set<SubjectItem> itemSet = subject.getSubjectItems();
		Integer firstItemId = null;
		Integer secondItemId = null;
		for (SubjectItem item : itemSet) {
			if (item.getItemOrder().equals(1)) {
				firstItemId = item.getItemId();
			} else if (item.getItemOrder().equals(2)) {
				secondItemId = item.getItemId();
			}
		}
		// test shift up
		quizService.shiftItemUp(subjectId, secondItemId);
		SubjectItem firstItem = quizService.loadSubjectItemById(firstItemId);
		SubjectItem secondItem = quizService.loadSubjectItemById(secondItemId);
		assertThat(firstItem.getItemOrder(), is(2));
		assertThat(secondItem.getItemOrder(), is(1));
		// test shift down
		quizService.shiftItemDown(subjectId, secondItemId);
		firstItem = quizService.loadSubjectItemById(firstItemId);
		secondItem = quizService.loadSubjectItemById(secondItemId);
		assertThat(firstItem.getItemOrder(), is(1));
		assertThat(secondItem.getItemOrder(), is(2));
		
		quizService.deleteQuiz(quizId);;
	}
	
	@Test
	public void testShiftSubject() {
		Quiz quiz = prepareQuiz();
		Integer quizId = quizService.createQuiz(quiz);
		assertNotNull(quizId);
		QuizSubject secondSubject = prepareSubject(quiz);
		quizService.addSubjectToQuiz(quizId, secondSubject);
		Quiz quizInDB = quizService.loadQuizById(quizId);
		Integer secondSubjectId = null;
		for (QuizSubject subject : quizInDB.getQuizSubjects()) {
			if (subject.getQuestion().equals(secondSubject.getQuestion())) {
				assertThat(subject.getSubjectOrder(), equalTo(1));
				secondSubjectId = subject.getSubjectId();
				assertNotNull(secondSubjectId);
			} else {
				assertThat(subject.getSubjectOrder(), equalTo(0));
			}
		}
		// test shift up
		quizService.shiftSubjectUp(quizId, secondSubjectId);
		quizInDB = quizService.loadQuizById(quizId);
		for (QuizSubject subject : quizInDB.getQuizSubjects()) {
			if (subject.getQuestion().equals(secondSubject.getQuestion())) {
				assertThat(subject.getSubjectOrder(), equalTo(0));
			} else {
				assertThat(subject.getSubjectOrder(), equalTo(1));
			}
		}
		// test shift down
		quizService.shiftSubjectDown(quizId, secondSubjectId);
		quizInDB = quizService.loadQuizById(quizId);
		for (QuizSubject subject : quizInDB.getQuizSubjects()) {
			if (subject.getQuestion().equals(secondSubject.getQuestion())) {
				assertThat(subject.getSubjectOrder(), equalTo(1));
			} else {
				assertThat(subject.getSubjectOrder(), equalTo(0));
			}
		}
		
		quizService.deleteQuiz(quizId);
	}
	
	@Test
	public void testInsertItem() {
		Quiz quiz = prepareQuiz();
		Integer quizId = quizService.createQuiz(quiz);
		Quiz quizInDB = quizService.loadQuizById(quizId);
		QuizSubject subject = quizInDB.getQuizSubjects().iterator().next();
		Integer subjectId = subject.getSubjectId();
		SubjectItem newItem = prepareItem(subject);
		quizService.insertItemAt(subjectId, newItem, 1);
		QuizSubject subjectInDB = quizService.loadQuizSubjectById(subjectId);
		for (SubjectItem itemInDB : subjectInDB.getSubjectItems()) {
			if (itemInDB.getChoice().equals("碗里")) {
				assertThat(itemInDB.getItemOrder(), is(1));
				assertNotNull(itemInDB.getItemId());
			}
		}
		
		quizService.deleteQuiz(quizId);
	}
	
	@Test
	public void testInsertSubject() {
		Quiz quiz = prepareQuiz();
		Integer quizId = quizService.createQuiz(quiz);
		assertNotNull(quizId);		
		Quiz quizInDB = quizService.loadQuizById(quizId);
		QuizSubject existSubject = quizInDB.getQuizSubjects().iterator().next();
		
		QuizSubject newSubject = prepareSubject(quiz);
		quizService.insertSubjectAt(quizId, newSubject, 0);
		quizInDB = quizService.loadQuizById(quizId);
		assertThat(quizInDB.getQuizSubjects().size(), equalTo(2));
		for (QuizSubject subject : quizInDB.getQuizSubjects()) {
			if (subject.getSubjectId().equals(existSubject.getSubjectId())) {
				assertThat(subject.getSubjectOrder(), equalTo(1));
			} else {
				assertThat(subject.getSubjectOrder(), equalTo(0));
			}
		}
		
		quizService.deleteQuiz(quizId);
	}
	
	@Test
	public void testQuiz() {
		Quiz quiz = prepareQuiz();
		Integer quizId = quizService.createQuiz(quiz);
		assertNotNull(quizId);
		
		List<Quiz> quizList = quizService.loadAllQuiz();
		assertThat(quizList.size(), greaterThan(0));
		
		Quiz quizInDB = quizService.loadQuizById(quizId);
		assertThat(quizInDB.getTitle(), equalTo(quiz.getTitle()));
		
		String newTitle = "新的心理测试题";
		quiz.setTitle(newTitle);
		quizService.updateQuiz(quiz);
		quizInDB = quizService.loadQuizById(quizId);
		assertThat(quizInDB.getTitle(), equalTo(newTitle));
		
		quizService.deleteQuiz(quizId);
		assertNull(quizService.loadQuizById(quizId));
	}
	
	@Test
	public void testQuizCategory() {
		Quiz quiz = prepareQuiz();
		quiz.setCategory(100);
		Integer quizId = quizService.createQuiz(quiz);
		
		List<Quiz> quizList = quizService.getQuizByCategory(100);
		assertThat(quizList.size(), equalTo(1));
		
		quizService.deleteQuiz(quizId);
	}
	
	@Test
	public void testAddQuizCategory() {
		QuizCategory category = new QuizCategory();
		category.setCategoryName("心理学");
		category.setDescription("基本心理学常识");
		Integer categoryId = quizService.createQuizCategory(category);
		QuizCategory categoryInDB = quizService.loadQuizCategoryById(categoryId);
		assertThat(categoryInDB.getCategoryName(), equalTo("心理学"));
		quizService.deleteQuizCategory(categoryId);
	}
	
	private SubjectItem prepareItem(QuizSubject subject) {
		SubjectItem item = new SubjectItem();
		item.setQuizSubject(subject);
		item.setChoice("碗里");
		item.setScore(new Double(50));
		item.setNextSubjectId(null);
		item.setItemOrder(5);
		return item;
	}
	
	private QuizSubject prepareSubject(Quiz quiz) {
		QuizSubject subject = new QuizSubject();
		subject.setQuiz(quiz);
		subject.setType(QuizConst.TYPE_ANSWER);
		subject.setResourceId(null);
		subject.setSubjectOrder(0);
		subject.setQuestion("吃西餐最先动那一道?");
		subject.setSubjectItems(null);
		return subject;
	}
	
	private Quiz prepareQuiz() {
		Quiz quiz = new Quiz();
		quiz.setTitle("第一套心理测试题");
		quiz.setDescription("这是第一套心理测试题");
		quiz.setCategory(0);
		quiz.setNeedCharge(QuizConst.NOT_NEED_CHARGE);
		quiz.setPrice(0.0);
		quiz.setSubmitDate(new Date());
		quiz.setRepeatable(QuizConst.REPEATABLE_TRUE);
		quiz.setStatus(QuizConst.STATUS_SUBMITTED);
		Set<QuizSubject> subjects = new HashSet<>();
		quiz.setQuizSubjects(subjects);
		
		QuizSubject firstSubject = new QuizSubject();
		subjects.add(firstSubject);
		firstSubject.setQuiz(quiz);
		firstSubject.setType(QuizConst.TYPE_SINGLE_CHOICE);
		firstSubject.setResourceId(null);
		firstSubject.setSubjectOrder(0);
		firstSubject.setQuestion("一个生鸡蛋可以放在四个环境里，水，口袋，树上，土里，请问你怎么放？");
		Set<SubjectItem> options = new HashSet<>();
		firstSubject.setSubjectItems(options);
		
		SubjectItem firstOption = new SubjectItem(firstSubject, "水", Double.parseDouble("10"), null, 0);
		options.add(firstOption);
		SubjectItem secondOption = new SubjectItem(firstSubject, "口袋", Double.parseDouble("20"), null, 1);
		options.add(secondOption);
		SubjectItem thirdOption = new SubjectItem(firstSubject, "树上", Double.parseDouble("30"), null, 2);
		options.add(thirdOption);
		SubjectItem fourthOption = new SubjectItem(firstSubject, "土里", Double.parseDouble("40"), null, 3);
		options.add(fourthOption);
		
		return quiz;
	}

}
