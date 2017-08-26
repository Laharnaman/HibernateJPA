package com.bob.testharness;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import javax.persistence.CascadeType;
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
		

		// START NEW DATA GENERATION and test
			
				Set<Subject> subjects = generateSampleSetOfSubjects();
				Map<String, Subject> subjectMap = generateSampleMapOfSubjects(); //might already be in DB - check!
		
				/*	Not needed if using cascade=CascadeType.PERSIST	
				 * 	for (Subject s : subjectMap.values()) {
					em.persist(s);
				}*/
				
				Set<Student> students = generateSampleSetOfStudents(subjectMap);
		/*		Not needed if using cascade=CascadeType.PERSIST 
		 * 		for (Student s : students) {
					em.persist(s);
				}	*/
				
				Set<Tutor> tutors = generateSampleSetOfTutors(subjectMap);
				tutors = assignStudentsToTutors(students, tutors);
				
				for (Tutor t : tutors) {
					em.persist(t);
				}
				
	
				testDBRetrieval(em);
	
				tx.commit();
				em.close();
		// END SAVE
				

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
		
		System.out.println("\n\nSTUDENTS =============================");
		List<Student> studentListFromDB = new ArrayList<Student>();
		Query allStudents = em.createQuery("SELECT e FROM Student e");
		studentListFromDB = allStudents.getResultList();
		
		studentListFromDB
		.stream()
		.forEach(printStudentsAndTheirAllocatedTutors);
		
		System.out.println("\n\nSUBJECTS =============================");
		List<Subject> subjects = new ArrayList<Subject>();
		Query allSubjects = em.createQuery("SELECT e FROM Subject e");
		subjects = allSubjects.getResultList();
		
		subjects
		.stream()
		.forEach(s -> System.out.println(s));
		
	}

}//end of testharness
