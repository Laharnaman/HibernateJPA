package com.bob.testharness;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.bob.domain.Student;
import com.bob.domain.Tutor;

public class HibernateTestHarnessUptoChapter8 {

	private static SessionFactory sessionFactory;
	
	public static void main(String[] args) {
		SessionFactory sf = getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();	
		
		Tutor tutor = new Tutor("AE09","Archimedes",1000);
		Student foundStudent = (Student) session.get(Student.class, 4);
		System.out.println(foundStudent);
//		foundStudent.allocateSupervisor(tutor);
//		session.save(tutor);
		
		tx.commit();
		
		
/*		Tutor tutor = new Tutor("AE08","Oliver Heaviside",100000);
		Student student = new Student("Bob Kelly");
		

		
		try {
			session.save(tutor);
			session.save(student);
			student.allocateSupervisor(tutor);
		
			tx.commit();
		}
		catch(Exception e){
			if(tx != null) tx.rollback();
			System.out.println(e);
			
		} finally {
			if (session != null) session.close();
		}
		*/
	}
	
	
	
	public static SessionFactory getSessionFactory()
	{
		if (sessionFactory == null)
		{
			Configuration configuration = new Configuration();
			configuration.configure();
			
			ServiceRegistry serviceRegistry = new 
					ServiceRegistryBuilder().applySettings(configuration.getProperties())
					.buildServiceRegistry();        

			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		return sessionFactory;
	}			

}
