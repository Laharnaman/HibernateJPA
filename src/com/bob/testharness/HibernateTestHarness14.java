package com.bob.testharness;

import static com.bob.utils.Utils.assignStudentsToTutors;
import static com.bob.utils.Utils.generateSampleSetOfStudents;
import static com.bob.utils.Utils.generateSampleSetOfTutors;
import static com.bob.utils.Utils.assignTutorsToStudents;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.bob.domain.Student;
import com.bob.domain.Subject;
import com.bob.domain.Tutor;
import com.bob.utils.Utils;

public class HibernateTestHarness14 {

	private static SessionFactory sessionFactory;

	public static void main(String[] args) {
		SessionFactory sf = getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		Tutor t1 = new Tutor("SID-04", "John von Neuman", 23456);
		
		/**
		 * illustrates why using the db id as a comparison for equals is a bad idea
		 * the following 3 objects would only result in 1 object being inserted into the Set!
		 */
		Student s1 = new Student("John Kelly", "EID-10-2015");
		Student s2 = new Student("Jack Kelly", "EID-11-2014");
		Student s3 = new Student("James Kelly", "EID-12-2016");
		
		t1.addStudentsToSupervisionGroup(s1); 
		t1.addStudentsToSupervisionGroup(s2);
		t1.addStudentsToSupervisionGroup(s3);
		
		Set<Student> allStudents = t1.getSupervisionGroup();
		allStudents.stream().forEach(s -> System.out.println(s));
		System.out.println("\n\n>>>"+ allStudents.size());
		session.save(s1);
		session.save(s2);
		session.save(s3);
		session.save(t1);
		
		tx.commit();
		session.close();
		
		
		/*session = sf.openSession();
		tx = session.beginTransaction();
		Tutor laura = (Tutor)session.get(Tutor.class, 1);
		Set<Student> studentsInLaurasSupervisionGroup = laura.getSupervisionGroup();
		
		boolean isRyanInIt = studentsInLaurasSupervisionGroup.contains(s2);
		System.out.println(isRyanInIt);
		
		tx.commit();
		session.close();*/
		
		//TEST OUTPUT from DB using Set for supervisionGroup in Tutor domain class.
	//	testDBRetrieval(session);
			
		// TEST OUTPUT from DB

		
		
		// START NEW DATA GENERATION and test
			
/*				Set<Subject> subjects = Utils.generateSampleSetOfSubjects();
				Map<String, Subject> subjectMap = Utils.generateSampleMapOfSubjects(); //might already be in DB - check!
				Set<Student> students = generateSampleSetOfStudents(subjectMap);
				Set<Tutor> tutors = generateSampleSetOfTutors(subjectMap);
					
				
				tutors = assignStudentsToTutors(students, tutors);
				// SAVE
				for (Subject s : subjectMap.values()) {
					session.save(s);
				}
				for (Student s : students) {
					session.save(s);
				}			
				for (Tutor t : tutors) {
					session.save(t);
				}*/
	

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





	private static void testDBRetrieval(Session session) throws HibernateException {
		Consumer<Tutor> printTutorsAndStudents = t -> {
			System.out.println("\nTutor : "+ t);
			Set<Student> studentMap = t.getSupervisionGroup();
			studentMap.forEach(s -> System.out.println(s));
		};
		Consumer<Student> printStudentsAndTheirAllocatedTutors = s -> {
			System.out.println(s);
		};
		
		List<Tutor> allTutors = new ArrayList<Tutor>();
			Query allTutorsQuery = session.createQuery("from Tutor");
		allTutors = allTutorsQuery.list();
			
		allTutors
		.stream()
		.forEach(printTutorsAndStudents);
		
		System.out.println("=============================");
		List<Student> studentListFromDB = new ArrayList<Student>();
		Query allStudents = session.createQuery("from Student");
		studentListFromDB = allStudents.list();
		
		studentListFromDB
		.stream()
		.forEach(printStudentsAndTheirAllocatedTutors);
	}

	
	
	
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			Configuration configuration = new Configuration();
			configuration.configure();

			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
					.buildServiceRegistry();

			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		return sessionFactory;
	}

}
