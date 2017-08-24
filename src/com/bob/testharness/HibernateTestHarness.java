package com.bob.testharness;

import static com.bob.utils.Utils.assignStudentsToTutors;
import static com.bob.utils.Utils.generateSampleSetOfStudents;
import static com.bob.utils.Utils.generateSampleSetOfTutors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.bob.domain.Student;
import com.bob.domain.Tutor;

public class HibernateTestHarness {

	private static SessionFactory sessionFactory;

	public static void main(String[] args) {
		SessionFactory sf = getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		
		//TEST OUTPUT from DB using Map for supervisionGroup in Tutor domain class.
			List<Tutor> tutorListFromDB = new ArrayList<Tutor>();
			tutorListFromDB.add((Tutor)session.get(Tutor.class, 1));
			tutorListFromDB.add((Tutor)session.get(Tutor.class, 2));
			tutorListFromDB.add((Tutor)session.get(Tutor.class, 3));
			
				Consumer<Tutor> printTutorsAndStudents = t -> {
					System.out.println("\nTutor is:>>"+t.getName());
					Map<String,Student> studentMap = t.getSupervisionGroup();
					studentMap.keySet().forEach(k -> System.out.println("Key= "+k+ ", "+studentMap.get(k)));
				};
				
				tutorListFromDB
				.stream()
				.forEach(printTutorsAndStudents);
		// TEST OUTPUT from DB

		
		
		// START NEW DATA GENERATION
			/*	Set<Tutor> tutors = generateSampleSetOfTutors();
				Set<Student> students = generateSampleSetOfStudents();
				tutors = assignStudentsToTutors(students, tutors);*/
				
		
				
		
				/*for (Tutor t : tutors) {
					session.save(t);
				}
				for (Student s : students) {
					session.save(s);
				}*/
		// END NEW DATA GENERATION
		
		
		tx.commit();
		session.close();
	} // ============== end of Main

	
	
	
	
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
