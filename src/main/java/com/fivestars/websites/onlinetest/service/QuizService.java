package com.fivestars.websites.onlinetest.service;

import java.util.List;

import com.fivestars.websites.onlinetest.model.Quiz;
import com.fivestars.websites.onlinetest.model.QuizCategory;
import com.fivestars.websites.onlinetest.model.QuizSubject;
import com.fivestars.websites.onlinetest.model.SubjectItem;

public interface QuizService {

	/**
	 * Create a new quiz
	 * @param quiz
	 * @return the quizId of created quiz
	 */
	public Integer createQuiz(Quiz quiz);
	
	/**
	 * Update a quiz
	 * @param quiz
	 */
	public void updateQuiz(Quiz quiz);
	
	/**
	 * Load a quiz by quizId
	 * @param quizId
	 * @return
	 */
	public Quiz loadQuizById(Integer quizId);
	
	public List<Quiz> loadAllQuizLatestFirst();
	
	/**
	 * Load all available quiz
	 * @return the list of quiz
	 */
	public List<Quiz> loadAllQuiz();
	
	/**
	 * Load all available quiz
	 * @return the list of quiz
	 */
	public List<Quiz> loadAllQuizTitles();
	
	/**
	 * Delete quiz by quizId
	 * @param quizId
	 */
	public void deleteQuiz(Integer quizId);
	
	/**
	 * Add one new subject to a quiz, the order is appended to last
	 * @param quizId
	 * @param subject
	 * @return subjectId
	 */
	public Integer addSubjectToQuiz(Integer quizId, QuizSubject subject);
	
	/**
	 * Add several subjects to a quiz in bulk, the positions are appended to last.
	 * The order of new subject will be in accordance with position in list
	 * @param quizId
	 * @param subjectList
	 */
	public void addSubjectsToQuiz(Integer quizId, List<QuizSubject> subjectList);
	
	/**
	 * Delete a subject from a quiz
	 * @param quizId
	 * @param subjectId
	 */
	public void deleteSubjectFromQuiz(Integer quizId, Integer subjectId);
	
	/**
	 * Insert a subject at a specified position
	 * @param quizId
	 * @param subject
	 * @param order
	 */
	public void insertSubjectAt(Integer quizId, QuizSubject subject, int order);
	
	/**
	 * Shift a subject to a previous position
	 * @param quizId
	 * @param subjectId
	 */
	public void shiftSubjectUp(Integer quizId, Integer subjectId);
	
	/**
	 * Shift a subject to a latter position 
	 * @param quizId
	 * @param subjectId
	 */
	public void shiftSubjectDown(Integer quizId, Integer subjectId);
	
	/**
	 * Update a subject
	 * @param subject
	 */
	public void updateQuizSubject(QuizSubject subject);
	
	/**
	 * Load a subject by subjectId
	 * @param subjectId
	 */
	public QuizSubject loadQuizSubjectById(Integer subjectId);
	
	/**
	 * Load all subjects
	 * @return
	 */
	public List<QuizSubject> loadAllSubjects();
	
	/**
	 * Add one new item to a subject, the order is appended to last
	 * @param subjectId
	 * @param item
	 * @return itemId
	 */
	public Integer addItemToSubject(Integer subjectId, SubjectItem item);
	
	/**
	 * Add several items to a subject in bulk, the positions are appended to last.
	 * The order of new items will be in accordance with position in list
	 * @param quizId
	 * @param subjectSet
	 */
	public void addItemsToQuiz(Integer subjectId, List<SubjectItem> items);
	
	/**
	 * Delete an item from subject
	 * @param subjectId
	 * @param itemId
	 */
	public void deleteItemFromSubject(Integer subjectId, Integer itemId);
	
	/**
	 * Insert an item at a specified position
	 * @param subjectId
	 * @param item
	 * @param order
	 */
	public void insertItemAt(Integer subjectId, SubjectItem item, int order);
	
	/**
	 * Shift an item to a previous position
	 * @param quizId
	 * @param subjectId
	 */
	public void shiftItemUp(Integer subjectId, Integer itemId);
	
	/**
	 * Shift an item to a latter position 
	 * @param quizId
	 * @param subjectId
	 */
	public void shiftItemDown(Integer subjectId, Integer itemId);
	
	/**
	 * Load a item by itemId
	 * @param itemId
	 */
	public SubjectItem loadSubjectItemById(Integer itemId);
	
	/**
	 * Update an item
	 * @param item
	 */
	public void updateSubjectItem(SubjectItem item);
	
	/**
	 * Create a quiz category
	 * @param category
	 * @return
	 */
	public Integer createQuizCategory(QuizCategory category);
	
	/**
	 * Delete a quiz category
	 * @param categoryId
	 */
	public void deleteQuizCategory(Integer categoryId);
	
	/**
	 * Get all quiz categories
	 * @return
	 */
	public List<QuizCategory> getAllQuizCategories();
	
	/**
	 * Get category by quiz category id
	 * @param categoryId
	 * @return
	 */
	public QuizCategory getQuizCategoryById(Integer categoryId);
	
	/**
	 * Get quiz by category
	 * @param categoryId
	 * @return
	 */
	public List<Quiz> getQuizByCategory(Integer categoryId);
	
	/**
	 * Get QuizCategory by id
	 * @param quizeCategoryId
	 * @return
	 */
	public QuizCategory loadQuizCategoryById(Integer quizeCategoryId);

	/**
	 * Get All quiz size
	 * @return size number of all quiz
	 */
	Integer getAllQuizSize();

	/**
	 * Get all submitted quiz by categoryId, userName, curPageNum, pageSize
	 * @param categoryId
	 * @param userName
	 * @param curPageNum
	 * @param pageSize
	 * @return specific list of quiz
	 */
	List<Quiz> loadAllSubmittedQuiz(Integer categoryId, String userName, int curPageNum, int pageSize);

	/**
	 * Get size of list for submitted quiz for specific categoryId, userName
	 * @param CategoryId
	 * @param userName
	 * @return count of specific list
	 */
	Integer getAllSubmittedQuizSize(Integer CategoryId, String userName);
	
	/**
	 * Get all submitted quiz by category id
	 * @param categoryId
	 * @return list of submitted quiz for specific category
	 */
	Integer getAllSubmittedQuizSizeByCategoryId(Integer categoryId);

	/**
	 * Get all submitted quiz by category id
	 * @param categoryId
	 * @return list of submitted quiz for specific category
	 */
	List<Quiz> getSubmittedQuizByCategory(Integer categoryId, Integer curPageNum, Integer pageSize);

	/**
	 * 
	 * @param quizIdList
	 * @return
	 */
	List<Quiz> loadQuizzesByIds(List<Integer> quizIdList);
}
