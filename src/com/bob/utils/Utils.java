package com.bob.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.bob.domain.Address;
import com.bob.domain.Student;
import com.bob.domain.Subject;
import com.bob.domain.Tutor;

public class Utils {

	public static Set<Tutor> assignStudentsToTutors(Set<Student> students, Set<Tutor> tutors) {
		Optional<Tutor> tutor_heisenberg = tutors.stream().filter(t -> t.getName().toLowerCase().contains("heisenberg"))
				.findFirst();
		List<Student> kellys = students.stream().filter(s -> s.getName().toLowerCase().contains("kelly"))
				.collect(Collectors.toList());

		Optional<Tutor> tutor_feynman = tutors.stream().filter(t -> t.getName().toLowerCase().contains("feynman"))
				.findFirst();
		List<Student> mccreadys = students.stream().filter(s -> s.getName().toLowerCase().contains("mccready"))
				.collect(Collectors.toList());

		Optional<Tutor> tutor_mills = tutors.stream().filter(t -> t.getName().toLowerCase().contains("mills"))
				.findFirst();
		
		Optional<Tutor> tutor_wigner = tutors.stream().filter(t -> t.getName().toLowerCase().contains("wigner"))
				.findFirst();
		
		
		List<Student> millars = students.stream().filter(s -> s.getName().toLowerCase().contains("millar"))
				.collect(Collectors.toList());

		for (Student s : kellys) {
			if(s.getName().toLowerCase().contains("bob")) {
				tutor_wigner.get().addStudentsToSupervisionGroup(s);
			} else {
				tutor_heisenberg.get().addStudentsToSupervisionGroup(s);
			}
		}
		for (Student s : mccreadys) {
			if(s.getName().toLowerCase().contains("karen")) {
				tutor_wigner.get().addStudentsToSupervisionGroup(s);
			} else {
				tutor_feynman.get().addStudentsToSupervisionGroup(s);
			}
		}
		for (Student s : millars) {
			tutor_mills.get().addStudentsToSupervisionGroup(s);
		}

		Set<Tutor> updatedTutorList = new HashSet<Tutor>();
		updatedTutorList.add(tutor_mills.get());
		updatedTutorList.add(tutor_feynman.get());
		updatedTutorList.add(tutor_heisenberg.get());
		updatedTutorList.add(tutor_wigner.get());

		return updatedTutorList;

	}
	
	public static Set<Student> assignTutorsToStudents(Set<Tutor> tutors, Set<Student> students) {
		//Tutors by name
		Optional<Tutor> tutor_heisenberg = tutors.stream().filter(t -> t.getName().toLowerCase().contains("heisenberg"))
				.findFirst();

		Optional<Tutor> tutor_feynman = tutors.stream().filter(t -> t.getName().toLowerCase().contains("feynman"))
				.findFirst();

		Optional<Tutor> tutor_mills = tutors.stream().filter(t -> t.getName().toLowerCase().contains("mills"))
				.findFirst();
		
		Optional<Tutor> tutor_wigner = tutors.stream().filter(t -> t.getName().toLowerCase().contains("wigner"))
				.findFirst();
		
		// Students by name
		List<Student> millars = students.stream().filter(s -> s.getName().toLowerCase().contains("millar"))
				.collect(Collectors.toList());

		List<Student> kellys = students.stream().filter(s -> s.getName().toLowerCase().contains("kelly"))
				.collect(Collectors.toList());
		List<Student> mccreadys = students.stream().filter(s -> s.getName().toLowerCase().contains("mccready"))
				.collect(Collectors.toList());
		
		//Assign a specific tutor to each student with the name 'kelly'
		for (Student s : kellys) {
			
			if(s.getName().toLowerCase().contains("bob")) {
				s.allocateSupervisor(tutor_wigner.get());
			} else {
				s.allocateSupervisor(tutor_heisenberg.get());
			}
		}
		for (Student s : mccreadys) {
			
			if(s.getName().toLowerCase().contains("karen")) {
				s.allocateSupervisor(tutor_wigner.get());
			} else {
				s.allocateSupervisor(tutor_feynman.get());
			}
		}
		for (Student s : millars) {
			s.allocateSupervisor(tutor_mills.get());
		}
		
		//Now add all the 3 student lists together.
		Set<Student> updatedStudentList = new HashSet<Student>();
		updatedStudentList.addAll(kellys);
		updatedStudentList.addAll(mccreadys);
		updatedStudentList.addAll(millars);
		
		return updatedStudentList;
		
	}

