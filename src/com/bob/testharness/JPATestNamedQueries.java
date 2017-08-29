package com.bob.testharness;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.bob.domain.Student;
import com.bob.domain.Subject;
import com.bob.domain.Tutor;

public class JPATestNamedQueries {
	public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA_MYSQL_CONFIG");
	
	public static void main(String[] args) {
		setUpData();
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		//List<Student> results = 
	/*			em.createNamedQuery("searchByName")
				.setParameter("name", "Marco Fortes")
				.getResultList()
				.forEach( s -> System.out.println(s.toString()));*/

/*				List<String> results2 = 
						em.createNamedQuery("findAllStudentNamesByStudent")
						.getResultList();
				results2.stream().forEach(System.out::println);*/
				
/*				List<Object[]> results2 = 
						em.createNamedQuery("findAllStudentNamesAndEnrollmentIDs")
						.getResultList();
				results2.stream().forEach(o -> System.out.println(o[0] + "--" + o[1]));*/
				
//				
//				long count = (long) em.createQuery("select count(student) from Student as student").getSingleResult();
//				System.out.println(count);
//				double avg = (double) em.createQuery("select avg(subject.numberOfSemesters) from Subject as subject").getSingleResult();
//				System.out.println(avg);
				
				
		// This is very memory intensive and generates a lot of unnecessary sql
/*				List<Tutor> tutors = em.createQuery("from Tutor as tutor").getResultList();
				tutors.stream().forEach(t -> t.setSalary(t.getSalary()*2));
				tutors.stream().forEach(System.out::println);*/
				
		// Better is to use hibernate's bulk update		
/*				 int _executeUpdate = em.createQuery("update Tutor as tutor set tutor.salary = tutor.salary * 2").executeUpdate();
				System.out.println(_executeUpdate != -1 ? "success: rows updated "+_executeUpdate:"failed");*/
				
		// Using native queries (JDBC!). Problem is that this contains primitives - see below for auto object creation.
	/*	List<Object[]> results = em.createNativeQuery("select s.name, s.enrollmentid from student s").getResultList();
		results.stream().forEach(s -> System.out.println(s[0] +"--"+s[1]));*/
		
		
		List<Student> results = em.createNativeQuery("select * from student s", Student.class).getResultList();
		results.stream().forEach(s -> System.out.println(s.getName() + " -- "+s.getEnrollmentID()));
		
		tx.commit();
		em.close();

	} // ============== end of Main

	public static void setUpData()
	{ 
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		// Some subjects
		Subject mathematics = new Subject("Mathematics", 2);
		Subject science = new Subject("Science", 2);
		Subject history = new Subject("History", 3);
		em.persist(mathematics);
		em.persist(science);
		em.persist(history);

		// This tutor will be very busy, with lots of students
		Tutor t1 = new Tutor("ABC123", "David Banks", 10000);
		t1.addSubjectToQualifications(mathematics);
		t1.addSubjectToQualifications(science);
		
		// This tutor is new and has no students
		// But he will be able to teach science and mathematics
		Tutor t2 = new Tutor("DEF456", "Alan Bridges", 20000);
		t2.addSubjectToQualifications(mathematics);
		t2.addSubjectToQualifications(science);
		
		// This tutor is the only tutor who can teach History
		Tutor t3 = new Tutor("GHI678", "Linda Histroia", 40000);
		t3.addSubjectToQualifications(history);
		t3.addSubjectToQualifications(science);
		em.persist(t1);
		em.persist(t2);
		em.persist(t3);

		// this only works because we are cascading from tutor to student
		t1.createStudentAndAddToSupervisionGroup("Marco Fortes", "1-FOR-2010", "1 The Street", "Georgia", "484848");
		t1.createStudentAndAddToSupervisionGroup("Kath Grainer", "2-GRA-2009", "2 Kaths Street", "Georgia", "939393");
		t3.createStudentAndAddToSupervisionGroup("Sandra Perkins", "3-PER-2009", "4 The Avenue", "Georgia", "939393");
		
		tx.commit();
		em.close();
	}



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
