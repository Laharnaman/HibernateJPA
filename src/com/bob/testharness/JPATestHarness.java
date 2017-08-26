package com.bob.testharness;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.bob.domain.Student;
import com.bob.domain.Subject;
import com.bob.domain.Tutor;
import static com.bob.utils.Utils.*;

public class JPATestHarness {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA_MYSQL_CONFIG");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		
	
	//	testDBRetrieval(session);
			
		// TEST OUTPUT from DB

		
		
		// START NEW DATA GENERATION and test
			
				Set<Subject> subjects = generateSampleSetOfSubjects();
				Map<String, Subject> subjectMap = generateSampleMapOfSubjects(); //might already be in DB - check!
				for (Subject s : subjectMap.values()) {
					em.persist(s);
				}
				
				Set<Student> students = generateSampleSetOfStudents(subjectMap);
				for (Student s : students) {
					em.persist(s);
				}	
				
				Set<Tutor> tutors = generateSampleSetOfTutors(subjectMap);
				
				for (Tutor t : tutors) {
					em.persist(t);
				}
				
				tutors = assignStudentsToTutors(students, tutors);
				
	/*			System.out.println("========= PERSIST FINISHED============================================================");
				System.out.println(tutors);
				System.out.println("=====================================================================");
				System.out.println(students);
				System.out.println("=====================================================================");
				System.out.println(subjects);*/
				// SAVE
				System.out.println("==========START RETRIEVAL===========================================================");
	/*	List<Tutor> allTutors = new ArrayList<Tutor>();
		Query allTutorsQuery = em.createQuery("SELECT t FROM Tutor t");
			
		allTutors = allTutorsQuery.getResultList();
		System.out.println(allTutors);*/
				testDBRetrieval(em);
	
				tx.commit();
				em.close();
		// END SAVE
				
				// redundant since update to addStudentsToSupervisionGroup
				//students = assignTutorsToStudents(tutors,students);
				
				// TEST output from above generation step
/*					Consumer<Tutor> printTutorsAndTheirSupervisionGroups = t -> {
						System.out.println("\nTutor is:>>"+t.getName());
						Set<Student> studentMap = t.getSupervisionGroup();
						studentMap.forEach(s -> System.out.println(s));
						Set<Subject> subjects2 = t.getSubjects();
						subjects2.forEach(System.out::println);
					};
					Consumer<Student> printStudentsAndTheirAllocatedTutors = s -> {
						System.out.println(s);
					};
					
					tutors
					.stream()
					.forEach(printTutorsAndTheirSupervisionGroups);	
					System.out.println();
					students
					.stream()
					.forEach(printStudentsAndTheirAllocatedTutors);*/
				// END TEST
		// END NEW DATA GENERATION
	

		
				
				
//		tx.commit();
//		session.close();
	} // ============== end of Main





	private static void testDBRetrieval(EntityManager em) {
		Consumer<Tutor> printTutorsAndStudents = t -> {
			System.out.println("\nTutor : "+ t);
			Set<Student> studentMap = t.getSupervisionGroup();
			studentMap.forEach(s -> System.out.println(s));
		};
		Consumer<Student> printStudentsAndTheirAllocatedTutors = s -> {
			System.out.println(s);
		};
		
				
		List<Tutor> allTutors = new ArrayList<Tutor>();
		Query allTutorsQuery = em.createQuery("SELECT e FROM Tutor e");
			
		allTutors = allTutorsQuery.getResultList();
		
		System.out.println("TUTORS =============================");
		allTutors
		.stream()
		.forEach(printTutorsAndStudents);
		
		System.out.println("STUDENTS =============================");
		List<Student> studentListFromDB = new ArrayList<Student>();
		Query allStudents = em.createQuery("SELECT e FROM Student e");
		studentListFromDB = allStudents.getResultList();
		
		studentListFromDB
		.stream()
		.forEach(printStudentsAndTheirAllocatedTutors);
	}

}//end of testharness
