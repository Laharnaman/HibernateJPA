package com.bob.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

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
		List<Student> millars = students.stream().filter(s -> s.getName().toLowerCase().contains("millar"))
				.collect(Collectors.toList());

		for (Student s : kellys) {
			tutor_heisenberg.get().addStudentsToSupervisionGroup(s);
		}
		for (Student s : mccreadys) {
			tutor_feynman.get().addStudentsToSupervisionGroup(s);
		}
		for (Student s : millars) {
			tutor_mills.get().addStudentsToSupervisionGroup(s);
		}

		Set<Tutor> updatedTutorList = new HashSet<Tutor>();
		updatedTutorList.add(tutor_mills.get());
		updatedTutorList.add(tutor_feynman.get());
		updatedTutorList.add(tutor_heisenberg.get());

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
		
		// Students by name
		List<Student> millars = students.stream().filter(s -> s.getName().toLowerCase().contains("millar"))
				.collect(Collectors.toList());

		List<Student> kellys = students.stream().filter(s -> s.getName().toLowerCase().contains("kelly"))
				.collect(Collectors.toList());
		List<Student> mccreadys = students.stream().filter(s -> s.getName().toLowerCase().contains("mccready"))
				.collect(Collectors.toList());
		
		//Assign a specific tutor to each student with the name 'kelly'
		for (Student s : kellys) {
			s.allocateSupervisor(tutor_heisenberg.get());
		}
		for (Student s : mccreadys) {
			s.allocateSupervisor(tutor_feynman.get());
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
		Set<Student> students = new HashSet<Student>();
		
		Set<Subject> kelly_heisenberg_subjects = new HashSet<Subject>();
		Set<Subject> millar_mills_subjects = new HashSet<Subject>();
		Set<Subject> mcready_feynman_subjects = new HashSet<Subject>();
		
		kelly_heisenberg_subjects.add(subjects.get("QUA"));
		kelly_heisenberg_subjects.add(subjects.get("MAT"));
		millar_mills_subjects.add(subjects.get("GUT"));
		millar_mills_subjects.add(subjects.get("COS"));
		mcready_feynman_subjects.add(subjects.get("MHL"));
		
		students.add(new Student("Aaron McCready", mcready_feynman_subjects));
		students.add(new Student("Bob Kelly", kelly_heisenberg_subjects));
		students.add(new Student("Erinn Kelly",kelly_heisenberg_subjects));
		students.add(new Student("Emma Kelly",kelly_heisenberg_subjects));
		students.add(new Student("Karen McCready",mcready_feynman_subjects));
		students.add(new Student("Kristin Millar",millar_mills_subjects));
		students.add(new Student("Ryan Millar",millar_mills_subjects));
		students.add(new Student("Joy Millar",millar_mills_subjects));
		
		int i = 1;
		for (Student s : students) {
			s.setEnrollmentID(s.getName().substring(0,3).toUpperCase()+"-" + (i++) + "-2017");
		}
		return students;

	}

	public static Set<Tutor> generateSampleSetOfTutors(Map<String,Subject> subjects) {
		int min = 50000;
		int max = 90000;
		Random r = new Random();
		//Map<String,Subject> subjects = generateSampleMapOfSubjects();
		Set<Subject> feyman_subjects = new HashSet<Subject>();
		Set<Subject> mills_subjects = new HashSet<Subject>();
		Set<Subject> heisenberg_subjects = new HashSet<Subject>();
		
		feyman_subjects.add(subjects.get("QED"));
		feyman_subjects.add(subjects.get("MHL"));
		
		mills_subjects.add(subjects.get("GUT"));
		mills_subjects.add(subjects.get("ELE"));
		mills_subjects.add(subjects.get("COS"));
		
		heisenberg_subjects.add(subjects.get("QUA"));
		heisenberg_subjects.add(subjects.get("MAT"));
		
		Set<Tutor> tutors = new HashSet<Tutor>();
		tutors.add(new Tutor("Richard Feynman", feyman_subjects ));
		tutors.add(new Tutor("Randell Mills", mills_subjects));
		tutors.add(new Tutor("Werner Heisenberg", heisenberg_subjects));

		int i = 0;

		for (Tutor t : tutors) {
			t.setStaffId("SID-" + (i++));
			t.setSalary(r.nextInt((max - min) + 1) + min);
			
		}

		return tutors;
	}

	public static Map<String, Subject> generateSampleMapOfSubjects() {
		Map<String,Subject> subjectMap = new HashMap<String,Subject>();
		
		subjectMap.put("QED", new Subject("Quantum Electrodynamics", 3 ));
		subjectMap.put("MHL", new Subject("Mechanics, Heat and Light", 1 ));
		subjectMap.put("GUT", new Subject("GUT-CP", 3 ));
		subjectMap.put("ELE", new Subject("Electromagnetism", 2 ));
		subjectMap.put("COS", new Subject("Cosmology", 3 ));
		subjectMap.put("QUA", new Subject("Quantum Mechanics", 3 ));
		subjectMap.put("MAT", new Subject("Matrix Mechanics", 3 ));
		
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
		
		return set;
	}
	

	
}
