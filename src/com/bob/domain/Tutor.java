package com.bob.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

@Entity
public class Tutor {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String staffId;
	private String name;
	private int salary;
	
	@OneToMany
	@MapKey(name="enrollmentID")
	@JoinColumn(name="TUTOR_FK")
	//private Set<Student> supervisionGroup;
	private Map<String, Student> supervisionGroup;
	
	
	Tutor() {}

	//business constructor
	public Tutor(String staffId, String name, int salary) {
		super();
		this.staffId = staffId;
		this.name = name;
		this.salary = salary;
		this.supervisionGroup = new HashMap<String, Student>(); 
	}

	public Tutor(String name) {
		this.name=name;
		this.supervisionGroup = new HashMap<String, Student>();
	}

	public Tutor(String staffId, String name) {
		this.staffId=staffId;
		this.name=name;
	}

	public void addStudentsToSupervisionGroup(Student s){
		this.supervisionGroup.put(s.getEnrollmentID(), s);
	}
	
	public Map<String, Student> getSupervisionGroup(){
		return Collections.unmodifiableMap(this.supervisionGroup); //avoids side-effects
		//return this.supervisionGroup;
	}
	@Override
	public String toString() {
		String str = "\n\tSTUDENTS: " + this.getSupervisionGroup().toString();
		return "Tutor [id=" + id + ", staffId=" + staffId + ", name=" + name + ", salary=" + salary + "]" +str +"\n";
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

	public void setSupervisionGroup(Map<String, Student> supervisionGroup) {
		this.supervisionGroup = supervisionGroup;
	}
	
	
	
}
