package com.practice.hibernate.HibetnateExample;

import java.util.List;
import java.util.Scanner;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

public class HibernateConfiguration {

	private static SessionFactory sessionfactory;
	Scanner s = new Scanner(System.in);

	@SuppressWarnings("deprecation")
	public static void connectDB() {
		Configuration configure = new Configuration().configure("HibernateSample.cfg.xml");
		sessionfactory = configure.buildSessionFactory();
		System.out.println("DataBase Connection is extablished");
	}

	public void insertValues() {
		StudentDetails sd = new StudentDetails();
		System.out.println("Enter the name ");
		String readthename = s.next();
		System.out.println("Enter the age ");
		int age = s.nextInt();
		System.out.println("Enter the id ");
		int id = s.nextInt();
		sd.setStudentName(readthename);
		sd.setStudentAge(age);
		sd.setStudentId(id);

		Session session = null;
		try {
			session = sessionfactory.openSession();
			session.save(sd);
			Transaction transaction = session.getTransaction();
			transaction.begin();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public void readFromTable() {
		Session session = null;
		try {
			session = sessionfactory.openSession();
			Criteria createCriteria = session.createCriteria(StudentDetails.class);
			List list = createCriteria.list();
			list.forEach(student -> {
				System.out.println(student.toString());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readSelectedFromTable() {

		Session session = null;
		try {
			System.out.println("Enter the name ");
			String readthename = s.next();
			System.out.println("Enter the age ");
			int age = s.nextInt();
			session = sessionfactory.openSession();
			Criteria createCriteria = session.createCriteria(StudentDetails.class);
			SimpleExpression eq = Restrictions.eq("studentName", readthename);
			SimpleExpression eq1 = Restrictions.eq("studentAge", age);
			LogicalExpression lo = Restrictions.and(eq, eq1);
			createCriteria.add(lo);
			List list = createCriteria.list();
			list.forEach(student -> {
				System.out.println(student.toString());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateToTable() {
		System.out.println("Enter the id where you want to update the name");
		int id = s.nextInt();
		System.out.println("Enter the name ");
		String readthename = s.next();
		Session session = null;

		try {
			session = sessionfactory.openSession();
			Transaction beginTransaction = session.beginTransaction();
			StudentDetails student = (StudentDetails) session.get(StudentDetails.class, id);
			if (student != null) {
				student.setStudentName(readthename);
			}
			session.save(student);
			beginTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteFromTable() {
		System.out.println("Enter the id where you want to update the name");
		int id = s.nextInt();

		Session session = null;

		try {
			session = sessionfactory.openSession();
			Transaction beginTransaction = session.beginTransaction();
			StudentDetails student = (StudentDetails) session.get(StudentDetails.class, id);
			session.delete(student);
			beginTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dropTable() {
		Session session = null;

		try {
			session = sessionfactory.openSession();
			Query createQuery = session.createSQLQuery("drop table studentdetailtb");
			createQuery.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		sessionfactory.close();
	}
}
