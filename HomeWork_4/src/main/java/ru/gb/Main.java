package ru.gb;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.List;
public class Main {
        public static void main(String[] args) {
            Configuration configuration = new Configuration().configure();
            try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
                insertStudents(sessionFactory);
                show(sessionFactory);
                System.out.println("----------");
                getByAge(sessionFactory);
                System.out.println("----------");
                updateStudents(sessionFactory, 2L);
                show(sessionFactory);
                System.out.println("----------");
                removeStudents(sessionFactory, 3L);
                show(sessionFactory);
            }
        }

        public static void insertStudents(SessionFactory factory) {
            try (Session session = factory.openSession()) {
                session.beginTransaction();
                session.persist(
                        new Student(1L, "Иванов", "Иван", 28)
                );
                session.persist(
                        new Student(2L, "Петров", "Петр", 30)
                );
                session.persist(
                        new Student(3L, "Сидоров", "Иван", 40)
                );
                session.persist(
                        new Student(4L, "Смирнова", "Анна", 23)
                );
                session.persist(
                        new Student(5L, "Смирнова", "Инна", 48)
                );
                session.getTransaction().commit();
            }
        }

        public static void show(SessionFactory factory) {
            try (Session session = factory.openSession()) {
                Query<Student> query = session.createQuery("select s from Student s", Student.class);
                List<Student> resultList = query.getResultList();
                resultList.forEach(System.out::println);
            }
        }

        public static void getByAge(SessionFactory factory) {
            try (Session session = factory.openSession()) {
                Query<Student> query = session.createQuery("select s from Student s where age >= :age", Student.class);
                query.setParameter("age", 20);
                List<Student> resultList = query.getResultList();
                resultList.forEach(System.out::println);
            }
        }

        public static void updateStudents(SessionFactory factory, Long id) {
            try (Session session = factory.openSession()) {
                session.beginTransaction();
                Student student = session.find(Student.class, id);
                student.setFirstName("UPDATED");
                session.merge(student);
                session.getTransaction().commit();
            }
        }

        public static void removeStudents(SessionFactory factory, Long id) {
            try (Session session = factory.openSession()) {
                session.beginTransaction();
                Student student = session.find(Student.class, id);
                session.remove(student);
                session.getTransaction().commit();
            }
        }
    }