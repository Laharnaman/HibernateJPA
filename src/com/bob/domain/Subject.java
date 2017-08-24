package com.bob.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Subject {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String subject;
	private int noOfSemesters;
	
	public Subject() {}
	
	public Subject(String subject, int noOfSemesters) {
		super();
		this.subject = subject;
		this.noOfSemesters = noOfSemesters;
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getNoOfSemesters() {
		return noOfSemesters;
	}

	public void setNoOfSemesters(int noOfSemesters) {
		this.noOfSemesters = noOfSemesters;
	}

	@Override
	public String toString() {
		return "Subject [subject=" + subject + ", noOfSemesters=" + noOfSemesters + "]";
	}
	
	
	
}
