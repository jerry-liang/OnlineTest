package com.fivestars.websites.onlinetest.model;
// Generated Jan 15, 2016 12:20:03 AM by Hibernate Tools 4.3.1.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * QuizSubject generated by hbm2java
 */
@Entity
@Table(name = "quiz_subject", catalog = "online_test")
public class QuizSubject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer subjectId;
	private Quiz quiz;
	private Integer type;
	private Integer resourceId;
	private Integer order;
	private String question;
	private List<SubjectItem> subjectItems = new ArrayList<>();

	public QuizSubject() {
	}

	public QuizSubject(Quiz quiz, Integer type) {
		this.quiz = quiz;
		this.type = type;
	}

	public QuizSubject(Quiz quiz, Integer type, Integer resourceId, Integer order, String question,
			List<SubjectItem> subjectItems) {
		this.quiz = quiz;
		this.type = type;
		this.resourceId = resourceId;
		this.order = order;
		this.question = question;
		this.subjectItems = subjectItems;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "subjectId", unique = true, nullable = false)
	public Integer getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "quizId", nullable = false)
	public Quiz getQuiz() {
		return this.quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "resourceId")
	public Integer getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name = "order")
	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@Column(name = "question", length = 65535)
	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "quizSubject")
	public List<SubjectItem> getSubjectItems() {
		return this.subjectItems;
	}

	public void setSubjectItems(List<SubjectItem> subjectItems) {
		this.subjectItems = subjectItems;
	}

}
