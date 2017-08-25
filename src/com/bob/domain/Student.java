package com.bob.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Represents a Student enrolled in the college management
 * system (CMS)
 */
@Entity
public class Student
{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(unique=true, nullable=false)
    private String enrollmentID;
    private String name;
   
    @ManyToOne
    @JoinColumn(name="TUTOR_FK")
    private Tutor supervisor; 
    
    @ManyToMany
    private Set<Subject> subjects = new HashSet<Subject>();
   
    /*
     * Hibernate needs this.
     */
    public Student() {
    	
    }
    /**
     * Initialises a student with a particular tutor
     */
    public Student(String name, Tutor tutor)
    {
    	this.name = name;
    	this.supervisor = tutor;
    }
    
    /**
     * Initialises a student with no pre set tutor
     */
    public Student(String name)
    {
    	this.name = name;
//    	this.supervisor  = null;
    }
    
    public Student(String name, Set<Subject> subjects)
    {
    	this.name = name;
    	this.subjects=subjects;
    }
    
    public Student(String name, String enrollmentID)
    {
    	this.name = name;
    	this.enrollmentID=enrollmentID;
    	this.supervisor  = null;
    }
    
    @Override
	public String toString() {
    	String subjects = " SUBJECTS: [" + this.getSubjects().toString() + " ]";
		return "Student [  " + enrollmentID + ", " + name + " ]" 
    			+ " Tutor is: " + "[ "+ supervisor.getName() + "  ]"
    			+ subjects;
	//	return "Student [enrollmentID=" + enrollmentID + ", name=" + name  + "]";
	}
	public double calculateGradePointAverage()
    {
    	// some complex business logic!
    	// we won't need this method on the course, BUT it is import
    	// to remember that classes aren't just get/set pairs - we expect
    	// business logic in here as well.
    	return 0;
    }
	
	/*
	 * Putting a @Id annotation on a method would 
	 * tell hibernate to use property access rather than field(via reflection).
	 * These two techniques cannot be mixed within a class.
	 * A single annotation will INCLUDE ALL! the other accessors as hibernate's 
	 * default access method
	 */
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEnrollmentID() {
		return enrollmentID;
	}
	public void setEnrollmentID(String enrollmentID) {
		this.enrollmentID = enrollmentID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name.toUpperCase();
	}
	public void allocateSupervisor(Tutor newSupervisor) {
		this.supervisor=newSupervisor;
		newSupervisor.getModifiableSupervisionGroup().add(this); // maintain bi-directional relationship.
		
	}
	public Tutor getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(Tutor supervisor) {
		this.supervisor = supervisor;
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
		result = prime * result + ((enrollmentID == null) ? 0 : enrollmentID.hashCode());
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
		Student other = (Student) obj;
		if (enrollmentID == null) {
			if (other.enrollmentID != null)
				return false;
		} else if (!enrollmentID.equals(other.enrollmentID))
			return false;
		return true;
	}
	
	
	
}