	public static Set<Student> generateSampleSetOfStudents(Map<String,Subject> subjects) {
		int minEID=2000;
		int maxEID=5000;
		Random r = new Random();
		Set<Student> students = new HashSet<Student>();
		
		Set<Subject> kelly_heisenberg_subjects = new HashSet<Subject>();
		Set<Subject> millar_mills_subjects = new HashSet<Subject>();
		Set<Subject> mcready_feynman_subjects = new HashSet<Subject>();
		Set<Subject> wigner_subjects = new HashSet<Subject>();
		
		kelly_heisenberg_subjects.add(subjects.get("QUA"));
		kelly_heisenberg_subjects.add(subjects.get("MAT"));
		millar_mills_subjects.add(subjects.get("GUT"));
		millar_mills_subjects.add(subjects.get("COS"));
		mcready_feynman_subjects.add(subjects.get("MHL"));
		wigner_subjects.add(subjects.get("STA"));
		
		students.add(new Student("EID-" + createRandom(minEID, maxEID, r),  "Aaron McCready", 	mcready_feynman_subjects));
		students.add(new Student("EID-" + createRandom(minEID, maxEID, r),	"Bob Kelly", 		wigner_subjects));
		students.add(new Student("EID-" + createRandom(minEID, maxEID, r),	"Erinn Kelly",		kelly_heisenberg_subjects));
		students.add(new Student("EID-" + createRandom(minEID, maxEID, r),	"Emma Kelly",		kelly_heisenberg_subjects));
		students.add(new Student("EID-" + createRandom(minEID, maxEID, r),	"Karen McCready",	wigner_subjects));
		students.add(new Student("EID-" + createRandom(minEID, maxEID, r),	"Kristin Millar",	millar_mills_subjects));
		students.add(new Student("EID-" + createRandom(minEID, maxEID, r),	"Ryan Millar",		millar_mills_subjects));
		students.add(new Student("EID-" + createRandom(minEID, maxEID, r),	"Joy Millar",		millar_mills_subjects));
		
		/*int i = 1;
		for (Student s : students) {
			s.setEnrollmentID(s.getName().substring(0,3).toUpperCase()+"-" + (i++) + "-2017");
		}*/
		return students;

	}

	private static int createRandom(int minEID, int maxEID, Random r) {
		return r.nextInt((maxEID - minEID) + 1) + minEID;
	}

	public static Set<Tutor> generateSampleSetOfTutors(Map<String,Subject> subjects) {
		int min = 50000;
		int max = 90000;
		int minStaffId=100;
		int maxStaffId=199;
		Random r = new Random();
		//Map<String,Subject> subjects = generateSampleMapOfSubjects();
		Set<Subject> feyman_subjects = new HashSet<Subject>();
		Set<Subject> mills_subjects = new HashSet<Subject>();
		Set<Subject> heisenberg_subjects = new HashSet<Subject>();
		Set<Subject> wigner_subjects = new HashSet<Subject>();
		
		feyman_subjects.add(subjects.get("QED"));
		feyman_subjects.add(subjects.get("MHL"));
		
		mills_subjects.add(subjects.get("GUT"));
		mills_subjects.add(subjects.get("ELE"));
		mills_subjects.add(subjects.get("COS"));
		
		heisenberg_subjects.add(subjects.get("QUA"));
		heisenberg_subjects.add(subjects.get("MAT"));
		
		wigner_subjects.add(subjects.get("STA"));
		wigner_subjects.add(subjects.get("COS"));
		wigner_subjects.add(subjects.get("QUA"));
		Set<Tutor> tutors = new HashSet<Tutor>();
	
//use constructor public Tutor(String staffId, String name, int salary, Set<Subject> subjects) {
		
		tutors.add(new Tutor("SID-" + createRandom(minStaffId, maxStaffId, r),	"Richard Feynman",		createRandom(min, max, r),	feyman_subjects));
		tutors.add(new Tutor("SID-" + createRandom(minStaffId, maxStaffId, r),	"Randell Mills",		createRandom(min, max, r),	mills_subjects));
		tutors.add(new Tutor("SID-" + createRandom(minStaffId, maxStaffId, r),	"Werner Heisenberg",	createRandom(min, max, r),	heisenberg_subjects));
		tutors.add(new Tutor("SID-" + createRandom(minStaffId, maxStaffId, r),	"Eugene Wigner",		createRandom(min, max, r),	wigner_subjects));
		
		return tutors;
	}

	
	public static Tutor createTestTutor() {
		Map<String,Subject> subjects = generateSampleMapOfSubjects();
		Set<Subject> selectedSubjects = subjects
						.values()
						.stream()
						.filter(sub -> sub.getNoOfSemesters() == 1)
						.collect(Collectors.toSet());
		Tutor result  = new Tutor("EXTID-" 
						+ createRandom(1000, 2000, new Random()),	
						"Louis De Broglie-"+createRandom(0, 2000, new Random()) ,		
						createRandom(100000, 200000, new Random()),	
						selectedSubjects);
		return result;
	}
	
