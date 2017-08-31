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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.bob.domain.Address;
import com.bob.domain.Address_;
import com.bob.domain.Student;
import com.bob.domain.Student_;
import com.bob.domain.Subject;
import com.bob.domain.Tutor;
import com.bob.domain.Tutor_;

public class JPAChapter20 {
	public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA_MYSQL_CONFIG");
	private static List list;

	public static void main(String[] args) {
		setUpData();
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		//CriteriaQuery<Student> criteria = builder.createQuery(Student.class);
		
		/**
		 * Using the meta model generator from Hibernate. 
		 * Benefits: better type safety; 
		 * downside: extra compilation step via hibernate metamodel code generation.
		 * Scans all entities and makes attributes public via a 'cloned' class
		 * How to activate:
		 * prefs > Java Compiler > Annotation processing: change to ./generated for the path
		 * prefs > Java Compilater > Annotation processing > Factory path: add the two hibernate jars
		 *    jpamodelgen and jpa-2.0-api
		 */
		
		CriteriaQuery<Tutor> criteria = builder.createQuery(Tutor.class).distinct(true);
		Root<Tutor> root = criteria.from(Tutor.class);
		Join<Tutor, Student> students = root.join(Tutor_.supervisionGroup);
		
		Path<Address> address = students.get(Student_.address);
		Path<String> city = address.get(Address_.city);
		criteria.where(builder.equal(city, "Georgia"));
		
		TypedQuery<Tutor> q = em.createQuery(criteria);
		q.getResultList().stream().forEach(System.out::println);
		
		
		/**
		 * tutors whose student lives in Georgia
		 */
//		CriteriaQuery<Tutor> criteria = builder.createQuery(Tutor.class).distinct(true);
//		Root<Tutor> root = criteria.from(Tutor.class);
//		Join<Tutor, Student> students = root.join("supervisionGroup");
//		
//		Path<Address> address = students.get("address");
//		Path<String> city = address.get("city");
//		criteria.where(builder.equal(city, "Georgia"));
		
		
		/**
		 * get students who have letter e in their name
		 */
//		Root<Student> root = criteria.from(Student.class);
//		criteria.where(builder.like(root.get("name"), "%e%"));
		
		/**
		 * Select students where supervisor is david banks
		 */
//		Path<Tutor> tutor = root.get("supervisor");
//		criteria.where(builder.equal(tutor.get("name"), "David Banks"));
		
		//17:00
		//criteria.where(builder.equal(root.get("name"), "Marco Fortes"));
		
		
		//Query q = em.createQuery(criteria);
		
		//q.getResultList().stream().forEach(System.out::println);
		
		
		
	
		tx.commit();
		em.close();

	} // ============== end of Main

	public static void setUpData() {
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
		t1.createStudentAndAddToSupervisionGroup("Kath Grainer", "2-GRA-2009", "2 Kaths Street", "Geor", "939393");
		t3.createStudentAndAddToSupervisionGroup("Sandra Perkins", "3-PER-2009", "4 The Avenue", "New York", "939393");

		tx.commit();
		em.close();
	}

	private static void testDBRetrieval(EntityManager em) {
		Consumer<Tutor> printTutorsAndStudents = t -> {
			System.out.println("\nTutor : " + t);
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
		allTutors.stream().forEach(printTutorsAndStudents);

		System.out.println("\n\nSTUDENTS =============================");
		List<Student> studentListFromDB = new ArrayList<Student>();
		Query allStudents = em.createQuery("SELECT e FROM Student e");
		studentListFromDB = allStudents.getResultList();

		studentListFromDB.stream().forEach(printStudentsAndTheirAllocatedTutors);

		System.out.println("\n\nSUBJECTS =============================");
		List<Subject> subjects = new ArrayList<Subject>();
		Query allSubjects = em.createQuery("SELECT e FROM Subject e");
		subjects = allSubjects.getResultList();

		subjects.stream().forEach(s -> System.out.println(s));

	}

}// end of testharness
