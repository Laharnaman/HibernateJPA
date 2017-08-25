package com.bob.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Subject {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(unique=true, nullable=false)
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subject other = (Subject) obj;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}
	
	
	
}