	public static Map<String, Subject> generateSampleMapOfSubjects() {
		Map<String,Subject> subjectMap = new HashMap<String,Subject>();
		
		subjectMap.put("QED", new Subject("Quantum Electrodynamics","QED", 3 ));
		subjectMap.put("MHL", new Subject("Mechanics, Heat and Light", "MHL", 1 ));
		subjectMap.put("GUT", new Subject("GUT-CP", "GUT",3 ));
		subjectMap.put("ELE", new Subject("Electromagnetism", "ELE",2 ));
		subjectMap.put("COS", new Subject("Cosmology", "COS",3 ));
		subjectMap.put("QUA", new Subject("Quantum Mechanics", "QUA", 1 ));
		subjectMap.put("MAT", new Subject("Matrix Mechanics",  "MAT", 3 ));
		subjectMap.put("STA", new Subject("Statistical Mechanics", "STA", 3 ));
		
		return subjectMap;
	}
	
	public static Set<Subject> generateSampleSetOfSubjects() {
		Set<Subject> set = new HashSet<Subject>();
		
		set.add(generateSampleMapOfSubjects().get("QED"));
		set.add(generateSampleMapOfSubjects().get("MHL"));
		set.add(generateSampleMapOfSubjects().get("GUT"));
		set.add(generateSampleMapOfSubjects().get("ELE"));
		set.add(generateSampleMapOfSubjects().get("COS"));
		set.add(generateSampleMapOfSubjects().get("QUA"));
		set.add(generateSampleMapOfSubjects().get("MAT"));
		set.add(generateSampleMapOfSubjects().get("STA"));
		
		return set;
	}

	public static Student createFullTestStudent() {
		Map<String,Subject> subjects = generateSampleMapOfSubjects();
//		Set<Subject> selectedSubjects = subjects
//						.values()
//						.stream()
//						.filter(sub -> sub.getNoOfSemesters() == 3)
//						.collect(Collectors.toSet());
		Student s = new Student("EID-" + createRandom(100, 199, new Random()),  
							"Raymond Kelly-"+createRandom(100, 199, new Random()));
		s.setAddress(new Address( createRandom(50, 199, new Random()) + " Mundenheimerstr", "Ludwigshafen", "67123"));
	
		return s;
	}
	

	public static Student createTestStudentWithAddress() {
		
		Student s = new Student("EID-" + createRandom(100, 199, new Random()),  
							"Patrick Kelly-"+createRandom(100, 199, new Random()));
		s.setAddress(new Address( createRandom(50, 199, new Random()) + " Maxstr", "Ludwigshafen", "67000"));
	
		return s;
	}
	
public static Student createTestStudentWithEIDAndName() {
		
		Student s = new Student("EID-" + createRandom(100, 199, new Random()),  
							"Patrick Kelly-"+createRandom(100, 199, new Random()));
	
		return s;
	}
	
}
