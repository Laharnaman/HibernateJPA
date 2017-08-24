package com.bob.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.bob.domain.Student;
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

	public static Set<Student> generateSampleSetOfStudents() {
		Set<Student> students = new HashSet<Student>();
		students.add(new Student("Aaron McCready"));
		students.add(new Student("Bob Kelly"));
		students.add(new Student("Erinn Kelly"));
		students.add(new Student("Emma Kelly"));
		students.add(new Student("Karen McCready"));
		students.add(new Student("Kristin Millar"));
		students.add(new Student("Ryan Millar"));
		students.add(new Student("Joy Millar"));
		int i = 1;
		for (Student s : students) {
			s.setEnrollmentID(s.getName().substring(0,3).toUpperCase()+"-" + (i++) + "-2017");
		}
		return students;

	}

	public static Set<Tutor> generateSampleSetOfTutors() {
		int min = 50000;
		int max = 90000;
		Random r = new Random();

		Set<Tutor> tutors = new HashSet<Tutor>();
		tutors.add(new Tutor("Richard Feynman"));
		tutors.add(new Tutor("Randell Mills"));
		tutors.add(new Tutor("Werner Heisenberg"));

		int i = 0;

		for (Tutor t : tutors) {
			t.setStaffId("SID-" + (i++));
			t.setSalary(r.nextInt((max - min) + 1) + min);
			;
		}

		return tutors;
	}

}
