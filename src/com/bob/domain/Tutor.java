package com.bob.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

@Entity
public class Tutor {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(unique=true, nullable=false)
	private String staffId;
	private String name;
	private int salary;
	
	// BI-DIRECTIONAL RELATIONSHIP SETUP
	// 'mappedB' should really read: 'alreadyMappedBy'
	//	It connects to the ManyToOne property on the Student table (basically saying they are the SAME relationship
	
	@OneToMany(mappedBy="supervisor", cascade=CascadeType.PERSIST) 
	private Set<Student> supervisionGroup = new HashSet<Student>();
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	private Set<Subject> subjects;
	
	
	
	Tutor() {}

	//business constructor
		public Tutor(String staffId, String name, int salary) {
			super();
			this.staffId = staffId;
			this.name = name;
			this.salary = salary;
			this.supervisionGroup = new HashSet<Student>(); 
			this.subjects = new HashSet<Subject>();
		}
		
		
	//business constructor
	public Tutor(String staffId, String name, int salary, Set<Subject> subjects) {
		super();
		this.staffId = staffId;
		this.name = name;
		this.salary = salary;
		this.supervisionGroup = new HashSet<Student>(); 
		this.subjects = subjects;
	}

	public Tutor(String name) {
		this.name=name;
		this.supervisionGroup = new HashSet<Student>();
		this.subjects = new HashSet<Subject>();
	}

	public Tutor(String name, Set<Subject> subjects) {
		this.name=name;
		this.subjects = subjects;
		
	}
	public Tutor(String staffId, String name) {
		this.staffId=staffId;
		this.name=name;
	}

	public void addStudentsToSupervisionGroup(Student s){
		this.supervisionGroup.add(s);
		s.allocateSupervisor(this); // shortcut to implementing a more robust bi-directional relationship
	}
	
	public Set<Student> getSupervisionGroup(){
		return Collections.unmodifiableSet(this.supervisionGroup); //avoids side-effects
		//return this.supervisionGroup;
	}
	
	public Set<Student> getModifiableSupervisionGroup(){
		return this.supervisionGroup; 
	}
	
	@Override
	public String toString() {
		String students =  this.getSupervisionGroup().toString();
		String subjects =  this.getSubjects().toString();
				
		return name + "(" +staffId + ") " + salary 
				+"\nTUTOR'S STUDENTS: " + students
				+"\nTUTOR'S SUBJECTS: " + subjects+ "\n";
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSupervisionGroup(Set<Student> supervisionGroup) {
		this.supervisionGroup = supervisionGroup;
	}

	public Set<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((staffId == null) ? 0 : staffId.hashCode());
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
		Tutor other = (Tutor) obj;
		if (staffId == null) {
			if (other.staffId != null)
				return false;
		} else if (!staffId.equals(other.staffId))
			return false;
		return true;
	}
	
	
	
}
